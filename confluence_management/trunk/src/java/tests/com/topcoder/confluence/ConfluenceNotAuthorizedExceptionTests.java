/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for the <code>ConfluenceNotAuthorizedException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceNotAuthorizedExceptionTests extends TestCase {

    /**
     * <p>
     * Represents the error message for testing.
     * </p>
     */
    private static final String MESSAGE = "error message";

    /**
     * <p>
     * Represents the <code>Exception</code> instance used for testing.
     * </p>
     */
    private static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Represents the <code>ExceptionData</code> instance used for testing.
     * </p>
     */
    private static final ExceptionData EXCEPTIONDATA = new ExceptionData();

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceNotAuthorizedException(String)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor1Accuracy() {
        ConfluenceNotAuthorizedException exception = new ConfluenceNotAuthorizedException(MESSAGE);
        assertNotNull("Unable to instantiate ConfluenceNotAuthorizedException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceNotAuthorizedException(String, Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        ConfluenceNotAuthorizedException exception = new ConfluenceNotAuthorizedException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ConfluenceNotAuthorizedException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceNotAuthorizedException(String, ExceptionData)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message and the exceptionData are properly propagated.
     * </p>
     */
    public void testCtor3Accuracy() {
        ConfluenceNotAuthorizedException exception = new ConfluenceNotAuthorizedException(MESSAGE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate ConfluenceNotAuthorizedException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("ExceptionData is not properly propagated to super class.", EXCEPTIONDATA.getErrorCode(),
            exception.getErrorCode());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceNotAuthorizedException(String, Throwable, ExceptionData)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message, the cause, and the exceptionData are properly propagated.
     * </p>
     */
    public void testCtor4Accuracy() {
        ConfluenceNotAuthorizedException exception =
            new ConfluenceNotAuthorizedException(MESSAGE, CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate ConfluenceNotAuthorizedException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
        assertEquals("ExceptionData is not properly propagated to super class.", EXCEPTIONDATA.getErrorCode(),
            exception.getErrorCode());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ConfluenceNotAuthorizedException</code> subclasses
     * <code>ConfluenceManagerException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ConfluenceNotAuthorizedException does not subclass ConfluenceManagerException.",
            new ConfluenceNotAuthorizedException(MESSAGE) instanceof ConfluenceManagerException);
    }
}