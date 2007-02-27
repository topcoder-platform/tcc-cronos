/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.AuctionLocal;
import com.orpheus.auction.persistence.ejb.AuctionLocalHome;
import com.orpheus.auction.persistence.ejb.BidDTO;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * <p>
 * Implements the abstract ejbXXX methods to work with the local auction EJB. Simply defers all calls to the EJB. It
 * uses the ConfigManager and Object Factory to initialize the JNDI EJB reference to obtain the handle to the EJB
 * interface itself.
 * </p>
 * <p>
 * <b>Thread Safety: </b>This class is mutable.  It is thread safe to the extent that its superclass is thread safe,
 * and to the extent that concurrent access to multiple instances of the persistence EJB is thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class LocalCustomAuctionPersistence extends CustomAuctionPersistence {

    /**
     * <p>
     * Represents the property name to retrieve the jndiEjbReference value.
     * </p>
     */
    private static final String JNDI_EJB_REFERENCE_PROPERTY = "jndiEjbReference";

    /**
     * <p>
     * Represents the local ejb's home interface, from which an EJB instance is obtained for each calls. Created in
     * the consructor, will not be null, and will not change.
     * </p>
     */
    private final AuctionLocalHome auctionEJBHome;

    /**
     * <p>
     * Instantiates new LocalCustomAuctionPersistence instance from the given namespace. It will use ConfigManager and
     * ObjectFactory to instantiate a new AuctionTranslator and Cache objects. Will also obtain a reference to the
     * Auction EJB's local home interface.
     * </p>
     *
     * @param namespace configuration namespace.
     *
     * @throws IllegalArgumentException If namespace is null or empty.
     * @throws ObjectInstantiationException If there is an error with construction
     */
    public LocalCustomAuctionPersistence(String namespace) throws ObjectInstantiationException {
        super(namespace);

        // obtain jndi ejb reference from ConfigManager
        String jndiEjbReference = AuctionPersistenceHelper.getStringPropertyValue(namespace,
                JNDI_EJB_REFERENCE_PROPERTY, true);

        try {
            InitialContext ic = new InitialContext();

            auctionEJBHome = (AuctionLocalHome) ic.lookup(jndiEjbReference);
        } catch (NamingException e) {
            throw new ObjectInstantiationException("Fails to create the ejb.", e);
        }
    }

    /**
     * <p>
     * Creates the auction in persistence using the applicable EJB.
     * </p>
     *
     * @param auction the auction to create.
     *
     * @return the created auction.
     *
     * @throws InvalidEntryException If the passed auction does not contain an ID
     * @throws DuplicateEntryException If the persistent store already contains an auction with the specified ID.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    protected AuctionDTO ejbCreateAuction(AuctionDTO auction) throws PersistenceException {
        AuctionLocal auctionEJB = getEJB();

        try {
            return auctionEJB.createAuction(auction);
        } finally {
            removeEJB(auctionEJB);
        }
    }

    /**
     * <p>
     * Gets the auction for this ID from persistence using the applicable EJB.
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
    protected AuctionDTO ejbGetAuction(long auctionId) throws PersistenceException {
        AuctionLocal auctionEJB = getEJB();

        try {
            return auctionEJB.getAuction(auctionId);
        } finally {
            removeEJB(auctionEJB);
        }
    }

    /**
     * <p>
     * Updates the auction in persistence using the applicable EJB.
     * </p>
     *
     * @param auction the auction to update.
     *
     * @return updated the updated auction.
     *
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store.
     * @throws InvalidEntryException If the passed auction does not contain an ID.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    protected AuctionDTO ejbUpdateAuction(AuctionDTO auction) throws PersistenceException {
        AuctionLocal auctionEJB = getEJB();

        try {
            return auctionEJB.updateAuction(auction);
        } finally {
            removeEJB(auctionEJB);
        }
    }

    /**
     * <p>
     * Updates the bids for the given auctionId in persistence using the applicable EJB.
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
     *         auctionID.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    protected AuctionDTO ejbUpdateBids(long auctionId, BidDTO[] bids) throws PersistenceException {
        AuctionLocal auctionEJB = getEJB();

        try {
            return auctionEJB.updateBids(auctionId, bids);
        } finally {
            removeEJB(auctionEJB);
        }
    }

    /**
     * <p>
     * Deletes the auction with this ID from persistence using the applicable EJB.
     * </p>
     *
     * @param auctionId the auction id to delete.
     *
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    protected void ejbDeleteAuction(long auctionId) throws PersistenceException {
        AuctionLocal auctionEJB = getEJB();

        try {
            auctionEJB.deleteAuction(auctionId);
        } finally {
            removeEJB(auctionEJB);
        }
    }

    /**
     * <p>
     * Gets all auctions currently in persistence whose start date is not smaller than startingBay date parameter, and
     * whose end date is smaller than endingAfter date parameter, using the applicable EJB. Returns empty array if no
     * auctions found.
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
    protected AuctionDTO[] ejbFindAuctionsByDate(Date startingBy, Date endingAfter) throws PersistenceException {
        AuctionLocal auctionEJB = getEJB();

        try {
            return auctionEJB.findAuctionsByDate(startingBy, endingAfter);
        } finally {
            removeEJB(auctionEJB);
        }
    }

    /**
     * <p>
     * Gets all auctions currently in persistence for the given bidderId whose end date is smaller than endingAfter
     * date parameter, using the applicable EJB. Returns empty array if no auctions found.
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
    protected AuctionDTO[] ejbFindAuctionsByBidder(long bidderId, Date endingAfter) throws PersistenceException {
        AuctionLocal auctionEJB = getEJB();

        try {
            return auctionEJB.findAuctionsByBidder(bidderId, endingAfter);
        } finally {
            removeEJB(auctionEJB);
        }
    }

    /**
     * Obtains an EJB instance from the configured home interface
     *
     * @return an <code>AuctionLocal</code> instance
     *
     * @throws PersistenceException if no EJB instance can be obtained
     */
    private AuctionLocal getEJB() throws PersistenceException {
        try {
            return auctionEJBHome.create();
        } catch (CreateException ce) {
            throw new PersistenceException("Could not obtain EJB instance", ce);
        }
    }

    /**
     * Removes the specified EJB instance, signalling to the container that this object
     * is finished using it.  This method swallows any exception that may be thrown,
     * but does print stack traces of such exceptions to System.err
     *
     * @param auctionEJB the <code>AuctionLocal</code> instance to remove
     */
    private void removeEJB(AuctionLocal auctionEJB) {
        try {
            auctionEJB.remove();
        } catch (RemoveException re) {
            re.printStackTrace(System.err);

            // don't throw anything
        }
    }
}

