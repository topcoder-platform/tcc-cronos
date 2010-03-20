/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test case of {@link InvalidContestCategoryException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InvalidContestCategoryExceptionTest extends TestCase {
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
     * {@link InvalidContestCategoryException#InvalidContestCategoryException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testInvalidContestCategoryExceptionString() {
        InvalidContestCategoryException exception = new InvalidContestCategoryException(MESSAGE);
        assertNotNull("Unable to instantiate InvalidContestCategoryException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link InvalidContestCategoryException#InvalidContestCategoryException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testInvalidContestCategoryExceptionStringThrowable() {
        InvalidContestCategoryException exception = new InvalidContestCategoryException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate InvalidContestCategoryException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Test method for
     * {@link InvalidContestCategoryException#InvalidContestCategoryException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testInvalidContestCategoryExceptionStringExceptionData() {
        InvalidContestCategoryException exception = new InvalidContestCategoryException(MESSAGE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate InvalidContestCategoryException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link InvalidContestCategoryException#InvalidContestCategoryException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testInvalidContestCategoryExceptionStringCauseExceptionData() {
        InvalidContestCategoryException exception =
            new InvalidContestCategoryException(MESSAGE, CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate InvalidContestCategoryException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }
}
