/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.send;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.database.DatabaseSearchStrategy;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.notification.MockValidator;
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.UnitTestHelper;
import com.topcoder.timetracker.notification.persistence.InformixNotificationPersistence;
import com.topcoder.timetracker.notification.persistence.MockAuditManager;

import junit.framework.TestCase;

import java.io.File;

import java.util.HashMap;
import java.util.Map;


/**
 * Unit test for the <code>Notification</code> class.
 *
 * @author kzhu
 * @version 1.0
 */
public class EmailNotificationSenderUnitTests extends TestCase {
    /** Represents default connection name. */
    private static final String CONNECT_NAME = "informix_connect";

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

    /** Represents the private message body generator. */
    private MessageBodyGenerator generator = null;

    /** Represents the private contact manager used for test. */
    private ContactManager contactManager = null;

    /**
     * Sets up the test environment.
     *
     * @throws Exception any exception to JUnit
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "log_config.xml");
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
     * Failure test for method <code>Constructor</code>. Construct with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithNull1() throws Exception {
        try {
            sender = new EmailNotificationSender(null, CONNECT_NAME, contactManager, generator, LOG_NAME);
            fail("Construct with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>Constructor</code>. Construct with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithNull2() throws Exception {
        try {
            sender = new EmailNotificationSender(dbFactory, null, contactManager, generator, LOG_NAME);
            fail("Construct with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>Constructor</code>. Construct with empty is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithEmpty2() throws Exception {
        try {
            sender = new EmailNotificationSender(dbFactory, "       ", contactManager, generator, LOG_NAME);
            fail("Construct with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>Constructor</code>. Construct with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithNull3() throws Exception {
        try {
            sender = new EmailNotificationSender(dbFactory, CONNECT_NAME, null, generator, LOG_NAME);
            fail("Construct with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>Constructor</code>. Construct with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithNull4() throws Exception {
        try {
            sender = new EmailNotificationSender(dbFactory, CONNECT_NAME, contactManager, null, LOG_NAME);
            fail("Construct with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>Constructor</code>. Construct with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithNull5() throws Exception {
        try {
            sender = new EmailNotificationSender(dbFactory, CONNECT_NAME, contactManager, generator, null);
            fail("Construct with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>Constructor</code>. Construct with empty is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithEmpty5() throws Exception {
        try {
            sender = new EmailNotificationSender(dbFactory, CONNECT_NAME, contactManager, generator, "     ");
            fail("Construct with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for Constructor, the instance should be created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("The instance should be created.", sender);
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

        sender.send(notifications[0].getId(), persistence);
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
