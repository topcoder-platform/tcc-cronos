/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ConfigurationExceptionTest.java
 */
package com.topcoder.apps.screening.applications.specification;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>ConfigurationException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationExceptionTest extends TestCase {

    /**Message.*/
    private static final String MESSAGE = "message";

    /**Cause.*/
    private static final Exception CAUSE = new IllegalArgumentException();

    /**ConfigurationException instance that will be tested.*/
    private ConfigurationException exception;

    /**
     * <p>
     * Set up environment.
     * </p>
     */
    public void setUp() {
        exception = new ConfigurationException(MESSAGE, CAUSE);
    }

    /**
     * <p>
     * Tests constructor ConfigurationException(message).
     * </p>
     */
    public void testCtor1() {
        exception = new ConfigurationException(MESSAGE);
        assertTrue(exception instanceof Exception);
        assertEquals(MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests constructor ConfigurationException(message, cause).
     * </p>
     */
    public void testCtor2() {
        assertTrue(exception instanceof Exception);
        assertTrue(exception.getMessage().indexOf(MESSAGE) != -1);
        assertEquals(CAUSE, exception.getCause());
    }
}