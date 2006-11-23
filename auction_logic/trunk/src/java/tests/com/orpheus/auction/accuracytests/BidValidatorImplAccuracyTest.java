/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.orpheus.auction.BidValidatorImpl;
import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.BidValidator;

/**
 * The accuracy unit tests for the {@link BidValidatorImpl} class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class BidValidatorImplAccuracyTest extends TestCase {
    /**
     * The BidValidatorImpl instance to tests on.
     */
    private BidValidator validator = new BidValidatorImpl();

    /**
     * Tests the {@link BidValidatorImpl#isNewBidValid(Auction, Bid)} method accuracy.
     * Checks if the bid is valid when the auction is active and the bid value is equal to the min bid value.
     */
    public void testIsNewBidValid_Valid() {
        Auction auction = TestHelper.createAuction(1, 1, -10000, 10000, null);
        Bid bid = new CustomBid(1, 1, 1, new Date());
        assertTrue("The bid should be valid.", validator.isNewBidValid(auction, bid));
        
    }
    
    /**
     * Tests the {@link BidValidatorImpl#isNewBidValid(Auction, Bid)} method accuracy.
     * Checks if the bid is invalid when the auction hasn't start yet.
     */
    public void testIsNewBidValid_AuctionNotStartedYet() {
        Auction auction = TestHelper.createAuction(1, 1, 100, 100, null);
        Bid bid = new CustomBid(1, 1, 1, new Date());
        assertFalse("The auction didn't start yet, bid should be invalid.", validator.isNewBidValid(auction, bid));
    }
    
    /**
     * Tests the {@link BidValidatorImpl#isNewBidValid(Auction, Bid)} method accuracy.
     * Checks if the bid is invalid when the auction has finished.
     */
    public void testIsNewBidValid_AuctionFinished() {
        Auction auction = TestHelper.createAuction(1, 1, -1000, -100, null);
        Bid bid = new CustomBid(1, 1, 1, new Date());
        assertFalse("The auction has finished, bid should be invalid.", validator.isNewBidValid(auction, bid));
    }
    
    /**
     * Tests the {@link BidValidatorImpl#isNewBidValid(Auction, Bid)} method accuracy.
     * Checks if the bid is valid when the bid was made before auction start.
     */
    public void testIsNewBidValid_BidBeforeAuction() {
        Auction auction = TestHelper.createAuction(1, 1, -1, 100, null);
        Bid bid = new CustomBid(1, 1, 1, new Date(System.currentTimeMillis() - 2000));
        assertTrue("The bid before auction, bid should be invalid.", validator.isNewBidValid(auction, bid));
    }
    
    /**
     * Tests the {@link BidValidatorImpl#isNewBidValid(Auction, Bid)} method accuracy.
     * Checks if the bid is invalid when the bid value is lower than minimum bid for auction.
     */
    public void testIsNewBidValid_BidTooLow() {
        Auction auction = TestHelper.createAuction(1, 2, -1000, 100, null);
        Bid bid = new CustomBid(1, 1, 1, new Date());
        assertFalse("The bid is too low, should be invalid.", validator.isNewBidValid(auction, bid));    
    }

    /**
     * Tests the {@link BidValidatorImpl#isBidUpdateValid(Auction, Bid, Bid)} method accuracy.
     * Check if the bid is valid if the action is active, the new bid has the same properties as orginal bid, 
     * the new bid value is higher the previous one and the original bid exists in the auction. 
     */
    public void testIsBidUpdateValid() {
        CustomBid original = new CustomBid(1, 1, 1, new Date());
        original.setId(10);
        Auction auction = TestHelper.createAuction(1, 1, -4000, 4000, new Bid[] {original});
        
        CustomBid bid = new CustomBid(1, 1, 2, new Date());
        bid.setId(10);
        assertTrue("Bid should be valid.", validator.isBidUpdateValid(auction, original, bid));
        
    }
    
    /**
     * Tests the {@link BidValidatorImpl#isBidUpdateValid(Auction, Bid, Bid)} mwthod accuracy.
     * Check if the bid is invalid if the action hasn't started yet. 
     */
    public void testIsBidUpdateValid_AuctionNotStartedYet() {
        CustomBid original = new CustomBid(1, 1, 1, new Date(System.currentTimeMillis() + 2000));
        original.setId(10);
        Auction auction = TestHelper.createAuction(1, 1, 2000, 4000, new Bid[] {original});
        CustomBid bid = new CustomBid(1, 1, 2, new Date(System.currentTimeMillis() + 2001));
        bid.setId(10);
        assertFalse("Bid should be invalid, auction didn't start yet.", 
                validator.isBidUpdateValid(auction, original, bid));
        
    }
    
    /**
     * Tests the {@link BidValidatorImpl#isBidUpdateValid(Auction, Bid, Bid)} mwthod accuracy.
     * Check if the bid is invalid if the action finished. 
     */
    public void testIsBidUpdateValid_AuctionFinished() {
        CustomBid original = new CustomBid(1, 1, 1, new Date(System.currentTimeMillis() - 1000));
        original.setId(10);
        Auction auction = TestHelper.createAuction(1, 1, -2000, -1000, new Bid[] {original});
        CustomBid bid = new CustomBid(1, 1, 2, new Date(System.currentTimeMillis() -900));
        bid.setId(10);
        assertFalse("Bid should be invalid, auction is over.", 
                validator.isBidUpdateValid(auction, original, bid));
        
    }
    
    /**
     * Tests the {@link BidValidatorImpl#isBidUpdateValid(Auction, Bid, Bid)} mwthod accuracy.
     * Check if the bid is invalid if the new max bid is same as previos one.
     */
    public void testIsBidUpdateValid_NewBidTooLow() {
        CustomBid original = new CustomBid(1, 1, 1, new Date());
        original.setId(10);
        Auction auction = TestHelper.createAuction(1, 1, -1000, 4000, new Bid[] {original});
        CustomBid bid = new CustomBid(1, 1, 1, new Date());
        bid.setId(10);
        assertFalse("Bid should be invalid, new bid too low.", 
                validator.isBidUpdateValid(auction, original, bid));
        
    }
    
    /**
     * Tests the {@link BidValidatorImpl#isBidUpdateValid(Auction, Bid, Bid)} mwthod accuracy.
     * Check if the bid is invalid if the original bid doesn't exists in auction.
     */
     public void testIsBidUpdateValid_OrginalBidDoesntExist() {
        CustomBid original = new CustomBid(1, 1, 1, new Date());
        original.setId(10);
        Auction auction = TestHelper.createAuction(1, 1, -1000, 4000, new Bid[] {});
        CustomBid bid = new CustomBid(1, 1, 1, new Date());
        bid.setId(10);
        assertFalse("Bid should be invalid, the original bid doesn't exists in action.", 
                validator.isBidUpdateValid(auction, original, bid));
        
    }
    
     /**
      * Tests the {@link BidValidatorImpl#isBidUpdateValid(Auction, Bid, Bid)} mwthod accuracy.
      * Check if the bid is invalid when the original bid and new bid have different IDs.
      */
     public void testIsBidUpdateValid_DifferentIDs() {
        CustomBid original = new CustomBid(1, 1, 1, new Date());
        original.setId(10);
        Auction auction = TestHelper.createAuction(1, 1, -1000, 4000, new Bid[] {original});
        CustomBid bid = new CustomBid(1, 1, 1, new Date());
        bid.setId(11);
        assertFalse("Bid should be invalid, different bid IDs.", 
                validator.isBidUpdateValid(auction, original, bid));
    }
    
     /**
      * Tests the {@link BidValidatorImpl#isBidUpdateValid(Auction, Bid, Bid)} mwthod accuracy.
      * Check if the bid is invalid when the original bid and new bid have different bidders.
      */
     public void testIsBidUpdateValid_DifferentBidders() {
        CustomBid original = new CustomBid(2, 1, 1, new Date());
        original.setId(10);
        Auction auction = TestHelper.createAuction(1, 1, -1000, 4000, new Bid[] {original});
        CustomBid bid = new CustomBid(1, 1, 1, new Date());
        bid.setId(10);
        assertFalse("Bid should be invalid, different bidders.", 
                validator.isBidUpdateValid(auction, original, bid));
        
    }
    
     /**
      * Tests the {@link BidValidatorImpl#isBidUpdateValid(Auction, Bid, Bid)} mwthod accuracy.
      * Check if the bid is invalid when the original bid and new bid have different image IDs.
      */
     public void testIsBidUpdateValid_DifferentImageIds() {
        CustomBid original = new CustomBid(1, 2, 1, new Date());
        original.setId(10);
        Auction auction = TestHelper.createAuction(1, 1, -1000, 4000, new Bid[] {original});
        CustomBid bid = new CustomBid(1, 1, 1, new Date());
        bid.setId(10);
        assertFalse("Bid should be invalid, different image IDs.", 
                validator.isBidUpdateValid(auction, original, bid));
        
    }

}
