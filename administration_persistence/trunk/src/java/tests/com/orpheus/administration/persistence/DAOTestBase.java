/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Base class containing common helper methods used by the <code>DAO</code> tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public abstract class DAOTestBase extends TestCase {
    /**
     * The config manager instance.
     */
    private static final ConfigManager MANAGER = ConfigManager.getInstance();

    /**
     * The database connection to use for the test. This member is initialized in {@link #setUp setUp} to be a new
     * instance for each test.
     */
    private Connection connection;

    /**
     * Pre-test initialization: initializes <code>connection</code> for the upcoming test.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        connection = new DBConnectionFactoryImpl("connection.factory.namespace").createConnection();
    }

    /**
     * Post-test cleanup: removes all namespaces from the configuration manager and closes the connection.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        removeAllNamespaces();
        connection.close();
        connection = null;
    }

    /**
     * Removes all namespaces from the configuration manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    protected void removeAllNamespaces() throws Exception {
        for (Iterator it = MANAGER.getAllNamespaces(); it.hasNext();) {
            MANAGER.removeNamespace((String) it.next());
        }
    }

    /**
     * Returns the database connection to use for the current test.
     *
     * @return the database connection to use for the current test
     */
    protected Connection getConnection() {
        return connection;
    }

    /**
     * Returns the configuration manager to use for the current test.
     *
     * @return the configuration manager to use for the current test
     */
    protected static ConfigManager getConfigManager() {
        return MANAGER;
    }

    /**
     * Removes all rows from the specified table.
     *
     * @param table the table to clear
     * @throws Exception if an unexpected exception occurs
     */
    protected void clearTable(String table) throws Exception {
        connection.prepareStatement("DELETE FROM " + table).executeUpdate();
    }
}
