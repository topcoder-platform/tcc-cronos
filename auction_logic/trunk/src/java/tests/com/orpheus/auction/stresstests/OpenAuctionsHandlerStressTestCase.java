/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.stresstests;

import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.OpenAuctionsHandler;

import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

import org.easymock.MockControl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * <p>Stress test case for class <code>AuctionListenerImpl</code>.</p>
 * @author waits
 * @version 1.0
 */
public class OpenAuctionsHandlerStressTestCase extends TestCase {
    /** The number of operations in the stress tests. */
    private static final int OPERATIONS = 500;

    /** OpenAuctionsHandler instance for testing. */
    private OpenAuctionsHandler handler = null;

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception into JUnit
     */
    protected void setUp() throws Exception {
        Helper.addConfigFile(Helper.CONFIG_FILE);
    }

    /**
     * test the execute(ActionContext) method.
     *
     * @throws Exception into JUnit
     */
    public void testExecute() throws Exception {
        handler = new OpenAuctionsHandler(Helper.getDomElement("test_files/stress_testcases/OpenAuctions.xml"));

        long start = System.currentTimeMillis();

        for (int i = 0; i < OPERATIONS; ++i) {
            MockControl requestControl = (MockControl.createNiceControl(HttpServletRequest.class));
            HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
            HttpServletResponse response = (HttpServletResponse) (MockControl.createNiceControl(HttpServletResponse.class)).getMock();
            MockControl sessionControl = (MockControl.createControl(HttpSession.class));
            HttpSession session = (HttpSession) sessionControl.getMock();

            MockControl contxtControl = (MockControl.createNiceControl(ServletContext.class));
            ServletContext servletContext = (ServletContext) contxtControl.getMock();
            
            MockAuctionManager auctionManager = new MockAuctionManager();
            MockAuctionPersistence persistence = new MockAuctionPersistence();
            MockAuction auction = new MockAuction();
            auction.setId(2);
            auction.setStartDate(new java.util.Date());
            auction.setEndDate(new java.util.Date());
            persistence.addAuction(auction);
            
            auctionManager.setAuctionPersistence(persistence);
            
            contxtControl.expectAndReturn(servletContext.getAttribute(KeyConstants.AUCTION_MANAGER_KEY),
            		auctionManager);
            contxtControl.replay();
            sessionControl.expectAndReturn(session.getServletContext(), servletContext);
            sessionControl.replay();
            requestControl.expectAndReturn(request.getSession(), session);
            requestControl.expectAndReturn(request.getSession(false), session);
            requestControl.expectAndReturn(request.getSession(true), session);
            requestControl.replay();

            ActionContext context = new ActionContext(request, response);
            handler.execute(context);
        }

        long end = System.currentTimeMillis();

        System.out.println("StressTests - OpenAuctionsHandler.execute() : " + OPERATIONS + " operations, " +
            "takes " + (end - start) + " ms");
    }

    /**
     * clear the environement.
     */
    protected void tearDown() throws Exception {
        Helper.clearConfigManager();
    }
}
