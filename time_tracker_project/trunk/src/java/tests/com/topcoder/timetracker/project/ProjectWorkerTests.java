/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import java.util.Date;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for ProjectWorker.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ProjectWorkerTests extends TestCase {
    /**
     * <p>
     * The ProjectWorker instance for testing.
     * </p>
     */
    private ProjectWorker worker;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        worker = new ProjectWorker();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        worker = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectWorkerTests.class);
    }

    /**
     * <p>
     * Tests ctor ProjectWorker#ProjectWorker() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectWorker instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new ProjectWorker instance.", worker);
    }

    /**
     * <p>
     * Tests ProjectWorker#getStartDate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorker#getStartDate() is correct.
     * </p>
     */
    public void testGetStartDate() {
        Date startDate = new Date();
        worker.setStartDate(startDate);
        assertSame("Failed to get the start date correctly.", startDate, worker.getStartDate());
    }

    /**
     * <p>
     * Tests ProjectWorker#setStartDate(Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorker#setStartDate(Date) is correct.
     * </p>
     */
    public void testSetStartDate() {
        Date startDate = new Date();
        worker.setStartDate(startDate);
        assertSame("Failed to set the start date correctly.", startDate, worker.getStartDate());
    }

    /**
     * <p>
     * Tests ProjectWorker#setStartDate(Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when startDate is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetStartDate_NullStartDate() {
        try {
            worker.setStartDate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorker#getEndDate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorker#getEndDate() is correct.
     * </p>
     */
    public void testGetEndDate() {
        Date endDate = new Date();
        worker.setEndDate(endDate);
        assertSame("Failed to get the end date correctly.", endDate, worker.getEndDate());
    }

    /**
     * <p>
     * Tests ProjectWorker#setEndDate(Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorker#setEndDate(Date) is correct.
     * </p>
     */
    public void testSetEndDate() {
        Date endDate = new Date();
        worker.setEndDate(endDate);
        assertSame("Failed to set the end date correctly.", endDate, worker.getEndDate());
    }

    /**
     * <p>
     * Tests ProjectWorker#setEndDate(Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when endDate is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetEndDate_NullEndDate() {
        try {
            worker.setEndDate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorker#getPayRate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorker#getPayRate() is correct.
     * </p>
     */
    public void testGetPayRate() {
        worker.setPayRate(8.0);
        assertEquals("Failed to get the pay rate correctly.", 8.0, worker.getPayRate(), 0.001);
    }

    /**
     * <p>
     * Tests ProjectWorker#setPayRate(double) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorker#setPayRate(double) is correct.
     * </p>
     */
    public void testSetPayRate() {
        worker.setPayRate(8.0);
        assertEquals("Failed to set the pay rate correctly.", 8.0, worker.getPayRate(), 0.001);
    }

    /**
     * <p>
     * Tests ProjectWorker#setPayRate(double) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when payRate is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetPayRate_NegativePayRate() {
        try {
            worker.setPayRate(-8.0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorker#getProjectId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorker#getProjectId() is correct.
     * </p>
     */
    public void testGetProjectId() {
        worker.setProjectId(8);
        assertEquals("Failed to get the project id correctly.", 8, worker.getProjectId());
    }

    /**
     * <p>
     * Tests ProjectWorker#setProjectId(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorker#setProjectId(long) is correct.
     * </p>
     */
    public void testSetProjectId() {
        worker.setProjectId(8);
        assertEquals("Failed to set the project id correctly.", 8, worker.getProjectId());
    }

    /**
     * <p>
     * Tests ProjectWorker#setProjectId(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetProjectId_NegativeProjectId() {
        try {
            worker.setProjectId(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorker#getUserId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorker#getUserId() is correct.
     * </p>
     */
    public void testGetUserId() {
        worker.setUserId(8);
        assertEquals("Failed to get the user id correctly.", 8, worker.getUserId());
    }

    /**
     * <p>
     * Tests ProjectWorker#setUserId(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorker#setUserId(long) is correct.
     * </p>
     */
    public void testSetUserId() {
        worker.setUserId(8);
        assertEquals("Failed to set the user id correctly.", 8, worker.getUserId());
    }

    /**
     * <p>
     * Tests ProjectWorker#setUserId(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetUserId_NegativeUserId() {
        try {
            worker.setUserId(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}