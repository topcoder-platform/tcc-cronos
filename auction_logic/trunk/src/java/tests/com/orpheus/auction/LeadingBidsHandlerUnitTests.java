/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.w3c.dom.Element;

import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.Bid;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * Unit test cases for the LeadingBidsHandler class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LeadingBidsHandlerUnitTests extends ServletTestCase {
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
     * @param theRequest WebRequest instance to setup initial parameters.
     */
    public void begin(WebRequest theRequest) {
        theRequest.addParameter("abc", "239");
    }

    /**
     * <p>
     * Creates new required instances.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        // used by KeyConstants
        UnitTestsHelper.loadConfig(UnitTestsHelper.CONFIG_FILE);

        actionContext = new ActionContext(request, response);
        handler = new LeadingBidsHandler(UnitTestsHelper
            .createHandlerElement("LeadingBidsHandler", new String[] {
                "auction_id_param_key",
                "leading_bids_key"}, new String[] {"abc", "bids"}));

        auctionManager = new MockAuctionManager();
        auctionPersistence = new MockAuctionPersistence();

        // remove attribute from the servlet context if it exists
        request.getSession().getServletContext().removeAttribute(KeyConstants.AUCTION_MANAGER_KEY);
    }

    /**
     * <p>
     * Clears configuration and removes attribute from the ServletContext.
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
     * Tests that LeadingBidsHandler(String, String) instance is created.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1_1() throws Exception {
        LeadingBidsHandler h = new LeadingBidsHandler("abc", "bcd");
        assertNotNull("Unable to instantiate LeadingBidsHandler", h);
    }

    /**
     * <p>
     * Tests LeadingBidsHandler(String, String) for failure. Passes empty string as first argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1_2() {
        try {
            new LeadingBidsHandler(" ", "bcd");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests LeadingBidsHandler(String, String) for failure. Passes empty string as second argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1_3() {
        try {
            new LeadingBidsHandler("abc", " ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that LeadingBidsHandler(Element) instance is created and arguments are correctly
     * propagated.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor2_1() throws Exception {
        LeadingBidsHandler h = new LeadingBidsHandler(UnitTestsHelper.createHandlerElement(
            "LeadingBidsHandler", new String[] {"auction_id_param_key", "leading_bids_key"},
            new String[] {"abc", "bcd"}));
        assertNotNull("Unable to instantiate LeadingBidsHandler", h);
    }

    /**
     * <p>
     * Tests LeadingBidsHandler(Element) for failure. Passes null, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testConstructor2_2() {
        Element element = null;
        try {
            new LeadingBidsHandler(element);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests LeadingBidsHandler(Element) for failure. Passes empty leading_bids_key value,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor2_3() throws Exception {
        try {
            new LeadingBidsHandler(UnitTestsHelper.createHandlerElement("LeadingBidsHandler",
                new String[] {"auction_id_param_key", "leading_bids_key"},
                new String[] {"abc", " "}));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests LeadingBidsHandler(Element) for failure. Passes empty auction_id_param_key value,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor2_4() throws Exception {
        try {
            new LeadingBidsHandler(UnitTestsHelper.createHandlerElement("LeadingBidsHandler",
                new String[] {"auction_id_param_key", "leading_bids_key"},
                new String[] {" ", "bcd"}));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests execute(ActionContext) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute1() throws Exception {
        try {
            handler.execute(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that execute(ActionContext) correctly retrieves bids and assigns them to the attribute.
     * Empty array is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute2() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);
        // adds auction without bids
        addAuctionToPersistence(239, new Bid[] {});

        MockAuctionStrategy auctionStrategy = new MockAuctionStrategy();
        auctionStrategy.setLeadingBids(new Bid[] {});
        auctionManager.setAuctionStrategy(auctionStrategy);

        assertNull("Null is expected", handler.execute(actionContext));

        Bid[] res = (Bid[]) actionContext.getRequest().getAttribute("bids");
        assertEquals("The returned result is incorrect", 0, res.length);
    }

    /**
     * <p>
     * Tests that execute(ActionContext) correctly retrieves bids and assigns them to the attribute.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute3() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        Bid[] bids = new Bid[5];
        for (int i = 0; i < bids.length; i++) {
            bids[i] = new CustomBid(i, i, 35, new Date());
        }
        addAuctionToPersistence(239, bids);

        MockAuctionStrategy auctionStrategy = new MockAuctionStrategy();
        auctionStrategy.setLeadingBids(bids);
        auctionManager.setAuctionStrategy(auctionStrategy);

        assertNull("Null is expected", handler.execute(actionContext));

        Bid[] res = (Bid[]) actionContext.getRequest().getAttribute("bids");
        assertEquals("The returned result is incorrect", 5, res.length);
    }

    /**
     * <p>
     * Tests execute(ActionContext) for failure. Auction manager doesn't exist,
     * HandlerExecutionException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute4() throws Exception {
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
    public void testExecute5() throws Exception {
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
     * Tests execute(ActionContext) for failure. Auction is not existed within the persistence,
     * HandlerExecutionException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute6() throws Exception {
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
     * Tests execute(ActionContext) for failure. Auction id is not set, HandlerExecutionException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute7() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        try {
            new LeadingBidsHandler("auction", "bids").execute(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Initializes parameter that will be used in testExecute9 method.
     * </p>
     * @param theRequest WebRequest instance to setup initial parameters.
     */
    public void beginExecute8(WebRequest theRequest) {
        theRequest.addParameter("auction", "2.39");
    }

    /**
     * <p>
     * Tests execute(ActionContext) for failure. Auction id is not an integer,
     * HandlerExecutionException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute8() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        try {
            new LeadingBidsHandler("auction", "bids").execute(actionContext);
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
    public void testExecute9() throws Exception {
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
