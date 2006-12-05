/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.util.Iterator;

/**
 * <p>
 * This class contains helper methods to use in failure test cases.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class FailureHelper {
    /**
     * The namespace of the DB Connection Factory component.
     */
    public static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * Private constructor to prevent initialization.
     */
    private FailureHelper() {
    }

    /**
     * <p>
     * Load all neccessary configration files for the component.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void loadConfiguration() throws Exception {
        clearAllConfigurationNS();
        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add("failure/Config.xml");
        configManager.add("failure/DBConnectionFactory.xml");
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

            String sqlContent = readFile("test_files/failure/test_data.sql");

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

            String sqlContent = readFile("test_files/failure/cleanup.sql");

            conn.createStatement().execute(sqlContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {                
                conn.close();
            }
        }
    }

    /**
     * Convert the given XML string to an element.
     *
     * @param xml the xml string
     * @return the element from the given xml.
     * @throws Exception to JUnit.
     */
    public static Element convertXmlString(String xml) throws Exception {
        DOMParser parser = new DOMParser();
        parser.parse(new InputSource(new StringReader(xml)));

        return parser.getDocument().getDocumentElement();
    }
}
