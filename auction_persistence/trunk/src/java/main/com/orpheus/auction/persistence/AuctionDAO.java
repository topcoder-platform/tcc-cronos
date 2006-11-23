/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

import java.util.Date;


/**
 * <p>
 * Interface specifying the methods for Auction persistence. Works with the DTO version of the Auction. Supports all
 * methods in the client.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Implementations should strive to be thread-safe, but they can expect to be used in a
 * thread-safe manner.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface AuctionDAO {
    /**
     * <p>
     * Creates the auction in persistence.
     * </p>
     *
     * @param auction the auction to create.
     *
     * @return the created auction.
     *
     * @throws InvalidEntryException If the passed auction does not contain an ID, or hosting_block does not have such
     *         an Id, or the bidderId of any bidder does not exist.
     * @throws DuplicateEntryException If the persistent store already contains an auction with the specified ID.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    AuctionDTO createAuction(AuctionDTO auction) throws PersistenceException;

    /**
     * <p>
     * Gets the auction for this ID from persistence.
     * </p>
     *
     * @param auctionId the auction id to get.
     *
     * @return the retrieved auction.
     *
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    AuctionDTO getAuction(long auctionId) throws PersistenceException;

    /**
     * <p>
     * Updates the auction in persistence.
     * </p>
     *
     * @param auction the auction to update.
     *
     * @return updated the updated auction.
     *
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store, or if a passed bid in the persistence store has an auctionId that is not the passed
     *         auctionID.
     * @throws InvalidEntryException If the passed auction does not contain an ID, or the bidderId of any bidder does
     *         not exist.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    AuctionDTO updateAuction(AuctionDTO auction) throws PersistenceException;

    /**
     * <p>
     * Updates the bids for the given auctionId in persistence.
     * </p>
     *
     * @param auctionId auction id of the bids.
     * @param bids bids to update/create.
     *
     * @return the updated auction.
     *
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auctionId or
     *         bidId in the persistent store.
     * @throws InvalidEntryException If a passed bid in the persistence store has an auctionId that is not the passed
     *         auctionID, or the bidderId of any bidder does not exist.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    AuctionDTO updateBids(long auctionId, BidDTO[] bids) throws PersistenceException;

    /**
     * <p>
     * Deletes the auction with this ID from persistence.
     * </p>
     *
     * @param auctionId the auction id to delete.
     *
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    void deleteAuction(long auctionId) throws PersistenceException;

    /**
     * <p>
     * Gets all auctions currently in persistence whose start date is not smaller than startingBay date parameter, and
     * whose end date is smaller than endingAfter date parameter. Returns empty array if no auctions found.
     * </p>
     *
     * <p>
     * If startingBy or endingAfter is null, ignores the parameter in comparisons.
     * </p>
     *
     * @param startingBy search date limit on the earliest start time, inclusive.
     * @param endingAfter search date limit on the latest end time, exclusive.
     *
     * @return the retrieved auctions instances.
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    AuctionDTO[] findAuctionsByDate(Date startingBy, Date endingAfter) throws PersistenceException;

    /**
     * <p>
     * Gets all auctions currently in persistence for the given bidderId whose end date is smaller than endingAfter
     * date parameter. Returns empty array if no auctions found.
     * </p>
     *
     * <p>
     * If endingAfter is null, ignores the parameter in comparisons.
     * </p>
     *
     * @param bidderId id of bidder to get all auctions.
     * @param endingAfter search date limit on the latest end time, exclusive.
     *
     * @return the retrieved auctions instances.
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    AuctionDTO[] findAuctionsByBidder(long bidderId, Date endingAfter) throws PersistenceException;
}
