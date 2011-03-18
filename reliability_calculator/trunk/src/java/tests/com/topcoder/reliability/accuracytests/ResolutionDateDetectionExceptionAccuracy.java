/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import junit.framework.TestCase;

import com.topcoder.reliability.ReliabilityCalculationException;
import com.topcoder.reliability.impl.ResolutionDateDetectionException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * Accuracy test for <code>ResolutionDateDetectionException</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResolutionDateDetectionExceptionAccuracy extends TestCase {
    /**
     * The message for testing.
     */
    private static final String MESSAGE = "error message";

    /**
     * The inner cause exception for testing.
     */
    private static final Throwable CAUSE = new Exception("exception");

    /**
     * The inner cause exception for testing.
     */
    private static final ExceptionData DATA = new ExceptionData();

    /**
     * Accuracy test for <code>ResolutionDateDetectionException(String)</code>.
     */
    public void testResolutionDateDetectionException_Str() {
        ResolutionDateDetectionException ex = new ResolutionDateDetectionException(MESSAGE);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>ResolutionDateDetectionException(String, Throwable)</code>.
     */
    public void testResolutionDateDetectionException_Str_Throwable() {
        ResolutionDateDetectionException ex = new ResolutionDateDetectionException(MESSAGE,
                CAUSE);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The cause should match.", CAUSE, ex.getCause());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>ResolutionDateDetectionException(String, ExceptionData)</code>.
     */
    public void testResolutionDateDetectionException_Str_Exp() {
        ResolutionDateDetectionException ex = new ResolutionDateDetectionException(MESSAGE,
                DATA);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>ResolutionDateDetectionException(String, Throwable, ExceptionData)</code>.
     */
    public void testResolutionDateDetectionException_Str_Throwable_Exp() {
        ResolutionDateDetectionException ex = new ResolutionDateDetectionException(MESSAGE,
                CAUSE, DATA);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The cause should match.", CAUSE, ex.getCause());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }
}
