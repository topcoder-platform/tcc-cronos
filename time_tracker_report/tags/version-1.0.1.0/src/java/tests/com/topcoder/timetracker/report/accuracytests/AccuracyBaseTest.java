/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.Statement;

import java.util.Iterator;


/**
 * Base class for accuracy tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyBaseTest extends TestCase {
    /** DB Connection Factory configuration namespace. */
    protected static final String DB_NAMESPACE = "com.topcoder.timetracker.report.Informix";

    /** DB Connection Factory configuration file. */
    protected static final String DB_CONFIG_FILE = "accuracyTests/DB.xml";

    /** The time tracker reprot configuration file. */
    protected static final String TTR_CONFIG_FILE = "accuracyTests/TimeTrackerReport.xml";

    /** The name of the file used to insert data into the database. */
    private static final String INSERT_SQL_FILE = "accuracyTests/insert.sql";

    /**
     * Sets up test environment. It loads DB Connection Factory config and clears all tables.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        clearCM();

        ConfigManager manager = ConfigManager.getInstance();

        manager.add(DB_CONFIG_FILE);
        manager.add(TTR_CONFIG_FILE);

        clearTables();
    }

    /**
     * Clears after test.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        clearTables();
        clearCM();
    }

    /**
     * Creates database connection.
     *
     * @return database connection.
     * @throws Exception to JUnit.
     */
    public static Connection getConnection() throws Exception {
        return new DBConnectionFactoryImpl(DB_NAMESPACE).createConnection();
    }

    /**
     * Removes all namespaces from the Config Manager.
     *
     * @throws Exception to JUnit.
     */
    public static void clearCM() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String ns = (String) it.next();
            cm.removeNamespace(ns);
        }
    }

    /**
     * Inserts data into component tables.
     *
     * @throws Exception to JUnit.
     */
    public static void fillTables() throws Exception {
        String fullName = AccuracyBaseTest.class.getClassLoader().getResource(INSERT_SQL_FILE).getFile();

        BufferedReader in = new BufferedReader(new FileReader(fullName));

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        String str;

        try {
            while ((str = in.readLine()) != null) {
                if (str.trim().length() != 0) {
                    stmt.executeUpdate(str);
                }
            }
        } finally {
            stmt.close();
            in.close();
            conn.close();
        }
    }

    /**
     * Removes data from component tables.
     *
     * @throws Exception to JUnit.
     */
    public static void clearTables() throws Exception {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        try {
            stmt.executeUpdate("DELETE FROM ProjectWorkers");
            stmt.executeUpdate("DELETE FROM ProjectTimes");
            stmt.executeUpdate("DELETE FROM ProjectManagers");
            stmt.executeUpdate("DELETE FROM ProjectExpenses");

            stmt.executeUpdate("DELETE FROM ClientProjects");
            stmt.executeUpdate("DELETE FROM Clients");
            stmt.executeUpdate("DELETE FROM Projects");

            stmt.executeUpdate("DELETE FROM ExpenseEntries");
            stmt.executeUpdate("DELETE FROM ExpenseTypes");
            stmt.executeUpdate("DELETE FROM ExpenseStatuses");

            stmt.executeUpdate("DELETE FROM TimeEntries");
            stmt.executeUpdate("DELETE FROM TimeStatuses");
            stmt.executeUpdate("DELETE FROM TaskTypes");
            stmt.executeUpdate("DELETE FROM Users");
        } finally {
            stmt.close();
            conn.close();
        }
    }
}
