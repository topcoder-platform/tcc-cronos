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

import com.orpheus.auction.BidPlacementHandler;
import com.orpheus.auction.KeyConstants;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>Stress test case for class <code>BidPlacementHandler</code>.</p>
 * @author waits
 * @version 1.0
 */
public class BidPlacementHandlerStressTestCase extends TestCase {

	 /** The number of operations in the stress tests. */
    private static final int OPERATIONS = 500;

    /** BidPlacementHandler instance for testing. */
    private BidPlacementHandler handler = null;

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
        handler = new BidPlacementHandler(Helper.getDomElement("test_files/stress_testcases/BidPlacement.xml"));

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
                new MockAuctionManager());
            contxtControl.replay();
            sessionControl.expectAndReturn(session.getServletContext(), servletContext);
            sessionControl.expectAndReturn(session.getAttribute("user_profile"), bond);
            sessionControl.replay();
            requestControl.expectAndReturn(request.getSession(), session);
            requestControl.expectAndReturn(request.getSession(true), session);
            requestControl.expectAndReturn(request.getSession(false), session);
            requestControl.expectAndReturn(request.getParameter("auction_id_param_key"), "1");
            requestControl.expectAndReturn(request.getParameter("max_amount_param_key"), "2");
            requestControl.expectAndReturn(request.getParameter("image_id_param_key"), "3");
            
            requestControl.replay();

            ActionContext context = new ActionContext(request, response);
            handler.execute(context);
        }

        long end = System.currentTimeMillis();

        System.out.println("StressTests - BidPlacementHandler.execute() : " + OPERATIONS + " operations, " +
            "takes " + (end - start) + " ms");
    }

    /**
     * clear the environement.
     */
    protected void tearDown() throws Exception {
        Helper.clearConfigManager();
    }

}
