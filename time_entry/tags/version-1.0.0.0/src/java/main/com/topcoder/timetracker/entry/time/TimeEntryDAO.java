/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * This class extends <code>BaseDAO</code> and provides basic persistence functionality for the <code>TimeEntry</code>
 * data object. Implements all abstract helper methods. The creation and modification parameters for the create and
 * update operations are obtained  from the user parameter passed as part of these operations and current time.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
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
     * <p>
     * Creates a new instance of <code>TimeEntryDAO</code> class. Sets the connection name and namespace that
     * the component will use to obtain Connections from the DBConnection Factory.
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
     * data and hours field of the dataObject will be checked.
     * </p>
     *
     * @param dataObject The DataObject which will be checked
     *
     * @throws NullPointerException if <code>dataObject</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>dataObject</code> is not an instance of TimeEntry, or if description
     * or date member in <code>dataObject</code> is <code>null</code>, or hours below 0.0F.
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
     *
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
     * or date member in <code>dataObject</code> is <code>null</code>, or hours below 0.0F.
     * @throws DAOActionException if any other exception happens.
     */
    protected void verifyUpdateDataObject(final DataObject dataObject) throws DAOActionException {
        this.validateDataObject(dataObject);
    }

    /**
     * <p>
     * Returns the sql string that will be used in the prepared statement for updating a time entry.
     * SQL_UPDATE_STATEMENT will be returned.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for update a record
     *
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
        String modificationUser, Date modificationDate)
        throws DAOActionException {
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
     *
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
     *
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
    protected DataObject processReadResultSet(ResultSet resultSet)
        throws DAOActionException {
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
     *
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
    protected List processReadListResultSet(ResultSet resultSet)
        throws DAOActionException {
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
     * retrieve the DataObject record from the given resultSet.
     * </p>
     *
     * @param resultSet the ResultSet to retrieve the DataObject record
     *
     * @return the retrieved the DataObject record
     *
     * @throws SQLException if Exception occurs during the database operation
     */
    private DataObject getDataObject(ResultSet resultSet)
        throws SQLException {
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
     * or date member in <code>dataObject</code> is <code>null</code>, or hours below 0.0F.
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
