/**
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cronos.timetracker.entry.time.DataObject;
import com.cronos.timetracker.entry.time.RejectReason;
import com.cronos.timetracker.entry.time.TaskType;
import com.cronos.timetracker.entry.time.TimeEntry;
import com.cronos.timetracker.entry.time.TimeStatus;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * UnitTestHelper class provides some useful helper methods. Such as create a
 * customer defined connection, insert/select/delete records in the datebase.
 * </p>
 * @author fuyun
 * @version 1.1
 */
class StressTestHelper {

    /**
     * Represents the connection name used to get connection in DB Connection
     * Factory component.
     */
    static final String CONNECTION_NAME = "stresstest";

    /**
     * Represents the description field.
     */
    static final String DESCRIPTION = "TopCoder";

    /**
     * Represents the creation and modification date.
     */
    static final Date CREATION_DATE = new Date();

    /**
     * Represents the creation and modification user.
     */
    static final String CREATION_USER = "topcoders";

    /**
     * The SQL statement to insert a row into the TaskTypes table.
     */
    private static final String INSERT_TASKTYPES_SQL = "INSERT INTO TaskTypes(TaskTypesID, Description, CreationUser, "
            + "CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)";

    /**
     * The SQL statement to select all rows from the TaskTypes table.
     */
    private static final String SELECT_TASKTYPES_SQL = "SELECT * FROM TaskTypes";

    /**
     * The SQL statement to delete all rows from the TaskTypes table.
     */
    private static final String DELETE_TASKTYPES_SQL = "DELETE FROM TaskTypes";

    /**
     * The SQL statement to insert a row into the TimeStatuses table.
     */
    private static final String INSERT_TIMESTATUSES_SQL = "INSERT INTO TimeStatuses(TimeStatusesID, Description, "
            + "CreationUser, CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)";

    /**
     * The SQL statement to select all rows from the TimeStatuses table.
     */
    private static final String SELECT_TIMESTATUSES_SQL = "SELECT * FROM TimeStatuses";

    /**
     * The SQL statement to delete all rows from the TimeStatuses table.
     */
    private static final String DELETE_TIMESTATUSES_SQL = "DELETE FROM TimeStatuses";

    /**
     * The SQL statement to insert a row into the TimeEntries table.
     */
    private static final String INSERT_TIMEENTRIES_SQL = "INSERT INTO TimeEntries(TimeEntriesID, TaskTypesID, "
            + "TimeStatusesID, Description, EntryDate, Hours, Billable, CreationUser, CreationDate, ModificationUser, "
            + "ModificationDate) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * The SQL statement to select all rows from the TimeEntries table.
     */
    private static final String SELECT_TIMEENTRIES_SQL = "SELECT * FROM TimeEntries";

    /**
     * The SQL statement to delete all rows from the TimeEntries table.
     */
    private static final String DELETE_TIMEENTRIES_SQL = "DELETE FROM TimeEntries";

    /**
     * The SQL statement to delete all rows from the time_reject_reason table.
     */
    private static final String DELETE_TIMEREJECTREASON_SQL = "DELETE FROM time_reject_reason";

    /**
     * <p>
     * Private constructor to prevent this class be instantiated.
     * </p>
     */
    private StressTestHelper() {
        // empty
    }

    /**
     * Inserts the given TaskType into the TaskTypes table.
     * @param taskType the DataObject instance holding the inforation to store
     *            into the database
     * @param conn the connection instance to connect the database
     * @throws SQLException if any SQL error occurs.
     */
    static void insertTaskTypes(TaskType taskType, Connection conn)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(INSERT_TASKTYPES_SQL);

            pstmt.setInt(1, taskType.getPrimaryId());
            pstmt.setString(2, truncatesString(taskType.getDescription()));
            pstmt.setString(3, truncatesString(taskType.getCreationUser()));
            pstmt.setDate(4, new java.sql.Date(taskType.getCreationDate()
                    .getTime()));
            pstmt.setString(5, truncatesString(taskType.getModificationUser()));
            pstmt.setDate(6, new java.sql.Date(taskType.getModificationDate()
                    .getTime()));

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Select all the records from TaskTypes table.
     * @param conn the connection instance to connect the database
     * @return all the records
     * @throws SQLException if any SQL error occurs.
     */
    static List selectTaskTypes(Connection conn) throws SQLException {
        Statement pstmt = null;
        List taskTypes = new ArrayList();

        try {
            pstmt = conn.createStatement();

            ResultSet resultSet = pstmt.executeQuery(SELECT_TASKTYPES_SQL);

            if (resultSet != null) {
                while (resultSet.next()) {
                    TaskType taskType = new TaskType();

                    // get record from the resultSet
                    taskType.setPrimaryId(resultSet.getInt("TaskTypesID"));
                    taskType.setDescription(resultSet.getString("Description"));
                    taskType.setCreationUser(resultSet
                            .getString("CreationUser"));
                    taskType.setCreationDate(resultSet.getDate("CreationDate"));
                    taskType.setModificationUser(resultSet
                            .getString("ModificationUser"));
                    taskType.setModificationDate(resultSet
                            .getDate("ModificationDate"));

                    taskTypes.add(taskType);
                }
            }

            return taskTypes;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Deletes all taskType from the TaskTypes table.
     * @param conn the connection instance to connect the database
     * @throws SQLException if any SQL error occurs.
     */
    static void clearTaskTypes(Connection conn) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            // delete all rows
            pstmt = conn.prepareStatement(DELETE_TASKTYPES_SQL);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Inserts the given TimeStatus into the TimeStatuses table.
     * @param timeStatus the DataObject instance holding the inforation to store
     *            into the database
     * @param conn the connection instance to connect the database
     * @throws SQLException if any SQL error occurs.
     */
    static void insertTimeStatuses(TimeStatus timeStatus, Connection conn)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(INSERT_TIMESTATUSES_SQL);

            pstmt.setInt(1, timeStatus.getPrimaryId());
            pstmt.setString(2, truncatesString(timeStatus.getDescription()));
            pstmt.setString(3, truncatesString(timeStatus.getCreationUser()));
            pstmt.setDate(4, new java.sql.Date(timeStatus.getCreationDate()
                    .getTime()));
            pstmt.setString(5,
                    truncatesString(timeStatus.getModificationUser()));
            pstmt.setDate(6, new java.sql.Date(timeStatus.getModificationDate()
                    .getTime()));

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Select all the records from TimeStatuses table.
     * @param conn the connection instance to connect the database
     * @return all the records
     * @throws SQLException if any SQL error occurs.
     */
    static List selectTimeStatuses(Connection conn) throws SQLException {
        Statement pstmt = null;
        List timeStatuses = new ArrayList();

        try {
            pstmt = conn.createStatement();

            ResultSet resultSet = pstmt.executeQuery(SELECT_TIMESTATUSES_SQL);

            if (resultSet != null) {
                while (resultSet.next()) {
                    TimeStatus timeStatus = new TimeStatus();

                    // get record from the resultSet
                    timeStatus.setPrimaryId(resultSet.getInt("TimeStatusesID"));
                    timeStatus.setDescription(resultSet
                            .getString("Description"));
                    timeStatus.setCreationUser(resultSet
                            .getString("CreationUser"));
                    timeStatus.setCreationDate(resultSet
                            .getDate("CreationDate"));
                    timeStatus.setModificationUser(resultSet
                            .getString("ModificationUser"));
                    timeStatus.setModificationDate(resultSet
                            .getDate("ModificationDate"));

                    timeStatuses.add(timeStatus);
                }
            }

            return timeStatuses;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Deletes all TimeStatus from the TimeStatuses table.
     * @param conn the connection instance to connect the database
     * @throws SQLException if any SQL error occurs.
     */
    static void clearTimeStatuses(Connection conn) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            // delete all rows
            pstmt = conn.prepareStatement(DELETE_TIMESTATUSES_SQL);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Inserts the given TimeEntry into the TimeEntries table.
     * @param timeEntry the DataObject instance holding the inforation to store
     *            into the database
     * @param conn the connection instance to connect the database
     * @throws SQLException if any SQL error occurs.
     */
    static void insertTimeEntries(TimeEntry timeEntry, Connection conn)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(INSERT_TIMEENTRIES_SQL);

            pstmt.setInt(1, timeEntry.getPrimaryId());
            pstmt.setInt(2, timeEntry.getTaskTypeId());
            pstmt.setInt(3, timeEntry.getTimeStatusId());
            pstmt.setString(4, truncatesString(timeEntry.getDescription()));
            pstmt.setDate(5, new java.sql.Date(timeEntry.getDate().getTime()));
            pstmt.setFloat(6, timeEntry.getHours());
            pstmt.setBoolean(7, timeEntry.isBillable());
            pstmt.setString(8, truncatesString(timeEntry.getCreationUser()));
            pstmt.setDate(9, new java.sql.Date(timeEntry.getCreationDate()
                    .getTime()));
            pstmt.setString(10,
                    truncatesString(timeEntry.getModificationUser()));
            pstmt.setDate(11, new java.sql.Date(timeEntry.getModificationDate()
                    .getTime()));

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Select all the records from TimeEntries table.
     * @param conn the connection instance to connect the database
     * @return all the records
     * @throws SQLException if any SQL error occurs.
     */
    static List selectTimeEntries(Connection conn) throws SQLException {
        Statement pstmt = null;
        List timeEntries = new ArrayList();

        try {
            pstmt = conn.createStatement();

            ResultSet resultSet = pstmt.executeQuery(SELECT_TIMEENTRIES_SQL);

            if (resultSet != null) {
                while (resultSet.next()) {
                    TimeEntry timeEntry = new TimeEntry();

                    // get record from the resultSet
                    timeEntry.setPrimaryId(resultSet.getInt("TimeEntriesID"));
                    timeEntry.setTaskTypeId(resultSet.getInt("TaskTypesID"));
                    timeEntry.setTimeStatusId(resultSet
                            .getInt("TimeStatusesID"));
                    timeEntry
                            .setDescription(resultSet.getString("Description"));
                    timeEntry.setDate(resultSet.getDate("EntryDate"));
                    timeEntry.setHours(resultSet.getFloat("Hours"));
                    timeEntry.setBillable(resultSet.getBoolean("Billable"));
                    timeEntry.setCreationUser(resultSet
                            .getString("CreationUser"));
                    timeEntry
                            .setCreationDate(resultSet.getDate("CreationDate"));
                    timeEntry.setModificationUser(resultSet
                            .getString("ModificationUser"));
                    timeEntry.setModificationDate(resultSet
                            .getDate("ModificationDate"));

                    timeEntries.add(timeEntry);
                }
            }

            return timeEntries;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Deletes all TimeEntry from the TimeEntries table.
     * @param conn the connection instance to connect the database
     * @throws SQLException if any SQL error occurs.
     */
    static void clearTimeEntries(Connection conn) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            // delete all rows
            pstmt = conn.prepareStatement(DELETE_TIMEENTRIES_SQL);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     * @param namespace the namespace to load the DBConnectionFactory
     *            configuration
     * @param conname the connection name to fetch the connection
     * @return the connection created from DBConnectionFactory
     * @throws Exception any exception during the create connection process
     */
    static Connection getConnection(String namespace, String conname)
        throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        return factory.createConnection(conname);
    }

    /**
     * <p>
     * release the resources.
     * </p>
     * @param resultSet the ResultSet to be released
     * @param statement the Statement to be released
     * @param connection the Connection to be released
     * @throws SQLException any exception during the closing process
     */
    static void closeResources(ResultSet resultSet, Statement statement,
            Connection connection) throws SQLException {
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

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException se) {
                success = false;
            }
        }

        if (!success) {
            throw new SQLException("fail to close the resources");
        }
    }

    /**
     * convert the input Date instance to a long value. Use second accuracy.
     * @param date the Date instance needed to convert
     * @return the converted value
     */
    static long convertDate(Date date) {
        long time = date.getTime();
        return ((long) (time / 1000.0)) * 1000;
    }

    /**
     * truncates the given string if its length is longer than 64.
     * @param oldString the string should be check to truncate
     * @return the truncated string
     */
    static String truncatesString(String oldString) {
        if (oldString.length() <= 64) {
            return oldString;
        }

        return oldString.substring(0, 64);
    }

    /**
     * Clean the database.
     * @param namespace the namespace to load configuration.
     * @param connName the connection name.
     * @throws Exception if there is any unexpected exception.
     */
    static void cleanDB(String namespace, String connName) throws Exception {
        Connection conn = getConnection(namespace, connName);
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(DELETE_TIMEREJECTREASON_SQL);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(DELETE_TIMEENTRIES_SQL);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(DELETE_TASKTYPES_SQL);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(DELETE_TIMESTATUSES_SQL);
            stmt.executeUpdate();
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    /**
     * <p>
     * return a TaskType instance for testing.
     * </p>
     * @param primaryID the primary id for the instance.
     * @return a TaskType instance for testing.
     */
    static DataObject getTaskType(int primaryID) {
        TaskType taskType = new TaskType();

        taskType.setPrimaryId(primaryID);
        taskType.setDescription(DESCRIPTION);
        taskType.setCreationUser(CREATION_USER);
        taskType.setCreationDate(CREATION_DATE);
        taskType.setModificationUser(CREATION_USER);
        taskType.setModificationDate(CREATION_DATE);

        return taskType;
    }

    /**
     * <p>
     * return a TimeStatus instance for testing.
     * </p>
     * @param primaryID the primary id for the instance.
     * @return a TimeStatus instance for testing.
     */
    static DataObject getTimeStatus(int primaryID) {
        TimeStatus timeStatus = new TimeStatus();

        timeStatus.setPrimaryId(primaryID);
        timeStatus.setDescription(DESCRIPTION);
        timeStatus.setCreationUser(CREATION_USER);
        timeStatus.setCreationDate(CREATION_DATE);
        timeStatus.setModificationUser(CREATION_USER);
        timeStatus.setModificationDate(CREATION_DATE);

        return timeStatus;
    }

    /**
     * <p>
     * return a TimeEntry instance for testing.
     * </p>
     * @param rejectReason1 the RejectReason object in TimeEntry.
     * @param rejectReason2 the RejectReason object in TimeEntry.
     * @return a TimeEntry instance for testing.
     */
    static DataObject getTimeEntry(RejectReason rejectReason1,
            RejectReason rejectReason2) {
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.addRejectReason(rejectReason1);
        timeEntry.addRejectReason(rejectReason2);
        timeEntry.setDescription(StressTestHelper.DESCRIPTION);
        timeEntry.setDate(StressTestHelper.CREATION_DATE);
        return timeEntry;
    }

    /**
     * <p>
     * return a RejectReason instance for testing.
     * </p>
     * @param id the primary id for the RejectReason istance.
     * @return a RejectReason instance for testing.
     */
    static RejectReason getRejectReason(int id) {
        RejectReason rejectReason = new RejectReason();
        rejectReason.setPrimaryId(id);
        return rejectReason;
    }
}
