/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import com.topcoder.timetracker.entry.time.BaseDAO;
import com.topcoder.timetracker.entry.time.DAOActionException;
import com.topcoder.timetracker.entry.time.DataObject;


/**
 * <p>
 * A mock BaseDAO used for failure tests.
 * </p>
 * 
 * @author GavinWang
 * @version 1.1
 */
class MockBaseDAO11 extends BaseDAO {
    /**
     * <p>
     * Constructor. Sets the connection name and namespace that the component will use to obtain Connections from the
     * DBConnection Factory
     * </p>
     *
     * @param connName The name of the Connection
     * @param namespace Namespace to be used in the DBConnection Factory component.
     *
     * @throws NullPointerException If connName or namespace is null.
     * @throws IllegalArgumentException If connName or namespace is empty.
     */
    protected MockBaseDAO11(String connName, String namespace) {
        super(connName, namespace);
    }

    /**
     * <p>
     * Mock implementation, do nothing here.
     * </p>
     *
     * @param dataObject The DataObject which will be checked
     *
     * @throws NullPointerException If dataObject is null.
     * @throws IllegalArgumentException if DataObject does not match requirements of the DAO implementation.
     */
    protected void verifyCreateDataObject(DataObject dataObject) {
        // do nothing here
    }

    /**
     * <p>
     * Mock implementation, only return null.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for creating a record
     */
    protected String getCreateSqlString() {
        return null;
    }

    /**
     * <p>
     * Mock implementation, do nothting here.
     * </p>
     *
     * @param statement the PreparedStatement to fill with values
     * @param dataObject the DataObject which contains the values to fill into PreparedStatement
     * @param creationUser the creation user of this database operation
     * @param creationDate the creation date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected void fillCreatePreparedStatement(PreparedStatement statement, DataObject dataObject, String creationUser,
        Date creationDate) throws DAOActionException {
        // do nothing
    }

    /**
     * <p>
     * Mock implementation, do nothing here.
     * </p>
     *
     * @param dataObject the DataObject to set creation fields
     * @param creationUser the creation user of this database operation
     * @param creationDate the creation date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected void setCreationParametersInDataObject(DataObject dataObject, String creationUser, Date creationDate)
        throws DAOActionException {
        // do nothing
    }

    /**
     * <p>
     * Mock implementation, do nothing here.
     * </p>
     *
     * @param dataObject t DataObject which will be checked
     *
     * @throws NullPointerException If dataObject is null.
     * @throws IllegalArgumentException if DataObject does not match requirements of the DAO implementation.
     */
    protected void verifyUpdateDataObject(DataObject dataObject) {
        // do nothing
    }

    /**
     * <p>
     * Mock implementation, just return null.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for update a record
     */
    protected String getUpdateSqlString() {
        return null;
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @param statement the PreparedStatement to fill with values
     * @param dataObject the DataObject which contains the values to fill into PreparedStatement
     * @param modificationUser the modification user of this database operation
     * @param modificationDate the modification date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected void fillUpdatePreparedStatement(PreparedStatement statement, DataObject dataObject,
        String modificationUser, Date modificationDate)
        throws DAOActionException {
        // do nothing
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @param dataObject the DataObject to set modification fields
     * @param modificationUser the modification user of this database operation
     * @param modificationDate the modification date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected void setModificationParametersInDataObject(DataObject dataObject, String modificationUser,
        Date modificationDate) throws DAOActionException {
        // do nothing
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for delete a record
     */
    protected String getDeleteSqlString() {
        return null;
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for get a record
     */
    protected String getReadSqlString() {
        return null;
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @param resultSet the resultSet to retrieve DataObject instance
     *
     * @return the retrieved DataObject instance
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected DataObject processReadResultSet(ResultSet resultSet)
        throws DAOActionException {
        return null;
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for get a record list
     */
    protected String getReadListSqlString() {
        return null;
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @param resultSet the resultSet to retrieve DataObject instances
     *
     * @return the retrieved DataObject instances
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected List processReadListResultSet(ResultSet resultSet)
        throws DAOActionException {
        return null;
    }
}
