/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment.accuracytests;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.payment.InvalidReviewersStatisticsException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Set of tests for testing InvalidReviewersStatisticsException class.
 * @author vilain
 * @version 1.0
 */
public class InvalidReviewersStatisticsExceptionAccuracyTests extends TestCase {

    /**
     * Exception message for testing.
     */
    private String message = "message";

    /**
     * Exception for testing.
     */
    private Exception e = new Exception();

    /**
     * Instance of ExceptionData for testing.
     */
    private ExceptionData exceptionData = new ExceptionData();

    /**
     * Constructor under test for InvalidReviewersStatisticsException(String).
     * Accuracy testing of correctly assigned exception message and data.
     */
    public final void testInvalidReviewersStatisticsExceptionString() {
        InvalidReviewersStatisticsException exception = new InvalidReviewersStatisticsException(message);
        // testing for equal messages
        assertEquals("messages must be equal", message, exception.getMessage());
    }

    /**
     * Constructor under test for InvalidReviewersStatisticsException(String,
     * Throwable). Accuracy testing of correctly assigned exception message and
     * cause.
     */
    public final void testInvalidReviewersStatisticsExceptionStringThrowable() {
        InvalidReviewersStatisticsException exception = new InvalidReviewersStatisticsException(message, e);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("causes must be equal", e, exception.getCause());
    }

    /**
     * Constructor under test for InvalidReviewersStatisticsException(String,
     * ExceptionData). Accuracy testing of correctly assigned exception message
     * and data.
     */
    public final void testInvalidReviewersStatisticsExceptionStringExceptionData() {
        InvalidReviewersStatisticsException exception =
            new InvalidReviewersStatisticsException(message, exceptionData);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("exception datas must be equal", exceptionData.getCreationDate(), exception
            .getCreationDate());
    }

    /**
     * Constructor under test for InvalidReviewersStatisticsException(String,
     * Throwable, ExceptionData). Accuracy testing of correctly assigned
     * exception message, cause, and data.
     */
    public final void testInvalidReviewersStatisticsExceptionStringThrowableExceptionData() {
        InvalidReviewersStatisticsException exception =
            new InvalidReviewersStatisticsException(message, e, exceptionData);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("causes must be equal", e, exception.getCause());
        assertEquals("exception datas must be equal", exceptionData.getCreationDate(), exception
            .getCreationDate());
    }
}