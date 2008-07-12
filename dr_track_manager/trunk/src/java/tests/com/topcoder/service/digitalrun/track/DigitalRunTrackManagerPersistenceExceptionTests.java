/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;


import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality of <code>DigitalRunTrackManagerPersistenceException</code>. All the constructors and
 * inherit relationship are tested.
 * </p>
 * @author waits
 * @version 1.0
 */
public class DigitalRunTrackManagerPersistenceExceptionTests extends TestCase {
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
     * Tests accuracy of <code>DigitalRunTrackManagerPersistenceException.java(String)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor1_Accuracy() {
        DigitalRunTrackManagerPersistenceException exception = new DigitalRunTrackManagerPersistenceException(MESSAGE);
        assertNotNull("Unable to instantiate DigitalRunTrackManagerPersistenceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>DigitalRunTrackManagerPersistenceException(String, Throwable)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor2_Accuracy() {
        DigitalRunTrackManagerPersistenceException exception = new DigitalRunTrackManagerPersistenceException(MESSAGE,
                CAUSE);
        assertNotNull("Unable to instantiate DigitalRunTrackManagerPersistenceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>DigitalRunTrackManagerPersistenceException(String, ExceptionData)</code>
     * constructor.
     * </p>
     *
     * <p>
     * Verifies the error message and the exceptionData are properly propagated.
     * </p>
     */
    public void testCtor3_Accuracy() {
        DigitalRunTrackManagerPersistenceException exception = new DigitalRunTrackManagerPersistenceException(MESSAGE,
                EXCEPTIONDATA);
        assertNotNull("Unable to instantiate DigitalRunTrackManagerPersistenceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("ExceptionData is not properly propagated to super class.", EXCEPTIONDATA.getErrorCode(),
            exception.getErrorCode());
    }

    /**
     * <p>
     * Tests accuracy of <code>DigitalRunTrackManagerPersistenceException(String, Throwable,
     * ExceptionData)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message, the cause, and the exceptionData are properly propagated.
     * </p>
     */
    public void testCtor4_Accuracy() {
        DigitalRunTrackManagerPersistenceException exception = new DigitalRunTrackManagerPersistenceException(MESSAGE,
                CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate DigitalRunTrackManagerPersistenceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
        assertEquals("ExceptionData is not properly propagated to super class.", EXCEPTIONDATA.getErrorCode(),
            exception.getErrorCode());
    }

    /**
     * <p>
     * Inheritance test, verifies DigitalRunTrackManagerPersistenceException subclasses DigitalRunTrackManagerException.
     * </p>
     */
    public void testInheritance() {
        assertTrue("DigitalRunTrackManagerPersistenceException does not subclass DigitalRunTrackManagerException.",
            new DigitalRunTrackManagerPersistenceException(MESSAGE) instanceof DigitalRunTrackManagerException);
    }
}
