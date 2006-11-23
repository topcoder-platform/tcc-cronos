/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.accuracytests;

import com.orpheus.auction.persistence.DuplicateEntryException;
import com.orpheus.auction.persistence.EntryNotFoundException;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;
import com.orpheus.auction.persistence.impl.SQLServerAuctionDAO;

import com.topcoder.util.cache.Cache;

import junit.framework.TestCase;

import java.sql.Connection;

import java.util.Date;


/**
 * Test the SQLServerAuctionDAO class.
 * @author waits
 * @version 1.0
 */
public class SQLServerAuctionDAOAccTests extends TestCase {
    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** SQLServerAuctionDAO dao. */
    private SQLServerAuctionDAO dao = null;

    /** timestamp for bidDTO. */
    private Date timestamp = null;

    /** start date. */
    private Date startDate = null;

    /** end date. */
    private Date endDate = null;

    /** BidDTO array. */
    private BidDTO[] bidDTOs = null;

    /** AuctionDTO instance for testing. */
    private AuctionDTO auctionDTO = null;

    /**
     * create instance.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();

        TestHelper.addConfigFile(TestHelper.AUCTION_PERSISTENCE_CONFIG_FILE);
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);

        //insert data to database for testing
        connection = TestHelper.getConnection();
        TestHelper.prepareData(connection);

        //create instance
        dao = new SQLServerAuctionDAO(TestHelper.DAO_NAMESPACE);

        bidDTOs = new BidDTO[1];
        timestamp = new Date(1000);
        bidDTOs[0] = TestHelper.getBidDTO(1, 1, 3, new Integer(1), 1, timestamp);
        startDate = new Date(1000);
        endDate = new Date(2000);

        auctionDTO = TestHelper.getAuctionDTO(1, "desc", "summary", 1, 1, startDate, endDate, bidDTOs);
    }

    /**
     * <p>
     * Test the createAuction(auctionDTO) method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateAuction() throws Exception {
        AuctionDTO ret = dao.createAuction(this.auctionDTO);
        //verify the result
        TestHelper.assertEquals(auctionDTO, ret, false);

        //verify in the cache
        try {
            dao.createAuction(ret);
            fail("It should already exist in cache.");
        } catch (DuplicateEntryException e) {
            //good
        }

        //get the AuctionDTO from dao
        AuctionDTO getOne = dao.getAuction(ret.getId().longValue());
        //verify the result from database
        TestHelper.assertEquals(auctionDTO, getOne, true);
    }

    /**
     * Test for the method of getAuction(long).
     *
     * @throws Exception into JUnit
     */
    public void testGetAuction() throws Exception {
        // add it
        AuctionDTO auction = dao.createAuction(auctionDTO);

        // get it and check it
        AuctionDTO ret = dao.getAuction(auction.getId().longValue());
        assertSame("Cache should be used.", auction, ret);

        // clear the cache of SQLServerAuctionDAO
        Cache cache = (Cache) TestHelper.getPrivateField(dao.getClass(), dao, "cache");
        cache.clear();

        ret = dao.getAuction(auction.getId().longValue());
        TestHelper.assertEquals(auction, ret, true);
    }

    /**
     * Test the UpdateAuction method.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateAuction() throws Exception {
        // add it
        AuctionDTO auction = dao.createAuction(auctionDTO);

        //update the item
        auction.setItemCount(auction.getItemCount() + 1);
        auction.setMinimumBid(auction.getMinimumBid() + 1);
        auction.setEndDate(new Date());

        BidDTO[] bids = auction.getBids();
        bids[0].setMaxAmount(bids[0].getMaxAmount() + 1);
        bids[0].setEffectiveAmount(new Integer(bids[0].getMaxAmount() + 1));

        auction.setBids(bids);

        //update in database
        AuctionDTO updated = dao.updateAuction(auction);
        //verify
        TestHelper.assertEquals(updated, auction, false);

        AuctionDTO getOne = dao.getAuction(auction.getId().longValue());
        TestHelper.assertEquals(getOne, updated, false);
    }

    /**
     * Test the method ,updateBids(auctionId,bids) .
     *
     * @throws Exception into Junit
     */
    public void testUpdateBids() throws Exception {
        // add it
        AuctionDTO auction = dao.createAuction(auctionDTO);

        BidDTO[] bids = auction.getBids();

        BidDTO[] updatedBids = new BidDTO[1];
        updatedBids[0] = new BidDTO();
        updatedBids[0].setBidderId(bids[0].getBidderId());
        updatedBids[0].setImageId(bids[0].getImageId());
        updatedBids[0].setMaxAmount(bids[0].getMaxAmount() + 1);
        updatedBids[0].setEffectiveAmount(new Integer(bids[0].getMaxAmount() + 1));
        updatedBids[0].setTimestamp(new Date());
        updatedBids[0].setId(bids[0].getId());

        //update  it
        AuctionDTO updatedAuction = dao.updateBids(auction.getId().longValue(), updatedBids);

        AuctionDTO getOne = dao.getAuction(auction.getId().longValue());
        //verify
        TestHelper.assertEquals(updatedAuction, getOne, false);
    }

    /**
     * <p>
     * Test the Delete Auction method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testDeleteAuction() throws Exception {
        // add it
        AuctionDTO auction = dao.createAuction(auctionDTO);
        //delete it
        dao.deleteAuction(auction.getId().longValue());

        try {
            dao.getAuction(auction.getId().longValue());
            fail("The auction is deleted.");
        } catch (EntryNotFoundException e) {
            //good
        }

        //add it again
        dao.createAuction(auctionDTO);
    }

    /**
     * Test the method findAuctionsByDate(Date, Date).
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate() throws Exception {
        dao.createAuction(auctionDTO);

        startDate = new Date(900);
        endDate = new Date(3000);

        // get it and check it
        AuctionDTO[] retAuctions = dao.findAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 1, retAuctions.length);

        TestHelper.assertEquals(this.auctionDTO, retAuctions[0], true);

        assertEquals("The length got should be correct.", 0,
            dao.findAuctionsByDate(new Date(3000), new Date(4000)).length);
    }
    
    /**
     * Test findAuctionsByBidder method.
     * @throws Exception into Junit
     */
    public void testFindAuctionsByBidder() throws Exception{
    	 dao.createAuction(auctionDTO);
    	 
    	 AuctionDTO[] dto = dao.findAuctionsByBidder(1,new Date(100000));
    	 assertEquals("The size of result is wrong.", 1, dto.length);
    	 
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
            TestHelper.clearConfigManager();
            TestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
