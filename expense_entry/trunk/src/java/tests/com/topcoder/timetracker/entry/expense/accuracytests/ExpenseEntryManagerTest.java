/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.ExpenseEntryRejectReason;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;

import junit.framework.TestCase;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;


/**
 * <p>
 * Accuracy tests for class ExpenseEntryManager. Here only the new methods not in version 1.0 are tested.
 * </p>
 *
 * @author -oo-
 * @author kr00tki
 * @version 2.0
 * @since 1.1
 */
public class ExpenseEntryManagerTest extends TestCase {
    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.expense";

    /** Represents the database connection to access database. */
    private Connection connection = null;

    /** Represents a valid expense entry instance. */
    private ExpenseEntry entry;

    /** Represents a valid expense entries array instance. */
    private ExpenseEntry[] entries;

    /** Represents a valid expense entry type instance. */
    private ExpenseEntryType type;

    /** Represents a valid expense entry status instance. */
    private ExpenseEntryStatus status;

    /** Represents the manager instance used in tests. */
    private ExpenseEntryManager manager;

    /**
     * <p>
     * Sets up the test environment. Valid configurations are loaded. A valid manager is created. The data table is
     * truncated. A valid expense entry entry is created. One expense type and one expense status are added into the
     * database.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.addValidConfiguration();

        // Create the manager
        manager = new ExpenseEntryManager(NAMESPACE);

        TestHelper.initDatabase();

        type = TestHelper.createTypeInstance();
        status = TestHelper.createStatusInstance();

        entry = new ExpenseEntry(5);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        for (int i = 0; i < 3; i++) {
            entry.addRejectReason(new ExpenseEntryRejectReason(i));
        }

        entries = new ExpenseEntry[3];

        for (int i = 0; i < entries.length; ++i) {
            entries[i] = new ExpenseEntry(i * 10);
            entries[i].setCreationUser("create" + i);
            entries[i].setModificationUser("modify" + i);
            entries[i].setDescription("description" + i);
            entries[i].setAmount(new BigDecimal(i * 1000));
            entries[i].setBillable(true);
            entries[i].setDate(TestHelper.createDate(2005, 7, i));
            entries[i].setExpenseType(type);
            entries[i].setStatus(status);
            entries[i].addRejectReason(new ExpenseEntryRejectReason(i));
            // since 2.0
            entries[i].setCompanyId(TestHelper.COMPANY_ID);
        }

        initConnection();
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed. The data table is truncated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.clearDatabase(connection);
        closeConnection();
    }

    /**
     * Accuracy tests for addEntries() in atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testAddEntries1() throws Exception {
        // test while all things OK
        assertNull("the return value is not correct", manager.addEntries(entries, true));

        List actual = manager.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, actual.size());
        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * Accuracy tests for addEntries() in non-atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testAddEntries2() throws Exception {
        // test while all things OK
        ExpenseEntry[] ret = manager.addEntries(entries, false);
        assertEquals("the return value is not correct", 0, ret.length);

        List actual = manager.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, actual.size());
        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * Accuracy tests for deleteEntries() in atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteEntries1() throws Exception {
        // test while all entries already existed
        TestHelper.initDatabase();
        manager.addEntries(entries, true);

        int[] ret = manager.deleteEntries(new int[] { entries[0].getId(), entries[1].getId(), entries[2].getId() }, true);
        assertNull("the return value is not correct", ret);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
            assertRejectReasonsNotExist(entries[i]);
        }
    }

    /**
     * Accuracy tests for deleteEntries() in non-atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteEntries2() throws Exception {
        // test while all entries already existed
        TestHelper.initDatabase();
        manager.addEntries(entries, true);

        int[] ret = manager.deleteEntries(new int[] { entries[0].getId(), entries[1].getId(), entries[2].getId() },
                false);
        assertEquals("the return value is not correct", 0, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
            assertRejectReasonsNotExist(entries[i]);
        }
    }

    /**
     * Accuracy tests for updateEntries() in atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateEntries1() throws Exception {
        // test while all entries already existed
        TestHelper.initDatabase();
        manager.addEntries(entries, true);

        for (int i = 0; i < entries.length; ++i) {
            entries[i].setAmount(entry.getAmount());
            entries[i].setDate(entry.getDate());
            entries[i].setDescription(entry.getDescription());
            entries[i].setExpenseType(entry.getExpenseType());
            entries[i].setStatus(entry.getStatus());
        }

        ExpenseEntry[] ret = manager.updateEntries(entries, true);
        assertNull("the return value is not correct", ret);

        List result = manager.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Accuracy tests for updateEntries() in non-atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateEntries2() throws Exception {
        // test while all entries already existed
        TestHelper.initDatabase();
        manager.addEntries(entries, true);

        for (int i = 0; i < entries.length; ++i) {
            entries[i].setAmount(entry.getAmount());
            entries[i].setDate(entry.getDate());
            entries[i].setDescription(entry.getDescription());
            entries[i].setExpenseType(entry.getExpenseType());
            entries[i].setStatus(entry.getStatus());
        }

        ExpenseEntry[] ret = manager.updateEntries(entries, false);
        assertEquals("the return value is not correct", 0, ret.length);

        List result = manager.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Accuracy tests for retrieveEntries() in atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntries1() throws Exception {
        // test while all entries already existed
        TestHelper.initDatabase();
        manager.addEntries(entries, true);

        ExpenseEntry[] ret = manager.retrieveEntries(new int[] {
                    entries[0].getId(), entries[1].getId(), entries[2].getId()
                }, true);
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }

        List result = manager.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Accuracy tests for retrieveEntries() in non-atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntries2() throws Exception {
        // test while all entries already existed
        TestHelper.initDatabase();
        manager.addEntries(entries, true);

        ExpenseEntry[] ret = manager.retrieveEntries(new int[] {
                    entries[0].getId(), entries[1].getId(), entries[2].getId()
                }, false);
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }

        List result = manager.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Initialize the connection field.
     *
     * @throws Exception to JUnit
     */
    private void initConnection() throws Exception {
        if (connection == null) {
            connection = TestHelper.getConnection();
        }
    }

    /**
     * Close the connection field.
     *
     * @throws Exception to JUnit
     */
    private void closeConnection() throws Exception {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * Asserts the entry does not exist in database.
     *
     * @param entry the expense entry to assert
     *
     * @throws Exception to JUnit
     */
    private void assertExpenseEntryNotExist(ExpenseEntry entry)
        throws Exception {
        // Verify record in database
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            initConnection();
            ps = connection.prepareStatement("SELECT * FROM expense_entry WHERE expense_entry_id=?");
            ps.setInt(1, entry.getId());
            resultSet = ps.executeQuery();

            assertFalse("The record should not exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * Asserts all of the reject reasons of entry do not exist in database.
     *
     * @param entry the expense entry to assert
     *
     * @throws Exception to JUnit
     */
    private void assertRejectReasonsNotExist(ExpenseEntry entry)
        throws Exception {
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            initConnection();
            ps = connection.prepareStatement("SELECT * FROM exp_reject_reason WHERE expense_entry_id=?");
            ps.setInt(1, entry.getId());
            resultSet = ps.executeQuery();

            assertFalse("The reject reasons should not exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (ps != null) {
                ps.close();
            }
        }
    }
}
