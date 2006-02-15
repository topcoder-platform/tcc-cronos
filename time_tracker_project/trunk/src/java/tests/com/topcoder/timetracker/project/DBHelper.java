/*
 * DBHelper.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A helper class to perform those database operations which are not available from the
 * InformixTimeTrackerProjectPersistence class. These include checking whether a table is empty, clearing the tables,
 * etc.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DBHelper {
    /**
     * Private constructor to prevent instantiation.
     */
    private DBHelper() {
    }

    /**
     * Gets the connection to perform database operations. This method assumes that the namespace of the db connection
     * factory has been loaded.
     *
     * @return the database connection
     *
     * @throws Exception if any unexpected exception occurs.
     */
    private static Connection getConnection() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(TestHelper.DB_NAMESPACE);

        return factory.createConnection(TestHelper.CONNECTION_PRODUCER_NAME);
    }

    /**
     * Checks whether the table of the given name is empty. An empty table means it has no rows.
     *
     * @param tableName the name of the table
     *
     * @return whether the table of the given name is empty
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public static boolean isEmptyTable(String tableName)
        throws Exception
    {
        String sql = "SELECT * FROM " + tableName;

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        boolean result = !rs.next();

        rs.close();
        pstmt.close();
        conn.close();

        return result;
    }

    /**
     * Clears all the tables in the database.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public static void clearTables() throws Exception {
        String[] tableNames = {
            "ProjectTimes", "ProjectExpenses", "ProjectManagers", "ProjectWorkers", "ClientProjects", "Projects",
            "Clients"
        };

        Connection conn = getConnection();

        for (int i = 0; i < tableNames.length; i++) {
            String sql = "DELETE FROM " + tableNames[i];
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();
            pstmt.close();
        }

        conn.close();
    }
}
