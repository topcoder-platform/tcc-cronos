/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.stresstests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * A helper classs for database operation.
 *
 * @author Chenhong
 * @version 1.0
 */
final class DBUtil {
    /**
     * Private ctor.
     */
    private DBUtil() {
        // Empty.
    }

    /**
     * Create a jdbc connection.
     *
     * @return jdbc connection
     *
     * @throws Exception to junit
     */
    static Connection getConnection() throws Exception {
        // the informix database configuration file must be preloaded.
        DBConnectionFactory factory = new DBConnectionFactoryImpl("stresstests");

        return factory.createConnection();
    }

    /**
     * Close the connection.
     *
     * @param connection the connection to be closed.
     */
    static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore.
            }
        }
    }

    /**
     * Insert records into the database.
     *
     * @param sql the sql to be inserted.
     *
     * @throws Exception to junit
     */
    static void insertRecords(String[] sql) throws Exception {
        Connection connection = getConnection();

        try {
            Statement statement = connection.createStatement();

            for (int i = 0; i < sql.length; i++) {
                System.out.println(sql[i]);
                statement.executeUpdate(sql[i]);
            }
        } finally {
            // close the connection.
            closeConnection(connection);
        }
    }

    /**
     * Clear all the tables used in the testing.
     */
    static void clearTables() {
        String[] tables = new String[] {
                "member_answer", "competition_document", "document_version", "document_name", "document"
            };

        Connection connection = null;

        try {
            connection = getConnection();

            Statement statement = connection.createStatement();

            for (int i = 0; i < tables.length; i++) {
                statement.executeUpdate("delete from " + tables[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close the connection.
            closeConnection(connection);
        }
    }
}
