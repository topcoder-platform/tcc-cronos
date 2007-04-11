/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.stresstests;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * A helper class for stress test cases.
 *
 * @author Chenhong
 * @version 1.0
 */
public class DBUtil {

    /**
     * Private constructor.
     *
     */
    private DBUtil() {
        // Empty.
    }

    /**
     * Get the DBConnectionFactory instance.
     *
     * @return DBConnectionFactory instance.
     *
     * @throws Exception
     *             to junit.
     */
    public static DBConnectionFactory getDBConnectionFactory() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add(new File("test_files/stress/DBConnectionFactory.xml").getCanonicalPath());

        return new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
    }

    /**
     * Get the IDGenerator with name
     *
     * @param name
     *            the IDGenerator name
     * @return an IDGenerator instance.
     * @throws Exception
     *             to junit.
     */
    public static IDGenerator getIDGenerator(String name) throws Exception {
        return IDGeneratorFactory.getIDGenerator(name);
    }

    /**
     * Get a Connection to the database.
     *
     * @return database connection.
     * @throws Excption
     *             to junit.
     */
    public static Connection getConnection() throws Exception {
        DBConnectionFactory factory = getDBConnectionFactory();

        return factory.createConnection();
    }

    /**
     * Insert a record into table role.
     *
     * @param connection
     *            the connection to the database.
     * @param roleId
     *            the id for the role
     * @param roleName
     *            the name for the role.
     *
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordIntoRole(Connection connection, int roleId, String roleName) throws Exception {
        PreparedStatement st = connection.prepareStatement("insert into role (role_id, role_name) values(?, ?)");
        try {
            st.setInt(1, roleId);
            st.setString(2, roleName);

            st.executeUpdate();
        } finally {
            closeStatement(st);
        }
    }

    /**
     * Insert a record into tablse Principal.
     *
     * @param connection
     *            the database connection
     * @param principal_id
     *            the id for principal
     * @param principal_name
     *            the name for principal
     *
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordIntoPrincipal(Connection connection, int principal_id, String principal_name)
            throws Exception {

        PreparedStatement st = connection
                .prepareStatement("insert into principal (principal_id, principal_name) values(?, ?)");
        try {
            st.setInt(1, principal_id);
            st.setString(2, principal_name);

            st.executeUpdate();
        } finally {
            closeStatement(st);
        }
    }

    /**
     * Insert a record into table Pricipal_Role.
     *
     * @param connection
     *            the database connection
     * @param p
     *            the pricipal_id
     * @param r
     *            the role_id
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordIntoPrincipal_Role(Connection connection, int p, int r) throws Exception {
        PreparedStatement st = connection
                .prepareStatement("INSERT INTO principal_role (principal_id, role_id) VALUES (?, ?)");

        try {
            st.setInt(1, p);
            st.setInt(2, r);

            st.executeUpdate();
        } finally {
            closeStatement(st);
        }
    }

    /**
     * Insert a record into table Category.
     *
     * @param Connection
     *            the connection to database.
     * @param id
     *            the id for category.
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordIntoCategory(Connection connection, int id) throws Exception {
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO category (category_id, name, description, chattable_flag, "
                        + "create_date, create_user, modify_date, modify_user) VALUES (?, ?, ?, ?, "
                        + " CURRENT, USER, CURRENT, USER)");
        try {
            statement.setLong(1, id);
            statement.setString(2, "name" + id);
            statement.setString(3, "des" + id);
            statement.setString(4, "Y");
            statement.executeUpdate();
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Insert a record into table manager_category.
     *
     * @param connection
     *            the database connection
     * @param manager_id
     *            the manager_id
     * @param category_id
     *            the category_id
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordInto_manager_category(Connection connection, int manager_id, int category_id)
            throws Exception {
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO manager_category (manager_id, category_id, create_date, "
                        + "create_user, modify_date, modify_user) VALUES (?, ?, CURRENT, USER, " + "CURRENT, USER)");
        try {
            statement.setInt(1, manager_id);
            statement.setInt(2, category_id);

            statement.executeUpdate();
        } finally {

            DBUtil.closeStatement(statement);
        }
    }

    /**
     * Insert a record into table all_user.
     *
     * @param connection
     *            the database connection.
     * @param userId
     *            the user_id for persisting
     * @param flag
     *            the registered_flag
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordIntoAll_User(Connection connection, int userId, String flag) throws Exception {
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO all_user(user_id, registered_flag, username, create_date, "
                        + "create_user, modify_date, modify_user) VALUES (?, ?, ?, CURRENT, USER, CURRENT, USER)");

        try {
            statement.setInt(1, userId);
            statement.setString(2, flag);
            statement.setString(3, "name" + userId);

            statement.executeUpdate();
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Insert a record into table client.
     *
     * @param connection
     *            the database connection to the database.
     * @param id
     *            the id
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordIntoClient(Connection connection, int id) throws Exception {
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO client(client_id, first_name, last_name,company, title, email,"
                        + " create_date, "
                        + "create_user, modify_date, modify_user) VALUES (?, ?, ?, ?, ?,?, CURRENT,"
                        + " USER, CURRENT, USER)");

        try {
            statement.setInt(1, id);
            statement.setString(2, "f" + id);
            statement.setString(3, "l" + id);
            statement.setString(4, "company" + id);
            statement.setString(5, "title" + id);
            statement.setString(6, "email" + id + "@topcoder.com");

            statement.executeUpdate();
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Insert a record into table user.
     *
     * @param connection
     *            the database connection
     * @param id
     *            the id
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordIntoUser(Connection connection, int id) throws Exception {
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO user (user_id, first_name, last_name, handle) "
                        + "VALUES (?, ?, ?, ?)");

        try {
            statement.setInt(1, id);
            statement.setString(2, "f_" + id);
            statement.setString(3, "l" + id);
            statement.setString(4, "handle" + id);

            statement.executeUpdate();
        } finally {

            closeStatement(statement);
        }
    }

    /**
     * Insert a record into table email.
     *
     * @param connection
     *            the connection to database.
     * @param id
     *            the id
     * @throws Exception
     *             to junit.
     */
    public static void insertRecordIntoEmail(Connection connection, int id) throws Exception {
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO email (user_id, address) VALUES (?, ?)");

        try {
            statement.setInt(1, id);
            statement.setString(2, "email" + id + "@topcoder.com");

            statement.executeUpdate();
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Clear the tables associated with role and category.
     *
     * @param connection
     *            the database connection
     * @throws Exception
     *             to junit.
     */
    public static void clearTablesForRoleCategory(Connection connection) throws Exception {
        Statement st = connection.createStatement();

        try {
            st.executeUpdate("DELETE FROM principal_role");
            st.executeUpdate("DELETE FROM manager_category");
            st.executeUpdate("DELETE FROM principal");
            st.executeUpdate("DELETE FROM role");
            st.executeUpdate("DELETE FROM category");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeStatement(st);
        }
    }

    /**
     * Clear the table all_user which is used for ProfileKey.
     *
     * @param connection
     *            the database connection.
     * @throws Exception
     *             to junit.
     */
    public static void clearTableForProfileKeyManager(Connection connection) throws Exception {
        Statement st = connection.createStatement();

        try {
            st.executeUpdate("DELETE FROM all_user");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeStatement(st);
        }
    }

    /**
     * Close the database connection
     *
     * @param connection
     *            the connection to be closed.
     *
     * @throws Exception
     *             to junit.
     */
    public static void closeConnection(Connection connection) throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Close the statement instance.
     *
     * @param statement
     *            The sql statement to be closed.
     * @throws Exception
     *             to junit.
     */
    public static void closeStatement(Statement statement) throws Exception {
        if (statement != null) {
            statement.close();
        }
    }
}