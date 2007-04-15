/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.stresstests;

import junit.framework.TestCase;

import com.cronos.im.ajax.IMAjaxSupportUtility;
import com.cronos.im.ajax.RequestHandlerManager;
import com.cronos.im.ajax.TestHelper;

/**
 * <p>
 * Stress test cases for RequestHandlerManager class.
 * </p>
 *
 * @author 80x86
 * @version 1.0
 */
public class RequestHandlerManagerStressTests extends TestCase {

    /**
     * Reference to an instance of RequestHandlerManager for the following tests.
     */
    private RequestHandlerManager manager = null;

    /**
     * Sets up the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.setUp();
        manager = new RequestHandlerManager();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDown();
    }

    /**
     * Tests the handle method for StressTestHelper.MAX_COUNT times.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testHandle() throws Exception {
        String requestXml = "<request type=\"PostTextMessage\"> <session_id>666</session_id> <chat_text>text</chat_text> </request>";
        MockHttpServletRequest req = (MockHttpServletRequest) StressTestHelper.createHttpServletRequest();
        req.setParameter(IMAjaxSupportUtility.getXMLRequestParamKey(), requestXml);
        MockHttpServletResponse res = new MockHttpServletResponse();

        long start = System.currentTimeMillis();

        for (int j = 0; j < StressTestHelper.MAX_COUNT; j++) {
            manager.handle(req, res);
        }
        System.out.println("Handling " + StressTestHelper.MAX_COUNT
                + " requests using RequestHandlerManager will cost "
                + (System.currentTimeMillis() - start) + " milliseconds.");
    }

}
