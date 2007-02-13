/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * This implementation of the UserPersistence interface provides a database backend for storing and
 * retrieving imported users. The class utilizes the DB Connection Factory component to support
 * various database systems.
 * </p>
 * <p>
 * The database table used is &quot;Users&quot; and is described in the Requirement Specification.
 * Note that the CreationDate, CreationUser, ModificationDate, ModificationUser fields are out of
 * scope of this component (as noted in Developer Forums).
 * </p>
 * <p>
 * The following basic operations are supported:
 * </p>
 * <ul>
 * <li>addUser - add a User to the Users table</li>
 * <li>removeUser - remove a specified user, identified by its ID, from the Users table</li>
 * <li>getUsers - retrieve all User rows from the Users table</li>
 * </ul>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class UserPersistenceImpl implements UserPersistence {

    /**
     * The configuration property name for the connection name to be used when requesting connections
     * from the DBConnectionFactory.
     */
    private static final String CONNECTION_NAME = "connection_name";

    /**
     * The SQL statement that is used by addUser to insert a new user. The values will be filled
     * in using a PreparedStatement.
     */
    private static final String INSERT_SQL = "INSERT into Users (UsersID, Name, UserStore, Email) VALUES (?, ?, ?, ?)";

    /**
     * The SQL statement that is used by getUsers to retrieve all imported Users from the database.
     */
    private static final String GET_USERS_SQL = "SELECT UsersID, Name, UserStore, Email FROM Users";

    /** The index of the ID column in the INSERT_SQL and GET_USERS_SQL strings. */
    private static final int ID_COLUMN = 1;

    /** The index of the name column in the INSERT_SQL and GET_USERS_SQL strings. */
    private static final int NAME_COLUMN = 2;

    /** The index of the user store column in the INSERT_SQL and GET_USERS_SQL strings. */
    private static final int USER_STORE_COLUMN = 3;

    /** The index of the email column in the INSERT_SQL and GET_USERS_SQL strings. */
    private static final int EMAIL_COLUMN = 4;

    /**
     * The SQL statement that is used by removeUser to remove the specified user from the
     * database, given its unique user ID.
     */
    private static final String REMOVE_USER_SQL =  "DELETE FROM Users WHERE UsersID = ?";


    /**
     * The DBConnectionFactory instance used to retrieve and store user entities within the
     * database. Set by constructors, used by all methods. Will never be null.
     */
    private final DBConnectionFactory dbFactory;


    /**
     * The connection name which is used to get a connection from dbFactory. Set by constructors,
     * used by all methods. Will never be null.
     */
    private final String connectionName;



    /**
     * <p>
     * Constructs a UserPersistenceImpl by reading configuration settings from the given
     * namespace. This constructor does the following:
     * <ul>
     * <li>Sets connectionName to the value in the configuration file from the "connection_name" property</li>
     * <li>Instantiates a DBConnectionFactory using the given namespace</li>
     * </ul>
     * </p>
     *
     * @param namespace the ConfigManager configuration namespace from which to read configuration data
     * @throws NullPointerException if namespace is null
     * @throws IllegalArgumentException if namespace is the empty String
     * @throws ConfigurationException if any error occurs during initialization, including, not being
     *      able to find the given namespace, not being able to find the ConnectionName property
     *      in that namespace, not being able to instantiate the DBConnectionFactory, or not being
     *      able to create the desired connection with the instantiated DBConnectionFactory.
     */
    public UserPersistenceImpl(String namespace) throws ConfigurationException {
        if (namespace == null) {
            throw new NullPointerException("namespace cannot be null.");
        }
        if (namespace.length() == 0) {
            throw new IllegalArgumentException("namespace cannot be empty.");
        }


        ConfigManager cm = ConfigManager.getInstance();
        if (!cm.existsNamespace(namespace)) {
            throw new ConfigurationException("namespace " + namespace + " does not exist.");
        }

        // Get the conneciton name (required) from the configuration namespace
        try {
            cm.refresh(namespace);

            connectionName = cm.getString(namespace, CONNECTION_NAME);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("Could not find namespace " + namespace + ".", e);
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("Could not refresh namespace " + namespace + ".", e);
        }
        if (connectionName == null || connectionName.trim().length() == 0) {
            throw new ConfigurationException("Could not read required property " + CONNECTION_NAME + ".");
        }

        // initialize the factory from the same namespace.
        try {
            DBConnectionFactoryImpl dbFactoryImpl = new DBConnectionFactoryImpl(namespace);

            this.dbFactory = dbFactoryImpl;

            // make sure the dbFactory knows about this connection.
            // throws ConfigurationException if invalid connection
            validateDbFactory(dbFactoryImpl, connectionName);
        } catch (UnknownConnectionException e) {
            throw new ConfigurationException("Could not initialize DBConnectionFactory.", e);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            throw new ConfigurationException("Could not initialize DBConnectionFactory.", e);
        }
    }


    /**
     * <p>
     * Creates a UserPersistenceImpl with the given connection name and DBConnectionFactory.
     * </p>
     *
     * @param connectionName the connection name to be used for creating database connections by the
     *      specified DBConnectionFactory
     * @param dbFactory the DBConnectionFactory to be used to create database connections
     * @throws NullPointerException if connectionName or dbFactory is null
     * @throws IllegalArgumentException if connectionName is the empty String
     * @throws PersistenceException if the requested connection was not recognized by the factory
     */
    public UserPersistenceImpl(String connectionName, DBConnectionFactory dbFactory) throws PersistenceException {
        if (dbFactory == null) {
            throw new NullPointerException("dbFactory cannot be null.");
        }
        if (connectionName == null) {
            throw new NullPointerException("connectionName cannot be null.");
        }
        if (connectionName.length() == 0) {
            throw new IllegalArgumentException("connectionName cannot be empty.");
        }

        this.connectionName = connectionName;
        this.dbFactory = dbFactory;

        // check for validity
        try {
            // validate the connection name by the factory
            if (dbFactory instanceof DBConnectionFactoryImpl) {
                // just ask the implementation
                validateDbFactory((DBConnectionFactoryImpl) dbFactory, connectionName);
            } else {
                // have to open a connection
                validateDbFactory(connectionName);
            }
        } catch (ConfigurationException e) {
            // re-wrap the old exception
            throw new PersistenceException(e.getMessage(), e.getCause());
        }
    }


    /**
     * Validate that the given DBConnectionFactory knows about the requested connection name. Since
     * we are dealing with a DBConnectionFactoryImpl and not the interface, we can query the class
     * directly if it knows about the connection.
     *
     * @param testDbFactory The DBConnectionFactory to query
     * @param testConnectionName the connection name to pass to the testDbFactory to see if it
     *            exists in the factory.
     * @throws ConfigurationException if the factory could not be created, or if it could not create
     *             or close a connection with the given name
     */
    private void validateDbFactory(DBConnectionFactoryImpl testDbFactory, String testConnectionName)
        throws ConfigurationException {

        if (!testDbFactory.contains(testConnectionName)) {
            throw new ConfigurationException("connection name " + testConnectionName + " not configured.");
        }
    }


    /**
     * Validate that the already-configured dbConnectionFactory (instance variable) knows about the
     * requested connection name.  This method actually opens and closes a connection, which, while
     * inefficient, is the only way to find out if the connection name is recognized, based
     * on the DBConnectionFactory interface.
     * @param testConnectionName the connection name to pass to the dbConnectionFactory to
     *  see if it exists in the factory.
     * @throws ConfigurationException if the factory could not be created, or if it could
     *  not create or close a connection with the given name
     */
    private void validateDbFactory(String testConnectionName) throws ConfigurationException {
        try {
            // make sure the dbFactory knows about this connection.
            Connection conn = dbFactory.createConnection(testConnectionName);
            conn.close();
        } catch (UnknownConnectionException e) {
            throw new ConfigurationException("Could not initialize DBConnectionFactory.", e);
        } catch (DBConnectionException e) {
            throw new ConfigurationException(
                            "DBConnectionFactory could not create connection for " + testConnectionName + ".", e);
        } catch (SQLException e) {
            throw new ConfigurationException("DBConnectionfactory provided uncloseable connection.", e);
        }
    }


    /**
     * <p>
     * Adds the given user to the User table in the database by running a SQL Insert statement to insert the user
     * ID, name and user store name.
     * </p>
     *
     * @param user the User instance to add to the Users table
     * @throws NullPointerException if user is null
     * @throws PersistenceException if any error occurs while adding the record; if an error
     *  occurs, the user will not be inserted.
     */
    public void addUser(User user) throws PersistenceException {

        if (user == null) {
            throw new NullPointerException("user cannot be null.");
        }

        // Get a connection from the factory. This method throws PersistenceException for us
        Connection connection = DbUtils.getConnection(dbFactory, connectionName);

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(INSERT_SQL);

            // Set the values of the columns to insert
            stmt.setInt(ID_COLUMN, (int) user.getId());
            stmt.setString(NAME_COLUMN, user.getName());
            stmt.setString(USER_STORE_COLUMN, user.getUserStoreName());
            stmt.setString(EMAIL_COLUMN, user.getEmail());

            // insert the row and close the statement.
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Could not add user " + user.getName() + ".", e);
        } finally {

            // Close the connection and statement. Does NOT throw any exceptions.
            DbUtils.releaseResources(connection, stmt, null);
        }
    }


    /**
     * <p>
     * Removes given user from the user persistence by running a SQL query to delete a row
     * from the Users table with the specified user's ID.  If no record with the specified
     * ID was not found, nothing happens.
     * </p>
     *
     * @param user User instance whose ID we wish to remove from the Users table
     * @throws NullPointerException if user is null
     * @throws PersistenceException if any error occurs during removing the user
     */
    public void removeUser(User user) throws PersistenceException {
        if (user == null) {
            throw new NullPointerException("user cannot be null.");
        }

        // Get a connection from the factory. This method will throw PersistenceException for us
        Connection connection = DbUtils.getConnection(dbFactory, connectionName);
        PreparedStatement stmt = null;

        try {

            // delete the user
            stmt = connection.prepareStatement(REMOVE_USER_SQL);
            stmt.setLong(ID_COLUMN, user.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Could not remove user " + user.getName() + ".", e);
        } finally {

            // Close the connection, statement and result set. Does NOT throw any exceptions.
            DbUtils.releaseResources(connection, stmt, null);
        }
    }


    /**
     * <p>
     * Retrieves all existing imported users from persistence storage by running a SQL "select"
     * query from the Users table in the database.
     * </p>
     *
     * @return a Collection of User instances based on all the rows in the Users table
     * @throws PersistenceException if any error occurs during retrieving the records
     */
    public Collection getUsers() throws PersistenceException {

        // Get a connection from the factory. This method throws PersistenceException for us
        Connection connection = DbUtils.getConnection(dbFactory, connectionName);
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Collection users = new ArrayList();

            stmt = connection.createStatement();

            // Loop through all the rows in the GET_USERS_SQL query, creating
            // User objects as we go along
            rs = stmt.executeQuery(GET_USERS_SQL);
            while (rs.next()) {
                // make a user from the three columns
                int id = rs.getInt(ID_COLUMN);
                String name = rs.getString(NAME_COLUMN);
                String userStore = rs.getString(USER_STORE_COLUMN);
                String email = rs.getString(EMAIL_COLUMN);
                User user = new User(id, name, userStore, email);
                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            throw new PersistenceException("Could not get users.", e);
        } finally {

            // Close the connection, statement and result set. Does NOT throw any exceptions.
            DbUtils.releaseResources(connection, stmt, rs);
        }
    }
}