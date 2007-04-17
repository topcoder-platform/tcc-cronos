/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.timetracker.entry.time.TimeEntry;

import junit.framework.TestCase;


/**
 * <p>
 * Failure test cases for TimeEntry.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class TimeEntryFailureTests extends TestCase {
    /**
     * <p>
     * The TimeEntry instance for testing.
     * </p>
     */
    private TimeEntry instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new TimeEntry();
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
     * Tests TimeEntry#setTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetTaskType() {
        try {
            instance.setTaskType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntry#setInvoiceId(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the invoice id is less than -1 and expects IllegalArgumentException.
     * </p>
     */
    public void testSetInvoiceId() {
        try {
            instance.setInvoiceId(-999);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntry#setStatus(TimeStatus) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatus is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetStatus() {
        try {
            instance.setStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntry#setHours() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the hours id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetHours() {
        try {
            instance.setHours(-999.0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}
