/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;

import java.sql.Connection;

import java.util.List;


/**
 * <p>
 * Defines functionality to manipulate expense entries in persistence. The operations include adding, updating,
 * deleting and retrieving. The information in expense entry is used directly without any modifications. Usually,
 * user should not use this interface directly.
 * </p>
 *
 * <p>
 * Implementations should provide two constructors accpeting one and two string arguments respectively in order to be
 * used by <code>ExpenseEntryManager</code> class. For one-argument constructor, the argument defines a
 * configuration namespace. For two-argument constructor, the arguments define a connection producer name and a
 * configuration namespace.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public interface ExpenseEntryPersistence {
    /**
     * <p>
     * Adds a new <code>ExpenseEntry</code> instance to the persistence.
     * </p>
     *
     * @param entry the expense entry to be added to the persistence.
     *
     * @return <code>true</code> if the ID does not exist in persistence and the expense entry is added;
     *         <code>false</code> otherwise.
     *
     * @throws NullPointerException if <code>entry</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when adding the expense entry to the persistence.
     */
    public boolean addEntry(ExpenseEntry entry) throws PersistenceException;

    /**
     * <p>
     * Deletes an <code>ExpenseEntry</code> instance with the given ID from the persistence.
     * </p>
     *
     * @param entryId the ID of the expense entry to be deleted.
     *
     * @return <code>true</code> if the expense entry exists in persistence and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry.
     */
    public boolean deleteEntry(int entryId) throws PersistenceException;

    /**
     * <p>
     * Deletes all <code>ExpenseEntry</code> instances in the persistence.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entries.
     */
    public void deleteAllEntries() throws PersistenceException;

    /**
     * <p>
     * Updates an <code>ExpenseEntry</code> instance to the persistence.
     * </p>
     *
     * @param entry the expense entry to be updated in the persistence.
     *
     * @return <code>true</code> if the expense entry exists in persistence and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws NullPointerException if <code>entry</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when updating the expense entry.
     */
    public boolean updateEntry(ExpenseEntry entry) throws PersistenceException;

    /**
     * <p>
     * Retrieves an <code>ExpenseEntry</code> instance with the given ID from the persistence. The related
     * <code>ExpenseEntryType</code> and <code>ExpenseEntryStatus</code> instances are also retrieved.
     * </p>
     *
     * @param entryId the ID of the expense entry to be retrieved.
     *
     * @return an <code>ExpenseEntry</code> instance with the given ID; or <code>null</code> if such instance cannot
     *         be found in the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry; or the value in persistence is
     *         invalid.
     */
    public ExpenseEntry retrieveEntry(int entryId) throws PersistenceException;

    /**
     * <p>
     * Retrieves all <code>ExpenseEntry</code> instances from the persistence. The related
     * <code>ExpenseEntryType</code> and <code>ExpenseEntryStatus</code> instances are also retrieved.
     * </p>
     *
     * @return a list of <code>ExpenseEntry</code> instances retrieved from the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entries; or the value in persistence
     *         is invalid.
     */
    public List retrieveAllEntries() throws PersistenceException;

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
    public void initConnection(String connectionProducerName)
        throws PersistenceException;

    /**
     * <p>
     * Sets the existing connection which is used to access database for future operations. The previous connection
     * is closed if it exists. Only useful when persistence is JDBC-based.
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






