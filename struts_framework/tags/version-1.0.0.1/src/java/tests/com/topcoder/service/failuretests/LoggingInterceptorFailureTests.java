/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.failuretests;

import junit.framework.TestCase;

import com.opensymphony.xwork2.mock.MockActionInvocation;

import com.topcoder.service.interceptors.LoggingInterceptor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Failure tests for LoggingInterceptor.
 *
 * @author morehappiness
 * @version 1.0
 */
public class LoggingInterceptorFailureTests {
    /**
     * The interceptor for tests.
     */
    private LoggingInterceptor interceptor;

    /**
     * Sets up the environment.
     */
    @Before
    public void setupInterceptor() {
        interceptor = new LoggingInterceptor();
    }

    /**
     * Test for setLogger. If argument is null, IllegalArgumentException.class is expected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setLogger_failure1() {
        interceptor.setLogger(null);
        fail("IllegalArgumentException is expected");
    }

    /**
     * Test for setLogger. If argument is empty, IllegalArgumentException.class is expected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setLogger_failure2() {
        interceptor.setLogger("  \t\n  ");
        fail("IllegalArgumentException is expected");
    }

    /**
     * Test for intercept. If logger is not set, IllegalStateException is expected.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void test_intercept_failure2() throws Exception {
        interceptor.intercept(new MockActionInvocation());
        fail("IllegalStateException is expected");
    }
}
