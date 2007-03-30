/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
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
 *
 * @author marius_neo
 * @version 1.0
 */
public final class TestHelper {
    /**
     * <p>
     * Represents the namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DB_CONNECTION_FACTORY_NAMESPACE =
        "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * The configuration file for the DB Connection Factory component.
     * </p>
     */
    public static final String DB_CONNECTION_FACTORY_CONFIG_FILE = "test_files"
        + File.separator + "db_connection_factory_config.xml";

    /**
     * <p>
     * The configuration file for the Chat Contact Manager component.
     * </p>
     */
    public static final String CHAT_CONTACT_MANAGER_CONFIG_FILE = "test_files"
        + File.separator + "chat_contact_manager_config.xml";

    /**
     * <p>
     * The configuration file for the Chat Session Manager component.
     * </p>
     */
    public static final String CHAT_SESSION_MANAGER_CONFIG_FILE = "test_files"
        + File.separator + "chat_session_manager_config.xml";

    /**
     * Represents the directory in which are placed the xsd files which contain the
     * schema definitions for the XML representations of the messages defined in this
     * component.
     */
    public static final String XSD_DIRECTORY = "file:test_files" + File.separator + "xsd";

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @param fileName config file to set up environment
     * @throws Exception when any exception occurs
     */
    public static void loadXMLConfig(String fileName) throws Exception {
        //set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        config.add(file.getCanonicalPath());
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * <p>
     * Inserts some data to the tables which this component depends on.
     * </p>
     *
     * <p>
     * This is used to simplify the testing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public static void setUpDataBase() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            clearTables(connection);
            insertBasicDataToDB(connection);
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Clears all the data from the tables using by this component.
     * </p>
     *
     * <p>
     * This is used to simplify the testing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public static void tearDownDataBase() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            clearTables(connection);
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Inserts some data to the tableS for testing purpose.
     * </p>
     *
     * @param connection Connection instance to access the database
     * @throws SQLException if exception occurs during database operation
     */
    private static void insertBasicDataToDB(Connection connection) throws SQLException {
        String[] sqlStatements = new String[]{
            // Users ids:{0, 1, 2, 3, 4, 5}
            "INSERT INTO all_user (user_id, registered_flag, username, create_date, "
                + "create_user, modify_date, modify_user) VALUES (0, 'Y', 'tc1', CURRENT, USER, CURRENT, USER)",
            "INSERT INTO all_user (user_id, registered_flag, username, create_date, "
                + "create_user, modify_date, modify_user) VALUES (1, 'Y', 'tc2', CURRENT, USER, CURRENT, USER)",
            "INSERT INTO all_user (user_id, registered_flag, username, create_date, "
                + "create_user, modify_date, modify_user) VALUES (2, 'Y', 'tc3', CURRENT, USER, CURRENT, USER)",
            "INSERT INTO all_user (user_id, registered_flag, username, create_date, "
                + "create_user, modify_date, modify_user) VALUES (3, 'Y', 'tc4', CURRENT, USER, CURRENT, USER)",
            "INSERT INTO all_user (user_id, registered_flag, username, create_date, "
                + "create_user, modify_date, modify_user) VALUES (4, 'Y', 'tc5', CURRENT, USER, CURRENT, USER)",
            "INSERT INTO all_user (user_id, registered_flag, username, create_date, "
                + "create_user, modify_date, modify_user) VALUES (5, 'Y', 'tc6', CURRENT, USER, CURRENT, USER)",

            "INSERT INTO session_mode (session_mode_id, name, description, create_date, "
                + "create_user, modify_date, modify_user) VALUES (1, 'tc1', 'one', CURRENT, USER, CURRENT, USER)",
            "INSERT INTO session_mode (session_mode_id, name, description, create_date, "
                + "create_user, modify_date, modify_user) VALUES (2, 'tc2', 'two', CURRENT, USER, CURRENT, USER)",
            "INSERT INTO session_mode (session_mode_id, name, description, create_date, "
                + "create_user, modify_date, modify_user) VALUES (3, 'tc3', 'three', CURRENT, USER, CURRENT, USER)",
            "INSERT INTO session_mode (session_mode_id, name, description, create_date, "
                + "create_user, modify_date, modify_user) VALUES (5, 'tc5', 'five', CURRENT, USER, CURRENT, USER)",

            "INSERT INTO session (session_id, session_mode_id, create_user_id, create_date, "
                + "create_user, modify_date, modify_user) VALUES (0, 1, 1, CURRENT, USER, CURRENT, USER)",
            "INSERT INTO session (session_id, session_mode_id, create_user_id, create_date, "
                + "create_user, modify_date, modify_user) VALUES (1, 1, 1, CURRENT, USER, CURRENT, USER)",
            "INSERT INTO session (session_id, session_mode_id, create_user_id, create_date, "
                + "create_user, modify_date, modify_user) VALUES (2, 5, 1, CURRENT, USER, CURRENT, USER)",

            // User ids :{0,1,2,3,4} are in session {1}
            "INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, "
                + "exit_date, create_date, create_user, modify_date, modify_user) VALUES (3, 1, 0, "
                + "CURRENT, NULL, CURRENT, USER, CURRENT, USER)",
            "INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, "
                + "exit_date, create_date, create_user, modify_date, modify_user) VALUES (4, 1, 1, "
                + "CURRENT, NULL, CURRENT, USER, CURRENT, USER)",
            "INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, "
                + "exit_date, create_date, create_user, modify_date, modify_user) VALUES (5, 1, 2, "
                + "CURRENT, NULL, CURRENT, USER, CURRENT, USER)",
            "INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, "
                + "exit_date, create_date, create_user, modify_date, modify_user) VALUES (6, 1, 3, "
                + "CURRENT, NULL, CURRENT, USER, CURRENT, USER)",
            "INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, "
                + "exit_date, create_date, create_user, modify_date, modify_user) VALUES (7, 1, 4, "
                + "CURRENT, NULL, CURRENT, USER, CURRENT, USER)",

            "INSERT INTO session_requested_user (session_id, requested_user_id, create_date, create_user, "
                + "modify_date, modify_user) VALUES (1, 1, CURRENT, USER, CURRENT, USER)",
            "INSERT INTO session_requested_user (session_id, requested_user_id, create_date, create_user, "
                + "modify_date, modify_user) VALUES (0, 2, DateTime(2005-12-31 00:00:00) Year to Second, USER, "
                + "CURRENT, USER)",

            "INSERT INTO category (category_id, name, description, chattable_flag, create_date, create_user, "
                + "modify_date, modify_user) VALUES (1, 'sale', 'sale', 'Y', CURRENT, USER, CURRENT, USER)"};

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            for (int i = 0; i < sqlStatements.length; i++) {
                stmt.executeUpdate(sqlStatements[i]);
            }
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * <p>
     * Deletes data from all the tables used by this component.
     * </p>
     *
     * @param connection Connection instance to access the database
     * @throws SQLException if exception occurs during database operation
     */
    private static void clearTables(Connection connection) throws SQLException {
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
     * Returns a new connection to be used for persistence.
     * </p>
     *
     * @return the connection instance for database operation
     * @throws Exception If unable to obtain a Connection
     */
    private static Connection getConnection() throws Exception {
        Connection connection = new DBConnectionFactoryImpl(DB_CONNECTION_FACTORY_NAMESPACE).createConnection();
        return connection;
    }

    /**
     * <p>
     * Closes the given Connection instance.
     * </p>
     *
     * @param con the given Connection instance to close.
     */
    private static void closeConnection(Connection con) {
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
     *
     * @param state the given Statement instance to close.
     */
    private static void closeStatement(Statement state) {
        try {
            if (state != null) {
                state.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

}
