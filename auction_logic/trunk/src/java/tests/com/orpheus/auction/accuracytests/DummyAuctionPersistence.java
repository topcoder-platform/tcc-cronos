/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionPersistence;
import com.topcoder.util.auction.AuctionPersistenceException;
import com.topcoder.util.auction.Bid;

/**
 * The implementation of the AuctionPersistence interface for test proposes only.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class DummyAuctionPersistence implements AuctionPersistence {
    /**
     * The in-memory list of the auctions.
     */
    private List auctions = new ArrayList();

    /**
     * Simply stores the given auction in internal list.
     * 
     * @param auction the auction to persist.
     * @return the given auction.
     * 
     * @throws AuctionPersistenceException never.
     */
    public Auction createAuction(Auction auction) throws AuctionPersistenceException {
        auctions.add(auction);
        return auction;
    }

    /**
     * Not implemented.
     * 
     * @param auctionId auction id.
     * @throws AuctionPersistenceException never.
     */
    public void deleteAuction(long auctionId) throws AuctionPersistenceException {
        // TODO Auto-generated method stub

    }

    /**
     * Not implemented.
     * 
     * @param bidderId the bidder's id.
     * @param endingAfter the auction range
     * @return null.
     * 
     * @throws AuctionPersistenceException never.
     */
    public Auction[] findAuctionsByBidder(long bidderId, Date endingAfter) throws AuctionPersistenceException {
        return null;
    }

    /**
     * Returns all auctions if both parameters are null; otherwise it return empty arrat.
     * 
     * @param startingBy starting date
     * @param endingAfter ending date
     * @return Auctions array
     * 
     * @throws AuctionPersistenceException never.
     */
    public Auction[] findAuctionsByDate(Date startingBy, Date endingAfter) throws AuctionPersistenceException {
        if ((startingBy == null) && (endingAfter == null)) {
            return (Auction[]) auctions.toArray(new Auction[0]);
        }
        
        return new Auction[0];
    }

    /**
     * Returns auction with given id.
     * 
     * @param auctionId the id of the auction.
     * @return auction with given id or null if not found.
     * 
     * @throws AuctionPersistenceException never.
     */
    public Auction getAuction(long auctionId) throws AuctionPersistenceException {
        for (Iterator iter = auctions.iterator(); iter.hasNext();) {
            Auction element = (Auction) iter.next();
            if ((element.getId() != null) && (element.getId().longValue() == auctionId)) {
                return element;
            }
        }
        return null;
    }

    /**
     * Updateds the given auction in persistence.
     * 
     * @param auction auction to update.
     * @throws AuctionPersistenceException never.
     */
    public void updateAuction(Auction auction) throws AuctionPersistenceException {
        Auction old = getAuction(auction.getId() == null ? 0 : auction.getId().longValue());
        auctions.remove(old);
        auctions.add(auction);
    }

    /**
     * Not implemented.
     * 
     * @param auctionId the id of auction.
     * @param bids the array of Bids.
     * 
     * @throws AuctionPersistenceException never.
     */
    public void updateBids(long auctionId, Bid[] bids) throws AuctionPersistenceException {
        // TODO Auto-generated method stub

    }

}
