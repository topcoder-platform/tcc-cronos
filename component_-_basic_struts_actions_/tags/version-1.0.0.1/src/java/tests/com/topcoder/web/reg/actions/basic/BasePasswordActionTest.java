/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.PasswordRecoveryDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;

/**
 * <p>
 * Unit tests for class <code>BasePasswordAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BasePasswordActionTest {
    /**
     * <p>
     * Represents the <code>BasePasswordAction</code> instance used to test against.
     * </p>
     */
    private BasePasswordAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BasePasswordActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockBasePasswordAction();
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
     * Inheritance test, verifies <code>BasePasswordAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseUserDAOAwareAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BasePasswordAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPasswordRecoveryDAO()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPasswordRecoveryDAO() {
        assertNull("The initial value should be null", impl.getPasswordRecoveryDAO());

        PasswordRecoveryDAO expect = EasyMock.createMock(PasswordRecoveryDAO.class);

        impl.setPasswordRecoveryDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getPasswordRecoveryDAO());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPasswordRecoveryDAO(PasswordRecoveryDAO)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPasswordRecoveryDAO() {
        PasswordRecoveryDAO expect = EasyMock.createMock(PasswordRecoveryDAO.class);

        impl.setPasswordRecoveryDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getPasswordRecoveryDAO());
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

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The passwordRecoveryDAO is not set.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_PasswordRecoveryDAONotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Mock class for <code>BasePasswordAction</code>.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    class MockBasePasswordAction extends BasePasswordAction {
        /**
         * <p>
         * This is the default constructor for the class.
         * </p>
         */
        MockBasePasswordAction() {
            // Empty
        }
    }

}
