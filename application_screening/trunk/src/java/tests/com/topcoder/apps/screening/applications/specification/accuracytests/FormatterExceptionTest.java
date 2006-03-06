/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * FormatterExceptionTest.java
 */
package com.topcoder.apps.screening.applications.specification.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.apps.screening.applications.specification.FormatterException;

/**
 * Set of tests for testing FormatterException class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FormatterExceptionTest extends TestCase {

    private String message = "message";

    private Exception cause = new Exception();

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(FormatterExceptionTest.class);
    }

    /**
     * Accuracy test method for FormatterException(Throwable).
     */
    public final void testFormatterExceptionString() {
        FormatterException exception = new FormatterException(message);
        // checking that message for correctly retrieved message
        assertTrue(exception.getMessage().indexOf(message) != -1);
    }

    /**
     * Accuracy test method for FormatterException(String, Throwable).
     */
    public final void testFormatterExceptionStringThrowable() {
        FormatterException exception = new FormatterException(message,
                cause);
        // testing for equal messages
        assertTrue(exception.getMessage().indexOf(message) != -1);
        // testing for cause
        assertEquals(cause, exception.getCause());
    }
}