/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import junit.framework.TestCase;

import com.orpheus.game.KeySubmissionHandler;
import com.topcoder.util.config.ConfigManager;
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
        handler = new KeySubmissionHandler("gameId", "submissions", "inactive", "failureCountExceededResult",
                        "failureCountNotMetResult", 3);
        MockHttpSession session = new MockHttpSession();
        MockHttpRequest request = new MockHttpRequest(session);
        context = new ActionContext(request, new MockHttpResponse());
        request.setAttribute("request_property_name", "request_property_value");
        request.setParameter("gameId", "1");
        request.setParameter("submissions", "2");
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
