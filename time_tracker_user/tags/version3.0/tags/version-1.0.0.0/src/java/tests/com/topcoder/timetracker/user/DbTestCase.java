/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

/**
 * <p>
 * This abstract base class is used when the subclass needs to insert records into the Users
 * and DefaultUsers table, or to remove all records from those tables after testing (tearDown).
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
abstract class DbTestCase extends ConfigTestCase {

    /** The "standard" connection name to be used for a 'good' connection. */
    protected static final String CONNECTION_NAME = "InformixConnection";

    /** The Insert statement that is used to insert records into the DefaultUsers table. */
    private static final String INSERT_DEFAULT_USER_STMT =
        "insert into DefaultUsers (DefaultUsersID, "
        + "       UserName, "
        + "       Password, "
        + "       CreationDate, "
        + "       CreationUser, "
        + "       ModificationDate, "
        + "       ModificationUser)"
        + " values (?, ?, ?, ?, 'user', ?, 'user')";

    /** The Insert statement that is used to insert records into the Users table. */
    private static final String INSERT_USER_STMT =
        "insert into Users (UsersID, "
        + "       Name, "
        + "       userstore) "
        + " values (?, ?, ?)";

    /**
     * Flag indicates that something was inserted, and that the tearDown method
     * should remove all records from the Users and DefaultUsers tables.
     */
    private boolean inserted;

    /**
     * A connection to the database, it is created when needed and only closed
     * in the tearDown method.
     */
    private Connection conn;


    /**
     * Remove all records from the Users and DefaultUsers table, if the inserted
     * flag is set; closes the connection.
     * @throws Exception Never under normal conditions.
     */
    protected void tearDown() throws Exception {
        if (inserted) {
            cleanupDatabase();
        }
        if (conn != null) {
            conn.close();
        }
        super.tearDown();
    }


    /**
     * Turn on the 'inserted' flag.
     */
    protected void inserted() {
        inserted = true;
    }


    /**
     * Opens a connection from the DBConectionFactory using the default namesapce
     * and default connection name (InformixConnection).  Only creates one connection
     * and keeps giving back that same connection.
     * @return a Connection to connect to Informix
     * @throws Exception Never under normal condition.
     */
    protected Connection getConnection() throws Exception {
        if (conn == null) {
            conn = new DBConnectionFactoryImpl(NAMESPACE).createConnection(CONNECTION_NAME);
        }
        return conn;
    }


    /**
     * Remove all records from the Users and DefaultUsers table. Also, removes all principal
     * roles and principals, so that the SQLAuthorizationPersistence will be working from
     * a clean database each time.
     *
     * @throws Exception Never under normal conditions.
     */
    protected void cleanupDatabase() throws Exception {
        getConnection();
        conn.createStatement().executeUpdate("delete from defaultUsers");
        conn.createStatement().executeUpdate("delete from users");
        conn.createStatement().executeUpdate("delete from principal_role");
        conn.createStatement().executeUpdate("delete from principal");
        conn.close();
        conn = null;
    }


    /**
     * Insert FOUR records into the DefaultUsers table.
     * @throws Exception Never under normal conditions.
     */
    protected void insertDefaultUsers() throws Exception {

        inserted();

        conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(INSERT_DEFAULT_USER_STMT);
        for (int i = 1; i <= 3; ++i) {
            stmt.setInt(1, i);
            stmt.setString(2, "username" + i);
            stmt.setString(3, "password" + i);
            stmt.setDate(4, new Date(System.currentTimeMillis()));
            stmt.setDate(5, new Date(System.currentTimeMillis()));
            stmt.executeUpdate();
        }
        stmt.setInt(1, 4);
        stmt.setString(2, "username");
        stmt.setString(3, "password");
        stmt.setDate(4, new Date(System.currentTimeMillis()));
        stmt.setDate(5, new Date(System.currentTimeMillis()));
        stmt.executeUpdate();
        stmt.close();
    }


    /**
     * Insert three records into the Users table.
     * @throws Exception Never under normal conditions.
     */
    protected void insertUsers() throws Exception {

        inserted();

        conn = getConnection();
        for (int i = 1; i <= 3; ++i) {
            PreparedStatement stmt = conn.prepareStatement(INSERT_USER_STMT);
            stmt.setInt(1, i);
            stmt.setString(2, "username" + i);
            stmt.setString(3, "userStore");
            stmt.executeUpdate();
            stmt.close();
        }
    }
}
