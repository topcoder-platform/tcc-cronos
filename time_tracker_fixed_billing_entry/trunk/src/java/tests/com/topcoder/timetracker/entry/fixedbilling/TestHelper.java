/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import java.io.File;

import java.sql.Connection;
import java.sql.Statement;

import java.util.Iterator;


/**
 * The test helper class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public final class TestHelper {
    /** The file path for testing. */
    public static final String DB_CONFIGE_FILE_NAME = "test_files/DbConfig.xml";

    /** The file path for testing. */
    public static final String OBJECT_FACTORY_CONFIGE_FILE_NAME = "test_files/ObjectFactoryConfig.xml";

    /** The file path for testing. */
    public static final String J2EE_CONFIGE_FILE_NAME = "test_files/J2eeConfig.xml";

    /** The file path for testing. */
    public static final String DB_FACTORY_CONFIGE_FILE_NAME = "test_files/DBConnectionFactoryImpl.xml";

    /**
     * The private constructor.
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Load the supplied config file.
     * </p>
     *
     * @param file the config file to load.
     *
     * @throws Exception to JUnit.
     */
    public static void loadConfigFile(String file) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File(file).getCanonicalPath());
    }

    /**
     * Unload all configurations from files.
     *
     * @throws Exception to JUnit
     */
    public static void removeNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator itor = cm.getAllNamespaces(); itor.hasNext();) {
            String str = (String) itor.next();
            cm.removeNamespace(str);
        }
    }

    /**
     * Run the sql statement.
     *
     * @param sql to run.
     *
     * @throws Exception to Junit.
     */
    public static void executeSQL(String sql) throws Exception {
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory");
        String connectionName = "Informix_Connection_Producer";
        Connection connection = connectionFactory.createConnection(connectionName);
        Statement state = connection.createStatement();
        state.execute(sql);
        state.close();
        connection.close();
    }
}
