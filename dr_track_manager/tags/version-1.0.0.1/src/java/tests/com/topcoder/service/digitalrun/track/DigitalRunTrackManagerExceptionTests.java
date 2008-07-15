/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality of <code>DigitalRunTrackManagerException</code>. All the constructors and
 * inherit relationship are tested.
 * </p>
 * @author waits
 * @version 1.0
 */
public class DigitalRunTrackManagerExceptionTests extends TestCase {
    /**
     * <p>
     * Represents the error message for testing.
     * </p>
     */
    private static final String MESSAGE = "error message";

    /**
     * <p>
     * Represents the <code>Exception</code> instance used for testing.
     * </p>
     */
    private static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Represents the <code>ExceptionData</code> instance used for testing.
     * </p>
     */
    private static final ExceptionData EXCEPTIONDATA = new ExceptionData();

    /**
     * <p>
     * Tests accuracy of <code>DigitalRunTrackManagerException(String)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor1_Accuracy() {
        DigitalRunTrackManagerException exception = new DigitalRunTrackManagerException(MESSAGE);
        assertNotNull("Unable to instantiate DigitalRunTrackManagerException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>DigitalRunTrackManagerException(String, Throwable)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor2_Accuracy() {
        DigitalRunTrackManagerException exception = new DigitalRunTrackManagerException(MESSAGE,
                CAUSE);
        assertNotNull("Unable to instantiate DigitalRunTrackManagerException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>DigitalRunTrackManagerException(String, ExceptionData)</code>
     * constructor.
     * </p>
     *
     * <p>
     * Verifies the error message and the exceptionData are properly propagated.
     * </p>
     */
    public void testCtor3_Accuracy() {
        DigitalRunTrackManagerException exception = new DigitalRunTrackManagerException(MESSAGE,
                EXCEPTIONDATA);
        assertNotNull("Unable to instantiate DigitalRunTrackManagerException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("ExceptionData is not properly propagated to super class.", EXCEPTIONDATA.getErrorCode(),
            exception.getErrorCode());
    }

    /**
     * <p>
     * Tests accuracy of <code>DigitalRunTrackManagerException(String, Throwable,
     * ExceptionData)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message, the cause, and the exceptionData are properly propagated.
     * </p>
     */
    public void testCtor4_Accuracy() {
        DigitalRunTrackManagerException exception = new DigitalRunTrackManagerException(MESSAGE,
                CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate DigitalRunTrackManagerException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
        assertEquals("ExceptionData is not properly propagated to super class.", EXCEPTIONDATA.getErrorCode(),
            exception.getErrorCode());
    }

    /**
     * <p>
     * Inheritance test, verifies DigitalRunTrackManagerException subclasses BaseCriticalException.
     * </p>
     */
    public void testInheritance() {
        assertTrue("DigitalRunTrackManagerException does not subclass BaseCriticalException.",
            new DigitalRunTrackManagerException(MESSAGE) instanceof BaseCriticalException);
    }
}
