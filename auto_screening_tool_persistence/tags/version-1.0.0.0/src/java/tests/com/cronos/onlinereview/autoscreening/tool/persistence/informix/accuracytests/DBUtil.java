/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.persistence.informix.accuracytests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * A helper class for database testing.
 *
 * @author Chenhong
 * @version 1.0
 */
public final class DBUtil {
    /**
     * <p>
     * Represents the namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * The table names in this component.
     */
    private static String[] tableNames = { "screening_result", "screening_response_lu", "response_severity_lu",
            "screening_task", "screening_status_lu", "upload", "resource_info", "resource_info_type_lu", "resource",
            "project", "project_category_lu", "id_sequences", "email", "user_rating", "user_reliability", "user" };

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private static DBConnectionFactory factory;

    /**
     * Private constructor.
     *
     */
    private DBUtil() {
        // empty.
    }

    /**
     * Cleart the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public static void clearConfigManager() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Deletes data from the table used by this component.
     * </p>
     *
     * @param table
     *            the table to be cleared.
     * @throws Exception
     *             to junit.
     */
    public static void clearTables() throws Exception {
        Connection connection = getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            for (int i = 0; i < tableNames.length; i++) {
                statement.executeUpdate("DELETE FROM " + tableNames[i]);
            }
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * Prepare the test data.
     *
     * @throws Exception
     *             to junit.
     */
    static void prepareDate() throws Exception {
        Connection connection = getConnection();
        Statement st = connection.createStatement();
        // insert 2 resource_info_type into resource_info_type_lu table.
        try {
            st.execute("INSERT INTO resource_info_type_lu(" + "resource_info_type_id, name, description,"
                    + " create_user, create_date, modify_user, modify_date)"
                    + "VALUES(1, 'type1', 'External Reference ID', " + "'System', CURRENT, 'System', CURRENT)");

            st.execute("INSERT INTO resource_info_type_lu(" + "resource_info_type_id, name, description,"
                    + " create_user, create_date, modify_user, modify_date)"
                    + "VALUES(2, 'type2', 'External Reference ID', " + "'System', CURRENT, 'System', CURRENT)");

            st.execute("INSERT INTO id_sequences "
                    + "(name, next_block_start, block_size, exhausted) VALUES ('response_id_seq', 20, 20, 0)");

            // insert records into project_category_lu table.
            st.execute("INSERT INTO project_category_lu (project_category_id) VALUES (1)");
            st.execute("INSERT INTO project_category_lu (project_category_id) VALUES (2)");

            // insert 2 project into project table.
            st.execute("INSERT INTO project (project_id, project_category_id) VALUES (1, 1)");
            st.execute("INSERT INTO project (project_id, project_category_id) VALUES (2, 2)");

            // insert records into resource table.
            st.execute("INSERT INTO resource (resource_id) VALUES (1)");
            st.execute("INSERT INTO resource (resource_id) VALUES (2)");
            st.execute("INSERT INTO resource (resource_id) VALUES (3)");

            // insert records into table resource_info.
            st.execute("INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(1, 1, '1')");
            st.execute("INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(2, 1, '10')");
            st.execute("INSERT INTO resource_info (resource_id, resource_info_type_id, value) VALUES(3, 2, '100')");

            // insert records into table upload.
            st.execute("INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(1, 1, 1, 'p1')");
            st.execute("INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(2, 2, 2, 'p2')");
            st.execute("INSERT INTO upload (upload_id, project_id, resource_id, parameter) VALUES(3, 2, 3, 'p3')");

            // insert records into table screening_status_lu.
            st.execute("INSERT INTO screening_status_lu"
                    + " (screening_status_id, name, description, create_user, create_date, modify_user, modify_date)"
                    + "VALUES(1, 'Pending', 'Pending', 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_status_lu"
                    + " (screening_status_id, name, description, create_user, create_date, modify_user, modify_date)"
                    + "VALUES(2, 'Screening', 'Screening', 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_status_lu"
                    + " (screening_status_id, name, description, create_user, create_date, modify_user, modify_date)"
                    + "VALUES(3, 'Failed', 'Failed', 'System', CURRENT, 'User', CURRENT)");
            st.execute("INSERT INTO screening_status_lu"
                    + " (screening_status_id, name, description, create_user, create_date, modify_user, modify_date)"
                    + "VALUES(4, 'Passed', 'Passed', 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_status_lu"
                    + " (screening_status_id, name, description, create_user, create_date, modify_user, modify_date)"
                    + "VALUES(5, 'Passed with Warning', 'Passed with Warning', 'System', CURRENT, 'User', CURRENT)");

            // insert records into table response_severity_lu.
            st.execute("INSERT INTO response_severity_lu ("
                    + "response_severity_id, name, description, create_user, create_date, modify_user, modify_date)"
                    + "VALUES(1, 's1', 'response_severity1', 'System', CURRENT, 'User', CURRENT);");

            st.execute("INSERT INTO response_severity_lu ("
                    + "response_severity_id, name, description, create_user, create_date, modify_user, modify_date)"
                    + "VALUES(2, 's2', 'response_severity2', 'System', CURRENT, 'User', CURRENT);");

            st.execute("INSERT INTO response_severity_lu ("
                    + "response_severity_id, name, description, create_user, create_date, modify_user, modify_date)"
                    + "VALUES(3, 's3', 'response_severity3', 'System', CURRENT, 'User', CURRENT);");

            // insert records into table screening_response_lu.
            st.execute("INSERT INTO screening_response_lu ("
                    + "screening_response_id, response_severity_id, response_code, response_text,"
                    + " create_user, create_date, modify_user, modify_date) "
                    + " VALUES(1, 1, 'response1', 'response_1', 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_response_lu ("
                    + "screening_response_id, response_severity_id, response_code, response_text,"
                    + " create_user, create_date, modify_user, modify_date) "
                    + " VALUES(2, 2, 'response2', 'response_2', 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_response_lu ("
                    + "screening_response_id, response_severity_id, response_code, response_text,"
                    + " create_user, create_date, modify_user, modify_date) "
                    + " VALUES(3, 3, 'response3', 'response_3', 'System', CURRENT, 'User', CURRENT)");

            // insert records into table screening task.
            st.execute("INSERT INTO screening_task ("
                    + "screening_task_id, upload_id, screening_status_id, create_user, "
                    + "create_date, modify_user, modify_date) VALUES(1, 1, 1, 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_task ("
                    + "screening_task_id, upload_id, screening_status_id, create_user, "
                    + "create_date, modify_user, modify_date) VALUES(2, 2, 2, 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_task ("
                    + "screening_task_id, upload_id, screening_status_id, create_user, "
                    + "create_date, modify_user, modify_date) VALUES(3, 1, 3, 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_task ("
                    + "screening_task_id, upload_id, screening_status_id, screener_id, create_user,"
                    + " create_date, modify_user, modify_date)"
                    + "VALUES(4, 2, 4, 1, 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_task ("
                    + "screening_task_id, upload_id, screening_status_id, screener_id, create_user,"
                    + " create_date, modify_user, modify_date)"
                    + "VALUES(5, 2, 5, 2, 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_task ("
                    + "screening_task_id, upload_id, screening_status_id, screener_id, create_user,"
                    + " create_date, modify_user, modify_date)"
                    + "VALUES(6, 1, 2, 1, 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO screening_task ("
                    + "screening_task_id, upload_id, screening_status_id, screener_id, create_user,"
                    + " create_date, modify_user, modify_date)"
                    + "VALUES(7, 2, 2, 2, 'System', CURRENT, 'User', CURRENT)");

            st.execute("INSERT INTO user (user_id, handle, first_name, last_name, status) "
                    + "VALUES (1, 'user1', 'top1', 'coder1', 'OK')");
            st.execute("INSERT INTO user (user_id, handle, first_name, last_name, status) "
                    + "VALUES (2, 'user2', 'top2', 'coder2', 'OK')");
            st.execute("INSERT INTO user (user_id, handle, first_name, last_name, status) "
                    + "VALUES (3, 'user3', 'top3', 'coder3', 'OK')");
            st.execute("INSERT INTO user (user_id, handle, first_name, last_name, status) "
                    + "VALUES (4, 'user4', 'top4', 'coder4', 'OK')");
            st.execute("INSERT INTO user (user_id, handle, first_name, last_name, status) "
                    + "VALUES (5, 'user5', 'top5', 'coder5', 'OK')");
            st.execute("INSERT INTO user (user_id, handle, first_name, last_name, status) "
                    + "VALUES (6, 'user6', 'top56', 'coder6', 'OK')");
            st.execute("INSERT INTO user (user_id, handle, first_name, last_name, status) "
                    + "VALUES (7, 'user7', 'top7', 'coder7', 'OK')");
            st.execute("INSERT INTO user (user_id, handle, first_name, last_name, status) "
                    + "VALUES (8, 'user8', 'top8', 'coder8', 'OK')");
            st.execute("INSERT INTO user (user_id, handle, first_name, last_name, status) "
                    + "VALUES (9, 'user9', 'top9', 'coder9', 'OK')");
            st.execute("INSERT INTO user (user_id, handle, first_name, last_name, status) "
                    + "VALUES (10, 'user10', 'top10', 'coder10', 'OK')");

            st.execute("INSERT INTO email (email_id, user_id, address, primary_ind)"
                    + " VALUES (1, 1, 'user1@topcoder.com', 1)");
            st.execute("INSERT INTO email (email_id, user_id, address, primary_ind)"
                    + " VALUES (2, 2, 'user2@topcoder.com', 1)");
            st.execute("INSERT INTO email (email_id, user_id, address, primary_ind)"
                    + " VALUES (3, 3, 'user3@topcoder.com', 1)");
            st.execute("INSERT INTO email (email_id, user_id, address, primary_ind)"
                    + " VALUES (4, 4, 'user4@topcoder.com', 1)");
            st.execute("INSERT INTO email (email_id, user_id, address, primary_ind)"
                    + " VALUES (5, 5, 'user5@topcoder.com', 1)");
            st.execute("INSERT INTO email (email_id, user_id, address, primary_ind)"
                    + " VALUES (6, 6, 'user6@topcoder.com', 1)");
            st.execute("INSERT INTO email (email_id, user_id, address, primary_ind)"
                    + " VALUES (7, 7, 'user7@topcoder.com', 1)");
            st.execute("INSERT INTO email (email_id, user_id, address, primary_ind)"
                    + " VALUES (8, 8, 'user8@topcoder.com', 1)");
            st.execute("INSERT INTO email (email_id, user_id, address, primary_ind)"
                    + " VALUES (9, 9, 'user9@topcoder.com', 1)");
            st.execute("INSERT INTO email (email_id, user_id, address, primary_ind)"
                    + " VALUES (10, 10, 'user10@topcoder.com', 1)");

        } finally {
            closeConnection(connection);
            closeStatement(st);
        }
    }

    /**
     * <p>
     * Returns a new connection to be used for persistence.
     * </p>
     *
     * @return the connection instance for database operation
     *
     * @throws Exception
     *             If unable to obtain a Connection
     */
    private static Connection getConnection() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
        cm.add("accuracytests/DBConnectionFactory.xml");
        factory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
        return factory.createConnection();
    }

    /**
     * <p>
     * Closes the given Connection instance.
     * </p>
     *
     * @param connection
     *            the given Connection instance to close.
     */
    static void closeConnection(Connection connection) {
        try {
            if ((connection != null) && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given PreparedStatement instance.
     * </p>
     *
     * @param statement
     *            the given Statement instance to close.
     */
    static void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given ResultSet.
     * </p>
     *
     * @param rs
     *            the given ResultSet instance to close.
     */
    static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // ignore.
        }
    }
}