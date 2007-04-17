/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.entry.time.TaskType;

/**
 * <p>
 * Accuracy Unit test cases for TaskType.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class TaskTypeAccuracyTests extends TestCase {
    /**
     * <p>
     * TaskType instance for testing.
     * </p>
     */
    private TaskType instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new TaskType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
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
        return new TestSuite(TaskTypeAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor TaskType#TaskType() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create TaskType instance.", instance);
    }

    /**
     * <p>
     * Tests TaskType#getCompanyId() for accuracy.
     * </p>
     */
    public void testGetCompanyId() {
        instance.setCompanyId(5);
        assertEquals("Failed to get the company id.", 5, instance.getCompanyId());
    }

    /**
     * <p>
     * Tests TaskType#setCompanyId(long) for accuracy.
     * </p>
     */
    public void testSetCompanyId() {
        instance.setCompanyId(5);
        assertEquals("Failed to set the company id.", 5, instance.getCompanyId());
    }

    /**
     * <p>
     * Tests TaskType#getActive() for accuracy.
     * </p>
     */
    public void testGetActive() {
        instance.setActive(false);
        assertFalse("Failed to get the active.", instance.getActive());
    }

    /**
     * <p>
     * Tests TaskType#isActive() for accuracy.
     * </p>
     */
    public void testIsActive() {
        instance.setActive(false);
        assertFalse("Failed to get the active.", instance.isActive());
    }

    /**
     * <p>
     * Tests TaskType#setActive(boolean) for accuracy.
     * </p>
     */
    public void testSetActive() {
        instance.setActive(false);
        assertFalse("Failed to set the active.", instance.getActive());
    }

    /**
     * <p>
     * Tests TaskType#getDescription() for accuracy.
     * </p>
     */
    public void testGetDescription() {
        instance.setDescription("description");
        assertEquals("Failed to get the description.", "description", instance.getDescription());
    }

    /**
     * <p>
     * Tests TaskType#setDescription(String) for accuracy.
     * </p>
     */
    public void testSetDescription() {
        instance.setDescription("description");
        assertEquals("Failed to set the description.", "description", instance.getDescription());
    }

}