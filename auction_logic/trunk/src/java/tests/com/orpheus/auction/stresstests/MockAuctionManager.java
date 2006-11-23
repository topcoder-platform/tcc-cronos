/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.stresstests;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionException;
import com.topcoder.util.auction.AuctionListener;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.AuctionPersistence;
import com.topcoder.util.auction.AuctionStrategy;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.BidValidator;

/**
 * <p>
 * A mock implementation of the AuctionManager interface. Allows to set the return values for some
 * methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuctionManager implements AuctionManager {

    /**
     * <p>
     * The AuctionPersistence instance that will be returned in getAuctionPersistence() method.
     * </p>
     */
    private AuctionPersistence auctionPersistence;

    /**
     * <p>
     * The AuctionStrategy instance that will be returned in getAuctionStrategy() method.
     * </p>
     */
    private AuctionStrategy auctionStrategy;

    /**
     * <p>
     * Auction for the last bid update.
     * </p>
     */
    private long auctionForLastBidUpdate = 0;

    /**
     * <p>
     * Auction for the last new bid.
     * </p>
     */
    private long auctionForLastNewBid = 0;

    /**
     * <p>
     * Original bid for the last bid update.
     * </p>
     */
    private Bid originalBidForLastBidUpdate = null;

    /**
     * <p>
     * New bid for the last bid update.
     * </p>
     */
    private Bid newBidForLastBidUpdate = null;

    /**
     * <p>
     * Last new bid.
     * </p>
     */
    private Bid lastNewBid = null;

    /**
     * <p>
     * Method stub. Returns auctionPersistence.
     * </p>
     */
    public AuctionPersistence getAuctionPersistence() {
        return auctionPersistence;
    }

    /**
     * <p>
     * Sets auctionPersistence.
     * </p>
     * @param auctionPersistence
     *            the value that will be returned in getAuctionPersistence method.
     */
    public void setAuctionPersistence(AuctionPersistence auctionPersistence) {
        this.auctionPersistence = auctionPersistence;
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     */
    public BidValidator getBidValidator() {
        return null;
    }

    /**
     * <p>
     * Method stub. Returns auctionStrategy.
     * </p>
     */
    public AuctionStrategy getAuctionStrategy() {
        return auctionStrategy;
    }

    /**
     * <p>
     * Sets auctionStrategy.
     * </p>
     * @param auctionStrategy
     *            the value that will be returned in getAuctionStrategy method.
     */
    public void setAuctionStrategy(AuctionStrategy auctionStrategy) {
        this.auctionStrategy = auctionStrategy;
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     */
    public Auction createAuction(Auction auction) throws AuctionException {
        return null;
    }

    /**
     * <p>
     * Method stub. Saves auction and bid. Returns null.
     * </p>
     */
    public Auction acceptNewBid(long auction, Bid bid) throws AuctionException {
        auctionForLastNewBid = auction;
        lastNewBid = bid;

        return null;
    }

    /**
     * <p>
     * Returns auction for the last new bid.
     * </p>
     * @return auction for the last new bid.
     */
    public long getAuctionForLastNewBid() {
        return auctionForLastNewBid;
    }

    /**
     * <p>
     * Returns last new bid.
     * </p>
     * @return last new bid.
     */
    public Bid getLastNewBid() {
        return lastNewBid;
    }

    /**
     * <p>
     * Method stub. Saves the last accepted bid. Returns null.
     * </p>
     */
    public Auction acceptBidUpdate(long auction, Bid originalBid, Bid newBid) throws AuctionException {
        auctionForLastBidUpdate = auction;
        originalBidForLastBidUpdate = originalBid;
        newBidForLastBidUpdate = newBid;

        return null;
    }

    /**
     * <p>
     * Returns auction for the last bid update.
     * </p>
     * @return auction for the last bid update.
     */
    public long getAuctionForLastBidUpdate() {
        return auctionForLastBidUpdate;
    }

    /**
     * <p>
     * Returns original bid for the last bid update.
     * </p>
     * @return original bid for the last bid update.
     */
    public Bid getOriginalBidForLastBidUpdate() {
        return originalBidForLastBidUpdate;
    }

    /**
     * <p>
     * Returns new bid for the last bid update.
     * </p>
     * @return new bid for the last bid update.
     */
    public Bid getNewBidForLastBidUpdate() {
        return newBidForLastBidUpdate;
    }

    /**
     * <p>
     * Method stub. Does nothing.
     * </p>
     */
    public void addAuctionListener(AuctionListener listener) {
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     */
    public AuctionListener[] getAuctionListeners() {
        return null;
    }

    /**
     * <p>
     * Method stub. Does nothing.
     * </p>
     */
    public void removeAuctionListener(AuctionListener listener) {
    }

    /**
     * <p>
     * Method stub. Does nothing.
     * </p>
     */
    public void removeAllAuctionListeners() {
    }
}
