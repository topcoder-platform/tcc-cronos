/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import junit.framework.TestCase;

import com.topcoder.reliability.ReliabilityCalculationException;
import com.topcoder.reliability.impl.ReliabilityDataPersistenceException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * Accuracy test for <code>ReliabilityDataPersistenceException</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReliabilityDataPersistenceExceptionAccuracy extends TestCase {
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
     * Accuracy test for <code>ReliabilityDataPersistenceException(String)</code>.
     */
    public void testReliabilityDataPersistenceException_Str() {
        ReliabilityDataPersistenceException ex = new ReliabilityDataPersistenceException(MESSAGE);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>ReliabilityDataPersistenceException(String, Throwable)</code>.
     */
    public void testReliabilityDataPersistenceException_Str_Throwable() {
        ReliabilityDataPersistenceException ex = new ReliabilityDataPersistenceException(MESSAGE,
                CAUSE);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The cause should match.", CAUSE, ex.getCause());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>ReliabilityDataPersistenceException(String, ExceptionData)</code>.
     */
    public void testReliabilityDataPersistenceException_Str_Exp() {
        ReliabilityDataPersistenceException ex = new ReliabilityDataPersistenceException(MESSAGE,
                DATA);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>ReliabilityDataPersistenceException(String, Throwable, ExceptionData)</code>.
     */
    public void testReliabilityDataPersistenceException_Str_Throwable_Exp() {
        ReliabilityDataPersistenceException ex = new ReliabilityDataPersistenceException(MESSAGE,
                CAUSE, DATA);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The cause should match.", CAUSE, ex.getCause());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }
}
