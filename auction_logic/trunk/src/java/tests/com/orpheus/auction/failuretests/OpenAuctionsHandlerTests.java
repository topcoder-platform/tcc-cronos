/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.failuretests;

import javax.servlet.http.HttpSession;

import org.apache.cactus.ServletTestCase;
import org.w3c.dom.Element;

import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.OpenAuctionsHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * Unit test cases for the OpenAuctionsHandler class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class OpenAuctionsHandlerTests extends ServletTestCase {

    /**
     * <p>
     * ActionContext instance. Used for testing.
     * </p>
     */
    private ActionContext actionContext;

    /**
     * <p>
     * OpenAuctionsHandler instance. Used for testing.
     * </p>
     */
    private OpenAuctionsHandler handler;

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
                new OpenAuctionsHandler(TestsHelper.createHandlerElement(
                        "OpenAuctions", new String[] {"open_auction_key"}, new String[] {"key"}));

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
     * Failure test for the method <code>OpenAuctionsHandler(String openAuctionKey)</code>.
     * <p>
     * openAuctionKey is empty. IllegalArgumentException is expected.
     */
    public void testConstructorA1() {
        try {
            new OpenAuctionsHandler("   ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>OpenAuctionsHandler(String openAuctionKey)</code>.
     * <p>
     * openAuctionKey is null. IllegalArgumentException is expected.
     */
    public void testConstructorA2() {
        try {
            new OpenAuctionsHandler((String) null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>OpenAuctionsHandler(Element element)</code>.
     * <p>
     * element is null. IllegalArgumentException is expected.
     */
    public void testConstructorB1() {
        try {
            new OpenAuctionsHandler((Element) null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>OpenAuctionsHandler(Element element)</code>.
     * <p>
     * The value of open_auction_key is empty, IllegalArgumentException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructorB2() throws Exception {
        Element element =
                TestsHelper.createHandlerElement(
                        "OpenAuctions", new String[] {"open_auction_key"}, new String[] {"   "});

        try {
            new OpenAuctionsHandler(element);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * Failed to call method findAuctionsByDate. HandlerExecutionException is expected.
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute1() throws Exception {
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

    /**
     * Failure test for the method <code>String execute(ActionContext context)</code>.
     * <p>
     * context is null. IllegalArgumentException is expected.
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
     * </p>
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
     * AuctionManager attribute is Boolean, HandlerExecutionException is expected.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute5() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, Boolean.TRUE);

        try {
            handler.execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // ok.
        }
    }
}
