/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.NotificationManager;
import com.topcoder.timetracker.notification.NotificationPersistence;
import com.topcoder.timetracker.notification.NotificationSender;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link NotificationManager}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class NotificationManagerFailureTests extends TestCase {

    /**
     * <p>
     * Represents the mock implementation of NotificationPersistence
     * </p>
     */
    private NotificationPersistence notificationPersistence = new FailureNotificationPersistence(true);

    /**
     * <p>
     * Represents the NotificationSender instance used for testing.
     * </p>
     */
    private NotificationSender notificationSender = new FailureNotificationSender();

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
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager.xml");

        new NotificationManager();
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
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager()}</code> constructor.
     * </p>
     */
    public void testNotificationManager1_NamespaceNotFound() throws Exception {
        FailureTestHelper.clearNamespaces();

        try {
            new NotificationManager();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager()}</code> constructor.
     * </p>
     */
    public void testNotificationManager1_MissingObjectFactoryNamespace() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager1.xml");
        try {
            new NotificationManager();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager()}</code> constructor.
     * </p>
     */
    public void testNotificationManager1_EmptyObjectFactoryNamespace() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager2.xml");
        try {
            new NotificationManager();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager()}</code> constructor.
     * </p>
     */
    public void testNotificationManager1_ObjectFactoryNamespaceNotFound() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager3.xml");
        try {
            new NotificationManager();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager1_MissingPersistenceKey() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager4.xml");
        try {
            new NotificationManager();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager1_EmptyPersistenceKey() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager5.xml");
        try {
            new NotificationManager();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager1_NotFoundPersistenceKey() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager6.xml");
        try {
            new NotificationManager();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager1_InvalidPersistenceKey() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager7.xml");
        try {
            new NotificationManager();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager2_NullNamespace() throws Exception {
        try {
            new NotificationManager(null);
            fail("expect throw IllegalArgunmentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager2_EmptyNamespace() throws Exception {
        try {
            new NotificationManager("");
            fail("expect throw IllegalArgunmentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager2_TrimmedEmptyNamespace() throws Exception {
        try {
            new NotificationManager(" ");
            fail("expect throw IllegalArgunmentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     */
    public void testNotificationManager2_NamespaceNotFound() throws Exception {
        FailureTestHelper.clearNamespaces();

        try {
            new NotificationManager(NotificationManager.class.getName());
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     */
    public void testNotificationManager2_MissingObjectFactoryNamespace() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager2.xml");
        try {
            new NotificationManager(NotificationManager.class.getName());
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     */
    public void testNotificationManager2_EmptyObjectFactoryNamespace() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager2.xml");
        try {
            new NotificationManager(NotificationManager.class.getName());
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     */
    public void testNotificationManager2_ObjectFactoryNamespaceNotFound() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager3.xml");
        try {
            new NotificationManager(NotificationManager.class.getName());
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager2_MissingPersistenceKey() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager4.xml");
        try {
            new NotificationManager(NotificationManager.class.getName());
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager2_EmptyPersistenceKey() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager5.xml");
        try {
            new NotificationManager(NotificationManager.class.getName());
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager2_NotFoundPersistenceKey() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager6.xml");
        try {
            new NotificationManager(NotificationManager.class.getName());
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationManager#NotificationManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationManager2_InvalidPersistenceKey() throws Exception {
        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "ObjectFactory.xml");
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "NotificationManager7.xml");
        try {
            new NotificationManager(NotificationManager.class.getName());
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link NotificationManager#NotificationManager(NotificationPersistence, NotificationSender)}</code>
     * constructor.
     * </p>
     */
    public void testNotificationManager3_NullNotificationPersisence() {
        try {
            new NotificationManager(null, notificationSender);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link NotificationManager#NotificationManager(NotificationPersistence, NotificationSender)}</code>
     * constructor.
     * </p>
     */
    public void testNotificationManager3_NullNotificationSender() {
        try {
            new NotificationManager(notificationPersistence, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
