/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.informix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;

/**
 * Database utility class.
 *
 * @author enefem21
 * @version 1.0
 */
final class DBUtil {

    /**
     * Private constructor to avoid an instantiation of this utility class.
     */
    private DBUtil() {
        // nothing to do
    }

    /**
     * Retrieves connection from <code>DBConnectionFactory</code>.
     *
     * @param connectionFactory
     *            the given <code>DBConnectionFactory</code> from where the connection will be created
     *
     * @return the connection
     *
     * @throws InvoiceDataAccessException
     *             if the connection creation is failed
     */
    static Connection getConnection(DBConnectionFactory connectionFactory) throws InvoiceDataAccessException {
        try {
            return connectionFactory.createConnection();
        } catch (DBConnectionException e) {
            throw new InvoiceDataAccessException("Error creating connection", e);
        }
    }

    /**
     * Closes the connection.
     *
     * @param connection
     *            the given connection
     */
    static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes the statement.
     *
     * @param statement
     *            the given statement
     */
    static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Executes query and get the result set.
     *
     * @param statement
     *            the given statement where the query will be sent to
     * @param sqlQuery
     *            the given SQL query to be executed
     *
     * @return the result set
     *
     * @throws InvoiceDataAccessException
     *             if there is some database related error
     */
    static ResultSet executeQuery(Statement statement, String sqlQuery) throws InvoiceDataAccessException {
        try {
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new InvoiceDataAccessException("There is some database related error", e);
        }
    }

    /**
     * Executes data modification query.
     *
     * @param statement
     *            the given statement where the query will be sent to
     * @param params
     *            the given parameters
     * @param expectedAffected
     *            the number of expected number of rows affected by the query
     *
     * @throws InvoiceDataAccessException
     *             if there is some database related error
     */
    static void execute(PreparedStatement statement, Object[] params, int expectedAffected)
        throws InvoiceDataAccessException {
        try {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Date) {
                    Date date = (Date) params[i];
                    java.sql.Date dateSql = new java.sql.Date(date.getTime());
                    statement.setDate(i + 1, dateSql);
                } else {
                    statement.setObject(i + 1, params[i]);
                }
            }
            statement.execute();

            int updateCount = statement.getUpdateCount();
            if (expectedAffected != updateCount) {
                throw new InvoiceDataAccessException("The number of updated rows is not as expected");
            }

        } catch (SQLException e) {
            throw new InvoiceDataAccessException("There is some database related error when executing", e);
        }
    }

    /**
     * Closes the result set.
     *
     * @param resultSet
     *            the given result set
     */
    static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
