/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.interceptors.ThrottleInterceptor;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ThrottleInterceptor.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ThrottleInterceptorFailureTests extends TestCase {
    /**
     * <p>
     * The ThrottleInterceptor instance for testing.
     * </p>
     */
    private ThrottleInterceptor instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new ThrottleInterceptor();
        instance.setThrottleInterval(10);
        instance.setThrottleMaxHits(20);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ThrottleInterceptorFailureTests.class);
    }

    /**
     * <p>
     * Tests ThrottleInterceptor#intercept(ActionInvocation) for failure.
     * It tests the case that when actionInvocation is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testIntercept_NullActionInvocation() throws Exception {
        try {
            instance.intercept(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ThrottleInterceptor#checkConfiguration() for failure.
     * It tests the case that when throttleMaxHits is negative and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NegativeThrottleMaxHits() {
        instance.setThrottleMaxHits(-10);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ThrottleInterceptor#checkConfiguration() for failure.
     * It tests the case that when throttleInterval is negative and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NegativeThrottleInterval() {
        instance.setThrottleInterval(-10);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }
}