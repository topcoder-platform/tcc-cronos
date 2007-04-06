/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.notification.NotificationPersistence;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceFactory;

/**
 * <p>
 * Accuracy Unit test cases for NotificationPersistenceFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class NotificationPersistenceFactoryAccuracyTests extends TestCase {
    /**
     * <p>NotificationPersistenceFactory instance for testing.</p>
     */
    private NotificationPersistenceFactory instance;

    /**
     * <p>Setup test environment.</p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(NotificationPersistenceFactoryAccuracyTests.class);
    }

    /**
     * <p>Tests NotificationPersistenceFactory#getNotificationPersistence() for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testGetNotificationPersistence() throws Exception {
        NotificationPersistence persistence = NotificationPersistenceFactory.getNotificationPersistence();

        assertNotNull("Failed to get the notification persistence.", persistence);

        assertSame("Same object should be returned by the factory method.", persistence,
            NotificationPersistenceFactory.getNotificationPersistence());
    }
}