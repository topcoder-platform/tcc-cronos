/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.NotificationManager;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceFactory;

/**
 * <p>
 * Accuracy Unit test cases for NotificationManager.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class NotificationManagerAccuracyTests extends TestCase {
    /**
     * <p>NotificationManager instance for testing.</p>
     */
    private NotificationManager instance;

    /**
     * <p>Setup test environment.</p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        instance = new NotificationManager();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
        instance = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(NotificationManagerAccuracyTests.class);
    }

    /**
     * <p>Tests ctor NotificationManager#NotificationManager() for accuracy.</p>
     */
    public void testCtor() {
        assertNotNull("Failed to create NotificationManager instance.", instance);
    }

    /**
     * <p>Tests NotificationManager#sendNotification(long) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testSendNotification() throws Exception {
        Notification notification = AccuracyTestHelper.createNotification(300);
        NotificationPersistenceFactory.getNotificationPersistence().createNotification(notification, true);

        instance.sendNotification(notification.getId());
    }

    /**
     * <p>Tests NotificationManager#getNotification(long) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testGetNotification() throws Exception {
        assertNull("Expects null notification if the id is unknown.", instance.getNotification(300));
    }

    /**
     * <p>Tests NotificationManager#createNotification(Notification,boolean) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateNotification() throws Exception {
        Notification notification = AccuracyTestHelper.createNotification(300);
        instance.createNotification(notification, true);

        AccuracyTestHelper.assertNotifications(notification, instance.getNotification(notification.getId()));
    }

    /**
     * <p>Tests NotificationManager#updateNotification(Notification,boolean) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNotification() throws Exception {
        Notification notification = AccuracyTestHelper.createNotification(300);
        instance.createNotification(notification, true);

        notification.setActive(false);
        notification.setFromAddress("jim@topcoder.com");
        notification.setSubject("Gift!");

        instance.updateNotification(notification, true);

        AccuracyTestHelper.assertNotifications(notification, instance.getNotification(notification.getId()));
    }

    /**
     * <p>Tests NotificationManager#deleteNotification(long,boolean) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteNotification() throws Exception {
        // there should no exception if the id cannot be found
        instance.deleteNotification(300, true);

        Notification notification = AccuracyTestHelper.createNotification(300);
        instance.createNotification(notification, true);

        instance.deleteNotification(notification.getId(), true);

        assertNull("Failed to remove the notification.", instance.getNotification(notification.getId()));
    }

    /**
     * <p>Tests NotificationManager#getAllNotifications() for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllNotifications() throws Exception {
        assertEquals("There should be not notifications in the database.", 0, instance.getAllNotifications().length);
    }

    /**
     * <p>Tests NotificationManager#searchNotifications(Filter) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotifications() throws Exception {
        Notification notification = AccuracyTestHelper.createNotification(300);
        instance.createNotification(notification, true);

        Notification[] notifications = instance.searchNotifications(
            instance.getNotificationFilterFactory().createCompanyIdFilter(300));
        assertEquals("Failed to search the notifications.", 1, notifications.length);
        assertEquals("Failed to search the notifications.", notification, notifications[0]);
    }

    /**
     * <p>Tests NotificationManager#getNotificationFilterFactory() for accuracy.</p>
     */
    public void testGetNotificationFilterFactory() {
        assertNotNull("Failed to initialize the notification filter factory.", instance.getNotificationFilterFactory());
    }
}