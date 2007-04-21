/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.stresstests;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

/**
 * A mocked implementation for interface ExpenseEntryManager.
 *
 * @author Chenhong
 * @version 1.0
 */
public class MyExpenseEntryManager implements ExpenseEntryManager {

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#addEntry(com.topcoder.timetracker.entry.expense.ExpenseEntry,
     *      boolean)
     */
    public boolean addEntry(ExpenseEntry entry, boolean flag) throws InsufficientDataException, PersistenceException {
        return false;
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#updateEntry(com.topcoder.timetracker.entry.expense.ExpenseEntry,
     *      boolean)
     */
    public boolean updateEntry(ExpenseEntry arg0, boolean arg1) throws PersistenceException {
        return false;
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#deleteEntry(long, boolean)
     */
    public boolean deleteEntry(long arg0, boolean arg1) throws PersistenceException {
        return false;
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#retrieveEntry(long)
     */
    public ExpenseEntry retrieveEntry(long arg0) throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#deleteAllEntries(boolean)
     */
    public void deleteAllEntries(boolean arg0) throws PersistenceException {
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#retrieveAllEntries()
     */
    public ExpenseEntry[] retrieveAllEntries() throws PersistenceException {
        return new ExpenseEntry[0];
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#searchEntries(com.topcoder.timetracker.entry.expense.criteria.Criteria)
     */
    public ExpenseEntry[] searchEntries(Criteria arg0) throws PersistenceException {
        return new ExpenseEntry[0];
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#addEntries(com.topcoder.timetracker.entry.expense.ExpenseEntry[],
     *      boolean)
     */
    public void addEntries(ExpenseEntry[] arg0, boolean arg1) throws InsufficientDataException, PersistenceException {
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#addEntries(com.topcoder.timetracker.entry.expense.ExpenseEntry[],
     *      boolean, boolean)
     */
    public ExpenseEntry[] addEntries(ExpenseEntry[] arg0, boolean arg1, boolean arg2)
            throws InsufficientDataException, PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#updateEntries(com.topcoder.timetracker.entry.expense.ExpenseEntry[],
     *      boolean)
     */
    public void updateEntries(ExpenseEntry[] arg0, boolean arg1) throws PersistenceException {
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#updateEntries(com.topcoder.timetracker.entry.expense.ExpenseEntry[],
     *      boolean, boolean)
     */
    public ExpenseEntry[] updateEntries(ExpenseEntry[] arg0, boolean arg1, boolean arg2) throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#deleteEntries(int[], boolean)
     */
    public void deleteEntries(int[] arg0, boolean arg1) throws PersistenceException {
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#deleteEntries(int[], boolean, boolean)
     */
    public int[] deleteEntries(int[] arg0, boolean arg1, boolean arg2) throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#retrieveEntries(long[])
     */
    public ExpenseEntry[] retrieveEntries(long[] arg0) throws PersistenceException {
        return new ExpenseEntry[0];
    }

    /**
     * @see com.topcoder.timetracker.entry.expense.ExpenseEntryManager#retrieveEntries(int[], boolean)
     */
    public ExpenseEntry[] retrieveEntries(int[] arg0, boolean arg1) throws PersistenceException {
        return new ExpenseEntry[0];
    }

}