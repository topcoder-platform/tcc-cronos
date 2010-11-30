/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * This class provides methods for testing this component.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class TestsHelper {
    /**
     * <p>
     * Represents the empty string.
     * </p>
     */
    public static final String EMPTY_STRING = " \t ";

    /**
     * <p>
     * Represents the timestamp format used in tests.
     * </p>
     */
    public static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * <p>
     * Represents the path of test files.
     * </p>
     */
    public static final String TEST_FILES = "test_files" + File.separator;

    /**
     * <p>
     * Represents the configuration file used in tests.
     * </p>
     */
    public static final String CONFIG_FILE = TEST_FILES + "ReliabilityCalculationUtility.xml";

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private TestsHelper() {
        // empty
    }

    /**
     * <p>
     * Gets a connection.
     * </p>
     *
     * @return the connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static Connection getConnection() throws Exception {
        ConfigurationObject config = TestsHelper.getConfig().getChild("reliabilityCalculator1").getChild("config")
            .getChild("reliabilityDataPersistenceConfig");

        // Get configuration of DB Connection Factory
        ConfigurationObject dbConnectionFactoryConfig = Helper.getChildConfig(config, "dbConnectionFactoryConfig");

        // Create database connection factory using the extracted configuration
        DBConnectionFactoryImpl dbConnectionFactory = new DBConnectionFactoryImpl(dbConnectionFactoryConfig);

        return dbConnectionFactory.createConnection();
    }

    /**
     * <p>
     * Gets the rows count of the table.
     * </p>
     *
     * @param connection
     *            the given connection.
     * @param table
     *            the table.
     *
     * @return the rows count.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static int getRowsCount(Connection connection, String table) throws Exception {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM " + table);

        int count = 0;

        while (rs.next()) {
            ++count;
        }

        return count;
    }

    /**
     * <p>
     * Closes the given connection.
     * </p>
     *
     * @param connection
     *            the given connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void closeConnection(Connection connection) throws Exception {
        if ((connection != null) && (!connection.isClosed())) {
            connection.close();
        }
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     *
     * @param connection
     *            the given connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void clearDB(Connection connection) throws Exception {
        Statement stmt = null;

        try {
            stmt = connection.createStatement();

            stmt.execute("DELETE FROM 'informix'.project_result");
            stmt.execute("DELETE FROM 'informix'.project_phase");
            stmt.execute("DELETE FROM 'informix'.project");
            stmt.execute("DELETE FROM 'informix'.project_info");
            stmt.execute("DELETE FROM 'informix'.component_inquiry");
            stmt.execute("DELETE FROM 'informix'.submission");
            stmt.execute("DELETE FROM 'informix'.resource_info");
            stmt.execute("DELETE FROM 'informix'.resource");
            stmt.execute("DELETE FROM 'informix'.upload");
            stmt.execute("DELETE FROM 'informix'.contest_eligibility");
            stmt.execute("DELETE FROM 'informix'.user_reliability");
            stmt.execute("DELETE FROM 'informix'.project_reliability");

        } finally {
            // Close the statement
            stmt.close();
        }
    }

    /**
     * <p>
     * Loads data into the database.
     * </p>
     *
     * @param connection
     *            the given connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void loadDB(Connection connection) throws Exception {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            String[] values = readFile(TEST_FILES + "DBData.sql").split(";");

            for (int i = 0; i < values.length; i++) {
                String sql = values[i].trim();
                if (sql.length() != 0) {
                    stmt.executeUpdate(sql);
                }
            }
        } finally {
            stmt.close();
        }
    }

    /**
     * <p>
     * Gets the configuration object used for tests.
     * </p>
     *
     * @return the configuration object.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static ConfigurationObject getConfig() throws Exception {
        XMLFilePersistence persistence = new XMLFilePersistence();

        // Get configuration
        ConfigurationObject obj = persistence.loadFile(
            "com.topcoder.reliability.ReliabilityCalculationUtility", new File(CONFIG_FILE));

        return obj.getChild("com.topcoder.reliability.ReliabilityCalculationUtility");
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param obj
     *            the given object.
     * @param field
     *            the field name.
     *
     * @return the field value.
     */
    public static Object getField(Object obj, String field) {
        Object value = null;
        try {
            Field declaredField = null;
            try {
                declaredField = obj.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }
            if (declaredField == null) {
                // From the superclass
                declaredField = obj.getClass().getSuperclass().getDeclaredField(field);
            }

            declaredField.setAccessible(true);

            value = declaredField.get(obj);

            declaredField.setAccessible(false);
        } catch (IllegalArgumentException e) {
            // Ignore
        } catch (SecurityException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }

        return value;
    }

    /**
     * <p>
     * Reads the content of a given file.
     * </p>
     *
     * @param fileName
     *            the name of the file to read.
     *
     * @return a string represents the content.
     *
     * @throws IOException
     *             if any error occurs during reading.
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
            return sb.toString();
        } finally {
            try {
                reader.close();
            } catch (IOException ioe) {
                // Ignore
            }
        }
    }
}
