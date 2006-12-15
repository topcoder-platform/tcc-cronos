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

import com.topcoder.bloom.BloomFilter;
import com.topcoder.message.messenger.MessageAPI;
import com.topcoder.util.generator.guid.UUIDType;
import com.topcoder.util.url.validation.SiteValidationResults;
import com.topcoder.util.url.validation.SiteValidator;

import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
    private static final String NEW_GAME_POLL_INTERVAl = "new_game_poll_interval";

    /**
     * The property name used to get the game start poll interval.
     */
    private static final String GAME_START_POLL_INTERVAl = "game_started_poll_interval";
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
     * Represents a flag that signifies if the manager is stopped or working.
     * It is changed by the stopManager method to true.
     * At any point all methods in the API should test this variable and
     * if the variable is true IllegalStateException should be thrown.
     * </p>
     *
     */
    private boolean stopped = false;

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

        long gameStartInterval;
        long newGameInterval;

        try {
            gameStartInterval = Long.parseLong(Helper.getMandatoryProperty(
                        namespace, GAME_START_POLL_INTERVAl));
            newGameInterval = Long.parseLong(Helper.getMandatoryProperty(
                        namespace, NEW_GAME_POLL_INTERVAl));

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
     * domain¡¯s document root and the documents hosting all the slot¡¯s domain targets are all reachable.
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

        if (stopped) {
            throw new IllegalStateException(
                "The game data manager is already stopped.");
        }

        //get the domain and the domain targets to test
        Domain domain = slot.getDomain();
        DomainTarget[] targets = slot.getDomainTargets();

        try {
            if (domain != null) {
                //create the SiteValidator and to the site validation
                SiteValidator sv = new SiteValidator(domain.getDomainName());
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
            throw new GameDataException("MalformedURLException occurs when get the SiteValidator.",
                e);
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

        if (stopped) {
            throw new IllegalStateException(
                "The game data manager is already stopped.");
        }

        long[] randomizedBidIds = (long[]) bidIds.clone();

        //create a Random instance
        Random random = new Random(0);
        int length = bidIds.length;

        //we define at most try length * length times
        final int maxTime = length * length;

        //a set used to store the ids that are already exists
        Set set = new HashSet();

        int times = 0;
        int uniqueIdCount = 0;

        while (uniqueIdCount < length) {
            int next = random.nextInt(length);
            times++;

            //if the id is not unique and the time is not limited yet, try again
            if (set.contains(new Integer(next)) && (times < maxTime)) {
                continue;
            }

            //add to the set
            set.add(new Integer(next));

            //do the swap
            long temp = randomizedBidIds[uniqueIdCount];
            randomizedBidIds[uniqueIdCount] = randomizedBidIds[next];
            randomizedBidIds[next] = temp;
            uniqueIdCount++;
        }

        try {
            if (gameDataPersistenceLocal != null) {
                gameDataPersistenceLocal.createSlots(blockId, randomizedBidIds);
            } else {
                gameDataPersistenceRemote.createSlots(blockId, randomizedBidIds);
            }
        } catch (Exception e) {
            throw new GameDataException("Exception occurs when create slots with EJB.",
                e);
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
        if (stopped) {
            throw new IllegalStateException(
                "The game data manager is already stopped.");
        }

        Game game = null;

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
        if( game == null) {
            throw new GameDataException("No Game can be retrieved with the id.");
        }

        //get the blocks of the game
        HostingBlock[] blocks = game.getBlocks();
        if (blocks == null) {
            return;
        }
        Date current = new Date();

        for (int i = 0; i < blocks.length; i++) {
            HostingSlot[] slots = blocks[i].getSlots();

            //if no slots exists, just go processing
            if (slots == null) {
                continue;
            }

            //set all the end date of the ordered slot to now
            for (int j = 0; j < slots.length; j++) {
                Date startDate = slots[j].getHostingStart();
                Date endDate = slots[j].getHostingEnd();

                //find the slot to be advanced
                if ((startDate != null) || (endDate == null)) {
                    //set end date of this slot to NOW
                    slots[j] = Helper.copyToSetEndDate(slots[j], current);

                    if (j < (slots.length - 1)) {
                        //set next slot after that by giving it its start date of NOW
                        slots[j + 1] = Helper.copyToSetStartDate(slots[j + 1],
                                current);
                    }

                    //if not valid, set sequence number to -1
                    if (!testUpcomingDomain(slots[j])) {
                        slots[j] = Helper.copyToSequenceNumber(slots[j], -1);
                    }

                    //break this block
                    break;
                }
            }
            try {
                if (gameDataPersistenceLocal != null) {
                    gameDataPersistenceLocal.updateSlots(slots);
                } else {
                    gameDataPersistenceRemote.updateSlots(slots);
                }
            } catch (Exception e) {
                throw new GameDataException("Exception occues when update the slots.", e);
            }
        }
    }

    /**
     * <p>
     * This is a clean up routine or the manager. If the manager uses any kind of remote resources such as
     * db connections, or threads this method would be implemented to call on all the resources to clean up.
     * Once the manager is stopped it is no longer operational and all methods
     * should be throwing IllegalStateException.
     * Any other exceptions should be wrapped into GameDataException.
     * </p>
     *
     * @throws GameDataException if there were any issues with this method call.
     */
    public void stopManager() throws GameDataException {
        gameStartNotifier.stopNotifier();
        newGameAvailableNotifier.stopNotifier();
        try {
            gameStartNotifier.join();
            newGameAvailableNotifier.join();
        } catch (Exception e) {
            throw new GameDataException("can not stop the threads.", e);
        }
        stopped = true;
    }

    /**
     * <p>
     * Check whether the manager is stopped.
     * </p>
     *
     * @return true if the manager is stopped, otherwise false.
     */
    public boolean isStopped() {
        return stopped;
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
         * Represents the flag (used to stop the thread) that tells the thread if it should stop execution or not.
         * If the flag is set to true then the thread continues to monitor the context;
         * if it is set to false then the thread will stop.
         * </p>
         *
         */
        private boolean stopped = false;

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
            //wait for the manager to stop
            while (!stopped) {
                try {
                    Thread.sleep(sleepInterval);
                } catch (InterruptedException e) {
                    //ignore
                }
            }

            //get the current games
            Game[] games = getAllCurrentNotStartedGames();

            // for each game see if it needs to be started
            for (int i = 0; (i < games.length) && games[i].getStartDate().before(new Date()); i++) {
                Domain [] domains = null;
                try {
                    if(gameDataPersistenceRemote != null) {
                        domains = gameDataPersistenceRemote.findActiveDomains();
                    } else {
                        domains = gameDataPersistenceLocal.findActiveDomains();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                gameStatusChangedToStarted(games[i]);
                //after start a game, broadcast to a BloomFilter update
                BloomFilter bloomFilter = new BloomFilter(capacity, errorRate);
                if(domains != null) {
                    //add the names of the domain to the BloomFilter
                    for(int j = 0; j < domains.length; j ++) {
                        bloomFilter.add(domains[i].getDomainName());
                    }
                }
                try {
                    OrpheusMessengerPlugin plugin = new RemoteOrpheusMessengerPlugin(messagerPluginNS);
                    MessageAPI message = plugin.createMessage();

                    //set the parameters, TODO
                    message.setParameterValue("GUID", UUIDUtility.getNextUUID(UUIDType.TYPE1));
                    message.setParameterValue("category", category);
                    message.setParameterValue("bloom_filter", "application/x-tc-bloom-filter");
                    message.setParameterValue("body", bloomFilter.getSerialized());
                    message.setParameterValue("time", new Date());
                    plugin.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * <p>Singals the thread to stop.
         * This is simply achieved by changing the value of stopped to true.
         * </p>
         *
         */
        public void stopNotifier() {
            this.stopped = true;
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
         * Represents the flag (used to stop the thread) that tells the thread if it should stop execution or not.
         * If the flag is set to true then the thread continues to monitor the context (i.e. the server for new games);
         * if it is set to false then the thread will stop.
         * </p>
         *
         */
        private boolean stopped = false;

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
         * @throws IllegalArgumentException if the interval is <=0; or if we get a null in any of the parameter inputs;
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
         * @throws IllegalArgumentException if the interval is <=0;,
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
            //wait for the manager to stop
            while (!stopped) {
                try {
                    Thread.sleep(sleepInterval);
                } catch (InterruptedException e) {
                    //ignore
                }
            }

            Game[] games = null;

            try {
                //get the array of games by remote or local
                if (gameDataPersistenceLocal != null) {
                    games = gameDataPersistenceLocal.findGames(Boolean.FALSE,
                            null);
                } else {
                    games = gameDataPersistenceRemote.findGames(Boolean.FALSE,
                            null);
                }
            } catch (Exception e) {
                //ignore
            }

            if (games != null) {
                for (int i = 0; i < games.length; i++) {
                    //once we have the games, we now check against the cached data
                    //and we only consider games with assigned ids.
                    if (!cachedGameIds.contains(games[i].getId()) && (games[i].getId() != null)) {
                        newGameAvailableListener.newGameAvailable(games[i]);
                        cachedGameIds.add(games[i].getId());
                    }
                }
            }
        }

        /**
         * <p>
         * Singals the thread to stop.
         * This is simply achieved by changing the value of stopped to true.
         * </p>
         *
         */
        public void stopNotifier() {
            this.stopped = true;
        }
    }
}
