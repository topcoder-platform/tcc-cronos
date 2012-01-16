/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.persistence.db;

import java.sql.Connection;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.util.log.Log;

/**
 * <p>
 * The {@code MockBaseDatabasePersistence} class is a mock class for abstract {@link BaseDatabasePersistence} class.
 * It is used to test concrete methods of {@code BaseDatabasePersistence}.
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class MockBaseDatabasePersistence extends BaseDatabasePersistence {
    /**
     * Constructs new {@code MockBaseDatabasePersistence} instance.
     */
    public MockBaseDatabasePersistence() {
        // Empty
    }

    /**
     * Creates a database connection (exposes protected {@code createConnection} method).
     *
     * @return the created connection instance (not null)
     *
     * @throws IllegalStateException
     *              if persistence instance was not configured with {@code configure()} method
     * @throws com.topcoder.db.connectionfactory.NoDefaultConnectionException
     *              if the default connection is not configured within the factory
     * @throws com.topcoder.db.connectionfactory.UnknownConnectionException
     *              if a connection with the specified name is not configured within the connection factory
     * @throws DBConnectionException
     *              if any other error occurs while creating the connection
     */
    @Override
    public Connection createConnection() throws DBConnectionException {
        return super.createConnection();
    }

    /**
     * Gets the logger (exposes protected {@code getLog} method).
     *
     * @return the logger used by subclasses for logging errors and debug information
     */
    @Override
    public Log getLog() {
        return super.getLog();
    }
}
