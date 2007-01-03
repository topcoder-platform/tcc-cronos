/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.accuracytests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * This is a helper class for Accuracy test.
 *
 * @author Chenhong
 * @version 1.0
 */
public class Util {
    /**
     * <p>
     * Represents the namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * The SQL query. Insert the company into the database.
     */
    private static final String INSERT_COMPANY = "INSERT INTO company(company_id, name, passcode, creation_date,"
            + " creation_user, modification_date, " + "modification_user) VALUES (?, ?, ?, ?, ? ,?, ?)";

    /**
     * Represents the Sql query to insert record into table state_name.
     */
    private static final String INSERT_STATE_NAME = "INSERT INTO state_name(state_name_id, name, abbreviation, "
            + "creation_date, creation_user,modification_date," + "modification_user) VALUES(?, ?, ?, ?,?,?,?)";

    /**
     * Represents the Sql query to insert record into table role
     */
    private static final String INSERT_ROLE = "INSERT INTO role(role_id, role_name) values(?,?)";

    /**
     * SQL query. Inserts the user account in the database.
     */
    private static final String INSERT_USER_ACCOUNT = "INSERT INTO user_account(user_account_id, company_id, "
            + "account_status_id, user_name, password, "
            + "creation_date, creation_user,  modification_date, modification_user) "
            + "VALUES (?, ?, ?, ?, ? ,?, ?, ?, ?)";

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private static DBConnectionFactory factory;

    /**
     * Represents the tables in the database layer.
     * FINAL REVIEWER (agh) FIX: added 'principal_role' otherwise it was impossible to clear 'role'
     * table because of existing reference.
     */
    private static final String[] tables = new String[] { "comp_reject_email", "reject_email", "comp_rej_reason",
            "company_contact", "user_contact", "contact", "user_address", "user_account", "company_address", "company",
            "address", "reject_reason", "state_name", "principal_role", "role" };

    /**
     * Private constructor.
     *
     */
    private Util() {
        // empty.
    }

    /**
     * This method clear all the namespaces used in the config manager instance.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public static void clearNamespace() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Returns a new connection to be used for persistence.
     * </p>
     *
     * @return the connection instance for database operation
     *
     * @throws Exception
     *             If unable to obtain a Connection
     */
    private static Connection getConnection() throws Exception {
        if (factory == null) {
            factory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
        }

        return factory.createConnection();
    }

    /**
     * <p>
     * Closes the given Connection instance.
     * </p>
     *
     * @param connection
     *            the given Connection instance to close.
     */
    private static void closeConnection(Connection connection) {
        try {
            if ((connection != null) && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given PreparedStatement instance.
     * </p>
     *
     * @param statement
     *            the given Statement instance to close.
     */
    private static void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Ignore
        }
    }

    /**
     * <p>
     * Deletes data from all the tables used by this component.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public static void clearTables() throws Exception {

        Connection connection = getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            for (int i = 0; i < tables.length; i++) {
                statement.executeUpdate("DELETE FROM " + tables[i]);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            // ignore
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * Insert one record into company table.
     *
     * @param id
     *            the company_id value.
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordsIntoCompanyTable(int id) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_COMPANY);
            statement.setLong(1, id);
            statement.setString(2, "topcoder");
            statement.setString(3, "pcode" + id);

            Timestamp time = new Timestamp(System.currentTimeMillis());
            statement.setTimestamp(4, time);

            statement.setString(5, "tc_reviewer");

            statement.setTimestamp(6, time);

            statement.setString(7, "tc_reviewer");

            statement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            // ignore.
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * Insert one record into state_name table.
     *
     * @param id
     *            the state_name_id value.
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordsIntoState_NameTable(int id) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_STATE_NAME);
            statement.setLong(1, id);
            statement.setString(2, "new york");
            statement.setString(3, "NY");

            Timestamp time = new Timestamp(System.currentTimeMillis());
            statement.setTimestamp(4, time);

            statement.setString(5, "tc_reviewer");

            statement.setTimestamp(6, time);

            statement.setString(7, "tc_reviewer");

            statement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            // ignore.
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * Insert record into table User_Account.
     *
     * @param id
     *            the User_Account_id.
     *
     * @param com_id
     *            the company id
     *
     * @param user
     *            the user
     *
     * @param psw
     *            the password
     *
     * @throws Exception
     *             to junit.
     */
    public static void insertIntoTable_User_Account(int id, int com_id, String user, String psw) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_USER_ACCOUNT);
            statement.setLong(1, id);
            statement.setLong(2, com_id);
            statement.setLong(3, 1);
            statement.setString(4, user);
            statement.setString(5, psw);

            Timestamp time = new Timestamp(System.currentTimeMillis());

            statement.setTimestamp(6, time);

            statement.setString(7, "tc_reviewer");

            statement.setTimestamp(8, time);

            statement.setString(9, "tc_reviewer");

            statement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            // ignore.
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }

    }

    /**
     * This method will add some roles into the role table for test.
     *
     * @param id
     *            the id of the role.
     * @param name
     *            the name of the role.
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordToTableRole(int id, String name) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_ROLE);
            statement.setLong(1, id);
            statement.setString(2, name);

            statement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            // ignore.
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }
}
