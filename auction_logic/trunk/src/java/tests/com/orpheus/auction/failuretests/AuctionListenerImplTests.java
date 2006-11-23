/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.failuretests;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.cactus.ServletTestCase;

import com.orpheus.auction.AuctionListenerImpl;
import com.orpheus.auction.AuctionListenerImplException;
import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.Bid;

/**
 * Unit test cases for the AuctionListenerImpl class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuctionListenerImplTests extends ServletTestCase {

    /**
     * AuctionListenerImpl used for testing.
     */
    private AuctionListenerImpl listener;

    /**
     * <p>
     * Creates new required instances.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        // used by KeyConstants and UserProfile
        TestsHelper.loadConfig(TestsHelper.CONFIG_FILE);

        listener = new AuctionListenerImpl(request.getSession().getServletContext());

        // remove attributes from the servlet context if any
        request.getSession().getServletContext().removeAttribute(KeyConstants.ADMINISTRATION_MANAGER_KEY);
        request.getSession().getServletContext().removeAttribute(KeyConstants.GAME_DATA_MANAGER_KEY);
    }

    /**
     * <p>
     * Clears configuration.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestsHelper.clearConfig();

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
     * Failure test for the method <code>AuctionListenerImpl(ServletContext context)</code>.
     * <p>
     * context is null. IllegalArgumentException is expected.
     */
    public void testConstructor1() {
        try {
            new AuctionListenerImpl(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * auction is null. IllegalArgumentException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete1() throws Exception {
        try {
            listener.auctionComplete(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * No value for KeyConstants.GAME_DATA_MANAGER_KEY in servlet context.
     * AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete2() throws Exception {
        // MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager =
                new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // do NOT add GameDataManager
        // request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY,
        // gameDataManager);

        // add AdministrationManager
        request.getSession().getServletContext().setAttribute(
                KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);

        // prepare bids
        CustomBid[] bids = new CustomBid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            bids[i].setId(10 + i);
        }
        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        auction.setBids(bids);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * The value for KeyConstants.GAME_DATA_MANAGER_KEY in servlet context is not of class
     * GameDataManager. AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete3() throws Exception {
        // MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager =
                new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // add an object that is not of class GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, new Long(0));

        // add AdministrationManager
        request.getSession().getServletContext().setAttribute(
                KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);

        // prepare bids
        CustomBid[] bids = new CustomBid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            bids[i].setId(10 + i);
        }
        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        auction.setBids(bids);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * No value for KeyConstants.ADMINISTRATION_MANAGER_KEY in servlet context.
     * AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete4() throws Exception {
        MockGameDataManager gameDataManager = new MockGameDataManager();
        // MockAdministrationManager administrationManager =
        // new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // add GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, gameDataManager);

        // do NOT add AdministrationManager
        // request.getSession().getServletContext().setAttribute(
        // KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);

        // prepare bids
        CustomBid[] bids = new CustomBid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            bids[i].setId(10 + i);
        }
        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        auction.setBids(bids);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * The value for KeyConstants.ADMINISTRATION_MANAGER_KEY in servlet context is not of class
     * AdministrationManager. AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete5() throws Exception {
        MockGameDataManager gameDataManager = new MockGameDataManager();
        // MockAdministrationManager administrationManager =
        // new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // add GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, gameDataManager);

        // add an object that is not of class AdministrationManager
        request.getSession().getServletContext().setAttribute(KeyConstants.ADMINISTRATION_MANAGER_KEY, new Long(0));

        // prepare bids
        CustomBid[] bids = new CustomBid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            bids[i].setId(10 + i);
        }
        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        auction.setBids(bids);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * Some of the bids is null. AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete6() throws Exception {
        MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager =
                new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // add GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, gameDataManager);

        // add an object that is not of class AdministrationManager
        request.getSession().getServletContext().setAttribute(
                KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);

        // prepare bids
        CustomBid[] bids = new CustomBid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            bids[i].setId(10 + i);
        }
        bids[2] = null;

        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        auction.setBids(bids);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * Some of the bids is not of class CustomBid. AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete7() throws Exception {
        MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager =
                new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // add GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, gameDataManager);

        // add an object that is not of class AdministrationManager
        request.getSession().getServletContext().setAttribute(
                KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);

        // prepare bids
        Bid[] bids = new Bid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            ((CustomBid) bids[i]).setId(10 + i);
        }
        bids[2] = new MockBid(2, 3, 4, new Date());
        ((MockBid) bids[2]).setId(12);

        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        auction.setBids(bids);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * The id of some of the bids is not set. AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete8() throws Exception {
        MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager =
                new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // add GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, gameDataManager);

        // add an object that is not of class AdministrationManager
        request.getSession().getServletContext().setAttribute(
                KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);

        // prepare bids
        Bid[] bids = new CustomBid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            if (i != 2) {
                ((CustomBid) bids[i]).setId(10 + i);
            }
        }

        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        auction.setBids(bids);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * The id of the auction is not set. AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete9() throws Exception {
        MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager =
                new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // add GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, gameDataManager);

        // add an object that is not of class AdministrationManager
        request.getSession().getServletContext().setAttribute(
                KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);

        // prepare bids
        Bid[] bids = new Bid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            ((CustomBid) bids[i]).setId(10 + i);
        }

        // prepare auction
        MockAuction auction = new MockAuction();
        // auction.setId(35);
        auction.setBids(bids);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * The bids of the auction are not set. AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete10() throws Exception {
        MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager =
                new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // add GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, gameDataManager);

        // add an object that is not of class AdministrationManager
        request.getSession().getServletContext().setAttribute(
                KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);

        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        // auction.setBids(bids);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * Failed to call method recordWinningBids. AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete11() throws Exception {
        MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager =
                new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // add GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, gameDataManager);

        // add an object that is not of class AdministrationManager
        request.getSession().getServletContext().setAttribute(
                KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);

        // prepare bids
        Bid[] bids = new CustomBid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            ((CustomBid) bids[i]).setId(10 + i);
        }

        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        auction.setBids(bids);

        gameDataManager.setFailed(true);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>void auctionComplete(Auction auction)</code>.
     * <p>
     * Failed to call method initializeSlotsForBlock. AuctionListenerImplException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testAuctionComplete12() throws Exception {
        MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager =
                new MockAdministrationManager(new MockPuzzleTypeSource(), "ns1");

        // add GameDataManager
        request.getSession().getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, gameDataManager);

        // add an object that is not of class AdministrationManager
        request.getSession().getServletContext().setAttribute(
                KeyConstants.ADMINISTRATION_MANAGER_KEY, administrationManager);

        // prepare bids
        Bid[] bids = new CustomBid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            ((CustomBid) bids[i]).setId(10 + i);
        }

        // prepare auction
        MockAuction auction = new MockAuction();
        auction.setId(35);
        auction.setBids(bids);

        administrationManager.setFailed(true);

        try {
            listener.auctionComplete(auction);
            fail("AuctionListenerImplException is expected");
        } catch (AuctionListenerImplException e) {
            // ok.
        }
    }
}
