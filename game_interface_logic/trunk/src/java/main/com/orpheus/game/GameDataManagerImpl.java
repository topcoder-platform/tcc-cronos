/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.administration.persistence.OrpheusMessengerPlugin;
import com.orpheus.administration.persistence.RemoteOrpheusMessengerPlugin;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;

import com.topcoder.bloom.BitSetSerializer;
import com.topcoder.bloom.BloomFilter;
import com.topcoder.bloom.serializers.DefaultBitSetSerializer;
import com.topcoder.message.messenger.MessageAPI;
import com.topcoder.util.compression.CompressionUtility;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.generator.guid.UUIDType;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.url.validation.SiteValidationResults;
import com.topcoder.util.url.validation.SiteValidator;

import java.net.MalformedURLException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import com.topcoder.util.generator.guid.UUIDUtility;


/**
 * <p>
 * This is an implementation of the base manager abstract class. It overrides the key three methods and uses either the
 * local or remote ejb interface to enact persistence functionality. It provides persistence based mechanism for testing
 * whether an upcoming domain is ready to begin hosting a specific slot, provides a mechanism for programmatically
 * advancing to the next hosting slot, and finally it provides an implementation of functionality for recording
 * the IDs of the winning bids for the slots of a specified hosting block. It also defines two threads to act as
 * poll thread workers to actas notifiers of when new game data becomes available or when a game is ready to be started.
 * </p>
 * <p>
 * Thread safe:
 * This implementation class is thread-safe.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class GameDataManagerImpl extends BaseGameDataManager {
    /**
     * The property name used to get the new game poll interval.
     */
    private static final String NEW_GAME_POLL_INTERVAL = "new_game_poll_interval";

    /**
     * The property name used to get the game start poll interval.
     */
    private static final String GAME_START_POLL_INTERVAL = "game_started_poll_interval";

    /**
     * The property name used to get the BitSetSerializer specification namespace
     */
    private static final String BIT_SERIALIZER_SPEC = "bit_serializer_spec";

    /**
     * The property name used to get the BitSetSerializer object factory key
     */
    private static final String BIT_SERIALIZER_KEY = "bit_serializer_key";

    /**
     * The constant String Local.
     */
    private static final String LOCAL = JndiLookupDesignation.LOCAL.getName();

    /**
     * The constant String Remote.
     */
    private static final String REMOTE = JndiLookupDesignation.REMOTE.getName();

    /**
     * The property name used to load the jndi names from the configuration.
     */
    private static final String JNDI_NAMES = "jndi_names";

    /**
     * The name of the GUID message property to use when generating a Bloom Filter update message
     */
    private static final String ADMIN_MESSAGE_GUID = "guid";

    /**
     * The name of the CATEGORY message property to use when generating a Bloom Filter update message
     */
    private static final String ADMIN_MESSAGE_CATEGORY = "category";

    /**
     * The name of the CONTENT TYPE message property to use when generating a Bloom Filter update message
     */
    private static final String ADMIN_MESSAGE_CONTENT_TYPE = "contentType";

    /**
     * The name of the MESSAGE CONTENT message property to use when generating a Bloom Filter update message
     */
    private static final String ADMIN_MESSAGE_CONTENT = "content";

    /**
     * The name of the TIMESTAMP message property to use when generating a Bloom Filter update message
     */
    private static final String ADMIN_MESSAGE_TIMESTAMP = "timestamp";

    /**
     * <p>
     * Represents a notification thread which will poll the server for new game entries at some regular.
     * It can not be null.
     * </p>
     */
    private final NewGameAvailableNotifier newGameAvailableNotifier;

    /**
     * <p>Represents a notification thread which will poll the current list of not yet
     * started games game entries that should bestarted at some regular,
     * configured through <li>gameStartedPollInterval</li> variable. It can not be null.
     * </p>
     *
     */
    private final GameStartNotifier gameStartNotifier;

    /**
     * <p>
     * Represents the remote ejb interface used for persistence.
     * </p>
     *
     */
    private final GameData gameDataPersistenceRemote;

    /**
     * <p>
     * Represents the local ejb interface used for persistence.
     * This is set based on the fact that a JndiLookupDesignation.LOCAL was used when looking up the ejb.
     * It can be null, which would mean that the local interface must be set.
     * We can not have a situation where both this and the gameDataPersistenceRemote variable are both null.
     * </p>
     *
     */
    private final GameDataLocal gameDataPersistenceLocal;

    /**
     * <p>
     * Represents a mutable flag that signifies if the manager is stopped or working.
     * It is changed by the stopManager method to true.
     * At any point all methods in the API should test this variable and
     * if the variable is true IllegalStateException should be thrown.  Thread-safety
     * demands that all tests and updates of this array's element be synchronized on
     * a common object; the object designated for that purpose is the array itself.
     * </p>
     */
    private final boolean[] stopped = new boolean[] { false };

    /**
     * The temporary variable to hold the remote EJB.
     */
    private GameData remoteEJB = null;

    /**
     * The temporary variable to hold the local EJB.
     */
    private GameDataLocal localEJB = null;
    /**
     * The capacity of the Bloom Filter.
     */
    private final int capacity;
    /**
     * The errorrate of the Bloom Filter.
     */
    private final float errorRate;
    /**
     * The namespace to create the messager pluin instance.
     */
    private final String messagerPluginNS;
    /**
     * The category of the component.
     */
    private final String category;

    /**
     * A BitSetSerializer with which to configure BloomFilter objects created by this GameDataManager
     */
    private final BitSetSerializer bitSetSerializer;

    /**
     * <p>
     * Creates a new GameDataManagerImpl instance based on default namespace configuration.
     * </p>
     *
     * @throws GameDataManagerConfigurationException if there are any issues with configuration
     * @throws GameDataException if any other error occurs
     */
    public GameDataManagerImpl()
        throws GameDataManagerConfigurationException, GameDataException {
        this(GameDataManagerImpl.class.getName());
    }

    /**
     * <p>
     * Creates a new GameDataManagerImpl instance based on the input namespace configuration.
     * </p>
     *
     * @param namespace configuration namespace
     * @throws IllegalArgumentException if the namespace is null or empty String
     * @throws GameDataManagerConfigurationException if there are any issues with configuration
     * @throws GameDataException if any other error occurs
     */
    public GameDataManagerImpl(String namespace)
        throws GameDataManagerConfigurationException, GameDataException {
        Helper.checkStringNotNullOrEmpty(namespace, "namespace");

        String[] jndiNameValues = Helper.getMandatoryPropertyArray(namespace,
                JNDI_NAMES);

        String[] jndiNames = new String[jndiNameValues.length];
        String[] jndiDesignations = new String[jndiNameValues.length];

        //parse the values, the format should be 'jndiname,Remote' or 'jndiname,Local'
        for (int i = 0; i < jndiDesignations.length; i++) {
            int index = jndiNameValues[i].lastIndexOf(",");

            if (index < 0) {
                throw new GameDataManagerConfigurationException(
                    "The value for property jndi_names should be "
                    + " 'jndiname,Remote' or 'jndiname,Local'.");
            }

            jndiNames[i] = jndiNameValues[i].substring(0, index).trim();
            jndiDesignations[i] = jndiNameValues[i].substring(index + 1).trim();

            //check the jndi names should not be empty, and the designations should be 'local' or 'remote'
            if ((jndiNames[i].length() == 0) || (!LOCAL.equalsIgnoreCase(jndiDesignations[i])
                    && !REMOTE.equalsIgnoreCase(jndiDesignations[i]))) {
                throw new GameDataManagerConfigurationException(
                    "The value for property jndi_names should be  'jndiname,Remote' or 'jndiname,Local'.");
            }
        }

	// initialize the bitSetSerializer
        String serializerSpecNamespace;

        try {
            serializerSpecNamespace = ConfigManager.getInstance().getString(
			namespace, BIT_SERIALIZER_SPEC);
        } catch (UnknownNamespaceException e) {
            throw new GameDataManagerConfigurationException("The namespace '"
                + namespace + "' is missing in configuration.", e);
        }

        try {
	    if (serializerSpecNamespace == null || serializerSpecNamespace.length() == 0) {
		bitSetSerializer = new DefaultBitSetSerializer();
	    } else {
		String bitSerializerKey = Helper.getMandatoryProperty(
                        namespace, BIT_SERIALIZER_KEY);
                ObjectFactory factory = new ObjectFactory(
			new ConfigManagerSpecificationFactory(serializerSpecNamespace));

		bitSetSerializer = (BitSetSerializer) factory.createObject(bitSerializerKey);
	    }
        } catch (Exception e) {
            throw new GameDataManagerConfigurationException("Error creating BitSetSerializer", e);
        }

        long gameStartInterval;
        long newGameInterval;

        try {
            gameStartInterval = Long.parseLong(Helper.getMandatoryProperty(
                        namespace, GAME_START_POLL_INTERVAL));
            newGameInterval = Long.parseLong(Helper.getMandatoryProperty(
                        namespace, NEW_GAME_POLL_INTERVAL));

            capacity = Integer.parseInt(Helper.getMandatoryProperty(namespace, "capacity"));
            errorRate = Float.parseFloat(Helper.getMandatoryProperty(namespace, "errorRate"));
            messagerPluginNS = Helper.getMandatoryProperty(namespace, "messengerPluginNS");
            category = Helper.getMandatoryProperty(namespace, "category");
            //check the intervals should be > 0
            if (gameStartInterval <= 0) {
                throw new GameDataManagerConfigurationException(
                    "The game start interval should be > 0.");
            }

            if (newGameInterval <= 0) {
                throw new GameDataManagerConfigurationException(
                    "The new game interval should be > 0.");
            }
        } catch (NumberFormatException e) {
            throw new GameDataManagerConfigurationException("The value of interval properties should be numbers.",
                e);
        }

        lookUpEJB(jndiNames, jndiDesignations);

        //if no EJB is looked up successfully
        if ((remoteEJB == null) && (localEJB == null)) {
            throw new GameDataManagerConfigurationException(
                "No EJB can be looked up via the JNDI names configed.");
        }

        Game[] games = null;

        //init the EJB instance
        this.gameDataPersistenceLocal = localEJB;
        this.gameDataPersistenceRemote = remoteEJB;

        try {
            if (gameDataPersistenceLocal != null) {
                games = gameDataPersistenceLocal.findGames(Boolean.FALSE, null);
            } else {
                games = gameDataPersistenceRemote.findGames(Boolean.FALSE, null);
            }
        } catch (Exception e) {
            throw new GameDataException("can not get the games via the EJB.", e);
        }

        if (games == null) {
            games = new Game[0];
        }
        Long[] ids = new Long[games.length];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = games[i].getId();
        }

        //init the notifiers
        gameStartNotifier = new GameStartNotifier(gameStartInterval, this);

        if (gameDataPersistenceLocal != null) {
            newGameAvailableNotifier = new NewGameAvailableNotifier(newGameInterval,
                    this, gameDataPersistenceLocal, ids);
        } else {
            newGameAvailableNotifier = new NewGameAvailableNotifier(newGameInterval,
                    this, gameDataPersistenceRemote, ids);
        }

        //start threads
        gameStartNotifier.start();
        newGameAvailableNotifier.start();
    }

    /**
     * <p>
     * Creates a new GameDataManagerImpl instance based on input parameters.
     * </p>
     *
     * @param jndiNames an array of jndi names to use when looking up the ejb
     * @param jndiDesignations jndi designations such as Local or Remote
     * @param newGameDiscoveryPollInterval interval used to configure the poll frequency (ms)
     * @param gameStartedPollInterval interval used to configure the poll frequency (ms)
     * @throws IllegalArgumentException if any array is null or contains null element,
     * or <code>jndiNames</code> contains empty trimmed String,
     * or <code>jndiDesignations</code> contains Strings that not Remote or Local,
     * or two array String not of the same length,
     * or any long parameters <= 0,
     * or no EJB can be looked up with the given jndis
     * @throws GameDataException if any other error occurs
     */
    public GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations,
        long newGameDiscoveryPollInterval, long gameStartedPollInterval,
        int capacity, float errorRate, String messengerPluginNS, String category)
        throws GameDataException {
        Helper.checkObjectNotNull(jndiNames, "jndiNames");
        Helper.checkObjectNotNull(jndiDesignations, "jndiDesignations");
        Helper.checkStringNotNullOrEmpty(messengerPluginNS, "messengerPluginNS");

        if (jndiNames.length != jndiDesignations.length) {
            throw new IllegalArgumentException(
                "The jndiNames and jndiDesignations should be of same length.");
        }

        //each jndi name can not be null or empty string and the Designation should be either Remote or Local
        for (int i = 0; i < jndiNames.length; i++) {
            Helper.checkStringNotNullOrEmpty(jndiNames[i],
                "jndiNames[" + i + "]");

            if ((!LOCAL.equalsIgnoreCase(jndiDesignations[i]) && !REMOTE.equalsIgnoreCase(jndiDesignations[i]))) {
                throw new IllegalArgumentException(
                    "The value for property jndi_names should be  'jndiname,Remote' or 'jndiname,Local'.");
            }
        }

        if (newGameDiscoveryPollInterval <= 0) {
            throw new IllegalArgumentException(
                "The new game interval should be > 0.");
        }

        if (gameStartedPollInterval <= 0) {
            throw new IllegalArgumentException(
                "The game start interval should be > 0.");
        }
        if (errorRate <= 0 || errorRate >= 1.0) {
            throw new IllegalArgumentException("The errorRate should be between 0 and 1.");
        }

        this.capacity = capacity;
        this.errorRate = errorRate;
        this.messagerPluginNS = messengerPluginNS;
        this.category = category;
	bitSetSerializer = new DefaultBitSetSerializer();
        lookUpEJB(jndiNames, jndiDesignations);

        //if no EJB is looked up successfully
        if ((remoteEJB == null) && (localEJB == null)) {
            throw new IllegalArgumentException(
                "No EJB can be looked up via the JNDI names configed.");
        }

        Game[] games = null;

        //init the EJB instance
        this.gameDataPersistenceLocal = localEJB;
        this.gameDataPersistenceRemote = remoteEJB;

        try {
            if (gameDataPersistenceLocal != null) {
                games = gameDataPersistenceLocal.findGames(Boolean.FALSE, null);
            } else {
                games = gameDataPersistenceRemote.findGames(Boolean.FALSE, null);
            }
        } catch (Exception e) {
            throw new GameDataException("can not get the games via the EJB.", e);
        }

        if (games == null) {
            games = new Game[0];
        }
        Long[] ids = new Long[games.length];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = games[i].getId();
        }

        //init the notifiers
        gameStartNotifier = new GameStartNotifier(gameStartedPollInterval, this);

        if (gameDataPersistenceLocal != null) {
            newGameAvailableNotifier = new NewGameAvailableNotifier(newGameDiscoveryPollInterval,
                    this, gameDataPersistenceLocal, ids);
        } else {
            newGameAvailableNotifier = new NewGameAvailableNotifier(newGameDiscoveryPollInterval,
                    this, gameDataPersistenceRemote, ids);
        }

        //start threads
        gameStartNotifier.start();
        newGameAvailableNotifier.start();
    }

    /**
     * <p>
     * Look up the EJB with the given names and desigantions.
     * Once a local or a remote EJB is looked up successfully,
     * stop looking up immediately.
     * </p>
     *
     * @param jndiNames the names of the JNDI
     * @param jndiDesignations the designations denote remote or local
     */
    private void lookUpEJB(String[] jndiNames, String[] jndiDesignations) {
        InitialContext ctx = null;

        //look up the EJB via the JNDI
        try {
            ctx = new InitialContext();
        } catch (Exception e) {
            //ignore
        }

        for (int i = 0; i < jndiNames.length; i++) {
            try {
                //if the JNDI is expected to be local, init the localEJB
                if (LOCAL.equalsIgnoreCase(jndiDesignations[i])) {
                    GameDataLocalHome home = (GameDataLocalHome) ctx.lookup(jndiNames[i]);
                    localEJB = (GameDataLocal) home.create();
                } else {
                    Object lookup = ctx.lookup(jndiNames[i]);
                    GameDataHome home = (GameDataHome) PortableRemoteObject.narrow(lookup, GameDataHome.class);
                    //init the remote EJB otherwise
                    remoteEJB = (GameData) home.create();
                }
            } catch (Exception e) {
                //go and continue to find a Remote or Local EJB
                continue;
            }

            //if any type of ejb is looked successfully, break;
            break;
        }
    }

    /**
     * <p>
     * Tests whether an upcoming domain is ready to begin hosting a specific slot.
     * Bascially we need to ensure that the
     * domain's document root and the documents hosting all the slot's domain targets are all reachable.
     * Note that this implementation is inherently thread-safe since it
     * delegates to an EJB and container will ensure thread-safe invocation.
     * </p>
     *
     * @param slot hosting slot ot validator
     * @return true if the host os valid; false otherwise
     * @throws IllegalArgumentException if the slot is null
     * @throws GameDataException if a checked exception prevents this method from completing successfully.
     * @throws IllegalStateException if the manager has been stopped.
     */
    public boolean testUpcomingDomain(HostingSlot slot)
        throws GameDataException {
        Helper.checkObjectNotNull(slot, "slot");

        if (isStopped()) {
            throw new IllegalStateException(
                "The game data manager is already stopped.");
        }

        //get the domain and the domain targets to test
        Domain domain = slot.getDomain();
        DomainTarget[] targets = slot.getDomainTargets();

        try {
            if (domain != null) {
                //create the SiteValidator and to the site validation
                SiteValidator sv = new SiteValidator("http://" + domain.getDomainName() + "/");
                SiteValidationResults result = sv.validateTarget(0);

                //if the domain can not pass the validatiton, return false directly
                if (result.getInvalidCount() > 0) {
                    return false;
                }

                if (targets != null) {
                    for (int i = 0; i < targets.length; i++) {
                        sv = new SiteValidator(targets[i].getUriPath());
                        result = sv.validateTarget(0);

                        //if any domain target can not pass the validatiton, return false directly
                        if (result.getInvalidCount() > 0) {
                            return false;
                        }
                    }
                }
            }
        } catch (MalformedURLException e) {
            throw new GameDataException("MalformedURLException occurs when get the SiteValidator.", e);
        }

        //all right, return true
        return true;
    }

    /**
     * <p>
     * Records the IDs of the winning bids for the slots in the specified hosting block.
     * It is expected that the component will shuffle the provided IDs into a random order before
     * handing them off to the associated persistence component to do the real work.
     *
     * Note that this implementation is inherently thread-safe since it delegates to an
     * EJB and container will ensure thread-safe invocation.
     * </p>
     *
     *
     * @param blockId block id
     * @param bidIds an array of block ids
     * @throws IllegalArgumentException if the bidIds is null
     * @throws GameDataException if a checked exception prevents this method from completing successfully,
     * or if bid IDs have already been assigned to the block.
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void recordWinningBids(long blockId, long[] bidIds)
        throws GameDataException {
        Helper.checkObjectNotNull(bidIds, "bidIds");

        if (isStopped()) {
            throw new IllegalStateException(
                "The game data manager is already stopped.");
        }

        long[] randomizedBidIds = new long[bidIds.length];
        List bidIdList = new ArrayList(bidIds.length);

        for (int i = 0; i < bidIds.length; i++) {
            bidIdList.add(new Long(bidIds[i]));
        }

        Collections.shuffle(bidIdList);

        for (int i = 0; i < bidIdList.size(); i++) {
            randomizedBidIds[i] = ((Long) bidIdList.get(i)).longValue();
        }

        try {
            if (gameDataPersistenceLocal != null) {
                gameDataPersistenceLocal.createSlots(blockId, randomizedBidIds);
            } else {
                gameDataPersistenceRemote.createSlots(blockId, randomizedBidIds);
            }
        } catch (Exception e) {
            throw new GameDataException("Exception occurs when create slots with EJB.", e);
        }
    }

    /**
     * <p>
     * This call will advance to the next hosting slot by setting the hosting
     * end timestamp on the current slot and the hosting start timestamp on the next one in the same game.
     * When it does this, it should also test the subsequent domain
     * to make sure it is still valid; if not then it will flag the slot as inaccessible by setting
     * its sequence number to -1. Note that this implementation is inherently thread-safe since
     * it delegates to an EJB and container will ensure thread-safe invocation.
     * </p>
     *
     * @param gameId id of the game
     * @throws GameDataException if a checked exception prevents this method from completing successfully.
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void advanceHostingSlot(long gameId) throws GameDataException {
        if (isStopped()) {
            throw new IllegalStateException(
                "The game data manager is already stopped.");
        }

        Game game;

        try {
            //get the game with the given id
            if (gameDataPersistenceLocal != null) {
                game = gameDataPersistenceLocal.getGame(gameId);
            } else {
                game = gameDataPersistenceRemote.getGame(gameId);
            }
        } catch (Exception e) {
            throw new GameDataException("can not get the game via the EJB.", e);
        }
        if (game == null) {
            throw new GameDataException("No Game can be retrieved with the id.");
        }

        //get the blocks of the game
        HostingBlock[] blocks = game.getBlocks();
        if (blocks == null) {
            return;
        }

        hosting_blocks:
        for (int i = 0; i < blocks.length; i++) {
            HostingSlot[] slots = blocks[i].getSlots();

            // if no slots exist, just go on to the next block
            if (slots == null) {
                continue hosting_blocks;
            }

            // set the end date of the current slot and the start date of the next slot to now
            find_current_slot:
            for (int j = 0; j < slots.length; j++) {

                // if this is the current slot ...
                if ((slots[j].getHostingStart() != null) && (slots[j].getHostingEnd() == null)) {
                    List slotsToUpdate = new ArrayList();
                    int nextSlotBlockIndex = i;
                    HostingSlot[] nextSlots = slots;
                    int searchIndex = j + 1;
                    Date current = new Date();
                    int nextSlotIndex;

                    // set end date of this slot to NOW
                    slotsToUpdate.add(Helper.copyToSetEndDate(slots[j], current));

                    // find the next slot, and mark any invalid slots between
                    find_next_slot:
                    for (nextSlotIndex = findNextSlot(nextSlots, searchIndex); ;
                            nextSlotIndex = findNextSlot(nextSlots, searchIndex)) {
                        if (nextSlotIndex < 0) {

                            // all the slots from the search index to the end of the array need to be updated
                            // (they are invalid)
                              slotsToUpdate.addAll(Arrays.asList(nextSlots).subList(searchIndex, nextSlots.length));

                            // Try the next block, if there is one
                            if (++nextSlotBlockIndex < blocks.length) {
                                 nextSlots = blocks[nextSlotBlockIndex].getSlots();
                                searchIndex = 0;
                            } else {
                                // out of slots
                                // extra slot generation could be inserted here
                                break find_next_slot;
                            }
                        } else {
                            // slots from the search index to just before the nextSlotIndex are invalid
                              slotsToUpdate.addAll(Arrays.asList(nextSlots).subList(searchIndex, nextSlotIndex));

                            // start the next slot by making its start date be NOW
                            slotsToUpdate.add(Helper.copyToSetStartDate(nextSlots[nextSlotIndex], current));
                            break find_next_slot;
                        }
                    }

                    slots = (HostingSlot[]) slotsToUpdate.toArray(new HostingSlot[slotsToUpdate.size()]);

                    // record slot changes to the DB
                    try {
                        if (gameDataPersistenceLocal != null) {
                            gameDataPersistenceLocal.updateSlots(slots);
                        } else {
                            gameDataPersistenceRemote.updateSlots(slots);
                        }
                    } catch (Exception e) {
                        throw new GameDataException("Exception occurs when update slots with EJB.", e);
                    }

                    // update the Bloom Filter
                    sendBloomFilterUpdate();

                    // done
                    break hosting_blocks;
                }
            }
        }
    }

    /**
     * Finds a among the provided slots the first suitable one to which the Ball
     * may be advanced
     *
     * @param  slots a <code>HostingSlot[]</code> containing the candidate
     *         slots; some or all of the elements starting at the test index
     *         may be replaced with copies modified to mark them as invalid
     * @param  testIndex the index into the <code>slots</code> of the first
     *         candidate
     *
     * @return the index into the <code>slots</code> array of the slot that
     *         should be next, or <code>-1</code> if there is no suitable candidate
     */
    private int findNextSlot(HostingSlot[] slots, int testIndex) throws GameDataException {
        for (; testIndex < slots.length; testIndex++) {
            if (slots[testIndex].getSequenceNumber() < 0) {
                continue;
            } else if (testUpcomingDomain(slots[testIndex])) {
                return testIndex;
            } else {
                // not valid: set the sequence number to a negative number
                slots[testIndex] = Helper.copyToSequenceNumber(
                        slots[testIndex], -slots[testIndex].getSequenceNumber() - 1);
            }
        }

        return -1;
    }

    /**
     * <p>
     * This is a clean up routine for the manager. If the manager uses any kind of remote resources such as
     * db connections, or threads this method would be implemented to call on all the resources to clean up.
     * Once the manager is stopped it is no longer operational and all methods
     * should be throwing IllegalStateException.
     * Any other exceptions should be wrapped into GameDataException.
     * </p>
     *
     * @throws GameDataException if there were any issues with this method call.
     */
    public void stopManager() throws GameDataException {
        synchronized (stopped) {
            stopped[0] = true;
        }

        gameStartNotifier.stopNotifier();
        newGameAvailableNotifier.stopNotifier();
        try {
            gameStartNotifier.join();
            newGameAvailableNotifier.join();
        } catch (Exception e) {
            throw new GameDataException("can not stop the threads.", e);
        }
    }

    /**
     * Creates a <code>BloomFilter</code> containing all the active domains,
     * and broadcasts a message containing it to all players via their plug-ins'
     * messaging channel.
     */
    void sendBloomFilterUpdate() throws GameDataException {
        Domain [] domains;

        try {
            if (gameDataPersistenceRemote != null) {
                domains = gameDataPersistenceRemote.findActiveDomains();
            } else {
                domains = gameDataPersistenceLocal.findActiveDomains();
            }

            if (domains != null) {
                BloomFilter bloomFilter = new BloomFilter(capacity, errorRate, bitSetSerializer);

                //add the names of the domain to the Bloom Filter
                for(int j = 0; j < domains.length; j++) {
                    bloomFilter.add(domains[j].getDomainName());
                }

                OrpheusMessengerPlugin plugin = new RemoteOrpheusMessengerPlugin(messagerPluginNS);
                MessageAPI message = plugin.createMessage();

                message.setParameterValue(ADMIN_MESSAGE_GUID,
                        UUIDUtility.getNextUUID(UUIDType.TYPE1).toString());
                message.setParameterValue(ADMIN_MESSAGE_CATEGORY,
                        category);
                message.setParameterValue(ADMIN_MESSAGE_CONTENT_TYPE,
                        "application/x-tc-bloom-filter");
                message.setParameterValue(ADMIN_MESSAGE_CONTENT,
                        toBase64(bloomFilter.getSerialized()));
                message.setParameterValue(ADMIN_MESSAGE_TIMESTAMP,
                        new Date());
                plugin.sendMessage(message);
            }
        } catch (Exception e) {
            throw new GameDataException(
                    "An exception occurred while broadcasting a Bloom Filter update", e);
        }
    }

    /**
     * Creates a String containing a BASE-64 encoding of the UTF-8 encoding of the characters in the
     * specified string
     *
     * @param  s a <code>String</code> containing the source characters
     *
     * @return a <code>String</code> containing the BASE-64 encoding
     *
     * @throws UnsupportedEncodingException if the VM is non-compliant by not supporting UTF-8 or US-ASCII
     * @throws ClassNotFoundException if the TopCoder Base64Codec class is not founf in the ClassPath
     * @throws IllegalAccessException if the version of the Base64Codec class found in the classpath has been
     *         modified so that the class itself or the required constructor is not accessible
     * @throws InstantiationException if the Base64Codec class's constructor throws any exception
     * @throws IOException if an I/O error occurs during internal I/O to perform the encoding
     */
    private String toBase64(String s) throws UnsupportedEncodingException, ClassNotFoundException,
            IllegalAccessException, InstantiationException, IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        CompressionUtility utility = new CompressionUtility("com.topcoder.util.compression.Base64Codec", stream);

        utility.compress(new ByteArrayInputStream(s.getBytes("UTF-8")));
        utility.close();

        return stream.toString("US-ASCII");
    }

    /**
     * <p>
     * Checks whether the manager is stopped.
     * </p>
     *
     * @return true if the manager is stopped, otherwise false.
     */
    public boolean isStopped() {

        /*
         * Implementation Note: this method performs appropriate synchronization when
         * testing the flag.  It is recommended that internal tests of the stop flag
         * use this method so as to not have to perform synchronization themselves.
         */
        synchronized (stopped) {
            return stopped[0];
        }
    }

    /**
     * <p>Update the slot in db, that is to set the start date of slot.</p>
     * @see com.orpheus.game.BaseGameDataManager#startGameInDB(com.orpheus.game.persistence.HostingSlot)
     */
    protected void persistSlot(HostingSlot slot) {
        Helper.checkObjectNotNull(slot, "hostingSlot to update");

        try {
            if (this.gameDataPersistenceRemote != null) {
                this.gameDataPersistenceRemote.updateSlots(new HostingSlot[] { slot });
            } else {
                this.gameDataPersistenceLocal.updateSlots(new HostingSlot[] { slot });
            }
        } catch(Exception e) {
            //ignore
        }
    }

    /**
     * <p>
     * This is a simple inner class, which runs as a worker thread and checks,
     * at specified intervals, if games (which have not yet started) should be started.
     * This class accepts a registered listener, which gets notified whenever a game has been started.
     * This is an internal worker thread class so it is guaranteed to be only executed by a single thread.
     * Note also that we do not have to worry about concurrency issues as far as the list of games is
     * concerned since we are working on a copy so the original could actually be concurrently updated.
     * </p>
     *
     */
    private class GameStartNotifier extends Thread {
        /**
         * <p>
         * Represents the sleep interval in ms which determines how often this thread checks if games have started.
         * </p>
         *
         */
        private final long sleepInterval;

        /**
         * <p>
         * Represents the listener registered with this thread that is interested in
         * receiving the notification that a game has started.
         * </p>
         *
         */
        private final GameStartListener gameStartListener;

        /**
         * <p>
         * Represents the flag (used to stop the thread) that is raised to signal the
         * thread to stop execution.  The flag value is the single element of the array,
         * and if set to <code>true</code> then the thread will stop.  Thread safety
         * demands that all reads and updates of the flag value be synchronized on a
         * common object; the designated object for this purpose is the array object itself.
         * </p>
         */
        private final boolean[] stopped = new boolean[] { false };

        /**
         * <p>
         * Creates a new instance initialized with the parameters.
         * </p>
         *
         * @param sleepInterval interval which dictates how often the thread checks if a game has started.
         * The unit is in milliseconds (ms)
         * @param listener registered listener that is interested in receiving notification about games starting
         */
        public GameStartNotifier(long sleepInterval, GameStartListener listener) {
            this.sleepInterval = sleepInterval;
            this.gameStartListener = listener;
        }

        /**
         * <p>
         * This is the thread run method which will use parent GameDataManagerImpl#getAllCurrentNotStartedGames to see
         * if any games should be started (by checking their start time with current time).
         * </p>
         *
         */
        public void run() {
            System.err.println("GameStartNotifier started");

            // it will not stop until the manager stops it
            for (;;) {
                try {
                    Thread.sleep(sleepInterval);
                } catch (InterruptedException e) {
                    //ignore
                }

                synchronized (stopped) {
                    if (stopped[0]) {
                        break;
                    }
                }

                //get the current games
                Game[] games = getAllCurrentNotStartedGames();

                // for each game see if it needs to be started
                for (int i = 0; i < games.length; i++) {
                    try {
                        Game unstartedGame;

                        //reload the game
                        if (gameDataPersistenceRemote != null){
                            unstartedGame = gameDataPersistenceRemote.getGame(games[i].getId().longValue());
                        } else {
                            unstartedGame = gameDataPersistenceLocal.getGame(games[i].getId().longValue());
                        }

                        // check whether this game need to be started
                        if (!unstartedGame.getStartDate().after(new Date())) {
                            // start the game
                            gameStatusChangedToStarted(unstartedGame);

                            System.err.println("Starting game " + unstartedGame.getName());

                            // broadcast a Bloom Filter update, if possible
                            sendBloomFilterUpdate();
                        }
                    } catch (Exception e) {
                        //ignore
                    }
                }
            }

            System.err.println("GameStartNotifier stopped");
        }

        /**
         * <p>
         * Signals the thread to stop by raising the stop flag and interrupting this thread.
         * </p>
         */
        public void stopNotifier() {
            synchronized(stopped){
                stopped[0] = true;
            }
            this.interrupt();
        }
    }

    /**
     * <p>
     * This is a simple class, which runs as a worker thread and checks, at specified intervals,
     * if new game data is available on
     * the server. This class is ¡®smart¡¯ enough to work with both
     * local and remote ejb interfaces transparently.
     * This class accepts a registered listener,
     * which gets notified whenever a new game has indeed been found on the server.
     * This is achieved by keeping a cache of 'known' games, which gets updated whenever a new game is discovered.
     * The memory consumption for this should not be very large
     * since we are only caching ids and not the whole game data.
     * This is an internal worker thread class so it is guaranteed to be only executed by a single thread.
     * </p>
     *
     * <p>
     * This is an inner class of the GameDataManagerImpl
     * class and as such has access to all its veriables and methods.
     * </p>
     */
    private class NewGameAvailableNotifier extends Thread {
        /**
         * <p>
         * Represents the sleep interval in ms which determines how often this
         * thread checks the availability of new games on the server.
         * It must be larger than 0.
         * </p>
         *
         */
        private final long sleepInterval;

        /**
         * <p>
         * Represents the listener registered with this thread that is
         * interested in receiving the notification that a new game has become available on the server.
         * </p>
         *
         */
        private final NewGameAvailableListener newGameAvailableListener;

        /**
         * <p>
         * Represents the remote ejb interface used to check if any new games have been created on the server.
         * </p>
         *
         */
        private final GameData gameDataPersistenceRemote;

        /**
         * <p>
         * Represents the local ejb interface used to check if any new games have been created on the server.
         * </p>
         *
         */
        private final GameDataLocal gameDataPersistenceLocal;

        /**
         * <p>
         * Represents the flag (used to stop the thread) that is raised to signal the
         * thread to stop execution.  The flag value is the single element of the array,
         * and if set to <code>true</code> then the thread will stop.  Thread safety
         * demands that all reads and updates of the flag value be synchronized on a
         * common object; the designated object for this purpose is the array object itself.
         * </p>
         */
        private final boolean[] stopped = new boolean[] { false };

        /**
         * <p>
         * Represents cached (already seen) game ids. This is used to decide if a new game has indeed been discovered.
         * This is set in the constructor and is initialized with data from
         * the knownGames paarameter, it then gets new ids added to it as new games become available.
         * A list of Long instances. Can not have null elements. Can not have duplicate elements
         * </p>
         *
         */
        private final List cachedGameIds = new ArrayList();

        /**
         * <p>
         * Creates a new instance initialized with the parameters include the <code>GameData</code>.
         * </p>
         *
         * @param sleepInterval interval which dictates how often the thread polls the server, the unit is milliseconds.
         * @param listener the NewGameAvailableListener instance.
         * @param gameData Remote ejb interface used to get persistence data. Used in looking up the new game data
         * @param knownGames this is a listing of games of which the manager is already aware of
         * @throws IllegalArgumentException if the interval is <=0; or if we get a null in any of the parameter inputs.
         */
        public NewGameAvailableNotifier(long sleepInterval,
            NewGameAvailableListener listener, GameData gameData,
            Long[] knownGames) {
            this(sleepInterval, listener, gameData, null, knownGames);
        }

        /**
         * <p>
         * Creates a new instance initialized with the parameters include the <code>GameDataLocal</code>.
         * </p>
         *
         * @param sleepInterval interval which dictates how often the thread polls the server.
         * The unit is in milliseconds (ms)
         * @param listener the NewGameAvailableListener instance.
         * @param gameDataLocal Local ejb interface used to get persistence data. Used in looking up the new game data
         * @param knownGames this is a listing of games of which the manager is already aware of
         * @throws IllegalArgumentException if the interval is non-positive or if any argument is <code>null</code>
         */
        public NewGameAvailableNotifier(long sleepInterval,
            NewGameAvailableListener listener, GameDataLocal gameDataLocal,
            Long[] knownGames) {
            this(sleepInterval, listener, null, gameDataLocal, knownGames);
        }

        /**
         * <p>
         * A helper private constructor, include all the parameters.
         * </p>
         *
         * @param sleepInterval interval which dictates how often the thread polls the server, the unit is milliseconds.
         * @param listener the NewGameAvailableListener instance.
         * @param gameDataLocal Local ejb interface used to get persistence data. Used in looking up the new game data
         * @param knownGames this is a listing of games of which the manager is already aware of
         * @param gameData the Remote EJB interface
         * @throws IllegalArgumentException if the interval is non-positive,
         * or the listener is null,
         * or gameData and gameDataLocal are both null,
         * or knownGames is null
         */
        private NewGameAvailableNotifier(long sleepInterval,
            NewGameAvailableListener listener, GameData gameData,
            GameDataLocal gameDataLocal, Long[] knownGames) {
            if (sleepInterval <= 0) {
                throw new IllegalArgumentException(
                    "The sleep interval can not be <= 0.");
            }

            if ((gameData == null) && (gameDataLocal == null)) {
                throw new IllegalArgumentException(
                    "The gameData and gameDataLocal can not be both null.");
            }

            Helper.checkObjectNotNull(listener, "The NewGameAvailableListener");
            Helper.checkObjectNotNull(knownGames, "knownGames");
            this.sleepInterval = sleepInterval;
            this.newGameAvailableListener = listener;
            this.gameDataPersistenceRemote = gameData;
            this.gameDataPersistenceLocal = gameDataLocal;

            for (int i = 0; i < knownGames.length; i++) {
                cachedGameIds.add(knownGames[i]);
            }
        }

        /**
         * <p>This is the thread run method which will use the ejb interface to check availability of new games.
         * </p>
         *
         */
        public void run() {
            System.err.println("NewGameAvailableNotifier started");

            //it stops if the stop signal is received
            for (;;) {
                try {
                    Thread.sleep(sleepInterval);
                } catch (InterruptedException e) {
                    //ignore
                }

                synchronized (stopped) {
                    if (stopped[0]) {
                        break;
                    }
                }

                try {
                    Game[] games;

                    //get the array of games by remote or local
                    if (gameDataPersistenceLocal != null) {
                        games = gameDataPersistenceLocal.findGames(Boolean.FALSE, null);
                    } else {
                        games = gameDataPersistenceRemote.findGames(Boolean.FALSE, null);
                    }

                    for (int i = 0; i < games.length; i++) {
                        //once we have the games, we now check against the cached data
                        //and we only consider games with assigned ids.
                        if (!cachedGameIds.contains(games[i].getId()) && (games[i].getId() != null)) {
                            newGameAvailableListener.newGameAvailable(games[i]);
                            cachedGameIds.add(games[i].getId());
                            System.err.println("Queueing game " + games[i].getName() + " for startup");
                        }
                    }
                } catch (Exception e) {
                    //ignore
                }
            }

            System.err.println("NewGameAvailableNotifier stopped");
        }

        /**
         * <p>
         * Signals the thread represented by this Thread to stop,
         * by raising the stop flag and interrupting this thread.
         * </p>
         */
        public void stopNotifier() {
            synchronized(stopped){
                stopped[0] = true;
            }
            this.interrupt();
        }
    }
}
