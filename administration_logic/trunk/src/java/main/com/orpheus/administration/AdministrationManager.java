/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import com.orpheus.administration.entities.DomainTargetImpl;
import com.orpheus.administration.entities.HostingSlotImpl;

import com.orpheus.game.GameDataException;
import com.orpheus.game.GameDataManager;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.PersistenceException;

import com.topcoder.randomstringimg.Configuration;
import com.topcoder.randomstringimg.InvalidConfigException;
import com.topcoder.randomstringimg.ObfuscationAlgorithm;
import com.topcoder.randomstringimg.ObfuscationException;
import com.topcoder.randomstringimg.RandomStringImage;

import com.topcoder.util.algorithm.hash.HashAlgorithmManager;
import com.topcoder.util.algorithm.hash.HashException;
import com.topcoder.util.algorithm.hash.algorithm.HashAlgorithm;
import com.topcoder.util.datavalidator.AndValidator;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.ObjectFactoryException;
import com.topcoder.util.objectfactory.SpecificationFactoryException;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.topcoder.util.web.sitestatistics.SiteStatistics;
import com.topcoder.util.web.sitestatistics.StatisticsException;
import com.topcoder.util.web.sitestatistics.TextStatistics;

import com.topcoder.webspider.crawling.BreadthFirstCrawlStrategy;
import com.topcoder.webspider.validators.DepthValidator;
import com.topcoder.webspider.validators.RegExValidator;
import com.topcoder.webspider.web.WebAddressContext;
import com.topcoder.webspider.web.WebCrawler;
import com.topcoder.webspider.web.WebPageData;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ejb.CreateException;

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
 *  &lt;Property name=&quot;HuntTargetsPerSlot&quot;&gt;
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
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
public class AdministrationManager {

    /**
     * The maximum depth to which the Web Spider will descend when crawling a
     * target site for the purpose of extracting hunt targets.  This should
     * probably be externally-configurable in some future version.
     */
    private final static int MAX_SPIDERING_DEPTH = 10;
   
    /**
     * This holds the JNDI name to use to look up the GameDataHome service.<br/>
     * This variable is initialized in the constructor and does not change after
     * that.<br/> It will never be null or empty.<br/>
     *
     */
    private final String gameDataJndiName;

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
     * Number of targets to generate for any particular slot.<br/>
     * This variable is initialized in the constructor and does not change after
     * that.<br/> Must be positive.
     *
     */
    private final int huntTargetsPerSlot;

    
    /**
     * Preferred length of generated targets.<br/>
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
     * The singleton HashAlgorithmManager, cached here for convenience
     */
    private final HashAlgorithmManager hashAlgManager;

    /**
     * A RandomStringImage onject for use generating images of text
     */
    private final RandomStringImage randomStringImage;

    /**
     * GameDataManager for regeneratepuzzle and brainteaser.
     */
    private GameDataManager gameDataManager = null;
    
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
    public AdministrationManager(String namespace) throws ConfigurationException {
        Helper.checkString(namespace, "namespace");
        
        // initialize other fields
        gameDataJndiName = Helper.getPropertyString(namespace, "game-data-jndi-name");
        minTargetLength = Helper.getPropertyInt(namespace, "MinTargetLength");
        maxTargetLength = Helper.getPropertyInt(namespace, "MaxTargetLength");
        huntTargetsPerSlot = Helper.getPropertyInt(namespace, "HuntTargetsPerSlot");
        preferredTargetLength = Helper.getPropertyInt(namespace, "PreferredTargetLength");
        minDistinctPages = Helper.getPropertyInt(namespace, "MinDistinctPages");
        maxDistinctPages = Helper.getPropertyInt(namespace, "MaxDistinctPages");
        preferredDistinctPages = Helper.getPropertyInt(namespace, "PreferredDistinctPages");
        objectFactory = Helper.getPropertyString(namespace, "objFactoryNS");

        try {
            hashAlgManager = HashAlgorithmManager.getInstance();
        } catch (com.topcoder.util.algorithm.hash.ConfigurationException ce) {
            throw new ConfigurationException("Could not obtain HashAlgorithmManager instance", ce);
        }

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
            throw new ConfigurationException("Could not obtain a RandomStringImage instance", ice);
        } catch (IOException ioe) {
            throw new ConfigurationException("Could not obtain a RandomStringImage instance", ioe);
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
        GameDataHome gameDataHome = (GameDataHome) sl.getRemoteHome(gameDataJndiName,
                GameDataHome.class);

        try {
            return gameDataHome.create();
        } catch (RemoteException e) {
            throw new AdministrationException("Failed to get GameData ejb instance",
                e);
        } catch (CreateException e) {
            throw new AdministrationException("Failed to get GameData ejb instance",
                e);
        }
    }

   

    /**
     * (Re)generates the mini-hunt targets for the specified hosting slot. It
     * will use the Web Site Statistics component to gather candidates for the
     * targets. It will base its selections in part on the number of distinct
     * pages on which candidates appear. The minimum, maximum, and preferred
     * number of pages will be configuration parameters. It will then record the
     * targets for the slot by recording updated slot information with the
     * GameData EJB.
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
     * regenerateBrainTeaser() and generateHuntTargets() for each slot id.
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

        // Create the GameData EJB instance
        GameData gameData = getGameData(sl);

        try {
            // Get the block
            HostingBlock block = gameData.getBlock(blockId);
            HostingSlot[] slots = block.getSlots();

            for (int i = 0; i < slots.length; i++) {
                long slotId = slots[i].getId().longValue();

                this.gameDataManager.regeneratePuzzle(slotId);
                generateHuntTargets(slotId, gameData);
                this.gameDataManager.regenerateBrainTeaser(slotId);
            }
        } catch (AdministrationException e) {
            // re-throw
            throw e;
        } catch (Exception e) {
            throw new AdministrationException("Failed to initialize slots for block.",
                e);
        }
    }

    

    

    /**
     * This helper method is called to actually generates the mini-hunt targets
     * for the specified hosting slot. It is called by the
     * generateHuntTargets(slotId) and initializeSlotsForBlock(blockId) methods.
     *
     * @param slotId
     *            slot id.
     * @param gameData
     *            GameData EJB instance.
     * @throws AdministrationException
     *             if an error such as RemoteException of GameDataException
     *             occurs.
     * @throws IllegalReferenceException 
     * @throws SpecificationConfigurationException 
     */
    private void generateHuntTargets(long slotId, GameData gameData)
        throws AdministrationException {
        
        try {
            // Get the hosting slot
            HostingSlot slot = gameData.getSlot(slotId);
            String domainName = slot.getDomain().getDomainName();
    
            DomainTarget[] domainTargets = generateDomainTargets(gameData,
                    domainName);
    
            // Create a new HostingSlotImpl instance
            HostingSlotImpl newSlot = Helper.copySlot(slot,
                    slot.getSequenceNumber());
            // Set the new domain targets
            newSlot.setDomainTargets(domainTargets);
            // Update slot
            gameData.updateSlots(new HostingSlot[] { newSlot });            
        } catch (RemoteException e) {
            throw new AdministrationException("Failed to generate hunt targets.", e);
        } catch (PersistenceException e) {
            throw new AdministrationException("Failed to generate hunt targets.", e);
        } catch (ObjectFactoryException e) {
            throw new AdministrationException("Failed to generate hunt targets.", e);
        } catch (StatisticsException e) {
            throw new AdministrationException("Failed to generate hunt targets.", e);
        } catch (HashException e) {
            throw new AdministrationException("Failed to generate hunt targets.", e);
        } catch (SpecificationFactoryException e) {
            throw new AdministrationException("Failed to generate hunt targets.", e);
        }         
    }

    /**
     * <p>
     * Generate hunt targets for specified domain.
     * </p>
     * @param gameData
     *      GameData EJB instance.
     * @param domainName domain name from which to crawl -- will be converted to a URL
     *        by prepending "http://" and appending "/"
     * @return domain targets generated.
     * @throws AdministrationException 
     * @throws StatisticsException 
     * @throws HashException 
     * @throws ObjectFactoryException
     * @throws SpecificationFactoryException 
     * @throws Exception - if fail to generate hunt targets for specified domain.
     */
    private DomainTarget[] generateDomainTargets(GameData gameData, String domainName) throws 
            ObjectFactoryException, AdministrationException, 
            StatisticsException, HashException, SpecificationFactoryException {
        // Spider the domain
        String baseUrl = "http://" + domainName + "/";
        WebCrawler crawler = new WebCrawler(new BreadthFirstCrawlStrategy());
        crawler.getStrategy().addAddress(new WebAddressContext(baseUrl, 0));
        crawler.setValidator(new AndValidator(
                RegExValidator.inclusionValidator(
                        "(?i)http://\\Q" + domainName + "\\E/.*"),
                new DepthValidator(MAX_SPIDERING_DEPTH)));

        List results = crawler.crawl();

        // use ObjectFactory to create an instance of SiteStatistics
        SiteStatistics siteStatistics = createSiteStatistics();

        // Feed the pages from crawler to site statistics
        Map docUrlMap = new HashMap();
        int idx = 0;

        // assign doc id to each document, index from 0.
        for (Iterator iter = results.iterator(); iter.hasNext(); idx++) {
            WebPageData data = (WebPageData) iter.next();

            // if malformed or exception when parsing.
            if (data.getException() != null) {
                continue;
            }

            // site statistics the page.
            if (data.getContentsAsString() != null) {
                String docId = "docID" + idx;
                siteStatistics.accumulateFrom(data.getContentsAsString(), docId);
                docUrlMap.put(docId, data.getAddressContext().getAddress());
            }
        }

        TextStatistics[] stats = siteStatistics.getElementContentStatistics();

        // Filter statistics by document count and target length boundaries
        stats = filterStatistics(stats);
        
        Random rd = new Random();
        int[] selected = new int[stats.length];
        for (int i = 0; i < selected.length; i++) {
            selected[i] = 0;
        }

        int numTargetsToGenerate = Math.min(huntTargetsPerSlot, stats.length);
        DomainTarget[] domainTargets = new DomainTarget[numTargetsToGenerate];
        Map targetsCountOnDoc = new HashMap();

        HashAlgorithm hasher = hashAlgManager.getAlgorithm("SHA-1");
                
        for (int cnt = 0; cnt < numTargetsToGenerate; cnt++) {
            boolean tryAnotherStat = false;
            int attempts = 10;
            
            // create a DomainTargetImpl instance
            DomainTargetImpl domainTarget = new DomainTargetImpl();
            
            do {
                // Choose textStatistics from result randomly, one textStat can be choosen once.
                // Quit if there are no more stats or if we try too many times without success.
                TextStatistics textStatistics = ((attempts-- > 0)
                            ? selectStatistics(selected, stats, rd, cnt)
                            : null);

                if (textStatistics == null) {
                    // No more targets can be obtained
                    DomainTarget[] domainTargets2 = new DomainTarget[cnt];
                    System.arraycopy(domainTargets, 0, domainTargets2, 0, cnt);
                    return domainTargets2;
                }
                
                String[] docs = textStatistics.getDocuments();
    
                // choose document id randomly which contains the text.
                final String docId = randomFindDocId(rd, docs, targetsCountOnDoc);
                
                domainTarget.setSequenceNumber(cnt);
                // get uri from doc id.
                domainTarget.setUriPath((String) docUrlMap.get(docId));
                domainTarget.setIdentifierText(textStatistics.getText());
                domainTarget.setIdentifierHash(hasher.hashToHexString(
                        textStatistics.getText().replaceAll("[\n\r \t\f\u200b]+", ""),
                        "UTF-8"));
                try {
                    domainTarget.setClueImageId(createClueImage(textStatistics.getText(), gameData));
                } catch (Exception e) {
                    tryAnotherStat = true;
                }
                
            } while (tryAnotherStat);
            
            domainTargets[cnt] = domainTarget;            
        }

        return domainTargets;
    }

    /**
     * Chooses from among the provided TextStatistics the ones that satisfy the configured criteria
     * for target length and number of pages
     * 
     * @param stats a <code>TextStatistics[]</code> containing the statistics to consider
     *
     * @return a <code>TextStatistics[]</code> containing those elements of <code>stats</code>
     *         that satisfy the criteria
     */
    private TextStatistics[] filterStatistics(TextStatistics[] stats) {
        List results = new ArrayList();

        for (int i = 0; i < stats.length; i++) {
            String[] documents = stats[i].getDocuments();        
            if ((documents.length >= minDistinctPages) && (documents.length <= maxDistinctPages) &&
                    (stats[i].getText().length() >= minTargetLength) && (stats[i].getText().length() <= maxTargetLength)) {
                results.add(stats[i]);
            }    
        }

        return (TextStatistics[]) results.toArray(new TextStatistics[results.size()]);      
    }

    /**
     * <p>
     * Find docId randomly on which to generate target. the targets appear on different
     * pages rather than on same pages.
     * </p>
     * @param rd random number.
     * @param docs document Id list.
     * @param targetsCountOnDoc map from docId to targets count.
     * @return docId with less targets.
     */
    private static String randomFindDocId(Random rd, String[] docs,
        Map targetsCountOnDoc) {
        String docIdMin = null;
        int countMin = Integer.MAX_VALUE;

        // find docId: no targets yet, return directly; otherwise find which contains smallest number of targets.
        for (int i = 0; i < docs.length; i++) {
            int chosenDocId = rd.nextInt(docs.length);
            String docId = docs[chosenDocId];

            if (!targetsCountOnDoc.containsKey(docId)) {
                targetsCountOnDoc.put(docId, new Integer(1));

                return docId;
            } else {
                int count = ((Integer) targetsCountOnDoc.get(docId)).intValue();

                if (countMin > count) {
                    countMin = count;
                    docIdMin = docId;
                }
            }
        }

        targetsCountOnDoc.put(docIdMin, new Integer(1 + countMin));

        return docIdMin;
    }

    /**
     * <p>
     * Create {@link SiteStatistics} from object factory.
     * </p>
     * @return created {@link SiteStatistics} object.
     * @throws AdministrationException 
     * @throws SpecificationFactoryException
     */
    private SiteStatistics createSiteStatistics() throws 
            ObjectFactoryException, AdministrationException, 
            SpecificationFactoryException {
        // Create an instance of ObjectFactory
        ObjectFactory of = new ObjectFactory(new ConfigManagerSpecificationFactory(
                objectFactory), ObjectFactory.BOTH);

        try {
            return (SiteStatistics) of.createObject("SiteStatistics");
        } catch (ClassCastException e) {
            throw new AdministrationException("Failed to create SiteStatistics instance via objectFactory.", e);
        }
    }

    /**
     * Creates an image representation of the specified text, records it as a
     * downloadable object, and returns the download object ID
     *
     * @param  imageText a String containing the text to be rendered as an image
     * @param  gameData a GameData with which to record the generated image
     *
     * @return the downloadable object ID of the generated image
     *
     * @throws AdministrationException if an error occurs while generating
     *         or recording the image
     */
    private long createClueImage(String imageText, GameData gameData)
        throws AdministrationException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        try {
            randomStringImage.generate(imageText, stream);
            return gameData.recordBinaryObject("clue_image.png", "image/png", stream.toByteArray());
        } catch (IOException ioe) {
            throw new AdministrationException("Could not generate clue image", ioe);
        } catch (ObfuscationException oe) {
            throw new AdministrationException("Could not generate clue image", oe);
        } catch (InvalidConfigException ice) {
            throw new AdministrationException("Could not generate clue image", ice);
        } catch (GameDataException gde) {
            throw new AdministrationException("Could not generate clue image", gde);
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
    private TextStatistics selectStatistics(int[] selected,
        TextStatistics[] stats, Random rd, int num) {
        int rdNum = rd.nextInt(stats.length - num);

        for (int i = 0; i < stats.length; i++) {
            if ((rdNum == 0) && (selected[i] == 0)) {
                selected[i] = 1;

                return stats[i];
            }

            if (selected[i] == 0) {
                rdNum--;
            }
        }

        return null;
    }

    /**
     * Return the GameDataManager instance.
     * @return the gameDataManager
     */
    public GameDataManager getGameDataManager() {
        return gameDataManager;
    }

    /**
     * Set the GameDataManager instance.
     * @param gameDataManager the gameDataManager to set
     */
    public void setGameDataManager(GameDataManager gameDataManager) {
        this.gameDataManager = gameDataManager;
    }
}
