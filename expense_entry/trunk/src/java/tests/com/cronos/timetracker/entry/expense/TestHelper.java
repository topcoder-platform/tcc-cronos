/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import junit.framework.Assert;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public final class TestHelper {
    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /**
     * <p>
     * Creates a new instance of <code>TestHelper</code> class. The private constructor prevents the creation of a
     * new instance.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Sorts a list of data objects according to their IDs. Smallest ID comes first.
     * </p>
     *
     * @param list the list of data objects to be sorted.
     */
    public static void sortDataObjects(List list) {
        for (int i = 0; i < list.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                if (((BasicInfo) list.get(i)).getId() < ((BasicInfo) list.get(j)).getId()) {
                    Object obj = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, obj);
                }
            }
        }
    }

    /**
     * <p>
     * Asserts that two given common information should be the same. They are considered the same if and only if
     * their fields are the same.
     * </p>
     *
     * @param expected the expected common information.
     * @param actual the actual common information.
     */
    public static void assertEquals(BasicInfo expected, BasicInfo actual) {
        Assert.assertEquals("The ID should be correct.", expected.getId(), actual.getId());
        Assert.assertEquals("The description should be correct.", expected.getDescription(), actual.getDescription());
        assertEquals("The creation date should be correct.", expected.getCreationDate(), actual.getCreationDate());
        assertEquals("The modification date should be correct.", expected.getModificationDate(),
            actual.getModificationDate());
        Assert.assertEquals("The creation user should be correct.", expected.getCreationUser(),
            actual.getCreationUser());
        Assert.assertEquals("The modification user should be correct.", expected.getModificationUser(),
            actual.getModificationUser());
    }

    /**
     * <p>
     * Asserts that two given expense entries should be the same. They are considered the same if and only if their
     * fields are the same.
     * </p>
     *
     * @param expected the expected expense entry.
     * @param actual the actual expense entry.
     */
    public static void assertEquals(ExpenseEntry expected, ExpenseEntry actual) {
        assertEquals((BasicInfo) expected, (BasicInfo) actual);

        assertEquals(expected.getExpenseType(), actual.getExpenseType());
        assertEquals(expected.getStatus(), actual.getStatus());
        Assert.assertEquals("The billable flag should be correct.", expected.isBillable(), actual.isBillable());
        assertEquals("The date should be correct.", expected.getDate(), actual.getDate());
        Assert.assertEquals("The amount of money should be correct.", expected.getAmount().doubleValue(),
            actual.getAmount().doubleValue(), 1E-9);
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
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws SQLException if database error occurs.
     */
    public static void clearDatabase(Connection connection)
        throws SQLException {
        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("DELETE FROM comp_rej_reason;");
            statement.executeUpdate("DELETE FROM comp_exp_type;");
            statement.executeUpdate("DELETE FROM exp_reject_reason;");
            statement.executeUpdate("DELETE FROM expense_entry;");
            statement.executeUpdate("DELETE FROM expense_type;");
            statement.executeUpdate("DELETE FROM expense_status;");
            statement.executeUpdate("DELETE FROM company;");
            statement.executeUpdate("DELETE FROM reject_reason;");
        } finally {
            statement.close();
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

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }

        if (configManager.existsNamespace(DB_NAMESPACE)) {
            configManager.removeNamespace(DB_NAMESPACE);
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

        configManager.add("Valid.xml");
        configManager.add("Database.xml");
    }
}


