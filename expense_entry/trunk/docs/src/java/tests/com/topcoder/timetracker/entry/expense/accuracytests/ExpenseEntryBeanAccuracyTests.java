/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import java.util.Arrays;
import java.util.List;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseEntryBean;

/**
 * The test of ExpenseEntryBean.
 *
 * @author brain_cn
 * @version 3.2
 */
public class ExpenseEntryBeanAccuracyTests extends PersistenceTestCase {
    /** Represents the ExpenseEntryBean instance used in tests. */
    private ExpenseEntryBean instance;

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

        // Create the instance
        instance = new ExpenseEntryBean();
        context = new SessionContextTester();
        instance.ejbCreate();
        instance.setSessionContext(context);
    }

    /**
     * Test method for 'ejbCreate()'
     *
     * @throws Exception to JUnit.
     */
    public void testEjbCreate() throws Exception {
        assertNotNull("failed to create", getField(instance, "basicExpenseEntryLocal"));
    }

    /**
     * Test method for 'ExpenseEntryBean()'
     *
     * @throws Exception to JUnit.
     */
    public void testExpenseEntryBean() throws Exception {
        assertNotNull("failed to create", instance);
    }

    /**
     * Test method for 'addEntries(ExpenseEntry[], boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testAddEntriesExpenseEntryArrayBoolean()
        throws Exception {
        // test while all things OK
    	instance.addEntries(entries, true);

        List actual = Arrays.asList(instance.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", entries.length, actual.size());
        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * Test method for 'addEntries(ExpenseEntry[], boolean, boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testAddEntriesExpenseEntryArrayBooleanBoolean_isAtomic()
        throws Exception {
        // test while all things OK
    	instance.addEntries(entries, true, true);

        List actual = Arrays.asList(instance.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", entries.length, actual.size());
        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * Test method for 'addEntries(ExpenseEntry[], boolean, boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testAddEntriesExpenseEntryArrayBooleanBoolean_NotAtomic()
        throws Exception {
        // test while all things OK
    	instance.addEntries(entries, true, false);

        List actual = Arrays.asList(instance.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", entries.length, actual.size());
        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * Test method for 'updateEntries(ExpenseEntry[], boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateEntriesExpenseEntryArrayBoolean()
        throws Exception {
        instance.addEntries(entries, true);

        for (int i = 0; i < entries.length; ++i) {
            entries[i].setAmount(entry.getAmount());
            entries[i].setDate(entry.getDate());
            entries[i].setDescription(entry.getDescription());
            entries[i].setExpenseType(entry.getExpenseType());
            entries[i].setStatus(entry.getStatus());
        }

        instance.updateEntries(entries, true);

        List result = Arrays.asList(instance.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Test method for 'updateEntries(ExpenseEntry[], boolean, boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateEntriesExpenseEntryArrayBooleanBoolean_isAtomic()
        throws Exception {
        instance.addEntries(entries, true);

        for (int i = 0; i < entries.length; ++i) {
            entries[i].setAmount(entry.getAmount());
            entries[i].setDate(entry.getDate());
            entries[i].setDescription(entry.getDescription());
            entries[i].setExpenseType(entry.getExpenseType());
            entries[i].setStatus(entry.getStatus());
        }

        instance.updateEntries(entries, true, true);

        List result = Arrays.asList(instance.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Test method for 'updateEntries(ExpenseEntry[], boolean, boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateEntriesExpenseEntryArrayBooleanBoolean_NotAtomic()
        throws Exception {
        instance.addEntries(entries, true);

        for (int i = 0; i < entries.length; ++i) {
            entries[i].setAmount(entry.getAmount());
            entries[i].setDate(entry.getDate());
            entries[i].setDescription(entry.getDescription());
            entries[i].setExpenseType(entry.getExpenseType());
            entries[i].setStatus(entry.getStatus());
        }

        instance.updateEntries(entries, true, false);

        List result = Arrays.asList(instance.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Test method for 'deleteEntries(long[], boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteEntriesLongArrayBoolean() throws Exception {
        // test while all entries already existed
        TestHelper.initDatabase();
        instance.addEntries(entries, true);

        instance.deleteEntries(new long[] { entries[0].getId(), entries[1].getId(), entries[2].getId() },
                false);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
        }
    }

    /**
     * Test method for 'deleteEntries(long[], boolean, boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteEntriesLongArrayBooleanBoolean_isAtomic()
        throws Exception {
        instance.addEntries(entries, true);

        instance.deleteEntries(new long[] { entries[0].getId(), entries[1].getId(), entries[2].getId() },
                false, true);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
        }
    }

    /**
     * Test method for 'deleteEntries(long[], boolean, boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteEntriesLongArrayBooleanBoolean_NotAtomic()
        throws Exception {
        instance.addEntries(entries, true);

        instance.deleteEntries(new long[] { entries[0].getId(), entries[1].getId(), entries[2].getId() },
                false, false);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
        }
    }

    /**
     * Test method for 'retrieveEntries(long[])'
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveEntriesLongArray() throws Exception {
        instance.addEntries(entries, true);

        ExpenseEntry[] ret = instance.retrieveEntries(new long[] {
                    entries[0].getId(), entries[1].getId(), entries[2].getId()});
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }
    }

    /**
     * Test method for 'retrieveEntries(long[], boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveEntriesLongArrayBoolean_isAtomic() throws Exception {
        instance.addEntries(entries, true);

        ExpenseEntry[] ret = instance.retrieveEntries(new long[] {
                    entries[0].getId(), entries[1].getId(), entries[2].getId()
                }, true);
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }
    }

    /**
     * Test method for 'retrieveEntries(long[], boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveEntriesLongArrayBoolean_NotAtomic() throws Exception {
        instance.addEntries(entries, true);

        ExpenseEntry[] ret = instance.retrieveEntries(new long[] {
                    entries[0].getId(), entries[1].getId(), entries[2].getId()
                }, false);
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }
    }
}
