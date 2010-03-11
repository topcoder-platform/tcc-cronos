/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.accuracytests;

import java.util.HashMap;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.service.interceptors.LoggingInterceptor;


/**
 * <p>Accuracy test cases for LoggingInterceptor.</p>
 *
 * @author onsky
 * @version 1.0
 */
public class LoggingInterceptorAccuracyTests extends TestCase {
    /**
     * <p>Represents LoggingInterceptor instance for testing.</p>
     */
    private LoggingInterceptor instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new LoggingInterceptor();
        instance.setLogger("acc");
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
     * <p>Accuracy test for <code>LoggingInterceptor()</code> constructor.</p>
     */
    public void test_ctor() {
        assertNotNull("object must be initialized", instance);
    }

    /**
     * <p>Accuracy test for <code>intercept()</code> method.</p>
     *
     * @throws Exception to JUnit
     */
    public void test_intercept_accuracy() throws Exception {

        MockActionInvocation invocation = new MockActionInvocation();
        ActionContext context = new ActionContext(new HashMap<String, Object>());
        invocation.setInvocationContext(context);
        invocation.setStack(new MockValueStack());
        assertEquals("user must be intercepted", null, instance.intercept(invocation));
    }
}
