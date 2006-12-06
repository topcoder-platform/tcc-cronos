/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.ejb;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataDAOFactory;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.InstantiationException;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.persistence.TestHelper;
import com.orpheus.game.persistence.entities.BallColorImpl;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.DomainTargetImpl;
import com.orpheus.game.persistence.entities.HostingBlockImplTests;
import com.orpheus.game.persistence.entities.HostingSlotImpl;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.puzzle.PuzzleData;

import com.topcoder.web.frontcontroller.results.DownloadData;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * <p>
 * Unit test case for class <code>GameDataBean</code>.  Here we MockEJB/MockRUnner to test ejb.
 * </p>
 * @author waits
 * @version 1.0
 */
public class GameDataBeanUnitTests extends TestCase {
    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** mock user trnasaction. */
    private MockUserTransaction mockTransaction;

    /** remote. */
    private GameData gameData = null;

    /** the ballColor lists. */
    private List colors = null;

    /** keyCount. */
    private int keyCount = 1;

    /** game startDate. */
    private Date gameStartDate = null;

    /** sponsorId. */
    private long sponsorId = 0;

    /** playerId. */
    private long playerId = 0;

    /**
     * Deploys and creates EJBs needed for our tests.
     */
    public void setUp() throws Exception {
        TestHelper.clearConfigManager();

        //prepared database/data
        List results = TestHelper.prepareDatabase();
        playerId = ((Long) results.get(0)).longValue();
        sponsorId = ((Long) results.get(1)).longValue();
        colors = (List) results.get(2);
        gameStartDate = new Date();

        //add config files
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.OBJECT_FACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.GAME_PERSISTENCE_CONFIG_FILE);

        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("game/GameDataBean",
                GameDataHome.class, GameData.class, new GameDataBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        //      Lookup the home
        Object gameDataHomeObj = context.lookup("game/GameDataBean");

        // PortableRemoteObject does not do anything in MockEJB but it does no harm to call it
        GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(gameDataHomeObj, GameDataHome.class);

        // create the bean
        gameData = gameDataHome.create();

        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }

    /**
     * Test the gameDataDao method, the required namespace does not exist, InstantiationException expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetGameDataDAO_namespaceNotExist() throws Exception {
        ConfigManager.getInstance().removeNamespace("com.orpheus.game.persistence.GameDataDAOFactory");

        try {
            GameDataDAOFactory.getGameDataDAO();
            fail("The namespace does not exist.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the gameDataDao method, the required property 'specNamespace' does not exist, InstantiationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetGameDataDAO_specNameSpacePropNotExist()
        throws Exception {
        ConfigManager.getInstance().removeNamespace("com.orpheus.game.persistence.GameDataDAOFactory");
        TestHelper.addConfigFile("invalidConfig/Factory_Config_missingSpecNamespace.xml");

        try {
            GameDataDAOFactory.getGameDataDAO();
            fail("The property 'specNamespace' does not exist.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the gameDataDao method, the required property 'specNamespace''s value does not exist,
     * InstantiationException expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetGameDataDAO_specNameSpacePropInvalid()
        throws Exception {
        ConfigManager.getInstance().removeNamespace("com.orpheus.game.persistence.GameDataDAOFactory");
        TestHelper.addConfigFile("invalidConfig/Factory_Config_notExistSpecNamespace.xml");

        try {
            GameDataDAOFactory.getGameDataDAO();
            fail("The property 'specNamespace' value does not exist.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the gameDataDao method, the required property 'gameDataDAO' does not exist, InstantiationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetGameDataDAO_gameDataDAOPropNotExist()
        throws Exception {
        ConfigManager.getInstance().removeNamespace("com.orpheus.game.persistence.GameDataDAOFactory");
        TestHelper.addConfigFile("invalidConfig/Factory_Config_missingAuctionDAO.xml");

        try {
            GameDataDAOFactory.getGameDataDAO();
            fail("The property 'gameDataDAO' does not exist.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the exception Hanlding of the ejb. Here we use the create game method as example.
     *
     * @throws Exception into Junit
     */
    public void testExceptionHanlding() throws Exception {
        /////
        //iae
        try {
            gameData.createGame(null);
            fail("The game to create is null.");
        } catch (RemoteException e) {
            assertTrue("IllegalArgumentException should be thrown.", e.detail instanceof IllegalArgumentException);
        }

        ///////
        ////remove the factory namespace,PersistenceException will be thrown
        ///
        ConfigManager.getInstance().removeNamespace("com.orpheus.game.persistence.GameDataDAOFactory");

        try {
            gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));
            fail("The factory namespace is removed.");
        } catch (PersistenceException e) {
            //good
        }

        /////////
        /// no namespace for the dao, 
        ///
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        ConfigManager.getInstance().removeNamespace(TestHelper.REMOTE_NAMESPACE);
        ConfigManager.getInstance().removeNamespace(TestHelper.LOCAL_NAMESPACE);

        //remove the SQLServerGameDataDAO namespace, PersistenceException expected.
        TestHelper.addConfigFile(TestHelper.GAME_PERSISTENCE_CONFIG_FILE);
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);

        try {
            gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));
            fail("The SQLServerGameDataDAO namespace is removed.");
        } catch (PersistenceException e) {
            //good
        }

        /////////
        //EntryNotFoundException
        //
        ConfigManager.getInstance().removeNamespace("com.orpheus.game.persistence.GameDataDAOFactory");
        ConfigManager.getInstance().removeNamespace(TestHelper.REMOTE_NAMESPACE);
        ConfigManager.getInstance().removeNamespace(TestHelper.LOCAL_NAMESPACE);
        TestHelper.addConfigFile(TestHelper.GAME_PERSISTENCE_CONFIG_FILE);

        BallColor color = ((BallColor) colors.get(1));
        BallColor toPersist = new BallColorImpl(new Long(color.getId().longValue() + 1), color.getName(),
                color.getImageId());

        try {
            gameData.createGame(TestHelper.getGameImplInstance(toPersist, keyCount, gameStartDate,
                    TestHelper.getBlocks()));
            fail("The game's color to persist does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * This is an accuracy test case for createGame(game).
     *
     * @throws Exception into Junit
     */
    public void testCreateGame() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));
        assertNotNull("The game is persisted.", game);
        assertEquals("The game.keyCount is not the same.", keyCount, game.getKeyCount());
        assertEquals("The game.startDate is not the same.", gameStartDate, game.getStartDate());
        assertNull("The game.endDate is not null.", game.getEndDate());
        assertEquals("The game.ballColor.imageId is not the same.", ((BallColor) colors.get(0)).getImageId(),
            game.getBallColor().getImageId());
        assertEquals("The size of the game.blocks is wrong.", 2, game.getBlocks().length);
        assertEquals("The game.block.perSlot is not the same.", blocks[0].getMaxHostingTimePerSlot(),
            game.getBlocks()[0].getMaxHostingTimePerSlot());
    }

    /**
     * <p>
     * An accuracy test case for addBlock method.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testAddBlock() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        HostingBlock block = gameData.addBlock(game.getId().longValue(),
                HostingBlockImplTests.MAX_HOSTING_TIME_PER_SLOT);
        assertEquals("The block's MAX_HOSTING_TIME_PER_SLOT  is not the one to persist.",
            HostingBlockImplTests.MAX_HOSTING_TIME_PER_SLOT, block.getMaxHostingTimePerSlot());
        assertEquals("The slot should be empty.", 0, block.getSlots().length);
    }

    /**
     * <p>
     * Test getBlock method, it is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetBlock() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        HostingBlock block = gameData.getBlock(game.getBlocks()[0].getId().longValue());
        assertEquals("The slot array is not empty.", 0, block.getSlots().length);
        assertEquals("The sequenceNumber is not the same.", game.getBlocks()[0].getSequenceNumber(),
            block.getSequenceNumber());
        assertEquals("The maxHostingTimePerSlotr is not the same.", game.getBlocks()[0].getMaxHostingTimePerSlot(),
            block.getMaxHostingTimePerSlot());
    }

    /**
     * <p>
     * Test the createDomain method , it is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateDomain() throws Exception {
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images);
        Domain persisted = gameData.createDomain(toPersist);
        assertEquals("The sponsorId is not the one set.", sponsorId, persisted.getSponsorId());
        assertEquals("The domain name is not the one set.", "domainName", persisted.getDomainName());
        assertEquals("The images length is wrong.", images.length, persisted.getImages().length);
    }

    /**
     * <p>
     * Test the getDomain(domainId), it is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetDomain() throws Exception {
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images);

        //persiste the domain
        Domain persisted = gameData.createDomain(toPersist);

        //get the domain
        Domain getValue = gameData.getDomain(persisted.getId().longValue());

        //verify the result
        assertEquals("The domain Name is not the one saved.", "domainName", getValue.getDomainName());
        assertEquals("The domain.sponsorId is not the one saved.", sponsorId, getValue.getSponsorId());
        assertEquals("The domain.images's size is invalid.", images.length, getValue.getImages().length);
        assertEquals("the domain.images.0.downloadId is invalid.", persisted.getImages()[0].getDownloadId(),
            getValue.getImages()[0].getDownloadId());
    }

    /**
     * <p>
     * Test the updateDomain(domain),it is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testuUpdateDomain() throws Exception {
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images);

        //persiste the domain
        Domain persisted = gameData.createDomain(toPersist);

        //create a domain with the persited id and two new images
        Domain toUpdate = new DomainImpl(persisted.getId(), sponsorId, "domainName", new Boolean(false), images);

        //update the domain
        gameData.updateDomain(toUpdate);

        //get the domain
        Domain updated = gameData.getDomain(toUpdate.getId().longValue());

        //verify 
        assertFalse("The approved does not changed.", updated.isApproved().booleanValue());
        assertEquals("The size of image should be four.", 4, updated.getImages().length);
    }

    /**
     * <p>
     * Test createSlots(blockId,bidIds) method,  it is an accuracy case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateSlot() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();

        //persist the game and the block
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        //persist the domain
        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        //create slots
        HostingSlot[] slots = gameData.createSlots(blockId, bidIds);

        //verify
        assertEquals("The size of slots is incorrect.", 1, slots.length);
        assertEquals("The domain does not match.", persisted.getDomainName(), slots[0].getDomain().getDomainName());
        assertEquals("The domainTarget is not empty.", 0, slots[0].getDomainTargets().length);
        assertEquals("The brainTeaserIds is not empty.", 0, slots[0].getBrainTeaserIds().length);
        assertEquals("The image id does not match.", imageId, slots[0].getImageId());
    }

    /**
     * <p>
     * Test the getSlot method, it is a accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetSlot() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    images));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        HostingSlot slot = gameData.getSlot(slotId);

        //verify slot
        assertEquals("The brntsrIds length is wrong.", 1, slot.getBrainTeaserIds().length);
        assertEquals("The brntsrIds is not the one to set.", puzzleId, slot.getBrainTeaserIds()[0]);
        assertNotNull("The hostingStart is not the same.", slot.getHostingStart());
        assertNull("The hostingEnd date is null.", slot.getHostingEnd());
        assertEquals("The sequenceNumber is wrong.", sequenceNumber, slot.getSequenceNumber());
        assertEquals("The puzzleId is not the same.", puzzleId, slot.getPuzzleId().longValue());
        assertEquals("The winningBid is the one to set.", 2, slot.getWinningBid());
        assertEquals("The imageId is not the one to set.", imageId, slot.getImageId());

        //verify the domain
        assertEquals("The domain. sponsorId is not the one set.", sponsorId, slot.getDomain().getSponsorId());

        //verify the domainTarget
        assertEquals("The size of domainTarget is wrong.", 1, slot.getDomainTargets().length);
        assertEquals("The uriPath is wrong.", "path", slot.getDomainTargets()[0].getUriPath());
        assertEquals("The text is wrong.", "text", slot.getDomainTargets()[0].getIdentifierText());
        assertEquals("The hash is wrong.", "hash", slot.getDomainTargets()[0].getIdentifierHash());
        assertEquals("The downloadId is not the one set.", downloadId, slot.getDomainTargets()[0].getClueImageId());
    }

    /**
     * <p>
     * test the findCompletedSlots method, it is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindCompletedSlots() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));
        HostingSlot[] slots = gameData.findCompletedSlots(game.getId().longValue());
        assertEquals("The slots is not empty.", 0, slots.length);

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    images));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        slots = gameData.findCompletedSlots(game.getId().longValue());
        assertEquals("The slots is not empty.", 0, slots.length);
        //record the slot completion
        gameData.recordSlotCompletion(playerId, slotId, new Date());

        //find again
        slots = gameData.findCompletedSlots(game.getId().longValue());
        assertEquals("The slots is not empty.", 1, slots.length);
    }

    /**
     * <p>
     * Test findSlotCompletions, it is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testFindSlotCompletions() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    images));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        //find completions
        SlotCompletion[] completions = gameData.findSlotCompletions(game.getId().longValue(), slotId);
        assertEquals("The size of the completions is not null.", 0, completions.length);

        gameData.recordSlotCompletion(playerId, slotId, new Date());
        completions = gameData.findSlotCompletions(game.getId().longValue(), slotId);
        assertEquals("The size of the completions is null.", 1, completions.length);
    }

    /**
     * test the deleteSlot method. it is an accuracy test case.
     *
     * @throws Exception into Junit
     */
    public void testDeleteSlot() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    images));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        gameData.deleteSlot(slotId);

        try {
            gameData.getSlot(slotId);
            fail("The slot does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * test updateSlots(HostingSlot[] slots) method, it is an accuracy test case.
     *
     * @throws Exception into Junit
     */
    public void testUpateSlot() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    images));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        //get slot
        HostingSlot slot = gameData.getSlot(slotId);

        DomainTarget[] targets = slot.getDomainTargets();
        targets[0] = new DomainTargetImpl(null, targets[0].getSequenceNumber(), targets[0].getUriPath(),
                targets[0].getIdentifierText(), targets[0].getIdentifierHash(), targets[0].getClueImageId());

        //create a slot to be updated
        HostingSlot toUpdate = new HostingSlotImpl(slot.getId(), slot.getDomain(), slot.getImageId(),
                slot.getBrainTeaserIds(), slot.getPuzzleId(), slot.getSequenceNumber() + 1, targets,
                slot.getWinningBid(), slot.getHostingStart(), slot.getHostingEnd());

        //update the slot
        HostingSlot[] updated = gameData.updateSlots(new HostingSlot[] { toUpdate });

        //verify
        assertEquals("The sequenceNumber is wrong.", sequenceNumber + 1, updated[0].getSequenceNumber());
    }

    /**
     * <p>
     * Test the getGame method,  it is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetGame() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    images));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        //get game instance
        Game toGet = gameData.getGame(game.getId().longValue());

        //verify the game's attribute
        assertEquals("The game's keyCount is not the same.", keyCount, toGet.getKeyCount());
        assertNull("The endDate is not null.", toGet.getEndDate());
        assertEquals("The ballColor's name is not the same.", game.getBallColor().getName(),
            toGet.getBallColor().getName());

        HostingBlock[] getBlocks = toGet.getBlocks();
        assertEquals("The size of blocks of the game is invalid.", 2, getBlocks.length);

        for (int i = 0; i < getBlocks.length; i++) {
            if (getBlocks[0].getId().longValue() == blockId) {
                HostingSlot slot = getBlocks[0].getSlots()[0];
                //verify slot
                assertEquals("The brntsrIds length is wrong.", 1, slot.getBrainTeaserIds().length);
                assertEquals("The brntsrIds is not the one to set.", puzzleId, slot.getBrainTeaserIds()[0]);
                assertNotNull("The hostingStart is not the same.", slot.getHostingStart());
                assertNull("The hostingEnd date is null.", slot.getHostingEnd());
                assertEquals("The sequenceNumber is wrong.", sequenceNumber, slot.getSequenceNumber());
                assertEquals("The puzzleId is not the same.", puzzleId, slot.getPuzzleId().longValue());
                assertEquals("The winningBid is the one to set.", 2, slot.getWinningBid());
                assertEquals("The imageId is not the one to set.", imageId, slot.getImageId());

                //verify the domain
                assertEquals("The domain. sponsorId is not the one set.", sponsorId, slot.getDomain().getSponsorId());

                //verify the domainTarget
                assertEquals("The size of domainTarget is wrong.", 1, slot.getDomainTargets().length);
                assertEquals("The uriPath is wrong.", "path", slot.getDomainTargets()[0].getUriPath());
                assertEquals("The text is wrong.", "text", slot.getDomainTargets()[0].getIdentifierText());
                assertEquals("The hash is wrong.", "hash", slot.getDomainTargets()[0].getIdentifierHash());
                assertEquals("The downloadId is not the one set.", downloadId,
                    slot.getDomainTargets()[0].getClueImageId());
            }
        }
    }

    /**
     * <p>
     * Test the getDownloadData method, it is an accuracy test case.
     * </p>
     *
     * @throws Exception
     */
    public void testGetDownloadData() throws Exception {
        DownloadData data = gameData.getDownloadData(((BallColor) this.colors.get(0)).getImageId());
        assertNotNull("The data is null.", data);
        assertEquals("The mediaType is not the same.", "html/text", data.getMediaType());
        assertEquals("The name is not the same.", "html", data.getSuggestedName());

        InputStream stream = data.getContent();
        byte[] out = new byte["This is the content.".getBytes().length];
        stream.read(out);
        assertEquals("The content is not the same.", "This is the content.", new String(out));
    }

    /**
     * <p>
     * Test getPuzzle(puzzleId) method, it is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetPuzzle() throws Exception {
        long puzzleId = TestHelper.persistPuzzle(((BallColor) this.colors.get(0)).getImageId(), "puzzleName");

        PuzzleData data = gameData.getPuzzle(puzzleId);
        assertEquals("The data's name is invalid.", "puzzleName", data.getName());
        assertEquals("The attribute size is invalid.", 2, data.getAllAttributes().size());
        assertEquals("The attribute att1 does not exist.", "value1", data.getAttribute("att1"));
        assertEquals("The resource size is invalid.", 1, data.getAllResources().size());
        assertEquals("The resource'name is invalid.", "html", data.getResource("resource1").getName());
    }

    /**
     * <p>
     * Test getKeysForPlayer(playerId, slotIds), it is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetKeysForPlayer() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    images));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        //get the keys
        String[] keyTexts = gameData.getKeysForPlayer(playerId, new long[] { slotId });

        //verify
        assertEquals("The size of keyText now is 1.", 1, keyTexts.length);
        assertNull("The keyTexts should be null.", keyTexts[0]);

        //persist the complet slot for player
    }

    /**
     * <p>
     * Test recordSlotCompletion(playerId, slotId, date),it is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordSlotCompletion() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    images));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        //record the slot completion
        SlotCompletion slotCompltd = gameData.recordSlotCompletion(playerId, slotId, new Date());
        assertEquals("The slotId is not the same.", slotId, slotCompltd.getSlotId());
        assertEquals("The playerId is not the same.", playerId, slotCompltd.getPlayerId());
    }

    /**
     * test the recordGameCompletion(playerId, gameId) method, it is an accuracy test case.
     *
     * @throws Exception into JUnit
     */
    public void testRecordGameCompletion() throws Exception {
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, TestHelper.getBlocks()));
        gameData.recordGameCompletion(playerId, game.getId().longValue());
    }

    /**
     * test the recordRegistration(playerId, gameId) method, accuracy test case.
     *
     * @throws Exception into JUnit
     */
    public void testRecordRegistration() throws Exception {
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, TestHelper.getBlocks()));
        gameData.recordRegistration(playerId, game.getId().longValue());
    }

    /**
     * <p>
     * Test the recordPluginDownload method, accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordPluginDownload() throws Exception {
        TestHelper.persistPluginDownload("pluginName");
        gameData.recordPluginDownload("pluginName");
    }

    /**
     * <p>
     * Test the findAllBallColors method. it is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindAllBallColors() throws Exception {
        BallColor[] ballColors = gameData.findAllBallColors();
        assertEquals("The size of ballColor is wrong.", 2, ballColors.length);
    }

    /**
     * <p>
     * Test findSlotForDomain(long gameId, long playerId, String domain) method, it is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindSlotForDomain() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    images));

        //get domain now, no slot can be found
        assertNull("now no hosting slot can be found.",
            gameData.findSlotForDomain(game.getId().longValue(), playerId, "domainName"));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        //find the slot for domain
        HostingSlot slot = gameData.findSlotForDomain(game.getId().longValue(), playerId, "domainName");

        assertEquals("The brntsrIds length is wrong.", 1, slot.getBrainTeaserIds().length);
        assertEquals("The brntsrIds is not the one to set.", puzzleId, slot.getBrainTeaserIds()[0]);
        assertNotNull("The hostingStart is not the same.", slot.getHostingStart());
        assertNull("The hostingEnd date is null.", slot.getHostingEnd());
        assertEquals("The sequenceNumber is wrong.", sequenceNumber, slot.getSequenceNumber());
        assertEquals("The puzzleId is not the same.", puzzleId, slot.getPuzzleId().longValue());
        assertEquals("The winningBid is the one to set.", 2, slot.getWinningBid());
        assertEquals("The imageId is not the one to set.", imageId, slot.getImageId());

        //complete the slot
        gameData.recordSlotCompletion(playerId, slotId, new Date());

        assertNull("now no hosting slot can be found.",
            gameData.findSlotForDomain(game.getId().longValue(), playerId, "domainName"));
    }

    /**
     * <p>
     * Test the findDomainsForSponsor method , it is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindDomainsForSponsor() throws Exception {
        Domain[] domains = gameData.findDomainsForSponsor(sponsorId);
        assertEquals("The domains's length is not zero.", 0, domains.length);

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images));
        domains = gameData.findDomainsForSponsor(sponsorId);

        assertEquals("The size of domain's length is invalid.", 1, domains.length);
        assertEquals("The sponsorId is not the one set.", sponsorId, domains[0].getSponsorId());
        assertEquals("The domain name is not the one set.", "domainName", domains[0].getDomainName());
        assertEquals("The images length is wrong.", images.length, domains[0].getImages().length);
    }

    /**
     * Test the findGameRegistrations method , it is an accuracy test case.
     *
     * @throws Exception into JUnit
     */
    public void testFindGameRegistrations() throws Exception {
        long[] gameIds = gameData.findGameRegistrations(playerId);
        assertEquals("The size of the gameIds array is zero.", 0, gameIds.length);

        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        //complete the game
        gameData.recordRegistration(playerId, game.getId().longValue());

        //find the game Complete again
        gameIds = gameData.findGameRegistrations(playerId);
        assertEquals("The size of the gameIds is invalid.", 1, gameIds.length);
        assertEquals("The game id is not the one we need.", game.getId().longValue(), gameIds[0]);
    }

    /**
     * Test findGames(isStarted,isEnded). accuracy test case.
     *
     * @throws Exception into JUnit
     */
    public void testFindGames() throws Exception {
        //persist a game
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, TestHelper.getBlocks()));
        assertNotNull("The startDate is null.", game.getStartDate());
        assertNull("The endDate is not null.", game.getEndDate());

        //all games
        Game[] games = gameData.findGames(null, null);
        assertEquals("The size of games is zero.", 1, games.length);

        //end games
        games = gameData.findGames(null, new Boolean(true));
        assertEquals("The size of games is zero.", 0, games.length);

        //not end games
        games = gameData.findGames(null, new Boolean(false));
        assertEquals("The size of games is zero.", 1, games.length);

        //start games
        games = gameData.findGames(new Boolean(true), null);
        assertEquals("The size of games is zero.", 1, games.length);

        //not start games
        games = gameData.findGames(new Boolean(false), null);
        assertEquals("The size of games is zero.", 0, games.length);
        games = gameData.findGames(new Boolean(false), new Boolean(false));
        assertEquals("The size of games is zero.", 0, games.length);

        //start but not end games
        games = gameData.findGames(new Boolean(true), new Boolean(false));
        assertEquals("The size of games is zero.", 1, games.length);

        //start and end games
        games = gameData.findGames(new Boolean(true), new Boolean(true));
        assertEquals("The size of games is zero.", 0, games.length);
    }

    /**
     * <p>
     * Test the findGamesByDomain method, it is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindGamesByDomain() throws Exception {
        //persist the domain
        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        Game[] games = gameData.findGamesByDomain("domainName", playerId);
        assertEquals("The size of game is incorrent.", 0, games.length);

        //create game
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, TestHelper.getBlocks()));
        //find again
        games = gameData.findGamesByDomain("domainName", playerId);
        assertEquals("The size of game is incorrent.", 0, games.length);

        //      the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        //find again
        games = gameData.findGamesByDomain("domainName", playerId);
        assertEquals("The size of game is incorrent.", 1, games.length);

        gameData.recordSlotCompletion(playerId, slotId, new Date());

        games = gameData.findGamesByDomain("domainName", playerId);
        assertEquals("The size of game is incorrent.", 0, games.length);
    }

    /**
     * <p>
     * Test the findActiveDomains method, it is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindActiveDomains() throws Exception {
        Domain[] domains = gameData.findActiveDomains();
        assertEquals("The domain array is not empty.", 0, domains.length);

        //persist the domain
        Domain persisted = gameData.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));
        Game game = gameData.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, TestHelper.getBlocks()));

        //      the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        long downloadId = ((BallColor) this.colors.get(0)).getImageId();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        //find again
        domains = gameData.findActiveDomains();
        assertEquals("The domain array is empty.", 1, domains.length);
    }

    /**
     * remove the environment.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.clearDatabase();
    }
}
