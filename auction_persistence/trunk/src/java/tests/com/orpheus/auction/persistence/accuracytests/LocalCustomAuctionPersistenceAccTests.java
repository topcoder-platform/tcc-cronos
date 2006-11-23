/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.accuracytests;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.auction.persistence.LocalCustomAuctionPersistence;
import com.orpheus.auction.persistence.PersistenceException;
import com.orpheus.auction.persistence.ejb.AuctionBean;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.AuctionLocal;
import com.orpheus.auction.persistence.ejb.AuctionLocalHome;
import com.orpheus.auction.persistence.ejb.BidDTO;
import com.orpheus.auction.persistence.impl.DefaultAuctionTranslator;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.impl.AuctionImpl;
import com.topcoder.util.cache.Cache;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.sql.Connection;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * <p>
 * Test the class LocalCustomAuctionPersistence.
 * </p>
 * 
 * @author waits
 * @version 1.0
 */
public class LocalCustomAuctionPersistenceAccTests extends TestCase {
    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** timestamp for bidDTO. */
    private Date timestamp = null;

    /** start date. */
    private Date startDate = null;

    /** end date. */
    private Date endDate = null;

    /** BidDTO array. */
    private BidDTO[] bidDTOs = null;

    /** AuctionDTO instance for testing. */
    private AuctionDTO auctionDTO = null;

    /** Auction instance. */
    private Auction auction = null;

    /** translator. */
    private DefaultAuctionTranslator translator = null;

    /** LocalCustomAuctionPersistence instnace to test against. */
    private LocalCustomAuctionPersistence persistence = null;

    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** mock user trnasaction. */
    private MockUserTransaction mockTransaction;

    /**
     * create instance.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();

        TestHelper.addConfigFile(TestHelper.AUCTION_PERSISTENCE_CONFIG_FILE);
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);

        //insert data to database for testing
        connection = TestHelper.getConnection();
        TestHelper.prepareData(connection);

        bidDTOs = new BidDTO[1];
        timestamp = new Date(1000);
        bidDTOs[0] = TestHelper.getBidDTO(1, 1, 3, new Integer(1), 1, timestamp);
        startDate = new Date(1000);
        endDate = new Date(2000);

        auctionDTO = TestHelper.getAuctionDTO(1, "desc", "summary", 1, 1, startDate, endDate, bidDTOs);
        translator = new DefaultAuctionTranslator();

        auction = this.translator.assembleAuction(auctionDTO);

        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("auction/AuctionEJB",
                AuctionLocalHome.class, AuctionLocal.class, new AuctionBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        //create instance
        this.persistence = new LocalCustomAuctionPersistence(TestHelper.LOCAL_NAMESPACE);
    }
    

    /**
     * Test thhe createAuction method.
     *
     * @throws Exception into JUnit
     */
    public void testCreateAuction() throws Exception {
        // add it
        Auction ret = persistence.createAuction(auction);

        // check the cache
        assertSame("The created auction should be cached.", ret, persistence.getAuction(ret.getId().longValue()));

        // clear the cache of CustomAuctionPersistence
        Cache cache = (Cache) TestHelper.getPrivateField(persistence.getClass().getSuperclass(), persistence, "cache");
        cache.clear();

        // get it and check it
        Auction persisted = persistence.getAuction(ret.getId().longValue());
        TestHelper.assertEquals(auction, persisted, true);

        //clear the 'cache'
        persistence.deleteAuction(this.auction.getId().longValue());
    }

    /**
     * Test the updateAuction method.
     *
     * @throws Exception into Junit
     */
    public void testUpdateAuction() throws Exception {
        // add it
        Auction ret = persistence.createAuction(auction);

        Auction updated = new AuctionImpl(ret.getId(), "summary", "desc", ret.getItemCount() + 1,
                ret.getMinimumBid() + 1, new Date(1000), new Date(2000), ret.getBids());

        this.persistence.updateAuction(updated);

        Auction getOne = this.persistence.getAuction(ret.getId().longValue());
        //verify
        TestHelper.assertEquals(updated, getOne, true);

        //clear the 'cache'
        persistence.deleteAuction(this.auction.getId().longValue());
    }

    /**
     * test updateBids method.
     *
     * @throws Exception into Junit
     */
    public void testUpdateBids() throws Exception {
        // add it
        Auction ret = persistence.createAuction(auction);

        CustomBid[] toUpdated = new CustomBid[ret.getBids().length];

        for (int i = 0; i < toUpdated.length; i++) {
            toUpdated[i] = new CustomBid(ret.getBids()[i].getBidderId(), ((CustomBid) ret.getBids()[i]).getImageId(),
                    ret.getBids()[i].getMaxAmount() + 1, ret.getBids()[i].getTimestamp());
            toUpdated[i].setId(((CustomBid) ret.getBids()[i]).getId().longValue());
        }

        //update
        this.persistence.updateBids(ret.getId().longValue(), toUpdated);

        //verify
        Auction auctionUpdated = new AuctionImpl(auction.getId(), auction.getSummary(), auction.getDescription(),
                auction.getItemCount(), auction.getMinimumBid(), auction.getStartDate(), auction.getEndDate(),
                toUpdated);
        Auction getOne = this.persistence.getAuction(ret.getId().longValue());

        TestHelper.assertEquals(auctionUpdated, getOne, true);

        //clear the 'cache'
        persistence.deleteAuction(this.auction.getId().longValue());
    }

    /**
     * Test the DeleteAuction method.
     *
     * @throws Exception into Junit
     */
    public void testDeleteAuction() throws Exception {
        //add it
        Auction ret = persistence.createAuction(auction);
        this.persistence.deleteAuction(ret.getId().longValue());

        try {
            persistence.deleteAuction(ret.getId().longValue());
            fail("delete a not exist one.");
        } catch (PersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the findAuctionsByDate method.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testFindAuctionsByDate() throws Exception {
        //add it
        Auction ret = persistence.createAuction(auction);
        startDate = new Date(100);
        endDate = new Date(4000);
        assertEquals("The size of return value is not correct.", 1,
            persistence.findAuctionsByDate(startDate, endDate).length);
        TestHelper.assertEquals(ret, persistence.findAuctionsByDate(startDate, endDate)[0], true);
        assertEquals("The return value should be empty.", 0,
            this.persistence.findAuctionsByDate(new Date(4000), new Date(5000)).length);
        //clear the 'cache'
        persistence.deleteAuction(this.auction.getId().longValue());
    }

    /**
     * <p>
     * Test the findAuctionsByBidder method.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testFindAuctionsByBidder() throws Exception {
        //add it
        Auction ret = persistence.createAuction(auction);
        endDate = new Date(5000);
        assertEquals("The size of return value is not correct.", 1,
            persistence.findAuctionsByBidder(1, endDate).length);
        TestHelper.assertEquals(ret, persistence.findAuctionsByBidder(1, endDate)[0], true);
        assertEquals("The return value should be empty.", 0,
            this.persistence.findAuctionsByBidder(1, new Date(300)).length);
        //clear the 'cache'
        persistence.deleteAuction(this.auction.getId().longValue());
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        try {
            TestHelper.clearConfigManager();
            TestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
