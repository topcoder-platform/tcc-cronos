/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * <p>
 * For clients that do not have an existing user store, this default database implementation is
 * provided. Clients must maintain their database of users externally. The Time Tracker application
 * will be able to import users from this implementation just like from any other user store.
 * </p>
 *
 * <p>
 * For this version, a SQL database system will be used as the persistent storage for this component
 * and the Time Tracker application. Note that DbUserStore supports any database registered in the
 * DB Connection Factory component, so other database systems are easy pluggable into the framework,
 * aside from the initial Informix implementation.
 * </p>
 *
 * <p>
 * The database table used is &quot;DefaultUsers&quot; and is described in the Requirement Specification.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class DbUserStore implements UserStore {

    /**
     * This is the SQL query that is run from the getNames method.
     */
    private static final String GET_NAMES_SQL = "SELECT USERNAME FROM DefaultUsers";

    /**
     * This is the SQL query that is run from the search method.
     */
    private static final String SEARCH_SQL =
            "SELECT USERNAME FROM DefaultUsers where USERNAME like ?";

    /**
     * This is the SQL query that is run from the authenticate method.
     */
    private static final String AUTHENTICATE_SQL =
            "SELECT USERNAME FROM DefaultUsers WHERE USERNAME = ? AND PASSWORD = ?";

    /**
     * This SQL query is run from the contains method and is used for exact matches only.
     */
    private static final String CONTAINS_SQL = "SELECT USERNAME FROM DefaultUsers where USERNAME = ?";


    /**
     * The DBConnectionFactory instance used to create database connections for database-
     * related operations (getNames, contains, search, authenticate methods). Set by constructor
     * and will never be null.
     */
    private final DBConnectionFactory dbFactory;


    /**
     * The connection name used to create database connections from dbFactory.  Set by
     * setConnectionString and must never be null after it is set.  If any method is called that
     * needs the database before this is set, that method will throw IllegalStateException.
     */
    private String connectionName;


    /**
     * <p>
     * Creates a DbUserStore instance which will have an undefined connection name, and a
     * DBConnectonFactory that uses the UserManager.CONFIG_NAMESPACE as its namespace.
     * </p>
     *
     * <p>
     * Users must call the setConnectionString method to define the connection name, otherwise
     * all other methods in this class with throw IllegalStateException.
     * </p>
     *
     * @throws ConfigurationException if any exception occurs during initialization
     */
    public DbUserStore() throws ConfigurationException {

        // initialize the factory from the default namespace
        try {
            dbFactory = new DBConnectionFactoryImpl(UserManager.CONFIG_NAMESPACE);
        } catch (UnknownConnectionException e) {
            throw new ConfigurationException("Could not initialize DBConnectionFactory.", e);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            throw new ConfigurationException("Could not initialize DBConnectionFactory.", e);
        }
    }


    /**
     * <p>
     * Creates a DbUserStore instance with the given DBConnectionFactory instance.
     * </p>
     *
     * @param connectionName the connection name to request from the DBConnectionFactory
     * @param dbFactory DBConnectionFactory instance used to get connections
     * @throws NullPointerException if dbFactory or connectionName is null
     * @throws IllegalArgumentException if connection is the empty String
     */
    public DbUserStore(String connectionName, DBConnectionFactory dbFactory) {

        if (dbFactory == null) {
            throw new NullPointerException("dbFactory cannot be null.");
        }
        this.dbFactory = dbFactory;
        setConnectionString(connectionName);
    }


    /**
     * <p>
     * Enumerates all usernames contained within the DefaultUsers table.
     * </p>
     *
     * @return Collection of String instances representing the usernames in the store.
     * @throws PersistenceException if any errors occur while retrieving the usernames.
     * @throws IllegalStateException if the connectionString has not been set yet.
     */
    public Collection getNames() throws PersistenceException {

        // this SQL statement returns all records; throws PersistenceException for us
        return retrieveNames(GET_NAMES_SQL, null);
    }


    /**
     * <p>
     * Searches for specific users by name in the DefaultUsers table. The specified pattern can be any
     * standard SQL "like" clause, including wildcard characters "%" and "_".
     * </p>
     *
     * @param pattern a SQL-type "LIKE" pattern which can optionally include '%', to represent any
     *            number of characters
     * @return Collection of String instances representing the usernames found in the DefaultUsers
     *         table that match the pattern
     * @throws NullPointerException if pattern is null
     * @throws IllegalArgumentException if pattern is the empty String
     * @throws IllegalStateException if setConnectionString has not been called yet
     * @throws PersistenceException if any errors occur during the search, which may wrap an
     *             underlying exception (e.g., SQLException)
     */
    public Collection search(String pattern) throws PersistenceException {
        if (pattern == null) {
            throw new NullPointerException("pattern cannot be null.");
        }
        if (pattern.length() == 0) {
            throw new IllegalArgumentException("pattern cannot be empty.");
        }

        // this SQL statement accepts the 'like' clause; the method throws PersistenceException for us
        return retrieveNames(SEARCH_SQL, new String[] {pattern});
    }


    /**
     * <p>
     * Searches for a specific user by their username in the DefaultUsers table. This search is
     * case-sensitive and performs an *exact match*.
     * </p>
     *
     * @param name username of user to find
     * @return true if there is a user with the given username within the DefaultUsers table, false
     *        otherwise
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if name is the empty String
     * @throws IllegalStateException if setConnectionString has not been called yet
     * @throws PersistenceException if any error occurs during the search, which may wrap an
     *        underlying exceptoin (e.g., SQLException)
     */
    public boolean contains(String name) throws PersistenceException {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }

        // Returns all records which have 'name' as the username; this method will throw
        // PersistenceException for us.
        Collection matches = retrieveNames(CONTAINS_SQL, new Object[] {name});

        // if we found exactly one, great.
        return matches.size() == 1;
    }


    /**
     * <p>
     * Authenticate the user with the given username against the given password in the DefaultUsers
     * table.  In this impelmentation, the password is treated as a String, and is compared to the
     * password column in the table to determine if the user is authorized or not.
     * </p>
     *
     * @param username username of the user to authenticate
     * @param password password of the user to authenticate
     * @return a Response instance (from Authentication Factory component) with the 'successful
     *         flag' set to true if authentication was successful, false otherwise. Nothing else in
     *         the Response object is required to be filled in.
     * @throws NullPointerException if name or password is null
     * @throws IllegalArgumentException if name is the empty String
     * @throws IllegalStateException if setConnectionString has not been called yet
     * @throws AuthenticateException if any error occurs during authentication, which may wrap an
     *             underlying exception (e.g., NamingException or SQLException)
     */
    public Response authenticate(String username, Object password) throws AuthenticateException {
        if (username == null) {
            throw new NullPointerException("username cannot be null.");
        }
        if (password == null) {
            throw new NullPointerException("password cannot be null.");
        }
        if (username.length() == 0) {
            throw new IllegalArgumentException("username cannot be empty.");
        }

        try {
            // Match on username and password; throws IllegalStateException and PersistenceException
            // if need be.
            Collection matches = retrieveNames(AUTHENTICATE_SQL, new Object[] {username, password});

            // if we found exactly one, it means it's valid.
            boolean authenticated = matches.size() == 1;
            return new Response(authenticated);

        } catch (PersistenceException e) {
            // convert from PersistenceException to AuthenticateException
            throw new AuthenticateException("Could not authenticate user " + username + ".", e);
        }
    }


    /**
     * <p>
     * Sets the connection string to the user store.  In this implementation, the connection string
     * is considered as the connection name to be provided to the DB Connection Factory.  Note
     * that if the connection is not recognized by the DB Connection Factory, it is not detected
     * here.
     * </p>
     *
     * <p>
     * This method must be called before any other method is called in this class, or an
     * IllegalStateException will be thrown.
     * </p>
     *
     * @param connection connection string
     * @throws NullPointerException if connection is null
     * @throws IllegalArgumentException if connection is the empty String
     */
    public void setConnectionString(String connection) {
        if (connection == null) {
            throw new NullPointerException("connection cannot be null.");
        }
        if (connection.length() == 0) {
            throw new IllegalArgumentException("connection cannot be empty.");
        }

        this.connectionName = connection;
    }


    /**
     * This method is the "work-horse" of this class. It runs the given SQL "select" statement,
     * whose one and only column must be the username. It then builds a collection of these names
     * (as Strings) and returns that collection. If no rows were returned, this method returns an
     * empty list. Note that this method, since it's private, does not check its arguments for null.
     *
     * @param searchSql the "Select" query to run; the one column must be the username.
     * @param parameters any parameters to set on the query after converting to a PreparedStatement.
     * @return a Collection of Strings, representing the usernames found by the query.
     * @throws PersistenceException if anything went wrong.
     * @throws IllegalStateException if the connection name has not been set yet.
     */
    private Collection retrieveNames(String searchSql, Object[] parameters)
        throws PersistenceException {

        // throws PersistenceException if failure
        Connection connection = DbUtils.getConnection(dbFactory, connectionName);

        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {

            // run through the arguments, setting them on the prepared statement
            stmt = connection.prepareStatement(searchSql);
            for (int i = 0; parameters != null && i < parameters.length; ++i) {
                stmt.setObject(i + 1, parameters[i]);
            }

            // We'll be returning this list
            Collection names = new ArrayList();
            // run through the result set, capturing the first column
            rs = stmt.executeQuery();
            while (rs != null && rs.next()) {
                // add to the list.
                names.add(rs.getString(1));
            }

            return names;

        } catch (SQLException e) {
            throw new PersistenceException("Could not retrieve users.", e);
        } finally {
            // releaseResources will never throw an exception.
            DbUtils.releaseResources(connection, stmt, rs);
        }
    }
}