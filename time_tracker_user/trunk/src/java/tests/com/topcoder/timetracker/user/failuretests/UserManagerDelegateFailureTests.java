/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.j2ee.UserManagerDelegate;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure test for <code>{@link UserManagerDelegate}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class UserManagerDelegateFailureTests extends TestCase {
    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserManagerDelegate.xml");

    }

    /**
     * <p>
     * tear down the testing environment.
     * </p>
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.clearNamespaces();
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerDelegate#UserManagerDelegate(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserManagerDelegate_NullNamespace() throws Exception {
        try {
            new UserManagerDelegate(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerDelegate#UserManagerDelegate(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserManagerDelegate_EmptyNamespace() throws Exception {
        try {
            new UserManagerDelegate("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerDelegate#UserManagerDelegate(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserManagerDelegate_TrimmedEmptyNamespace() throws Exception {
        try {
            new UserManagerDelegate("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerDelegate#UserManagerDelegate(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserManagerDelegate_InvalidConfig1() throws Exception {
        try {
            new UserManagerDelegate("com.topcoder.timetracker.user.j2ee.UserManagerDelegate1");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerDelegate#UserManagerDelegate(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserManagerDelegate_InvalidConfig2() throws Exception {
        try {
            new UserManagerDelegate("com.topcoder.timetracker.user.j2ee.UserManagerDelegate2");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }
    
    
    /**
     * <p>
     * Failure test for <code>{@link UserManagerDelegate#UserManagerDelegate(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserManagerDelegate_InvalidConfig3() throws Exception {
        try {
            new UserManagerDelegate("com.topcoder.timetracker.user.j2ee.UserManagerDelegate3");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }
}
