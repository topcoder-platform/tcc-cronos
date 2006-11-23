/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.failuretests;

import javax.servlet.http.HttpSession;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.w3c.dom.Element;

import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.LeadingBidsHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * Unit test cases for the LeadingBidsHandler class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LeadingBidsHandlerTests extends ServletTestCase {
    /**
     * <p>
     * ActionContext instance. Used for testing.
     * </p>
     */
    private ActionContext actionContext;

    /**
     * <p>
     * LeadingBidsHandler instance. Used for testing.
     * </p>
     */
    private LeadingBidsHandler handler;

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
     * Initializes HTTP related parameters that can be used in all test methods.
     * </p>
     * @param theRequest
     *            WebRequest instance to setup initial parameters.
     */
    public void begin(WebRequest theRequest) {
        theRequest.addParameter("id", "123");
    }

    /**
     * <p>
     * Creates new required instances.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        // used by KeyConstants
        TestsHelper.loadConfig(TestsHelper.CONFIG_FILE);

        actionContext = new ActionContext(request, response);
        handler =
                new LeadingBidsHandler(TestsHelper.createHandlerElement("LeadingBidsHandler", new String[] {
                    "auction_id_param_key", "leading_bids_key"}, new String[] {"id", "bids"}));

        auctionManager = new MockAuctionManager();
        auctionPersistence = new MockAuctionPersistence();

        // remove attribute from the servlet context if it exists
        request.getSession().getServletContext().removeAttribute(KeyConstants.AUCTION_MANAGER_KEY);
    }

    /**
     * <p>
     * Clears configuration and removes attribute from the ServletContext.
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
     * <code>LeadingBidsHandler(String auctionIdParamKey, String leadingBidsKey)</code>.
     * <p>
     * auctionIdParamKey is empty. IllegalArgumentException is expected.
     */
    public void testConstructorA1() {
        try {
            new LeadingBidsHandler("   ", "bids");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method
     * <code>LeadingBidsHandler(String auctionIdParamKey, String leadingBidsKey)</code>.
     * <p>
     * leadingBidsKey is empty. IllegalArgumentException is expected.
     */
    public void testConstructorA2() {
        try {
            new LeadingBidsHandler("id", "   ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method
     * <code>LeadingBidsHandler(String auctionIdParamKey, String leadingBidsKey)</code>.
     * <p>
     * auctionIdParamKey is null. IllegalArgumentException is expected.
     */
    public void testConstructorA3() {
        try {
            new LeadingBidsHandler(null, "bids");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method
     * <code>LeadingBidsHandler(String auctionIdParamKey, String leadingBidsKey)</code>.
     * <p>
     * leadingBidsKey is null. IllegalArgumentException is expected.
     */
    public void testConstructorA4() {
        try {
            new LeadingBidsHandler("id", null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>LeadingBidsHandler(Element element)</code>.
     * <p>
     * element is null. IllegalArgumentException is expected.
     */
    public void testConstructorB1() {
        try {
            new LeadingBidsHandler((Element) null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>LeadingBidsHandler(Element element)</code>.
     * <p>
     * The value of leading_bids_key is empty. IllegalArgumentException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructorB2() throws Exception {
        Element element =
                TestsHelper.createHandlerElement("LeadingBidsHandler", new String[] {"auction_id_param_key",
                    "leading_bids_key"}, new String[] {"id", "    "});
        try {
            new LeadingBidsHandler(element);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>LeadingBidsHandler(Element element)</code>.
     * <p>
     * The value of auction_id_param_key is empty. IllegalArgumentException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructorB3() throws Exception {
        try {
            new LeadingBidsHandler(TestsHelper.createHandlerElement("LeadingBidsHandler", new String[] {
                "auction_id_param_key", "leading_bids_key"}, new String[] {"   ", "bids"}));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * context is null. IllegalArgumentException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute1() throws Exception {
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
     * AuctionManager doesn't exist. HandlerExecutionException is expected.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute2() throws Exception {
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
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute5() throws Exception {
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
     * No auction is found. HandlerExecutionException is expected.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute6() throws Exception {
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
     * Auction id is not set. HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute7() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        try {
            new LeadingBidsHandler("auction", "bids").execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Initializes parameter that will be used in testExecute8 method.
     * </p>
     * @param theRequest
     *            WebRequest instance to setup initial parameters.
     */
    public void beginExecute8(WebRequest theRequest) {
        theRequest.addParameter("auction", "2.39");
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * Auction id is not an integer. HandlerExecutionException is expected.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute8() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        try {
            new LeadingBidsHandler("auction", "bids").execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * AuctionManager attribute is Boolean. HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute9() throws Exception {
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
     * Failed to call the method getAuction. HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute10() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);
        auctionPersistence.setFailed(true);

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }

}
