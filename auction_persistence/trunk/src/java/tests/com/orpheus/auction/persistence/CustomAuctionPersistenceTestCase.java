/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.AuctionLocal;
import com.orpheus.auction.persistence.ejb.AuctionRemote;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionImpl;
import com.topcoder.util.cache.Cache;

import junit.framework.AssertionFailedError;

import net.sourceforge.junitejb.EJBTestCase;
import net.sourceforge.junitejb.EJBTestRunner;
import net.sourceforge.junitejb.EJBTestRunnerHome;
import net.sourceforge.junitejb.RemoteAssertionFailedError;
import net.sourceforge.junitejb.RemoteTestException;

import java.sql.Connection;

import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * <p>
 * Tests functionality and error cases of <code>CustomAuctionPersistence</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class CustomAuctionPersistenceTestCase extends EJBTestCase {
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

    /** Represents the <code>CustomAuctionPersistence</code> instance used for testing. */
    private CustomAuctionPersistence persistence;

    /**
     * The constructor of this class.
     *
     * @param testName the name of the test to run
     */
    protected CustomAuctionPersistenceTestCase(String testName) {
        super(testName);
    }

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The configuration namespace is loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        UnitTestHelper.addConfig();
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);
        persistence = createPersistence();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", null);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        Object obj = UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "auctionEJB");

        if (obj instanceof AuctionLocal) {
            ((AuctionLocal) obj).remove();
        } else if (obj instanceof AuctionRemote) {
            ((AuctionRemote) obj).remove();
        }

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
     * Accuracy test for the constructor <code>CustomAuctionPersistence(String)</code> when the namespace is correct.
     * Everything should be successfully initialized.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testCustomAuctionPersistence_Accuracy() throws Exception {
        assertNotNull("The CustomAuctionPersistence instance should be created.", persistence);
        assertNotNull("The auctionTranslator should be created.",
            UnitTestHelper.getPrivateField(persistence.getClass().getSuperclass(), persistence, "auctionTranslator"));
        assertNotNull("The cache should be created.",
            UnitTestHelper.getPrivateField(persistence.getClass().getSuperclass(), persistence, "cache"));
    }

    /**
     * Test the method of <code>createAuction(Auction)</code> when the given auction is null, IllegalArgumentException
     * is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NullAuction() throws Exception {
        try {
            persistence.createAuction(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(Auction)</code> when the given auction id is null, InvalidEntryException
     * is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NullID() throws Exception {
        AuctionDTO auctionDTO = UnitTestHelper.buildAuctionDTO(1, 5);
        Auction auction = new AuctionImpl(null, auctionDTO.getSummary(), auctionDTO.getDescription(),
                auctionDTO.getItemCount(), auctionDTO.getMinimumBid(), auctionDTO.getStartDate(),
                auctionDTO.getEndDate(), UnitTestHelper.buildBids(5));

        try {
            persistence.createAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(Auction)</code> when the auction id does not exist in hosting_block
     * table, InvalidEntryException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_InvalidAuctionId() throws Exception {
        Auction auction = UnitTestHelper.buildAuction(100, 5);

        try {
            persistence.createAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>CreateAuction(Auction)</code> when auction is already exist, DuplicateEntryException is
     * expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_ExistAuction() throws Exception {
        Auction auction = UnitTestHelper.buildAuction(1, 4);
        persistence.createAuction(auction);

        try {
            persistence.createAuction(auction);
            fail("DuplicateEntryException should be thrown.");
        } catch (DuplicateEntryException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method of <code>createAuction(Auction)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_Accuracy() throws Exception {
        Auction auction = UnitTestHelper.buildAuction(1, 5);

        // add it
        Auction ret = persistence.createAuction(auction);

        // check the cache
        assertSame("The created auction should be cached.", ret, persistence.getAuction(ret.getId().longValue()));

        // clear the cache of CustomAuctionPersistence
        Cache cache = (Cache) UnitTestHelper.getPrivateField(persistence.getClass().getSuperclass(), persistence,
                "cache");
        cache.clear();

        // get it and check it
        Auction persisted = persistence.getAuction(ret.getId().longValue());
        UnitTestHelper.assertEquals(auction, persisted, true);
    }

    /**
     * Test the method of <code>getAuction(long)</code> when auction already exists, EntryNotFoundException is
     * expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetAuction_NotExistAuction() throws Exception {
        try {
            persistence.getAuction(1);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method of <code>getAuction(long)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetAuction_Accuracy() throws Exception {
        Auction auction = UnitTestHelper.buildAuction(1, 5);

        // add it
        auction = persistence.createAuction(auction);

        // get it and check it
        Auction ret = persistence.getAuction(auction.getId().longValue());
        assertSame("Cache should be used.", auction, ret);

        Auction another = persistence.getAuction(auction.getId().longValue());
        assertSame("Cache should be used.", ret, another);
    }

    /**
     * Test the method of <code>updateAuction(Auction)</code> when the given auction is null, IllegalArgumentException
     * is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NullAuction() throws Exception {
        try {
            persistence.updateAuction(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(Auction)</code> when the given auction id is null, InvalidEntryException
     * is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NullID() throws Exception {
        AuctionDTO auctionDTO = UnitTestHelper.buildAuctionDTO(1, 5);
        Auction auction = new AuctionImpl(null, auctionDTO.getSummary(), auctionDTO.getDescription(),
                auctionDTO.getItemCount(), auctionDTO.getMinimumBid(), auctionDTO.getStartDate(),
                auctionDTO.getEndDate(), UnitTestHelper.buildBids(5));

        try {
            persistence.updateAuction(auction);
            fail("InvalidEntryException should be thrown.");
        } catch (InvalidEntryException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(Auction)</code> when auction does not exist, EntryNotFoundException is
     * expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NotExistAuction() throws Exception {
        Auction auction = UnitTestHelper.buildAuction(1, 5);

        try {
            persistence.updateAuction(auction);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(Auction)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_Accuracy() throws Exception {
        Auction auction = UnitTestHelper.buildAuction(1, 3);

        // add it
        persistence.createAuction(auction);

        Auction auctionUpdated = UnitTestHelper.buildAuction(1, 5);

        // update it
        persistence.updateAuction(auctionUpdated);

        Bid[] bids = new Bid[8];

        for (int i = 0; i < 3; i++) {
            bids[i] = auction.getBids()[i];
        }

        for (int i = 3; i < 8; i++) {
            bids[i] = auctionUpdated.getBids()[i - 3];
        }

        auctionUpdated = new AuctionImpl(auctionUpdated.getId(), auctionUpdated.getSummary(),
                auctionUpdated.getDescription(), auctionUpdated.getItemCount(), auctionUpdated.getMinimumBid(),
                auctionUpdated.getStartDate(), auctionUpdated.getEndDate(), bids);

        // get it and check it
        Auction persisted = persistence.getAuction(auction.getId().longValue());
        UnitTestHelper.assertEquals(auctionUpdated, persisted, true);
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code> when the given bids is null, IllegalArgumentException
     * is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_NullBids() throws Exception {
        try {
            persistence.updateBids(1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code> when the given bids contain null element,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_ContainNullBid() throws Exception {
        Bid[] bids = new Bid[] {null};

        try {
            persistence.updateBids(1, bids);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code> when the auction does not exist,
     * EntryNotFoundException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_NotExistAuction() throws Exception {
        Bid[] bids = UnitTestHelper.buildBids(2);

        try {
            persistence.updateBids(1, bids);
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(long, BidDTO[])</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_Accuracy() throws Exception {
        Auction auction = UnitTestHelper.buildAuction(1, 5);

        // add it
        auction = persistence.createAuction(auction);

        // update it
        Bid[] newIds = UnitTestHelper.buildBids(4);
        ((CustomBid) newIds[0]).setId(((CustomBid) auction.getBids()[0]).getId().longValue());
        ((CustomBid) newIds[1]).setId(((CustomBid) auction.getBids()[1]).getId().longValue());

        persistence.updateBids(auction.getId().longValue(), newIds);

        Bid[] bids = new Bid[7];

        for (int i = 0; i < 2; i++) {
            bids[i] = newIds[i];
        }

        for (int i = 2; i < 5; i++) {
            bids[i] = auction.getBids()[i];
        }

        for (int i = 5; i < 7; i++) {
            bids[i] = newIds[i - 3];
        }

        Auction auctionUpdated = new AuctionImpl(auction.getId(), auction.getSummary(), auction.getDescription(),
                auction.getItemCount(), auction.getMinimumBid(), auction.getStartDate(), auction.getEndDate(), bids);

        // get it and check it
        Auction persisted = persistence.getAuction(auction.getId().longValue());
        UnitTestHelper.assertEquals(auctionUpdated, persisted, true);
    }

    /**
     * Test the method of <code>deleteAuction(long)</code>when the given auction does not exist. EntryNotFoundException
     * is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteAuction_NotExistAuctionId() throws Exception {
        Auction auction = UnitTestHelper.buildAuction(1, 5);

        try {
            persistence.deleteAuction(auction.getId().longValue());
            fail("EntryNotFoundException is expected.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Test the method of <code>deleteAuction(long)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteAuction_Accuracy() throws Exception {
        Auction auction = UnitTestHelper.buildAuction(1, 5);

        // add it
        persistence.createAuction(auction);
        persistence.deleteAuction(auction.getId().longValue());

        // get it and check it
        try {
            persistence.getAuction(auction.getId().longValue());
            fail("EntryNotFoundException should be thrown.");
        } catch (EntryNotFoundException e) {
            // good
        }
    }

    /**
     * Test the method of <code>findAuctionsByDate(Date, Date)</code> when start and end date are not null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate_Accuracy1() throws Exception {
        Auction[] auctions = new Auction[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuction(i + 1, i + 1);

            // add it
            persistence.createAuction(auctions[i]);
        }

        startDate = new Date(15000);
        endDate = new Date(45000);

        // get it and check it
        Auction[] retAuctions = persistence.findAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 2, retAuctions.length);

        for (int i = 0; i < 2; i++) {
            UnitTestHelper.assertEquals(auctions[i + 1], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>findAuctionsByDate(Date, Date)</code> when end date is null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate_Accuracy2() throws Exception {
        Auction[] auctions = new Auction[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuction(i + 1, i + 1);

            // add it
            persistence.createAuction(auctions[i]);
        }

        startDate = new Date(15000);

        // get it and check it
        Auction[] retAuctions = persistence.findAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 4, retAuctions.length);

        for (int i = 0; i < 4; i++) {
            UnitTestHelper.assertEquals(auctions[i + 1], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>findAuctionsByDate(Date, Date)</code>when start date is null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate_Accuracy3() throws Exception {
        Auction[] auctions = new Auction[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuction(i + 1, i + 1);

            // add it
            persistence.createAuction(auctions[i]);
        }

        endDate = new Date(45000);

        // get it and check it
        Auction[] retAuctions = persistence.findAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 3, retAuctions.length);

        for (int i = 0; i < 3; i++) {
            UnitTestHelper.assertEquals(auctions[i], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>findAuctionsByDate(Date, Date)</code>when all start and end date are null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate_Accuracy4() throws Exception {
        Auction[] auctions = new Auction[5];
        Date startDate = null;
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuction(i + 1, i + 1);

            // add it
            persistence.createAuction(auctions[i]);
        }

        // get it and check it
        Auction[] retAuctions = persistence.findAuctionsByDate(startDate, endDate);
        assertEquals("The length got should be correct.", 5, retAuctions.length);

        for (int i = 0; i < 5; i++) {
            UnitTestHelper.assertEquals(auctions[i], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>findAuctionsByBidder(long, endingAfter)</code>when end date is not null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByBidder_Accuracy1() throws Exception {
        Auction[] auctions = new Auction[5];
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuction(i + 1, i + 1);

            // add it
            persistence.createAuction(auctions[i]);
        }

        endDate = new Date(45000);

        // get it and check it
        Auction[] retAuctions = persistence.findAuctionsByBidder(UnitTestHelper.BIDDER_ID, endDate);
        assertEquals("The length got should be correct.", 3, retAuctions.length);

        for (int i = 0; i < 3; i++) {
            UnitTestHelper.assertEquals(auctions[i], retAuctions[i], true);
        }
    }

    /**
     * Test the method of <code>findAuctionsByBidder(long, endingAfter)</code>when end date is null.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByBidder_Accuracy2() throws Exception {
        Auction[] auctions = new Auction[5];
        Date endDate = null;

        for (int i = 0; i < 5; i++) {
            auctions[i] = UnitTestHelper.buildAuction(i + 1, i + 1);

            // add it
            persistence.createAuction(auctions[i]);
        }

        // get it and check it
        Auction[] retAuctions = persistence.findAuctionsByBidder(UnitTestHelper.BIDDER_ID, endDate);
        assertEquals("The length got should be correct.", 5, retAuctions.length);

        for (int i = 0; i < 5; i++) {
            UnitTestHelper.assertEquals(auctions[i], retAuctions[i], true);
        }
    }

    /**
     * Returns the persistence used by this test.
     *
     * @return the persistence used by this test
     */
    protected CustomAuctionPersistence getPersistence() {
        return persistence;
    }

    /**
     * Test cases for subclasses of <code>CustomAuctionPersistence</code> should implement this method.
     *
     * @return an instance of the subclass.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected abstract CustomAuctionPersistence createPersistence() throws Exception;

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
