/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.autoscreening.tool.persistence.informix;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.DAOException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningData;
import com.cronos.onlinereview.autoscreening.tool.ScreeningStatus;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTaskDAO;
import com.cronos.onlinereview.autoscreening.tool.UpdateFailedException;
import com.cronos.onlinereview.external.UserRetrieval;
import com.cronos.onlinereview.external.impl.DBUserRetrieval;

/**
 * Unit tests for <code>InformixTaskDAOTest</code>.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InformixTaskDAOTest extends DbTestCase {

    /**
     * Represents an instance of InformixTaskDAO, whose methods are going to be
     * tested.
     */
    private InformixTaskDAO instance;

    /**
     * Represents an instance of UserRetrieval, used to construct
     * InformixTaskDAO.
     */
    private UserRetrieval userRetrieval;

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(InformixTaskDAOTest.class);
    }

    /**
     * Sets up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        userRetrieval = new DBUserRetrieval(DB_NAMESPACE);
        instance = new InformixTaskDAO(getConnectionFactory(), userRetrieval);
    }

    /**
     * Clean up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>InformixTaskDAO(DBConnectionFactory connectionFactory,
     * UserRetrieval userRetrieval)</code>.
     * </p>
     * <p>
     * An instance of InformixTaskDAO should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorA1() throws Exception {
        ScreeningTaskDAO taskDAO = new InformixTaskDAO(getConnectionFactory(), userRetrieval);
        assertNotNull("check the instance", taskDAO);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixTaskDAO(DBConnectionFactory connectionFactory,
     * UserRetrieval userRetrieval)</code>.
     * </p>
     * <p>
     * connectionFactory is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA1() throws Exception {

        try {
            new InformixTaskDAO(null, userRetrieval);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "connectionFactory should not be null.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixTaskDAO(DBConnectionFactory connectionFactory,
     * UserRetrieval userRetrieval)</code>.
     * </p>
     * <p>
     * userRetrieval is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA2() throws Exception {

        try {
            new InformixTaskDAO(getConnectionFactory(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "userRetrieval should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>InformixTaskDAO(DBConnectionFactory connectionFactory, String connectionName,
     * UserRetrieval userRetrieval)</code>.
     * </p>
     * <p>
     * An instance of InformixTaskDAO should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorB1() throws Exception {
        ScreeningTaskDAO taskDAO = new InformixTaskDAO(getConnectionFactory(), CONNECTION_NAME,
            userRetrieval);
        assertNotNull("check the instance", taskDAO);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>InformixTaskDAO(DBConnectionFactory connectionFactory, String connectionName,
     * UserRetrieval userRetrieval)</code>.
     * </p>
     * <p>
     * An instance of InformixResponseDAO should be created successfully.
     * connectionName can be null.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorB2() throws Exception {
        ScreeningTaskDAO taskDAO = new InformixTaskDAO(getConnectionFactory(), null, userRetrieval);
        assertNotNull("check the instance", taskDAO);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixTaskDAO(DBConnectionFactory connectionFactory, String connectionName,
     * UserRetrieval userRetrieval)</code>.
     * </p>
     * <p>
     * connectionFactory is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB1() throws Exception {

        try {
            new InformixTaskDAO(null, CONNECTION_NAME, userRetrieval);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "connectionFactory should not be null.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixTaskDAO(DBConnectionFactory connectionFactory, String connectionName,
     * UserRetrieval userRetrieval)</code>.
     * </p>
     * <p>
     * connectionName is empty. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB2() throws Exception {

        try {
            new InformixTaskDAO(getConnectionFactory(), "   ", userRetrieval);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "connectionName should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixTaskDAO(DBConnectionFactory connectionFactory, String connectionName,
     UserRetrieval userRetrieval)</code>.
     * </p>
     * <p>
     * userRetrieval is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB3() throws Exception {

        try {
            new InformixTaskDAO(getConnectionFactory(), CONNECTION_NAME, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "userRetrieval should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * The tasks should be loaded successfully. screenerId is null. all the
     * screener ids are accepted. status is ScreeningStatus.PENDING.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadScreeningTasks1() throws Exception {
        ScreeningTask[] tasks = instance.loadScreeningTasks(null, ScreeningStatus.PENDING);
        assertEquals("check # of tasks", 4, tasks.length);
        sortScreeningTasksById(tasks);

        ScreeningTask task;
        int pos = 0;

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(1, Long.MIN_VALUE, "System", "User",
            ScreeningStatus.PENDING, createScreeningData(10, 1, 1, "fileId10", "dev1", "first1",
                "last10", "dev1@topcoder.com", new String[] {"alternative1_dev1@topcoder.com",
                    "alternative2_dev1@topcoder.com"})), task);

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(2, Long.MIN_VALUE, "System", "User",
            ScreeningStatus.PENDING, createScreeningData(9, 2, 2, "fileId9", "dev2", "first2",
                "last9", "dev2@topcoder.com", new String[] {"alternative1_dev2@topcoder.com",
                    "alternative2_dev2@topcoder.com"})), task);

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(3, Long.MIN_VALUE, "System", "User",
            ScreeningStatus.PENDING, createScreeningData(8, 3, 3, "fileId8", "dev3", "first3",
                "last8", "dev3@topcoder.com", new String[] {"alternative1_dev3@topcoder.com",
                    "alternative2_dev3@topcoder.com"})), task);

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(4, Long.MIN_VALUE, "System", "User",
            ScreeningStatus.PENDING, createScreeningData(7, 4, 1, "fileId7", "dev4", "first4",
                "last7", "dev4@topcoder.com", new String[] {"alternative1_dev4@topcoder.com",
                    "alternative2_dev4@topcoder.com"})), task);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * The tasks should be loaded successfully. screenerId is null. all the
     * screener ids are accepted. status is ScreeningStatus.SCREENING.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadScreeningTasks2() throws Exception {
        ScreeningTask[] tasks = instance.loadScreeningTasks(null, ScreeningStatus.SCREENING);
        assertEquals("check # of tasks", 2, tasks.length);
        sortScreeningTasksById(tasks);

        ScreeningTask task;
        int pos = 0;

        task = tasks[pos++];
        compareScreeningTask(
            createScreeningTask(5, 1, "System", "User", ScreeningStatus.SCREENING,
                createScreeningData(6, 5, 2, "fileId6", "dev5", "first5", "last6",
                    "dev5@topcoder.com", new String[] {"alternative1_dev5@topcoder.com",
                        "alternative2_dev5@topcoder.com"})), task);

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(6, 2, "System", "User", ScreeningStatus.SCREENING,
            createScreeningData(5, 1, 1, "fileId5", "dev6", "first6", "last5", "dev6@topcoder.com",
                new String[] {})), task);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * The tasks should be loaded successfully. screenerId is null. all the
     * screener ids are accepted. status is ScreeningStatus.FAILED.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadScreeningTasks3() throws Exception {
        ScreeningTask[] tasks = instance.loadScreeningTasks(null, ScreeningStatus.FAILED);
        assertEquals("check # of tasks", 1, tasks.length);
        sortScreeningTasksById(tasks);

        ScreeningTask task;
        int pos = 0;

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(7, 3, "System", "User", ScreeningStatus.FAILED,
            createScreeningData(4, 2, 2, "fileId4", "dev7", "first7", "last4", "dev7@topcoder.com",
                new String[] {})), task);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * The tasks should be loaded successfully. screenerId is null. all the
     * screener ids are accepted. status is ScreeningStatus.PASSED.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadScreeningTasks4() throws Exception {
        ScreeningTask[] tasks = instance.loadScreeningTasks(null, ScreeningStatus.PASSED);
        assertEquals("check # of tasks", 2, tasks.length);
        sortScreeningTasksById(tasks);

        ScreeningTask task;
        int pos = 0;

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(8, 4, "System", "User", ScreeningStatus.PASSED,
            createScreeningData(3, 3, 3, "fileId3", "dev8", "first8", "last3", "dev8@topcoder.com",
                new String[] {})), task);

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(10, 6, "System", "User", ScreeningStatus.PASSED,
            createScreeningData(1, 5, 2, "fileId1", "dev10", "first10", "last1",
                "dev10@topcoder.com", new String[] {})), task);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * The tasks should be loaded successfully. screenerId is null. all the
     * screener ids are accepted. status is ScreeningStatus.PASSED_WITH_WARNING.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadScreeningTasks5() throws Exception {
        ScreeningTask[] tasks = instance.loadScreeningTasks(null,
            ScreeningStatus.PASSED_WITH_WARNING);
        assertEquals("check # of tasks", 1, tasks.length);
        sortScreeningTasksById(tasks);

        ScreeningTask task;
        int pos = 0;

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(9, 5, "System", "User",
            ScreeningStatus.PASSED_WITH_WARNING, createScreeningData(2, 4, 1, "fileId2", "dev9",
                "first9", "last2", "dev9@topcoder.com", new String[] {})), task);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * The tasks should be loaded successfully. screenerId is null. all the
     * screener ids are accepted. status is null. all the status are accepted.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadScreeningTasks6() throws Exception {
        ScreeningTask[] tasks = instance.loadScreeningTasks(null, null);

        // here we just check # of tasks, since all of records are returned and
        // their content has been checked in previous test cases.
        assertEquals("check # of tasks", 10, tasks.length);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * The tasks should be loaded successfully. screenerId is 2. status is
     * ScreeningStatus.SCREENING
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadScreeningTasks7() throws Exception {
        ScreeningTask[] tasks = instance.loadScreeningTasks(new Long(2), ScreeningStatus.SCREENING);

        assertEquals("check # of tasks", 1, tasks.length);
        sortScreeningTasksById(tasks);

        ScreeningTask task;
        int pos = 0;

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(6, 2, "System", "User", ScreeningStatus.SCREENING,
            createScreeningData(5, 1, 1, "fileId5", "dev6", "first6", "last5", "dev6@topcoder.com",
                new String[] {})), task);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * The tasks should be loaded successfully. screenerId is 2. status is
     * ScreeningStatus.PASSED
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadScreeningTasks8() throws Exception {
        ScreeningTask[] tasks = instance.loadScreeningTasks(new Long(2), ScreeningStatus.PASSED);

        // no records are returned
        assertEquals("check # of tasks", 0, tasks.length);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * The tasks should be loaded successfully. screenerId is 2. status is null
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadScreeningTasks9() throws Exception {
        ScreeningTask[] tasks = instance.loadScreeningTasks(new Long(2), null);

        assertEquals("check # of tasks", 1, tasks.length);
        sortScreeningTasksById(tasks);

        ScreeningTask task;
        int pos = 0;

        task = tasks[pos++];
        compareScreeningTask(createScreeningTask(6, 2, "System", "User", ScreeningStatus.SCREENING,
            createScreeningData(5, 1, 1, "fileId5", "dev6", "first6", "last5", "dev6@topcoder.com",
                new String[] {})), task);
    }

    /**
     * <p>
     * Failure test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * screenerId is not null and <= 0. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadScreeningTasks1() throws Exception {
        try {
            instance.loadScreeningTasks(new Long(0), null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "screenerId should be > 0, if it's not null.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * email table is deleted. unable to get user information. DAOException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadScreeningTasks2() throws Exception {
        doSQLUpdate("DELETE email", new Object[] {});

        try {
            instance.loadScreeningTasks(null, null);
            fail("DAOException should be thrown");
        } catch (DAOException e) {
            assertEquals("check error message",
                "There is no personal information for user id [1].", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * file identifier data from database are all empty. unable to set them to
     * screening task. DAOException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadScreeningTasks3() throws Exception {
        doSQLUpdate("UPDATE upload SET parameter='    ' WHERE 1=1", new Object[] {});

        try {
            instance.loadScreeningTasks(null, null);
            fail("DAOException should be thrown");
        } catch (DAOException e) {
            assertEquals("check error message",
                "Error occurs when setting data to screeningData., "
                    + "caused by fileIdentifier should not be empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * user id is a string. unable to parse user id. DAOException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadScreeningTasks4() throws Exception {
        doSQLUpdate("UPDATE resource_info SET value='abcde' WHERE 1=1", new Object[] {});

        try {
            instance.loadScreeningTasks(null, null);
            fail("DAOException should be thrown");
        } catch (DAOException e) {
            assertEquals("check error message",
                "Unable to parse external user id [abcde] to long value., "
                    + "caused by For input string: \"abcde\"", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)</code>.
     * </p>
     * <p>
     * user id is negative. DAOException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadScreeningTasks5() throws Exception {
        doSQLUpdate("UPDATE resource_info SET value='-123' WHERE 1=1", new Object[] {});

        try {
            instance.loadScreeningTasks(null, null);
            fail("DAOException should be thrown");
        } catch (DAOException e) {
            assertEquals("check error message",
                "Unable to get external user information for user id [-123]., "
                    + "caused by ids[0] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * The task should be updated successfully. status from 'pending' to
     * 'screening'.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyUpdateScreeningTask1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(1);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.SCREENING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        instance.updateScreeningTask(screeningTask);

        assertPersistence("check task is persisted in the database", screeningTask, true);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * The task should be updated successfully. status from 'screening' to
     * 'pending'.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyUpdateScreeningTask2() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(5);
        screeningTask.setScreenerId(Long.MIN_VALUE);
        screeningTask.setStartTimestamp(null);
        screeningTask.setScreeningStatus(ScreeningStatus.PENDING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        instance.updateScreeningTask(screeningTask);

        assertPersistence("check task is persisted in the database", screeningTask, true);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * The task should be updated successfully. status from 'screening' to
     * 'failed'.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyUpdateScreeningTask3() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(5);
        screeningTask.setScreeningStatus(ScreeningStatus.FAILED);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        instance.updateScreeningTask(screeningTask);

        assertPersistence("check task is persisted in the database", screeningTask, true);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * The task should be updated successfully. status from 'screening' to
     * 'passed'.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyUpdateScreeningTask4() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(5);
        screeningTask.setScreeningStatus(ScreeningStatus.PASSED);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        instance.updateScreeningTask(screeningTask);

        assertPersistence("check task is persisted in the database", screeningTask, true);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * The task should be updated successfully. status from 'screening' to
     * 'passed with warning'.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyUpdateScreeningTask5() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(5);
        screeningTask.setScreeningStatus(ScreeningStatus.PASSED_WITH_WARNING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        instance.updateScreeningTask(screeningTask);

        assertPersistence("check task is persisted in the database", screeningTask, true);
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'pending' to 'pending'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(1);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PENDING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'pending' to 'failed'. UpdateFailedException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask2() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(1);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.FAILED);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'pending' to 'passed'. UpdateFailedException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask3() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(1);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PASSED);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'pending' to 'passed with warning'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask4() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(1);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PASSED_WITH_WARNING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'screening' to 'screening'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask5() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(5);
        screeningTask.setScreeningStatus(ScreeningStatus.SCREENING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'failed' to 'pending'. UpdateFailedException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask6() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(7);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PENDING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'failed' to 'screening'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask7() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(7);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.SCREENING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'failed' to 'failed'. UpdateFailedException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask8() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(7);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.FAILED);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'failed' to 'passed'. UpdateFailedException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask9() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(7);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PASSED);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'failed' to 'passed with warning'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask10() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(7);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PASSED_WITH_WARNING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'passed' to 'pending'. UpdateFailedException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask11() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(8);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PENDING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'passed' to 'screening'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask12() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(8);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.SCREENING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'passed' to 'failed'. UpdateFailedException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask13() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(8);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.FAILED);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'passed' to 'passed'. UpdateFailedException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask14() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(8);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PASSED);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'passed' to 'passed with warning'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask15() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(8);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PASSED_WITH_WARNING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'passed with warning' to 'pending'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask16() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(9);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PENDING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'passed with warning' to 'screening'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask17() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(9);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.SCREENING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'passed with warning' to 'failed'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask18() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(9);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.FAILED);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'passed with warning' to 'passed'.
     * UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask19() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(9);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PASSED);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * Unable to update status from 'passed with warning' to 'passed with
     * warning'. UpdateFailedException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask20() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(9);
        screeningTask.setScreenerId(1);
        screeningTask.setStartTimestamp(new Date());
        screeningTask.setScreeningStatus(ScreeningStatus.PASSED_WITH_WARNING);
        // screeningData is not set, since it will not be used by
        // informixTaskDAO. that's is to say the upload_id column will never be
        // updated
        screeningTask.setCreationUser("system1");
        screeningTask.setCreationDate(new Date());
        screeningTask.setModificationUser("user1");
        screeningTask.setModificationDate(new Date());

        try {
            instance.updateScreeningTask(screeningTask);
            fail("UpdateFailedException should be thrown");
        } catch (UpdateFailedException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * screeningTask is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask21() throws Exception {
        try {
            instance.updateScreeningTask(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "screeningTask should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * screeningTask.getScreeningStatus is null. DAOException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask22() throws Exception {
        try {
            instance.updateScreeningTask(new ScreeningTask());
            fail("DAOException should be thrown");
        } catch (DAOException e) {
            assertEquals("check error message",
                "Screening status of screeningTask has not been specified.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void updateScreeningTask(ScreeningTask screeningTask)</code>.
     * </p>
     * <p>
     * screeningTask.getScreeningStatus is not null, but all other fields are
     * unset. DAOException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateScreeningTask23() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setScreeningStatus(ScreeningStatus.FAILED);
        try {
            instance.updateScreeningTask(screeningTask);
            fail("DAOException should be thrown");
        } catch (DAOException e) {
            // ok.
        }
    }

    /**
     * Create a ScreeningData instance.
     * @param uploadId
     *            upload id
     * @param projectId
     *            project id
     * @param projectCategoryId
     *            category id
     * @param fileIdentifier
     *            file identifier
     * @param handle
     *            user handle
     * @param firstName
     *            user's first name
     * @param lastName
     *            user's last name
     * @param email
     *            user's email
     * @param alternativeEmails
     *            user's alternative emails
     * @return a ScreeningData instance
     */
    private ScreeningData createScreeningData(long uploadId, long projectId,
        long projectCategoryId, String fileIdentifier, String handle, String firstName,
        String lastName, String email, String[] alternativeEmails) {

        ScreeningData screeningData = new ScreeningData();
        screeningData.setUploadId(uploadId);
        screeningData.setProjectId(projectId);
        screeningData.setProjectCategoryId(projectCategoryId);
        screeningData.setFileIdentifier(fileIdentifier);
        screeningData.setSubmitterHandle(handle);
        screeningData.setSubmitterFirstName(firstName);
        screeningData.setSubmitterLastName(lastName);
        screeningData.setSubmitterEmail(email);
        screeningData.setSubmitterAlternativeEmails(alternativeEmails);
        return screeningData;
    }

    /**
     * Create a ScreeningTask instance. Date fields are ignored
     * @param taskId
     *            task id
     * @param screenerId
     *            screener id
     * @param createUser
     *            create user
     * @param modifyUser
     *            modify user
     * @param status
     *            screening status
     * @param data
     *            screening data
     * @return a ScreeningTask instance
     */
    private ScreeningTask createScreeningTask(long taskId, long screenerId, String createUser,
        String modifyUser, ScreeningStatus status, ScreeningData data) {
        ScreeningTask screeningTask = new ScreeningTask();

        screeningTask.setId(taskId);
        screeningTask.setScreenerId(screenerId);
        screeningTask.setCreationUser(createUser);
        screeningTask.setModificationUser(modifyUser);
        screeningTask.setScreeningStatus(status);
        screeningTask.setScreeningData(data);
        return screeningTask;
    }

    /**
     * Compare contents of two object arrays.
     * @param array1
     *            the first array to compare
     * @param array2
     *            the second array to compare
     */
    private void compareArray(Object[] array1, Object[] array2) {
        assertEquals("check array length", array1.length, array2.length);
        Arrays.sort(array1);
        Arrays.sort(array2);
        for (int i = 0; i < array1.length; ++i) {
            assertEquals("check element" + i, array1[i], array2[i]);
        }
    }

    /**
     * Compare two ScreeningData instances.
     * @param data1
     *            the first ScreeningData instance
     * @param data2
     *            the second ScreeningData instance
     */
    private void compareScreeningData(ScreeningData data1, ScreeningData data2) {
        assertEquals("check project id", data1.getProjectId(), data2.getProjectId());
        assertEquals("check project category id", data1.getProjectCategoryId(), data2
            .getProjectCategoryId());
        assertEquals("check upload id", data1.getUploadId(), data2.getUploadId());
        assertEquals("check file identifier", data1.getFileIdentifier(), data2.getFileIdentifier());
        assertEquals("check submitter handle", data1.getSubmitterHandle(), data2
            .getSubmitterHandle());
        assertEquals("check submitter first name", data1.getSubmitterFirstName(), data2
            .getSubmitterFirstName());
        assertEquals("check submitter last name", data1.getSubmitterLastName(), data2
            .getSubmitterLastName());
        assertEquals("check submitter email", data1.getSubmitterEmail(), data2.getSubmitterEmail());

        compareArray(data1.getSubmitterAlternativeEmails(), data2.getSubmitterAlternativeEmails());
    }

    /**
     * Compare two ScreeningTask instances. Date fields are ignored.
     * @param task1
     *            the first task
     * @param task2
     *            the second task
     */
    private void compareScreeningTask(ScreeningTask task1, ScreeningTask task2) {
        assertEquals("check task id", task1.getId(), task2.getId());
        assertEquals("check screener id", task1.getScreenerId(), task2.getScreenerId());
        assertEquals("check screening status", task1.getScreeningStatus(), task2
            .getScreeningStatus());
        assertEquals("check create user", task1.getCreationUser(), task2.getCreationUser());
        assertEquals("check modify user", task1.getModificationUser(), task2.getModificationUser());
        compareScreeningData(task1.getScreeningData(), task2.getScreeningData());
    }

    /**
     * Sort the ScreeningTask array by the id of screening task in ascending
     * order.
     * @param tasks
     *            the ScreeningTask array to sort
     */
    private void sortScreeningTasksById(ScreeningTask[] tasks) {

        Arrays.sort(tasks, new Comparator() {
            public int compare(Object a, Object b) {
                ScreeningTask task1 = (ScreeningTask) a;
                ScreeningTask task2 = (ScreeningTask) b;
                if (task1.getId() < task2.getId()) {
                    return -1;
                } else if (task1.getId() > task2.getId()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    /**
     * <p>
     * Verify the ScreeningTask is in database.
     * </p>
     * @param msg
     *            the message about the assertion
     * @param screeningTask
     *            the screeningTask to check.
     * @param isExisted
     *            the screeningTask should be existed or not.
     * @throws Exception
     *             if any error occurs
     */
    private void assertPersistence(String msg, ScreeningTask screeningTask, boolean isExisted)
        throws Exception {

        String sql = "select * from screening_task where screening_task_id="
            + screeningTask.getId();

        // get connection
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (!isExisted) {
                assertFalse(msg, rs.next());
            } else {
                assertTrue(msg, rs.next());

                assertEquals(msg, screeningTask.getScreeningStatus().getId(), rs
                    .getLong("screening_status_id"));

                long screenerId = rs.getLong("screener_id");
                if (rs.wasNull()) {
                    screenerId = Long.MIN_VALUE;
                }
                assertEquals(msg, screeningTask.getScreenerId(), screenerId);

                Timestamp startTimestamp = rs.getTimestamp("start_timestamp");
                assertEquals(msg, screeningTask.getStartTimestamp(), startTimestamp == null ? null
                    : new Date(startTimestamp.getTime()));

                assertEquals(msg, screeningTask.getCreationUser(), rs.getString("create_user"));
                assertEquals(msg, screeningTask.getModificationUser(), rs.getString("modify_user"));
                assertEquals(msg, screeningTask.getCreationDate(), new Date(rs.getTimestamp(
                    "create_date").getTime()));
                assertEquals(msg, screeningTask.getModificationDate(), new Date(rs.getTimestamp(
                    "modify_date").getTime()));
            }
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
    }
}
