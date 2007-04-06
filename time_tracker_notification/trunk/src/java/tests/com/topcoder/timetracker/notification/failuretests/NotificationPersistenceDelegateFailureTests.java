/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceDelegate;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link NotificationPersistenceDelegate}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class NotificationPersistenceDelegateFailureTests extends TestCase {
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
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationPersistenceDelegate.xml");

    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationPersistenceDelegate#NotificationPersistenceDelegate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationPersistenceDelegate1_MissingLocation() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationPersistenceDelegate1.xml");
        try {
            new NotificationPersistenceDelegate();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationPersistenceDelegate#NotificationPersistenceDelegate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationPersistenceDelegate1_EmptyLocation() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationPersistenceDelegate2.xml");
        try {
            new NotificationPersistenceDelegate();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationPersistenceDelegate#NotificationPersistenceDelegate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationPersistenceDelegate1_FailedLocation() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationPersistenceDelegate3.xml");
        try {
            new NotificationPersistenceDelegate();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationPersistenceDelegate#NotificationPersistenceDelegate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationPersistenceDelegate2_NullNamespace() throws Exception {
        try {
            new NotificationPersistenceDelegate(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationPersistenceDelegate#NotificationPersistenceDelegate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationPersistenceDelegate2_EmptyNamespace() throws Exception {
        try {
            new NotificationPersistenceDelegate(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationPersistenceDelegate#NotificationPersistenceDelegate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationPersistenceDelegate2_TrimmedEmptyNamespace() throws Exception {
        try {
            new NotificationPersistenceDelegate(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationPersistenceDelegate#NotificationPersistenceDelegate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationPersistenceDelegate2_MissingLocation() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationPersistenceDelegate1.xml");
        try {
            new NotificationPersistenceDelegate(
                "com.topcoder.timetracker.notification.ejb.NotificationPersistenceDelegate");
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationPersistenceDelegate#NotificationPersistenceDelegate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationPersistenceDelegate2_EmptyLocation() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationPersistenceDelegate2.xml");
        try {
            new NotificationPersistenceDelegate(
                "com.topcoder.timetracker.notification.ejb.NotificationPersistenceDelegate");
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationPersistenceDelegate#NotificationPersistenceDelegate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationPersistenceDelegate2_FailedLocation() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationPersistenceDelegate3.xml");
        try {
            new NotificationPersistenceDelegate(
                "com.topcoder.timetracker.notification.ejb.NotificationPersistenceDelegate");
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }
}
