/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.failuretests;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;


/**
 * This is a base (helper) class for database related unit tests for this component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Helper extends TestCase {

    /** The connection name defined in the DBConnectionFactory. */
    public static final String CONNECTION_NAME = "informix_connection";

    /**
     * private constructor.
     */
    private Helper() {
    }

    /**
     * Loads the configuration files to ConfigManager.
     *
     * @param file the configurable file.
     *
     * @throws Exception to JUnit
     */
    public static void loadConfig(String file) throws Exception {
        // load it to the ConfigManager
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(file);
    }

    /**
     * Clears all the namespaces in the ConfigManager.
     *
     * @throws Exception to JUnit
     */
    public static void clearNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        // iterator through to clear the namespace
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String namespace = (String) it.next();

            if (cm.existsNamespace(namespace)) {
                // removes the namespace that exists
                cm.removeNamespace(namespace);
            }
        }
    }

    /**
     * Returns the default connection.
     *
     * @return the default connection.
     *
     * @throws Exception to JUnit
     */
    public static Connection getConnection() throws Exception {
        // returns the default connection (that is set to be non-auto commit).
        return new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory").createConnection();
    }

    /**
     * Load a SQL file and run each line as a statement
     *
     * @param filename file where the statements reside
     *
     * @throws Exception
     */
    public static void executeSQLFile(String filename)
        throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        StringBuffer sb = new StringBuffer();
        String str = null;

        while ((str = in.readLine()) != null) {
            sb.append(str + "\r\n");
        }

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sb.toString());

        conn.close();
        in.close();
    }

    /**
     * fill the db tables with test data
     *
     * @throws Exception to junit.
     */
    public static void fillTables() throws Exception {
        executeSQLFile("test_files/createtables.sql");
        executeSQLFile("test_files/preparedata.sql");
    }

    /**
     * Deletes records from table.
     *
     * @param table the table name.
     *
     * @throws Exception if any error occurs when deleting data
     */
    public static void dropTable(String table) throws Exception {
        Connection connection = getConnection();
        Statement stat = null;

        try {
            stat = connection.createStatement();
            stat.execute("drop table " + table);
        } finally {
            if (stat != null) {
                stat.close();
            }

            connection.close();
        }
    }


    /**
     * Closes the connection silently.
     *
     * @param con the connection to close.
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes the statement silently.
     *
     * @param stmt the statement to close.
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes a result set silently.
     *
     * @param rs the result set to close
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
