/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.EJBObject;

import com.orpheus.auction.persistence.PersistenceException;


/**
 * <p>
 * This the remote interface used to talk to the AuctionBean. Supports all client operations.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface AuctionRemote extends EJBObject {
    /**
     * <p>
     * Creates the auction in persistence.
     * </p>
     *
     * @param auction the auction to create.
     *
     * @return the created auction.
     *
     * @throws IllegalArgumentException If auction is null.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws RemoteException If there is a network issue.
     */
    AuctionDTO createAuction(AuctionDTO auction) throws PersistenceException, RemoteException;

    /**
     * <p>
     * Gets the auction for this ID from persistence.
     * </p>
     *
     * @param auctionId the auction id to get.
     *
     * @return the retrieved auction.
     *
     * @throws PersistenceException If there is any problem in the persistence layer. EJB exceptions will be wrapped
     *         here.
     * @throws RemoteException If there is a network issue.
     */
    AuctionDTO getAuction(long auctionId) throws PersistenceException, RemoteException;

    /**
     * <p>
     * Updates the profile in persistence.
     * </p>
     *
     * @param auction the auction to update.
     *
     * @return updated the updated auction.
     *
     * @throws IllegalArgumentException If auction is null.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws RemoteException If there is a network issue.
     */
    AuctionDTO updateAuction(AuctionDTO auction) throws PersistenceException, RemoteException;

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
     * @throws IllegalArgumentException If bids is null or it contains null elements in the array.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws RemoteException If there is a network issue.
     */
    AuctionDTO updateBids(long auctionId, BidDTO[] bids) throws PersistenceException, RemoteException;

    /**
     * <p>
     * Deletes the auction with this ID from persistence.
     * </p>
     *
     * @param auctionId the auction id to delete.
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws RemoteException If there is a network issue.
     */
    void deleteAuction(long auctionId) throws PersistenceException, RemoteException;

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
     * @throws RemoteException If there is a network issue.
     */
    AuctionDTO[] findAuctionsByDate(Date startingBy, Date endingAfter) throws PersistenceException, RemoteException;

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
     * @throws RemoteException If there is a network issue.
     */
    AuctionDTO[] findAuctionsByBidder(long bidderId, Date endingAfter) throws PersistenceException, RemoteException;
}
