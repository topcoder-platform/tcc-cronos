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
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;

/**
 * <p>
 * Unit tests for class <code>BaseUserDAOAwareAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseUserDAOAwareActionTest {
    /**
     * <p>
     * Represents the <code>BaseUserDAOAwareAction</code> instance used to test against.
     * </p>
     */
    private BaseUserDAOAwareAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseUserDAOAwareActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockBaseUserDAOAwareAction();
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
     * Inheritance test, verifies <code>BaseUserDAOAwareAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseUserDAOAwareAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
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

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The userDAO is not set.<br>
     * Expect BasicStrutsActionsConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicStrutsActionsConfigurationException.class)
    public void testCheckConfiguration_UserDAONotSet() throws Exception {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Mock class for <code>BaseUserDAOAwareAction</code>.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    class MockBaseUserDAOAwareAction extends BaseUserDAOAwareAction {
        /**
         * <p>
         * This is the default constructor for the class.
         * </p>
         */
        MockBaseUserDAOAwareAction() {
            // Empty
        }
    }

}
