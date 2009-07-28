/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import junit.framework.TestCase;

import java.sql.Connection;
import java.util.Iterator;

/**
 * This is a base class for all accuracy test cases. It loads configuration settings, database connection
 * and clean up for the test cases.
 *
 * @author tuenm
 * @version 1.0
 */
public class BaseAccuracyTest extends TestCase {
    /**
     * The accuracy test configuration file.
     */
    public static final String CONNECTION_FACTORY_NS = "accuracy/ConnectionFactory.xml";

    /**
     * The configuration namespace for phase handlers
     */
    public static final String PHASE_HANDLER_NS = "com.cronos.onlinereview.phases.Handler";

    /**
     * Represents the DBConnectionFactory instance.
     */
    private DBConnectionFactory dbFactory;

    /**
     * Represents the database connection instance.
     */
    private Connection conn;

    /**
     * <p/>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        // clear all configuration namespaces
        clearAllNamespace();

        // load the configuration file
        configManager.add(CONNECTION_FACTORY_NS);

        // initialize DBConnectionFactory
        this.dbFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
    }

    /**
     * <p/>
     * Cleans up the test environment.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        // clear all configuration namespaces
        clearAllNamespace();

        closeConnection();
        conn = null;
        dbFactory = null;
    }

    /**
     * Clear all cofiguration namepsaces.
     *
     * @throws Exception to JUnit.
     */
    protected void clearAllNamespace() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        Iterator iter = configManager.getAllNamespaces();

        while (iter.hasNext()) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Returns a connection instance.
     *
     * @return a connection instance.
     * @throws Exception not for this test case.
     */
    protected Connection getConnection() throws Exception {
        if (conn == null) {
            conn = dbFactory.createConnection();
        }
        return conn;
    }


    /**
     * Closes the connection.
     *
     * @throws Exception not for this test case.
     */
    private void closeConnection() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }
}
