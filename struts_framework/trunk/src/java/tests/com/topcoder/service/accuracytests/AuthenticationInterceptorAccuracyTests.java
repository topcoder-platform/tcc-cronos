/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.accuracytests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.service.interceptors.AuthenticationInterceptor;


/**
 * <p>Accuracy test cases for AuthenticationInterceptor.</p>
 *
 * @author onsky
 * @version 1.0
 */
public class AuthenticationInterceptorAccuracyTests extends TestCase {
    /**
     * <p>Represents AuthenticationInterceptor instance for testing.</p>
     */
    private AuthenticationInterceptor instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new AuthenticationInterceptor();
        instance.setUserSessionIdentityKey("user");
        instance.setLoginPageName("login");
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
     * <p>Accuracy test for <code>AuthenticationInterceptor()</code> constructor.</p>
     */
    public void test_ctor() {
        assertNotNull("object must be initialized", instance);
    }

    /**
     * <p>Accuracy test for <code>intercept()</code> method.</p>
     *
     * @throws Exception to JUnit
     */
    public void test_intercept_accuracy1() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        ActionContext context = new ActionContext(new HashMap<String, Object>());
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("user", "Test1");

        context.setSession(session);
        invocation.setInvocationContext(context);
        assertEquals("user must be intercepted", null, instance.intercept(invocation));
    }

    /**
     * <p>Accuracy test for <code>intercept()</code> method.</p>
     *
     * @throws Exception to JUnit
     */
    public void test_intercept_accuracy2() throws Exception {

        MockActionInvocation invocation = new MockActionInvocation();
        ActionContext context = new ActionContext(new HashMap<String, Object>());
        context.setSession(new HashMap<String, Object>());
        invocation.setInvocationContext(context);
        assertEquals("user must be intercepted", "login", instance.intercept(invocation));
    }
}
