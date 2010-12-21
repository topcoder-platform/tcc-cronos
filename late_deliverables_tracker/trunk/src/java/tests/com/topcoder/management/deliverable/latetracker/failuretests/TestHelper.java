/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.failuretests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * Helper class of getting configuration object from the file.
 *
 * @author gjw99, TCSDEVELOPER
 * @version 1.1
 */
final class TestHelper {
    /**
     * The tables to be cleaned.
     */
    private static final String[] ALL_TABLE_NAMES = new String[] {"id_sequences"};

    /**
     * Config fiiles to be loaded.
     */
    private static final String[] COMPONENT_FILE_NAMES = new String[] { "config/Project_Management.xml",
        "config/Phase_Management.xml", "config/Upload_Resource_Search.xml", "config/SearchBuilderCommon.xml",
        "failure/Project_Management_Invalid.xml"};

    /**
     * The database connection factory instance.
     */
    private static DBConnectionFactory dbFactory;

    /**
     * The database connection.
     */
    private static Connection connection;


    /**
     * Gets the configuration object from the given file with the given namespace.
     *
     * @param file
     *            the configuration file.
     * @param namespace
     *            the namespace.
     * @return the configuration object.
     * @throws Exception
     *             to JUnit.
     */
    static ConfigurationObject getConfigurationObject(String file, String namespace)
        throws Exception {
        ConfigurationFileManager manager = new ConfigurationFileManager();

        manager.loadFile("root", file);

        ConfigurationObject config = manager.getConfiguration("root");

        return config.getChild(namespace);
    }

    /**
     * <p>
     * Sets the value of a private field in the given class.
     * </p>
     *
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be set
     * @param value the value to set
     * @param classType the class to get field
     * @throws RuntimeException if any error occurred when calling this method
     */
    public static void setPrivateField(Class<?> classType, Object instance, String name, Object value) {
        Field field = null;
        try {
            // get the reflection of the field
            field = classType.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);
            // set the value
            field.set(instance, value);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred when calling setPrivateField method.", e);
        }
    }

    /**
     * <p>
     * Gets the sql file content.
     * </p>
     *
     * @param file
     *            The sql file to get its content.
     * @return The content of sql file.
     * @throws Exception
     *             to JUnit
     */
    private static String getSql(String file) throws Exception {
        StringBuilder sql = new StringBuilder();
        InputStream is = new FileInputStream(file);

        if (is == null) {
            throw new FileNotFoundException("Not found: " + file);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(is));

        try {
            for (String s = in.readLine(); s != null; s = in.readLine()) {
                int commentIndex = s.indexOf("--");

                if (commentIndex >= 0) { // Remove any SQL comment
                    s = s.substring(0, commentIndex);
                }

                sql.append(s).append(' '); // The BufferedReader drops newlines so insert
                // whitespace
            }
        } finally {
            in.close();
        }

        return sql.toString();
    }


    /**
     * Returns a connection instance.
     *
     * @return a connection instance.
     * @throws Exception
     *             not for this test case.
     */
    protected static Connection getConnection() throws Exception {
        if (connection == null) {
            dbFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
            connection = dbFactory.createConnection();
        }

        return connection;
    }

    /**
     * Closes the connection.
     */
    protected static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                // do nothing.
            }
        }

        connection = null;
    }

    /**
     * helper method to close a statement.
     *
     * @param stmt
     *            statement to close.
     */
    protected static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                // do nothing
            }
        }
    }

    /**
     * Executes the sql file.
     *
     * @param file
     *            The sql file to be executed.
     * @throws Exception
     *             to JUnit
     */
    public static void executeSqlFile(String file) throws Exception {
        TestHelper.cleanTables();
        String sql = getSql(file);

        Connection conn = null;
        Statement stmt = null;

        StringTokenizer st = new StringTokenizer(sql, ";");

        try {
            conn = getConnection();
            stmt = conn.createStatement();

            for (int count = 1; st.hasMoreTokens(); count++) {
                String statement = st.nextToken().trim();

                if (statement.length() > 0) {
                    stmt.addBatch(statement);
                }
            }

            stmt.executeBatch();
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }

    /**
     * Cleans up records in the given table names.
     *
     * @throws Exception
     *             to JUnit
     */
    public static void cleanTables() throws Exception {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();

            for (int i = 0; i < ALL_TABLE_NAMES.length; i++) {
                String sql = "delete from " + ALL_TABLE_NAMES[i];
                stmt.addBatch(sql);
            }

            stmt.executeBatch();
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }


    /**
     * Adds all required configuration to config manager.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void addConfig() throws Exception {
        clearNamespace();

        ConfigManager configManager = ConfigManager.getInstance();

        // init db factory
        configManager.add("config/DB_Factory.xml");

        // load logging wrapper configuration
        configManager.add("config/Logging_Wrapper.xml");

        // add all dependencies config
        for (String config : COMPONENT_FILE_NAMES) {
            configManager.add(config);
        }
    }

    /**
     * Clears all namespace under config manager.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void clearNamespace() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        Iterator<?> iter = configManager.getAllNamespaces();

        while (iter.hasNext()) {
            configManager.removeNamespace((String) iter.next());
        }
    }
}