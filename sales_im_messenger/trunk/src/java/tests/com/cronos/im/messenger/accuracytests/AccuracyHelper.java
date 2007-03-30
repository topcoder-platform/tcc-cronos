/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.impl.ChatMessageTrackerImpl;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import java.io.File;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * The helper class provides utils for testing.
 *
 * @author lyt
 * @version 1.0
 */
public final class AccuracyHelper {
    /**
     * Private constructor prevent from instanciated.
     */
    private AccuracyHelper() {
    }

    /**
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance. If the instance is null, the field is a static field. If any error occurs, null is
     * returned.
     *
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be retrieved
     *
     * @return the value of the private field
     */
    public static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            // Get the reflection of the field
            field = type.getDeclaredField(name);

            // Set the field accessible.
            field.setAccessible(true);

            // Get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * Sets the value of a private field in the given class.
     *
     * @param type the class which the private field belongs to
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be retrieved
     * @param value the value to set
     */
    public static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible.
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * Load the configuration files to ConfigManager.
     *
     * @param filename DOCUMENT ME!
     *
     * @throws Exception exception to the caller.
     */
    public static void loadConfig(String filename) throws Exception {
        ConfigManager.getInstance().add("Accuracy" + File.separator + filename);
    }

    /**
     * Clear all the configurations.
     *
     * @throws Exception exception to the caller.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        List nameSpaces = new ArrayList();

        while (it.hasNext()) {
            nameSpaces.add(it.next());
        }

        for (int i = 0; i < nameSpaces.size(); i++) {
            cm.removeNamespace((String) nameSpaces.get(i));
        }
    }

    /**
     * Create a DocumentBuilder with the specified XSD file as validator.
     *
     * @param fileName the specified XSD file
     *
     * @return the DocumentBuilder with the specified XSD file as validator
     *
     * @throws Exception to JUnit
     */
    public static DocumentBuilder getDocumentBuilder(String fileName)
        throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");

        dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
            "file:test_files" + File.separator + "xsd" + File.separator + fileName);

        DocumentBuilder buidler = dbf.newDocumentBuilder();

        //buidler.setErrorHandler(new DefaultErrorHandler());
        return buidler;
    }

    /**
     * Create an instance of <code>ChatMessageTrackerImpl</code>.
     *
     * @return an instance of <code>ChatMessageTrackerImpl</code>
     *
     * @throws Exception to JUnit
     */
    public static ChatMessageTrackerImpl createTracker()
        throws Exception {
        DBConnectionFactoryImpl dbFactory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        String conn = "informix";
        String user = "TOPCODER";

        return new ChatMessageTrackerImpl(dbFactory, conn, user);
    }

    /**
     * <p>
     * Get a new db connection.
     * </p>
     *
     * @return the connection instance
     *
     * @throws Exception to JUnit
     */
    public static Connection getConnection() throws Exception {
        return new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
        .createConnection();
    }

    /**
     * <p>
     * Closes the Connection.
     * </p>
     *
     * @param con the Connection instance.
     */
    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            // Do nothing
        }
    }

    /**
     * <p>
     * Closes the PreparedStatement.
     * </p>
     *
     * @param state the given PreparedStatement
     */
    public static void closeStatement(Statement state) {
        try {
            if (state != null) {
                state.close();
            }
        } catch (SQLException e) {
            // Do nothing
        }
    }

    /**
     * <p>
     * Clear all data from tables
     * </p>
     *
     * @param conn Connection instance to access the database
     *
     * @throws SQLException if exception occurs during database operation
     */
    public static void deleteTables(Connection conn) throws SQLException {
        Statement stmt = null;
        String[] tables = new String[] {
                "session_user_message", "session_requested_user", "session_user", "group_session", "sales_session",
                "session", "session_mode", "buddy_user", "blocked_user", "all_user", "category",
            };

        try {
            stmt = conn.createStatement();

            for (int i = 0; i < tables.length; i++) {
                stmt.executeUpdate("DELETE FROM " + tables[i]);
            }
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * <p>
     * Inserts some data to the tableS for testing purpose.
     * </p>
     *
     * @param connection Connection instance to access the database
     *
     * @throws SQLException if exception occurs during database operation
     */
    public static void prepareTables(Connection connection)
        throws SQLException {
        Statement statement = null;

        try {
            statement = connection.createStatement();

            statement.executeUpdate(
                "insert into all_user (user_id, registered_flag, username, create_date, create_user, modify_date,"
                + " modify_user) VALUES (0, 'YES', 'Topcoder1', CURRENT, USER, CURRENT, USER)");
            statement.executeUpdate(
                "insert into all_user (user_id, registered_flag, username, create_date, create_user, modify_date,"
                + " modify_user) VALUES (1, 'YES', 'Topcoder2', CURRENT, USER, CURRENT, USER)");

            statement.executeUpdate(
                "insert into session_mode (session_mode_id, name, description, create_date, create_user, modify_date,"
                + " modify_user) VALUES (1, 'Topcoder1', 'one', CURRENT, USER, CURRENT, USER)");
            statement.executeUpdate(
                "insert into session_mode (session_mode_id, name, description, create_date, create_user, modify_date,"
                + " modify_user) VALUES (2, 'Topcoder2', 'two', CURRENT, USER, CURRENT, USER)");

            statement.executeUpdate(
                "insert into session (session_id, session_mode_id, create_user_id, create_date, create_user,"
                + " modify_date, modify_user) VALUES (0, 1, 1, CURRENT, USER, CURRENT, USER)");
            statement.executeUpdate(
                "insert into session (session_id, session_mode_id, create_user_id, create_date, create_user, "
                + "modify_date, modify_user) VALUES (1, 1, 1, CURRENT, USER, CURRENT, USER)");

            statement.executeUpdate(
                "insert into session_user (session_user_id, session_id, user_id, enter_date, exit_date, create_date, create_user, modify_date, modify_user) VALUES (3, 1, 0, CURRENT, NULL, CURRENT, USER, CURRENT, USER)");
            statement.executeUpdate("insert into session_user (session_user_id, session_id, user_id, enter_date, "
                + "exit_date, create_date, create_user, modify_date, modify_user) VALUES (4, 1, 1, "
                + "CURRENT, NULL, CURRENT, USER, CURRENT, USER)");

            statement.executeUpdate(
                "insert into session_requested_user (session_id, requested_user_id, create_date, create_user, "
                + "modify_date, modify_user) VALUES (1, 1, CURRENT, USER, CURRENT, USER)");

            statement.executeUpdate(
                "insert into category (category_id, name, description, chattable_flag, create_date, create_user, "
                + "modify_date, modify_user) VALUES (1, 'Topcoder', 'Topcoder', 'YES', CURRENT, USER, CURRENT, USER)");
        } finally {
            closeStatement(statement);
        }
    }
}
