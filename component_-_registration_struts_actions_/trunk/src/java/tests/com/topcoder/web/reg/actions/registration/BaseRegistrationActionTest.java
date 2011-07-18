/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import static org.junit.Assert.assertEquals;
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
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.RegistrationTypeDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.RegistrationActionConfigurationException;

/**
 * <p>
 * Unit tests for class <code>BaseRegistrationAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BaseRegistrationActionTest {
    /**
     * <p>
     * Represents the <code>BaseRegistrationAction</code> instance used to test against.
     * </p>
     */
    private BaseRegistrationAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseRegistrationActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockBaseRegistrationAction();
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
     * Inheritance test, verifies <code>BaseRegistrationAction</code> subclasses should be correct.
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
     * Accuracy test for the constructor <code>BaseRegistrationAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInitialization()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckInitialization() {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);
        impl.setAuthenticationSessionKey("webAuthentication");
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createMock(RegistrationTypeDAO.class);
        impl.setRegistrationTypeDAO(registrationTypeDAO);
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The auditDAO is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_AuditDAONotSet() throws Exception {
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);
        impl.setAuthenticationSessionKey("webAuthentication");
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createMock(RegistrationTypeDAO.class);
        impl.setRegistrationTypeDAO(registrationTypeDAO);
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The log is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_LogNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setAuthenticationSessionKey("webAuthentication");
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createMock(RegistrationTypeDAO.class);
        impl.setRegistrationTypeDAO(registrationTypeDAO);
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The authenticationSessionKey is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_AuthenticationSessionKeyNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createMock(RegistrationTypeDAO.class);
        impl.setRegistrationTypeDAO(registrationTypeDAO);
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The authenticationSessionKey is empty.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_AuthenticationSessionKeyEmpty() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);
        impl.setAuthenticationSessionKey("   ");
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createMock(RegistrationTypeDAO.class);
        impl.setRegistrationTypeDAO(registrationTypeDAO);
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The session is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_SessionNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);
        impl.setAuthenticationSessionKey("webAuthentication");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createMock(RegistrationTypeDAO.class);
        impl.setRegistrationTypeDAO(registrationTypeDAO);
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The userDAO is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_UserDAONotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);
        impl.setAuthenticationSessionKey("webAuthentication");
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createMock(RegistrationTypeDAO.class);
        impl.setRegistrationTypeDAO(registrationTypeDAO);
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The registrationTypeDAO is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_RegistrationTypeDAONotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);
        impl.setAuthenticationSessionKey("webAuthentication");
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The servletRequest is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_ServletRequestNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);
        impl.setAuthenticationSessionKey("webAuthentication");
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createMock(RegistrationTypeDAO.class);
        impl.setRegistrationTypeDAO(registrationTypeDAO);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuthentication()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAuthentication() throws Exception {
        Map<String, Object> session = new HashMap<String, Object>();
        WebAuthentication webAuthentication = new BasicAuthentication(null, null, null);
        session.put("webAuthentication", webAuthentication);
        impl.setSession(session);

        impl.setAuthenticationSessionKey("webAuthentication");
        WebAuthentication result = impl.getAuthentication();
        assertEquals("The return value should be same as ", webAuthentication, result);
    }

    /**
     * <p>
     * Failure test for the method <code>getAuthentication()</code>.<br>
     * The value got by authenticationSessionKey is null.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testGetAuthentication_ValueNull() throws Exception {
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);

        impl.getAuthentication();

    }

    /**
     * <p>
     * Failure test for the method <code>getAuthentication()</code>.<br>
     * The value got by authenticationSessionKey is string.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testGetAuthentication_ValueString() throws Exception {
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("webAuthentication", "string");
        impl.setSession(session);

        impl.getAuthentication();
    }

    /**
     * <p>
     * Accuracy test for the method <code>auditAction(String, String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testAuditAction() throws Exception {
        try {
            impl.setAuditDAO(new AuditDAOHibernate());
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");

            EasyMock.replay(servletRequest);

            HibernateUtils.begin();

            impl.auditAction("operationType", "handle");

            EasyMock.verify(servletRequest);

        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLogger()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetLogger() {
        assertNull("The initial value should be null", impl.getLogger());

        Log expect = LogManager.getLog("test");

        impl.setLogger(expect);

        assertEquals("The return value should be same as ", expect, impl.getLogger());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLogger(Map)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetLogger() {
        Log expect = LogManager.getLog("test");

        impl.setLogger(expect);

        assertEquals("The return value should be same as ", expect, impl.getLogger());
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
     * Accuracy test for the method <code>getAuthenticationSessionKey()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetAuthenticationSessionKey() {
        assertNull("The initial value should be null", impl.getAuthenticationSessionKey());

        String expect = "newKey";

        impl.setAuthenticationSessionKey(expect);

        assertEquals("The return value should be same as ", expect, impl.getAuthenticationSessionKey());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAuthenticationSessionKey(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetAuthenticationSessionKey() {
        String expect = "newKey";

        impl.setAuthenticationSessionKey(expect);

        assertEquals("The return value should be same as ", expect, impl.getAuthenticationSessionKey());
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
     * Accuracy test for the method <code>getUserDAO()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetUserDAO() {
        assertNull("The initial value should be null", impl.getUserDAO());

        UserDAO expect = EasyMock.createMock(UserDAO.class);

        impl.setUserDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getUserDAO());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserDAO(UserDAO)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetUserDAO() {
        UserDAO expect = EasyMock.createMock(UserDAO.class);

        impl.setUserDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getUserDAO());
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
     * Accuracy test for the method <code>getRegistrationTypeDAO()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetRegistrationTypeDAO() {
        assertNull("The initial value should be null", impl.getRegistrationTypeDAO());

        RegistrationTypeDAO expect = EasyMock.createMock(RegistrationTypeDAO.class);

        impl.setRegistrationTypeDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getRegistrationTypeDAO());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRegistrationTypeDAO(RegistrationTypeDAO)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetRegistrationTypeDAO() {
        RegistrationTypeDAO expect = EasyMock.createMock(RegistrationTypeDAO.class);

        impl.setRegistrationTypeDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getRegistrationTypeDAO());
    }

    /**
     * <p>
     * Mock class for testing.
     * </p>
     *
     * @author stevenfrog
     * @version 1.0
     */
    class MockBaseRegistrationAction extends BaseRegistrationAction {
        // Empty
    }

}
