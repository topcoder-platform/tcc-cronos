/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TestHelper;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbTaskTypeDAO.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbTaskTypeDAOTests extends TestCase {
    /**
     * <p>
     * The DbTaskTypeDAO instance for testing.
     * </p>
     */
    private DbTaskTypeDAO taskTypeDao;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * The AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.SEARCH_CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(null, null, null);

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(TestHelper.AUDIT_NAMESPACE);

        taskTypeDao = new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
            TestHelper.SEARCH_NAMESPACE, "TaskTypeBundle", auditManager);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        dbFactory = null;
        auditManager = null;
        taskTypeDao = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbTaskTypeDAOTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbTaskTypeDAO instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbTaskTypeDAO instance.", taskTypeDao);
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connFactory is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnFactory() throws Exception {
        try {
            new DbTaskTypeDAO(null, TestHelper.CONNECTION_NAME, "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
                "TaskTypeBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyConnName() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, " ", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE, "TaskTypeBundle",
                auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullIdGen() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, null, TestHelper.SEARCH_NAMESPACE,
                "TaskTypeBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyIdGen() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, " ", TestHelper.SEARCH_NAMESPACE,
                "TaskTypeBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullSearchStrategyNamespace() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, "TaskTypeIdGenerator", null, "TaskTypeBundle",
                auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptySearchStrategyNamespace() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, "TaskTypeIdGenerator", " ", "TaskTypeBundle",
                auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when auditor is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullAuditor() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
                TestHelper.SEARCH_NAMESPACE, "TaskTypeBundle", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the search strategy namespace is unknown and expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ConfigurationException() {
        try {
            new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, "TaskTypeIdGenerator", "unknown_namespace",
                "TaskTypeBundle", auditManager);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeDAO#createTaskTypes(TaskType[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);

        taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});

        TaskType[] insertedTaskTypes = taskTypeDao.getTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        TestHelper.assertTaskTypeEquals(taskType1, insertedTaskTypes[0]);
        TestHelper.assertTaskTypeEquals(taskType2, insertedTaskTypes[1]);
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes_NullTimeEntries() throws Exception {
        try {
            taskTypeDao.createTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes_EmptyTimeEntries() throws Exception {
        try {
            taskTypeDao.createTaskTypes(new TaskType[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes_SameElement() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);

        try {
            taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes_NullInTimeEntries() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);

        try {
            taskTypeDao.createTaskTypes(new TaskType[] {taskType1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes_TaskTypeWithIdSet() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        taskType2.setId(34);

        try {
            taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null creation date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes_TaskTypeWithNullCreationDate() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType("CreationDate");

        try {
            taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null creation user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes_TaskTypeWithNullCreationUser() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType("CreationUser");

        try {
            taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null modification date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes_TaskTypeWithNullModificationDate() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType("ModificationDate");

        try {
            taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes_TaskTypeWithNullModificationUser() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType("ModificationUser");

        try {
            taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes_DataAccessException() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        taskTypeDao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);

        try {
            taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeDAO#updateTaskTypes(TaskType[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});

        taskType1.setDescription("Time status.");
        taskTypeDao.updateTaskTypes(new TaskType[] {taskType1, taskType2});

        TaskType[] updatedTaskTypes = taskTypeDao.getTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        TestHelper.assertTaskTypeEquals(taskType1, updatedTaskTypes[0]);
        TestHelper.assertTaskTypeEquals(taskType2, updatedTaskTypes[1]);
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes_NullTimeEntries() throws Exception {
        try {
            taskTypeDao.updateTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes_EmptyTimeEntries() throws Exception {
        try {
            taskTypeDao.updateTaskTypes(new TaskType[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes_SameTaskType() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1});

        try {
            taskTypeDao.updateTaskTypes(new TaskType[] {taskType1, taskType1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes_NullInTimeEntries() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1});

        try {
            taskTypeDao.updateTaskTypes(new TaskType[] {taskType1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes_TaskTypeWithIdSet() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1});
        TaskType taskType2 = TestHelper.createTestingTaskType(null);

        try {
            taskTypeDao.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null modification date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes_TaskTypeWithNullModificationDate() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1});
        TaskType taskType2 = TestHelper.createTestingTaskType("ModificationDate");
        taskType2.setId(100);

        try {
            taskTypeDao.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes_TaskTypeWithNullModificationUser() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1});
        TaskType taskType2 = TestHelper.createTestingTaskType("ModificationUser");
        taskType2.setId(300);

        try {
            taskTypeDao.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes_DataAccessException() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});

        taskTypeDao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        try {
            taskTypeDao.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#deleteTaskTypes(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeDAO#deleteTaskTypes(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});

        taskTypeDao.deleteTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        assertEquals("Failed to remove the task types.", 0, taskTypeDao.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#deleteTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes_NullTaskTypeIds() throws Exception {
        try {
            taskTypeDao.deleteTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#deleteTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeIds is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes_EmptyTaskTypeIds() throws Exception {
        try {
            taskTypeDao.deleteTaskTypes(new long[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#deleteTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeIds contains equal id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes_EqualTaskTypeId() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1});

        try {
            taskTypeDao.deleteTaskTypes(new long[] {taskType1.getId(), taskType1.getId()});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#deleteTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes_DataAccessException() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1});

        taskTypeDao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);

        try {
            taskTypeDao.deleteTaskTypes(new long[] {taskType1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#getTaskTypes(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeDAO#getTaskTypes(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});

        TaskType[] taskTypes = taskTypeDao.getTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        TestHelper.assertTaskTypeEquals(taskTypes[0], taskType1);
        TestHelper.assertTaskTypeEquals(taskTypes[1], taskType2);
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#getTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypes_NullTaskTypeIds() throws Exception {
        try {
            taskTypeDao.getTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#getTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypes_DataAccessException() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1});

        taskTypeDao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);

        try {
            taskTypeDao.getTaskTypes(new long[] {taskType1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#searchTaskTypes(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeDAO#searchTaskTypes(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskType1.setDescription("NewTaskType");
        taskTypeDao.createTaskTypes(new TaskType[] {taskType1});

        // verify the description filter
        Filter filter = taskTypeDao.getTaskTypeFilterFactory().createDescriptionFilter(taskType1.getDescription(),
            StringMatchType.ENDS_WITH);
        TaskType[] taskTypes = taskTypeDao.searchTaskTypes(filter);
        assertEquals("Failed to search the task types.", 1, taskTypes.length);
        TestHelper.assertTaskTypeEquals(taskType1, taskTypes[0]);

        // verify the active filter
        filter = taskTypeDao.getTaskTypeFilterFactory().createActiveFilter(taskType1.getActive());
        taskTypes = taskTypeDao.searchTaskTypes(filter);
        assertEquals("Failed to search the task types.", 1, taskTypes.length);
        TestHelper.assertTaskTypeEquals(taskType1, taskTypes[0]);

        // verify the company id filter
        filter = taskTypeDao.getTaskTypeFilterFactory().createCompanyIdFilter(taskType1.getCompanyId());
        taskTypes = taskTypeDao.searchTaskTypes(filter);
        assertEquals("Failed to search the task types.", 1, taskTypes.length);
        TestHelper.assertTaskTypeEquals(taskType1, taskTypes[0]);

        // verify the creation user filter
        filter = taskTypeDao.getTaskTypeFilterFactory().createCreationUserFilter(taskType1.getCreationUser(),
            StringMatchType.ENDS_WITH);
        taskTypes = taskTypeDao.searchTaskTypes(filter);
        assertEquals("Failed to search the task types.", 1, taskTypes.length);
        TestHelper.assertTaskTypeEquals(taskType1, taskTypes[0]);

        // verify the modification user filter
        filter = taskTypeDao.getTaskTypeFilterFactory().createModificationUserFilter(taskType1.getModificationUser(),
            StringMatchType.ENDS_WITH);
        taskTypes = taskTypeDao.searchTaskTypes(filter);
        assertEquals("Failed to search the task types.", 1, taskTypes.length);
        TestHelper.assertTaskTypeEquals(taskType1, taskTypes[0]);
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#searchTaskTypes(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when criteria is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTaskTypes_NullCriteria() throws Exception {
        try {
            taskTypeDao.searchTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#searchTaskTypes(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTaskTypes_DataAccessException() {
        Filter filter = new EqualToFilter("no_column", "value");
        try {
            taskTypeDao.searchTaskTypes(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#getTaskTypeFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeDAO#getTaskTypeFilterFactory() is correct.
     * </p>
     */
    public void testGetTaskTypeFilterFactory() {
        assertNotNull("Failed to get the task type filter factory.", taskTypeDao.getTaskTypeFilterFactory());
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#getAllTaskTypes() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeDAO#getAllTaskTypes() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTaskTypes() throws Exception {
        assertEquals("The time_status table should be empty.", 0, taskTypeDao.getAllTaskTypes().length);

        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);

        taskTypeDao.createTaskTypes(new TaskType[] {taskType1, taskType2});

        TaskType[] retrievedTaskTypes = taskTypeDao.getTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        TestHelper.assertTaskTypeEquals(taskType1, retrievedTaskTypes[0]);
        TestHelper.assertTaskTypeEquals(taskType2, retrievedTaskTypes[1]);
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#getAllTaskTypes() for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTaskTypes_DataAccessException() throws Exception {
        taskTypeDao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        try {
            taskTypeDao.getAllTaskTypes();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

}