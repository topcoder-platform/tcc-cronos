/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import hr.leads.services.jobs.TogglePipelineIdentificationCycleJobExecutionException;
import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for <code>TogglePipelineIdentificationCycleJobExecutionException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleJobExecutionExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String MESSAGE = "the error message";

    /**
     * <p>
     * The inner exception for testing.
     * </p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleJobExecutionException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        TogglePipelineIdentificationCycleJobExecutionException exception = new TogglePipelineIdentificationCycleJobExecutionException(
            MESSAGE);
        assertNotNull(
            "Unable to create TogglePipelineIdentificationCycleJobExecutionException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleJobExecutionException should be instance of BaseCriticalException.",
            exception instanceof Exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleJobExecutionException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        TogglePipelineIdentificationCycleJobExecutionException exception = new TogglePipelineIdentificationCycleJobExecutionException(
            (String) null);
        assertNotNull(
            "Unable to create TogglePipelineIdentificationCycleJobExecutionException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleJobExecutionException should be instance of Exception.",
            exception instanceof Exception);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleJobExecutionException(String, ExceptionData)</code>
     * .
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrExp() {
        TogglePipelineIdentificationCycleJobExecutionException exception = new TogglePipelineIdentificationCycleJobExecutionException(
            MESSAGE, null);
        assertNotNull(
            "Unable to create TogglePipelineIdentificationCycleJobExecutionException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleJobExecutionException should be instance of Exception.",
            exception instanceof Exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleJobExecutionException(String, ExceptionData)</code>
     * .
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and exception
     * data.
     * </p>
     */
    public void testCtorStrExp_Null() {
        TogglePipelineIdentificationCycleJobExecutionException exception = new TogglePipelineIdentificationCycleJobExecutionException(
            null, null);
        assertNotNull(
            "Unable to create TogglePipelineIdentificationCycleJobExecutionException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleJobExecutionException should be instance of Exception.",
            exception instanceof Exception);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleJobExecutionException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        TogglePipelineIdentificationCycleJobExecutionException exception = new TogglePipelineIdentificationCycleJobExecutionException(
            MESSAGE, CAUSE);
        assertNotNull(
            "Unable to create TogglePipelineIdentificationCycleJobExecutionException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleJobExecutionException should be instance of Exception.",
            exception instanceof Exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleJobExecutionException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        TogglePipelineIdentificationCycleJobExecutionException exception = new TogglePipelineIdentificationCycleJobExecutionException(
            null, (Throwable) null);
        assertNotNull(
            "Unable to create TogglePipelineIdentificationCycleJobExecutionException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleJobExecutionException should be instance of Exception.",
            exception instanceof Exception);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

}
