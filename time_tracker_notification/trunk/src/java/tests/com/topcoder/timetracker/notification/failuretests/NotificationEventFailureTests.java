/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.send.NotificationEvent;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link NotificationEvent}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class NotificationEventFailureTests extends TestCase {

    /**
     * <p>
     * Represents the NotificationEvent instance used in tests.
     * </p>
     */
    private NotificationEvent notificationEvent;

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

        notificationEvent = new NotificationEvent();
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationEvent#NotificationEvent()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testNotificationEvent_NotificationConfigurationException() throws Exception {
        FailureTestHelper.clearNamespaces();

        // failure case for NotificationManager is checked in another place.
        try {
            new NotificationEvent();
            fail("expect throw NotificationConfigurationException");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationEvent#setJobName(String)}</code> method.
     * </p>
     */
    public void testSetJobName_NullJobName() {
        try {
            notificationEvent.setJobName(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationEvent#setJobName(String)}</code> method.
     * </p>
     */
    public void testSetJobName_EmptyJobName() {
        try {
            notificationEvent.setJobName("");
            fail("expect throw IllealArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link NotificationEvent#setJobName(String)}</code> method.
     * </p>
     */
    public void testSetJobName_TrimmedEmptyJobName() {
        try {
            notificationEvent.setJobName("");
            fail("expect throw IllealArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
