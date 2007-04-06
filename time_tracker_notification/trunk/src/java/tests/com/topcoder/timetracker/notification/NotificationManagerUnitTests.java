/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.database.DatabaseSearchStrategy;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.notification.persistence.InformixNotificationPersistence;
import com.topcoder.timetracker.notification.persistence.MockAuditManager;
import com.topcoder.timetracker.notification.send.EmailNotificationSender;
import com.topcoder.timetracker.notification.send.MessageBodyGenerator;
import com.topcoder.timetracker.notification.send.MockContactManager;
import com.topcoder.timetracker.notification.send.MockMessageBodyGenerator;

import junit.framework.TestCase;

import java.io.File;

import java.util.HashMap;
import java.util.Map;


/**
 * Unit test for the <code>NotificationManager</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class NotificationManagerUnitTests extends TestCase {
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

    /** Represents the log name. */
    private static final String LOG_NAME = "EmailNotificationSender";

    /** Represents the private instance used for test. */
    private InformixNotificationPersistence persistence = null;

    /** Represents the private audit manager. */
    private AuditManager auditManager = null;

    /** Represents an instance of DBConnectionFactory class. */
    private DBConnectionFactory dbFactory = null;

    /** Represents an instance of search bundle class. */
    private SearchBundle searchBundle = null;

    /** Represents the private instance used for test. */
    private EmailNotificationSender sender = null;

    /** Represents the privagte mesage body generator. */
    private MessageBodyGenerator generator = null;

    /** Represents the private contact manager used for test. */
    private ContactManager contactManager = null;

    /** Represents the private instance used for test. */
    private NotificationManager manager = null;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "log_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "notification_manager_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_search_strategy_config.xml");

        searchBundle = createSearchBundle();
        auditManager = new MockAuditManager();
        dbFactory = new DBConnectionFactoryImpl(UnitTestHelper.DB_FACTORY_NAMESPACE);
        persistence = new InformixNotificationPersistence(dbFactory, CONNECT_NAME, searchBundle, ID_GENERATOR_NAME,
                auditManager);

        contactManager = new MockContactManager();
        dbFactory = new DBConnectionFactoryImpl(UnitTestHelper.DB_FACTORY_NAMESPACE);
        generator = new MockMessageBodyGenerator();
        sender = new EmailNotificationSender(dbFactory, CONNECT_NAME, contactManager, generator, LOG_NAME);

        manager = new NotificationManager();
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
     * Failure test for Construct with no argument. UnitTest with invalid config is illegal.
     * NotificationConfigurationException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor1WithInvalidConfig() throws Exception {
        try {
            UnitTestHelper.clearConfig();

            manager = new NotificationManager();
            fail("UnitTest with invalid config is illegal, NotificationConfigurationException should be thrown");
        } catch (NotificationConfigurationException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>Constructor1</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor1Accuracy() throws Exception {
        assertNotNull("The instance should be created.", new NotificationManager());
    }

    /**
     * <p>
     * Failure test for the constructor with one String argument. Construct with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor2WithNull() throws Exception {
        try {
            new NotificationManager(null);
            fail("Construct with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for the constructor with one String argument. Construct with empty is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor2WithEmpty() throws Exception {
        try {
            new NotificationManager("    ");
            fail("Construct with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for the constructor with not exist nameSpace. Construct with empty is illegal.
     * NotificationConfigurationException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor2WithNotExist() throws Exception {
        try {
            new NotificationManager("something");
            fail("Construct with empty is illegal, NotificationConfigurationException should be thrown");
        } catch (NotificationConfigurationException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for constructor with two arguments. Set with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor3WithNull1() throws Exception {
        try {
            new NotificationManager(null, sender);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for constructor with two arguments. Set with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor3WithNull2() throws Exception {
        try {
            new NotificationManager(persistence, null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for constructor with two arguments.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor3Accuracy() throws Exception {
        assertNotNull("Instance should be created.", new NotificationManager(persistence, sender));
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

            manager = new NotificationManager(persistence, sender);
            manager.getNotification(1);
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

        Notification[] notifications = manager.getAllNotifications();

        assertEquals("The notification get should be the first one.", notifications[0].getId(),
            manager.getNotification(notifications[0].getId()).getId());
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

        assertNull("Should return null.", manager.getNotification(1));
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

            manager = new NotificationManager(persistence, sender);
            manager.createNotification(UnitTestHelper.createNotification(1), false);
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

            manager.createNotification(notification, false);
            fail("Create will invalid notification is illegal, NotificationPersistenceException should be thrown");
        } catch (NotificationPersistenceException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createNotification</code>. Create with null is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNotificationWith() throws Exception {
        try {
            manager.createNotification(null, false);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
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
            manager.createNotification(UnitTestHelper.createNotification(i), false);
        }

        Notification[] notifications = manager.getAllNotifications();

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
            manager.createNotification(UnitTestHelper.createNotification(i), true);
        }

        Notification[] notifications = manager.getAllNotifications();

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

            manager = new NotificationManager(persistence, sender);
            manager.updateNotification(UnitTestHelper.createNotification(1), false);
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

            manager.updateNotification(notification, false);
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
            manager.updateNotification(null, false);
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

        Notification[] notifications = manager.getAllNotifications();

        notifications[0].setFromAddress("bbb@topcoder.com");

        manager.updateNotification(notifications[0], false);
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

        Notification[] notifications = manager.getAllNotifications();

        notifications[0].setFromAddress("bbb@topcoder.com");

        manager.updateNotification(notifications[0], true);
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

            manager = new NotificationManager(persistence, sender);
            manager.deleteNotification(1, false);
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
        Notification[] oldNotificaitons = manager.getAllNotifications();

        manager.deleteNotification(1234874, false);

        Notification[] newNotifications = manager.getAllNotifications();

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
        Notification[] oldNotificaitons = manager.getAllNotifications();

        manager.deleteNotification(1234874, true);

        Notification[] newNotifications = manager.getAllNotifications();

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
        Notification[] oldNotificaitons = manager.getAllNotifications();

        manager.deleteNotification(oldNotificaitons[0].getId(), false);

        Notification[] newNotifications = manager.getAllNotifications();

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
        Notification[] oldNotificaitons = manager.getAllNotifications();

        manager.deleteNotification(oldNotificaitons[0].getId(), true);

        Notification[] newNotifications = manager.getAllNotifications();

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

            manager = new NotificationManager(persistence, sender);

            manager.getAllNotifications();
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

        Notification[] notifications = manager.getAllNotifications();

        assertEquals("4 items should be get.", 4, notifications.length);

        manager.deleteNotification(notifications[0].getId(), false);

        notifications = manager.getAllNotifications();

        assertEquals("3 items should remain.", 3, notifications.length);
    }

    /**
     * <p>
     * Failure test for method <code>searchNotifications</code>. Search with null is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchNotificationsWithNull() throws Exception {
        try {
            manager.searchNotifications(null);
            fail("Search with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>searchNotifications</code>. Search with empty database is illegal.
     * NotificationPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchNotificationsWithEmptyDatabase()
        throws Exception {
        try {
            UnitTestHelper.clearConfig();
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "invalid_db_search_strategy_config.xml");

            searchBundle = createSearchBundle();
            auditManager = new MockAuditManager();
            dbFactory = new DBConnectionFactoryImpl(UnitTestHelper.DB_FACTORY_NAMESPACE);
            persistence = new InformixNotificationPersistence(dbFactory, CONNECT_NAME, searchBundle, ID_GENERATOR_NAME,
                    auditManager);

            manager = new NotificationManager(persistence, sender);

            manager.searchNotifications(manager.getNotificationFilterFactory().createActiveFilter(true));
            fail("Search with empty database is illegal, NotificationPersistenceException should be thrown");
        } catch (NotificationPersistenceException e) {
            // ok to be here
        }
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

        Notification[] notifications = manager.searchNotifications(manager.getNotificationFilterFactory()
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

        Notification[] notifications = manager.searchNotifications(manager.getNotificationFilterFactory()
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

        Notification[] notifications = manager.searchNotifications(manager.getNotificationFilterFactory()
                                                                          .createCompanyIdFilter(1));

        assertEquals("Empty array should be return.", 1, notifications.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>sendNotification</code>. Email message will be sent.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsendNotificationAccuracy() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);
        UnitTestHelper.insertToDatabase(persistence);

        Notification[] notifications = persistence.getAllNotifications();

        manager.sendNotification(notifications[0].getId());
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
