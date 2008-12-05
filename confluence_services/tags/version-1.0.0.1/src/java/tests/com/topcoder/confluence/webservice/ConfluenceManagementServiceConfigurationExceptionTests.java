/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>OSFilterException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceConfigurationExceptionTests extends TestCase {
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
     * Tests accuracy of <code>ConfluenceManagementServiceConfigurationException()</code> constructor.
     * </p>
     * <p>
     * Verifies the exception is properly created.
     * </p>
     */
    public void testCtor1Accuracy() {
        ConfluenceManagementServiceConfigurationException exception =
            new ConfluenceManagementServiceConfigurationException();
        assertNotNull("Unable to instantiate ConfluenceManagementServiceConfigurationException.", exception);
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceConfigurationException(String)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        ConfluenceManagementServiceConfigurationException exception =
            new ConfluenceManagementServiceConfigurationException(MESSAGE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceConfigurationException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceConfigurationException(Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the cause are properly propagated.
     * </p>
     */
    public void testCtor3Accuracy() {
        ConfluenceManagementServiceConfigurationException exception =
            new ConfluenceManagementServiceConfigurationException(CAUSE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceConfigurationException.", exception);
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceConfigurationException(String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor4Accuracy() {
        ConfluenceManagementServiceConfigurationException exception =
            new ConfluenceManagementServiceConfigurationException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceConfigurationException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ConfluenceManagementServiceConfigurationException</code> subclasses
     * <code>RuntimeException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ConfluenceManagementServiceConfigurationException does not subclass RuntimeException.",
            new ConfluenceManagementServiceConfigurationException() instanceof RuntimeException);
    }
}