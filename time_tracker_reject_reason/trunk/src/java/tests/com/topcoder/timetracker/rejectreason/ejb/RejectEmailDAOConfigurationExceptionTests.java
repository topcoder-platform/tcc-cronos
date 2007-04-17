/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import junit.framework.TestCase;


/**
 * Unit test for <code>RejectEmailDAOConfigurationException</code> class. Instantiation tests and basic functionalities
 * tests.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailDAOConfigurationExceptionTests extends TestCase {
    /** The exception message to create the exception. */
    private final String message = "an exception message";

    /** The cause to create the exception. */
    private final Throwable cause = new Exception();

    /**
     * <p>
     * Tests constructor(String). Verifies it can be instantiated properly.
     * </p>
     */
    public void testConstructorWithMessage() {
        RejectEmailDAOConfigurationException exception = new RejectEmailDAOConfigurationException(message);

        assertNotNull("Unable to instantiate RejectEmailDAOConfigurationException", exception);
        assertTrue("Message is not initialized correctly", exception.getMessage().matches(message + ".*"));
        assertEquals("Cause is not initialized correctly", null, exception.getCause());
    }

    /**
     * <p>
     * Tests constructor(String, Throwable). Verifies it can be instantiated properly.
     * </p>
     */
    public void testConstructorWithMessageAndCause() {
        RejectEmailDAOConfigurationException exception = new RejectEmailDAOConfigurationException(message, cause);

        assertNotNull("Unable to instantiate RejectEmailDAOConfigurationException", exception);
        assertTrue("Message is not initialized correctly", exception.getMessage().matches(message + ".*"));
        assertEquals("Cause is not initialized correctly", cause, exception.getCause());
    }
}
