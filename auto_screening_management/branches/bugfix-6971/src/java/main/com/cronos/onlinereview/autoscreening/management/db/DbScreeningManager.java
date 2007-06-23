/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.cronos.onlinereview.autoscreening.management.Helper;
import com.cronos.onlinereview.autoscreening.management.PersistenceException;
import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.cronos.onlinereview.autoscreening.management.logging.LogMessage;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This class abstracts the screening manager that uses database as the persistence for the
 * screening tasks. The database connection is made configurable by using the DB Connection Factory
 * component. The connection-related parameters should be provided in the constructor in order to be
 * used by the Object Factory.
 * </p>
 * <p>
 * Under this abstraction, subclasses don't need to care about how the database connection is
 * specified. Rather, they can just focus on the persistence logic for the operations.
 * </p>
 * <p>
 * This class is thread-safe by being immutable.
 * </p>
 *
 * @author colau, haozhangr
 * @version 1.0
 */
public abstract class DbScreeningManager implements ScreeningManager {
	 /** Logger instance using the class name as category */
    private static final Log logger = LogFactory.getLog(DbScreeningManager.class.getName()); 
    
    /**
     * <p>
     * Represents the database connection factory to produce connections (based on the connection
     * producer name).
     * </p>
     * <p>
     * It is initialized in the constructor and not changed afterwards. It cannot be null. It is
     * accessed in the createConnection() method.
     * </p>
     *
     *
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the connection producer name which is used to obtain connection from the
     * connection factory.
     * </p>
     * <p>
     * It is initialized in the constructor and not changed afterwards. It can be null (if default
     * connection producer name is used) or non-empty. It is accessed in the createConnection()
     * method.
     * </p>
     *
     *
     */
    private final String connectionName;

    /**
     * <p>
     * Constructor that accepts the given argument. Default connection producer name will be used to
     * obtain connections.
     * </p>
     *
     *
     *
     * @param connectionFactory
     *            the connection factory to use
     * @throws IllegalArgumentException
     *             if connectionFactory is null
     */
    protected DbScreeningManager(DBConnectionFactoryImpl connectionFactory) {
        Helper.checkNull(connectionFactory, "connectionFactory");
        this.connectionFactory = connectionFactory;
        this.connectionName = null;
    }

    /**
     * <p>
     * Constructor that accepts the given arguments.
     * </p>
     *
     *
     *
     * @param connectionFactory
     *            the connection factory to use
     * @param connectionName
     *            the connection producer name
     * @throws IllegalArgumentException
     *             if connectionFactory is null, or connectionName is null or empty String
     */
    protected DbScreeningManager(DBConnectionFactoryImpl connectionFactory, String connectionName) {
        Helper.checkNull(connectionFactory, "connectionFactory");
        Helper.checkString(connectionName, "connectionName");
        
        logger.log(Level.INFO,
        		"Instantiate SqlDeliverablePersistence with connectionFactory and connectioName:" + connectionName);
        
        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
    }

    /**
     * <p>
     * Creates a new database connection. The returned connection has auto-commit flag set to false
     * to support transaction.
     * </p>
     *
     *
     * @return a new database connection
     * @throws PersistenceException
     *             if the database connection cannot be created
     */
    protected Connection createConnection() throws PersistenceException {
        try {
            Connection conn = null;
            if (connectionName == null) {
            	logger.log(Level.INFO, "create db connection using default connection name");
                conn = connectionFactory.createConnection();
            } else {
            	logger.log(Level.INFO, "create db connection using connection name:" + connectionName);
                conn = connectionFactory.createConnection(connectionName);
            }
            conn.setAutoCommit(false);
            return conn;
        } catch (DBConnectionException e) {
        	logger.log(Level.ERROR,
        			"Failed to create database connection.\n" + LogMessage.getExceptionStackTrace(e));
            throw new PersistenceException("Failed to create database connection.", e);
        } catch (SQLException e) {
        	logger.log(Level.ERROR,
        			"Failed to start transaction of the connection.\n" + LogMessage.getExceptionStackTrace(e));
            throw new PersistenceException(
                    "Failed to set database connection auto commit to false.", e);
        }
    }
}
