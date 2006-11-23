/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.w3c.dom.Element;

import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.util.auction.Bid;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;

/**
 * <p>
 * Unit test cases for the BidPlacementHandler class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BidPlacementHandlerUnitTests extends ServletTestCase {

    /**
     * <p>
     * ActionContext instance. Used for testing.
     * </p>
     */
    private ActionContext actionContext;

    /**
     * <p>
     * BidPlacementHandler instance. Used for testing.
     * </p>
     */
    private BidPlacementHandler handler;

    /**
     * <p>
     * MockAuctionManager instance. Used for testing.
     * </p>
     */
    private MockAuctionManager auctionManager;

    /**
     * <p>
     * MockAuctionPersistence instance. Used for testing.
     * </p>
     */
    private MockAuctionPersistence auctionPersistence;

    /**
     * <p>
     * LoginHandler instance. Used for testing.
     * </p>
     */
    private LoginHandler loginHandler;

    /**
     * <p>
     * UserProfileManager instance. Used for testing.
     * </p>
     */
    private UserProfileManager profileManager;

    /**
     * <p>
     * Initializes HTTP related parameters that can be used in all test methods.
     * </p>
     * @param theRequest WebRequest instance to setup initial parameters.
     */
    public void begin(WebRequest theRequest) {
        theRequest.addParameter("auction", "239");
        theRequest.addParameter("amount", "392");
        theRequest.addParameter("image", "923");

        // add the user parameters to login
        theRequest.addParameter("firstName", "James");
        theRequest.addParameter("lastName", "Bond");
        theRequest.addParameter("e-mail", "007@sis.uk");
    }

    /**
     * <p>
     * Creates new required instances.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        // used by KeyConstants and UserProfile
        UnitTestsHelper.loadConfig(UnitTestsHelper.CONFIG_FILE);

        actionContext = new ActionContext(request, response);
        handler = new BidPlacementHandler(UnitTestsHelper.createHandlerElement(
            "BidPlacementHandler", new String[] {
                "auction_id_param_key",
                "max_amount_param_key",
                "image_id_param_key"}, new String[] {"auction", "amount", "image"}));

        auctionManager = new MockAuctionManager();
        auctionPersistence = new MockAuctionPersistence();

        // initialize profile manager
        profileManager = new MockUserProfileManager();

        // initialize login handler
        Map attrs = new HashMap();
        attrs.put("profile_manager", profileManager);
        attrs.put("request_param_keys", new String[] {"firstName", "lastName", "e-mail"});
        attrs.put("search_profile_keys", new String[] {"first_name", "last_name"});
        attrs.put("credential_profile_keys", new String[] {"email_address"});
        attrs.put("no_such_profile_result_name", "no_such_profile_result_name");
        attrs.put("incorrect_credential_result_name", "incorrect_credential_result_name");
        loginHandler = new LoginHandler(attrs);

        // remove attribute from the servlet context if it exists
        request.getSession().getServletContext().removeAttribute(KeyConstants.AUCTION_MANAGER_KEY);
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
            // remove attribute from the servlet context if it exists
            session.getServletContext().removeAttribute(KeyConstants.AUCTION_MANAGER_KEY);
            // invalidate session if it exists
            session.invalidate();
        }
    }

    /**
     * <p>
     * Tests BidPlacementHandler(Element) for failure. Passes null, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testConstructor1_1() {
        Element element = null;
        try {
            new BidPlacementHandler(element);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests BidPlacementHandler(Element) for failure. Passes empty auction_id_param_key,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1_2() throws Exception {
        try {
            new BidPlacementHandler(UnitTestsHelper
                .createHandlerElement("BidUpdateHandler", new String[] {
                    "auction_id_param_key",
                    "max_amount_param_key",
                    "image_id_param_key"}, new String[] {" ", "bcd", "cde"}));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests BidPlacementHandler(Element) for failure. Passes empty max_amount_param_key,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1_3() throws Exception {
        try {
            new BidPlacementHandler(UnitTestsHelper
                .createHandlerElement("BidUpdateHandler", new String[] {
                    "auction_id_param_key",
                    "max_amount_param_key",
                    "image_id_param_key"}, new String[] {"abc", " ", "cde"}));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests BidPlacementHandler(Element) for failure. Passes empty image_id_param_key,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1_4() throws Exception {
        try {
            new BidPlacementHandler(UnitTestsHelper
                .createHandlerElement("BidUpdateHandler", new String[] {
                    "auction_id_param_key",
                    "max_amount_param_key",
                    "image_id_param_key"}, new String[] {"abc", "bcd", " "}));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that BidPlacementHandler(Element) instance is created.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1_5() throws Exception {
        BidPlacementHandler h = new BidPlacementHandler(UnitTestsHelper.createHandlerElement(
            "BidPlacementHandler", new String[] {
                "auction_id_param_key",
                "max_amount_param_key",
                "image_id_param_key"}, new String[] {"abc", "bcd", "cde"}));
        assertNotNull("Unable to instantiate BidPlacementHandler", h);
    }

    /**
     * <p>
     * Tests BidPlacementHandler(String, String, String) for failure. Passes empty string as first
     * element, IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor2_1() {
        try {
            new BidPlacementHandler(" ", "bcd", "cde");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests BidPlacementHandler(String, String, String) for failure. Passes empty string as second
     * element, IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor2_2() {
        try {
            new BidPlacementHandler("abc", " ", "cde");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests BidPlacementHandler(String, String, String) for failure. Passes empty string as thrid
     * element, IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor2_3() {
        try {
            new BidPlacementHandler("abc", "bcd", " ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that BidPlacementHandler(String, String, String) instance is created.
     * </p>
     */
    public void testConstructor2_4() {
        BidPlacementHandler h = new BidPlacementHandler("abc", "bcd", "cde");
        assertNotNull("Unable to instantiate BidPlacementHandler", h);
    }

    /**
     * <p>
     * Tests that execute(ActionContext) correctly creates new bid in an open auction.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute1() throws Exception {
        // create UserProfile within the session
        UserProfile bond = new UserProfile(new Long(7));
        bond.setProperty("first_name", "James");
        bond.setProperty("last_name", "Bond");
        bond.setProperty("email_address", "007@sis.uk");
        profileManager.createUserProfile(bond);
        loginHandler.execute(actionContext);

        // add auction manager
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);
        addAuctionToPersistence(239, new Bid[] {});

        assertNull("Null is expected", handler.execute(actionContext));
        assertEquals("The method execute(ActionContext) works incorrectly", 239, auctionManager
            .getAuctionForLastNewBid());

        CustomBid newBid = (CustomBid) auctionManager.getLastNewBid();
        assertEquals("The method execute(ActionContext) works incorrectly", 7, newBid.getBidderId());
        assertEquals("The method execute(ActionContext) works incorrectly", 923, newBid
            .getImageId());
        assertEquals("The method execute(ActionContext) works incorrectly", 392, newBid
            .getMaxAmount());
    }

    /**
     * <p>
     * Tests execute(ActionContext) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute2() throws Exception {
        try {
            handler.execute(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests execute(ActionContext) for failure. Auction manager doesn't exist,
     * HandlerExecutionException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute3() throws Exception {
        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests execute(ActionContext) for failure. Auction persistence is null,
     * HandlerExecutionException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute4() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests execute(ActionContext) for failure. Auction doesn't exist within the persistence,
     * HandlerExecutionException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute5() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests execute(ActionContext) for failure. User profile doesn't exist,
     * HandlerExecutionException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute6() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);
        // adds auction with required bid
        Date timestamp = new Date();
        CustomBid originalBid = new CustomBid(932, 8, 35, timestamp);
        originalBid.setId(35);
        originalBid.setEffectiveAmount(9);
        addAuctionToPersistence(239, new Bid[] {originalBid});

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests execute(ActionContext) for failure. Auction manager attribute is Boolean,
     * HandlerExecutionException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute7() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            Boolean.TRUE);

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Creates MockAuction instance and adds it to the persistence.
     * </p>
     * @param id auction's id.
     * @param bids auction's bids.
     */
    private void addAuctionToPersistence(int id, Bid[] bids) {
        MockAuction auction = new MockAuction();
        auction.setId(id);
        auction.setBids(bids);
        auctionPersistence.addAuction(auction);
    }

}
