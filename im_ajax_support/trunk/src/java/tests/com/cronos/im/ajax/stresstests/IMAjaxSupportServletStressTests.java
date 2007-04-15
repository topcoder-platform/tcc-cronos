/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.stresstests;

import junit.framework.TestCase;

import com.cronos.im.ajax.IMAjaxSupportServletTester;

/**
 * <p>
 * Stress test cases for IMAjaxSupportServlet class.
 * </p>
 *
 * @author 80x86
 * @version 1.0
 */
public class IMAjaxSupportServletStressTests extends TestCase {

    /**
     * Sets up the environment. the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        StressTestHelper.setUp();
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
     * Tests the init method for StressTestHelper.MAX_COUNT times.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInit() throws Exception {
        long start = System.currentTimeMillis();

        for (int j = 0; j < StressTestHelper.MAX_COUNT; j++) {
            new IMAjaxSupportServletTester().init();
        }
        System.out.println("Initing " + StressTestHelper.MAX_COUNT
                + " times using IMAjaxSupportServlet will cost "
                + (System.currentTimeMillis() - start) + " milliseconds.");

    }

}
