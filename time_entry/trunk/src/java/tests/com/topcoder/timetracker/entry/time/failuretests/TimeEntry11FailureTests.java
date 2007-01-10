/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.TimeEntry;

/**
 * <p>
 * Failure tests for TimeEntry.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
public class TimeEntry11FailureTests extends TestCase {
    /**
     * TimeEntry instance for testing.
     */
    private TimeEntry entry;

    /**
     * <p>
     * Set up tests.
     * </p>
     * @throws Exception to JUnit
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        entry = new TimeEntry();
    }

    /**
     * Failure test for TimeEntry.addRejectReason(RejectReason),
     * with null RejectReason, IAE is expected.
     */
    public void testAddRejectReasonNullRejectReason() {
        try {
            entry.addRejectReason(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
