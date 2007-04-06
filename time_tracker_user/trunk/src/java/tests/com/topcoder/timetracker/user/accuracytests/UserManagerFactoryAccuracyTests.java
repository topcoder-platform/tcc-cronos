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
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
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

}