/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * <p>
 * Base Test Case for accuracy testing of persistence class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public abstract class BaseTestCase extends TestCase {

    /**
     * <p>
     * Represents the namespace for database connection factory.
     * </p>
     */
    public static final String DB_CONNECTION_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Represents the database connection factory.
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * Represents the database connection.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        clearNamespaces();

        ConfigManager.getInstance().add("accuracytests" + File.separator + "accuracy.xml");

        dbConnectionFactory = new DBConnectionFactoryImpl(DB_CONNECTION_FACTORY_NAMESPACE);

        connection = dbConnectionFactory.createConnection();
        
        clearTables();
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        clearTables();
        connection.close();
        clearNamespaces();
    }

    /**
     * <p>
     * Clear the testing configuration.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    private static void clearNamespaces() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Clear all the database tables.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void clearTables() throws Exception {
        Statement stmt = connection.createStatement();

        stmt.executeUpdate("DELETE FROM entity_status_history");
        stmt.executeUpdate("DELETE FROM entity_status");
        stmt.executeUpdate("DELETE FROM all_user");
        stmt.executeUpdate("DELETE FROM principal_role");
        stmt.executeUpdate("DELETE FROM principal");
        stmt.executeUpdate("DELETE FROM role");
        stmt.executeUpdate("DELETE FROM manager_category");
        stmt.executeUpdate("DELETE FROM category");
        stmt.executeUpdate("DELETE FROM contact");
        stmt.executeUpdate("DELETE FROM company");
        stmt.executeUpdate("DELETE FROM email");
        stmt.executeUpdate("DELETE FROM user");
        stmt.executeUpdate("DELETE FROM client");
    }

    /**
     * <p>
     * Return an instance of Database connection.
     * </p>
     * @return the database connection instance.
     */
    public Connection getConnection() {
        return connection;
    }
}
