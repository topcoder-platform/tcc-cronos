/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * <p>
 * Unit test cases for TimeEntry.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the get and set process.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TimeEntryUnitTest extends TestCase {
    /**
     * <p>
     * The Date instance for testing.
     * </p>
     */
    private Date date;

    /**
     * <p>
     * TimeEntry instance for testing.
     * </p>
     */
    private TimeEntry timeEntry = null;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(TimeEntryUnitTest.class);
    }

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     */
    protected void setUp() {
        this.timeEntry = new TimeEntry();
        this.date = new Date();
    }

    /**
     * <p>
     * Tests the constructor - TimeEntry().
     * </p>
     */
    public void testTimeEntry() {
        assertNotNull("The constructor does not work well", this.timeEntry);
    }

    /**
     * <p>
     * Tests the getDate() method.
     * </p>
     */
    public void testGetDate1() {
        timeEntry.setDate(date);
        assertEquals("date is not properly got", date, timeEntry.getDate());
    }

    /**
     * <p>
     * Tests the getDate() method.
     * Confirm the return value is a shallow copy.
     * </p>
     */
    public void testGetDate2() {
        timeEntry.setDate(date);
        Date ret = timeEntry.getDate();
        ret.setTime(1000);
        assertEquals("date is not properly got", date, timeEntry.getDate());
    }


    /**
     * <p>
     * Tests the setDate(Date) method.
     * </p>
     */
    public void testSetDate1() {
        timeEntry.setDate(date);
        assertEquals("date is not properly set", date, timeEntry.getDate());
    }

    /**
     * <p>
     * Tests the setDate(Date) method.
     * Confirm the set value is a shallow copy
     * </p>
     */
    public void testSetDate2() {
        timeEntry.setDate(date);
        Date ret = timeEntry.getDate();
        date.setTime(1000);
        assertEquals("date is not properly set", ret, timeEntry.getDate());
    }
    /**
     * <p>
     * Tests the getHours() method.
     * </p>
     */
    public void testGetHours() {
        assertTrue("hours is not properly got", 0.0F == timeEntry.getHours());
    }

    /**
     * <p>
     * Tests the setHours(float) method.
     * </p>
     */
    public void testSetHours() {
        timeEntry.setHours(0.3F);
        assertTrue("hours is not properly set", 0.3F == timeEntry.getHours());
    }

    /**
     * <p>
     * Tests the getTaskTypeId() method.
     * </p>
     */
    public void testGetTaskTypeId() {
        assertTrue("taskTypeId is not properly got", 0 == timeEntry.getTaskTypeId());
    }

    /**
     * <p>
     * Tests the setTaskTypeId(int) method.
     * </p>
     */
    public void testSetTaskTypeId() {
        timeEntry.setTaskTypeId(3);
        assertEquals("taskTypeId is not properly set", 3, timeEntry.getTaskTypeId());
    }

    /**
     * <p>
     * Tests the getTimeStatusId() method.
     * </p>
     */
    public void testGetTimeStatusId() {
        assertEquals("timeStatusId is not properly got", 0, timeEntry.getTimeStatusId());
    }

    /**
     * <p>
     * Tests the setTimeStatusId(int) method.
     * </p>
     */
    public void testSetTimeStatusId() {
        timeEntry.setTimeStatusId(3);
        assertEquals("timeStatusId is not properly set", 3, timeEntry.getTimeStatusId());
    }

    /**
     * <p>
     * Tests the getBillable() method.
     * </p>
     */
    public void testGetBillable() {
        assertFalse("billable is not properly got", timeEntry.isBillable());
    }

    /**
     * <p>
     * Tests the getBillable(boolean) method.
     * </p>
     */
    public void testSetBillable() {
        timeEntry.setBillable(true);
        assertTrue("billable is not properly set", timeEntry.isBillable());
    }
}
