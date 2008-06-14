/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.accuracytests;

import com.topcoder.util.dependency.report.CircularComponentDependencyException;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * This is a test case for <code>CircularComponentDependencyException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CircularComponentDependencyExceptionAccuracyTest extends TestCase {

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
     * Accuracy test for constructor <code>CircularComponentDependencyException(String)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy1() {
        CircularComponentDependencyException exception = new CircularComponentDependencyException(MESSAGE);
        assertNotNull("Unable to create CircularComponentDependencyException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>CircularComponentDependencyException(String)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorAccuracy2() {
        CircularComponentDependencyException exception = new CircularComponentDependencyException((String) null);
        assertNotNull("Unable to create CircularComponentDependencyException instance.", exception);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>CircularComponentDependencyException(String, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy3() {
        CircularComponentDependencyException exception = new CircularComponentDependencyException(MESSAGE, DATA);
        assertNotNull("Unable to create CircularComponentDependencyException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>CircularComponentDependencyException(String, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully with null message and exception data.
     * </p>
     */
    public void testCtorAccuracy4() {
        CircularComponentDependencyException exception = new CircularComponentDependencyException(null,
            (ExceptionData) null);
        assertNotNull("Unable to create CircularComponentDependencyException instance.", exception);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>CircularComponentDependencyException(String, Throwable)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy5() {
        CircularComponentDependencyException exception = new CircularComponentDependencyException(MESSAGE, CAUSE);
        assertNotNull("Unable to create CircularComponentDependencyException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>CircularComponentDependencyException(String, Throwable)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorAccuracy6() {
        CircularComponentDependencyException exception = new CircularComponentDependencyException(null,
            (Throwable) null);
        assertNotNull("Unable to create CircularComponentDependencyException instance.", exception);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>CircularComponentDependencyException(String, Throwable, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy7() {
        CircularComponentDependencyException exception = new CircularComponentDependencyException(MESSAGE, CAUSE,
            DATA);
        assertNotNull("Unable to create CircularComponentDependencyException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>CircularComponentDependencyException(String, Throwable, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully with null arguments.
     * </p>
     */
    public void testCtorAccuracy8() {
        CircularComponentDependencyException exception = new CircularComponentDependencyException(null, null, null);
        assertNotNull("Unable to create CircularComponentDependencyException instance.", exception);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
