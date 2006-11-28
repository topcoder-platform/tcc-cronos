/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.DuplicateEntryException;
import com.orpheus.administration.persistence.EntryNotFoundException;
import com.orpheus.administration.persistence.InstantiationException;
import com.orpheus.administration.persistence.Message;
import com.orpheus.administration.persistence.MessageDAO;
import com.orpheus.administration.persistence.ObjectFactoryHelpers;
import com.orpheus.administration.persistence.ParameterHelpers;
import com.orpheus.administration.persistence.PersistenceException;
import com.orpheus.administration.persistence.SearchCriteriaDTO;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>An implementation of <code>MessageDAO</code> that uses SQL Server as the persistent storage.
 *
 * <p>This class has five configuration parameters.
 *
 * <ul>
 *   <li><strong>specNamespace</strong> (required): the object factory specification namespace to use when creating
 *     the DB connection factory</li>
 *   <li><strong>factoryKey</strong> (required): the object factory key to use when creating the DB connection
 *     factory</li>
 *   <li><strong>name</strong> (optional): the name of the database connection to use (if not present, the default
 *     connection is used)</li>
 *   <li><strong>searchNamespace</strong> (required): the name of the search bundle manager configuration
 *     namespace</li>
 *   <li><strong>searchBundle</strong> (required): the name of the search bundle to use to find messages</li>
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and therefore thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class SQLServerMessageDAO implements MessageDAO {
    /**
     * SQL statement to select messages that match a specified GUID.
     */
    private static final String MESSAGES_BY_ID = "SELECT COUNT(*) FROM message WHERE guid = ?";

    /**
     * SQL statement to insert a new message.
     */
    private static final String INSERT_MESSAGE =
        "INSERT INTO message (guid, category, content_type, update_time, content) VALUES (?, ?, ?, ?, ?)";

    /**
     * SQL statement to update a message.
     */
    private static final String UPDATE_MESSAGE =
        "UPDATE message SET category = ?, content_type = ?, update_time = ?, content = ? WHERE guid = ?";

    /**
     * SQL statement to delete a message.
     */
    private static final String DELETE_MESSAGE = "DELETE FROM message WHERE guid = ?";

    /**
     * The database connection factory used to obtain connections for performing the requested operations. This
     * member is initialized in the constructor, is immutable, and will never be <code>null</code>.
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * The name to use when retrieving connections from the {@link #connectionFactory connection factory}. If
     * <code>null</code>, then the default connection will be retrieved.
     */
    private final String connectionName;

    /**
     * The search bundle that is used by {@link #findMessages findMessages}. This member is initialized in the
     * constructor, is immutable, and will never be <code>null</code>.
     */
    private final SearchBundle searchBundle;

    /**
     * Creates a new <code>SQLServerMessageDAO</code> configued using the specified namespace. See the class
     * documentation for a description of the configuration parameters.
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws InstantiationException if an error occurs while configuring the instance
     */
    public SQLServerMessageDAO(String namespace) throws InstantiationException {
        ParameterHelpers.checkString(namespace, "SQL server message DAO namespace");

        // create the connection factory
        connectionFactory = (DBConnectionFactory)
            ObjectFactoryHelpers.instantiateObject(namespace, "specNamespace", "factoryKey",
                                                   DBConnectionFactory.class);

        // populate the connection name
        ConfigManager manager = ConfigManager.getInstance();
        try {
            connectionName = manager.getString(namespace, "name");
        } catch (UnknownNamespaceException ex) {
            throw new InstantiationException("unknown namespace '" + namespace + "'");
        }

        try {
            // create the search bundle
            SearchBundleManager sbm =
                new SearchBundleManager(ObjectFactoryHelpers.getProperty(namespace, "searchNamespace"));
            searchBundle = sbm.getSearchBundle(ObjectFactoryHelpers.getProperty(namespace, "searchBundle"));
            if (searchBundle == null) {
                throw new InstantiationException("failed to obtain search bundle");
            }
        } catch (SearchBuilderConfigurationException ex) {
            throw new InstantiationException("error configuring search builder: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves messages from the data stored matching the specified criteria.
     *
     * @param criteria the criteria for filtering the returned messages
     * @return a possibly empty array containing the messages that match the specified criteria
     * @throws PersistenceException if an error occurs while accessing the data store
     * @throws IllegalArgumentException if <code>criteria</code> is <code>null</code>
     */
    public Message[] findMessages(SearchCriteriaDTO criteria) throws PersistenceException {
        if (criteria == null) {
            throw new IllegalArgumentException("search criteria must not be null");
        }

        // translate the DTO to the VO
        Filter filter = RSSSearchCriteriaTranslator.convertFilter(criteria.getSearchFilter());

        CustomResultSet results;
        try {
            // perform the search
            results = (CustomResultSet) searchBundle.search(filter);
        } catch (SearchBuilderException ex) {
            throw new PersistenceException("error searching messages: " + ex.getMessage(), ex);
        }

        List ret = new ArrayList();

        try {
            // process the results
            while (results.next()) {
                ret.add(new MessageImpl(results.getString("guid"), results.getString("category"),
                                        results.getString("content_type"), results.getString("content"),
                                        new Date(results.getTimestamp("update_time").getTime())));
            }
        } catch (InvalidCursorStateException ex) {
            throw new PersistenceException("error processing search results: " + ex.getMessage(), ex);
        }

        return (Message[]) ret.toArray(new Message[ret.size()]);
    }

    /**
     * Inserts a new message into the data store.
     *
     * @param message the message to insert
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws DuplicateEntryException if a message with the same GUID already exists
     * @throws PersistenceException if an error occurs while accessing the data store
     */
    public void createMessage(Message message) throws PersistenceException {
        if (message == null) {
            throw new IllegalArgumentException("message to create must not be null");
        }

        String guid = message.getGuid();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            // check to ensure that the message does not already exist
            statement = connection.prepareStatement(MESSAGES_BY_ID);

            statement.setString(1, guid);

            results = statement.executeQuery();
            if (results.next()) {
                if (results.getLong(1) > 0) {
                    throw new DuplicateEntryException("message with guid '" + guid + "' already exists",
                                                      message);
                }
            }

            results.close();
            results = null;
            statement.close();
            statement = null;

            // insert the new message
            statement = connection.prepareStatement(INSERT_MESSAGE);

            int idx = 1;
            statement.setString(idx++, guid);
            statement.setString(idx++, message.getCategory());
            statement.setString(idx++, message.getContentType());
            statement.setTimestamp(idx++, new Timestamp(message.getTimestamp().getTime()));
            statement.setString(idx++, message.getContent());

            if (statement.executeUpdate() < 1) {
                throw new PersistenceException("failed to insert message");
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error updating message: " + ex.getMessage(), ex);
        } finally {
            closeResources(results, statement);
        }
    }

    /**
     * Closes a result set and prepared statement and converts <code>SQLException</code> to
     * <code>PersistenceException</code>.
     *
     * @param results the result set to close (or <code>null</code>)
     * @param statement the prepared statement to close (or <code>null</code>)
     * @throws PersistenceException if an <code>SQLException</code> occurs
     */
    private void closeResources(ResultSet results, PreparedStatement statement) throws PersistenceException {
        try {
            if (results != null) {
                results.close();
            }

            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("failed to close resources: " + ex.getMessage(), ex);
        }
    }

    /**
     * Updates an existing message in the data store.
     *
     * @param message the message to update
     * @throws EntryNotFoundException if no message matching the GUID exists in the data store
     * @throws PersistenceException if an error occurs while accessing the data store
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     */
    public void updateMessage(Message message) throws PersistenceException {
        if (message == null) {
            throw new IllegalArgumentException("message to update must not be null");
        }

        Connection connection = getConnection();

        try {
            try {
                PreparedStatement statement = connection.prepareStatement(UPDATE_MESSAGE);

                try {
                    int idx = 1;
                    String guid = message.getGuid();
                    statement.setString(idx++, message.getCategory());
                    statement.setString(idx++, message.getContentType());
                    statement.setTimestamp(idx++, new Timestamp(message.getTimestamp().getTime()));
                    statement.setString(idx++, message.getContent());
                    statement.setString(idx++, guid);

                    if (statement.executeUpdate() == 0) {
                        throw new EntryNotFoundException("attempt to update non-existent message with GUID '" + guid
                                                         + "'", guid);
                    }
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error updating message: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes the specified message from the data store.
     *
     * @param message the message to delete
     * @throws PersistenceException if an error occurs while accessing the data store
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     */
    public void deleteMessage(Message message) throws PersistenceException {
        if (message == null) {
            throw new IllegalArgumentException("message to delete must not be null");
        }

        Connection connection = getConnection();

        try {
            try {
                PreparedStatement statement = connection.prepareStatement(DELETE_MESSAGE);
                try {
                    String guid = message.getGuid();
                    statement.setString(1, guid);

                    statement.executeUpdate();
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("error deleting message: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the named database connection from the DB connection factory, or the default connection if the
     * connection name is <code>null</code>.
     *
     * @return the named database connection from the DB connection factory, or the default connection if the
     *   connection name is <code>null</code>
     * @throws PersistenceException if an error occurs while retrieving the connection
     */
    private Connection getConnection() throws PersistenceException {
        try {
            if (connectionName == null) {
                return connectionFactory.createConnection();
            } else {
                return connectionFactory.createConnection(connectionName);
            }
        } catch (DBConnectionException ex) {
            throw new PersistenceException("error creating database connection: " + ex.getMessage(), ex);
        }
    }
}
