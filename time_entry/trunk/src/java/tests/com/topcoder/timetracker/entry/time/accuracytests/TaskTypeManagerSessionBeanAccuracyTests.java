/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.delegate.TaskTypeManagerDelegate;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerSessionBean;

/**
 * <p>
 * Accuracy Unit test cases for TaskTypeManagerSessionBean.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class TaskTypeManagerSessionBeanAccuracyTests extends TestCase {
    /**
     * <p>
     * TaskTypeManagerSessionBean instance for testing.
     * </p>
     */
    private TaskTypeManagerSessionBean instance;

    /**
     * <p>
     * The TaskTypeManagerDelegate instance for testing.
     * </p>
     */
    private TaskTypeManagerDelegate delegate;

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
        instance = new TaskTypeManagerSessionBean();
        AccuracyTestHelper.setUpEJBEnvironment(instance, null, null);

        delegate = new TaskTypeManagerDelegate("com.topcoder.timetracker.entry.time.delegate.TaskTypeManagerDelegate");
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

        delegate = null;
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
        return new TestSuite(TaskTypeManagerSessionBeanAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor TaskTypeManagerSessionBean#TaskTypeManagerSessionBean() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create TaskTypeManagerSessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskType(TaskType) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskType() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        delegate.createTaskType(taskType);

        assertEquals("Failed to insert the task type.", taskType, delegate.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskType(TaskType) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskType() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        delegate.createTaskType(taskType);

        taskType.setDescription("Time status.");
        delegate.updateTaskType(taskType);

        assertEquals("Failed to update the task type.", taskType, delegate.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#deleteTaskType(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskType() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        delegate.createTaskType(taskType);
        delegate.deleteTaskType(taskType.getId());

        assertEquals("Failed to remove the task types.", 0, delegate.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getTaskType(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskType() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        delegate.createTaskType(taskType);

        assertEquals("Failed to get the task type.", taskType, delegate.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#searchTaskTypes(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTaskTypes() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        taskType.setDescription("NewTaskType");
        delegate.createTaskTypes(new TaskType[] {taskType});

        Filter filter = delegate.getTaskTypeFilterFactory().createDescriptionFilter(taskType.getDescription(),
            StringMatchType.ENDS_WITH);
        TaskType[] taskTypes = delegate.searchTaskTypes(filter);

        assertEquals("Failed to search the task types.", 1, taskTypes.length);
        assertEquals("Failed to search the task types.", taskType, taskTypes[0]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskTypes(TaskType[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        delegate.createTaskTypes(new TaskType[] {taskType});
        TaskType[] insertedTaskTypes = delegate.getTaskTypes(new long[] {taskType.getId()});

        assertEquals("Failed to insert the task types.", taskType, insertedTaskTypes[0]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskTypes(TaskType[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        delegate.createTaskTypes(new TaskType[] {taskType});

        taskType.setDescription("Time status.");
        delegate.updateTaskTypes(new TaskType[] {taskType});
        TaskType[] updatedTaskTypes = delegate.getTaskTypes(new long[] {taskType.getId()});

        assertEquals("Failed to update the task types.", taskType, updatedTaskTypes[0]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#deleteTaskTypes(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        delegate.createTaskTypes(new TaskType[] {taskType});
        delegate.deleteTaskTypes(new long[] {taskType.getId()});

        assertEquals("Failed to remove the task types.", 0, delegate.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getTaskTypes(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypes() throws Exception {
        TaskType taskType = AccuracyTestHelper.createTaskType(null);
        delegate.createTaskTypes(new TaskType[] {taskType});
        TaskType[] taskTypes = delegate.getTaskTypes(new long[] {taskType.getId()});

        assertEquals("Failed to get the task types.", taskTypes[0], taskType);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getTaskTypeFilterFactory() for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskTypeFilterFactory() throws Exception {
        assertNotNull("Failed to get the task type filter factory.", instance.getTaskTypeFilterFactory());
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getAllTaskTypes() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTaskTypes() throws Exception {
        assertEquals("The time_status table should be empty.", 0, delegate.getAllTaskTypes().length);
    }
}