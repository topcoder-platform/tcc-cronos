/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.KeySubmissionHandler;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Stress test case for KeySubmissionHandler,the execute method has been tested.
 * </p>
 *
 * @author catcher
 * @version 1.0
 */
public class KeySubmissionHandlerStressTest extends TestCase {
    /**
     * keep the instance of the <c>ActionContext</c> for test.
     */
    private ActionContext context;

    /**
     * keep the instance of the <c>KeySubmissionHandler</c> for test.
     */
    private KeySubmissionHandler handler;

    /**
     * create MessageHandler instance.
     *
     * @throws Exception
     *         into Junit
     */
    protected void setUp() throws Exception {
        TestsHelper.removeAllCMNamespaces();
        TestsHelper.loadConfig();
        handler = new KeySubmissionHandler("gameId", "submissions", "inactive", "success",
                        "failureCountNotMetResult", 3);
		ServletContext servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getDataStoreKey(), new MockDataStore());
        HttpSession session = new MockHttpSession(servletContext);
        MockHttpRequest mockRequest = new MockHttpRequest(session);
        HttpServletRequest request = mockRequest;
        context = new ActionContext(request, new MockHttpResponse());
        request.setAttribute("request_property_name", "request_property_value");
        mockRequest.setParameter("gameId", "1");
        mockRequest.setParameter("submissions", "2");
		session.setAttribute("user_profile", new UserProfile(new Long(1)));
    }

    /**
     * Remove the namespace and remove the authorization informaiton. *
     *
     * @throws Exception
     *         into Junit
     */
    protected void tearDown() throws Exception {
        TestsHelper.removeAllCMNamespaces();
    }

    /**
     * test execute with context,with valid value. *
     *
     * @throws Exception
     *         into Junit
     */
    public void testExecute() throws Exception {
        long start = System.currentTimeMillis();
        for (int j = 0; j < TestsHelper.MAX_COUNT; j++) {
            handler.execute(context);
        }
        System.out.println("handling " + TestsHelper.MAX_COUNT + " requests using KeySubmissionHandler will cost "
                        + (System.currentTimeMillis() - start) + " milliseconds.");
    }

}
