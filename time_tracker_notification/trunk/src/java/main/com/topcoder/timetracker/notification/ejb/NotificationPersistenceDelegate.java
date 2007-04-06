/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.ejb;

import com.topcoder.naming.jndiutility.JNDIUtils;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.notification.Helper;
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.NotificationFilterFactory;
import com.topcoder.timetracker.notification.NotificationPersistence;
import com.topcoder.timetracker.notification.NotificationPersistenceException;

import com.topcoder.util.config.ConfigManagerException;

import javax.ejb.CreateException;

import javax.naming.NamingException;

import javax.rmi.PortableRemoteObject;


/**
 * <p>
 * This is a Business Delegate that may be used within a J2EE application.  It is responsible for looking up the local
 * interface of the SessionBean for NotificationPersistence, and delegating any calls to the bean.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public class NotificationPersistenceDelegate implements NotificationPersistence {
    /**
     * <p>
     * Represents key to retrieve location.
     * </p>
     */
    private static final String LOCATION_KEY = "location";

    /**
     * <p>
     * Represents key to retrieve context name.
     * </p>
     */
    private static final String CONTEXT_NAME_KEY = "context_name";

    /**
     * <p>
     * This is the local interface for the NotificationPersistence business services.  All business calls are delegated
     * here.
     * </p>
     *
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     *
     * <p>
     * Accessed In: Not Accessed
     * </p>
     *
     * <p>
     * Modified In: Not modified after initialization
     * </p>
     *
     * <p>
     * Utilized In: All NotificationPersistence methods
     * </p>
     *
     * <p>
     * Valid Values: Non-null NotificationPersistenceLocal
     * </p>
     */
    private final NotificationPersistence local;

    /**
     * <p>
     * Default constructor.  This will delegate to the namespace constructor with the namespace equal to the
     * fully-qualified class name of this class.
     * </p>
     *
     * @throws NotificationConfigurationException if a problem occurs during initialization..
     */
    public NotificationPersistenceDelegate() throws NotificationConfigurationException {
        this(NotificationPersistenceDelegate.class.getName());
    }

    /**
     * <p>
     * Constructor that allows a NotificationPersistenceDelegate from a given namespace.  It will use the given
     * configuration values to retrieve a NotificationPersistenceLocal instance to delegate the calls to.
     * </p>
     *
     * <p>
     * Implementation Notes: - Retrieve the context using contextName (if specified) from JNDIUtils. - Retrieve the
     * object using JNDIUtils.getObject(context, name) with the given name (if a context was specified) or just use
     * the default context and do JNDIUtils.getObject(name) if no context was specified.
     * </p>
     *
     * @param namespace The configuration namespace to use.
     *
     * @throws NotificationConfigurationException if a problem occurs during initialization.
     * @throws IllegalArgumentException if namespace is null or an empty String.
     */
    public NotificationPersistenceDelegate(String namespace)
        throws NotificationConfigurationException {
        Helper.checkString(namespace, "namespace");

        String location = Helper.getConfigString(namespace, LOCATION_KEY, true);
        String contextName = Helper.getConfigString(namespace, CONTEXT_NAME_KEY, false);

        try {
            Object ejbObject = null;

            if (contextName == null) {
                ejbObject = JNDIUtils.getObject(location);
            } else {
                ejbObject = JNDIUtils.getObject(JNDIUtils.getContext(contextName),
                        location);
            }

            NotificationPersistenceLocalHome home = (NotificationPersistenceLocalHome) PortableRemoteObject
                                                    .narrow(ejbObject, NotificationPersistenceLocalHome.class);

            this.local = home.create();
        } catch (NamingException ne) {
            throw new NotificationConfigurationException("Name exception.", ne);
        } catch (ConfigManagerException cme) {
            throw new NotificationConfigurationException("Error relating to configuration manager.", cme);
        } catch (CreateException ce) {
            throw new NotificationConfigurationException("Error creating the object.", ce);
        }
    }

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
        throws NotificationPersistenceException {
        Helper.checkPositive(notificationId, "notificationId");
        return local.getNotification(notificationId);
    }

    /**
     * Create the notification.
     *
     * @param notification the notification to create
     * @param audit the audit flag
     *
     * @throws NotificationPersistenceException if any error occurred
     * @throws IllegalArgumentExceptin if argument is null
     */
    public void createNotification(Notification notification, boolean audit)
        throws NotificationPersistenceException {
        Helper.checkNull(notification, "notification");
        local.createNotification(notification, audit);
    }

    /**
     * Update the notification If audit is true, the audit functionality is enabled.
     *
     * @param notification the notification to update
     * @param audit the audit flag
     *
     * @throws NotificationPersistenceException if any error occurred
     * @throws IllegalArgumentExceptin if argument is null
     */
    public void updateNotification(Notification notification, boolean audit)
        throws NotificationPersistenceException {
        Helper.checkNull(notification, "notification");
        local.updateNotification(notification, audit);
    }

    /**
     * Delete the notification by id Implementation Notes: - Delegate to the local interface.
     *
     * @param notificationId the notification id
     * @param audit the audit flag
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public void deleteNotification(long notificationId, boolean audit)
        throws NotificationPersistenceException {
        Helper.checkPositive(notificationId, "notificationId");
        local.deleteNotification(notificationId, audit);
    }

    /**
     * Get all the notifications in the database. Implementation Notes: - Delegate to the local interface.
     *
     * @return all notification instances
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification[] getAllNotifications() throws NotificationPersistenceException {
        return local.getAllNotifications();
    }

    /**
     * Search the matching notifications against the given filter. If none found, return empty array.
     *
     * @param filter the search filter
     *
     * @return all the matching notification instances
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification[] searchNotifications(Filter filter)
        throws NotificationPersistenceException {
        Helper.checkNull(filter, "filter");
        return local.searchNotifications(filter);
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
        return local.getNotificationFilterFactory();
    }
}
