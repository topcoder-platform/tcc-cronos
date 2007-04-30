/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * The ConfigHelper is used to setup configuration.
 *
 * @author brain_cn
 * @version 1.0
 */
class ConfigHelper {
    /** Represents the configuration file. */
    private static final String CONFIG_FILE = "accuracytests/config.xml";

    /** Represents the db connection namespace. */
    private static final String DB_CONNECTION_FACTORY_NAMESPACE =
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** Represents the all namespaces. */
    private static final String[] NAMESPACES =
            new String[] {"com.topcoder.timetracker.report.ReportDaoFactory",
                "com.topcoder.timetracker.report.InformixReportDAO", "AllDao_ObjectFactoryNS",
                "informixSearchBundleManager", "com.topcoder.search.builder.strategy.factory",
                "com.topcoder.search.builder.validator.factory",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy",
                "com.topcoder.search.builder.database.factory", DB_CONNECTION_FACTORY_NAMESPACE };

    /**
     * <p>
     * Represents the connection factory that is used for performing database operations.
     * </p>
     */
    private static DBConnectionFactory dbConnectionFactory = null;

    /**
     * Loads the namespaces under the default configuration file.
     *
     * @throws Exception to JUnit
     */
    static void loadNamespaces() throws Exception {
        releaseNamespaces();

        ConfigManager config = ConfigManager.getInstance();

        config.add(CONFIG_FILE);
    }

    /**
     * Clears all the namespaces.
     *
     * @throws Exception to JUnit
     */
    static void releaseNamespaces() throws Exception {
        ConfigManager config = ConfigManager.getInstance();

        for (int i = 0; i < NAMESPACES.length; i++) {
            if (config.existsNamespace(NAMESPACES[i])) {
                config.removeNamespace(NAMESPACES[i]);
            }
        }
    }

    /**
     * <p>
     * Retrieves a connection to the database.
     * </p>
     *
     * @return A not-null connection to the database.
     *
     * @throws Exception if connection creation failed.
     */
    private static Connection getConnection() throws Exception {
        if (dbConnectionFactory == null) {
            dbConnectionFactory = new DBConnectionFactoryImpl(DB_CONNECTION_FACTORY_NAMESPACE);
        }
        Connection conn = dbConnectionFactory.createConnection();
        conn.setAutoCommit(false);

        return conn;
    }

    /**
     * <p>
     * Closes the given Connection instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param conn the given Connection instance to close.
     */
    private static void closeConnection(Connection conn) {
        try {
            if ((conn != null) && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            // Ignore
        }
    }

    /**
     * <p>
     * Set up a testing data.
     * </p>
     *
     * @throws Exception to JUnit
     */
    static void setupData() throws Exception {
        setupDataWithScriptFile("/accuracytests/setup_data.sql");
    }

    /**
     * <p>
     * Set up a testing data.
     * </p>
     *
     * @throws Exception to JUnit
     */
    static void setupDataWithScriptFile(String fileName) throws Exception {
        Statement statement = null;
        Connection conn = null;

        try {
            StringBuffer queries = new StringBuffer();

            // read SQL queries from file
            InputStream is = ConfigHelper.class.getResourceAsStream(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            do {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                if (line.trim().length() == 0) {
                    continue;
                }
                queries.append(line);
            } while (true);
            in.close();

            conn = getConnection();
            statement = conn.createStatement();

            StringTokenizer st = new StringTokenizer(queries.toString(), ";");
            while (st.hasMoreTokens()) {
                statement.addBatch(st.nextToken());
            }

            statement.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            rollback(conn);
            throw ex;
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Clear test date.
     * </p>
     *
     * @throws Exception to JUnit
     */
    static void clearData() throws Exception {
        Connection conn = getConnection();
        String[] tables = {"client", "client_project", "project", "user_account", "project_worker", // general
            "project_fix_bill", "fix_bill_entry", "fix_bill_status", "fix_bill_type", // fixedbilling
                                                                                        // entry
            "project_expense", "expense_entry", "expense_status", "expense_type", // expense entry
            "project_time", "time_entry", "time_status", "task_type" }; // time entry

        Statement statement = conn.createStatement();

        try {
            for (int i = 0; i < tables.length; i++) {
                statement.addBatch("delete from " + tables[i]);
            }

            // clear all tables
            statement.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Closes the given Statement instance, <code>SQLException</code> will be ignored.
     * </p>
     *
     * @param statement the given Statement instance to close.
     */
    static void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            // Ignore
        }
    }

    /**
     * <p>
     * Roll back the current connection if any error occurs while updating the persistence.
     * </p>
     *
     * @param conn the given Connection instance to roll back
     */
    static void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException ex) {
            // Just ignore
        }
    }

    /**
     * Print the debug information.
     *
     * @param prompt the prompt info
     * @param out the out info
     */
    static void print(String prompt, String out) {
        System.out.println(prompt + " : " + out);
    }
}
