/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for Status.
 * </p>
 *
 * @author biotrail
 * @version 3.2
 */
public class StatusTests extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(StatusTests.class);
    }

    /**
     * <p>
     * Tests Status#getName() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Status#getName() is correct.
     * </p>
     */
    public void testGetName() {
        assertEquals("Failed to get the name correctly.", "Active", Status.ACTIVE.getName());
        assertEquals("Failed to get the name correctly.", "Inactive", Status.INACTIVE.getName());
        assertEquals("Failed to get the name correctly.", "Locked", Status.LOCKED.getName());
    }

    /**
     * <p>
     * Tests Status#toString() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Status#toString() is correct.
     * </p>
     */
    public void testToString() {
        assertEquals("Failed to return the value correctly.", "Active", Status.ACTIVE.toString());
        assertEquals("Failed to return the value correctly.", "Inactive", Status.INACTIVE.toString());
        assertEquals("Failed to return the value correctly.", "Locked", Status.LOCKED.toString());
    }

    /**
     * <p>
     * Tests Status#getId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Status#getId() is correct.
     * </p>
     */
    public void testGetId() {
        assertEquals("Failed to get the id correctly.", 1, Status.ACTIVE.getId());
        assertEquals("Failed to get the id correctly.", 0, Status.INACTIVE.getId());
        assertEquals("Failed to get the id correctly.", 3, Status.LOCKED.getId());
    }

}