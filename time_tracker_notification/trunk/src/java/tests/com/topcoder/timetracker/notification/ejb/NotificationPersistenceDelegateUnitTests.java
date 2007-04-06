/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.ejb;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.NotificationPersistenceException;
import com.topcoder.timetracker.notification.UnitTestHelper;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.io.File;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * Unit test for the <code>NotificationPersistenceDelegate</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class NotificationPersistenceDelegateUnitTests extends TestCase {
    /** Represents default connection name. */
    private static final String CONNECT_NAME = "informix_connect";

    /** Represents the private instance used for test. */
    private NotificationPersistenceDelegate delegate = null;

    /** Represents an instance of DBConnectionFactory class. */
    private DBConnectionFactory dbFactory = null;

    /**
     * Set up the environment.
     *
     * @throws Exception any exception to JUnit
     */
    protected void setUp() throws Exception {
        //setup the JNDI environment.
        MockContextFactory.setAsInitial();

        //Create the initial context that will be used for binding EJBs
        Context ctx = new InitialContext();

        //  Create an instance of the MockContainer
        MockContainer mc = new MockContainer(ctx);

        SessionBeanDescriptor dd = new SessionBeanDescriptor("java:com/topcoder/timetracker/notification",
                NotificationPersistenceLocalHome.class, NotificationPersistenceLocal.class,
                new NotificationPersistenceSessionBean());

        mc.deploy(dd);

        // we use MockTransaction outside of the app server.
        MockUserTransaction mockTransaction = new MockUserTransaction();
        ctx.rebind("javax.transaction.UserTransaction", mockTransaction);

        UnitTestHelper.clearConfig();
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "log_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "notification_manager_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_search_strategy_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "np_factory_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "np_delegate_config.xml");

        dbFactory = new DBConnectionFactoryImpl(UnitTestHelper.DB_FACTORY_NAMESPACE);

        delegate = new NotificationPersistenceDelegate();
    }

    /**
     * Clean the test.
     *
     * @throws Exception to Junit
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);
    }
    /**
     * <p>
     * Accuracy test for method <code>getNotification</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetNotificationAccuracy() throws Exception {
        delegate.getNotification(1);
    }

    /**
     * <p>
     * Accuracy test for method <code>getNotification</code>. Should return the specified notification.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetNotificationAccuracy1() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);
        UnitTestHelper.insertToDatabase(delegate);

        Notification[] notifications = delegate.getAllNotifications();

        assertEquals("The notification get should be the first one.", notifications[0].getId(),
            delegate.getNotification(notifications[0].getId()).getId());
    }

    /**
     * <p>
     * Accuracy test for method <code>getNotification</code>. Should return null.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetNotificationAccuracy2() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);

        assertNull("Should return null.", delegate.getNotification(1));
    }

    /**
     * <p>
     * Failure test for method <code>createNotification</code>. Create will invalid notification is illegal.
     * NotificationPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNotificationWithInvalid1() throws Exception {
        try {
            Notification notification = new Notification();

            delegate.createNotification(notification, false);
            fail("Create will invalid notification is illegal, NotificationPersistenceException should be thrown");
        } catch (NotificationPersistenceException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createNotification</code>. With audit disable.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNotificationAccuracy1() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);

        for (int i = 1; i < 5; i++) {
            delegate.createNotification(UnitTestHelper.createNotification(i), false);
        }

        Notification[] notifications = delegate.getAllNotifications();

        assertEquals("4 notifications should be created.", 4, notifications.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>createNotification</code>. With audit enable.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNotificationAccuracy2() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);

        for (int i = 1; i < 5; i++) {
            delegate.createNotification(UnitTestHelper.createNotification(i), true);
        }

        Notification[] notifications = delegate.getAllNotifications();

        assertEquals("4 notifications should be created.", 4, notifications.length);
    }

    /**
     * <p>
     * Failure test for method <code>updateNotification</code>. Update with invalid is illegal.
     * NotificationPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateNotificationWithInvalid() throws Exception {
        try {
            Notification notification = new Notification();

            delegate.updateNotification(notification, false);
            fail("Update with invalid is illegal, NotificationPersistenceException should be thrown");
        } catch (NotificationPersistenceException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>updateNotification</code>. Update with null is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateNotificationWithNull() throws Exception {
        try {
            delegate.updateNotification(null, false);
            fail("Update with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>updateNotification</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateNotificationAccuracy1() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);

        UnitTestHelper.insertToDatabase(delegate);

        Notification[] notifications = delegate.getAllNotifications();

        notifications[0].setFromAddress("bbb@topcoder.com");

        delegate.updateNotification(notifications[0], false);
    }

    /**
     * <p>
     * Accuracy test for method <code>updateNotification</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateNotificationAccuracy2() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);

        UnitTestHelper.insertToDatabase(delegate);

        Notification[] notifications = delegate.getAllNotifications();

        notifications[0].setFromAddress("bbb@topcoder.com");

        delegate.updateNotification(notifications[0], true);
    }

    /**
     * <p>
     * Accuracy test for method <code>deleteNotification</code>. Delete with not exist notification.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testdeleteNotificationAccuracy1() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);
        UnitTestHelper.insertToDatabase(delegate);
        Notification[] oldNotificaitons = delegate.getAllNotifications();

        delegate.deleteNotification(1234874, false);

        Notification[] newNotifications = delegate.getAllNotifications();

        assertEquals("Nothing should be changed.", oldNotificaitons.length, newNotifications.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>deleteNotification</code>. Delete with not exist notification.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testdeleteNotificationAccuracy2() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);
        UnitTestHelper.insertToDatabase(delegate);
        Notification[] oldNotificaitons = delegate.getAllNotifications();

        delegate.deleteNotification(1234874, true);

        Notification[] newNotifications = delegate.getAllNotifications();

        assertEquals("Nothing should be changed.", oldNotificaitons.length, newNotifications.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>deleteNotification</code>. Delete with audit disable.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testdeleteNotificationAccuracy3() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);
        UnitTestHelper.insertToDatabase(delegate);
        Notification[] oldNotificaitons = delegate.getAllNotifications();

        delegate.deleteNotification(oldNotificaitons[0].getId(), false);

        Notification[] newNotifications = delegate.getAllNotifications();

        assertEquals("One item should be delete.", oldNotificaitons.length - 1, newNotifications.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>deleteNotification</code>. Delete with audit enable.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testdeleteNotificationAccuracy4() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);
        UnitTestHelper.insertToDatabase(delegate);
        Notification[] oldNotificaitons = delegate.getAllNotifications();

        delegate.deleteNotification(oldNotificaitons[0].getId(), true);

        Notification[] newNotifications = delegate.getAllNotifications();

        assertEquals("One item should be delete.", oldNotificaitons.length - 1, newNotifications.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>getAllNotifications</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllNotificationsAccuracy() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);

        UnitTestHelper.insertToDatabase(delegate);

        Notification[] notifications = delegate.getAllNotifications();

        assertEquals("4 items should be get.", 4, notifications.length);

        delegate.deleteNotification(notifications[0].getId(), false);

        notifications = delegate.getAllNotifications();

        assertEquals("3 items should remain.", 3, notifications.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchNotifications</code>. Search use active filter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchNotificationsAccuracy1() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);
        UnitTestHelper.insertToDatabase(delegate);

        Notification[] notifications = delegate.searchNotifications(delegate.getNotificationFilterFactory()
                                                                            .createActiveFilter(true));

        assertEquals("All the items should be select.", 4, notifications.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchNotifications</code>. Search use active filter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchNotificationsAccuracy2() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);
        UnitTestHelper.insertToDatabase(delegate);

        Notification[] notifications = delegate.searchNotifications(delegate.getNotificationFilterFactory()
                                                                            .createActiveFilter(false));

        assertEquals("Empty array should be return.", 0, notifications.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchNotifications</code>. Search use company id filter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchNotificationsAccuracy3() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);
        UnitTestHelper.insertToDatabase(delegate);

        Notification[] notifications = delegate.searchNotifications(delegate.getNotificationFilterFactory()
                                                                            .createCompanyIdFilter(1));

        assertEquals("Empty array should be return.", 1, notifications.length);
    }
}
