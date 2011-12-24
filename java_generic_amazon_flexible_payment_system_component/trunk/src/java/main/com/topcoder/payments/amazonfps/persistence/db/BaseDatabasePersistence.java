/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.persistence.db;

import java.sql.Connection;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.Helper;
import com.topcoder.util.log.Log;

/**
 * <p>
 * The abstract {@code BaseDatabasePersistence} class is a base class for database persistence implementations
 * provided in this component. It simply holds {@code DBConnectionFactory} instance and connection name specified
 * within the configuration and provides a method for establishing database connections. Additionally it holds a
 * <i>Logging Wrapper</i> logger instance if logging is required according to the configuration.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is mutable, but thread safe when {@code configure()} method is called
 * just once right after instantiation.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public abstract class BaseDatabasePersistence {
    /**
     * Constant for connectionName parameter name.
     */
    private static final String CONNECTION_NAME_PARAMETER = "connectionName";

    /**
     * Constant for dbConnectionFactoryConfig parameter name.
     */
    private static final String DB_CONNECTION_FACTORY_CONFIG_PARAMETER = "dbConnectionFactoryConfig";

    /**
     * The database connection factory to be used for creating database connections. It is initialized in the
     * {@code configure()} method and never changed after that (assuming that {@code configure()} is called just once
     * right after construction). Cannot be {@code null} after initialization. It is used in createConnection()
     * method.
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * The connection name to be passed to the connection factory. If it is {@code null}, the default connection
     * is used. It is initialized in the {@code configure()} method and never changed after that (assuming that
     * {@code configure()} is called just once right after construction). Cannot be empty after initialization.
     * It is used in createConnection() method.
     */
    private String connectionName;

    /**
     * The logger used by subclasses for logging errors and debug information. It is initialized in the
     * {@code configure()} method and never changed after that (assuming that {@code configure()} is called just once
     * right after construction). If it is {@code null} after initialization, logging is not performed.
     * Has a protected getter.
     */
    private Log log;

    /**
     * Constructs new {@code BaseDatabasePersistence} instance.
     */
    protected BaseDatabasePersistence() {
        // Empty
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param configuration
     *              the configuration object
     *
     * @throws IllegalArgumentException
     *              if configuration is {@code null}
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *              if some error occurred when initializing an instance using the given configuration
     */
    public void configure(ConfigurationObject configuration) {
        ParameterCheckUtility.checkNotNull(configuration, "configuration");

        try {
            log = Helper.getLog(configuration);

            // create db connection factory
            ConfigurationObject dbConnectionFactoryConfig = Helper.getChildConfiguration(configuration,
                    DB_CONNECTION_FACTORY_CONFIG_PARAMETER);

            dbConnectionFactory = new DBConnectionFactoryImpl(dbConnectionFactoryConfig);

            // retrieve connection name. Connection name is optional.
            connectionName = Helper.getProperty(configuration, CONNECTION_NAME_PARAMETER, false);

        } catch (UnknownConnectionException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "The default producer is not provided", e);
        } catch (ConfigurationException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "Failed to configure DB connection factory", e);
        }
    }

    /**
     * Creates a database connection.
     *
     * @return the created connection instance (not null)
     *
     * @throws IllegalStateException
     *              if persistence instance was not configured with {@code configure()} method
     * @throws com.topcoder.db.connectionfactory.NoDefaultConnectionException
     *              if the default connection is not configured within the factory
     * @throws UnknownConnectionException
     *              if a connection with the specified name is not configured within the connection factory
     * @throws DBConnectionException
     *              if any other error occurs while creating the connection
     */
    protected Connection createConnection() throws DBConnectionException {
        // check that configure() method was called
        ValidationUtility.checkNotNull(dbConnectionFactory, "dbConnectionFactory", IllegalStateException.class);

        Connection connection;
        if (connectionName == null) {
            // create default connection
            connection = dbConnectionFactory.createConnection();
        } else {
            // create connection with the configured name
            connection = dbConnectionFactory.createConnection(connectionName);
        }
        return connection;
    }

    /**
     * Retrieves the logger used by subclasses for logging errors and debug information.
     *
     * @return the logger used by subclasses for logging errors and debug information
     */
    protected Log getLog() {
        return log;
    }
}
