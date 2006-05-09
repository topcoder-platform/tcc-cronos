/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.accuracytests;

import com.cronos.timetracker.entry.time.BaseDataObject;
import com.cronos.timetracker.entry.time.RejectReason;
import com.cronos.timetracker.entry.time.TaskType;
import com.cronos.timetracker.entry.time.TimeEntry;
import com.cronos.timetracker.entry.time.TimeStatus;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import junit.framework.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author oodinary
 * @version 1.1
 */
public final class AccuracyTestHelper {
    /**
     * <p>
     * The private constructor prevents the creation of a new instance.
     * </p>
     */
    private AccuracyTestHelper() {
    }

    /**
     * <p>
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws SQLException if database error occurs.
     */
    public static void clearDatabase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("DELETE FROM time_reject_reason;");
            statement.executeUpdate("DELETE FROM reject_reason;");
            statement.executeUpdate("DELETE FROM TimeEntries;");
            statement.executeUpdate("DELETE FROM TimeStatuses;");
            statement.executeUpdate("DELETE FROM TaskTypes;");
        } finally {
            statement.close();
        }
    }

    /**
     * Inserts the given TaskType into the TaskTypes table.
     *
     * @param taskType the DataObject instance holding the inforation to store into the database
     * @param conn the connection instance to connect the database
     *
     * @throws SQLException if any SQL error occurs.
     */
    public static void insertTaskTypes(TaskType taskType, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO TaskTypes(TaskTypesID, Description, CreationUser, " +
                    "CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

            pstmt.setInt(1, taskType.getPrimaryId());
            pstmt.setString(2, taskType.getDescription());
            pstmt.setString(3, taskType.getCreationUser());
            pstmt.setDate(4, new java.sql.Date(taskType.getCreationDate().getTime()));
            pstmt.setString(5, taskType.getModificationUser());
            pstmt.setDate(6, new java.sql.Date(taskType.getModificationDate().getTime()));

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Inserts the given TimeStatus into the TimeStatuses table.
     *
     * @param timeStatus the DataObject instance holding the inforation to store into the database
     * @param conn the connection instance to connect the database
     *
     * @throws SQLException if any SQL error occurs.
     */
    public static void insertTimeStatuses(TimeStatus timeStatus, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO TimeStatuses(TimeStatusesID, Description, " +
                    "CreationUser, CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

            pstmt.setInt(1, timeStatus.getPrimaryId());
            pstmt.setString(2, timeStatus.getDescription());
            pstmt.setString(3, timeStatus.getCreationUser());
            pstmt.setDate(4, new java.sql.Date(timeStatus.getCreationDate().getTime()));
            pstmt.setString(5, timeStatus.getModificationUser());
            pstmt.setDate(6, new java.sql.Date(timeStatus.getModificationDate().getTime()));

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Inserts the given RejectReason into the reject_reason table.
     *
     * @param rejectReason the RejectReason instance holding the inforation to store into the database
     * @param conn the connection instance to connect the database
     *
     * @throws SQLException if any SQL error occurs.
     */
    public static void insertRejectReasons(RejectReason rejectReason, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO reject_reason(reject_reason_id, description, " +
                    "creation_user, creation_date, modification_user, modification_date) VALUES (?,?,?,?,?,?)");

            pstmt.setInt(1, rejectReason.getPrimaryId());
            pstmt.setString(2, rejectReason.getDescription());
            pstmt.setString(3, rejectReason.getCreationUser());
            pstmt.setDate(4, new java.sql.Date(rejectReason.getCreationDate().getTime()));
            pstmt.setString(5, rejectReason.getModificationUser());
            pstmt.setDate(6, new java.sql.Date(rejectReason.getModificationDate().getTime()));

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Select all the records from reject_reason table.
     *
     * @param conn the connection instance to connect the database
     *
     * @return all the records
     *
     * @throws SQLException if any SQL error occurs.
     */
    public static List selectRejectReasons(Connection conn) throws SQLException {
        Statement pstmt = null;
        List rejectReasons = new ArrayList();
        pstmt = conn.createStatement();

        ResultSet resultSet = pstmt.executeQuery("SELECT * FROM reject_reason");

        while (resultSet.next()) {
            RejectReason rejectReason = new RejectReason();

            // get record from the resultSet
            rejectReason.setPrimaryId(resultSet.getInt("reject_reason_id"));
            rejectReason.setDescription(resultSet.getString("description"));
            rejectReason.setCreationUser(resultSet.getString("creation_user"));
            rejectReason.setCreationDate(resultSet.getDate("creation_date"));
            rejectReason.setModificationUser(resultSet.getString("modification_user"));
            rejectReason.setModificationDate(resultSet.getDate("modification_date"));

            rejectReasons.add(rejectReason);
        }

        pstmt.close();

        return rejectReasons;
    }

    /**
     * Select all the records from TimeEntries table.
     *
     * @param conn the connection instance to connect the database
     *
     * @return all the records
     *
     * @throws SQLException if any SQL error occurs.
     */
    public static List selectTimeEntries(Connection conn) throws SQLException {
        Statement pstmt = null;
        List timeEntries = new ArrayList();

        pstmt = conn.createStatement();

        ResultSet resultSet = pstmt.executeQuery("SELECT * FROM TimeEntries");

        while (resultSet.next()) {
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

            timeEntries.add(timeEntry);
        }

        pstmt.close();

        return timeEntries;
    }

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @param namespace the namespace to load the DBConnectionFactory configuration
     * @param conname the connection name to fetch the connection
     *
     * @return the connection created from DBConnectionFactory
     *
     * @throws Exception any exception during the create connection process
     */
    public static Connection getConnection(String namespace, String conname) throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        return factory.createConnection(conname);
    }

    /**
     * <p>
     * Adds a valid configuration.
     * </p>
     *
     * @throws ConfigManagerException if error occurs when adding the configuration.
     */
    public static void addValidConfiguration() throws ConfigManagerException {
        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add("AccuracyTests/SampleXML.xml");
    }

    /**
     * <p>
     * Clears the configuration used in tests.
     * </p>
     *
     * @throws ConfigManagerException if error occurs when clearing the configuration.
     */
    public static void clearConfiguration() throws ConfigManagerException {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Creates a <code>Date</code> instance representing the given year, month and date. The time would be 0:0:0.
     * </p>
     *
     * @param year the year in the instance.
     * @param month the month in the instance.
     * @param date the date in the instance.
     *
     * @return a <code>Date</code> instance representing the year, month and date.
     */
    public static Date createDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, date, 0, 0, 0);

        return calendar.getTime();
    }

    /**
     * <p>
     * Asserts that two given dates should be the same. They are considered the same if and only if the year, month,
     * date, hours, minutes and seconds are the same.
     * </p>
     *
     * @param message the message to be given if two given dates are not the same.
     * @param expected the expected date.
     * @param actual the actual date.
     */
    public static void assertEquals(String message, Date expected, Date actual) {
        Calendar expectedCalendar = Calendar.getInstance();
        Calendar actualCalendar = Calendar.getInstance();

        expectedCalendar.setTime(expected);
        actualCalendar.setTime(actual);

        Assert.assertEquals(message, expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.DAY_OF_MONTH),
            actualCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.HOUR_OF_DAY),
            actualCalendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.MINUTE), actualCalendar.get(Calendar.MINUTE));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.SECOND), actualCalendar.get(Calendar.SECOND));
    }

    /**
     * <p>
     * Asserts that two <code>BaseDataObject</code> objects is the same.
     * </p>
     *
     * @param msg the description.
     * @param expected the expected BaseDataObject instance.
     * @param actual the actual BaseDataObject instance.
     */
    public static void assertEquals(String msg, BaseDataObject expected, BaseDataObject actual) {
        assertEquals(msg, expected.getCreationDate(), actual.getCreationDate());
        Assert.assertEquals(msg, expected.getCreationUser(), actual.getCreationUser());
        Assert.assertEquals(msg, expected.getDescription(), actual.getDescription());
        assertEquals(msg, expected.getModificationDate(), actual.getModificationDate());
        Assert.assertEquals(msg, expected.getModificationUser(), actual.getModificationUser());
        Assert.assertEquals(msg, expected.getPrimaryId(), actual.getPrimaryId());
    }

    /**
     * <p>
     * judge whether the two TimeEntry are equals. They are equals when all their fields contain the same value.
     * </p>
     *
     * @param message the error message when the two entry are not equal.
     * @param expected the expected TimeEntry.
     * @param actual the actual TimeEntry.
     */
    public static void assertEquals(String message, TimeEntry expected, TimeEntry actual) {
        Assert.assertEquals(message, expected.getPrimaryId(), actual.getPrimaryId());
        Assert.assertEquals(message, expected.getTaskTypeId(), actual.getTaskTypeId());
        Assert.assertEquals(message, expected.getTimeStatusId(), actual.getTimeStatusId());
        AccuracyTestHelper.assertEquals(message, expected.getDate(), actual.getDate());
        Assert.assertEquals(message, expected.getHours(), actual.getHours(), 1e-8);
        Assert.assertEquals(message, expected.isBillable(), actual.isBillable());
        Assert.assertEquals(message, expected.getDescription(), actual.getDescription());
        Assert.assertEquals(message, expected.getCreationUser(), actual.getCreationUser());
        AccuracyTestHelper.assertEquals(message, expected.getCreationDate(), actual.getCreationDate());
        Assert.assertEquals(message, expected.getModificationUser(), actual.getModificationUser());
        AccuracyTestHelper.assertEquals(message, expected.getModificationDate(), actual.getModificationDate());
        Assert.assertEquals(message, expected.getAllRejectReasons().length, actual.getAllRejectReasons().length);
    }
}
