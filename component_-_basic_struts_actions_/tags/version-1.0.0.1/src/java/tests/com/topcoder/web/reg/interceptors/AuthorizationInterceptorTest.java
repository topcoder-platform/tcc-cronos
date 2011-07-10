/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.interceptors;

import static org.junit.Assert.assertEquals;
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
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.TCResponse;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.TestHelper;
import com.topcoder.web.reg.actions.basic.MockBasicAuthentication;

/**
 * <p>
 * Unit tests for class <code>AuthorizationInterceptor</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuthorizationInterceptorTest {
    /**
     * <p>
     * Represents the <code>AuthorizationInterceptor</code> instance used to test against.
     * </p>
     */
    private AuthorizationInterceptor impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AuthorizationInterceptorTest.class);
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
        impl = new AuthorizationInterceptor();
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
     * Inheritance test, verifies <code>AuthorizationInterceptor</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseInterceptor);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AuthorizationInterceptor()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
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
     * Can not connect to server.<br>
     * Expect AuthorizationInterceptorException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = AuthorizationInterceptorException.class)
    public void testIntercept_CannotConnectToServer() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        ActionContext context = new ActionContext(map);
        ServletActionContext.setContext(context);

        HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
        TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
        TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
        SessionPersistor persistor = new SessionPersistor(httpSession);
        WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest, tcResponse);

        EasyMock.expect(httpSession.getAttribute("webAuthentication")).andReturn(webAuthentication)
                        .anyTimes();
        HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
        EasyMock.expect(servletRequest.getSession()).andReturn(httpSession);
        ServletActionContext.setRequest(servletRequest);

        ActionInvocation actionInvocation = EasyMock.createNiceMock(ActionInvocation.class);
        EasyMock.expect(actionInvocation.invoke()).andReturn("success");

        EasyMock.replay(httpSession, tcRequest, tcResponse, servletRequest, actionInvocation);

        impl.intercept(actionInvocation);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSessionInfoKey(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetSessionInfoKey() throws Exception {
        assertEquals("The initial value should be same as", "sessionInfo", TestHelper.getField(
            AuthorizationInterceptor.class, impl, "sessionInfoKey"));

        String expect = "test";

        impl.setSessionInfoKey(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            AuthorizationInterceptor.class, impl, "sessionInfoKey"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setWebAuthenticationSessionKey(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetWebAuthenticationSessionKey() throws Exception {
        assertEquals("The initial value should be same as", "webAuthentication", TestHelper.getField(
            AuthorizationInterceptor.class, impl, "webAuthenticationSessionKey"));

        String expect = "test";

        impl.setWebAuthenticationSessionKey(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            AuthorizationInterceptor.class, impl, "webAuthenticationSessionKey"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkConfiguration()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckConfiguration() {
        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The sessionInfoKey is null.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_SessionInfoKeyNull() throws Exception {
        impl.setSessionInfoKey(null);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The webAuthenticationSessionKey is empty.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_WebAuthenticationSessionKeyEmpty() throws Exception {
        impl.setWebAuthenticationSessionKey(" ");

        impl.checkConfiguration();
    }

}
