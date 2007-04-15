/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.stresstests;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import com.cronos.im.ajax.handler.ReadClientUserMessageHandler;

/**
 * <p>
 * Stress test cases for ReadClientUserMessageHandler class.
 * </p>
 *
 * @author 80x86
 * @version 1.0
 */
public class ReadClientUserMessageHandlerStressTests extends TestCase {

    /**
     * Reference to an instance of ReadClientUserMessageHandler for the tests.
     */
    private ReadClientUserMessageHandler handler = null;

    /**
     * Sets up the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        StressTestHelper.setUp();

        handler = new ReadClientUserMessageHandler();
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
        String requestXml = "<request type=\"ReadClientUserMessage\"> <session_id>666</session_id> </request>";
        HttpServletRequest req = StressTestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        long start = System.currentTimeMillis();

        for (int j = 0; j < StressTestHelper.MAX_COUNT; j++) {
            handler.handle(StressTestHelper.getElement(requestXml), req, res);
        }
        System.out.println("Handling " + StressTestHelper.MAX_COUNT
                + " requests using ReadClientUserMessageHandler will cost "
                + (System.currentTimeMillis() - start) + " milliseconds.");
    }

}
