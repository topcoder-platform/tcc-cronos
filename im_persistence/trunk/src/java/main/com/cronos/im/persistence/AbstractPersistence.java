/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>A common base class for all persistence implementations in this component. The common functionality consists
 * of operations to configure the DB connection factory and create DB connections.
 *
 * <p>Note that this class is not intended to be used directly by clients of this component, but it needs to be
 * public in order to be accessible to the <i>rolecategories</i> subpackage.
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public abstract class AbstractPersistence {
    /**
     * The connection factory used to create connections to the data store. This member is initialized at
     * construction, is immutable, and will never be <code>null</code>.
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * The named database connection to retrieve from the DB connection factory. This member is initialized at
     * construction, is immutable, and will never be <code>null</code> or an empty string.
     */
    private final String connectionName;

    /**
     * The object factory specification namespace. This member is initialized at construction, is immutable, and
     * can be <code>null</code> if the namespace constructor was not used.
     */
    private final String specNamespace;

    /**
     * Constructs a new <code>AbstractPersistence</code> from the specified configuration namespace.
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws ConfigurationException if the namespace is invalid or a required property is missing
     */
    protected AbstractPersistence(String namespace) throws ConfigurationException {
        ParameterHelpers.checkParameter(namespace, "namespace");

        this.specNamespace = ConfigHelpers.getRequiredProperty(namespace, "specification_factory_namespace");
        this.connectionName = ConfigHelpers.getRequiredProperty(namespace, "connection_name");
        String dbKey = ConfigHelpers.getRequiredProperty(namespace, "db_connection_factory_key");

        // create the connection factory
        try {
            this.connectionFactory = (DBConnectionFactory) ConfigHelpers.createObject(specNamespace, dbKey);
        } catch (ClassCastException ex) {
            throw new ConfigurationException("invalid type for db_connection_factory_key");
        }
    }

    /**
     * Constructs a new <code>AbstractPersistence</code> with the specified connection factory and connection name.
     *
     * @param connectionFactory the DB connection factory
     * @param connectionName the connection name
     * @throws IllegalArgumentException if either argument is <code>null</code> or <code>connectionName</code> is
     *   an empty string
     */
    protected AbstractPersistence(DBConnectionFactory connectionFactory, String connectionName) {
        ParameterHelpers.checkParameter(connectionFactory, "connection factory");
        ParameterHelpers.checkParameter(connectionName, "connection name");

        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
        this.specNamespace = null;
    }

    /**
     * Returns the DB connection factory.
     *
     * @return the DB connection factory
     */
    protected DBConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * Returns the DB connection name.
     *
     * @return the DB connection name
     */
    protected String getConnectionName() {
        return connectionName;
    }

    /**
     * Returns the specification namespace.
     *
     * @return the specification namespace
     */
    protected String getSpecNamespace() {
        return specNamespace;
    }

    /**
     * Rolls back the specified connection and suppresses any exception messages.
     *
     * @param connection the connection to roll back
     */
    protected void rollbackConnection(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            // there is no reason to raise this error up to the user
        }
    }
}
