/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * This class aggregates all test cases for com.topcoder.web.reg.actions.profile.ProfileCompletenessReport.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProfileCompletenessReportTest extends TestCase {

    /** Represents ProfileCompletenessReport instance to test. */
    private ProfileCompletenessReport profileCompletenessReport;

    /**
     * Sets up the test environment.
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        profileCompletenessReport = new ProfileCompletenessReport();
    }

    /**
     * Tears down the test environment.
     * @throws Exception if any exception occurred
     */
    public void tearDown() throws Exception {
        profileCompletenessReport = null;
    }

    /**
     * <p>
     * Tests ProfileCompletenessReport constructor.
     * </p>
     * <p>
     * ProfileCompletenessReport instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("ProfileCompletenessReport instance should be created successfully.", profileCompletenessReport);
    }

    /**
     * <p>
     * Tests ProfileCompletenessReport#getCompletionPercentage() method.
     * </p>
     * <p>
     * completionPercentage should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCompletionPercentage() {
        int completionPercentage = 1;
        profileCompletenessReport.setCompletionPercentage(completionPercentage);
        assertEquals("getCompletionPercentage() doesn't work properly.", completionPercentage,
                profileCompletenessReport.getCompletionPercentage());
    }

    /**
     * <p>
     * Tests ProfileCompletenessReport#setCompletionPercentage(int) method.
     * </p>
     * <p>
     * completionPercentage should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCompletionPercentage() {
        int completionPercentage = 1;
        profileCompletenessReport.setCompletionPercentage(completionPercentage);
        assertEquals("setCompletionPercentage() doesn't work properly.", completionPercentage,
                profileCompletenessReport.getCompletionPercentage());
    }

    /**
     * <p>
     * Tests ProfileCompletenessReport#getTaskCompletionStatuses() method.
     * </p>
     * <p>
     * taskCompletionStatuses should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetTaskCompletionStatuses() {
        List<TaskCompletionStatus> taskCompletionStatuses = new ArrayList<TaskCompletionStatus>();
        profileCompletenessReport.setTaskCompletionStatuses(taskCompletionStatuses);
        assertSame("getTaskCompletionStatuses() doesn't work properly.", taskCompletionStatuses,
                profileCompletenessReport.getTaskCompletionStatuses());
    }

    /**
     * <p>
     * Tests ProfileCompletenessReport#setTaskCompletionStatuses(List) method.
     * </p>
     * <p>
     * taskCompletionStatuses should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTaskCompletionStatuses() {
        List<TaskCompletionStatus> taskCompletionStatuses = new ArrayList<TaskCompletionStatus>();
        profileCompletenessReport.setTaskCompletionStatuses(taskCompletionStatuses);
        assertSame("setTaskCompletionStatuses() doesn't work properly.", taskCompletionStatuses,
                profileCompletenessReport.getTaskCompletionStatuses());
    }
}