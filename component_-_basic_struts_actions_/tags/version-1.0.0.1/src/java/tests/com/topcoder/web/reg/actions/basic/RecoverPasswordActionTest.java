/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.PasswordRecoveryDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.PasswordRecoveryDAOHibernate;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.Constants;
import com.topcoder.web.reg.TestHelper;

/**
 * <p>
 * Unit tests for class <code>RecoverPasswordAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RecoverPasswordActionTest {
    /**
     * <p>
     * Represents the <code>RecoverPasswordAction</code> instance used to test against.
     * </p>
     */
    private RecoverPasswordAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(RecoverPasswordActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockRecoverPasswordAction();
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
     * Inheritance test, verifies <code>RecoverPasswordAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BasePasswordRecoveryAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>RecoverPasswordAction()</code>.<br>
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
            UserDAO userDAO = new UserDAOHibernate();
            impl.setUserDAO(userDAO);
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
            impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");

            impl.setEmailBodyTemplateFilePath("test_files" + File.separator + "mailBodyTemplate.txt");
            impl.setEmailFromAddress("payment@topcoder.com");
            impl.setEmailSubject("SubjectPayment");
            impl.setResetPasswordLinkTemplate("Id:%passwordRecoveryId%, hashCode:%hashCode%");

            impl.setHandle("dok_tester");
            impl.setPasswordRecoveryExpiration(1000);

            EasyMock.replay(servletRequest);

            HibernateUtils.begin();

            String result = impl.execute();

            assertEquals("The return value should be same as ", "success", result);

            EasyMock.verify(servletRequest);
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
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute2() throws Exception {
        try {
            UserDAO userDAO = new UserDAOHibernate();
            impl.setUserDAO(userDAO);
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
            impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");

            impl.setEmailBodyTemplateFilePath("test_files" + File.separator + "mailBodyTemplate.txt");
            impl.setEmailFromAddress("payment@topcoder.com");
            impl.setEmailSubject("SubjectPayment");
            impl.setResetPasswordLinkTemplate("Id:%passwordRecoveryId%, hashCode:%hashCode%");

            impl.setEmail("foo@fooonyou.com");
            impl.setPasswordRecoveryExpiration(1000);

            EasyMock.replay(servletRequest);

            HibernateUtils.begin();

            String result = impl.execute();

            assertEquals("The return value should be same as ", "success", result);

            EasyMock.verify(servletRequest);
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
        AuditDAO auditDAO = new AuditDAOHibernate();
        impl.setAuditDAO(auditDAO);

        impl.execute();
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate() {
        impl.setHandle("handle");
        impl.setEmail("email@topcoder.com");

        UserDAO userDAO = EasyMock.createNiceMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        EasyMock.expect(userDAO.find("handle", false)).andReturn(new User());
        EasyMock.expect(userDAO.find("email@topcoder.com")).andReturn(new User());

        Constants.MAX_EMAIL_LENGTH = 32;

        EasyMock.replay(userDAO);

        impl.validate();

        assertTrue("The errors collection should be empty", impl.getActionErrors().isEmpty());
        assertTrue("The errors collection should be empty", impl.getFieldErrors().isEmpty());

        EasyMock.verify(userDAO);
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate2() {
        impl.setHandle(null);
        impl.setEmail(null);

        impl.validate();

        assertEquals("The errors collection's size should be same as", 1, impl.getActionErrors().size());
        assertTrue("The errors collection should contain expect string", impl.getActionErrors().contains(
            "passwordRecovery.handleOrEmail"));
        assertTrue("The errors collection should be empty", impl.getFieldErrors().isEmpty());
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate3() {
        impl.setHandle("handle");
        impl.setEmail("email@topcoder.com");

        UserDAO userDAO = EasyMock.createNiceMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        EasyMock.expect(userDAO.find("handle", false)).andReturn(null);
        EasyMock.expect(userDAO.find("email@topcoder.com")).andReturn(null);

        EasyMock.replay(userDAO);

        impl.validate();

        assertTrue("The errors collection should be empty", impl.getActionErrors().isEmpty());
        assertEquals("The errors collection's size should be same as", 2, impl.getFieldErrors().size());
        assertEquals("The errors collection should contain expect string", "handle.invalid", impl
                        .getFieldErrors().get("handle").get(0));
        assertEquals("The errors collection should contain expect string", "email.invalid", impl
                        .getFieldErrors().get("email").get(0));

        EasyMock.verify(userDAO);
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate4() {
        impl.setHandle("handle");
        impl.setEmail("email@topcoder.com");

        UserDAO userDAO = EasyMock.createNiceMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        EasyMock.expect(userDAO.find("handle", false)).andReturn(new User());

        Constants.MAX_EMAIL_LENGTH = 3;

        EasyMock.replay(userDAO);

        impl.validate();

        assertTrue("The errors collection should be empty", impl.getActionErrors().isEmpty());
        assertEquals("The errors collection's size should be same as", 1, impl.getFieldErrors().size());
        assertEquals("The errors collection should contain expect string", "email.invalid", impl
                        .getFieldErrors().get("email").get(0));

        EasyMock.verify(userDAO);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getHandle()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetHandle() {
        assertNull("The initial value should be null", impl.getHandle());

        String expect = "tests";

        impl.setHandle(expect);

        assertEquals("The return value should be same as ", expect, impl.getHandle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setHandle(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetHandle() {
        String expect = "tests";

        impl.setHandle(expect);

        assertEquals("The return value should be same as ", expect, impl.getHandle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEmail()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetEmail() {
        assertNull("The initial value should be null", impl.getEmail());

        String expect = "tests";

        impl.setEmail(expect);

        assertEquals("The return value should be same as ", expect, impl.getEmail());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEmail(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetEmail() {
        String expect = "tests";

        impl.setEmail(expect);

        assertEquals("The return value should be same as ", expect, impl.getEmail());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPasswordRecoveryExpiration(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetPasswordRecoveryExpiration() throws Exception {
        assertEquals("The initial value should be same as", 0, TestHelper.getField(
            RecoverPasswordAction.class, impl, "passwordRecoveryExpiration"));

        int expect = 14;

        impl.setPasswordRecoveryExpiration(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            RecoverPasswordAction.class, impl, "passwordRecoveryExpiration"));
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

        impl.setEmailBodyTemplateFilePath("test_files/mailBodyTemplate.txt");
        impl.setEmailFromAddress("emailFromAddress");
        impl.setEmailSubject("emailSubject");
        impl.setResetPasswordLinkTemplate("'%passwordRecoveryId%' and '%hashCode%'");

        impl.setPasswordRecoveryExpiration(5);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The passwordRecoveryExpiration is negative.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_PasswordRecoveryExpirationNegative() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

        impl.setEmailBodyTemplateFilePath("test_files/mailBodyTemplate.txt");
        impl.setEmailFromAddress("emailFromAddress");
        impl.setEmailSubject("emailSubject");
        impl.setResetPasswordLinkTemplate("'%passwordRecoveryId%' and '%hashCode%'");

        impl.setPasswordRecoveryExpiration(-1);

        impl.checkConfiguration();
    }
}
