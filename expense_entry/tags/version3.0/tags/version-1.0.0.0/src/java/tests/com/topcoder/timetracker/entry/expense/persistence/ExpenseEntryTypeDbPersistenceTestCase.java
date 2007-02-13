/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.timetracker.entry.expense.TestHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryTypeManager</code> class. The test uses
 * <code>ExpenseEntryTypeDbPersistence</code> as the underlying persistence.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class ExpenseEntryTypeDbPersistenceTestCase extends TestCase {
    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /** Represents the manager instance used in tests. */
    private ExpenseEntryTypePersistence persistence;

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a DB connection factory to create connections. */
    private DBConnectionFactory factory;

    /** Represents a valid expense entry type instance. */
    private ExpenseEntryType type;

    /**
     * <p>
     * Sets up the test environment. The valid configurations are loaded. A valid manager is created. The data table
     * is truncated. A valid expense entry type is created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.addValidConfiguration();

        persistence = new ExpenseEntryTypeDbPersistence("Connection", DB_NAMESPACE);
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();

        TestHelper.clearDatabase(connection);

        type = new ExpenseEntryType(5);
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

        connection.close();

        try {
            persistence.closeConnection();
        } catch (Exception e) {
            // ignore
        }

        persistence = null;
        connection = null;
        type = null;
        factory = null;
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeDbPersistence(String)</code> when the given namespace is
     * <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeDbPersistenceStringNull()
        throws Exception {
        try {
            new ExpenseEntryDbPersistence(null);
            fail("The namespace is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeDbPersistence(String)</code> when the given namespace is empty string.
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeDbPersistenceStringEmpty()
        throws Exception {
        try {
            new ExpenseEntryDbPersistence("  ");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeDbPersistence(String)</code> when the given namespace is empty string.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testExpenseEntryTypeDbPersistenceStringFactoryError() {
        try {
            new ExpenseEntryDbPersistence("Unknown");
            fail("The DB connection factory cannot be created, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeDbPersistence(String, String)</code> when the given namespace is
     * <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeDbPersistenceStringStringNamespaceNull()
        throws Exception {
        try {
            new ExpenseEntryDbPersistence("Connection", null);
            fail("The namespace is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeDbPersistence(String, String)</code> when the given namespace is empty
     * string. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeDbPersistenceStringStringNamespaceEmpty()
        throws Exception {
        try {
            new ExpenseEntryDbPersistence("Connection", "  ");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeDbPersistence(String, String)</code> when the given connection
     * producer name is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeDbPersistenceStringStringNameNull()
        throws Exception {
        try {
            new ExpenseEntryDbPersistence(null, DB_NAMESPACE);
            fail("The connection producer name is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeDbPersistence(String, String)</code> when the given connection
     * producer name is empty string. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeDbPersistenceStringStringNameEmpty()
        throws Exception {
        try {
            new ExpenseEntryDbPersistence("  ", DB_NAMESPACE);
            fail("The connection producer name is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeDbPersistence(String, String)</code> when the given connection
     * producer name does not exist in DB connection factory. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeDbPersistenceStringStringNameUnknown()
        throws Exception {
        try {
            new ExpenseEntryDbPersistence("Unknown", DB_NAMESPACE);
            fail("The connection producer name does not exist, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryTypeDbPersistence(String, String)</code> DB connection factory cannot be
     * created. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeDbPersistenceStringStringFactoryError()
        throws Exception {
        try {
            new ExpenseEntryDbPersistence("Connection", "Unknown");
            fail("The DB connection factory cannot be created, should throw PersistenceException.");
        } catch (PersistenceException e) {
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
            persistence.addType(null);
            fail("The expense type is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
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
        persistence.setConnection(conn);

        type = new ExpenseEntryType();
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        try {
            persistence.addType(type);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeNoConnection() throws Exception {
        persistence = new ExpenseEntryTypeDbPersistence(DB_NAMESPACE);

        try {
            persistence.addType(type);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
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
        persistence.setConnection(conn);

        try {
            persistence.deleteType(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteType</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteTypeNoConnection() throws Exception {
        persistence = new ExpenseEntryTypeDbPersistence(DB_NAMESPACE);

        try {
            persistence.deleteType(0);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteAllTypes</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllTypesPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.deleteAllTypes();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteAllTypes</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllTypesNoConnection() throws Exception {
        persistence = new ExpenseEntryTypeDbPersistence(DB_NAMESPACE);

        try {
            persistence.deleteAllTypes();
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
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
            persistence.updateType(null);
            fail("The expense type is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
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
        persistence.setConnection(conn);

        type = new ExpenseEntryType();
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setDescription("Description");

        try {
            persistence.updateType(type);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateTypeNoConnection() throws Exception {
        persistence = new ExpenseEntryTypeDbPersistence(DB_NAMESPACE);

        try {
            persistence.updateType(type);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
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
        persistence.setConnection(conn);

        try {
            persistence.retrieveType(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveType</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveTypeNoConnection() throws Exception {
        persistence = new ExpenseEntryTypeDbPersistence(DB_NAMESPACE);

        try {
            persistence.retrieveType(0);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveAllTypes</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllTypesPersistenceError()
        throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.retrieveAllTypes();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveAllTypes</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllTypesNoConnection() throws Exception {
        persistence = new ExpenseEntryTypeDbPersistence(DB_NAMESPACE);

        try {
            persistence.retrieveAllTypes();
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setConnection</code> when the given connection is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSetConnectionNull() throws Exception {
        try {
            persistence.setConnection(null);
            fail("The connection is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>initConnection</code> when the given connection producer name is <code>null</code>. Expect
     * NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInitConnectionNull() throws Exception {
        try {
            persistence.initConnection(null);
            fail("The connection producer name is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>initConnection</code> when the given connection producer name is empty string. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInitConnectionEmpty() throws Exception {
        try {
            persistence.initConnection("  ");
            fail("The connection producer name is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>initConnection</code> when the given connection producer name is unknown. Expect
     * PersistenceException.
     * </p>
     */
    public void testInitConnectionUnknown() {
        try {
            persistence.initConnection("Unknown");
            fail("The connection producer name is unknown, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryTypeDbPersistence(String)</code>. The namespace should be set
     * correctly. The connection producer name should be <code>null</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeDbPersistenceStringAccuracy()
        throws Exception {
        persistence = new ExpenseEntryTypeDbPersistence(DB_NAMESPACE);

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
     * Tests accuracy of constructor <code>ExpenseEntryTypeDbPersistence(String, String)</code>. The namespace and
     * conection producer name should be set correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryTypeDbPersistenceStringStringAccuracy()
        throws Exception {
        persistence = new ExpenseEntryTypeDbPersistence("Connection", DB_NAMESPACE);

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
     * Tests accuracy of <code>addType</code>. A new record should be inserted into the database. The method should
     * return <code>true</code>. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddTypeAccuracy() throws Exception {
        assertTrue("The type should be added.", persistence.addType(type));

        assertNotNull("A connection should be created.", persistence.getConnection());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseTypes");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", type.getId(), resultSet.getInt("ExpenseTypesID"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("Description"));
            TestHelper.assertEquals("The creation date should be correct.", type.getCreationDate(),
                resultSet.getDate("CreationDate"));
            TestHelper.assertEquals("The modification date should be correct.", type.getModificationDate(),
                resultSet.getDate("ModificationDate"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("CreationUser"));
            assertEquals("The modification user should be correct.", "Modify", resultSet.getString("ModificationUser"));

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
        persistence.addType(type);

        // Add again, should return false.
        assertFalse("The ID exists in database.", persistence.addType(type));
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
        persistence.addType(type);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", persistence.deleteType(type.getId()));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseTypes");

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
     * Tests accuracy of <code>deleteAllTypes</code>. All records should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllTypesAccuracy() throws Exception {
        // Add two records
        persistence.addType(type);

        type = new ExpenseEntryType(6);
        type.setCreationDate(new Date());
        type.setModificationDate(new Date());
        type.setDescription("Description");
        type.setCreationUser("Create");
        type.setModificationUser("Modify");

        persistence.addType(type);

        // Delete
        persistence.deleteAllTypes();

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseTypes");

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
     * <code>false</code>. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteTypeIDNotExistAccuracy() throws Exception {
        assertFalse("The ID does not exist in database.", persistence.deleteType(5));

        assertNotNull("A connection should be created.", persistence.getConnection());
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
        persistence.addType(type);

        ExpenseEntryType update = new ExpenseEntryType(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setModificationDate(new Date());

        assertTrue("The record should be updated.", persistence.updateType(update));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseTypes");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getInt("ExpenseTypesID"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("Description"));
            TestHelper.assertEquals("The creation date should not be modified.", type.getCreationDate(),
                resultSet.getDate("CreationDate"));
            TestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
                resultSet.getDate("ModificationDate"));
            assertEquals("The creation user should not be modified.", "Create", resultSet.getString("CreationUser"));
            assertEquals("The modification user should be correct.", "Modify2",
                resultSet.getString("ModificationUser"));

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
     * <code>false</code>. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateTypeIDNotExistAccuracy() throws Exception {
        assertFalse("The record should not be updated.", persistence.updateType(type));

        assertNotNull("A connection should be created.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveType</code> when ID does not exist in database. <code>null</code> should be
     * returned. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveTypeIDNotExistAccuracy() throws Exception {
        assertNull("The ID does not exist in database, null should return.", persistence.retrieveType(5));

        assertNotNull("A connection should be created.", persistence.getConnection());
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
        persistence.addType(type);

        ExpenseEntryType retrieved = persistence.retrieveType(5);

        TestHelper.assertEquals(type, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllTypes</code> when no record exists in database. An empty list should be
     * returned. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllTypesNoRecordAccuracy()
        throws Exception {
        assertTrue("The retrieved list should be empty.", persistence.retrieveAllTypes().isEmpty());

        assertNotNull("A connection should be created.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllTypes</code>. The correct list of instances should be returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllTypesAccuracy() throws Exception {
        List expected = new ArrayList();

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            type = new ExpenseEntryType(i);
            type.setCreationUser("Create" + i);
            type.setModificationUser("Modify" + i);
            type.setDescription("Description" + i);
            type.setCreationDate(new Date());
            type.setModificationDate(new Date());

            persistence.addType(type);
            expected.add(type);
        }

        List actual = persistence.retrieveAllTypes();

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
     * Tests accuracy of <code>getConnection</code> for initial value. Initially, the connection is not set.
     * </p>
     */
    public void testGetConnectionInitAccuracy() {
        assertNull("Initially, the connection is null.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of <code>setConnection</code>. The connection should be set correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSetConnectionAccuracy() throws Exception {
        persistence.setConnection(connection);

        assertSame("The connection should be set.", connection, persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of <code>initConnection</code>. The connection should be initialized correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInitConnectionAccuracy() throws Exception {
        persistence.initConnection("Connection");

        assertNotNull("The connection should be initialized.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of <code>closeConnection</code>. The connection should be closed and set to <code>null</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testCloseConnectionAccuracy() throws Exception {
        persistence.initConnection("Connection");

        persistence.closeConnection();

        assertNull("The connection should be set to null.", persistence.getConnection());
    }

    /**
     * <p>
     * Aggragates all tests in this class.
     * </p>
     *
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryTypeDbPersistenceTestCase.class);
    }
}


