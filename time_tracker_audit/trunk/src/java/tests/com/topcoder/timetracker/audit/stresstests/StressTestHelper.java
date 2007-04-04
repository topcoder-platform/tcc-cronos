/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.stresstests;

import com.topcoder.util.config.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

/**
 * <p>
 * This class is the helper class for this stress test.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
final public class StressTestHelper {

    /**
     * <p>
     * Creates a new instance of <code>UnitTestHelper</code> class. The private constructor prevents the creation of a
     * new instance.
     * </p>
     */
    private StressTestHelper() {
    }

    /**
     * <p>
     * Loads all the configurations from the corresponding files.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void loadConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("DBConnectionFactory_Config.xml");
        configManager.add("InformixAuditPersistence_Config.xml");
        configManager.add("SearchBuilder_Config.xml");
        configManager.add("Logging_Wrapper.xml");
        configManager.add("AuditDelegate_Config.xml");
        configManager.add("AuditSessionBean_Config.xml");
        configManager.add("DefaultValuesContainer_Config.xml");
        configManager.add("com.topcoder.naming.jndiutility", "JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
    }

    /**
     * <p>
     * clears all the configurations in the config manager.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();
        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }
    
    /**
     * <p>
     * Initializes all the data in the database for stress test.
     * </p>
     *
     * @param connection database connection.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void initData(Connection connection) throws SQLException {
        clearDatabase(connection);
        Statement statement = connection.createStatement();
        try {
            InputStream input = StressTestHelper.class.getClassLoader().getResourceAsStream("sql/prepare.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if ((line.length() != 0) && !line.startsWith("--")) {
                    statement.executeUpdate(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
    }
    
    /**
     * <p>
     * Clears the data of the database.
     * </p>
     *
     * @param connection the DB connection
     * @throws SQLException any exception when clear the database.
     */
    public static void clearDatabase(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("delete from audit_detail");
        stmt.executeUpdate("delete from audit");
        stmt.executeUpdate("delete from user_account");
        stmt.executeUpdate("delete from account_status");
        stmt.executeUpdate("delete from application_area");
        stmt.executeUpdate("delete from client");
        stmt.executeUpdate("delete from project");
        stmt.executeUpdate("delete from company");
    }
}