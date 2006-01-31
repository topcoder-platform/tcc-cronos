/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;

import java.sql.Connection;

import java.util.List;


/**
 * <p>
 * Defines functionality to manipulate expense entry statuses in persistence. The operations include adding,
 * updating, deleting and retrieving. The information in expense entry status is used directly without any
 * modifications. Usually, user should not use this interface directly.
 * </p>
 *
 * <p>
 * Implementations should provide two constructors accpeting one and two string arguments respectively in order to be
 * used by <code>ExpenseEntryStatusManager</code> class. For one-argument constructor, the argument defines a
 * configuration namespace. For two-argument constructor, the arguments define a connection producer name and a
 * configuration namespace.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public interface ExpenseEntryStatusPersistence {
    /**
     * <p>
     * Adds a new <code>ExpenseEntryStatus</code> instance to the persistence.
     * </p>
     *
     * @param status the expense entry status to be added to the persistence.
     *
     * @return <code>true</code> if the ID does not exist in persistence and the expense entry status is added;
     *         <code>false</code> otherwise.
     *
     * @throws NullPointerException if <code>status</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when adding the expense entry status.
     */
    public boolean addStatus(ExpenseEntryStatus status)
        throws PersistenceException;

    /**
     * <p>
     * Deletes an <code>ExpenseEntryStatus</code> instance with the given ID from the persistence.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be deleted.
     *
     * @return <code>true</code> if the expense entry status exists in persistence and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry status.
     */
    public boolean deleteStatus(int statusId) throws PersistenceException;

    /**
     * <p>
     * Deletes all <code>ExpenseEntryStatus</code> instances in the persistence.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry statuses.
     */
    public void deleteAllStatuses() throws PersistenceException;

    /**
     * <p>
     * Updates an <code>ExpenseEntryStatus</code> instance to the persistence.
     * </p>
     *
     * @param status the expense entry status to be updated in the persistence.
     *
     * @return <code>true</code> if the expense entry status exists in persistence and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws NullPointerException if <code>status</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when updating the expense entry status.
     */
    public boolean updateStatus(ExpenseEntryStatus status)
        throws PersistenceException;

    /**
     * <p>
     * Retrieves an <code>ExpenseEntryStatus</code> instance with the given ID from the persistence.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be retrieved.
     *
     * @return an <code>ExpenseEntryStatus</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry status; or the value in
     *         persistence is invalid.
     */
    public ExpenseEntryStatus retrieveStatus(int statusId)
        throws PersistenceException;

    /**
     * <p>
     * Retrieves all <code>ExpenseEntryStatus</code> instances from the persistence.
     * </p>
     *
     * @return a list of <code>ExpenseEntryStatus</code> instances retrieved from the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry statuses; or the value in
     *         persistence is invalid.
     */
    public List retrieveAllStatuses() throws PersistenceException;

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






