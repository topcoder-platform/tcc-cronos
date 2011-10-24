/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.accuracytests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;


/**
 * <p>This class provides methods for testing this component.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestsHelper {

    /**
     * <p>Represents the path of test files.</p>
     */
    private static final String TEST_FILES = "test_files" + File.separator + "accuracy" + File.separator;

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private TestsHelper() {
        // empty
    }

    /**
     * <p>Gets a connection.</p>
     *
     * @param appContext the application context.
     *
     * @return the connection.
     *
     * @throws Exception to JUnit.
     */
    public static Connection getConnection(ApplicationContext appContext)
        throws Exception {
        try {
            DataSource dataSource = (DataSource) appContext.getBean("dataSource");

            return dataSource.getConnection();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * <p>Closes the given connection.</p>
     *
     * @param connection the given connection.
     *
     * @throws Exception to JUnit.
     */
    public static void closeConnection(Connection connection)
        throws Exception {
        if ((connection != null) && (!connection.isClosed())) {
            connection.close();
        }
    }

    /**
     * <p>Loads data into the database.</p>
     *
     * @param connection the connection.
     *
     * @throws Exception to JUnit.
     */
    public static void loadDB(Connection connection) throws Exception {
        executeSQL(connection, TEST_FILES + "insert.sql");
    }

    /**
     * <p>Clears the database.</p>
     *
     * @param connection the connection.
     *
     * @throws Exception to JUnit.
     */
    public static void clearDB(Connection connection) throws Exception {
        executeSQL(connection, TEST_FILES + "clear.sql");
    }

    /**
     * <p>Executes the SQL statements in the file. Empty lines will be ignored.</p>
     *
     * @param connection the connection.
     * @param file the file.
     *
     * @throws Exception to JUnit.
     */
    private static void executeSQL(Connection connection, String file)
        throws Exception {
        String[] values = readFile(file).split(";");

        Statement statement = connection.createStatement();

        try {
            for (int i = 0; i < values.length; i++) {
                String sql = values[i].trim();

                if (sql.length() != 0) {
                    statement.executeUpdate(sql);
                }
            }
        } finally {
            statement.close();
        }
    }

    /**
     * <p>Reads the content of a given file.</p>
     *
     * @param fileName the name of the file to read.
     *
     * @return a string represents the content.
     *
     * @throws IOException if any error occurs during reading.
     */
    private static String readFile(String fileName) throws IOException {
        Reader reader = new FileReader(fileName);

        try {
            // Create a StringBuilder instance
            StringBuilder sb = new StringBuilder();

            // Buffer for reading
            char[] buffer = new char[1024];

            // Number of read chars
            int k = 0;

            // Read characters and append to string builder
            while ((k = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, k);
            }

            // Return read content
            return sb.toString().replace("\r\n", "\n");
        } finally {
            reader.close();
        }
    }
}
