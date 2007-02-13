/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import junit.framework.TestCase;

/**
 * <p>
 * Tests the ConfigurationException class.
 * </p>
 *
 * @see ConfigurationException
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationExceptionTest extends TestCase {

    /** The message that is used to create exceptions. */
    private static final String MESSAGE = "message";

    /** The root cause of exceptions. */
    private static final Throwable CAUSE = new Throwable("The Cause");

    /** The ConfigurationException that we're testing. */
    private ConfigurationException exception;


    /**
     * Instantiate a ConfigurationException to test.
     *
     * @throws Exception never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();

        exception = new ConfigurationException(MESSAGE);
    }


    /**
     * Tests that the 1-arg constructor sets initial values correctly.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test1ArgCtorStringSetsInitialValues() throws Exception {

        // Make sure the parameters are set in the ctor.
        assertNull("Cause is not defaulted by ctor!", exception.getCause());
        assertEquals("Message is not set by ctor!", MESSAGE, exception.getMessage());
    }


    /**
     * Tests that the 2-arg constructor sets initial values correctly.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test2ArgCtorSetsInitialValues() throws Exception {

        // Instantiate a new ConfigurationException
        ConfigurationException newException = new ConfigurationException(MESSAGE, CAUSE);

        // Make sure the parameters are set in the ctor.
        assertEquals("Cause is not set by ctor!", CAUSE, newException.getCause());
        assertEquals("Message is not set by ctor!",
                     MESSAGE + ", caused by The Cause",
                     newException.getMessage());
    }


    /**
     * Tests that the 1-arg constructor sets initial values correctly.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test1ArgCtorThrowableSetsInitialValues() throws Exception {

        // Instantiate a new ConfigurationException
        ConfigurationException newException = new ConfigurationException(CAUSE);

        // Make sure the parameters are set in the ctor.
        assertEquals("Cause is not set by ctor!", CAUSE, newException.getCause());
        assertEquals("Message is not set by ctor!",
                     CAUSE.getClass().getName() + ": The Cause, caused by The Cause",
                     newException.getMessage());
    }
}