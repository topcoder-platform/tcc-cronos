/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.topcoder.security.authenticationfactory.ConfigurationException;
import com.topcoder.timetracker.user.UserAuthenticator;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure test for <code>{@link UserAuthenticator}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class UserAuthenticatorFailureTests extends TestCase {

    /**
     * <p>
     * Represents the UserAuthenticator instance for testing.
     * </p>
     */
    private UserAuthenticator userAuthenticator;

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
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserAuthenticator.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");

        userAuthenticator = new UserAuthenticator("com.topcoder.timetracker.user.UserAuthenticator");
    }

    /**
     * <p>
     * Teardown the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        FailureTestHelper.clearNamespaces();
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_NullNamespace() throws Exception {
        try {
            new UserAuthenticator(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_EmptyNamespace() throws Exception {
        try {
            new UserAuthenticator("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_TrimmedEmptyNamespace() throws Exception {
        try {
            new UserAuthenticator("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_UnknownNamespace() throws Exception {
        try {
            new UserAuthenticator("NamespaceNotFound");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_MissingObjectFactoryNamespace() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserAuthenticator1.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");

        try {
            new UserAuthenticator("com.topcoder.timetracker.user.UserAuthenticator");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_EmptyObjectFactoryNamespace() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserAuthenticator2.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");

        try {
            new UserAuthenticator("com.topcoder.timetracker.user.UserAuthenticator");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_InvalidObjectFactoryNamespace() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserAuthenticator3.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");

        try {
            new UserAuthenticator("com.topcoder.timetracker.user.UserAuthenticator");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_MissingUserDAO() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserAuthenticator4.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");

        try {
            new UserAuthenticator("com.topcoder.timetracker.user.UserAuthenticator");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_MissingClassName() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserAuthenticator5.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");

        try {
            new UserAuthenticator("com.topcoder.timetracker.user.UserAuthenticator");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_EmptyClassName() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserAuthenticator6.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");

        try {
            new UserAuthenticator("com.topcoder.timetracker.user.UserAuthenticator");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserAuthenticator#UserAuthenticator(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserAuthenticator_InvalidClasName() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "UserAuthenticator7.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");

        try {
            new UserAuthenticator("com.topcoder.timetracker.user.UserAuthenticator");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }
}
