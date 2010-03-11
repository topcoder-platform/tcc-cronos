/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.failuretests;

import junit.framework.TestCase;

import com.opensymphony.xwork2.mock.MockActionInvocation;

import com.topcoder.service.interceptors.AuthenticationInterceptor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Failure tests for AuthenticationInterceptor.
 *
 * @author morehappiness
 * @version 1.0
 */
public class AuthenticationInterceptorFailureTests {
    /**
     * The interceptor for tests.
     */
    private AuthenticationInterceptor interceptor;

    /**
     * Sets up the environment.
     */
    @Before
    public void setupInterceptor() {
        interceptor = new AuthenticationInterceptor();
    }

    /**
     * Test for setLoginPageName. If argument is null, IllegalArgumentException.class is expected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setLoginPageName_failure1() {
        interceptor.setLoginPageName(null);
        fail("IllegalArgumentException is expected");
    }

    /**
     * Test for setLoginPageName. If argument is empty, IllegalArgumentException.class is expected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setLoginPageName_failure2() {
        interceptor.setLoginPageName("  \t\n  ");
        fail("IllegalArgumentException is expected");
    }

    /**
     * Test for setUserSessionIdentityKey. If argument is null, IllegalArgumentException.class is expected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setUserSessionIdentityKey_failure1() {
        interceptor.setUserSessionIdentityKey(null);
        fail("IllegalArgumentException is expected");
    }

    /**
     * Test for setUserSessionIdentityKey. If argument is null, IllegalArgumentException.class is expected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setUserSessionIdentityKey_failure2() {
        interceptor.setUserSessionIdentityKey("  \t\n  ");
        fail("IllegalArgumentException is expected");
    }

    /**
     * Test for intercept. If loginPageName is not set, IllegalStateException is expected.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void test_intercept_failure1() throws Exception {
        interceptor.setUserSessionIdentityKey("userSessionIdentityKey");
        interceptor.intercept(new MockActionInvocation());
        fail("IllegalStateException is expected");
    }

    /**
     * Test for intercept. If userSessionIdentityKey is not set, IllegalStateException is expected.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void test_intercept_failure2() throws Exception {
        interceptor.setLoginPageName("loginPageName");
        interceptor.intercept(new MockActionInvocation());
        fail("IllegalStateException is expected");
    }
}
