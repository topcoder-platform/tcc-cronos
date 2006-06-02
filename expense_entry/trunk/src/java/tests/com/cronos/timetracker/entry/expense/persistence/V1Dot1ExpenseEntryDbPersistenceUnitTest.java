/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.persistence;

import com.cronos.timetracker.entry.expense.ExpenseEntry;
import com.cronos.timetracker.entry.expense.ExpenseEntryRejectReason;
import com.cronos.timetracker.entry.expense.ExpenseEntryStatus;
import com.cronos.timetracker.entry.expense.ExpenseEntryType;
import com.cronos.timetracker.entry.expense.V1Dot1TestHelper;
import com.cronos.timetracker.entry.expense.search.FieldBetweenCriteria;
import com.cronos.timetracker.entry.expense.search.FieldMatchCriteria;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import junit.framework.TestCase;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryDbPersistence</code> class.
 * </p>
 *
 * <p>
 * Modify test cases for bug fix for TT-1976. Adding descriptions to reject reason of entry. Add checking the
 * description of reject reason in entries returned from retrieveEntry, retrieveEntries and  retrieveAllEntries
 * methods. This checking is done in helper class V1Dot1TestHelper#assertEquals method.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1ExpenseEntryDbPersistenceUnitTest extends TestCase {
    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.cronos.timetracker.entry.expense.connection";

    /** The description of reject reason. */
    private final String description1 = "description1";

    /** The description of reject reason. */
    private final String description2 = "description2";

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

    /** Represents a valid expense reject reason instance. */
    private ExpenseEntryRejectReason reason1;

    /** Represents a valid expense reject reason instance. */
    private ExpenseEntryRejectReason reason2;

    /**
     * <p>
     * Sets up the test environment. Valid configurations are loaded. A valid manager is created. The data table is
     * truncated. A valid expense entry entry is created. One expense type and one expense status  and two reject
     * reasons are added into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        V1Dot1TestHelper.clearConfiguration();
        V1Dot1TestHelper.addValidConfiguration();

        // Create the manager
        persistence = new ExpenseEntryDbPersistence("Connection", DB_NAMESPACE);
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();
        V1Dot1TestHelper.clearDatabase(connection);
        V1Dot1TestHelper.executeSQL("insert into company values(1, 'a', 'a', current, 'a', current, 'a')", connection);

        // Insert an expense type
        PreparedStatement ps = connection.prepareStatement("INSERT INTO expense_type(expense_type_id, description, " +
                "creation_user, creation_date, modification_user, modification_date,active) VALUES (?,?,?,?,?,?,0)");

        try {
            ps.setInt(1, 1);
            ps.setString(2, "Travel Expense");
            ps.setString(3, "TangentZ");
            ps.setDate(4, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(5, "Ivern");
            ps.setDate(6, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 2, 1).getTime()));
            ps.executeUpdate();
        } finally {
            ps.close();
        }
        V1Dot1TestHelper.executeSQL("insert into comp_exp_type values(1, 1, current, 'a', current, 'a')", connection);

        // Insert an expense status
        ps = connection.prepareStatement("INSERT INTO expense_status(expense_status_id, description, creation_user, " +
                "creation_date, modification_user, modification_date) VALUES (?,?,?,?,?,?)");

        try {
            ps.setInt(1, 2);
            ps.setString(2, "Pending Approval");
            ps.setString(3, "TangentZ");
            ps.setDate(4, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(5, "Ivern");
            ps.setDate(6, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 2, 1).getTime()));
            ps.executeUpdate();
        } finally {
            ps.close();
        }

        // Insert an reject reason
        ps = connection.prepareStatement("INSERT INTO reject_reason(reject_reason_id, description, creation_date, " +
                "creation_user, modification_date, modification_user, active) VALUES (?,?,?,?,?,?,0)");

        try {
            ps.setInt(1, 1);
            ps.setString(2, description1);
            ps.setDate(3, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(4, "TangentZ");
            ps.setDate(5, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 2, 1).getTime()));
            ps.setString(6, "Ivern");
            ps.executeUpdate();

            ps.setInt(1, 3);
            ps.setString(2, description2);
            ps.setDate(3, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(4, "TangentZ");
            ps.setDate(5, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 2, 1).getTime()));
            ps.setString(6, "Ivern");
            ps.executeUpdate();
        } finally {
            ps.close();
        }
        V1Dot1TestHelper.executeSQL("insert into comp_rej_reason values(1, 1, current, 'a', current, 'a')", connection);
        V1Dot1TestHelper.executeSQL("insert into comp_rej_reason values(1, 3, current, 'a', current, 'a')", connection);

        // Create the expense status
        status = new ExpenseEntryStatus(2);

        status.setDescription("Pending Approval");
        status.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        status.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        status.setCreationUser("TangentZ");
        status.setModificationUser("Ivern");

        // Create the expense type
        type = new ExpenseEntryType(1);
        type.setCompanyId(1);
        type.setDescription("Travel Expense");
        type.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        type.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        type.setCreationUser("TangentZ");
        type.setModificationUser("Ivern");

        // Create the reject reason
        reason1 = new ExpenseEntryRejectReason(1);
        reason1.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        reason1.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        reason1.setCreationUser("TangentZ");
        reason1.setModificationUser("Ivern");
        reason1.setDescription(description1);

        reason2 = new ExpenseEntryRejectReason(3);
        reason2.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        reason2.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        reason2.setCreationUser("TangentZ");
        reason2.setModificationUser("Ivern");
        reason2.setDescription(description2);

        // Create the expense entry
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setCreationDate(new Date());
        entry.setModificationDate(new Date());
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100));
        entry.setBillable(true);
        entry.setDate(V1Dot1TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);
        entry.addRejectReason(reason1);
        entry.addRejectReason(reason2);
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed. The manager instance is disposed. The data table
     * is truncated. The expense entry entry instance is disposed. The expense type and expense status are deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        V1Dot1TestHelper.clearConfiguration();
        V1Dot1TestHelper.clearDatabase(connection);

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
        entry.setCompanyId(1);
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
     * Tests accuracy of <code>addEntry</code>. A new record should be inserted into the database. The method should
     * return <code>true</code>. A connection should be created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_Accuracy() throws Exception {
        assertTrue("The entry should be added.", persistence.addEntry(entry));

        // Verify record in database
        this.verifyOriginalEntry();
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
            resultSet = statement.executeQuery("SELECT * FROM expense_entry");

            assertFalse("No record should exist.", resultSet.next());

            resultSet = statement.executeQuery("SELECT * FROM exp_reject_reason");

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
     * Tests accuracy of <code>deleteAllEntries</code>. All records should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllEntriesAccuracy() throws Exception {
        // Add two records
        persistence.addEntry(entry);

        entry = new ExpenseEntry(6);
        entry.setCompanyId(1);
        entry.setCreationDate(new Date());
        entry.setModificationDate(new Date());
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100));
        entry.setBillable(true);
        entry.setDate(V1Dot1TestHelper.createDate(2005, 2, 5));
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
            resultSet = statement.executeQuery("SELECT * FROM expense_entry");

            assertFalse("No record should exist.", resultSet.next());

            resultSet = statement.executeQuery("SELECT * FROM exp_reject_reason");

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
        entry.setCompanyId(1);
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
     * Tests accuracy of <code>updateEntry</code> when ID exists in database. The method should return
     * <code>true</code>. The modification date should be modified. The record in database should be updated. The
     * creation user and creation date should not be modified.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryAccuracy1() throws Exception {
        persistence.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(201));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        assertTrue("The record should be updated.", persistence.updateEntry(update));

        this.verifyUpdatedEntry(update);
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
    public void testUpdateEntryAccuracy2() throws Exception {
        persistence.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(201));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);
        update.addRejectReason(reason2);

        assertTrue("The record should be updated.", persistence.updateEntry(update));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_entry");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getInt("expense_entry_id"));
            assertEquals("The company ID should be correct.", update.getCompanyId(), resultSet.getInt("company_id"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("description"));
            V1Dot1TestHelper.assertEquals("The creation date should not be modified.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            V1Dot1TestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should not be modified.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify2",
                resultSet.getString("modification_user"));
            assertEquals("The amount of money should be correct.", 201, resultSet.getDouble("amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 0, resultSet.getShort("billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("expense_type_id"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("expense_status_id"));
            V1Dot1TestHelper.assertEquals("The date should be correct.", V1Dot1TestHelper.createDate(2005, 3, 5),
                resultSet.getDate("entry_date"));

            assertFalse("Only A record should exist.", resultSet.next());

            resultSet = statement.executeQuery("SELECT * FROM exp_reject_reason");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getInt("expense_entry_id"));
            assertEquals("The ID should be correct.", reason2.getRejectReasonId(),
                resultSet.getInt("reject_reason_id"));
            V1Dot1TestHelper.assertEquals("The creation date should not be modified.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            V1Dot1TestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
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

        V1Dot1TestHelper.assertEquals(entry, retrieved);
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
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);

            persistence.addEntry(entry);
            expected.add(entry);
        }

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.size(), actual.size());

        V1Dot1TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < expected.size(); ++i) {
            V1Dot1TestHelper.assertEquals((ExpenseEntry) expected.get(i), (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when entries is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NullEntries() throws Exception {
        try {
            persistence.addEntries(null, true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when entries is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_EmptyEntries() throws Exception {
        try {
            persistence.addEntries(new ExpenseEntry[0], true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when entries contain <code>null</code> element. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_EntriesContainsNullElement()
        throws Exception {
        try {
            persistence.addEntries(new ExpenseEntry[] { null }, true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when persistence error occurs. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntriesPersistenceError1() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        try {
            persistence.addEntries(expected, true);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when persistence error occurs. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntriesPersistenceError2() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        try {
            persistence.addEntries(expected, false);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when there is no available connection. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntriesNoConnection1() throws Exception {
        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        try {
            persistence.addEntries(expected, true);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when there is no available connection. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntriesNoConnection2() throws Exception {
        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        try {
            persistence.addEntries(expected, false);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. All the entries
     * should be added.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        ExpenseEntry[] ret = persistence.addEntries(expected, true);
        assertNull("addEntries should return null.", ret);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.size());

        V1Dot1TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the entries
     * has already existed in the database. No entries should be added.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        persistence.addEntry(expected[4]);

        ExpenseEntry[] ret = persistence.addEntries(expected, true);
        assertEquals("The return value of addEntries should be correct.", 1, ret.length);
        assertEquals("The return value of addEntries should be correct.", expected[4], ret[0]);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 1, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the entries
     * has errors to insert into the database. No entries should be added.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        expected[4].setStatus(new ExpenseEntryStatus(10));

        try {
            persistence.addEntries(expected, true);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the entries
     * has errors to insert into the database and one of the entries has already existed in the database. No entries
     * should be added.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy4() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        expected[4].setStatus(new ExpenseEntryStatus(10));
        persistence.addEntry(expected[3]);

        ExpenseEntry[] ret = persistence.addEntries(expected, true);
        assertEquals("The return value of addEntries should be correct.", 1, ret.length);
        assertEquals("The return value of addEntries should be correct.", expected[3], ret[0]);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 1, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. All the
     * entries should be added.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy1()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        ExpenseEntry[] ret = persistence.addEntries(expected, false);
        assertEquals("The return value of addEntries should be correct.", 0, ret.length);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.size());

        V1Dot1TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. One of the
     * entries has already existed in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy2()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        persistence.addEntry(expected[4]);

        ExpenseEntry[] ret = persistence.addEntries(expected, false);
        assertEquals("The return value of addEntries should be correct.", 1, ret.length);
        assertEquals("The return value of addEntries should be correct.", expected[4], ret[0]);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 5, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. One of the
     * entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy3()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        expected[4].setStatus(new ExpenseEntryStatus(10));

        ExpenseEntry[] ret = persistence.addEntries(expected, false);
        assertEquals("The return value of addEntries should be correct.", 1, ret.length);
        assertEquals("The return value of addEntries should be correct.", expected[4], ret[0]);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 4, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. One of the
     * entries has errors to insert into the database and one of the entries has already existed in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy4()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        expected[4].setStatus(new ExpenseEntryStatus(10));
        persistence.addEntry(expected[3]);

        ExpenseEntry[] ret = persistence.addEntries(expected, false);
        assertEquals("The return value of addEntries should be correct.", 2, ret.length);
        assertEquals("The return value of addEntries should be correct.", expected[3], ret[0]);
        assertEquals("The return value of addEntries should be correct.", expected[4], ret[1]);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 4, actual.size());
    }

    /**
     * <p>
     * Tests <code>deleteEntries(int[], boolean)</code> when entryIds is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NullEntries() throws Exception {
        try {
            persistence.deleteEntries(null, true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(int[], boolean)</code> when entryIds is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_EmptyEntries() throws Exception {
        try {
            persistence.deleteEntries(new int[0], true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(int[], boolean)</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntriesPersistenceError1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, true);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(int[], boolean)</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntriesPersistenceError2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, false);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(int[], boolean)</code> when there is no available connection. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntriesNoConnection1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, true);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(int[], boolean)</code> when there is no available connection. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntriesNoConnection2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, false);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(int[], boolean)</code> when it is in Atomic mode. All the entryIds should
     * be deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAccuracy1()
        throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        int[] ret = persistence.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, true);
        assertNull("deleteEntries should return null.", ret);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(int[], boolean)</code> when it is in Atomic mode. One of the entryIds has
     * not exist in the database. No entryIds should be deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAccuracy2()
        throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        persistence.deleteEntry(entry.getId());

        int[] ret = persistence.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, true);
        assertEquals("The return value of deleteEntries should be correct.", 1, ret.length);
        assertEquals("The return value of deleteEntries should be correct.", 4, ret[0]);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 4, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(int[], boolean)</code> when it is in non Atomic mode. All the entryIds
     * should be deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NonAtomicModeAccuracy1()
        throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        int[] ret = persistence.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, false);
        assertEquals("The return value of deleteEntries should be correct.", 0, ret.length);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(int[], boolean)</code> when it is in non Atomic mode. One of the entryIds
     * has not existed in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NonAtomicModeAccuracy2()
        throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        persistence.deleteEntry(entry.getId());

        int[] ret = persistence.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, false);
        assertEquals("The return value of deleteEntries should be correct.", 1, ret.length);
        assertEquals("The return value of deleteEntries should be correct.", entry.getId(), ret[0]);

        List actual = persistence.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, actual.size());
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when entries is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NullEntries() throws Exception {
        try {
            persistence.updateEntries(null, true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when entries is empty. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_EmptyEntries() throws Exception {
        try {
            persistence.updateEntries(new ExpenseEntry[0], true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when entries contain <code>null</code> element. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_EntriesContainsNullElement()
        throws Exception {
        try {
            persistence.updateEntries(new ExpenseEntry[] { null }, true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when persistence error occurs. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntriesPersistenceError1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        try {
            persistence.updateEntries(expected, true);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when persistence error occurs. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntriesPersistenceError2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        try {
            persistence.updateEntries(expected, false);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when there is no available connection. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntriesNoConnection1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        try {
            persistence.updateEntries(expected, true);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when there is no available connection. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntriesNoConnection2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        try {
            persistence.updateEntries(expected, false);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. All the entries
     * should be updated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy1()
        throws Exception {
        persistence.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(201));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        assertNull("The record should be updated.", persistence.updateEntries(new ExpenseEntry[] { update }, true));

        // Verify record in database
        this.verifyUpdatedEntry(update);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the
     * entries has not existed in the database. No entries should be updated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy2()
        throws Exception {
        persistence.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(4);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(201));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        ExpenseEntry[] ret = persistence.updateEntries(new ExpenseEntry[] { update }, true);
        assertEquals("The return value of updateEntries should be correct.", 1, ret.length);
        assertEquals("The return value of updateEntries should be correct.", update, ret[0]);

        // Verify record in database
        this.verifyOriginalEntry();
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the
     * entries has errors to update from the database. No entries should be updated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy3()
        throws Exception {
        persistence.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(201));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(new ExpenseEntryStatus(10));

        try {
            persistence.updateEntries(new ExpenseEntry[] { update }, true);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        // Verify record in database
        this.verifyOriginalEntry();
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. All the
     * entries should be updated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAccuracy1()
        throws Exception {
        persistence.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(201));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        assertEquals("The return value of updateEntries should be correct.", 0,
            persistence.updateEntries(new ExpenseEntry[] { update }, false).length);

        // Verify record in database
        this.verifyUpdatedEntry(update);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. One of the
     * entries has not existed in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAccuracy2()
        throws Exception {
        persistence.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(4);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(201));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        ExpenseEntry[] ret = persistence.updateEntries(new ExpenseEntry[] { update }, false);
        assertEquals("The return value of updateEntries should be correct.", 1, ret.length);
        assertEquals("The return value of updateEntries should be correct.", update, ret[0]);

        // Verify record in database
        this.verifyOriginalEntry();
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. One of the
     * entries has errors to update from the database. No entries should be updated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAccuracy3()
        throws Exception {
        persistence.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(201));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(new ExpenseEntryStatus(10));

        ExpenseEntry[] ret = persistence.updateEntries(new ExpenseEntry[] { update }, false);
        assertEquals("The return value of updateEntries should be correct.", 1, ret.length);
        assertEquals("The return value of updateEntries should be correct.", update, ret[0]);

        // Verify record in database
        this.verifyOriginalEntry();
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(int[], boolean)</code> when entryIds is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NullEntries() throws Exception {
        try {
            persistence.retrieveEntries(null, true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(int[], boolean)</code> when entryIds is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_EmptyEntries() throws Exception {
        try {
            persistence.retrieveEntries(new int[0], true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(int[], boolean)</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntriesPersistenceError1()
        throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, true);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(int[], boolean)</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntriesPersistenceError2()
        throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, false);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(int[], boolean)</code> when there is no available connection. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntriesNoConnection1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, true);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(int[], boolean)</code> when there is no available connection. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntriesNoConnection2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, false);
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. All the entries should
     * be retrieved.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy1()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            persistence.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = persistence.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, true);

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.length);

        V1Dot1TestHelper.sortDataObjects(Arrays.asList(actual));

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. One of the entryIds
     * has not exist in the database. PersistenceException is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy2()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[3];

        // Add 3 instances
        for (int i = 0; i < 3; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            persistence.addEntry(expected[i]);
        }

        try {
            persistence.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, true);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. Error occurs when
     * retrieving one entry. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy3()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[3];

        // Add 5 instances
        for (int i = 0; i < 3; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            persistence.addEntry(expected[i]);
        }

        // Insert an expense type
        PreparedStatement ps = connection.prepareStatement("Update expense_entry set billable = 4 where " +
                "expense_entry_id = 2");

        try {
            ps.executeUpdate();
        } finally {
            ps.close();
        }

        try {
            persistence.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, true);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in non Atomic mode. All the entries
     * should be retrieved.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NonAtomicModeAccuracy1()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            persistence.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = persistence.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, false);

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.length);

        V1Dot1TestHelper.sortDataObjects(Arrays.asList(actual));

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in non Atomic mode. One of the
     * entryIds has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NonAtomicModeAccuracy2()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[3];

        // Add 3 instances
        for (int i = 0; i < 3; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            persistence.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = persistence.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, false);

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.length);

        V1Dot1TestHelper.sortDataObjects(Arrays.asList(actual));

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in non Atomic mode. Error occurs when
     * retrieving one entry. Not expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NonAtomicModeAccuracy3()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[3];

        // Add 5 instances
        for (int i = 0; i < 3; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            persistence.addEntry(expected[i]);
        }

        // Insert an expense type
        PreparedStatement ps = connection.prepareStatement("Update expense_entry set billable = 4 where " +
                "expense_entry_id = 2");

        try {
            ps.executeUpdate();
        } finally {
            ps.close();
        }

        ExpenseEntry[] actual = persistence.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, false);

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length - 1, actual.length);

        // Verify instances
        for (int i = 0; i < (expected.length - 1); ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * <p>
     * Tests <code>searchEntries(Criteria)</code> when criteria is <code>null</code>. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSearchEntries_NullCriteria() throws Exception {
        try {
            persistence.searchEntries(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>searchEntries(Criteria)</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSearchEntriesPersistenceError() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        persistence.setConnection(conn);

        try {
            persistence.searchEntries(new FieldMatchCriteria("expense_entry.expense_type_id",
                new Integer(type.getId())));
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>searchEntries(Criteria)</code> when there is no available connection. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSearchEntriesNoConnection() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setCreationDate(new Date());
            entry.setModificationDate(new Date());
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            persistence.addEntry(entry);
        }

        persistence = new ExpenseEntryDbPersistence(DB_NAMESPACE);

        try {
            persistence.searchEntries(new FieldMatchCriteria("expense_entry.expense_type_id",
                new Integer(type.getId())));
            fail("Connection and connection producer name are not set, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>searchEntries(Criteria)</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSearchEntries_Accuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            expected[i].addRejectReason(reason1);
            expected[i].addRejectReason(reason2);
            persistence.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = persistence.searchEntries(FieldMatchCriteria.getExpenseStatusMatchCriteria(
                    status.getId()));

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.length);

        V1Dot1TestHelper.sortDataObjects(Arrays.asList(actual));

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>searchEntries(Criteria)</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSearchEntries_Accuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            expected[i].addRejectReason(reason1);
            expected[i].addRejectReason(reason2);
            persistence.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = persistence.searchEntries(FieldMatchCriteria.getExpenseTypeMatchCriteria(type.getId()));

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.length);

        V1Dot1TestHelper.sortDataObjects(Arrays.asList(actual));

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>searchEntries(Criteria)</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSearchEntries_Accuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            expected[i].addRejectReason(reason1);
            expected[i].addRejectReason(reason2);
            persistence.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = persistence.searchEntries(FieldBetweenCriteria.getCreationDateBetweenCriteria(
                    V1Dot1TestHelper.createDate(2005, 2, 1), V1Dot1TestHelper.createDate(2007, 2, 1)));

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.length);

        V1Dot1TestHelper.sortDataObjects(Arrays.asList(actual));

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>searchEntries(Criteria)</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSearchEntries_Accuracy4() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setCreationDate(new Date());
            expected[i].setModificationDate(new Date());
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            expected[i].addRejectReason(reason1);
            expected[i].addRejectReason(reason2);
            persistence.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = persistence.searchEntries(FieldBetweenCriteria.getAmountBetweenCriteria(
                    new BigDecimal(-1), new BigDecimal(100)));

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.length);

        V1Dot1TestHelper.sortDataObjects(Arrays.asList(actual));

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * Verifies the entry created in the setUp(). Check whether it is in the database.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    private void verifyOriginalEntry() throws Exception {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_entry");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("expense_entry_id"));
            assertEquals("The company ID should be correct.", entry.getCompanyId(), resultSet.getInt("company_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            V1Dot1TestHelper.assertEquals("The creation date should be correct.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            V1Dot1TestHelper.assertEquals("The modification date should be correct.", entry.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify",
                resultSet.getString("modification_user"));
            assertEquals("The amount of money should be correct.", 100, resultSet.getDouble("amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 1, resultSet.getShort("billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("expense_type_id"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("expense_status_id"));
            V1Dot1TestHelper.assertEquals("The date should be correct.", V1Dot1TestHelper.createDate(2005, 2, 5),
                resultSet.getDate("entry_date"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }

        // Verify record in exp_reject_reason table
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM exp_reject_reason");

            assertTrue("First record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("expense_entry_id"));
            assertEquals("The ID should be correct.", reason1.getRejectReasonId(),
                resultSet.getInt("reject_reason_id"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify",
                resultSet.getString("modification_user"));

            assertTrue("Second record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("expense_entry_id"));
            assertEquals("The ID should be correct.", reason2.getRejectReasonId(),
                resultSet.getInt("reject_reason_id"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify",
                resultSet.getString("modification_user"));

            assertFalse("Only two records should exist.", resultSet.next());
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
     * Verifies the updated entry. Check whether it is in the database.
     *
     * @param update the updated entry.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    private void verifyUpdatedEntry(ExpenseEntry update)
        throws Exception {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_entry");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getInt("expense_entry_id"));
            assertEquals("The company ID should be correct.", update.getCompanyId(), resultSet.getInt("company_id"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("description"));
            V1Dot1TestHelper.assertEquals("The creation date should not be modified.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            V1Dot1TestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should not be modified.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify2",
                resultSet.getString("modification_user"));
            assertEquals("The amount of money should be correct.",
                update.getAmount().doubleValue(), resultSet.getDouble("amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 0, resultSet.getShort("billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("expense_type_id"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("expense_status_id"));
            V1Dot1TestHelper.assertEquals("The date should be correct.", V1Dot1TestHelper.createDate(2005, 3, 5),
                resultSet.getDate("entry_date"));

            assertFalse("Only one record should exist.", resultSet.next());

            resultSet = statement.executeQuery("SELECT * FROM exp_reject_reason");
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
}
