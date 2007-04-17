/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for TimeEntry.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TimeEntryTests extends TestCase {
    /**
     * <p>
     * The TimeEntry instance for testing.
     * </p>
     */
    private TimeEntry timeEntry;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        timeEntry = new TimeEntry();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        timeEntry = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TimeEntryTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeEntry#TimeEntry() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created TimeEntry instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new TimeEntry instance.", timeEntry);
    }

    /**
     * <p>
     * Tests TimeEntry#isBillable() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#isBillable() is correct.
     * </p>
     */
    public void testIsBillable() {
        timeEntry.setBillable(true);
        assertTrue("Failed to set the billable.", timeEntry.isBillable());
    }

    /**
     * <p>
     * Tests TimeEntry#getBillable() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#getBillable() is correct.
     * </p>
     */
    public void testGetBillable() {
        timeEntry.setBillable(true);
        assertTrue("Failed to set the billable.", timeEntry.getBillable());
    }

    /**
     * <p>
     * Tests TimeEntry#setBillable(boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#setBillable(boolean) is correct.
     * </p>
     */
    public void testSetBillable() {
        timeEntry.setBillable(true);
        assertTrue("Failed to set the billable.", timeEntry.getBillable());
    }

    /**
     * <p>
     * Tests TimeEntry#getTaskType() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#getTaskType() is correct.
     * </p>
     */
    public void testGetTaskType() {
        TaskType taskType = new TaskType();
        timeEntry.setTaskType(taskType);
        assertSame("Failed to get the task type.", taskType, timeEntry.getTaskType());
    }

    /**
     * <p>
     * Tests TimeEntry#setTaskType(TaskType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#setTaskType(TaskType) is correct.
     * </p>
     */
    public void testSetTaskType() {
        TaskType taskType = new TaskType();
        timeEntry.setTaskType(taskType);
        assertSame("Failed to set the task type.", taskType, timeEntry.getTaskType());
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
    public void testSetTaskType_NullTaskType() {
        try {
            timeEntry.setTaskType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntry#getInvoiceId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#getInvoiceId() is correct.
     * </p>
     */
    public void testGetInvoiceId() {
        timeEntry.setInvoiceId(8);
        assertEquals("Failed to get the invoice id.", 8, timeEntry.getInvoiceId());
    }

    /**
     * <p>
     * Tests TimeEntry#setInvoiceId(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#setInvoiceId(long) is correct.
     * </p>
     */
    public void testSetInvoiceId() {
        timeEntry.setInvoiceId(8);
        assertEquals("Failed to set the invoice id.", 8, timeEntry.getInvoiceId());
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
    public void testSetInvoiceId_LessThan() {
        try {
            timeEntry.setInvoiceId(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntry#getHours() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#getHours() is correct.
     * </p>
     */
    public void testGetHours() {
        timeEntry.setHours(8.0);
        assertEquals("Failed to get the hours.", 8.0, timeEntry.getHours(), 0.01);
    }

    /**
     * <p>
     * Tests TimeEntry#getStatus() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#getStatus() is correct.
     * </p>
     */
    public void testGetStatus() {
        TimeStatus timeStatus = new TimeStatus();
        timeEntry.setStatus(timeStatus);
        assertSame("Failed to get the time status.", timeStatus, timeEntry.getStatus());
    }

    /**
     * <p>
     * Tests TimeEntry#setStatus(TimeStatus) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#setStatus(TimeStatus) is correct.
     * </p>
     */
    public void testSetStatus() {
        TimeStatus timeStatus = new TimeStatus();
        timeEntry.setStatus(timeStatus);
        assertSame("Failed to set the time status.", timeStatus, timeEntry.getStatus());
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
    public void testSetStatus_NullTimeStatus() {
        try {
            timeEntry.setStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntry#setHours(double) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntry#setHours(double) is correct.
     * </p>
     */
    public void testSetHours() {
        timeEntry.setHours(8.0);
        assertEquals("Failed to set the hours.", 8.0, timeEntry.getHours(), 0.01);
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
    public void testSetHours_Negative() {
        try {
            timeEntry.setHours(-8.0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }

    }

}