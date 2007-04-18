/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;


/**
 * <p>
 * The local interface of the Expense Entry EJB, which provides access to the persistent store for expense entries
 * managed by the application. It extends <code>BasicExpenseEntryLocal</code> by providing batch operations.
 * <code>ExpenseEntryBean</code> is the corresponding bean that will perform the actual tasks.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public interface ExpenseEntryLocal extends BasicExpenseEntryLocal {
    /**
     * <p>
     * Adds atomically set of entries to the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * @param entries the entries to add.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set for any expense entry.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    void addEntries(ExpenseEntry[] entries, boolean auditFlag) throws InsufficientDataException, PersistenceException;

    /**
     * <p>
     * Adds a set of entries to the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * If the addition is atomic then it means that entire addition will fail if a single expense entry addition fails.
     * </p>
     *
     * <p>
     * If the addition is non-atomic then it means each expense entry is added individually. If it fails, that won't
     * affect the others. A list with the failed entries is returned to the user (empty if no error occurs).
     * </p>
     *
     * @param entries the entries to add.
     * @param isAtomic whether the operation should be atomic or not.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return the entries that failed to be added in non atomic mode (null in atomic mode).
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set for any expense entry.(in atomic mode only)
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    ExpenseEntry[] addEntries(ExpenseEntry[] entries, boolean isAtomic, boolean auditFlag)
        throws InsufficientDataException, PersistenceException;

    /**
     * <p>
     * Updates atomically a set of entries in the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * @param entries the entries to update.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    void updateEntries(ExpenseEntry[] entries, boolean auditFlag)
        throws InsufficientDataException, PersistenceException;

    /**
     * <p>
     * Updates a set of entries in the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * If the update is atomic then it means that entire update will fail if a single expense entry update fails.
     * </p>
     *
     * <p>
     * If the update is non-atomic then it means each expense entry is updated individually. If it fails, that won't
     * affect the others. A list with the failed entries is returned to the user (empty if no error occurs).
     * </p>
     *
     * @param entries the ids of the entries to update
     * @param isAtomic whether the operation should be atomic or not
     * @param auditFlag audit flag which specifies if the user want to audit the action
     *
     * @return the entries that failed to be updated in non atomic mode (null in atomic mode)
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.(in atomic mode only)
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    ExpenseEntry[] updateEntries(ExpenseEntry[] entries, boolean isAtomic, boolean auditFlag)
        throws InsufficientDataException, PersistenceException;

    /**
     * <p>
     * Deletes atomically a set of entries from the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * @param entryIds the ids of the entries to delete.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception)
     */
    void deleteEntries(long[] entryIds, boolean auditFlag) throws PersistenceException;

    /**
     * <p>
     * Deletes a set of entries from the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * If the deletion is atomic then it means that entire retrieval will fail if a single expense entry deletion
     * fails.
     * </p>
     *
     * <p>
     * If the deletion is non-atomic then it means each expense entry is deleted individually. If it fails, that won't
     * affect the others. A list with the failed ids is returned to the user (empty if no error occurs).
     * </p>
     *
     * @param entryIds the ids of the entries to delete
     * @param isAtomic whether the operation should be atomic or not
     * @param auditFlag audit flag which specifies if the user want to audit the action
     *
     * @return the entries that failed to be deleted in non atomic mode (null in atomic mode)
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    long[] deleteEntries(long[] entryIds, boolean isAtomic, boolean auditFlag) throws PersistenceException;

    /**
     * <p>
     * Retrieves atomically a set of entries with given ids from the database.
     * </p>
     *
     * @param entryIds the ids of the entries to retrieve.
     *
     * @return the entries that were retrieved in both modes.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    ExpenseEntry[] retrieveEntries(long[] entryIds) throws PersistenceException;

    /**
     * <p>
     * Retrieves a set of entries with given ids from the database.
     * </p>
     *
     * <p>
     * If the retrieval is atomic then it means that entire retrieval will fail if a single expense entry retrieval
     * fails.
     * </p>
     *
     * <p>
     * If the retrieval is non-atomic then it means each expense entry is retrieved individually. If it fails that
     * won't affect the others. The potentially partial list of results will be returned.
     * </p>
     *
     * @param entryIds the ids of the entries to retrieve.
     * @param isAtomic whether the operation should be atomic or not.
     *
     * @return the entries that were retrieved in either mode, or empty if none found.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    ExpenseEntry[] retrieveEntries(long[] entryIds, boolean isAtomic) throws PersistenceException;
}
