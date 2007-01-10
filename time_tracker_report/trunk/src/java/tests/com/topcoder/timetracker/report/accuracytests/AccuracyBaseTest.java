/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

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
 * @version 2.0
 */
public class AccuracyBaseTest extends TestCase {
    /** DB Connection Factory configuration namespace. */
    protected static final String DB_NAMESPACE = "com.cronos.timetracker.report.Informix";

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
        loadConfig();

        clearTables();
    }

    /**
     * @throws ConfigManagerException
     */
    public static void loadConfig() throws Exception {
        clearCM();
        ConfigManager manager = ConfigManager.getInstance();

        manager.add(DB_CONFIG_FILE);
        manager.add(TTR_CONFIG_FILE);
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
        fillTables(INSERT_SQL_FILE);
    }

    /**
     * Inserts data into component tables.
     *
     * @param path the file name.
     * @throws Exception to JUnit.
     */
    public static void fillTables(String path) throws Exception {
        String fullName = AccuracyBaseTest.class.getClassLoader().getResource(path).getFile();

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
            stmt.executeUpdate("DELETE FROM client_project");
            stmt.executeUpdate("DELETE FROM client");
            stmt.executeUpdate("DELETE FROM time_reject_reason");
            stmt.executeUpdate("DELETE FROM exp_reject_reason");

            stmt.executeUpdate("DELETE FROM company_address");
            stmt.executeUpdate("DELETE FROM user_address");
            stmt.executeUpdate("DELETE FROM user_contact");

            stmt.executeUpdate("DELETE FROM company_contact");
            stmt.executeUpdate("DELETE FROM address");
            stmt.executeUpdate("DELETE FROM state_name");

            stmt.executeUpdate("DELETE FROM contact");
            stmt.executeUpdate("DELETE FROM project_worker");
            stmt.executeUpdate("DELETE FROM project_manager");
            stmt.executeUpdate("DELETE FROM user_account");

            stmt.executeUpdate("DELETE FROM account_status");
            stmt.executeUpdate("DELETE FROM comp_task_type");
            stmt.executeUpdate("DELETE FROM comp_exp_type");
            stmt.executeUpdate("DELETE FROM comp_rej_reason");

            stmt.executeUpdate("DELETE FROM reject_reason");
            stmt.executeUpdate("DELETE FROM project_time");
            stmt.executeUpdate("DELETE FROM time_entry");
            stmt.executeUpdate("DELETE FROM task_type");

            stmt.executeUpdate("DELETE FROM reject_reason");
            stmt.executeUpdate("DELETE FROM project_time");
            stmt.executeUpdate("DELETE FROM time_entry");
            stmt.executeUpdate("DELETE FROM task_type");

            stmt.executeUpdate("DELETE FROM time_status");
            stmt.executeUpdate("DELETE FROM project_expense");
            stmt.executeUpdate("DELETE FROM comp_reject_email");
            stmt.executeUpdate("DELETE FROM reject_email");

            stmt.executeUpdate("DELETE FROM expense_entry");
            stmt.executeUpdate("DELETE FROM expense_status");
            stmt.executeUpdate("DELETE FROM expense_type");
            stmt.executeUpdate("DELETE FROM project");
            stmt.executeUpdate("DELETE FROM company");
        } finally {
            stmt.close();
            conn.close();
        }
    }
}
