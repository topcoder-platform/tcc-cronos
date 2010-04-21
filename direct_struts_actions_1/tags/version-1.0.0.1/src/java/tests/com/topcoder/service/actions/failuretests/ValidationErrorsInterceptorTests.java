/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import com.opensymphony.xwork2.mock.MockActionInvocation;

import com.topcoder.service.actions.ValidationErrorsInterceptor;

import junit.framework.TestCase;


/**
 * Failure cases of ValidationErrorsInterceptor.
 *
 * @author onsky
 * @version 1.0
 */
public class ValidationErrorsInterceptorTests extends TestCase {
    /**
     * <p>Represents ValidationErrorsInterceptor instance for testing.</p>
     */
    private ValidationErrorsInterceptor instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new ValidationErrorsInterceptor();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Failure test for method intercept.
     *
     * @throws Exception if any error.
     */
    public void test_intercept1() throws Exception {
        try {
            instance.intercept(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test for method intercept.
     *
     * @throws Exception if any error.
     */
    public void test_intercept2() throws Exception {
        try {
            MockActionInvocation in = new MockActionInvocation();
            in.setAction(new Object());
            instance.intercept(new MockActionInvocation());
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
