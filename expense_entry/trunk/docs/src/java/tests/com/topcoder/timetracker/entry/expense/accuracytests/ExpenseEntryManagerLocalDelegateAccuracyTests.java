/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import java.util.Arrays;
import java.util.List;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.delegate.ExpenseEntryManagerLocalDelegate;


/**
 * <p>
 * Accuracy tests for class ExpenseEntryManager. Here only the new methods not in version 1.0 are tested.
 * </p>
 *
 * @author -oo-
 * @author kr00tki, brain_cn
 * @version 3.2
 * @since 1.1
 */
public class ExpenseEntryManagerLocalDelegateAccuracyTests extends PersistenceTestCase {
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
    	super.setUp();

        // Create the manager
        manager = new ExpenseEntryManagerLocalDelegate();
    }

    /**
     * Accuracy tests for addEntries() in atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testAddEntries1() throws Exception {
        // test while all things OK
        manager.addEntries(entries, true);

        List actual = Arrays.asList(manager.retrieveAllEntries());
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
        manager.addEntries(entries, false);

        List actual = Arrays.asList(manager.retrieveAllEntries());
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

        manager.deleteEntries(new long[] { entries[0].getId(), entries[1].getId(), entries[2].getId() }, true);
        
        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
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

        manager.deleteEntries(new long[] { entries[0].getId(), entries[1].getId(), entries[2].getId() },
                false);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
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

        manager.updateEntries(entries, true);

        List result = Arrays.asList(manager.retrieveAllEntries());
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

        manager.updateEntries(entries, false);

        List result = Arrays.asList(manager.retrieveAllEntries());
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

        ExpenseEntry[] ret = manager.retrieveEntries(new long[] {
                    entries[0].getId(), entries[1].getId(), entries[2].getId()
                }, true);
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }

        List result = Arrays.asList(manager.retrieveAllEntries());
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

        ExpenseEntry[] ret = manager.retrieveEntries(new long[] {
                    entries[0].getId(), entries[1].getId(), entries[2].getId()
                }, false);
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }

        List result = Arrays.asList(manager.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }
}
