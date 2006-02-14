/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.stresstests;

import java.sql.Connection;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Helper class for stress test.
 * </p>
 *
 * @author arylio
 * @version 1.0
 *
 */
final class StressHelper {
    /**
     * <p>
     * Const defined for operator, create an dao.
     * </p>
     */
    public static final int CREATE = 1;

    /**
     * <p>
     * Const defined for operator, update an dao.
     * </p>
     */
    public static final int UPDATE = 2;

    /**
     * <p>
     * Const defined for operator, delete an dao.
     * </p>
     */
    public static final int DELETE = 3;

    /**
     * <p>
     * Const defined for operator, get an dao.
     * </p>
     */
    public static final int GET = 4;

    /**
     * <p>
     * Const defined for operator, get an dao list.
     * </p>
     */
    public static final int GETLIST = 5;


    /**
     * <p>
     * Represents the namespace, used by stress test.
     * </p>
     */
    public static final String NAMESPACE = "com.topcoder.timetracker.entry.time";

    /**
     * <p>
     * Represents the config file, used by stress test.
     * </p>
     */
    public static final String CONFIG_FILE = "stress/Time_Entry.xml";

    /**
     * <p>
     * Represents the property name in the config file.
     * </p>
     */
    public static final String PROPERTY_CONNECTION = "Connection";

    /**
     * The database connection instance.
     */
    private static Connection connection = null;

    /**
     * <p>
     * Add config file for testing.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public static void addConfigFile() throws Exception {
        removeConfigFile();

        // load the configuration
        ConfigManager.getInstance().add(CONFIG_FILE);
    }

    /**
     * <p>
     * Remove config file for testing.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public static void removeConfigFile() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();
        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }
    }

    /**
     * <p>
     * Delete all rows from the spcific table.
     * </p>
     *
     * @param tableName the name of the table to delete rows.
     * @throws Exception Exception to JUnit.
     */
    public static void clearTable(String tableName) throws Exception {
        getConnection().createStatement().executeUpdate("DELETE FROM " + tableName);
    }

    /**
     * Get a connection to the database.
     *
     * @return a connection to the database.
     *
     * @throws Exception to JUnit
     */
    private static Connection getConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            // create a new Connection if the connection is not initialized or closed.
            DBConnectionFactory factory = new DBConnectionFactoryImpl(NAMESPACE);
            connection = factory.createConnection();
        }
        return connection;
    }

}
