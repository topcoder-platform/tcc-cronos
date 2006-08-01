/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax.handlers;

import junit.framework.TestCase;

/**
 * Test the class <code>RoleResolutionException</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RoleResolutionExceptionTest extends TestCase {

    /**
     * Represents the exception message.
     */
    private static final String MESSAGE = "exception message";

    /**
     * Represents the exception cause.
     */
    private static final Exception CAUSE = new Exception();

    /**
     * Test method for ConfigurationException().
     */
    public void testConfigurationException() {
        assertNotNull("The constructor doesn't work.", new RoleResolutionException());
    }

    /**
     * Test method for RoleResolutionException(java.lang.String).
     */
    public void testConfigurationExceptionString() {
        RoleResolutionException e = new RoleResolutionException(MESSAGE);
        assertEquals("The message is not right.", MESSAGE, e.getMessage());
    }

    /**
     * Test method for RoleResolutionException(java.lang.Throwable).
     */
    public void testConfigurationExceptionThrowable() {
        RoleResolutionException e = new RoleResolutionException(CAUSE);
        assertEquals("The cause is not right.", CAUSE, e.getCause());
    }

    /**
     * Test method for RoleResolutionException(java.lang.String, java.lang.Throwable).
     */
    public void testConfigurationExceptionStringThrowable() {
        RoleResolutionException e = new RoleResolutionException(MESSAGE, CAUSE);
        assertEquals("The message is not right.", MESSAGE + ", caused by null", e.getMessage());
        assertEquals("The cause is not right.", CAUSE, e.getCause());
    }

}
