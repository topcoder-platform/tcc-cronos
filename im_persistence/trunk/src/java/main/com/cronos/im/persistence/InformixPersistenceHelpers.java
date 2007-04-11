/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.ChatUserProfilePersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Contains functionality common to both <code>UnregisteredChatUserProfileInformixPersistence</code> and
 * <code>RegisteredChatUserProfileInformixPersistence</code>.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

class InformixPersistenceHelpers {
    /**
     * Private constructor to prevent instantiation.
     */
    private InformixPersistenceHelpers() {
    }

    /**
     * If the specified attribute has a value or values in the criteria, this method adds the appropriate clause to
     * the query and appends the values to the list of prepared statement arguments.
     *
     * @param query the SQL query being build
     * @param args a list of arguments to the prepared statement
     * @param criteria the criteria used to filter the search
     * @param attribute the attribute to add to the query
     * @param columnMap the column/name mapping
     */
    static void addToQuery(StringBuffer query, List args, Map criteria, String attribute, Map columnMap) {
        Object value = criteria.get(attribute);
        if (value == null) {
            return;
        }

        String column = (String) columnMap.get(attribute);

        if (value instanceof String) {
            query.append(column);
            query.append(" = ? AND ");
            args.add(value);
        } else {
            // the value must be a list
            List list = (List) value;
            if (list.size() == 0) {
                return;
            }

            query.append("(");
            boolean firstOne = true;
            for (Iterator it = list.iterator(); it.hasNext();) {
                if (firstOne) {
                    firstOne = false;
                } else {
                    query.append(" OR ");
                }

                query.append(column + " = ?");
                args.add(it.next());
            }
            query.append(") AND ");
        }
    }

    /**
     * Closes the database connection and rethrows any exceptions as
     * <code>ChatUserProfilePersistenceException</code>.
     *
     * @param connection the database connection to close
     * @throws ChatUserProfilePersistenceException if a SQL error occurs
     */
    static void closeConnection(Connection connection) throws ChatUserProfilePersistenceException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new ChatUserProfilePersistenceException("error closing connection: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the result set and rethrows any exceptions as <code>ChatUserProfilePersistenceException</code>.
     *
     * @param results the result set to close
     * @throws ChatUserProfilePersistenceException if a SQL error occurs
     */
    static void closeResults(ResultSet results) throws ChatUserProfilePersistenceException {
        try {
            results.close();
        } catch (SQLException ex) {
            throw new ChatUserProfilePersistenceException("error closing result set: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the prepared statement and rethrows any exceptions as
     * <code>ChatUserProfilePersistenceException</code>.
     *
     * @param statement the statement to close
     * @throws ChatUserProfilePersistenceException if a SQL error occurs
     */
    static void closeStatement(PreparedStatement statement) throws ChatUserProfilePersistenceException {
        try {
            statement.close();
        } catch (SQLException ex) {
            throw new ChatUserProfilePersistenceException("error closing prepared statement: " + ex.getMessage(), ex);
        }
    }

    /**
     * Populates the properties of the specified profile based on the SQL result set.
     *
     * @param profile the profile
     * @param results the query results
     * @param columnMap the column/name mapping
     * @throws SQLException if a database error occurs
     */
    static void populateProperties(ChatUserProfile profile, ResultSet results, Map columnMap)
        throws SQLException {
        // first name
        String firstName = results.getString((String) columnMap.get(UserDefinedAttributeNames.FIRST_NAME));
        profile.setProperty(UserDefinedAttributeNames.FIRST_NAME, firstName);

        // last name
        String lastName = results.getString((String) columnMap.get(UserDefinedAttributeNames.LAST_NAME));
        profile.setProperty(UserDefinedAttributeNames.LAST_NAME, lastName);

        // e-mail
        String email = results.getString((String) columnMap.get(UserDefinedAttributeNames.EMAIL));
        profile.setProperty(UserDefinedAttributeNames.EMAIL, email);

        // name
        profile.setProperty(UserDefinedAttributeNames.NAME, firstName + lastName);

        // title
        String title = results.getString((String) columnMap.get(UserDefinedAttributeNames.TITLE));
        if (title != null) {
            profile.setProperty(UserDefinedAttributeNames.TITLE, title);
        }

        // company
        String company = results.getString((String) columnMap.get(UserDefinedAttributeNames.COMPANY));
        if (company != null) {
            profile.setProperty(UserDefinedAttributeNames.COMPANY, company);
        }
    }
}
