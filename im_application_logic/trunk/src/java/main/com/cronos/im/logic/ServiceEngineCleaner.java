/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class provides command line interface to cleanup the tables used by the Service Engine component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ServiceEngineCleaner {
    /**
     * <p>
     * Represents the default namespace used to load the configuration.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.im.logic.ServiceEngineCleaner";

    /**
     * <p>
     * Represents the property key for the namespace to load the db connection factory.
     * </p>
     */
    public static final String DB_FACTORY_NAME = "db_factory_name";

    /**
     * <p>
     * Represents the property key for the connection name to get the database connection.
     * </p>
     */
    public static final String CONNECTION_NAME = "connection_name";

    /**
     * <p>
     * Represents the property key for the logger name.
     * </p>
     */
    public static final String LOG_NAME = "log_name";

    /**
     * <p>
     * The SQL statement to get the requesters whose session id is of CLOSE status.
     * </p>
     */
    private static final String GET_REQUESTER_SQL = "select requester_id from requester where exists " +
        "(select 1 from session_status_history where requester.session_id = session_status_history.session_id " +
        "and session_status_id = 204 and end_date is null)";

    /**
     * <p>
     * The SQL statement to remove rows from locked_category table.
     * </p>
     */
    private static final String REMOVE_LOCKED_CATEGORY_SQL = "delete from locked_category where requester_id in (?)";

    /**
     * <p>
     * The SQL statement to remove rows from requester table.
     * </p>
     */
    private static final String REMOVE_REQUESTER_SQL = "delete from requester where requester_id in (?)";

    /**
     * <p>
     * Private constructor to prevent direct instantiation.
     * </p>
     */
    private ServiceEngineCleaner() {
    }

    /**
     * <p>
     * This is the main entry of command line interface of ServiceEngineCleaner.
     * </p>
     */
    public static void main(String[] args) throws Exception {
        cleanup();
    }

    /**
     * <p>
     * Cleanup the requester table. The rows whose session id is in the CLOSE status will be removed. If the
     * requester is locked, the row will also be removed from the locked_category table. Note that the requester_queue
     * table is assumed to be cleaned up by the message pool detector.
     * </p>
     */
    private static void cleanup() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Log logger = null;
        int count = 0;

        try {
            // load the config file
            String dbFactoryName = IMHelper.getRequiredConfigString(DEFAULT_NAMESPACE, DB_FACTORY_NAME);
            String connectionName = IMHelper.getRequiredConfigString(DEFAULT_NAMESPACE, CONNECTION_NAME);
            String logName = IMHelper.getRequiredConfigString(DEFAULT_NAMESPACE, LOG_NAME);

            // prepare the logger
            logger = LogFactory.getLog(logName);

            // determine the rows to be removed
            DBConnectionFactory factory = new DBConnectionFactoryImpl(dbFactoryName);

            connection = factory.createConnection(connectionName);
            pstmt = connection.prepareStatement(GET_REQUESTER_SQL);
            rs = pstmt.executeQuery();

            StringBuffer sb = new StringBuffer();

            while (rs.next()) {
                sb.append(rs.getLong(1));
                sb.append(", ");
            }

            rs.close();
            pstmt.close();
            if (sb.length() == 0) {
                return;
            }
            sb.setLength(sb.length() - 2);

            // remove the rows from the locked_category table
            pstmt = connection.prepareStatement(REMOVE_LOCKED_CATEGORY_SQL.replaceAll("\\?", sb.toString()));
            count = pstmt.executeUpdate();
            pstmt.close();

            logger.log(Level.INFO, "Remove [" + count + "] rows from the locked_category table.");

            // remove the rows from the requester table
            pstmt = connection.prepareStatement(REMOVE_REQUESTER_SQL.replaceAll("\\?", sb.toString()));
            count = pstmt.executeUpdate();
            pstmt.close();

            logger.log(Level.INFO, "Remove [" + count + "] rows from the requester table.");
        } catch (Exception e) {
            e.printStackTrace();

            if (logger != null) {
                logger.log(Level.ERROR, e.toString());
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // ignore error
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // ignore error
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // ignore error
                }
            }
        }
    }
}
