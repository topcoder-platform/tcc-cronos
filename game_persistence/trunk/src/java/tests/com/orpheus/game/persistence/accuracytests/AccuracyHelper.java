/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * This class contains helper methods to use in accuracy test cases.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class AccuracyHelper {
    /**
     * The namespace of the DB Connection Factory component.
     */
    public static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Load all neccessary configration files for the component.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void loadConfiguration(String fileName) throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add(fileName);
    }

    /**
     * <p>
     * Clear all configuration namespaces.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void clearAllConfigurationNS() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Load the configuration files of the base component for testing.
     *
     * @throws Exception to JUnit.
     */
    public static void loadBaseConfig() throws Exception {
        AccuracyHelper.loadConfiguration("accuracy/DBFactory.xml");
        AccuracyHelper.loadConfiguration("accuracy/ObjectFactory.xml");
        AccuracyHelper.loadConfiguration("accuracy/Game_Persistence_Config.xml");
    }

    /**
     * Reads content of a text file
     *
     * @param file The file to read content.
     * @return The content of the file.
     * @throws Exception to JUnit.
     */
    public static String readFile(String file) throws Exception {

        StringBuffer contents = new StringBuffer();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = input.readLine()) != null) {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                // ignore
            }
        }

        return contents.toString();
    }

    /**
     * <p>
     * Setup the test data in the database for testing.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void setupDatabase() throws Exception {
        cleanupDatabase();

        Connection conn = null;
        try {
            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();

            String sqlContent = readFile("test_files/accuracy/test_data.sql");

            conn.createStatement().execute(sqlContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    /**
     * <p>
     * Cleanup the database after testing.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void cleanupDatabase() throws Exception {
        Connection conn = null;
        try {
            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();

            String sqlContent = readFile("test_files/accuracy/cleanup.sql");

            conn.createStatement().execute(sqlContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    /**
     * <p>
     * Convert the given input stream to a string.
     * </p>
     *
     * @param is the input stream to convert.
     * @return the converted string.
     * @throws Exception to JUnit.
     */
    public static String convertISToString(InputStream is) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();

        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                // ignore
            }
        }
        return buffer.toString();
    }

    /**
     * Compare a string to a date value. The string must be in the format 'yyyy/M/d'.
     *
     * @param expected The expected date.
     * @param actual   The actual date.
     * @throws Exception to JUnit.
     */
    public static boolean compareStringToDate(String expected, Date actual) throws Exception {

        DateFormat df = new SimpleDateFormat("yyyy/M/d");

        Date expectedDate = df.parse(expected);

        return expectedDate.equals(actual);
    }

    /**
     * Convert a string to date value. The string must be in the format 'yyyy/M/d'.
     *
     * @param input The input string to convert
     * @return The corresponding date value.
     * @throws Exception to JUnit.
     */
    public static Date convertStringToDate(String input) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy/M/d");
        return df.parse(input);
    }

    /**
     * Return the ids from the hosting_slot table.
     *
     * @return the ids from the hosting_slot table.
     */
    public static List getHostingSlotIds() throws Exception {
        List ids = new ArrayList();
        Connection conn = null;
        ResultSet rs = null;
        try {

            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();

            rs = conn.createStatement().executeQuery("SELECT id FROM hosting_slot");

            while (rs.next()) {
                ids.add(new Long(rs.getLong("id")));
            }
            rs.close();
            return ids;
        } finally {
            rs.close();
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Return the bid id from the hosting_slot table using the slot_id.
     *
     * @return the bid id corresponding to the slot_id, or -1 if it does not exist.
     */
    public static long getBidIdFromSlotId(long id) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        try {

            DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
            conn = dbFactory.createConnection();

            rs = conn.createStatement().executeQuery("SELECT bid_id FROM hosting_slot WHERE id=" + id);

            if (rs.next()) {
                return rs.getLong("bid_id");
            }
            return -1;
        } finally {
            rs.close();
            if (conn != null) {
                conn.close();
            }
        }
    }
}