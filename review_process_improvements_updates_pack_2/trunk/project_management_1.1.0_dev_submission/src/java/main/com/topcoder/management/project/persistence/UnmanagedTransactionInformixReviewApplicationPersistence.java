/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.ReviewApplicationConfigurationException;
import com.topcoder.management.project.ReviewApplicationPersistenceException;
import com.topcoder.management.project.persistence.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This class performs exactly the same tasks as InformixReviewApplicationPersistence, but is designed to be used with
 * externally managed transactions.
 * </p>
 *
 * <p>
 * It is supposed to be used with externally managed transactions so the implementations of
 * <code>openConnection()</code>, <code>closeConnection(Connection)</code> and
 * <code>closeConnectionOnError(Connection)</code> does not call the transaction management related method like
 * <code>commit()</code>, <code>setAutoCommit(boolean)</code> or <code>rollback()</code>. The transaction is expected
 * to be handled externally to the component such as EJB.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is immutable and thread-safe.
 * </p>
 *
 * @author moonli, pvmagacho
 * @version 1.2
 * @since 1.2
 */
public class UnmanagedTransactionInformixReviewApplicationPersistence extends
    AbstractInformixReviewApplicationPersistence {
    /**
     * Logger instance using the class name as category.
     */
    private static final Log LOGGER = LogFactory
        .getLog(UnmanagedTransactionInformixReviewApplicationPersistence.class.getName());

    /**
     *
     * Creates an instance of <code>UnmanagedTransactionInformixReviewApplicationPersistence</code> from the settings
     * in configuration.
     *
     * <p>
     * The following parameters are configured.
     * <ul>
     * <li>DBConnFactoryNamespace: The namespace that contains settings for DB Connection Factory. It is required.</li>
     * <li>ConnectionName: The name of the connection that will be used by DBConnectionFactory to create connection.
     * If missing, default connection will be created. It is optional.</li>
     * <li>ReviewApplicationIDSequenceName: The sequence name used to create Id generator for project Id. If missing
     * default value (review_applications_id_seq) is used. It is optional.</li>
     * <li>CacheNamespace: The namespace for the Simple Cache object. It is required.</li>
     * </ul>
     * </p>
     * <p>
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if namespace is null or empty
     * @throws ReviewApplicationConfigurationException if any error occurred during configuration
     * @throws ConfigurationException temp.
     */
    public UnmanagedTransactionInformixReviewApplicationPersistence(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Return log instance.
     *
     * @return the log instance.
     */
    protected Log getLogger() {
        return LOGGER;
    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixReviewApplicationPersistence.</code>.
     * </p>
     *
     * <p>
     * It uses the DB connection factory to create the connection to underlying database. If the connection is not
     * configured, the default connection from DB connection factory will be created, otherwise, the connection with
     * the specified name in DB connection factory will be created.
     * </p>
     *
     * @return the opened database connection
     * @throws ReviewApplicationPersistenceException if any error occurred
     */
    protected Connection openConnection() throws ReviewApplicationPersistenceException {
        try {
            LOGGER.log(Level.INFO, new LogMessage(null, null, "creating db connection using connection name: "
                + getConnectionName()));

            return getConnectionFactory().createConnection(getConnectionName());
        } catch (DBConnectionException e) {
            throw new ReviewApplicationPersistenceException("Failed to create the connection", e);
        }
    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixProjectPersistence.</code> used to close the connection after an operation fails to
     * complete.
     * </p>
     *
     * @param connection a Connection to close
     * @throws IllegalArgumentException if the argument is null
     * @throws ReviewApplicationPersistenceException if any problem occurs trying to close the connection
     */
    protected void closeConnection(Connection connection) throws ReviewApplicationPersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        try {
            if (!connection.isClosed()) {
                LOGGER.log(Level.INFO, "close the connection.");
                connection.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixProjectPersistence.</code> used to close the connection after an operation fails to
     * complete.
     * </p>
     *
     * @param connection a Connection to close
     * @throws IllegalArgumentException if the argument is null
     * @throws ReviewApplicationPersistenceException if any problem occurs trying to close the connection
     */
    protected void closeConnectionOnError(Connection connection) throws ReviewApplicationPersistenceException {
        closeConnection(connection);
    }
}
