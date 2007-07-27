/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class utility for database operation.
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
     * Closes the connection.
     *
     * @param connection
     *            the given connection
     */
    static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // ignore
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
