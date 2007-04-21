/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

/**
 * Mock for <code>ExpenseEntryManager</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class MockExpenseManager implements ExpenseEntryManager {

    /**
     * Mock method.
     *
     * @param entries
     *            not used
     * @param auditFlag
     *            not used
     *
     * @throws InsufficientDataException
     *             not thrown
     * @throws PersistenceException
     *             not thrown
     */
    public void addEntries(ExpenseEntry[] entries, boolean auditFlag) throws InsufficientDataException,
        PersistenceException {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param entries
     *            not used
     * @param auditFlag
     *            not used
     * @param isAtomic
     *            not used
     *
     * @return null
     *
     * @throws InsufficientDataException
     *             not thrown
     * @throws PersistenceException
     *             not thrown
     */
    public ExpenseEntry[] addEntries(ExpenseEntry[] entries, boolean auditFlag, boolean isAtomic)
        throws InsufficientDataException, PersistenceException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param entry
     *            not used
     * @param auditFlag
     *            not used
     *
     * @return false
     *
     * @throws InsufficientDataException
     *             not thrown
     * @throws PersistenceException
     *             not thrown
     */
    public boolean addEntry(ExpenseEntry entry, boolean auditFlag) throws InsufficientDataException,
        PersistenceException {
        return false;
    }

    /**
     * Mock method.
     *
     * @param auditFlag
     *            not used
     *
     * @throws PersistenceException
     *             not thrown
     */
    public void deleteAllEntries(boolean auditFlag) throws PersistenceException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param entryIds
     *            not used
     * @param auditFlag
     *            not used
     *
     * @throws PersistenceException
     *             not thrown
     */
    public void deleteEntries(int[] entryIds, boolean auditFlag) throws PersistenceException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param entryIds
     *            not used
     * @param auditFlag
     *            not used
     * @param isAtomic
     *            not used
     *
     * @return null
     *
     * @throws PersistenceException
     *             not thrown
     */
    public int[] deleteEntries(int[] entryIds, boolean auditFlag, boolean isAtomic) throws PersistenceException {

        return null;
    }

    /**
     * Mock method.
     *
     * @param entryId
     *            not used
     * @param auditFlag
     *            not used
     *
     * @return false
     *
     * @throws PersistenceException
     *             not thrown
     */
    public boolean deleteEntry(long entryId, boolean auditFlag) throws PersistenceException {
        return false;
    }

    /**
     * Mock method.
     *
     * @return null
     *
     * @throws PersistenceException
     *             not thrown
     */
    public ExpenseEntry[] retrieveAllEntries() throws PersistenceException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param entryIds
     *            not used
     *
     * @return empty array
     *
     * @throws PersistenceException
     *             not thrown
     */
    public ExpenseEntry[] retrieveEntries(long[] entryIds) throws PersistenceException {
        return new ExpenseEntry[0];
    }

    /**
     * Mock method.
     *
     * @param entryIds
     *            not used
     * @param isAtomic
     *            not used
     *
     * @return empty array
     *
     * @throws PersistenceException
     *             not thrown
     */
    public ExpenseEntry[] retrieveEntries(int[] entryIds, boolean isAtomic) throws PersistenceException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param entryId
     *            not used
     *
     * @return null
     *
     * @throws PersistenceException
     *             not thrown
     */
    public ExpenseEntry retrieveEntry(long entryId) throws PersistenceException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param criteria
     *            not used
     *
     * @return an array contains one element
     *
     * @throws PersistenceException
     *             not thrown
     */
    public ExpenseEntry[] searchEntries(Criteria criteria) throws PersistenceException {
        ExpenseEntry expenseEntry = new ExpenseEntry();
        return new ExpenseEntry[] {expenseEntry};
    }

    /**
     * Mock method.
     *
     * @param entries
     *            not used
     * @param auditFlag
     *            not used
     *
     * @throws PersistenceException
     *             not thrown
     */
    public void updateEntries(ExpenseEntry[] entries, boolean auditFlag) throws PersistenceException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param entries
     *            not used
     * @param auditFlag
     *            not used
     * @param isAtomic
     *            not used
     *
     * @return null
     *
     * @throws PersistenceException
     *             not thrown
     */
    public ExpenseEntry[] updateEntries(ExpenseEntry[] entries, boolean auditFlag, boolean isAtomic)
        throws PersistenceException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param entry
     *            not used
     * @param auditFlag
     *            not used
     *
     * @return false
     *
     * @throws PersistenceException
     *             not thrown
     */
    public boolean updateEntry(ExpenseEntry entry, boolean auditFlag) throws PersistenceException {
        return false;
    }

}
