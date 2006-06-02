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

import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryTypeDbPersistence;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryTypePersistence;
import com.cronos.timetracker.entry.expense.persistence.PersistenceException;
import com.cronos.timetracker.entry.expense.search.CompositeCriteria;
import com.cronos.timetracker.entry.expense.search.Criteria;
import com.cronos.timetracker.entry.expense.search.FieldLikeCriteria;
import com.cronos.timetracker.entry.expense.search.FieldMatchCriteria;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryTypeManager</code> class. The test uses
 * <code>ExpenseEntryTypeDbPersistence</code> as the underlying persistence.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class ExpenseEntryTypeManagerTestCase extends TestCase {
    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /** Represents the namespace to load wrong DB connection factory configuration. */
    private static final String WRONG_DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connectionA";

    /** Represents the configuration manager instance used to load and delete configuration. */
    private ConfigManager configManager;

    /** Represents the manager instance used in tests. */
    private ExpenseEntryTypeManager manager;

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a DB connection factory to create connections. */
    private DBConnectionFactory factory;

    /** Represents a valid expense entry type instance. */
    private ExpenseEntryType type;

    /**
     * <p>
     * Sets up the test environment. The configurations are removed. A valid manager is created. The data table is
     * truncated. A valid expense entry type is created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        configManager = ConfigManager.getInstance();

        if (configManager.existsNamespace(WRONG_DB_NAMESPACE)) {
            configManager.removeNamespace(WRONG_DB_NAMESPACE);
        }

        TestHelper.clearConfiguration();
        TestHelper.addValidConfiguration();

        manager = new ExpenseEntryTypeManager(NAMESPACE);
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();

        TestHelper.clearDatabase(connection);

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }

        V1Dot1TestHelper.executeSQL("insert into company values(1, 'a', 'a', current, 'a', current, 'a')", connection);
        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setCreationDate(new Date());
        type.setModificationDate(new Date());
        type.setDescription("Description");
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed. The manager instance is disposed. The data
     * table is truncated. The expense entry type instance is disposed.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.clearDatabase(connection);

        if (configManager.existsNamespace(WRONG_DB_NAMESPACE)) {
            configManager.removeNamespace(WRONG_DB_NAMESPACE);
        }

        connection.close();

        configManager = null;

        try {
            manager.getTypePersistence().closeConnection();
        } catch (Exception e) {
            // ignore
        }

        manager = null;

        connection = null;
        type = null;
        factory = null;
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the given namespace is <code>null</code>. Expect
     * NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerNull() throws Exception {
        try {
            new ExpenseEntryTypeManager(null);
            fail("The namespace is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the given namespace is empty string. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerEmpty() throws Exception {
        try {
            new ExpenseEntryTypeManager("  ");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the given namespace does not exist. Expect
     * ConfigurationException.
     * </p>
     */
    public void testExpenseEntryTypeManagerNamespaceNotExist() {
        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail("The namespace does not exist, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the persistence class name is missing in
     * configuration. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerClassMissing()
        throws Exception {
        configManager.add("classmissing.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail("The persistence class name is missing, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the persistence class name is empty string in
     * configuration. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerClassEmpty()
        throws Exception {
        configManager.add("classempty.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail("The persistence class name is empty string, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the persistence class cannot be found. Expect
     * ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerClassUnknown()
        throws Exception {
        configManager.add("classunknown.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail("The persistence class name cannot be found, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the persistence class cannot be instantiated.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerNotClass() throws Exception {
        configManager.add("notclass.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail("The persistence class name cannot be instantiated, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the persistence class does not have a proper
     * constructor. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerNoProperConstructor()
        throws Exception {
        configManager.add("noconstructor.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail(
                "The persistence class name does not have a proper constructor, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the class is not a persistence class. Expect
     * ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerClassNotPersistence()
        throws Exception {
        configManager.add("notpersistence.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail("The class is not a persistence class, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the namespace used to create DB connection factory
     * is missing. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerFactoryNamespaceMissing()
        throws Exception {
        configManager.add("DBmissing.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail(
                "The namespace used to create DB connection factory is missing, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the namespace used to create DB connection factory
     * is empty string. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerFactoryNamespaceEmpty()
        throws Exception {
        configManager.add("DBempty.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail("The namespace used to create DB connection factory is empty string, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the namespace used to create DB connection factory
     * cannot be found in configuration. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerFactoryNamespaceUnknown()
        throws Exception {
        configManager.add("DBunknown.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail("The namespace used to create DB connection factory cannot be found, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the configuration for DB connection factory
     * contains error. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerFactoryConfigurationError()
        throws Exception {
        configManager.add("DBerror.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail("The configuration for DB connection factory contains error, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeManager</code> when the producer name cannot be found in DB connection
     * factory. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerProducerNameUnknown()
        throws Exception {
        configManager.add("producerunknown.xml");

        try {
            new ExpenseEntryTypeManager(NAMESPACE);
            fail("The producer name cannot be found in DB connection factory, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the given expense type is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeNull() throws Exception {
        try {
            manager.addType(null);
            fail("The expense type is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the creation date is already set. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeCreationDateNotNull() throws Exception {
        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setDescription("Description");
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setCreationDate(new Date());

        try {
            manager.addType(type);
            fail("The creation date is not null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the modification date is already set. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeModificationDateNotNull() throws Exception {
        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setDescription("Description");
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setModificationDate(new Date());

        try {
            manager.addType(type);
            fail("The modification date is not null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the creation user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeCreationUserNull() throws Exception {
        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setDescription("Description");
        type.setModificationUser("Modify");

        try {
            manager.addType(type);
            fail("The creation user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the modification user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeModificationUserNull() throws Exception {
        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setDescription("Description");
        type.setCreationUser("Create");

        try {
            manager.addType(type);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the description is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeDescriptionNull() throws Exception {
        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");

        try {
            manager.addType(type);
            fail("The description is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypePersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getTypePersistence().setConnection(conn);

        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        try {
            manager.addType(type);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteType</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteTypePersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getTypePersistence().setConnection(conn);

        try {
            manager.deleteType(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteAllTypees</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllTypeesPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getTypePersistence().setConnection(conn);

        try {
            manager.deleteAllTypes();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when the given expense type is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateTypeNull() throws Exception {
        try {
            manager.updateType(null);
            fail("The expense type is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when the creation user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateTypeCreationUserNull() throws Exception {
        type = new ExpenseEntryType(5);
        type.setCompanyId(1);
        type.setDescription("Description");
        type.setModificationUser("Modify");

        try {
            manager.updateType(type);
            fail("The creation user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when the modification user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateTypeModificationUserNull() throws Exception {
        type = new ExpenseEntryType(5);
        type.setCompanyId(1);
        type.setDescription("Description");
        type.setCreationUser("Create");

        try {
            manager.updateType(type);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when the description is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateTypeDescriptionNull() throws Exception {
        type = new ExpenseEntryType(5);
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");

        try {
            manager.updateType(type);
            fail("The description is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateTypePersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getTypePersistence().setConnection(conn);

        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        try {
            manager.updateType(type);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveType</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveTypePersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getTypePersistence().setConnection(conn);

        try {
            manager.retrieveType(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveAllTypees</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllTypeesPersistenceError()
        throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getTypePersistence().setConnection(conn);

        try {
            manager.retrieveAllTypes();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryTypeManager</code> when the connection producer name is
     * specified. The constructor of persistence class with two string arguments should be called. The connection
     * producer name and namespace in persistence class should be set correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerAccuracy() throws Exception {
        configManager.add("Valid.xml");
        manager = new ExpenseEntryTypeManager(NAMESPACE);

        ExpenseEntryTypePersistence persistence = manager.getTypePersistence();

        assertTrue("The persistence should be ExpenseEntryTypeDbPersistence instance.",
            persistence instanceof ExpenseEntryTypeDbPersistence);

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
     * Tests accuracy of constructor <code>ExpenseEntryTypeManager</code> when the connection producer name is not
     * specified. The constructor of persistence class with one string arguments should be called. The connection
     * producer name should be <code>null</code>. The namespace in persistence class should be set correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeManagerNoConnectionProducerAccuracy()
        throws Exception {
        configManager.add("ValidProducerMissing.xml");
        manager = new ExpenseEntryTypeManager(NAMESPACE);

        ExpenseEntryTypePersistence persistence = manager.getTypePersistence();

        assertTrue("The persistence should be ExpenseEntryTypeDbPersistence instance.",
            persistence instanceof ExpenseEntryTypeDbPersistence);

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
     * Tests accuracy of <code>addType</code> when ID is -1. A new ID is generated. A new record should be inserted
     * into the database. The creation date and modification date should be set the same. The method should return
     * <code>true</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeAccuracy() throws Exception {
        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        assertTrue("The type should be added.", manager.addType(type));

        // Verify instance
        assertTrue("A ID should be generated.", type.getId() != -1);
        assertNotNull("The creation date should be set.", type.getCreationDate());
        assertNotNull("The modification date should be set.", type.getModificationDate());
        assertEquals("The creation date and modification date should be the same.", type.getCreationDate(),
            type.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", type.getId(), resultSet.getInt("expense_type_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            TestHelper.assertEquals("The creation date should be correct.", type.getCreationDate(),
                resultSet.getDate("creation_date"));
            TestHelper.assertEquals("The modification date should be correct.", type.getModificationDate(),
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
     * Tests accuracy of <code>addType</code> when ID is not -1. The ID should be used. A new record should be
     * inserted into the database. The creation date and modification date should be set the same. The method should
     * return <code>true</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeIDSetAccuracy() throws Exception {
        type = new ExpenseEntryType(5);
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        assertTrue("The type should be added.", manager.addType(type));

        // Verify instance
        assertEquals("The ID should be used.", 5, type.getId());
        assertNotNull("The creation date should be set.", type.getCreationDate());
        assertNotNull("The modification date should be set.", type.getModificationDate());
        assertEquals("The creation date and modification date should be the same.", type.getCreationDate(),
            type.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", type.getId(), resultSet.getInt("expense_type_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            TestHelper.assertEquals("The creation date should be correct.", type.getCreationDate(),
                resultSet.getDate("creation_date"));
            TestHelper.assertEquals("The modification date should be correct.", type.getModificationDate(),
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
     * Tests accuracy of <code>addType</code> when ID already exists in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeExistAccuracy() throws Exception {
        type = new ExpenseEntryType(5);
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        manager.addType(type);

        // Add again, should return false.
        type = new ExpenseEntryType(5);
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        assertFalse("The ID exists in database.", manager.addType(type));
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteType</code> when ID exists in database. The method should return
     * <code>true</code>. The record should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteTypeAccuracy() throws Exception {
        type = new ExpenseEntryType(5);
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        manager.addType(type);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", manager.deleteType(type.getId()));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

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
     * Tests accuracy of <code>deleteAllTypees</code>. All records should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllTypeesAccuracy() throws Exception {
        // Add two records
        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        manager.addType(type);

        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        manager.addType(type);

        // Delete
        manager.deleteAllTypes();

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

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
     * Tests accuracy of <code>deleteType</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteTypeIDNotExistAccuracy() throws Exception {
        assertFalse("The ID does not exist in database.", manager.deleteType(5));
    }

    /**
     * <p>
     * Tests accuracy of <code>updateType</code> when ID exists in database. The method should return
     * <code>true</code>. The modification date should be modified. The record in database should be updated. The
     * creation user and creation date should not be modified.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateTypeAccuracy() throws Exception {
        type = new ExpenseEntryType(5);
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");
        manager.addType(type);

        ExpenseEntryType update = new ExpenseEntryType(5);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");

        assertTrue("The record should be updated.", manager.updateType(update));

        // Verify instance
        assertNull("The creation date should not be modified.", update.getCreationDate());
        assertNotNull("The modification date should be set.", update.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getInt("expense_type_id"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("description"));
            TestHelper.assertEquals("The creation date should not be modified.", type.getCreationDate(),
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
     * Tests accuracy of <code>updateType</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateTypeIDNotExistAccuracy() throws Exception {
        type = new ExpenseEntryType(5);
        type.setCompanyId(1);
        type.setCreationUser("Create2");
        type.setModificationUser("Modify2");
        type.setDescription("Modified");

        assertFalse("The record should not be updated.", manager.updateType(type));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveType</code> when ID does not exist in database. <code>null</code> should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveTypeIDNotExistAccuracy() throws Exception {
        assertNull("The ID does not exist in database, null should return.", manager.retrieveType(5));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveType</code> when ID exists in database. The correct instance should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveTypeAccuracy() throws Exception {
        type = new ExpenseEntryType(5);
        type.setCompanyId(1);
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        manager.addType(type);

        ExpenseEntryType retrieved = manager.retrieveType(5);

        TestHelper.assertEquals(type, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllTypees</code> when no record exists in database. An empty list should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllTypeesNoRecordAccuracy()
        throws Exception {
        assertTrue("The retrieved list should be empty.", manager.retrieveAllTypes().isEmpty());
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllTypees</code>. The correct list of instances should be returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllTypeesAccuracy() throws Exception {
        List expected = new ArrayList();

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            type = new ExpenseEntryType(i);
            type.setCompanyId(1);
            type.setCreationUser("Create" + i);
            type.setModificationUser("Modify" + i);
            type.setDescription("Description" + i);

            manager.addType(type);
            expected.add(type);
        }

        List actual = manager.retrieveAllTypes();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.size(), actual.size());

        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < expected.size(); ++i) {
            TestHelper.assertEquals((ExpenseEntryType) expected.get(i), (ExpenseEntryType) actual.get(i));
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>getTypePersistence</code>. The persistence instance should be correct.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetTypePersistenceAccuracy() throws Exception {
        Field field = manager.getClass().getDeclaredField("typePersistence");

        try {
            field.setAccessible(true);
            assertSame("The persistence instance should be correct.", field.get(manager), manager.getTypePersistence());
        } finally {
            field.setAccessible(false);
        }
    }

    /**
     * <p>
     * Test ExpenseEntryType[] searchEntries(Criteria criteria),
     * the types matched should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchEntries() throws Exception {
        for (int i = 0; i < 5; ++i) {
            type = new ExpenseEntryType(i);
            type.setCompanyId(1);
            type.setCreationUser("Create" + i);
            type.setModificationUser("Modify" + i);
            if (i % 3 == 0) {
                type.setDescription("Description" + i);
            } else {
                type.setDescription("xxxx");
            }
            manager.addType(type);
        }
        Criteria c1 = FieldMatchCriteria.getExpenseTypeCompanyIdMatchCriteria(1);
        Criteria c2 = FieldLikeCriteria.getExpenseTypeDescriptionContainsCriteria("Des");
        Criteria criteria = CompositeCriteria.getAndCompositeCriteria(new Criteria[] {c1, c2});

        ExpenseEntryType[] types = manager.searchEntries(criteria);
        assertEquals("Failed to get the expected result.", 2, types.length);
    }

    /**
     * <p>
     * Aggragates all tests in this class.
     * </p>
     *
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryTypeManagerTestCase.class);
    }
}
