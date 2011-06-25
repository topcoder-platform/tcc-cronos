/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.interceptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.TestHelper;

/**
 * <p>
 * Unit tests for class <code>ThrottleInterceptor</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ThrottleInterceptorTest {
    /**
     * <p>
     * Represents the <code>ThrottleInterceptor</code> instance used to test against.
     * </p>
     */
    private ThrottleInterceptor impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ThrottleInterceptorTest.class);
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
     * Inheritance test, verifies <code>ThrottleInterceptor</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseInterceptor);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ThrottleInterceptor()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>intercept(ActionInvocation)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testIntercept() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        ActionContext context = new ActionContext(map);
        ServletActionContext.setContext(context);

        HttpSession session = EasyMock.createNiceMock(HttpSession.class);
        HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
        EasyMock.expect(servletRequest.getSession()).andReturn(session);
        ServletActionContext.setRequest(servletRequest);

        ActionInvocation actionInvocation = EasyMock.createNiceMock(ActionInvocation.class);
        EasyMock.expect(actionInvocation.invoke()).andReturn("input");

        impl.setThrottleEnabled(true);

        EasyMock.replay(session, servletRequest, actionInvocation);

        String result = impl.intercept(actionInvocation);

        assertEquals("The return value should be same as ", "input", result);

        EasyMock.verify(session, servletRequest, actionInvocation);
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIntercept_ParamNull() throws Exception {
        impl.intercept(null);
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The Exception occurs.<br>
     * Expect Exception.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = Exception.class)
    public void testIntercept_ExceptionOccurs() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        ActionContext context = new ActionContext(map);
        ServletActionContext.setContext(context);

        HttpSession session = EasyMock.createNiceMock(HttpSession.class);
        HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
        EasyMock.expect(servletRequest.getSession()).andReturn(session);
        ServletActionContext.setRequest(servletRequest);

        ActionInvocation actionInvocation = EasyMock.createNiceMock(ActionInvocation.class);
        EasyMock.expect(actionInvocation.invoke()).andThrow(new Exception("error"));

        impl.setThrottleEnabled(false);

        EasyMock.replay(session, servletRequest, actionInvocation);

        impl.intercept(actionInvocation);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setThrottleEnabled(boolean)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetThrottleEnabled() throws Exception {
        assertFalse("The initial value should be false", (Boolean) TestHelper.getField(
            ThrottleInterceptor.class, impl, "throttleEnabled"));

        impl.setThrottleEnabled(true);

        assertTrue("The return value should be true ", (Boolean) TestHelper.getField(
            ThrottleInterceptor.class, impl, "throttleEnabled"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setThrottleMaxHits(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetThrottleMaxHits() throws Exception {
        assertEquals("The initial value should be same as", 10, TestHelper.getField(
            ThrottleInterceptor.class, impl, "throttleMaxHits"));

        int expect = 14;

        impl.setThrottleMaxHits(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            ThrottleInterceptor.class, impl, "throttleMaxHits"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setThrottleInterval(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetThrottleInterval() throws Exception {
        assertEquals("The initial value should be same as", 5000, TestHelper.getField(
            ThrottleInterceptor.class, impl, "throttleInterval"));

        int expect = 14;

        impl.setThrottleInterval(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            ThrottleInterceptor.class, impl, "throttleInterval"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkConfiguration()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckConfiguration() {
        impl.setThrottleInterval(1);
        impl.setThrottleMaxHits(3);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The throttleMaxHits is negative.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_ThrottleMaxHitsNegative() throws Exception {
        impl.setThrottleInterval(1);
        impl.setThrottleMaxHits(-3);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The throttleInterval is negative.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_ThrottleIntervalNegative() throws Exception {
        impl.setThrottleInterval(-1);
        impl.setThrottleMaxHits(3);

        impl.checkConfiguration();
    }

}
