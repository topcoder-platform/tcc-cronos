/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.user.Status;

/**
 * <p>
 * Accuracy Unit test cases for Status.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class StatusAccuracyTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(StatusAccuracyTests.class);
    }

    /**
     * <p>
     * Tests Status#getName() for accuracy.
     * </p>
     */
    public void testGetName() {
        assertEquals("Failed to get the name.", "Active", Status.ACTIVE.getName());
        assertEquals("Failed to get the name.", "Inactive", Status.INACTIVE.getName());
        assertEquals("Failed to get the name.", "Locked", Status.LOCKED.getName());
    }

    /**
     * <p>
     * Tests Status#toString() for accuracy.
     * </p>
     */
    public void testToString() {
        assertEquals("Failed to string.", "Active", Status.ACTIVE.toString());
        assertEquals("Failed to string.", "Inactive", Status.INACTIVE.toString());
        assertEquals("Failed to string.", "Locked", Status.LOCKED.toString());
    }

    /**
     * <p>
     * Tests Status#getId() for accuracy.
     * </p>
     */
    public void testGetId() {
        assertEquals("Failed to get the id.", 1, Status.ACTIVE.getId());
        assertEquals("Failed to get the id.", 0, Status.INACTIVE.getId());
        assertEquals("Failed to get the id.", 3, Status.LOCKED.getId());
    }

}