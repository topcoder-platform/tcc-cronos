/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>EmailSendingException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EmailSendingExceptionTest extends TestCase {

    /**
     * <p>
     * Represents the error message for testing.
     * </p>
     */
    private static final String MESSAGE = "error message";

    /**
     * <p>
     * Accuracy test for the constructor <code>EmailSendingException(String)</code>, expects the instance is created
     * properly.
     * </p>
     */
    public void testCtor1Accuracy() {
        EmailSendingException exception = new EmailSendingException(MESSAGE);

        assertNotNull("Failed to create the EmailSendingException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>EmailSendingException(String, Throwable)</code>, expects the instance
     * is created properly.
     * </p>
     */
    public void testCtor2Accuracy() {
        Throwable cause = new Throwable();
        EmailSendingException exception = new EmailSendingException(MESSAGE, cause);

        assertNotNull("Failed to create the EmailSendingException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the cause is propagated properly.", cause, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>EmailSendingException(String, ExceptionData)</code> , expects the
     * instance is created properly.
     * </p>
     */
    public void testCtor3Accuracy() {
        ExceptionData data = new ExceptionData();
        EmailSendingException exception = new EmailSendingException(MESSAGE, data);

        assertNotNull("Failed to create the EmailSendingException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the exception data is propagated properly.", data.getErrorCode(), exception
                .getErrorCode());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>EmailSendingException(String, Throwable, ExceptionData)</code>, expects
     * the instance is created properly.
     * </p>
     */
    public void testCtor4Accuracy() {
        Throwable cause = new Throwable();
        ExceptionData data = new ExceptionData();
        EmailSendingException exception = new EmailSendingException(MESSAGE, cause, data);

        assertNotNull("Failed to create the EmailSendingException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the cause is propagated properly.", cause, exception.getCause());
        assertEquals("Expects the exception data is propagated properly.", data.getErrorCode(), exception
                .getErrorCode());
    }

    /**
     * <p>
     * Inheritance test, expects <code>EmailSendingException</code> subclasses
     * <code>DirectServiceFacadeException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("Expects EmailSendingException subclass DirectServiceFacadeException.", new EmailSendingException(
                MESSAGE) instanceof DirectServiceFacadeException);
    }
}