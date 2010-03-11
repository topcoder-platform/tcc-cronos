/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.stresstests.interceptors;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.service.interceptors.LoggingInterceptor;

public class LoggingInterceptorStressTest extends TestCase {
    /**
     * Represents the count of run times.
     */
    private static final int RUN_TIMES = 100;

    /**
     * Represents the <code>LoggingInterceptor</code> instance used in tests.
     */
    private LoggingInterceptor instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        instance = new LoggingInterceptor();
    }

    /**
     * Stress test the method <code>intercept</code>.
     *
     * @throws Exception to JUnit.
     */
    public void testIntercept() throws Exception {
        instance.setLogger("test");
        // Remember the start time
        long start = new Date().getTime();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertEquals("Failed to call the intercept method.", "stress_result",
                instance.intercept(new StressActionInvocation()));
        }
        endCall("intercept", start);
    }

    /**
     * Remember the time calling the method.
     *
     * @param methodName name of the method.
     * @param start start time before calling the method.
     */
    private static void endCall(String methodName, long start) {
        System.out.println("Calling the method '" + methodName
            + "' for '" + new Integer(RUN_TIMES).toString() + "' times cost '"
            + new Long(new Date().getTime() - start).toString() + "' milliseconds.");
    }
}
