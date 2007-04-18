/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;


/**
 * <p>
 * This interface defines the contract for the complete management of an expense type. It provides CRUDE and extensive
 * search operations. It has one implementation in this design: <code>ExpenseTypeManagerLocalDelegate</code>.
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
public interface ExpenseTypeManager {
    /**
     * <p>
     * Adds a new <code>ExpenseType</code> instance to the database.
     * </p>
     *
     * @param type the expense entry type to be added to the database.
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry type is added;
     *         <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if the argument is null, or if the creation date or modification date is not
     *         <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when adding the expense entry type.
     */
    boolean addType(ExpenseType type) throws InsufficientDataException, PersistenceException;

    /**
     * <p>
     * Deletes an <code>ExpenseType</code> instance with the given ID from the database.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be deleted.
     *
     * @return <code>true</code> if the expense entry type exists in database and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry type.
     */
    boolean deleteType(long typeId) throws PersistenceException;

    /**
     * <p>
     * Deletes all <code>ExpenseType</code> instances in the database.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry types.
     */
    void deleteAllTypes() throws PersistenceException;

    /**
     * <p>
     * Updates an <code>ExpenseType</code> instance to the database.
     * </p>
     *
     * @param type the expense entry type to be updated in the database.
     *
     * @return <code>true</code> if the expense entry type exists in database and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws IllegalArgumentException if <code>type</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when updating the expense entry type.
     */
    boolean updateType(ExpenseType type) throws InsufficientDataException, PersistenceException;

    /**
     * <p>
     * Retrieves an <code>ExpenseType</code> instance with the given ID from the database.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be retrieved.
     *
     * @return an <code>ExpenseType</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry type; or the value in persistence
     *         is invalid.
     */
    ExpenseType retrieveType(long typeId) throws PersistenceException;

    /**
     * <p>
     * Retrieves all <code>ExpenseType</code> instances from the database.
     * </p>
     *
     * @return an array of <code>ExpenseType</code> instances retrieved from the database, or an empty array if there
     *         is no record.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry types; or the value in database
     *         is invalid.
     */
    ExpenseType[] retrieveAllTypes() throws PersistenceException;

    /**
     * <p>
     * Performs a search for expense types matching a given criteria. The criteria is abstracted using the <code>
     * Criteria</code> interface. The <code>Criteria</code> implementations cover the basic SQL filtering abilities
     * (=, like, between, or, and, not). The result of the search is an array with the matched expense types. It is
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
    ExpenseType[] searchEntries(Criteria criteria) throws PersistenceException;
}
