/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.stresstests;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * A helper to manipulate the database.
 *
 * @author slion
 * @version 1.0
  */
public final class DBHelper {
    /**
     * <p>
     * Represents the file contains prepare sql statements.
     * </p>
     */
    private static final String INSERT_FILE = "test_files/stress/prepare.sql";

    /**
     * <p>
     * Represents the file contains clear sql statements.
     * </p>
     */
    private static final String REMOVE_FILE = "test_files/stress/clear.sql";

    /**
     * <p>
     * Represents the DBConnectionFactory instance used in test.
     * </p>
     */
    private static DBConnectionFactory factory;

    /**
     * Invisible Constructor.
     */
    private DBHelper() {
    }

    /**
     * Get the available connection from configuration.
     * @param ns the namespace used the factory.
     * @return the available connection.
     * @throws Exception
     *             to JUnit
     */
    public static Connection getConnection(String ns) throws Exception {
        if (factory == null) {
            factory = new DBConnectionFactoryImpl(ns);
        }
        return factory.createConnection();
    }

    /**
     * Close the connection.
     * @param con
     *            the connection to close.
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Close the statement.
     * @param stmt
     *            the statement to close.
     */
    private static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Close a result set.
     * @param rs
     *            the result set to close
     */
    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Get the sql statements from file.
     * @param file the file contains sql statements.
     * @return the sql statements.
     * @throws Exception to JUnit.
     */
    private static List getSqlsFromFile(String file) throws Exception {
        InputStream in = new FileInputStream(file);
        byte[] buf = new byte[1024];
        int len = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
        in.close();
        String content = out.toString();
        out.close();
        List sqls = new ArrayList();
        int lastIndex = 0;

        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }
        return sqls;
    }

    /**
     * Run the sql statements in a file before testing.
     * @param file
     *            the sql file.
     * @param conn
     *            the connection.
     * @throws Exception
     *             to JUnit
     */
    private static void runSqls(List sqls, Connection conn) throws Exception {
        for (Iterator it = sqls.iterator(); it.hasNext();) {
            String sql = (String) it.next();
            Statement st = null;
            try {
                st = conn.createStatement();
                st.executeUpdate(sql);
            } catch (SQLException e) {
                throw new Exception("Error when executing sql.", e);
            } finally {
                // close the resources
                closeStatement(st);
            }
        }
    }

    /**
     * Clear the tasks.
     *
     * @param conn
     *            the connection.
     * @throws Exception
     *             to JUnit
     */
    public static void clearTasks(Connection conn) throws Exception {
            Statement st = null;
            try {
                st = conn.createStatement();
                st.executeUpdate("DELETE screening_task");
            } catch (SQLException e) {
                throw new Exception("Error when executing sql.", e);
            } finally {
                // close the resources
                closeStatement(st);
            }
    }

    /**
     * Insert some data before testing.
     * @throws Exception to JUnit.
     */
    public static void initData(Connection conn) throws Exception {
        runSqls(getSqlsFromFile(INSERT_FILE), conn);
    }

    /**
     * Remove the inserted data after testing.
     * @throws Exception to JUnit.
     */
    public static void removeData(Connection conn) throws Exception {
        runSqls(getSqlsFromFile(REMOVE_FILE), conn);
    }

    /**
     * Insert some records for testing.
     * @param conn the connection.
     * @param id the id of task.
     */
    public static void insertScreeningTask(Connection conn, int id) throws Exception {
		clearTasks(conn);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO screening_task "
                + "(screening_task_id, upload_id, screening_status_id, screener_id, start_timestamp, create_user, "
                + "create_date, modify_user, modify_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setInt(1, id);
            ps.setInt(2, 1);
            ps.setInt(3, 2);
            ps.setInt(4, 1);
            ps.setTimestamp(5, null);
            ps.setString(6, "System");
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            ps.setString(8, "User");
            ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
    }

    /**
     * Insert a pending screen task for testing.
     * @param conn the connection.
     * @param id the id of task.
     */
    public static void insertPendingTask(Connection conn, int id) throws Exception {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO screening_task "
                + "(screening_task_id, upload_id, screening_status_id, screener_id, start_timestamp, create_user, "
                + "create_date, modify_user, modify_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

        ps.setInt(1, id);
        ps.setInt(2, 1);
        ps.setInt(3, 1);
        ps.setInt(4, 1);
        ps.setTimestamp(5, null);
        ps.setString(6, "System");
        ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
        ps.setString(8, "User");
        ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
        ps.executeUpdate();
    }
}
