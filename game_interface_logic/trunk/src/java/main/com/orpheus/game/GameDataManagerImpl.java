/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game;

import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.administration.persistence.AdminDataLocal;
import com.orpheus.administration.persistence.AdminDataLocalHome;
import com.orpheus.administration.persistence.OrpheusMessengerPlugin;
import com.orpheus.administration.persistence.RemoteOrpheusMessengerPlugin;
import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.entities.DomainTargetImpl;

import com.topcoder.bloom.BitSetSerializer;
import com.topcoder.bloom.BloomFilter;
import com.topcoder.bloom.serializers.DefaultBitSetSerializer;
import com.topcoder.message.messenger.MessageAPI;
import com.topcoder.randomstringimg.Configuration;
import com.topcoder.randomstringimg.InvalidConfigException;
import com.topcoder.randomstringimg.ObfuscationAlgorithm;
import com.topcoder.randomstringimg.RandomStringImage;
import com.topcoder.util.algorithm.hash.ConfigurationException;
import com.topcoder.util.algorithm.hash.HashAlgorithmManager;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionImpl;
import com.topcoder.util.compression.CompressionUtility;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.generator.guid.UUIDType;
import com.topcoder.util.net.httputility.HttpException;
import com.topcoder.util.net.httputility.HttpUtility;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleGenerator;
import com.topcoder.util.puzzle.PuzzleType;
import com.topcoder.util.puzzle.PuzzleTypeSource;
import com.topcoder.util.url.validation.SiteValidationResults;
import com.topcoder.util.url.validation.SiteValidator;
import com.topcoder.util.url.validation.URLAndFilter;
import com.topcoder.util.url.validation.URLFilter;
import com.topcoder.util.url.validation.URLHostFilter;
import com.topcoder.util.url.validation.SiteValidator;
import com.topcoder.util.web.sitestatistics.SiteStatistics;
import com.topcoder.util.web.sitestatistics.StatisticsException;
import com.topcoder.util.web.sitestatistics.TextStatistics;

import java.net.MalformedURLException;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import com.topcoder.util.generator.guid.UUIDUtility;
import com.topcoder.util.image.manipulation.Image;
import com.topcoder.util.image.manipulation.image.MutableMemoryImage;
import com.topcoder.web.frontcontroller.results.DownloadData;


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
     * <p>
     * Holds the puzzle types to choose from when regenerating puzzles. One of
     * the puzzle types will be chosen at random in the method
     * regeneratePuzzle().
     * </p>
     *
     */
    private static final PuzzleTypeEnum[] PUZZLE_TYPES = new PuzzleTypeEnum[] {
            PuzzleTypeEnum.JIGSAW, PuzzleTypeEnum.SLIDING_TILE
        };
    /**
     * <p>
     * Holds the puzzle types to choose from when regenerating brain teasers.
     * One of the puzzle types will be chosen at random in the method
     * regenerateBrainTeaser().
     * </p>
     *
     */
    private static final PuzzleTypeEnum[] BRAINTEASER_TYPES = new PuzzleTypeEnum[] {
            PuzzleTypeEnum.MISSING_LETTER, PuzzleTypeEnum.LETTER_SCRAMBLE
        };
    /**
     * The property name used to get the new game poll interval.
     */
    private static final String NEW_GAME_POLL_INTERVAL = "new_game_poll_interval";

    /**
     * The property name used to get the game start poll interval.
     */
    private static final String GAME_START_POLL_INTERVAL = "game_started_poll_interval";

    /**
     * The property name used to get the target update check poll interval.
     */
    private static final String TARGET_CHECK_POLL_INTERVAL = "target_update_poll_interval";

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
    private static final String GAME_DATA_EJB_JNDI_NAMES = "game_data_ejb_jndi_names";
    /**
     * The property name used to load the jndi names from the configuration.
     */
    private static final String ADMIN_DATA_EJB_JNDI_NAMES = "admin_data_ejb_jndi_names";

    /**
     * The name of the GUID message property to use when generating a Bloom Filter update (or other) message
     */
    private static final String ADMIN_MESSAGE_GUID = "guid";

    /**
     * The name of the CATEGORY message property to use when generating a Bloom Filter update (or other) message
     */
    private static final String ADMIN_MESSAGE_CATEGORY = "category";

    /**
     * The name of the CONTENT TYPE message property to use when generating a Bloom Filter update (or other) message
     */
    private static final String ADMIN_MESSAGE_CONTENT_TYPE = "contentType";

    /**
     * The name of the MESSAGE CONTENT message property to use when generating a Bloom Filter update (or other) message
     */
    private static final String ADMIN_MESSAGE_CONTENT = "content";

    /**
     * The name of the TIMESTAMP message property to use when generating a Bloom Filter update (or other) message
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
     * Represents a notification thread which will poll the hosting sites and determine (for each
     * site) whether the target it is hosting has been updated. The polls are performed at some
     * interval, which can be configured using <code>target_check_poll_interval</code> property.
     * This member variable cannot be null.
     * </p>
     */
    private final TargetCheckNotifier targetCheckNotifier;

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
     * Represents the remote ejb interface used for persistence.
     * </p>
     *
     */
    private final AdminData adminDataPersistenceRemote;

    /**
     * <p>
     * Represents the local ejb interface used for persistence.
     * This is set based on the fact that a JndiLookupDesignation.LOCAL was used when looking up the ejb.
     * It can be null, which would mean that the local interface must be set.
     * We can not have a situation where both this and the gameDataPersistenceRemote variable are both null.
     * </p>
     *
     */
    private final AdminDataLocal adminDataPersistenceLocal;

    /**
     * This contains configuration to use when regenerating puzzles and
     * brainteasers. The key is an instance of PuzzleTypeEnum and value is an
     * instance of PuzzleConfig. Hence this is a mapping of configuration values
     * per puzzle type.<br/> This variable is initialized in the constructor
     * and does not change after that.<br/> It will never be null or empty, nor
     * will contain null keys or values.<br/>
     *
     */
    private final Map puzzleConfigMap;

    /**
     * Represents the PuzzleTypeSource instance to generate puzzles and
     * brainteasers with.<br/> This variable is initialized in the constructor
     * and does not change after that.<br/> It will never be null.<br/>
     *
     */
    private final PuzzleTypeSource puzzleTypeSource;

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
     * AuctionManager is used to auto-creation of the exhausted hosting blocks and hosting slots.
     */
    private AuctionManager auctionManager = null;

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
     * The singleton <code>HashAlgorithmManager</code>, cached here for convenience.
     */
    private final HashAlgorithmManager hashAlgManager;

    /**
     * A <code>RandomStringImage</code> object that is used to generate images with text, which
     * are in turn used to generate clues for targets.
     */
    private final RandomStringImage randomStringImage;

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
    public GameDataManagerImpl(PuzzleTypeSource puzzleTypeSource)
        throws GameDataManagerConfigurationException, GameDataException {
        this(puzzleTypeSource,GameDataManagerImpl.class.getName());
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
    public GameDataManagerImpl(PuzzleTypeSource puzzleTypeSource,String namespace)
        throws GameDataManagerConfigurationException, GameDataException {
        Helper.checkObjectNotNull(puzzleTypeSource, "puzzleTypeSource");
        Helper.checkStringNotNullOrEmpty(namespace, "namespace");

        this.puzzleTypeSource = puzzleTypeSource;
        try{
            InitialContext ctx = new InitialContext();
            EJBHomes homes = readEJBConfig(namespace,GAME_DATA_EJB_JNDI_NAMES,ctx,GameDataHome.class);
            //init the game data EJB instance
            this.gameDataPersistenceLocal = (homes.localHome != null)
                    ? ((GameDataLocalHome) homes.localHome).create() : null;
            this.gameDataPersistenceRemote = (homes.remoteHome != null)
                    ? ((GameDataHome) homes.remoteHome).create() : null;

            homes = readEJBConfig(namespace,ADMIN_DATA_EJB_JNDI_NAMES,ctx,AdminDataHome.class);
            this.adminDataPersistenceLocal = (homes.localHome != null)
                    ? ((AdminDataLocalHome) homes.localHome).create() : null;
            this.adminDataPersistenceRemote = (homes.remoteHome != null)
                    ? ((AdminDataHome) homes.remoteHome).create() : null;

        } catch(Exception e){
            throw new GameDataManagerConfigurationException("Fails to create EJB through the configuration.",e);
        }

        puzzleConfigMap = new HashMap();
        // Load property "jigsaw"
        initializePuzzleType(namespace, "jigsaw", PuzzleTypeEnum.JIGSAW);
        // Load property "slidingTile"
        initializePuzzleType(namespace, "slidingTile", PuzzleTypeEnum.SLIDING_TILE);
        // Load property "missingLetter"
        initializeBrainTeaserType(namespace, "missingLetter", PuzzleTypeEnum.MISSING_LETTER);
        // Load property "letterScramble"
        initializeBrainTeaserType(namespace, "letterScramble", PuzzleTypeEnum.LETTER_SCRAMBLE);

        /*
         * Create Hash Algorithm Manager
         */
        try {
            hashAlgManager = HashAlgorithmManager.getInstance();
        } catch (ConfigurationException ce) {
            throw new GameDataManagerConfigurationException(
                    "Could not obtain HashAlgorithmManager instance", ce);
        }

        /*
         * Create Random String Image
         */
        try {
            Configuration config;

            randomStringImage = new RandomStringImage(
                    Helper.getPropertyString(namespace, "RandomStringImageFile"));
            config = randomStringImage.getConfiguration();
            config.clearAlgorithms();
            config.addAlgorithm(new ObfuscationAlgorithm() {
                    public int getType() {
                        return ObfuscationAlgorithm.AFTER_TEXT;
                    }

                    public void obfuscate(BufferedImage image, Color textColor,
                        Color backgroundColor) {
                        /* does nothing */
                    }
                });
        } catch (InvalidConfigException ice) {
            throw new GameDataManagerConfigurationException(
                    "Could not obtain a RandomStringImage instance", ice);
        } catch (IOException ioe) {
            throw new GameDataManagerConfigurationException(
                    "Could not obtain a RandomStringImage instance", ioe);
        }

        //initialize the bitSetSerializer
        String serializerSpecNamespace;

        try {
            serializerSpecNamespace = ConfigManager.getInstance().getString(namespace, BIT_SERIALIZER_SPEC);
        } catch (UnknownNamespaceException e) {
            throw new GameDataManagerConfigurationException("The namespace '" + namespace + "' is missing in configuration.", e);
        }

        try {
            if (serializerSpecNamespace == null || serializerSpecNamespace.length() == 0) {
                bitSetSerializer = new DefaultBitSetSerializer();
            } else {
                String bitSerializerKey = Helper.getMandatoryProperty(namespace, BIT_SERIALIZER_KEY);
                ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory(serializerSpecNamespace));
                bitSetSerializer = (BitSetSerializer) factory.createObject(bitSerializerKey);
            }
        } catch (Exception e) {
            throw new GameDataManagerConfigurationException("Error creating BitSetSerializer", e);
        }


        long gameStartInterval;
        long newGameInterval;
        long targetCheckInterval;

        try {
            // Convert poll intervals into integers
            gameStartInterval = Long.parseLong(Helper.getMandatoryProperty(namespace, GAME_START_POLL_INTERVAL));
            newGameInterval = Long.parseLong(Helper.getMandatoryProperty(namespace, NEW_GAME_POLL_INTERVAL));
            targetCheckInterval = Long.parseLong(Helper.getMandatoryProperty(namespace, TARGET_CHECK_POLL_INTERVAL));

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
            if (targetCheckInterval <= 0) {
                throw new GameDataManagerConfigurationException("The target update check interval should be > 0.");
            }
        } catch (NumberFormatException e) {
            throw new GameDataManagerConfigurationException("The value of interval properties should be numbers.",
                e);
        }


        Game[] games;

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

        setCurrentNotStartedGames(games);

        Long[] ids = new Long[games.length];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = games[i].getId();
        }

        //init the notifiers
        gameStartNotifier = new GameStartNotifier(gameStartInterval, this);
        targetCheckNotifier = new TargetCheckNotifier(targetCheckInterval, this);

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
        targetCheckNotifier.start();
    }



    /**
     * <p>
     * Creates a new GameDataManagerImpl instance based on input parameters.
     * </p>
     *
     * @param gameDataJndiNames an array of jndi names to use when looking up the ejb
     * @param gameDataJndiDesignations jndi designations such as Local or Remote
     * @param newGameDiscoveryPollInterval interval used to configure the poll frequency (ms)
     * @param gameStartedPollInterval interval used to configure the poll frequency (ms)
     * @param targetCheckPollInterval interval used to set the frequency of target update checks, in
     *            milliseconds
     * @param randomStringImageFile the name of a file containing configuration for Random String
     *            Image component.
     * @throws IllegalArgumentException if any array is null or contains null element, or
     *             <code>jndiNames</code> contains empty trimmed String, or
     *             <code>jndiDesignations</code> contains Strings that not Remote or Local, or two
     *             array String not of the same length, or any long parameters <= 0, or no EJB can
     *             be looked up with the given jndis
     * @throws GameDataException if any other error occurs
     * @throws GameDataManagerConfigurationException if any error occurs while creating Hash
     *             Algorithm Manager object.
     */
    public GameDataManagerImpl(PuzzleTypeSource puzzleTypeSource, Map puzzleConfigMap, String[] gameDataJndiNames,
            String[] gameDataJndiDesignations, String[] adminDataJndiNames, String[] adminDataJndiDesignations,
            long newGameDiscoveryPollInterval, long gameStartedPollInterval, long targetCheckPollInterval,
            int capacity, float errorRate, String messengerPluginNS, String category, String randomStringImageFile)
        throws GameDataException, GameDataManagerConfigurationException {
        Helper.checkObjectNotNull(puzzleTypeSource, "puzzleTypeSource");
        Helper.checkObjectNotNull(puzzleConfigMap, "puzzleConfigMap");
        Helper.checkObjectNotNull(gameDataJndiNames, "gameDataJndiNames");
        Helper.checkObjectNotNull(gameDataJndiDesignations, "gameDataJndiDesignations");
        Helper.checkObjectNotNull(adminDataJndiNames, "adminDataJndiNames");
        Helper.checkObjectNotNull(adminDataJndiDesignations, "adminDataJndiDesignations");
        Helper.checkStringNotNullOrEmpty(messengerPluginNS, "messengerPluginNS");

        this.puzzleTypeSource = puzzleTypeSource;

        this.puzzleConfigMap = puzzleConfigMap;

        if (adminDataJndiNames.length != adminDataJndiDesignations.length) {
            throw new IllegalArgumentException(
                "The jndiNames and jndiDesignations should be of same length.");
        }

        if (gameDataJndiNames.length != gameDataJndiDesignations.length) {
            throw new IllegalArgumentException(
                "The jndiNames and jndiDesignations should be of same length.");
        }

        //each jndi name can not be null or empty string and the Designation should be either Remote or Local
        for (int i = 0; i < adminDataJndiNames.length; i++) {
            Helper.checkStringNotNullOrEmpty(adminDataJndiNames[i],
                "jndiNames[" + i + "]");

            if ((!LOCAL.equalsIgnoreCase(adminDataJndiDesignations[i]) && !REMOTE.equalsIgnoreCase(adminDataJndiDesignations[i]))) {
                throw new IllegalArgumentException(
                    "The value for property admin_data_ejb_jndi_names should be  'jndiname,Remote' or 'jndiname,Local'.");
            }
        }
        //each jndi name can not be null or empty string and the Designation should be either Remote or Local
        for (int i = 0; i < gameDataJndiNames.length; i++) {
            Helper.checkStringNotNullOrEmpty(gameDataJndiNames[i],
                "jndiNames[" + i + "]");

            if ((!LOCAL.equalsIgnoreCase(gameDataJndiDesignations[i]) && !REMOTE.equalsIgnoreCase(gameDataJndiDesignations[i]))) {
                throw new IllegalArgumentException(
                    "The value for property game_data_ejb_jndi_names should be  'jndiname,Remote' or 'jndiname,Local'.");
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

        if (targetCheckPollInterval <= 0) {
            throw new IllegalArgumentException(
                "The target update check interval should be > 0.");
        }
        if (errorRate <= 0 || errorRate >= 1.0) {
            throw new IllegalArgumentException("The errorRate should be between 0 and 1.");
        }

        this.capacity = capacity;
        this.errorRate = errorRate;
        this.messagerPluginNS = messengerPluginNS;
        this.category = category;

        /*
         * Create Hash Algorithm Manager
         */
        try {
            hashAlgManager = HashAlgorithmManager.getInstance();
        } catch (ConfigurationException ce) {
            throw new GameDataManagerConfigurationException(
                    "Could not obtain HashAlgorithmManager instance", ce);
        }

        /*
         * Create Random String Image
         */
        try {
            Configuration config;

            randomStringImage = new RandomStringImage(randomStringImageFile);
            config = randomStringImage.getConfiguration();
            config.clearAlgorithms();
            config.addAlgorithm(new ObfuscationAlgorithm() {
                    public int getType() {
                        return ObfuscationAlgorithm.AFTER_TEXT;
                    }

                    public void obfuscate(BufferedImage image, Color textColor,
                        Color backgroundColor) {
                        /* does nothing */
                    }
                });
        } catch (InvalidConfigException ice) {
            throw new GameDataManagerConfigurationException(
                    "Could not obtain a RandomStringImage instance", ice);
        } catch (IOException ioe) {
            throw new GameDataManagerConfigurationException(
                    "Could not obtain a RandomStringImage instance", ioe);
        }

        bitSetSerializer = new DefaultBitSetSerializer();

        try{
            InitialContext ctx = new InitialContext();
            EJBHomes homes;

            //if no EJB is looked up successfully
            homes = locateEJB(gameDataJndiNames, gameDataJndiDesignations,ctx,GameDataHome.class);
            if ((homes.remoteHome == null) && (homes.localHome == null)) {
                throw new IllegalArgumentException(
                    "No EJB can be looked up via the JNDI names configed.");
            }

            //init the game data EJB instance
            this.gameDataPersistenceLocal = (homes.localHome != null)
                    ? ((GameDataLocalHome) homes.localHome).create() : null;
            this.gameDataPersistenceRemote = (homes.remoteHome != null)
                    ? ((GameDataHome) homes.remoteHome).create() : null;

            homes = locateEJB(adminDataJndiNames, adminDataJndiDesignations,ctx,AdminDataHome.class);
            if ((homes.remoteHome == null) && (homes.localHome == null)) {
                throw new IllegalArgumentException(
                    "No EJB can be looked up via the JNDI names configed.");
            }
            //init the admin data EJB instance
            this.adminDataPersistenceLocal = (homes.localHome != null)
                    ? ((AdminDataLocalHome) homes.localHome).create() : null;
            this.adminDataPersistenceRemote = (homes.remoteHome != null)
                    ? ((AdminDataHome) homes.remoteHome).create() : null;
        } catch(Exception e){
            throw new IllegalArgumentException("The configuration for ejb seems not right.");
        }

        Game[] games;

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

        setCurrentNotStartedGames(games);

        Long[] ids = new Long[games.length];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = games[i].getId();
        }

        //init the notifiers
        gameStartNotifier = new GameStartNotifier(gameStartedPollInterval, this);
        targetCheckNotifier = new TargetCheckNotifier(targetCheckPollInterval, this);

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
        targetCheckNotifier.start();
    }

    /**
     *
     * Read the configuration for the ejb's local and remote jndi. And also try to lookup the ejb through the
     * jndi in the context.
     *
     * @param namespace the namespace to read the configuration
     * @param property the property name in the namespace
     * @param ctx the context to lookup the ejb
     * @param classType the remote Ejb class type
     * @throws GameDataManagerConfigurationException any fails in configuration or fails to read ejb
     */
    private EJBHomes readEJBConfig(String namespace,String property,InitialContext ctx, Class classType) throws GameDataManagerConfigurationException {
        String[] jndiNameValues = Helper.getMandatoryPropertyArray(namespace, property);

        String[] jndiNames = new String[jndiNameValues.length];
        String[] jndiDesignations = new String[jndiNameValues.length];
        EJBHomes result;

        //parse the values, the format should be 'jndiname,Remote' or 'jndiname,Local'
        for (int i = 0; i < jndiDesignations.length; i++) {
            int index = jndiNameValues[i].lastIndexOf(",");

            if (index < 0) {
                throw new GameDataManagerConfigurationException(
                    "The value for property " + property  + " should be "
                    + " 'jndiname,Remote' or 'jndiname,Local'.");
            }

            jndiNames[i] = jndiNameValues[i].substring(0, index).trim();
            jndiDesignations[i] = jndiNameValues[i].substring(index + 1).trim();

            //check the jndi names should not be empty, and the designations should be 'local' or 'remote'
            if ((jndiNames[i].length() == 0) || (!LOCAL.equalsIgnoreCase(jndiDesignations[i])
                    && !REMOTE.equalsIgnoreCase(jndiDesignations[i]))) {
                throw new GameDataManagerConfigurationException(
                    "The value for property " + property  + " should be  'jndiname,Remote' or 'jndiname,Local'.");
            }
       }

       result = this.locateEJB(jndiNames, jndiDesignations, ctx, classType);

       //if no EJB is looked up successfully
       if ((result.remoteHome == null) && (result.localHome == null)) {
           throw new GameDataManagerConfigurationException(
               "No EJB can be looked up via the JNDI names configed.");
       } else {
           return result;
       }
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
     * @param ctx the context to lookup the ejb
     * @param classType the remote Ejb class type
     */
    private EJBHomes locateEJB(String[] jndiNames, String[] jndiDesignations,InitialContext ctx, Class classType) {
        EJBHomes result = new EJBHomes();

        //try to lookup the ejb
        for (int i = 0; i < jndiNames.length; i++) {
            try {
                //if the JNDI is expected to be local, init the localEJB
                if (LOCAL.equalsIgnoreCase(jndiDesignations[i])) {
                    result.localHome = (EJBLocalHome) ctx.lookup(jndiNames[i]);
                } else {
                    Object lookup = ctx.lookup(jndiNames[i]);
                    result.remoteHome =  (EJBHome) PortableRemoteObject.narrow(lookup, classType);
                }
            } catch (Exception e) {
                //go and continue to find a Remote or Local EJB
                continue;
            }

            //if any type of ejb is looked successfully, break;
            break;
        }

        return result;
    }

    /**
     * Initialize brain teaser type parameters.
     *
     * @param namespace
     *            the namespace
     * @param name
     *            the name of brain teaser property
     * @param type
     *            the type of the brain teaser
     * @throws ConfigurationException
     *             if any sub-property miss or is valid
     */
    private void initializeBrainTeaserType(String namespace, String name,
            PuzzleTypeEnum type) throws GameDataManagerConfigurationException {
        Property property = Helper.getProperty(namespace, name);
        String typeName = Helper.getSubPropertyString(property, "puzzleTypeName");
        int seriesSize = Helper.getSubPropertyInt(property, "seriesSize");
        PuzzleConfig config = new PuzzleConfig(typeName, null, null, seriesSize);
        puzzleConfigMap.put(type, config);
    }

    /**
     * Initialize puzzle type parameters.
     *
     * @param namespace
     *            the namespace
     * @param name
     *            the name of puzzle property
     * @param type
     *            the type of the puzzle
     * @throws ConfigurationException
     *             if any sub-property miss or is valid
     */
    private void initializePuzzleType(String namespace, String name,
            PuzzleTypeEnum type) throws GameDataManagerConfigurationException {
        Property property = Helper.getProperty(namespace, name);
        String typeName = Helper.getSubPropertyString(property, "puzzleTypeName");
        int width = Helper.getSubPropertyInt(property, "width");
        int height = Helper.getSubPropertyInt(property, "height");
        PuzzleConfig config = new PuzzleConfig(typeName, new Integer(width), new Integer(height), 0);
        puzzleConfigMap.put(type, config);
    }

    /**
     * <p>
     * Tests whether an upcoming domain is ready to begin hosting a specific slot.  Basically, we need to
     * ensure that the domain's document root and the documents hosting all the slot's domain targets are
     * reachable.  This method does not modify the state of any object visible from outside, but it does
     * issue HTTP requests and it does depend on its argument to not mutate while this method is executing.
     * </p>
     *
     * @param slot hosting slot ot validator
     * @return <code>true</code> if the slot is valid; <code>false</code> otherwise
     * @throws IllegalArgumentException if the slot is null
     * @throws IllegalStateException if the manager has been stopped.
     * @throws GameDataException if a checked exception prevents this method from completing successfully
     */
    public boolean testUpcomingDomain(HostingSlot slot)
            throws GameDataException {
        Helper.checkObjectNotNull(slot, "slot");

        if (isStopped()) {
            throw new IllegalStateException(
                "The game data manager is already stopped.");
        }

        // get the domain and the domain targets to test
        Domain domain = slot.getDomain();

        if (domain == null) {
            throw new GameDataException("Hosting slot has no assigned domain");
        }

        Set testedURLs = new HashSet();
        HttpUtility http = new HttpUtility(HttpUtility.GET);
        String baseURL = "http://" + domain.getDomainName() + "/"; 

        http.setFollowRedirects(true);
        http.setDepthLimit(10);
        http.setKeepCookies(true);

        //if the domain can not pass the validatiton, return false directly
        if (!testSingleURL(baseURL, http)) {
            return false;
        }

        testedURLs.add(baseURL);

        DomainTarget[] targets = slot.getDomainTargets();

        // There must be at least one target for this domain to be valid
        if ((targets == null) || (targets.length == 0)) {
            return false;
        } else {

            // The target pages must be accessible
            for (int i = 0; i < targets.length; i++) {
                String targetURL = targets[i].getUriPath();

                /*
                 * if any domain target can not pass the validatiton, return false directly;
                 * don't test the same page multiple times
                 */
                if (testedURLs.add(targetURL) && !testSingleURL(targetURL, http)) {
                    return false;
                }
            }
        }

        // all right, return true
        return true;
    }

    /**
     * Tests a single URL to determine whether it can be loaded successfully via HTTP or HTTPS,
     * as appropriate, and that the response content is declared to be HTML
     *
     * @param urlString a <code>String</code> representation of the absolute URL to test
     *
     * @return <code>true</code> if the URL is valid, <code>false</code> if not
     *
     * @throws MalformedURLException if the argument cannot be parsed as an absolute URL
     */
    private boolean testSingleURL(String urlString, HttpUtility http) {
        try {
            http.executeToStream(urlString, new OutputStream() {
                public void write(byte[] bytes) { /* does nothing */ }
                public void write(byte[] bytes, int off, int len) { /* does nothing */ }
                public void write(int b) { /* does nothing */ }
            });

            String contentType = http.getResponseHeaders().getByName("content-type");
        
            return ((http.getResponseStatusCode() == 200) && (contentType != null)
                    && contentType.replaceFirst("[+;].*", "").toLowerCase().equals("text/html"));
        } catch (HttpException he) {
            System.err.println("While testing for target disappearance, error trying to load\n"
                    + urlString + ":\ncode: " + he.getCode() + "  message: " + he.getHttpMessage());

            return false;
        } catch (IOException ioe) {
            System.err.println("I/O error while retrieving " + urlString + ".  Stack trace follows");
            ioe.printStackTrace(System.err);

            return false;
        }
    }

    /**
     * (Re)generates the brain teaser for the specified hosting slot. This
     * method does the following.
     * <ol>
     * <li>Get the text for the slot's first domain target.</li>
     * <li>Choose between missing letter and letter scramble puzzles randomly.</li>
     * <li>Generate a puzzle series for the chosen type.</li>
     * <li>Store the puzzle using AdminData.</li>
     * <li>Update the puzzle information for the slot using GameData.</li>
     * </ol>
     *
     *
     * @param slotId
     *            slot id.
     * @throws GameDataException
     *             if a checked exception prevents this method from completing
     *             normally
     */
    public void regenerateBrainTeaser(long slotId) throws GameDataException {
        try {
            HostingSlot slot = null;
            // Get the hosting slot
            if ( gameDataPersistenceLocal != null){
                slot = gameDataPersistenceLocal.getSlot(slotId);
            } else{
                slot = gameDataPersistenceRemote.getSlot(slotId);
            }

            // Get text for puzzle
            DomainTarget[] targets = slot.getDomainTargets();

            if (targets.length == 0) {
                throw new GameDataException(
                    "Failed to regenerate BrainTeaser for slot(slotId:" +
                    slotId +
                    "), caused by there is no DomainTargets set for that slot.");
            }

            String puzzleText = targets[0].getIdentifierText();
            int chosenPuzzle = new Random().nextInt(BRAINTEASER_TYPES.length);

            // Get puzzle config for the type
            PuzzleConfig config = null;

            synchronized (puzzleConfigMap) {
                config = (PuzzleConfig) puzzleConfigMap.get(BRAINTEASER_TYPES[chosenPuzzle]);
            }

            // Generate the puzzle series
            PuzzleType puzzleType = puzzleTypeSource.getPuzzleType(config.getPuzzleTypeName());
            PuzzleGenerator puzzleGenerator = puzzleType.createGenerator();
            puzzleGenerator.setAttribute("text", puzzleText);

            PuzzleData[] puzzleDatas = puzzleGenerator.generatePuzzleSeries(config.getPuzzleSeriesSize());

            // Store the puzzles and get ids
            long[] puzzleIds = (adminDataPersistenceLocal != null)? adminDataPersistenceLocal.storePuzzles(puzzleDatas):
                this.adminDataPersistenceRemote.storePuzzles(puzzleDatas);

            // Create a new HostingSlotImpl instance
            HostingSlotImpl newSlot = Helper.doCopy(slot);
            // Set the new brainteaser ids
            newSlot.setBrainTeaserIds(puzzleIds);
            // Update slot
            if (this.gameDataPersistenceLocal != null) {
                gameDataPersistenceLocal.updateSlots(new HostingSlot[] { newSlot });
            } else{
                gameDataPersistenceRemote.updateSlots(new HostingSlot[] { newSlot });
            }

        } catch (Exception e) {
            throw new GameDataException("Failed to regenerate puzzle.", e);
        }
    }

    /**
     * (Re)generates the game-win puzzle for the specified hosting slot. This
     * method does the following.
     * <ol>
     * <li>Get the download data for slot's associated image id.</li>
     * <li>Choose between jigsaw and sliding tyle puzzles randomly.</li>
     * <li>Generate a puzzle for the chosen type.</li>
     * <li>Store the puzzle using AdminData.</li>
     * <li>Update the puzzle information for the slot using GameData.</li>
     * </ol>
     *
     * @param slotId
     *            the slot id.
     * @throws GameDataException
     *             if a checked exception prevents this method from completing
     *             normally.
     */
    public void regeneratePuzzle(long slotId) throws GameDataException {
        try {
            HostingSlot slot = null;
            // Get the hosting slot
            if ( gameDataPersistenceLocal != null){
                slot = gameDataPersistenceLocal.getSlot(slotId);
            } else{
                slot = gameDataPersistenceRemote.getSlot(slotId);
            }

            // Get the image for the puzzle
            DownloadData imageData = getDownloadData(slot);
            Image image = new MutableMemoryImage(ImageIO.read(
                        imageData.getContent()));
            int chosenPuzzle = new Random().nextInt(PUZZLE_TYPES.length);

            // Get puzzle config for the type
            PuzzleConfig config = null;

            synchronized (puzzleConfigMap) {
                config = (PuzzleConfig) puzzleConfigMap.get(PUZZLE_TYPES[chosenPuzzle]);
            }

            // Set puzzle generator configuration
            PuzzleType puzzleType = puzzleTypeSource.getPuzzleType(config.getPuzzleTypeName());
            PuzzleGenerator puzzleGenerator = puzzleType.createGenerator();
            puzzleGenerator.setAttribute("image", image);
            puzzleGenerator.setAttribute("width", config.getWidth());
            puzzleGenerator.setAttribute("height", config.getHeight());

            // Generate puzzle
            PuzzleData puzzleData = puzzleGenerator.generatePuzzle();

            // Store the puzzle and get id
            long[] puzzleIds = (adminDataPersistenceLocal != null)? adminDataPersistenceLocal.storePuzzles(new PuzzleData[] {
                        puzzleData}): adminDataPersistenceRemote.storePuzzles(new PuzzleData[] {puzzleData});

            // Create a new HostingSlotImpl instance
            HostingSlotImpl newSlot = Helper.doCopy(slot);
            // Set the new puzzle id
            newSlot.setPuzzleId(new Long(puzzleIds[0]));
            // Update slot
            if ( this.gameDataPersistenceLocal != null){
                this.gameDataPersistenceLocal.updateSlots(new HostingSlot[] { newSlot });
            } else{
                this.gameDataPersistenceRemote.updateSlots(new HostingSlot[] { newSlot });
            }
        } catch (GameDataException e) {
            throw e;
        } catch (Exception e) {
            throw new GameDataException("Failed to regenerate puzzle.", e);
        }
    }

    /**
     * Get download data from gamedata.
     *
     * @param gameData
     *            GameData EJB instance.
     * @param slot
     *            the slot to get download data id
     * @return download data
     * @throws GameDataException
     *             if any exception occur, or there is no corresponding download
     *             exist in domain.
     */
    private DownloadData getDownloadData(HostingSlot slot)
        throws GameDataException {
        Domain domain = slot.getDomain();
        ImageInfo[] images = domain.getImages();
        long imageId = slot.getImageId();

        for (int i = 0; i < images.length; i++) {
            if (images[i].getId().longValue() == imageId) {
                try {
                    return (gameDataPersistenceLocal != null)? gameDataPersistenceLocal.getDownloadData(images[i].getDownloadId()):
                        gameDataPersistenceRemote.getDownloadData(images[i].getDownloadId());
                } catch (EntryNotFoundException e) {
                    throw new GameDataException(
                        "Failed to get download data. (slot id is " +
                        slot.getId() + ")", e);
                } catch (PersistenceException e) {
                    throw new GameDataException(
                        "Failed to get download data. (slot id is " +
                        slot.getId() + ")", e);
                } catch (RemoteException e) {
                    throw new GameDataException(
                        "Failed to get download data. (slot id is " +
                        slot.getId() + ")", e);
                }
            }
        }

        // throw exception if download data do not exist
        throw new GameDataException(
            "Failed to get download data for there is no corresponding" +
            " download image exist in domain. (slot id is " + slot.getId() +
            ", expected game id is " + imageId + ")");
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
     * Advances the Ball in the iactive game of the specified ID to the next hosting available hosting slot,
     * by setting the hosting end timestamp on the current slot and the hosting start timestamp on the next
     * one.  As part of the process, this method tests the subsequent domain to make sure it is still valid;
     * if not then it will delete it.
     * </p>
     *
     * @param gameId id of the game
     * @throws GameDataException if a checked exception prevents this method from completing successfully.
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void advanceHostingSlot(long gameId) throws GameDataException {
        advanceHostingSlot(gameId, true);
    }

    /**
     * <p>
     * Advances the Ball in the active game of the specified ID to the next hosting available hosting slot,
     * by setting the hosting end timestamp on the current slot and the hosting start timestamp on the next
     * one.  As part of the process, this method tests the subsequent domain to make sure it is still valid;
     * if not then it will flag the slot as deleted. Creates additional hosting slots (and blocks) if necessary and
     * permitted.
     * </p>
     *
     * @param gameId id of the game
     * @param allowSlotCreation <code>true</code> if this method is permitted to create new hosting slots in
     *        order to fulfull the request; <code>false</code> if not
     * @throws GameDataException if a checked exception prevents this method from completing successfully.
     * @throws IllegalStateException if the manager has been stopped.
     */
    private void advanceHostingSlot(long gameId, boolean allowSlotCreation) throws GameDataException {
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
        if (game.getEndDate() != null) {
            // Game has already ended; return without doing anything. in the future, this should be logged
            return;
        }

        //get the blocks of the game
        HostingBlock[] blocks = game.getBlocks();
        if (blocks == null || blocks.length == 0) {
            throw new GameDataException("The game to be advanced has HostingBlock array or empty HostingBlock.");
        }

        hosting_blocks:
        for (int i = 0; i < blocks.length; i++) {
            HostingSlot[] slots = blocks[i].getSlots();

            // if no slots exist, just go on to the next block
            if (slots == null) {
                continue;
            }

            // set the end date of the current slot and the start date of the next slot to now
            find_current_slot:
            for (int j = 0; j < slots.length; j++) {

                if ( slots[j] == null){
                    throw new GameDataException("Null slots in HostingBlock of the game.");
                }

                // if this is the current slot ...
                if ((slots[j].getHostingStart() != null) && (slots[j].getHostingEnd() == null)) {
                    List slotsToUpdate = new ArrayList();
                    List slotsToDelete = new ArrayList();
                    int nextSlotBlockIndex = i;
                    HostingSlot[] nextSlots = slots;
                    int searchIndex = j + 1;
                    Date current = new Date();
                    int nextSlotIndex;

                    // set end date of this slot to now
                    slotsToUpdate.add(Helper.copyToSetEndDate(slots[j], current));

                    // find the next slot, and mark any invalid slots between
                    find_next_slot:
                    for (nextSlotIndex = findNextSlot(nextSlots, searchIndex); ;
                            nextSlotIndex = findNextSlot(nextSlots, searchIndex)) {
                        if (nextSlotIndex < 0) {

                            // all the slots from the search index to the end of the array need to be deleted
                            // (they are invalid)
                            slotsToDelete.addAll(Arrays.asList(nextSlots).subList(searchIndex, nextSlots.length));

                            // Try the next block, if there is one
                            if (++nextSlotBlockIndex < blocks.length) {
                                 nextSlots = blocks[nextSlotBlockIndex].getSlots();
                                searchIndex = 0;
                            } else {
                                // out of slots

                                if (allowSlotCreation) {
                                    autoCreateHostingBlocks(gameId);

                                    // try again after adding slots to the game, avoiding further recursion
                                    advanceHostingSlot(gameId, false);
                                    return;
                                } else {
                                    break find_next_slot;
                                }
                            }
                        } else {
                            // slots from the search index to just before the nextSlotIndex are invalid
                            slotsToDelete.addAll(Arrays.asList(nextSlots).subList(searchIndex, nextSlotIndex));

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
                            for (Iterator slotIterator = slotsToDelete.iterator(); slotIterator.hasNext(); ) {
                                HostingSlot slot = (HostingSlot) slotIterator.next();

                                gameDataPersistenceLocal.deleteSlot(slot.getId().longValue());
                            }
                        } else {
                            gameDataPersistenceRemote.updateSlots(slots);
                            for (Iterator slotIterator = slotsToDelete.iterator(); slotIterator.hasNext(); ) {
                                HostingSlot slot = (HostingSlot) slotIterator.next();

                                gameDataPersistenceRemote.deleteSlot(slot.getId().longValue());
                            }
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
     * Specifically, each of the game's existing blocks will be used as the basis for a new block.
     *
     * @param gameId the game to re create the hosting block and hosting slots.
     */
    private void autoCreateHostingBlocks(long gameId) throws GameDataException{
        Game game;

        try{
            //get the game with the given id
            if (gameDataPersistenceLocal != null) {
                game = gameDataPersistenceLocal.getGame(gameId);
            } else {
                game = gameDataPersistenceRemote.getGame(gameId);
            }
            if (game == null) {
                throw new GameDataException("There is no game with ID " + gameId);
            }

            HostingBlock[] blocks = game.getBlocks();
            for(int i = 0 ; i < blocks.length; i++){
                HostingBlock block = blocks[i];

                //copy the block, add it to database
                HostingBlock newBlock = null;
                if (gameDataPersistenceLocal != null) {
                    newBlock = gameDataPersistenceLocal.addBlock(gameId, block.getMaxHostingTimePerSlot());
                } else {
                    newBlock = gameDataPersistenceRemote.addBlock(gameId, block.getMaxHostingTimePerSlot());
                }
                //get the corresponding auction of the hosting block
                Auction oldAuction = auctionManager.getAuctionPersistence().getAuction(block.getId().longValue());

                //create new bid for the fake auction
                Bid [] bids = oldAuction.getBids();
                Bid [] newBids = new Bid[(bids != null) ? bids.length : 0];
                for(int j = 0 ; j < newBids.length; j++){
                    newBids[j] = new CustomBid(bids[j].getBidderId(), ((CustomBid)bids[j]).getImageId(),
                            bids[j].getMaxAmount(), bids[j].getTimestamp());
                }

                //create a new "Fake Auction" for the new HostingBlock, copy the attribute from the old Auctions,
                //the new bids will also be persisted
                Auction fakeAuction = new AuctionImpl(newBlock.getId(), oldAuction.getSummary(),
                        oldAuction.getDescription(), oldAuction.getItemCount(), oldAuction.getMinimumBid(),
                               oldAuction.getStartDate(), oldAuction.getEndDate(), newBids);
                auctionManager.getAuctionPersistence().createAuction(fakeAuction);

                //reload the auction from persistence in order to get all the things with newIds.
                fakeAuction = auctionManager.getAuctionPersistence().getAuction(newBlock.getId().longValue());

                //reload the new bids
                newBids = fakeAuction.getBids();

                //copy the slots and auto create it
                autoCreateHostingSlots(block.getSlots(), newBlock.getId().longValue(), newBids);
            }
        } catch(Exception e){
            throw new GameDataException("Error in auto-creation hosting blocks and slots.", e);
        }
    }

    /**
     * <p>
     * Auto Create HostingSlots for the new hosting block.
     * For each new slots, copy the domain targets, set the hostingstart and hosting end
     * and generate the puzzle and brainteaser list.
     * </p>
     *
     * @param oldSlots the old slots
     * @param newBlockId the new HostingBlock id
     * @param newBids the new Bid array to create slots
     * @throws PersistenceException fail to update db
     * @throws RemoteException fail to call ejb
     * @throws GameDataException fail to auto create hosting slots
     */
    private void autoCreateHostingSlots(HostingSlot [] oldSlots, long newBlockId, Bid [] newBids)
           throws PersistenceException, RemoteException, GameDataException {

        // shuffle randomly the newBids array
        List newBidsList = Arrays.asList(newBids);
        Collections.shuffle(newBidsList);

        // get the bid id array
        long  [] copiedBidIds = new long[newBidsList.size()];

        for(int j = 0 ; j < newBidsList.size(); j++){
            copiedBidIds[j] = ((CustomBid)newBidsList.get(j)).getId().longValue();
        }

        //create new HostingSlots array with the new block id and the new bid ids
        HostingSlot [] newSlots = (gameDataPersistenceLocal != null)
                ? gameDataPersistenceLocal.createSlots(newBlockId, copiedBidIds)
                : gameDataPersistenceRemote.createSlots(newBlockId, copiedBidIds);

        /*
         * for each new slot, copy the domain targets, set the hosting start and hosting end
         * and generate the puzzle and brainteaser list
         */
        oldSlots = (HostingSlot[]) oldSlots.clone();  // this array gets modified by the procedure that follows

        for(int j = 0 ; j < newSlots.length; j++){
            // get the domain targets of the old slots and shuffle randomly, except for the last target
            HostingSlot oldSlot = findSlotByDomainId(oldSlots, newSlots[j].getDomain().getId().longValue());

            if (oldSlot == null) { // probably the corresponding old slot had been deleted
                if (gameDataPersistenceLocal != null) {
                    gameDataPersistenceLocal.deleteSlot(newSlots[j].getId().longValue());
                } else {
                    gameDataPersistenceRemote.deleteSlot(newSlots[j].getId().longValue());
                }
                continue;
            }

            List targets = Arrays.asList(oldSlot.getDomainTargets());

            // no point in shuffling one (or fewer) objects, and must in any case test for an empty list:
            if (targets.size() > 2) {
                Collections.shuffle(targets.subList(0, targets.size() - 1));
            }

            // create new DomainTargets with null id objects.
            List newTargets = new ArrayList();

            for(int fix = 0 ;  fix < targets.size(); fix++) {
                DomainTarget target = (DomainTarget)targets.get(fix);

                newTargets.add(new DomainTargetImpl(null, fix, target.getUriPath(), target.getIdentifierText(),
                            target.getIdentifierHash(), createClueImage(target.getIdentifierText())));
            }

            // copy the slot to set the hosting start, hosting end and shuffled randomly targets
            HostingSlot copiedSlot = Helper.copySlot(newSlots[j], null, null,
                    (DomainTarget[]) newTargets.toArray(new DomainTarget[0]));

            // update the slot
            HostingSlot updatedSlot = ((gameDataPersistenceLocal != null)
                    ? gameDataPersistenceLocal.updateSlots(new HostingSlot[]{copiedSlot})
                    : gameDataPersistenceRemote.updateSlots(new HostingSlot[]{copiedSlot}))[0];

            // regenerate Puzzle
            regeneratePuzzle(updatedSlot.getId().longValue());

            // regenerate BrainTeaser array
            regenerateBrainTeaser(updatedSlot.getId().longValue());
        }
    }

    /**
     * Find a slot for which the domain id is equal to the given domainId.
     *
     * @param slots the slots array; members are set to null as they are used by this method
     * @param domainId the domain id
     * @return the HostingSlot
     */
    private HostingSlot findSlotByDomainId(HostingSlot [] slots, long domainId){
        for(int i = 0 ; i < slots.length; i++){
            if (slots[i] != null && slots[i].getDomain().getId().longValue() == domainId){
                HostingSlot slot = slots[i];

                slots[i] = null;

                return slot;
            }
        }
        return null;
    }

    /**
     * Finds a among the provided slots the first suitable one to which the Ball
     * may be advanced
     *
     * @param  slots a <code>HostingSlot[]</code> containing the candidate
     *         slots
     * @param  testIndex the index into the <code>slots</code> of the first
     *         candidate
     *
     * @return the index into the <code>slots</code> array of the slot that
     *         should be next, or <code>-1</code> if there is no suitable candidate
     */
    private int findNextSlot(HostingSlot[] slots, int testIndex) throws GameDataException {
        for (; testIndex < slots.length; testIndex++) {
            if (testUpcomingDomain(slots[testIndex])) {
                return testIndex;
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
        targetCheckNotifier.stopNotifier();
        try {
            gameStartNotifier.join();
            newGameAvailableNotifier.join();
            targetCheckNotifier.join();
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
     * Get the AuctionManager.
     * @return the auctionManager
     */
    public AuctionManager getAuctionManager() {
        return auctionManager;
    }

    /**
     * Set the auctionManager instance.
     * @param auctionManager the auctionManager to set
     */
    public void setAuctionManager(AuctionManager auctionManager) {
        this.auctionManager = auctionManager;
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
     * <p>
     * This method is used by abstract parent class to obtain an instance of the
     * <code>HashAlgorithmManager</code> class. This object may only be constructed in the derived
     * concrete classes, since only they have access to the specific configuration parameters.
     * </p>
     *
     * @return an instance of the <code>HashAlgorithmManager</code> class.
     * @see BaseGameDataManager#getHashAlgorithmManager()
     */
    protected HashAlgorithmManager getHashAlgorithmManager() {
        return this.hashAlgManager;
    }

    /**
     * <p>
     * This method is used by abstract parent class to obtain an instance of the
     * <code>RandomStringImage</code> class. This object may only be constructed in the derived
     * concrete classes, since only they have access to the specific configuration parameters.
     * </p>
     *
     * @return an instance of the <code>RandomStringImage</code> class.
     * @see BaseGameDataManager#getRandomStringImage()
     */
    protected RandomStringImage getRandomStringImage() {
        return this.randomStringImage;
    }

    /**
     * <p>
     * This method is used by abstract parent class to obtain an instance of the
     * <code>GameDataLocal</code> class. This object may only be constructed in the derived
     * concrete classes, since only they have access to the specific configuration parameters.
     * </p>
     *
     * @return an instance of the <code>GameDataLocal</code> class.
     * @see BaseGameDataManager#getGameDataPersistenceLocal()
     */
    protected GameDataLocal getGameDataPersistenceLocal() {
        return this.gameDataPersistenceLocal;
    }

    /**
     * <p>
     * This method is used by abstract parent class to obtain an instance of the
     * <code>GameData</code> class. This object may only be constructed in the derived concrete
     * classes, since only they have access to the specific configuration parameters.
     * </p>
     *
     * @return an instance of the <code>GameData</code> class.
     * @see BaseGameDataManager#getGameDataPersistenceRemote()
     */
    protected GameData getGameDataPersistenceRemote() {
        return this.gameDataPersistenceRemote;
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
     * Checks whether target text exists in the provided statistics information.
     *
     * @return <code>true</code> if target text has been found in the provided statistics
     *         information, <code>false</code> if it has not.
     * @param textStatistics text statistics collected from some page of a hosting site. Used to
     *            check for the presence of the target text.
     * @param targetText target text to check its presence in the provided statistics information.
     */
    private static boolean checkForTextExistence(TextStatistics[] textStatistics, String targetText) {
        Helper.checkObjectNotNull(textStatistics, "textStatistics");
        Helper.checkStringNotNullOrEmpty(targetText, "targetText");

        for (int i = 0; i < textStatistics.length; ++i)
            if (textStatistics[i].getText().equals(targetText))
                return true;

        return false;
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
     * the server. This class is ��smart�� enough to work with both
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

    /**
     * <p>
     * This is a simple inner class, which runs as a worker thread and checks, at specified
     * intervals, if targets on hosting site have been updated. This class accepts a registered
     * listener, which gets notified whenever target has been updated. This is an internal worker
     * thread class so it is guaranteed to be only executed by a single thread.
     * </p>
     */
    private class TargetCheckNotifier extends Thread {
        /**
         * <p>
         * Represents the sleep interval, in milliseconds, which determines how often this thread
         * checks if targets have been updated.
         * </p>
         */
        private final long sleepInterval;

        /**
         * <p>
         * Represents the listener registered with this thread that is interested in
         * receiving the notification that a game has started.
         * </p>
         *
         */
        private final TargetUpdateListener targetUpdateListener;

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
         * @param sleepInterval interval which dictates how often the thread polls the hosting sites for target update check, in milliseconds.
         * @param listener the <code>TargetCheckListener</code> instance.
         */
        public TargetCheckNotifier(long sleepInterval, TargetUpdateListener listener) {
            this.sleepInterval = sleepInterval;
            this.targetUpdateListener = listener;
        }

        /**
         * <p>
         * This is the thread's run method which will use <code>findGames</code> method from
         * either <code>GameData</code> or <code>GamaDataLocal</code> classes to obtain a list
         * of all games that have not yet ended, and iterate over that list to check targets. If any
         * of the targets has disappeared, this method will try to update the target.
         * </p>
         */
        public void run() {
            System.err.println("TargetCheckNotifier started");

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

                try {
                    // Get games that have not ended yet
                    Game[] games;

                    if (gameDataPersistenceLocal != null) {
                        games = gameDataPersistenceLocal.findGames(null, Boolean.FALSE);
                    } else {
                        games = gameDataPersistenceRemote.findGames(null, Boolean.FALSE);
                    }

                    // This will check whether any targets are in ned of update,
                    // and update those ones that need to be updated
                    updateGames(games);
                } catch (Exception e) {
                    // eat the exception
                }
            }

            System.err.println("TargetCheckNotifier stopped");
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

        /**
         * Checks targets and updates them if needed.
         *
         * @param games the list of all games that need their targets to be checked and, possibly,
         *            updated.
         */
        private void updateGames(Game[] games) {
            HttpUtility http = new HttpUtility(HttpUtility.GET);
            Map pageCache = new HashMap();

            http.setFollowRedirects(true);
            http.setDepthLimit(10);

            for (int gameIndex = 0; gameIndex < games.length; ++gameIndex) {
                HostingBlock[] hostingBlocks = games[gameIndex].getBlocks();

                for (int blockIndex = 0; blockIndex < hostingBlocks.length; ++blockIndex) {
                    HostingSlot[] slots = hostingBlocks[blockIndex].getSlots();

                    for (int slotIndex = 0; slotIndex < slots.length; ++slotIndex) {
                        Date hostingStart = slots[slotIndex].getHostingStart();

                        // Skip slots that have not yet begun hosting
                        if (hostingStart == null) {
                            continue;
                        }

                        DomainTarget[] targets = slots[slotIndex].getDomainTargets();
                        boolean anyUpdated = false;
                        boolean firstUpdated = false;

                        for (int targetIndex = 0; targetIndex < targets.length; ++targetIndex) {
                            DomainTarget updatedTarget = checkAndUpdateTarget(http, targets[targetIndex], pageCache);

                            if (updatedTarget != null) {
                                targets[targetIndex] = updatedTarget;
                                anyUpdated = true;
                                if (targetIndex == 0) {
                                    firstUpdated = true;
                                }
                            }
                        }

                        // Persist the slot if any target has been updated
                        if (anyUpdated) {
                            // Create a new HostingSlotImpl instance
                            HostingSlotImpl newSlot = Helper.doCopy(slots[slotIndex]);
                            // Set the new domain targets
                            newSlot.setDomainTargets(targets);
                            // Update slot
                            persistSlot(newSlot);

                            if (firstUpdated) {
                                try {
                                    regenerateBrainTeaser(slots[slotIndex].getId().longValue());
                                } catch (GameDataException gde) {
                                    System.err.println("Unable to generate a new brain teaser for slot "
                                            + slots[slotIndex].getId().longValue());
                                    gde.printStackTrace(System.err);
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * <p>
         * Checks target for existence and updates it if needed. If this method fails for any
         * reason, the return value is <code>null</code>.
         * </p>
         *
         * @return newly-created <code>DomainTarget</code> that contains updated target, or
         *         <code>null</code> if target does not need to be updated or there was a failure
         *         during check or update operation.
         * @param  httpUtil <code>HttpUtility</code> object to fetch the page that possibly
         *         contains target from hosting site over HTTP protocol.
         * @param  target object that contains target, and which needs to be checked for validity.
         * @param  pageCache a Map from target URLs to page content, used to cache pages on which multiple
         *         targets appear to boost performance and reduce impact on host sites.  If a
         *         page is loaded by this method then it will be cached in this map
         */
        private DomainTarget checkAndUpdateTarget(HttpUtility httpUtil, DomainTarget target, Map pageCache) {
            SiteStatistics siteStatistics;

            try {
                String urlString = target.getUriPath();
                String contents;

                if (pageCache.containsKey(urlString)) {
                    contents = (String) pageCache.get(urlString);
                } else {
                    contents = httpUtil.execute(target.getUriPath());
                    pageCache.put(urlString, contents);
                }

                siteStatistics = GameDataUtility.getConfiguredSiteStatisticsInstance("SiteStatistics");
                siteStatistics.accumulateFrom(contents, "document1");
            } catch (IOException ioe) {
                System.err.println("IOException while trying to check address: " + target.getUriPath());
                System.err.println("Message says: " + ioe.getMessage());
                return null; // Target has not been updated
            } catch (HttpException httpe) {
                System.err.println("HttpException while trying to check address: " + target.getUriPath());
                System.err.println("Message says: " + httpe.getMessage());
                return null; // Target has not been updated
            } catch (GameDataManagerConfigurationException gdmce) {
                System.err.println("Unable to obtain SiteStatistics object. Exception information follows.");
                gdmce.printStackTrace(System.err);
                return null; // Target has not been updated
            } catch (StatisticsException se) {
                System.err.println("Unable to accumulate statistics information from document located at: "
                        + target.getUriPath());
                se.printStackTrace(System.err);
                return null; // Target has not been updated
            }

            TextStatistics[] stats = siteStatistics.getElementContentStatistics();

            // If the target has not been found, update it and return the updated target
            if (!checkForTextExistence(stats, target.getIdentifierText())) {
                return targetUpdateListener.targetUpdated(stats, target);
            }

            // Target is in no need of update
            return null;
        }
    }

    private class EJBHomes {
        EJBHome remoteHome;
        EJBLocalHome localHome;

        public EJBHomes() {}
    }
}
