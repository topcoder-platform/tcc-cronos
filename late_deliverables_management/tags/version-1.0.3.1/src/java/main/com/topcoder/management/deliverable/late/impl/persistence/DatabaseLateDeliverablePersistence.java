/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.late.impl.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.deliverable.late.Helper;
import com.topcoder.management.deliverable.late.LateDeliverable;
import com.topcoder.management.deliverable.late.LateDeliverableManagementConfigurationException;
import com.topcoder.management.deliverable.late.impl.LateDeliverableNotFoundException;
import com.topcoder.management.deliverable.late.impl.LateDeliverablePersistence;
import com.topcoder.management.deliverable.late.impl.LateDeliverablePersistenceException;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of LateDeliverablePersistence that updates late deliverables in database
 * persistence using JDBC and DB Connection Factory component. This class uses Logging Wrapper component to log errors
 * and debug information.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable, but thread safe when configure() method is called just once
 * right after instantiation and entities passed to it are used by the caller in thread safe manner. It uses thread
 * safe DBConnectionFactory and Log instances. This class uses transactions when updating data in the database.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0.3
 */
public class DatabaseLateDeliverablePersistence implements LateDeliverablePersistence {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DatabaseLateDeliverablePersistence.class.getName();

    /**
     * <p>
     * Represents the child key 'dbConnectionFactoryConfig'.
     * </p>
     */
    private static final String KEY_DBCF_CONFIG = "dbConnectionFactoryConfig";

    /**
     * <p>
     * Represents the property key 'connectionName'.
     * </p>
     */
    private static final String KEY_CONN_NAME = "connectionName";

    /**
     * <p>
     * Represents the SQL string to update late deliverable.
     * </p>
     */
    private static final String SQL_UPDATE_LATE_DELIVERABLE = "UPDATE late_deliverable SET project_phase_id = ?,"
        + " resource_id = ?, deliverable_id = ?, deadline = ?, create_date = ?, forgive_ind = ?, last_notified = ?,"
        + " delay = ?, explanation = ?, explanation_date = ?, response = ?, response_user = ?, response_date = ?"
        + " WHERE late_deliverable_id = ?";

    /**
     * <p>
     * The database connection factory to be used.
     * </p>
     *
     * <p>
     * Is initialized in the configure() method and never changed after that. Cannot be null after initialization. Is
     * used in update().
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * The connection name to be used.
     * </p>
     *
     * <p>
     * Is initialized in the configure() method and never changed after that. Cannot be empty after initialization. If
     * null, the default connection is used. Is used in update().
     * </p>
     */
    private String connectionName;

    /**
     * <p>
     * The logger used by this class for logging errors and debug information.
     * </p>
     *
     * <p>
     * Is null if logging is not required. Is initialized in the configure() method and never changed after that. Is
     * used in update().
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Creates an instance of DatabaseLateDeliverablePersistence.
     * </p>
     */
    public DatabaseLateDeliverablePersistence() {
        // Empty
    }

    /**
     * <p>
     * Configures this instance with use of the given configuration object.
     * </p>
     *
     * @param configuration
     *            the configuration object.
     *
     * @throws IllegalArgumentException
     *             if configuration is null.
     * @throws LateDeliverableManagementConfigurationException
     *             if some error occurred when initializing an instance using the given configuration.
     */
    public void configure(ConfigurationObject configuration) {
        Helper.checkNull(configuration, "configuration");

        // Get logger
        log = Helper.getLog(configuration);

        try {
            // Create database connection factory
            dbConnectionFactory = new DBConnectionFactoryImpl(Helper.getChildConfig(configuration, KEY_DBCF_CONFIG));
        } catch (UnknownConnectionException e) {
            throw new LateDeliverableManagementConfigurationException(
                "Failed to create a database connection factory.", e);
        } catch (ConfigurationException e) {
            throw new LateDeliverableManagementConfigurationException(
                "Failed to create a database connection factory.", e);
        }

        // Get connection name
        connectionName = Helper.getProperty(configuration, KEY_CONN_NAME, false);
    }

    /**
     * <p>
     * Updates the given late deliverable in persistence.
     * </p>
     *
     * @param lateDeliverable
     *            the late deliverable with updated data.
     *
     * @throws IllegalArgumentException
     *             if lateDeliverable is null, lateDeliverable.getId() &lt;= 0, lateDeliverable.getDeadline() is null,
     *             lateDeliverable.getCreateDate() is null.
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null).
     * @throws LateDeliverableNotFoundException
     *             if late deliverable with ID equal to lateDeliverable.getId() doesn't exist in persistence.
     * @throws LateDeliverablePersistenceException
     *             if some other error occurred when accessing the persistence.
     */
    public void update(LateDeliverable lateDeliverable) throws LateDeliverablePersistenceException {
        Date enterTimestamp = new Date();
        String signature = getSignature("update(LateDeliverable lateDeliverable)");

        try {
            // Log method entry
            Helper.logEntrance(log, signature,
                new String[] {"lateDeliverable"},
                new Object[] {lateDeliverable});

            Helper.checkNull(lateDeliverable, "lateDeliverable");
            Helper.checkPositive(lateDeliverable.getId(), "lateDeliverable.getId()");
            Helper.checkNull(lateDeliverable.getDeadline(), "lateDeliverable.getDeadline()");
            Helper.checkNull(lateDeliverable.getCreateDate(), "lateDeliverable.getCreateDate()");

            if (dbConnectionFactory == null) {
                throw new IllegalStateException("This persistence was not properly configured.");
            }

            Connection connection = null;
            try {
                // Create a connection
                connection = (connectionName == null) ? dbConnectionFactory.createConnection()
                    : dbConnectionFactory.createConnection(connectionName);

                // Disable the auto commit mode
                connection.setAutoCommit(false);

                // Update the given late deliverable in persistence
                updateLateDeliverable(lateDeliverable, connection, log, signature);
            } catch (DBConnectionException e) {
                throw new LateDeliverablePersistenceException("Failed to create a database connection.", e);
            } catch (SQLException e) {
                throw new LateDeliverablePersistenceException("A database access error occurred.", e);
            } finally {
                if (connection != null) {
                    try {
                        // Close the connection
                        connection.close();
                    } catch (SQLException e) {
                        // Log exception
                        Helper.logException(log, signature, e, "A database access error occurred"
                            + " when closing the connection to persistence (will be ignored).");

                        // Ignore
                    }
                }
            }

            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (LateDeliverableNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverableNotFoundException is thrown"
                + " when updating the given late deliverable in persistence.");
        } catch (LateDeliverablePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverablePersistenceException is thrown"
                + " when updating the given late deliverable in persistence.");
        }
    }

    /**
     * <p>
     * Updates the given late deliverable in persistence.
     * </p>
     *
     * @param lateDeliverable
     *            the late deliverable with updated data.
     *
     * @param connection
     *            the connection (not <code>null</code>).
     * @param log
     *            the logger.
     * @param signature
     *            the signature of the method to log.
     *
     * @throws LateDeliverableNotFoundException
     *             if late deliverable with ID equal to lateDeliverable.getId() doesn't exist in persistence.
     * @throws SQLException
     *             if a database access errors.
     */
    private static void updateLateDeliverable(LateDeliverable lateDeliverable, Connection connection, Log log,
        String signature) throws LateDeliverableNotFoundException, SQLException {
        // Create a prepared statement:
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_LATE_DELIVERABLE);
        try {
            int index = 1;

            // Set project phase ID to the prepared statement:
            preparedStatement.setLong(index++, lateDeliverable.getProjectPhaseId());
            // Set resource ID to the prepared statement:
            preparedStatement.setLong(index++, lateDeliverable.getResourceId());
            // Set deliverable ID to the prepared statement:
            preparedStatement.setLong(index++, lateDeliverable.getDeliverableId());
            // Set deadline to the prepared statement:
            preparedStatement.setTimestamp(index++, new Timestamp(lateDeliverable.getDeadline().getTime()));
            // Set create date to the prepared statement:
            preparedStatement.setTimestamp(index++, new Timestamp(lateDeliverable.getCreateDate().getTime()));
            // Set forgiven flag to the prepared statement:
            preparedStatement.setInt(index++, lateDeliverable.isForgiven() ? 1 : 0);

            // Get last notified from the late deliverable:
            Date lastNotified = lateDeliverable.getLastNotified();
            // Set it to the prepared statement:
            preparedStatement.setTimestamp(index++,
                (lastNotified != null) ? new Timestamp(lastNotified.getTime()) : null);

            // Get delay from the late deliverable:
            Long delay = lateDeliverable.getDelay();
            if (delay != null) {
                // Set delay to the prepared statement:
                preparedStatement.setLong(index++, delay);
            } else {
                // Set delay equal to null to the prepared statement:
                preparedStatement.setNull(index++, Types.BIGINT);
            }

            // Set explanation to the prepared statement:
            preparedStatement.setString(index++, lateDeliverable.getExplanation());
            // Get explanation date from the late deliverable:
            Date explanationDate = lateDeliverable.getExplanationDate();
            // Set explanation date to the prepared statement:
            preparedStatement.setTimestamp(index++,
                (explanationDate != null) ? new Timestamp(explanationDate.getTime()) : null);
            // Set response to the prepared statement:
            preparedStatement.setString(index++, lateDeliverable.getResponse());
            // Set response user to the prepared statement:
            preparedStatement.setString(index++, lateDeliverable.getResponseUser());
            // Get response date from the late deliverable:
            Date responseDate = lateDeliverable.getResponseDate();
            // Set response date to the prepared statement:
            preparedStatement.setTimestamp(index++,
                (responseDate != null) ? new Timestamp(responseDate.getTime()) : null);

            // Get ID from the late deliverable:
            long lateDeliverableId = lateDeliverable.getId();
            // Set it to the prepared statement:
            preparedStatement.setLong(index, lateDeliverableId);

            // Execute the UPDATE statement
            if (preparedStatement.executeUpdate() == 0) {
                throw new LateDeliverableNotFoundException(Helper.concat("The late deliverable with ID '",
                    lateDeliverableId, "' doesn't exist in persistence."), lateDeliverableId);
            }

            // Commit the changes
            connection.commit();
        } catch (SQLException e) {
            // Roll back
            rollback(connection, log, signature);

            throw e;
        } finally {
            try {
                // Close the prepared statement:
                preparedStatement.close();
            } catch (SQLException e) {
                // Log exception
                Helper.logException(log, signature, e, "A database access error occurred"
                    + " when closing the connection to persistence (will be ignored).");

                // Ignore
            }
        }
    }

    /**
     * <p>
     * Rolls back the current transaction.
     * </p>
     *
     * @param connection
     *            the connection (not <code>null</code>).
     * @param log
     *            the logger.
     * @param signature
     *            the signature of the method to log.
     */
    private static void rollback(Connection connection, Log log, String signature) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            // Log exception
            Helper.logException(log, signature, e,
                "A database access error occurred when rolling back (will be ignored).");

            // Ignore
        }
    }

    /**
     * <p>
     * Gets the signature for given method for logging.
     * </p>
     *
     * @param method
     *            the method name.
     *
     * @return the signature for given method.
     */
    private static String getSignature(String method) {
        return Helper.concat(CLASS_NAME, Helper.DOT, method);
    }
}
