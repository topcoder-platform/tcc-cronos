/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceDelegate;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceLocal;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceLocalHome;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceSessionBean;

/**
 * <p>
 * Accuracy Unit test cases for NotificationPersistenceSessionBean and NotificationPersistenceDelegate.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class NotificationPersistenceSessionBeanAccuracyTests extends TestCase {
    /**
     * <p>NotificationPersistenceSessionBean instance for testing.</p>
     */
    private NotificationPersistenceSessionBean bean;

    /**
     * <p>NotificationPersistenceDelegate instance for testing.</p>
     */
    private NotificationPersistenceDelegate delegate;

    /**
     * <p>Setup test environment.</p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        bean = new NotificationPersistenceSessionBean();

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("NotificationPersistenceLocalHome",
            NotificationPersistenceLocalHome.class, NotificationPersistenceLocal.class, bean);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        delegate = new NotificationPersistenceDelegate();
    }

    /**
     * <p>Tears down test environment.</p>
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(NotificationPersistenceSessionBeanAccuracyTests.class);
    }

    /**
     * <p>Tests NotificationPersistenceSessionBean#getNotification(long) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testGetNotification() throws Exception {
        assertNull("Expects null notification if the id is unknown.", delegate.getNotification(300));
    }

    /**
     * <p>Tests NotificationPersistenceSessionBean#createNotification(Notification,boolean) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateNotification() throws Exception {
        Notification notification = AccuracyTestHelper.createNotification(300);
        delegate.createNotification(notification, true);

        AccuracyTestHelper.assertNotifications(notification, delegate.getNotification(notification.getId()));
    }

    /**
     * <p>Tests NotificationPersistenceSessionBean#updateNotification(Notification,boolean) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNotification() throws Exception {
        Notification notification = AccuracyTestHelper.createNotification(300);
        delegate.createNotification(notification, true);

        notification.setActive(false);
        notification.setFromAddress("jim@topcoder.com");
        notification.setSubject("Gift!");

        delegate.updateNotification(notification, true);

        AccuracyTestHelper.assertNotifications(notification, delegate.getNotification(notification.getId()));
    }

    /**
     * <p>Tests NotificationPersistenceSessionBean#deleteNotification(long,boolean) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteNotification() throws Exception {
        //      there should no exception if the id cannot be found
        delegate.deleteNotification(300, true);

        Notification notification = AccuracyTestHelper.createNotification(300);
        delegate.createNotification(notification, true);

        delegate.deleteNotification(notification.getId(), true);

        assertNull("Failed to remove the notification.", delegate.getNotification(notification.getId()));
    }

    /**
     * <p>Tests NotificationPersistenceSessionBean#getAllNotifications() for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllNotifications() throws Exception {
        assertEquals("There should be not notifications in the database.", 0, delegate.getAllNotifications().length);
    }

    /**
     * <p>Tests NotificationPersistenceSessionBean#searchNotifications(Filter) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchNotifications() throws Exception {
        Notification notification = AccuracyTestHelper.createNotification(300);
        delegate.createNotification(notification, true);

        Notification[] notifications = delegate.searchNotifications(
            delegate.getNotificationFilterFactory().createCompanyIdFilter(300));
        assertEquals("Failed to search the notifications.", 1, notifications.length);
        assertEquals("Failed to search the notifications.", notification, notifications[0]);
    }

    /**
     * <p>Tests NotificationPersistenceSessionBean#getNotificationFilterFactory() for accuracy.</p>
     */
    public void testGetNotificationFilterFactory() {
        assertNotNull("Failed to initialize the notification filter factory.", delegate.getNotificationFilterFactory());
    }
}