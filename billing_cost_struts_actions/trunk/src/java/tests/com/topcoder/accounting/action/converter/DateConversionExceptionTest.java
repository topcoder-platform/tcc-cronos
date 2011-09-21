/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link DateConversionException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DateConversionExceptionTest {
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
     * Tests accuracy of <code>DateConversionException(String)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    @Test
    public void testCtor1Accuracy() {
        DateConversionException exception = new DateConversionException(MESSAGE);
        assertNotNull("Unable to instantiate DateConversionException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>DateConversionException(String, Throwable)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    @Test
    public void testCtor2Accuracy() {
        DateConversionException exception = new DateConversionException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate DateConversionException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }


    /**
     * <p>
     * Inheritance test, verifies <code>DateConversionException</code> subclasses
     * <code>RuntimeException</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("DateConversionException does not subclass RuntimeException.",
            DateConversionException.class.getSuperclass() == RuntimeException.class);
    }
}
