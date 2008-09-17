/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.accuracytests;

import com.topcoder.util.dependency.report.DependencyReportGenerationException;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * This is a test case for <code>DependencyReportConfigurationException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DependencyReportGenerationExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String MESSAGE = "the error message";

    /**
     * <p>
     * The ExceptionData used for testing.
     * </p>
     */
    private static final ExceptionData DATA = new ExceptionData();

    /**
     * <p>
     * The inner exception for testing.
     * </p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>
     * Accuracy test for constructor <code>DependencyReportGenerationException(String)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy1() {
        DependencyReportGenerationException exception = new DependencyReportGenerationException(MESSAGE);
        assertNotNull("The created instance should not be null", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DependencyReportGenerationException(String, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy2() {
        DependencyReportGenerationException exception = new DependencyReportGenerationException(MESSAGE, DATA);
        assertNotNull("Unable to create DependencyReportGenerationException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DependencyReportGenerationException(String, Throwable)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy3() {
        DependencyReportGenerationException exception = new DependencyReportGenerationException(MESSAGE, CAUSE);
        assertNotNull("Unable to create DependencyReportGenerationException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>DependencyReportGenerationException(String, Throwable, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy4() {
        DependencyReportGenerationException exception = new DependencyReportGenerationException(MESSAGE, CAUSE,
            DATA);
        assertNotNull("Unable to create DependencyReportGenerationException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>DependencyReportGenerationException(String, Throwable, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully with null arguments.
     * </p>
     */
    public void testCtorAccuracy5() {
        DependencyReportGenerationException exception = new DependencyReportGenerationException(null, null, null);
        assertNotNull("Unable to create DependencyReportGenerationException instance.", exception);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
