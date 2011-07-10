/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.interceptors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.web.common.security.WebAuthentication;

/**
 * <p>
 * Unit tests for class <code>BaseInterceptor</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseInterceptorTest {
    /**
     * <p>
     * Represents the <code>BaseInterceptor</code> instance used to test against.
     * </p>
     */
    private BaseInterceptor impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseInterceptorTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        impl = new ThrottleInterceptor();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        impl = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BaseInterceptor</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof AbstractInterceptor);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseInterceptor()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createWebAuthentication()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCreateWebAuthentication() {
        Map<String, Object> map = new HashMap<String, Object>();
        ActionContext context = new ActionContext(map);
        ServletActionContext.setContext(context);

        HttpSession session = EasyMock.createNiceMock(HttpSession.class);
        HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
        EasyMock.expect(servletRequest.getSession()).andReturn(session);
        ServletActionContext.setRequest(servletRequest);
        HttpServletResponse servletResponse = EasyMock.createNiceMock(HttpServletResponse.class);
        ServletActionContext.setResponse(servletResponse);

        EasyMock.replay(servletRequest, servletResponse);

        WebAuthentication result = impl.createWebAuthentication();

        assertNotNull("The result should be non-null", result);

        EasyMock.verify(servletRequest, servletResponse);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createWebAuthentication()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCreateWebAuthentication2() {
        Map<String, Object> map = new HashMap<String, Object>();
        ActionContext context = new ActionContext(map);
        ServletActionContext.setContext(context);

        HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
        EasyMock.expect(servletRequest.getSession()).andThrow(new IllegalArgumentException("error"));
        ServletActionContext.setRequest(servletRequest);
        HttpServletResponse servletResponse = EasyMock.createNiceMock(HttpServletResponse.class);
        ServletActionContext.setResponse(servletResponse);

        EasyMock.replay(servletRequest, servletResponse);

        WebAuthentication result = impl.createWebAuthentication();

        assertNull("The result should be null", result);

        EasyMock.verify(servletRequest, servletResponse);
    }

}
