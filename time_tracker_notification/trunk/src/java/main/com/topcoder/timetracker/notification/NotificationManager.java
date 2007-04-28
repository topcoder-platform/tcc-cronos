/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.objectfactory.ObjectFactory;


/**
 * <p>
 * NotificationManager groups all the methods of NotificationPersistence and SendNotification interfaces for easy
 * access.
 * </p>
 *
 * <p>
 * Application users can get, create, update, delete and search the notification in the persistence, as well as send
 * the notification manually.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b>This class is thread safe since it¡¯s immutable.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public class NotificationManager {
    /**
     * <p>
     * Represents the key to get the object factory name from.
     * </p>
     */
    private static final String OF_NAMESPACE = "Of_namespace";

    /**
     * <p>
     * Represents the persistence key used to create the persistence object.
     * </p>
     */
    private static final String PERSISTENCE_KEY = "persistence_key";

    /**
     * <p>
     * Represents the sender key used to create the sender object.
     * </p>
     */
    private static final String SEND_NOTIFICATION_KEY = "send_notification_key";

    /**
     * <p>
     * The NotificationPersistence is used to create, update, delete, and search the Notification in some storage.
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and immutable after set
     * </p>
     */
    private final NotificationPersistence persistence;

    /**
     * <p>
     * The sendNotification is used to send the notification by the id.
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and immutable after set
     * </p>
     */
    private final NotificationSender sendNotification;

    /**
     * Create the instance from default namespace, the default namespace is the fully qualified class name.
     *
     * @throws NotificationConfigurationException if any error occurred
     */
    public NotificationManager() throws NotificationConfigurationException {
        this(NotificationManager.class.getName());
    }

    /**
     * Create the instance from namespace.
     *
     * @param namespace the namespace to create the instance
     *
     * @throws IllegalArgumentExceptin if argument is null or string argument is empty
     * @throws NotificationConfigurationException if any error occurred
     */
    public NotificationManager(String namespace) throws NotificationConfigurationException {
        Helper.checkString(namespace, "namespace");

        // get the object factory namespace, it's a required value.
        String objectFactoryNamespace = Helper.getConfigString(namespace, OF_NAMESPACE, true);

        // create the object factory
        ObjectFactory of = Helper.getObjectFactory(objectFactoryNamespace);

        try {
            this.persistence = (NotificationPersistence) Helper.createObjectViaObjectFactory(of,
                    Helper.getConfigString(namespace, PERSISTENCE_KEY, true));
            this.sendNotification = (NotificationSender) Helper.createObjectViaObjectFactory(of,
                    Helper.getConfigString(namespace, SEND_NOTIFICATION_KEY, true));
        } catch (ClassCastException cce) {
            throw new NotificationConfigurationException("Error when cast the class.", cce);
        }
    }

    /**
     * Create the instance with arguments.
     *
     * @param persistence the notification persistence
     * @param sn the send notification instance
     *
     * @throws IllegalArgumentExceptin if argument is null
     */
    public NotificationManager(NotificationPersistence persistence, NotificationSender sn) {
        Helper.checkNull(persistence, "persistence");
        Helper.checkNull(sn, "sn");

        this.persistence = persistence;
        this.sendNotification = sn;
    }

    /**
     * Get the non-null notification by notification id.
     *
     * @param notificationId the notification id
     *
     * @return the notification with given id
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification getNotification(long notificationId)
        throws NotificationPersistenceException {
        return this.persistence.getNotification(notificationId);
    }

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
        throws NotificationPersistenceException {
        this.persistence.createNotification(notification, audit);
    }

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
        throws NotificationPersistenceException {
        this.persistence.updateNotification(notification, audit);
    }

    /**
     * Delete the notification by id. If audit is true, the audit functionality is enabled.
     *
     * @param notificationId the notification id
     * @param audit the audit flag
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public void deleteNotification(long notificationId, boolean audit)
        throws NotificationPersistenceException {
        this.persistence.deleteNotification(notificationId, audit);
    }

    /**
     * Get all the notifications in the database.
     *
     * @return all the notification instance
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification[] getAllNotifications() throws NotificationPersistenceException {
        return this.persistence.getAllNotifications();
    }

    /**
     * Search the matching notifications against the given filter. If none found, empty array will be return The
     * filters may be produced using the NotificationFilterFactory.
     *
     * @param filter the search filter
     *
     * @return all the matching notification
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification[] searchNotifications(Filter filter)
        throws NotificationPersistenceException {
        return this.persistence.searchNotifications(filter);
    }

    /**
     * Send the notification with given id.
     *
     * @param notificationId the notification id to get the notification to send
     *
     * @throws NotificationSendingException if any error occurred
     */
    public void sendNotification(long notificationId) throws NotificationSendingException {
        this.sendNotification.send(notificationId, persistence);
    }

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
    public NotificationFilterFactory getNotificationFilterFactory() {
        return this.persistence.getNotificationFilterFactory();
    }
}
