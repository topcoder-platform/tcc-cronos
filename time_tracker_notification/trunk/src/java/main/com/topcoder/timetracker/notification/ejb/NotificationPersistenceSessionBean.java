/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.ejb;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.NotificationFilterFactory;
import com.topcoder.timetracker.notification.NotificationPersistence;
import com.topcoder.timetracker.notification.NotificationPersistenceException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business services to manage Notifications within the Time
 * Tracker Application. It contains the same methods as NotificationPersistence, and delegates to an instance of
 * NotificationPersistence.
 * </p>
 *
 * <p>
 * The instance of NotificationPersistence to use is retrieved from the NotificationPersistenceFactory class.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> The NotificationPersistence interface implementations are required to be thread-safe, and so
 * this stateless session bean is thread-safe also.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public class NotificationPersistenceSessionBean implements NotificationPersistence, SessionBean {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -4553510465048551485L;

	/**
     * <p>
     * This is the instance of SessionContext that was provided by the EJB container.  It is stored and made available
     * to subclasses. It is also used when performing a rollback.
     * </p>
     *
     * <p>
     * Initial Value: Null
     * </p>
     *
     * <p>
     * Accessed In: getSessionContext();
     * </p>
     *
     * <p>
     * Modified In: setSessionContext
     * </p>
     *
     * <p>
     * Utilized In: All Business methods of this class.
     * </p>
     *
     * <p>
     * Valid Values: sessionContext objects (possibly null)
     * </p>
     */
    private SessionContext context;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public NotificationPersistenceSessionBean() {
        // empty constructor
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
        try {
            return NotificationPersistenceFactory.getNotificationPersistence().getNotification(notificationId);
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new NotificationPersistenceException("Error configure or get the notification.", e);
        }
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
        try {
            NotificationPersistenceFactory.getNotificationPersistence().createNotification(notification, audit);
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new NotificationPersistenceException("Error configure or create the notification.", e);
        }
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
        try {
            NotificationPersistenceFactory.getNotificationPersistence().updateNotification(notification, audit);
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new NotificationPersistenceException("Error configure or update the notification.", e);
        }
    }

    /**
     * Delete the notification by id.
     *
     * @param notificationId the notification id
     * @param audit the audit flag
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public void deleteNotification(long notificationId, boolean audit)
        throws NotificationPersistenceException {
        try {
            NotificationPersistenceFactory.getNotificationPersistence().deleteNotification(notificationId, audit);
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new NotificationPersistenceException("Error configure or update the notification.", e);
        }
    }

    /**
     * Get all the notifications in the database.
     *
     * @return all notification instances
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification[] getAllNotifications() throws NotificationPersistenceException {
        try {
            return NotificationPersistenceFactory.getNotificationPersistence().getAllNotifications();
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new NotificationPersistenceException("Error configure or update the notification.", e);
        }
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
        try {
            return NotificationPersistenceFactory.getNotificationPersistence().searchNotifications(filter);
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new NotificationPersistenceException("Error configure or update the notification.", e);
        }
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface.
     * </p>
     */
    public void ejbCreate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface.
     * </p>
     */
    public void ejbActivate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface.
     * </p>
     */
    public void ejbRemove() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface.
     * </p>
     */
    public void ejbPassivate() {
        // empty
    }

    /**
     * <p>
     * Sets the SessionContext to use for this session.
     * </p>
     *
     * @param ctx The SessionContext to use.
     */
    public void setSessionContext(SessionContext ctx) {
        this.context = ctx;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the session context if necessary.
     * </p>
     *
     * @return The session context provided by the EJB container.
     */
    protected SessionContext getSessionContext() {
        return context;
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
     *
     * @throws NotificationConfigurationExceptionException any error occurred
     */
    public NotificationFilterFactory getNotificationFilterFactory() {
        try {
            return NotificationPersistenceFactory.getNotificationPersistence().getNotificationFilterFactory();
        } catch (Exception e) {
            context.setRollbackOnly();
        }

        return null;
    }
}
