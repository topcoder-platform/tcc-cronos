/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test case of {@link ContestDataRetrieverException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestDataRetrieverExceptionTest extends TestCase {
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
     * {@link ContestDataRetrieverException#ContestDataRetrieverException()}.
     * </p>
     * <p>
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testContestDataRetrieverExceptionString() {
        ContestDataRetrieverException exception = new ContestDataRetrieverException(MESSAGE);
        assertNotNull("Unable to instantiate ContestDataRetrieverException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestDataRetrieverException#ContestDataRetrieverException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testContestDataRetrieverExceptionStringThrowable() {
        ContestDataRetrieverException exception = new ContestDataRetrieverException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ContestDataRetrieverException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestDataRetrieverException#ContestDataRetrieverException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testContestDataRetrieverExceptionStringExceptionData() {
        ContestDataRetrieverException exception = new ContestDataRetrieverException(MESSAGE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate ContestDataRetrieverException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestDataRetrieverException#ContestDataRetrieverException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testContestDataRetrieverExceptionStringCauseExceptionData() {
        ContestDataRetrieverException exception = new ContestDataRetrieverException(MESSAGE, CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate ContestDataRetrieverException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }
}
