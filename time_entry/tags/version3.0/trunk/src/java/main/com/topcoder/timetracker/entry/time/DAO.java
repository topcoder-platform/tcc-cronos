/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.sql.Connection;

import java.util.List;


/**
 * <p>
 * Interface. Defines five CRUD(Create, Read, Update, and Delete) database related operations for the <code>DataObject
 * </code>. It also defines other three methods that can be used to external manage connection for the <code>DAO</code>
 * if local transactions are desired. For non-JDBC DAOs, it is recommended that these three methods throw <code>
 * UnsupportedOperationException</code>.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface DAO {
    /**
     * <p>
     * Creates a record in the persistence store based on the info in the data object.
     * </p>
     *
     * @param dataObject the data object instance to be created in persistence.
     * @param user the user who creates this data object.
     *
     * @throws NullPointerException if <code>dataObject</code> or <code>user</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>user</code> is empty string; or any required member in data object
     *         is <code>null</code>.
     * @throws DAOActionException if error occurs when creating the data object in persistence.
     */
    public void create(DataObject dataObject, String user) throws DAOActionException;

    /**
     * <p>
     * Updates the record in the persistence store.
     * </p>
     *
     * @param dataObject the data object instance to be updated in persistence.
     * @param user the user who modifies this data object.
     *
     * @throws NullPointerException if <code>dataObject</code> or <code>user</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>user</code> is empty string; or any required member in data object
     *         is <code>null</code>.
     * @throws DAOActionException if error occurs when updating the data object in persistence.
     */
    public void update(DataObject dataObject, String user) throws DAOActionException;


    /**
     * <p>
     * Deletes the record from the persistence store based on the primary Id. If there is no match, i.e., no such
     * record exists to delete, false is returned. If the deletion is successful, true is returned.
     * </p>
     *
     * @param primaryId The primary Id of the record to be deleted.
     *
     * @return <code>true</code> if the record was deleted successfully. <code>false</code> if no record exists to
     *         delete.
     *
     * @throws DAOActionException if error occurs when deleting the data object from persistence.
     */
    public boolean delete(int primaryId) throws DAOActionException;

    /**
     * <p>
     * Gets a record in the persistence store based on the passed id. Up to the implementations to state the result if
     * no record with such a primary ID exists in the data store.
     * </p>
     *
     * @param primaryId The primary Id of the record to be retrieved.
     *
     * @return DataObjects that has the passed primary Id, or <code>null</code> if it doesn't exist
     *
     * @throws DAOActionException if error occurs when retrieving the data object from persistence.
     */
    public DataObject get(int primaryId) throws DAOActionException;

    /**
     * <p>
     * Gets a list of data objects based on the where clause. A null or empty parameter indicates that no constraints
     * are requested, and the implemention is expected to return all records in this table.
     * </p>
     *
     * @param whereClause The where clause that defines the constraints for the data retrieval.
     *
     * @return List of data objects that meet the search criteria
     *
     * @throws IllegalArgumentException If the <code>whereClause</code> begins with WHERE keyword.
     * @throws DAOActionException if error occurs when retrieving the data objects from persistence.
     */
    public List getList(String whereClause) throws DAOActionException;

    /**
     * <p>
     * Sets the connection. Allows the running application to control the connection and exercise local trsaction
     * control. The <code>DAO</code> would use this connection in lieu of any other connection scheme.
     * </p>
     *
     * @param connection the user defined connection instance
     *
     * @throws NullPointerException if <code>connection</code> is <code>null</code>.
     * @throws UnsupportedOperationException if the DAO does not use JDBC.
     * @throws DAOActionException if error occurs when setting the connection.
     */
    public void setConnection(Connection connection) throws DAOActionException;

    /**
     * <p>
     * Gets the connection that was set by the <code>setConnection</code> method.
     * </p>
     *
     * @return the connection that was set by the setConnection
     *
     * @throws UnsupportedOperationException if the <code>DAO</code> does not use JDBC.
     * @throws DAOActionException if error occurs when getting the connection.
     */
    public Connection getConnection() throws DAOActionException;

    /**
     * <p>
     * Removes the connection that was set by the <code>setConnection</code> method. The <code>DAO</code> would then
     * revert to its normal connection-obtaining schema.
     * </p>
     *
     * @throws UnsupportedOperationException if the DAO does not use JDBC.
     * @throws DAOActionException if error occurs when removing the connection.
     */
    public void removeConnection() throws DAOActionException;
}
