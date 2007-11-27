/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.user.UserManagerFactory;

/**
 * <p>
 * Accuracy Unit test cases for UserManagerFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 * @author Chenhong
 * @version 3.2.1
 */
public class UserManagerFactoryAccuracyTests extends TestCase {

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE_ADD);
        AccuracyTestHelper.setUpDataBase();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserManagerFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserManager() for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetUserManager() throws Exception {
        assertNotNull("Failed to get user manager.", UserManagerFactory.getUserManager());
    }

    /**
     * Test UserStatusManager getUserStatusManager().
     * @throws Exception to junit.
     */
    public void testGetUserStatusManager() throws Exception {
        assertNotNull(UserManagerFactory.getUserStatusManager());
    }

    /**
     * Test UserTypeManager getUserTypeManager().
     * @throws Exception to junit.
     */
    public void testGetUserTypeManager() throws Exception {
        assertNotNull(UserManagerFactory.getUserTypeManager());
    }
}