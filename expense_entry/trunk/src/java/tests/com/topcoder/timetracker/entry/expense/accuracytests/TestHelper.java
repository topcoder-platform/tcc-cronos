/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.BasicInfo;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryRejectReason;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import junit.framework.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author visualage
 * @author kr00tki
 * @version 2.0
 *
 * @since 1.0
 */
public final class TestHelper {
    /**
     * The test id of the company.
     */
    public static final int COMPANY_ID = 10;

    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /**
     * <p>
     * Creates a new instance of <code>TestHelper</code> class. The private constructor prevents the creation of
     * a new instance.
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
        Assert.assertEquals("The description should be correct.", expected.getDescription(), actual
                .getDescription());
        assertEquals("The creation date should be correct.", expected.getCreationDate(), actual.getCreationDate());
        assertEquals("The modification date should be correct.", expected.getModificationDate(), actual
                .getModificationDate());
        Assert.assertEquals("The creation user should be correct.", expected.getCreationUser(), actual
                .getCreationUser());
        Assert.assertEquals("The modification user should be correct.", expected.getModificationUser(), actual
                .getModificationUser());
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
        Assert.assertEquals("The amount of money should be correct.", expected.getAmount().doubleValue(), actual
                .getAmount().doubleValue(), 1E-9);
        Assert.assertEquals("Incorrect company id.", expected.getCompanyId(), actual.getCompanyId());
        Assert.assertTrue("The reject reasons are not equal.", compareIntArray(expected.getRejectReasonIds(),
                actual.getRejectReasonIds()));
    }

    /**
     * <p>
     * Asserts that two given dates should be the same. They are considered the same if and only if the year,
     * month, date, hours, minutes and seconds are the same.
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
        Assert.assertEquals(message, expectedCalendar.get(Calendar.DAY_OF_MONTH), actualCalendar
                .get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.HOUR_OF_DAY), actualCalendar
                .get(Calendar.HOUR_OF_DAY));
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
    public static void clearDatabase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("DELETE FROM comp_exp_type;");
            statement.executeUpdate("DELETE FROM comp_rej_reason;");
            statement.executeUpdate("DELETE FROM exp_reject_reason;");
            statement.executeUpdate("DELETE FROM expense_entry;");
            statement.executeUpdate("DELETE FROM expense_status;");
            statement.executeUpdate("DELETE FROM expense_type;");
            statement.executeUpdate("DELETE FROM reject_reason;");
            statement.executeUpdate("DELETE FROM company;");
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

        configManager.add("accuracytests/Valid.xml");
        configManager.add("accuracytests/Database.xml");
    }

    /**
     * Initialize the database for testing. It adds some data to database.
     *
     * @throws Exception to JUnit
     */
    public static void initDatabase() throws Exception {
        Connection connection = getConnection();
        clearDatabase(connection);

        try {
            Statement stmt = connection.createStatement();
            stmt.execute("insert into company values(10, 'a', 'a', current, 'a', current, 'a')");
            stmt.execute("insert into company values(20, 'a', 'a1', current, 'a', current, 'a')");
            stmt.execute("insert into company values(30, 'a', 'a2', current, 'a', current, 'a')");
            // Insert an expense type
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO expense_type(expense_type_id, Description, "
                            + "active, creation_user, creation_date, modification_user, modification_date) VALUES (?,?,?,?,?,?,?)");

            try {
                ps.setInt(1, 1);
                ps.setString(2, "Travel Expense");
                ps.setInt(3, 1);
                ps.setString(4, "TangentZ");
                ps.setDate(5, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                ps.setString(6, "Ivern");
                ps.setDate(7, new java.sql.Date(TestHelper.createDate(2005, 2, 1).getTime()));
                ps.executeUpdate();
            } finally {
                ps.close();
            }

            // add company - expense type mapping
            ps = connection.prepareStatement("INSERT INTO comp_exp_type(company_id, expense_type_id, "
                    + "creation_date, creation_user, modification_date, modification_user) "
                    + "VALUES (?,?,?,?,?,?)");

            try {
                ps.setInt(1, COMPANY_ID);
                ps.setInt(2, 1);
                ps.setDate(3, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                ps.setString(4, "TangentZ");
                ps.setDate(5, new java.sql.Date(TestHelper.createDate(2005, 2, 1).getTime()));
                ps.setString(6, "Ivern");

                ps.executeUpdate();
            } finally {
                ps.close();
            }

            // Insert an expense status
            ps = connection
                    .prepareStatement("INSERT INTO expense_status(expense_status_id, Description, creation_user, "
                            + "creation_date, modification_user, modification_date) VALUES (?,?,?,?,?,?)");

            try {
                ps.setInt(1, 2);
                ps.setString(2, "Pending Approval");
                ps.setString(3, "TangentZ");
                ps.setDate(4, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                ps.setString(5, "Ivern");
                ps.setDate(6, new java.sql.Date(TestHelper.createDate(2005, 2, 1).getTime()));
                ps.executeUpdate();
            } finally {
                ps.close();
            }

            // Insert three reject reasons whose id is 0, 1, 2.
            ps = connection.prepareStatement("INSERT INTO reject_reason(reject_reason_id, Description, "
                    + "creation_date, creation_user, modification_date, modification_user, active) "
                    + "VALUES (?,?,?,?,?,?,?)");

            try {
                ps.setString(2, "reject reason1");
                ps.setDate(3, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                ps.setString(4, "Creator");
                ps.setDate(5, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                ps.setString(6, "Modifier");
                ps.setInt(7, 1);

                for (int i = 0; i < 3; i++) {
                    ps.setInt(1, i);
                    ps.executeUpdate();
                }
            } finally {
                ps.close();
            }

            // add company - reject reason mapping
            ps = connection.prepareStatement("INSERT INTO comp_rej_reason(company_id, reject_reason_id,"
                    + "creation_date, creation_user, modification_date, modification_user) "
                    + "VALUES (?, ?, CURRENT, 'system', CURRENT, 'system')");
            try {
                for (int i = 0; i < 3; i++) {
                    ps.setInt(1, COMPANY_ID);
                    ps.setInt(2, i);
                    ps.executeUpdate();
                }
            } finally {
                ps.close();
            }
        } finally {
            connection.close();
        }
    }

    /**
     * Create an ExpenseEntryStatus instance according data in database which has been initialized in
     * initDatabase() method.
     *
     * @return an ExpenseEntryStatus instance
     */
    public static ExpenseEntryStatus createStatusInstance() {
        // Create the expense status
        ExpenseEntryStatus status = new ExpenseEntryStatus(2);

        status.setDescription("Pending Approval");
        status.setCreationDate(TestHelper.createDate(2005, 1, 1));
        status.setModificationDate(TestHelper.createDate(2005, 2, 1));
        status.setCreationUser("TangentZ");
        status.setModificationUser("Ivern");

        return status;
    }

    /**
     * Create an ExpenseEntryType instance according data in database which has been initialized in initDatabase()
     * method.
     *
     * @return an ExpenseEntryType instance
     */
    public static ExpenseEntryType createTypeInstance() {
        // Create the expense type
        ExpenseEntryType type = new ExpenseEntryType(1);

        type.setDescription("Travel Expense");
        type.setCreationDate(TestHelper.createDate(2005, 1, 1));
        type.setModificationDate(TestHelper.createDate(2005, 2, 1));
        type.setCreationUser("TangentZ");
        type.setModificationUser("Ivern");
        // since 2.0
        type.setCompanyId(COMPANY_ID);

        return type;
    }

    /**
     * Create a connection and return it.
     *
     * @return a connection
     *
     * @throws Exception to JUnit
     */
    public static Connection getConnection() throws Exception {
        return new DBConnectionFactoryImpl(DB_NAMESPACE).createConnection();
    }

    /**
     * Compares the two array of ExpenseEntryRejectReason instances to judge whether they consist of the same
     * elements disregarding the order of their appearing.
     *
     * @param array1 the first array to compare
     * @param array2 the second array to compare
     *
     * @return true if they consist of the same elements disregarding the order of their appearing.
     */
    public static boolean compareRejectReasonsArray(ExpenseEntryRejectReason[] array1,
            ExpenseEntryRejectReason[] array2) {
        ExpenseEntryRejectReason[] array1Copy = (ExpenseEntryRejectReason[]) array1.clone();
        ExpenseEntryRejectReason[] array2Copy = (ExpenseEntryRejectReason[]) array2.clone();
        sortRejectReasons(array1Copy);
        sortRejectReasons(array2Copy);

        return Arrays.equals(array1Copy, array2Copy);
    }

    /**
     * Sort rejectReasons array into ascending reject reason id numerical order.
     *
     * @param array1 the ExpenseEntryRejectReason array to sort
     */
    private static void sortRejectReasons(ExpenseEntryRejectReason[] array1) {
        Arrays.sort(array1, new Comparator() {
            /**
             * Compare the id of the two objects instance of ExpenseEntryRejectReason.
             *
             * @param obj1 the object instance of ExpenseEntryRejectReason to compare
             * @param obj2 the object instance of ExpenseEntryRejectReason to compare
             *
             * @return 1 if the id of obj1 is bigger than the one of obj2; -1 if smaller; 0 if equals.
             */
            public int compare(Object obj1, Object obj2) {
                int id1 = ((ExpenseEntryRejectReason) obj1).getRejectReasonId();
                int id2 = ((ExpenseEntryRejectReason) obj2).getRejectReasonId();

                return (id1 < id2) ? (-1) : ((id1 > id2) ? 1 : 0);
            }
        });
    }

    /**
     * Compares the two array of int instances to judge whether they consist of the same int value disregarding the
     * order of their appearing.
     *
     * @param array1 the first array to compare
     * @param array2 the second array to compare
     *
     * @return true if they consist of the same int value disregarding the order of their appearing.
     */
    public static boolean compareIntArray(int[] array1, int[] array2) {
        int[] array1Copy = (int[]) array1.clone();
        int[] array2Copy = (int[]) array2.clone();
        Arrays.sort(array1Copy);
        Arrays.sort(array2Copy);

        return Arrays.equals(array1Copy, array2Copy);
    }
}
