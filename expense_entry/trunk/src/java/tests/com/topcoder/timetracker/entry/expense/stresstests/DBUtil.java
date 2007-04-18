/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.util.config.ConfigManager;

/**
 * A helper class for stress test.
 *
 * <p>
 * It contains methods to insert record to some specified tables, and clear all tables and resources.
 * </p>
 *
 * @author Chenhong
 * @version 3.2
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
     * @throws Exception
     *             to junit.
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
     * @throws Exception
     *             to junit.
     */
    public static Connection getConnection() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        return factory.createConnection();
    }

    /**
     * Create an ExpenseStatus instance for testing.
     *
     * @param id
     *            the id for ExpenseStatus.
     * @return the ExpenseStatus instance.
     */
    static ExpenseStatus createExpenseStatus(long id) {
        ExpenseStatus status = new ExpenseStatus();
        status.setId(id);

        status.setCreationUser("user");
        status.setModificationUser("user");

        status.setDescription("des");

        return status;
    }

    /**
     * Create an ExpenseType instance.
     *
     * @param id
     *            the id for ExpenseType
     * @return the ExpenseType instance.
     */
    static ExpenseType createExpenseType(long id) {

        ExpenseType type = new ExpenseType();
        type.setId(id);

        type.setCompanyId(1);
        type.setCreationUser("user");
        type.setDescription("des");
        type.setModificationUser("user");

        return type;

    }

    /**
     * Insert a record into table company for testing.
     *
     * @param connection
     *            the database connection.
     * @param id
     *            the id for company
     *
     * @throws Exception
     *             to junit.
     */
    static void insertRecordInto_Company(Connection connection, long id) throws Exception {
        String query = "insert into company (company_id , name, passcode, creation_date,"
                + " creation_user, modification_date, modification_user)values  (?,?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, id);
            s.setString(2, "name" + id);
            s.setString(3, "passcode" + id);

            s.setDate(4, new Date(System.currentTimeMillis()));
            s.setString(5, "user");
            s.setDate(6, new Date(System.currentTimeMillis()));
            s.setString(7, "user");

            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to insert a record into table company cause by " + e.getMessage());
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table reject_reason.
     *
     * @param id
     *            the id of the reject_reason.
     * @throws Exception
     *             to junit.
     */
    static void insertRerordInto_reject_reason(Connection connection, long id) throws Exception {
        String query = "INSERT INTO reject_reason(reject_reason_id, description, creation_date, creation_user, "
                + "modification_date, modification_user, active) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, id);
            s.setString(2, "des" + id);

            s.setDate(3, new Date(System.currentTimeMillis()));
            s.setString(4, "user");
            s.setDate(5, new Date(System.currentTimeMillis()));
            s.setString(6, "user");
            s.setInt(7, 0);

            s.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to insert a record into table reject_reason cause by " + e.getMessage());
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table comp_rej_reason(
     *
     * @param connection
     *            the database connection
     * @param id
     *            the id
     * @throws Exception
     *             to junit.
     */
    static void insertRecordInto_comp_rej_reason(Connection connection, long id) throws Exception {
        String query = "insert into comp_rej_reason(company_id, reject_reason_id, "
                + "creation_date, creation_user, modification_date, modification_user) values (" + id
                + ", 1, current, 'a', current, 'a')";

        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to insert a record into comp_rej_reason cause by " + e.getMessage());
        } finally {
            closeStatement(s);
        }

    }

    /**
     * Insert a record into table invoice_status.
     *
     * @param connection
     *            the jdbc connection.
     * @param id
     *            the id
     *
     * @throws Exception
     *             to junit.
     */
    static void insertRecordInto_invoice_status(Connection connection, long id) throws Exception {
        String query = "insert into invoice_status (invoice_status_id, description, creation_date,"
                + " creation_user, modification_date, modification_user)values  (?,?,?,?,?,?);";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, id);
            s.setString(2, "des" + id);

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
     * Insert a record into table Invoice.
     *
     * @param conneciton
     *            the connection to database.
     * @param id
     *            the id for Invoice instance.
     * @throws Exception
     *             to junit.
     */
    static void insertRecordInto_invoice(Connection connection, long id) throws Exception {
        String query = "INSERT INTO invoice (invoice_id, project_id, creation_date, "
                + "creation_user, modification_date, modification_user, salestax, payment_terms_id, "
                + "invoice_number, po_number, invoice_date, due_date, paid, company_id, invoice_status_id) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.setLong(1, id);
            s.setLong(2, 1);
            s.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            s.setString(4, "user");
            s.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            s.setString(6, "modificationuser");

            s.setBigDecimal(7, new BigDecimal("11.5"));
            s.setLong(8, 1);
            s.setString(9, "invoice_number");
            s.setString(10, "po_number");
            s.setDate(11, new java.sql.Date(System.currentTimeMillis() + 10000));

            s.setDate(12, new java.sql.Date(System.currentTimeMillis() + 10000));
            s.setShort(13, (short) 1);
            s.setLong(14, 1);
            s.setLong(15, 1);

            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table Project.
     *
     * @param connection
     *            the connection to database.
     * @param id
     *            the id for project.
     * @throws Exception
     *             to junit.
     */
    static void insertRecordInto_Project(Connection connection, long id) throws Exception {
        String query = "insert into project(project_id, name, company_id, description, creation_date,"
                + " creation_user, modification_date, modification_user, start_date, end_date)" + " values (" + id
                + ", 'name', 1, 'description', current, 'a', current, 'a', current, current)";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Insert a record into table PaymentTerm.
     *
     * @param connection
     *            the connection to database.
     * @throws Exception
     *             to junit.
     */
    static void insertRecordInto_PaymentTerm(Connection connection) throws Exception {
        String query = "insert into payment_terms(payment_terms_id, description, creation_date, "
                + "creation_user, modification_date, modification_user, active, term) "
                + "values (1, 'description', current, 'a', current, 'a', 0, 0)";
        PreparedStatement s = connection.prepareStatement(query);

        try {
            s.executeUpdate();
        } finally {
            closeStatement(s);
        }
    }

    /**
     * Clear the specifiled table.
     *
     * @param name
     *            the table to be cleared.
     *
     * @throws Exception
     *             to junit.
     */
    static void clearTables() throws Exception {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("delete from exp_reject_reason");
            statement.executeUpdate("delete from expense_entry");
            statement.executeUpdate("delete from expense_status");
            statement.executeUpdate("delete from comp_exp_type");
            statement.executeUpdate("delete from expense_type");
            statement.executeUpdate("delete from invoice");
            statement.executeUpdate("delete from project");
            statement.executeUpdate("delete from payment_terms");
            statement.executeUpdate("delete from invoice_status");
            statement.executeUpdate("delete from comp_rej_reason");
            statement.executeUpdate("delete from reject_reason");
            statement.executeUpdate("delete from company");
        } catch (SQLException e) {
            System.out.println("Failed to clear the tables " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Close the sql statement
     *
     * @param statement
     *            the statement to be closed.
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
     * @param connection
     *            the connection to dd.
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