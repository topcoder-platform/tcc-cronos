/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.sql.Connection;
import java.sql.SQLException;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.management.resource.ReviewerStatisticsPersistenceException;
import com.topcoder.management.resource.ReviewerStatisticsConfigurationException;
import com.topcoder.management.resource.persistence.logging.LogMessage;
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
 * @author moonli, pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class InformixReviewerStatisticsPersistence extends AbstractInformixReviewerStatisticsPersistence {
    /**
     * Logger instance using the class name as category.
     */
    private static final Log LOGGER = LogFactory.getLog(InformixReviewerStatisticsPersistence.class.getName());

    /**
     * Creates an instance of InformixReviewerStatisticsPersistence class.
     *
     * <p>
     * The following parameters are configured.
     * <ul>
     * <li>DBConnFactoryNamespace: The namespace that contains settings for DB Connection Factory. It is required.</li>
     * <li>ConnectionName: The name of the connection that will be used by DBConnectionFactory to create connection.
     * If missing, default connection will be created. It is required.</li>
     * <li>ReviewerStatisticsIDSequenceName: The sequence name used to create Id generator for project Id. It is
     * required.</li>
     * <li>CacheNamespace: The namespace for the Simple Cache object. It is required.</li>
     * </ul>
     * </p>
     * <p>
     *
     * <pre>
     * Sampĺe configuration:
     * &lt;!-- Namespace for InformixReviewerStatisticsPersistence class --&gt;
     * &lt;Config name="com.topcoder.management.resource.persistence.sql.InformixReviewerStatisticsPersistence"&gt;
     *   &lt;Property name="DBConnFactoryNamespace"&gt;
     *    &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;!-- the connection name to retrieve connection in DB Connection Factory, required --&gt;
     *   &lt;Property name="ConnectionName"&gt;
     *    &lt;Value&gt;dbconnection&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;Property name="ReviewerStatisticsIDSequenceName"&gt;
     *    &lt;Value&gt;review_statistics_id_seq&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;Property name="CacheNamespace"&gt;
     *    &lt;Value&gt;simpleCache&lt;/Value&gt;
     *   &lt;/Property&gt;
     * &lt;/Config&gt;
     * </pre>
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if the input is null or empty string
     * @throws ReviewerStatisticsConfigurationException if any error occurred during configuration
     */
    public InformixReviewerStatisticsPersistence(String namespace) {
        super(namespace);
    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixProjectPersistence.</code>.
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
     * @return an open Connection to underlying database.
     * @throws ReviewerStatisticsPersistenceException if there's a problem getting the Connection
     */
    protected Connection openConnection() throws ReviewerStatisticsPersistenceException {
        Connection connection = null;
        try {
            LOGGER.log(Level.INFO, new LogMessage(null, null, "creating db connection using connection name: "
                + getConnectionName()));
            connection = getConnectionFactory().createConnection(getConnectionName());
            connection.setAutoCommit(false);

            return connection;
        } catch (DBConnectionException e) {
            throw new ReviewerStatisticsPersistenceException("Failed to create the connection", e);
        } catch (SQLException e) {
            // connection occurred while setting the auto-commit flag
            closeConnectionOnError(connection);
            throw new ReviewerStatisticsPersistenceException("Failed to disable autocommit on connection", e);
        }
    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixReviewerStatisticsPersistence.</code> used to commit the transaction and close the
     * connection after an operation successfully completes.
     * </p>
     *
     * @param connection a Connection to close
     * @throws ReviewerStatisticsPersistenceException if any problem occurs trying to close the connection
     * @throws IllegalArgumentException if the argument is null
     */
    protected void closeConnection(Connection connection) throws ReviewerStatisticsPersistenceException {
        Util.checkNull(connection, "connection");
        try {
            if (!connection.isClosed()) {
                LOGGER.log(Level.INFO, "committing transaction");
                connection.commit();
            }
        } catch (SQLException e) {
            throw new ReviewerStatisticsPersistenceException("Failed to close the connection properly", e);
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
     * <code>AbstractInformixProjectPersistence.</code> used to rollback the transaction and close the connection
     * after an operation fails to complete.
     * </p>
     *
     * @param connection a connection to close
     * @throws IllegalArgumentException if the argument is null
     * @throws ReviewerStatisticsPersistenceException if any problem occurs trying to close the connection
     */
    protected void closeConnectionOnError(Connection connection) throws ReviewerStatisticsPersistenceException {
        Util.checkNull(connection, "connection");
        try {
            if (!connection.isClosed()) {
                LOGGER.log(Level.INFO, "rollback transaction");
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new ReviewerStatisticsPersistenceException("Failed to close the connection properly", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Gets the logger instance.
     *
     * @return the logger instance
     */
    protected Log getLogger() {
        return LOGGER;
    }
}
