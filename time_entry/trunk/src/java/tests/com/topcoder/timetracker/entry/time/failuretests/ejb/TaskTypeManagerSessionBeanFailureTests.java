/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.ejb;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.delegate.TaskTypeManagerDelegate;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerSessionBean;
import com.topcoder.timetracker.entry.time.failuretests.FailureTestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Failure test cases for TaskTypeManagerSessionBean.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class TaskTypeManagerSessionBeanFailureTests extends TestCase {
    /**
     * <p>
     * The TaskTypeManagerSessionBean instance for testing.
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
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.loadXMLConfig(FailureTestHelper.AUDIT_CONFIG_FILE);
        FailureTestHelper.setUpDataBase();

        instance = new TaskTypeManagerSessionBean();
        FailureTestHelper.setUpEJBEnvironment(instance, null, null);

        delegate = new TaskTypeManagerDelegate(
                "com.topcoder.timetracker.entry.time.delegate.TaskTypeManagerDelegate");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.tearDownDataBase();
        FailureTestHelper.clearConfig();

        delegate = null;
        instance = null;
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
    public void testGetTaskType() throws Exception {
        try {
            delegate.getTaskType(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
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
    public void testUpdateTaskType1() throws Exception {
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
    public void testUpdateTaskType2() throws Exception {
        TaskType taskType = FailureTestHelper.createTestingTaskType(null);
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
     * Tests TaskTypeManagerSessionBean#deleteTaskType(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskType() throws Exception {
        try {
            delegate.deleteTaskType(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
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
    public void testCreateTaskType() throws Exception {
        try {
            delegate.createTaskType(null);
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
     * It tests the case that when taskTypes is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes1() throws Exception {
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
    public void testCreateTaskTypes2() throws Exception {
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
    public void testCreateTaskTypes3() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);

        try {
            delegate.createTaskTypes(new TaskType[] { taskType1, taskType1 });
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
    public void testCreateTaskTypes4() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);

        try {
            delegate.createTaskTypes(new TaskType[] { taskType1, null });
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
    public void testCreateTaskTypes5() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        TaskType taskType2 = FailureTestHelper.createTestingTaskType(null);
        taskType2.setId(34);

        try {
            delegate.createTaskTypes(new TaskType[] { taskType1, taskType2 });
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
    public void testCreateTaskTypes6() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        TaskType taskType2 = FailureTestHelper.createTestingTaskType("CreationUser");

        try {
            delegate.createTaskTypes(new TaskType[] { taskType1, taskType2 });
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
    public void testCreateTaskTypes7() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        TaskType taskType2 = FailureTestHelper.createTestingTaskType("ModificationUser");

        try {
            delegate.createTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testUpdateTaskTypes1() throws Exception {
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
    public void testUpdateTaskTypes2() throws Exception {
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
    public void testUpdateTaskTypes3() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        delegate.createTaskTypes(new TaskType[] { taskType1 });

        try {
            delegate.updateTaskTypes(new TaskType[] { taskType1, taskType1 });
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
    public void testUpdateTaskTypes4() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        delegate.createTaskTypes(new TaskType[] { taskType1 });

        try {
            delegate.updateTaskTypes(new TaskType[] { taskType1, null });
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
    public void testUpdateTaskTypes5() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        delegate.createTaskTypes(new TaskType[] { taskType1 });

        TaskType taskType2 = FailureTestHelper.createTestingTaskType(null);

        try {
            delegate.updateTaskTypes(new TaskType[] { taskType1, taskType2 });
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
    public void testUpdateTaskTypes6() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        delegate.createTaskTypes(new TaskType[] { taskType1 });

        TaskType taskType2 = FailureTestHelper.createTestingTaskType("ModificationUser");
        taskType2.setId(300);

        try {
            delegate.updateTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testDeleteTaskTypes1() throws Exception {
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
    public void testDeleteTaskTypes2() throws Exception {
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
    public void testDeleteTaskTypes3() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        delegate.createTaskTypes(new TaskType[] { taskType1 });

        try {
            delegate.deleteTaskTypes(new long[] { taskType1.getId(), taskType1.getId() });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
    public void testGetTaskTypes() throws Exception {
        try {
            delegate.getTaskTypes(null);
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
     * It tests the case that when criteria is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTaskTypes1() throws Exception {
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
    public void testSearchTaskTypes2() {
        Filter filter = new EqualToFilter("no_column", "value");

        try {
            delegate.searchTaskTypes(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}
