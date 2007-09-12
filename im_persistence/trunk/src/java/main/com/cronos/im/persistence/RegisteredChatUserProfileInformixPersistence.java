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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.ChatUserProfilePersistence;
import com.topcoder.chat.user.profile.ChatUserProfilePersistenceException;
import com.topcoder.chat.user.profile.ProfileNotFoundException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

/**
 * <p>An implementation of <code>AbstractPersistence</code> that uses an Informix database. This implementation only
 * supports retrieval and searching.
 *
 * <p>The following configuration parameters are supported.
 *
 * <ul>
 *   <li><strong>specification_factory_namespace</strong> (required): the namespace containing the object factory
 *      specifications</li>
 *   <li><strong>db_connection_factory_key</strong> (required): the object factory key used to instantiate the DB
 *     connection factory</li>
 *   <li><strong>connection_name</strong> (required): the name used to retrieve connections from the DB connection
 *     factory</li>
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class RegisteredChatUserProfileInformixPersistence extends AbstractPersistence
    implements ChatUserProfilePersistence {
    /**
     * Constant representing the first name column in the <i>user</i> table.
     */
    public static final String FIRST_NAME_COLUMN = "first_name";

    /**
     * Constant representing the last name column in the <i>user</i> table.
     */
    public static final String LAST_NAME_COLUMN = "last_name";

    /**
     * Constant representing the company column in the <i>company</i> table.
     */
    public static final String COMPANY_COLUMN = "company_name";

    /**
     * Constant representing the title column in the <i>contact</i> table.
     */
    public static final String TITLE_COLUMN = "title";

    /**
     * Constant representing the e-mail column in the <i>email</i> table.
     */
    public static final String EMAIL_COLUMN = "address";

    /**
     * The namespace used by the {@link #RegisteredChatUserProfileInformixPersistence default constructor} to
     * configure new instances.
     */
    public static final String DEFAULT_NAMESPACE =
        "com.cronos.im.persistence.RegisteredChatUserProfileInformixPersistence";

    /**
     * Mapping from the attribute names defined in {@link UserDefinedAttributeNames UserDefinedAttributeNames} to
     * the appropriate column names.
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
     * Constructs a new <code>RegisteredChatUserProfileInformixPersistence</code> based on the specified
     * configuration namespace. See the class documentation for a description of the configuration parameters.
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws ConfigurationException if the namespace is missing or invalid or a required attribute is missing or
     *   a required object cannot be instantiated
     */
    public RegisteredChatUserProfileInformixPersistence(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Constructs a new <code>RegisteredChatUserProfileInformixPersistence</code> with the specified connection
     * factory and connection name.
     *
     * @param connectionFactory the database connection factory
     * @param connectionName the database connection name
     * @throws IllegalArgumentException if either argument is <code>null</code> or <code>connectionName</code> is
     *   an empty string
     */
    public RegisteredChatUserProfileInformixPersistence(DBConnectionFactory connectionFactory, String connectionName) {
        super(connectionFactory, connectionName);
    }

    /**
     * Equivalent to {@link #RegisteredChatUserProfileInformixPersistence(String)
     * RegisteredChatUserProfileInformixPersistence(DEFAULT_NAMESPACE)}.
     *
     * @throws ConfigurationException if the namespace is missing or invalid or a required attribute is missing or
     *   a required object cannot be instantiated
     */
    public RegisteredChatUserProfileInformixPersistence() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * Creates a new profile in the Informix database. This operation is not supported by this implementation.
     *
     * @param profile the profile to create
     * @throws UnsupportedOperationException always thrown by this implementation
     */
    public void createProfile(ChatUserProfile profile) {
        throw new UnsupportedOperationException("createProfile not supported by "
                                                + "RegisteredChatUserProfileInformixPersistence");
    }

    /**
     * Deletes the specified profile from the Informix database. This operation is not supported by this
     * implementation.
     *
     * @param username the username to delete
     * @throws UnsupportedOperationException always thrown by this implementation
     */
    public void deleteProfile(String username) {
        throw new UnsupportedOperationException("deleteProfile not supported by "
                                                + "RegisteredChatUserProfileInformixPersistence");
    }

    /**
     * Updates the specified profile in the Informix database. This operation is not supported by this
     * implementation.
     *
     * @param profile the profile to update
     * @throws UnsupportedOperationException always thrown by this implementation
     */
    public void updateProfile(ChatUserProfile profile) {
        throw new UnsupportedOperationException("updateProfile not supported by "
                                                + "RegisteredChatUserProfileInformixPersistence");
    }

    /**
     * Retrieves the chat profile for the specified username.
     *
     * @param username the username to retrieve
     * @return the profile for the specified username
     * @throws IllegalArgumentException if <code>username</code> is <code>null</code> or an empty string
     * @throws ProfileNotFoundException if the profile can not be located in the database
     * @throws ChatUserProfilePersistenceException if a database error occurs other than the profile not being found
     */
    public ChatUserProfile getProfile(String username)
        throws ProfileNotFoundException, ChatUserProfilePersistenceException {
        ParameterHelpers.checkParameter(username, "username");

        Connection connection = getConnection();
        try {
            return getProfile(connection, username);
        } finally {
            InformixPersistenceHelpers.closeConnection(connection);
        }
    }

    /**
     * A batch version of {@link #getProfile(String) getProfile}. Elements in the returned array correspond to the
     * elements at the same index in the input array. If no profile is found for a particular username, a
     * <code>null</code> element will be returned in that slot.
     *
     * @param usernames the usernames to retrieve
     * @return the profiles corresponding to the specified usernames
     * @throws IllegalArgumentException if <code>usernames</code> is <code>null</code> or contains
     *   <code>null</code> or empty strings
     * @throws ProfileNotFoundException if no profile could be located in the database for any of the usernames
     * @throws ChatUserProfilePersistenceException if a database error occurs other than no profiles being located
     */
    public ChatUserProfile[] getProfiles(String[] usernames)
        throws ProfileNotFoundException, ChatUserProfilePersistenceException {
        ParameterHelpers.checkParameter(usernames, "usernames");

        for (int idx = 0; idx < usernames.length; ++idx) {
            ParameterHelpers.checkParameter(usernames[idx], "username");
        }

        if (usernames.length == 0) {
            throw new ProfileNotFoundException("no profiles match any of the usernames");
        }

        Connection connection = getConnection();
        ResultSet results = null;
        PreparedStatement statement = null;
        try {
            String sql = "select * from user join email " +
                    " on user.user_id = email.user_id " + "left join " +
                    " (contact LEFT JOIN company ON contact.company_id = " +
                    " company.company_id) ON user.user_id = contact.contact_id " +
                    " where user.handle IN (IN_CLAUSE)";
    
            StringBuffer questionMarks = new StringBuffer();
            for (int i = 0; i < usernames.length; i++) {
                if (i != 0) {
                    questionMarks.append(", ");
                }
                questionMarks.append("?");
            }
            // replace with question marks
            sql = sql.replaceFirst("IN_CLAUSE", questionMarks.toString());
            // instantiate the preparedstatement
            statement = connection.prepareStatement(sql);
            
            for(int i = 0; i < usernames.length; i++) {
                statement.setString((i+1), usernames[i]);
            }

            results = statement.executeQuery();
            // will hold profile results
            Map resultMap = new HashMap();
            while (results.next()) {
                // get the handle
                String handle = results.getString("handle");
                // instantiate a registered profile
                ChatUserProfile profile = new ChatUserProfile(handle, 
                        InformixProfileKeyManager.TYPE_REGISTERED);
                // populate the properties
                InformixPersistenceHelpers.populateProperties(profile, results, COLUMN_NAME_MAPPING);
                // add to profile List
                resultMap.put(handle, profile);
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
     * @param registeredUsers the usernames to retrieve
     * @return a (possibly empty) list of profiles for the specified users that match the specified criteria; the
     *   order of elements does not correspond to the order of elements in the input array
     * @throws IllegalArgumentException if either argumnet is <code>null</code> or <code>criteria</code> contains
     *   <code>null</code> keys or values or <code>criteria</code> contains values that are not
     *   non-<code>null</code>, non-empty strings or a list of non-<code>null</code>, non-empty strings or any
     *   elements of <code>registeredUsers</code> are <code>null</code> or empty
     * @throws ChatUserProfilePersistenceException if a database error occurs
     */
    public ChatUserProfile[] searchProfiles(Map criteria, String[] registeredUsers)
        throws ChatUserProfilePersistenceException {
        ParameterHelpers.validateCriteria(criteria);
        ParameterHelpers.checkParameter(registeredUsers, "registered usernames");

        if (registeredUsers.length == 0) {
            return new ChatUserProfile[0];
        }

        for (int idx = 0; idx < registeredUsers.length; ++idx) {
            ParameterHelpers.checkParameter(registeredUsers[idx], "username");
        }

        Connection connection = getConnection();
        try {
            List preparedStatementArgs = new ArrayList();

            // build the query and the prepared statement arg
            String query = buildSearchQuery(criteria, registeredUsers, preparedStatementArgs);

            try {
                // create the prepared statement and populate the args
                PreparedStatement statement = connection.prepareStatement(query);

                try {
                    int idx = 1;
                    for (Iterator it = preparedStatementArgs.iterator(); it.hasNext();) {
                        statement.setObject(idx++, it.next());
                    }

                    ResultSet results = statement.executeQuery();
                    try {
                        // only allow duplicate names if title or company was searched for
                        boolean allowDuplicateUsernames = (criteria.containsKey(UserDefinedAttributeNames.TITLE)
                                                           || criteria.containsKey(UserDefinedAttributeNames.COMPANY));
                        List profiles = new ArrayList();
                        Set usernames = new HashSet();

                        // build an a list of ChatUserProfiles based on the results
                        while (results.next()) {
                            String username = results.getString("handle");
                            if (allowDuplicateUsernames || !usernames.contains(username)) {
                                usernames.add(username);

                                ChatUserProfile profile =
                                    new ChatUserProfile(username, InformixProfileKeyManager.TYPE_REGISTERED);

                                InformixPersistenceHelpers.populateProperties(profile, results, COLUMN_NAME_MAPPING);
                                profiles.add(profile);
                            }
                        }

                        return (ChatUserProfile[]) profiles.toArray(new ChatUserProfile[profiles.size()]);
                    } finally {
                        InformixPersistenceHelpers.closeResults(results);
                    }
                } finally {
                    InformixPersistenceHelpers.closeStatement(statement);
                }
            } catch (SQLException ex) {
                throw new ChatUserProfilePersistenceException("error searching profiles: " + ex.getMessage(), ex);
            }
        } finally {
            InformixPersistenceHelpers.closeConnection(connection);
        }
    }

    /**
     * Builds the search query used by {@link #searchProfiles searchProfiles}.
     *
     * @param criteria the search criteria
     * @param registeredUsers the users to search
     * @param preparedStatementArgs an out parameter that will be populated with the prepared statement args
     * @return the search query to use
     */
    private static String buildSearchQuery(Map criteria, String[] registeredUsers, List preparedStatementArgs) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * FROM email, user LEFT JOIN ");
        query.append("(contact LEFT JOIN company ON contact.company_id = company.company_id) ");
        query.append("ON user.user_id = contact.contact_id ");
        query.append("WHERE user.handle IN (");

        for (int idx = 0; idx < registeredUsers.length; ++idx) {
            if (idx > 0) {
                query.append(", ");
            }

            query.append("?");
            preparedStatementArgs.add(registeredUsers[idx]);
        }

        query.append(") AND ");
        InformixPersistenceHelpers.addToQuery(query, preparedStatementArgs, criteria,
                                              UserDefinedAttributeNames.FIRST_NAME, COLUMN_NAME_MAPPING);
        InformixPersistenceHelpers.addToQuery(query, preparedStatementArgs, criteria,
                                              UserDefinedAttributeNames.LAST_NAME, COLUMN_NAME_MAPPING);
        InformixPersistenceHelpers.addToQuery(query, preparedStatementArgs, criteria,
                                              UserDefinedAttributeNames.EMAIL, COLUMN_NAME_MAPPING);
        InformixPersistenceHelpers.addToQuery(query, preparedStatementArgs, criteria,
                                              UserDefinedAttributeNames.TITLE, COLUMN_NAME_MAPPING);
        InformixPersistenceHelpers.addToQuery(query, preparedStatementArgs, criteria,
                                              UserDefinedAttributeNames.COMPANY, COLUMN_NAME_MAPPING);

        query.append(" user.user_id = email.user_id");

        return query.toString();
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
     * Retrieves the profile for the specified username.
     *
     * @param connection the DB connection
     * @param username the username to retrieve
     * @return the profile for the specified username
     * @throws ProfileNotFoundException if the profile can not be located in the database
     * @throws ChatUserProfilePersistenceException if a database error occurs other than the profile not being found
     */
    private static ChatUserProfile getProfile(Connection connection, String username)
        throws ProfileNotFoundException, ChatUserProfilePersistenceException {
        try {
            PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM email, user LEFT JOIN "
                                            + "(contact LEFT JOIN company ON contact.company_id = "
                                            + "company.company_id) ON user.user_id = contact.contact_id "
                                            + "WHERE handle = ? AND "
                                            + "user.user_id = email.user_id");
            try {
                statement.setString(1, username);

                ResultSet results = statement.executeQuery();
                try {
                    if (!results.next()) {
                        throw new ProfileNotFoundException("username '" + username + "' not found");
                    }

                    ChatUserProfile profile = new ChatUserProfile(username, InformixProfileKeyManager.TYPE_REGISTERED);

                    InformixPersistenceHelpers.populateProperties(profile, results, COLUMN_NAME_MAPPING);

                    return profile;
                } finally {
                    InformixPersistenceHelpers.closeResults(results);
                }
            } finally {
                InformixPersistenceHelpers.closeStatement(statement);
            }
        } catch (SQLException ex) {
            throw new ChatUserProfilePersistenceException("error retrieving profile: " + ex.getMessage(), ex);
        }
    }
}
