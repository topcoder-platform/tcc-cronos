/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TaskTypeManagerImpl;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeDAO;

/**
 * <p>
 * Accuracy Unit test cases for TaskTypeManagerImpl.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class TaskTypeManagerImplAccuracyTests extends TestCase {
    /**
     * <p>
     * TaskTypeManagerImpl instance for testing.
     * </p>
     */
    private TaskTypeManagerImpl instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.AUDIT_CONFIG_FILE);
        AccuracyTestHelper.setUpDataBase();
        AccuracyTestHelper.setUpEJBEnvironment(null, null, null);

        DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(AccuracyTestHelper.DB_FACTORY_NAMESPACE);
        AuditManager auditManager = new AuditDelegate(AccuracyTestHelper.AUDIT_NAMESPACE);
        TaskTypeDAO dao = new DbTaskTypeDAO(dbFactory, AccuracyTestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
            AccuracyTestHelper.SEARCH_NAMESPACE, auditManager);

        instance = new TaskTypeManagerImpl(dao);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();

        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TaskTypeManagerImplAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor TaskTypeManagerImpl#TaskTypeManagerImpl(TaskTypeDAO) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create TaskTypeManagerImpl instance.", instance);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskType(TaskType) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskType() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        instance.createTaskType(taskType);

        assertEquals("Failed to insert the task type.", taskType, instance.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskType(TaskType) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskType() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        instance.createTaskType(taskType);

        taskType.setDescription("Time status.");
        instance.updateTaskType(taskType);

        assertEquals("Failed to update the task type.", taskType, instance.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskType(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskType() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        instance.createTaskType(taskType);
        instance.deleteTaskType(taskType.getId());

        assertEquals("Failed to remove the task types.", 0, instance.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskType(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskType() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        instance.createTaskType(taskType);

        assertEquals("Failed to get the task type.", taskType, instance.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#searchTaskTypes(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTaskTypes() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        taskType.setDescription("NewTaskType");
        instance.createTaskTypes(new TaskType[] {taskType});

        Filter filter = instance.getTaskTypeFilterFactory().createDescriptionFilter(taskType.getDescription(),
            StringMatchType.ENDS_WITH);
        TaskType[] taskTypes = instance.searchTaskTypes(filter);

        assertEquals("Failed to search the task types.", 1, taskTypes.length);
        assertEquals("Failed to search the task types.", taskType, taskTypes[0]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType});
        TaskType[] insertedTaskTypes = instance.getTaskTypes(new long[] {taskType.getId()});

        assertEquals("Failed to insert the task types.", taskType, insertedTaskTypes[0]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType});

        taskType.setDescription("Time status.");
        instance.updateTaskTypes(new TaskType[] {taskType});
        TaskType[] updatedTaskTypes = instance.getTaskTypes(new long[] {taskType.getId()});

        assertEquals("Failed to update the task types.", taskType, updatedTaskTypes[0]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskTypes(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType});
        instance.deleteTaskTypes(new long[] {taskType.getId()});

        assertEquals("Failed to remove the task types.", 0, instance.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskTypes(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypes() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType});
        TaskType[] taskTypes = instance.getTaskTypes(new long[] {taskType.getId()});

        assertEquals("Failed to get the task types.", taskTypes[0], taskType);
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskTypeFilterFactory() for accuracy.
     * </p>
     */
    public void testGetTaskTypeFilterFactory() {
        assertNotNull("Failed to get the task type filter factory.", instance.getTaskTypeFilterFactory());
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#getAllTaskTypes() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTaskTypes() throws Exception {
        assertEquals("The time_status table should be empty.", 0, instance.getAllTaskTypes().length);
    }
}