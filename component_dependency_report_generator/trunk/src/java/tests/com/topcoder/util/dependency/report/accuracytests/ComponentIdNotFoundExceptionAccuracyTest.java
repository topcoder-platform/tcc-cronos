/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.accuracytests;

import com.topcoder.util.dependency.report.ComponentIdNotFoundException;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * This is a test case for <code>ComponentIdNotFoundException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentIdNotFoundExceptionAccuracyTest extends TestCase {

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
     * Accuracy test for constructor <code>ComponentIdNotFoundException(String)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy1() {
        ComponentIdNotFoundException exception = new ComponentIdNotFoundException(MESSAGE);
        assertNotNull("Unable to create ComponentIdNotFoundException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ComponentIdNotFoundException(String, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy2() {
        ComponentIdNotFoundException exception = new ComponentIdNotFoundException(MESSAGE, DATA);
        assertNotNull("Unable to create ComponentIdNotFoundException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ComponentIdNotFoundException(String, Throwable)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy3() {
        ComponentIdNotFoundException exception = new ComponentIdNotFoundException(MESSAGE, CAUSE);
        assertNotNull("Unable to create ComponentIdNotFoundException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>ComponentIdNotFoundException(String, Throwable, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorAccuracy4() {
        ComponentIdNotFoundException exception = new ComponentIdNotFoundException(MESSAGE, CAUSE, DATA);
        assertNotNull("Unable to create ComponentIdNotFoundException instance.", exception);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>ComponentIdNotFoundException(String, Throwable, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully with null arguments.
     * </p>
     */
    public void testCtorAccuracy5() {
        ComponentIdNotFoundException exception = new ComponentIdNotFoundException(null, null, null);
        assertNotNull("Unable to create ComponentIdNotFoundException instance.", exception);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
