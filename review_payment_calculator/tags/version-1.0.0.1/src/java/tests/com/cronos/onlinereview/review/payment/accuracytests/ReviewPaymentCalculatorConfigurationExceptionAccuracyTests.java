/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment.accuracytests;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.payment.ReviewPaymentCalculatorConfigurationException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Set of tests for testing ReviewPaymentCalculatorConfigurationException class.
 * @author vilain
 * @version 1.0
 */
public class ReviewPaymentCalculatorConfigurationExceptionAccuracyTests extends TestCase {

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
     * Constructor under test for
     * ReviewPaymentCalculatorConfigurationException(String). Accuracy testing
     * of correctly assigned exception message and data.
     */
    public final void testReviewPaymentCalculatorConfigurationExceptionString() {
        ReviewPaymentCalculatorConfigurationException exception =
            new ReviewPaymentCalculatorConfigurationException(message);
        // testing for equal messages
        assertEquals("messages must be equal", message, exception.getMessage());
    }

    /**
     * Constructor under test for
     * ReviewPaymentCalculatorConfigurationException(String, Throwable).
     * Accuracy testing of correctly assigned exception message and cause.
     */
    public final void testReviewPaymentCalculatorConfigurationExceptionStringThrowable() {
        ReviewPaymentCalculatorConfigurationException exception =
            new ReviewPaymentCalculatorConfigurationException(message, e);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("causes must be equal", e, exception.getCause());
    }

    /**
     * Constructor under test for
     * ReviewPaymentCalculatorConfigurationException(String, ExceptionData).
     * Accuracy testing of correctly assigned exception message and data.
     */
    public final void testReviewPaymentCalculatorConfigurationExceptionStringExceptionData() {
        ReviewPaymentCalculatorConfigurationException exception =
            new ReviewPaymentCalculatorConfigurationException(message, exceptionData);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("exception datas must be equal", exceptionData.getCreationDate(), exception
            .getCreationDate());
    }

    /**
     * Constructor under test for
     * ReviewPaymentCalculatorConfigurationException(String, Throwable,
     * ExceptionData). Accuracy testing of correctly assigned exception message,
     * cause, and data.
     */
    public final void testReviewPaymentCalculatorConfigurationExceptionStringThrowableExceptionData() {
        ReviewPaymentCalculatorConfigurationException exception =
            new ReviewPaymentCalculatorConfigurationException(message, e, exceptionData);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("causes must be equal", e, exception.getCause());
        assertEquals("exception datas must be equal", exceptionData.getCreationDate(), exception
            .getCreationDate());
    }
}