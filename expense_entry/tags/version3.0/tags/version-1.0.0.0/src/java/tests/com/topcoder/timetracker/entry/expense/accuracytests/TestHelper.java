/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;

import com.topcoder.util.config.ConfigManager;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Calendar;
import java.util.Date;


/**
 * <p>
 * Helper class provides some useful helper methods. Such as create a customer defined connection, insert/select/delete
 * records in the datebase.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
class TestHelper {
    /** Represents the namespace to load manager configuration. */
    static final String NAMESPACE = "com.topcoder.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @param namespace the namespace to load the DBConnectionFactory configuration
     *
     * @return the connection created from DBConnectionFactory
     *
     * @throws Exception any exception during the create connection process
     */
    static Connection getConnection(String namespace) throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        return factory.createConnection();
    }

    /**
     * Deletes all records from the tables.
     *
     * @param conn the connection instance to connect the database
     *
     * @throws SQLException if any SQL error occurs.
     */
    static void clearRecords(Connection conn) throws SQLException {
        Statement statement = null;

        try {
            statement = conn.createStatement();

            statement.executeUpdate("DELETE FROM ExpenseEntries;");
            statement.executeUpdate("DELETE FROM ExpenseTypes;");
            statement.executeUpdate("DELETE FROM ExpenseStatuses;");
        } finally {
            statement.close();
        }
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
    static void closeResources(ResultSet resultSet, Statement statement, Connection connection)
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
     * clear the config.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    static void clearConfig() throws Exception {
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
     * add the config.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    static void addConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }

        if (configManager.existsNamespace(DB_NAMESPACE)) {
            configManager.removeNamespace(DB_NAMESPACE);
        }

        configManager.add(NAMESPACE, "accuracytests/accuracytests.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
        configManager.add(DB_NAMESPACE, "Database.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
    }

    /**
     * Insert an expense type for testing
     *
     * @param connection the connection
     *
     * @throws SQLException if any SQL error occurs.
     */
    static void insertExpenseType(Connection connection)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement("INSERT INTO ExpenseTypes(ExpenseTypesID, Description, " +
                    "CreationUser, CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

            pstmt.setInt(1, 1);
            pstmt.setString(2, "test expense type");
            pstmt.setString(3, "PE");
            pstmt.setDate(4, new java.sql.Date(createDate(2001, 1, 1, 1, 1, 1).getTime()));
            pstmt.setString(5, "PE");
            pstmt.setDate(6, new java.sql.Date(createDate(2001, 2, 1, 1, 1, 1).getTime()));
            pstmt.executeUpdate();
        } finally {
            pstmt.close();
        }
    }

    /**
     * Insert an expense entry for testing
     *
     * @param connection the connection
     *
     * @throws SQLException if any SQL error occurs.
     */
    static void insertExpenseEntry(Connection connection)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement("INSERT INTO ExpenseEntries (ExpenseEntriesID, ExpenseTypesID, " +
                    "ExpenseStatusesID, Description, EntryDate, Amount, Billable, CreationDate, CreationUser, ModificationDate, " +
                    "ModificationUser) VALUES (?,?,?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, 1);
            pstmt.setInt(2, 1);
            pstmt.setInt(3, 1);
            pstmt.setString(4, "test expense entry");
            pstmt.setDate(5, new java.sql.Date(createDate(2005, 1, 1, 1, 1, 1).getTime()));
            pstmt.setBigDecimal(6, new BigDecimal(3.14));
            pstmt.setBoolean(7, true);
            pstmt.setDate(8, new java.sql.Date(createDate(2001, 1, 1, 1, 1, 1).getTime()));
            pstmt.setString(9, "PE");
            pstmt.setDate(10, new java.sql.Date(createDate(2001, 2, 1, 1, 1, 1).getTime()));
            pstmt.setString(11, "PE");
            pstmt.executeUpdate();
        } finally {
            pstmt.close();
        }
    }

    /**
     * Insert an expense status for testing
     *
     * @param connection the connection
     *
     * @throws SQLException if any SQL error occurs.
     */
    static void insertExpenseStatus(Connection connection)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(
                    "INSERT INTO ExpenseStatuses(ExpenseStatusesID, Description, CreationUser, " +
                    "CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

            pstmt.setInt(1, 1);
            pstmt.setString(2, "test expense status");
            pstmt.setString(3, "PE");
            pstmt.setDate(4, new java.sql.Date(createDate(2001, 1, 1, 1, 1, 1).getTime()));
            pstmt.setString(5, "PE");
            pstmt.setDate(6, new java.sql.Date(createDate(2001, 2, 1, 1, 1, 1).getTime()));
            pstmt.executeUpdate();
        } finally {
            pstmt.close();
        }
    }

    /**
     * create an ExpenseEntryType instance for testing.
     *
     * @return an ExpenseEntryType instance for testing.
     */
    static ExpenseEntryType createExpenseType() {
        ExpenseEntryType type = new ExpenseEntryType(1);

        type.setDescription("test expense type");

        //type.setCreationDate(createDate(2001, 1, 1, 1, 1, 1));
        //type.setModificationDate(createDate(2001, 2, 1, 1, 1, 1));
        type.setCreationUser("PE");
        type.setModificationUser("PE");

        return type;
    }

    /**
     * create an ExpenseEntryStatus instance for testing.
     *
     * @return an ExpenseEntryStatus instance for testing.
     */
    static ExpenseEntryStatus createExpenseStatus() {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);

        status.setDescription("test expense status");

        //status.setCreationDate(createDate(2001, 1, 1, 1, 1, 1));
        //status.setModificationDate(createDate(2001, 2, 1, 1, 1, 1));
        status.setCreationUser("PE");
        status.setModificationUser("PE");

        return status;
    }

    /**
     * create an ExpenseEntry instance for testing.
     *
     * @return an ExpenseEntry instance for testing.
     */
    static ExpenseEntry createExpenseEntry() {
        ExpenseEntry entry = new ExpenseEntry(1);

        //entry.setCreationDate(createDate(2001, 1, 1, 1, 1, 1));
        //entry.setModificationDate(createDate(2001, 2, 1, 1, 1, 1));
        entry.setDescription("test expense entry");
        entry.setCreationUser("PE");
        entry.setModificationUser("PE");
        entry.setAmount(new BigDecimal(3.14));
        entry.setBillable(true);
        entry.setDate(createDate(2005, 1, 1, 1, 1, 1));
        entry.setExpenseType(createExpenseType());
        entry.setStatus(createExpenseStatus());

        return entry;
    }

    /**
     * whether the two types equals.
     *
     * @param type1 the first type
     * @param type2 the second type
     *
     * @return whether the two types equals.
     */
    static boolean isExpenseTypeEqual(ExpenseEntryType type1, ExpenseEntryType type2) {
        if (!type1.getCreationUser().equals(type2.getCreationUser())) {
            return false;
        }

        if (!type1.getDescription().equals(type2.getDescription())) {
            return false;
        }

        if (!type1.getModificationUser().equals(type2.getModificationUser())) {
            return false;
        }

        if (type1.getId() != type2.getId()) {
            return false;
        }

        return true;
    }

    /**
     * whether the two status equals.
     *
     * @param status1 the first status
     * @param status2 the second status
     *
     * @return whether the two status equals.
     */
    static boolean isExpenseStatusEqual(ExpenseEntryStatus status1, ExpenseEntryStatus status2) {
        if (!status1.getCreationUser().equals(status2.getCreationUser())) {
            return false;
        }

        if (!status1.getDescription().equals(status2.getDescription())) {
            return false;
        }

        if (!status1.getModificationUser().equals(status2.getModificationUser())) {
            return false;
        }

        if (status1.getId() != status2.getId()) {
            return false;
        }

        return true;
    }

    /**
     * whether the two entry equals.
     *
     * @param entry1 the first entry
     * @param entry2 the second entry
     *
     * @return whether the two entry equals.
     */
    static boolean isExpenseEntryEqual(ExpenseEntry entry1, ExpenseEntry entry2) {
        if (entry1.getAmount().subtract(entry2.getAmount()).abs().doubleValue() > 1e-8) {
            return false;
        }

        if (!entry1.getCreationUser().equals(entry2.getCreationUser())) {
            return false;
        }

        if (convertDate(entry1.getDate()) != convertDate(entry2.getDate())) {
            return false;
        }

        if (!entry1.getDescription().equals(entry2.getDescription())) {
            return false;
        }

        if (!entry1.getModificationUser().equals(entry2.getModificationUser())) {
            return false;
        }

        if (entry1.getId() != entry2.getId()) {
            return false;
        }

        if (entry1.isBillable() != entry2.isBillable()) {
            return false;
        }

        if (!isExpenseTypeEqual(entry1.getExpenseType(), entry2.getExpenseType())) {
            return false;
        }

        if (!isExpenseStatusEqual(entry1.getStatus(), entry2.getStatus())) {
            return false;
        }

        return true;
    }

    /**
     * <p>
     * Creates a Date instance representing the given year, month and date. The time would be 0:0:0 0:0:0.
     * </p>
     *
     * @param year the year in the instance.
     * @param month the month in the instance.
     * @param date the date in the instance.
     * @param hour the hour in the instance.
     * @param minute the minute in the instance.
     * @param second the second in the instance.
     *
     * @return a Date instance representing the year, month, date, hour, minute and second.
     */
    static Date createDate(int year, int month, int date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, date, hour, minute, second);

        return calendar.getTime();
    }

    /**
     * convert the input Date instance to a long value. Use second accuracy.
     *
     * @param date the Date instance needed to convert
     *
     * @return the converted value
     */
    static long convertDate(Date date) {
        long time = date.getTime();

        return ((long) (time / 1000.0)) * 1000;
    }
}
