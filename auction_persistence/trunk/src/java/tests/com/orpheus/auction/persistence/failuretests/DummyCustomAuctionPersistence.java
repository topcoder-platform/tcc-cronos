/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import java.util.Date;

import com.orpheus.auction.persistence.CustomAuctionPersistence;
import com.orpheus.auction.persistence.DuplicateEntryException;
import com.orpheus.auction.persistence.EntryNotFoundException;
import com.orpheus.auction.persistence.InvalidEntryException;
import com.orpheus.auction.persistence.ObjectInstantiationException;
import com.orpheus.auction.persistence.PersistenceException;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

/**
 * <p>
 * Dummy implementation of <code>CustomAuctionPersistence</code> for testing purpose.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class DummyCustomAuctionPersistence extends CustomAuctionPersistence {

    /**
     * <p>
     * Creates the new instance of <code>CustomAuctionPersistence</code> class from the given namespace. It will use
     * ConfigManager and ObjectFactory to instantiate a new AuctionTranslator and Cache objects.
     * </p>
     * @param namespace
     *            configuration namespace.
     * @throws IllegalArgumentException
     *             If namespace is null or empty.
     * @throws ObjectInstantiationException
     *             If there is an error with construction.
     */
    protected DummyCustomAuctionPersistence(String namespace) throws ObjectInstantiationException {
        super(namespace);
    }

    /**
     * <p>
     * Creates the auction in persistence using the applicable EJB.
     * </p>
     * @param auction
     *            the auction to create.
     * @return the created auction.
     * @throws InvalidEntryException
     *             If the passed auction does not contain an ID.
     * @throws DuplicateEntryException
     *             If the persistent store already contains an auction with the specified ID.
     * @throws PersistenceException
     *             If there is any problem in the persistence layer. EJB exceptions will be wrapped here.
     */
    protected AuctionDTO ejbCreateAuction(AuctionDTO auction) throws PersistenceException {
        return null;
    }

    /**
     * <p>
     * Gets the auction for this ID from persistence using the applicable EJB.
     * </p>
     * @param auctionId
     *            the auction id to get.
     * @return the retrieved auction.
     * @throws EntryNotFoundException
     *             If there is not yet any persistent representation of the specified auction in the persistent store.
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    protected AuctionDTO ejbGetAuction(long auctionId) throws PersistenceException {
        return null;
    }

    /**
     * <p>
     * Updates the auction in persistence using the applicable EJB.
     * </p>
     * @param auction
     *            the auction to update.
     * @return updated the updated auction.
     * @throws EntryNotFoundException
     *             If there is not yet any persistent representation of the specified auction in the persistent store.
     * @throws InvalidEntryException
     *             If the passed auction does not contain an ID.
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    protected AuctionDTO ejbUpdateAuction(AuctionDTO auction) throws PersistenceException {
        return null;
    }

    /**
     * <p>
     * Updates the bids for the given auctionId in persistence using the applicable EJB.
     * </p>
     * @param auctionId
     *            auction id of the bids.
     * @param bids
     *            bids to update/create.
     * @return the updated auction.
     * @throws EntryNotFoundException
     *             If there is not yet any persistent representation of the specified auctionId or bidId in the
     *             persistent store.
     * @throws InvalidEntryException
     *             If a passed bid in the persistence store has an auctionId that is not the passed auctionID.
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    protected AuctionDTO ejbUpdateBids(long auctionId, BidDTO[] bids) throws PersistenceException {
        return null;
    }

    /**
     * <p>
     * Deletes the auction with this ID from persistence using the applicable EJB.
     * </p>
     * @param auctionId
     *            the auction id to delete.
     * @throws EntryNotFoundException
     *             If there is not yet any persistent representation of the specified auction in the persistent store.
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    protected void ejbDeleteAuction(long auctionId) throws PersistenceException {
    }

    /**
     * <p>
     * Gets all auctions currently in persistence whose start date is not smaller than startingBay date parameter, and
     * whose end date is smaller than endingAfter date parameter, using the applicable EJB. Returns empty array if no
     * auctions found.
     * </p>
     * <p>
     * If startingBy or endingAfter is null, ignore the parameter in comparisons.
     * </p>
     * @param startingBy
     *            search date limit on the earliest start time, inclusive.
     * @param endingAfter
     *            search date limit on the latest end time, exclusive.
     * @return the retrieved auctions instances.
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    protected AuctionDTO[] ejbFindAuctionsByDate(Date startingBy, Date endingAfter) throws PersistenceException {
        return null;
    }

    /**
     * <p>
     * Gets all auctions currently in persistence for the given bidderId whose end date is smaller than endingAfter date
     * parameter, using the applicable EJB. Returns empty array if no auctions found.
     * </p>
     * <p>
     * If endingAfter is null, ignore the parameter in comparisons.
     * </p>
     * @param bidderId
     *            id of bidder to get all auctions.
     * @param endingAfter
     *            search date limit on the latest end time, exclusive.
     * @return the retrieved auctions instances.
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    protected AuctionDTO[] ejbFindAuctionsByBidder(long bidderId, Date endingAfter) throws PersistenceException {
        return null;
    }

}
