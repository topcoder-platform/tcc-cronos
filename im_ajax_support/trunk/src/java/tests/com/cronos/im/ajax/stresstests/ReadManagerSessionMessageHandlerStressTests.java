/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.stresstests;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import com.cronos.im.ajax.handler.ReadManagerSessionMessageHandler;

/**
 * <p>
 * Stress test cases for ReadManagerSessionMessageHandler class.
 * </p>
 *
 * @author 80x86
 * @version 1.0
 */
public class ReadManagerSessionMessageHandlerStressTests extends TestCase {

    /**
     * Reference to an instance of ReadManagerSessionMessageHandler for the following tests.
     */
    private ReadManagerSessionMessageHandler handler = null;

    /**
     * Sets up the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        StressTestHelper.setUp();

        handler = new ReadManagerSessionMessageHandler();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        StressTestHelper.tearDown();
    }

    /**
     * Tests the handle method for StressTestHelper.MAX_COUNT times.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testHandle() throws Exception {
        String requestXml = "<request type=\"ReadManagerSessionMessage\"> <session_id>666</session_id> </request>";
        HttpServletRequest req = StressTestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        long start = System.currentTimeMillis();
        for (int j = 0; j < StressTestHelper.MAX_COUNT; j++) {
            handler.handle(StressTestHelper.getElement(requestXml), req, res);
        }
        System.out.println("Handling " + StressTestHelper.MAX_COUNT
                + " requests using ReadManagerSessionMessageHandler will cost "
                + (System.currentTimeMillis() - start) + " milliseconds.");
    }

}
