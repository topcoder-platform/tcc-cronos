/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;

/**
 * <p>
 * Unit tests for class <code>BaseAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseActionTest {
    /**
     * <p>
     * Represents the <code>BaseAction</code> instance used to test against.
     * </p>
     */
    private BaseAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new LogoutAction();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        impl = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BaseAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof ActionSupport);
        assertTrue("The instance's subclass is not correct.", impl instanceof SessionAware);
        assertTrue("The instance's subclass is not correct.", impl instanceof ServletRequestAware);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getWebAuthenticationFromSession()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetWebAuthenticationFromSession() throws Exception {
        Map<String, Object> session = new HashMap<String, Object>();
        WebAuthentication webAuthentication = new BasicAuthentication(null, null, null);
        session.put("webAuthentication", webAuthentication);
        impl.setSession(session);

        WebAuthentication result = impl.getWebAuthenticationFromSession();
        assertEquals("The return value should be same as ", webAuthentication, result);
    }

    /**
     * <p>
     * Failure test for the method <code>getWebAuthenticationFromSession()</code>.<br>
     * The value got by webAuthenticationSessionKey is null.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testGetWebAuthenticationFromSession_ValueNull() throws Exception {
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);

        impl.getWebAuthenticationFromSession();

    }

    /**
     * <p>
     * Failure test for the method <code>getWebAuthenticationFromSession()</code>.<br>
     * The value got by webAuthenticationSessionKey is string.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testGetWebAuthenticationFromSession_ValueString() throws Exception {
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("webAuthentication", "string");
        impl.setSession(session);

        impl.getWebAuthenticationFromSession();
    }

    /**
     * <p>
     * Accuracy test for the method <code>audit(String, String, String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testAudit() throws Exception {
        try {
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");

            EasyMock.replay(servletRequest);

            HibernateUtils.begin();

            assertFalse("The operation should not be in database", auditDAO.hasOperation("handle1", "op"));

            impl.audit("handle1", "previousValue1", "newValue1");

            assertTrue("The operation that just audited should be in database", auditDAO.hasOperation(
                "handle1", "op"));

            EasyMock.verify(servletRequest);
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Failure test for the method <code>audit(String, String, String)</code>.<br>
     * The handle is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAudit_HandleNull() throws Exception {
        impl.audit(null, "previousValue1", "newValue1");
    }

    /**
     * <p>
     * Failure test for the method <code>audit(String, String, String)</code>.<br>
     * The handle is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAudit_HandleEmpty() throws Exception {
        impl.audit("", "previousValue1", "newValue1");
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuditDAO()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetAuditDAO() {
        assertNull("The initial value should be null", impl.getAuditDAO());

        AuditDAO expect = EasyMock.createMock(AuditDAO.class);

        impl.setAuditDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getAuditDAO());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAuditDAO(AuditDAO)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetAuditDAO() {
        AuditDAO expect = EasyMock.createMock(AuditDAO.class);

        impl.setAuditDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getAuditDAO());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getWebAuthenticationSessionKey()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetWebAuthenticationSessionKey() {
        assertEquals("The initial value should be same as ", "webAuthentication", impl
                        .getWebAuthenticationSessionKey());

        String expect = "newKey";

        impl.setWebAuthenticationSessionKey(expect);

        assertEquals("The return value should be same as ", expect, impl.getWebAuthenticationSessionKey());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setWebAuthenticationSessionKey(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetWebAuthenticationSessionKey() {
        String expect = "newKey";

        impl.setWebAuthenticationSessionKey(expect);

        assertEquals("The return value should be same as ", expect, impl.getWebAuthenticationSessionKey());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSession()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSession() {
        assertNull("The initial value should be null", impl.getSession());

        Map<String, Object> expect = new HashMap<String, Object>();

        impl.setSession(expect);

        assertEquals("The return value should be same as ", expect, impl.getSession());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSession(Map)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSession() {
        Map<String, Object> expect = new HashMap<String, Object>();

        impl.setSession(expect);

        assertEquals("The return value should be same as ", expect, impl.getSession());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getServletRequest()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetServletRequest() {
        assertNull("The initial value should be null", impl.getServletRequest());

        HttpServletRequest expect = EasyMock.createMock(HttpServletRequest.class);

        impl.setServletRequest(expect);

        assertEquals("The return value should be same as ", expect, impl.getServletRequest());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setServletRequest(HttpServletRequest)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetServletRequest() {
        HttpServletRequest expect = EasyMock.createMock(HttpServletRequest.class);

        impl.setServletRequest(expect);

        assertEquals("The return value should be same as ", expect, impl.getServletRequest());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getOperationType()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetOperationType() {
        assertNull("The initial value should be null", impl.getOperationType());

        String expect = "op";

        impl.setOperationType(expect);

        assertEquals("The return value should be same as ", expect, impl.getOperationType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setOperationType(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetOperationType() {
        String expect = "op";

        impl.setOperationType(expect);

        assertEquals("The return value should be same as ", expect, impl.getOperationType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkConfiguration()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckConfiguration() {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The auditDAO is not set.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_AuditDAONotSet() throws Exception {
        impl.setOperationType("op");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The operationType is not set.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_OperationTypeNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);

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
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        impl.setWebAuthenticationSessionKey(" ");

        impl.checkConfiguration();
    }

}
