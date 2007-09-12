/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.ChatUserProfilePersistence;
import com.topcoder.chat.user.profile.ChatUserProfilePersistenceException;
import com.topcoder.chat.user.profile.DuplicateProfileException;
import com.topcoder.chat.user.profile.ProfileNotFoundException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>An implementation of <code>ChatUserProfilePersistence</code> that uses an Informix database as the backing
 * storage. Only the creation, retrieval, and searching methods are supported.
 *
 * <p>Profile attributes are mapped to the <i>client</i> table as follows.
 *
 * <ul>
 *   <li>{@link UserDefinedAttributeNames#FIRST_NAME first name} is mapped to <i>first_name</i></li>
 *   <li>{@link UserDefinedAttributeNames#LAST_NAME last name} is mapped to <i>last_name</i></li>
 *   <li>{@link UserDefinedAttributeNames#EMAIL e-mail} is mapped to <i>email</i></li>
 *   <li>{@link UserDefinedAttributeNames#COMPANY company} is mapped to <i>company</i></li>
 *   <li>{@link UserDefinedAttributeNames#TITLE title} is mapped to <i>title</i></li>
 *   <li>The profile username is mapped to <i>client_id</i>. Note that usernames must be string-encoded
 *       non-negative long values.</li>
 * </ul>
 *
 * <p>The following configuration options are supported.
 *
 * <ul>
 *   <li><strong>specification_factory_namespace</strong> (required): the namespace containing the object factory
 *      specifications</li>
 *   <li><strong>db_connection_factory_key</strong> (required): the object factory key used to instantiate the DB
 *     connection factory</li>
 *   <li><strong>connection_name</strong> (required): the name used to retrieve connections from the DB connection
 *     factory</li>
 *   <li><strong>validator_key</strong> (optional): the object factory key used to instantiate the object
 *     validator; if omitted, no validation is done</li>
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class UnregisteredChatUserProfileInformixPersistence extends AbstractPersistenceWithValidator
    implements ChatUserProfilePersistence {
    /**
     * Constant representing the first name column in the <i>client</i> table.
     */
    public static final String FIRST_NAME_COLUMN = "first_name";

    /**
     * Constant representing the last name column in the <i>client</i> table.
     */
    public static final String LAST_NAME_COLUMN = "last_name";

    /**
     * Constant representing the company name column in the <i>client</i> table.
     */
    public static final String COMPANY_COLUMN = "company";

    /**
     * Constant representing the user title column in the <i>client</i> table.
     */
    public static final String TITLE_COLUMN = "title";

    /**
     * Constant representing the e-mail column in the <i>client</i> table.
     */
    public static final String EMAIL_COLUMN = "email";

    /**
     * The namespace used by the {@link #UnregisteredChatUserProfileInformixPersistence default constructor}.
     */
    public static final String DEFAULT_NAMESPACE =
        "com.cronos.im.persistence.UnregisteredChatUserProfileInformixPersistence";

    /**
     * The mapping from user-defined attribute names found in {@link UserDefinedAttributeNames
     * UserDefinedAttributeNames} to the corresponding column names in the <i>client_id</i> table. This member is
     * initialized at class initialization time, is immutable, and will not contain <code>null</code> or empty keys
     * or values.
     */
    private static final Map COLUMN_NAME_MAPPING = new HashMap();

    /**
     * Static initializer for <code>COLUMN_NAME_MAPPING</code>.
     */
    static {
        COLUMN_NAME_MAPPING.put(UserDefinedAttributeNames.FIRST_NAME, FIRST_NAME_COLUMN);
        COLUMN_NAME_MAPPING.put(UserDefinedAttributeNames.LAST_NAME, LAST_NAME_COLUMN);
        COLUMN_NAME_MAPPING.put(UserDefinedAttributeNames.COMPANY, COMPANY_COLUMN);
        COLUMN_NAME_MAPPING.put(UserDefinedAttributeNames.TITLE, TITLE_COLUMN);
        COLUMN_NAME_MAPPING.put(UserDefinedAttributeNames.EMAIL, EMAIL_COLUMN);
    }

    /**
     * The query used to insert a row into the <i>client</i> table.
     */
    private static final String INSERT_QUERY;

    static {
        StringBuffer insert = new StringBuffer("INSERT INTO client (client_id, ");
        insert.append(COLUMN_NAME_MAPPING.get(UserDefinedAttributeNames.FIRST_NAME));
        insert.append(", ");
        insert.append(COLUMN_NAME_MAPPING.get(UserDefinedAttributeNames.LAST_NAME));
        insert.append(", ");
        insert.append(COLUMN_NAME_MAPPING.get(UserDefinedAttributeNames.COMPANY));
        insert.append(", ");
        insert.append(COLUMN_NAME_MAPPING.get(UserDefinedAttributeNames.TITLE));
        insert.append(", ");
        insert.append(COLUMN_NAME_MAPPING.get(UserDefinedAttributeNames.EMAIL));
        insert.append(", create_date, modify_date, create_user, modify_user) "
                      + "VALUES (?, ?, ?, ?, ?, ?, CURRENT, CURRENT, USER, USER)");

        INSERT_QUERY = insert.toString();
    }

    /**
     * Creates a new <code>UnregisteredChatUserProfileInformixPersistence</code> based on the specified
     * configuration namespace. See the class documentation for a description of the configuration parameters.
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws ConfigurationException if the namespace is missing or invalid, a required property is missing, or
     *   one of the required objects cannot be instantiated
     */
    public UnregisteredChatUserProfileInformixPersistence(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Constructs a new <code>UnregisteredChatUserProfileInformixPersistence</code> with the specified parameters.
     *
     * @param connectionFactory the DB connection factory
     * @param connectionName the DB connection name
     * @param validator the profile validator or <code>null</code> if no validation is required
     * @throws IllegalArgumentException if <code>connectionFactory</code>, <code>connectionName</code> is
     *   <code>null</code> or <code>connectionName</code> is an empty string
     */
    public UnregisteredChatUserProfileInformixPersistence(DBConnectionFactory connectionFactory, String connectionName,
                                                          ObjectValidator validator) {
        super(connectionFactory, connectionName, validator);
    }

    /**
     * Equivalent to {@link #UnregisteredChatUserProfileInformixPersistence(String)
     * UnregisteredChatUserProfileInformixPersistence(DEFAULT_NAMESPACE)}.
     *
     * @throws ConfigurationException if the namespace is invalid or the connection factory or object validator
     */
    public UnregisteredChatUserProfileInformixPersistence() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * Enters the specified profile into the <i>client</i> table.
     *
     * @param profile the profile to create
     * @throws IllegalArgumentException if <code>profile</code> is <code>null</code> or the username cannot be
     *   parsed as a non-negative long
     * @throws DuplicateProfileException if the profile already exists in the database
     * @throws ChatUserProfilePersistenceException if a database error occurs other than the profile already existing
     * @throws ChatUserProfileValidationException if the profile fails validation
     */
    public void createProfile(ChatUserProfile profile)
        throws ChatUserProfilePersistenceException, DuplicateProfileException {
        ParameterHelpers.checkParameter(profile, "profile");

        // if a validator was configured, use it to validate the profile
        ObjectValidator validator = getValidator();
        if (validator != null && !validator.valid(profile)) {
            throw new ChatUserProfileValidationException(validator.getMessage(profile));
        }

        String username = profile.getUsername();
        Connection connection = getConnection();
        try {
            try {
                // make sure the profile doesn't already exist
                PreparedStatement statement =
                    connection.prepareStatement("SELECT client_id FROM client WHERE client_id = ?");

                try {
                    statement.setLong(1, parseUsername(username));

                    ResultSet results = statement.executeQuery();
                    try {
                        if (results.next()) {
                            throw new DuplicateProfileException("profile for '" + username + "' already exists");
                        }
                    } finally {
                        InformixPersistenceHelpers.closeResults(results);
                    }
                } finally {
                    InformixPersistenceHelpers.closeStatement(statement);
                }

                // create the profile
                insertProfile(connection, profile);
            } catch (SQLException ex) {
                throw new ChatUserProfilePersistenceException("error creating profile: " + ex.getMessage(), ex);
            }
        } finally {
            InformixPersistenceHelpers.closeConnection(connection);
        }
    }

    /**
     * Deletes the specified username from the persistent storage. This operation is not supported by this
     * implementation and will always throw <code>UnsupportedOperationException</code>.
     *
     * @param username the username to delete
     * @throws UnsupportedOperationException always thrown by this implementation
     */
    public void deleteProfile(String username) {
        throw new UnsupportedOperationException("deleteProfile not supported by "
                                                + "UnregisteredChatUserProfileInformixPersistence");
    }

    /**
     * Updates an existing profile in the persistent storage. This operation is not supported by this
     * implementation and will always throw <code>UnsupportedOperationException</code>.
     *
     * @param profile the profile to update
     * @throws UnsupportedOperationException always thrown by this implementation
     */
    public void updateProfile(ChatUserProfile profile) {
        throw new UnsupportedOperationException("updateProfile not supported by "
                                                + "UnregisteredChatUserProfileInformixPersistence");
    }

    /**
     * Retrieves the profile for the specified username. For unregistered users, the username must be a
     * string-encoded non-negative long value. The type of the returned profile will be set to <i>Unregistered</i>
     * and the properties will be populated based on the corresponding columns of the <i>client</i> table.
     *
     * @param username the username to retrieve
     * @return the profile for the specified username
     * @throws IllegalArgumentException if username cannot be converted to a non-negative long
     * @throws ProfileNotFoundException if the specified username does not exist
     * @throws ChatUserProfilePersistenceException if a database error occurs other than the profile not being found
     */
    public ChatUserProfile getProfile(String username)
        throws ProfileNotFoundException, ChatUserProfilePersistenceException {
        ParameterHelpers.checkParameter(username, "username");

        long uid = parseUsername(username);

        Connection connection = getConnection();
        try {
            return getProfile(connection, username, uid);
        } finally {
            InformixPersistenceHelpers.closeConnection(connection);
        }
    }

    /**
     * A batch version of {@link #getProfile(String) getProfile}. Profiles in the returned array map to the
     * corresponding usernames in the input array. If a particular profile cannot be found, the corresponding
     * position in the returned array will contain a <code>null</code>.
     *
     * @param usernames the usernames to retrieve
     * @return an array of profiles corresponding to the names in the input array
     * @throws IllegalArgumentException if <code>usernames</code> is <code>null</code> or contains
     *   <code>null</code> or empty strings
     * @throws ProfileNotFoundException if no profiles could be located
     * @throws ChatUserProfilePersistenceException if a database error occurs other than no profiles being located
     */
    public ChatUserProfile[] getProfiles(String[] usernames)
        throws ProfileNotFoundException, ChatUserProfilePersistenceException {
        ParameterHelpers.checkParameter(usernames, "username array");

        for (int idx = 0; idx < usernames.length; ++idx) {
            ParameterHelpers.checkParameter(usernames[idx], "username");
        }

        Connection connection = getConnection();
        ResultSet results = null;
        PreparedStatement statement = null;
        try {
            String sql = "SELECT * FROM client WHERE client_id IN (IN_CLAUSE)";
            int realCount = 0;
            List validUsername = new ArrayList();
            StringBuffer questionMarks = new StringBuffer();
            for (int i = 0; i < usernames.length; i++) {
                // check here first if the username is parseable
                try {
                    Long.parseLong(usernames[i]);
                    validUsername.add(usernames[i]);
                    if (realCount != 0) {
                        questionMarks.append(", ");
                    }
                    questionMarks.append("?");
                    realCount++;
                } catch (NumberFormatException ex) {
                    continue;
                }
            }
            // if the validUsername is empty, this means no valid Username was passed
            if(validUsername.isEmpty()) {
                throw new IllegalArgumentException("No valid Unregistered Client username was supplied!");
            }
            // replace with question marks
            sql = sql.replaceFirst("IN_CLAUSE", questionMarks.toString());
            // instantiate the preparedstatement
            statement = connection.prepareStatement(sql);
            // replace the question marks with real value
            for(int i = 0; i < validUsername.size(); i++) {
                statement.setLong((i+1), Long.parseLong((String) validUsername.get(i)));
            }
            // execute the query
            results = statement.executeQuery();
            // will hold profile results
            Map resultMap = new HashMap();
            while (results.next()) {
                // get the handle
                long handle = results.getLong("client_id");
                // instantiate a registered profile
                ChatUserProfile profile = new ChatUserProfile(Long.toString(handle), 
                        InformixProfileKeyManager.TYPE_UNREGISTERED);
                // populate the properties
                InformixPersistenceHelpers.populateProperties(profile, results, COLUMN_NAME_MAPPING);
                // add to profile List
                resultMap.put(new Long(handle).toString(), profile);
            }

            if (resultMap.isEmpty()) {
                throw new ProfileNotFoundException("no profiles were found");
            }
            
            ChatUserProfile[] ret = new ChatUserProfile[usernames.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (ChatUserProfile) resultMap.get(usernames[i]);
            }

            return ret;
            
        } catch(SQLException sqe) {
            throw new ChatUserProfilePersistenceException("error retrieving profile: " + sqe.getMessage(), sqe);
        } finally {
            if(results != null) {
                InformixPersistenceHelpers.closeResults(results);
            }
            if(statement != null) {
                InformixPersistenceHelpers.closeStatement(statement);
            }
            InformixPersistenceHelpers.closeConnection(connection);
        }
    }

    /**
     * Retrieves the profiles for the specified users filtered by the specified criteria. The criteria keys should
     * be the attributes defined in {@link UserDefinedAttributeNames UserDefinedAttributeNames} and the values
     * should be strings or a list of strings used to filter the results. If a list is supplied, profiles that
     * match any of the values of the list will be included in the results.
     *
     * @param criteria a mapping of attribute names to strings or list of strings to be used to filter the results
     * @param unregisteredUsers the usernames to retrieve
     * @return a (possibly empty) list of profiles for the specified users that match the specified criteria; the
     *   order of elements does not correspond to the order of elements in the input array
     * @throws IllegalArgumentException if either argumnet is <code>null</code> or <code>criteria</code> contains
     *   <code>null</code> keys or values or <code>criteria</code> contains values that are not
     *   non-<code>null</code>, non-empty strings or a list of non-<code>null</code>, non-empty strings or any
     *   elements of <code>unregisteredUsers</code> cannot be converted to a non-negative long
     * @throws ChatUserProfilePersistenceException if a database error occurs
     */
    public ChatUserProfile[] searchProfiles(Map criteria, String[] unregisteredUsers)
        throws ChatUserProfilePersistenceException {
        ParameterHelpers.validateCriteria(criteria);
        ParameterHelpers.checkParameter(unregisteredUsers, "unregistered users array");
        // the contents of unregisteredUsers will be checked below

        if (unregisteredUsers.length == 0) {
            return new ChatUserProfile[0];
        }

        List args = new ArrayList(); // the arguments to the prepared statement

        // create the query
        String query = buildSearchQuery(args, criteria, unregisteredUsers);

        Connection connection = getConnection();
        try {
            try {
                // create and populate the prepared statement
                PreparedStatement statement = connection.prepareStatement(query);

                try {
                    int idx = 1;
                    for (Iterator it = args.iterator(); it.hasNext();) {
                        statement.setObject(idx++, it.next());
                    }

                    // build ChatUserProfile for each row in the results
                    ResultSet results = statement.executeQuery();
                    try {
                        List ret = new ArrayList();
                        while (results.next()) {
                            ChatUserProfile profile =
                                new ChatUserProfile(String.valueOf(results.getLong("client_id")),
                                                    InformixProfileKeyManager.TYPE_UNREGISTERED);

                            InformixPersistenceHelpers.populateProperties(profile, results, COLUMN_NAME_MAPPING);

                            ret.add(profile);
                        }

                        return (ChatUserProfile[]) ret.toArray(new ChatUserProfile[ret.size()]);
                    } finally {
                        InformixPersistenceHelpers.closeResults(results);
                    }
                } finally {
                    InformixPersistenceHelpers.closeStatement(statement);
                }
            } catch (SQLException ex) {
                throw new ChatUserProfilePersistenceException("SQL error: " + ex.getMessage(), ex);
            }
        } finally {
            InformixPersistenceHelpers.closeConnection(connection);
        }
    }

    /**
     * Prepared the search query used by {@link #searchProfiles searchProfiles}.
     *
     * @param args an out parameter that will be populated with the arguments to the prepared statement
     * @param criteria the search criteria
     * @param unregisteredUsers the users to search for
     * @return the SQL query used to execute the search
     */
    private String buildSearchQuery(List args, Map criteria, String[] unregisteredUsers) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * from client WHERE ");
        InformixPersistenceHelpers.addToQuery(query, args, criteria,
                                              UserDefinedAttributeNames.FIRST_NAME, COLUMN_NAME_MAPPING);
        InformixPersistenceHelpers.addToQuery(query, args, criteria,
                                              UserDefinedAttributeNames.LAST_NAME, COLUMN_NAME_MAPPING);
        InformixPersistenceHelpers.addToQuery(query, args, criteria,
                                              UserDefinedAttributeNames.EMAIL, COLUMN_NAME_MAPPING);
        InformixPersistenceHelpers.addToQuery(query, args, criteria,
                                              UserDefinedAttributeNames.TITLE, COLUMN_NAME_MAPPING);
        InformixPersistenceHelpers.addToQuery(query, args, criteria,
                                              UserDefinedAttributeNames.COMPANY, COLUMN_NAME_MAPPING);

        query.append("client_id IN (");

        for (int idx = 0; idx < unregisteredUsers.length; ++idx) {
            ParameterHelpers.checkParameter(unregisteredUsers[idx], "username");

            if (idx > 0) {
                query.append(", ");
            }
            query.append("?");
            args.add(new Long(parseUsername(unregisteredUsers[idx])));
        }
        query.append(")");

        return query.toString();
    }

    /**
     * A helper method that parses a username as a long.
     *
     * @param username the username
     * @return <code>username</code> converted to a long
     * @throws IllegalArgumentException if <code>username</code> is <code>null</code> or cannot be converted to a
     *   non-negative long
     */
    private static long parseUsername(String username) {
        try {
            long id = Long.parseLong(username);
            if (id < 0) {
                throw new IllegalArgumentException("ID must be a non-negative long");
            }

            return id;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("username must be convertible to a long");
        }
    }

    /**
     * Retrieves the profile for the specified user.
     *
     * @param connection the DB connection
     * @param username the string username
     * @param uid the long representation of <code>username</code>
     * @return the profile for the specified username
     * @throws ProfileNotFoundException if the specified username does not exist
     * @throws ChatUserProfilePersistenceException if a database error occurs other than the profile not being found
     */
    private ChatUserProfile getProfile(Connection connection, String username, long uid)
        throws ProfileNotFoundException, ChatUserProfilePersistenceException {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM client WHERE client_id = ?");
            try {
                statement.setLong(1, uid);

                ResultSet results = statement.executeQuery();
                try {
                    if (!results.next()) {
                        throw new ProfileNotFoundException("no profile for username '" + username + "'");
                    }

                    ChatUserProfile profile = new ChatUserProfile(username,
                                                                  InformixProfileKeyManager.TYPE_UNREGISTERED);

                    InformixPersistenceHelpers.populateProperties(profile, results, COLUMN_NAME_MAPPING);

                    return profile;
                } finally {
                    InformixPersistenceHelpers.closeResults(results);
                }
            } finally {
                InformixPersistenceHelpers.closeStatement(statement);
            }
        } catch (SQLException ex) {
            throw new ChatUserProfilePersistenceException("SQL error while retrieving profile: "
                                                          + ex.getMessage(), ex);
        }
    }

    /**
     * Inserts the specified profile into the <i>client</i> table.
     *
     * @param connection the DB connection
     * @param profile the profile to insert
     * @throws SQLException if a database error occurs
     * @throws ChatUserProfilePersistenceException if a database error occurs
     */
    private void insertProfile(Connection connection, ChatUserProfile profile)
        throws SQLException, ChatUserProfilePersistenceException {
        PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);

        long id = parseUsername(profile.getUsername());

        try {
            int idx = 1;
            statement.setLong(idx++, id);
            statement.setString(idx++, getProperty(profile, UserDefinedAttributeNames.FIRST_NAME));
            statement.setString(idx++, getProperty(profile, UserDefinedAttributeNames.LAST_NAME));
            statement.setString(idx++, getProperty(profile, UserDefinedAttributeNames.COMPANY));
            statement.setString(idx++, getProperty(profile, UserDefinedAttributeNames.TITLE));
            statement.setString(idx++, getProperty(profile, UserDefinedAttributeNames.EMAIL));

            if (statement.executeUpdate() == 0) {
                throw new ChatUserProfilePersistenceException("insert query failed");
            }
        } finally {
            InformixPersistenceHelpers.closeStatement(statement);
        }
    }

    /**
     * Helper method that creates a database connection and rethrows any exceptions as
     * <code>ChatUserProfilePersistenceException</code>.
     *
     * @return a new database connection
     * @throws ChatUserProfilePersistenceException if the connection cannot be created
     */
    private Connection getConnection() throws ChatUserProfilePersistenceException {
        try {
            return getConnectionFactory().createConnection(getConnectionName());
        } catch (DBConnectionException ex) {
            throw new ChatUserProfilePersistenceException("error creating connection: " + ex.getMessage(), ex);
        }
    }

    /**
     * Helper method to retrieve the first value for the specified property of the specified profile or
     * <code>null</code> if no such value exists.
     *
     * @param profile the profile
     * @param property the property to retrieve
     * @return the first value for the specified property of the specified profile or <code>null</code> if no such
     *   value exists
     */
    private static String getProperty(ChatUserProfile profile, String property) {
        String[] prop = profile.getPropertyValue(property);
        return (prop.length == 0 ? null : prop[0]);
    }
}
