/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.timetracker.entry.expense.search.Criteria;

import java.sql.Connection;

import java.util.List;


/**
 * <p>
 * Defines functionality to manipulate expense entry types in persistence. The operations include adding, updating,
 * deleting and retrieving. The information in expense entry type is used directly without any modifications. Usually,
 * user should not use this interface directly.
 * </p>
 *
 * <p>
 * Implementations should provide two constructors accpeting one and two string arguments respectively in order to be
 * used by <code>ExpenseEntryTypeManager</code> class. For one-argument constructor, the argument defines a
 * configuration namespace. For two-argument constructor, the arguments define a connection producer name and a
 * configuration namespace.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public interface ExpenseEntryTypePersistence {
    /**
     * <p>
     * Adds a new <code>ExpenseEntryType</code> instance to the persistence.
     * </p>
     *
     * @param type the expense entry type to be added to the persistence.
     *
     * @return <code>true</code> if the ID does not exist in persistence and the expense entry type is added;
     *         <code>false</code> otherwise.
     *
     * @throws NullPointerException if <code>type</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when adding the expense entry type.
     */
    public boolean addType(ExpenseEntryType type) throws PersistenceException;

    /**
     * <p>
     * Deletes an <code>ExpenseEntryType</code> instance with the given ID from the persistence.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be deleted.
     *
     * @return <code>true</code> if the expense entry type exists in persistence and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry type.
     */
    public boolean deleteType(int typeId) throws PersistenceException;

    /**
     * <p>
     * Deletes all <code>ExpenseEntryType</code> instances in the persistence.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry types.
     */
    public void deleteAllTypes() throws PersistenceException;

    /**
     * <p>
     * Updates an <code>ExpenseEntryType</code> instance to the persistence.
     * </p>
     *
     * @param type the expense entry type to be updated in the persistence.
     *
     * @return <code>true</code> if the expense entry type exists in persistence and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws NullPointerException if <code>type</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when updating the expense entry type.
     */
    public boolean updateType(ExpenseEntryType type) throws PersistenceException;

    /**
     * <p>
     * Retrieves an <code>ExpenseEntryType</code> instance with the given ID from the persistence.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be retrieved.
     *
     * @return an <code>ExpenseEntryType</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry type; or the value in persistence
     *         is invalid.
     */
    public ExpenseEntryType retrieveType(int typeId) throws PersistenceException;

    /**
     * <p>
     * Retrieves all <code>ExpenseEntryType</code> instances from the persistence.
     * </p>
     *
     * @return a list of <code>ExpenseEntryType</code> instances retrieved from the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry types; or the value in
     *         persistence is invalid.
     */
    public List retrieveAllTypes() throws PersistenceException;

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
     *
     * @since 2.0
     */
    public ExpenseEntryType[] searchEntries(Criteria criteria) throws PersistenceException;

    /**
     * <p>
     * Initializes the existing connection which is used to access database for future operations. The previous
     * connection is closed if it exists. The new connection is created from DB connection factory according to the
     * given connection producer name. Only useful when persistence is JDBC-based.
     * </p>
     *
     * @param connectionProducerName the connection producer name to obtain a connection from DB connection factory.
     *
     * @throws NullPointerException if <code>connectionProducerName</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>connectionProducerName</code> is empty string.
     * @throws PersistenceException if error occurs when closing the previous connection; or error occurs when
     *         obtaining the new connection from DB connection factory.
     */
    public void initConnection(String connectionProducerName) throws PersistenceException;

    /**
     * <p>
     * Sets the existing connection which is used to access database for future operations. The previous connection is
     * closed if it exists. Only useful when persistence is JDBC-based.
     * </p>
     *
     * @param connection the connection used to access database.
     *
     * @throws NullPointerException if <code>connection</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when closing the existing connection.
     */
    public void setConnection(Connection connection) throws PersistenceException;

    /**
     * <p>
     * Gets the existing connection. If there is no existing connection, <code>null</code> is returned. Only useful
     * when persistence is JDBC-based.
     * </p>
     *
     * @return the existing connection if it is available; <code>null</code> otherwise.
     */
    public Connection getConnection();

    /**
     * <p>
     * Closes the existing connection if available. Only useful when persistence is JDBC-based.
     * </p>
     *
     * @throws PersistenceException if error occurs when closing the connection.
     */
    public void closeConnection() throws PersistenceException;
}
