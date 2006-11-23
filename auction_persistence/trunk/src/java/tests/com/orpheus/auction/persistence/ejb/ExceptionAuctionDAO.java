/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import com.orpheus.auction.persistence.AuctionDAO;
import com.orpheus.auction.persistence.PersistenceException;

import java.util.Date;


/**
 * <p>
 * A mock class which extends AuctionDAO for testing. In this mock class, the implemented methods will do nothing and
 * just throw the PersistenceException.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ExceptionAuctionDAO implements AuctionDAO {
    /**
     * Always throw PersistenceException.
     *
     * @param auction ignored.
     *
     * @return always null.
     *
     * @throws PersistenceException always.
     */
    public AuctionDTO createAuction(AuctionDTO auction) throws PersistenceException {
        throw new PersistenceException("Angry.");
    }

    /**
     * Always throw PersistenceException.
     *
     * @param auctionId ignored.
     *
     * @return always null.
     *
     * @throws PersistenceException always.
     */
    public AuctionDTO getAuction(long auctionId) throws PersistenceException {
        throw new PersistenceException("Angry.");
    }

    /**
     * Always throw PersistenceException.
     *
     * @param auction ignored.
     *
     * @return always null.
     *
     * @throws PersistenceException always.
     */
    public AuctionDTO updateAuction(AuctionDTO auction) throws PersistenceException {
        throw new PersistenceException("Angry.");
    }

    /**
     * Always throw PersistenceException.
     *
     * @param auctionId ignored.
     * @param bids ignored.
     *
     * @return always null.
     *
     * @throws PersistenceException always.
     */
    public AuctionDTO updateBids(long auctionId, BidDTO[] bids) throws PersistenceException {
        throw new PersistenceException("Angry.");
    }

    /**
     * Always throw PersistenceException.
     *
     * @param auctionId ignored.
     *
     * @throws PersistenceException always.
     */
    public void deleteAuction(long auctionId) throws PersistenceException {
        throw new PersistenceException("Angry.");
    }

    /**
     * Always throw PersistenceException.
     *
     * @param startingBy ignored.
     * @param endingAfter ignored.
     *
     * @return always null.
     *
     * @throws PersistenceException always.
     */
    public AuctionDTO[] findAuctionsByDate(Date startingBy, Date endingAfter) throws PersistenceException {
        throw new PersistenceException("Angry.");
    }

    /**
     * Always throw PersistenceException.
     *
     * @param bidderId ignored.
     * @param endingAfter ignored.
     *
     * @return always null.
     *
     * @throws PersistenceException always.
     */
    public AuctionDTO[] findAuctionsByBidder(long bidderId, Date endingAfter) throws PersistenceException {
        throw new PersistenceException("Angry.");
    }
}
