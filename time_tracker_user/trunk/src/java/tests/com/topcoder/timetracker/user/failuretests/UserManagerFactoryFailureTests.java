/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.UserManagerFactory;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link UserManagerFactory}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class UserManagerFactoryFailureTests extends TestCase {
    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.resetUserManagerToNull();
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        FailureTestHelper.clearNamespaces();
        // handle the singleton problem.
        FailureTestHelper.resetUserManagerToNull();
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerFactory#getUserManager()}</code> method.
     * </p>
     */
    public void testGetUserManager_UnknownNamespace() {
        try {
            UserManagerFactory.getUserManager();
            fail("expect throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerFactory#getUserManager()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUserManager1() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserManagerFactory1.xml");
        try {
            UserManagerFactory.getUserManager();
            fail("expect throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerFactory#getUserManager()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUserManager2() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserManagerFactory2.xml");
        try {
            UserManagerFactory.getUserManager();
            fail("expect throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }
}
