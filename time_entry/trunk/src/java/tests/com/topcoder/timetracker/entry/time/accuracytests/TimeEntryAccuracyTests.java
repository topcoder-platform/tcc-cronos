/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeStatus;

/**
 * <p>
 * Accuracy Unit test cases for TimeEntry.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class TimeEntryAccuracyTests extends TestCase {
    /**
     * <p>
     * TimeEntry instance for testing.
     * </p>
     */
    private TimeEntry instance;

    /**
     * <p>
     * Setup test environment.
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
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TimeEntryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeEntry#TimeEntry() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create TimeEntry instance.", instance);
    }

    /**
     * <p>
     * Tests TimeEntry#getTaskType() for accuracy.
     * </p>
     */
    public void testGetTaskType() {
        TaskType taskType = new TaskType();
        instance.setTaskType(taskType);
        assertSame("Failed to get the task type.", taskType, instance.getTaskType());
    }

    /**
     * <p>
     * Tests TimeEntry#isBillable() for accuracy.
     * </p>
     */
    public void testIsBillable() {
        instance.setBillable(false);
        assertFalse("Failed to get the billable.", instance.isBillable());
    }

    /**
     * <p>
     * Tests TimeEntry#getBillable() for accuracy.
     * </p>
     */
    public void testGetBillable() {
        instance.setBillable(false);
        assertFalse("Failed to get the billable.", instance.isBillable());
    }

    /**
     * <p>
     * Tests TimeEntry#setBillable(boolean) for accuracy.
     * </p>
     */
    public void testSetBillable() {
        instance.setBillable(false);
        assertFalse("Failed to set the billable.", instance.isBillable());
    }

    /**
     * <p>
     * Tests TimeEntry#setTaskType(TaskType) for accuracy.
     * </p>
     */
    public void testSetTaskType() {
        TaskType taskType = new TaskType();
        instance.setTaskType(taskType);
        assertSame("Failed to set the task type.", taskType, instance.getTaskType());
    }

    /**
     * <p>
     * Tests TimeEntry#getInvoiceId() for accuracy.
     * </p>
     */
    public void testGetInvoiceId() {
        instance.setInvoiceId(5);
        assertEquals("Failed to get the invoice id.", 5, instance.getInvoiceId());
    }

    /**
     * <p>
     * Tests TimeEntry#setInvoiceId(long) for accuracy.
     * </p>
     */
    public void testSetInvoiceId() {
        instance.setInvoiceId(5);
        assertEquals("Failed to set the invoice id.", 5, instance.getInvoiceId());
    }

    /**
     * <p>
     * Tests TimeEntry#getHours() for accuracy.
     * </p>
     */
    public void testGetHours() {
        instance.setHours(5.0);
        assertEquals("Failed to get the hours.", 5.0, instance.getHours(), 0.01);
    }

    /**
     * <p>
     * Tests TimeEntry#getStatus() for accuracy.
     * </p>
     */
    public void testGetStatus() {
        TimeStatus timeStatus = new TimeStatus();
        instance.setStatus(timeStatus);
        assertSame("Failed to get the time status.", timeStatus, instance.getStatus());
    }

    /**
     * <p>
     * Tests TimeEntry#setStatus(TimeStatus) for accuracy.
     * </p>
     */
    public void testSetStatus() {
        TimeStatus timeStatus = new TimeStatus();
        instance.setStatus(timeStatus);
        assertSame("Failed to set the time status.", timeStatus, instance.getStatus());
    }

    /**
     * <p>
     * Tests TimeEntry#setHours(double) for accuracy.
     * </p>
     */
    public void testSetHours() {
        instance.setHours(5.0);
        assertEquals("Failed to set the hours.", 5.0, instance.getHours(), 0.01);
    }

}