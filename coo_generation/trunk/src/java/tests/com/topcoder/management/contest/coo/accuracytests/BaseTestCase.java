/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;


/**
 * <p>
 * This is a base (helper) class for accuracy test cases.
 * </p>
 *
 * @author myxgyy
 * @version 1.0
 */
abstract class BaseTestCase extends TestCase {
    /**
     * Accuracy test files directory.
     */
    public static final String ACCURACY = "test_files" + File.separator + "accuracy" + File.separator;

    /**
     * The file contains prepare sql statements.
     */
    private static final String PREPARE_FILE = ACCURACY + "prepare.sql";

    /**
     * The file contains clear sql statements.
     */
    private static final String CLEAR_FILE = ACCURACY + "clear.sql";

    /**
     * The configuration file for loading the database connection factory.
     */
    private static final String DB_PROPERTIES = ACCURACY + "DBConnectionFactory.properties";

    /**
     * Represent the connection used to by the testing methods.
     */
    private Connection conn;

    /**
     * Sets up the environments.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        // load the configuration
        conn = getConnection();
        // clear the previous records first
        clearData();
        // prepare data
        prepareData();
    }

    /**
     * Clears all the test environments.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        clearData();

        conn.close();
    }

    /**
     * Returns the default connection.
     *
     * @return the default connection.
     * @throws Exception
     *             to JUnit
     */
    protected static Connection getConnection() throws Exception {
        // returns the default connection (that is set to be non-auto commit).
        Connection con = getConnectionFactory().createConnection();

        return con;
    }

    /**
     * Returns the connection factory.
     *
     * @return the connection factory
     * @throws Exception
     *             to JUnit
     */
    protected static DBConnectionFactory getConnectionFactory()
        throws Exception {
        ConfigurationFileManager manager = new ConfigurationFileManager(DB_PROPERTIES);
        ConfigurationObject rootObject = manager.getConfiguration(
                "default");

        return new DBConnectionFactoryImpl(rootObject);
    }

    /**
     * Gets the configuration object from the given file.
     *
     * @param file
     *            the configuration file.
     * @return the configuration object.
     * @throws Exception
     *             to JUnit.
     */
    protected static ConfigurationObject getConfigurationObject(
        String file) throws Exception {
        ConfigurationFileManager manager = new ConfigurationFileManager();

        manager.loadFile("root", file);

        ConfigurationObject config = manager
            .getConfiguration("root");

        return config;
    }

    /**
     * <p>
     * Clear the data in table.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    private void clearData() throws Exception {
        executeSqlFile(CLEAR_FILE);
    }

    /**
     * <p>
     * Prepare the data in table.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private void prepareData() throws Exception {
        executeSqlFile(PREPARE_FILE);
    }

    /**
     * Execute the sql statements in a file.
     *
     * @param filename
     *            the sql file.
     * @throws Exception
     *             to JUnit.
     */
    private void executeSqlFile(String filename) throws Exception {
        InputStream in = new FileInputStream(filename);

        byte[] buf = new byte[1024];
        int len = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        String content;

        try {
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

            content = out.toString();
        } finally {
            in.close();
            out.close();
        }

        List<String> sqls = new ArrayList<String>();
        int lastIndex = 0;

        // parse the sqls
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }

        for (int i = 0; i < sqls.size(); i++) {
            doSQLUpdate((String) sqls.get(i));
        }
    }

    /**
     * Does the update operation to the database.
     *
     * @param sql
     *            the SQL statement to be executed.
     * @throws Exception
     *             to JUnit.
     */
    private void doSQLUpdate(String sql) throws Exception {
        PreparedStatement ps = null;

        try {
            // creates the prepared statement
            ps = conn.prepareStatement(sql);


            // do the update
            ps.executeUpdate();
        } finally {
            // close the resources
            closeStatement(ps);
        }
    }

    /**
     * Closes the statement silently.
     *
     * @param stmt
     *            the statement to close.
     */
    private static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
