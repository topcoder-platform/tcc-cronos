/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.PasswordRecoveryDAOHibernate;

/**
 * <p>
 * Unit tests for class <code>ResendPasswordRecoveryEmailAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResendPasswordRecoveryEmailActionTest {
    /**
     * <p>
     * Represents the <code>ResendPasswordRecoveryEmailAction</code> instance used to test against.
     * </p>
     */
    private ResendPasswordRecoveryEmailAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ResendPasswordRecoveryEmailActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ResendPasswordRecoveryEmailAction();
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
     * Inheritance test, verifies <code>ResendPasswordRecoveryEmailAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BasePasswordRecoveryAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ResendPasswordRecoveryEmailAction()</code>.<br>
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
            PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
            impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");

            impl.setEmailBodyTemplateFilePath("test_files" + File.separator + "mailBodyTemplate.txt");
            impl.setEmailFromAddress("payment@topcoder.com");
            impl.setEmailSubject("SubjectPayment");
            impl.setResetPasswordLinkTemplate("Id:%passwordRecoveryId%, hashCode:%hashCode%");

            impl.setPasswordRecoveryId(1);

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
     * The passwordRecovery is null.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testExecute_PasswordRecoveryNull() throws Exception {
        try {
            PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
            impl.setPasswordRecoveryDAO(passwordRecoveryDAO);

            impl.setPasswordRecoveryId(7);

            HibernateUtils.begin();

            impl.execute();
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
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

        long expect = 7;

        impl.setPasswordRecoveryId(expect);

        assertEquals("The return value should be same as ", expect, impl.getPasswordRecoveryId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPasswordRecoveryId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPasswordRecoveryId() {
        long expect = 7;

        impl.setPasswordRecoveryId(expect);

        assertEquals("The return value should be same as ", expect, impl.getPasswordRecoveryId());
    }

}
