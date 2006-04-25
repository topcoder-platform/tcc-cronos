/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import com.cronos.timetracker.entry.expense.persistence.PersistenceException;
import com.cronos.timetracker.entry.expense.search.FieldMatchCriteria;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import junit.framework.TestCase;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryManager</code> class.
 * </p>
 * 
 * <p>
 * 2006-4-24: Add/Modify Test Cases by Xuchen for TT-1974 which is about generating unique id for entries whose
 * original id is -1 in adding entries batch. <br>
 * 1. Add two test cases testAddEntries_AtomicModeAccuracy6() and testAddEntries_NonAtomicModeAccuracy6(), in which we
 * try to add entries whose original id is -1, and verify ExpenseEntryManager has assigned them with unique id. <br>
 * 2. Modify two test cases testAddEntries_AtomicModeAccuracy1() and testAddEntries_NonAtomicModeAccuracy1(), in which
 * we add the asserting at the bottom that the original id (which is not -1) does not change after adding entries.
 * </p>
 * 
 * <p>
 * Modify test cases for bug fix for TT-1976. Adding descriptions to reject reason of entry (in setUp method.).  Add
 * checking the description of reject reason in entries returned from searchEntries methods. This checking  is done in
 * helper class V1Dot1TestHelper#assertEquals method.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1ExpenseEntryManagerUnitTest extends TestCase {
    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.cronos.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.cronos.timetracker.entry.expense.connection";

    /** The description of reject reason. */
    private final String description1 = "description1";

    /** The description of reject reason. */
    private final String description2 = "description2";

    /** Represents the manager instance used in tests. */
    private ExpenseEntryManager manager;

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
        manager = new ExpenseEntryManager(NAMESPACE);
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();

        V1Dot1TestHelper.clearDatabase(connection);

        // Insert an expense type
        PreparedStatement ps = connection.prepareStatement("INSERT INTO ExpenseTypes(ExpenseTypesID, Description, " +
                "CreationUser, CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

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

        // Insert an expense status
        ps = connection.prepareStatement("INSERT INTO ExpenseStatuses(ExpenseStatusesID, Description, CreationUser, " +
                "CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

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
                "creation_user, modification_date, modification_user) VALUES (?,?,?,?,?,?)");

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

        // Create the expense status
        status = new ExpenseEntryStatus(2);

        status.setDescription("Pending Approval");
        status.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        status.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        status.setCreationUser("TangentZ");
        status.setModificationUser("Ivern");

        // Create the expense type
        type = new ExpenseEntryType(1);

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
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
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
            manager.getEntryPersistence().closeConnection();
        } catch (Exception e) {
            // ignore
        }

        manager = null;
        connection = null;
        entry = null;
        factory = null;
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
            manager.addEntries(null, true);
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
            manager.addEntries(new ExpenseEntry[0], true);
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
            manager.addEntries(new ExpenseEntry[] { null }, true);
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
        manager.getEntryPersistence().setConnection(conn);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        try {
            manager.addEntries(expected, true);
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
        manager.getEntryPersistence().setConnection(conn);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        try {
            manager.addEntries(expected, false);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. All the entries
     * should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        ExpenseEntry[] ret = manager.addEntries(expected, true);
        assertNull("addEntries should return null.", ret);

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.size());

        V1Dot1TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], (ExpenseEntry) actual.get(i));

            // also verify its id.
            assertEquals("The id should be original.", expected[i].getId(), ((ExpenseEntry) actual.get(i)).getId());
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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        entry = new ExpenseEntry(4);
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setDescription("Description");
        entry.setAmount(new BigDecimal(1));
        entry.setBillable(true);
        entry.setDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        entry.setExpenseType(type);
        entry.setStatus(status);

        manager.addEntry(entry);

        ExpenseEntry[] ret = manager.addEntries(expected, true);
        assertEquals("The return value of addEntries should be correct.", 1, ret.length);
        assertEquals("The return value of addEntries should be correct.", expected[4], ret[0]);

        List actual = manager.retrieveAllEntries();

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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        expected[4].setStatus(new ExpenseEntryStatus(10));

        try {
            manager.addEntries(expected, true);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the entries
     * will throw IllegalArgumentException. No entries should be added.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy4() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        expected[4].setCreationDate(new Date());

        try {
            manager.addEntries(expected, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the entries
     * will throw InsufficientDataException. No entries should be added.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy5() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
        }

        for (int i = 0; i < 4; i++) {
            expected[i].setStatus(status);
        }

        try {
            manager.addEntries(expected, true);
            fail("InsufficientDataException should be thrown.");
        } catch (InsufficientDataException e) {
            // good
        }

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. All the entries
     * should be added. These entries does not have their id specified (originally is -1). So ExpenseEntryManager
     * should assign their unique id.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy6() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry();

            // check the default original id is -1.
            assertEquals("The default original id should be -1.", -1, expected[i].getId());
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        ExpenseEntry[] ret = manager.addEntries(expected, true);
        assertNull("addEntries should return null.", ret);

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.size());

        V1Dot1TestHelper.sortDataObjects(actual);

        List expectedList = Arrays.asList(expected);
        V1Dot1TestHelper.sortDataObjects(expectedList);
        expected = (ExpenseEntry[]) expectedList.toArray(new ExpenseEntry[expected.length]);

        // Verify instances
        Set uniqueIds = new HashSet();

        for (int i = 0; i < expected.length; ++i) {
            ExpenseEntry actualEntry = (ExpenseEntry) actual.get(i);

            V1Dot1TestHelper.assertEquals(expected[i], actualEntry);

            // also verify its id has been assigned, NOT -1.
            assertTrue("The id should be assigned, NOT -1.", -1 != actualEntry.getId());

            // verify the generated id is unique.
            Integer id = new Integer(actualEntry.getId());
            assertFalse("The id generated should be unique.", uniqueIds.contains(id));
            uniqueIds.add(id);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. All the
     * entries should be added. These entries already have their own id (NOT -1).
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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        ExpenseEntry[] ret = manager.addEntries(expected, false);
        assertEquals("The return value of addEntries should be correct.", 0, ret.length);

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.size());

        V1Dot1TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], (ExpenseEntry) actual.get(i));

            // also verify its id.
            assertEquals("The id should be original.", expected[i].getId(), ((ExpenseEntry) actual.get(i)).getId());
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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        entry = new ExpenseEntry(4);
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setDescription("Description");
        entry.setAmount(new BigDecimal(1));
        entry.setBillable(true);
        entry.setDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        entry.setExpenseType(type);
        entry.setStatus(status);

        manager.addEntry(entry);

        ExpenseEntry[] ret = manager.addEntries(expected, false);
        assertEquals("The return value of addEntries should be correct.", 1, ret.length);
        assertEquals("The return value of addEntries should be correct.", expected[4], ret[0]);

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 5, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. One of the
     * entries has errors to insert into the database. No entries should be added.
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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        expected[4].setStatus(new ExpenseEntryStatus(10));

        ExpenseEntry[] ret = manager.addEntries(expected, false);
        assertEquals("The return value of addEntries should be correct.", 1, ret.length);
        assertEquals("The return value of addEntries should be correct.", expected[4], ret[0]);

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 4, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. One of the
     * entries will throw IllegalArgumentException.
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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        expected[4].setCreationDate(new Date());

        ExpenseEntry[] ret = manager.addEntries(expected, false);
        assertEquals("The return value of addEntries should be correct.", 1, ret.length);
        assertEquals("The return value of addEntries should be correct.", expected[4], ret[0]);

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 4, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. One of the
     * entries will throw InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy5()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
        }

        for (int i = 0; i < 4; i++) {
            expected[i].setStatus(status);
        }

        ExpenseEntry[] ret = manager.addEntries(expected, false);
        assertEquals("The return value of addEntries should be correct.", 1, ret.length);
        assertEquals("The return value of addEntries should be correct.", expected[4], ret[0]);

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", 4, actual.size());
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. All the
     * entries should be added. These entries does not have their id specified (originally is -1). So
     * ExpenseEntryManager should assign their unique id.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy6()
        throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry();

            // check the default original id is -1.
            assertEquals("The default original id should be -1.", -1, expected[i].getId());
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        ExpenseEntry[] ret = manager.addEntries(expected, false);
        assertEquals("The return value of addEntries should be correct.", 0, ret.length);

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.size());

        V1Dot1TestHelper.sortDataObjects(actual);

        List expectedList = Arrays.asList(expected);
        V1Dot1TestHelper.sortDataObjects(expectedList);
        expected = (ExpenseEntry[]) expectedList.toArray(new ExpenseEntry[expected.length]);

        // Verify instances
        Set uniqueIds = new HashSet();

        for (int i = 0; i < expected.length; ++i) {
            ExpenseEntry actualEntry = (ExpenseEntry) actual.get(i);

            V1Dot1TestHelper.assertEquals(expected[i], actualEntry);

            // also verify its id has been assigned, NOT -1.
            assertTrue("The id should be assigned, NOT -1.", -1 != actualEntry.getId());

            // verify the generated id is unique.
            Integer id = new Integer(actualEntry.getId());
            assertFalse("The id generated should be unique.", uniqueIds.contains(id));
            uniqueIds.add(id);
        }
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
            manager.deleteEntries(null, true);
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
            manager.deleteEntries(new int[0], true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(int[], boolean)</code> when manager error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntriesPersistenceError1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        try {
            manager.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, true);
            fail("The manager error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(int[], boolean)</code> when manager error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntriesPersistenceError2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        try {
            manager.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, false);
            fail("The manager error occurs, should throw PersistenceException.");
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
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        int[] ret = manager.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, true);
        assertNull("deleteEntries should return null.", ret);

        List actual = manager.retrieveAllEntries();

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
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        manager.deleteEntry(entry.getId());

        int[] ret = manager.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, true);
        assertEquals("The return value of deleteEntries should be correct.", 1, ret.length);
        assertEquals("The return value of deleteEntries should be correct.", 4, ret[0]);

        List actual = manager.retrieveAllEntries();

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
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        int[] ret = manager.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, false);
        assertEquals("The return value of deleteEntries should be correct.", 0, ret.length);

        List actual = manager.retrieveAllEntries();

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
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        manager.deleteEntry(entry.getId());

        int[] ret = manager.deleteEntries(new int[] { 0, 1, 2, 3, 4 }, false);
        assertEquals("The return value of deleteEntries should be correct.", 1, ret.length);
        assertEquals("The return value of deleteEntries should be correct.", entry.getId(), ret[0]);

        List actual = manager.retrieveAllEntries();

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
            manager.updateEntries(null, true);
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
            manager.updateEntries(new ExpenseEntry[0], true);
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
            manager.updateEntries(new ExpenseEntry[] { null }, true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when manager error occurs. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntriesPersistenceError1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
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
            manager.updateEntries(expected, true);
            fail("The manager error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when manager error occurs. Expect
     * PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntriesPersistenceError2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
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
            manager.updateEntries(expected, false);
            fail("The manager error occurs, should throw PersistenceException.");
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
        manager.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(200.12));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        assertNull("The record should be updated.", manager.updateEntries(new ExpenseEntry[] { update }, true));

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
        manager.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(4);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(200.12));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        ExpenseEntry[] ret = manager.updateEntries(new ExpenseEntry[] { update }, true);
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
        manager.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(200.12));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(new ExpenseEntryStatus(10));

        try {
            manager.updateEntries(new ExpenseEntry[] { update }, true);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        // Verify record in database
        this.verifyOriginalEntry();
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the
     * entries will throw InsufficientDataException. No entries should be updated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy4()
        throws Exception {
        manager.addEntry(entry);

        // no description
        ExpenseEntry update = new ExpenseEntry(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(200.12));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        try {
            manager.updateEntries(new ExpenseEntry[] { update }, true);
            fail("InsufficientDataException should be thrown.");
        } catch (InsufficientDataException e) {
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
        manager.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(200.12));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        assertEquals("The return value of updateEntries should be correct.", 0,
            manager.updateEntries(new ExpenseEntry[] { update }, false).length);

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
        manager.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(4);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(200.12));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        ExpenseEntry[] ret = manager.updateEntries(new ExpenseEntry[] { update }, false);
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
        manager.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(200.12));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(new ExpenseEntryStatus(10));

        ExpenseEntry[] ret = manager.updateEntries(new ExpenseEntry[] { update }, false);
        assertEquals("The return value of updateEntries should be correct.", 1, ret.length);
        assertEquals("The return value of updateEntries should be correct.", update, ret[0]);

        // Verify record in database
        this.verifyOriginalEntry();
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in non Atomic mode. One of the
     * entries will throw InsufficientDataException. No entries should be updated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAccuracy4()
        throws Exception {
        manager.addEntry(entry);

        // no description
        ExpenseEntry update = new ExpenseEntry(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setCreationDate(new Date());
        update.setModificationDate(new Date());
        update.setAmount(new BigDecimal(200.12));
        update.setBillable(false);
        update.setDate(V1Dot1TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        ExpenseEntry[] ret = manager.updateEntries(new ExpenseEntry[] { update }, false);
        assertEquals("The return value of updateEntries should be correct.", 1, ret.length);
        assertEquals("The return value of updateEntries should be correct.", update, ret[0]);

        // Verify record in database
        this.verifyOriginalEntry();
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
            resultSet = statement.executeQuery("SELECT * FROM ExpenseEntries");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("ExpenseEntriesID"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("Description"));
            V1Dot1TestHelper.assertEquals("The creation date should be correct.", entry.getCreationDate(),
                resultSet.getDate("CreationDate"));
            V1Dot1TestHelper.assertEquals("The modification date should be correct.", entry.getModificationDate(),
                resultSet.getDate("ModificationDate"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("CreationUser"));
            assertEquals("The modification user should be correct.", "Modify", resultSet.getString("ModificationUser"));
            assertEquals("The amount of money should be correct.", 100.12, resultSet.getDouble("Amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 1, resultSet.getShort("Billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("ExpenseTypesID"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("ExpenseStatusesID"));
            V1Dot1TestHelper.assertEquals("The date should be correct.", V1Dot1TestHelper.createDate(2005, 2, 5),
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

        // Verify record in exp_reject_reason table
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM exp_reject_reason");

            assertTrue("First record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("ExpenseEntriesID"));
            assertEquals("The ID should be correct.", reason1.getRejectReasonId(), resultSet.getInt("reject_reason_id"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify", resultSet.getString("modification_user"));

            assertTrue("Second record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("ExpenseEntriesID"));
            assertEquals("The ID should be correct.", reason2.getRejectReasonId(), resultSet.getInt("reject_reason_id"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify", resultSet.getString("modification_user"));

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
            resultSet = statement.executeQuery("SELECT * FROM ExpenseEntries");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getInt("ExpenseEntriesID"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("Description"));
            V1Dot1TestHelper.assertEquals("The creation date should not be modified.", entry.getCreationDate(),
                resultSet.getDate("CreationDate"));
            V1Dot1TestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
                resultSet.getDate("ModificationDate"));
            assertEquals("The creation user should not be modified.", "Create", resultSet.getString("CreationUser"));
            assertEquals("The modification user should be correct.", "Modify2", resultSet.getString("ModificationUser"));
            assertEquals("The amount of money should be correct.", 200.12, resultSet.getDouble("Amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 0, resultSet.getShort("Billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("ExpenseTypesID"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("ExpenseStatusesID"));
            V1Dot1TestHelper.assertEquals("The date should be correct.", V1Dot1TestHelper.createDate(2005, 3, 5),
                resultSet.getDate("EntryDate"));

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
            manager.retrieveEntries(null, true);
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
            manager.retrieveEntries(new int[0], true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(int[], boolean)</code> when manager error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntriesPersistenceError1()
        throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        try {
            manager.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, true);
            fail("The manager error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(int[], boolean)</code> when manager error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntriesPersistenceError2()
        throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        try {
            manager.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, false);
            fail("The manager error occurs, should throw PersistenceException.");
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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            manager.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = manager.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, true);

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
     * has not exist in the database.
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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            manager.addEntry(expected[i]);
        }

        try {
            manager.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, true);
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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            manager.addEntry(expected[i]);
        }

        // Insert an expense type
        PreparedStatement ps = connection.prepareStatement("Update ExpenseEntries set Billable = 4 where " +
                "ExpenseEntriesID = 2");

        try {
            ps.executeUpdate();
        } finally {
            ps.close();
        }

        try {
            manager.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, true);
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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            manager.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = manager.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, false);

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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            manager.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = manager.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, false);

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
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            manager.addEntry(expected[i]);
        }

        // Insert an expense type
        PreparedStatement ps = connection.prepareStatement("Update ExpenseEntries set Billable = 4 where " +
                "ExpenseEntriesID = 2");

        try {
            ps.executeUpdate();
        } finally {
            ps.close();
        }

        ExpenseEntry[] actual = manager.retrieveEntries(new int[] { 0, 1, 2, 3, 4 }, false);

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
            manager.searchEntries(null);
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
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setDescription("Description" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);
            manager.addEntry(entry);
        }

        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        try {
            manager.searchEntries(new FieldMatchCriteria("ExpenseEntries.ExpenseTypesID", new Integer(type.getId())));
            fail("The persistence error occurs, should throw PersistenceException.");
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
    public void testSearchEntries_Accuracy() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            expected[i].addRejectReason(reason1);
            expected[i].addRejectReason(reason2);
            manager.addEntry(expected[i]);
        }

        ExpenseEntry[] actual = manager.searchEntries(new FieldMatchCriteria("ExpenseEntries.ExpenseTypesID",
                    new Integer(type.getId())));

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.length, actual.length);

        V1Dot1TestHelper.sortDataObjects(Arrays.asList(actual));

        // Verify instances
        for (int i = 0; i < expected.length; ++i) {
            V1Dot1TestHelper.assertEquals(expected[i], actual[i]);
        }
    }
}
