/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.accuracytests;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * This class is used for testing so that testing can be simple.
 * @author KLW
 * @version 1.1
 */
public class TestHelper {
    /**
     * configuration file for test.
     */
    public static final String FILE_MANAGER_CONFIG = "test_files/accuracy/ManagerConfig.xml";
    /**
     * configuration file for test.
     */
    public static final String FILE_BEAN_CONFIG = "test_files/accuracy/BeanConfig.xml";
    /**
     * 
     */
    public static final String FILE_DAO_CONFIG = "test_files/accuracy/DAOConfig.xml";

    /**
     * table name for test.
     */
    public static final String TEST_TABLE = "service_details";

    /**
     * test namespace.
     */
    public static final String TEST_DAO_NAMESPACE = "com.topcoder.timetracker.invoice.servicedetail.dao.impl";

    /**
     * test namespace of DB connection.
     */
    public static final String TEST_CONN_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * test namespace.
     */
    public static final String TEST_NAMESPACE = "com.topcoder.timetracker.invoice.servicedetail.impl";

    /**
     * Load configuration file to config ConfigManager.
     */
    public static void loadConfiguration(String fileName) {
        ConfigManager cm = ConfigManager.getInstance();

        try {
            File fileConfig = new File(fileName);

            String tempConfig = fileConfig.getAbsolutePath();
            cm.add(tempConfig);
        } catch (UnknownNamespaceException e3) {
            //
        } catch (ConfigManagerException e4) {
            //
        }
    }

    /**
     * Release all configuration namespace.
     */
    public static void releaseConfiguration() {
        ConfigManager cm = ConfigManager.getInstance();

        try {
            for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
                cm.removeNamespace((String) iter.next());
            }
        } catch (UnknownNamespaceException e) {
            //
        }
    }

    /**
     * @param namespace
     *            namespace to load configuration
     * @return database connectioin factory
     * @throws ConfigurationException
     *             throw configuration exception
     */
    public static DBConnectionFactory getDBConnectionFactory(String namespace) throws ConfigurationException {
        DBConnectionFactory connectionFactory;

        try {
            connectionFactory = new DBConnectionFactoryImpl(namespace);
        } catch (ConfigurationException e1) {
            e1.printStackTrace();
            throw new ConfigurationException(
                    "Errors occurred while loading configuration settings or required properties", e1);
        } catch (UnknownConnectionException e2) {
            throw new ConfigurationException(e2.getMessage(), e2);
        }

        return connectionFactory;
    }

    /**
     * <p>
     * Deletes data from table used by this component.
     * </p>
     * @param table
     *            table name
     */
    public static void clearTable() {
        try {
            Connection conn = getDBConnectionFactory(TEST_CONN_NAMESPACE).createConnection();
            PreparedStatement SQL = conn.prepareStatement("DELETE FROM " + " " + TEST_TABLE);
            SQL.execute();

            conn.close();
        } catch (SQLException e) {
            System.out.println("There is no records in " + TEST_TABLE + ",so delete error!");
        } catch (DBConnectionException e) {
            System.out.println("Failed connect to database!");
        } catch (ConfigurationException e) {
            System.out.println("Failed connect to database!");
        }
    }

    /**
     * Prepare data in database for testing.
     */
    public static void setUpDatabase() {
        clearTable();

        try {
            Connection conn = getDBConnectionFactory(TEST_CONN_NAMESPACE).createConnection();
            String INS = " INSERT INTO "
                    + TEST_TABLE
                    + "(service_detail_id ,  time_entry_id, invoice_id, rate, amount, creation_date, creation_user, modification_date, modification_user)VALUES";
            String SQL;
            SQL = INS
                    + "(1, 77, 42, 13559, 30907.17, '2007-04-16 04:12:21', 'S2K2yko1AuAkR8237TkeY O7Wj97fM HaG98Pz6KQJ6BYYrXPHegyL3tfzY9 d9M', '2008-11-26 14:24:27', 'ZwY5Z8EvAJxego1xhsN24dMNYGU699yZaegmAx278qV46jFZJsUFG850s');";
            SQL = SQL
                    + INS
                    + "(2, 33, 61, 76065, 54731.98, '2010-06-12 20:42:21', 'gYuY9WnlOdF8ezbWYzz', '2011-01-30 22:06:07', 'Le');";
            SQL = SQL
                    + INS
                    + "(3, 72, 75, 27118, 63738.46, '2010-12-25 03:52:25', '5hJuYt7838kzf72zF3zpdHHT3Z31bd6J00c8D8 60HK3 zis8gmHI96J3u8GNOYs', '2009-06-25 20:29:47', 'DG3Wx05rUK8wGG7KXCl2gRqWiSRj');";
            SQL = SQL
                    + INS
                    + "(4, 66, 42, 67801, 74324.55, '2010-08-01 05:19:01', 'WajTS O5vBr45tWEi1KIjGgol2oFmTpKIx21', '2008-10-17 15:41:02', 'fnFfgu67842nuOBA449rI7mD');";
            SQL = SQL
                    + INS
                    + "(5, 78, 25, 40976, 52752.71, '2012-01-26 04:39:04', 'KlzT0nr', '2011-02-11 19:48:58', 'XR Uw4UxBVmbBH55rGbBKH9Ay3kccIrXUT11UJ9rQ9K4kfJeZfyrcz9D4170X8n9');";

            PreparedStatement SQLStatement = conn.prepareStatement(SQL);
            SQLStatement.execute();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed inserting data into database!");
            e.printStackTrace();
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
