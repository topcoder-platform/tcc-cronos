/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * FormatterExceptionTest.java
 */
package com.topcoder.apps.screening.applications.specification;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>FormatterException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FormatterExceptionTest extends TestCase {

    /**Message.*/
    private static final String MESSAGE = "message";

    /**Cause.*/
    private static final Exception CAUSE = new IllegalArgumentException();

    /**FormatterException instance that will be tested.*/
    private FormatterException exception;

    /**
     * <p>
     * Set up environment.
     * </p>
     */
    public void setUp() {
        exception = new FormatterException(MESSAGE, CAUSE);
    }

    /**
     * <p>
     * Tests constructor FormatterException(message).
     * </p>
     */
    public void testCtor1() {
        exception = new FormatterException(MESSAGE);
        assertTrue(exception instanceof Exception);
        assertEquals(MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests constructor FormatterException(message, cause).
     * </p>
     */
    public void testCtor2() {
        assertTrue(exception instanceof Exception);
        assertTrue(exception.getMessage().indexOf(MESSAGE) != -1);
        assertEquals(CAUSE, exception.getCause());
    }
}