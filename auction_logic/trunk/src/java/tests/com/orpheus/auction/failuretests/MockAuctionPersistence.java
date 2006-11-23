/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.failuretests;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionPersistence;
import com.topcoder.util.auction.AuctionPersistenceException;
import com.topcoder.util.auction.Bid;

/**
 * <p>
 * A mock implementation of the AuctionPersistence interface. Uses map to store auctions.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuctionPersistence implements AuctionPersistence {

    /**
     * <p>
     * Maps the auction ids to the Auction instances. Initialized in place. Can be changed in
     * addAuction method.
     * </p>
     */
    private Map auctions = new HashMap();

    /**
     * Whether an exception should be thrown.
     */
    private boolean failed = false;

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     * @param auction
     *            ignore
     * @return ignore
     * @throws AuctionPersistenceException
     *             ignore
     */
    public Auction createAuction(Auction auction) throws AuctionPersistenceException {
        return null;
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     * @param auctionId
     *            ignore
     * @return ignore
     * @throws AuctionPersistenceException
     *             ignore
     */
    public Auction getAuction(long auctionId) throws AuctionPersistenceException {
        if (failed) {
            throw new AuctionPersistenceException();
        }

        return (Auction) auctions.get(new Long(auctionId));
    }

    /**
     * <p>
     * Method stub. Does nothing.
     * </p>
     * @param auction
     *            ignore
     * @throws AuctionPersistenceException
     *             ignore
     */
    public void updateAuction(Auction auction) throws AuctionPersistenceException {
    }

    /**
     * <p>
     * Method stub. Does nothing.
     * </p>
     * @param auctionId
     *            ignore
     * @param bids
     *            ignore
     * @throws AuctionPersistenceException
     *             ignore
     */
    public void updateBids(long auctionId, Bid[] bids) throws AuctionPersistenceException {
    }

    /**
     * <p>
     * Method stub. Does nothing.
     * </p>
     * @param auctionId
     *            ignore
     * @throws AuctionPersistenceException
     *             ignore
     */
    public void deleteAuction(long auctionId) throws AuctionPersistenceException {
    }

    /**
     * Sets whether an exception should be thrown.
     * @param failed
     *            whether an exception should be thrown
     */
    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    /**
     * <p>
     * Method stub. Retrieves the auctions from map persistence whose start and end times lie
     * within the given range.
     * </p>
     * @param startingBy
     *            ignore
     * @param endingAfter
     *            ignore
     * @return ignore
     * @throws AuctionPersistenceException
     *             ignore
     */
    public Auction[] findAuctionsByDate(Date startingBy, Date endingAfter) throws AuctionPersistenceException {
        if (failed) {
            throw new AuctionPersistenceException();
        }

        List res = new LinkedList();
        for (Iterator i = auctions.values().iterator(); i.hasNext();) {
            Auction auction = (Auction) i.next();
            if (startingBy != null && auction.getStartDate().compareTo(startingBy) < 0) {
                continue;
            }
            if (endingAfter != null && auction.getEndDate().compareTo(endingAfter) > 0) {
                continue;
            }
            res.add(auction);
        }
        return (Auction[]) res.toArray(new Auction[res.size()]);
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     * @param bidderId
     *            ignore
     * @param endingAfter
     *            ignore
     * @return ignore
     * @throws AuctionPersistenceException
     *             ignore
     */
    public Auction[] findAuctionsByBidder(long bidderId, Date endingAfter) throws AuctionPersistenceException {
        return null;
    }

    /**
     * <p>
     * Adds given auction to the persistence.
     * </p>
     * @param auction
     *            the auction to add.
     */
    public void addAuction(Auction auction) {
        auctions.put(auction.getId(), auction);
    }
}
