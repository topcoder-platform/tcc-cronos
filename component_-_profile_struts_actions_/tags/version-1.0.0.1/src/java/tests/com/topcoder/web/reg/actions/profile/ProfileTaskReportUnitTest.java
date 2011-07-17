/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import junit.framework.TestCase;

/**
 * This class aggregates all test cases for com.topcoder.web.reg.actions.profile.ProfileTaskReport.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProfileTaskReportUnitTest extends TestCase {

    /** Represents ProfileTaskReport instance to test. */
    private ProfileTaskReport profileTaskReport;

    /**
     * Sets up the test environment.
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        profileTaskReport = new ProfileTaskReport();
    }

    /**
     * Tears down the test environment.
     * @throws Exception if any exception occurred
     */
    public void tearDown() throws Exception {
        profileTaskReport = null;
    }

    /**
     * <p>
     * Tests ProfileTaskReport constructor.
     * </p>
     * <p>
     * ProfileTaskReport instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("ProfileTaskReport instance should be created successfully.", profileTaskReport);
    }

    /**
     * <p>
     * Tests ProfileTaskReport#getTaskName() method.
     * </p>
     * <p>
     * taskName should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetTaskName() {
        String taskName = "test";
        profileTaskReport.setTaskName(taskName);
        assertSame("getTaskName() doesn't work properly.", taskName, profileTaskReport.getTaskName());
    }

    /**
     * <p>
     * Tests ProfileTaskReport#setTaskName(String) method.
     * </p>
     * <p>
     * taskName should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTaskName() {
        String taskName = "test";
        profileTaskReport.setTaskName(taskName);
        assertSame("setTaskName() doesn't work properly.", taskName, profileTaskReport.getTaskName());
    }

    /**
     * <p>
     * Tests ProfileTaskReport#getCompletedFieldCount() method.
     * </p>
     * <p>
     * completedFieldCount should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCompletedFieldCount() {
        int completedFieldCount = 1;
        profileTaskReport.setCompletedFieldCount(completedFieldCount);
        assertEquals("getCompletedFieldCount() doesn't work properly.", completedFieldCount,
                profileTaskReport.getCompletedFieldCount());
    }

    /**
     * <p>
     * Tests ProfileTaskReport#setCompletedFieldCount(int) method.
     * </p>
     * <p>
     * completedFieldCount should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCompletedFieldCount() {
        int completedFieldCount = 1;
        profileTaskReport.setCompletedFieldCount(completedFieldCount);
        assertEquals("setCompletedFieldCount() doesn't work properly.", completedFieldCount,
                profileTaskReport.getCompletedFieldCount());
    }

    /**
     * <p>
     * Tests ProfileTaskReport#getTotalFieldCount() method.
     * </p>
     * <p>
     * totalFieldCount should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetTotalFieldCount() {
        int totalFieldCount = 1;
        profileTaskReport.setTotalFieldCount(totalFieldCount);
        assertEquals("getTotalFieldCount() doesn't work properly.", totalFieldCount,
                profileTaskReport.getTotalFieldCount());
    }

    /**
     * <p>
     * Tests ProfileTaskReport#setTotalFieldCount(int) method.
     * </p>
     * <p>
     * totalFieldCount should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTotalFieldCount() {
        int totalFieldCount = 1;
        profileTaskReport.setTotalFieldCount(totalFieldCount);
        assertEquals("setTotalFieldCount() doesn't work properly.", totalFieldCount,
                profileTaskReport.getTotalFieldCount());
    }

    /**
     * <p>
     * Tests ProfileTaskReport#getCompleted() method.
     * </p>
     * <p>
     * completed should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCompleted() {
        boolean completed = true;
        profileTaskReport.setCompleted(completed);
        assertEquals("getCompleted() doesn't work properly.", completed, profileTaskReport.getCompleted());
    }

    /**
     * <p>
     * Tests ProfileTaskReport#setCompleted(boolean) method.
     * </p>
     * <p>
     * completed should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCompleted() {
        boolean completed = true;
        profileTaskReport.setCompleted(completed);
        assertEquals("setCompleted() doesn't work properly.", completed, profileTaskReport.getCompleted());
    }
}