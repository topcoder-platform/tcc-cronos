/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.AuctionRemote;
import com.orpheus.auction.persistence.ejb.AuctionRemoteHome;
import com.orpheus.auction.persistence.ejb.BidDTO;

import java.rmi.RemoteException;

import java.util.Date;

import javax.ejb.CreateException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.rmi.PortableRemoteObject;


/**
 * <p>
 * Implements the abstract ejbXXX methods to work with the remote auction EJB. Simply defers all calls to the EJB. It
 * uses the ConfigManager and Object Factory to initialize the JNDI EJB reference to obtain the handle to the EJB
 * interface itself.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is mutable and thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class RemoteCustomAuctionPersistence extends CustomAuctionPersistence {

    /**
     * <p>
     * Represents the property name to retrieve the jndiEjbReference value.
     * </p>
     */
    private static final String JNDI_EJB_REFERENCE_PROPERTY = "jndiEjbReference";

    /**
     * <p>
     * Represents the remote ejb instance used for all calls. Created in the consructor, will not be null, and will not
     * change.
     * </p>
     */
    private final AuctionRemote auctionEJB;

    /**
     * <p>
     * Instantiates new RemoteCustomAuctionPersistence instance from the given namespace. It will use ConfigManager and
     * ObjectFactory to instantiate a new AuctionTranslator and Cache objects. Will also obtain a reference to the EJB
     * AuctionRemote.
     * </p>
     *
     * @param namespace configuration namespace
     *
     * @throws IllegalArgumentException If namespace is null or empty.
     * @throws ObjectInstantiationException If there is an error with construction.
     */
    public RemoteCustomAuctionPersistence(String namespace) throws ObjectInstantiationException {
        super(namespace);

        // obtain jndi ejb reference from ConfigManager
        String jndiEjbReference = AuctionPersistenceHelper.getStringPropertyValue(namespace,
                JNDI_EJB_REFERENCE_PROPERTY, true);

        try {
            InitialContext ic = new InitialContext();
            Object lookup = ic.lookup(jndiEjbReference);
            AuctionRemoteHome home = (AuctionRemoteHome) PortableRemoteObject.narrow(lookup, AuctionRemoteHome.class);
            auctionEJB = home.create();
        } catch (NamingException e) {
            throw new ObjectInstantiationException("Fails to create the ejb.", e);
        } catch (RemoteException e) {
            throw new ObjectInstantiationException("Fails to create the ejb.", e);
        } catch (CreateException e) {
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
     * @throws PersistenceException If there is any problem in the persistence layer. EJB exceptions will be wrapped
     *         here.
     */
    protected AuctionDTO ejbCreateAuction(AuctionDTO auction) throws PersistenceException {
        try {
            return auctionEJB.createAuction(auction);
        } catch (RemoteException e) {
            throw new PersistenceException("Error occurs when create the auction.", e);
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
     * @throws PersistenceException If there is any problem in the persistence layer. EJB exceptions will be wrapped
     *         here.
     */
    protected AuctionDTO ejbGetAuction(long auctionId) throws PersistenceException {
        try {
            return auctionEJB.getAuction(auctionId);
        } catch (RemoteException e) {
            throw new PersistenceException("Error occurs when get the auction.", e);
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
     * @throws InvalidEntryException If the passed auction does not contain an ID.
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store.
     * @throws PersistenceException If there is any problem in the persistence layer. EJB exceptions will be wrapped
     *         here.
     */
    protected AuctionDTO ejbUpdateAuction(AuctionDTO auction) throws PersistenceException {
        try {
            return auctionEJB.updateAuction(auction);
        } catch (RemoteException e) {
            throw new PersistenceException("Error occurs when update the auction.", e);
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
     * @throws PersistenceException If there is any problem in the persistence layer. EJB exceptions will be wrapped
     *         here.
     */
    protected AuctionDTO ejbUpdateBids(long auctionId, BidDTO[] bids) throws PersistenceException {
        try {
            return auctionEJB.updateBids(auctionId, bids);
        } catch (RemoteException e) {
            throw new PersistenceException("Error occurs when update the bids.", e);
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
     * @throws PersistenceException If there is any problem in the persistence layer. EJB exceptions will be wrapped
     *         here.
     */
    protected void ejbDeleteAuction(long auctionId) throws PersistenceException {
        try {
            auctionEJB.deleteAuction(auctionId);
        } catch (RemoteException e) {
            throw new PersistenceException("Error occurs when delete the auctions.", e);
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
     * @throws PersistenceException If there is any problem in the persistence layer. EJB exceptions will be wrapped
     *         here.
     */
    protected AuctionDTO[] ejbFindAuctionsByDate(Date startingBy, Date endingAfter) throws PersistenceException {
        try {
            return auctionEJB.findAuctionsByDate(startingBy, endingAfter);
        } catch (RemoteException e) {
            throw new PersistenceException("Error occurs when find the auctions.", e);
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
     * @throws PersistenceException If there is any problem in the persistence layer. EJB exceptions will be wrapped
     *         here.
     */
    protected AuctionDTO[] ejbFindAuctionsByBidder(long bidderId, Date endingAfter) throws PersistenceException {
        try {
            return auctionEJB.findAuctionsByBidder(bidderId, endingAfter);
        } catch (RemoteException e) {
            throw new PersistenceException("Error occurs when find the auctions.", e);
        }
    }
}
