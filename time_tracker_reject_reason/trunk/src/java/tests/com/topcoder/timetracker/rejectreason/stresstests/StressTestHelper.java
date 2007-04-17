/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.stresstests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This is a help class which is used in stress tests.
 * </p>
 *
 * @author restarter
 * @version 3.2
 */
class StressTestHelper {

    /**
     * <p>
     * The times to run the methods.
     * </p>
     */
    static final int TIMES = 100;

    /**
     * <p>
     * Load the namespaces from the config files.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    static void loadNamespaces() throws Exception {
        clearNamespaces();
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("stresstests/config.xml");
        configManager.add("stresstests/dbconnectionfactory.xml");
        configManager.add("stresstests/searchbundle.xml");
        configManager.add("stresstests/searchstrategy.xml");
    }

    /**
     * <p>
     * Clean the database.
     * </p>
     *
     * @throws Exception to JUnit
     */
    static void cleanDatabase() throws Exception {
        Connection connection =
            new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from comp_rej_email");
        statement.executeUpdate("delete from reject_email");
        statement.executeUpdate("delete from comp_rej_reason");
        statement.executeUpdate("delete from reject_reason");
        statement.close();
        connection.close();
    }

    /**
     * <p>
     * Remove the namespaces.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    static void clearNamespaces() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Retrieve all ids in the table.
     * </p>
     *
     * @param string the table name
     * @return all the ids in the database.
     * @throws Exception to JUnit
     */
    static long[] retrieveIds(String string) throws Exception {
        Connection connection =
            new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
        Statement statement = connection.createStatement();
        ResultSet rs;
        if ("reject_email".equals(string)) {
            rs =
                statement
                    .executeQuery("select reject_email_id from reject_email order by reject_email_id asc");
        } else {
            rs =
                statement
                    .executeQuery("select reject_reason_id from reject_reason order by reject_reason_id asc");
        }
        List ids = new ArrayList();
        while (rs.next()) {
            ids.add(String.valueOf(rs.getLong(1)));
        }
        long[] idsLong = new long[ids.size()];
        for (int i = 0; i < idsLong.length; i++) {
            idsLong[i] = Long.parseLong((String) ids.get(i));
        }
        statement.close();
        connection.close();
        return idsLong;
    }
}
