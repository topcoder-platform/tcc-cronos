/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.stresstests;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.easymock.MockControl;

import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.LeadingBidsHandler;
import com.topcoder.util.auction.Bid;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Stress test case for class <code>LeadingBidsHandler</code>.
 * </p>
 * @author waits
 * @version 1.0
 */
public class LeadingBidsHandlerStressTestCase extends TestCase {
    /** The number of operations in the stress tests. */
    private static final int OPERATIONS = 500;

    /** LeadingBidsHandler instance for testing. */
    private LeadingBidsHandler handler = null;

    /**
     * Sets up the environment for the TestCase.
     * @throws Exception
     *             into JUnit
     */
    protected void setUp() throws Exception {
        Helper.addConfigFile(Helper.CONFIG_FILE);
    }

    /**
     * test the execute(ActionContext) method.
     * @throws Exception
     *             into JUnit
     */
    public void testExecute() throws Exception {
        handler = new LeadingBidsHandler(Helper.getDomElement("test_files/stress_testcases/LeadingBids.xml"));

        long start = System.currentTimeMillis();

        for (int i = 0; i < OPERATIONS; ++i) {
            MockControl requestControl = (MockControl.createNiceControl(HttpServletRequest.class));
            HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
            HttpServletResponse response =
                    (HttpServletResponse) (MockControl.createNiceControl(HttpServletResponse.class)).getMock();
            MockControl sessionControl = (MockControl.createControl(HttpSession.class));
            HttpSession session = (HttpSession) sessionControl.getMock();

            MockControl contxtControl = (MockControl.createNiceControl(ServletContext.class));
            ServletContext servletContext = (ServletContext) contxtControl.getMock();

            MockAuctionManager auctionManager = new MockAuctionManager();
            MockAuctionStrategy auctionStrategy = new MockAuctionStrategy();
            auctionStrategy.setLeadingBids(new Bid[] {});
            auctionManager.setAuctionStrategy(auctionStrategy);

            MockAuctionPersistence persistence = new MockAuctionPersistence();
            MockAuction auction = new MockAuction();
            auction.setId(2);
            persistence.addAuction(auction);

            auctionManager.setAuctionPersistence(persistence);
            auctionManager.getAuctionStrategy();
            contxtControl.expectAndReturn(
                    servletContext.getAttribute(KeyConstants.AUCTION_MANAGER_KEY), auctionManager);
            contxtControl.replay();
            sessionControl.expectAndReturn(session.getServletContext(), servletContext);
            sessionControl.replay();
            requestControl.expectAndReturn(request.getSession(), session);
            requestControl.expectAndReturn(request.getSession(true), session);
            requestControl.expectAndReturn(request.getSession(false), session);
            requestControl.expectAndReturn(request.getParameter("key1"), "2");
            requestControl.replay();

            ActionContext context = new ActionContext(request, response);
            handler.execute(context);
        }

        long end = System.currentTimeMillis();

        System.out.println("StressTests - LeadingBidsHandler.execute() : " + OPERATIONS + " operations, " + "takes "
                + (end - start) + " ms");
    }

    /**
     * clear the environement.
     */
    protected void tearDown() throws Exception {
        Helper.clearConfigManager();
    }
}
