/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;


/**
 * <p>
 * This interface defines the contract for the complete management of an expense entry. It provides single and batch
 * CRUD operations with the choice to perform audits on all writeable operations using the Time Tracker Audit 3.1
 * component. Furthermore, all batch operations support selective operations, so these calls can be either atomic or
 * non-atomic. Atomic mode means that a failure on one entry causes the entire operation to fail. Non-atomic means
 * that a failure in one entry doesn't affect the other and the user has a way to know which ones failed. It has one
 * implementation in this design: <code>ExpenseEntryManagerLocalDelegate</code>.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Implementations need not necessarily be thread safe. Each implementation should specify
 * whether it is thread-safe or not. The application should pick the correct implementation for it's requirements.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public interface ExpenseEntryManager {
    /**
     * <p>
     * Adds a new <code>ExpenseEntry</code> instance to the database. Audit actions will be done if the auditFlag is
     * specified.
     * </p>
     *
     * @param entry the expense entry to be added to the database.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry is added;
     *         <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if the argument is null.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    boolean addEntry(ExpenseEntry entry, boolean auditFlag) throws InsufficientDataException, PersistenceException;

    /**
     * <p>
     * Updates an <code>ExpenseEntry</code> instance to the database. Audit actions will be done if the auditFlag is
     * specified.
     * </p>
     *
     * @param entry the expense entry to be updated in the database.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the expense entry exists in database and is updated; <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if <code>entry</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    boolean updateEntry(ExpenseEntry entry, boolean auditFlag) throws InsufficientDataException, PersistenceException;

    /**
     * <p>
     * Deletes an <code>ExpenseEntry</code> instance with the given ID from the database. Audit actions will be done if
     * the auditFlag is specified.
     * </p>
     *
     * @param entryId the ID of the expense entry to be deleted.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the expense entry exists in database and is deleted; <code>false</code> otherwise.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    boolean deleteEntry(long entryId, boolean auditFlag) throws PersistenceException;

    /**
     * <p>
     * Retrieves an <code>ExpenseEntry</code> instance with the given ID from the database. The related
     * <code>Invoice</code>, <code>ExpenseType</code> and <code>ExpenseStatus</code> instances are also retrieved.
     * </p>
     *
     * @param entryId the ID of the expense entry to be retrieved.
     *
     * @return an <code>ExpenseEntry</code> instance with the given ID; or <code>null</code> if such instance cannot be
     *         found in the database.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    ExpenseEntry retrieveEntry(long entryId) throws PersistenceException;

    /**
     * <p>
     * Deletes all <code>ExpenseEntry</code> instances in the database. Audit actions will be done if the auditFlag is
     * specified.
     * </p>
     *
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    void deleteAllEntries(boolean auditFlag) throws PersistenceException;

    /**
     * <p>
     * Retrieves all <code>ExpenseEntry</code> instances from the database. The related <code>Invoice</code>,
     * <code>ExpenseType</code> and <code>ExpenseStatus</code> instances are also retrieved.
     * </p>
     *
     * @return an array of <code>ExpenseEntry</code> instances retrieved from the database, or an empty array if there
     *         is no record.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    ExpenseEntry[] retrieveAllEntries() throws PersistenceException;

    /**
     * <p>
     * Performs a search for expense entries matching a given criteria. The criteria is abstracted using the <code>
     * Criteria</code> interface. The <code>Criteria</code> implementations cover the basic SQL filtering abilities
     * (=, like, between, or, and, not). The result of the search is an array with the matched expense entries. It is
     * empty if no matches found (but it can't be <code>null</code> or contain <code>null</code>) elements.
     * </p>
     *
     * @param criteria the criteria to be used in the search.
     *
     * @return the results of the search (can be empty if no matches found).
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    ExpenseEntry[] searchEntries(Criteria criteria) throws PersistenceException;

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
