/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.reg.actions.profile.TaskCompletionStatus;

/**
 * <p>
 * Accuracy tests for the {@link TaskCompletionStatus}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class TaskCompletionStatusTest {
    /** Represents the completed used to test again. */
    private boolean completedValue;

    /** Represents the taskName used to test again. */
    private String taskNameValue;

    /** Represents the instance used to test again. */
    private TaskCompletionStatus testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new TaskCompletionStatus();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link TaskCompletionStatus#TaskCompletionStatus()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testTaskCompletionStatus() throws Exception {
        new TaskCompletionStatus();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link TaskCompletionStatus#isCompleted()}
     * </p>
     * <p>
     * The value of <code>completed</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_isCompleted() throws Exception {
        assertNull("Old value", testInstance.isCompleted());
    }

    /**
     * <p>
     * Accuracy test {@link TaskCompletionStatus#setCompleted(boolean)}.
     * </p>
     * <p>
     * The value of <code>completed</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCompleted() throws Exception {
        testInstance.setCompleted(completedValue);
        assertEquals("New value", completedValue, testInstance.isCompleted());
    }

    /**
     * <p>
     * Accuracy test for {@link TaskCompletionStatus#getTaskName()}
     * </p>
     * <p>
     * The value of <code>taskName</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getTaskName() throws Exception {
        assertNull("Old value", testInstance.getTaskName());
    }

    /**
     * <p>
     * Accuracy test {@link TaskCompletionStatus#setTaskName(String)}.
     * </p>
     * <p>
     * The value of <code>taskName</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setTaskName() throws Exception {
        testInstance.setTaskName(taskNameValue);
        assertEquals("New value", taskNameValue, testInstance.getTaskName());
    }
}