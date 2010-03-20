/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test case of {@link ComponentDependencyExtractorException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentDependencyExtractorExceptionTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String MESSAGE = "the error message";
    /**
     * <p>
     * The exception data used for testing.
     * </p>
     */
    private static final ExceptionData EXCEPTIONDATA = new ExceptionData();
    /**
     * <p>
     * The inner exception for testing.
     * </p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>
     * Test method for
     * {@link ComponentDependencyExtractorException#ComponentDependencyExtractorException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testComponentDependencyExtractorExceptionString() {
        ComponentDependencyExtractorException exception = new ComponentDependencyExtractorException(MESSAGE);
        assertNotNull("Unable to instantiate ComponentDependencyExtractorException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link ComponentDependencyExtractorException#ComponentDependencyExtractorException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testComponentDependencyExtractorExceptionStringThrowable() {
        ComponentDependencyExtractorException exception = new ComponentDependencyExtractorException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ComponentDependencyExtractorException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Test method for
     * {@link ComponentDependencyExtractorException#ComponentDependencyExtractorException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testComponentDependencyExtractorExceptionStringExceptionData() {
        ComponentDependencyExtractorException exception =
            new ComponentDependencyExtractorException(MESSAGE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate ComponentDependencyExtractorException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link ComponentDependencyExtractorException#ComponentDependencyExtractorException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testComponentDependencyExtractorExceptionStringCauseExceptionData() {
        ComponentDependencyExtractorException exception =
            new ComponentDependencyExtractorException(MESSAGE, CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate ComponentDependencyExtractorException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }
}
