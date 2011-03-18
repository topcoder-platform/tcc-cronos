/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import junit.framework.TestCase;

import com.topcoder.reliability.ReliabilityCalculationException;
import com.topcoder.reliability.impl.UserReliabilityCalculationException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * Accuracy test for <code>UserReliabilityCalculationException</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserReliabilityCalculationExceptionAccuracy extends TestCase {
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
     * Accuracy test for <code>UserReliabilityCalculationException(String)</code>.
     */
    public void testUserReliabilityCalculationException_Str() {
        UserReliabilityCalculationException ex = new UserReliabilityCalculationException(MESSAGE);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>UserReliabilityCalculationException(String, Throwable)</code>.
     */
    public void testUserReliabilityCalculationException_Str_Throwable() {
        UserReliabilityCalculationException ex = new UserReliabilityCalculationException(MESSAGE,
                CAUSE);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The cause should match.", CAUSE, ex.getCause());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>UserReliabilityCalculationException(String, ExceptionData)</code>.
     */
    public void testUserReliabilityCalculationException_Str_Exp() {
        UserReliabilityCalculationException ex = new UserReliabilityCalculationException(MESSAGE,
                DATA);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>UserReliabilityCalculationException(String, Throwable, ExceptionData)</code>.
     */
    public void testUserReliabilityCalculationException_Str_Throwable_Exp() {
        UserReliabilityCalculationException ex = new UserReliabilityCalculationException(MESSAGE,
                CAUSE, DATA);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The cause should match.", CAUSE, ex.getCause());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }
}
