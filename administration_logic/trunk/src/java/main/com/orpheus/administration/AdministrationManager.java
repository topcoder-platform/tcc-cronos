/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ejb.CreateException;
import javax.imageio.ImageIO;

import com.orpheus.administration.entities.DomainTargetImpl;
import com.orpheus.administration.entities.HostingSlotImpl;
import com.orpheus.administration.entities.PuzzleConfig;
import com.orpheus.administration.entities.PuzzleTypeEnum;
import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.util.config.Property;
import com.topcoder.util.image.manipulation.Image;
import com.topcoder.util.image.manipulation.image.MutableMemoryImage;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleGenerator;
import com.topcoder.util.puzzle.PuzzleType;
import com.topcoder.util.puzzle.PuzzleTypeSource;
import com.topcoder.util.web.sitestatistics.SiteStatistics;
import com.topcoder.util.web.sitestatistics.TextStatistics;
import com.topcoder.web.frontcontroller.results.DownloadData;
import com.topcoder.webspider.web.WebAddressContext;
import com.topcoder.webspider.web.WebCrawler;
import com.topcoder.webspider.web.WebPageData;

/**
 * <p>
 * A class providing an API for administrative functions that need to be
 * accessible to other components or that need to be accessed by some handlers
 * of this component. It provides methods to regenerate puzzles, brain teasers
 * and hunt targets for a particular slot. It also provides a method to
 * initialize all slots for a particular block.
 * </p>
 * 
 * <p>
 * This class will use SiteStatistics to gather candidates for targets from a
 * slot's domain when generating hunt targets. Configuration parameters such as
 * inclusion rules that constrain element statistics to those elements within
 * minimum and maximum lengths will be provided to the Web Site Statistics
 * component rather than this component.
 * </p>
 * 
 * <p>
 * Following is a sample configuration.
 * <pre>
 * &lt;Config name=&quot;com.orpheus.administration.AdministrationManager&quot;&gt;
 *  &lt;Property name=&quot;jigsaw&quot;&gt;
 *  &lt;Property name=&quot;puzzleTypeName&quot;&gt;
 *  &lt;Value&gt;jigsaw&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;width&quot;&gt;
 *  &lt;Value&gt;120&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;height&quot;&gt;
 *  &lt;Value&gt;100&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  
 *  &lt;Property name=&quot;slidingTile&quot;&gt;
 *  &lt;Property name=&quot;puzzleTypeName&quot;&gt;
 *  &lt;Value&gt;slidingTile&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;width&quot;&gt;
 *  &lt;Value&gt;120&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;height&quot;&gt;
 *  &lt;Value&gt;100&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  
 *  &lt;Property name=&quot;missingLetter&quot;&gt;
 *  &lt;Property name=&quot;puzzleTypeName&quot;&gt;
 *  &lt;Value&gt;missingLetter&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;seriesSize&quot;&gt;
 *  &lt;Value&gt;12&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  
 *  &lt;Property name=&quot;letterScramble&quot;&gt;
 *  &lt;Property name=&quot;puzzleTypeName&quot;&gt;
 *  &lt;Value&gt;letterScramble&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;seriesSize&quot;&gt;
 *  &lt;Value&gt;12&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  
 *  &lt;Property name=&quot;admin-data-jndi-name&quot;&gt;
 *  &lt;Value&gt;AdminDataHome&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;game-data-jndi-name&quot;&gt;
 *  &lt;Value&gt;GameDataHome&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;MinTargetLength&quot;&gt;
 *  &lt;Value&gt;1&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;MaxTargetLength&quot;&gt;
 *  &lt;Value&gt;12&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;PreferredTargetLength&quot;&gt;
 *  &lt;Value&gt;5&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;MinDistinctPages&quot;&gt;
 *  &lt;Value&gt;1&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;MaxDistinctPages&quot;&gt;
 *  &lt;Value&gt;4&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;PreferredDistinctPages&quot;&gt;
 *  &lt;Value&gt;3&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;objFactoryNS&quot;&gt;
 *  &lt;Value&gt;objFactoryNS&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Config&gt;
 * </pre>
 * </p>
 * 
 * <p>
 * Thread-Safety: This class is thread-safe. It is immutable. The
 * puzzleTypeSource instance variable is thread-safe by nature. The methods
 * synchronize over puzzleConfigMap whenever it is accessed.
 * </p>
 * 
 * @author bose_java, KKD
 * @version 1.0
 */
public class AdministrationManager {

    /**
     * <p>
     * Holds the puzzle types to choose from when regenerating puzzles. One of
     * the puzzle types will be chosen at random in the method
     * regeneratePuzzle().
     * </p>
     * 
     */
    private static final PuzzleTypeEnum[] PUZZLE_TYPES = new PuzzleTypeEnum[] {
            PuzzleTypeEnum.JIGSAW, PuzzleTypeEnum.SLIDING_TILE};

    /**
     * <p>
     * Holds the puzzle types to choose from when regenerating brain teasers.
     * One of the puzzle types will be chosen at random in the method
     * regenerateBrainTeaser().
     * </p>
     * 
     */
    private static final PuzzleTypeEnum[] BRAINTEASER_TYPES = new PuzzleTypeEnum[] {
            PuzzleTypeEnum.MISSING_LETTER, PuzzleTypeEnum.LETTER_SCRAMBLE};

    /**
     * Represents the PuzzleTypeSource instance to generate puzzles and
     * brainteasers with.<br/> This variable is initialized in the constructor
     * and does not change after that.<br/> It will never be null.<br/>
     * 
     */
    private final PuzzleTypeSource puzzleTypeSource;

    /**
     * This holds the JNDI name to use to look up the GameDataHome service.<br/>
     * This variable is initialized in the constructor and does not change after
     * that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String gameDataJndiName;

    /**
     * This holds the JNDI name to use to look up the AdminDataHome service.<br/>
     * This variable is initialized in the constructor and does not change after
     * that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String adminDataJndiName;

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
     * Minimum number of targets to generate for any particular slot.<br/> This
     * variable is initialized in the constructor and does not change after
     * that.<br/> Must be positive.
     * 
     */
    private final int minTargetLength;

    /**
     * Maximum number of targets to generate for any particular slot.<br/> This
     * variable is initialized in the constructor and does not change after
     * that.<br/> Must be positive.
     * 
     */
    private final int maxTargetLength;

    /**
     * Preferred number of targets to generate for any particular slot.<br/>
     * This variable is initialized in the constructor and does not change after
     * that.<br/> Must be positive.
     * 
     */
    private final int preferredTargetLength;

    /**
     * Minimum number of distinct pages that a text should appear to be a
     * candidate for selection.<br/> This variable is initialized in the
     * constructor and does not change after that.<br/> Must be positive.
     * 
     */
    private final int minDistinctPages;

    /**
     * Maximum number of distinct pages that a text should appear to be a
     * candidate for selection.<br/> This variable is initialized in the
     * constructor and does not change after that.<br/> Must be positive.
     * 
     */
    private final int maxDistinctPages;

    /**
     * Preferred number of distinct pages that a text should appear to be a
     * candidate for selection.<br/> This variable is initialized in the
     * constructor and does not change after that.<br/> Must be positive.
     * 
     */
    private final int preferredDistinctPages;

    /**
     * Namespace for instantiating ConfigManagerSpecificationFactory of
     * ObjectFactory.
     * 
     */
    private final String objectFactory;

    /**
     * Creates a AdministrationManager instance with given PuzzleTypeSource to
     * use when generating puzzles and brain teasers and set up with
     * configuration values from given namespace.
     * 
     * 
     * @param puzzleTypeSource
     *            PuzzleTypeSource to use when generating puzzles and brain
     *            teasers.
     * @param namespace
     *            namespace to load configuration details from.
     * @throws ConfigurationException
     *             if configuration values are missing or invalid.
     * @throws IllegalArgumentException
     *             if puzzleTypeSource is null, or if namespace is null or
     *             empty.
     */
    public AdministrationManager(PuzzleTypeSource puzzleTypeSource,
            String namespace) throws ConfigurationException {
        Helper.checkNull(puzzleTypeSource, "puzzleTypeSource");
        Helper.checkString(namespace, "namespace");
        this.puzzleTypeSource = puzzleTypeSource;
        puzzleConfigMap = new HashMap();
        // Load property "jigsaw"
        initializePuzzleType(namespace, "jigsaw", PuzzleTypeEnum.JIGSAW);
        // Load property "slidingTile"
        initializePuzzleType(namespace, "slidingTile",
                PuzzleTypeEnum.SLIDING_TILE);
        // Load property "missingLetter"
        initializeBrainTeaserType(namespace, "missingLetter",
                PuzzleTypeEnum.MISSING_LETTER);
        // Load property "letterScramble"
        initializeBrainTeaserType(namespace, "letterScramble",
                PuzzleTypeEnum.LETTER_SCRAMBLE);

        // initialize other fields
        adminDataJndiName = Helper.getPropertyString(namespace,
                "admin-data-jndi-name");
        gameDataJndiName = Helper.getPropertyString(namespace,
                "game-data-jndi-name");
        minTargetLength = Helper.getPropertyInt(namespace, "MinTargetLength");
        maxTargetLength = Helper.getPropertyInt(namespace, "MaxTargetLength");
        preferredTargetLength = Helper.getPropertyInt(namespace,
                "PreferredTargetLength");
        minDistinctPages = Helper.getPropertyInt(namespace, "MinDistinctPages");
        maxDistinctPages = Helper.getPropertyInt(namespace, "MaxDistinctPages");
        preferredDistinctPages = Helper.getPropertyInt(namespace,
                "PreferredDistinctPages");
        objectFactory = Helper.getPropertyString(namespace, "objFactoryNS");
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
            PuzzleTypeEnum type) throws ConfigurationException {
        Property property = Helper.getProperty(namespace, name);
        String typeName = Helper.getSubPropertyString(property,
                "puzzleTypeName");
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
            PuzzleTypeEnum type) throws ConfigurationException {
        Property property = Helper.getProperty(namespace, name);
        String typeName = Helper.getSubPropertyString(property,
                "puzzleTypeName");
        int width = Helper.getSubPropertyInt(property, "width");
        int height = Helper.getSubPropertyInt(property, "height");
        PuzzleConfig config = new PuzzleConfig(typeName, new Integer(width),
                new Integer(height), 0);
        puzzleConfigMap.put(type, config);
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
     * Impl Notes:
     * <ol>
     * <li>Create the AdminData EJB instance [adminData] as described in Common
     * Operations section of Comp Spec.</li>
     * <li>Create the GameData EJB instance [gameData] as described in Common
     * Operations section of Comp Spec.</li>
     * <li>If an RemoteException occurs, wrap in AdministrationException and
     * re-throw.</li>
     * <li>Call regeneratePuzzle(slotId, adminData, gameData).</li>
     * </ol>
     * 
     * 
     * @param slotId
     *            the slot id.
     * @throws AdministrationException
     *             if a checked exception prevents this method from completing
     *             normally.
     */
    public void regeneratePuzzle(long slotId) throws AdministrationException {
        ServiceLocator sl = new ServiceLocator();
        // Create the AdminData EJB instance
        AdminData adminData = getAdminData(sl);
        // Create the GameData EJB instance
        GameData gameData = getGameData(sl);
        regeneratePuzzle(slotId, adminData, gameData);
    }

    /**
     * This method try to get remote AdminData.
     * 
     * @param sl
     *            the ServiceLocator
     * @return a AdminData
     * @throws AdministrationException
     *             if any exception occur
     */
    private AdminData getAdminData(ServiceLocator sl)
        throws AdministrationException {
        AdminDataHome adminDataHome = (AdminDataHome) sl.getRemoteHome(
                adminDataJndiName, AdminDataHome.class);
        try {
            return adminDataHome.create();
        } catch (RemoteException e) {
            throw new AdministrationException(
                    "Failed to get AdminData ejb instance.", e);
        } catch (CreateException e) {
            throw new AdministrationException(
                    "Failed to get AdminData ejb instance.", e);
        }
    }

    /**
     * This method try to get remote GameData.
     * 
     * @param sl
     *            the ServiceLocator
     * @return a GameData
     * @throws AdministrationException
     *             if any exception occur
     */
    private GameData getGameData(ServiceLocator sl)
        throws AdministrationException {
        GameDataHome gameDataHome = (GameDataHome) sl.getRemoteHome(
                gameDataJndiName, GameDataHome.class);
        try {
            return gameDataHome.create();
        } catch (RemoteException e) {
            throw new AdministrationException(
                    "Failed to get GameData ejb instance", e);
        } catch (CreateException e) {
            throw new AdministrationException(
                    "Failed to get GameData ejb instance", e);
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
     * Impl Notes:
     * <ol>
     * <li>Create the AdminData EJB instance [adminData] as described in Common
     * Operations section of Comp Spec.</li>
     * <li>Create the GameData EJB instance [gameData] as described in Common
     * Operations section of Comp Spec.</li>
     * <li>If an RemoteException occurs, wrap in AdministrationException and
     * re-throw.</li>
     * <li>Call regenerateBrainTeaser(slotId, adminData, gameData).</li>
     * </ol>
     * 
     * 
     * @param slotId
     *            slot id.
     * @throws AdministrationException
     *             if a checked exception prevents this method from completing
     *             normally
     */
    public void regenerateBrainTeaser(long slotId)
        throws AdministrationException {
        ServiceLocator sl = new ServiceLocator();
        // Create the AdminData EJB instance
        AdminData adminData = getAdminData(sl);
        // Create the GameData EJB instance
        GameData gameData = getGameData(sl);
        regenerateBrainTeaser(slotId, adminData, gameData);
    }

    /**
     * (Re)generates the mini-hunt targets for the specified hosting slot. It
     * will use the Web Site Statistics component to gather candidates for the
     * targets. It will base its selections in part on the number of distinct
     * pages on which candidates appear. The minimum, maximum, and preferred
     * number of pages will be configuration parameters. It will then record the
     * targets for the slot by recording updated slot information with the
     * GameData EJB. Impl Notes:
     * <ol>
     * <li>Create the GameData EJB instance [gameData] as described in Common
     * Operations section of Comp Spec.</li>
     * <li>If an RemoteException occurs, wrap in AdministrationException and
     * re-throw.</li>
     * <li>Call generateHuntTargets(slotId, gameData).</li>
     * </ol>
     * 
     * 
     * @param slotId
     *            the slot id
     * @throws AdministrationException
     *             if a checked exception prevents this method from completing
     *             normally
     */
    public void generateHuntTargets(long slotId) throws AdministrationException {
        ServiceLocator sl = new ServiceLocator();
        // Create the GameData EJB instance
        GameData gameData = getGameData(sl);
        generateHuntTargets(slotId, gameData);
    }

    /**
     * Initializes all the slots in the specified hosting block by generating
     * minihunt targets, brain teasers, and game-win puzzles for them. It will
     * retrieve the slot ids for the given block and call regeneratePuzzle(),
     * regenerateBrainTeaser() and generateHuntTargets() for each slot id.<br/>
     * Impl Notes : See Section 1.3.17 of Comp Spec.
     * 
     * 
     * @param blockId
     *            block id.
     * @throws AdministrationException
     *             if a checked exception prevents this method from completing
     *             normally
     */
    public void initializeSlotsForBlock(long blockId)
        throws AdministrationException {
        ServiceLocator sl = new ServiceLocator();
        // Create the AdminData EJB instance
        AdminData adminData = getAdminData(sl);
        // Create the GameData EJB instance
        GameData gameData = getGameData(sl);
        try {
            // Get the block
            HostingBlock block = gameData.getBlock(blockId);
            HostingSlot[] slots = block.getSlots();
            for (int i = 0; i < slots.length; i++) {
                Long slotId = slots[i].getId();
                regeneratePuzzle(slotId.longValue(), adminData, gameData);
                regenerateBrainTeaser(slotId.longValue(), adminData, gameData);
                generateHuntTargets(slotId.longValue(), gameData);
            }
        } catch (AdministrationException e) {
            // re-throw
            throw e;
        } catch (Exception e) {
            throw new AdministrationException(
                    "Failed to initialize slots for block.", e);
        }
    }

    /**
     * This helper method is called to actually regenerate puzzle for particular
     * slot. It is called by the regeneratePuzzle(slotId) and
     * initializeSlotsForBlock(blockId) methods.<br/> Impl Notes : See Section
     * 1.3.14 of Comp Spec.
     * 
     * 
     * @param slotId
     *            slot id.
     * @param adminData
     *            AdminData EJB instance.
     * @param gameData
     *            GameData EJB instance.
     * @throws AdministrationException
     *             if an error such as RemoteException of GameDataException
     *             occurs.
     */
    private void regeneratePuzzle(long slotId, AdminData adminData,
            GameData gameData) throws AdministrationException {
        try {
            // Get the hosting slot
            HostingSlot slot = gameData.getSlot(slotId);
            // Get the image for the puzzle
            DownloadData imageData = gameData
                    .getDownloadData(slot.getImageId());
            Image image = new MutableMemoryImage(ImageIO.read(imageData
                    .getContent()));
            int chosenPuzzle = new Random().nextInt(PUZZLE_TYPES.length);
            // Get puzzle config for the type
            PuzzleConfig config = null;
            synchronized (puzzleConfigMap) {
                config = (PuzzleConfig) puzzleConfigMap
                        .get(PUZZLE_TYPES[chosenPuzzle]);
            }
            // Set puzzle generator configuration
            PuzzleType puzzleType = puzzleTypeSource.getPuzzleType(config
                    .getPuzzleTypeName());
            PuzzleGenerator puzzleGenerator = puzzleType.createGenerator();
            puzzleGenerator.setAttribute("image", image);
            puzzleGenerator.setAttribute("width", config.getWidth());
            puzzleGenerator.setAttribute("height", config.getHeight());
            // Generate puzzle
            PuzzleData puzzleData = puzzleGenerator.generatePuzzle();
            // Store the puzzle and get id
            long[] puzzleIds = adminData
                    .storePuzzles(new PuzzleData[] {puzzleData});
            // Create a new HostingSlotImpl instance
            HostingSlotImpl newSlot = Helper.copySlot(slot, slot
                    .getSequenceNumber());
            // Set the new puzzle id
            newSlot.setPuzzleId(new Long(puzzleIds[0]));
            // Update slot
            gameData.updateSlots(new HostingSlot[] {newSlot});
        } catch (Exception e) {
            throw new AdministrationException("Failed to regenerate puzzle.", e);
        }
    }

    /**
     * This helper method is called to actually regenerate brainteasers for
     * particular slot. It is called by the regenerateBrainTeaser(slotId) and
     * initializeSlotsForBlock(blockId) methods.
     * 
     * 
     * @param slotId
     *            slot id.
     * @param adminData
     *            AdminData EJB instance.
     * @param gameData
     *            GameData EJB instance.
     * @throws AdministrationException
     *             if an error such as RemoteException of GameDataException
     *             occurs.
     */
    private void regenerateBrainTeaser(long slotId, AdminData adminData,
            GameData gameData) throws AdministrationException {
        try {
            // Get the hosting slot
            HostingSlot slot = gameData.getSlot(slotId);
            // Get text for puzzle
            DomainTarget[] targets = slot.getDomainTargets();
            if (targets.length == 0) {
                throw new AdministrationException(
                        "Failed to regenerate BrainTeaser for slot(slotId:"
                                + slotId
                                + "), caused by there is no DomainTargets set for that slot.");
            }
            String puzzleText = targets[0].getIdentifierText();
            int chosenPuzzle = new Random().nextInt(BRAINTEASER_TYPES.length);
            // Get puzzle config for the type
            PuzzleConfig config = null;
            synchronized (puzzleConfigMap) {
                config = (PuzzleConfig) puzzleConfigMap
                        .get(BRAINTEASER_TYPES[chosenPuzzle]);
            }
            // Generate the puzzle series
            PuzzleType puzzleType = puzzleTypeSource.getPuzzleType(config
                    .getPuzzleTypeName());
            PuzzleGenerator puzzleGenerator = puzzleType.createGenerator();
            puzzleGenerator.setAttribute("text", puzzleText);
            PuzzleData[] puzzleDatas = puzzleGenerator
                    .generatePuzzleSeries(config.getPuzzleSeriesSize());
            // Store the puzzles and get ids
            long[] puzzleIds = adminData.storePuzzles(puzzleDatas);
            // Create a new HostingSlotImpl instance
            HostingSlotImpl newSlot = Helper.copySlot(slot, slot
                    .getSequenceNumber());
            // Set the new brainteaser ids
            newSlot.setBrainTeaserIds(puzzleIds);
            // Update slot
            gameData.updateSlots(new HostingSlot[] {newSlot});
        } catch (Exception e) {
            throw new AdministrationException("Failed to regenerate puzzle.", e);
        }
    }

    /**
     * This helper method is called to actually generates the mini-hunt targets
     * for the specified hosting slot. It is called by the
     * generateHuntTargets(slotId) and initializeSlotsForBlock(blockId) methods.
     * 
     * 
     * @param slotId
     *            slot id.
     * @param gameData
     *            GameData EJB instance.
     * @throws AdministrationException
     *             if an error such as RemoteException of GameDataException
     *             occurs.
     */
    private void generateHuntTargets(long slotId, GameData gameData)
        throws AdministrationException {
        try {
            // Get the hosting slot
            HostingSlot slot = gameData.getSlot(slotId);
            String domainName = slot.getDomain().getDomainName();
            // Spider the domain
            WebCrawler crawler = new WebCrawler();
            crawler.getStrategy().addAddress(
                    new WebAddressContext(domainName, 0));
            List results = crawler.crawl();
            // Create a SiteStatistics instance
            // Create an instance of ObjectFactory
            ObjectFactory of = new ObjectFactory(
                    new ConfigManagerSpecificationFactory(objectFactory),
                    ObjectFactory.BOTH);
            // use ObjectFactory to create an instance of SiteStatistics
            SiteStatistics siteStatistics;
            try {
                siteStatistics = (SiteStatistics) of
                        .createObject("SiteStatistics");
            } catch (ClassCastException e) {
                throw new AdministrationException(
                        "Failed to create SiteStatistics instance via objectFactory.",
                        e);
            }
            // Feed the pages from crawler to site statistics
            Map docUrlMap = new HashMap();
            int idx = 0;
            for (Iterator iter = results.iterator(); iter.hasNext(); idx++) {
                WebPageData data = (WebPageData) iter.next();
                if (data.getContentsAsString() != null) {
                    String docId = "docID" + idx;
                    siteStatistics.accumulateFrom(data.getContentsAsString(),
                            docId);
                    docUrlMap.put(docId, data.getAddressContext().getAddress());
                }
            }
            TextStatistics[] stats = siteStatistics
                    .getElementContentStatistics();
            Random rd = new Random();
            int[] selected = new int[stats.length];
            for (int i = 0; i < selected.length; i++) {
                selected[i] = 0;
            }
            // generate domainTargets
            DomainTarget[] domainTargets = new DomainTarget[preferredTargetLength];
            for (int cnt = 0; cnt < preferredTargetLength && cnt < stats.length; cnt++) {
                TextStatistics textStatistics = selectStatisatics(selected,
                        stats, rd, cnt);
                if (textStatistics.getDocuments().length >= minDistinctPages
                        && textStatistics.getDocuments().length <= maxDistinctPages) {
                    String[] docs = textStatistics.getDocuments();
                    int chosenDocId = rd.nextInt(docs.length);
                    // create a DomainTargetImpl instance
                    DomainTargetImpl domainTarget = new DomainTargetImpl();
                    domainTarget.setUriPath((String) docUrlMap
                            .get(docs[chosenDocId]));
                    domainTarget.setIdentifierText(textStatistics.getText());
                    domainTargets[cnt] = domainTarget;
                }
            }
            // Create a new HostingSlotImpl instance
            HostingSlotImpl newSlot = Helper.copySlot(slot, slot
                    .getSequenceNumber());
            // Set the new domain targets
            newSlot.setDomainTargets(domainTargets);
            // Update slot
            gameData.updateSlots(new HostingSlot[] {newSlot});
        } catch (Exception e) {
            throw new AdministrationException("Failed to regenerate puzzle.", e);
        }
    }

    /**
     * Get a un-selected textStatistics.
     * 
     * @param selected
     *            an array indicates if the corresponding textStatistics has
     *            been selected
     * @param stats
     *            the textStatistics
     * @param rd
     *            the random generator
     * @param num
     *            the number of selected textStatistics
     * @return a random un-selected textStatistics
     */
    private TextStatistics selectStatisatics(int[] selected,
            TextStatistics[] stats, Random rd, int num) {
        int rdNum = rd.nextInt(stats.length - num);
        for (int i = 0; i < stats.length; i++) {
            if (rdNum == 0 && selected[i] == 0) {
                selected[i] = 1;
                return stats[i];
            }
            if (selected[i] == 0) {
                rdNum--;
            }
        }
        return null;
    }
}
