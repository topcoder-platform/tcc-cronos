/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.PasswordRecoveryDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.PasswordRecovery;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.TestHelper;

/**
 * <p>
 * Unit tests for class <code>BasePasswordRecoveryAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BasePasswordRecoveryActionTest {
    /**
     * <p>
     * Represents the <code>BasePasswordRecoveryAction</code> instance used to test against.
     * </p>
     */
    private BasePasswordRecoveryAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BasePasswordRecoveryActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockBasePasswordRecoveryAction();
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
     * Inheritance test, verifies <code>BasePasswordRecoveryAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BasePasswordAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BasePasswordRecoveryAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>sendPasswordRecoveryEmail(PasswordRecovery)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSendPasswordRecoveryEmail() throws Exception {
        impl.setEmailBodyTemplateFilePath("test_files" + File.separator + "mailBodyTemplate.txt");
        impl.setEmailFromAddress("payment@topcoder.com");
        impl.setEmailSubject("SubjectPayment");
        impl.setResetPasswordLinkTemplate("Id:%passwordRecoveryId%, hashCode:%hashCode%");

        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setId(new Long(123));
        passwordRecovery.setExpireDate(new Date());
        passwordRecovery.setNew(true);
        passwordRecovery.setRecoveryAddress("recoveryAddress@topcoder.com");
        passwordRecovery.setUsed(false);
        User user = new User();
        user.setHandle("handle1");
        passwordRecovery.setUser(user);

        impl.sendPasswordRecoveryEmail(passwordRecovery);
    }

    /**
     * <p>
     * Failure test for the method <code>sendPasswordRecoveryEmail(PasswordRecovery)</code>.<br>
     * The passwordRecovery is not null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSendPasswordRecoveryEmail_PasswordRecoveryNull() throws Exception {
        impl.sendPasswordRecoveryEmail(null);
    }

    /**
     * <p>
     * Failure test for the method <code>sendPasswordRecoveryEmail(PasswordRecovery)</code>.<br>
     * The mailBodyTemplate is invalid.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testSendPasswordRecoveryEmail_MailBodyTemplateInvalid() throws Exception {
        impl.setEmailBodyTemplateFilePath("test_files" + File.separator + "mailBodyTemplateError.txt");
        impl.setEmailFromAddress("payment@topcoder.com");
        impl.setEmailSubject("SubjectPayment");
        impl.setResetPasswordLinkTemplate("Id:%passwordRecoveryId%, hashCode:%hashCode%");

        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setId(new Long(123));
        passwordRecovery.setExpireDate(new Date());
        passwordRecovery.setNew(true);
        passwordRecovery.setRecoveryAddress("recoveryAddress@topcoder.com");
        passwordRecovery.setUsed(false);
        User user = new User();
        user.setHandle("handle1");
        passwordRecovery.setUser(user);

        impl.sendPasswordRecoveryEmail(passwordRecovery);
    }

    /**
     * <p>
     * Failure test for the method <code>sendPasswordRecoveryEmail(PasswordRecovery)</code>.<br>
     * The mailBodyTemplate is not exist.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testSendPasswordRecoveryEmail_MailBodyTemplateNotExist() throws Exception {
        impl.setEmailBodyTemplateFilePath("test_files" + File.separator + "not_exist.txt");
        impl.setEmailFromAddress("payment@topcoder.com");
        impl.setEmailSubject("SubjectPayment");
        impl.setResetPasswordLinkTemplate("Id:%passwordRecoveryId%, hashCode:%hashCode%");

        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setId(new Long(123));
        passwordRecovery.setExpireDate(new Date());
        passwordRecovery.setNew(true);
        passwordRecovery.setRecoveryAddress("recoveryAddress@topcoder.com");
        passwordRecovery.setUsed(false);
        User user = new User();
        user.setHandle("handle1");
        passwordRecovery.setUser(user);

        impl.sendPasswordRecoveryEmail(passwordRecovery);
    }

    /**
     * <p>
     * Failure test for the method <code>sendPasswordRecoveryEmail(PasswordRecovery)</code>.<br>
     * The address is null.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testSendPasswordRecoveryEmail_AddressNull() throws Exception {
        impl.setEmailBodyTemplateFilePath("test_files" + File.separator + "mailBodyTemplate.txt");
        impl.setEmailFromAddress("payment@topcoder.com");
        impl.setEmailSubject("SubjectPayment");
        impl.setResetPasswordLinkTemplate("Id:%passwordRecoveryId%, hashCode:%hashCode%");

        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setId(new Long(123));
        passwordRecovery.setExpireDate(new Date());
        passwordRecovery.setNew(true);
        passwordRecovery.setRecoveryAddress(null);
        passwordRecovery.setUsed(false);
        User user = new User();
        user.setHandle("handle1");
        passwordRecovery.setUser(user);

        impl.sendPasswordRecoveryEmail(passwordRecovery);
    }

    /**
     * <p>
     * Failure test for the method <code>sendPasswordRecoveryEmail(PasswordRecovery)</code>.<br>
     * The address is invalid.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testSendPasswordRecoveryEmail_AddressInvalid() throws Exception {
        impl.setEmailBodyTemplateFilePath("test_files" + File.separator + "mailBodyTemplate.txt");
        impl.setEmailFromAddress("payment@topcoder.com");
        impl.setEmailSubject("SubjectPayment");
        impl.setResetPasswordLinkTemplate("Id:%passwordRecoveryId%, hashCode:%hashCode%");

        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setId(new Long(123));
        passwordRecovery.setExpireDate(new Date());
        passwordRecovery.setNew(true);
        passwordRecovery.setRecoveryAddress("error");
        passwordRecovery.setUsed(false);
        User user = new User();
        user.setHandle("handle1");
        passwordRecovery.setUser(user);

        impl.sendPasswordRecoveryEmail(passwordRecovery);
    }

    /**
     * <p>
     * Failure test for the method <code>sendPasswordRecoveryEmail(PasswordRecovery)</code>.<br>
     * The address is invalid.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testSendPasswordRecoveryEmail_AddressInvalid2() throws Exception {
        impl.setEmailBodyTemplateFilePath("test_files" + File.separator + "mailBodyTemplate.txt");
        impl.setEmailFromAddress("payment@topcoder.com");
        impl.setEmailSubject("SubjectPayment");
        impl.setResetPasswordLinkTemplate("Id:%passwordRecoveryId%, hashCode:%hashCode%");

        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setId(new Long(123));
        passwordRecovery.setExpireDate(new Date());
        passwordRecovery.setNew(true);
        passwordRecovery.setRecoveryAddress("@[");
        passwordRecovery.setUsed(false);
        User user = new User();
        user.setHandle("handle1");
        passwordRecovery.setUser(user);

        impl.sendPasswordRecoveryEmail(passwordRecovery);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEmailSubject(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetEmailSubject() throws Exception {
        assertNull("The initial value should be null", TestHelper.getField(BasePasswordRecoveryAction.class,
            impl, "emailSubject"));

        String expect = "test";

        impl.setEmailSubject(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            BasePasswordRecoveryAction.class, impl, "emailSubject"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEmailBodyTemplateFilePath(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetEmailBodyTemplateFilePath() throws Exception {
        assertNull("The initial value should be null", TestHelper.getField(BasePasswordRecoveryAction.class,
            impl, "emailBodyTemplateFilePath"));

        String expect = "test";

        impl.setEmailBodyTemplateFilePath(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            BasePasswordRecoveryAction.class, impl, "emailBodyTemplateFilePath"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEmailFromAddress(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetEmailFromAddress() throws Exception {
        assertNull("The initial value should be null", TestHelper.getField(BasePasswordRecoveryAction.class,
            impl, "emailFromAddress"));

        String expect = "test";

        impl.setEmailFromAddress(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            BasePasswordRecoveryAction.class, impl, "emailFromAddress"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setResetPasswordLinkTemplate(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetResetPasswordLinkTemplate() throws Exception {
        assertNull("The initial value should be null", TestHelper.getField(BasePasswordRecoveryAction.class,
            impl, "resetPasswordLinkTemplate"));

        String expect = "test";

        impl.setResetPasswordLinkTemplate(expect);

        assertEquals("The return value should be same as ", expect, TestHelper.getField(
            BasePasswordRecoveryAction.class, impl, "resetPasswordLinkTemplate"));
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

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The emailFromAddress is not set.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_EmailFromAddressNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

        impl.setEmailBodyTemplateFilePath("test_files/mailBodyTemplate.txt");
        impl.setEmailSubject("emailSubject");
        impl.setResetPasswordLinkTemplate("'%passwordRecoveryId%' and '%hashCode%'");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The emailSubject is empty.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_EmailSubjectEmpty() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

        impl.setEmailBodyTemplateFilePath("test_files/mailBodyTemplate.txt");
        impl.setEmailFromAddress("emailFromAddress");
        impl.setEmailSubject("");
        impl.setResetPasswordLinkTemplate("'%passwordRecoveryId%' and '%hashCode%'");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The emailBodyTemplateFilePath is not set.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_EmailBodyTemplateFilePathNotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

        impl.setEmailFromAddress("emailFromAddress");
        impl.setEmailSubject("emailSubject");
        impl.setResetPasswordLinkTemplate("'%passwordRecoveryId%' and '%hashCode%'");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The emailBodyTemplateFilePath is not exist.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_EmailBodyTemplateFilePathNotExist() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

        impl.setEmailBodyTemplateFilePath("wrong");
        impl.setEmailFromAddress("emailFromAddress");
        impl.setEmailSubject("emailSubject");
        impl.setResetPasswordLinkTemplate("'%passwordRecoveryId%' and '%hashCode%'");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The emailBodyTemplateFilePath does not contains '%handle%'.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_EmailBodyTemplateFilePathNotContainHandle() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

        impl.setEmailBodyTemplateFilePath("test_files/mailBodyTemplateError1.txt");
        impl.setEmailFromAddress("emailFromAddress");
        impl.setEmailSubject("emailSubject");
        impl.setResetPasswordLinkTemplate("'%passwordRecoveryId%' and '%hashCode%'");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The emailBodyTemplateFilePath does not contains '%link%'.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_EmailBodyTemplateFilePathNotContainLink() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createMock(PasswordRecoveryDAO.class);
        impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

        impl.setEmailBodyTemplateFilePath("test_files/mailBodyTemplateError2.txt");
        impl.setEmailFromAddress("emailFromAddress");
        impl.setEmailSubject("emailSubject");
        impl.setResetPasswordLinkTemplate("'%passwordRecoveryId%' and '%hashCode%'");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The resetPasswordLinkTemplate is empty.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_ResetPasswordLinkTemplateEmpty() throws Exception {
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
        impl.setResetPasswordLinkTemplate("  ");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The resetPasswordLinkTemplate does not contain '%passwordRecoveryId%'.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_ResetPasswordLinkTemplateNotContainPasswordRecoveryId()
        throws Exception {
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
        impl.setResetPasswordLinkTemplate("'%hashCode%'");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The resetPasswordLinkTemplate does not contain '%hashCode%'.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_ResetPasswordLinkTemplateNotContainHashCode() throws Exception {
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
        impl.setResetPasswordLinkTemplate("'%passwordRecoveryId%'");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Mock class for <code>BasePasswordRecoveryAction</code>.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    class MockBasePasswordRecoveryAction extends BasePasswordRecoveryAction {
        /**
         * <p>
         * This is the default constructor for the class.
         * </p>
         */
        MockBasePasswordRecoveryAction() {
            // Empty
        }
    }

}
