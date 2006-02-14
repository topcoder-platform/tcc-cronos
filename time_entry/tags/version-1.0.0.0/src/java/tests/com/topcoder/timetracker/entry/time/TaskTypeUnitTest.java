/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit test cases for TaskType.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the get and set process.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TaskTypeUnitTest extends TestCase {
    /**
     * <p>
     * TaskType instance for testing.
     * </p>
     */
    private TaskType taskType = null;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(TaskTypeUnitTest.class);
    }

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     */
    protected void setUp() {
        this.taskType = new TaskType();
    }

    /**
     * <p>
     * Tests the constructor - TaskType().
     * </p>
     */
    public void testTaskType() {
        assertNotNull("The constructor does not work well", this.taskType);
    }
}
