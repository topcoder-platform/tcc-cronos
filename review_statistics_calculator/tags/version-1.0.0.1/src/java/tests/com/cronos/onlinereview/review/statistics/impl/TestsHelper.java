/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.config.ConfigManager;

/**
 * Test Helper for the component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestsHelper {
    /**
     * Namespace to use in tests.
     */
    public static final String NAMESPACE = "com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator";

    /**
     * The test files path.
     */
    private static final String TEST_FILES = "test_files" + File.separator;

    /**
     * Private test helper.
     */
    private TestsHelper() {
    }

    /**
     * Get the URL string of the file.
     *
     * @param fileName the file name
     * @return the url string.
     */
    public static String getURLString(String fileName) {
        return "file:///" + new File(fileName).getAbsolutePath();
    }

    /**
     * Load a config file to ConfigManager.
     *
     * @param configFile XML configuration file to be added to the ConfigManager instance.
     * @throws Exception exception to the caller.
     */
    public static void loadSingleConfigFile(String configFile) throws Exception {
        clearConfig();
        ConfigManager.getInstance().add(configFile);
    }

    /**
     * Clear all the configs.
     *
     * @throws Exception exception to the caller.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator<?> it = cm.getAllNamespaces();
        List<String> nameSpaces = new ArrayList<String>();

        while (it.hasNext()) {
            nameSpaces.add((String) it.next());
        }

        for (String namespace : nameSpaces) {
            cm.removeNamespace(namespace);
        }
    }

    /**
     * Get configuration object from XML default file.
     *
     * @param fileName The configuration file name
     *
     * @throws Exception for JUnit.
     *
     * @return the configuration object.
     */
    public static ConfigurationObject loadConfig(String fileName) throws Exception {
        // load the ConfigurationObject from the input file
        XMLFilePersistence xmlFilePersistence = new XMLFilePersistence();
        ConfigurationObject cfgObject = xmlFilePersistence.loadFile("name", new File(TEST_FILES + fileName));

        return cfgObject;
    }

    /**
     * Gets value for field of given object.
     *
     * @param obj the given object.
     * @param field the field name.
     * @param useClass the field owner.
     *
     * @throws Exception for JUnit.
     *
     * @return the field value.
     */
    public static Object getField(Object obj, String field, Class<?> useClass) throws Exception {
        Object value = null;

        Field declaredField = useClass.getDeclaredField(field);
        declaredField.setAccessible(true);

        value = declaredField.get(obj);

        return value;
    }

    /**
     * Update data into database.
     *
     * @param connectionFactory the database connection factory
     * @param sqlFile the sql file
     *
     * @throws IOException if error occurs during reading file
     * @throws SQLException if erros occurs during database interaction
     * @throws DBConnectionException if error occurs creating connection
     */
    public static void updateData(DBConnectionFactory connectionFactory, String sqlFile) throws IOException,
        SQLException, DBConnectionException {
        // Read sql file
        BufferedReader reader = new BufferedReader(new FileReader(sqlFile));
        StringBuffer sb = new StringBuffer();
        String thisLine;
        while ((thisLine = reader.readLine()) != null) {
            thisLine.replace("\n", "");
            sb.append(thisLine);
        }

        // Break queries using ';' as delimiter
        String strFile = sb.toString();
        String[] querys = strFile.split(";");

        // Execute queries
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.setAutoCommit(false);
            for (String query : querys) {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.execute();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // ignore
            }
            throw e;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e1) {
                // ignore
            }
        }
    }
}
