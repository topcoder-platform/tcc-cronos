/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import java.sql.Connection;
import java.sql.Statement;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * The helper class which provides utility methods used for accuracy test.
 * </p>
 * <p>
 * The helper methods' functionality including get the database connection, prepare the test data,
 * clean the test data and so on.
 * </p>
 * @author fuyun
 * @version 1.0
 */
final class AccuracyTestHelper {

    /**
     * <p>
     * Private constructor avoiding this help class to be instanced.
     * </p>
     */
    private AccuracyTestHelper() {

    }

    /**
     * <p>
     * prepare the data in table Users.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public static void prepareUsersTable() throws Exception {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        try {
            stmt.executeUpdate("insert into Users (UsersID, Name, UserStore)"
                    + "values (1, 'user1', 'DbUserStore1');");
            stmt.executeUpdate("insert into Users (UsersID, Name, UserStore)"
                    + "values (2, 'user2', 'DbUserStore1');");
        } finally {
            stmt.close();
            connection.close();
        }
    }

    /**
     * <p>
     * Clean the table Users.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public static void cleanUsersTable() throws Exception {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        try {
            stmt.execute("delete from Users;");
        } finally {
            stmt.close();
            connection.close();
        }
    }

    /**
     * <p>
     * prepare the data in table DefaultUsers.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public static void prepareDefaultUsersTable() throws Exception {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        try {
            stmt
                    .execute("insert into DefaultUsers (DefaultUsersID, UserName, Password, CreationDate,"
                            + "CreationUser, ModificationDate, ModificationUser) values (3, 'user3', 'user3',"
                            + "'2005-07-22 00:00:00', 'creator3', '2005-07-22 00:00:01', 'modifier3');");
            stmt
                    .execute("insert into DefaultUsers (DefaultUsersID, UserName, Password, CreationDate,"
                            + "CreationUser, ModificationDate, ModificationUser) values (4, 'user4', 'user4',"
                            + "'2005-07-22 00:00:00', 'creator4', '2005-07-22 00:00:01', 'modifier4');");
            stmt
                    .execute("insert into DefaultUsers (DefaultUsersID, UserName, Password, CreationDate,"
                            + "CreationUser, ModificationDate, ModificationUser) values (5, 'user5', 'user5',"
                            + "'2005-07-22 00:00:00', 'creator5', '2005-07-22 00:00:01', 'modifier5');");
        } finally {
            stmt.close();
            connection.close();
        }
    }

    /**
     * <p>
     * Clean the table DefaultUsers.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public static void cleanDefaultUsersTable() throws Exception {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        try {
            stmt.execute("delete from DefaultUsers;");
        } finally {
            stmt.close();
            connection.close();
        }
    }

    /**
     * prepare the tables for component Authentication.
     * @throws Exception if any problem occurs.
     */
    public static void prepareAuthenticationTables() throws Exception {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        try {
            stmt.execute("INSERT INTO principal(principal_id, principal_name) VALUES(1, 'user1');");
            stmt.execute("INSERT INTO principal(principal_id, principal_name) VALUES(2, 'user2');");
            stmt.execute("INSERT INTO principal_role(principal_id, role_id) VALUES(1, 1);");
        } finally {
            stmt.close();
            connection.close();
        }
    }

    /**
     * clean the tables for component Authentication.
     * @throws Exception if any problem occurs.
     */
    public static void cleanAuthenticationTables() throws Exception {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        try {
            stmt.execute("delete from principal_role;");
            stmt.execute("delete from principal;");
        } finally {
            stmt.close();
            connection.close();
        }
    }

    /**
     * <p>
     * Get the connection used for database operating.
     * </p>
     * <p>
     * The method uses the DBConnectionFactory component to create the connection.
     * </p>
     * @return the database connection.
     * @throws Exception if fail to create connection.
     */
    public static Connection getConnection() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        return factory.createConnection();
    }
}
