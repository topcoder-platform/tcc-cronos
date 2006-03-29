/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * This class extends <code>BaseDAO</code> and provides basic persistence functionality for the <code>TimeEntry</code>
 * data object. Implements all abstract helper methods. The creation and modification parameters for the create and
 * update operations are obtained  from the user parameter passed as part of these operations and current time.
 * </p>
 *
 * <p>
 * Version 1.1 changes: The current TimeEntry has an added(which makes it a composite Value Object) RejectReason
 * dependency which allows for linking rejectReasons to time entries. Four new methods for doing bulk operations on
 * sets of times entries have also been added. These method can work in atomic mode (a failure on one entry causes the
 * entire operation to fail) or non-atomic (a failure in one entry doesn't affect the other and the user has a way to
 * know which ones failed).
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @author argolite, TCSDEVELOPER
 * @version 1.1
 *
 * @since 1.0
 */
public class TimeEntryDAO extends BaseDAO {
    /**
     * <p>
     * Represents the create sql statement that is returned to the <code>create</code> method by the
     * <code>getCreateSqlString</code> helper method in this class.
     * </p>
     */
    public static final String SQL_CREATE_STATEMENT = "INSERT INTO TimeEntries(TimeEntriesID, TaskTypesID, "
        + "TimeStatusesID, Description, EntryDate, Hours, Billable, CreationUser, CreationDate, "
        + "ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * <p>
     * Represents the update sql statement that is returned to the <code>update</code> method by the
     * <code>getUpdateSqlString</code> helper method in this class.
     * </p>
     */
    public static final String SQL_UPDATE_STATEMENT = "UPDATE TimeEntries SET TaskTypesID=?, TimeStatusesID=?, "
        + "Description=?, EntryDate=?, Hours=?, Billable=?, ModificationUser=?, ModificationDate=? "
        + "WHERE TimeEntriesID=?";

    /**
     * <p>
     * Represents the delete sql statement that is returned to the <code>delete</code> method by the
     * <code>getDeleteSqlString</code> helper method in this class.
     * </p>
     */
    public static final String SQL_DELETE_STATEMENT = "DELETE FROM TimeEntries WHERE TimeEntriesID=?";

    /**
     * <p>
     * Represents the get sql statement that is returned to the <code>get</code> method by the <code>getReadSqlString
     * </code> helper method in this class.
     * </p>
     */
    public static final String SQL_GET_STATEMENT = "SELECT * FROM TimeEntries WHERE TimeEntriesID=?";

    /**
     * <p>
     * Represents the get list sql statement that is returned to the <code>getList</code> method by the
     * <code>getReadListSqlString</code> helper method in this class.
     * </p>
     */
    public static final String SQL_GET_LIST_STATEMENT = "SELECT * FROM TimeEntries ";

    /**
     * Represents the prepared SQL statement to add into the time_reject_reason table.
     *
     * @since 1.1
     */
    private static final String ADD_TIME_REJECT_REASON_SQL = "INSERT INTO time_reject_reason (TimeEntriesID,"
        + "reject_reason_id, creation_date, creation_user, modification_date, modification_user) "
        + "VALUES (?,?,?,?,?,?)";

    /**
     * Represents the prepared SQL statement to delete the time_reject_reason table.
     *
     * @since 1.1
     */
    private static final String DELETE_TIME_REJECT_REASON_SQL = "DELETE FROM time_reject_reason "
        + "WHERE TimeEntriesID=?";

    /**
     * Represents the prepared SQL statement to get the reject reasons with given TimeEntriesID.
     *
     * @since 1.1
     */
    private static final String RETRIEVE_REJECT_REASON_SQL = "SELECT R.reject_reason_id, "
        + "R.creation_date, R.creation_user, R.modification_date, R.modification_user FROM time_reject_reason AS E "
        + "LEFT OUTER JOIN reject_reason AS R ON E.reject_reason_id = R.reject_reason_id " + "WHERE E.TimeEntriesID=?";

    /**
     * Represents the column name for the reject reason ID of the expense entry.
     *
     * @since 1.1
     */
    private static final String REASON_ID_COLUMN = "reject_reason_id";

    /**
     * Represents the column name for reject reason creation date.
     *
     * @since 1.1
     */
    private static final String REASON_CREATION_DATE_COLUMN = "creation_date";

    /**
     * Represents the column name for reject reason creation user.
     *
     * @since 1.1
     */
    private static final String REASON_CREATION_USER_COLUMN = "creation_user";

    /**
     * Represents the column name for reject reason modification date.
     *
     * @since 1.1
     */
    private static final String REASON_MODIFICATION_DATE_COLUMN = "modification_date";

    /**
     * Represents the column name for reject reason modification user.
     *
     * @since 1.1
     */
    private static final String REASON_MODIFICATION_USER_COLUMN = "modification_user";

    /**
     * Represents the batch read operation.
     */
    private static final int BATCH_READ = -1;

    /**
     * Represents the batch create operation.
     */
    private static final int BATCH_CREATE = 0;

    /**
     * Represents the batch delete operation.
     */
    private static final int BATCH_DELETE = 1;

    /**
     * Represents the batch update operation.
     */
    private static final int BATCH_UPDATE = 2;

    /**
     * <p>
     * Creates a new instance of <code>TimeEntryDAO</code> class. Sets the connection name and namespace that the
     * component will use to obtain Connections from the DBConnection Factory.
     * </p>
     *
     * @param connName the connection name used to create database connection from DB connection factory.
     * @param namespace the namespace used to create DB connection factory.
     *
     * @throws NullPointerException if <code>connName</code> or <code>namespace</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>connName</code> or <code>namespace</code> is empty string.
     */
    public TimeEntryDAO(String connName, String namespace) {
        super(connName, namespace);
    }

    /**
     * <p>
     * Checks the data object for illegal values, and throws appropriate exceptions if it finds them. The decription,
     * date and hours field of the dataObject will be checked.
     * </p>
     *
     * @param dataObject The DataObject which will be checked
     *
     * @throws NullPointerException if <code>dataObject</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>dataObject</code> is not an instance of TimeEntry, or if description
     *         or date member in <code>dataObject</code> is <code>null</code>, or hours below 0.0F.
     * @throws DAOActionException if any other exception happens.
     */
    protected void verifyCreateDataObject(DataObject dataObject) throws DAOActionException {
        this.validateDataObject(dataObject);
    }

    /**
     * <p>
     * Returns the sql string that will be used in the prepared statement for creating a time entry.
     * SQL_CREATE_STATEMENT will be returned.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for creating a record
     */
    protected String getCreateSqlString() {
        return SQL_CREATE_STATEMENT;
    }

    /**
     * <p>
     * Fills the create PreparedStatement with values to be used in the execution based on the order specified in the
     * SQL_CREATE_STATEMENT. All values, save for the modification and creation user and date, will come from the
     * dataObject. The passed creationUser parameter will be used to set the CreationUser and ModificationUser
     * parameters in the statement, and the passed creationDate parameter will be used to set the CreationDate and
     * ModificationDate parameters in the statement. Truncates all String fileds to length of 64 if they are longer.
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
        try {
            TimeEntry timeEntry = (TimeEntry) dataObject;

            // Truncates the creationUser field to length of 64 if it is longer
            String user = TimeEntryHelper.truncatesString(creationUser);

            // change the java.util.Date to java.sql.Date
            java.sql.Date date = new java.sql.Date(creationDate.getTime());

            // fill the create preparedStatement
            int i = 0;
            statement.setInt(++i, timeEntry.getPrimaryId());
            statement.setInt(++i, timeEntry.getTaskTypeId());
            statement.setInt(++i, timeEntry.getTimeStatusId());
            statement.setString(++i, TimeEntryHelper.truncatesString(timeEntry.getDescription()));
            statement.setDate(++i, new java.sql.Date(timeEntry.getDate().getTime()));
            statement.setFloat(++i, timeEntry.getHours());
            statement.setBoolean(++i, timeEntry.isBillable());
            statement.setString(++i, user);
            statement.setDate(++i, date);
            statement.setString(++i, user);
            statement.setDate(++i, date);
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("can not fill the create preparedStatement", e);
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during fillCreatePreparedStatement process", e);
        }
    }

    /**
     * <p>
     * For consistency, this helper method is used to set the creation and modification fields to the ones used when
     * creating the record.
     * </p>
     *
     * <p>
     * The passed creationUser parameter will be used to set the creationUser and modificationUser  members of the
     * dataObject, and the passed creationDate parameter will be used to set the creationDate and  modificationDate
     * members.
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
        try {
            TimeEntry timeEntry = (TimeEntry) dataObject;

            // set the creationUser and modificationUser members of the dataObject
            timeEntry.setCreationUser(creationUser);
            timeEntry.setModificationUser(creationUser);

            // set the creationDate and  modificationDate members of the dataObject
            timeEntry.setCreationDate(creationDate);
            timeEntry.setModificationDate(creationDate);
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during setCreationParametersInDataObject process", e);
        }
    }

    /**
     * <p>
     * Checks the data object for illegal values, and throws appropriate exceptions if it finds them. The decription,
     * date and hours field of the dataObject will be checked.
     * </p>
     *
     * @param dataObject The DataObject which will be checked
     *
     * @throws NullPointerException if <code>dataObject</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>dataObject</code> is not an instance of TimeEntry, or if description
     *         or date member in <code>dataObject</code> is <code>null</code>, or hours below 0.0F.
     * @throws DAOActionException if any other exception happens.
     */
    protected void verifyUpdateDataObject(final DataObject dataObject) throws DAOActionException {
        this.validateDataObject(dataObject);
        if (((TimeEntry) dataObject).getCreationDate() == null) {
            throw new IllegalArgumentException("The create date should not be null when updating.");
        }
    }

    /**
     * <p>
     * Returns the sql string that will be used in the prepared statement for updating a time entry.
     * SQL_UPDATE_STATEMENT will be returned.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for update a record
     */
    protected String getUpdateSqlString() {
        return SQL_UPDATE_STATEMENT;
    }

    /**
     * <p>
     * Fills the update PreparedStatement with values to be used in the execution based on the order specified in the
     * SQL_UPDATE_STATEMENT. Creation user and date are not updated. Modification User and Date is set by the passed
     * modificationUser and modificationDate parameters, respectively. The rest of the parameters in the statement are
     * set  from the dataObject. Truncates all String fileds to length of 64 if they are longer.
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
        String modificationUser, Date modificationDate) throws DAOActionException {
        try {
            TimeEntry timeEntry = (TimeEntry) dataObject;

            // Truncates the modificationUser filed to length of 64 if it is longer
            String user = TimeEntryHelper.truncatesString(modificationUser);

            // convert the java.util.Date to java.sql.Date
            java.sql.Date date = new java.sql.Date(modificationDate.getTime());

            // fill the update PreparedStatement
            int i = 0;
            statement.setInt(++i, timeEntry.getTaskTypeId());
            statement.setInt(++i, timeEntry.getTimeStatusId());
            statement.setString(++i, TimeEntryHelper.truncatesString(timeEntry.getDescription()));
            statement.setDate(++i, new java.sql.Date(timeEntry.getDate().getTime()));
            statement.setFloat(++i, timeEntry.getHours());
            statement.setBoolean(++i, timeEntry.isBillable());
            statement.setString(++i, user);
            statement.setDate(++i, date);
            statement.setInt(++i, dataObject.getPrimaryId());
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("can not fill the update preparedStatement", e);
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during fillUpdatePreparedStatement process", e);
        }
    }

    /**
     * <p>
     * For consistency, this helper method is used to set the modification fields to the ones used when updating the
     * record.
     * </p>
     *
     * <p>
     * The passed modificationUser parameter will be used to set the modificationUser member of the dataObject,  and
     * the passed modificationDate parameter will be used to set the modificationDate member.
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
        try {
            // The passed modificationUser parameter will be used to set the modificationUser member of the dataObject
            ((TimeEntry) dataObject).setModificationUser(modificationUser);

            // The passed modificationDate parameter will be used to set the modificationDate member of the dataObject
            ((TimeEntry) dataObject).setModificationDate(modificationDate);
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during setModificationParametersInDataObject process", e);
        }
    }

    /**
     * <p>
     * Returns the sql string that will be used in the prepared statement for deleting a time entry.
     * SQL_DELETE_STATEMENT will be returned.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for delete a record
     */
    protected String getDeleteSqlString() {
        return SQL_DELETE_STATEMENT;
    }

    /**
     * <p>
     * Returns the sql string that will be used in the prepared statement for retrieving a time entry record.
     * SQL_READ_STATEMENT will be returned.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for get a record
     */
    protected String getReadSqlString() {
        return SQL_GET_STATEMENT;
    }

    /**
     * <p>
     * Retrieves all column fields from the ResultSet, and returns a <code>TimeEntry</code> instance containing them.
     * Returns <code>null</code> if no records were retrieved. If for some reason more than one record is retrieved,
     * only the first is used.
     * </p>
     *
     * @param resultSet the result set containing information to create data object.
     *
     * @return a data object created from the next record in the result set; or <code>null</code> if the result set
     *         reaches the end.
     *
     * @throws DAOActionException if error occurs when processing the result set.
     */
    protected DataObject processReadResultSet(ResultSet resultSet) throws DAOActionException {
        try {
            // if the resultSet is null
            if (resultSet == null) {
                throw new DAOActionException("resultSet is null when retriving all the column fields");
            }

            if (resultSet.next()) {
                // retrieve the DataObject
                return this.getDataObject(resultSet);
            }

            // if no entry exists in the resultSet, return null
            return null;
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("SQLException happened during the process of reading resultSet", e);
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during processReadResultSet process", e);
        }
    }

    /**
     * <p>
     * Returns the sql string that will be used in the statement for retrieving selected time entry records.
     * SQL_READ_LIST_STATEMENT will be returned.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for get a record list
     */
    protected String getReadListSqlString() {
        return SQL_GET_LIST_STATEMENT;
    }

    /**
     * <p>
     * Retrieves all column fields from the ResultSet for each record, and returns a List of <code>TimeEntry</code>
     * instances containing them. Returns empty list if no records were retrieved.
     * </p>
     *
     * @param resultSet the result set containing information to create data objects.
     *
     * @return a list of data objects created from the records in the result set.
     *
     * @throws DAOActionException if error occurs when processing the result set.
     */
    protected List processReadListResultSet(ResultSet resultSet) throws DAOActionException {
        List list = new ArrayList();

        try {
            if (resultSet == null) {
                // if the resultSet is null
                throw new DAOActionException("resultSet is null when retriving all the column fields");
            }

            while (resultSet.next()) {
                // retrieve the DataObject
                DataObject timeEntry = this.getDataObject(resultSet);
                list.add(timeEntry);
            }

            return list;
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("SQLException happened during the process of reading resultSet", e);
        } catch (Exception e) {
            // wrap any exception with DAOActionException
            throw new DAOActionException("Exception occurs during processReadListResultSet process", e);
        }
    }

    /**
     * <p>
     * Adds a set of entries to the database.
     * </p>
     *
     * <p>
     * If the addition is atomic then it means that entire retrieval will fail if a single time entry addition fails.
     * </p>
     *
     * <p>
     * If the addition is non-atomic then it means each time entry is added individually. If it fails, that won't
     * affect the others, we create an entry in an exceptionList array and we also need to keep track of the index of
     * the operation that we are on. Exception is related to acquiring an SQL connection or something like that, it is
     * obvious that all entries will fail so the exception should be thrown.
     * </p>
     *
     * <p>
     * Note: an JDBC transaction must be used in atomic mode to be able to rollback everything in case of failure.
     * </p>
     *
     * @param dataObjects an array of <code>DataObject</code> instances to create in DB.
     * @param user creation user.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty or has <code>null</code> element or
     *         has invalid type; or any argument is <code>null</code>.
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     *
     * @since 1.1
     */
    public void batchCreate(DataObject[] dataObjects, String user, boolean allOrNone, ResultData resultData)
        throws BatchOperationException {
        // argument validation
        if (dataObjects == null) {
            throw new IllegalArgumentException("dataObjects should not be null.");
        }

        if (dataObjects.length == 0) {
            throw new IllegalArgumentException("dataObjects should not be empty.");
        }

        for (int i = 0; i < dataObjects.length; i++) {
            if (dataObjects[i] == null) {
                throw new IllegalArgumentException("dataObjects should not contain a null element.");
            }

            if (!(dataObjects[i] instanceof TimeEntry)) {
                throw new IllegalArgumentException("dataObjects should not contain a non-TimeEntry type element.");
            }
        }

        if (user == null) {
            throw new IllegalArgumentException("user should not be null.");
        }

        if (user.trim().length() == 0) {
            throw new IllegalArgumentException("user should not be empty.");
        }

        if (resultData == null) {
            throw new IllegalArgumentException("resultData should not be null.");
        }

        this.doTransaction(dataObjects, user, allOrNone, resultData, BATCH_CREATE);
    }

    /**
     * Does the transaction according to the type of action.
     *
     * @param dataObjects an array of <code>DataObject</code> instances to create in DB.
     * @param user creation user.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     * @param action the type of the transaction. -1 means read; 0 means add; 1 means delete; 2 means update.
     *
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     *
     * @since 1.1
     */
    private void doTransaction(Object[] dataObjects, String user, boolean allOrNone, ResultData resultData, int action)
        throws BatchOperationException {
        // get the current connection
        Connection oldConnection = super.getConnection();

        // If the exception is related to acquiring an SQL connection or something like that, it is obvious that
        // all entries will fail so the exception should be thrown.
        Connection newConnection = null;

        try {
            newConnection = super.createConnection();
        } catch (DAOActionException e) {
            throw new BatchOperationException("Fails to create the connection.");
        }

        boolean preAutoCommit = true;

        if (action != BATCH_READ) {
            // only set the auto commit when not in read module
            preAutoCommit = this.beginTransaction(newConnection, allOrNone);
        }

        String actionString = null;

        if (action == BATCH_READ) {
            actionString = "reading";
        } else if (action == BATCH_CREATE) {
            actionString = "creating";
        } else if (action == BATCH_DELETE) {
            actionString = "deleting";
        } else if (action == BATCH_UPDATE) {
            actionString = "updating";
        }

        try {
            super.setConnection(newConnection);

            BatchOperationException batchOperationException = null;
            boolean success = true;

            BatchOperationException[] batchOperationExceptions = new BatchOperationException[dataObjects.length];
            DataObject[] batchResults = new DataObject[dataObjects.length];

            boolean totolSuccess = true;
            for (int i = 0; i < dataObjects.length; i++) {
                success = true;

                try {
                    if (action == BATCH_READ) {
                        batchResults[i] = this.get(((Integer) dataObjects[i]).intValue());

                        if (batchResults[i] == null) {
                            throw new DAOActionException("No dataObject for the id "
                                + ((Integer) dataObjects[i]).intValue() + " exists.");
                        }
                    } else if (action == BATCH_CREATE) {
                        this.create((TimeEntry) dataObjects[i], user);
                    } else if (action == BATCH_DELETE) {
                        if (!this.delete(((Integer) dataObjects[i]).intValue())) {
                            throw new DAOActionException("No dataObject for the id "
                                    + ((Integer) dataObjects[i]).intValue() + " exists.");
                        }
                    } else if (action == BATCH_UPDATE) {
                        this.update((TimeEntry) dataObjects[i], user);
                    }
                } catch (DAOActionException e) {
                    success = false;
                    batchOperationException = new BatchOperationException("Error " + actionString + " process.", e);
                    batchOperationExceptions[i] = batchOperationException;
                } catch (IllegalArgumentException e) {
                    success = false;
                    batchOperationException = new BatchOperationException("Error " + actionString + " process.", e);
                    batchOperationExceptions[i] = batchOperationException;
                }

                totolSuccess &= success;

                if (!success) {
                    if (allOrNone) {
                        // in atomic mode, cancel the entire retrieval
                        break;
                    }
                }
            }

            if (action == BATCH_READ) {
                // only set the result data when in read module
                resultData.setBatchResults(batchResults);
            }

            // set the result data
            if (!totolSuccess) {
                resultData.setExceptionList(batchOperationExceptions);
            }
            resultData.setOperations(dataObjects);


            if (action == BATCH_READ) {
                if (allOrNone && !success) {
                    throw batchOperationException;
                }
            } else {
                // only to commit the transaction when not in read module
                this.endTransaction(newConnection, allOrNone, success, batchOperationException);
            }
        } finally {
            try {
                if (allOrNone) {
                    // load the previous status of autoCommit
                    if (action != BATCH_READ) {
                        // only set the auto commit when not in read module
                        newConnection.setAutoCommit(preAutoCommit);
                    }
                }

                super.removeConnection();

                if (oldConnection != null) {
                    // this means that there was a user connection and we restore the user's connection
                    // and in this condition, the newConnection == oldConnection. So we can not close connection here
                    super.setConnection(oldConnection);
                } else {
                    // close the new connection
                    if (newConnection != null) {
                        newConnection.close();
                    }
                }
            } catch (SQLException e) {
                throw new BatchOperationException("Fail to access the autoCommit or close the connection.", e);
            }
        }
    }

    /**
     * Begins the transaction. If it is in atomic module, the connection's autoCommit value will be set to false.
     *
     * @param connection the connection to the database.
     * @param isAtomic whether it is in atomic module.
     *
     * @return the previous value of connection's autoCommit.
     *
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     *
     * @since 1.1
     */
    private boolean beginTransaction(Connection connection, boolean isAtomic)
        throws BatchOperationException {
        boolean preAutoCommit = true;

        try {
            if (connection.isClosed()) {
                throw new BatchOperationException("The connection has already been closed.");
            }

            if (isAtomic) {
                // save the previous status of autoCommit
                preAutoCommit = connection.getAutoCommit();

                // an JDBC transaction must be used in atomic mode to be able to rollback everything in case of failure.
                connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new BatchOperationException("Fail to access the value of autoCommit.", e);
        }

        return preAutoCommit;
    }

    /**
     * Ends the transaction. If it is in atomic module, should manually commit the transaction.
     *
     * @param connection the connection to the database.
     * @param isAtomic whether it is in atomic module.
     * @param shouldCommit whether it should commit the transaction.
     * @param batchOperationException if it is not <code>null</code> and in atomic module, this exception should be
     *        thrown directly.
     *
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     *
     * @since 1.1
     */
    private void endTransaction(Connection connection, boolean isAtomic, boolean shouldCommit,
        BatchOperationException batchOperationException) throws BatchOperationException {
        if (isAtomic) {
            try {
                if (shouldCommit) {
                    connection.commit();
                } else {
                    rollback(connection);
                }
            } catch (SQLException e) {
                rollback(connection);
            }

            if (batchOperationException != null) {
                throw batchOperationException;
            }
        }
    }

    /**
     * Rollback the transaction of the database.
     *
     * @param connection the connection of the database.
     *
     * @throws BatchOperationException it fails to rollback the transaction.
     *
     * @since 1.1
     */
    private void rollback(Connection connection) throws BatchOperationException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new BatchOperationException("Can not rollback the transaction.", e);
        }
    }

    /**
     * <p>
     * Deletes a set of entries from the database with the given IDs from the persistence.
     * </p>
     *
     * <p>
     * If the deletion is atomic then it means that entire retrieval will fail if a single time entry deletion fails.
     * </p>
     *
     * <p>
     * If the deletion is non-atomic then it means each time entry is deleted individually. If it fails, that won't
     * affect the others, we create an entry in an exceptionList array and we also need to keep track of the index of
     * the operation that we are on. Exception is related to acquiring an SQL connection or something like that, it is
     * obvious that all entries will fail so the exception should be thrown.
     * </p>
     *
     * <p>
     * Note: an JDBC transaction must be used in atomic mode to be able to rollback everything in case of failure.
     * </p>
     *
     * @param idList list of ids of DataObject records to delete.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty; or any argument is <code>null</code>.
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     *
     * @since 1.1
     */
    public void batchDelete(int[] idList, boolean allOrNone, ResultData resultData)
        throws BatchOperationException {
        // argument validation
        if (idList == null) {
            throw new IllegalArgumentException("idList should not be null.");
        }

        if (idList.length == 0) {
            throw new IllegalArgumentException("idList should not be empty.");
        }

        if (resultData == null) {
            throw new IllegalArgumentException("resultData should not be null.");
        }

        // change them to object collection to avoid code redundance
        Integer[] ids = new Integer[idList.length];

        for (int i = 0; i < idList.length; i++) {
            ids[i] = new Integer(idList[i]);
        }

        // do the transaction
        this.doTransaction(ids, null, allOrNone, resultData, BATCH_DELETE);
    }

    /**
     * <p>
     * Updates a set of entries to the database.
     * </p>
     *
     * <p>
     * If the update is atomic then it means that entire retrieval will fail if a single time entry update fails.
     * </p>
     *
     * <p>
     * If the update is non-atomic then it means each time entry is updated individually. If it fails, that won't
     * affect the others, we create an entry in an exceptionList array and we also need to keep track of the index of
     * the operation that we are on. Exception is related to acquiring an SQL connection or something like that, it is
     * obvious that all entries will fail so the exception should be thrown.
     * </p>
     *
     * <p>
     * Note: an JDBC transaction must be used in atomic mode to be able to rollback everything in case of failure.
     * </p>
     *
     * @param dataObjects an array of <code>DataObject</code> instances to update in DB.
     * @param user modification user.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty or has <code>null</code> element or
     *         has invalid type; or any argument is <code>null</code>.
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     *
     * @since 1.1
     */
    public void batchUpdate(DataObject[] dataObjects, String user, boolean allOrNone, ResultData resultData)
        throws BatchOperationException {
        // argument validation
        if (dataObjects == null) {
            throw new IllegalArgumentException("dataObjects should not be null.");
        }

        if (dataObjects.length == 0) {
            throw new IllegalArgumentException("dataObjects should not be empty.");
        }

        for (int i = 0; i < dataObjects.length; i++) {
            if (dataObjects[i] == null) {
                throw new IllegalArgumentException("dataObjects should not contain a null element.");
            }

            if (!(dataObjects[i] instanceof TimeEntry)) {
                throw new IllegalArgumentException("dataObjects should not contain a non-TimeEntry type element.");
            }
        }

        if (user == null) {
            throw new IllegalArgumentException("user should not be null.");
        }

        if (user.trim().length() == 0) {
            throw new IllegalArgumentException("user should not be empty.");
        }

        if (resultData == null) {
            throw new IllegalArgumentException("resultData should not be null.");
        }

        this.doTransaction(dataObjects, user, allOrNone, resultData, BATCH_UPDATE);
    }

    /**
     * <p>
     * Retrieves a set of entries with given ids from the database.
     * </p>
     *
     * <p>
     * If the retrieval is atomic then it means that entire retrieval will fail if a single time entry retrieval fails.
     * </p>
     *
     * <p>
     * If the retrieval is non-atomic then it means each expense entry is retrieved individually. If it fails, that
     * won't affect the others, we create an entry in an exceptionList array and we also need to keep track of the
     * index of the operation that we are on. Exception is related to acquiring an SQL connection or something like
     * that, it is obvious that all entries will fail so the exception should be thrown.
     * </p>
     *
     * <p>
     * Note: an JDBC transaction must be used in atomic mode to be able to rollback everything in case of failure.
     * </p>
     *
     * @param idList list of ids of DataObject records to fetch.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty; or any argument is <code>null</code>.
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     *
     * @since 1.1
     */
    public void batchRead(int[] idList, boolean allOrNone, ResultData resultData)
        throws BatchOperationException {
        // argument validation
        if (idList == null) {
            throw new IllegalArgumentException("idList should not be null.");
        }

        if (idList.length == 0) {
            throw new IllegalArgumentException("idList should not be empty.");
        }

        if (resultData == null) {
            throw new IllegalArgumentException("resultData should not be null.");
        }

        // change them to object collection to avoid code redundance
        Integer[] ids = new Integer[idList.length];

        for (int i = 0; i < idList.length; i++) {
            ids[i] = new Integer(idList[i]);
        }

        // do the transaction
        this.doTransaction(ids, null, allOrNone, resultData, BATCH_READ);
    }

    /**
     * <p>
     * Adds a new <code>TimeEntry</code> instance to the database.
     * </p>
     *
     * <p>
     * Changes in 1.1: We need to override this since the time entry may contain now several reject reasons. For each
     * contained reject reason, a row needs to be inserted in the time_reject_reason table.
     * </p>
     *
     * @param dataObject <code>DataObject</code> to create
     * @param user The user that initiates this creation.
     *
     * @throws NullPointerException if <code>dataObject</code> or <code>user</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>user</code> is empty string; or if <code>dataObject</code> is not
     *         an instance of TimeEntry, or if description or date member in <code>dataObject</code> is
     *         <code>null</code>, or hours below 0.0F.
     * @throws DAOActionException if error occurs when creating the data object in database.
     *
     * @since 1.0
     */
    public void create(DataObject dataObject, String user) throws DAOActionException {
        super.create(dataObject, user);

        // add into the time_reject_reason table
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // get the connection
            conn = super.createConnection();

            statement = conn.prepareStatement(ADD_TIME_REJECT_REASON_SQL);

            RejectReason[] rejectReasons = ((TimeEntry) dataObject).getAllRejectReasons();

            // get the current date and change the java.util.Date to java.sql.Date
            java.sql.Date now = new java.sql.Date(((TimeEntry) dataObject).getCreationDate().getTime());

            for (int i = 0; i < rejectReasons.length; ++i) {
                int index = 0;
                statement.setInt(++index, dataObject.getPrimaryId());
                statement.setInt(++index, rejectReasons[i].getPrimaryId());
                statement.setDate(++index, now);
                statement.setString(++index, user);
                statement.setDate(++index, now);
                statement.setString(++index, user);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("error happened in the database operation", e);
        } finally {
            // close the resources
            this.closeResources(null, statement, conn);
        }
    }

    /**
     * <p>
     * Updates an <code>TimeEntry</code> instance to the database.
     * </p>
     *
     * <p>
     * Changes in 1.1: The time entry may contain now several reject reasons. The time_reject_reason stores in
     * persistence the reject reason ids for an time entries. The updated entry may contain a totally different set of
     * reject reasons. In order to update the table we have to retrieve all reject records for the time entry. The
     * common reject entries (all fields, including users and dates) from the retrieved reject entries and from the
     * update object must be eliminated (they don't need updating). All reject entries present in the update object
     * but not in the table, must be added. All reject entries present in the table but not in the update object, must
     * be deleted. The reject entries present in both but with different users/dates, must be updated.
     * </p>
     *
     * @param dataObject <code>DataObject</code> to update
     * @param user The user that initiates this update.
     *
     * @throws NullPointerException if <code>dataObject</code> or <code>user</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>user</code> is empty string; or if <code>dataObject</code> is not
     *         an instance of TimeEntry, or if description or date member in <code>dataObject</code> is
     *         <code>null</code>, or hours below 0.0F.
     * @throws DAOActionException if error occurs when updating the data object in database.
     *
     * @since 1.0
     */
    public void update(DataObject dataObject, String user) throws DAOActionException {
        super.update(dataObject, user);

        // update the time_reject_reason table
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // get the connection
            conn = super.createConnection();

            // first delete all the records by given TimeEntriesID
            statement = conn.prepareStatement(DELETE_TIME_REJECT_REASON_SQL);
            statement.setInt(1, ((TimeEntry) dataObject).getPrimaryId());
            statement.executeUpdate();

            // then add all the records into the time_reject_reason table.
            statement = conn.prepareStatement(ADD_TIME_REJECT_REASON_SQL);

            RejectReason[] rejectReasons = ((TimeEntry) dataObject).getAllRejectReasons();

            // get the current date and change the java.util.Date to java.sql.Date
            java.sql.Date now = new java.sql.Date(((TimeEntry) dataObject).getModificationDate().getTime());

            for (int i = 0; i < rejectReasons.length; ++i) {
                int index = 0;
                statement.setInt(++index, ((TimeEntry) dataObject).getPrimaryId());
                statement.setInt(++index, rejectReasons[i].getPrimaryId());
                statement.setDate(++index, new java.sql.Date(((TimeEntry) dataObject).getCreationDate().getTime()));
                statement.setString(++index, ((TimeEntry) dataObject).getCreationUser());
                statement.setDate(++index, now);
                statement.setString(++index, user);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("error happened in the database operation", e);
        } finally {
            // close the resources
            this.closeResources(null, statement, conn);
        }
    }

    /**
     * <p>
     * Deletes an <code>TimeEntry</code> instance with the given ID from the database.
     * </p>
     *
     * <p>
     * Changes in 1.1: The time entry may contain now several reject reasons. When deleting an entry, the linked rows
     * from time_reject_reason must be deleted too.
     * </p>
     *
     * @param primaryId the ID of the data object to be deleted from the database.
     *
     * @return <code>true</code> if the ID exists in database and the data object is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws DAOActionException if error occurs when deleting the data object from database.
     *
     * @since 1.0
     */
    public boolean delete(int primaryId) throws DAOActionException {
        // delete from the time_reject_reason table
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // get the connection
            conn = super.createConnection();

            statement = conn.prepareStatement(DELETE_TIME_REJECT_REASON_SQL);
            statement.setInt(1, primaryId);
            statement.executeUpdate();
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("error happened in the database operation", e);
        } finally {
            // close the resources
            this.closeResources(null, statement, conn);
        }

        return super.delete(primaryId);
    }

    /**
     * <p>
     * Retrieves all <code>TimeEntry</code> instances from the database. The related <code>TaskType</code> and
     * <code>TimeStatus</code> instances are also retrieved.
     * </p>
     *
     * <p>
     * Changes in 1.1: The time entry may contain now several reject reasons. The retrieval query must now be joined
     * with time_reject_reason on the TimeEntriesID field (a separate query is acceptable too and in this case perhaps
     * it is recommended since retrieving all the information in one query is probably too much, especially because of
     * the one-to-many join between time entries and reject reasons). The reject reason fields will populate instances
     * of the RejectReason class.
     * </p>
     *
     * @param whereClause The where clause that defines the constraints for the data retrieval.
     *
     * @return List of DataObjects that meet the search criteria
     *
     * @throws IllegalArgumentException If the <code>whereClause</code> begins with WHERE keyword.
     * @throws DAOActionException if error occurs when retrieving the data objects from database.
     *
     * @since 1.0
     */
    public List getList(String whereClause) throws DAOActionException {
        List dataObjects = super.getList(whereClause);

        // retrieve the reject reasons
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // get the connection
            conn = super.createConnection();

            statement = conn.prepareStatement(RETRIEVE_REJECT_REASON_SQL);

            for (Iterator iter = dataObjects.iterator(); iter.hasNext();) {
                TimeEntry entry = (TimeEntry) iter.next();
                retrieveRejectReasons(statement, entry);
            }
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("error happened in the database operation", e);
        } finally {
            // close the resources
            this.closeResources(null, statement, conn);
        }

        return dataObjects;
    }

    /**
     * Retrieve the reject reasons with given time entry. All the retrieved values will be added into the time entry
     * instance.
     *
     * @param statement the statement to the database queries.
     * @param entry the expense entry instance to retrieve the reject reasons.
     *
     * @throws DAOActionException any exception during the database operations.
     *
     * @since 1.1
     */
    private void retrieveRejectReasons(PreparedStatement statement, TimeEntry entry)
        throws DAOActionException {
        ResultSet resultSet = null;

        try {
            statement.setInt(1, entry.getPrimaryId());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                RejectReason reason = new RejectReason();
                reason.setPrimaryId(resultSet.getInt(REASON_ID_COLUMN));
                reason.setCreationDate(resultSet.getDate(REASON_CREATION_DATE_COLUMN));
                reason.setCreationUser(resultSet.getString(REASON_CREATION_USER_COLUMN));
                reason.setModificationDate(resultSet.getDate(REASON_MODIFICATION_DATE_COLUMN));
                reason.setModificationUser(resultSet.getString(REASON_MODIFICATION_USER_COLUMN));
                entry.addRejectReason(reason);
            }
        } catch (SQLException e) {
            throw new DAOActionException("Accessing database fails.", e);
        } finally {
            this.closeResources(resultSet, null, null);
        }
    }

    /**
     * <p>
     * Retrieves an <code>TimeEntry</code> instance with the given ID from the database. The related
     * <code>TaskType</code> and <code>TimeStatus</code> instances are also retrieved.
     * </p>
     *
     * <p>
     * Changes in 1.1: The time entry may contain now several reject reasons. The retrieval query must now be joined
     * with time_reject_reason on the TimeEntriesID field (a separate query is acceptable too). The reject reason
     * fields will populate instances of the RejectReason class.
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
        DataObject timeEntry = super.get(primaryId);

        if (timeEntry == null) {
            return null;
        }

        // retrieve the reject reasons
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // get the connection
            conn = super.createConnection();

            statement = conn.prepareStatement(RETRIEVE_REJECT_REASON_SQL);

            retrieveRejectReasons(statement, (TimeEntry) timeEntry);
        } catch (SQLException e) {
            // wrap the SQLException with DAOActionException
            throw new DAOActionException("error happened in the database operation", e);
        } finally {
            // close the resources
            this.closeResources(null, statement, conn);
        }

        return timeEntry;
    }

    /**
     * <p>
     * retrieve the DataObject record from the given resultSet.
     * </p>
     *
     * @param resultSet the ResultSet to retrieve the DataObject record
     *
     * @return the retrieved the DataObject record
     *
     * @throws SQLException if Exception occurs during the database operation
     */
    private DataObject getDataObject(ResultSet resultSet) throws SQLException {
        TimeEntry timeEntry = new TimeEntry();

        // get record from the resultSet
        timeEntry.setPrimaryId(resultSet.getInt("TimeEntriesID"));
        timeEntry.setTaskTypeId(resultSet.getInt("TaskTypesID"));
        timeEntry.setTimeStatusId(resultSet.getInt("TimeStatusesID"));
        timeEntry.setDescription(resultSet.getString("Description"));
        timeEntry.setDate(resultSet.getDate("EntryDate"));
        timeEntry.setHours(resultSet.getFloat("Hours"));
        timeEntry.setBillable(resultSet.getBoolean("Billable"));
        timeEntry.setCreationUser(resultSet.getString("CreationUser"));
        timeEntry.setCreationDate(resultSet.getDate("CreationDate"));
        timeEntry.setModificationUser(resultSet.getString("ModificationUser"));
        timeEntry.setModificationDate(resultSet.getDate("ModificationDate"));

        return timeEntry;
    }

    /**
     * <p>
     * Checks the data object for illegal values, and throws appropriate exceptions if it finds them.
     * </p>
     *
     * @param dataObject The DataObject which will be checked
     *
     * @throws NullPointerException if <code>dataObject</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>dataObject</code> is not an instance of TimeEntry, or if description
     *         or date member in <code>dataObject</code> is <code>null</code>, or hours below 0.0F.
     */
    private void validateDataObject(DataObject dataObject) {
        // check null
        TimeEntryHelper.checkNull(dataObject, "dataObject");

        // assert dataObject is the instance of TimeEntry
        if (!(dataObject instanceof TimeEntry)) {
            throw new IllegalArgumentException("dataObject is not instance of TimeEntry");
        }

        // only the decription, date and hours fields of the dataObject will be checked.
        if (((TimeEntry) dataObject).getDescription() == null) {
            throw new IllegalArgumentException("description in dataObject is null");
        }

        if (((TimeEntry) dataObject).getDate() == null) {
            throw new IllegalArgumentException("date in dataObject is null");
        }

        if (((TimeEntry) dataObject).getHours() < 0.0F) {
            throw new IllegalArgumentException("hours in dataObject is below 0.0F");
        }
    }
}
