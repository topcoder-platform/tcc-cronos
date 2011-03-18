/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import junit.framework.TestCase;

import com.topcoder.reliability.ProjectCategoryNotSupportedException;
import com.topcoder.reliability.ReliabilityCalculationException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * Accuracy test for <code>ProjectCategoryNotSupportedException</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectCategoryNotSupportedExceptionAccuracy extends TestCase {
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
     * Accuracy test for <code>ProjectCategoryNotSupportedException(String)</code>.
     */
    public void testProjectCategoryNotSupportedException_Str() {
        ProjectCategoryNotSupportedException ex = new ProjectCategoryNotSupportedException(MESSAGE,
                10);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The category should match.", 10, ex.getProjectCategoryId());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>ProjectCategoryNotSupportedException(String, Throwable)</code>.
     */
    public void testProjectCategoryNotSupportedException_Str_Throwable() {
        ProjectCategoryNotSupportedException ex = new ProjectCategoryNotSupportedException(MESSAGE,
                CAUSE, 10);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The cause should match.", CAUSE, ex.getCause());
        assertEquals("The category should match.", 10, ex.getProjectCategoryId());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>ProjectCategoryNotSupportedException(String, ExceptionData)</code>.
     */
    public void testProjectCategoryNotSupportedException_Str_Exp() {
        ProjectCategoryNotSupportedException ex = new ProjectCategoryNotSupportedException(MESSAGE,
                DATA, 10);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The category should match.", 10, ex.getProjectCategoryId());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }

    /**
     * Accuracy test for <code>ProjectCategoryNotSupportedException(String, Throwable, ExceptionData)</code>.
     */
    public void testProjectCategoryNotSupportedException_Str_Throwable_Exp() {
        ProjectCategoryNotSupportedException ex = new ProjectCategoryNotSupportedException(MESSAGE,
                CAUSE, DATA, 10);
        assertNotNull("instance should be created.", ex);
        assertEquals("The message should match.", MESSAGE, ex.getMessage());
        assertEquals("The cause should match.", CAUSE, ex.getCause());
        assertEquals("The category should match.", 10, ex.getProjectCategoryId());
        assertTrue("The instance should extends correct exception.",
            ex instanceof ReliabilityCalculationException);
    }
}
