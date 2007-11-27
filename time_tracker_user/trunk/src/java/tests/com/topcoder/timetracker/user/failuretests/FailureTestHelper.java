/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.user.UserManagerFactory;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure test helper class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
class FailureTestHelper {

    /**
     * Represents the the root of all failure config files.
     */
    public static final String FAILURE_CONFIG_ROOT = "test_files" + File.separator + "failuretests" + File.separator;

    public static final String CONFIG_FILE = FailureTestHelper.FAILURE_CONFIG_ROOT + "config.xml";
    /**
     * <p>
     * private class preventing instantiation.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * Clear previously loaded configurations.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public static void clearNamespaces() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @param fileName
     *            config file to set up environment
     *
     * @throws Exception
     *             when any exception occurs
     */
    public static void loadXMLConfig(String fileName) throws Exception {
        // set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        config.add(file.getCanonicalPath());
    }

    /**
     * <p>
     * Executes the sql scripts in the given sql file.
     * </p>
     * @param connection
     *            Connection instance to access the database
     * @param sqlPath
     *            the path of the sql file to execute
     * @throws SQLException
     *             if exception occurs during database operation
     * @throws IOException
     *             if fails to read the sql file
     */
    private static void executeSqlFile(Connection connection, String sqlPath) throws SQLException, IOException {
        String[] sqlStatements = loadSqlFile(sqlPath);

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            for (int i = 0; i < sqlStatements.length; i++) {
                if (sqlStatements[i].trim().length() != 0) {
                    stmt.executeUpdate(sqlStatements[i]);
                }
            }
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * <p>
     * Inserts some data to the tables which this component depends on.
     * </p>
     * <p>
     * This is used to simplify the testing.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public static void setUpDataBase() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            executeSqlFile(connection, FAILURE_CONFIG_ROOT + "clear_data.sql");
            executeSqlFile(connection, FAILURE_CONFIG_ROOT + "add_data.sql");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Clears all the data from the tables using by this component.
     * </p>
     * <p>
     * This is used to simplify the testing.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public static void tearDownDataBase() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            executeSqlFile(connection, FAILURE_CONFIG_ROOT + "clear_data.sql");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * This method return the sql scripts from the given sql file.
     * </p>
     * @param path
     *            the path of the sql file
     * @return the sql scripts
     * @throws IOException
     *             if fails to read the sql file
     */
    private static String[] loadSqlFile(String path) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        try {
            String line = reader.readLine();
            while (line != null) {
                line = line.trim();
                if (line.length() != 0 && !line.startsWith("--")) {
                    sb.append(line);
                }

                line = reader.readLine();
            }

            return sb.toString().split(";");
        } finally {
            reader.close();
        }
    }

    /**
     * <p>
     * Returns a new connection to be used for persistence.
     * </p>
     * @return the connection instance for database operation
     * @throws ConfigurationException
     *             to JUnit
     * @throws DBConnectionException
     *             to JUnit
     */
    static Connection getConnection() throws DBConnectionException, ConfigurationException {
        return new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
    }

    /**
     * <p>
     * Closes the given Connection instance.
     * </p>
     * @param con
     *            the given Connection instance to close.
     */
    static void closeConnection(Connection con) {
        try {
            if ((con != null) && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given PreparedStatement instance.
     * </p>
     * @param state
     *            the given Statement instance to close.
     */
    static void closeStatement(Statement state) {
        try {
            if (state != null) {
                state.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * This method sets the user manager in the <code>UserManagerFactory</code> to null using reflection.
     * </p>
     */
    public static void resetUserManagerToNull() {
        try {
            Field field = UserManagerFactory.class.getDeclaredField("userManager");
            field.setAccessible(true);
            field.set(null, null);
        } catch (SecurityException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        }
    }
}
