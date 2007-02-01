/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.dao;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.DuplicateEntryException;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameDataDAO;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.InvalidEntryException;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.persistence.TestHelper;
import com.orpheus.game.persistence.entities.BallColorImpl;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.DomainTargetImpl;
import com.orpheus.game.persistence.entities.HostingBlockImplTests;
import com.orpheus.game.persistence.entities.HostingSlotImpl;
import com.orpheus.game.persistence.entities.ImageInfoImpl;

import com.topcoder.util.puzzle.PuzzleData;

import com.topcoder.web.frontcontroller.results.DownloadData;

import junit.framework.TestCase;

import java.io.InputStream;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * Unit Test case for the class <code>SQLServerGameDataDAO</code>.
 * </p>
 * @author waits
 * @version 1.0
 */
public class SQLServerGameDataDAOUnitTests extends TestCase {
    /** SQLServerGameDataDAO instance to test against. */
    private GameDataDAO dao = null;

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

    private List downloadIds = null;
    /**
     * create instance.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();

        List results = TestHelper.prepareDatabase();
        playerId = ((Long) results.get(0)).longValue();
        sponsorId = ((Long) results.get(1)).longValue();
        colors = (List) results.get(2);
        this.downloadIds = (List) results.get(3);
        
        gameStartDate = new Date();

        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.OBJECT_FACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.GAME_PERSISTENCE_CONFIG_FILE);

        dao = new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
    }

    /**
     * clear the environement.
     *
     * @throws Exception into JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.clearDatabase();
    }

    /**
     * <p>
     * Test the createGame(game) method, the game is null, iae expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateGame_nullGame() throws Exception {
        try {
            dao.createGame(null);
            fail("The game to create is null.");
        } catch (IllegalArgumentException e) {
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));
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
     * Test the createGame(game) method, the game to persist is a duplicated one, DuplicateEntryException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateGame_duplicateGame() throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));

        try {
            dao.createGame(game);
            fail("The game to persist is a duplicated one.");
        } catch (DuplicateEntryException e) {
            //good
        }
    }

    /**
     * <p>
     * Test createGame(game) method, game's ballColor to persist does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateGame_notExistBallColor() throws Exception {
        BallColor color = ((BallColor) colors.get(1));
        BallColor toPersist = new BallColorImpl(new Long(color.getId().longValue() + 1), color.getName(),
                color.getImageId());

        try {
            dao.createGame(TestHelper.getGameImplInstance(toPersist, keyCount, gameStartDate, TestHelper.getBlocks()));
            fail("The game's color to persist does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        HostingBlock block = dao.addBlock(game.getId().longValue(), HostingBlockImplTests.MAX_HOSTING_TIME_PER_SLOT);
        assertEquals("The block's MAX_HOSTING_TIME_PER_SLOT  is not the one to persist.",
            HostingBlockImplTests.MAX_HOSTING_TIME_PER_SLOT, block.getMaxHostingTimePerSlot());
        assertEquals("The slot should be empty.", 0, block.getSlots().length);
    }

    /**
     * <p>
     * Test the addBlock method, the gameId which the block adding to does not exist, EntryNotFoundException
     * expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testAddBlock_gameNotExist() throws Exception {
        try {
            dao.addBlock(1, HostingBlockImplTests.MAX_HOSTING_TIME_PER_SLOT);
            fail("The game the block to add does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getBlock method, the blockId does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetBlock_notExistBlock() throws Exception {
        try {
            dao.getBlock(1);
            fail("The block does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        HostingBlock block = dao.getBlock(game.getBlocks()[0].getId().longValue());
        assertEquals("The slot array is not empty.", 0, block.getSlots().length);
        assertEquals("The sequenceNumber is not the same.", game.getBlocks()[0].getSequenceNumber(),
            block.getSequenceNumber());
        assertEquals("The maxHostingTimePerSlotr is not the same.", game.getBlocks()[0].getMaxHostingTimePerSlot(),
            block.getMaxHostingTimePerSlot());
    }

    /**
     * <p>
     * Test the createDomain method ,the domain is null, iae expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateDomain_nullDomain() throws Exception {
        try {
            dao.createDomain(null);
            fail("The domain to create is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
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
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images);
        Domain persisted = dao.createDomain(toPersist);
        assertEquals("The sponsorId is not the one set.", sponsorId, persisted.getSponsorId());
        assertEquals("The domain name is not the one set.", "domainName2", persisted.getDomainName());
        assertEquals("The images length is wrong.", images.length, persisted.getImages().length);
    }

    /**
     * <p>
     * Test the createDomain method, the domain already exist,DuplicateEntryException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateDomain_duplicatedDomain() throws Exception {
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images);
        Domain persisted = dao.createDomain(toPersist);

        try {
            dao.createDomain(persisted);
            fail("The domain to persiste already exist.");
        } catch (DuplicateEntryException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the createDomain method, the domain's sponsorId does not exist,EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateDomain_notExistSponsor() throws Exception {
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain toPersist = TestHelper.getDomain(sponsorId + 1, "domainName2", new Boolean(true), images);

        try {
            dao.createDomain(toPersist);
            fail("The domain's sponsorId does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the createDomain method, the domain's image array has duplicated items,DuplicateEntryException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateDomain_imageAlreadyExist() throws Exception {
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images));

        try {
            dao.createDomain(TestHelper.getDomain(sponsorId, "domainName3", new Boolean(true), persisted.getImages()));
            fail("The domain's image array has duplicated item.");
        } catch (DuplicateEntryException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the getDomain(domainId), the domain id does not exist,EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetDomain_notExistDomain() throws Exception {
        try {
            dao.getDomain(1);
            fail("The domain id does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images);

        //persiste the domain
        Domain persisted = dao.createDomain(toPersist);

        //get the domain
        Domain getValue = dao.getDomain(persisted.getId().longValue());

        //verify the result
        assertEquals("The domain Name is not the one saved.", "domainName2", getValue.getDomainName());
        assertEquals("The domain.sponsorId is not the one saved.", sponsorId, getValue.getSponsorId());
        assertEquals("The domain.images's size is invalid.", images.length, getValue.getImages().length);
        assertEquals("the domain.images.0.downloadId is invalid.", persisted.getImages()[0].getDownloadId(),
            getValue.getImages()[0].getDownloadId());
    }

    /**
     * <p>
     * Test the updateDomain(domain), the value is null ,iae expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testuUpdateDomain_nullDomain() throws Exception {
        try {
            dao.updateDomain(null);
            fail("The domain to update is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the updateDomain(domain), the domain's Id is null,iae expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testuUpdateDomain_nullDomainId() throws Exception {
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images);

        try {
            dao.updateDomain(toPersist);
            fail("The domain's id  to update is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the updateDomain(domain), the domain to update does not exist,EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testuUpdateDomain_notExistDomain() throws Exception {
        //persist a domain first
        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true),
                    TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        //create a domain with a not exist id
        Domain notExist = new DomainImpl(new Long(persisted.getId().longValue() + 1), sponsorId, "domainName3",
                new Boolean(true), persisted.getImages());

        try {
            dao.updateDomain(notExist);
            fail("The domain to update does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the updateDomain(domain), the domain's sponsorId to update does not exist,EntryNotFoundException
     * expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testuUpdateDomain_notExistDomainSponsor()
        throws Exception {
        //persist a domain first
        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true),
                    TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        //create a domain with a not exist sponsorId
        Domain notExist = new DomainImpl(persisted.getId(), sponsorId + 1, "domainName3", new Boolean(true),
                persisted.getImages());

        try {
            dao.updateDomain(notExist);
            fail("The domain's sponsorId to update does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the updateDomain(domain), the domain's image's downloadId to update does not exist,EntryNotFoundException
     * expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testuUpdateDomain_notExistDomainImageDownloadId()
        throws Exception {
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images);

        //persiste the domain
        Domain persisted = dao.createDomain(toPersist);
        //create a image with not exist download id
        images[0] = new ImageInfoImpl(null, images[1].getDownloadId() + images[0].getDownloadId(),
                images[0].getDescription(), images[0].isApproved());

        //create a domain with a not exist downloadId
        Domain notExist = new DomainImpl(persisted.getId(), sponsorId, "domainName3", new Boolean(true), images);

        try {
            dao.updateDomain(notExist);
            fail("The domain's image's downloadId to update does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the updateDomain(domain), the domain's image to update does not exist,EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testuUpdateDomain_notExistDomainImage()
        throws Exception {
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images);

        //persiste the domain
        Domain persisted = dao.createDomain(toPersist);
        ImageInfo[] persitedImages = persisted.getImages();

        //create a image with not exist imageId
        images[0] = new ImageInfoImpl(new Long(persitedImages[0].getId().longValue() +
                    persitedImages[1].getId().longValue()), images[0].getDownloadId(), images[0].getDescription(),
                images[0].isApproved());

        //create a domain with a not exist image
        Domain notExist = new DomainImpl(persisted.getId(), sponsorId, "domainName3", new Boolean(true), images);

        try {
            dao.updateDomain(notExist);
            fail("The domain's image to update does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images);

        //persiste the domain
        Domain persisted = dao.createDomain(toPersist);

        //create a domain with the persited id and two new images
        Domain toUpdate = new DomainImpl(persisted.getId(), sponsorId, "domainName3", null, images);

        //update the domain
        dao.updateDomain(toUpdate);

        //get the domain
        Domain updated = dao.getDomain(toUpdate.getId().longValue());

        //verify 
        assertNull("The approved does not changed.", updated.isApproved());
        assertEquals("The size of image should be four.", 4, updated.getImages().length);
    }

    /**
     * <p>
     * Test createSlots(blockId,bidIds) method, the Bidids is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateSlots_nullBidIds() throws Exception {
        try {
            dao.createSlots(1L, null);
            fail("The bidIds to persist is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test createSlots(blockId,bidIds) method, the block id does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateSlots_notExistBlockId() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();

        //persist the game and the block
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true),
                    TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        //create a not exist block id
        long blockId = game.getBlocks()[0].getId().longValue() + game.getBlocks()[1].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(game.getBlocks()[0].getId().longValue(), imageId, 1, new Date());

        try {
            dao.createSlots(blockId, bidIds);
            fail("The blockId does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test createSlots(blockId,bidIds) method, the bid id does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateSlots_notExistBidId() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();

        //persist the game and the block
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        try {
            dao.createSlots(game.getBlocks()[0].getId().longValue(), new long[] { 1, 2 });
            fail("The bid Id does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test createSlots(blockId,bidIds) method, The blockId does not match the one the bids refers to,
     * InvalidEntryException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateSlots_notMatchBlock() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();

        //persist the game and the block
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true),
                    TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        try {
            dao.createSlots(game.getBlocks()[1].getId().longValue(), bidIds);
            fail("The blockId does not match the one the bids refers to.");
        } catch (InvalidEntryException e) {
            //good
        }
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true),
                    TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        //create slots
        HostingSlot[] slots = dao.createSlots(blockId, bidIds);

        //verify
        assertEquals("The size of slots is incorrect.", 1, slots.length);
        assertEquals("The domain does not match.", persisted.getDomainName(), slots[0].getDomain().getDomainName());
        assertEquals("The domainTarget is not empty.", 0, slots[0].getDomainTargets().length);
        assertEquals("The brainTeaserIds is not empty.", 0, slots[0].getBrainTeaserIds().length);
        assertEquals("The image id does not match.", imageId, slots[0].getImageId());
        assertNotNull("The id is not null.", slots[0].getId());
        
        List ids = TestHelper.getIds("hosting_slot");
        assertEquals("The size in db in incorrect.", 1, ids.size());
        assertEquals("The size in db in incorrect.", slots[0].getId().longValue(), ((Long)ids.get(0)).longValue());
    }

    /**
     * <p>
     * Test the getSlot method, the slotid does not exist,EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetSlot_notExistSlot() throws Exception {
        try {
            this.dao.getSlot(1);
            fail("The slot does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images));

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

        HostingSlot slot = dao.getSlot(slotId);

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
     * test the findCompletedSlots method, the game does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindCompletedSlots_notExistGame() throws Exception {
        try {
            dao.findCompletedSlots(1);
            fail("The game does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));
        HostingSlot[] slots = dao.findCompletedSlots(game.getId().longValue());
        assertEquals("The slots is not empty.", 0, slots.length);

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images));

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

        slots = dao.findCompletedSlots(game.getId().longValue());
        assertEquals("The slots is not empty.", 0, slots.length);
        //record the slot completion
        dao.recordSlotCompletion(playerId, slotId, new Date());

        //find again
        slots = dao.findCompletedSlots(game.getId().longValue());
        assertEquals("The slots is not empty.", 1, slots.length);
    }

    /**
     * <p>
     * Test findSlotCompletions method(gameId, slotId), the game does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindSlotCompletions_NotExistGame()
        throws Exception {
        try {
            dao.findSlotCompletions(1, 1);
            fail("The game does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test findSlotCompletions method(gameId, slotId), the slot does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindSlotCompletions_NotExistSlot()
        throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        try {
            dao.findSlotCompletions(game.getId().longValue(), 1);
            fail("The slot does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test findSlotCompletions method(gameId, slotId), the slot is not indicated by the gameId,
     * InvalidEntryException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindSlotCompletions_notMatch() throws Exception {
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images));

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

        Game game2 = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount,
                    gameStartDate, blocks));

        try {
            dao.findSlotCompletions(game2.getId().longValue(), slotId);
            fail("The slot and the game id does not match.");
        } catch (InvalidEntryException e) {
            //good
        }
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images));

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
        SlotCompletion[] completions = dao.findSlotCompletions(game.getId().longValue(), slotId);
        assertEquals("The size of the completions is not null.", 0, completions.length);

        dao.recordSlotCompletion(playerId, slotId, new Date());
        completions = dao.findSlotCompletions(game.getId().longValue(), slotId);
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images));

        //the persisted block id
        long blockId = game.getBlocks()[0].getId().longValue();

        //the persisted image id
        long imageId = persisted.getImages()[0].getId().longValue();

        //persist a bid record for testing
        long[] bidIds = TestHelper.persistBid(blockId, imageId, 1, new Date());

        
        long downloadId = ((Long)downloadIds.get(0)).longValue();

        //persist the puzzle
        long puzzleId = TestHelper.persistPuzzle(downloadId, "puzzleName");

        //persist the slot
        int sequenceNumber = 1;
        Date hostingStart = new Date();
        long slotId = TestHelper.persistSlot(bidIds[0], sequenceNumber, hostingStart, puzzleId, downloadId);

        dao.deleteSlot(slotId);

        try {
            dao.getSlot(slotId);
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images));

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
        HostingSlot slot = dao.getSlot(slotId);

        DomainTarget[] targets = slot.getDomainTargets();
        targets[0] = new DomainTargetImpl(null, targets[0].getSequenceNumber(), targets[0].getUriPath(),
                targets[0].getIdentifierText(), targets[0].getIdentifierHash(), targets[0].getClueImageId());

        //create a slot to be updated
        HostingSlot toUpdate = new HostingSlotImpl(slot.getId(), slot.getDomain(), slot.getImageId(),
                slot.getBrainTeaserIds(), slot.getPuzzleId(), slot.getSequenceNumber() + 1, targets,
                slot.getWinningBid(), slot.getHostingStart(), slot.getHostingEnd());

        //update the slot
        HostingSlot[] updated = dao.updateSlots(new HostingSlot[] { toUpdate });

        //verify
        assertEquals("The sequenceNumber is wrong.", sequenceNumber + 1, updated[0].getSequenceNumber());
    }

    /**
     * test updateSlots(HostingSlot[] slots) method, the slots does not exist,EntryNotFoundException expected.
     *
     * @throws Exception into Junit
     */
    public void testUpateSlot_notExistSlot() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images));

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
        HostingSlot slot = dao.getSlot(slotId);

        //create a not exist slot
        HostingSlot notExist = new HostingSlotImpl(new Long(slot.getId().longValue() + 1), slot.getDomain(), slot.getImageId(),
                slot.getBrainTeaserIds(), slot.getPuzzleId(), slot.getSequenceNumber(), slot.getDomainTargets(),
                slot.getWinningBid(), slot.getHostingStart(), slot.getHostingEnd());

        try {
            dao.updateSlots(new HostingSlot[] { notExist });
            fail("The slot to update does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * test updateSlots(HostingSlot[] slots) method, the domain target does not exist,EntryNotFoundException
     * expected.
     *
     * @throws Exception into Junit
     */
    public void testUpateSlot_notExistDomainTarget() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images));

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
        HostingSlot slot = dao.getSlot(slotId);

        //create a not exist DomainTarget
        DomainTarget[] targets = slot.getDomainTargets();
        targets[0] = new DomainTargetImpl(new Long(targets[0].getId().longValue() + 1), targets[0].getSequenceNumber(),
                targets[0].getUriPath(), targets[0].getIdentifierText(), targets[0].getIdentifierHash(),
                targets[0].getClueImageId());

        //create a not exist slot
        HostingSlot notExist = new HostingSlotImpl(slot.getId(), slot.getDomain(), slot.getImageId(),
                slot.getBrainTeaserIds(), slot.getPuzzleId(), slot.getSequenceNumber(), targets,
                slot.getWinningBid(), slot.getHostingStart(), slot.getHostingEnd());

        try {
            dao.updateSlots(new HostingSlot[] { notExist });
            fail("The domain target to update does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * test updateSlots(HostingSlot[] slots) method, the slots is null, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testUpateSlot_nullSlot() throws Exception {
        try {
            dao.updateSlots(null);
            fail("The slot to update is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test updateSlots(HostingSlot[] slots) method, the slots  contains null elements, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testUpateSlot_nullElementSlot() throws Exception {
        try {
            dao.updateSlots(new HostingSlot[2]);
            fail("The slot contains null element.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the getGame method, the gameId does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetGame_entryNotExist() throws Exception {
        try {
            dao.getGame(1);
            fail("The game id does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName2", new Boolean(true), images));

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
        Game toGet = dao.getGame(game.getId().longValue());

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
        DownloadData data = dao.getDownloadData(((BallColor) this.colors.get(0)).getImageId());
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
     * Test the getDownloadData method, the downloadId does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetDownloadData_notExist() throws Exception {
        try {
            dao.getDownloadData(((BallColor) this.colors.get(0)).getImageId() +
                ((BallColor) this.colors.get(1)).getImageId());
            fail("The downloadId does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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

        PuzzleData data = dao.getPuzzle(puzzleId);
        assertEquals("The data's name is invalid.", "puzzleName", data.getName());
        assertEquals("The attribute size is invalid.", 2, data.getAllAttributes().size());
        assertEquals("The attribute att1 does not exist.", "value1", data.getAttribute("att1"));
        assertEquals("The resource size is invalid.", 1, data.getAllResources().size());
        assertEquals("The resource'name is invalid.", "resource1", data.getResource("resource1").getName());
    }

    /**
     * <p>
     * Test getPuzzle(puzzleId) method, the puzzleId does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetPuzzle_notExistPuzzleId() throws Exception {
        try {
            dao.getPuzzle(1);
            fail("The puzzleId does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images));

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
        String[] keyTexts = dao.getKeysForPlayer(playerId, new long[] { slotId });

        //verify
        assertEquals("The size of keyText now is 1.", 1, keyTexts.length);
        assertNull("The keyTexts should be null.", keyTexts[0]);

        //persist the complet slot for player
    }

    /**
     * <p>
     * Test getKeysForPlayer(playerId, slotIds), the player does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetKeysForPlayer_notExistPlayer() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images));

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

        try {
            dao.getKeysForPlayer(playerId + 1, new long[] { slotId });
            fail("The player is null.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getKeysForPlayer(playerId, slotIds), the slotIds does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetKeysForPlayer_notExistSlot() throws Exception {
        try {
            dao.getKeysForPlayer(playerId, new long[] { 1 });
            fail("The slotIds does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getKeysForPlayer(playerId, slotIds), the slotIds is null, iae expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetKeysForPlayer_nullSlotIds() throws Exception {
        try {
            dao.getKeysForPlayer(playerId, null);
            fail("The slotIds is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images));

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
        SlotCompletion slotCompltd = dao.recordSlotCompletion(playerId, slotId, new Date());
        assertEquals("The slotId is not the same.", slotId, slotCompltd.getSlotId());
        assertEquals("The playerId is not the same.", playerId, slotCompltd.getPlayerId());
    }

    /**
     * <p>
     * Test recordSlotCompletion(playerId, slotId, date), record the same record twice, DuplicateEntryException
     * expected..
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordSlotCompletion_duplicated() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images));

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
        SlotCompletion slotCompltd = dao.recordSlotCompletion(playerId, slotId, new Date());

        try {
            dao.recordSlotCompletion(playerId, slotId, new Date());
            fail("record the same record twice.");
        } catch (DuplicateEntryException e) {
            //good
        }
    }

    /**
     * <p>
     * Test recordSlotCompletion(playerId, slotId, date), the date is null ,iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordSlotCompletion_nullDate() throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images));

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

        try {
            dao.recordSlotCompletion(playerId, slotId, null);
            fail("The date is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test recordSlotCompletion(playerId, slotId, date), the player does not exist ,EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordSlotCompletion_notExistPlayer()
        throws Exception {
        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images));

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

        try {
            dao.recordSlotCompletion(playerId + 1, slotId, new Date());
            fail("The player does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test recordSlotCompletion(playerId, slotId, date), the slot does not exist ,EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordSlotCompletion_notExistSlot()
        throws Exception {
        try {
            dao.recordSlotCompletion(playerId, 1, new Date());
            fail("The slot does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the recordBinaryObject method, the content is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordBinaryObject_nullContent() throws Exception {
        try {
            dao.recordBinaryObject("name", "type", null);
            fail("the content is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the recordBinaryObject method, the name is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordBinaryObject_nullName() throws Exception {
        try {
            dao.recordBinaryObject(null, "type", "content".getBytes());
            fail("the name is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the recordBinaryObject method, the name is empty, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordBinaryObject_empty() throws Exception {
        try {
            dao.recordBinaryObject(" ", "type", "content".getBytes());
            fail("the name is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the recordBinaryObject method, the MeidaTtype is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordBinaryObject_nulLType() throws Exception {
        try {
            dao.recordBinaryObject("name", null, "content".getBytes());
            fail("the MeidaTtype is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the recordBinaryObject method, the MeidaTtype is empty, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordBinaryObject_emptyType() throws Exception {
        try {
            dao.recordBinaryObject("name", " ", "content".getBytes());
            fail("the MeidaTtype is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the recordBinaryObject method, it is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordBinaryObject() throws Exception {
        //Simply call the method
        dao.recordBinaryObject("name", "type", "content".getBytes());
    }

    /**
     * test the recordGameCompletion(playerId, gameId) method, it is an accuracy test case.
     *
     * @throws Exception into JUnit
     */
    public void testRecordGameCompletion() throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));
        dao.recordGameCompletion(playerId, game.getId().longValue());
    }

    /**
     * test the recordGameCompletion(playerId, gameId) method, the game is complete,DuplicateEntryException expected.
     *
     * @throws Exception into JUnit
     */
    public void testRecordGameCompletion_duplicated() throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));
        //complete it
        dao.recordGameCompletion(playerId, game.getId().longValue());

        try {
            dao.recordGameCompletion(playerId, game.getId().longValue());
            fail("The game is complete for the player.");
        } catch (DuplicateEntryException e) {
            //good
        }
    }

    /**
     * test the recordGameCompletion(playerId, gameId) method, the game does not exist, EntryNotFoundException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testRecordGameCompletion_notExistGame()
        throws Exception {
        try {
            this.dao.recordGameCompletion(playerId, 1);
            fail("the game does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * test the recordGameCompletion(playerId, gameId) method, the player does not exist, EntryNotFoundException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testRecordGameCompletion_notExistPlayer()
        throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));

        try {
            this.dao.recordGameCompletion(playerId + 1, game.getId().longValue());
            fail("the game does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * test the recordRegistration(playerId, gameId) method, the game does not exist, EntryNotFoundException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testRecordRegistration_notExistGame() throws Exception {
        try {
            this.dao.recordRegistration(playerId, 1);
            fail("the game does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * test the recordRegistration(playerId, gameId) method, the player does not exist, EntryNotFoundException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testRecordRegistration_notExistPlayer()
        throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));

        try {
            this.dao.recordRegistration(playerId + 1, game.getId().longValue());
            fail("the game does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * test the recordRegistration(playerId, gameId) method, the player register  game twice, DuplicateEntryException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testRecordRegistration_duplicated() throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));
        this.dao.recordRegistration(playerId, game.getId().longValue());

        try {
            this.dao.recordRegistration(playerId, game.getId().longValue());
            fail("the game registered twice.");
        } catch (DuplicateEntryException e) {
            //good
        }
    }

    /**
     * test the recordRegistration(playerId, gameId) method, accuracy test case.
     *
     * @throws Exception into JUnit
     */
    public void testRecordRegistration() throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));
        dao.recordRegistration(playerId, game.getId().longValue());
    }

    /**
     * <p>
     * Test the recordPluginDownload method, the pluginName is empty, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordPluginDownloadt_emptyName() throws Exception {
        try {
            dao.recordPluginDownload(null);
            fail("the name is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the recordPluginDownload method, the pluginName is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordPluginDownloadt_nullName() throws Exception {
        try {
            dao.recordPluginDownload(null);
            fail("the name is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the recordPluginDownload method, the pluginName does not exist, EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRecordPluginDownloadt_notExistName()
        throws Exception {
        try {
            dao.recordPluginDownload("name");
            fail("the name does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        dao.recordPluginDownload("pluginName");
    }

    /**
     * <p>
     * Test the findAllBallColors method. it is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindAllBallColors() throws Exception {
        BallColor[] ballColors = dao.findAllBallColors();
        assertEquals("The size of ballColor is wrong.", 2, ballColors.length);
    }

    /**
     * Test findSlotForDomain(long gameId, long playerId, String domain) method, the domain is null ,iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testFindSlotForDomain_nullDomain() throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));

        try {
            dao.findSlotForDomain(game.getId().longValue(), playerId, null);
            fail("The domain name is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test findSlotForDomain(long gameId, long playerId, String domain) method, the domain is empty ,iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testFindSlotForDomain_emptyDomain() throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));

        try {
            dao.findSlotForDomain(game.getId().longValue(), playerId, " ");
            fail("The domain name is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test findSlotForDomain(long gameId, long playerId, String domain) method, the domain does not exist
     * ,EntryNotFoundException expected.
     *
     * @throws Exception into JUnit
     */
    public void testFindSlotForDomain_notExistDomain()
        throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));

        try {
            dao.findSlotForDomain(game.getId().longValue(), playerId, "domainName");
            fail("The domain name does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * Test findSlotForDomain(long gameId, long playerId, String domain) method, the game does not exist
     * ,EntryNotFoundException expected.
     *
     * @throws Exception into JUnit
     */
    public void testFindSlotForDomain_notExistGame() throws Exception {
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        Domain toPersist = TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images);
        Domain persisted = dao.createDomain(toPersist);

        try {
            dao.findSlotForDomain(1, playerId, "domainName");
            fail("The game not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test findSlotForDomain(long gameId, long playerId, String domain) method, the player does not exist
     * ,EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindSlotForDomain_notExistPlayer()
        throws Exception {
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));

        dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        try {
            dao.findSlotForDomain(game.getId().longValue(), playerId + 1, "domainName");
            fail("The domain name does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));

        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images));

        //get domain now, no slot can be found
        assertNull("now no hosting slot can be found.",
            dao.findSlotForDomain(game.getId().longValue(), playerId, "domainName"));

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
        HostingSlot slot = dao.findSlotForDomain(game.getId().longValue(), playerId, "domainName");

        assertEquals("The brntsrIds length is wrong.", 1, slot.getBrainTeaserIds().length);
        assertEquals("The brntsrIds is not the one to set.", puzzleId, slot.getBrainTeaserIds()[0]);
        assertNotNull("The hostingStart is not the same.", slot.getHostingStart());
        assertNull("The hostingEnd date is null.", slot.getHostingEnd());
        assertEquals("The sequenceNumber is wrong.", sequenceNumber, slot.getSequenceNumber());
        assertEquals("The puzzleId is not the same.", puzzleId, slot.getPuzzleId().longValue());
        assertEquals("The winningBid is the one to set.", 2, slot.getWinningBid());
        assertEquals("The imageId is not the one to set.", imageId, slot.getImageId());

        //complete the slot
        dao.recordSlotCompletion(playerId, slotId, new Date());

        assertNull("now no hosting slot can be found.",
            dao.findSlotForDomain(game.getId().longValue(), playerId, "domainName"));
    }

    /**
     * <p>
     * Test the findDomainsForSponsor method ,with a not exist sponsorId , EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindDomainsForSponsor_notExistSponsor()
        throws Exception {
        try {
            dao.findDomainsForSponsor(sponsorId + 1);
            fail("The sponsor does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the findDomainsForSponsor method , it is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindDomainsForSponsor() throws Exception {
        Domain[] domains = dao.findDomainsForSponsor(sponsorId);
        assertEquals("The domains's length is not zero.", 0, domains.length);

        //persist the domain
        ImageInfo[] images = TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]));
        dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true), images));
        domains = dao.findDomainsForSponsor(sponsorId);

        assertEquals("The size of domain's length is invalid.", 1, domains.length);
        assertEquals("The sponsorId is not the one set.", sponsorId, domains[0].getSponsorId());
        assertEquals("The domain name is not the one set.", "domainName", domains[0].getDomainName());
        assertEquals("The images length is wrong.", images.length, domains[0].getImages().length);
    }

    /**
     * Test the findGameRegistrations method, with a not exist playerId, EntryNotFoundException expected.
     *
     * @throws Exception into JUnit
     */
    public void testFindGameRegistrations_notExistPlayer()
        throws Exception {
        try {
            dao.findGameRegistrations(playerId + 1);
            fail("The player does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * Test the findGameRegistrations method , it is an accuracy test case.
     *
     * @throws Exception into JUnit
     */
    public void testFindGameRegistrations() throws Exception {
        long[] gameIds = dao.findGameRegistrations(playerId);
        assertEquals("The size of the gameIds array is zero.", 0, gameIds.length);

        //persist the game
        HostingBlock[] blocks = TestHelper.getBlocks();
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    blocks));

        //complete the game
        dao.recordRegistration(playerId, game.getId().longValue());

        //find the game Complete again
        gameIds = dao.findGameRegistrations(playerId);
        assertEquals("The size of the gameIds is invalid.", 1, gameIds.length);
        assertEquals("The game id is not the one we need.", game.getId().longValue(), gameIds[0]);
    }

    /**
     * <p>
     * Test the findGames, isStarted is false while isEnded is true, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindGames_wrongCombo() throws Exception {
        try {
            dao.findGames(new Boolean(false), new Boolean(true));
            fail("The start is false and the end is true.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test findGames(isStarted,isEnded). accuracy test case.
     *
     * @throws Exception into JUnit
     */
    public void testFindGames() throws Exception {
        //persist a game
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));
        assertNotNull("The startDate is null.", game.getStartDate());
        assertNull("The endDate is not null.", game.getEndDate());

        //all games
        Game[] games = dao.findGames(null, null);
        assertEquals("The size of games is zero.", 1, games.length);

        //end games
        games = dao.findGames(null, new Boolean(true));
        assertEquals("The size of games is zero.", 0, games.length);

        //not end games
        games = dao.findGames(null, new Boolean(false));
        assertEquals("The size of games is zero.", 1, games.length);

        //start games
        games = dao.findGames(new Boolean(true), null);
        assertEquals("The size of games is zero.", 0, games.length);

        //not start games
        games = dao.findGames(new Boolean(false), null);
        assertEquals("The size of games is zero.", 0, games.length);
        games = dao.findGames(new Boolean(false), new Boolean(false));
        assertEquals("The size of games is zero.", 0, games.length);

        //start but not end games
        games = dao.findGames(new Boolean(true), new Boolean(false));
        assertEquals("The size of games is zero.", 0, games.length);

        //start and end games
        games = dao.findGames(new Boolean(true), new Boolean(true));
        assertEquals("The size of games is zero.", 0, games.length);
    }

    /**
     * <p>
     * Test the findGamesByDomain method, the domain is null ,iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindGamesByDomain_nullDomain() throws Exception {
        try {
            dao.findGamesByDomain(null, playerId);
            fail("The domain is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the findGamesByDomain method, the domain is empty ,iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindGamesByDomain_emptyDomain() throws Exception {
        try {
            dao.findGamesByDomain(" ", playerId);
            fail("The domain is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the findGamesByDomain method, the domain does not exist ,EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindGamesByDomain_notExistDomain()
        throws Exception {
        try {
            dao.findGamesByDomain("notExist", playerId);
            fail("The domain does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the findGamesByDomain method, the  player does not exist ,EntryNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindGamesByDomain_notExistPlayer()
        throws Exception {
        dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        try {
            dao.findGamesByDomain("domainName", playerId + 1);
            fail("The player does not exist.");
        } catch (EntryNotFoundException e) {
            //good
        }
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
        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));

        Game[] games = dao.findGamesByDomain("domainName", playerId);
        assertEquals("The size of game is incorrent.", 0, games.length);

        //create game
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));
        //find again
        games = dao.findGamesByDomain("domainName", playerId);
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
        games = dao.findGamesByDomain("domainName", playerId);
        assertEquals("The size of game is incorrent.", 1, games.length);

        dao.recordSlotCompletion(playerId, slotId, new Date());

        games = dao.findGamesByDomain("domainName", playerId);
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
        Domain[] domains = dao.findActiveDomains();
        assertEquals("The domain array is not empty.", 0, domains.length);

        //persist the domain
        Domain persisted = dao.createDomain(TestHelper.getDomain(sponsorId, "domainName", new Boolean(true),
                    TestHelper.getImages((BallColor[]) colors.toArray(new BallColor[0]))));
        Game game = dao.createGame(TestHelper.getGameImplInstance((BallColor) colors.get(0), keyCount, gameStartDate,
                    TestHelper.getBlocks()));

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
        domains = dao.findActiveDomains();
        assertEquals("The domain array is empty.", 1, domains.length);
    }
}
