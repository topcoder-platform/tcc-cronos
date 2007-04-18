/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;


/**
 * <p>
 * This interface defines the contract for the complete management of an expense entry statyus. It provides CRUDE
 * methods for this purpose. It additionally provides robust search capabilities. It has one implementation in this
 * design: <code>InformixExpenseStatusDAO</code>.
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
public interface ExpenseEntryStatusDAO {
    /**
     * <p>
     * Adds a new <code>ExpenseStatus</code> instance to the database.
     * </p>
     *
     * @param status the status that will be added to the database
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry status is added;
     *         <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if the argument is null, or if the creation date or modification date is not
     *         <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when adding the expense entry status.
     */
    boolean addStatus(ExpenseStatus status) throws InsufficientDataException, PersistenceException;

    /**
     * <p>
     * Deletes an <code>ExpenseStatus</code> instance with the given ID from the database.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be deleted.
     *
     * @return <code>true</code> if the expense entry status exists in database and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry status.
     */
    boolean deleteStatus(long statusId) throws PersistenceException;

    /**
     * <p>
     * Deletes all <code>ExpenseStatus</code> instances in the database.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry statuses.
     */
    void deleteAllStatuses() throws PersistenceException;

    /**
     * <p>
     * Updates an <code>ExpenseStatus</code> instance to the database.
     * </p>
     *
     * @param status the expense entry status to be updated in the database.
     *
     * @return <code>true</code> if the expense entry status exists in database and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws IllegalArgumentException if <code>status</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when updating the expense entry status.
     */
    boolean updateStatus(ExpenseStatus status) throws InsufficientDataException, PersistenceException;

    /**
     * <p>
     * Retrieves an <code>ExpenseStatus</code> instance with the given ID from the database.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be retrieved.
     *
     * @return an <code>ExpenseStatus</code> instance with the given ID; or <code>null</code> if such instance cannot
     *         be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry status; or the value in database
     *         is invalid.
     */
    ExpenseStatus retrieveStatus(long statusId) throws PersistenceException;

    /**
     * <p>
     * Retrieves all <code>ExpenseStatus</code> instances from the database.
     * </p>
     *
     * @return an array of <code>ExpenseStatus</code> instances retrieved from the database, or an empty array if there
     *         is no record.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry statuses; or the value in
     *         database is invalid.
     */
    ExpenseStatus[] retrieveAllStatuses() throws PersistenceException;

    /**
     * <p>
     * Performs a search for expense status matching a given criteria. The criteria is abstracted using the <code>
     * Criteria</code> interface. The <code>Criteria</code> implementations cover the basic SQL filtering abilities
     * (=, like, between, or, and, not). The result of the search is an array with the matched expense status. It is
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
    ExpenseStatus[] searchEntries(Criteria criteria) throws PersistenceException;
}
