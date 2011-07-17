/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.reg.actions.profile.ProfileTaskReport;

/**
 * <p>
 * Accuracy tests for the {@link ProfileTaskReport}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class ProfileTaskReportTest {
    /** Represents the completed used to test again. */
    private boolean completedValue;

    /** Represents the completedFieldCount used to test again. */
    private int completedFieldCountValue;

    /** Represents the taskName used to test again. */
    private String taskNameValue;

    /** Represents the totalFieldCount used to test again. */
    private int totalFieldCountValue;

    /** Represents the instance used to test again. */
    private ProfileTaskReport testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ProfileTaskReport();
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
     * Accuracy test for {@link ProfileTaskReport#ProfileTaskReport()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testProfileTaskReport() throws Exception {
        new ProfileTaskReport();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link ProfileTaskReport#getCompleted()}
     * </p>
     * <p>
     * The value of <code>completed</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCompleted() throws Exception {
        assertEquals("Old value", false, testInstance.getCompleted());
    }

    /**
     * <p>
     * Accuracy test {@link ProfileTaskReport#setCompleted(boolean)}.
     * </p>
     * <p>
     * The value of <code>completed</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCompleted() throws Exception {
        testInstance.setCompleted(completedValue);
        assertEquals("New value", completedValue, testInstance.getCompleted());
    }

    /**
     * <p>
     * Accuracy test for {@link ProfileTaskReport#getCompletedFieldCount()}
     * </p>
     * <p>
     * The value of <code>completedFieldCount</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCompletedFieldCount() throws Exception {
        assertEquals("Old value", 0, testInstance.getCompletedFieldCount());
    }

    /**
     * <p>
     * Accuracy test {@link ProfileTaskReport#setCompletedFieldCount(int)}.
     * </p>
     * <p>
     * The value of <code>completedFieldCount</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCompletedFieldCount() throws Exception {
        testInstance.setCompletedFieldCount(completedFieldCountValue);
        assertEquals("New value", completedFieldCountValue, testInstance.getCompletedFieldCount());
    }

    /**
     * <p>
     * Accuracy test for {@link ProfileTaskReport#getTaskName()}
     * </p>
     * <p>
     * The value of <code>taskName</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getTaskName() throws Exception {
        assertNull("Old value", testInstance.getTaskName());
    }

    /**
     * <p>
     * Accuracy test {@link ProfileTaskReport#setTaskName(String)}.
     * </p>
     * <p>
     * The value of <code>taskName</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setTaskName() throws Exception {
        testInstance.setTaskName(taskNameValue);
        assertEquals("New value", taskNameValue, testInstance.getTaskName());
    }

    /**
     * <p>
     * Accuracy test for {@link ProfileTaskReport#getTotalFieldCount()}
     * </p>
     * <p>
     * The value of <code>totalFieldCount</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getTotalFieldCount() throws Exception {
        assertEquals("Old value", 0, testInstance.getTotalFieldCount());
    }

    /**
     * <p>
     * Accuracy test {@link ProfileTaskReport#setTotalFieldCount(int)}.
     * </p>
     * <p>
     * The value of <code>totalFieldCount</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setTotalFieldCount() throws Exception {
        testInstance.setTotalFieldCount(totalFieldCountValue);
        assertEquals("New value", totalFieldCountValue, testInstance.getTotalFieldCount());
    }
}