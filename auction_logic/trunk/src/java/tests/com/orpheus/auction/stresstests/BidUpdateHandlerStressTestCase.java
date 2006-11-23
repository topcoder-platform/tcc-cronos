/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.stresstests;

import java.util.Date;

import com.orpheus.auction.BidUpdateHandler;
import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.persistence.CustomBid;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

import org.easymock.MockControl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * <p>Stress test case for class <code>BidUpdateHandler</code>.</p>
 * @author waits
 * @version 1.0
 */
public class BidUpdateHandlerStressTestCase extends TestCase {
    /** The number of operations in the stress tests. */
    private static final int OPERATIONS = 500;

    /** BidUpdateHandler instance for testing. */
    private BidUpdateHandler handler = null;

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
        handler = new BidUpdateHandler(Helper.getDomElement("test_files/stress_testcases/BidUpdater.xml"));

        MockAuctionManager auctionManager = new MockAuctionManager();
        MockAuctionPersistence persistence = new MockAuctionPersistence();
        //prepare bids
        CustomBid[] bids = new CustomBid[31];
        for (int j = 0; j < 31; j++) {
            bids[j] = new CustomBid(j,j + 1, j + 2, new Date());
            bids[j].setId(j);
           // bids[j].setEffectiveAmount(100);
        }
        
        MockAuction auction = new MockAuction();
        auction.setId(2);
        auction.setBids(bids);
        
        auction.setStartDate(new java.util.Date());
        auction.setEndDate(new java.util.Date());
        persistence.addAuction(auction);

        auctionManager.setAuctionPersistence(persistence);

        UserProfile bond = new UserProfile(new Long(123));
        
        long start = System.currentTimeMillis();

        for (int i = 0; i < OPERATIONS; ++i) {
            MockControl requestControl = (MockControl.createNiceControl(HttpServletRequest.class));
            HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
            HttpServletResponse response = (HttpServletResponse) (MockControl.createNiceControl(HttpServletResponse.class)).getMock();
            MockControl sessionControl = (MockControl.createControl(HttpSession.class));
            HttpSession session = (HttpSession) sessionControl.getMock();

            MockControl contxtControl = (MockControl.createNiceControl(ServletContext.class));
            ServletContext servletContext = (ServletContext) contxtControl.getMock();
            contxtControl.expectAndReturn(servletContext.getAttribute(KeyConstants.AUCTION_MANAGER_KEY),
                auctionManager);
            contxtControl.replay();
            sessionControl.expectAndReturn(session.getServletContext(), servletContext);
            sessionControl.expectAndReturn(session.getAttribute("user_profile"), bond);
            sessionControl.replay();
            requestControl.expectAndReturn(request.getSession(), session);
            requestControl.expectAndReturn(request.getSession(true), session);
            requestControl.expectAndReturn(request.getSession(false), session);
            requestControl.expectAndReturn(request.getParameter("auction_id_param_key"), "2");
            requestControl.expectAndReturn(request.getParameter("bid_id_param_key"), "10");
            requestControl.expectAndReturn(request.getParameter("max_amount_param_key"), "3");

            requestControl.replay();

            ActionContext context = new ActionContext(request, response);
            handler.execute(context);
        }

        long end = System.currentTimeMillis();

        System.out.println("StressTests - BidUpdateHandler.execute() : " + OPERATIONS + " operations, " + "takes " +
            (end - start) + " ms");
    }

    /**
     * clear the environement.
     */
    protected void tearDown() throws Exception {
        Helper.clearConfigManager();
    }
}
