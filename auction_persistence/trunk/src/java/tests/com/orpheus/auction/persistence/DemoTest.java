/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.impl.SQLServerAuctionDAO;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;

import junit.framework.AssertionFailedError;

import net.sourceforge.junitejb.EJBTestCase;
import net.sourceforge.junitejb.EJBTestRunner;
import net.sourceforge.junitejb.EJBTestRunnerHome;
import net.sourceforge.junitejb.RemoteAssertionFailedError;
import net.sourceforge.junitejb.RemoteTestException;

import java.sql.Connection;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * <p>
 * Demonstrates the usage of this component. The usage include only one part.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends EJBTestCase {
    /** The EJB name on which the local test cases are run on. */
    private static final String TEST_RUNNER_NAME = "auction/EJBTestRunner";

    /** The host to get the EJB homes and beans. */
    private static final String REMOTE_NAMING_HOST = "localhost:1099";

    /**
     * A <code>Hashtable</code> containing naming context environment parameters for use when constructing an
     * InitialContext.
     */
    private static Hashtable namingContextEnv;

    static {
        namingContextEnv = new Hashtable();
        namingContextEnv.put(Context.PROVIDER_URL, REMOTE_NAMING_HOST);
        namingContextEnv.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
    }

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /**
     * The constructor of this class.
     *
     * @param testName the name of the test to run
     */
    public DemoTest(String testName) {
        super(testName);
    }

    /**
     * <p>
     * Sets up the test environment. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        UnitTestHelper.addConfig();
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);

        SQLServerAuctionDAO persistence = new SQLServerAuctionDAO(
                "com.orpheus.auction.persistence.impl.SQLServerAuctionDAO");
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", null);
        persistence.createAuction(UnitTestHelper.buildAuctionDTO(3, 5));
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            UnitTestHelper.clearConfig();
            UnitTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        super.tearDown();
    }

    /**
     * <p>
     * This demo will demonstrate typical usage of the CustomAuctionPersistence class. This demo will focus on the
     * remote implementation, with the knowledge that usage of the local implementation is the same. Typically this is
     * done in the manager, but we will show method calls directly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testTypicalAuctionClient_Usage() throws Exception {
        // create remote instance with a namespace
        CustomAuctionPersistence client = new RemoteCustomAuctionPersistence(
                "com.orpheus.auction.persistence.RemoteCustomAuctionPersistence");

        // we might begin by inserting some new auction
        Auction auction1 = UnitTestHelper.buildAuction(1, 3);
        Auction auction2 = UnitTestHelper.buildAuction(2, 4);
        client.createAuction(auction1);
        client.createAuction(auction2);

        // at this point, both are inserted and cached at both levels
        // we retrieve a cached and non-cached auction, respectively
        Auction cAuction1 = client.getAuction(1);
        Auction cAuction3 = client.getAuction(3);

        // #3 comes from DB since not in cache. After this retrieval it is cached
        // we now update an auction, after we make some changes
        // both caches are updated with this
        cAuction3 = UnitTestHelper.buildAuction(cAuction3.getId().longValue(), 1);
        client.updateAuction(cAuction3);

        // we now update some bids in auction #2. Some bids will have changes, and others will be new.
        // Yet others will not be part of the update but exist in the persistence
        Bid[] bids = UnitTestHelper.buildBids(5);

        for (int i = 0; i < 2; i++) {
            bids[i] = auction2.getBids()[i];
        }

        // both caches are updated with the auction #2. It is important to note
        // that this updated auction will contain the new bids, updated bids, and
        // existing bids that have not been altered in the persistence, and were
        // not part of the bids input array.
        client.updateBids(2, bids);

        // we want to delete an auction
        // auction deleted from persistence and both caches
        client.deleteAuction(cAuction1.getId().longValue());

        // retrieve all auctions between 1/1/2001 and 31/12/2001
        // the client and DAO cache/recache all of these auctions
        Date startDate = null;
        Date endDate = null;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2001);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        startDate = c.getTime();
        c.set(Calendar.MONTH, 12);
        c.set(Calendar.DAY_OF_MONTH, 131);
        endDate = c.getTime();

        Auction[] auctionsIn2001 = client.findAuctionsByDate(startDate, endDate);

        // retrieve all auctions
        // the client and DAO cache/recache all of these auctions
        Auction[] auctionsAll = client.findAuctionsByDate(null, null);

        // retrieve all auctions for a bidder ending no latter than 31/12/2002
        // the client and DAO cache/recache all of these auctions
        long bidderId = UnitTestHelper.BIDDER_ID;
        c.set(Calendar.YEAR, 2002);
        c.set(Calendar.MONTH, 12);
        c.set(Calendar.DAY_OF_MONTH, 31);

        Date endDate2 = c.getTime();
        startDate = c.getTime();

        Auction[] myAuctions = client.findAuctionsByBidder(bidderId, endDate2);

        // retrieve all auctions for a bidder
        // the client and DAO cache/recache all of these auctions
        Auction[] myAuctionsAll = client.findAuctionsByBidder(bidderId, null);
    }

    /**
     * This method copied directly from EJBTestCase to work around that class' brain dead InitialContext construction.
     * This works with getEJBTestRunner(), below.
     *
     * @throws Throwable if any unexpected exception occurs.
     */
    public void runBare() throws Throwable {
        if (!isServerSide()) {
            // We're not on the server side yet, invoke the test on the serverside.
            EJBTestRunner testRunner = null;

            try {
                testRunner = getEJBTestRunner();
                assertNotNull("Obtained a null EJBTestRunner reference", testRunner);
                testRunner.run(getClass().getName(), getName());
            } catch (RemoteTestException e) {
                // if the remote test exception is from an assertion error
                // rethrow it with a sub class of AssertionFailedError so it is
                // picked up as a failure and not an error.
                // The server has to throw sub classes of Error because that is the
                // allowable scope of application exceptions. So
                // AssertionFailedError which is an instance of error has to be
                // wrapped in an exception.
                Throwable remote = e.getRemoteThrowable();

                if (remote instanceof AssertionFailedError) {
                    throw new RemoteAssertionFailedError((AssertionFailedError) remote, e.getRemoteStackTrace());
                }

                throw e;
            } finally {
                // be a good citizen, drop my ref to the session bean.
                if (testRunner != null) {
                    testRunner.remove();
                }
            }
        } else {
            // We're on the server side, so invoke the test the regular way.
            super.runBare();
        }
    }

    /**
     * Looks up the ejb test runner home in JNDI (at "ejb/EJBTestRunner") and creates a new runner.  This method is
     * invoked only on the client side.
     *
     * @return the ejb test runner created from home
     *
     * @throws Exception if any problem happens
     */
    private EJBTestRunner getEJBTestRunner() throws Exception {
        InitialContext jndiContext = new InitialContext(namingContextEnv);

        // Get a reference from this to the Bean's Home interface
        Object ref = jndiContext.lookup(TEST_RUNNER_NAME);
        EJBTestRunnerHome runnerHome = (EJBTestRunnerHome) PortableRemoteObject.narrow(ref, EJBTestRunnerHome.class);

        // create the test runner
        return runnerHome.create();
    }
}
