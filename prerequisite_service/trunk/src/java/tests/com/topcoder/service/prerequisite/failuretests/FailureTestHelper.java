/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.failuretests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;


/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class FailureTestHelper {
    /** Represents the file folder for testing. */
    private static final String TEST_FOLDER = "failure_tests";

    /** Represents the db connection factory for testing. */
    private static DBConnectionFactory connFactory;

    /**
     * <p>
     * Creates a new instance of <code>FailureTestHelper</code> class. The private constructor prevents the creation of
     * a new instance.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * Prepare data for test.
     * </p>
     *
     * @param connection database connection.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void prepareData(Connection connection) throws SQLException {
        clearDatabase(connection);
        executeScript(connection, TEST_FOLDER + "/sql/prepare.sql");
    }

    /**
     * <p>
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void clearDatabase(Connection connection) throws SQLException {
        executeScript(connection, TEST_FOLDER + "/sql/clear.sql");
    }

    /**
     * <p>
     * Executes the sql script from the given file.
     * </p>
     *
     * @param connection database connection.
     * @param fileName the file name.
     *
     * @throws SQLException error occurs when access the database.
     */
    private static void executeScript(Connection connection, String fileName)
        throws SQLException {
        Statement statement = connection.createStatement();

        try {
            InputStream input = FailureTestHelper.class.getClassLoader().getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if ((line.length() != 0) && !line.startsWith("--")) {
                    statement.executeUpdate(line);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * Get the DBConnectionFactory instance.
     * </p>
     *
     * @return the DBConnectionFactory instance.
     *
     * @throws Exception any exception during the create DBConnectionFactory process.
     */
    public static DBConnectionFactory getDBConnectionFactory() throws Exception {
        if (connFactory == null) {
            ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(TEST_FOLDER +
                    "/failuretests.properties");

            connFactory = new DBConnectionFactoryImpl(configurationFileManager.getConfiguration("DBConnectionFactory"));
        }

        return connFactory;
    }

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @return the connection created from DBConnectionFactory.
     *
     * @throws Exception any exception during the create connection process.
     */
    public static Connection getConnection() throws Exception {
        return getDBConnectionFactory().createConnection();
    }
}
