/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.stresstests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;
import com.orpheus.auction.persistence.impl.SQLServerAuctionDAO;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * Stress tests for the class <code>com.orpheus.auction.persistence.impl.SQLServerAuctionDAO</code>.
 * @author urtks
 * @version 1.0
 */
public class SQLServerAuctionDAOTest extends TestCase {

    /**
     * The namespace used for the object factory config spec.
     */
    private static final String NAMESPACE = "com.orpheus.auction.persistence.impl.SQLServerAuctionDAO";

    /**
     * The namespace for the db connection factory configuration.
     */
    private static final String DB_CONNECTION_FACTORY = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * Represents the bidder id for testing.
     */
    private static final long BIDDER_ID = 1;

    /**
     * Represents the image id for testing.
     */
    private static final long IMAGE_ID = 3;

    /**
     * Instance of class under test.
     */
    private SQLServerAuctionDAO dao;

    /**
     * Setup the test environment.
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        addConfig();
        prepareData();

        dao = new SQLServerAuctionDAO(NAMESPACE);
    }

    /**
     * Cleanup the test environment.
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        clearData();
        clearConfig();
    }

    /**
     * Add the config files.
     * @throws Exception
     *             to JUnit
     */
    private void addConfig() throws Exception {
        clearConfig();

        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("stresstests/auction_persistence.xml");
        configManager.add("DBConnectionFactory_Config.xml");
    }

    /**
     * Clear the config files.
     * @throws Exception
     *             to JUnit
     */
    private void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Prepare the data in database.
     * @throws Exception
     *             to JUnit
     */
    private void prepareData() throws Exception {
        clearData();
        executeScript(getScript("test_files/stresstests/prepare.sql"));
    }

    /**
     * Clear the data in database.
     * @throws Exception
     *             to JUnit
     */
    private void clearData() throws Exception {
        executeScript(getScript("test_files/stresstests/clear.sql"));
    }

    /**
     * Loads the commands from a file.
     * @param filename
     *            the file containing the queries.
     * @return array of queries to execute
     * @throws IOException
     *             if the script cannot be loaded
     */
    private String[] getScript(String filename) throws IOException {
        File f = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String input = null;
        List commands = new ArrayList();
        while ((input = reader.readLine()) != null) {
            if (!input.trim().startsWith("#") && input.trim().length() > 0) {
                commands.add(input);
            }
        }
        return (String[]) commands.toArray(new String[commands.size()]);
    }

    /**
     * Executes the statements sequentially.
     * @param script
     *            list of commands
     * @throws Exception
     *             if DB problems occur
     */
    private void executeScript(String[] script) throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DB_CONNECTION_FACTORY);
        Connection conn = factory.createConnection();
        try {
            conn.setAutoCommit(false);
            for (int i = 0; i < script.length; i++) {
                Statement stmt = conn.createStatement();
                // System.out.println("executing: " + script[i]);
                stmt.executeUpdate(script[i]);
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Stress test for the method <code>AuctionDTO createAuction(AuctionDTO auction)</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testCreateAuction() throws Exception {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; ++i) {
            AuctionDTO auction = buildAuctionDTO(i + 3, 5);
            AuctionDTO dto = dao.createAuction(auction);
            assertNotNull("an object should be created", dto);
        }
        System.out.println("Running createAuction for 100 times takes " + (System.currentTimeMillis() - start) + "ms.");
    }

    /**
     * Stress test for the method <code>AuctionDTO getAuction(long auctionId)</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testGetAuction() throws Exception {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; ++i) {
            AuctionDTO dto = dao.getAuction(1);
            assertNotNull("an object should be returned", dto);
        }
        System.out.println("Running getAuction for 100 times takes " + (System.currentTimeMillis() - start) + "ms.");
    }

    /**
     * Stress test for the method <code>AuctionDTO updateAuction(AuctionDTO auction)</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateAuction() throws Exception {
        AuctionDTO auction = dao.getAuction(1);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; ++i) {
            auction.setEndDate(new Date(i + 1234567));
            auction.setDescription("" + i);

            AuctionDTO dto = dao.updateAuction(auction);
            assertNotNull("An object should be returned", dto);
        }
        System.out.println("Running updateAuction for 100 times takes " + (System.currentTimeMillis() - start) + "ms.");
    }

    /**
     * Stress test for the method <code>AuctionDTO updateBids(long auctionId, BidDTO[] bids)</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateBids() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; ++i) {
            AuctionDTO dto = dao.updateBids(1, buildBidDTOs(i % 20));
            assertNotNull("An object should be returned", dto);
        }
        System.out.println("Running updateBids for 100 times takes " + (System.currentTimeMillis() - start) + "ms.");
    }

    /**
     * Stress test for the method <code>void deleteAuction(long auctionId)</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteAuction() throws Exception {
        for (int i = 0; i < 100; ++i) {
            AuctionDTO auction = buildAuctionDTO(i + 3, 5);
            dao.createAuction(auction);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; ++i) {
            dao.deleteAuction(i + 3);
        }
        System.out.println("Running deleteAuction for 100 times takes " + (System.currentTimeMillis() - start) + "ms.");
    }

    /**
     * Stress test for the method
     * <code>AuctionDTO[] findAuctionsByDate(Date startingBy, Date endingAfter)</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testFindAuctionsByDate() throws Exception {
        // in test data:
        // auction 1 - '2006-11-01', '2006-11-07'
        // auction 2 - '2006-11-01', '2006-11-04'
        Date date1 = new GregorianCalendar(2006, Calendar.NOVEMBER, 1).getTime();
        Date date2 = new GregorianCalendar(2006, Calendar.NOVEMBER, 8).getTime();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; ++i) {
            AuctionDTO[] dtos = dao.findAuctionsByDate(date1, date2);

            // because both are enclosed
            assertEquals("a) 2 matches should be returned", 2, dtos.length);

        }
        System.out.println("Running findAuctionsByDate for 100 times takes " + (System.currentTimeMillis() - start)
                + "ms.");

    }

    /**
     * Stress test for the method
     * <code>AuctionDTO[] findAuctionsByBidder(long bidderId, Date endingAfter)</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testFindAuctionsByBidder() throws Exception {
        // In the DB
        // bidder 1 has bid on both auctions
        // bidder 2 has bid on auction 1
        // auction 1 - '2006-11-01', '2006-11-07'
        // auction 2 - '2006-11-01', '2006-11-04'

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; ++i) {
            Date date1 = new GregorianCalendar(2006, Calendar.NOVEMBER, 7).getTime();
            AuctionDTO[] dtos = dao.findAuctionsByBidder(1, date1);

            // end date should filter out auction 1
            assertEquals("1 matches should be returned", 1, dtos.length);

        }
        System.out.println("Running findAuctionsByBidder for 100 times takes " + (System.currentTimeMillis() - start)
                + "ms.");

    }

    /**
     * Build an auctionDTO for testing.
     * @param auctionId
     *            the auction id.
     * @param bidNum
     *            the number of the auction
     * @return an AuctionDTO instance
     */
    private AuctionDTO buildAuctionDTO(long auctionId, int bidNum) {
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
     * Build an array of BidDTO with special length.
     * @param bidNum
     *            the number of the bidDTO to build.
     * @return array of BidDTO instance, empty array when bidNum is zero.
     */
    private BidDTO[] buildBidDTOs(int bidNum) {
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
