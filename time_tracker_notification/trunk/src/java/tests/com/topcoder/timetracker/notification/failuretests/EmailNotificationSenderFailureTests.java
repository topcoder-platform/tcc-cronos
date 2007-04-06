/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import org.jmock.MockObjectTestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.send.EmailNotificationSender;
import com.topcoder.timetracker.notification.send.MessageBodyGenerator;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure test for <code>{@link EmailNotificationSender}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class EmailNotificationSenderFailureTests extends MockObjectTestCase {

    /**
     * <p>
     * Represents the database connection factory.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * Represents the contact manager
     * </p>
     */
    private ContactManager contactManager;

    /**
     * <p>
     * Represents the message body generator.
     * </p>
     */
    private MessageBodyGenerator messageBodyGenerator;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "Logging.xml");

        dbConnectionFactory = (DBConnectionFactory) mock(DBConnectionFactory.class).proxy();
        contactManager = (ContactManager) mock(ContactManager.class).proxy();
        messageBodyGenerator = (MessageBodyGenerator) mock(MessageBodyGenerator.class).proxy();
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link EmailNotificationSender#EmailNotificationSender(DBConnectionFactory, String, ContactManager, MessageBodyGenerator, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEmailNotificationSender_NullDBConnectionFactory() throws Exception {
        try {
            new EmailNotificationSender(null, "connections", contactManager, messageBodyGenerator, "Failure");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link EmailNotificationSender#EmailNotificationSender(DBConnectionFactory, String, ContactManager, MessageBodyGenerator, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEmailNotificationSender_NullConnectionName() throws Exception {
        try {
            new EmailNotificationSender(dbConnectionFactory, null, contactManager, messageBodyGenerator, "Failure");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link EmailNotificationSender#EmailNotificationSender(DBConnectionFactory, String, ContactManager, MessageBodyGenerator, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEmailNotificationSender_EmptyConnectionName() throws Exception {
        try {
            new EmailNotificationSender(dbConnectionFactory, "", contactManager, messageBodyGenerator, "Failure");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link EmailNotificationSender#EmailNotificationSender(DBConnectionFactory, String, ContactManager, MessageBodyGenerator, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEmailNotificationSender_TrimmedEmptyConnectionName() throws Exception {
        try {
            new EmailNotificationSender(dbConnectionFactory, " ", contactManager, messageBodyGenerator, "Failure");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link EmailNotificationSender#EmailNotificationSender(DBConnectionFactory, String, ContactManager, MessageBodyGenerator, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEmailNotificationSender_NullContactManager() throws Exception {
        try {
            new EmailNotificationSender(dbConnectionFactory, "connections", null, messageBodyGenerator, "Failure");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link EmailNotificationSender#EmailNotificationSender(DBConnectionFactory, String, ContactManager, MessageBodyGenerator, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEmailNotificationSender_NullMessageBodyGenerator() throws Exception {
        try {
            new EmailNotificationSender(dbConnectionFactory, "connections", contactManager, null, "Failure");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link EmailNotificationSender#EmailNotificationSender(DBConnectionFactory, String, ContactManager, MessageBodyGenerator, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEmailNotificationSender_NullLogName() throws Exception {
        try {
            new EmailNotificationSender(dbConnectionFactory, "connections", contactManager, messageBodyGenerator, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link EmailNotificationSender#EmailNotificationSender(DBConnectionFactory, String, ContactManager, MessageBodyGenerator, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEmailNotificationSender_EmptyLogName() throws Exception {
        try {
            new EmailNotificationSender(dbConnectionFactory, "connections", contactManager, messageBodyGenerator, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link EmailNotificationSender#EmailNotificationSender(DBConnectionFactory, String, ContactManager, MessageBodyGenerator, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEmailNotificationSender_TrimmedEmptyLogName() throws Exception {
        try {
            new EmailNotificationSender(dbConnectionFactory, "connections", contactManager, messageBodyGenerator, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
