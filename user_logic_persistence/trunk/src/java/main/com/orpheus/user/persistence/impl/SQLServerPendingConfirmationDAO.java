/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.orpheus.user.persistence.DuplicateEntryException;
import com.orpheus.user.persistence.EntryNotFoundException;
import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.PendingConfirmationDAO;
import com.orpheus.user.persistence.PersistenceException;
import com.orpheus.user.persistence.UserLogicPersistenceHelper;
import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;
import com.orpheus.user.persistence.ejb.PendingConfirmationBean;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.cache.Cache;

/**
 * <p>
 * An implementation of the {@link PendingConfirmationDAO} which persists
 * confirmation messages in a SQL Server relational database.
 * </p>
 * <p>
 * A <code>DBConnectionFactory</code> is used to obtain a connection to the
 * underlying database. It is expected that the database connection will be
 * created from a <code>DataSource</code> provided by the application server.
 * To this end, the <code>DBConnectionFactory</code> should be configured to
 * use a <code>JNDIConnectionProducer</code> with a JNDI reference to the
 * <code>DataSource</code>. Please consult the DB Connection Factory
 * component specification for information on how to configure the
 * <code>JNDIConnectionProducer</code>.
 * </p>
 * <p>
 * Confirmation messages are cached within this class using a <code>Cache</code>
 * instance in order to reduce the number of requests made to the underlying
 * database and to improve response times. The manner in which the messages are
 * cached (including the caching strategy, cache size, etc) can be configured in
 * the Simple Cache configuration namespace. Please consult the Simple Cache
 * component specification for information on how to configure the cache.
 * </p>
 * <p>
 * <b>Configuration:</b><br>
 * The names of the <code>DBConnectionFactory</code> and <code>Cache</code>
 * classes to use, as well as the name of the database connection to create,
 * need to be configured in the configuration namespace provided to the
 * {@link #SQLServerPendingConfirmationDAO(String)} constructor. The
 * configuration parameters are listed in the table below.
 * </p>
 * <table border="1"> <thead>
 * <tr>
 * <td><b>Property</b></td>
 * <td><b>Description</b></td>
 * <td><b>Required</b></td>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>specNamespace</td>
 * <td>The ObjectFactory configuration namespace specifying the
 * <code>DBConnectionFactory</code> and <code>Cache</code> object creation.
 * This namespace is passed to the
 * <code>ConfigManagerSpecificationFactory</code> class</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>factoryKey</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>DBConnectionFactory</code> instance</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>cacheKey</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>Cache</code> instance</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>name</td>
 * <td>The name of the database connection to retrieve from the
 * <code>DBConnectionFactory</code>. If this property is not specified, the
 * default database connection will be retrieved</td>
 * <td>No</td>
 * </tr>
 * </tbody> </table>
 * <p>
 * <b>Thread-safety:</b><br>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see PendingConfirmationBean
 * @see ConfirmationMessageDTO
 */
public class SQLServerPendingConfirmationDAO implements PendingConfirmationDAO {

    /**
     * <p>
     * The index of the 'address' column in the 'pending_email_conf' database
     * table.
     * </p>
     */
    private static final int ADDRESS_TABLE_COLUMN_INDEX = 1;

    /**
     * <p>
     * The index of the 'confirmation_code' column in the 'pending_email_conf'
     * database table.
     * </p>
     */
    private static final int CONFIRM_CODE_TABLE_COLUMN_INDEX = 2;

    /**
     * <p>
     * The index of the 'date_sent' column in the 'pending_email_conf'
     * database table.
     * </p>
     */
    private static final int DATE_SENT_TABLE_COLUMN_INDEX = 3;

    /**
     * <p>
     * The index of the 'message_subject' column in the 'pending_email_conf'
     * database table.
     * </p>
     */
    private static final int MSG_SUBJECT_TABLE_COLUMN_INDEX = 4;

    /**
     * <p>
     * The index of the 'message_body' column in the 'pending_email_conf'
     * database table.
     * </p>
     */
    private static final int MSG_BODY_TABLE_COLUMN_INDEX = 5;

    /**
     * <p>
     * The SQL statement to insert a confirmation message into the database.
     * </p>
     */
    private static final String INSERT_SQL_STATEMENT =
            "INSERT INTO pending_email_conf "
                    + "(address, confirmation_code, date_sent, message_subject, message_body) VALUES (?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to retrieve a confirmation message from the database.
     * </p>
     */
    private static final String SELECT_MESSAGE_SQL_STATEMENT = "SELECT * FROM pending_email_conf WHERE address=?";

    /**
     * <p>
     * The SQL statement to retrieve all the confirmation messages from the
     * database.
     * </p>
     */
    private static final String SELECT_ALL_MESSAGES_SQL_STATEMENT = "SELECT * FROM pending_email_conf";

    /**
     * <p>
     * The SQL statement to check whether there is a confirmation with the
     * specified address in the database.
     * </p>
     */
    private static final String CHECK_MESSAGE_EXISTS_SQL_STATEMENT
        = "SELECT address FROM pending_email_conf WHERE address=?";

    /**
     * <p>
     * The SQL statement to delete a confirmation message from the database.
     * </p>
     */
    private static final String DELETE_SQL_STATEMENT = "DELETE FROM pending_email_conf WHERE address=?";

    /**
     * <p>
     * The connection factory that is used to obtain database connections. It
     * should be backed by a JNDI connection producer, which eases the obtaining
     * of a connection from a DataSource via JNDI.
     * </p>
     * <p>
     * This field is set in the constructor, and will never change afterwards.
     * It cannot be null.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * The name of the database connection to obtain from the connection factory.
     * </p>
     * <p>
     * This field is set in the constructor, and will never change afterwards.
     * The value of this field can be null to indicate that the default database
     * connection should be retrieved from the connection factory. It cannot be
     * a blank string.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * A cache of ConirmationMessageDTO objects. The objects are indexed in the
     * by the corresponding confirmation message addresses. The cache size,
     * replacement policy and other caching options are set in the Simple Cache
     * configuration namespace.
     * </p>
     * <p>
     * This field is created in the constructor, and can never be changed
     * afterwards. It cannot be null.
     * </p>
     */
    private final Cache cache;

    /**
     * <p>
     * Creates a new <code>SQLServerPendingConfirmationDAO</code> instance
     * using the specified configuration namespace.
     * </p>
     * <p>
     * The configuration namespace is used to load the
     * <code>DBConnectionFactory</code>, which this class uses to obtain
     * database connections, and the <code>Cache</code>, which is used to
     * cache confirmation messages within this class. It is also used to obtain
     * the name of the database connection to obtain from the
     * <code>DBConnectionFactory</code>. If an error occurs reading the
     * configuration information or while instantiating the
     * <code>DBConnectionFactory</code> and <code>Cache</code> objects, an
     * <code>ObjectInstantiationException</code> is thrown. Please consult the
     * class documentation for more information on the configuration parameters.
     * </p>
     *
     * @param namespace the configuration namespace from which to read
     *        configuration information
     * @throws IllegalArgumentException if the configuration namespace is
     *         <code>null</code> or a blank string
     * @throws ObjectInstantiationException if an error occurs reading from the
     *         configuration namespace or while instantiating the
     *         <code>DBConnectionFactory</code> and <code>Cache</code>
     *         objects
     */
    public SQLServerPendingConfirmationDAO(String namespace) throws ObjectInstantiationException {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(namespace, "namespace");

        // Create the DBConnectionFactory and Cache instances.
        connectionFactory =
                (DBConnectionFactory) UserLogicPersistenceHelper.createObject(namespace, "factoryKey",
                        DBConnectionFactory.class);
        cache = (Cache) UserLogicPersistenceHelper.createObject(namespace, "cacheKey", Cache.class);

        // Get the connection name.
        connectionName = UserLogicPersistenceHelper.getConfigProperty(namespace, "name", false);
    }

    /**
     * <p>
     * Stores the given confirmation message in the database. If the
     * database already contains a confirmation message with the same
     * address as that of the given message, a
     * <code>DuplicateEntryException</code> is thrown.
     * </p>
     * <p>
     * Once the confirmation message has been successfully stored, it is cached
     * within this class in order to reduce the number of retrieval requests
     * made to the database and to improve response times.
     * </p>
     *
     * @param message the data transfer object (DTO) representing the
     *        confirmation message to store
     * @throws IllegalArgumentException if the confirmation message is
     *         <code>null</code>
     * @throws DuplicateEntryException if the database contains a
     *         confirmation message with the same address as that of the given
     *         message
     * @throws PersistenceException if storing the confirmation message in the
     *         database fails
     */
    public void store(ConfirmationMessageDTO message) throws PersistenceException {
        UserLogicPersistenceHelper.assertArgumentNotNull(message, "pending confirmation message");

        // Check if the confirmation message is in the cache.
        if (cache.get(message.getAddress().toLowerCase()) != null) {
            throw new DuplicateEntryException("A pending confirmation message with address, "
                                              + message.getAddress() + ", already exists in the database",
                                              message.getAddress());
        }

        // Insert the confirmation message into the DB.
        Connection connection = UserLogicPersistenceHelper.createDBConnection(connectionFactory, connectionName);
        try {
            // First check if the message is in database.
            // If it is not, throw a DuplicateEntryFoundException.
            PreparedStatement sql = connection.prepareStatement(CHECK_MESSAGE_EXISTS_SQL_STATEMENT);
            sql.setString(ADDRESS_TABLE_COLUMN_INDEX, message.getAddress());
            ResultSet result = sql.executeQuery();
            if (result.next()) {
                throw new DuplicateEntryException("A pending confirmation message with address, "
                                                  + message.getAddress() + ", already exists in the database",
                                                  message.getAddress());
            }
            sql.close();

            // Store the message.
            sql = connection.prepareStatement(INSERT_SQL_STATEMENT);
            sql.setString(ADDRESS_TABLE_COLUMN_INDEX, message.getAddress());
            sql.setString(CONFIRM_CODE_TABLE_COLUMN_INDEX, message.getUnlockCode());
            sql.setTimestamp(DATE_SENT_TABLE_COLUMN_INDEX, new Timestamp(message.getDateSent().getTime()));
            sql.setString(MSG_SUBJECT_TABLE_COLUMN_INDEX, message.getMessageSubject());
            sql.setString(MSG_BODY_TABLE_COLUMN_INDEX, message.getMessageBody());
            sql.executeUpdate();
            sql.close();
        } catch (SQLException e) {
            throw new PersistenceException("Failed to store pending confirmation message with address, "
                                           + message.getAddress() + ", in the database", e);
        } finally {
            // Close the DB connection.
            try {
                connection.close();
            } catch (SQLException e) {
                // Safe to ignore.
            }
        }

        // Put the confirmation message in the cache.
        cache.put(message.getAddress().toLowerCase(), message);
    }

    /**
     * <p>
     * Checks if a confirmation message with the specified address exists in the
     * database. It it does, a data transfer object (DTO) representing
     * the confirmation message is returned. Otherwise, <code>null</code> is
     * returned.
     * </p>
     * <p>
     * If the confirmation message was found in the database, it is
     * cached within this class in order to reduce the number of retrieval
     * requests made to the database and to improve response times.
     * </p>
     *
     * @param address the address of the confirmation message to check for
     * @return a <code>ConfirmationMessageDTO</code> representing the
     *         confirmation message with the specified address, or
     *         <code>null</code> if no such confirmation message exists in the
     *         database
     * @throws IllegalArgumentException if the specified address is
     *         <code>null</code> or a blank string
     * @throws PersistenceException if checking if the confirmation message is
     *         in the database fails
     */
    public ConfirmationMessageDTO contains(String address) throws PersistenceException {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(address, "pending confirmation message address");

        // If the confirmation message is in the cache, return it.
        ConfirmationMessageDTO message = (ConfirmationMessageDTO) cache.get(address.toLowerCase());
        if (message != null) {
            return message;
        }

        Connection connection = UserLogicPersistenceHelper.createDBConnection(connectionFactory, connectionName);
        try {
            PreparedStatement sql = connection.prepareStatement(SELECT_MESSAGE_SQL_STATEMENT);
            sql.setString(ADDRESS_TABLE_COLUMN_INDEX, address);
            ResultSet result = sql.executeQuery();

            // Create the ConfirmationMessageDTO if it was returned in the
            // result set.
            if (result.next()) {
                message = new ConfirmationMessageDTO();
                message.setAddress(result.getString(ADDRESS_TABLE_COLUMN_INDEX));
                message.setUnlockCode(result.getString(CONFIRM_CODE_TABLE_COLUMN_INDEX));
                message.setDateSent(result.getDate(DATE_SENT_TABLE_COLUMN_INDEX));
                message.setMessageSubject(result.getString(MSG_SUBJECT_TABLE_COLUMN_INDEX));
                message.setMessageBody(result.getString(MSG_BODY_TABLE_COLUMN_INDEX));
            }
            sql.close();
        } catch (SQLException e) {
            throw new PersistenceException("Failed to check if database contains a pending confirmation message "
                                           + "with address, " + address, e);
        } finally {
            // Close the DB connection.
            try {
                connection.close();
            } catch (SQLException e) {
                // Safe to ignore.
            }
        }

        // Put the message in the cache if it was found.
        if (message != null) {
            cache.put(address.toLowerCase(), message);
        }

        return message;
    }

    /**
     * <p>
     * Retrieves the confirmation message with the specified address from the
     * database. If no such confirmation message could be found, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     * <p>
     * Once the confirmation message has been retrieved, it is cached within
     * this class in order to reduce the number of retrieval requests made to
     * the database and to improve response times.
     * </p>
     *
     * @param address the address of the confirmation message to retrieve
     * @return a <code>ConfirmationMessageDTO</code> representing the
     *         confirmation message with the specified address
     * @throws IllegalArgumentException if the specified address is
     *         <code>null</code> or a blank string
     * @throws EntryNotFoundException if no confirmation message with the
     *         specified address exists in the database
     * @throws PersistenceException if retrieving the confirmation message from
     *         the database fails
     * @see #getMessages()
     */
    public ConfirmationMessageDTO retrieve(String address) throws PersistenceException {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(address, "pending confirmation message address");

        ConfirmationMessageDTO message = contains(address);

        // If the message is not in database,
        // throw an EntryNotFoundException.
        if (message == null) {
            throw new EntryNotFoundException("No pending confirmation message with address, " + address
                                             + ", was found in the database", address);
        }

        return message;
    }

    /**
     * <p>
     * Deletes the confirmation message with the specified address from the
     * database. If no such confirmation message could be found, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     * <p>
     * If the confirmation message is cached within this class, it is deleted
     * from the cache as well.
     * </p>
     *
     * @param address the address of the confirmation message to delete
     * @throws IllegalArgumentException if the specified address is
     *         <code>null</code> or a blank string
     * @throws EntryNotFoundException if no confirmation message with the
     *         specified address exists in the database
     * @throws PersistenceException if deleting the confirmation message from
     *         the database fails
     */
    public void delete(String address) throws PersistenceException {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(address, "pending confirmation message address");

        // Remove the confirmation message is from the cache.
        cache.remove(address.toLowerCase());

        // Delete the message from database.
        Connection connection = UserLogicPersistenceHelper.createDBConnection(connectionFactory, connectionName);
        try {
            // First check if the message is in the database.
            // If it is not, throw an EntryNotFoundException.
            PreparedStatement sql = connection.prepareStatement(CHECK_MESSAGE_EXISTS_SQL_STATEMENT);
            sql.setString(ADDRESS_TABLE_COLUMN_INDEX, address);
            ResultSet result = sql.executeQuery();
            if (!result.next()) {
                throw new EntryNotFoundException("No pending confirmation message with address, " + address
                                                 + ", was found in the database", address);
            }
            sql.close();

            // Now, delete the message.
            sql = connection.prepareStatement(DELETE_SQL_STATEMENT);
            sql.setString(ADDRESS_TABLE_COLUMN_INDEX, address);
            sql.executeUpdate();
            sql.close();
        } catch (SQLException e) {
            throw new PersistenceException("Failed to delete pending confirmation message with address, " + address
                                           + ", from the database", e);
        } finally {
            // Close the DB connection.
            try {
                connection.close();
            } catch (SQLException e) {
                // Safe to ignore.
            }
        }
    }

    /**
     * <p>
     * Retrieves all the confirmation messages from the database as an
     * array. If no confirmation messages exist in the database, an
     * empty array is returned.
     * </p>
     * <p>
     * Once all the confirmation message have been retrieved, they are cached
     * within this class in order to reduce the number of retrieval requests
     * made to the database and to improve response times.
     * </p>
     *
     * @return a <code>ConfirmationMessageDTO[]</code> array containing all
     *         the confirmation messages in the database, or an empty
     *         array if there are no confirmation messages
     * @throws PersistenceException if retrieving the all confirmation messages
     *         from the database fails
     * @see #retrieve(String)
     */
    public ConfirmationMessageDTO[] getMessages() throws PersistenceException {
        Connection connection = UserLogicPersistenceHelper.createDBConnection(connectionFactory, connectionName);

        List messages = new ArrayList();

        try {
            Statement sql = connection.createStatement();
            ResultSet results = sql.executeQuery(SELECT_ALL_MESSAGES_SQL_STATEMENT);

            // Create the ConfirmationMessageDTO and add it to the list.
            while (results.next()) {
                ConfirmationMessageDTO message = new ConfirmationMessageDTO();
                message.setAddress(results.getString(ADDRESS_TABLE_COLUMN_INDEX));
                message.setUnlockCode(results.getString(CONFIRM_CODE_TABLE_COLUMN_INDEX));
                message.setDateSent(results.getDate(DATE_SENT_TABLE_COLUMN_INDEX));
                message.setMessageSubject(results.getString(MSG_SUBJECT_TABLE_COLUMN_INDEX));
                message.setMessageBody(results.getString(MSG_BODY_TABLE_COLUMN_INDEX));
                messages.add(message);
            }
            sql.close();
        } catch (SQLException e) {
            throw new PersistenceException("Failed to retrieve all pending confirmation messages from the database",
                                           e);
        } finally {
            // Close the DB connection.
            try {
                connection.close();
            } catch (SQLException e) {
                // Safe to ignore.
            }
        }

        return (ConfirmationMessageDTO[]) messages.toArray(new ConfirmationMessageDTO[0]);
    }

}
