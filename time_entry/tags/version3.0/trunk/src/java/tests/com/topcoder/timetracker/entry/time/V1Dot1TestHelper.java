/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import junit.framework.Assert;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public final class V1Dot1TestHelper {
    /** The SQL statement to insert a row into the TaskTypes table. */
    private static final String INSERT_TASKTYPES_SQL = "INSERT INTO task_type(task_type_id, description," +
        " creation_user, creation_date, modification_user, modification_date, active) VALUES (?,?,?,?,?,?,0)";

    /** The SQL statement to insert a row into the TaskTypes table. */
    private static final String INSERT_COMP_TASKTYPES_SQL = "INSERT INTO comp_task_type(company_id, task_type_id, " +
            "creation_user, creation_date, modification_user, modification_date) VALUES (?,?,?,?,?,?)";

    /** The SQL statement to insert a row into the TimeStatuses table. */
    private static final String INSERT_TIMESTATUSES_SQL = "INSERT INTO time_status(time_status_id, description, "
        + "creation_user, creation_date, modification_user, modification_date) VALUES (?,?,?,?,?,?)";

    /** The SQL statement to insert a row into the reject_reason table. */
    private static final String INSERT_REJECTREASON_SQL = "INSERT INTO reject_reason(reject_reason_id, description, "
        + "creation_user, creation_date, modification_user, modification_date, active) VALUES (?,?,?,?,?,?,0)";

    /** The SQL statement to select all rows from the reject_reason table. */
    private static final String SELECT_REJECTREASON_SQL = "SELECT * FROM reject_reason";

    /** The SQL statement to insert a row into the TimeEntries table. */
    private static final String INSERT_TIMEENTRIES_SQL = "INSERT INTO time_entry(time_entry_id, task_type_id, "
        + "time_status_id, description, entry_date, hours, billable, creation_user, creation_date, modification_user, "
        + "modification_date, company_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,1)";

    /** The SQL statement to select all rows from the TimeEntries table. */
    private static final String SELECT_TIMEENTRIES_SQL = "SELECT * FROM time_entry";

    /**
     * <p>
     * Creates a new instance of <code>V1Dot1TestHelper</code> class. The private constructor prevents the creation of
     * a new instance.
     * </p>
     */
    private V1Dot1TestHelper() {
    }

    /**
     * <p>
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
     * </p>
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be retrieved.
     *
     * @return the value of the private field or <code>null</code> if any error occurs.
     */
    public static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
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
            statement.executeUpdate("DELETE FROM comp_rej_reason;");
            statement.executeUpdate("DELETE FROM comp_task_type;");
            statement.executeUpdate("DELETE FROM time_reject_reason;");
            statement.executeUpdate("DELETE FROM time_entry;");
            statement.executeUpdate("DELETE FROM task_type;");
            statement.executeUpdate("DELETE FROM time_status;");
            statement.executeUpdate("DELETE FROM company;");
            statement.executeUpdate("DELETE FROM reject_reason;");
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
    public static void insertTaskTypes(TaskType taskType, Connection conn)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(INSERT_TASKTYPES_SQL);
            pstmt.setInt(1, taskType.getPrimaryId());
            pstmt.setString(2, taskType.getDescription());
            pstmt.setString(3, taskType.getCreationUser());
            pstmt.setDate(4, new java.sql.Date(taskType.getCreationDate().getTime()));
            pstmt.setString(5, taskType.getModificationUser());
            pstmt.setDate(6, new java.sql.Date(taskType.getModificationDate().getTime()));
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(INSERT_COMP_TASKTYPES_SQL);
            pstmt.setInt(1, taskType.getCompanyId());
            pstmt.setInt(2, taskType.getPrimaryId());
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
    public static void insertTimeStatuses(TimeStatus timeStatus, Connection conn)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(INSERT_TIMESTATUSES_SQL);

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
    public static void insertRejectReasons(RejectReason rejectReason, Connection conn)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(INSERT_REJECTREASON_SQL);

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
     * <p>
     * Execute the sql statement to add some test data.
     * </p>
     *
     * @param sql the sql statement.
     * @param conn the connection
     * @throws SQLException if any SQL error occurs.
     */
    public static void executeSQL(String sql, Connection conn) throws SQLException {
            PreparedStatement pstmt = null;
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
            }
    }

    /**
     * <p>
     * Execute the sql statement to add some test data.
     * </p>
     *
     * @param sql the sql statement.
     * @param conn the connection
     * @throws SQLException if any SQL error occurs.
     */
    public static void printSql(String sql, Connection conn) throws SQLException {
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                System.out.println(rs.next());
            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (rs != null) {
                    rs.close();
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

        try {
            pstmt = conn.createStatement();

            ResultSet resultSet = pstmt.executeQuery(SELECT_REJECTREASON_SQL);

            if (resultSet != null) {
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
            }

            return rejectReasons;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Inserts the given TimeEntry into the TimeEntries table.
     *
     * @param timeEntry the DataObject instance holding the inforation to store into the database
     * @param conn the connection instance to connect the database
     *
     * @throws SQLException if any SQL error occurs.
     */
    public static void insertTimeEntries(TimeEntry timeEntry, Connection conn)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(INSERT_TIMEENTRIES_SQL);

            pstmt.setInt(1, timeEntry.getPrimaryId());
            pstmt.setInt(2, timeEntry.getTaskTypeId());
            pstmt.setInt(3, timeEntry.getTimeStatusId());
            pstmt.setString(4, TimeEntryHelper.truncatesString(timeEntry.getDescription()));
            pstmt.setDate(5, new java.sql.Date(timeEntry.getDate().getTime()));
            pstmt.setFloat(6, timeEntry.getHours());
            pstmt.setBoolean(7, timeEntry.isBillable());
            pstmt.setString(8, TimeEntryHelper.truncatesString(timeEntry.getCreationUser()));
            pstmt.setDate(9, new java.sql.Date(timeEntry.getCreationDate().getTime()));
            pstmt.setString(10, TimeEntryHelper.truncatesString(timeEntry.getModificationUser()));
            pstmt.setDate(11, new java.sql.Date(timeEntry.getModificationDate().getTime()));

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
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

        try {
            pstmt = conn.createStatement();

            ResultSet resultSet = pstmt.executeQuery(SELECT_TIMEENTRIES_SQL);

            if (resultSet != null) {
                while (resultSet.next()) {
                    TimeEntry timeEntry = new TimeEntry();
                    // get record from the resultSet
                    timeEntry.setPrimaryId(resultSet.getInt("time_entry_id"));
                    timeEntry.setTaskTypeId(resultSet.getInt("task_type_id"));
                    timeEntry.setTimeStatusId(resultSet.getInt("time_status_id"));
                    timeEntry.setDescription(resultSet.getString("description"));
                    timeEntry.setDate(resultSet.getDate("entry_date"));
                    timeEntry.setHours(resultSet.getFloat("hours"));
                    timeEntry.setBillable(resultSet.getBoolean("billable"));
                    timeEntry.setCreationUser(resultSet.getString("creation_user"));
                    timeEntry.setCreationDate(resultSet.getDate("creation_date"));
                    timeEntry.setModificationUser(resultSet.getString("modification_user"));
                    timeEntry.setModificationDate(resultSet.getDate("modification_date"));

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
     * Adds a valid configuration.
     * </p>
     *
     * @throws ConfigManagerException if error occurs when adding the configuration.
     */
    public static void addValidConfiguration() throws ConfigManagerException {
        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add("V1.1/SampleXML.xml");
    }

    /**
     * <p>
     * Asserts that two given array should be the same.
     * </p>
     *
     * @param message the message to be given if two given arrays are not the same.
     * @param expected the expected array.
     * @param actual the actual array.
     */
    public static void assertEquals(String message, Object[] expected, Object[] actual) {
        Assert.assertEquals(message, expected.length, actual.length);

        Set left = new HashSet(Arrays.asList(actual));

        for (int i = 0; i < expected.length; i++) {
            Assert.assertTrue(message, left.contains(expected[i]));
            left.remove(expected[i]);
        }
    }

    /**
     * convert the input Date instance to a long value. Use second accuracy.
     *
     * @param date the Date instance needed to convert
     *
     * @return the converted value
     */
    public static long convertDate(Date date) {
        long time = date.getTime();

        return ((long) (time / 1000.0)) * 1000;
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
    public static Connection getConnection(String namespace, String conname)
        throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        return factory.createConnection(conname);
    }

    /**
     * <p>
     * release the resources.
     * </p>
     *
     * @param resultSet the ResultSet to be released
     * @param statement the Statement to be released
     * @param connection the Connection to be released
     *
     * @throws SQLException any exception during the closing process
     */
    public static void closeResources(ResultSet resultSet, Statement statement, Connection connection)
        throws SQLException {
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
     * Asserts that two given dates should not be the same. They are considered the same if and only if the year,
     * month, date, hours, minutes and seconds are the same.
     * </p>
     *
     * @param message the message to be given if two given dates are the same.
     * @param expected the expected date.
     * @param actual the actual date.
     */
    public static void assertNotEquals(String message, Date expected, Date actual) {
        Calendar expectedCalendar = Calendar.getInstance();
        Calendar actualCalendar = Calendar.getInstance();

        expectedCalendar.setTime(expected);
        actualCalendar.setTime(actual);

        boolean equals = true;
        equals &= (expectedCalendar.get(Calendar.YEAR) == actualCalendar.get(Calendar.YEAR));
        equals &= (expectedCalendar.get(Calendar.MONTH) == actualCalendar.get(Calendar.MONTH));
        equals &= (expectedCalendar.get(Calendar.DAY_OF_MONTH) == actualCalendar.get(Calendar.DAY_OF_MONTH));
        equals &= (expectedCalendar.get(Calendar.HOUR_OF_DAY) == actualCalendar.get(Calendar.HOUR_OF_DAY));
        equals &= (expectedCalendar.get(Calendar.MINUTE) == actualCalendar.get(Calendar.MINUTE));
        equals &= (expectedCalendar.get(Calendar.SECOND) == actualCalendar.get(Calendar.SECOND));
        Assert.assertFalse(message, equals);
    }
}
