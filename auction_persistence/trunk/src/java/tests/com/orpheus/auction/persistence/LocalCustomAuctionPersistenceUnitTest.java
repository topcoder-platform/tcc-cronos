/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

import com.topcoder.util.cache.Cache;

import java.util.Date;


/**
 * <p>
 * Tests functionality and error cases of <code>LocalCustomAuctionPersistence</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LocalCustomAuctionPersistenceUnitTest extends CustomAuctionPersistenceTestCase {
    /** Represents the valid namespace for testing. */
    private static final String NAME_SPACE = "com.orpheus.auction.persistence.LocalCustomAuctionPersistence";

    /** Represents the <code>LocalCustomAuctionPersistence</code> instance used for testing. */
    private LocalCustomAuctionPersistence persistence;

    /**
     * The constructor of this class.
     *
     * @param testName the name of the test to run
     */
    public LocalCustomAuctionPersistenceUnitTest(String testName) {
        super(testName);
    }

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = (LocalCustomAuctionPersistence) getPersistence();
    }

    /**
     * Accuracy test for the constructor <code>LocalCustomAuctionPersistence(String)</code> when the namespace is
     * correct. Everything should be successfully initialized.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testLocalCustomAuctionPersistence_Accuracy() throws Exception {
        assertNotNull("The LocalCustomAuctionPersistence instance should be created.", persistence);
        assertNotNull("The auctionEJB should be created.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "auctionEJB"));
    }

    /**
     * Test the method of <code>ejbCreateAuction(AuctionDTO)</code> when the given auction id is null,
     * InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbCreateAuction_NullID() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setId(null);

        try {
            persistence.ejbCreateAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>ejbCreateAuction(AuctionDTO)</code> when the auction id does not exist in hosting_block
     * table, InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbCreateAuction_InvalidAuctionId() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(100, 5);

        try {
            persistence.ejbCreateAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>ejbCreateAuction(AuctionDTO)</code> when the bidder id of one bid does not exist,
     * InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbCreateAuction_InvalidBidderid() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setBidderId(100);

        try {
            persistence.ejbCreateAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>EjbCreateAuction(AuctionDTO)</code> when auction is already exist,
     * DuplicateEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbCreateAuction_ExistAuction() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 4);
        persistence.ejbCreateAuction(auction);

        try {
            persistence.ejbCreateAuction(auction);
            fail("DuplicateEntryException should be thrown.");
        } catch (DuplicateEntryException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method of <code>ejbCreateAuction(AuctionDTO)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbCreateAuction_Accuracy() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        // add it
        AuctionDTO ret = persistence.ejbCreateAuction(auction);
        UnitTestHelper.assertEquals(auction, ret, false);

        // get it and check it
        AuctionDTO persisted = persistence.ejbGetAuction(ret.getId().longValue());
        UnitTestHelper.assertEquals(auction, persisted, true);
    }

    /**
     * Test the method of <code>ejbGetAuction(long)</code> when auction already exists, EntryNotFoundException is
     * expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbGetAuction_NotExistAuction() throws Exception {
        try {
            persistence.ejbGetAuction(1);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method of <code>ejbGetAuction(long)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbGetAuction_Accuracy() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        // add it
        auction = persistence.ejbCreateAuction(auction);

        // get it and check it
        AuctionDTO ret = persistence.ejbGetAuction(auction.getId().longValue());
        assertSame("Cache should be used.", auction, ret);

        AuctionDTO another = persistence.ejbGetAuction(auction.getId().longValue());
        assertSame("Cache should be used.", ret, another);

        // clear the cache of SQLServerAuctionDAO
        Cache cache = (Cache) UnitTestHelper.getPrivateField(persistence.getClass().getSuperclass(), persistence,
                "cache");
        cache.clear();

        ret = persistence.ejbGetAuction(auction.getId().longValue());
        UnitTestHelper.assertEquals(auction, ret, true);
    }

    /**
     * Test the method of <code>ejbUpdateAuction(AuctionDTO)</code> when the given auction id is null,
     * InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbUpdateAuction_NullID() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setId(null);

        try {
            persistence.ejbUpdateAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>ejbUpdateAuction(AuctionDTO)</code> when the bidder id of one bid does not exist,
     * InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbUpdateAuction_InvalidBidderid() throws Exception {
        // add an auction
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 4);
        AuctionDTO persisted = persistence.ejbCreateAuction(auction);
        auction = UnitTestHelper.buildAuctionDTO(1, 10);
        (auction.getBids())[0].setBidderId(100);

        try {
            persistence.ejbUpdateAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }

        // validate the cache is not affected.
        assertEquals("The cache should not be affect.", persisted,
            persistence.ejbGetAuction(auction.getId().longValue()));

        // valid the previous auction record is not affected.
        UnitTestHelper.assertEquals(persisted, persistence.ejbGetAuction(auction.getId().longValue()), true);
    }

    /**
     * Test the method of <code>ejbUpdateAuction(AuctionDTO)</code> when auction does not exist, EntryNotFoundException
     * is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbUpdateAuction_NotExistAuction() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        try {
            persistence.ejbUpdateAuction(auction);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Test the method of <code>ejbUpdateAuction(AuctionDTO)</code>. Two bids will have id and other two will have no
     * id.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbUpdateAuction_Accuracy1() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        // add it
        persistence.ejbCreateAuction(auction);

        AuctionDTO auctionUpdated = UnitTestHelper.buildAuctionDTO(1, 4);
        auctionUpdated.getBids()[0].setId(auction.getBids()[0].getId());
        auctionUpdated.getBids()[1].setId(auction.getBids()[1].getId());

        // update it
        AuctionDTO ret = persistence.ejbUpdateAuction(auctionUpdated);
        UnitTestHelper.assertEquals(auctionUpdated, ret, false);

        // get it and check it
        AuctionDTO persisted = persistence.ejbGetAuction(ret.getId().longValue());
        UnitTestHelper.assertEquals(ret, persisted, true);
    }

    /**
     * Test the method of <code>ejbUpdateAuction(AuctionDTO)</code>. Two bids will have id and other five will have no
     * id.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbUpdateAuction_Accuracy2() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        // add it
        persistence.ejbCreateAuction(auction);

        AuctionDTO auctionUpdated = UnitTestHelper.buildAuctionDTO(1, 7);
        auctionUpdated.getBids()[0].setId(auction.getBids()[0].getId());
        auctionUpdated.getBids()[1].setId(auction.getBids()[1].getId());

        // update it
        AuctionDTO ret = persistence.ejbUpdateAuction(auctionUpdated);
        UnitTestHelper.assertEquals(auctionUpdated, ret, false);

        // get it and check it
        AuctionDTO persisted = persistence.ejbGetAuction(ret.getId().longValue());
        UnitTestHelper.assertEquals(ret, persisted, true);
    }

    /**
     * Test the method of <code>ejbUpdateBids(long, BidDTO[])</code> when the auction does not exist,
     * EntryNotFoundException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbUpdateBids_NotExistAuction() throws Exception {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(2);

        try {
            persistence.ejbUpdateBids(1, bids);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Test the method of <code>ejbUpdateBids(long, BidDTO[])</code> when the bidder id of one bid does not exist,
     * InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbUpdateBids_InvalidBidderid() throws Exception {
        // add an auction
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 0);
        persistence.ejbCreateAuction(auction);

        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setBidderId(100);
        auction.setBids(bids);

        try {
            persistence.ejbUpdateBids(auction.getId().longValue(), auction.getBids());
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>ejbUpdateBids(long, BidDTO[])</code>. Two bids will have id and other two will have no
     * id.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbUpdateBids_Accuracy1() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setDescription("");
        auction.setSummary("");

        // add it
        persistence.ejbCreateAuction(auction);

        // update it
        BidDTO[] newIds = UnitTestHelper.buildBidDTOs(4);
        newIds[0].setId(auction.getBids()[0].getId());
        newIds[1].setId(auction.getBids()[1].getId());
        auction.setBids(newIds);

        AuctionDTO ret = persistence.ejbUpdateBids(auction.getId().longValue(), auction.getBids());

        // get it and check it
        AuctionDTO persisted = persistence.ejbGetAuction(ret.getId().longValue());
        UnitTestHelper.assertEquals(ret, persisted, true);
    }

    /**
     * Test the method of <code>ejbUpdateBids(long, BidDTO[])</code>. Two bids will have id and other five will have no
     * id.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbUpdateBids_Accuracy2() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setDescription("");
        auction.setSummary("");

        // add it
        persistence.ejbCreateAuction(auction);

        // update it
        BidDTO[] newIds = UnitTestHelper.buildBidDTOs(7);
        newIds[0].setId(auction.getBids()[0].getId());
        newIds[1].setId(auction.getBids()[1].getId());
        auction.setBids(newIds);

        AuctionDTO ret = persistence.ejbUpdateBids(auction.getId().longValue(), auction.getBids());

        // get it and check it
        AuctionDTO persisted = persistence.ejbGetAuction(ret.getId().longValue());
        UnitTestHelper.assertEquals(ret, persisted, true);
    }

    /**
     * Test the method of <code>deleteAuction(long)</code>when the given auction does not exist. EntryNotFoundException
     * is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteAuction_NotExistAuctionId() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        try {
            persistence.deleteAuction(auction.getId().longValue());
            fail("EntryNotFoundException is expected.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Test the method of <code>deleteAuction(long)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteAuction_Accuracy() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        // add it
        persistence.ejbCreateAuction(auction);
        persistence.deleteAuction(auction.getId().longValue());

        // get it and check it
        try {
            persistence.ejbGetAuction(auction.getId().longValue());
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Test the method of <code>ejbFindAuctionsByDate(Date, Date)</code> when start and end date are not null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbFindAuctionsByDate_Accuracy1() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.ejbCreateAuction(auctions[i]);
        }

        startDate = new Date(15000);
        endDate = new Date(45000);

        // get it and check it
        AuctionDTO[] retAuctions = persistence.ejbFindAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 2, retAuctions.length);

        for (int i = 0; i < 2; i++) {
            UnitTestHelper.assertEquals(auctions[i + 1], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>ejbFindAuctionsByDate(Date, Date)</code> when end date is null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbFindAuctionsByDate_Accuracy2() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.ejbCreateAuction(auctions[i]);
        }

        startDate = new Date(15000);

        // get it and check it
        AuctionDTO[] retAuctions = persistence.ejbFindAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 4, retAuctions.length);

        for (int i = 0; i < 4; i++) {
            UnitTestHelper.assertEquals(auctions[i + 1], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>ejbFindAuctionsByDate(Date, Date)</code>when start date is null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbFindAuctionsByDate_Accuracy3() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.ejbCreateAuction(auctions[i]);
        }

        endDate = new Date(45000);

        // get it and check it
        AuctionDTO[] retAuctions = persistence.ejbFindAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 3, retAuctions.length);

        for (int i = 0; i < 3; i++) {
            UnitTestHelper.assertEquals(auctions[i], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>ejbFindAuctionsByDate(Date, Date)</code>when all start and end date are null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbFindAuctionsByDate_Accuracy4() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.ejbCreateAuction(auctions[i]);
        }

        // get it and check it
        AuctionDTO[] retAuctions = persistence.ejbFindAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 5, retAuctions.length);

        for (int i = 0; i < 5; i++) {
            UnitTestHelper.assertEquals(auctions[i], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>ejbFindAuctionsByBidder(long, endingAfter)</code>when end date is not null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbFindAuctionsByBidder_Accuracy1() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.ejbCreateAuction(auctions[i]);
        }

        endDate = new Date(45000);

        // get it and check it
        AuctionDTO[] retAuctions = persistence.ejbFindAuctionsByBidder(UnitTestHelper.BIDDER_ID, endDate);
        assertEquals("The length got should be correct.", 2, retAuctions.length);

        for (int i = 0; i < 2; i++) {
            UnitTestHelper.assertEquals(auctions[i + 1], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>ejbFindAuctionsByBidder(long, endingAfter)</code>when end date is null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEjbFindAuctionsByBidder_Accuracy2() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.ejbCreateAuction(auctions[i]);
        }

        // get it and check it
        AuctionDTO[] retAuctions = persistence.ejbFindAuctionsByBidder(UnitTestHelper.BIDDER_ID, endDate);
        assertEquals("The length got should be correct.", 4, retAuctions.length);

        for (int i = 0; i < 4; i++) {
            UnitTestHelper.assertEquals(auctions[i + 1], retAuctions[i], true);
        }
    }

    /**
     * Creates an instance of <code>LocalCustomAuctionPersistence</code>.
     *
     * @return an instance of <code>LocalCustomAuctionPersistence</code>.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected CustomAuctionPersistence createPersistence() throws Exception {
        return new LocalCustomAuctionPersistence(NAME_SPACE);
    }
}
