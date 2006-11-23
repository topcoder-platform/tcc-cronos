/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.impl;

import java.sql.Connection;
import java.util.Date;

import junit.framework.TestCase;

import com.orpheus.auction.persistence.DuplicateEntryException;
import com.orpheus.auction.persistence.EntryNotFoundException;
import com.orpheus.auction.persistence.InvalidEntryException;
import com.orpheus.auction.persistence.UnitTestHelper;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;
import com.topcoder.util.cache.Cache;


/**
 * <p>
 * Tests functionality and error cases of <code>SQLServerAuctionDAO</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SQLServerAuctionDAOUnitTest extends TestCase {
    /** Represents the valid namespace for testing. */
    private static final String NAME_SPACE = "com.orpheus.auction.persistence.impl.SQLServerAuctionDAO";

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>SQLServerAuctionDAO</code> instance used for testing. */
    private SQLServerAuctionDAO persistence;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);
        persistence = new SQLServerAuctionDAO(NAME_SPACE);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            UnitTestHelper.clearConfig();
            UnitTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Test the constructor <code>SQLServerAuctionDAO(String)</code> when the given namespace is null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSQLServerAuctionDAO_NullNamespace() throws Exception {
        try {
            new SQLServerAuctionDAO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>SQLServerAuctionDAO(String)</code> when the given namespace is an empty string,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSQLServerAuctionDAO_EmptyNamespace() throws Exception {
        try {
            new SQLServerAuctionDAO(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the constructor <code>SQLServerAuctionDAO(String)</code> when the namespace is correct.
     * Everything should be successfully initialized.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSQLServerAuctionDAO_Accuracy() throws Exception {
        assertNotNull("The SQLServerAuctionDAO instance should be created.", persistence);
        assertNotNull("The connectionFactory should be created.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "connectionFactory"));
        assertNotNull("The cache should be created.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "cache"));
        assertEquals("The connectionName should be loaded properly.", "SQLServerConnectionImpl",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "connectionName"));
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the given auction is null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NullAuction() throws Exception {
        try {
            persistence.createAuction(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the minimumBid of given auction is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NegativeMinimumBid() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setMinimumBid(-1);

        try {
            persistence.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the itemCount of given auction is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NegativeItemCount() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setItemCount(-1);

        try {
            persistence.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when bids is null, IllegalArgumentException is
     * expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NullBids() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setBids(null);

        try {
            persistence.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the given auction has null Bid elements,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NullBidElements() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setBids(new BidDTO[] {null});

        try {
            persistence.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the imageId of one bid is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NegativeImageId() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setImageId(-1);

        try {
            persistence.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the maxAmount of one bid is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NegativeMaxAmount() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setMaxAmount(-1);

        try {
            persistence.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the effectiveAmount of one bid is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NegativEffectiveAmount() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setEffectiveAmount(new Integer(-1));

        try {
            persistence.createAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the given auction id is null,
     * InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NullID() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setId(null);

        try {
            persistence.createAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the auction id does not exist in hosting_block
     * table, InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_InvalidAuctionId() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(100, 5);

        try {
            persistence.createAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }

        assertEquals("The bid table should not be affected.", 0, UnitTestHelper.getBidRecords(connection));
        assertEquals("The auction table should not be affected.", 0, UnitTestHelper.getAuctionRecords(connection));
        assertEquals("The effective_bid table should not be affected.", 0,
            UnitTestHelper.getEffectiveBidRecords(connection));
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the bidder id of one bid does not exist,
     * InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_InvalidBidderid() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setBidderId(100);

        try {
            persistence.createAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }

        assertEquals("The bid table should not be affected.", 0, UnitTestHelper.getBidRecords(connection));
        assertEquals("The auction table should not be affected.", 0, UnitTestHelper.getAuctionRecords(connection));
        assertEquals("The effective_bid table should not be affected.", 0,
            UnitTestHelper.getEffectiveBidRecords(connection));
    }

    /**
     * Test the method of <code>CreateAuction(AuctionDTO)</code> when auction is already exist, DuplicateEntryException
     * is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_ExistAuction() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 4);
        persistence.createAuction(auction);

        try {
            persistence.createAuction(auction);
            fail("DuplicateEntryException should be thrown.");
        } catch (DuplicateEntryException e) {
            // good
        }

        assertEquals("The bid table should not be affected.", auction.getBids().length,
            UnitTestHelper.getBidRecords(connection));
        assertEquals("The auction table should not be affected.", 1, UnitTestHelper.getAuctionRecords(connection));
        assertEquals("The effective_bid table should not be affected.", auction.getBids().length / 2,
            UnitTestHelper.getEffectiveBidRecords(connection));
    }

    /**
     * Accuracy test for the method of <code>createAuction(AuctionDTO)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_Accuracy() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        // add it
        AuctionDTO ret = persistence.createAuction(auction);
        UnitTestHelper.assertEquals(auction, ret, false);

        // get it and check it
        AuctionDTO persisted = persistence.getAuction(ret.getId().longValue());
        UnitTestHelper.assertEquals(auction, persisted, true);
    }

    /**
     * Test the method of <code>getAuction(long)</code> when auction already exists, EntryNotFoundException is
     * expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetAuction_NotExistAuction() throws Exception {
        try {
            persistence.getAuction(1);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method of <code>getAuction(long)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetAuction_Accuracy() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        // add it
        auction = persistence.createAuction(auction);

        // get it and check it
        AuctionDTO ret = persistence.getAuction(auction.getId().longValue());
        assertSame("Cache should be used.", auction, ret);

        AuctionDTO another = persistence.getAuction(auction.getId().longValue());
        assertSame("Cache should be used.", ret, another);

        // clear the cache of SQLServerAuctionDAO
        Cache cache = (Cache) UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "cache");
        cache.clear();

        ret = persistence.getAuction(auction.getId().longValue());
        UnitTestHelper.assertEquals(auction, ret, true);
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when the given auction is null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NullAuction() throws Exception {
        try {
            persistence.updateAuction(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when the minimumBid of given auction is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NegativeMinimumBid() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setMinimumBid(-1);

        try {
            persistence.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when the itemCount of given auction is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NegativeItemCount() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setItemCount(-1);

        try {
            persistence.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when bids is null, IllegalArgumentException is
     * expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NullBids() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setBids(null);

        try {
            persistence.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when the given auction has null Bid elements,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NullBidElements() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setBids(new BidDTO[] {null});

        try {
            persistence.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when the imageId of one bid is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NegativeImageId() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setImageId(-1);

        try {
            persistence.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when the maxAmount of one bid is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NegativeMaxAmount() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setMaxAmount(-1);

        try {
            persistence.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when the effectiveAmount of one bid is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NegativEffectiveAmount() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setEffectiveAmount(new Integer(-1));

        try {
            persistence.updateAuction(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when the given auction id is null,
     * InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NullID() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setId(null);

        try {
            persistence.updateAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when the bidder id of one bid does not exist,
     * InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_InvalidBidderid() throws Exception {
        // add an auction
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 4);
        AuctionDTO persisted = persistence.createAuction(auction);
        auction = UnitTestHelper.buildAuctionDTO(1, 10);
        (auction.getBids())[0].setBidderId(100);

        try {
            persistence.updateAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }

        // validate the cache is not affected.
        assertEquals("The cache should not be affect.", persisted, persistence.getAuction(auction.getId().longValue()));

        // valid the previous auction record is not affected.
        UnitTestHelper.assertEquals(persisted, persistence.getAuction(auction.getId().longValue()), true);
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when auction does not exist, EntryNotFoundException is
     * expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NotExistAuction() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        try {
            persistence.updateAuction(auction);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }

        assertEquals("The bid table should not be affected.", 0, UnitTestHelper.getBidRecords(connection));
        assertEquals("The auction table should not be affected.", 0, UnitTestHelper.getAuctionRecords(connection));
        assertEquals("The effective_bid table should not be affected.", 0,
            UnitTestHelper.getEffectiveBidRecords(connection));
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code>. Two bids will have id and other two will have no id.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_Accuracy1() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        // add it
        persistence.createAuction(auction);

        AuctionDTO auctionUpdated = UnitTestHelper.buildAuctionDTO(1, 4);
        auctionUpdated.getBids()[0].setId(auction.getBids()[0].getId());
        auctionUpdated.getBids()[1].setId(auction.getBids()[1].getId());

        // update it
        AuctionDTO ret = persistence.updateAuction(auctionUpdated);
        UnitTestHelper.assertEquals(auctionUpdated, ret, false);

        // get it and check it
        AuctionDTO persisted = persistence.getAuction(ret.getId().longValue());
        UnitTestHelper.assertEquals(ret, persisted, true);
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code>. Two bids will have id and other five will have no id.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_Accuracy2() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);

        // add it
        persistence.createAuction(auction);

        AuctionDTO auctionUpdated = UnitTestHelper.buildAuctionDTO(1, 7);
        auctionUpdated.getBids()[0].setId(auction.getBids()[0].getId());
        auctionUpdated.getBids()[1].setId(auction.getBids()[1].getId());

        // update it
        AuctionDTO ret = persistence.updateAuction(auctionUpdated);
        UnitTestHelper.assertEquals(auctionUpdated, ret, false);

        // get it and check it
        AuctionDTO persisted = persistence.getAuction(ret.getId().longValue());
        UnitTestHelper.assertEquals(ret, persisted, true);
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code> when the given bids is null, IllegalArgumentException
     * is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_NullBids() throws Exception {
        try {
            persistence.updateBids(1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code> when the given bids contain null element,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_ContainNullBid() throws Exception {
        BidDTO[] bids = new BidDTO[] {null};

        try {
            persistence.updateBids(1, bids);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code> when the imageId of one bid is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_NegativeImageId() throws Exception {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setImageId(-1);

        try {
            persistence.updateBids(1, bids);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code> when the maxAmount of one bid is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_NegativeMaxAmount() throws Exception {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setMaxAmount(-1);

        try {
            persistence.updateBids(1, bids);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code> when the effectiveAmount of one bid is negative,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_NegativEffectiveAmount() throws Exception {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setEffectiveAmount(new Integer(-1));

        try {
            persistence.updateBids(1, bids);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code> when the auction does not exist,
     * EntryNotFoundException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_NotExistAuction() throws Exception {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(2);

        try {
            persistence.updateBids(1, bids);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code> when the bidder id of one bid does not exist,
     * InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_InvalidBidderid() throws Exception {
        // add an auction
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 0);
        persistence.createAuction(auction);

        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setBidderId(100);
        auction.setBids(bids);

        try {
            persistence.updateBids(auction.getId().longValue(), auction.getBids());
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code>. Two bids will have id and other two will have no id.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_Accuracy1() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setDescription("");
        auction.setSummary("");

        // add it
        persistence.createAuction(auction);

        // update it
        BidDTO[] newIds = UnitTestHelper.buildBidDTOs(4);
        newIds[0].setId(auction.getBids()[0].getId());
        newIds[1].setId(auction.getBids()[1].getId());
        auction.setBids(newIds);

        AuctionDTO ret = persistence.updateBids(auction.getId().longValue(), auction.getBids());

        // get it and check it
        AuctionDTO persisted = persistence.getAuction(ret.getId().longValue());
        UnitTestHelper.assertEquals(ret, persisted, true);
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code>. Two bids will have id and other five will have no
     * id.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_Accuracy2() throws Exception {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setDescription("");
        auction.setSummary("");

        // add it
        persistence.createAuction(auction);

        // update it
        BidDTO[] newIds = UnitTestHelper.buildBidDTOs(7);
        newIds[0].setId(auction.getBids()[0].getId());
        newIds[1].setId(auction.getBids()[1].getId());
        auction.setBids(newIds);

        AuctionDTO ret = persistence.updateBids(auction.getId().longValue(), auction.getBids());

        // get it and check it
        AuctionDTO persisted = persistence.getAuction(ret.getId().longValue());
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
        persistence.createAuction(auction);
        persistence.deleteAuction(auction.getId().longValue());

        // get it and check it
        try {
            persistence.getAuction(auction.getId().longValue());
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Test the method of <code>findAuctionsByDate(Date, Date)</code> when start and end date are not null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate_Accuracy1() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.createAuction(auctions[i]);
        }

        startDate = new Date(15000);
        endDate = new Date(45000);

        // get it and check it
        AuctionDTO[] retAuctions = persistence.findAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 2, retAuctions.length);

        for (int i = 0; i < 2; i++) {
            UnitTestHelper.assertEquals(auctions[i + 1], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>findAuctionsByDate(Date, Date)</code> when end date is null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate_Accuracy2() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.createAuction(auctions[i]);
        }

        startDate = new Date(15000);

        // get it and check it
        AuctionDTO[] retAuctions = persistence.findAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 4, retAuctions.length);

        for (int i = 0; i < 4; i++) {
            UnitTestHelper.assertEquals(auctions[i + 1], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>findAuctionsByDate(Date, Date)</code>when start date is null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate_Accuracy3() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.createAuction(auctions[i]);
        }

        endDate = new Date(45000);

        // get it and check it
        AuctionDTO[] retAuctions = persistence.findAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 3, retAuctions.length);

        for (int i = 0; i < 3; i++) {
            UnitTestHelper.assertEquals(auctions[i], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>findAuctionsByDate(Date, Date)</code>when all start and end date are null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate_Accuracy4() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.createAuction(auctions[i]);
        }

        // get it and check it
        AuctionDTO[] retAuctions = persistence.findAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 5, retAuctions.length);

        for (int i = 0; i < 5; i++) {
            UnitTestHelper.assertEquals(auctions[i], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>findAuctionsByBidder(long, endingAfter)</code>when end date is not null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByBidder_Accuracy1() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.createAuction(auctions[i]);
        }

        endDate = new Date(45000);

        // get it and check it
        AuctionDTO[] retAuctions = persistence.findAuctionsByBidder(UnitTestHelper.BIDDER_ID, endDate);
        assertEquals("The length got should be correct.", 2, retAuctions.length);

        for (int i = 0; i < 2; i++) {
            UnitTestHelper.assertEquals(auctions[i + 1], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>findAuctionsByBidder(long, endingAfter)</code>when end date is null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByBidder_Accuracy2() throws Exception {
        AuctionDTO[] auctions = new AuctionDTO[5];
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuctionDTO(i + 1, i);

            // add it
            persistence.createAuction(auctions[i]);
        }

        // get it and check it
        AuctionDTO[] retAuctions = persistence.findAuctionsByBidder(UnitTestHelper.BIDDER_ID, endDate);
        assertEquals("The length got should be correct.", 4, retAuctions.length);

        for (int i = 0; i < 4; i++) {
            UnitTestHelper.assertEquals(auctions[i + 1], retAuctions[i], true);
        }
    }
}
