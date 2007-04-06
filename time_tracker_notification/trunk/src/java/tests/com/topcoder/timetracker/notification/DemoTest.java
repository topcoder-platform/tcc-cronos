/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.database.DatabaseSearchStrategy;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.OrFilter;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceDelegate;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceLocal;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceLocalHome;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceSessionBean;
import com.topcoder.timetracker.notification.persistence.InformixNotificationPersistence;
import com.topcoder.timetracker.notification.persistence.MockAuditManager;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.io.File;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * The demo for the component.
 *
 * @author kzhu
 * @version 3.2
 */
public class DemoTest extends TestCase {
    /** Represents default connection name. */
    private static final String CONNECT_NAME = "informix_connect";

    /** Represents id generator name. */
    private static final String ID_GENERATOR_NAME = "unit_test_id_sequence";

    /** Represents the search context for the searchBundle. */
    private static final String FILTER_CONTEXT = "SELECT DISTINCT notification.notification_id FROM notification"
        + " INNER JOIN Notify_resources ON notification.notification_id=Notify_resources.notification_id INNER JOIN"
        + " notify_projects ON notification.notification_id= notify_projects.notification_id INNER JOIN"
        + " notify_clients ON notification.notification_id=notify_clients.notification_id WHERE ";

    /** Represents the private instance used for test. */
    private InformixNotificationPersistence persistence = null;

    /** Represents the private audit manager. */
    private AuditManager auditManager = null;

    /** Represents an instance of DBConnectionFactory class. */
    private DBConnectionFactory dbFactory = null;

    /** Represents an instance of search bundle class. */
    private SearchBundle searchBundle = null;

    /**
     * Setup the test.
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
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "np_factory_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "np_delegate_config.xml");

        searchBundle = createSearchBundle();
        auditManager = new MockAuditManager();
        dbFactory = new DBConnectionFactoryImpl(UnitTestHelper.DB_FACTORY_NAMESPACE);
        persistence = new InformixNotificationPersistence(dbFactory, CONNECT_NAME, searchBundle, ID_GENERATOR_NAME,
                auditManager);

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
    }

    /**
     * Demo the notification management.
     *
     * @throws Exception any exception to JUnit
     */
    public void testDemoManageNotification() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, CONNECT_NAME);

        UnitTestHelper.insertToDatabase(persistence);

        // NotificationManager groups all the methods of NotificationPersistece
        // and SendNotification, the demo only show how to use the manager.
        // create the manager, assume that namespace, persistence, and
        // NotificationSender
        // are defined
        NotificationManager manager = new NotificationManager();

        Notification[] notifications = manager.getAllNotifications();

        // the id is auto generated, so we need to fetch some one first to know the id.
        Notification notification = manager.getNotification(notifications[0].getId());

        // update the notification
        notification.setSubject("hello yyy");
        manager.updateNotification(notification, false);

        // create notification
        Notification temp = new Notification();
        temp.setActive(true);
        temp.setCompnayId(1234);
        temp.setCreationDate(new Date());
        temp.setCreationUser("creation_user");
        temp.setModificationDate(new Date());
        temp.setModificationUser("modification_user");
        temp.setFromAddress("aaa@topcoder.com");
        temp.setId(1234);
        temp.setLastTimeSent(new Date());
        temp.setMessage("Hello, you win.");
        temp.setNextTimeToSend(new Date());
        temp.setScheduleId(1234);
        temp.setSubject("hello");

        // assume that client 1 exists in the database.
        temp.setToClients(new long[] {1});
        temp.setActive(true);

        manager.createNotification(temp, false);

        // search the notification whose client id is 1 or subject is ¡°hello yyy¡±);
        NotificationFilterFactory filterFactory = manager.getNotificationFilterFactory();

        Filter filter1 = filterFactory.createCompanyIdFilter(1);
        Filter filter2 = filterFactory.createSubjectFilter("hello yyy", StringMatchType.EXACT_MATCH);
        Filter orFilter = new OrFilter(filter1, filter2);

        // two elements expected
        Notification[] ns = manager.searchNotifications(orFilter);

        // get all notifications
        ns = manager.getAllNotifications();
    }

    /**
     * Demo get the delegate, the using of the delegate is the same to the manager.
     *
     * @throws Exception any exception to JUnit
     */
    public void testDemoDelegate() throws Exception {
        NotificationPersistenceDelegate delegate = new NotificationPersistenceDelegate();

        // the usage is the same to the persistence.
        delegate.getAllNotifications();
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
