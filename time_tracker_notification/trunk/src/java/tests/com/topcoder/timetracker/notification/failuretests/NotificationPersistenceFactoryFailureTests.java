/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceFactory;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link NotificationPersistenceFactory}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class NotificationPersistenceFactoryFailureTests extends TestCase {

    /**
     * <p>
     * Failure test for <code>{@link NotificationPersistenceFactory#getNotificationPersistence()}</code> method.
     * </p>
     */
    public void testGetNotificationPersistence_UnknownNamespace() {
        try {
            NotificationPersistenceFactory.getNotificationPersistence();
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }
}
