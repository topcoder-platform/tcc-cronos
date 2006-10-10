/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.persistence.informix.accuracytests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.cronos.onlinereview.autoscreening.tool.ScreeningStatus;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.persistence.informix.InformixTaskDAO;
import com.cronos.onlinereview.external.UserRetrieval;
import com.cronos.onlinereview.external.impl.DBUserRetrieval;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>InformixTaskDAO</code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestInformixTaskDAOAccuracy extends TestCase {

    /**
     * Represents the InformixTaskDAO instance for test.
     */
    private InformixTaskDAO dao = null;

    /**
     * Represents the UserRetrieval instance for test.
     */
    private UserRetrieval userRetrieval;

    /**
     * Represents the DBConnectionFactory instance for test.
     */
    private DBConnectionFactory factory = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        DBUtil.clearConfigManager();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add("accuracytests/DBConnectionFactory.xml");
        
        DBUtil.clearTables();
        DBUtil.prepareDate();

        userRetrieval = new DBUserRetrieval(DBUtil.DB_FACTORY_NAMESPACE);
        factory = new DBConnectionFactoryImpl(DBUtil.DB_FACTORY_NAMESPACE);

        dao = new InformixTaskDAO(factory, userRetrieval);
    }

    /**
     * Clear all table and namespaces in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        DBUtil.clearTables();

        DBUtil.clearConfigManager();
    }

    /**
     * Test constructor
     * <code>InformixTaskDAO(DBConnectionFactory connectionFactory, UserRetrieval userRetrieval)>/code>.
     *
     */
    public void testInformixTaskDAODBConnectionFactoryUserRetrieval() {
        assertNotNull("The InformixTaskDAO instance should be created.", dao);
    }

    /**
     * Test constructor <code>nformixTaskDAO(DBConnectionFactory connectionFactory, String connectionName,
     *  UserRetrieval userRetrieval) </code>.
     */
    public void testInformixTaskDAODBConnectionFactoryStringUserRetrieval_1() {
        dao = new InformixTaskDAO(factory, null, userRetrieval);
        assertNotNull("The InformixTaskDAO instance should be created.", dao);
    }

    /**
     * Test constructor <code>nformixTaskDAO(DBConnectionFactory connectionFactory, String connectionName,
     *  UserRetrieval userRetrieval) </code>.
     */
    public void testInformixTaskDAODBConnectionFactoryStringUserRetrieval_2() {
        dao = new InformixTaskDAO(factory, "informix_connection", userRetrieval);
        assertNotNull("The InformixTaskDAO instance should be created.", dao);
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is null, and status is Pending.
     * </p>
     *
     * <p>
     * For status Pending, there is 1 record with taskid =1.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_1() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(null, ScreeningStatus.PENDING);

        assertEquals("There should be 1 task returned.", 1, tasks.length);
        assertEquals("The first task taskId should be 1.", 1, tasks[0].getId());
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is null, and status is Screening.
     * </p>
     *
     * <p>
     * For status Screening, there are 3 records. with taskId 2, 6, 7.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_2() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(null, ScreeningStatus.SCREENING);

        assertEquals("There should be 3 tasks returned.", 3, tasks.length);
        Set set = new HashSet();
        for (int i = 0; i < tasks.length; i++) {
            set.add(new Long(tasks[i].getId()));
        }

        assertEquals("The size should be 3.", 3, set.size());
        assertTrue("True is expected.", set.contains(new Long(2)));
        assertTrue("True is expected.", set.contains(new Long(6)));
        assertTrue("True is expected.", set.contains(new Long(7)));
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is null, and status is Failed.
     * </p>
     *
     * <p>
     * For status Failed, there is only one records with task id = 3.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_3() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(null, ScreeningStatus.FAILED);
        assertEquals("There should be 1 tasks returned.", 1, tasks.length);
        assertEquals("The first task task id should be 3.", 3, tasks[0].getId());
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is null, and status is Passed.
     * </p>
     *
     * <p>
     * For status Passed, there is one task with taskId = 4.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_4() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(null, ScreeningStatus.PASSED);
        assertEquals("There should be 1 tasks returned.", 1, tasks.length);
        assertEquals("The first task task id should be 4.", 4, tasks[0].getId());
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is null, and status is PASSED_WITH_WARNING.
     * </p>
     *
     * <p>
     * For status PASSED_WITH_WARNING, there is one record with taskId = 5;
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_5() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(null, ScreeningStatus.PASSED_WITH_WARNING);
        assertEquals("There should be 1 tasks returned.", 1, tasks.length);
        assertEquals("The first task task id should be 5.", 5, tasks[0].getId());
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is null, and status is null. All tasks should be returned which is 7.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_6() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(null, null);

        assertEquals("There should be 7 tasks returned.", 7, tasks.length);
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is 1, and status is null.
     * </p>
     *
     * <p>
     * 2 tasks will be returned with taskId 4 and 6.
     * </P>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_7() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(new Long(1), null);

        assertEquals("There should be 2 tasks returned.", 2, tasks.length);
        Set set = new HashSet();
        for (int i = 0; i < tasks.length; i++) {
            set.add(new Long(tasks[i].getId()));
        }
        assertEquals("The size should be 2.", 2, set.size());
        assertTrue("True is expected.", set.contains(new Long(4)));
        assertTrue("True is expected.", set.contains(new Long(6)));
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is 2, and status is null.
     * </p>
     *
     * <p>
     * 2 tasks will be returned with taskId 5 and 7.
     * </P>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_8() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(new Long(2), null);

        assertEquals("There should be 2 tasks returned.", 2, tasks.length);
        Set set = new HashSet();
        for (int i = 0; i < tasks.length; i++) {
            set.add(new Long(tasks[i].getId()));
        }
        assertEquals("The size should be 2.", 2, set.size());
        assertTrue("True is expected.", set.contains(new Long(5)));
        assertTrue("True is expected.", set.contains(new Long(7)));
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is 3, and status is null.
     * </p>
     *
     * <p>
     * No tasks will be returned.
     * </P>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_9() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(new Long(3), null);

        assertEquals("There should be 0 tasks returned.", 0, tasks.length);
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is 1, and status is Pending.
     * </p>
     *
     * <p>
     * No tasks will be returned.
     * </P>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_10() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(new Long(1), ScreeningStatus.PENDING);

        assertEquals("There should be 0 tasks returned.", 0, tasks.length);
    }

    /**
     * Test method <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status) </code>.
     *
     * <p>
     * In this test case, screenerId is 1, and status is Pass.
     * </p>
     *
     * <p>
     * 1 tasks will be returned with taskId = 4
     * </P>
     *
     * @throws Exception
     *             to junit.
     */
    public void testLoadScreeningTasks_11() throws Exception {
        ScreeningTask[] tasks = dao.loadScreeningTasks(new Long(1), ScreeningStatus.PASSED);

        assertEquals("There should be 1 tasks returned.", 1, tasks.length);
        assertEquals("The taskId should be 4.", 4, tasks[0].getId());
    }

    /**
     * Test method <code>void updateScreeningTask(ScreeningTask screeningTask) </code>.
     *
     * <p>
     * Update status from screening to passed.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateScreeningTask_1() throws Exception {
        ScreeningTask task = new ScreeningTask();

        task.setCreationDate(new Date());
        task.setCreationUser("topcoder");
        task.setId(6);
        task.setStartTimestamp(new Date());
        task.setScreeningStatus(ScreeningStatus.PASSED);
        task.setModificationDate(new Date());
        task.setModificationUser("topcoder");

        dao.updateScreeningTask(task);

        ResultSet rs = null;
        Connection connection = null;
        Statement st = null;

        try {

            connection = factory.createConnection();
            st = connection.createStatement();
            rs = st.executeQuery("select * from screening_task where screening_task_id = 6");
            int index = 0;
            while (rs.next()) {
                assertEquals("The status should be 4 now.", "4", rs.getString(3).trim());
                index++;
            }

            assertEquals("should be only one record created.", 1, index);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(st);
        }
    }

    /**
     * Test method <code>void updateScreeningTask(ScreeningTask screeningTask) </code>.
     *
     * <p>
     * Update status from Pending to Screening.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateScreeningTask_2() throws Exception {
        ScreeningTask task = new ScreeningTask();

        task.setCreationDate(new Date());
        task.setCreationUser("topcoder");
        task.setId(1);
        task.setStartTimestamp(new Date());
        task.setScreeningStatus(ScreeningStatus.SCREENING);
        task.setModificationDate(new Date());
        task.setModificationUser("topcoder");

        dao.updateScreeningTask(task);

        ResultSet rs = null;
        Connection connection = null;
        Statement st = null;

        try {

            connection = factory.createConnection();
            st = connection.createStatement();
            rs = st.executeQuery("select * from screening_task where screening_task_id = 1");
            int index = 0;
            while (rs.next()) {
                assertEquals("The status should be 2 now.", "2", rs.getString(3).trim());
                index++;
            }

            assertEquals("should be only one record created.", 1, index);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(st);
        }
    }
}