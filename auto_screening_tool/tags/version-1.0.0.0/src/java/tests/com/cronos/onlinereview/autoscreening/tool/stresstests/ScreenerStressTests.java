/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.stresstests;

import java.sql.Connection;

import com.cronos.onlinereview.autoscreening.tool.Screener;

import junit.framework.TestCase;

/**
 * 
 * Stress tests for the Screener workhorse, which will test functionality of
 * the component under large scale accessing.
 * 
 * @author slion
 * @version 1.0
 */
public class ScreenerStressTests extends TestCase {

    /**
     * Represents the iterations that the stress test benchmarks.
     */
    private static final int ITERATION = 10;

    /**
     * Represents the path of the logging wrapper config file.
     */
    private static final String LOGGING_WRAPPER_CONFIG_FILE = "test_files/stress/logging_wrapper.xml";

    /**
     * Represents the path of the file upload config file.
     */
    private static final String FILE_UPLOAD_CONFIG_FILE = "test_files/stress/file_upload.xml";

    /**
     * Represents the path of the db connection factory config file.
     */
    private static final String DBCONNECTION_CONFIG_FILE = "test_files/stress/dbconfig.xml";

    /**
     * The namespace of the DBConnectionFactory.
     */
    private static final String DB_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * Represents the path of the screener config file.
     */
    private static final String SCREENER_CONFIG_FILE = "test_files/stress/screener.xml";

    /**
     * Represents the path of the object factory config file.
     */
    private static final String OBJECT_FACTORY_CONFIG_FILE = "test_files/stress/object_factory.xml";

    /**
     * Represents the namespace of the screener.
     */
    private static final String SCREENER_NAMESPACE = "com.cronos.onlinereview.autoscreening.tool.Screener";

    /**
     * Represent the sql connection used to by the tests.
     */
    private Connection conn = null;

    /**
     * Sets up the environments.
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        CMHelper.clearCM();
        CMHelper.loadConfig(LOGGING_WRAPPER_CONFIG_FILE);
        CMHelper.loadConfig(DBCONNECTION_CONFIG_FILE);
        CMHelper.loadConfig(FILE_UPLOAD_CONFIG_FILE);
        CMHelper.loadConfig(SCREENER_CONFIG_FILE);
        CMHelper.loadConfig(OBJECT_FACTORY_CONFIG_FILE);

        conn = DBHelper.getConnection(DB_NAMESPACE);
        DBHelper.removeData(conn);
        DBHelper.initData(conn);
    }

    /**
     * Clears all the test environments.
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        DBHelper.removeData(conn);
        DBHelper.closeConnection(conn);
        CMHelper.clearCM();
    }

    /**
     * Test creating the Screeners under large scale.
     * @throws Exception to JUnit.
     */
    public void testCreatingScreeners() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < ITERATION * 100; ++i) {
            assertNotNull("Failed to create Screener:",
                    new Screener(i + 1, SCREENER_NAMESPACE));
        }
        System.out.println("Creating Screener instance for " + ITERATION * 100
                + " times takes " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * Test initialize the Screeners under large scale.
     * @throws Exception to JUnit.
     */
    public void testInitializeScreener() throws Exception {
        long start = System.currentTimeMillis();
        Screener screener = new Screener(2, SCREENER_NAMESPACE);
        for (int i = 0; i < ITERATION; ++i) {
			DBHelper.insertScreeningTask(conn, i + 1);
            screener.initialize();
        }
        System.out.println("Initializing Screener instance for " + ITERATION
                + " times takes " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * Test screen action under large scale.
     * @throws Exception to JUnit.
     */
    public void testScreen() throws Exception {
		DBHelper.clearTasks(conn);
        long start = System.currentTimeMillis();
        Screener screener = new Screener(1, SCREENER_NAMESPACE);
        for (int i = 0; i < ITERATION; ++i) {
            DBHelper.insertPendingTask(conn, i + 1);
            screener.screen();
        }
        System.out.println("Performing screening action for " + ITERATION
                + " times takes " + (System.currentTimeMillis() - start) + "ms");
    }
}