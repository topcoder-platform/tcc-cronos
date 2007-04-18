/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import javax.ejb.EJBLocalObject;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;


/**
 * <p>
 * The local interface of the Basic Expense Entry EJB, which provides access to the persistent store for expense
 * entries managed by the application. It provides the basic, non-batch operations for managing an expense entry.
 * <code>BasicExpenseEntryBean</code> is the corresponding bean that will perform the actual tasks.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public interface BasicExpenseEntryLocal extends EJBLocalObject {
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
}
