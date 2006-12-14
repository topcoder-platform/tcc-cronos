/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.impl;

import com.orpheus.auction.persistence.AuctionDAO;
import com.orpheus.auction.persistence.AuctionPersistenceHelper;
import com.orpheus.auction.persistence.DuplicateEntryException;
import com.orpheus.auction.persistence.EntryNotFoundException;
import com.orpheus.auction.persistence.InvalidEntryException;
import com.orpheus.auction.persistence.ObjectInstantiationException;
import com.orpheus.auction.persistence.PersistenceException;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.util.cache.Cache;
import com.topcoder.util.objectfactory.ObjectFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * An implementation of the <code>AuctionDAO</code> interface which works with SQL Server database and the tables of
 * auction, bid, and effective_bid, with additional usage of the image, hosting_block, domain, and sponsor tables. The
 * mappings are mostly 1-1, with Auction info going into the auction table, and Bid info goes into the bid and
 * effective_bid tables, still one record in each per Bid. Well, at most one, since a Bid might not have an effective
 * bid.
 * </p>
 *
 * <p>
 * This class supports all defined CRUD operations. It also supports caching auctions to minimize SQL traffic. It uses
 * ConfigManager and Object Factory to configure the connection factory and cache instances. It is expected that the
 * former will use a JNDI connection provider so the Datasource is obtained from the application server. It creates,
 * caches, and consumes AuctionDTO objects. Note that AuctionDTO will contain the BidDTOs and once the BidDTOs are
 * persisted into the database, old ones will be not deleted when updating the BidDTOs of the AuctionDTO.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is mutable and thread-safe
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class SQLServerAuctionDAO implements AuctionDAO {

    /**
     * <p>
     * Represents the property name to retrieve the specNamespace value.
     * </p>
     */
    private static final String SPEC_NAMESPACE_PROPERTY = "specNamespace";

    /**
     * <p>
     * Represents the property name to retrieve the factoryKey value.
     * </p>
     */
    private static final String FACTORY_KEY_PROPERTY = "factoryKey";

    /**
     * <p>
     * Represents the property name to retrieve the cacheKey value.
     * </p>
     */
    private static final String CACHE_KEY_PROPERTY = "cacheKey";

    /**
     * <p>
     * Represents the property name to retrieve the name value.
     * </p>
     */
    private static final String NAME_PROPERTY = "name";

    /**
     * <p>
     * Represents the connection factory that is used for performing CRUD operations.
     * </p>
     *
     * <p>
     * It should be backed by a JNDI connection producer, which simply eases the obtaining of a connection from a
     * datasource via JNDI. This is created in the constructor, will not be null, and will never change.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the name of the connection to obtain from the connection factory that is used for performing CRUD
     * operations.
     * </p>
     *
     * <p>
     * This is created in the constructor. This value is optional, and empty will be set to null, and will never
     * change. If null, then the default connection will be obtained.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the cache of AuctionDTOs. The keys will be the auctionId field.
     * </p>
     *
     * <p>
     * This is created in the constructor, will not be null, and will never change.
     * </p>
     */
    private final Cache cache;

    /**
     * <p>
     * Creates the new instance of <code>SQLServerAuctionDAO</code> class from the given namespace. It will use
     * ConfigManager and ObjectFactory to instantiate a new DBConnectionFactory and Cache objects, and use the
     * ConfigManager to get the optional connection name.
     * </p>
     *
     * @param namespace configuration namespace.
     *
     * @throws IllegalArgumentException If namespace is null or empty.
     * @throws ObjectInstantiationException If there is an error with construction.
     */
    public SQLServerAuctionDAO(String namespace) throws ObjectInstantiationException {
        AuctionPersistenceHelper.validateString(namespace, "namespace");

        // create the ObjectFactory with the namespace obtained from ConfigManager
        String specNamespace = AuctionPersistenceHelper.getStringPropertyValue(namespace,
                SPEC_NAMESPACE_PROPERTY, true);
        ObjectFactory objectFactory = AuctionPersistenceHelper.createObjectFactory(specNamespace);

        // obtain the keys for the connection factory and cache form ConfgManager
        String factoryKey = AuctionPersistenceHelper.getStringPropertyValue(namespace,
                FACTORY_KEY_PROPERTY, true);
        String cacheKey = AuctionPersistenceHelper.getStringPropertyValue(namespace, CACHE_KEY_PROPERTY, true);

        // get the optional connection name and create the DBConnectionFactory and Cache objects from ObjectFactory
        this.connectionFactory = (DBConnectionFactory) AuctionPersistenceHelper.createObject(objectFactory, factoryKey,
                DBConnectionFactory.class);
        this.cache = (Cache) AuctionPersistenceHelper.createObject(objectFactory, cacheKey, Cache.class);

        String name = AuctionPersistenceHelper.getStringPropertyValue(namespace, NAME_PROPERTY, false);
        this.connectionName = ((name == null) || (name.trim().length() == 0)) ? null : name;
    }

    /**
     * <p>
     * Creates the auction in persistence. It first checks if the auction already exists in the cache. If not, it will
     * attempt to create the auction in persistence. It will fist check if the auctionId is a valid, existing
     * hosting_block.id. Then it will create a new auction and new bids.
     * </p>
     *
     * <p>
     * If operation is successful, it puts the AuctionDTO in the cache with its id as the key.
     * </p>
     *
     * @param auction the auction to create.
     *
     * @return the created auction.
     *
     * @throws IllegalArgumentException If auction is null, or has null Bid elements, or if auction.minBid,
     *         auction.itemCount, bid.imageId, bid.maxAmount, or bid.effectiveAmount (if not null) are negative.
     * @throws InvalidEntryException If the passed auction does not contain an ID, or hosting_block does not have such
     *         an Id, or the bidderId of any bidder does not exist.
     * @throws DuplicateEntryException If the persistent store already contains an auction with the specified ID.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public AuctionDTO createAuction(AuctionDTO auction) throws PersistenceException {
        AuctionPersistenceHelper.validateAuctionDTO(auction, "auction");

        if (auction.getId() == null) {
            throw new InvalidEntryException("The auction does not contain an id.", auction.getId());
        }

        // check if already in cache
        AuctionDTO obj = (AuctionDTO) cache.get(auction.getId());

        if (obj != null) {
            throw new DuplicateEntryException("The auction already exists.", obj.getId());
        }

        Connection conn = null;

        try {
            conn = this.createConnection();

            if (checkAuctionIdExistInAuction(conn, auction.getId().longValue())) {
                throw new DuplicateEntryException("The auction already exists.", auction.getId());
            }

            if (!checkAuctionIdExistInHostingBlock(conn, auction.getId().longValue())) {
                throw new InvalidEntryException("The hosting block doesn't hold this auction.", auction.getId());
            }

            // insert the AuctionDTO into the auction table
            insertAuction(conn, auction);

            // for each BidDTO
            BidDTO[] bidDTOs = auction.getBids();

            for (int i = 0; i < bidDTOs.length; i++) {
                // check if the bidderId exists.
                if (!checkBidderId(conn, bidDTOs[i].getImageId(), bidDTOs[i].getBidderId())) {
                    throw new InvalidEntryException("The bidderId does not exist.", bidDTOs[i]);
                }

                // insert the BidDTO into the bid table
                long bidID = insertBid(conn, auction.getId().longValue(), bidDTOs[i]);
                bidDTOs[i].setId(new Long(bidID));

                if (bidDTOs[i].getEffectiveAmount() != null) {
                    insertEffectiveBid(conn, bidID, bidDTOs[i].getEffectiveAmount().intValue());
                }
            }

            // If all goes well, put auction in cache
            auction.setDescription("Hosting slots for block " + auction.getId() + ".");
            auction.setSummary("Hosting slots for block " + auction.getId() + ".");
            cache.put(auction.getId(), auction);

            return auction;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Gets the auction for this ID from persistence. It will first check if there is an entry in the cache for this
     * auctionId. If not, it will retrieve from the database.
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
     */
    public AuctionDTO getAuction(long auctionId) throws PersistenceException {
        // check if already in cache
        AuctionDTO auction = (AuctionDTO) cache.get(new Long(auctionId));

        if (auction != null) {
            return auction;
        }

        Connection conn = null;

        try {
            conn = this.createConnection();

            // retrieve the auction from the database persistence
            auction = getAuction(conn, auctionId);

            if (auction == null) {
                throw new EntryNotFoundException("The auction does not exist in the persistence.", new Long(auctionId));
            }

            auction.setBids(getBids(conn, auctionId));

            // if all goes well, put auction in cache
            cache.put(auction.getId(), auction);

            return auction;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Updates the auction in persistence.
     * </p>
     *
     * <p>
     * If operation is successful, it puts the auction in the cache with its id as the key.
     * </p>
     *
     * @param auction the auction to update.
     *
     * @return updated the updated auction.
     *
     * @throws IllegalArgumentException If auction is null, or has null Bid elements, or if auction.minBid,
     *         auction.itemCount, bid.imageId, bid.maxAmount, or bid.effectiveAmount (if not null) are negative.
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store, or if a passed bid in the persistence store has an auctionId that is not the passed
     *         auctionID.
     * @throws InvalidEntryException If the passed auction does not contain an ID, or the bidderId of any bidder does
     *         not exist.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public AuctionDTO updateAuction(AuctionDTO auction) throws PersistenceException {
        AuctionPersistenceHelper.validateAuctionDTO(auction, "auction");

        if (auction.getId() == null) {
            throw new InvalidEntryException("The auction does not contain an id.", auction.getId());
        }

        Connection conn = null;
        try {
            conn = this.createConnection();

            // update the auction record
            if (updateAuction(conn, auction) != 1) {
                throw new EntryNotFoundException("The auction does not exist.", auction.getId());
            }

            // update the bid records
            updateBidDTOS(conn, auction.getId().longValue(), auction.getBids());
            auction.setBids(this.getBids(conn, auction.getId().longValue()));

            // if all goes well, put auction in cache
            auction.setDescription("Hosting slots for block " + auction.getId() + ".");
            auction.setSummary("Hosting slots for block " + auction.getId() + ".");
            cache.put(auction.getId(), auction);

            return auction;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Updates the bids in persistence.
     * </p>
     *
     * <p>
     * If operation is successful, it puts the auction representing those bids in the cache with the passed id as the
     * key.
     * </p>
     *
     * @param auctionId auction id of the bids.
     * @param bids bids to update/create.
     *
     * @return the updated auction.
     *
     * @throws IllegalArgumentException If bids is null, or if the array contains null elements, or if bid.imageId,
     *         bid.maxAmount, or bid.effectiveAmount (if not null) are negative.
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auctionId or
     *         bidId in the persistent store.
     * @throws InvalidEntryException If a passed bid in the persistence store has an auctionId that is not the passed
     *         auctionID, or the bidderId of any bidder does not exist.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public AuctionDTO updateBids(long auctionId, BidDTO[] bids) throws PersistenceException {
        AuctionPersistenceHelper.validateBidDTOCollection(bids, "bids");

        Connection conn = null;

        try {
            conn = this.createConnection();

            AuctionDTO auction = getAuction(conn, auctionId);

            if (auction == null) {
                throw new EntryNotFoundException("The auction does not exist.", new Long(auctionId));
            }

            // update the bids
            updateBidDTOS(conn, auctionId, bids);
            auction.setBids(this.getBids(conn, auction.getId().longValue()));

            // if all goes well, put auction in cache
            cache.put(auction.getId(), auction);

            return auction;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes the auction and its bids for this ID from persistence.
     * </p>
     *
     * <p>
     * It first checks if the entry exists, and deletes it if it does. It also removes the entry from the cache.
     * </p>
     *
     * @param auctionId the auction id to delete.
     *
     * @throws EntryNotFoundException If there is not yet any persistent representation of the specified auction in the
     *         persistent store.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public void deleteAuction(long auctionId) throws PersistenceException {
        Connection conn = null;

        try {
            conn = this.createConnection();

            List bidIds = getBidIds(conn, auctionId);

            for (Iterator iter = bidIds.iterator(); iter.hasNext();) {
                deleteEffectiveBid(conn, ((Long) iter.next()).longValue());
            }

            deleteBids(conn, auctionId);

            if (deleteAuction(conn, auctionId) != 1) {
                throw new EntryNotFoundException("The auction does not exist.", new Long(auctionId));
            }

            // Remove from cache
            cache.remove(new Long(auctionId));
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Gets all auctions currently in persistence whose start date is not smaller than startingBay date parameter, and
     * whose end date is smaller than endingAfter date parameter. If startingBy or endingAfter is null, ignores the
     * parameter in comparisons. It makes no sense to try to match cached items with persisted items, so we get all
     * directly from persistence, and then cache all retrieved AuctionDTOs. Returns empty array if no auctions found.
     * </p>
     *
     * @param startingBy search date limit on the earliest start time, inclusive.
     * @param endingAfter search date limit on the latest end time, exclusive.
     *
     * @return the retrieved auctions instances.
     *
     * @throws IllegalArgumentException If startBy date &gt; endAfter date (only if both not null)
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public AuctionDTO[] findAuctionsByDate(Date startingBy, Date endingAfter) throws PersistenceException {
        AuctionPersistenceHelper.validateTwoDate(startingBy, endingAfter);

        Connection conn = null;

        try {
            conn = this.createConnection();

            List auctionIds = selectAuctionIds(conn, startingBy, endingAfter);

            AuctionDTO[] auctionDTOs = new AuctionDTO[auctionIds.size()];

            for (int i = 0; i < auctionIds.size(); i++) {
                auctionDTOs[i] = getAuction(((Long) auctionIds.get(i)).longValue());
            }

            return auctionDTOs;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Gets all auctions currently in persistence for the given bidderId whose end date is smaller than endingAfter
     * date parameter. If endingAfter is null, ignores the parameter in comparisons. It makes no sense to try to match
     * cached items with persisted items, so we get all directly from persistence, and then cache all retrieved
     * AuctionDTOs. Returns empty array if no auctions found.
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
        Connection conn = null;

        try {
            conn = this.createConnection();

            List auctionIds = selectAuctionIdsByBidder(conn, bidderId, endingAfter);

            AuctionDTO[] auctionDTOs = new AuctionDTO[auctionIds.size()];

            for (int i = 0; i < auctionIds.size(); i++) {
                auctionDTOs[i] = getAuction(((Long) auctionIds.get(i)).longValue());
            }

            return auctionDTOs;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Updates the bids in persistence with the given auctionId. Note, we need not delete the previous bids in the
     * database.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auctionId auction id of the bids.
     * @param bidDTOs bids to update/create.
     *
     * @throws EntryNotFoundException If a passed bid in the persistence store has an auctionId that is not the passed
     *         auctionID.
     * @throws InvalidEntryException if the bidderId of any bidder does not exist.
     * @throws SQLException If there is any problem when accessing the database.
     */
    private void updateBidDTOS(Connection conn, long auctionId, BidDTO[] bidDTOs)
        throws InvalidEntryException, EntryNotFoundException, SQLException {
        for (int i = 0; i < bidDTOs.length; i++) {
            if (!checkBidderId(conn, bidDTOs[i].getImageId(), bidDTOs[i].getBidderId())) {
                throw new InvalidEntryException("The bidder does not exist.", bidDTOs[i].getId());
            }

            Long bidId = bidDTOs[i].getId();
            Integer effectiveAmount = bidDTOs[i].getEffectiveAmount();

            if (bidId != null) {
                if (updateBid(conn, auctionId, bidDTOs[i]) != 1) {
                    throw new EntryNotFoundException("The bid for auction: " + auctionId + " does not exist.", bidId);
                }

                if (effectiveAmount != null) {
                    if (updateEffectiveBid(conn, bidId.longValue(), effectiveAmount.intValue()) != 1) {
                        insertEffectiveBid(conn, bidId.longValue(), effectiveAmount.intValue());
                    }
                } else {
                    deleteEffectiveBid(conn, bidId.longValue());
                }
            } else {
                bidId = new Long(insertBid(conn, auctionId, bidDTOs[i]));
                bidDTOs[i].setId(bidId);

                if (effectiveAmount != null) {
                    insertEffectiveBid(conn, bidId.longValue(), effectiveAmount.intValue());
                }
            }
        }
    }

    /**
     * <p>
     * Get database connection from the db connection factory.
     * </p>
     *
     * @return A database connection.
     *
     * @throws PersistenceException If can't get connection.
     */
    private Connection createConnection() throws PersistenceException {
        Connection conn = null;

        try {
            // create a DB connection
            conn = (connectionName == null) ? connectionFactory.createConnection()
                                            : connectionFactory.createConnection(connectionName);

            return conn;
        } catch (DBConnectionException e) {
            this.releaseConnection(conn);
            throw new PersistenceException("Can't get the connection from database.", e);
        }
    }

    /**
     * <p>
     * Release the connection.
     * </p>
     *
     * @param conn the connection.
     */
    private void releaseConnection(Connection conn) {
        try {
            if ((conn != null) && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            // ignore it
        }
    }

    /**
     * <p>
     * Check whether an auction with specified id exists in the database.
     * </p>
     *
     * @param connection the connection to the database.
     * @param auctionId the id of the auction.
     *
     * @return return true if exist, false otherwise.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private boolean checkAuctionIdExistInAuction(Connection connection, long auctionId) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                    "SELECT top 1 hosting_block_id FROM auction where hosting_block_id = ?");
            statement.setLong(1, auctionId);
            resultSet = statement.executeQuery();

            return resultSet.next();
        } finally {
            releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Check whether the auction id exists in hosting_block table.
     * </p>
     *
     * @param connection the connection to the database.
     * @param auctionId the id of the auction.
     *
     * @return return true if exist, false otherwise.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private boolean checkAuctionIdExistInHostingBlock(Connection connection, long auctionId) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT top 1 id FROM hosting_block where id = ?");
            statement.setLong(1, auctionId);
            resultSet = statement.executeQuery();

            return resultSet.next();
        } finally {
            releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Check whether the bidder id exists. This is done by joining the image and domain tables.
     * </p>
     *
     * @param connection the connection to the database.
     * @param imageId the image id of the bid.
     * @param bidderId the bidder id of the bid.
     *
     * @return return true if exist, false otherwise.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private boolean checkBidderId(Connection connection, long imageId, long bidderId) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT top 1 domain_id FROM image i, domain d "
                    + "where i.domain_id = d.id and i.id = ? and d.sponsor_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, imageId);
            statement.setLong(2, bidderId);
            resultSet = statement.executeQuery();

            return resultSet.next();
        } finally {
            releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Select an auction with the specified id from the database.
     * </p>
     *
     * @param connection the connection to the database.
     * @param auctionId the id of the auction.
     *
     * @return the auction with the specified id or null if none if found.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private AuctionDTO getAuction(Connection connection, long auctionId) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                    "SELECT hosting_block_id, item_count, min_bid, start_time, end_time FROM auction "
                    + "where hosting_block_id = ?");
            statement.setLong(1, auctionId);
            resultSet = statement.executeQuery();

            AuctionDTO auction = null;

            if (resultSet.next()) {
                auction = new AuctionDTO();
                auction.setId(new Long(resultSet.getLong(1)));
                auction.setItemCount(resultSet.getInt(2));
                auction.setMinimumBid(resultSet.getInt(3));
                auction.setStartDate(timestamp2Date(resultSet.getTimestamp(4)));
                auction.setEndDate(timestamp2Date(resultSet.getTimestamp(5)));
                auction.setDescription("Hosting slots for block " + auction.getId() + ".");
                auction.setSummary("Hosting slots for block " + auction.getId() + ".");
            }

            return auction;
        } finally {
            releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Retrieve all the bids for the specified auction from the database.
     * </p>
     *
     * @param connection the connection to the database.
     * @param auctionId the id of the auction.
     *
     * @return a list of bids for the specified auction.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private BidDTO[] getBids(Connection connection, long auctionId) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT b.id, b.image_id, b.max_amount, b.time, e.current_amount, d.sponsor_id "
                + "FROM bid b left outer join effective_bid e on b.id = e.bid_id "
                + "inner join image i on b.image_id = i.id " + "inner join domain d on i.domain_id = d.id "
                + "where b.auction_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, auctionId);
            resultSet = statement.executeQuery();

            List bids = new ArrayList();

            while (resultSet.next()) {
                BidDTO bid = new BidDTO();
                bid.setId(new Long(resultSet.getLong(1)));
                bid.setImageId(resultSet.getLong(2));
                bid.setMaxAmount(resultSet.getInt(3));
                bid.setTimestamp(timestamp2Date(resultSet.getTimestamp(4)));

                int effectiveAmount = resultSet.getInt(5);

                if (!resultSet.wasNull()) {
                    bid.setEffectiveAmount(new Integer(effectiveAmount));
                }

                bid.setBidderId(resultSet.getLong(6));
                bids.add(bid);
            }

            return (BidDTO[]) bids.toArray(new BidDTO[bids.size()]);
        } finally {
            releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Retrieve all the bid ids for the specified auction from the database.
     * </p>
     *
     * @param connection the connection to the database.
     * @param auctionId the id of the auction.
     *
     * @return a list of bid ids for the specified auction.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private List getBidIds(Connection connection, long auctionId) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT id FROM bid where auction_id = ?";

            statement = connection.prepareStatement(sql);
            statement.setLong(1, auctionId);
            resultSet = statement.executeQuery();

            List bidIdList = new ArrayList();

            while (resultSet.next()) {
                bidIdList.add(new Long(resultSet.getLong(1)));
            }

            return bidIdList;
        } finally {
            releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Retrieve all auction ids that lies between the given starting and ending dates. If startingBy or endingAfter is
     * null, ignores the parameter in comparisons.
     * </p>
     *
     * @param connection the connection to the database.
     * @param start the earliest starting date (inclusive).
     * @param end the latest ending date (exclusive).
     *
     * @return the auction id list.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private List selectAuctionIds(Connection connection, Date start, Date end) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT hosting_block_id FROM auction");

            if (start != null) {
                sql.append(" WHERE start_time >= ?");

                if (end != null) {
                    sql.append(" AND end_time < ?");
                }
            } else {
                if (end != null) {
                    sql.append(" WHERE end_time < ?");
                }
            }

            statement = connection.prepareStatement(sql.toString());

            int index = 1;

            if (start != null) {
                statement.setTimestamp(index++, date2Timestamp(start));
            }

            if (end != null) {
                statement.setTimestamp(index++, date2Timestamp(end));
            }

            resultSet = statement.executeQuery();

            List auctionIds = new ArrayList();

            while (resultSet.next()) {
                auctionIds.add(new Long(resultSet.getLong(1)));
            }

            return auctionIds;
        } finally {
            releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Selects the ids of auctions which contain a bid by the specified bidder and end earlier than the specified
     * ending date. If endingAfter is null, ignores the parameter in comparisons.
     * </p>
     *
     * @param connection the connection to the database.
     * @param bidderId the id of the bidder.
     * @param endingAfter the latest ending date (exclusive).
     *
     * @return the auction id list.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private List selectAuctionIdsByBidder(Connection connection, long bidderId, Date endingAfter) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT distinct a.hosting_block_id " + "FROM image i, domain d, bid b, auction a "
                + "WHERE i.domain_id = d.id and i.id = b.image_id and "
                + "b.auction_id = a.hosting_block_id and d.sponsor_id = ?");

            if (endingAfter != null) {
                sql.append(" and a.end_time < ?");
            }

            statement = connection.prepareStatement(sql.toString());
            statement.setLong(1, bidderId);

            if (endingAfter != null) {
                statement.setTimestamp(2, date2Timestamp(endingAfter));
            }

            resultSet = statement.executeQuery();

            List auctionIds = new ArrayList();

            while (resultSet.next()) {
                auctionIds.add(new Long(resultSet.getLong(1)));
            }

            return auctionIds;
        } finally {
            releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Insert the AuctionDTO into the auction table: Summary and Description are discarted, and bid_increment column is
     * ignored.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auction the auction to persist.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private void insertAuction(Connection conn, AuctionDTO auction) throws SQLException {
        PreparedStatement statement = null;

        try {
            String sql = "INSERT INTO auction(hosting_block_id, start_time, end_time, min_bid, item_count)"
                + " VALUES (?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setLong(1, auction.getId().longValue());

            statement.setTimestamp(2, date2Timestamp(auction.getStartDate()));
            statement.setTimestamp(3, date2Timestamp(auction.getEndDate()));
            statement.setInt(4, auction.getMinimumBid());
            statement.setInt(5, auction.getItemCount());

            statement.executeUpdate();
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Insert a single bid for the specified auction into the database.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auctionId the id of the auction.
     * @param bid the bid to insert.
     *
     * @return the id of the newly inserted bid.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private long insertBid(Connection conn, long auctionId, BidDTO bid) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String sql = "INSERT INTO bid(auction_id, image_id, max_amount, time) VALUES (?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setLong(1, auctionId);
            statement.setLong(2, bid.getImageId());
            statement.setInt(3, bid.getMaxAmount());
            statement.setTimestamp(4, date2Timestamp(bid.getTimestamp()));
            statement.executeUpdate();
        } finally {
            releaseResource(null, statement);
        }

        try {
            String sql = "SELECT @@IDENTITY";
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery();
            rs.next();

            return rs.getLong(1);
        } finally {
            releaseResource(rs, statement);
        }
    }

    /**
     * <p>
     * Insert a record into the effective_bid table.
     * </p>
     *
     * @param conn the connection to the database.
     * @param bidId the id of the bid.
     * @param currentAmount the current amount of the bid.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private void insertEffectiveBid(Connection conn, long bidId, int currentAmount) throws SQLException {
        PreparedStatement statement = null;

        try {
            String sql = "INSERT INTO effective_bid(bid_id, current_amount) VALUES (?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setLong(1, bidId);
            statement.setInt(2, currentAmount);
            statement.executeUpdate();
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Update an auction in the database.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auction the auction to update.
     *
     * @return the updated row(rows).
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private int updateAuction(Connection conn, AuctionDTO auction) throws SQLException {
        PreparedStatement statement = null;

        try {
            String sql = "UPDATE auction SET start_time = ?, end_time = ?, "
                + "min_bid = ?, item_count = ? WHERE hosting_block_id = ?";

            statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, date2Timestamp(auction.getStartDate()));
            statement.setTimestamp(2, date2Timestamp(auction.getEndDate()));
            statement.setInt(3, auction.getMinimumBid());
            statement.setInt(4, auction.getItemCount());
            statement.setLong(5, auction.getId().longValue());

            return statement.executeUpdate();
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Update a single bid for the specified auction into the database.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auctionId the id of the auction.
     * @param bid the bid to update.
     *
     * @return the updated row(rows).
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private int updateBid(Connection conn, long auctionId, BidDTO bid) throws SQLException {
        PreparedStatement statement = null;

        try {
            String sql = "UPDATE bid SET image_id = ?, max_amount = ?, time = ? WHERE auction_id = ? AND id = ?";
            statement = conn.prepareStatement(sql);

            statement.setLong(1, bid.getImageId());
            statement.setInt(2, bid.getMaxAmount());
            statement.setTimestamp(3, date2Timestamp(bid.getTimestamp()));
            statement.setLong(4, auctionId);
            statement.setLong(5, bid.getId().longValue());

            return statement.executeUpdate();
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Update the effective amount for the given bid into the database.
     * </p>
     *
     * @param conn the connection to the database.
     * @param bidid the id of the bid.
     * @param currentAmount the effective amount of the bid.
     *
     * @return the updated row(rows).
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private int updateEffectiveBid(Connection conn, long bidid, int currentAmount) throws SQLException {
        PreparedStatement statement = null;

        try {
            String sql = "UPDATE effective_bid SET current_amount = ? where bid_id = ?";
            statement = conn.prepareStatement(sql);

            statement.setInt(1, currentAmount);
            statement.setLong(2, bidid);

            return statement.executeUpdate();
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Delete an auction from the database, but not its bids.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auctionId the id of the auction.
     *
     * @return the number of auction deleted.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private int deleteAuction(Connection conn, long auctionId) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement("DELETE FROM auction WHERE hosting_block_id = ?");
            statement.setLong(1, auctionId);

            return statement.executeUpdate();
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Delete all the bids for the given auction from the database.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auctionId the id of the auction.
     *
     * @return the number of bids deleted.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private int deleteBids(Connection conn, long auctionId) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement("DELETE FROM bid WHERE auction_id = ?");
            statement.setLong(1, auctionId);

            return statement.executeUpdate();
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Delete the effective amount for the given bid from the database.
     * </p>
     *
     * @param conn the connection to the database.
     * @param bidId the id of the bid.
     *
     * @return the number deleted.
     *
     * @throws SQLException If there is any problem when accessing the database.
     */
    private int deleteEffectiveBid(Connection conn, long bidId) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement("DELETE FROM effective_bid WHERE bid_id = ?");
            statement.setLong(1, bidId);

            return statement.executeUpdate();
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Releases the JDBC resources.
     * </p>
     *
     * @param resultSet the resultSet set to be closed.
     * @param statement the SQL statement to be closed.
     */
    private void releaseResource(ResultSet resultSet, Statement statement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException se) {
                // ignore
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException se) {
                // ignore
            }
        }
    }

    /**
     * Converts a {@link java.util.Date} to a {@link java.sql.Timestamp}.
     *
     * @param date the date to convert.
     *
     * @return a {@link java.sql.Timestamp} instance.
     */
    private Timestamp date2Timestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * Converts a {@link java.sql.Timestamp} to a {@link java.util.Date}.
     *
     * @param date the date to convert
     *
     * @return a {@link java.util.Date} instance.
     */
    private Date timestamp2Date(Timestamp date) {
        return new Date(date.getTime());
    }
}
