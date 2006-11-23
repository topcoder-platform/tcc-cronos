/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionImpl;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Assert;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class UnitTestHelper {
    /** Represents the bidder id for testing. */
    public static final long BIDDER_ID = 1;

    /** Represents the image id for testing. */
    public static final long IMAGE_ID = 3;

    /**
     * <p>
     * Creates a new instance of <code>UnitTestHelper</code> class. The private constructor prevents the creation of a
     * new instance.
     * </p>
     */
    private UnitTestHelper() {
    }

    /**
     * <p>
     * Add the valid config files for testing.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void addConfig() throws Exception {
        clearConfig();

        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("Auction_Persistence_Config.xml");
        configManager.add("DBConnectionFactory_Config.xml");
    }

    /**
     * <p>
     * clear the config.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
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
     * Sets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
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
     * <p>
     * Asserts the two given object arrays to be equals. The two object arrays will be regarded to be equals only if
     * both the length and the content are equals.
     * </p>
     *
     * @param errorMessage the error message which will be thrown when the two object arrays are not equals.
     * @param expected the expected object array.
     * @param actual the actual object array.
     */
    public static void assertEquals(String errorMessage, Object[] expected, Object[] actual) {
        Assert.assertEquals(errorMessage, expected.length, actual.length);

        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(errorMessage, expected[i], actual[i]);
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
        DBConnectionFactory factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        return factory.createConnection();
    }

    /**
     * <p>
     * Asserts the two given BidDTO instances to be equals.
     * </p>
     *
     * @param expected the expected BidDTO instance.
     * @param actual the actual BidDTO instance.
     * @param getFromDB whether the auction is got from database.
     */
    public static void assertEquals(BidDTO expected, BidDTO actual, boolean getFromDB) {
        Assert.assertEquals("The bidder id should be correct.", expected.getBidderId(), actual.getBidderId());
        Assert.assertEquals("The effective amount should be correct.", expected.getEffectiveAmount(),
            actual.getEffectiveAmount());
        Assert.assertEquals("The id should be correct.", expected.getId(), actual.getId());
        Assert.assertEquals("The imageId should be correct.", expected.getImageId(), actual.getImageId());
        Assert.assertEquals("The max amount should be correct.", expected.getMaxAmount(), actual.getMaxAmount());
        Assert.assertEquals("The time stamp should be correct.", expected.getTimestamp().getTime() / 1000,
            actual.getTimestamp().getTime() / 1000);
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

        Assert.assertEquals("The start date should be correct.", expected.getStartDate().getTime() / 1000,
            actual.getStartDate().getTime() / 1000);
        Assert.assertEquals("The end date should be correct.", expected.getEndDate().getTime() / 1000,
            actual.getEndDate().getTime() / 1000);
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

        BidDTO[] expectedBids = expected.getBids();
        BidDTO[] actualBids = actual.getBids();

        Assert.assertEquals("The bids should have the same length.", expectedBids.length, actualBids.length);

        for (int i = 0; i < expectedBids.length; i++) {
            assertEquals(expectedBids[i], actualBids[i], getFromDB);
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
        Assert.assertEquals("The time stamp should be correct.", expected.getTimestamp().getTime() / 1000,
                actual.getTimestamp().getTime() / 1000);
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

        Assert.assertEquals("The start date should be correct.", expected.getStartDate().getTime() / 1000,
            actual.getStartDate().getTime() / 1000);
        Assert.assertEquals("The end date should be correct.", expected.getEndDate().getTime() / 1000,
            actual.getEndDate().getTime() / 1000);
        Assert.assertEquals("The item count should be correct.", expected.getItemCount(), actual.getItemCount());
        Assert.assertEquals("The minimun bid should be correct.", expected.getMinimumBid(), actual.getMinimumBid());

        if (getFromDB) {
            Assert.assertEquals("The description should be empty", "", actual.getDescription());
            Assert.assertEquals("The summary should be empty", "", actual.getSummary());
        } else {
            Assert.assertEquals("The description should be correct.",
                    expected.getDescription(), actual.getDescription());
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
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void clearDatabase(Connection connection) throws SQLException {
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
     * Get the number of records in auction table.
     *
     * @param connection the connection to the database
     *
     * @return the number of records in auction table
     *
     * @throws Exception error occurs when access the database.
     */
    public static int getAuctionRecords(Connection connection) throws Exception {
        return getTableRecords(connection, "auction");
    }

    /**
     * Get the number of records in bid table.
     *
     * @param connection the connection to the database
     *
     * @return the number of records in bid table.
     *
     * @throws Exception error occurs when access the database.
     */
    public static int getBidRecords(Connection connection) throws Exception {
        return getTableRecords(connection, "bid");
    }

    /**
     * Get the number of records in effective_bid table.
     *
     * @param connection the connection to the database
     *
     * @return the number of records in effective_bid table.
     *
     * @throws Exception error occurs when access the database.
     */
    public static int getEffectiveBidRecords(Connection connection) throws Exception {
        return getTableRecords(connection, "effective_bid");
    }

    /**
     * Get the number of records in the special table.
     *
     * @param connection the connection to the database
     * @param tableName table name
     *
     * @return the record number in the special table.
     *
     * @throws Exception error occurs when access the database.
     */
    private static int getTableRecords(Connection connection, String tableName) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet rs = null;

        try {
            rs = statement.executeQuery("select count(*) FROM " + tableName);
            rs.next();

            return rs.getInt(1);
        } finally {
            rs.close();
            statement.close();
        }
    }

    /**
     * Build an auctionDTO for testing.
     *
     * @param auctionId the auction id.
     * @param bidNum the number of the auction
     *
     * @return an AuctionDTO instance
     */
    public static AuctionDTO buildAuctionDTO(long auctionId, int bidNum) {
        AuctionDTO auction = new AuctionDTO();
        auction.setBids(buildBidDTOs(bidNum));
        auction.setDescription("description" + bidNum);
        auction.setSummary("summary" + bidNum);
        auction.setStartDate(new Date(auctionId * 10000));
        auction.setEndDate(new Date(auctionId * 10000 + 5000));
        auction.setId(new Long(auctionId));
        auction.setItemCount(bidNum);
        auction.setMinimumBid(bidNum);

        return auction;
    }

    /**
     * Build an Auction for testing.
     *
     * @param auctionId the auction id.
     * @param bidNum the number of the auction.
     *
     * @return an Auction instance.
     */
    public static Auction buildAuction(long auctionId, int bidNum) {
        AuctionDTO auctionDTO = buildAuctionDTO(auctionId, bidNum);

        return new AuctionImpl(auctionDTO.getId(), auctionDTO.getSummary(), auctionDTO.getDescription(),
            auctionDTO.getItemCount(), auctionDTO.getMinimumBid(), auctionDTO.getStartDate(), auctionDTO.getEndDate(),
            buildBids(bidNum));
    }

    /**
     * Build an array of Bid with special length.
     *
     * @param bidNum the number of the bidDTO to build.
     *
     * @return array of Bid instance, empty array when bidNum is zero.
     */
    public static Bid[] buildBids(int bidNum) {
        BidDTO[] bidDTOs = buildBidDTOs(bidNum);
        Bid[] bids = new Bid[bidDTOs.length];

        for (int i = 0; i < bidDTOs.length; i++) {
            CustomBid bid = new CustomBid(bidDTOs[i].getBidderId(), bidDTOs[i].getImageId(), bidDTOs[i].getMaxAmount(),
                    bidDTOs[i].getTimestamp());

            if (bidDTOs[i].getEffectiveAmount() != null) {
                bid.setEffectiveAmount(bidDTOs[i].getEffectiveAmount().intValue());
            }

            if (bidDTOs[i].getId() != null) {
                bid.setId(bidDTOs[i].getId().longValue());
            }

            bids[i] = bid;
        }

        return bids;
    }

    /**
     * Build an array of BidDTO with special length.
     *
     * @param bidNum the number of the bidDTO to build.
     *
     * @return array of BidDTO instance, empty array when bidNum is zero.
     */
    public static BidDTO[] buildBidDTOs(int bidNum) {
        Calendar calendar = Calendar.getInstance();

        BidDTO[] bids = new BidDTO[bidNum];

        for (int i = 0; i < bids.length; i++) {
            bids[i] = new BidDTO();
            bids[i].setBidderId(BIDDER_ID);

            if (((i + bidNum) % 2) == 0) {
                bids[i].setEffectiveAmount(new Integer(i));
            }

            bids[i].setImageId(IMAGE_ID);
            bids[i].setMaxAmount(bidNum + i);

            calendar.add(Calendar.DAY_OF_YEAR, bidNum);
            bids[i].setTimestamp(calendar.getTime());
        }

        return bids;
    }
}
