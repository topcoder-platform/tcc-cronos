/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.management.project.ReviewApplicationPersistenceException;
import com.topcoder.management.project.persistence.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This is a review application persistence implementation. It manages transactions by internally. The only logic
 * remaining in this class is that of opening connections and managing transactions, and the only methods implemented
 * in this class are openConnection(), closeConnection(), and closeConnectionOnError(), which are concrete
 * implementations of the corresponding protected abstract methods in AbstractResourcePersistence and are used in the
 * context of a Template Method pattern.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is immutable and thread safe.
 * </p>
 *
 * @author moonli, pvmagacho, TCSDEVELOPER
 * @version 1.2.1
 * @since 1.2 (Review Process Improvements Project)
 */
public class InformixReviewApplicationPersistence extends AbstractInformixReviewApplicationPersistence {
    /**
     * Logger instance using the class name as category.
     */
    private static final Log LOGGER = LogFactory.getLog(InformixReviewApplicationPersistence.class.getName());

    /**
     * Creates an instance of <code>InformixReviewApplicationPersistence</code> from the settings in configuration.
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
     */
    public InformixReviewApplicationPersistence(String namespace) {
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
     * <p>
     * Once the connection is retrieved, the auto commit property will be set false to manage the transaction itself.
     * </p>
     *
     * @return the opened database connection
     * @throws ReviewApplicationPersistenceException if any error occurred
     */
    protected Connection openConnection() throws ReviewApplicationPersistenceException {
        Connection connection = null;
        try {
            LOGGER.log(Level.INFO, new LogMessage(null, null, "creating db connection using connection name: "
                + getConnectionName()));
            connection = getConnectionFactory().createConnection(getConnectionName());
            connection.setAutoCommit(false);

            return connection;
        } catch (DBConnectionException e) {
            throw new ReviewApplicationPersistenceException("Failed to create the connection", e);
        } catch (SQLException e) {
            // connection occurred while setting the auto-commit flag
            closeConnectionOnError(connection);
            throw new ReviewApplicationPersistenceException("Failed to disable autocommit on connection", e);
        }
    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixReviewApplicationPersistence.</code> used to commit the transaction and close the
     * connection after an operation successfully completes.
     * </p>
     *
     * @param connection the connection to be closed
     * @throws IllegalArgumentException if connection is null
     * @throws ReviewApplicationPersistenceException if any other error occurred
     */
    protected void closeConnection(Connection connection) throws ReviewApplicationPersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        try {
            if (!connection.isClosed()) {
                LOGGER.log(Level.INFO, "committing transaction");
                connection.commit();
            }
        } catch (SQLException e) {
            throw new ReviewApplicationPersistenceException("Failed to close the connection properly", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixReviewApplicationPersistence.</code> used to rollback the transaction and close the
     * connection after an operation fails to complete.
     * </p>
     *
     * @param connection the connection to be closed
     * @throws IllegalArgumentException if connection is null
     * @throws ReviewApplicationPersistenceException if any other error occurred
     */
    protected void closeConnectionOnError(Connection connection) throws ReviewApplicationPersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        try {
            if (!connection.isClosed()) {
                LOGGER.log(Level.INFO, "rollback transaction");
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new ReviewApplicationPersistenceException("Failed to close the connection properly", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
