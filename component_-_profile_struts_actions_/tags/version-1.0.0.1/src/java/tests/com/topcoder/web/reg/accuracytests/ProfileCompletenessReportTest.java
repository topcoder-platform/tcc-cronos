/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.reg.actions.profile.ProfileCompletenessReport;

/**
 * <p>
 * Accuracy tests for the {@link ProfileCompletenessReport}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class ProfileCompletenessReportTest {
    /** Represents the completionPercentage used to test again. */
    private int completionPercentageValue;

    /** Represents the taskCompletionStatuses used to test again. */
    private List taskCompletionStatusesValue;

    /** Represents the instance used to test again. */
    private ProfileCompletenessReport testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ProfileCompletenessReport();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link ProfileCompletenessReport#ProfileCompletenessReport()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testProfileCompletenessReport() throws Exception {
        new ProfileCompletenessReport();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link ProfileCompletenessReport#getCompletionPercentage()}
     * </p>
     * <p>
     * The value of <code>completionPercentage</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCompletionPercentage() throws Exception {
        assertNull("Old value", testInstance.getCompletionPercentage());
    }

    /**
     * <p>
     * Accuracy test {@link ProfileCompletenessReport#setCompletionPercentage(int)}.
     * </p>
     * <p>
     * The value of <code>completionPercentage</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCompletionPercentage() throws Exception {
        testInstance.setCompletionPercentage(completionPercentageValue);
        assertEquals("New value", completionPercentageValue, testInstance.getCompletionPercentage());
    }

    /**
     * <p>
     * Accuracy test for {@link ProfileCompletenessReport#getTaskCompletionStatuses()}
     * </p>
     * <p>
     * The value of <code>taskCompletionStatuses</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getTaskCompletionStatuses() throws Exception {
        assertNull("Old value", testInstance.getTaskCompletionStatuses());
    }

    /**
     * <p>
     * Accuracy test {@link ProfileCompletenessReport#setTaskCompletionStatuses(List)}.
     * </p>
     * <p>
     * The value of <code>taskCompletionStatuses</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setTaskCompletionStatuses() throws Exception {
        testInstance.setTaskCompletionStatuses(taskCompletionStatusesValue);
        assertEquals("New value", taskCompletionStatusesValue, testInstance.getTaskCompletionStatuses());
    }
}