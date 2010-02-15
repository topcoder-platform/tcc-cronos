/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.contest.coo.Helper;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class represents the base class for classes that would perform some
 * database connection in this component. This class hosts the logger,
 * connection name as well as database connection factory as well as providing
 * the getters for those.
 * </p>
 * <p>
 * <code>DBContestDataRetriever</code> and <code>DBComponentManager</code>
 * classes extend this class.
 * </p>
 * <p>
 * <strong>Thread Safe:</strong> This class is thread safe since it is
 * immutable.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseDBConnector {
    /**
     * <p>
     * Represents the connection name used to connect to the database.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. WIll not be null/empty.
     * Has getter.
     * </p>
     */
    private final String connectionName;
    /**
     * <p>
     * Represents the <code>DBConnectionFactory</code> to be used to create
     * connection to the database.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. WIll not be null/empty.
     * Has getter.
     * </p>
     */
    private final DBConnectionFactory dbConnectionFactory;
    /**
     * <p>
     * Represents the logger to be used.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. Can be null to indicate
     * no logging. Has getter.
     * </p>
     */
    private final Log logger;

    /**
     * <p>
     * Constructor. Initialize variables.
     * </p>
     * @param configuration The configuration object. must not be null.
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ConfigurationException if any error in configuration/missing
     *             value/etc
     *
     */
    public BaseDBConnector(ConfigurationObject configuration)
        throws ConfigurationException {
        Helper.checkNull("configuration", configuration);


        try {

            configuration = Helper.getChild(configuration, "default");

            // get logger instance
            this.logger = Helper.getLogger(configuration);

            // get connection name
            connectionName = Helper.getStringProperty(configuration, "connectionName", true);

            // get DBConnectionFactory from configuration
            ConfigurationObject dbConnectionFactoryConfig = Helper.getChild(configuration, "dbConnectionFactoryConfig");

            dbConnectionFactory = new DBConnectionFactoryImpl(dbConnectionFactoryConfig);
        } catch (UnknownConnectionException e) {
            throw new ConfigurationException("fail to create db connection factory.", e);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            throw new ConfigurationException("connection factory configuration error occur", e);
        }
        catch (Exception e) {
            throw new ConfigurationException("connection factory configuration error occur", e);
        }
    }

    /**
     * Getter for the connection name.
     *
     * @return The value of the connection name.
     */
    protected String getConnectionName() {
        return connectionName;
    }

    /**
     * Getter for the database connection factory.
     *
     * @return The value of the database connection factory.
     */
    protected DBConnectionFactory getDbConnectionFactory() {
        return dbConnectionFactory;
    }

    /**
     * Getter for the logger.
     *
     * @return The value of the logger.
     */
    protected Log getLogger() {
        return logger;
    }
}
