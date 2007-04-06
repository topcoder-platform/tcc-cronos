/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import org.jmock.MockObjectTestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.persistence.InformixNotificationPersistence;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure test for <code>{@link InformixNotificationPersistence}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class InformixNotificationPersistenceFailureTests extends MockObjectTestCase {

    /**
     * <p>
     * Represents the InformixNotificationPersistence instance used in tests.
     * </p>
     */
    private InformixNotificationPersistence informixNotificationPersistence;

    /**
     * <p>
     * Represents the database connection factory.
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * Represents the search bundle.
     * </p>
     */
    private SearchBundle searchBundle;

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
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add(FailureTestHelper.FAILURE_CONFIG_ROOT + "DBConnectionFactory.xml");
        configManager.add(FailureTestHelper.FAILURE_CONFIG_ROOT + "SearchBuilder.xml");

        dbConnectionFactory = new DBConnectionFactoryImpl();

        searchBundle = (new SearchBundleManager("com.topcoder.search.builder")).getSearchBundle("FirstSearchBundle");

        informixNotificationPersistence = new InformixNotificationPersistence(dbConnectionFactory, "Informix",
            searchBundle, "unit_test_id_sequence", (AuditManager) mock(AuditManager.class).proxy());
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence1_NullDBConnectionFactory() throws Exception {
        try {
            new InformixNotificationPersistence(null, "Informix", "unit_test_id_sequence", (AuditManager) mock(
                AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence1_NullConnectionName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, null, "unit_test_id_sequence",
                (AuditManager) mock(AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence1_EmptyConnectionName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "", "unit_test_id_sequence", (AuditManager) mock(
                AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence1_TrimmedEmptyConnectionName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, " ", "unit_test_id_sequence", (AuditManager) mock(
                AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence1_NullIDgeneratorName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", null, (AuditManager) mock(
                AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence1_EmptyIDgeneratorName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", "", (AuditManager) mock(
                AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence1_TrimmedEmptyIDgeneratorName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", " ", (AuditManager) mock(
                AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence1_NullAuditManager() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", "unit_test_id_sequence", null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence1_InvalidIDgeneratorName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", "invalid", (AuditManager) mock(
                AuditManager.class).proxy());
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence2_NullDBConnectionFactory() throws Exception {
        try {
            new InformixNotificationPersistence(null, "Informix", searchBundle, "unit_test_id_sequence",
                (AuditManager) mock(AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence2_NullConnectionName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, null, searchBundle, "unit_test_id_sequence",
                (AuditManager) mock(AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence2_EmptyConnectionName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "", searchBundle, "unit_test_id_sequence",
                (AuditManager) mock(AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence2_TrimmedEmptyConnectionName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, " ", searchBundle, "unit_test_id_sequence",
                (AuditManager) mock(AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence2_NullSearchBundle() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", null, "unit_test_id_sequence",
                (AuditManager) mock(AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence2_NullIDgeneratorName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", searchBundle, null,
                (AuditManager) mock(AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence2_EmptyIDgeneratorName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", searchBundle, "", (AuditManager) mock(
                AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence2_TrimmedEmptyIDgeneratorName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", searchBundle, " ",
                (AuditManager) mock(AuditManager.class).proxy());
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence2_NullAuditManager() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", searchBundle, "unit_test_id_sequence",
                null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#InformixNotificationPersistence(DBConnectionFactory, String, String, AuditManager)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixNotificationPersistence2_InvalidIDgeneratorName() throws Exception {
        try {
            new InformixNotificationPersistence(dbConnectionFactory, "Informix", searchBundle, "invalid",
                (AuditManager) mock(AuditManager.class).proxy());
            fail("expect throw NotificationConfigurationException.");
        } catch (NotificationConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#createNotification(com.topcoder.timetracker.notification.Notification, boolean)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateNotification_NullNotification() throws Exception {
        try {
            informixNotificationPersistence.createNotification(null, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#updateNotification(com.topcoder.timetracker.notification.Notification, boolean)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateNotification_NullNotification() throws Exception {
        try {
            informixNotificationPersistence.updateNotification(null, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link InformixNotificationPersistence#searchNotifications(com.topcoder.search.builder.filter.Filter)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSearchNotifications_NullFilter() throws Exception {
        try {
            informixNotificationPersistence.searchNotifications(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
