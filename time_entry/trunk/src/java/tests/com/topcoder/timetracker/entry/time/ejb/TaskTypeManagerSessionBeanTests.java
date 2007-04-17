/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.ejb;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.delegate.TaskTypeManagerDelegate;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for TaskTypeManagerSessionBean.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TaskTypeManagerSessionBeanTests extends TestCase {
    /**
     * <p>
     * The TaskTypeManagerSessionBean instance for testing.
     * </p>
     */
    private TaskTypeManagerSessionBean bean;

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
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();

        bean = new TaskTypeManagerSessionBean();
        TestHelper.setUpEJBEnvironment(bean, null, null);

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
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        delegate = null;
        bean = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TaskTypeManagerSessionBeanTests.class);
    }

    /**
     * <p>
     * Tests ctor TaskTypeManagerSessionBean#TaskTypeManagerSessionBean() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created TaskTypeManagerSessionBean instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new TaskTypeManagerSessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getTaskType(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#getTaskType(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskType() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        delegate.createTaskType(taskType);

        assertEquals("Failed to get the task type.", taskType, delegate.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getTaskType(long) for failure.
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
            delegate.getTaskType(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskType(TaskType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#createTaskType(TaskType) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskType() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        delegate.createTaskType(taskType);

        assertEquals("Failed to insert the task type.", taskType, delegate.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskType(TaskType) for failure.
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
            delegate.createTaskType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskType(TaskType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#updateTaskType(TaskType) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskType() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        delegate.createTaskType(taskType);

        taskType.setDescription("Time status.");
        delegate.updateTaskType(taskType);

        assertEquals("Failed to update the task type.", taskType, delegate.getTaskType(taskType.getId()));
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskType(TaskType) for failure.
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
            delegate.updateTaskType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskType(TaskType) for failure.
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
            delegate.updateTaskType(taskType);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#deleteTaskType(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#deleteTaskType(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskType() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        delegate.createTaskType(taskType);

        delegate.deleteTaskType(taskType.getId());

        assertEquals("Failed to remove the task type.", 0, delegate.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#deleteTaskType(long) for failure.
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
            delegate.deleteTaskType(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskTypes(TaskType[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#createTaskTypes(TaskType[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);

        delegate.createTaskTypes(new TaskType[] {taskType1, taskType2});

        TaskType[] insertedTaskTypes = delegate.getTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        assertEquals("Failed to insert the task types.", taskType1, insertedTaskTypes[0]);
        assertEquals("Failed to insert the task types.", taskType2, insertedTaskTypes[1]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskTypes(TaskType[]) for failure.
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
            delegate.createTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskTypes(TaskType[]) for failure.
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
            delegate.createTaskTypes(new TaskType[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskTypes(TaskType[]) for failure.
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
            delegate.createTaskTypes(new TaskType[] {taskType1, taskType1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskTypes(TaskType[]) for failure.
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
            delegate.createTaskTypes(new TaskType[] {taskType1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskTypes(TaskType[]) for failure.
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
            delegate.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskTypes(TaskType[]) for failure.
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
            delegate.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#createTaskTypes(TaskType[]) for failure.
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
            delegate.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskTypes(TaskType[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#updateTaskTypes(TaskType[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        delegate.createTaskTypes(new TaskType[] {taskType1, taskType2});

        taskType1.setDescription("Time status.");
        delegate.updateTaskTypes(new TaskType[] {taskType1, taskType2});

        TaskType[] updatedTaskTypes = delegate.getTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        assertEquals("Failed to update the task types.", taskType1, updatedTaskTypes[0]);
        assertEquals("Failed to update the task types.", taskType2, updatedTaskTypes[1]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskTypes(TaskType[]) for failure.
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
            delegate.updateTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskTypes(TaskType[]) for failure.
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
            delegate.updateTaskTypes(new TaskType[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskTypes(TaskType[]) for failure.
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
        delegate.createTaskTypes(new TaskType[] {taskType1});

        try {
            delegate.updateTaskTypes(new TaskType[] {taskType1, taskType1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskTypes(TaskType[]) for failure.
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
        delegate.createTaskTypes(new TaskType[] {taskType1});

        try {
            delegate.updateTaskTypes(new TaskType[] {taskType1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskTypes(TaskType[]) for failure.
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
        delegate.createTaskTypes(new TaskType[] {taskType1});
        TaskType taskType2 = TestHelper.createTestingTaskType(null);

        try {
            delegate.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#updateTaskTypes(TaskType[]) for failure.
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
        delegate.createTaskTypes(new TaskType[] {taskType1});
        TaskType taskType2 = TestHelper.createTestingTaskType("ModificationUser");
        taskType2.setId(300);

        try {
            delegate.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#deleteTaskTypes(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#deleteTaskTypes(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        delegate.createTaskTypes(new TaskType[] {taskType1, taskType2});

        delegate.deleteTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        assertEquals("Failed to remove the task types.", 0, delegate.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#deleteTaskTypes(long[]) for failure.
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
            delegate.deleteTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#deleteTaskTypes(long[]) for failure.
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
            delegate.deleteTaskTypes(new long[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#deleteTaskTypes(long[]) for failure.
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
        delegate.createTaskTypes(new TaskType[] {taskType1});

        try {
            delegate.deleteTaskTypes(new long[] {taskType1.getId(), taskType1.getId()});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getTaskTypes(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#getTaskTypes(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        delegate.createTaskTypes(new TaskType[] {taskType1, taskType2});

        TaskType[] taskTypes = delegate.getTaskTypes(new long[] {taskType1.getId(), taskType2.getId()});

        assertEquals("Failed to get the task types.", taskTypes[0], taskType1);
        assertEquals("Failed to get the task types.", taskTypes[1], taskType2);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getTaskTypes(long[]) for failure.
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
            delegate.getTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#searchTaskTypes(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#searchTaskTypes(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTaskTypes() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        taskType1.setDescription("NewTaskType");
        delegate.createTaskTypes(new TaskType[] {taskType1});

        Filter filter = delegate.getTaskTypeFilterFactory().createDescriptionFilter(taskType1.getDescription(),
            StringMatchType.ENDS_WITH);
        TaskType[] taskTypes = delegate.searchTaskTypes(filter);

        assertEquals("Failed to search the task types.", 1, taskTypes.length);
        assertEquals("Failed to search the task types.", taskType1, taskTypes[0]);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#searchTaskTypes(Filter) for failure.
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
            delegate.searchTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#searchTaskTypes(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTaskTypes_DataAccessException() {
        Filter filter = new EqualToFilter("no_column", "value");
        try {
            delegate.searchTaskTypes(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getTaskTypeFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#getTaskTypeFilterFactory() is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskTypeFilterFactory() throws Exception {
        assertNotNull("Failed to get the task type filter factory.", bean.getTaskTypeFilterFactory());
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getAllTaskTypes() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#getAllTaskTypes() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTaskTypes() throws Exception {
        assertEquals("The time_status table should be empty.", 0, delegate.getAllTaskTypes().length);
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#getSessionContext() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#getSessionContext() is correct.
     * </p>
     */
    public void testGetSessionContext() {
        bean.setSessionContext(null);
        assertNull("Failed to get the session context.", bean.getSessionContext());
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#ejbCreate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#ejbCreate() is correct.
     * </p>
     */
    public void testEjbCreate() {
        bean.ejbCreate();
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#ejbActivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#ejbActivate() is correct.
     * </p>
     */
    public void testEjbActivate() {
        bean.ejbActivate();
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#ejbPassivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#ejbPassivate() is correct.
     * </p>
     */
    public void testEjbPassivate() {
        bean.ejbPassivate();
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#ejbRemove() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#ejbRemove() is correct.
     * </p>
     */
    public void testEjbRemove() {
        bean.ejbRemove();
    }

    /**
     * <p>
     * Tests TaskTypeManagerSessionBean#setSessionContext(SessionContext) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskTypeManagerSessionBean#setSessionContext(SessionContext) is correct.
     * </p>
     */
    public void testSetSessionContext() {
        bean.setSessionContext(null);
        assertNull("Failed to set the session context.", bean.getSessionContext());
    }

}