/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for TimeStatus.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TimeStatusTests extends TestCase {
    /**
     * <p>
     * The TimeStatus instance for testing.
     * </p>
     */
    private TimeStatus timeStatus;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        timeStatus = new TimeStatus();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        timeStatus = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TimeStatusTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeStatus#TimeStatus() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created TimeStatus instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new TimeStatus instance.", timeStatus);
    }

    /**
     * <p>
     * Tests TimeStatus#getDescription() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatus#getDescription() is correct.
     * </p>
     */
    public void testGetDescription() {
        timeStatus.setDescription("description");
        assertEquals("Failed to get the description.", "description", timeStatus.getDescription());
    }

    /**
     * <p>
     * Tests TimeStatus#setDescription(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatus#setDescription(String) is correct.
     * </p>
     */
    public void testSetDescription() {
        timeStatus.setDescription("description");
        assertEquals("Failed to set the description.", "description", timeStatus.getDescription());
    }

    /**
     * <p>
     * Tests TimeStatus#setDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetDescription_NullDescription() {
        try {
            timeStatus.setDescription(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatus#setDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testSetDescription_EmptyDescription() {
        try {
            timeStatus.setDescription(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}