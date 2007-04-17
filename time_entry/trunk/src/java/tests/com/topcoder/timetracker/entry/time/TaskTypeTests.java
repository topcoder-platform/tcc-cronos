/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for TaskType.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TaskTypeTests extends TestCase {
    /**
     * <p>
     * The TaskType instance for testing.
     * </p>
     */
    private TaskType taskType;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        taskType = new TaskType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        taskType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TaskTypeTests.class);
    }

    /**
     * <p>
     * Tests ctor TaskType#TaskType() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created TaskType instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new TaskType instance.", taskType);
    }

    /**
     * <p>
     * Tests TaskType#getCompanyId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskType#getCompanyId() is correct.
     * </p>
     */
    public void testGetCompanyId() {
        taskType.setCompanyId(8);
        assertEquals("Failed to get the company id.", 8, taskType.getCompanyId());
    }

    /**
     * <p>
     * Tests TaskType#setCompanyId(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskType#setCompanyId(long) is correct.
     * </p>
     */
    public void testSetCompanyId() {
        taskType.setCompanyId(8);
        assertEquals("Failed to set the company id.", 8, taskType.getCompanyId());
    }

    /**
     * <p>
     * Tests TaskType#setCompanyId(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when companyId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetCompanyId_Negative() {
        try {
            taskType.setCompanyId(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskType#getActive() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskType#getActive() is correct.
     * </p>
     */
    public void testGetActive() {
        taskType.setActive(true);
        assertTrue("Failed to get the active.", taskType.getActive());
    }

    /**
     * <p>
     * Tests TaskType#isActive() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskType#isActive() is correct.
     * </p>
     */
    public void testIsActive() {
        taskType.setActive(true);
        assertTrue("Failed to get the active.", taskType.isActive());
    }

    /**
     * <p>
     * Tests TaskType#setActive(boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskType#setActive(boolean) is correct.
     * </p>
     */
    public void testSetActive() {
        taskType.setActive(true);
        assertTrue("Failed to set the active.", taskType.getActive());
    }

    /**
     * <p>
     * Tests TaskType#getDescription() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskType#getDescription() is correct.
     * </p>
     */
    public void testGetDescription() {
        taskType.setDescription("description");
        assertEquals("Failed to get the description.", "description", taskType.getDescription());
    }

    /**
     * <p>
     * Tests TaskType#setDescription(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TaskType#setDescription(String) is correct.
     * </p>
     */
    public void testSetDescription() {
        taskType.setDescription("description");
        assertEquals("Failed to set the description.", "description", taskType.getDescription());
    }

    /**
     * <p>
     * Tests TaskType#setDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetDescription_NullDescription() {
        try {
            taskType.setDescription(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskType#setDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testSetDescription_EmptyDescription() {
        try {
            taskType.setDescription(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}