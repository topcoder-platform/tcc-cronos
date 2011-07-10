/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.interceptors.AuthorizationInterceptor;
import com.topcoder.web.reg.interceptors.AuthorizationInterceptorException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for AuthorizationInterceptor.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class AuthorizationInterceptorFailureTests extends TestCase {

    /**
     * <p>
     * The AuthorizationInterceptor instance for testing.
     * </p>
     */
    private AuthorizationInterceptor instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new AuthorizationInterceptor();
        instance.setSessionInfoKey("sessionInfoKey");
        instance.setWebAuthenticationSessionKey("webAuthenticationSessionKey");
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AuthorizationInterceptorFailureTests.class);
    }

    /**
     * <p>
     * Tests AuthorizationInterceptor#intercept(ActionInvocation) for failure.
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
     * Tests AuthorizationInterceptor#intercept(ActionInvocation) for failure.
     * Expects AuthorizationInterceptorException.
     * </p>
     */
    public void testIntercept_AuthorizationInterceptorException() {
        try {
            instance.intercept(new MockActionInvocation());
            fail("AuthorizationInterceptorException expected.");
        } catch (AuthorizationInterceptorException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AuthorizationInterceptor#checkConfiguration() for failure.
     * It tests the case that when sessionInfoKey is null and
     * expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullSessionInfoKey() {
        instance.setSessionInfoKey(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AuthorizationInterceptor#checkConfiguration() for failure.
     * It tests the case that when sessionInfoKey is empty and
     * expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptySessionInfoKey() {
        instance.setSessionInfoKey(" ");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AuthorizationInterceptor#checkConfiguration() for failure.
     * It tests the case that when webAuthenticationSessionKey is null
     * and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullWebAuthenticationSessionKey() {
        instance.setWebAuthenticationSessionKey(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AuthorizationInterceptor#checkConfiguration() for failure.
     * It tests the case that when webAuthenticationSessionKey is empty
     * and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptyWebAuthenticationSessionKey() {
        instance.setWebAuthenticationSessionKey(" ");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }
}