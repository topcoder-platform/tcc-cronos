/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import junit.framework.TestCase;

import com.topcoder.reliability.ReliabilityCalculationException;
import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * Accuracy test for <code>ReliabilityCalculationException</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReliabilityCalculationExceptionAccuracy extends TestCase {
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
     * Accuracy test for <code>ReliabilityCalculationException(String)</code>.
     */
    public void testReliabilityCalculationException_Str() {
        ReliabilityCalculationException ex = new ReliabilityCalculationException(MESSAGE);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertTrue("The instance should extends correct exception.",
            ex instanceof BaseCriticalException);
    }

    /**
     * Accuracy test for <code>ReliabilityCalculationException(String, Throwable)</code>.
     */
    public void testReliabilityCalculationException_Str_Throwable() {
        ReliabilityCalculationException ex = new ReliabilityCalculationException(MESSAGE,
                CAUSE);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The cause should match.", CAUSE, ex.getCause());
        assertTrue("The instance should extends correct exception.",
            ex instanceof BaseCriticalException);
    }

    /**
     * Accuracy test for <code>ReliabilityCalculationException(String, ExceptionData)</code>.
     */
    public void testReliabilityCalculationException_Str_Exp() {
        ReliabilityCalculationException ex = new ReliabilityCalculationException(MESSAGE,
                DATA);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertTrue("The instance should extends correct exception.",
            ex instanceof BaseCriticalException);
    }

    /**
     * Accuracy test for <code>ReliabilityCalculationException(String, Throwable, ExceptionData)</code>.
     */
    public void testReliabilityCalculationException_Str_Throwable_Exp() {
        ReliabilityCalculationException ex = new ReliabilityCalculationException(MESSAGE,
                CAUSE, DATA);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The cause should match.", CAUSE, ex.getCause());
        assertTrue("The instance should extends correct exception.",
            ex instanceof BaseCriticalException);
    }
}
