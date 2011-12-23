/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.accuracy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * <p>
 * The base class for accuracy unit tests.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class BaseAccuracyUnitTests {
    /**
     * <p>
     * The application context used in testing.
     * </p>
     */
    protected static final ApplicationContext ACCURAY_APP_CONTEXT;
    /**
     * <p>
     * Represents the connection string.
     * </p>
     */
    private static final String CONNECTION_STRING;

    /**
     * <p>
     * Represents the driver class name.
     * </p>
     */
    private static final String DRIVER_CLASS;

    /**
     * <p>
     * Represents the username.
     * </p>
     */
    private static final String USERNAME;

    /**
     * <p>
     * Represents the password.
     * </p>
     */
    private static final String PASSWORD;

    static {
        Properties config = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream("test_files/accuracy/test.properties");
            config.load(stream);
        } catch (IOException e) {
            // Ignore
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                //Ignore
            }
        }

        DRIVER_CLASS = config.getProperty("driverClassName");
        CONNECTION_STRING = config.getProperty("connectionString");
        USERNAME = config.getProperty("username");
        PASSWORD = config.getProperty("password");

        ACCURAY_APP_CONTEXT = new FileSystemXmlApplicationContext("test_files/accuracy/applicationContext.xml");
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        executeSQL("test_files/accuracy/DBData.sql");
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        executeSQL("test_files/accuracy/DBClear.sql");
    }

    /**
     * Run the given SQL file.
     *
     * @param filePath SQL file path
     *
     * @throws Exception to JUnit
     */
    public static void executeSQL(String filePath) throws Exception {
        Class.forName(DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
        PreparedStatement preparedStatement = null;

        try {
            String content = getFileAsString(filePath);
            for (String st : content.split(";")) {
                preparedStatement = connection.prepareStatement(st);
                preparedStatement.executeUpdate();
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * <p>
     * Gets the file content as string.
     * </p>
     *
     * @param filePath the file path
     *
     * @return the file content
     *
     * @throws Exception to JUnit
     */
    private static String getFileAsString(String filePath) throws Exception {
        StringBuilder buf = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try {
            String s;
            while ((s = reader.readLine()) != null) {
                buf.append(s);
            }
            return buf.toString();
        } finally {
            reader.close();
        }
    }
}
