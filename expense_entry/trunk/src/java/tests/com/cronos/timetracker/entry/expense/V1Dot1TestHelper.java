/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import junit.framework.Assert;

import java.io.File;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
 * <p>
 * Bug fix for TT-1976. Modify assertEquals method also to check the description of reject reason of given entries. Add
 * two helper methods checkEquals(ExpenseEntryRejectReason[], ExpenseEntryRejectReason[]) and
 * sortReasons(ExpenseEntryRejectReason[]).
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public final class V1Dot1TestHelper {
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
     * Asserts that two given common information should be the same. They are considered the same if and only if their
     * fields are the same.
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

        final double eps = 1E-9;
        Assert.assertEquals("The amount of money should be correct.", expected.getAmount().doubleValue(),
            actual.getAmount().doubleValue(), eps);
        Assert.assertTrue(checkEquals(expected.getRejectReasons(), actual.getRejectReasons()));
    }

    /**
     * Check the equalness (reject reason id and its description) of given ExpenseEntryRejectReason array.
     *
     * @param reasons1 the ExpenseEntryRejectReason array to compare
     * @param reasons2 the ExpenseEntryRejectReason array to compare
     *
     * @return true if and only if these given two array are equal.
     */
    public static boolean checkEquals(ExpenseEntryRejectReason[] reasons1, ExpenseEntryRejectReason[] reasons2) {
        if (reasons1.length != reasons2.length) {
            return false;
        }

        sortReasons(reasons1);
        sortReasons(reasons2);

        for (int i = 0; i < reasons1.length; i++) {
            if ((reasons1[i].getRejectReasonId() != reasons2[i].getRejectReasonId()) ||
                    !reasons1[i].getDescription().equals(reasons2[i].getDescription())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Sort given array according their reject reason id (in asending order).
     *
     * @param reasons the ExpenseEntryRejectReason array to sort.
     */
    public static void sortReasons(ExpenseEntryRejectReason[] reasons) {
        for (int i = 0; i < reasons.length; ++i) {
            for (int j = 0; j < i; ++j) {
                if (reasons[i].getRejectReasonId() < reasons[j].getRejectReasonId()) {
                    ExpenseEntryRejectReason r = reasons[i];
                    reasons[i] = reasons[j];
                    reasons[j] = r;
                }
            }
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
     * Execute the sql statement.
     * </p>
     *
     * @param sql sql statement;
     * @param connection the database connection used to access database.
     *
     * @throws SQLException if database error occurs.
     */
    public static void executeSql(String sql, Connection connection)
        throws SQLException {
        Statement statement = connection.createStatement();
        try {
            statement.executeUpdate(sql);
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

        configManager.add(new File("test_files/V1.1/Valid.xml").getAbsolutePath());
        configManager.add(new File("test_files/V1.1/Database.xml").getAbsolutePath());
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
}
