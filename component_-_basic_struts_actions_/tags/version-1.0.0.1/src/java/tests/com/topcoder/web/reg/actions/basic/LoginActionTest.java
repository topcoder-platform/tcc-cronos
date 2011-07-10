/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.TextProvider;
import com.topcoder.security.TCSubject;
import com.topcoder.security.login.LoginRemote;
import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.TCResponse;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.TestHelper;

/**
 * <p>
 * Unit tests for class <code>LoginAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LoginActionTest {
    /**
     * <p>
     * Represents the <code>LoginAction</code> instance used to test against.
     * </p>
     */
    private LoginAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(LoginActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockLoginAction();
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
     * Inheritance test, verifies <code>LoginAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseUserDAOAwareAction);
        assertTrue("The instance's subclass is not correct.", impl instanceof ServletResponseAware);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>LoginAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>execute()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute() throws Exception {
        LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
        EasyMock.expect(
            loginRemote.login(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class), EasyMock
                            .anyObject(String.class))).andThrow(new RemoteException("error"));
        impl.setLoginRemote(loginRemote);

        TextProvider textProvider = EasyMock.createNiceMock(TextProvider.class);
        EasyMock.expect(textProvider.getText(EasyMock.anyObject(String.class))).andReturn("test");
        TestHelper.setField(ActionSupport.class, impl, "textProvider", textProvider);

        EasyMock.replay(loginRemote);

        String result = impl.execute();

        assertEquals("The return value should be same as ", "input", result);
        assertTrue("The error collection should contain expect string", impl.getActionErrors().contains(
            "credential.invalid"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>execute()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute2() throws Exception {
        try {
            TCSubject tcSubject = new TCSubject(20);
            LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
            EasyMock.expect(
                loginRemote.login(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                    EasyMock.anyObject(String.class))).andReturn(tcSubject);
            impl.setLoginRemote(loginRemote);
            impl.setActiveEmailStatus(2);

            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new BasicAuthentication(persistor, tcRequest, tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            UserDAO userDAO = new UserDAOHibernate();
            impl.setUserDAO(userDAO);

            EasyMock.replay(loginRemote);

            HibernateUtils.begin();

            String result = impl.execute();

            assertEquals("The return value should be same as ", "emailActivation", result);
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>execute()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute3() throws Exception {
        try {
            TCSubject tcSubject = new TCSubject(20);
            LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
            EasyMock.expect(
                loginRemote.login(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                    EasyMock.anyObject(String.class))).andReturn(tcSubject);
            impl.setLoginRemote(loginRemote);

            HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");

            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            UserDAO userDAO = new UserDAOHibernate();
            impl.setUserDAO(userDAO);
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");

            impl.setUsername("username");
            impl.setPassword("password");

            EasyMock.replay(loginRemote, servletRequest, httpSession, tcRequest, tcResponse);

            HibernateUtils.begin();

            String result = impl.execute();

            assertEquals("The return value should be same as ", "firstTimeLogin", result);
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>execute()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute4() throws Exception {
        try {
            TCSubject tcSubject = new TCSubject(20);
            LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
            EasyMock.expect(
                loginRemote.login(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                    EasyMock.anyObject(String.class))).andReturn(tcSubject);
            impl.setLoginRemote(loginRemote);

            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost").anyTimes();
            EasyMock.expect(servletRequest.getParameter("lastRequestedURIParameterName")).andReturn("uri");
            HttpServletResponse servletResponse = EasyMock.createNiceMock(HttpServletResponse.class);
            impl.setServletResponse(servletResponse);

            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            UserDAO userDAO = new UserDAOHibernate();
            impl.setUserDAO(userDAO);
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            impl.setLastRequestedURIParameterName("lastRequestedURIParameterName");

            impl.setUsername("username");
            impl.setPassword("password");

            EasyMock.replay(loginRemote, servletRequest, httpSession, tcRequest, tcResponse);

            HibernateUtils.begin();

            impl.audit("username", null, null);

            String result = impl.execute();

            assertNull("The return value should be null", result);
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>execute()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute5() throws Exception {
        try {
            TCSubject tcSubject = new TCSubject(20);
            LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
            EasyMock.expect(
                loginRemote.login(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                    EasyMock.anyObject(String.class))).andReturn(tcSubject);
            impl.setLoginRemote(loginRemote);

            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost").anyTimes();

            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            UserDAO userDAO = new UserDAOHibernate();
            impl.setUserDAO(userDAO);
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            impl.setLastRequestedURIParameterName("lastRequestedURIParameterName");

            impl.setUsername("username");
            impl.setPassword("password");

            EasyMock.replay(loginRemote, servletRequest, httpSession, tcRequest, tcResponse);

            HibernateUtils.begin();

            impl.audit("username", null, null);

            String result = impl.execute();

            assertEquals("The return value should be same as ", "success", result);
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>execute()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute6() throws Exception {
        try {
            TCSubject tcSubject = new TCSubject(999);
            LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
            EasyMock.expect(
                loginRemote.login(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                    EasyMock.anyObject(String.class))).andReturn(tcSubject);
            impl.setLoginRemote(loginRemote);

            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost").anyTimes();

            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            UserDAO userDAO = new UserDAOHibernate();
            impl.setUserDAO(userDAO);
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");

            impl.setUsername("username");
            impl.setPassword("password");

            EasyMock.replay(loginRemote, servletRequest, httpSession, tcRequest, tcResponse);

            HibernateUtils.begin();

            String result = impl.execute();

            assertEquals("The return value should be same as ", "error", result);
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The user is not find.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testExecute_UserNotFind() throws Exception {
        try {
            TCSubject tcSubject = new TCSubject(7);
            LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
            EasyMock.expect(
                loginRemote.login(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                    EasyMock.anyObject(String.class))).andReturn(tcSubject);
            impl.setLoginRemote(loginRemote);
            impl.setActiveEmailStatus(2);

            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new BasicAuthentication(persistor, tcRequest, tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            UserDAO userDAO = new UserDAOHibernate();
            impl.setUserDAO(userDAO);

            EasyMock.replay(loginRemote);

            HibernateUtils.begin();

            impl.execute();
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The LoginException occurs.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testExecute_LoginExceptionOccurs() throws Exception {
        try {
            TCSubject tcSubject = new TCSubject(21);
            LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
            EasyMock.expect(
                loginRemote.login(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                    EasyMock.anyObject(String.class))).andReturn(tcSubject);
            impl.setLoginRemote(loginRemote);

            HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");

            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            UserDAO userDAO = new UserDAOHibernate();
            impl.setUserDAO(userDAO);
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");

            impl.setUsername("dok_tester1");
            impl.setPassword("password");

            EasyMock.replay(loginRemote, servletRequest, httpSession, tcRequest, tcResponse);

            HibernateUtils.begin();

            impl.execute();
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The IOException occurs.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testExecute_IOExceptionOccurs() throws Exception {
        try {
            TCSubject tcSubject = new TCSubject(20);
            LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
            EasyMock.expect(
                loginRemote.login(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                    EasyMock.anyObject(String.class))).andReturn(tcSubject);
            impl.setLoginRemote(loginRemote);

            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost").anyTimes();
            EasyMock.expect(servletRequest.getParameter("lastRequestedURIParameterName")).andReturn("uri");
            HttpServletResponse servletResponse = EasyMock.createNiceMock(HttpServletResponse.class);
            impl.setServletResponse(servletResponse);
            servletResponse.sendRedirect("uri");
            EasyMock.expectLastCall().andThrow(new IOException("IOException error"));

            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            UserDAO userDAO = new UserDAOHibernate();
            impl.setUserDAO(userDAO);
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            impl.setLastRequestedURIParameterName("lastRequestedURIParameterName");

            impl.setUsername("username");
            impl.setPassword("password");

            EasyMock.replay(loginRemote, servletRequest, servletResponse, httpSession, tcRequest, tcResponse);

            HibernateUtils.begin();

            impl.audit("username", null, null);

            impl.execute();
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUsername()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetUsername() {
        assertNull("The initial value should be null", impl.getUsername());

        String expect = "tests";

        impl.setUsername(expect);

        assertEquals("The return value should be same as ", expect, impl.getUsername());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUsername(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetUsername() {
        String expect = "tests";

        impl.setUsername(expect);

        assertEquals("The return value should be same as ", expect, impl.getUsername());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPassword()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPassword() {
        assertNull("The initial value should be null", impl.getPassword());

        String expect = "tests";

        impl.setPassword(expect);

        assertEquals("The return value should be same as ", expect, impl.getPassword());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPassword(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPassword() {
        String expect = "tests";

        impl.setPassword(expect);

        assertEquals("The return value should be same as ", expect, impl.getPassword());
    }

    /**
     * <p>
     * Accuracy test for the method <code>isRememberMe()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testIsRememberMe() {
        assertFalse("The initial value should be false", impl.isRememberMe());

        impl.setRememberMe(true);

        assertTrue("The return value should be true", impl.isRememberMe());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRememberMe(boolean)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetRememberMe() {
        assertFalse("The initial value should be false", impl.isRememberMe());

        impl.setRememberMe(true);

        assertTrue("The return value should be true", impl.isRememberMe());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setActiveEmailStatus(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetActiveEmailStatus() throws Exception {
        assertEquals("The initial value should be same as ", 1, TestHelper.getField(LoginAction.class, impl,
            "activeEmailStatus"));

        int expect = 4;

        impl.setActiveEmailStatus(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(LoginAction.class,
            impl, "activeEmailStatus"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLoginRemote(LoginRemote)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetLoginRemote() throws Exception {
        assertNull("The initial value should be null", TestHelper.getField(LoginAction.class, impl,
            "loginRemote"));

        LoginRemote expect = EasyMock.createMock(LoginRemote.class);

        impl.setLoginRemote(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(LoginAction.class,
            impl, "loginRemote"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLastRequestedURIParameterName(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetLastRequestedURIParameterName() throws Exception {
        assertNull("The initial value should be null", TestHelper.getField(LoginAction.class, impl,
            "lastRequestedURIParameterName"));

        String expect = "test";

        impl.setLastRequestedURIParameterName(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(LoginAction.class,
            impl, "lastRequestedURIParameterName"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setServletResponse(HttpServletResponse)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetServletResponse() throws Exception {
        assertNull("The initial value should be null", TestHelper.getField(LoginAction.class, impl,
            "servletResponse"));

        HttpServletResponse expect = EasyMock.createMock(HttpServletResponse.class);

        impl.setServletResponse(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(LoginAction.class,
            impl, "servletResponse"));
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
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        LoginRemote loginRemote = EasyMock.createMock(LoginRemote.class);
        impl.setLoginRemote(loginRemote);
        impl.setLastRequestedURIParameterName("paramName");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The loginRemote is not set.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_LoginRemoteNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        impl.setLastRequestedURIParameterName("paramName");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The lastRequestedURIParameterName is not set.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_LastRequestedURIParameterNameNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        LoginRemote loginRemote = EasyMock.createMock(LoginRemote.class);
        impl.setLoginRemote(loginRemote);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The lastRequestedURIParameterName is empty.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_LastRequestedURIParameterNameEmpty() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        LoginRemote loginRemote = EasyMock.createMock(LoginRemote.class);
        impl.setLoginRemote(loginRemote);
        impl.setLastRequestedURIParameterName(" ");

        impl.checkConfiguration();
    }

}
