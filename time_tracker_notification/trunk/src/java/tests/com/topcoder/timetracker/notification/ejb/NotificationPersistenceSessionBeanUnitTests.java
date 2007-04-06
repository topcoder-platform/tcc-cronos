/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.ejb;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.database.DatabaseSearchStrategy;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.notification.MockValidator;
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.NotificationPersistence;
import com.topcoder.timetracker.notification.NotificationPersistenceException;
import com.topcoder.timetracker.notification.UnitTestHelper;
import com.topcoder.timetracker.notification.persistence.InformixNotificationPersistence;
import com.topcoder.timetracker.notification.persistence.MockAuditManager;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * Unit test for the <code>NotificationPersistenceSessionBean</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class NotificationPersistenceSessionBeanUnitTests extends TestCase {
    /** Represents default connection name. */
    private static final String CONNECT_NAME = "informix_connect";

    /** Represents the empty connection for failure test. */
    private static final String EMPTY_CONNECTION = "empty_connect";

    /** Represents id generator name. */
    private static final String ID_GENERATOR_NAME = "unit_test_id_sequence";

    /** Represents the search context for the searchBundle. */
    private static final String FILTER_CONTEXT = "SELECT DISTINCT notification.notification_id FROM notification"
        + " INNER JOIN Notify_resources ON notification.notification_id=Notify_resources.notification_id INNER JOIN"
        + " notify_projects ON notification.notification_id= notify_projects.notification_id INNER JOIN"
        + " notify_clients ON notification.notification_id=notify_clients.notification_id WHERE ";

    /** Represents the private instance used for test. */
    private NotificationPersistence persistence = null;

    /** Represents the private audit manager. */
    private AuditManager auditManager = null;

    /** Represents an instance of DBConnectionFactory class. */
    private DBConnectionFactory dbFactory = null;

    /** Represents an instance of search bundle class. */
    private SearchBundle searchBundle = null;

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

        // Look up the home
        Context ctx2 = new InitialContext();
        Object ejbObj = ctx2.lookup("java:com/topcoder/timetracker/notification");

        NotificationPersistenceLocalHome home = (NotificationPersistenceLocalHome) PortableRemoteObject.narrow(ejbObj,
                NotificationPersistenceLocalHome.class);

        persistence = home.create();
        searchBundle = createSearchBundle();
        auditManager = new MockAuditManager();
        dbFactory = new DBConnectionFactoryImpl(UnitTestHelper.DB_FACTORY_NAMESPACE);
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
        persistence.getNotification(1);
    }

    /**
     * <p>
     * Failure test for method <code>getNotification</code>. Retrieve from empty database is illegal.
     * NotificationPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetNotificationWithEmptyDatabase()
        throws Exception {
        try {
            persistence = new InformixNotificationPersistence(dbFactory, EMPTY_CONNECTION, searchBundle,
                    ID_GENERATOR_NAME, auditManager);

            persistence.getNotification(1);
            fail("Retrieve from empty database is illegal, NotificationPersistenceException should be thrown");
        } catch (NotificationPersistenceException e) {
            // ok to be here
        }
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
        UnitTestHelper.insertToDatabase(persistence);

        Notification[] notifications = persistence.getAllNotifications();

        assertEquals("The notification get should be the first one.", notifications[0].getId(),
            persistence.getNotification(notifications[0].getId()).getId());
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

        assertNull("Should return null.", persistence.getNotification(1));
    }

    /**
     * <p>
     * Failure test for method <code>createNotification</code>. Create with empty database is illegal.
     * NotificationPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNotificationWithEmptyDatabase()
        throws Exception {
        try {
            persistence = new InformixNotificationPersistence(dbFactory, EMPTY_CONNECTION, searchBundle,
                    ID_GENERATOR_NAME, auditManager);

            persistence.createNotification(UnitTestHelper.createNotification(1), false);
            fail("Create with empty database is illegal, NotificationPersistenceException should be thrown");
        } catch (NotificationPersistenceException e) {
            // ok to be here
        }
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

            persistence.createNotification(notification, false);
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
            persistence.createNotification(UnitTestHelper.createNotification(i), false);
        }

        Notification[] notifications = persistence.getAllNotifications();

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
            persistence.createNotification(UnitTestHelper.createNotification(i), true);
        }

        Notification[] notifications = persistence.getAllNotifications();

        assertEquals("4 notifications should be created.", 4, notifications.length);
    }

    /**
     * <p>
     * Failure test for method <code>updateNotification</code>. Update with empty database is illegal.
     * NotificationPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateNotificationWithEmptyDatabase()
        throws Exception {
        try {
            persistence = new InformixNotificationPersistence(dbFactory, EMPTY_CONNECTION, searchBundle,
                    ID_GENERATOR_NAME, auditManager);

            persistence.updateNotification(UnitTestHelper.createNotification(1), false);
            fail("Update with empty database is illegal, NotificationPersistenceException should be thrown");
        } catch (NotificationPersistenceException e) {
            // ok to be here
        }
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

            persistence.updateNotification(notification, false);
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
            persistence.updateNotification(null, false);
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

        UnitTestHelper.insertToDatabase(persistence);

        Notification[] notifications = persistence.getAllNotifications();

        notifications[0].setFromAddress("bbb@topcoder.com");

        persistence.updateNotification(notifications[0], false);
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

        UnitTestHelper.insertToDatabase(persistence);

        Notification[] notifications = persistence.getAllNotifications();

        notifications[0].setFromAddress("bbb@topcoder.com");

        persistence.updateNotification(notifications[0], true);
    }

    /**
     * <p>
     * Failure test for method <code>deleteNotification</code>. Delete with empty database is illegal.
     * NotificationPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testdeleteNotificationWithEmptyDatabase()
        throws Exception {
        try {
            persistence = new InformixNotificationPersistence(dbFactory, EMPTY_CONNECTION, searchBundle,
                    ID_GENERATOR_NAME, auditManager);

            persistence.deleteNotification(1, false);
            fail("Delete with empty database is illegal, NotificationPersistenceException should be thrown");
        } catch (NotificationPersistenceException e) {
            // ok to be here
        }
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

        UnitTestHelper.insertToDatabase(persistence);
        Notification[] oldNotificaitons = persistence.getAllNotifications();

        persistence.deleteNotification(1234874, false);

        Notification[] newNotifications = persistence.getAllNotifications();

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

        UnitTestHelper.insertToDatabase(persistence);
        Notification[] oldNotificaitons = persistence.getAllNotifications();

        persistence.deleteNotification(1234874, true);

        Notification[] newNotifications = persistence.getAllNotifications();

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

        UnitTestHelper.insertToDatabase(persistence);
        Notification[] oldNotificaitons = persistence.getAllNotifications();

        persistence.deleteNotification(oldNotificaitons[0].getId(), false);

        Notification[] newNotifications = persistence.getAllNotifications();

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

        UnitTestHelper.insertToDatabase(persistence);
        Notification[] oldNotificaitons = persistence.getAllNotifications();

        persistence.deleteNotification(oldNotificaitons[0].getId(), true);

        Notification[] newNotifications = persistence.getAllNotifications();

        assertEquals("One item should be delete.", oldNotificaitons.length - 1, newNotifications.length);
    }

    /**
     * <p>
     * Failure test for method <code>getAllNotifications</code>. Get with empty database is illegal.
     * NotificationPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllNotificationsWith() throws Exception {
        try {
            persistence = new InformixNotificationPersistence(dbFactory, EMPTY_CONNECTION, searchBundle,
                    ID_GENERATOR_NAME, auditManager);

            persistence.getAllNotifications();
            fail("Get with empty database is illegal, NotificationPersistenceException should be thrown");
        } catch (NotificationPersistenceException e) {
            // ok to be here
        }
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

        UnitTestHelper.insertToDatabase(persistence);

        Notification[] notifications = persistence.getAllNotifications();

        assertEquals("4 items should be get.", 4, notifications.length);

        persistence.deleteNotification(notifications[0].getId(), false);

        notifications = persistence.getAllNotifications();

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
        UnitTestHelper.insertToDatabase(persistence);

        Notification[] notifications = persistence.searchNotifications(persistence.getNotificationFilterFactory()
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
        UnitTestHelper.insertToDatabase(persistence);

        Notification[] notifications = persistence.searchNotifications(persistence.getNotificationFilterFactory()
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
        UnitTestHelper.insertToDatabase(persistence);

        Notification[] notifications = persistence.searchNotifications(persistence.getNotificationFilterFactory()
                                                                                  .createCompanyIdFilter(1));

        assertEquals("Empty array should be return.", 1, notifications.length);
    }

    /**
     * Create the search bundle.
     *
     * @return the SearchBundle
     *
     * @throws Exception any exception to JUnit
     */
    private SearchBundle createSearchBundle() throws Exception {
        DatabaseSearchStrategy strategy = new DatabaseSearchStrategy(
                "com.topcoder.timetracker.notification.persistence.searchstrategy");

        Map fields = new HashMap();

        fields.put("notification.company_id", new MockValidator());
        fields.put("notification_projects.project_id", new MockValidator());
        fields.put("notification_clients.client_id", new MockValidator());
        fields.put("notification_resources.notification_id", new MockValidator());
        fields.put("notification.status", new MockValidator());
        fields.put("notification.last_time_sent", new MockValidator());
        fields.put("notification.next_time_send", new MockValidator());
        fields.put("notification.from_line", new MockValidator());
        fields.put("notification.message", new MockValidator());
        fields.put("notification.subject", new MockValidator());

        SearchBundle sb = new SearchBundle("name", fields, FILTER_CONTEXT, strategy);

        return sb;
    }
}
