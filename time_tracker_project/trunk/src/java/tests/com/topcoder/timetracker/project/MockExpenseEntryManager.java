/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import java.math.BigDecimal;
import java.util.Date;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;

/**
 * <p>
 * This class implements ExpenseEntryManager interface.
 * It is only used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockExpenseEntryManager implements ExpenseEntryManager {
    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param entry the entry that will be added to the database
     * @param audit audit flag which specifies if the user want to audit the action
     *
     * @return false always
     */
    public boolean addEntry(ExpenseEntry entry, boolean audit) {
        return false;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param entry the entry to be updated
     * @param audit audit flag which specifies if the user want to audit the action
     *
     * @return false always
     */
    public boolean updateEntry(ExpenseEntry entry, boolean audit) {
        return false;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param id the id that identifies the entry
     * @param audit audit flag which specifies if the user want to audit the action
     *
     * @return false always
     */
    public boolean deleteEntry(long id, boolean audit) {
        return false;
    }

    /**
     * <p>
     * Retrieves the entry with the specified id from the database.
     * </p>
     *
     * @return an ExpenseEntry instance or null if not found
     * @param id the id identifying the entry
     */
    public ExpenseEntry retrieveEntry(long id) {
        if (id == 1 || id == 2) {
            ExpenseEntry entry = new ExpenseEntry();
            entry.setId(id);
            entry.setAmount(new BigDecimal(8));
            entry.setBillable(true);
            entry.setCompanyId(1);
            entry.setDate(new Date());

            return entry;
        }

        return null;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param audit audit flag which specifies if the user want to audit the action
     */
    public void deleteAllEntries(boolean audit) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return an empty array
     */
    public ExpenseEntry[] retrieveAllEntries() {
        return new ExpenseEntry[0];
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param criteria the criteria to be used in the search
     * @return an empty array
     */
    public ExpenseEntry[] searchEntries(Criteria criteria) {
        return new ExpenseEntry[0];
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param entries the entries to add
     * @param audit audit flag which specifies if the user want to audit the action
     */
    public void addEntries(ExpenseEntry[] entries, boolean audit) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param entries the entries to add
     * @param audit audit flag which specifies if the user want to audit the action
     * @param isAtomic whether the operation should be atomic or not
     * @return an empty array
     */
    public ExpenseEntry[] addEntries(ExpenseEntry[] entries, boolean audit, boolean isAtomic) {
        return new ExpenseEntry[0];
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param entries the ids of the entries to update
     * @param audit audit flag which specifies if the user want to audit the action
     */
    public void updateEntries(ExpenseEntry[] entries, boolean audit) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param entries the ids of the entries to update
     * @param audit audit flag which specifies if the user want to audit the action
     * @param isAtomic whether the operation should be atomic or not
     * @return an empty array
     */
    public ExpenseEntry[] updateEntries(ExpenseEntry[] entries, boolean audit, boolean isAtomic) {
        return new ExpenseEntry[0];
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param ids the ids of the entries to delete
     * @param audit audit flag which specifies if the user want to audit the action
     */
    public void deleteEntries(long[] ids, boolean audit) {
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param ids the ids of the entries to delete
     * @param audit audit flag which specifies if the user want to audit the action
     * @param isAtomic whether the operation should be atomic or not
     * @return an empty array
     */
    public long[] deleteEntries(long[] ids, boolean audit, boolean isAtomic) {
        return new long[0];
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param ids the ids of the entries to retrieve.
     * @return an empty array
     */
    public ExpenseEntry[] retrieveEntries(long[] ids) {
        return new ExpenseEntry[0];
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param ids the ids of the entries to retrieve
     * @param isAtomic whether the operation should be atomic or not
     * @return an empty array
     */
    public ExpenseEntry[] retrieveEntries(long[] ids, boolean isAtomic) {
        return new ExpenseEntry[0];
    }


}
