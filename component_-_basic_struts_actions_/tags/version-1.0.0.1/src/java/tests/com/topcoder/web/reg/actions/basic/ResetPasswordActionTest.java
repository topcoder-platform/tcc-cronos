/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.TCResponse;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.PasswordRecoveryDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.PasswordRecoveryDAOHibernate;
import com.topcoder.web.common.model.PasswordRecovery;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.TestHelper;

/**
 * <p>
 * Unit tests for class <code>ResetPasswordAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResetPasswordActionTest {
    /**
     * <p>
     * Represents the <code>ResetPasswordAction</code> instance used to test against.
     * </p>
     */
    private ResetPasswordAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ResetPasswordActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockResetPasswordAction();
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
     * Inheritance test, verifies <code>ResetPasswordAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BasePasswordAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ResetPasswordAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute() throws Exception {
        try {
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession);

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
            impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
            impl.setPasswordRecoveryId(1);
            impl.setMinimalPasswordLength(4);
            impl.setMaximalPasswordLength(8);
            impl.setPrincipalMgrRemote(new MockPrincipalMgrRemote());

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

            HibernateUtils.begin();

            String result = impl.execute();

            assertEquals("The return value should be same as ", "success", result);

        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The user is null.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testExecute_UserNull() throws Exception {
        try {
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession);

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
            impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
            impl.setPasswordRecoveryId(100);
            impl.setMinimalPasswordLength(4);
            impl.setMaximalPasswordLength(8);
            impl.setPrincipalMgrRemote(new MockPrincipalMgrRemote());

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

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
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession);

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
            impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
            impl.setPasswordRecoveryId(2);
            impl.setMinimalPasswordLength(4);
            impl.setMaximalPasswordLength(8);
            impl.setPrincipalMgrRemote(new MockPrincipalMgrRemote());

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

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
     * The RemoteException occurs.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testExecute_RemoteExceptionOccurs() throws Exception {
        try {
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession);

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
            impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
            impl.setPasswordRecoveryId(1);
            impl.setMinimalPasswordLength(4);
            impl.setMaximalPasswordLength(8);
            PrincipalMgrRemote principalMgrRemote = EasyMock.createNiceMock(PrincipalMgrRemote.class);
            EasyMock.expect(
                principalMgrRemote.editPassword(EasyMock.anyObject(UserPrincipal.class), EasyMock
                                .anyObject(String.class), EasyMock.anyObject(TCSubject.class), EasyMock
                                .anyObject(String.class))).andThrow(new RemoteException("error"));
            impl.setPrincipalMgrRemote(principalMgrRemote);

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse, principalMgrRemote);

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
     * The GeneralSecurityException occurs.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testExecute_GeneralSecurityExceptionOccurs() throws Exception {
        try {
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession);

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockBasicAuthentication(persistor, tcRequest,
                tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
            impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
            impl.setPasswordRecoveryId(1);
            impl.setMinimalPasswordLength(4);
            impl.setMaximalPasswordLength(8);
            PrincipalMgrRemote principalMgrRemote = EasyMock.createNiceMock(PrincipalMgrRemote.class);
            EasyMock.expect(
                principalMgrRemote.editPassword(EasyMock.anyObject(UserPrincipal.class), EasyMock
                                .anyObject(String.class), EasyMock.anyObject(TCSubject.class), EasyMock
                                .anyObject(String.class))).andThrow(new GeneralSecurityException("error"));
            impl.setPrincipalMgrRemote(principalMgrRemote);

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse, principalMgrRemote);

            HibernateUtils.begin();

            impl.execute();

        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate() {
        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setId(new Long(123));
        passwordRecovery.setExpireDate(new Date(new Date().getTime() + 1000 * 60 * 60));
        passwordRecovery.setNew(true);
        passwordRecovery.setRecoveryAddress("recoveryAddress@topcoder.com");
        passwordRecovery.setUsed(false);
        User user = new User();
        user.setHandle("handle1");
        passwordRecovery.setUser(user);

        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
        EasyMock.expect(passwordRecoveryDAO.find(new Long(123))).andReturn(passwordRecovery);

        impl.setPasswordRecoveryId(123);
        impl.setHashCode(passwordRecovery.hash());

        EasyMock.replay(passwordRecoveryDAO);

        impl.validate();

        assertTrue("The errors collection should be empty", impl.getActionErrors().isEmpty());
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate2() {
        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setId(new Long(123));
        passwordRecovery.setExpireDate(new Date(1000));
        passwordRecovery.setNew(true);
        passwordRecovery.setRecoveryAddress("recoveryAddress@topcoder.com");
        passwordRecovery.setUsed(true);
        User user = new User();
        user.setHandle("handle1");
        passwordRecovery.setUser(user);

        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
        EasyMock.expect(passwordRecoveryDAO.find(new Long(123))).andReturn(passwordRecovery);

        impl.setPasswordRecoveryId(123);
        impl.setHashCode("abc");

        EasyMock.replay(passwordRecoveryDAO);

        impl.validate();

        assertEquals("The errors collection's size should be same as", 3, impl.getActionErrors().size());
        assertTrue("The errors collection should contain expect value", impl.getActionErrors().contains(
            "hashCode.invalid"));
        assertTrue("The errors collection should contain expect value", impl.getActionErrors().contains(
            "passwordRecovery.expired"));
        assertTrue("The errors collection should contain expect value", impl.getActionErrors().contains(
            "passwordRecovery.used"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate3() {
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
        EasyMock.expect(passwordRecoveryDAO.find(new Long(123))).andReturn(null);

        impl.setPasswordRecoveryId(123);
        impl.setHashCode("abc");

        EasyMock.replay(passwordRecoveryDAO);

        impl.validate();

        assertEquals("The errors collection's size should be same as", 1, impl.getActionErrors().size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPasswordRecoveryId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPasswordRecoveryId() {
        assertEquals("The initial value should be same as ", 0, impl.getPasswordRecoveryId());

        long expect = 3;

        impl.setPasswordRecoveryId(expect);

        assertEquals("The return value should be same as ", expect, impl.getPasswordRecoveryId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPasswordRecoveryId(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPasswordRecoveryId() {
        long expect = 3;

        impl.setPasswordRecoveryId(expect);

        assertEquals("The return value should be same as ", expect, impl.getPasswordRecoveryId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getHashCode()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetHashCode() {
        assertNull("The initial value should be null", impl.getHashCode());

        String expect = "tests";

        impl.setHashCode(expect);

        assertEquals("The return value should be same as ", expect, impl.getHashCode());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setHashCode(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetHashCode() {
        String expect = "tests";

        impl.setHashCode(expect);

        assertEquals("The return value should be same as ", expect, impl.getHashCode());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getNewPassword()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetNewPassword() throws Exception {
        assertNull("The initial value should be null", impl.getNewPassword());

        TestHelper.setField(ResetPasswordAction.class, impl, "newPassword", "test");

        assertEquals("The return value should be same as ", "test", impl.getNewPassword());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setMinimalPasswordLength(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetMinimalPasswordLength() throws Exception {
        assertEquals("The initial value should be same as ", 0, TestHelper.getField(
            ResetPasswordAction.class, impl, "minimalPasswordLength"));

        int expect = 6;

        impl.setMinimalPasswordLength(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            ResetPasswordAction.class, impl, "minimalPasswordLength"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setMaximalPasswordLength(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetMaximalPasswordLength() throws Exception {
        assertEquals("The initial value should be same as ", 0, TestHelper.getField(
            ResetPasswordAction.class, impl, "maximalPasswordLength"));

        int expect = 16;

        impl.setMaximalPasswordLength(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            ResetPasswordAction.class, impl, "maximalPasswordLength"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrincipalMgrRemote(PrincipalMgrRemote)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetPrincipalMgrRemote() throws Exception {
        assertNull("The initial value should be null", TestHelper.getField(ResetPasswordAction.class, impl,
            "principalMgrRemote"));

        PrincipalMgrRemote expect = EasyMock.createMock(PrincipalMgrRemote.class);

        impl.setPrincipalMgrRemote(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            ResetPasswordAction.class, impl, "principalMgrRemote"));
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
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
        PrincipalMgrRemote principalMgrRemote = EasyMock.createMock(PrincipalMgrRemote.class);
        impl.setPrincipalMgrRemote(principalMgrRemote);
        impl.setMinimalPasswordLength(4);
        impl.setMaximalPasswordLength(16);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The principalMgrRemote is not set.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_PrincipalMgrRemoteNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
        impl.setMinimalPasswordLength(4);
        impl.setMaximalPasswordLength(16);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The minimalPasswordLength is not set.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_MinimalPasswordLengthNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
        PrincipalMgrRemote principalMgrRemote = EasyMock.createMock(PrincipalMgrRemote.class);
        impl.setPrincipalMgrRemote(principalMgrRemote);
        impl.setMaximalPasswordLength(16);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The maximalPasswordLength is negative.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_MaximalPasswordLengthNegative() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
        PrincipalMgrRemote principalMgrRemote = EasyMock.createMock(PrincipalMgrRemote.class);
        impl.setPrincipalMgrRemote(principalMgrRemote);
        impl.setMinimalPasswordLength(4);
        impl.setMaximalPasswordLength(-1);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The minimalPasswordLength is greater than maximalPasswordLength.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_MinimalPasswordLengthGreater() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);
        PrincipalMgrRemote principalMgrRemote = EasyMock.createMock(PrincipalMgrRemote.class);
        impl.setPrincipalMgrRemote(principalMgrRemote);
        impl.setMinimalPasswordLength(16);
        impl.setMaximalPasswordLength(4);

        impl.checkConfiguration();
    }
}
