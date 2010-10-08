/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for <code>ProjectNotFoundException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectNotFoundExceptionUnitTests extends TestCase {
    /**
     * The error message used for testing.
     * */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * The application code used in the ExceptionData.
     */
    private static final String APP_CODE = "ProjectNotFoundExceptionUnitTests";

    /**
     * An exception instance used to create the ProjectNotFoundException.
     */
    private final Exception cause = new Exception("Exception");

    /**
     * The Exception data used as fixture.
     */
    private final ExceptionData data = new ExceptionData();

    /**
     * The projectId used as fixture.
     */
    private final long projectId = 1L;

    /**
     * <p>
     * Sets up exception data.
     * </p>
     */
    protected void setUp() {
        data.setApplicationCode(APP_CODE);
    }

    /**
     * Tests constructor1: ProjectNotFoundException(String, long), with correct message. The message can be retrieved
     * correctly later.
     */
    public void test_Ctor1() {
        ProjectNotFoundException ex = new ProjectNotFoundException(ERROR_MESSAGE, projectId);
        assertTrue("Unable to instantiate ProjectNotFoundException.", ex instanceof StatisticsCalculatorException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertNull("Exception data is incorrect", ex.getApplicationCode());
        assertEquals("Project Id is not correct.", projectId, ex.getProjectId());
    }

    /**
     * Tests constructor1: ProjectNotFoundException(String, long), with null message. No exception is expected.
     */
    public void test_Ctor1_WithNullMessage() {
        // No exception
        new ProjectNotFoundException((String) null, projectId);
    }

    /**
     * Tests constructor1: ProjectNotFoundException(String, long), with empty message. No exception is expected.
     */
    public void test_Ctor1_WithEmptyMessage() {
        // No exception
        new ProjectNotFoundException("", projectId);
    }

    /**
     * Tests constructor1: ProjectNotFoundException(String, long), with trimmed empty message. No exception is
     * expected.
     */
    public void test_Ctor1_WithTrimmedEmptyMessage() {
        // No exception
        new ProjectNotFoundException("    ", projectId);
    }

    /**
     * Tests constructor2: ProjectNotFoundException(String, Throwable, long), with correct cause. The cause can be
     * retrieved correctly later.
     */
    public void test_Ctor2() {
        ProjectNotFoundException ex = new ProjectNotFoundException(ERROR_MESSAGE, cause, projectId);

        assertNotNull("Unable to instantiate WidgetServiceHandlingException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertEquals("The inner exception should match.", cause, ex.getCause());
        assertEquals("Project Id is not correct.", projectId, ex.getProjectId());
    }

    /**
     * Tests constructor2: ProjectNotFoundException(Throwable, long), with null inner exception. No exception is
     * expected.
     */
    public void test_Ctor2_WithNullCause() {
        // No exception
        new ProjectNotFoundException(ERROR_MESSAGE, (Throwable) null, projectId);
    }

    /**
     * Tests constructor3: ProjectNotFoundException(String, ExceptionData, long), with correct error message and data.
     * The message and data can be retrieved correctly later.
     */
    public void test_Ctor3() {
        ProjectNotFoundException ex = new ProjectNotFoundException(ERROR_MESSAGE, data, projectId);

        assertNotNull("Unable to instantiate ProjectNotFoundException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertNull("The inner exception should match.", ex.getCause());
        assertEquals("Exception data is incorrect", APP_CODE, ex.getApplicationCode());
        assertEquals("Project Id is not correct.", projectId, ex.getProjectId());
    }

    /**
     * Tests constructor3: ProjectNotFoundException(String, ExceptionData, long), with empty message. No exception is
     * expected.
     */
    public void test_Ctor3_WithEmptyMessage() {
        // No exception
        new ProjectNotFoundException("", data, projectId);
    }

    /**
     * Tests constructor3: ProjectNotFoundException(String, ExceptionData, long), with trimmed empty message. No
     * exception is expected.
     */
    public void test_Ctor3_WithTrimmedEmptyMessage() {
        // No exception
        new ProjectNotFoundException("  ", data, projectId);
    }

    /**
     * Tests constructor3: ProjectNotFoundException(String, ExceptionData, long), with null message. No exception is
     * expected.
     */
    public void test_Ctor3_WithNullMessage() {
        // No exception
        new ProjectNotFoundException((String) null, data, projectId);
    }

    /**
     * Tests constructor3: ProjectNotFoundException(String, ExceptionData, long), with null data. No exception is
     * expected.
     */
    public void test_Ctor3_WithNullExceptionData() {
        // No exception
        new ProjectNotFoundException(ERROR_MESSAGE, (ExceptionData) null, projectId);
    }

    /**
     * Tests constructor4: ProjectNotFoundException(String, Throwable, ExceptionData, long), with correct cause and
     * data. The cause and data can be retrieved correctly later.
     */
    public void test_Ctor4() {
        ProjectNotFoundException ex = new ProjectNotFoundException(ERROR_MESSAGE, cause, data, projectId);

        assertNotNull("Unable to instantiate ProjectNotFoundException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertEquals("The inner exception should match.", cause, ex.getCause());
        assertEquals("Exception data is incorrect", APP_CODE, ex.getApplicationCode());
        assertEquals("Project Id is not correct.", projectId, ex.getProjectId());
    }

    /**
     * Tests constructor4: ProjectNotFoundException(String, Throwable, ExceptionData, long), with null cause. No
     * exception is expected.
     */
    public void test_Ctor4_WithNullCause() {
        // No exception
        new ProjectNotFoundException(ERROR_MESSAGE, (Throwable) null, data, projectId);
    }

    /**
     * Tests constructor4: ProjectNotFoundException(String, Throwable, ExceptionData, long), with null data. No
     * exception is expected.
     */
    public void test_Ctor4_WithNullExceptionData() {
        // No exception
        new ProjectNotFoundException(ERROR_MESSAGE, cause, (ExceptionData) null, projectId);
    }
}
