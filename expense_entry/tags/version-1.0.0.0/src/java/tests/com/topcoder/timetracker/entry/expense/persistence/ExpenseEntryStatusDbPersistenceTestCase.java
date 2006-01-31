/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
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
 * Tests functionality and error cases of <code>ExpenseEntryStatusManager</code> class. The test uses
 * <code>ExpenseEntryStatusDbPersistence</code> as the underlying persistence.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class ExpenseEntryStatusDbPersistenceTestCase extends TestCase {
    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /** Represents the manager instance used in tests. */
    private ExpenseEntryStatusPersistence persistence;

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a DB connection factory to create connections. */
    private DBConnectionFactory factory;

    /** Represents a valid expense entry status instance. */
    private ExpenseEntryStatus status;

    /**
     * <p>
     * Sets up the test environment. The valid configurations are loaded. A valid manager is created. The data table
     * is truncated. A valid expense entry status is created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.addValidConfiguration();

        persistence = new ExpenseEntryStatusDbPersistence("Connection", DB_NAMESPACE);
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();

        TestHelper.clearDatabase(connection);

        status = new ExpenseEntryStatus(5);
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

        try {
            persistence.closeConnection();
        } catch (Exception e) {
            // ignore
        }

        persistence = null;
        connection = null;
        status = null;
        factory = null;
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusDbPersistence(String)</code> when the given namespace is
     * <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusDbPersistenceStringNull()
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
     * Tests constructor <code>ExpenseEntryStatusDbPersistence(String)</code> when the given namespace is empty
     * string. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusDbPersistenceStringEmpty()
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
     * Tests constructor <code>ExpenseEntryStatusDbPersistence(String)</code> when DB connection factory cannot be
     * created. Expect ConfigurationException.
     * </p>
     */
    public void testExpenseEntryStatusDbPersistenceStringFactoryError() {
        try {
            new ExpenseEntryDbPersistence("Unknown");
            fail("The DB connection factory cannot be created, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatusDbPersistence(String, String)</code> when the given namespace is
     * <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusDbPersistenceStringStringNamespaceNull()
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
     * Tests constructor <code>ExpenseEntryStatusDbPersistence(String, String)</code> when the given namespace is
     * empty string. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusDbPersistenceStringStringNamespaceEmpty()
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
     * Tests constructor <code>ExpenseEntryStatusDbPersistence(String, String)</code> when the given connection
     * producer name is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusDbPersistenceStringStringNameNull()
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
     * Tests constructor <code>ExpenseEntryStatusDbPersistence(String, String)</code> when the given connection
     * producer name is empty string. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusDbPersistenceStringStringNameEmpty()
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
     * Tests constructor <code>ExpenseEntryStatusDbPersistence(String, String)</code> when the given connection
     * producer name does not exist in DB connection factory. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusDbPersistenceStringStringNameUnknown()
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
     * Tests constructor <code>ExpenseEntryStatusDbPersistence(String, String)</code> DB connection factory cannot be
     * created. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusDbPersistenceStringStringFactoryError()
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
     * Tests <code>addStatus</code> when the given expense status is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusNull() throws Exception {
        try {
            persistence.addStatus(null);
            fail("The expense status is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
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
        persistence.setConnection(conn);

        status = new ExpenseEntryStatus();
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        try {
            persistence.addStatus(status);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusNoConnection() throws Exception {
        persistence = new ExpenseEntryStatusDbPersistence(DB_NAMESPACE);

        try {
            persistence.addStatus(status);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
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
        persistence.setConnection(conn);

        try {
            persistence.deleteStatus(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteStatus</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteStatusNoConnection() throws Exception {
        persistence = new ExpenseEntryStatusDbPersistence(DB_NAMESPACE);

        try {
            persistence.deleteStatus(0);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
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
        persistence.setConnection(conn);

        try {
            persistence.deleteAllStatuses();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteAllStatuses</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllStatusesNoConnection() throws Exception {
        persistence = new ExpenseEntryStatusDbPersistence(DB_NAMESPACE);

        try {
            persistence.deleteAllStatuses();
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
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
            persistence.updateStatus(null);
            fail("The expense status is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
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
        persistence.setConnection(conn);

        status = new ExpenseEntryStatus();
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
        status.setDescription("Description");

        try {
            persistence.updateStatus(status);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateStatus</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatusNoConnection() throws Exception {
        persistence = new ExpenseEntryStatusDbPersistence(DB_NAMESPACE);

        try {
            persistence.updateStatus(status);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
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
        persistence.setConnection(conn);

        try {
            persistence.retrieveStatus(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveStatus</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveStatusNoConnection() throws Exception {
        persistence = new ExpenseEntryStatusDbPersistence(DB_NAMESPACE);

        try {
            persistence.retrieveStatus(0);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
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
        persistence.setConnection(conn);

        try {
            persistence.retrieveAllStatuses();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveAllStatuses</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllStatusesNoConnection() throws Exception {
        persistence = new ExpenseEntryStatusDbPersistence(DB_NAMESPACE);

        try {
            persistence.retrieveAllStatuses();
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
     * Tests accuracy of constructor <code>ExpenseEntryStatusDbPersistence(String)</code>. The namespace should be
     * set correctly. The connection producer name should be <code>null</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusDbPersistenceStringAccuracy()
        throws Exception {
        persistence = new ExpenseEntryStatusDbPersistence(DB_NAMESPACE);

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
     * Tests accuracy of constructor <code>ExpenseEntryStatusDbPersistence(String, String)</code>. The namespace and
     * conection producer name should be set correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryStatusDbPersistenceStringStringAccuracy()
        throws Exception {
        persistence = new ExpenseEntryStatusDbPersistence("Connection", DB_NAMESPACE);

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
     * Tests accuracy of <code>addStatus</code>. A new record should be inserted into the database. The method should
     * return <code>true</code>. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusAccuracy() throws Exception {
        assertTrue("The status should be added.", persistence.addStatus(status));

        assertNotNull("A connection should be created.", persistence.getConnection());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseStatuses");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", status.getId(), resultSet.getInt("ExpenseStatusesID"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("Description"));
            TestHelper.assertEquals("The creation date should be correct.", status.getCreationDate(),
                resultSet.getDate("CreationDate"));
            TestHelper.assertEquals("The modification date should be correct.", status.getModificationDate(),
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
     * Tests accuracy of <code>addStatus</code> when ID already exists in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatusExistAccuracy() throws Exception {
        persistence.addStatus(status);

        // Add again, should return false.
        assertFalse("The ID exists in database.", persistence.addStatus(status));
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
        persistence.addStatus(status);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", persistence.deleteStatus(status.getId()));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseStatuses");

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
        persistence.addStatus(status);

        status = new ExpenseEntryStatus(6);
        status.setCreationDate(new Date());
        status.setModificationDate(new Date());
        status.setDescription("Description");
        status.setCreationUser("Create");
        status.setModificationUser("Modify");

        persistence.addStatus(status);

        // Delete
        persistence.deleteAllStatuses();

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseStatuses");

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
     * <code>false</code>. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteStatusIDNotExistAccuracy() throws Exception {
        assertFalse("The ID does not exist in database.", persistence.deleteStatus(5));

        assertNotNull("A connection should be created.", persistence.getConnection());
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
        persistence.addStatus(status);

        ExpenseEntryStatus update = new ExpenseEntryStatus(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setModificationDate(new Date());

        assertTrue("The record should be updated.", persistence.updateStatus(update));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseStatuses");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getInt("ExpenseStatusesID"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("Description"));
            TestHelper.assertEquals("The creation date should not be modified.", status.getCreationDate(),
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
     * Tests accuracy of <code>updateStatus</code> when ID does not exist in database. The method should return
     * <code>false</code>. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatusIDNotExistAccuracy() throws Exception {
        assertFalse("The record should not be updated.", persistence.updateStatus(status));

        assertNotNull("A connection should be created.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveStatus</code> when ID does not exist in database. <code>null</code> should be
     * returned. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveStatusIDNotExistAccuracy()
        throws Exception {
        assertNull("The ID does not exist in database, null should return.", persistence.retrieveStatus(5));

        assertNotNull("A connection should be created.", persistence.getConnection());
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
        persistence.addStatus(status);

        ExpenseEntryStatus retrieved = persistence.retrieveStatus(5);

        TestHelper.assertEquals(status, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllStatuses</code> when no record exists in database. An empty list should be
     * returned. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllStatusesNoRecordAccuracy()
        throws Exception {
        assertTrue("The retrieved list should be empty.", persistence.retrieveAllStatuses().isEmpty());

        assertNotNull("A connection should be created.", persistence.getConnection());
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
            status.setCreationDate(new Date());
            status.setModificationDate(new Date());

            persistence.addStatus(status);
            expected.add(status);
        }

        List actual = persistence.retrieveAllStatuses();

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
        return new TestSuite(ExpenseEntryStatusDbPersistenceTestCase.class);
    }
}


