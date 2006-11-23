/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.stresstests;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionPersistence;
import com.topcoder.util.auction.Bid;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * A mock implementation of the AuctionPersistence interface. Uses map to store auctions.
 * </p>
 * @author waits
 * @version 1.0
 */
public class MockAuctionPersistence implements AuctionPersistence {
    /** Maps the auction ids to the Auction instances. Initialized in place. Can be changed in addAuction method. */
    private Map auctions = new HashMap();

    /**
     * Method stub.
     *
     * @param auction auction
     *
     * @return null
     */
    public Auction createAuction(Auction auction) {
        return null;
    }

    /**
     * Method stub.
     *
     * @param auctionId id
     *
     * @return auction instance
     */
    public Auction getAuction(long auctionId) {
        return (Auction) auctions.get(new Long(auctionId));
    }

    /**
     * Method stub. Does nothing.
     *
     * @param auction auction
     */
    public void updateAuction(Auction auction) {
    }

    /**
     * Method stub. Does nothing.
     *
     * @param auctionId id
     * @param bids bidid
     */
    public void updateBids(long auctionId, Bid[] bids) {
    }

    /**
     * Method stub. Does nothing.
     *
     * @param auctionId id
     */
    public void deleteAuction(long auctionId) {
    }

    /**
     * Method stub. Retrieves the auctions from map persistence whose start and end times lie within the given range.
     *
     * @param startingBy startingBy
     * @param endingAfter endingAfter
     *
     * @return auction array
     */
    public Auction[] findAuctionsByDate(Date startingBy, Date endingAfter) {
        List res = new LinkedList();

        for (Iterator i = auctions.values().iterator(); i.hasNext();) {
            Auction auction = (Auction) i.next();

            if ((startingBy != null) && (auction.getStartDate().compareTo(startingBy) < 0)) {
                continue;
            }

            if ((endingAfter != null) && (auction.getEndDate().compareTo(endingAfter) > 0)) {
                continue;
            }

            res.add(auction);
        }

        return (Auction[]) res.toArray(new Auction[res.size()]);
    }

    /**
     * Method stub.
     *
     * @param bidderId bidderId
     * @param endingAfter endingAfter
     *
     * @return null
     */
    public Auction[] findAuctionsByBidder(long bidderId, Date endingAfter) {
        return null;
    }

    /**
     * Adds given auction to the persistence.
     *
     * @param auction the auction to add.
     */
    public void addAuction(Auction auction) {
        auctions.put(auction.getId(), auction);
    }
}
