/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionPersistence;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.cache.Cache;
import com.topcoder.util.objectfactory.ObjectFactory;

import java.util.Date;


/**
 * <p>
 * This is the auction persistence client to the EJB layer.
 * </p>
 *
 * <p>
 * This class implements the <code>AuctionPersistence</code> and supports all operations. It maintains a cache for
 * faster performance. It uses the ConfigManager and Object Factory to initialize itself. It is built to work with
 * EJBs, and this class leaves it to implementations to specify the EJBs. Hence the abstract ejbXXX methods. The
 * public methods defer to these for actual persistence calls. It delegates to <code>AuctionTranslator</code> instance
 * to perform translations between the Auction Frameworks <code>Auction</code> and <code>Bid</code> instances, and
 * their equivalent transport entities: <code>AuctionDTO</code> and <code>BidDTO</code>.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is mutable and thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public abstract class CustomAuctionPersistence implements AuctionPersistence {

    /**
     * <p>
     * Represents the property name to retrieve the specNamespace value.
     * </p>
     */
    private static final String SPEC_NAMESPACE_PROPERTY = "specNamespace";

    /**
     * <p>
     * Represents the property name to retrieve the translatorKey value.
     * </p>
     */
    private static final String TRANSLATOR_KEY_PROPERTY = "translatorKey";

    /**
     * <p>
     * Represents the property name to retrieve the cacheKey value.
     * </p>
     */
    private static final String CACHE_KEY_PROPERTY = "cacheKey";

    /**
     * <p>
     * Represents the auction translator instance used for translating between <code>Auction</code> and
     * <code>AuctionDTO</code>, and <code>Bid</code> and <code>BidDTO</code>.
     * </p>
     *
     * <p>
     * Will be set in constructor, will not be null, and will never change.
     * </p>
     */
    private final AuctionTranslator auctionTranslator;

    /**
     * <p>
     * Represents the cache of Auctions. The keys will be the auctionId field.
     * </p>
     *
     * <p>
     * This is created in the constructor, will not be null, and will never change.
     * </p>
     */
    private final Cache cache;

    /**
     * <p>
     * Creates the new instance of <code>CustomAuctionPersistence</code> class from the given namespace. It will use
     * ConfigManager and ObjectFactory to instantiate a new AuctionTranslator and Cache objects.
     * </p>
     *
     * @param namespace configuration namespace.
     *
     * @throws IllegalArgumentException If namespace is null or empty.
     * @throws ObjectInstantiationException If there is an error with construction.
     */
    protected CustomAuctionPersistence(String namespace) throws ObjectInstantiationException {
        // create the ObjectFactory with the namespace obtained from ConfigManager
        String specNamespace = AuctionPersistenceHelper.getStringPropertyValue(namespace,
                SPEC_NAMESPACE_PROPERTY, true);
        ObjectFactory objectFactory = AuctionPersistenceHelper.createObjectFactory(specNamespace);

        // obtain the keys for the auction translator and cache form ConfgManager
        String translatorKey = AuctionPersistenceHelper.getStringPropertyValue(namespace,
                TRANSLATOR_KEY_PROPERTY, true);
        String cacheKey = AuctionPersistenceHelper.getStringPropertyValue(namespace,
                CACHE_KEY_PROPERTY, true);

        // create the AuctionTranslator and Cache objects from ObjectFactory
        this.auctionTranslator = (AuctionTranslator) AuctionPersistenceHelper.createObject(objectFactory,
                translatorKey, AuctionTranslator.class);
        this.cache = (Cache) AuctionPersistenceHelper.createObject(objectFactory, cacheKey, Cache.class);
    }

    /**
     * <p>
     * Creates the auction in persistence. It first checks if the auction already exists in the cache. If not, it will
     * translate the auction into the AuctionDTO object suitable for transport, and delegate actual execution to the
     * ejbCreateAuction method.
     * </p>
     *
     * <p>
     * If operation is successful, it puts the retrieved and translated Auction in the cache with its id as the key.
     * </p>
     *
     * @param auction the auction to create.
     *
     * @return the created auction.
     *
     * @throws IllegalArgumentException If the given auction is null.
     * @throws InvalidEntryException If the passed auction does not contain an ID.
     * @throws DuplicateEntryException If the persistent store already contains an auction with the specified ID.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws TranslationException if it fails to translate the Auction or AuctionDTO instances.
     */
    public Auction createAuction(Auction auction) throws PersistenceException, TranslationException {
        AuctionPersistenceHelper.validateNotNull(auction, "auction");

        if (auction.getId() == null) {
            throw new InvalidEntryException("The auction does not contain an id.", auction.getId());
        }

        // check if already in cache
        Object obj = cache.get(auction.getId());

        if (obj != null) {
            throw new DuplicateEntryException("The auction already exists.", obj);
        }

        AuctionDTO auctionDTO = auctionTranslator.assembleAuctionDTO(auction);
        AuctionDTO retrievedAuction = ejbCreateAuction(auctionDTO);
        auction = auctionTranslator.assembleAuction(retrievedAuction);
        cache.put(auction.getId(), auction);

        return auction;
    }

    /**
     * <p>
     * Gets the auction for this ID from persistence. It will first check if there is an entry in the cache for this
     * auctionId. If not, it will delegate actual execution to the ejbGetAuction method, and translate the retrieved
     * AuctionDTO into the Auction object.
     * </p>
     *
     * <p>
     * If operation is successful, it puts the auction in the cache with its id as the key.
     * </p>
     *
     * @param auctionId the auction id to get.
     *
     * @return the retrieved auction.
     *
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws TranslationException if it fails to translate the Auction or AuctionDTO instances.
     */
    public Auction getAuction(long auctionId) throws PersistenceException, TranslationException {
        Auction auction = (Auction) cache.get(new Long(auctionId));

        if (auction == null) {
            AuctionDTO auctionDTO = ejbGetAuction(auctionId);
            auction = auctionTranslator.assembleAuction(auctionDTO);
            cache.put(auction.getId(), auction);
        }

        return auction;
    }

    /**
     * <p>
     * Updates the auction in persistence. It will translate the auction into the AuctionDTO object suitable for
     * transport, and delegate actual execution to the ejbUpdateAuction method.
     * </p>
     *
     * <p>
     * If operation is successful, it puts the retrieved and translated Auction in the cache with its id as the key.
     * The reason it needs the retrieved auction is so all bids are included (even those not part of the passed
     * auction)
     * </p>
     *
     * @param auction the auction to update.
     *
     * @throws IllegalArgumentException If the given auction is null.
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store.
     * @throws InvalidEntryException If the passed auction does not contain an ID.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws TranslationException if it fails to translate the Auction or AuctionDTO instances.
     */
    public void updateAuction(Auction auction) throws PersistenceException, TranslationException {
        AuctionPersistenceHelper.validateNotNull(auction, "auction");

        if (auction.getId() == null) {
            throw new InvalidEntryException("The auction does not contain an id.", auction.getId());
        }

        AuctionDTO auctionDTO = auctionTranslator.assembleAuctionDTO(auction);
        ejbUpdateAuction(auctionDTO);

        Auction retrievedAuction = auctionTranslator.assembleAuction(auctionDTO);
        cache.put(retrievedAuction.getId(), retrievedAuction);
    }

    /**
     * <p>
     * Updates the bids for the given auctionId in persistence. It will translate the bids into a BidDTO[] suitable for
     * transport, and delegate actual execution to the ejbUpdateBids method. If the bid is new, it will be added.
     * </p>
     *
     * <p>
     * If operation is successful, it puts the retrieved and translated Auction in the cache with the given auctionId
     * as the key. The reason it needs the retrieved auction is so all bids are included (even those not part of the
     * passed bid array)
     * </p>
     *
     * @param auctionId auction id of the bids.
     * @param bids bids to update/create.
     *
     * @throws IllegalArgumentException If the given bids is null, or the array contains null elements.
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auctionId or
     *         bidId in the persistent store.
     * @throws InvalidEntryException If a passed bid in the persistence store has an auctionId that is not the passed
     *         auctionID.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws TranslationException if it fails to translate the Auction or AuctionDTO instances.
     */
    public void updateBids(long auctionId, Bid[] bids) throws PersistenceException, TranslationException {
        AuctionPersistenceHelper.validateNotNull(bids, "bids");

        BidDTO[] bidDTOs = new BidDTO[bids.length];

        for (int i = 0; i < bids.length; i++) {
            bidDTOs[i] = auctionTranslator.assembleBidDTO(bids[i]);
        }

        AuctionDTO auctionDTO = ejbUpdateBids(auctionId, bidDTOs);
        Auction auction = auctionTranslator.assembleAuction(auctionDTO);
        cache.put(auction.getId(), auction);
    }

    /**
     * <p>
     * Deletes the auction with this ID from persistence. It will delegate actual execution to the ejbDeleteAuction
     * method. It also removes the entry from the cache.
     * </p>
     *
     * @param auctionId the auction id to delete.
     *
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public void deleteAuction(long auctionId) throws PersistenceException {
        ejbDeleteAuction(auctionId);
        cache.remove(new Long(auctionId));
    }

    /**
     * <p>
     * Gets all auctions currently in persistence whose start date is not smaller than startingBay date parameter, and
     * whose end date is smaller than endingAfter date parameter. Returns empty array if no auctions found. If
     * startingBy or endingAfter is null, ignores the parameter in comparisons.
     * </p>
     *
     * <p>
     * It will delegate actual execution to the ejbFindAuctionsByDate. It makes no sense to try to match cached items
     * with persisted items, se we get all directly from persistence, and then cache all retrieved and translated
     * Auctions. Returns empty array if no auctions found.
     * </p>
     *
     * @param startingBy search date limit on the earliest start time, inclusive.
     * @param endingAfter search date limit on the latest end time, exclusive.
     *
     * @return the retrieved auctions instances.
     *
     * @throws IllegalArgumentException If startBy date &gt; endAfter date (only if both not null)
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws TranslationException if it fails to translate the Auction or AuctionDTO instances.
     */
    public Auction[] findAuctionsByDate(Date startingBy, Date endingAfter)
        throws PersistenceException, TranslationException {
        AuctionPersistenceHelper.validateTwoDate(startingBy, endingAfter);

        AuctionDTO[] auctionDTOs = ejbFindAuctionsByDate(startingBy, endingAfter);
        Auction[] auctions = new Auction[auctionDTOs.length];

        for (int i = 0; i < auctionDTOs.length; i++) {
            auctions[i] = auctionTranslator.assembleAuction(auctionDTOs[i]);
            cache.put(auctions[i].getId(), auctions[i]);
        }

        return auctions;
    }

    /**
     * <p>
     * Gets all auctions currently in persistence for the given bidderId whose end date is smaller than endingAfter
     * date parameter. Returns empty array if no auctions found. If endingAfter is null, ignores the parameter in
     * comparisons.
     * </p>
     *
     * <p>
     * It will delegate actual execution to the ejbFindAuctionsByBidder. It makes no sense to try to match cached items
     * with persisted items, se we get all directly from persistence, and then cache all retrieved and translated
     * Auctions. Returns empty array if no auctions found.
     * </p>
     *
     * @param bidderId id of bidder to get all auctions.
     * @param endingAfter search date limit on the latest end time, exclusive.
     *
     * @return the retrieved auctions instances.
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws TranslationException if it fails to translate the Auction or AuctionDTO instances.
     */
    public Auction[] findAuctionsByBidder(long bidderId, Date endingAfter)
        throws PersistenceException, TranslationException {
        AuctionDTO[] auctionDTOs = ejbFindAuctionsByBidder(bidderId, endingAfter);
        Auction[] auctions = new Auction[auctionDTOs.length];

        for (int i = 0; i < auctionDTOs.length; i++) {
            auctions[i] = auctionTranslator.assembleAuction(auctionDTOs[i]);
            cache.put(auctions[i].getId(), auctions[i]);
        }

        return auctions;
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
     * @throws InvalidEntryException If the passed auction does not contain an ID.
     * @throws DuplicateEntryException If the persistent store already contains an auction with the specified ID.
     * @throws PersistenceException If there is any problem in the persistence layer. EJB exceptions will be wrapped
     *         here.
     */
    protected abstract AuctionDTO ejbCreateAuction(AuctionDTO auction) throws PersistenceException;

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
    protected abstract AuctionDTO ejbGetAuction(long auctionId) throws PersistenceException;

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
    protected abstract AuctionDTO ejbUpdateAuction(AuctionDTO auction) throws PersistenceException;

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
    protected abstract AuctionDTO ejbUpdateBids(long auctionId, BidDTO[] bids) throws PersistenceException;

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
    protected abstract void ejbDeleteAuction(long auctionId) throws PersistenceException;

    /**
     * <p>
     * Gets all auctions currently in persistence whose start date is not smaller than startingBay date parameter, and
     * whose end date is smaller than endingAfter date parameter, using the applicable EJB. Returns empty array if no
     * auctions found.
     * </p>
     *
     * <p>
     * If startingBy or endingAfter is null, ignore the parameter in comparisons.
     * </p>
     *
     * @param startingBy search date limit on the earliest start time, inclusive.
     * @param endingAfter search date limit on the latest end time, exclusive.
     *
     * @return the retrieved auctions instances.
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    protected abstract AuctionDTO[] ejbFindAuctionsByDate(Date startingBy, Date endingAfter)
        throws PersistenceException;

    /**
     * <p>
     * Gets all auctions currently in persistence for the given bidderId whose end date is smaller than endingAfter
     * date parameter, using the applicable EJB. Returns empty array if no auctions found.
     * </p>
     *
     * <p>
     * If endingAfter is null, ignore the parameter in comparisons.
     * </p>
     *
     * @param bidderId id of bidder to get all auctions.
     * @param endingAfter search date limit on the latest end time, exclusive.
     *
     * @return the retrieved auctions instances.
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    protected abstract AuctionDTO[] ejbFindAuctionsByBidder(long bidderId, Date endingAfter)
        throws PersistenceException;
}
