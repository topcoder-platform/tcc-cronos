/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.accuracytests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * A helper class for accuracy tests.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
final class AccuracyTestHelper {
    /**
     * Removes all namespaces from Config Manager.
     * @throws Exception to JUnit.
     */
    static void clearConfiguration() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Closes the sql statement.
     * </p>
     * @param statement the statement to be closed.
     */
    private static void close(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Closes the sql connection.
     * </p>
     * @param connection the connection to be closed.
     */
    private static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Executes the given sql string.
     * </p>
     * @param conn the database connection
     * @param sql the sql string to execute
     * @param args the sql argument objects
     * @throws SQLException when error occurs during execution.
     */
    private static void execute(Connection conn, String sql, Object[] args) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);

            for (int i = 0; i < args.length;) {
                Object obj = args[i++];

                if (obj instanceof Long) {
                    stmt.setLong(i, ((Long) obj).longValue());
                } else if (obj instanceof Double) {
                    stmt.setDouble(i, ((Double) obj).doubleValue());
                } else if (obj instanceof Boolean) {
                    stmt.setBoolean(i, ((Boolean) obj).booleanValue());
                } else if (obj instanceof String) {
                    stmt.setString(i, (String) obj);
                }
            }

            stmt.executeUpdate();
        } finally {
            close(stmt);
        }
    }

    /**
     * <p>
     * Clear all the data in database.
     * </p>
     * @throws Exception if fails
     */
    static void clearTables() throws Exception {
        Connection conn = null;
        Object[] args = new Object[0];
        try {
            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
            execute(conn, "DELETE FROM resource_submission", args);
            execute(conn, "DELETE FROM resource_info", args);
            execute(conn, "DELETE FROM resource", args);
            execute(conn, "DELETE FROM resource_role_lu", args);
            execute(conn, "DELETE FROM notification", args);
            execute(conn, "DELETE FROM notification_type_lu", args);
            execute(conn, "DELETE FROM resource_info_type_lu", args);
            execute(conn, "DELETE FROM project_phase", args);
            execute(conn, "DELETE FROM phase_type_lu", args);
            execute(conn, "DELETE FROM project", args);
            execute(conn, "DELETE FROM submission", args);
        } finally {
            close(conn);
        }
    }

    /**
     * <p>
     * Initializes test data in the database tables.
     * </p>
     * @throws Exception if fails
     */
    static void initTables() throws Exception {
        clearTables();

        Connection conn = null;
        PreparedStatement sqlState = null;

        try {
            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
            // insert data
            for (int i = 1; i <= 3; i++) {
                Long id = new Long(i);
                execute(conn, "INSERT INTO submission (submission_id) VALUES (?)", new Object[] {id});
                execute(conn, "INSERT INTO project (project_id) VALUES (?)", new Object[] {id});
                execute(conn, "INSERT INTO phase_type_lu (phase_type_id) VALUES (?)", new Object[] {new Long(
                    i)});
                execute(conn, "INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (?, ?, ?)",
                    new Object[] {id, id, id});
                execute(conn, "INSERT INTO resource_info_type_lu (resource_info_type_id, name, description, "
                    + "create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, "
                    + "'System', CURRENT, 'System', CURRENT)", new Object[] {id, "name" + i,
                        "description" + i});
            }
        } finally {
            close(conn);
        }
    }
}