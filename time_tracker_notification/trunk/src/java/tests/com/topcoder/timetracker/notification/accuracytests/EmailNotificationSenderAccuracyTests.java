/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceFactory;
import com.topcoder.timetracker.notification.send.EmailNotificationSender;

/**
 * <p>
 * Accuracy Unit test cases for EmailNotificationSender.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class EmailNotificationSenderAccuracyTests extends TestCase {
    /**
     * <p>EmailNotificationSender instance for testing.</p>
     */
    private EmailNotificationSender instance;

    /**
     * <p>Setup test environment.</p>
     *
     * @throws Exception to JUNit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(AccuracyTestHelper.DB_FACTORY_NAMESPACE);
        instance = new EmailNotificationSender(dbFactory, "tt_connection", new MockContactManager(),
            new MockMessageBodyGenerator(), "TestLogger");
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
        instance = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(EmailNotificationSenderAccuracyTests.class);
    }

    /**
     * <p>Tests ctor EmailNotificationSender#EmailNotificationSender(DBConnectionFactory,String,ContactManager,
     * MessageBodyGenerator,String) for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create EmailNotificationSender instance.", instance);
    }

    /**
     * <p>Tests EmailNotificationSender#send(long) for accuracy.</p>
     *
     * @throws Exception to JUnit
     */
    public void testSend() throws Exception {
        Notification notification = AccuracyTestHelper.createNotification(300);
        NotificationPersistenceFactory.getNotificationPersistence().createNotification(notification, true);

        instance.send(notification.getId());
    }
}