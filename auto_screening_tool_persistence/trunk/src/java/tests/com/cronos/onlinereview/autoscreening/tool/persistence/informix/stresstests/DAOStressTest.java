/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.cronos.onlinereview.autoscreening.tool.persistence.informix.stresstests;
import com.cronos.onlinereview.autoscreening.tool.ScreeningData;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponse;
import com.cronos.onlinereview.autoscreening.tool.ScreeningStatus;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.persistence.informix.InformixResponseDAO;
import com.cronos.onlinereview.autoscreening.tool.persistence.informix.InformixTaskDAO;
import com.cronos.onlinereview.external.UserRetrieval;
import com.cronos.onlinereview.external.impl.DBUserRetrieval;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Stress Tests for InformixTaskDAO and InformixResponseDAO class.
 *
 * @author qiucx0161
 * @version 1.0
 */
public class DAOStressTest extends TestCase {
    /** Db Connection Factory namespace. */
    private static final String DB_CONN_FACTORY_NAMESPACE = DBConnectionFactoryImpl.class.getName();

    /** Db Connection Factory config file. */
    private static final String DB_CONN_FACTORY_CONF = "test_files/stress/stress.xml";

    /** The table names. */
    private static final String[] tableNames = new String[] {
            "screening_result", "screening_response_lu", "response_severity_lu", "screening_task",
            "screening_status_lu", "upload", "resource_info", "resource", "resource_info_type_lu",
            "project_info", "project", "project_category_lu", "user", "email", "id_sequences"
        };

    /** The InformixResponseDAO used to manipulate on database. */
    private InformixResponseDAO respDAO = null;

    /** The InformixTaskDAO used to manipulate on database. */
    private InformixTaskDAO taskDAO = null;

    /** DBConnectionFactory instance used for test. */
    private DBConnectionFactory fac = null;

    /** UserRetrieval instance used for test. */
    private UserRetrieval userRetrieval = null;

    /** IDGenerator instance used for test. */
    private IDGenerator idGen = null;

    /** Database connection used by the manager. */
    private Connection connection = null;

    /** The test number. */
    private int NUM = 10;

    /** the running start. */
    private long start;

    /** the running stop. */
    private long stop;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        clearConfigManager();
        ConfigManager.getInstance().add(new File(DB_CONN_FACTORY_CONF).getAbsolutePath());
        clearTables();
        executeSQLFile(new File("test_files/stress/data.sql").getAbsolutePath());
        
        fac = new DBConnectionFactoryImpl(DB_CONN_FACTORY_NAMESPACE);

        userRetrieval = new DBUserRetrieval(DB_CONN_FACTORY_NAMESPACE);
        idGen = IDGeneratorFactory.getIDGenerator("screening_result_id_seq");
        respDAO = new InformixResponseDAO(fac, idGen);
        taskDAO = new InformixTaskDAO(fac, userRetrieval);

        clearTables();
    }

    /**
     * Clears after test.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        clearTables();
        clearConfigManager();
    }

    /**
     * Check the result of createScreeningResponse test.
     *
     * @param sr ScreeningResponse instance.
     *
     * @throws Exception to JUnit.
     */
    private void checkResult(ScreeningResponse sr) throws Exception {
        String sql = "select * from screening_result where screening_result_id=" + sr.getId();

        String message = "Creating ScreeningResponse fails";

        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                assertEquals(message, sr.getScreeningTaskId(), rs.getLong("screening_task_id"));
                assertEquals(message, sr.getResponseCodeId(), rs.getLong("screening_response_id"));
                assertEquals(message, sr.getDetailMessage(), rs.getString("dynamic_response_text"));

                assertEquals(message, sr.getCreateUser(), rs.getString("create_user"));
                assertEquals(message, sr.getCreateDate(),
                    new Date(rs.getTimestamp("create_date").getTime()));

                assertEquals(message, sr.getModificationUser(), rs.getString("modify_user"));
                assertEquals(message, sr.getModificationDate(),
                    new Date(rs.getTimestamp("modify_date").getTime()));
            }
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
    }

    /**
     * Stress test for loadScreeningTasks.
     *
     * @throws Exception to JUnit.
     */
    public void testLoadScreeningTasks() throws Exception {
        executeSQLFile(new File("test_files/stress/data.sql").getAbsolutePath());

        ScreeningTask[] tasks = null;

        start = System.currentTimeMillis();

        for (int i = 0; i < NUM; ++i) {
            tasks = taskDAO.loadScreeningTasks(new Long(81), ScreeningStatus.PASSED);
        }

        System.out.println("loading " + NUM + "ScreeningTasks cost :" +
            (System.currentTimeMillis() - start));

        String message = "loadScreeningTasks fails";
        ScreeningTask st = tasks[0];
        assertEquals(message, 1, tasks.length);
        assertEquals(message, 100075, st.getId());
        assertEquals(message, 81, st.getScreenerId());
        assertEquals(message, "jivern", st.getCreationUser());
        assertEquals(message, "screening_task_modify_user", st.getModificationUser());
    }

    /**
     * Stress test for updateScreeningTask.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateScreeningTask() throws Exception {
        executeSQLFile(new File("test_files/stress/data.sql").getAbsolutePath());

        ScreeningTask st = new ScreeningTask();
        st.setScreeningStatus(ScreeningStatus.PASSED);
        st.setId(100073);
        st.setCreationDate(new Date());
        st.setCreationUser("ivern");
        st.setModificationDate(new Date());
        st.setModificationUser("tc");
        st.setScreenerId(81);
        st.setScreeningData(new ScreeningData());
        st.setStartTimestamp(new Date());
        st.getScreeningData().setUploadId(51);

        start = System.currentTimeMillis();
        taskDAO.updateScreeningTask(st);

        System.out.println("updating ScreeningTask cost :" + (System.currentTimeMillis() - start));

        String sql = "select * from screening_task where screening_task_id=999990";

        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String message = "updateScreeningTask fails";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                assertEquals(message, st.getScreeningStatus().getId(),
                    rs.getLong("screening_status_id"));

                long screenerId = rs.getLong("screener_id");

                if (rs.wasNull()) {
                    screenerId = Long.MIN_VALUE;
                }

                assertEquals(message, st.getScreenerId(), screenerId);

                Timestamp startTimestamp = rs.getTimestamp("start_timestamp");
                assertEquals(message, st.getStartTimestamp(),
                    (startTimestamp == null) ? null : new Date(startTimestamp.getTime()));

                assertEquals(message, st.getCreationUser(), rs.getString("create_user"));
                assertEquals(message, st.getModificationUser(), rs.getString("modify_user"));
                assertEquals(message, st.getCreationDate(),
                    new Date(rs.getTimestamp("create_date").getTime()));
                assertEquals(message, st.getModificationDate(),
                    new Date(rs.getTimestamp("modify_date").getTime()));
            }
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
    }

    /**
     * Load a SQL file and run each line as a statement
     *
     * @param filename file where the statements reside
     *
     * @throws Exception to JUnit.
     */
    private static void executeSQLFile(String filename)
        throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        Connection conn = getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            String str;

            while ((str = in.readLine()) != null) {
                stmt.execute(str);
            }
        } finally {
            conn.close();
            in.close();
        }
    }

    /**
     * Clear the used tables.
     *
     * @throws Exception if error occurs.
     */
    private void clearTables() throws Exception {
        Connection conn = getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            for (int i = 0; i < tableNames.length; i++) {
                stmt.executeUpdate("DELETE FROM " + tableNames[i]);
            }
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
        }
    }

    /**
     * get a connection
     *
     * @return a connection
     *
     * @throws Exception to JUnit.
     */
    public static Connection getConnection() throws Exception {
        return new DBConnectionFactoryImpl(DB_CONN_FACTORY_NAMESPACE).createConnection();
    }

    /**
     * Removes all namespaces from Config Manager.
     *
     * @throws Exception to JUnit.
     */
    private void clearConfigManager() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String ns = (String) it.next();
            cm.removeNamespace(ns);
        }
    }

    /**
     * Closes the Connection.
     *
     * @param con the connection to close.
     */
    private void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes Statement.
     *
     * @param stmt the statement to close.
     */
    private void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Close ResultSet.
     *
     * @param rs the result set to close
     */
    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
