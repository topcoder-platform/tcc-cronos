/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.stresstests;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionStrategy;
import com.topcoder.util.auction.Bid;

/**
 * <p>
 * A mock implementation of the AuctionStrategy interface. Allows to set the return values for some
 * methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuctionStrategy implements AuctionStrategy {

    /**
     * <p>
     * Leading bids.
     * </p>
     */
    private Bid[] bids = null;

    /**
     * <p>
     * Method stub. Returns 0.
     * </p>
     */
    public int getMinimumIncrement(Auction auction) {
        return 0;
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     */
    public Auction processNewBid(Auction auction, Bid bid) {
        return null;
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     */
    public Auction processBidUpdate(Auction auction, Bid originalBid, Bid newBid) {
        return null;
    }

    /**
     * <p>
     * Method stub. Returns bids.
     * </p>
     */
    public Bid[] selectLeadingBids(Auction auction) {
        return bids;
    }

    /**
     * <p>
     * Set leading bids.
     * </p>
     * @param bids auctions' bids.
     */
    public void setLeadingBids(Bid[] bids) {
        this.bids = bids;
    }
}
