/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import junit.framework.TestCase;

/**
 * This class aggregates all test cases for com.topcoder.web.reg.actions.profile.TaskCompletionStatus.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TaskCompletionStatusTest extends TestCase {

    /** Represents TaskCompletionStatus instance to test. */
    private TaskCompletionStatus taskCompletionStatus;

    /**
     * Sets up the test environment.
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        taskCompletionStatus = new TaskCompletionStatus();
    }

    /**
     * Tears down the test environment.
     * @throws Exception if any exception occurred
     */
    public void tearDown() throws Exception {
        taskCompletionStatus = null;
    }

    /**
     * <p>
     * Tests TaskCompletionStatus constructor.
     * </p>
     * <p>
     * TaskCompletionStatus instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("TaskCompletionStatus instance should be created successfully.", taskCompletionStatus);
    }

    /**
     * <p>
     * Tests TaskCompletionStatus#getTaskName() method.
     * </p>
     * <p>
     * taskName should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetTaskName() {
        String taskName = "test";
        taskCompletionStatus.setTaskName(taskName);
        assertSame("getTaskName() doesn't work properly.", taskName, taskCompletionStatus.getTaskName());
    }

    /**
     * <p>
     * Tests TaskCompletionStatus#setTaskName(String) method.
     * </p>
     * <p>
     * taskName should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTaskName() {
        String taskName = "test";
        taskCompletionStatus.setTaskName(taskName);
        assertSame("setTaskName() doesn't work properly.", taskName, taskCompletionStatus.getTaskName());
    }

    /**
     * <p>
     * Tests TaskCompletionStatus#isCompleted() method.
     * </p>
     * <p>
     * completed should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testIsCompleted() {
        boolean completed = true;
        taskCompletionStatus.setCompleted(completed);
        assertEquals("isCompleted() doesn't work properly.", completed, taskCompletionStatus.isCompleted());
    }

    /**
     * <p>
     * Tests TaskCompletionStatus#setCompleted(boolean) method.
     * </p>
     * <p>
     * completed should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCompleted() {
        boolean completed = true;
        taskCompletionStatus.setCompleted(completed);
        assertEquals("setCompleted() doesn't work properly.", completed, taskCompletionStatus.isCompleted());
    }
}