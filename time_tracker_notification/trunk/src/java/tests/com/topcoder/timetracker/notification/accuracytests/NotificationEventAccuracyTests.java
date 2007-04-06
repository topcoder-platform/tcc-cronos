/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceFactory;
import com.topcoder.timetracker.notification.send.NotificationEvent;
import com.topcoder.util.scheduler.scheduling.ScheduledEnable;

/**
 * <p>
 * Accuracy Unit test cases for NotificationEvent.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class NotificationEventAccuracyTests extends TestCase {
    /**
     * <p>NotificationEvent instance for testing.</p>
     */
    private NotificationEvent instance;

    /**
     * <p>Setup test environment.</p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        instance = new NotificationEvent();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.clearConfig();
        instance = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(NotificationEventAccuracyTests.class);
    }

    /**
     * <p>Tests ctor NotificationEvent#NotificationEvent() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create NotificationEvent instance.", instance);
    }

    /**
     * <p>Tests NotificationEvent#getRunningStatus() for accuracy.</p>
     */
    public void testGetRunningStatus() {
        assertEquals("Failed to get the running status.", ScheduledEnable.NOT_STARTED, instance.getRunningStatus());
    }

    /**
     * <p>Tests NotificationEvent#getMessageData() for accuracy.</p>
     */
    public void testGetMessageData() {
        assertNull("Expects null message data.", instance.getMessageData());
    }

    /**
     * <p>Tests NotificationEvent#run() for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testRun() throws Exception {
        Notification notification = AccuracyTestHelper.createNotification(300);
        NotificationPersistenceFactory.getNotificationPersistence().createNotification(notification, true);

        instance.setJobName("TT_NOTE_" + notification.getId());

        instance.run();

        assertEquals("Failed to get the running status.", ScheduledEnable.SUCCESSFUL, instance.getRunningStatus());
    }

    /**
     * <p>Tests NotificationEvent#close() for accuracy.</p>
     */
    public void testClose() {
        instance.close();
    }

    /**
     * <p>Tests NotificationEvent#isDone() for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testIsDone() throws Exception {
        assertFalse("The job should not be started.", instance.isDone());

        Notification notification = AccuracyTestHelper.createNotification(300);
        NotificationPersistenceFactory.getNotificationPersistence().createNotification(notification, true);

        instance.setJobName("TT_NOTE_" + notification.getId());
        instance.run();

        assertTrue("The job should have been finished.", instance.isDone());
    }

    /**
     * <p>Tests NotificationEvent#getStatus() for accuracy.</p>
     */
    public void testGetStatus() {
        assertEquals("Failed to get the running status.", ScheduledEnable.NOT_STARTED, instance.getStatus());
    }

    /**
     * <p>Tests NotificationEvent#getJobName() for accuracy.</p>
     */
    public void testGetJobName() {
        assertNull("Expects null job name.", instance.getJobName());
    }

    /**
     * <p>Tests NotificationEvent#setJobName(String) for accuracy.</p>
     */
    public void testSetJobName() {
        instance.setJobName("TT_NOTIFICATION_3000");

        assertEquals("Failed to set the job name.", "TT_NOTIFICATION_3000", instance.getJobName());
    }

}