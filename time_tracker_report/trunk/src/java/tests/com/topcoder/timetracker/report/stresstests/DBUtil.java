/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.stresstests;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * A helper class for stress test.
 *
 * <p>
 * It contains methods to insert record to some specified tables, and clear all tables and
 * resources.
 * </p>
 *
 * @author Chenhong
 * @version 3.1
 */
final class DBUtil {

    /**
     * Private constructor.
     *
     */
    private DBUtil() {
        // Empty.
    }

    /**
     * Clear all the namespace in the config manager.
     *
     * @throws Exception to junit.
     */
    public static void clearNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Get a Database connection.
     *
     * @throws Exception to junit.
     */
    public static Connection getConnection() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        return factory.createConnection();
    }

    /**
     * Insert a record into table task_type
     *
     * @param connection the sql jdbc connection
     * @param id the id for task type.
     * @throws Exception to junit.
     */
    public static void insertRecordIntoTask_type(Connection connection, long id) throws Exception {
        String query =
                "insert into task_type (task_type_id, description, active, creation_date, creation_user, "
                        + "modification_date, modification_user) values (?,?, ?,?,?,?, ?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, id);
            s.setString(2, "des");
            s.setInt(3, 1);
            s.setDate(4, new Date(System.currentTimeMillis()));
            s.setString(5, "user");
            s.setDate(6, new Date(System.currentTimeMillis()));
            s.setString(7, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table time_status.
     *
     * @param connection the jdbc connection
     * @param id the id
     * @throws Exception to junit.
     */
    public static void insertRecordIntotime_status(Connection connection, long id) throws Exception {
        String query =
                "insert into time_status (time_status_id, description,  creation_date, creation_user, "
                        + "modification_date, modification_user) values (?,?, ?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, id);
            s.setString(2, "des");
            s.setDate(3, new Date(System.currentTimeMillis()));
            s.setString(4, "user");
            s.setDate(5, new Date(System.currentTimeMillis()));
            s.setString(6, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table project_worker.
     *
     * @param connection the db connection
     * @param project_id the project id
     * @param userAccount_id the user account id
     * @throws Exception to junit.
     */
    static void insertRecordInto_project_worker(Connection connection, long project_id, long userAccount_id)
        throws Exception {
        String query =
                "insert into project_worker (project_id, user_account_id, start_date, end_date, "
                        + "pay_rate, creation_date, creation_user, modification_date, modification_user) "
                        + "values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, project_id);
            s.setLong(2, userAccount_id);
            s.setDate(3, new Date(System.currentTimeMillis() - 10000));
            s.setDate(4, new Date(System.currentTimeMillis() + 10000));
            s.setDouble(5, 1.2);

            s.setDate(6, new Date(System.currentTimeMillis()));
            s.setString(7, "user");
            s.setDate(8, new Date(System.currentTimeMillis()));
            s.setString(9, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert record into table Project
     *
     * @param connection the database connection
     * @param project_id the project id
     * @param company_id the company id
     * @throws Exception to junit.
     */
    static void inserRecordIntoProject(Connection connection, long project_id, long company_id)
        throws Exception {
        String query =
                "insert into project (project_id, name, company_id, description, start_date, "
                        + "end_date, creation_date, creation_user, modification_date, modification_user) "
                        + "values (?,?,?,?, ?,?,?,?,?,? );";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, project_id);
            s.setString(2, "name");
            s.setLong(3, company_id);
            s.setString(4, "des");

            s.setDate(5, new Date(System.currentTimeMillis() - 10000));
            s.setDate(6, new Date(System.currentTimeMillis() + 10000));

            s.setDate(7, new Date(System.currentTimeMillis()));
            s.setString(8, "user");
            s.setDate(9, new Date(System.currentTimeMillis()));
            s.setString(10, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert record into table client.
     *
     * @param connection the jdbc connection
     * @param client_id the client id
     * @param company_id the company id
     * @throws Exception to junit.
     */
    static void insertRecordIntoClient(Connection connection, long client_id, long company_id)
        throws Exception {
        String query =
                "insert into client (client_id, name, company_id, creation_date, creation_user, "
                        + "modification_date, modification_user)  values (?,?,?,?,?,?,?)";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, client_id);
            s.setString(2, "name" + client_id);
            s.setLong(3, company_id);

            s.setDate(4, new Date(System.currentTimeMillis()));
            s.setString(5, "user" + client_id);
            s.setDate(6, new Date(System.currentTimeMillis()));
            s.setString(7, "user" + client_id);

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record to Client_Project table.
     *
     * @param connection the jdbc connection
     * @param client_id the client id
     * @param project_id the project id
     * @throws Exception to junit.
     */
    static void insertRerordInto_client_project(Connection connection, long client_id, long project_id)
        throws Exception {
        String query =
                "insert into client_project (client_id, project_id, creation_date, "
                        + "creation_user, modification_date, modification_user) "
                        + "values (?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, client_id);
            s.setLong(2, project_id);

            s.setDate(3, new Date(System.currentTimeMillis()));
            s.setString(4, "user");
            s.setDate(5, new Date(System.currentTimeMillis()));
            s.setString(6, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into User_Account table.
     *
     * @param connection the jdbc connection
     * @param account_id the account id
     * @param company_id the company id
     * @param status_id the status id
     * @throws Exception to junit.
     */
    static void insertRecordInto_UserAccount(Connection connection, long account_id, long company_id,
            long status_id) throws Exception {
        String query =
                "insert into user_account (user_account_id, company_id, account_status_id, "
                        + "user_name, password, creation_date, creation_user, modification_date, modification_user) "
                        + "values (?,?,?,?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, account_id);
            s.setLong(2, company_id);
            s.setLong(3, status_id);
            s.setString(4, "user");
            s.setString(5, "password");

            s.setDate(6, new Date(System.currentTimeMillis()));
            s.setString(7, "user");
            s.setDate(8, new Date(System.currentTimeMillis()));
            s.setString(9, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table Project_fix_bill.
     *
     * @param connection the jdbc connection
     * @param fix_bill_entry_id the fix_bill_entry id
     * @param project_id the project id
     * @throws Exception to junit.
     */
    static void insertRecordInto_project_fix_bill(Connection connection, long fix_bill_entry_id,
            long project_id) throws Exception {
        String query =
                "insert into project_fix_bill (fix_bill_entry_id, project_id, creation_date, creation_user, "
                        + "modification_date, modification_user) values (?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, fix_bill_entry_id);
            s.setLong(2, project_id);

            s.setDate(3, new Date(System.currentTimeMillis()));
            s.setString(4, "user");
            s.setDate(5, new Date(System.currentTimeMillis()));
            s.setString(6, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert record into Fix_bill_entry table.
     *
     * @param connection the jdbc connection
     * @param fix_bill_entry_id the id for fix_bill_entry
     * @param company_id the company id
     * @throws Exception to junit.
     */
    static void insertRecordInto_fix_bill_entry(Connection connection, long fix_bill_entry_id, long company_id)
        throws Exception {

        String query =
                "insert into fix_bill_entry (fix_bill_entry_id, company_id, invoice_id, "
                        + "fix_bill_type_id, fix_bill_status_id, description, entry_date, amount, creation_date, "
                        + "creation_user, modification_date, modification_user) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        long id = fix_bill_entry_id;
        try {
            s.setLong(1, fix_bill_entry_id);
            s.setLong(2, company_id);
            s.setLong(3, id);
            s.setLong(4, id);
            s.setLong(5, id);
            s.setString(6, "des");
            s.setDate(7, new Date(System.currentTimeMillis()));
            s.setDouble(8, 1.5);

            s.setDate(9, new Date(System.currentTimeMillis()));
            s.setString(10, "user");
            s.setDate(11, new Date(System.currentTimeMillis()));
            s.setString(12, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table fix_bill_status.
     *
     * @param connection the jdbc connection
     * @param id the id for fix_bill status
     * @throws Exception to junit.
     */
    static void insertRecordInto_fix_bill_status(Connection connection, long id) throws Exception {
        String query =
                "insert into fix_bill_status (fix_bill_status_id, description, creation_date, creation_user,"
                        + " modification_date, modification_user) values (?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, id);
            s.setString(2, "des");

            s.setDate(3, new Date(System.currentTimeMillis()));
            s.setString(4, "user");
            s.setDate(5, new Date(System.currentTimeMillis()));
            s.setString(6, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }

    }

    /**
     * Insert a record into table fix_bill_type.
     *
     * @param connection the jdbc connection
     * @param id the id for fix_bill type
     * @throws Exception to junit.
     */
    static void insertRecord_fix_bill_type(Connection connection, long id) throws Exception {
        String query =
                "insert into fix_bill_type (fix_bill_type_id, description, active, creation_date, "
                        + "creation_user, modification_date, modification_user) values (?,?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, id);
            s.setString(2, "des");
            s.setInt(3, 1);

            s.setDate(4, new Date(System.currentTimeMillis()));
            s.setString(5, "user");
            s.setDate(6, new Date(System.currentTimeMillis()));
            s.setString(7, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table Project_Expense.
     *
     * @param connection the jdbc connection
     * @param expense_entry_id the id for expense entry
     * @param project_id the project id
     * @throws Exception to junit.
     */
    static void insertRecord_project_expense(Connection connection, long expense_entry_id, long project_id)
        throws Exception {
        String query =
                "insert into project_expense (expense_entry_id, project_id, "
                        + "creation_date, creation_user, modification_date, modification_user) values  (?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, expense_entry_id);
            s.setLong(2, project_id);

            s.setDate(3, new Date(System.currentTimeMillis()));
            s.setString(4, "user");
            s.setDate(5, new Date(System.currentTimeMillis()));
            s.setString(6, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert record into table Expense_Entry
     *
     * @param connection the jdbc connection
     * @param expense_entry_id the id for expense entry
     * @param company_id the company id
     * @throws Exception to junit
     */
    static void insertRerordIntoexpense_entry(Connection connection, long expense_entry_id, long company_id)
        throws Exception {

        String query =
                "insert into expense_entry (expense_entry_id, company_id, invoice_id, expense_type_id, "
                        + "expense_status_id, description, entry_date, amount, billable, mileage, "
                        + "creation_date, creation_user, modification_date, modification_user) values "
                        + " (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        PreparedStatement s = connection.prepareStatement(query);

        long id = expense_entry_id;
        try {
            s.setLong(1, expense_entry_id);
            s.setLong(2, company_id);
            s.setLong(3, id);
            s.setLong(4, id);
            s.setLong(5, id);
            s.setString(6, "des");
            s.setDate(7, new Date(System.currentTimeMillis()));
            s.setDouble(8, 1.5);
            s.setInt(9, 1);
            s.setInt(10, 1);

            s.setDate(11, new Date(System.currentTimeMillis()));
            s.setString(12, "user");
            s.setDate(13, new Date(System.currentTimeMillis()));
            s.setString(14, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table expense_status
     *
     * @param connection the jdbc connection
     * @param id the id for expense_status
     * @throws Exception to junit.
     */
    static void insertRecordInto_expense_status(Connection connection, long id) throws Exception {
        String query =
                "insert into expense_status (expense_status_id, description, "
                        + "creation_date, creation_user, modification_date, modification_user) values (?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, id);
            s.setString(2, "des");

            s.setDate(3, new Date(System.currentTimeMillis()));
            s.setString(4, "user");
            s.setDate(5, new Date(System.currentTimeMillis()));
            s.setString(6, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into expense_type table.
     *
     * @param connection the jdbc connection
     * @param id the id for expense type
     * @throws Exception to junit
     */
    static void insertRecordInto_expense_type(Connection connection, long id) throws Exception {
        String query =
                "insert into expense_type (expense_type_id, description, active,"
                        + " creation_date, creation_user, modification_date, modification_user) values  (?,?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, id);
            s.setString(2, "des");
            s.setInt(3, 1);

            s.setDate(4, new Date(System.currentTimeMillis()));
            s.setString(5, "user");
            s.setDate(6, new Date(System.currentTimeMillis()));
            s.setString(7, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert record into table Project_Time
     *
     * @param connection the jdbc connection
     * @param time_entry_id the time entry id
     * @param project_id the project id
     * @throws Exception to junit.
     */
    static void insertRecordInto_project_time(Connection connection, long time_entry_id, long project_id)
        throws Exception {
        String query =
                "insert into project_time (time_entry_id, project_id, creation_date,"
                        + " creation_user, modification_date, modification_user)values  (?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, time_entry_id);
            s.setLong(2, project_id);

            s.setDate(3, new Date(System.currentTimeMillis()));
            s.setString(4, "user");
            s.setDate(5, new Date(System.currentTimeMillis()));
            s.setString(6, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table time_entry
     *
     * @param connection the jdbc connection
     * @param time_entry_id the id for time_entry
     * @param company_id the company id
     * @throws Exception to junit.
     */
    static void insertRecordInto_time_entry(Connection connection, long time_entry_id, long company_id)
        throws Exception {
        String query =
                "insert into time_entry (time_entry_id, company_id, invoice_id, task_type_id, time_status_id, "
                        + "description, entry_date, hours, billable, creation_date, creation_user,"
                        + " modification_date, modification_user) values "
                        + " (?,?,?,?,?,?,?,?,?,?,?,?,?);";

        PreparedStatement s = connection.prepareStatement(query);

        long id = time_entry_id;
        try {
            s.setLong(1, time_entry_id);
            s.setLong(2, company_id);
            s.setLong(3, id);
            s.setLong(4, id);
            s.setLong(5, id);
            s.setString(6, "des");
            s.setDate(7, new Date(System.currentTimeMillis()));
            s.setFloat(8, (float) 13.4);
            s.setInt(9, 1);

            s.setDate(10, new Date(System.currentTimeMillis()));
            s.setString(11, "user");
            s.setDate(12, new Date(System.currentTimeMillis()));
            s.setString(13, "user");

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Clear the tables in the database.
     *
     * @throws Exception to junit.
     */
    static void clearTables() throws Exception {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();

        try {
            statement.addBatch("delete from client");
            statement.addBatch("delete from client_project");
            statement.addBatch("delete from project");
            statement.addBatch("delete from user_account");
            statement.addBatch("delete from project_worker");

            statement.addBatch("delete from project_fix_bill");
            statement.addBatch("delete from fix_bill_entry");
            statement.addBatch("delete from fix_bill_status");
            statement.addBatch("delete from fix_bill_type");

            statement.addBatch("delete from project_expense");
            statement.addBatch("delete from expense_entry");
            statement.addBatch("delete from expense_status");
            statement.addBatch("delete from expense_type");

            statement.addBatch("delete from project_time");
            statement.addBatch("delete from time_entry");
            statement.addBatch("delete from time_status");
            statement.addBatch("delete from task_type");

            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println("Failed to clear the tables " + e.getMessage());
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * Close the sql statement
     *
     * @param statement the statement to be closed.
     */
    static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Error occure while closing Statement " + e.getMessage());
            }
        }
    }

    /**
     * Close the jdbc conenction
     *
     * @param connection the connection to dd.
     */
    static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close the connection " + e.getMessage());
            }
        }
    }
}