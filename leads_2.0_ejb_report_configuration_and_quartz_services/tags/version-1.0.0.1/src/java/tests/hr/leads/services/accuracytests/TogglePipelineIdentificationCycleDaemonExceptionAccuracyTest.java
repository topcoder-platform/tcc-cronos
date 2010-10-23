/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import hr.leads.services.jobs.TogglePipelineIdentificationCycleDaemonException;
import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for <code>TogglePipelineIdentificationCycleDaemonException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleDaemonExceptionAccuracyTest extends TestCase {

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
     * <code>TogglePipelineIdentificationCycleDaemonException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        TogglePipelineIdentificationCycleDaemonException exception = new TogglePipelineIdentificationCycleDaemonException(
            MESSAGE);
        assertNotNull("Unable to create TogglePipelineIdentificationCycleDaemonException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleDaemonException should be instance of BaseCriticalException.",
            exception instanceof Exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleDaemonException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        TogglePipelineIdentificationCycleDaemonException exception = new TogglePipelineIdentificationCycleDaemonException(
            (String) null);
        assertNotNull("Unable to create TogglePipelineIdentificationCycleDaemonException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleDaemonException should be instance of Exception.",
            exception instanceof Exception);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleDaemonException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrExp() {
        TogglePipelineIdentificationCycleDaemonException exception = new TogglePipelineIdentificationCycleDaemonException(
            MESSAGE, null);
        assertNotNull("Unable to create TogglePipelineIdentificationCycleDaemonException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleDaemonException should be instance of Exception.",
            exception instanceof Exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleDaemonException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and exception
     * data.
     * </p>
     */
    public void testCtorStrExp_Null() {
        TogglePipelineIdentificationCycleDaemonException exception = new TogglePipelineIdentificationCycleDaemonException(
            null, null);
        assertNotNull("Unable to create TogglePipelineIdentificationCycleDaemonException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleDaemonException should be instance of Exception.",
            exception instanceof Exception);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleDaemonException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        TogglePipelineIdentificationCycleDaemonException exception = new TogglePipelineIdentificationCycleDaemonException(
            MESSAGE, CAUSE);
        assertNotNull("Unable to create TogglePipelineIdentificationCycleDaemonException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleDaemonException should be instance of Exception.",
            exception instanceof Exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>TogglePipelineIdentificationCycleDaemonException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        TogglePipelineIdentificationCycleDaemonException exception = new TogglePipelineIdentificationCycleDaemonException(
            null, (Throwable) null);
        assertNotNull("Unable to create TogglePipelineIdentificationCycleDaemonException instance.",
            exception);
        assertTrue(
            "The TogglePipelineIdentificationCycleDaemonException should be instance of Exception.",
            exception instanceof Exception);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

}
