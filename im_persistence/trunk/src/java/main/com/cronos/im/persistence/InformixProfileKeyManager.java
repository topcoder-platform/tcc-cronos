/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.DuplicateProfileKeyException;
import com.topcoder.chat.user.profile.ProfileKey;
import com.topcoder.chat.user.profile.ProfileKeyManager;
import com.topcoder.chat.user.profile.ProfileKeyManagerPersistenceException;
import com.topcoder.chat.user.profile.ProfileKeyNotFoundException;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.util.datavalidator.ObjectValidator;

import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGenerationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>An implementation of <code>ProfileKeyManager</code> using an <i>Informix</i> database as the backing
 * storage. Specifically, this implementation reads and writes the <i>all_user</i> table. The <i>user_id</i> and
 * <i>username</i> columns will hold the profile ID and username, respectively. The <i>registered_flag</i> column
 * will be <i>Y</i> or <i>N</i> depending on whether the type is <i>Registered</i> or <i>Unregistered</i>. The
 * remaining columns are filled out with the time and current user at the time a profile is created.
 *
 * <p>Two of the three constructors initialize the key manager using the <i>Configuration Manager</i>
 * component. The following options are supported.
 *
 * <ul>
 *   <li><strong>specification_factory_namespace</strong> (required): the namespace containing the object factory
 *      specifications</li>
 *   <li><strong>db_connection_factory_key</strong> (required): the object factory key used to instantiate the DB
 *     connection factory</li>
 *   <li><strong>connection_name</strong> (required): the name used to retrieve connections from the DB connection
 *     factory</li>
 *   <li><strong>id_generator</strong> (required): the name of the ID generator to use to create profile IDs</li>
 *   <li><strong>validator_key</strong> (optional): the object factory key used to instantiate the object
 *     validator; if omitted, no validation is done</li>
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class InformixProfileKeyManager extends AbstractPersistenceWithGenerator implements ProfileKeyManager {
    /**
     * The configuration namespace that will be used by the {@link #InformixProfileKeyManager default constructor}.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.im.persistence.InformixProfileKeyManager";

    /**
     * Constant representing the type for a registered user (one of the two allowable types).
     */
    public static final String TYPE_REGISTERED = "Registered";

    /**
     * Constant representing the type for an unregistered user (one of the two allowable types).
     */
    public static final String TYPE_UNREGISTERED = "Unregistered";

    /**
     * Constructs a new <code>InformixProfileKeyManager</code> using the specified configuration namespace. See the
     * class documentation for a description of the configuration parameters.
     *
     * @param namespace the configuration namepsace
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws ConfigurationException if the namespace does not exist or there is a problem instantiating the
     *   database connection factory, object validator, or ID generator factory based on the namespace
     */
    public InformixProfileKeyManager(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Constructs a new <code>InformixProfileKeyManager</code> with the specified parameters.
     *
     * @param connectionFactory the DB connection factory
     * @param connectionName the DB connection name
     * @param idGenerator the ID generator
     * @param validator the profile validator or <code>null</code> if no validation is required
     * @throws IllegalArgumentException if <code>connectionFactory</code>, <code>connectionName</code>, or
     *   <code>idGenerator</code> is <code>null</code> or <code>connectionName</code> is an empty string
     */
    public InformixProfileKeyManager(DBConnectionFactory connectionFactory, String connectionName,
                                     IDGenerator idGenerator, ObjectValidator validator) {
        super(connectionFactory, connectionName, idGenerator, validator);
    }

    /**
     * Equivalent to {@link #InformixProfileKeyManager(String) InformixProfileKeyManager(DEFAULT_NAMESPACE}.
     *
     * @throws ConfigurationException if the namespace does not exist or there is a problem instantiating the
     *   database connection factory, object validator, or ID generator factory using the default namespace
     */
    public InformixProfileKeyManager() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * Creates an entry for the specified profile in the <code>all_user</code> table and returns a profile key
     * containing the newly created ID. If the profile key already contains an ID, it will be ignored. The only
     * types recognized are <i>Registered</i> and <i>Unregistered</i>.
     *
     * @param key the key to insert into the <code>all_user</code> table
     * @return a new profile key containing the newly created ID
     * @throws IllegalArgumentException if <code>key</code> is <code>null</code> or does not have a type of
     *   <i>Registered</i> or <i>Unregistered</i>
     * @throws DuplicateProfileKeyException if the username/type combination already exists
     * @throws ProfileKeyValidationException if the validator rejects the profile key
     * @throws ProfileKeyManagerPersistenceException if a persistence error occurs that does not fall into one of
     *   the other categories
     */
    public ProfileKey createProfileKey(ProfileKey key)
        throws DuplicateProfileKeyException, ProfileKeyManagerPersistenceException {
        ParameterHelpers.checkParameter(key, "profile key");

        String type = key.getType();
        String username = key.getUsername();

        boolean isRegistered = checkType(type);

        // if a validator is configured, validate the key
        ObjectValidator validator = getValidator();
        if (validator != null) {
            String message = validator.getMessage(key);
            if (message != null) {
                throw new ProfileKeyValidationException(message);
            }
        }

        Connection connection = getConnection();

        try {
            try {
                // check to see if the user already exists
                PreparedStatement statement =
                    connection.prepareStatement("SELECT username FROM all_user WHERE username = ? AND "
                                                + "registered_flag = ?");

                try {
                    statement.setString(1, username);
                    statement.setString(2, (isRegistered ? "Y" : "N"));

                    ResultSet results = statement.executeQuery();
                    try {
                        if (results.next()) {
                            throw new DuplicateProfileKeyException("key for '" + username + "' already exists");
                        }
                    } finally {
                        closeResults(results);
                    }
                } finally {
                    closeStatement(statement);
                }

                // generate a new user ID
                long id;
                try {
                    id = getGenerator().getNextID();
                } catch (IDGenerationException ex) {
                    throw new ProfileKeyManagerPersistenceException("error creating ID: " + ex.getMessage(), ex);
                }

                if (!isRegistered) {
                    username = String.valueOf(id);
                }

                // insert the profile
                insertProfile(connection, username, id, isRegistered);

                return new ProfileKey(id, username, type);
            } catch (SQLException ex) {
                throw new ProfileKeyManagerPersistenceException("SQL error: " + ex.getMessage(), ex);
            }
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Deletes the profile with the specified ID from the <i>all_user</i> table.
     *
     * @param id the ID to delete
     * @throws IllegalArgumentException if <code>id</code> is negative
     * @throws ProfileKeyNotFoundException if the specified ID does not exist
     * @throws ProfileKeyManagerPersistenceException if a persistence error occurs that does not fall into one of
     *   the other categories
     */
    public void deleteProfileKey(long id) throws ProfileKeyNotFoundException, ProfileKeyManagerPersistenceException {
        if (id < 0) {
            throw new IllegalArgumentException("ID must be non-negative");
        }

        Connection connection = getConnection();

        try {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM all_user WHERE user_id = ?");
                try {
                    statement.setLong(1, id);

                    if (statement.executeUpdate() == 0) {
                        throw new ProfileKeyNotFoundException("ID '" + id + "' not found");
                    }
                } finally {
                    closeStatement(statement);
                }
            } catch (SQLException ex) {
                throw new ProfileKeyManagerPersistenceException("SQL error: " + ex.getMessage(), ex);
            }
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Returns the profile for the specified ID or <code>null</code> if no such profile exists.
     *
     * @param id the ID of the profile to retrieve
     * @return the requested profile or <code>null</code> if no such profile exists
     * @throws IllegalArgumentException if <code>id</code> is negative
     * @throws ProfileKeyManagerPersistenceException if an error occurs while accessing the database
     */
    public ProfileKey getProfileKey(long id) throws ProfileKeyManagerPersistenceException {
        if (id < 0) {
            throw new IllegalArgumentException("ID must be non-negative");
        }

        Connection connection = getConnection();

        try {
            return getProfileKey(id, connection);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * A batch version of {@link #getProfileKey(long) getProfileKey}. The elements in the returned array will be
     * the profile keys for the corresponding items in the input array, or <code>null</code> in the cases where no
     * such profiles exist. Therefore, the returned array will always be the same size as the input array. If no
     * profile keys are found for any of the IDs, <code>null</code> is returned instead.
     *
     * @param ids an array of IDs for which to retrieve profile keys
     * @return an array of <code>ProfileKey</code> elements corresponding to the elements in the input array, or
     *   <code>null</code> if no profile keys are found
     * @throws IllegalArgumentException if <code>ids</code> is <code>null</code> or empty or contains negative elements
     * @throws ProfileKeyManagerPersistenceException if an error occurs while accessing the database
     */
    public ProfileKey[] getProfileKeys(long[] ids) throws ProfileKeyManagerPersistenceException {
        ParameterHelpers.checkParameter(ids, "ID array");

        if (ids.length == 0) {
            throw new IllegalArgumentException("ID array must contain at least one element");
        }

        // make sure all the numbers are non-negative
        for (int idx = 0; idx < ids.length; ++idx) {
            if (ids[idx] < 0) {
                throw new IllegalArgumentException("IDs must be non-negative");
            }
        }

        Connection connection = getConnection();
        ProfileKey[] keys = new ProfileKey[ids.length];
        try {
            // keep track if we have found any
            boolean found = false;
            for (int idx = 0; idx < ids.length; ++idx) {
                ProfileKey key = getProfileKey(ids[idx], connection);
                if (key != null) {
                    found = true;
                    keys[idx] = key;
                }
            }

            // return null if we did not find any
            return (found ? keys : null);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Retrieves the profile key for the specified username and type. The username and type of the returned key
     * will be the same as the method arguments, and the ID will be retrieved from the database. If no such profile
     * key exists, <code>null</code> is returned.
     *
     * @param username the username to retrieve
     * @param type the type to retrieve
     * @return the profile key for the specified username/type or <code>null</code> if no such key exists
     * @throws IllegalArgumentException if either argument is <code>null</code> or an empty string or
     *   <code>type</code> is not <i>Registered</i> or <i>Unregistered</i>
     * @throws ProfileKeyManagerPersistenceException if a database error occurs
     */
    public ProfileKey getProfileKey(String username, String type) throws ProfileKeyManagerPersistenceException {
        ParameterHelpers.checkParameter(username, "username");

        boolean registered = checkType(type);

        Connection connection = getConnection();

        try {
            return getProfileKey(connection, username, type, registered);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * A batch version of {@link #getProfileKey(String, String) getProfileKey}. The elements in the returned array
     * will correspond to the elements in the same positions in the <code>usernames</code> array. If no key is
     * found for a particular username, its slot will contain a <code>null</code> element. If no profile keys are
     * found for any of the usernames, <code>null</code> will be returned.
     *
     * @param usernames the usernames for which to retrieve keys
     * @param type the type for which to retrieve keys
     * @return an array of the keys corresponding to the usernames in the input array, or <code>null</code> if no
     *   keys are found
     * @throws IllegalArgumentException if <code>usernames</code> is <code>null</code> or empty or contains
     *   <code>null</code> or empty elements, or if <code>type</code> is anything other than <i>Registered</i> or
     *   <i>Unregistered</i>
     * @throws ProfileKeyManagerPersistenceException if a database error occurs
     */
    public ProfileKey[] getProfileKeys(String[] usernames, String type) throws ProfileKeyManagerPersistenceException {
        ParameterHelpers.checkParameter(usernames, "usernames");

        if (usernames.length == 0) {
            throw new IllegalArgumentException("username array must contain at least one element");
        }

        for (int idx = 0; idx < usernames.length; ++idx) {
            ParameterHelpers.checkParameter(usernames[idx], "username");
        }

        boolean registered = checkType(type);

        Connection connection = getConnection();
        try {
            boolean found = false;
            ProfileKey[] keys = new ProfileKey[usernames.length];

            // call getProfileKey(String) for each username
            for (int idx = 0; idx < usernames.length; ++idx) {
                ProfileKey key = getProfileKey(connection, usernames[idx], type, registered);
                if (key != null) {
                    keys[idx] = key;
                    found = true;
                }
            }

            return (found ? keys : null);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Retrieves all profile keys for the specified type.
     *
     * @param type the type for which to retrieve the profile keys
     * @return an array of all profile keys for the specified type
     * @throws IllegalArgumentException if type is not <i>Registered</i> or <i>Unregistered</i>
     * @throws ProfileKeyNotFoundException if no profiles are found for the specified type
     * @throws ProfileKeyManagerPersistenceException if a database error occurs other than no profiles being found
     */
    public ProfileKey[] getProfileKeys(String type)
        throws ProfileKeyNotFoundException, ProfileKeyManagerPersistenceException {
        boolean registered = checkType(type);

        Connection connection = getConnection();

        try {
            return getProfileKeys(connection, type, registered);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Batch version of {@link #getProfileKeys(String) getProfileKeys(type)}. Elements in the returned array
     * correspond to the elemnets at the same index of the input array. If a particular type does not have any
     * profiles, <code>null</code> will be returned in that array slot.
     *
     * @param types an array of types for which to retrieve profile keys
     * @return an two-dimensional array where each sub-array contains the set of profile keys for the corresponding
     *   type
     * @throws IllegalArgumentException if <code>types</code> is <code>null</code> or empty or contains elements
     *   other than <i>Registered</i> and <i>Unregistered</i>
     * @throws ProfileKeyNotFoundException if no profile keys are found for any of the types
     * @throws ProfileKeyManagerPersistenceException if a database error occurs other than no profiles being found
     */
    public ProfileKey[][] getProfileKeys(String[] types)
        throws ProfileKeyNotFoundException, ProfileKeyManagerPersistenceException {
        ParameterHelpers.checkParameter(types, "type array");

        if (types.length == 0) {
            throw new IllegalArgumentException("types array must contain at least one element");
        }

        boolean[] registered = new boolean[types.length];
        for (int idx = 0; idx < types.length; ++idx) {
            registered[idx] = checkType(types[idx]);
        }

        ProfileKey[][] keys = new ProfileKey[types.length][];
        boolean found = false; // have we found any?

        Connection connection = getConnection();

        try {
            // retrieve the keys for each type
            for (int idx = 0; idx < types.length; ++idx) {
                try {
                    keys[idx] = getProfileKeys(connection, types[idx], registered[idx]);
                    found = true;
                } catch (ProfileKeyNotFoundException ex) {
                    // do nothing
                }
            }
        } finally {
            closeConnection(connection);
        }

        if (!found) {
            throw new ProfileKeyNotFoundException("no profiles found for any of the types");
        }

        return keys;
    }

    /**
     * Retrieves all of the profile keys for the specified type.
     *
     * @param connection the DB connection to use
     * @param type the type to retrieve
     * @param registered an indication of whether the type is registered or unregistered
     * @return an array of all the profile keys for the specified type
     * @throws ProfileKeyNotFoundException if no profiles are found for the specified type
     * @throws ProfileKeyManagerPersistenceException if a database error occurs other than no profiles being found
     */
    private static ProfileKey[] getProfileKeys(Connection connection, String type, boolean registered)
        throws ProfileKeyNotFoundException, ProfileKeyManagerPersistenceException {
        List ret = new ArrayList();

        try {
            PreparedStatement statement =
                connection.prepareStatement("SELECT user_id, username FROM all_user WHERE registered_flag = ?");

            try {
                statement.setString(1, registered ? "Y" : "N");

                ResultSet results = statement.executeQuery();
                try {
                    while (results.next()) {
                        ProfileKey key = new ProfileKey(results.getLong(1), results.getString(2), type);
                        ret.add(key);
                    }
                } finally {
                    closeResults(results);
                }

                int size = ret.size();
                if (size == 0) {
                    throw new ProfileKeyNotFoundException("no profiles found for type '" + type + "'");
                }

                return (ProfileKey[]) ret.toArray(new ProfileKey[size]);
            } finally {
                closeStatement(statement);
            }
        } catch (SQLException ex) {
            throw new ProfileKeyManagerPersistenceException("error retrieving keys: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the specified <code>ResultSet</code> and rethrows any exceptions as
     * <code>ProfileKeyManagerPersistenceException</code>.
     *
     * @param results the <code>ResultSet</code> to close
     * @throws ProfileKeyManagerPersistenceException if a SQL exception occurs
     */
    private static void closeResults(ResultSet results) throws ProfileKeyManagerPersistenceException {
        try {
            results.close();
        } catch (SQLException ex) {
            throw new ProfileKeyManagerPersistenceException("error closing result set: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the specified <code>PreparedStatement</code> and rethrows any exceptions as
     * <code>ProfileKeyManagerPersistenceException</code>.
     *
     * @param statement the <code>PreparedStatement</code> to close
     * @throws ProfileKeyManagerPersistenceException if a SQL exception occurs
     */
    private static void closeStatement(PreparedStatement statement) throws ProfileKeyManagerPersistenceException {
        try {
            statement.close();
        } catch (SQLException ex) {
            throw new ProfileKeyManagerPersistenceException("error closing statement: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the specified <code>Connection</code> and rethrows any exceptions as
     * <code>ProfileKeyManagerPersistenceException</code>.
     *
     * @param connection the <code>Connection</code> to close
     * @throws ProfileKeyManagerPersistenceException if a SQL exception occurs
     */
    private static void closeConnection(Connection connection) throws ProfileKeyManagerPersistenceException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new ProfileKeyManagerPersistenceException("error closing connection: " + ex.getMessage(), ex);
        }
    }

    /**
     * Returns the profile for the specified ID using the specified connection, or <code>null</code> if no such
     * profile exists.
     *
     * @param id the ID of the profile to retrieve
     * @param connection the database connection to use
     * @return the profile key for the specified ID or <code>null</code> if no such profile exists
     * @throws ProfileKeyManagerPersistenceException if a database error occurs
     */
    private static ProfileKey getProfileKey(long id, Connection connection)
        throws ProfileKeyManagerPersistenceException {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT registered_flag, username "
                                                                      + "FROM all_user WHERE user_id = ?");

            try {
                statement.setLong(1, id);

                ResultSet results = statement.executeQuery();
                try {
                    if (!results.next()) {
                        return null;
                    }

                    String type = (results.getString(1).equalsIgnoreCase("Y")
                                   ? TYPE_REGISTERED : TYPE_UNREGISTERED);
                    String username = results.getString(2);

                    return new ProfileKey(id, username, type);
                } finally {
                    closeResults(results);
                }
            } finally {
                closeStatement(statement);
            }
        } catch (SQLException ex) {
            throw new ProfileKeyManagerPersistenceException("SQL error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Returns <code>true</code> if <code>type</code> refers to a registered user or <code>false</code> if
     * <code>type</code> refers to an unregistered user.
     *
     * @param type the type to check
     * @return <code>true</code> if <code>type</code> refers to a registered user or <code>false</code> if
     *   <code>type</code> refers to an unregistered user
     * @throws IllegalArgumentException if <code>type</code> is not <i>Registered</i> or <i>Unregistered</i>
     */
    private static boolean checkType(String type) {
        ParameterHelpers.checkParameter(type, "type");

        if (type.equals(TYPE_REGISTERED)) {
            return true;
        } else if (type.equals(TYPE_UNREGISTERED)) {
            return false;
        } else {
            throw new IllegalArgumentException("type must be 'Registered' or 'Unregistered'");
        }
    }

    /**
     * Returns the profile key for the specified username/type combination or <code>null</code> if no such key
     * exists.
     *
     * @param connection the DB connection to use
     * @param username the username
     * @param type the type
     * @param registered an indication of whether the user is registered
     * @return the profile key for the specified username/type combination or <code>null</code> if no such key
     *   exists
     * @throws ProfileKeyManagerPersistenceException if a database error occurs
     */
    private static ProfileKey getProfileKey(Connection connection, String username, String type, boolean registered)
        throws ProfileKeyManagerPersistenceException {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM all_user WHERE "
                                                                      + "username = ? AND registered_flag = ?");
            try {
                statement.setString(1, username);
                statement.setString(2, registered ? "Y" : "N");

                ResultSet results = statement.executeQuery();
                try {
                    if (!results.next()) {
                        return null;
                    }

                    return new ProfileKey(results.getLong(1), username, type);
                } finally {
                    closeResults(results);
                }
            } finally {
                closeStatement(statement);
            }
        } catch (SQLException ex) {
            throw new ProfileKeyManagerPersistenceException("error retrieving profile key: " + ex.getMessage(), ex);
        }
    }

    /**
     * Creates a new connection and rethrows any exceptions as <code>ProfileKeyManagerPersistenceException</code>.
     *
     * @return the new connection
     * @throws ProfileKeyManagerPersistenceException if a SQL exception occurs
     */
    private Connection getConnection() throws ProfileKeyManagerPersistenceException {
        try {
            return getConnectionFactory().createConnection(getConnectionName());
        } catch (DBConnectionException ex) {
            throw new ProfileKeyManagerPersistenceException("error creating connection: " + ex.getMessage(), ex);
        }
    }

    /**
     * Insers a new profile into the <i>all_user</i> table.
     *
     * @param connection the DB connection
     * @param username the username
     * @param id the user ID
     * @param isRegistered an indication of whether the user is registered
     * @throws SQLException if a database error occurs
     * @throws ProfileKeyManagerPersistenceException if the update query fails to insert any rows
     */
    private static void insertProfile(Connection connection, String username, long id, boolean isRegistered)
        throws SQLException, ProfileKeyManagerPersistenceException {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO all_user (username, user_id, registered_flag, "
                                        + "create_date, modify_date, create_user, modify_user) "
                                        + "VALUES (?, ?, ?, CURRENT, CURRENT, USER, USER)");

        try {
            int idx = 1;
            statement.setString(idx++, username);
            statement.setLong(idx++, id);
            statement.setString(idx++, (isRegistered ? "Y" : "N"));

            if (statement.executeUpdate() < 1) {
                throw new ProfileKeyManagerPersistenceException("failed to insert user");
            }
        } finally {
            closeStatement(statement);
        }
    }
}
