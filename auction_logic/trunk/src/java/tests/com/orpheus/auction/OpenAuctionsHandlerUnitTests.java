/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.cactus.ServletTestCase;
import org.w3c.dom.Element;

import com.topcoder.util.auction.Auction;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * Unit test cases for the OpenAuctionsHandler class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class OpenAuctionsHandlerUnitTests extends ServletTestCase {

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
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        // used by KeyConstants
        UnitTestsHelper.loadConfig(UnitTestsHelper.CONFIG_FILE);

        actionContext = new ActionContext(request, response);
        handler = new OpenAuctionsHandler(UnitTestsHelper.createHandlerElement("OpenAuctions",
            new String[] {"open_auction_key"}, new String[] {"key"}));

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
     * Tests that OpenAuctionsHandler(String) instance is created.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1_1() throws Exception {
        OpenAuctionsHandler h = new OpenAuctionsHandler("ABC");
        assertNotNull("Unable to instantiate OpenAuctionsHandler", h);
    }

    /**
     * <p>
     * Tests OpenAuctionsHandler(String) for failure. Passes non-empty string,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1_2() {
        try {
            new OpenAuctionsHandler(" ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests OpenAuctionsHandler(String) for failure. Passes null, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testConstructor1_3() {
        String s = null;
        try {
            new OpenAuctionsHandler(s);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that OpenAuctionsHandler(Element) instance is created.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor2_1() throws Exception {
        OpenAuctionsHandler h = new OpenAuctionsHandler(UnitTestsHelper.createHandlerElement(
            "OpenAuctions", new String[] {"open_auction_key"}, new String[] {"key"}));
        assertNotNull("Unable to instantiate OpenAuctionsHandler", h);
    }

    /**
     * <p>
     * Tests OpenAuctionsHandler(Element) for failure. Passes null, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testConstructor2_2() {
        Element element = null;
        try {
            new OpenAuctionsHandler(element);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests OpenAuctionsHandler(Element) for failure. Passes empty open_auction_key value,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor2_3() throws Exception {
        try {
            new OpenAuctionsHandler(UnitTestsHelper.createHandlerElement("OpenAuctions",
                new String[] {"open_auction_key"}, new String[] {" "}));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that execute(ActionContext) correctly retrieves auctions and assigns them to the
     * attribute.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute1() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        // get current date
        Calendar curDate = Calendar.getInstance();
        curDate.setTimeInMillis(System.currentTimeMillis());

        Calendar twoDaysBefore = (Calendar) curDate.clone();
        twoDaysBefore.add(Calendar.DATE, -2);
        Calendar twoDaysAfter = (Calendar) curDate.clone();
        twoDaysAfter.add(Calendar.DATE, 2);

        Calendar weekBefore = (Calendar) curDate.clone();
        weekBefore.add(Calendar.DATE, -7);
        Calendar weekAfter = (Calendar) curDate.clone();
        weekAfter.add(Calendar.DATE, 7);

        // add three auctions from past
        for (int i = 0; i < 3; i++) {
            addAuctionToPersistence(i, weekBefore.getTime(), twoDaysBefore.getTime());
        }
        // add three auctions that have been already started but not yet finished.
        for (int i = 3; i < 6; i++) {
            addAuctionToPersistence(i, twoDaysBefore.getTime(), twoDaysAfter.getTime());
        }
        // add three auctions from the future
        for (int i = 6; i < 9; i++) {
            addAuctionToPersistence(i, twoDaysAfter.getTime(), weekAfter.getTime());
        }

        assertNull("Null is expected", handler.execute(actionContext));

        Auction[] res = (Auction[]) actionContext.getRequest().getAttribute("key");
        long[] ids = new long[res.length];
        for (int i = 0; i < res.length; i++) {
            ids[i] = res[i].getId().longValue();
        }
        Arrays.sort(ids);
        assertTrue("The returned result is incorrect", Arrays.equals(new long[] {3, 4, 5}, ids));
    }

    /**
     * <p>
     * Tests that execute(ActionContext) correctly returns empty array.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute2() throws Exception {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        // get current date
        Calendar curDate = Calendar.getInstance();
        curDate.setTimeInMillis(System.currentTimeMillis());

        Calendar twoDaysBefore = (Calendar) curDate.clone();
        twoDaysBefore.add(Calendar.DATE, -2);
        Calendar twoDaysAfter = (Calendar) curDate.clone();
        twoDaysAfter.add(Calendar.DATE, 2);

        Calendar weekBefore = (Calendar) curDate.clone();
        weekBefore.add(Calendar.DATE, -7);
        Calendar weekAfter = (Calendar) curDate.clone();
        weekAfter.add(Calendar.DATE, 7);

        // add three auctions from past
        for (int i = 0; i < 3; i++) {
            addAuctionToPersistence(i, weekBefore.getTime(), twoDaysBefore.getTime());
        }
        // add three auctions from the future
        for (int i = 6; i < 9; i++) {
            addAuctionToPersistence(i, twoDaysAfter.getTime(), weekAfter.getTime());
        }

        assertNull("Null is expected", handler.execute(actionContext));

        Auction[] res = (Auction[]) actionContext.getRequest().getAttribute("key");
        assertEquals("The returned result is incorrect", 0, res.length);
    }

    /**
     * <p>
     * Tests execute(ActionContext) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute3() throws Exception {
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
     * Tests execute(ActionContext) for failure. Auction manager attribute is Boolean,
     * HandlerExecutionException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testExecute6() throws Exception {
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
     * @param startDate auction's start date.
     * @param endDate auction's end date.
     */
    private void addAuctionToPersistence(int id, Date startDate, Date endDate) {
        MockAuction auction = new MockAuction();
        auction.setId(id);
        auction.setStartDate(startDate);
        auction.setEndDate(endDate);
        auctionPersistence.addAuction(auction);
    }
}
