/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.accuracytests;

import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Assert;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.Iterator;


/**
 * Test Helper.
 */
public class TestHelper {
    /** constant represents the dbfactory config file. */
    public static final String DBFACTORY_CONFIG_XML = "DBConnectionFactory_Config.xml";

    /** config file for the component. */
    public static final String AUCTION_PERSISTENCE_CONFIG_FILE = "accuracy_tests/Auction_Persistence_Config.xml";

    /** namespace for SQLServerGameDataDao. */
    public static final String DAO_NAMESPACE = "com.orpheus.auction.persistence.impl.SQLServerAuctionDAO";

    /** local custom download namespace. */
    public static final String LOCAL_NAMESPACE = "com.orpheus.auction.persistence.LocalCustomAuctionPersistence";

    /** remote custom download namespace. */
    public static final String REMOTE_NAMESPACE = "com.orpheus.auction.persistence.RemoteCustomAuctionPersistence";

    /** database factory namespace. */
    private static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * This private constructor prevents the creation of a new instance.
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Add the config file.
     * </p>
     *
     * @param fileName the config file to add
     *
     * @throws Exception into JUnit
     */
    public static void addConfigFile(String fileName) throws Exception {
        ConfigManager.getInstance().add(fileName);
    }

    /**
     * <p>
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved
     * from the given instance.
     * </p>
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be retrieved.
     *
     * @return the value of the private field or <code>null</code> if any error occurs.
     */
    public static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * Sets the value of a private field in the given class. The field has the given name. The value is retrieved
     * from the given instance.
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be setted.
     * @param value the value be given to the field.
     */
    public static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * This method clears all the namespaces from ConfigManager.
     *
     * @throws Exception if any error occurs when clearing ConfigManager
     */
    public static void clearConfigManager() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void clearDatabase(Connection connection)
        throws SQLException {
        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("DELETE FROM effective_bid");
            statement.executeUpdate("DELETE FROM bid");
            statement.executeUpdate("DELETE FROM auction");
            statement.executeUpdate("DELETE FROM hosting_block");
            statement.executeUpdate("DELETE FROM image");
            statement.executeUpdate("DELETE FROM domain");
            statement.executeUpdate("DELETE FROM sponsor");
            statement.executeUpdate("DELETE FROM any_user");
            statement.executeUpdate("DELETE FROM game");
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @return the connection created from DBConnectionFactory.
     *
     * @throws Exception any exception during the create connection process.
     */
    public static Connection getConnection() throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);

        return factory.createConnection();
    }

    /**
     * <p>
     * Prepare data for test.
     * </p>
     *
     * @param connection database connection.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void prepareData(Connection connection) throws SQLException {
        clearDatabase(connection);

        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("insert into any_user(id, handle, e_mail) values ('1', 'handle', 'email')");
            statement.executeUpdate("insert into sponsor(any_user_id) values (1)");
            statement.executeUpdate("SET IDENTITY_INSERT domain ON");
            statement.executeUpdate("insert into domain(id, sponsor_id) values (2, 1)");
            statement.executeUpdate("SET IDENTITY_INSERT domain OFF");
            statement.executeUpdate("SET IDENTITY_INSERT image ON");
            statement.executeUpdate("insert into image(id, domain_id) values (3, 2)");
            statement.executeUpdate("SET IDENTITY_INSERT image OFF");
            statement.executeUpdate("SET IDENTITY_INSERT hosting_block ON");
            statement.executeUpdate("insert into hosting_block(id) values (1)");
            statement.executeUpdate("insert into hosting_block(id) values (2)");
            statement.executeUpdate("insert into hosting_block(id) values (3)");
            statement.executeUpdate("insert into hosting_block(id) values (4)");
            statement.executeUpdate("insert into hosting_block(id) values (5)");
            statement.executeUpdate("SET IDENTITY_INSERT hosting_block OFF");
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * Asserts the two given BidDTO instances to be equals.
     * </p>
     *
     * @param expected the expected BidDTO instance
     * @param actual the actual BidDTO instance
     */
    public static void assertEquals(BidDTO expected, BidDTO actual) {
        Assert.assertEquals("The bidder id should be correct.", expected.getBidderId(), actual.getBidderId());
        Assert.assertEquals("The effective amount should be correct.", expected.getEffectiveAmount(),
            actual.getEffectiveAmount());
        Assert.assertEquals("The id should be correct.", expected.getId(), actual.getId());
        Assert.assertEquals("The imageId should be correct.", expected.getImageId(), actual.getImageId());
        Assert.assertEquals("The max amount should be correct.", expected.getMaxAmount(), actual.getMaxAmount());
    }

    /**
     * <p>
     * Asserts the two given AuctionDTO instances to be equals.
     * </p>
     *
     * @param expected the expected AuctionDTO instance.
     * @param actual the actual AuctionDTO instance.
     * @param getFromDB whether the auction is got from database.
     */
    public static void assertEquals(AuctionDTO expected, AuctionDTO actual, boolean getFromDB) {
        Assert.assertEquals("The id should be correct.", expected.getId(), actual.getId());

        Assert.assertEquals("The item count should be correct.", expected.getItemCount(), actual.getItemCount());
        Assert.assertEquals("The minimun bid should be correct.", expected.getMinimumBid(), actual.getMinimumBid());

        if (getFromDB) {
            Assert.assertEquals("The description should be empty", "", actual.getDescription());
            Assert.assertEquals("The summary should be empty","", actual.getSummary());
        } else {
            Assert.assertEquals("The description should be correct.", expected.getDescription(),
                actual.getDescription());
            Assert.assertEquals("The summary should be correct.", expected.getSummary(), actual.getSummary());
        }

        BidDTO[] expectedBids = expected.getBids();
        BidDTO[] actualBids = actual.getBids();

        Assert.assertEquals("The bids should have the same length.", expectedBids.length, actualBids.length);

        for (int i = 0; i < expectedBids.length; i++) {
            assertEquals(expectedBids[i], actualBids[i]);
        }
    }

    /**
     * <p>
     * Asserts the two given CustomBid instances to be equals.
     * </p>
     *
     * @param expected the expected CustomBid instance.
     * @param actual the actual CustomBid instance.
     * @param getFromDB whether the auction is got from database.
     */
    public static void assertEquals(CustomBid expected, CustomBid actual, boolean getFromDB) {
        if (getFromDB) {
            Assert.assertNotNull("The id should be correct.", actual.getId());
        } else {
            Assert.assertEquals("The id should be correct.", expected.getId(), actual.getId());
        }

        Assert.assertEquals("The effectiv amount should be correct.", expected.getEffectiveAmount(),
            actual.getEffectiveAmount());
        Assert.assertEquals("The imageId should be correct.", expected.getImageId(), actual.getImageId());
        Assert.assertEquals("The max amount should be correct.", expected.getMaxAmount(), actual.getMaxAmount());
    }

    /**
     * <p>
     * Asserts the two given Auction instances to be equals.
     * </p>
     *
     * @param expected the expected AuctionDTO instance.
     * @param actual the actual Auction instance.
     * @param getFromDB whether the auction is got from database.
     */
    public static void assertEquals(Auction expected, Auction actual, boolean getFromDB) {
        Assert.assertEquals("The id should be correct.", expected.getId(), actual.getId());

        Assert.assertEquals("The item count should be correct.", expected.getItemCount(), actual.getItemCount());
        Assert.assertEquals("The minimun bid should be correct.", expected.getMinimumBid(), actual.getMinimumBid());

        if (getFromDB) {
            Assert.assertEquals("The description should be empty", "", actual.getDescription());
            Assert.assertEquals("The summary should be empty", "", actual.getSummary());
        } else {
            Assert.assertEquals("The description should be correct.", expected.getDescription(),
                actual.getDescription());
            Assert.assertEquals("The summary should be correct.", expected.getSummary(), actual.getSummary());
        }

        Bid[] expectedBids = expected.getBids();
        Bid[] actualBids = actual.getBids();

        Assert.assertEquals("The bids should have the same length", expectedBids.length, actualBids.length);

        for (int i = 0; i < expectedBids.length; i++) {
            assertEquals((CustomBid) expectedBids[i], (CustomBid) actualBids[i], getFromDB);
        }
    }

    /**
     * <p>
     * Create instance of BidDTO.
     * </p>
     *
     * @param id the id
     * @param bidderId the bidderId
     * @param imageId the imageId
     * @param effectiveAmount the effectiveAmount
     * @param maxAmount the maxAmount
     * @param timeStamp timestamp
     *
     * @return instance of BidDTO
     */
    public static BidDTO getBidDTO(long id, long bidderId, long imageId, Integer effectiveAmount, int maxAmount,
        Date timeStamp) {
        BidDTO dto = new BidDTO();
        dto.setBidderId(bidderId);
        dto.setEffectiveAmount(effectiveAmount);
        dto.setId(new Long(id));
        dto.setImageId(imageId);
        dto.setMaxAmount(maxAmount);
        dto.setTimestamp(timeStamp);

        return dto;
    }

    /**
     * create instance of AuctionDTO.
     *
     * @param id the id
     * @param desc the desc
     * @param summary the summary
     * @param itemCount itemCount
     * @param minimumBid the minimumBid
     * @param startDate the startDate
     * @param endDate the endDate
     * @param bidDtos the BIdDTO array
     *
     * @return instance of AuctionDTO
     */
    public static AuctionDTO getAuctionDTO(long id, String desc, String summary, int itemCount, int minimumBid,
        Date startDate, Date endDate, BidDTO[] bidDtos) {
        AuctionDTO dto = new AuctionDTO();
        dto.setId(new Long(id));
        dto.setDescription(desc);
        dto.setSummary(summary);
        dto.setItemCount(itemCount);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setMinimumBid(minimumBid);
        dto.setBids(bidDtos);

        return dto;
    }
}
