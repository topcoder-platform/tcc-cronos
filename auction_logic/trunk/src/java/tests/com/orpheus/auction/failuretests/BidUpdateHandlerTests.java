/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.failuretests;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.w3c.dom.Element;

import com.orpheus.auction.BidUpdateHandler;
import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.util.auction.Bid;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;

/**
 * <p>
 * Unit test cases for the BidUpdateHandler class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BidUpdateHandlerTests extends ServletTestCase {
    /**
     * <p>
     * ActionContext instance. Used for testing.
     * </p>
     */
    private ActionContext actionContext;

    /**
     * <p>
     * BidUpdateHandler instance. Used for testing.
     * </p>
     */
    private BidUpdateHandler handler;

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
     * @param theRequest
     *            WebRequest instance to setup initial parameters.
     */
    public void begin(WebRequest theRequest) {
        theRequest.addParameter("auction", "239");
        theRequest.addParameter("amount", "392");
        theRequest.addParameter("bid", "923");

        // add the user parameters to login
        theRequest.addParameter("firstName", "James");
        theRequest.addParameter("lastName", "Bond");
        theRequest.addParameter("e-mail", "007@sis.uk");
    }

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

        actionContext = new ActionContext(request, response);
        handler =
                new BidUpdateHandler(TestsHelper.createHandlerElement(
                        "BidUpdateHandler", new String[] {"auction_id_param_key",
                            "max_amount_param_key",
                            "bid_id_param_key"}, new String[] {"auction", "amount", "bid"}));

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
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestsHelper.clearConfig();

        HttpSession session = request.getSession(false);
        if (session != null) {
            // remove attribute from the servlet context if it exists
            session.getServletContext().removeAttribute(KeyConstants.AUCTION_MANAGER_KEY);
            // invalidate session if it exists
            session.invalidate();
        }
    }

    /**
     * Failure test for the method
     * <code>BidUpdateHandler(String auctionIdParamKey, String bidIdParamKey, String maxAmountParamKey)</code>.
     * <p>
     * auctionIdParamKey is empty. IllegalArgumentException is expected.
     */
    public void testConstructorA1() {
        try {
            new BidUpdateHandler("   ", "bidId", "maxAmount");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method
     * <code>BidUpdateHandler(String auctionIdParamKey, String bidIdParamKey, String maxAmountParamKey)</code>.
     * <p>
     * bidIdParamKey is empty, IllegalArgumentException is expected.
     */
    public void testConstructorA2() {
        try {
            new BidUpdateHandler("auctionId", "    ", "maxAmount");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method
     * <code>BidUpdateHandler(String auctionIdParamKey, String bidIdParamKey, String maxAmountParamKey)</code>.
     * <p>
     * maxAmountParamKey is empty. IllegalArgumentException is expected.
     */
    public void testConstructorA3() {
        try {
            new BidUpdateHandler("auctionId", "bidId", "    ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method
     * <code>BidUpdateHandler(String auctionIdParamKey, String bidIdParamKey, String maxAmountParamKey)</code>.
     * <p>
     * auctionIdParamKey is null. IllegalArgumentException is expected.
     */
    public void testConstructorA4() {
        try {
            new BidUpdateHandler(null, "bidId", "maxAmount");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method
     * <code>BidUpdateHandler(String auctionIdParamKey, String bidIdParamKey, String maxAmountParamKey)</code>.
     * <p>
     * bidIdParamKey is null. IllegalArgumentException is expected.
     */
    public void testConstructorA5() {
        try {
            new BidUpdateHandler("auctionId", null, "maxAmount");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method
     * <code>BidUpdateHandler(String auctionIdParamKey, String bidIdParamKey, String maxAmountParamKey)</code>.
     * <p>
     * maxAmountParamKey is null. IllegalArgumentException is expected.
     */
    public void testConstructorA6() {
        try {
            new BidUpdateHandler("auctionId", "bidId", null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>BidUpdateHandler(Element element)</code>.
     * <p>
     * element is null. IllegalArgumentException is expected.
     */
    public void testConstructorB1() {
        try {
            new BidUpdateHandler((Element) null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>BidUpdateHandler(Element element)</code>.
     * <p>
     * The value of auction_id_param_key is empty, IllegalArgumentException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructorB2() throws Exception {
        Element element =
                TestsHelper.createHandlerElement("BidUpdateHandler", new String[] {"auction_id_param_key",
                    "max_amount_param_key",
                    "bid_id_param_key"}, new String[] {"    ", "maxAmount", "bidId"});
        try {
            new BidUpdateHandler(element);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * Failure test for the method <code>BidUpdateHandler(Element element)</code>.
     * <p>
     * The value of max_amount_param_key is empty. IllegalArgumentException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructorB3() throws Exception {
        Element element =
                TestsHelper.createHandlerElement("BidUpdateHandler", new String[] {"auction_id_param_key",
                    "max_amount_param_key",
                    "bid_id_param_key"}, new String[] {"auctionId", "    ", "bidId"});
        try {
            new BidUpdateHandler(element);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>BidUpdateHandler(Element element)</code>.
     * <p>
     * The value of bid_id_param_key is empty. IllegalArgumentException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructorB4() throws Exception {
        Element element =
                TestsHelper.createHandlerElement("BidUpdateHandler", new String[] {"auction_id_param_key",
                    "max_amount_param_key",
                    "bid_id_param_key"}, new String[] {"auctionId", "maxAmount", "    "});
        try {
            new BidUpdateHandler(element);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * Failed to call the method acceptBidUpdate. HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
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
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        // adds auction with required bid
        Date timestamp = new Date();
        CustomBid originalBid = new CustomBid(111, 123, 150, timestamp);
        originalBid.setId(923);
        originalBid.setEffectiveAmount(199);
        addAuctionToPersistence(239, new Bid[] {originalBid});

        auctionManager.setFailed(true);

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * context is null, IllegalArgumentException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute2() throws Exception {
        try {
            handler.execute(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * AuctionManager doesn't exist, HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute3() throws Exception {
        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * AuctionPersistence is null, HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute4() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, auctionManager);

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * Auction doesn't exist within the persistence, HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute5() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * User profile doesn't exist, HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute6() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);
        // adds auction with required bid
        addAuctionToPersistence(239, new Bid[] {});

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * Auction manager attribute is Boolean, HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute7() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, Boolean.TRUE);

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * Bid doesn't exist, HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute8() throws Exception {
        // create UserProfile within the session
        UserProfile bond = new UserProfile(new Long(7));
        bond.setProperty("first_name", "James");
        bond.setProperty("last_name", "Bond");
        bond.setProperty("email_address", "007@sis.uk");
        profileManager.createUserProfile(bond);
        loginHandler.execute(actionContext);

        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);
        // adds auction with required bid
        Date timestamp = new Date();
        CustomBid originalBid = new CustomBid(923, 8, 35, timestamp);
        originalBid.setId(35);
        originalBid.setEffectiveAmount(9);
        addAuctionToPersistence(239, new Bid[] {originalBid});

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Creates MockAuction instance and adds it to the persistence.
     * </p>
     * @param id
     *            auction's id.
     * @param bids
     *            auction's bids.
     */
    private void addAuctionToPersistence(int id, Bid[] bids) {
        MockAuction auction = new MockAuction();
        auction.setId(id);
        auction.setBids(bids);
        auctionPersistence.addAuction(auction);
    }
}
