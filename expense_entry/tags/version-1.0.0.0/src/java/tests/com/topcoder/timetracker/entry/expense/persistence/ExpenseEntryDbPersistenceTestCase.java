/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.timetracker.entry.expense.TestHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.Field;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryManager</code> class. The test uses
 * <code>ExpenseEntryDbPersistence</code> as the underlying persistence.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class ExpenseEntryDbPersistenceTestCase extends TestCase {
    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /** Represents the manager instance used in tests. */
    private ExpenseEntryPersistence persistence;

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a DB connection factory to create connections. */
    private DBConnectionFactory factory;

    /** Represents a valid expense entry entry instance. */
    private ExpenseEntry entry;

    /** Represents a valid expense entry type instance. */
    private ExpenseEntryType type;

    /** Represents a valid expense entry status instance. */
    private ExpenseEntryStatus status;

    /**
     * <p>
     * Sets up the test environment. Valid configurations are loaded. A valid manager is created. The data table is
     * truncated. A valid expense entry entry is created. One expense type and one expense status are added into the
     * database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.addValidConfiguration();

        // Create the manager
        persistence = new ExpenseEntryDbPersistence("Connection", DB_NAMESPACE);
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();

        TestHelper.clearDatabase(connection);

        // Insert an expense type
        PreparedStatement ps = connection.prepareStatement("INSERT INTO ExpenseTypes(ExpenseTypesID, Description, "
                + "CreationUser, CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

        try {
            ps.setInt(1, 1);
            ps.setString(2, "Travel Expense");
            ps.setString(3, "TangentZ");
            ps.setDate(4, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(5, "Ivern");
            ps.setDate(6, new java.sql.Date(TestHelper.createDate(2005, 2, 1).getTime()));
            ps.executeUpdate();
        } finally {
            ps.close();
        }

        // Insert an expense status
        ps = connection.prepareStatement("INSERT INTO ExpenseStatuses(ExpenseStatusesID, Description, CreationUser, "
                + "CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

        try {
            ps.setInt(1, 2);
            ps.setString(2, "Pending Approval");
            ps.setString(3, "TangentZ");
            ps.setDate(4, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(5, "Ivern");
            ps.setDate(6, new java.sql.Date(TestHelper.createDate(2005, 2, 1).getTime()));
            ps.executeUpdate();
        } finally {
            ps.close();
        }

        // Create the expense status
        status = new ExpenseEntryStatus(2);

        status.setDescription("Pending Approval");
        status.setCreationDate(TestHelper.createDate(2005, 1, 1));
        status.setModificationDate(TestHelper.createDate(2005, 2, 1));
        status.setCreationUser("TangentZ");
        status.setModificationUser("Ivern");

        // Create the expense type
        type = new ExpenseEntryType(1);

        type.setDescription("Travel Expense");
        type.setCreationDate(TestHelper.createDate(2005, 1, 1));
        type.setModificationDate(TestHelper.createDate(2005, 2, 1));
        type.setCreationUser("TangentZ");
        type.setModificationUser("Ivern");

        // Create the expense entry
        entry = new ExpenseEntry(5);
        entry.setCreationDate(new Date());
        entry.setModificationDate(new Date());
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed. The manager instance is disposed. The data
     * table is truncated. The expense entry entry instance is disposed. The expense type and expense status are
     * deleted.
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
        entry = null;
        factory = null;
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryDbPersistence(String)</code> when the given namespace is
     * <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceStringNull()
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
     * Tests constructor <code>ExpenseEntryDbPersistence(String)</code> when the given namespace is empty string.
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceStringEmpty()
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
     * Tests constructor <code>ExpenseEntryDbPersistence(String)</code> when DB connection factory cannot be created.
     * Expect ConfigurationException.
     * </p>
     */
    public void testExpenseEntryDbPersistenceStringFactoryError() {
        try {
            new ExpenseEntryDbPersistence("Unknown");
            fail("The DB connection factory cannot be created, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryDbPersistence(String, String)</code> when the given namespace is
     * <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceStringStringNamespaceNull()
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
     * Tests constructor <code>ExpenseEntryDbPersistence(String, String)</code> when the given namespace is empty
     * string. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceStringStringNamespaceEmpty()
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
     * Tests constructor <code>ExpenseEntryDbPersistence(String, String)</code> when the given connection producer
     * name is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceStringStringNameNull()
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
     * Tests constructor <code>ExpenseEntryDbPersistence(String, String)</code> when the given connection producer
     * name is empty string. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceStringStringNameEmpty()
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
     * Tests constructor <code>ExpenseEntryDbPersistence(String, String)</code> when the given connection producer
     * name does not exist in DB connection factory. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceStringStringNameUnknown()
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
     * Tests constructor <code>ExpenseEntryDbPersistence(String, String)</code> DB connection factory cannot be
     * created. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceStringStringFactoryError()
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
     * Tests <code>addEntry</code> when the given expense entry is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntryNull() throws Exception {
        try {
            persistence.addEntry(null);
            fail("The expense entry is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntryPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        entry = new ExpenseEntry();
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setDescription("Description");

        try {
            persistence.addEntry(entry);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntryNoConnection() throws Exception {
        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.addEntry(entry);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntry</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntryPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.deleteEntry(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntry</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntryNoConnection() throws Exception {
        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.deleteEntry(0);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteAllEntries</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllEntriesPersistenceError()
        throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.deleteAllEntries();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteAllEntries</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllEntriesNoConnection() throws Exception {
        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.deleteAllEntries();
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the given expense entry is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryNull() throws Exception {
        try {
            persistence.updateEntry(null);
            fail("The expense entry is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        entry = new ExpenseEntry();
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setDescription("Description");

        try {
            persistence.updateEntry(entry);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryNoConnection() throws Exception {
        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.updateEntry(entry);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntry</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntryPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.retrieveEntry(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntry</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntryNoConnection() throws Exception {
        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.retrieveEntry(0);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveAllEntries</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllEntriesPersistenceError()
        throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.retrieveAllEntries();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveAllEntries</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllEntriesNoConnection() throws Exception {
        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.retrieveAllEntries();
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
     * Tests accuracy of constructor <code>ExpenseEntryDbPersistence(String)</code>. The namespace should be set
     * correctly. The connection producer name should be <code>null</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceStringAccuracy()
        throws Exception {
        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

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
     * Tests accuracy of constructor <code>ExpenseEntryDbPersistence(String, String)</code>. The namespace and
     * conection producer name should be set correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceStringStringAccuracy()
        throws Exception {
        persistence = new ExpenseEntryDbPersistence("Connection", DB_NAMESPACE);

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
     * Tests accuracy of <code>addEntry</code>. A new record should be inserted into the database. The method should
     * return <code>true</code>. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntryAccuracy() throws Exception {
        assertTrue("The entry should be added.", persistence.addEntry(entry));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseEntries");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("ExpenseEntriesID"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("Description"));
            TestHelper.assertEquals("The creation date should be correct.", entry.getCreationDate(),
                resultSet.getDate("CreationDate"));
            TestHelper.assertEquals("The modification date should be correct.", entry.getModificationDate(),
                resultSet.getDate("ModificationDate"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("CreationUser"));
            assertEquals("The modification user should be correct.", "Modify", resultSet.getString("ModificationUser"));
            assertEquals("The amount of money should be correct.", 100.12, resultSet.getDouble("Amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 1, resultSet.getShort("Billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("ExpenseTypesID"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("ExpenseStatusesID"));
            TestHelper.assertEquals("The date should be correct.", TestHelper.createDate(2005, 2, 5),
                resultSet.getDate("EntryDate"));

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
     * Tests accuracy of <code>addEntry</code> when ID already exists in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntryExistAccuracy() throws Exception {
        persistence.addEntry(entry);

        // Add again, should return false.
        assertFalse("The ID exists in database.", persistence.addEntry(entry));
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntry</code> when ID exists in database. The method should return
     * <code>true</code>. The record should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntryAccuracy() throws Exception {
        persistence.addEntry(entry);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", persistence.deleteEntry(entry.getId()));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseEntries");

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
     * Tests accuracy of <code>deleteAllEntries</code>. All records should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllEntriesAccuracy() throws Exception {
        // Add two records
        persistence.addEntry(entry);

        entry = new ExpenseEntry(6);
        entry.setCreationDate(new Date());
        entry.setModificationDate(new Date());
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        persistence.addEntry(entry);

        // Delete
        persistence.deleteAllEntries();

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseEntries");

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
     * Tests accuracy of <code>deleteEntry</code> when ID does not exist in database. The method should return
     * <code>false</code>. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntryIDNotExistAccuracy() throws Exception {
        assertFalse("The ID does not exist in database.", persistence.deleteEntry(5));

        assertNotNull("A connection should be created.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntry</code> when ID exists in database. The method should return
     * <code>true</code>. The modification date should be modified. The record in database should be updated. The
     * creation user and creation date should not be modified.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryAccuracy() throws Exception {
        persistence.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(200.12));
        update.setBillable(false);
        update.setDate(TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        assertTrue("The record should be updated.", persistence.updateEntry(update));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseEntries");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getInt("ExpenseEntriesID"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("Description"));
            TestHelper.assertEquals("The creation date should not be modified.", entry.getCreationDate(),
                resultSet.getDate("CreationDate"));
            TestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
                resultSet.getDate("ModificationDate"));
            assertEquals("The creation user should not be modified.", "Create", resultSet.getString("CreationUser"));
            assertEquals("The modification user should be correct.", "Modify2",
                resultSet.getString("ModificationUser"));
            assertEquals("The amount of money should be correct.", 200.12, resultSet.getDouble("Amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 0, resultSet.getShort("Billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("ExpenseTypesID"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("ExpenseStatusesID"));
            TestHelper.assertEquals("The date should be correct.", TestHelper.createDate(2005, 3, 5),
                resultSet.getDate("EntryDate"));

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
     * Tests accuracy of <code>updateEntry</code> when ID does not exist in database. The method should return
     * <code>false</code>. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryIDNotExistAccuracy() throws Exception {
        assertFalse("The record should not be updated.", persistence.updateEntry(entry));

        assertNotNull("A connection should be created.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntry</code> when ID does not exist in database. <code>null</code> should be
     * returned. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntryIDNotExistAccuracy() throws Exception {
        assertNull("The ID does not exist in database, null should return.", persistence.retrieveEntry(5));

        assertNotNull("A connection should be created.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntry</code> when ID exists in database. The correct instance should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntryAccuracy() throws Exception {
        persistence.addEntry(entry);

        ExpenseEntry retrieved = persistence.retrieveEntry(5);

        TestHelper.assertEquals(entry, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllEntries</code> when no record exists in database. An empty list should be
     * returned. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllEntriesNoRecordAccuracy()
        throws Exception {
        assertTrue("The retrieved list should be empty.", persistence.retrieveAllEntries().isEmpty());

        assertNotNull("A connection should be created.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllEntries</code>. The correct list of instances should be returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllEntriesAccuracy() throws Exception {
        List expected = new ArrayList();

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);

            persistence.addEntry(entry);
            expected.add(entry);
        }

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.size(), actual.size());

        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < expected.size(); ++i) {
            TestHelper.assertEquals((ExpenseEntry) expected.get(i), (ExpenseEntry) actual.get(i));
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
        return new TestSuite(ExpenseEntryDbPersistenceTestCase.class);
    }
}


