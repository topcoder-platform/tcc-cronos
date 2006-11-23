/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import com.orpheus.auction.persistence.AuctionDAO;
import com.orpheus.auction.persistence.AuctionDAOFactory;
import com.orpheus.auction.persistence.AuctionPersistenceHelper;
import com.orpheus.auction.persistence.ObjectInstantiationException;
import com.orpheus.auction.persistence.PersistenceException;

import java.util.Date;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


/**
 * <p>
 * The EJB that handles the actual client requests. It accepts all client operations, but simply delegates all
 * operations to the AuctionDAO it obtains from the AuctionDAOFactory.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This object is immutable and thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class AuctionBean implements SessionBean {
    /**
     * <p>
     * Represents the session context of this bean. It is used to indicate to the container if the bean wants to
     * rollback a transaction. This would usually occur if an application exception occurs. Set in the
     * setSessionContext() method by the container.
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public AuctionBean() {
    }

    /**
     * <p>
     * Creates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbCreate() {
    }

    /**
     * <p>
     * Creates the auction in persistence. Uses the AuctionDAOFactory to obtain the AuctionDAO to perform this action.
     * </p>
     *
     * @param auction the auction to create.
     *
     * @return the created auction.
     *
     * @throws IllegalArgumentException If auction is null.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public AuctionDTO createAuction(AuctionDTO auction) throws PersistenceException {
        AuctionPersistenceHelper.validateNotNull(auction, "auction");

        try {
            return getAuctionDAO().createAuction(auction);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Uses the AuctionDAOFactory to obtain the AuctionDAO to perform this action.
     * </p>
     *
     * @return the AuctionDAO instance.
     *
     * @throws PersistenceException if it fails to obtain the AuctionDAO instance.
     */
    private AuctionDAO getAuctionDAO() throws PersistenceException {
        try {
            return AuctionDAOFactory.getAuctionDAO();
        } catch (ObjectInstantiationException e) {
            throw new PersistenceException("Failed to get the auction dao from the auction dao factory.", e);
        }
    }

    /**
     * <p>
     * Gets the auction for this ID from persistence. Uses the AuctionDAOFActory to obtain the AuctionDAO to perform
     * this action.
     * </p>
     *
     * @param auctionId the auction id to get.
     *
     * @return the retrieved auction.
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public AuctionDTO getAuction(long auctionId) throws PersistenceException {
        try {
            return getAuctionDAO().getAuction(auctionId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Updates the auction in persistence. Uses the AuctionDAOFActory to obtain the AuctionDAO to perform this action.
     * </p>
     *
     * @param auction the auction to update.
     *
     * @return updated the updated auction.
     *
     * @throws IllegalArgumentException If auction is null.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public AuctionDTO updateAuction(AuctionDTO auction) throws PersistenceException {
        AuctionPersistenceHelper.validateNotNull(auction, "auction");

        try {
            return getAuctionDAO().updateAuction(auction);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Updates the bids for the given auctionId in persistence. Uses the AuctionDAOFActory to obtain the AuctionDAO to
     * perform this action.
     * </p>
     *
     * @param auctionId auction id of the bids.
     * @param bids bids to update/create.
     *
     * @return the updated auction.
     *
     * @throws IllegalArgumentException If bids is null or it contains null elements in the array.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public AuctionDTO updateBids(long auctionId, BidDTO[] bids) throws PersistenceException {
        AuctionPersistenceHelper.validateCollection(bids, "bids");

        try {
            return getAuctionDAO().updateBids(auctionId, bids);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Deletes the auction with this ID from persistence. Uses the AuctionDAOFActory to obtain the AuctionDAO to
     * perform this action.
     * </p>
     *
     * @param auctionId the auction id to delete.
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public void deleteAuction(long auctionId) throws PersistenceException {
        try {
            getAuctionDAO().deleteAuction(auctionId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Gets all auctions currently in persistence whose start date is not smaller than startingBay date parameter, and
     * whose end date is smaller than endingAfter date parameter. Returns empty array if no auctions found.
     * </p>
     *
     * <p>
     * If startingBy or endingAfter is null, ignores the parameter in comparisons. Uses the AuctionDAOFActory to obtain
     * the AuctionDAO to perform this action.
     * </p>
     *
     * @param startingBy search date limit on the earliest start time, inclusive.
     * @param endingAfter search date limit on the latest end time, exclusive.
     *
     * @return the retrieved auctions instances.
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public AuctionDTO[] findAuctionsByDate(Date startingBy, Date endingAfter) throws PersistenceException {
        try {
            return getAuctionDAO().findAuctionsByDate(startingBy, endingAfter);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Gets all auctions currently in persistence for the given bidderId whose end date is smaller than endingAfter
     * date parameter. Returns empty array if no auctions found.
     * </p>
     *
     * <p>
     * If endingAfter is null, ignores the parameter in comparisons. Uses the AuctionDAOFActory to obtain the
     * AuctionDAO to perform this action.
     * </p>
     *
     * @param bidderId id of bidder to get all auctions.
     * @param endingAfter search date limit on the latest end time, exclusive.
     *
     * @return the retrieved auctions instances.
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public AuctionDTO[] findAuctionsByBidder(long bidderId, Date endingAfter) throws PersistenceException {
        try {
            return getAuctionDAO().findAuctionsByBidder(bidderId, endingAfter);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Removes the bean. This is an empty implementation.
     * </p>
     */
    public void ejbRemove() {
    }

    /**
     * <p>
     * Activates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbActivate() {
    }

    /**
     * <p>
     * Passivates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbPassivate() {
    }

    /**
     * <p>
     * Sets the session context.
     * </p>
     *
     * @param ctx session context
     */
    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
    }
}
