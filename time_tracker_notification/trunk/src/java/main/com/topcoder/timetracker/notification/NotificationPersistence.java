/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines the contract of managing the Notification entity in some persistence. It provides the
 * persistence functionalities to create, update and search Notification instances.
 * </p>
 *
 * <p>
 * The implementation is required to be thread safe.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public interface NotificationPersistence {
    /**
     * Get the notification by notification id.
     *
     * @param notificationId the notification id
     *
     * @return notification with given id
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification getNotification(long notificationId)
        throws NotificationPersistenceException;

    /**
     * Create the notification. If audit is true, the audit functionality is enabled.
     *
     * @param notification the notification to create
     * @param audit the audit flag
     *
     * @throws NotificationPersistenceException if any error occurred
     * @throws IllegalArgumentExceptin if argument is null
     */
    public void createNotification(Notification notification, boolean audit)
        throws NotificationPersistenceException;

    /**
     * Update the notification. If audit is true, the audit functionality is enabled.
     *
     * @param notification the notification to update
     * @param audit the audit flag
     *
     * @throws NotificationPersistenceException if any error occurred
     * @throws IllegalArgumentExceptin if argument is null
     */
    public void updateNotification(Notification notification, boolean audit)
        throws NotificationPersistenceException;

    /**
     * Delete the notification by id. If audit is true, the audit functionality is enabled.
     *
     * @param notificationId the notification id
     * @param audit the audit flag
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public void deleteNotification(long notificationId, boolean audit)
        throws NotificationPersistenceException;

    /**
     * Get all the notifications in the database.
     *
     * @return all notification instances
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification[] getAllNotifications() throws NotificationPersistenceException;

    /**
     * Search the matching notifications against the given filter. If none found, return empty array. The filters may
     * be produced using the NotificationFilterFactory.
     *
     * @param filter the search filter
     *
     * @return all the matching notification instances
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification[] searchNotifications(Filter filter)
        throws NotificationPersistenceException;

    /**
     * <p>
     * Retrieves the NotificationFilterFactory that is capable of creating SearchFilters to use when searching for
     * Notifications.  This is used to conveniently specify the search criteria that should be used.  The filters
     * returned by the given factory should be used with this instance's searchNotifications method.
     * </p>
     *
     * @return the NotificationFilterFactory that is capable of creating SearchFilters to use when searching for Time
     *         Tracker notifications.
     */
    public NotificationFilterFactory getNotificationFilterFactory();
}
