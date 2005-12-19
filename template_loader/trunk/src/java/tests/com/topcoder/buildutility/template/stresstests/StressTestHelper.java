/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility.template.stresstests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;

import com.topcoder.buildutility.template.persistence.JDBCPersistence;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Stress Test Helper class, add data to the test table, and
 *
 * </p>
 *
 * @author arylio
 * @version 1.0
 */
final class StressTestHelper {
    /**
     * The name of the templates table.
     */
    private static final String TABLE_TEMPLATES = "templates";

    /**
     * The name of the template hierarchy table.
     */
    private static final String TABLE_TEMP_HIER = "temp_hier";

    /**
     * The name of the template hierarchy mapping table.
     */
    private static final String TABLE_TEMP_HIER_MAPPING = "temp_hier_mapping";

    /**
     * The file containings the script to initialize the database.
     */
    private static final String DATA_SQL_FILE = "test_files/stress/AddData.sql";

    /**
     * The config file for stress test.
     */
    private static final String CONFIG_FILE = "test_files/stress/templateLoader.xml";

    /**
     * The name space the for stress test.
     */
    private static final String NAMESPACE = "com.topcoder.buildutility.template.persistence";

    /**
     * The database connection instance.
     */
    private static Connection connection = null;


    /**
     * Private constructor to prevent this class be instantiated.
     */
    private StressTestHelper() {
        // empty
    }

    /**
     * Add config file for testing.
     *
     * @throws Exception Exception to JUnit.
     */
    public static void addConfigFile() throws Exception {
        removeConfigFile();

        // load the configuration
        ConfigManager.getInstance().add(CONFIG_FILE);
    }

    /**
     * Remove config file for testing.
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
     * Add test datas to the table.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public static void addTestData() throws Exception {
        // clear tables before inserting
        removeTestData();

        Connection conn = getConnection();

        BufferedReader reader = new BufferedReader(new FileReader(DATA_SQL_FILE));
        String sql;
        while ((sql = reader.readLine()) != null) {
            // ececute each sql insert statement,
            if (sql.length() > 2) {
                conn.createStatement().executeUpdate(sql);
            }
        }
    }

    /**
     * <p>
     * delete data from the tables
     * </p>
     *
     * @throws Exception to JUnit
     */
    static void removeTestData() throws Exception {
        clearTable(TABLE_TEMP_HIER_MAPPING);
        clearTable(TABLE_TEMPLATES);
        clearTable(TABLE_TEMP_HIER);
    }

    /**
     * <p>
     * delete the data from the specified table.
     * </p>
     *
     * @param name the table name
     *
     * @throws Exception to JUnit
     */
    private static void clearTable(String name) throws Exception {
        // Clears all rows in the given table.
        getConnection().createStatement().executeUpdate("DELETE FROM " + name);
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
