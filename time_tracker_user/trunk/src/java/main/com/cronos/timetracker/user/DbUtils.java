/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 * This package-private utility class gathers together a few methods that make it easier to access
 * and manipulate a database within the Time Tracker User component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class DbUtils {

    /**
     * Hide the default constructor of this static utility class.
     */
    private DbUtils() {
    }

    /**
     * Close the connection, statement, and result set, if they are not null.
     * If an exception occurs, the caller is not notified.
     *
     * @param connection the connection to close; if this is null, it will not be closed.
     * @param stmt the statement to close; if this is null, it will not be closed.
     * @param rs the ResultSet to close; if this is null, it will not be closed.
     */
    static void releaseResources(Connection connection, Statement stmt, ResultSet rs) {

        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // do nothing
            return;
        }
    }


    /**
     * Creates a connection from the given DBConnectionFatory with the given name.
     *
     * @param dbFactory the pre-configured connection factory from which to request connections.
     * @param connectionName the connection name to request from the factory.
     * @return a connection from the connection factory with the given name.
     * @throws PersistenceException if the connection factory doesn't recognize the conneciton name,
     *  or if the connection could not be made (e.g., bad server name, etc.)
     * @throws NullPointerException if dbFactory is null
     * @throws IllegalStateException if connectionName is null; note this is different than
     *  "normal" behavior because the methods that use this method are required to throw
     *  IllegalStateException and not NullPointerException if the connection name was never set.
     */
    static Connection getConnection(DBConnectionFactory dbFactory, String connectionName)
        throws PersistenceException {

        try {
            if (connectionName == null) {
                // illegal
                throw new IllegalStateException("connectionName is not set.");
            }
            return dbFactory.createConnection(connectionName);
        } catch (DBConnectionException e) {
            throw new PersistenceException("Could not create connection.", e);
        }
    }
}
