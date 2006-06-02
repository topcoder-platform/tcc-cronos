/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


/**
 * <p>
 * <code>BaseDAO</code> class implements the <code>DAO</code> interface.
 * </p>
 *
 * <p>
 * It contains a connection name and connection namespace to get connection from DBConnection factory in normal
 * connection-obtaining schema, and it also allows the running application to control the connection and exercise
 * local transaction control. It implements the five CRUD(Create, Read, Update, and Delete) database related
 * operations. These methods rely on helper methods to provide entity-specific information such as connection
 * creation, resource closing, primarykey generation and etc. Some of the helper methods are implemented here for its
 * common sense.
 * </p>
 *
 * <p>
 * Version 1.1 changes: Four new methods for doing bulk operations on sets of times entries have been added. These
 * method can work in atomic mode (a failure on one entry causes the entire operation to fail) or non-atomic (a
 * failure in one entry doesn't affect the other and the user has a way to know which ones failed).
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @author argolite, TCSDEVELOPER
 * @version 2.0
 *
 * @since 1.0
 */
public abstract class BaseDAO implements DAO, BatchDAO {
    /**
     * <p>
     * Represents the 'where' keyword used in the costruction of the sql query string for getting a list of DataObjects
     * in the <code>getList</code> method. It is expected that <code>BaseDAO</code> implementations will add this
     * keyword when doing the construction, and the application passes a clause.
     * </p>
     */
    public static final String SQL_WHERE_KEYWORD = " WHERE ";

    /**
     * <p>
     * Represents the JDBC connection that will be used by all methods to perform CRUD operations. Set in the
     * <code>setConnection</code> method only. Therefore, it will never be used to hold state for a conection obtained
     * from the DBConnection Factory via the <code>createconnection</code> method. It's default value is null.
     * </p>
     */
    private Connection connection = null;

    /**
     * <p>
     * Represents the name of the connection that will be used by instances of this class to obtain a connection from
     * the DBConnection Factory. Set in the constructor and will never change.
     * </p>
     */
    private final String connName;

    /**
     * <p>
     * Represents a flag as to whether this class is to create (and close) its own connection. If false, then this
     * class is to use the connection passed by the application. It is only set in two places: <code>setConnection
     * </code> method sets it to false, and <code>removeConnection</code> makes it true. The <code>createConnection
     * </code> uses this flag to determine if it is to request a connection form the DBConnection Factory(true) or to
     * use the connection object passed by the application (false).
     * </p>
     */
    private boolean useOwnConnection = true;

    /**
     * <p>
     * Represents the namespace to be used in the <code>createConnection</code> when obtaining a connection from the
     * DBConnection Factory. Set in the constructor and will never change.
     * </p>
     */
    private final String namespace;

    /**
     * <p>
     * The DBConnectionFactory instance to creation the connection.
     * </p>
     */
    private DBConnectionFactory factory = null;

    /**
     * <p>
     * Creates a new instance of <code>BaseDAO</code> class. Accepts a name of the connection and namespace that will
     * be used by the createConnection method to ask the DBConnection Factory for a Connection object whenever methods
     * need to perform CRUD operations and use their own connections. Extending classes will call this constructor
     * with a connection name and a namespace. Both must be non-null and non-empty.
     * </p>
     *
     * @param connName the connection name used to create database connection from DB connection factory.
     * @param namespace the namespace used to create DB connection factory.
     *
     * @throws NullPointerException if <code>connName</code> or <code>namespace</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>connName</code> or <code>namespace</code> is empty string.
     */
    protected BaseDAO(String connName, String namespace) {
        TimeEntryHelper.checkString(connName, "connection name");
        TimeEntryHelper.checkString(namespace, "namespace");
        this.connName = connName;
        this.namespace = namespace;
    }

    /**
     * <p>
     * Creates a record in the persistence store based on the info in the data object.
     * </p>
     *
     * @param dataObject <code>DataObject</code> to create
     * @param user The user that initiates this creation.
     *
     * @throws NullPointerException if <code>dataObject</code> or <code>user</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>user</code> is empty string; or any required member in data object is
     *         <code>null</code>.
     * @throws DAOActionException if error occurs when creating the data object in database.
     */
    public void create(DataObject dataObject, String user) throws DAOActionException {
        TimeEntryHelper.checkString(user, "user");

        // verify the given dataObject
        this.verifyCreateDataObject(dataObject);

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            // get the connection
            conn = this.createConnection();

            // generate primary Id and add to dataObject
            this.setDataObjectPrimaryId(dataObject);

            // obtain sql statement for creating a record in the persistence store based on the info in the data object
            // and get the prepare statement
            preparedStatement = conn.prepareStatement(this.getCreateSqlString());

            // get the current date
            Date date = new Date();

            // fill prepared statement with parameters
            this.fillCreatePreparedStatement(preparedStatement, dataObject, user, date);

            // execute query
            preparedStatement.executeUpdate();

            // update dataObject with used creation params to maintain consistency
            this.setCreationParametersInDataObject(dataObject, user, date);
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("error happened in the database operation", e);
        } catch (DAOActionException e) {
            // do not wrap it
            throw e;
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during create process", e);
        } finally {
            // close the resources
            this.closeResources(null, preparedStatement, conn);
        }
    }

    /**
     * <p>
     * Updates a record in the persistence store based on the info in the data object.
     * </p>
     *
     * @param dataObject <code>DataObject</code> to update
     * @param user The user that initiates this update.
     *
     * @throws NullPointerException if <code>dataObject</code> or <code>user</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>user</code> is empty string; or any required member in data object is
     *         <code>null</code>.
     * @throws DAOActionException if error occurs when updating the data object in database.
     */
    public void update(DataObject dataObject, String user) throws DAOActionException {
        TimeEntryHelper.checkString(user, "user");

        // verify the given dataObject
        this.verifyUpdateDataObject(dataObject);

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            // get the connection
            conn = this.createConnection();

            // obtain sql statement for updating a record in the persistence store based on the info in the data object
            // and get the prepare statement
            preparedStatement = conn.prepareStatement(this.getUpdateSqlString());

            // get the current date
            Date date = new Date();

            // fill prepared statement with parameters
            this.fillUpdatePreparedStatement(preparedStatement, dataObject, user, date);

            // execute query
            preparedStatement.executeUpdate();

            // update dataObject with used modification params to maintain consistency
            this.setModificationParametersInDataObject(dataObject, user, date);
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("error happened in the database operation", e);
        } catch (DAOActionException e) {
            // do not wrap it
            throw e;
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during update process", e);
        } finally {
            // close the resources
            this.closeResources(null, preparedStatement, conn);
        }
    }

    /**
     * <p>
     * Deletes the record from the persistence store based on the primary Id. If there is no match, i.e., no such
     * record exists to delete, <code>false</code> is returned. If the deletion is successful, <code>true</code> is
     * returned.
     * </p>
     *
     * @param primaryId the ID of the data object to be deleted from the database.
     *
     * @return <code>true</code> if the ID exists in database and the data object is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws DAOActionException if error occurs when deleting the data object from database.
     */
    public boolean delete(int primaryId) throws DAOActionException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            // get the connection
            conn = this.createConnection();

            // obtain sql statement for deleting the record form the persistence store based on the primary id
            // and get the prepare statement and set the primaryId as the parameter
            preparedStatement = conn.prepareStatement(this.getDeleteSqlString());
            preparedStatement.setInt(1, primaryId);

            // execute query
            int ret = preparedStatement.executeUpdate();

            // return value indicates whether the record was deleted successfully
            if (ret == 0) {
                // no record exists to delete
                return false;
            }

            return true;
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("error happened in the database operation", e);
        } catch (DAOActionException e) {
            // do not wrap it
            throw e;
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during delete process", e);
        } finally {
            // close the resources
            this.closeResources(null, preparedStatement, conn);
        }
    }

    /**
     * <p>
     * Gets a record in the persistence store based on the passed id. Returns <code>null</code> if no matching record
     * is found.
     * </p>
     *
     * @param primaryId the ID of the data object to be retrieved from the database.
     *
     * @return the data object with the given ID retrieved from the database; or <code>null</code> if such data object
     *         does not exist.
     *
     * @throws DAOActionException if error occurs when retrieving the data object from database.
     */
    public DataObject get(int primaryId) throws DAOActionException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // get the connection
            conn = this.createConnection();

            // obtain sql statement and
            // get the prepare statement and set the primaryId as the parameter
            preparedStatement = conn.prepareStatement(this.getReadSqlString());
            preparedStatement.setInt(1, primaryId);

            // execute query
            resultSet = preparedStatement.executeQuery();

            // process the resultSet
            DataObject dataObject = this.processReadResultSet(resultSet);

            // return the dataObject with given primaryId
            return dataObject;
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("error happened in the database operation", e);
        } catch (DAOActionException e) {
            // do not wrap it
            throw e;
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during get process", e);
        } finally {
            // close the resources
            this.closeResources(resultSet, preparedStatement, conn);
        }
    }

    /**
     * <p>
     * Gets a list of dataObjects based on the where clause. If there are no mathong records, an empty List is
     * returned.
     * </p>
     *
     * @param whereClause The where clause that defines the constraints for the data retrieval.
     *
     * @return List of DataObjects that meet the search criteria
     *
     * @throws IllegalArgumentException If the <code>whereClause</code> begins with WHERE keyword.
     * @throws DAOActionException if error occurs when retrieving the data objects from database.
     */
    public List getList(String whereClause) throws DAOActionException {
        if ((whereClause != null) && (whereClause.trim().length() != 0)) {
            StringTokenizer stringTokenizer = new StringTokenizer(whereClause);

            if (stringTokenizer.nextToken().equalsIgnoreCase(SQL_WHERE_KEYWORD.trim())) {
                throw new IllegalArgumentException("the whereClause begins with WHERE keyword");
            }
        }

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // get the connection
            conn = this.createConnection();

            // obtain sql statement
            String sqlString = this.getReadListSqlString();

            if ((whereClause != null) && (whereClause.trim().length() != 0)) {
                // If whereClause is non-null and non-empty:
                sqlString += (SQL_WHERE_KEYWORD + whereClause);
            }

            // get the statement to execute the query
            statement = conn.createStatement();

            // execute query
            resultSet = statement.executeQuery(sqlString);

            // process the resultSet
            List dataObjects = this.processReadListResultSet(resultSet);

            // return the dataObjects with given whereClause
            return dataObjects;
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("error happened in the database operation", e);
        } catch (DAOActionException e) {
            // do not wrap it
            throw e;
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during getList process", e);
        } finally {
            // close the resources
            this.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Protected helper method used by the <code>create</code> method to set the next id for the passed data object.
     * Subclasses are free to override this method and use a different key generation schema.
     * </p>
     *
     * @param dataObject The data object whose primary Id will be set.
     *
     * @throws NullPointerException if <code>dataObject</code> is <code>null</code>.
     * @throws DAOActionException if error occurs when generating the ID.
     */
    protected void setDataObjectPrimaryId(DataObject dataObject) throws DAOActionException {
        TimeEntryHelper.checkNull(dataObject, "dataObject");

        try {
            // generate the primary id
            IDGenerator myGenerator = IDGeneratorFactory.getIDGenerator(dataObject.getClass().getName());
            int nextID = (int) myGenerator.getNextID();

            // set this generated id to the dataObject
            dataObject.setPrimaryId(nextID);
        } catch (IDGenerationException e) {
            throw new DAOActionException("fail to generate the primary id", e);
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during setDataObjectPrimaryId process", e);
        }
    }

    /**
     * <p>
     * Checks the data object for illegal values, and throws appropriate exceptions if it finds them.
     * </p>
     *
     * @param dataObject The data object which will be checked
     *
     * @throws NullPointerException if <code>dataObject</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>dataObject</code> does not match the requirement to be created in
     *         persistence.
     * @throws DAOActionException if error occurs when verifying the data object.
     */
    protected abstract void verifyCreateDataObject(DataObject dataObject) throws DAOActionException;

    /**
     * <p>
     * Returns the sql string that will be used in the prepared statement for creating a record.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for creating a record
     */
    protected abstract String getCreateSqlString();

    /**
     * <p>
     * Fills the create PreparedStatement with values.
     * </p>
     *
     * @param statement the PreparedStatement to fill with values
     * @param dataObject the data object which contains the values to fill into PreparedStatement
     * @param creationUser the creation user of this database operation
     * @param creationDate the creation date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected abstract void fillCreatePreparedStatement(PreparedStatement statement, DataObject dataObject,
        String creationUser, Date creationDate) throws DAOActionException;

    /**
     * <p>
     * For consistency, this helper method can be used to set the creation and modification fields to the ones used
     * when creating the record.
     * </p>
     *
     * @param dataObject the data object to set creation fields
     * @param creationUser the creation user of this database operation
     * @param creationDate the creation date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected abstract void setCreationParametersInDataObject(DataObject dataObject, String creationUser,
        Date creationDate) throws DAOActionException;

    /**
     * <p>
     * Checks the data object for illegal values, and throws appropriate exceptions if it finds them.
     * </p>
     *
     * @param dataObject the data object which will be checked
     *
     * @throws NullPointerException if <code>dataObject</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>dataObject</code> does not match the requirement to be updated in
     *         persistence.
     * @throws DAOActionException if error occurs when verifying the data object.
     */
    protected abstract void verifyUpdateDataObject(final DataObject dataObject)
        throws DAOActionException;

    /**
     * <p>
     * Returns the sql string that will be used in the prepared statement for updating a record.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for update a record
     */
    protected abstract String getUpdateSqlString();

    /**
     * <p>
     * Fills the update PreparedStatement with values.
     * </p>
     *
     * @param statement the prepared SQL statement used to create a data object in database.
     * @param dataObject the data object which contains the values to fill into PreparedStatement
     * @param modificationUser the modification user of this database operation
     * @param modificationDate the modification date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected abstract void fillUpdatePreparedStatement(PreparedStatement statement, DataObject dataObject,
        String modificationUser, Date modificationDate) throws DAOActionException;

    /**
     * <p>
     * For consistency, this helper method can be used to set the modification fields to the ones used when updating
     * the record.
     * </p>
     *
     * @param dataObject the DataObject to set modification fields
     * @param modificationUser the modification user of this database operation
     * @param modificationDate the modification date of this database operation
     *
     * @throws DAOActionException if error occurs when setting the fields.
     */
    protected abstract void setModificationParametersInDataObject(DataObject dataObject, String modificationUser,
        Date modificationDate) throws DAOActionException;

    /**
     * <p>
     * Returns the sql string that will be used in the prepared statement for deleting a record.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for delete a record
     */
    protected abstract String getDeleteSqlString();

    /**
     * <p>
     * Returns the sql string that will be used in the prepared statement for getting a record.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for get a record
     */
    protected abstract String getReadSqlString();

    /**
     * <p>
     * Processes the ResultSet and returns a instance of a <code>DataObject</code> type. The <code>DAO</code>
     * implmentation will specify if a <code>null</code> or empty <code>DataObject</code> type instance will be
     * returned if there were no records retrieved.
     * </p>
     *
     * @param resultSet the result set containing information to create data object.
     *
     * @return a data object created from the next record in the result set; or <code>null</code> if the result set
     *         reaches the end.
     *
     * @throws DAOActionException if error occurs when processing the result set.
     */
    protected abstract DataObject processReadResultSet(ResultSet resultSet)
        throws DAOActionException;

    /**
     * <p>
     * Returns the sql string that will be used in the statement for getting a List of records.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for get a record list
     */
    protected abstract String getReadListSqlString();

    /**
     * <p>
     * Processes the ResultSet and returns a instance of a <code>DataObject</code> type. An empty list will be returned
     * if no records were retrieved.
     * </p>
     *
     * @param resultSet the result set containing information to create data objects.
     *
     * @return a list of data objects created from the records in the result set.
     *
     * @throws DAOActionException if error occurs when processing the result set.
     */
    protected abstract List processReadListResultSet(ResultSet resultSet) throws DAOActionException;

    /**
     * <p>
     * Creates a connection for a CRUD operation. If the useOwnConnection flag is on, then a connection will be
     * obtained from the DBConnection Factory. Otherwise, the connection in the local connection member will be
     * returned.
     * </p>
     *
     * <p>
     * Changes in V1.1: change the type of the method as protected instead of private.
     * </p>
     *
     * @return the created connection from DBConnectionFactory
     *
     * @throws UnsupportedOperationException if this is a non-JDBC DAO implementation
     * @throws DAOActionException If any other exception is thrown, such as error in the DBConnection Factory.
     *
     * @since 1.0
     */
    protected Connection createConnection() throws DAOActionException {
        if (this.useOwnConnection) {
            // if it is a normal connection-obtaining schema
            try {
                // get Connection from the DBConnectionFactory
                if (factory == null) {
                    factory = new DBConnectionFactoryImpl(this.namespace);
                }

                return factory.createConnection(this.connName);
            } catch (ConfigurationException e) {
                // wrap the ConfigurationException with DAOActionException
                throw new DAOActionException("can not properly load the configuration for DBConnectionFactory", e);
            } catch (DBConnectionException e) {
                // wrap the DBConnectionException with DAOActionException
                throw new DAOActionException("can not properly get the connection from DBConnectionFactory", e);
            }
        }

        return this.connection;
    }

    /**
     * <p>
     * Closes JDBC resources. If the useOwnConnection flag is on, then the connection is closed. Otherwise, the
     * connection is not touched.
     * </p>
     *
     * <p>
     * Changes in V1.1: change the type of the method as protected instead of private.
     * </p>
     *
     * @param resultSet the result set to be closed.
     * @param statement the SQL statement to be closed.
     * @param connection the connection to be closed if it is created from the DB connection factory.
     *
     * @throws DAOActionException If there is an error during closing the JDBC objects.
     *
     * @since 1.0
     */
    protected void closeResources(ResultSet resultSet, Statement statement, Connection connection)
        throws DAOActionException {
        boolean success = true;

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException se) {
                success = false;
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException se) {
                success = false;
            }
        }

        if (this.useOwnConnection && (connection != null)) {
            try {
                connection.close();
            } catch (SQLException se) {
                success = false;
            }
        }

        if (!success) {
            throw new DAOActionException("fail to close the resources");
        }
    }

    /**
     * <p>
     * Sets the external connection that the DAO will use in all its CRUD operations until this connection is removed
     * via the removeConnection() method.
     * </p>
     *
     * @param connection Connection to be used by this DAO for all CRUD operations
     *
     * @throws NullPointerException if <code>connection</code> is null
     * @throws UnsupportedOperationException if this is a non-JDBC DAO implementation
     */
    public void setConnection(Connection connection) {
        TimeEntryHelper.checkNull(connection, "connection");

        this.connection = connection;
        this.useOwnConnection = false;
    }

    /**
     * <p>
     * Gets the database connection which overrides the internal connection obtained from the DB connection factory.
     * </p>
     *
     * @return the database connection which overrides the internal connection. <code>null</code> if it is not set.
     *
     * @throws UnsupportedOperationException if this is a non-JDBC DAO implementation
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * <p>
     * Removes the external connection that the DAO will use in all its CRUD operations. The DAO will resume connection
     * creation using its normal means.
     * </p>
     *
     * @throws UnsupportedOperationException if the DAO does not use JDBC.
     */
    public void removeConnection() {
        this.connection = null;
        this.useOwnConnection = true;
    }

    /**
     * <p>
     * Always throw an UnsupportedOperationException since it is not implemented here.
     * </p>
     *
     * @param dataObjects an array of <code>DataObject</code> instances to create in DB.
     * @param user creation user.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     * @throws UnsupportedOperationException always throw this since this abstract class does not support this.
     *
     * @since 1.1
     */
    public void batchCreate(DataObject[] dataObjects, String user, boolean allOrNone, ResultData resultData)
        throws BatchOperationException {
        throw new UnsupportedOperationException("Not suppored here.");
    }

    /**
     * <p>
     * Always throw an UnsupportedOperationException since it is not implemented here.
     * </p>
     *
     * @param idList list of ids of DataObject records to delete.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     * @throws UnsupportedOperationException always throw this since this abstract class does not support this.
     *
     * @since version 1.1
     */
    public void batchDelete(int[] idList, boolean allOrNone, ResultData resultData)
        throws BatchOperationException {
        throw new UnsupportedOperationException("Not suppored here.");
    }

    /**
     * <p>
     * Always throw an UnsupportedOperationException since it is not implemented here.
     * </p>
     *
     * @param dataObjects an array of <code>DataObject</code> instances to update in DB.
     * @param user modification user.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     * @throws UnsupportedOperationException always throw this since this abstract class does not support this.
     *
     * @since version 1.1
     */
    public void batchUpdate(DataObject[] dataObjects, String user, boolean allOrNone, ResultData resultData)
        throws BatchOperationException {
        throw new UnsupportedOperationException("Not suppored here.");
    }

    /**
     * <p>
     * Always throw an UnsupportedOperationException since it is not implemented here.
     * </p>
     *
     * @param idList list of ids of DataObject records to fetch.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     * @throws UnsupportedOperationException always throw this since this abstract class does not support this.
     *
     * @since version 1.1
     */
    public void batchRead(int[] idList, boolean allOrNone, ResultData resultData)
        throws BatchOperationException {
        throw new UnsupportedOperationException("Not suppored here.");
    }
}
