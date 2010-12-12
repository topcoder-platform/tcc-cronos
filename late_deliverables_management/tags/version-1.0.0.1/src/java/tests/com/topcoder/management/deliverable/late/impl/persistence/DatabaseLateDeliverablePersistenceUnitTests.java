/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.late.impl.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.deliverable.late.LateDeliverable;
import com.topcoder.management.deliverable.late.LateDeliverableManagementConfigurationException;
import com.topcoder.management.deliverable.late.LateDeliverableManager;
import com.topcoder.management.deliverable.late.TestsHelper;
import com.topcoder.management.deliverable.late.impl.LateDeliverableManagerImpl;
import com.topcoder.management.deliverable.late.impl.LateDeliverableNotFoundException;
import com.topcoder.management.deliverable.late.impl.LateDeliverablePersistenceException;

/**
 * <p>
 * Unit tests for {@link DatabaseLateDeliverablePersistence} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DatabaseLateDeliverablePersistenceUnitTests {
    /**
     * <p>
     * Represents the <code>DatabaseLateDeliverablePersistence</code> instance used in tests.
     * </p>
     */
    private DatabaseLateDeliverablePersistence instance;

    /**
     * <p>
     * Represents the configuration object used in tests.
     * </p>
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Represents the connection used in tests.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the late deliverable manager used in tests.
     * </p>
     */
    private LateDeliverableManager lateDeliverableManager;


    /**
     * <p>
     * Represents the late deliverable used in tests.
     * </p>
     */
    private LateDeliverable lateDeliverable;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DatabaseLateDeliverablePersistenceUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        TestsHelper.unloadConfig();
        TestsHelper.loadConfig("SearchBundleManager.xml");

        connection = TestsHelper.getConnection();
        TestsHelper.clearDB(connection);
        TestsHelper.loadDB(connection);

        config = TestsHelper.getConfig().getChild("persistenceConfig");

        instance = new DatabaseLateDeliverablePersistence();
        instance.configure(config);

        lateDeliverableManager = new LateDeliverableManagerImpl();

        // Retrieve the late deliverable with ID=1
        lateDeliverable = lateDeliverableManager.retrieve(1);
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        TestsHelper.clearDB(connection);
        TestsHelper.closeConnection(connection);
        connection = null;
        instance = null;

        TestsHelper.unloadConfig();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DatabaseLateDeliverablePersistence()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DatabaseLateDeliverablePersistence();

        assertNull("'dbConnectionFactory' should be correct.", TestsHelper.getField(instance, "dbConnectionFactory"));
        assertNull("'connectionName' should be correct.", TestsHelper.getField(instance, "connectionName"));
        assertNull("'log' should be correct.", TestsHelper.getField(instance, "log"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject config)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_configure_1() {
        instance.configure(config);

        assertNotNull("'configure' should be correct.", TestsHelper.getField(instance, "log"));
        assertNotNull("'dbConnectionFactory' should be correct.",
            TestsHelper.getField(instance, "dbConnectionFactory"));
        assertEquals("'connectionName' should be correct.",
            "myConnection", TestsHelper.getField(instance, "connectionName"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject config)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_configure_2() throws Exception {
        config.removeProperty("loggerName");
        config.removeProperty("connectionName");

        instance.configure(config);

        assertNull("'configure' should be correct.", TestsHelper.getField(instance, "log"));
        assertNotNull("'dbConnectionFactory' should be correct.",
            TestsHelper.getField(instance, "dbConnectionFactory"));
        assertNull("'connectionName' should be correct.", TestsHelper.getField(instance, "connectionName"));
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with config is <code>null</code>
     * .<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_configure_configNull() {
        config = null;

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'loggerName' is not a
     * string.<br>
     * <code>LateDeliverableManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = LateDeliverableManagementConfigurationException.class)
    public void test_configure_loggerNameNotString() throws Exception {
        config.setPropertyValue("loggerName", 1);

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'loggerName' is empty.<br>
     * <code>LateDeliverableManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = LateDeliverableManagementConfigurationException.class)
    public void test_configure_loggerNameEmpty() throws Exception {
        config.setPropertyValue("loggerName", TestsHelper.EMPTY_STRING);

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'dbConnectionFactoryConfig'
     * is missing.<br>
     * <code>LateDeliverableManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = LateDeliverableManagementConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigMissing() throws Exception {
        config.removeChild("dbConnectionFactoryConfig");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'dbConnectionFactoryConfig'
     * is invalid.<br>
     * <code>LateDeliverableManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = LateDeliverableManagementConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigInvalid1() throws Exception {
        config.getChild("dbConnectionFactoryConfig")
            .removeChild("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'dbConnectionFactoryConfig'
     * is invalid.<br>
     * <code>LateDeliverableManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = LateDeliverableManagementConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigInvalid2() throws Exception {
        config.getChild("dbConnectionFactoryConfig")
            .getChild("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
            .getChild("connections").setPropertyValue("default", "invalid");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'connectionName' is not a
     * string.<br>
     * <code>LateDeliverableManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = LateDeliverableManagementConfigurationException.class)
    public void test_configure_connectionNameNotString() throws Exception {
        config.setPropertyValue("connectionName", 1);

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'connectionName' is
     * empty.<br>
     * <code>LateDeliverableManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = LateDeliverableManagementConfigurationException.class)
    public void test_configure_connectionNameEmpty() throws Exception {
        config.setPropertyValue("connectionName", TestsHelper.EMPTY_STRING);

        instance.configure(config);
    }

    /**
     * <p>
     * Accuracy test for the method <code>update(LateDeliverable lateDeliverable)</code>. <br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_update_1() throws Exception {
        assertFalse("'lateDeliverable' should be correct.", lateDeliverable.isForgiven());
        assertNull("'lateDeliverable' should be correct.", lateDeliverable.getExplanation());
        assertNull("'lateDeliverable' should be correct.", lateDeliverable.getDelay());
        assertNotNull("'lateDeliverable' should be correct.", lateDeliverable.getLastNotified());

        lateDeliverable.setDelay(10L);
        lateDeliverable.setForgiven(true);
        lateDeliverable.setExplanation("OR didn't work");
        lateDeliverable.setResponse("OK");
        lateDeliverable.setLastNotified(null);

        instance.update(lateDeliverable);

        LateDeliverable updatedLateDeliverable = lateDeliverableManager.retrieve(1);

        assertTrue("'update' should be correct.", updatedLateDeliverable.isForgiven());
        assertEquals("'update' should be correct.",
            "OR didn't work", lateDeliverable.getExplanation().trim());
        assertEquals("'update' should be correct.",
            10L, lateDeliverable.getDelay().longValue());
        assertEquals("'update' should be correct.",
            "OK", lateDeliverable.getResponse().trim());
        assertNull("'lateDeliverable' should be correct.", lateDeliverable.getLastNotified());
    }

    /**
     * <p>
     * Accuracy test for the method <code>update(LateDeliverable lateDeliverable)</code>. <br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_update_2() throws Exception {
        config.removeProperty("connectionName");
        instance.configure(config);

        lateDeliverable.setDelay(10L);
        lateDeliverable.setForgiven(true);
        lateDeliverable.setExplanation("OR didn't work");
        lateDeliverable.setResponse("OK");
        lateDeliverable.setLastNotified(null);

        instance.update(lateDeliverable);

        lateDeliverable.setForgiven(false);
        lateDeliverable.setDelay(null);
        lateDeliverable.setLastNotified(new Date());

        instance.update(lateDeliverable);

        LateDeliverable updatedLateDeliverable = lateDeliverableManager.retrieve(1);

        assertFalse("'update' should be correct.", updatedLateDeliverable.isForgiven());
        assertNull("'update' should be correct.", lateDeliverable.getDelay());
        assertNotNull("'lateDeliverable' should be correct.", lateDeliverable.getLastNotified());

        assertEquals("'update' should be correct.",
            "OR didn't work", lateDeliverable.getExplanation().trim());
        assertEquals("'update' should be correct.",
            "OK", lateDeliverable.getResponse().trim());
    }

    /**
     * <p>
     * Failure test for the method <code>update(LateDeliverable lateDeliverable)</code> with lateDeliverable is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_lateDeliverableNull() throws Exception {
        lateDeliverable = null;

        instance.update(lateDeliverable);
    }

    /**
     * <p>
     * Failure test for the method <code>update(LateDeliverable lateDeliverable)</code> with lateDeliverable.getId()
     * is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_lateDeliverableIdNegative() throws Exception {
        lateDeliverable.setId(-1);

        instance.update(lateDeliverable);
    }

    /**
     * <p>
     * Failure test for the method <code>update(LateDeliverable lateDeliverable)</code> with lateDeliverable.getId()
     * is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_lateDeliverableIdZero() throws Exception {
        lateDeliverable.setId(0);

        instance.update(lateDeliverable);
    }

    /**
     * <p>
     * Failure test for the method <code>update(LateDeliverable lateDeliverable)</code> with
     * lateDeliverable.getDeadline() is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_lateDeliverableDeadlineNull() throws Exception {
        lateDeliverable.setDeadline(null);

        instance.update(lateDeliverable);
    }

    /**
     * <p>
     * Failure test for the method <code>update(LateDeliverable lateDeliverable)</code> with
     * lateDeliverable.getCreateDate() is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_lateDeliverableCreateDateNull() throws Exception {
        lateDeliverable.setCreateDate(null);

        instance.update(lateDeliverable);
    }

    /**
     * <p>
     * Failure test for the method <code>update(LateDeliverable lateDeliverable)</code> with this persistence was not
     * properly configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_update_NotConfigured() throws Exception {
        instance = new DatabaseLateDeliverablePersistence();

        instance.update(lateDeliverable);
    }

    /**
     * <p>
     * Failure test for the method <code>update(LateDeliverable lateDeliverable)</code> with late deliverable with ID
     * equal to lateDeliverable.getId() doesn't exist in persistence.<br>
     * <code>LateDeliverableNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = LateDeliverableNotFoundException.class)
    public void test_update_LateDeliverableNotFound() throws Exception {
        lateDeliverable.setId(Long.MAX_VALUE);

        instance.update(lateDeliverable);
    }

    /**
     * <p>
     * Failure test for the method <code>update(LateDeliverable lateDeliverable)</code> with an error occurred.<br>
     * <code>LateDeliverablePersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = LateDeliverablePersistenceException.class)
    public void test_update_Error1() throws Exception {
        config.getChild("dbConnectionFactoryConfig")
            .getChild("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
            .getChild("connections").getChild("myConnection").getChild("parameters")
            .setPropertyValue("password", "invalid_\n_password");
        instance.configure(config);

        instance.update(lateDeliverable);
    }

    /**
     * <p>
     * Failure test for the method <code>update(LateDeliverable lateDeliverable)</code> with an error occurred.<br>
     * <code>LateDeliverablePersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = LateDeliverablePersistenceException.class)
    public void test_update_Error2() throws Exception {
        // Drop tables
        TestsHelper.executeSQL(connection, TestsHelper.TEST_FILES + "DBDrop_tcs.sql");

        try {
            instance.update(lateDeliverable);
        } finally {
            // Create tables
            TestsHelper.executeSQL(connection, TestsHelper.TEST_FILES + "DBSetup_tcs.sql");
        }
    }
}
