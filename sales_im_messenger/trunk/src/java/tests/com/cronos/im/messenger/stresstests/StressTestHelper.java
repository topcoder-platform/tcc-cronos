/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.stresstests;

import com.topcoder.util.config.ConfigManager;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

/**
 * <p>
 * A helper class to perform those common operations which are helpful for the test.
 * </p>
 * @author FireIce
 * @version 1.0
 */
final class StressTestHelper {

    /**
     * <p>
     * The configuration file for the stress testing.
     * </p>
     */
    public static final String CONFIG_FILE = "stresstests" + File.separator
        + "stress.xml";

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private StressTestHelper() {
        // empty
    }

    /**
     * <p>
     * Load the testing namespaces for stress testing.
     * </p>
     * @param fileName
     *            config file to set up environment
     * @throws Exception
     *             when any exception occurs
     */
    public static void loadNamespaces() throws Exception {
        // set up environment
        ConfigManager config = ConfigManager.getInstance();
        config.add(CONFIG_FILE);
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    public static void clearNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * <p>
     * Deletes data from all the tables used by this component.
     * </p>
     * @param connection
     *            Connection instance to access the database
     * @throws SQLException
     *             if exception occurs during database operation
     */
    public static void clearTables(Connection connection) throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM session_user_message");

            stmt.executeUpdate("DELETE FROM session_user");
            stmt.executeUpdate("DELETE FROM session_requested_user");
            stmt.executeUpdate("DELETE FROM group_session");
            stmt.executeUpdate("DELETE FROM sales_session");

            stmt.executeUpdate("DELETE FROM session");
            stmt.executeUpdate("DELETE FROM session_mode");
            stmt.executeUpdate("DELETE FROM buddy_user");
            stmt.executeUpdate("DELETE FROM blocked_user");
            stmt.executeUpdate("DELETE FROM all_user");

            stmt.executeUpdate("DELETE FROM category");
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * <p>
     * Closes the given Connection instance.
     * </p>
     * @param con
     *            the given Connection instance to close.
     */
    public static void closeConnection(Connection con) {
        try {
            if ((con != null) && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given PreparedStatement instance.
     * </p>
     * @param state
     *            the given Statement instance to close.
     */
    public static void closeStatement(Statement state) {
        try {
            if (state != null) {
                state.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

}
