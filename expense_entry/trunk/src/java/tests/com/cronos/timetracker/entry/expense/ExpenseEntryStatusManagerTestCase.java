/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryStatusDbPersistence;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryStatusPersistence;
import com.cronos.timetracker.entry.expense.persistence.PersistenceException;
import com.cronos.timetracker.entry.expense.search.Criteria;
import com.cronos.timetracker.entry.expense.search.FieldMatchCriteria;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryStatusManager</code> class. The test uses
 * <code>ExpenseEntryStatusDbPersistence</code> as the underlying persistence.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class ExpenseEntryStatusManagerTestCase extends TestCase {
    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /** Represents the configuration manager instance used to load and delete configuration. */
    private ConfigManager configManager;

    /** Represents the manager instance used in tests. */
    private ExpenseEntryStatusManager manager;

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a DB connection factory to create connections. */
    private DBConnectionFactory factory;

    /** Represents a valid expense entry status instance. */
    private ExpenseEntryStatus status;

    /**
     * <p>
     * Sets up the test environment. The configurations are removed. A valid manager is created. The data table is
     * truncated. A valid expense entry status is created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        configManager = ConfigManager.getInstance();

        TestHelper.clearConfiguration();
        TestHelper.addValidConfiguration();

        manager = new ExpenseEntryStatusManager(NAMESPACE);
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }

        TestHelper.clearDatabase(connection);

        status = new ExpenseEntryStatus();
        status.setCreationDate(new Date());
        status.setModificationDate(new Date());
        status.setDescription("Description");
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed. The manager instance is disposed. The data
     * table is truncated. The expense entry status instance is disposed.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.clearDatabase(connection);

        connection.close();

        configManager = null;

        try {
            manager.getStatusPersistence().closeConnection();
        } catch (Exception e) {
            // ignore
        }

        manager = null;

        connection = null;
        status = null;
        factory = null;
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the given namespace is <code>null</code>. Expect
     * NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerNull() throws Exception {
        try {
            new ExpenseEntryStatusManager(null);
            fail("The namespace is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the given namespace is empty string. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerEmpty() throws Exception {
        try {
            new ExpenseEntryStatusManager("  ");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the given namespace does not exist. Expect
     * ConfigurationException.
     * </p>
     */
    public void testExpenseEntryStatusManagerNamespaceNotExist() {
        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail("The namespace does not exist, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the persistence class name is missing in
     * configuration. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerClassMissing()
        throws Exception {
        configManager.add("classmissing.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail("The persistence class name is missing, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the persistence class name is empty string in
     * configuration. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerClassEmpty()
        throws Exception {
        configManager.add("classempty.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail("The persistence class name is empty string, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the persistence class cannot be found. Expect
     * ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerClassUnknown()
        throws Exception {
        configManager.add("classunknown.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail("The persistence class name cannot be found, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the persistence class cannot be instantiated.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerNotClass()
        throws Exception {
        configManager.add("notclass.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail("The persistence class name cannot be instantiated, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the persistence class does not have a proper
     * constructor. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerNoProperConstructor()
        throws Exception {
        configManager.add("noconstructor.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail(
                "The persistence class name does not have a proper constructor, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the class is not a persistence class. Expect
     * ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerClassNotPersistence()
        throws Exception {
        configManager.add("notpersistence.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail("The class is not a persistence class, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the namespace used to create DB connection
     * factory is missing. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerFactoryNamespaceMissing()
        throws Exception {
        configManager.add("DBmissing.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail(
                "The namespace used to create DB connection factory is missing, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the namespace used to create DB connection
     * factory is empty string. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerFactoryNamespaceEmpty()
        throws Exception {
        configManager.add("DBempty.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail("The namespace used to create DB connection factory is empty string, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the namespace used to create DB connection
     * factory cannot be found in configuration. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerFactoryNamespaceUnknown()
        throws Exception {
        configManager.add("DBunknown.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail("The namespace used to create DB connection factory cannot be found, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the configuration for DB connection factory
     * contains error. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerFactoryConfigurationError()
        throws Exception {
        if (configManager.existsNamespace("com.topcoder.timetracker.entry.expense.connectionA")) {
            configManager.removeNamespace("com.topcoder.timetracker.entry.expense.connectionA");
        }
        configManager.add("DBerror.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail("The configuration for DB connection factory contains error, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusManager</code> when the producer name cannot be found in DB
     * connection factory. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerProducerNameUnknown()
        throws Exception {
        configManager.add("producerunknown.xml");

        try {
            new ExpenseEntryStatusManager(NAMESPACE);
            fail("The producer name cannot be found in DB connection factory, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the given expense status is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusNull() throws Exception {
        try {
            manager.addStatus(null);
            fail("The expense status is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the creation date is already set. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusCreationDateNotNull() throws Exception {
        status = new ExpenseEntryStatus();
        status.setDescription("Description");
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setCreationDate(new Date());

        try {
            manager.addStatus(status);
            fail("The creation date is not null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the modification date is already set. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusModificationDateNotNull()
        throws Exception {
        status = new ExpenseEntryStatus();
        status.setDescription("Description");
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setModificationDate(new Date());

        try {
            manager.addStatus(status);
            fail("The modification date is not null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the creation user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusCreationUserNull() throws Exception {
        status = new ExpenseEntryStatus();
        status.setDescription("Description");
        status.setModificationUser("Modify");

        try {
            manager.addStatus(status);
            fail("The creation user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the modification user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusModificationUserNull() throws Exception {
        status = new ExpenseEntryStatus();
        status.setDescription("Description");
        status.setCreationUser("Create");

        try {
            manager.addStatus(status);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the description is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusDescriptionNull() throws Exception {
        status = new ExpenseEntryStatus();
        status.setCreationUser("Create");
        status.setModificationUser("Modify");

        try {
            manager.addStatus(status);
            fail("The description is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getStatusPersistence().setConnection(conn);

        status = new ExpenseEntryStatus();
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        try {
            manager.addStatus(status);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteStatus</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteStatusPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getStatusPersistence().setConnection(conn);

        try {
            manager.deleteStatus(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteAllStatuses</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllStatusesPersistenceError()
        throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getStatusPersistence().setConnection(conn);

        try {
            manager.deleteAllStatuses();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateStatus</code> when the given expense status is <code>null</code>. Expect
     * NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatusNull() throws Exception {
        try {
            manager.updateStatus(null);
            fail("The expense status is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateStatus</code> when the creation user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatusCreationUserNull() throws Exception {
        status = new ExpenseEntryStatus(5);
        status.setDescription("Description");
        status.setModificationUser("Modify");

        try {
            manager.updateStatus(status);
            fail("The creation user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateStatus</code> when the modification user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatusModificationUserNull()
        throws Exception {
        status = new ExpenseEntryStatus(5);
        status.setDescription("Description");
        status.setCreationUser("Create");

        try {
            manager.updateStatus(status);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateStatus</code> when the description is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatusDescriptionNull() throws Exception {
        status = new ExpenseEntryStatus(5);
        status.setCreationUser("Create");
        status.setModificationUser("Modify");

        try {
            manager.updateStatus(status);
            fail("The description is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateStatus</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatusPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getStatusPersistence().setConnection(conn);

        status = new ExpenseEntryStatus();
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        try {
            manager.updateStatus(status);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveStatus</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveStatusPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getStatusPersistence().setConnection(conn);

        try {
            manager.retrieveStatus(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveAllStatuses</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllStatusesPersistenceError()
        throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getStatusPersistence().setConnection(conn);

        try {
            manager.retrieveAllStatuses();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryStatusManager</code> when the connection producer name is
     * specified. The constructor of persistence class with two string arguments should be called. The connection
     * producer name and namespace in persistence class should be set correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerAccuracy()
        throws Exception {
        configManager.add("Valid.xml");
        manager = new ExpenseEntryStatusManager(NAMESPACE);

        ExpenseEntryStatusPersistence persistence = manager.getStatusPersistence();

        assertTrue("The persistence should be ExpenseEntryStatusDbPersistence instance.",
            persistence instanceof ExpenseEntryStatusDbPersistence);

        Field field = persistence.getClass().getDeclaredField("connectionProducerName");

        try {
            field.setAccessible(true);
            assertEquals("The connection producer name should be correct.", "Connection", field.get(persistence));
        } finally {
            field.setAccessible(false);
        }

        field = persistence.getClass().getDeclaredField("namespace");

        try {
            field.setAccessible(true);
            assertEquals("The namespace to create DB connection should be correct.", DB_NAMESPACE,
                field.get(persistence));
        } finally {
            field.setAccessible(false);
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryStatusManager</code> when the connection producer name is not
     * specified. The constructor of persistence class with one string arguments should be called. The connection
     * producer name should be <code>null</code>. The namespace in persistence class should be set correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusManagerNoConnectionProducerAccuracy()
        throws Exception {
        configManager.add("ValidProducerMissing.xml");
        manager = new ExpenseEntryStatusManager(NAMESPACE);

        ExpenseEntryStatusPersistence persistence = manager.getStatusPersistence();

        assertTrue("The persistence should be ExpenseEntryStatusDbPersistence instance.",
            persistence instanceof ExpenseEntryStatusDbPersistence);

        Field field = persistence.getClass().getDeclaredField("connectionProducerName");

        try {
            field.setAccessible(true);
            assertNull("The connection producer name should be null.", field.get(persistence));
        } finally {
            field.setAccessible(false);
        }

        field = persistence.getClass().getDeclaredField("namespace");

        try {
            field.setAccessible(true);
            assertEquals("The namespace to create DB connection should be correct.", DB_NAMESPACE,
                field.get(persistence));
        } finally {
            field.setAccessible(false);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addStatus</code> when ID is -1. A new ID is generated. A new record should be inserted
     * into the database. The creation date and modification date should be set the same. The method should return
     * <code>true</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusAccuracy() throws Exception {
        status = new ExpenseEntryStatus();
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        assertTrue("The status should be added.", manager.addStatus(status));

        // Verify instance
        assertTrue("A ID should be generated.", status.getId() != -1);
        assertNotNull("The creation date should be set.", status.getCreationDate());
        assertNotNull("The modification date should be set.", status.getModificationDate());
        assertEquals("The creation date and modification date should be the same.", status.getCreationDate(),
            status.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", status.getId(), resultSet.getInt("expense_status_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            TestHelper.assertEquals("The creation date should be correct.", status.getCreationDate(),
                resultSet.getDate("creation_date"));
            TestHelper.assertEquals("The modification date should be correct.", status.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify",
                resultSet.getString("modification_user"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addStatus</code> when ID is not -1. The ID should be used. A new record should be
     * inserted into the database. The creation date and modification date should be set the same. The method should
     * return <code>true</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusIDSetAccuracy() throws Exception {
        status = new ExpenseEntryStatus(5);
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        assertTrue("The status should be added.", manager.addStatus(status));

        // Verify instance
        assertEquals("The ID should be used.", 5, status.getId());
        assertNotNull("The creation date should be set.", status.getCreationDate());
        assertNotNull("The modification date should be set.", status.getModificationDate());
        assertEquals("The creation date and modification date should be the same.", status.getCreationDate(),
            status.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", status.getId(), resultSet.getInt("expense_status_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            TestHelper.assertEquals("The creation date should be correct.", status.getCreationDate(),
                resultSet.getDate("creation_date"));
            TestHelper.assertEquals("The modification date should be correct.", status.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify",
                resultSet.getString("modification_user"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addStatus</code> when ID already exists in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusExistAccuracy() throws Exception {
        status = new ExpenseEntryStatus(5);
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        manager.addStatus(status);

        // Add again, should return false.
        status = new ExpenseEntryStatus(5);
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        assertFalse("The ID exists in database.", manager.addStatus(status));
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteStatus</code> when ID exists in database. The method should return
     * <code>true</code>. The record should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteStatusAccuracy() throws Exception {
        status = new ExpenseEntryStatus(5);
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        manager.addStatus(status);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", manager.deleteStatus(status.getId()));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertFalse("No record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteAllStatuses</code>. All records should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllStatusesAccuracy() throws Exception {
        // Add two records
        status = new ExpenseEntryStatus();
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        manager.addStatus(status);

        status = new ExpenseEntryStatus();
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        manager.addStatus(status);

        // Delete
        manager.deleteAllStatuses();

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertFalse("No record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteStatus</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteStatusIDNotExistAccuracy() throws Exception {
        assertFalse("The ID does not exist in database.", manager.deleteStatus(5));
    }

    /**
     * <p>
     * Tests accuracy of <code>updateStatus</code> when ID exists in database. The method should return
     * <code>true</code>. The modification date should be modified. The record in database should be updated. The
     * creation user and creation date should not be modified.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatusAccuracy() throws Exception {
        status = new ExpenseEntryStatus(5);
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        manager.addStatus(status);

        ExpenseEntryStatus update = new ExpenseEntryStatus(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");

        assertTrue("The record should be updated.", manager.updateStatus(update));

        // Verify instance
        assertNull("The creation date should not be modified.", update.getCreationDate());
        assertNotNull("The modification date should be set.", update.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getInt("expense_status_id"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("description"));
            TestHelper.assertEquals("The creation date should not be modified.", status.getCreationDate(),
                resultSet.getDate("creation_date"));
            TestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should not be modified.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify2",
                resultSet.getString("modification_user"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateStatus</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatusIDNotExistAccuracy() throws Exception {
        status = new ExpenseEntryStatus(5);
        status.setCreationUser("Create2");
        status.setModificationUser("Modify2");
        status.setDescription("Modified");

        assertFalse("The record should not be updated.", manager.updateStatus(status));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveStatus</code> when ID does not exist in database. <code>null</code> should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveStatusIDNotExistAccuracy()
        throws Exception {
        assertNull("The ID does not exist in database, null should return.", manager.retrieveStatus(5));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveStatus</code> when ID exists in database. The correct instance should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveStatusAccuracy() throws Exception {
        status = new ExpenseEntryStatus(5);
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        manager.addStatus(status);

        ExpenseEntryStatus retrieved = manager.retrieveStatus(5);

        TestHelper.assertEquals(status, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllStatuses</code> when no record exists in database. An empty list should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllStatusesNoRecordAccuracy()
        throws Exception {
        assertTrue("The retrieved list should be empty.", manager.retrieveAllStatuses().isEmpty());
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllStatuses</code>. The correct list of instances should be returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllStatusesAccuracy() throws Exception {
        List expected = new ArrayList();

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            status = new ExpenseEntryStatus(i);
            status.setCreationUser("Create" + i);
            status.setModificationUser("Modify" + i);
            status.setDescription("Description" + i);

            manager.addStatus(status);
            expected.add(status);
        }

        List actual = manager.retrieveAllStatuses();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.size(), actual.size());

        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < expected.size(); ++i) {
            TestHelper.assertEquals((ExpenseEntryStatus) expected.get(i), (ExpenseEntryStatus) actual.get(i));
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>getStatusPersistence</code>. The persistence instance should be correct.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetStatusPersistenceAccuracy() throws Exception {
        Field field = manager.getClass().getDeclaredField("statusPersistence");

        try {
            field.setAccessible(true);
            assertSame("The persistence instance should be correct.", field.get(manager),
                manager.getStatusPersistence());
        } finally {
            field.setAccessible(false);
        }
    }

    /**
     * <p>
     * Test searchEntries(Criteria criteria), with criteria is null,
     * IllegalArugmentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchEntriesCriteriaIsNull() throws Exception {
        try {
            manager.searchEntries(null);
            fail("criteria is null, IllegalArugmentException is expected.");
        } catch (IllegalArgumentException e) {
            //good.
        }
    }

    /**
     * <p>
     * Test searchEntries(Criteria criteria), with criteria is null,
     * IllegalArugmentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchEntries() throws Exception {
        status = new ExpenseEntryStatus();
        status.setDescription("search");
        status.setCreationUser("admin");
        status.setModificationUser("Modify");
        manager.addStatus(status);
        status = new ExpenseEntryStatus();
        status.setDescription("search");
        status.setCreationUser("client");
        status.setModificationUser("Modify");
        manager.addStatus(status);

        Criteria criteria = FieldMatchCriteria.getExpenseStatusCreationUserMatchCriteria("admin");
        ExpenseEntryStatus[] status = manager.searchEntries(criteria);
        assertEquals("One record should be matched", 1, status.length);
    }

    /**
     * <p>
     * Aggragates all tests in this class.
     * </p>
     *
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryStatusManagerTestCase.class);
    }
}






