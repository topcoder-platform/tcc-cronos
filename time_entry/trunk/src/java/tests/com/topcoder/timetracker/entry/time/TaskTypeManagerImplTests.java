/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeDAO;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for TaskTypeManagerImpl.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TaskTypeManagerImplTests extends TestCase {
    /**
     * <p>
     * The TaskTypeManagerImpl instance for testing.
     * </p>
     */
    private TaskTypeManagerImpl impl;

    /**
     * <p>
     * The TaskTypeDAO instance for testing.
     * </p>
     */
    private TaskTypeDAO dao;

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
        dao = new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
            TestHelper.SEARCH_NAMESPACE, "TaskTypeBundle", auditManager);

        impl = new TaskTypeManagerImpl(dao);
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

        impl = null;
        dao = null;
        auditManager = null;
        dbFactory = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TaskTypeManagerImplTests.class);
    }

    /**
     * <p>
     * Tests ctor TaskTypeManagerImpl#TaskTypeManagerImpl(TaskTypeDAO) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created TaskTypeManagerImpl instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new TaskTypeManagerImpl instance.", impl);
    }

    /**
     * <p>
     * Tests ctor TaskTypeManagerImpl#TaskTypeManagerImpl(TaskTypeDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeDAO is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullTaskTypeDAO() {
        try {
            new TaskTypeManagerImpl(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskType(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#getTaskType(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskType() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        impl.createTaskType(taskType);

        assertEquals("Failed to get the task type.", taskType, impl.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskType(long) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskType_DataAccessException() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        impl.createTaskType(taskType);

        dao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        impl = new TaskTypeManagerImpl(dao);

        try {
            impl.getTaskType(taskType.getId());
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskType(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskType_UnrecognizedEntityException() throws Exception {
        try {
            impl.getTaskType(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskType(TaskType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#createTaskType(TaskType) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskType() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        impl.createTaskType(taskType);

        assertEquals("Failed to insert the task type.", taskType, impl.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskType is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskType_NullTaskType() throws Exception {
        try {
            impl.createTaskType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskType_DataAccessException() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        dao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        impl = new TaskTypeManagerImpl(dao);

        try {
            impl.createTaskType(taskType);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskType(TaskType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#updateTaskType(TaskType) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskType() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        impl.createTaskType(taskType);

        taskType.setDescription("Time status.");
        impl.updateTaskType(taskType);

        assertEquals("Failed to update the task type.", taskType, impl.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskType is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskType_NullTaskType() throws Exception {
        try {
            impl.updateTaskType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskType_UnrecognizedEntityException() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        taskType.setId(100);

        try {
            impl.updateTaskType(taskType);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskType_DataAccessException() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        impl.createTaskType(taskType);

        dao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        impl = new TaskTypeManagerImpl(dao);

        try {
            impl.updateTaskType(taskType);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskType(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#deleteTaskType(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskType() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        impl.createTaskType(taskType);

        impl.deleteTaskType(taskType.getId());

        assertEquals("Failed to remove the task type.", 0, impl.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskType(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskType_UnrecognizedEntityException() throws Exception {
        try {
            impl.deleteTaskType(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskType(long) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskType_DataAccessException() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        impl.createTaskType(taskType);
        dao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        impl = new TaskTypeManagerImpl(dao);

        try {
            impl.deleteTaskType(taskType.getId());
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#createTaskTypes(TaskType[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);

        impl.createTaskTypes(new TaskType[] {taskType1, taskType2});

        TaskType[] insertedTaskTypes = impl.getTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        assertEquals("Failed to insert the task types.", taskType1, insertedTaskTypes[0]);
        assertEquals("Failed to insert the task types.", taskType2, insertedTaskTypes[1]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
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
            impl.createTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
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
            impl.createTaskTypes(new TaskType[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
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
            impl.createTaskTypes(new TaskType[] {taskType1, taskType1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
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
            impl.createTaskTypes(new TaskType[] {taskType1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
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
            impl.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
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
            impl.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
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
            impl.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
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

        dao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        impl = new TaskTypeManagerImpl(dao);

        try {
            impl.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#updateTaskTypes(TaskType[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        impl.createTaskTypes(new TaskType[] {taskType1, taskType2});

        taskType1.setDescription("Time status.");
        impl.updateTaskTypes(new TaskType[] {taskType1, taskType2});

        TaskType[] updatedTaskTypes = impl.getTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        assertEquals("Failed to update the task types.", taskType1, updatedTaskTypes[0]);
        assertEquals("Failed to update the task types.", taskType2, updatedTaskTypes[1]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
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
            impl.updateTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
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
            impl.updateTaskTypes(new TaskType[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
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
        impl.createTaskTypes(new TaskType[] {taskType1});

        try {
            impl.updateTaskTypes(new TaskType[] {taskType1, taskType1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
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
        impl.createTaskTypes(new TaskType[] {taskType1});

        try {
            impl.updateTaskTypes(new TaskType[] {taskType1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
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
        impl.createTaskTypes(new TaskType[] {taskType1});
        TaskType taskType2 = TestHelper.createTestingTaskType(null);

        try {
            impl.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
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
        impl.createTaskTypes(new TaskType[] {taskType1});
        TaskType taskType2 = TestHelper.createTestingTaskType("ModificationUser");
        taskType2.setId(300);

        try {
            impl.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
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
        impl.createTaskTypes(new TaskType[] {taskType1, taskType2});

        dao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        impl = new TaskTypeManagerImpl(dao);

        try {
            impl.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskTypes(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#deleteTaskTypes(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        impl.createTaskTypes(new TaskType[] {taskType1, taskType2});

        impl.deleteTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        assertEquals("Failed to remove the task types.", 0, impl.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskTypes(long[]) for failure.
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
            impl.deleteTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskTypes(long[]) for failure.
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
            impl.deleteTaskTypes(new long[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskTypes(long[]) for failure.
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
        impl.createTaskTypes(new TaskType[] {taskType1});

        try {
            impl.deleteTaskTypes(new long[] {taskType1.getId(), taskType1.getId()});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskTypes(long[]) for failure.
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
        impl.createTaskTypes(new TaskType[] {taskType1});

        dao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        impl = new TaskTypeManagerImpl(dao);

        try {
            impl.deleteTaskTypes(new long[] {taskType1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskTypes(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#getTaskTypes(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        impl.createTaskTypes(new TaskType[] {taskType1, taskType2});

        TaskType[] taskTypes = impl.getTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        assertEquals("Failed to get the task types.", taskTypes[0], taskType1);
        assertEquals("Failed to get the task types.", taskTypes[1], taskType2);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskTypes(long[]) for failure.
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
            impl.getTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskTypes(long[]) for failure.
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
        impl.createTaskTypes(new TaskType[] {taskType1});

        dao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        impl = new TaskTypeManagerImpl(dao);

        try {
            impl.getTaskTypes(new long[] {taskType1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#searchTaskTypes(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#searchTaskTypes(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskType1.setDescription("NewTaskType");
        impl.createTaskTypes(new TaskType[] {taskType1});

        Filter filter = impl.getTaskTypeFilterFactory().createDescriptionFilter(taskType1.getDescription(),
            StringMatchType.ENDS_WITH);
        TaskType[] taskTypes = impl.searchTaskTypes(filter);

        assertEquals("Failed to search the task types.", 1, taskTypes.length);
        assertEquals("Failed to search the task types.", taskType1, taskTypes[0]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#searchTaskTypes(Filter) for failure.
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
            impl.searchTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#searchTaskTypes(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTaskTypes_DataAccessException() {
        Filter filter = new EqualToFilter("no_column", "value");
        try {
            impl.searchTaskTypes(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskTypeFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#getTaskTypeFilterFactory() is correct.
     * </p>
     */
    public void testGetTaskTypeFilterFactory() {
        assertNotNull("Failed to get the task type filter factory.", impl.getTaskTypeFilterFactory());
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getAllTaskTypes() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerImpl#getAllTaskTypes() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTaskTypes() throws Exception {
        assertEquals("The time_status table should be empty.", 0, impl.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getAllTaskTypes() for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTaskTypes_DataAccessException() throws Exception {
        dao = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TaskTypeBundle", auditManager);
        impl = new TaskTypeManagerImpl(dao);

        try {
            impl.getAllTaskTypes();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}