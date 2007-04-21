/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.stresstests;

import java.math.BigDecimal;
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
 * It contains methods to insert record to some specified tables, and clear all tables and resources.
 * </p>
 *
 * @author Chenhong
 * @version 1.0
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
            statement.executeUpdate("delete from invoice");
            statement.executeUpdate("delete from invoice_status");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
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