/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.NotificationFilterFactory;
import com.topcoder.timetracker.notification.NotificationPersistence;
import com.topcoder.timetracker.notification.NotificationPersistenceException;

/**
 * <p>
 * Mock implementation for NotificationPersistence interface.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class FailureNotificationPersistence implements NotificationPersistence {

    /**
     * <p>
     * Represents the flag whether calling method will throw Exception. for testing purpose.
     * </p>
     */
    private boolean throwException = false;

    /**
     * <p>
     * persistence constructor.
     * </p>
     * @param throwException
     */
    public FailureNotificationPersistence(boolean throwException) {
        this.throwException = throwException;
    }

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public FailureNotificationPersistence() {
    }

    /**
     * <p>
     * mock method.
     * </p>
     */
    public void createNotification(Notification arg0, boolean arg1) throws NotificationPersistenceException {
        if (throwException) {
            throw new NotificationPersistenceException("mock");
        }
    }

    /**
     * <p>
     * mock method.
     * </p>
     */
    public void deleteNotification(long arg0, boolean arg1) throws NotificationPersistenceException {
        if (throwException) {
            throw new NotificationPersistenceException("mock");
        }
    }

    /**
     * <p>
     * mock method.
     * </p>
     */
    public Notification[] getAllNotifications() throws NotificationPersistenceException {
        if (throwException) {
            throw new NotificationPersistenceException("mock");
        }
        return null;
    }

    /**
     * <p>
     * mock method.
     * </p>
     */
    public Notification getNotification(long arg0) throws NotificationPersistenceException {
        if (throwException) {
            throw new NotificationPersistenceException("mock");
        }
        return null;
    }

    /**
     * <p>
     * mock method.
     * </p>
     */
    public NotificationFilterFactory getNotificationFilterFactory() {
        return null;
    }

    /**
     * <p>
     * mock method.
     * </p>
     */
    public Notification[] searchNotifications(Filter arg0) throws NotificationPersistenceException {
        if (throwException) {
            throw new NotificationPersistenceException("mock");
        }
        return null;
    }

    /**
     * <p>
     * mock method.
     * </p>
     */
    public void updateNotification(Notification arg0, boolean arg1) throws NotificationPersistenceException {
        if (throwException) {
            throw new NotificationPersistenceException("mock");
        }
    }

}
