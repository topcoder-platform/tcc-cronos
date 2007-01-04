/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.cactus.ServletTestCase;

import com.orpheus.auction.persistence.CustomBid;

/**
 * <p>
 * Unit test cases for the AuctionListenerImpl class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuctionListenerImplUnitTests extends ServletTestCase {

    /**
     * <p>
     * AuctionListenerImpl used for testing.s
     * </p>
     */
    private AuctionListenerImpl listener;

    /**
     * <p>
     * Creates new required instances.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        // used by KeyConstants and UserProfile
        UnitTestsHelper.loadConfig(UnitTestsHelper.CONFIG_FILE);

        listener = new AuctionListenerImpl(request.getSession().getServletContext());

        // remove attributes from the servlet context if any
        request.getSession().getServletContext().removeAttribute(
            KeyConstants.ADMINISTRATION_MANAGER_KEY);
        request.getSession().getServletContext()
            .removeAttribute(KeyConstants.GAME_DATA_MANAGER_KEY);
    }

    /**
     * <p>
     * Clears configuration.
     * </p>
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        UnitTestsHelper.clearConfig();

        HttpSession session = request.getSession(false);
        if (session != null) {
            // remove attributes from the servlet context if any
            session.getServletContext().removeAttribute(KeyConstants.ADMINISTRATION_MANAGER_KEY);
            session.getServletContext().removeAttribute(KeyConstants.GAME_DATA_MANAGER_KEY);
            // invalidate session if it exists
            session.invalidate();
        }

    }

    /**
     * <p>
     * Tests AuctionListenerImpl(ServletContext) for failure. Passes null, IllegalArgumentException
     * is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new AuctionListenerImpl(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that AuctionListenerImpl(ServletContext) instance is created and arguments are
     * correctly propagated.
     * </p>
     */
    public void testConstructor2() {
        AuctionListenerImpl i = new AuctionListenerImpl(request.getSession().getServletContext());
        assertNotNull("Unable to instantiate AuctionListenerImpl", i);
    }

    /**
     * <p>
     * Tests auctionComplete(Auction) for failure. Passes null, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testAuctionComplete1() {
        AuctionListenerImpl i = new AuctionListenerImpl(request.getSession().getServletContext());
        try {
            i.auctionComplete(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that auctionComplete(Auction) works correctly.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testAuctionComplete2() throws Exception {
        MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager = new MockAdministrationManager();
        MockAuctionManager auctionManager = new MockAuctionManager();
        MockAuctionStrategy auctionStrategy = new MockAuctionStrategy();

        // add GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY,
            gameDataManager);
        // add AdministrationManager
        request.getSession().getServletContext().setAttribute(
            KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);
        // add AuctionManager
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionStrategy(auctionStrategy);

        // prepare bids
        CustomBid[] bids = new CustomBid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            bids[i].setId(10 + i);
        }
        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        auctionStrategy.setLeadingBids(bids);

        listener.auctionComplete(auction);

        assertEquals("Tested method works incorrectly", 35, administrationManager.getLastBlockId());
        assertEquals("Tested method works incorrectly", 35, gameDataManager.getLastBlockId());
        long[] ids = gameDataManager.getLastBidIds();
        Arrays.sort(ids);
        assertTrue("Tested method works incorrectly", Arrays.equals(new long[] {10, 11, 12},
            gameDataManager.getLastBidIds()));
    }

    /**
     * <p>
     * Tests auctionStarted(Auction) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testAuctionStarted1() {
        try {
            listener.auctionStarted(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests bidSubmitted(Auction, Bid) for failure. Passes null as first argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testBidSubmitted1() {
        try {
            listener.bidSubmitted(null, new CustomBid(1, 2, 3, new Date()));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests bidSubmitted(Auction, Bid) for failure. Passes null as second argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testBidSubmitted2() {
        try {
            listener.bidSubmitted(new MockAuction(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests bidUpdated(Auction, Bid) for failure. Passes null as first argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testBidUpdated1() {
        try {
            listener.bidUpdated(null, new CustomBid(1, 2, 3, new Date()));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests bidUpdated(Auction, Bid) for failure. Passes null as second argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testBidUpdated2() {
        try {
            listener.bidUpdated(new MockAuction(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests outBid(Auction, Bid) for failure. Passes null as first argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testOutBid1() {
        try {
            listener.outBid(null, new CustomBid(1, 2, 3, new Date()));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests outBid(Auction, Bid) for failure. Passes null as second argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testOutBid2() {
        try {
            listener.outBid(new MockAuction(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }
}
