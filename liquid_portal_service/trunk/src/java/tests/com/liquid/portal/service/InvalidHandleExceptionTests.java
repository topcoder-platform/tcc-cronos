/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>InvalidHandleException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InvalidHandleExceptionTests extends TestCase {
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
     * Represents the handle that was not found.
     * </p>
     */
    private static final String HANDLE = "handle";

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {

    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

    }

    /**
     * <p>
     * Tests accuracy of <code>InvalidHandleException(String, String)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated and handle is properly
     * set.
     * </p>
     */
    public void testCtor1Accuracy() {
        InvalidHandleException exception = new InvalidHandleException(HANDLE, MESSAGE);
        assertNotNull("Unable to instantiate InvalidHandleException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Handle is not properly set.", HANDLE, exception.getHandle());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>InvalidHandleException(String, String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated, and
     * handle is properly set.
     * </p>
     */
    public void testCtor2Accuracy() {
        InvalidHandleException exception = new InvalidHandleException(HANDLE, MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate InvalidHandleException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
        assertEquals("Handle is not properly set.", HANDLE, exception.getHandle());
    }

    /**
     * <p>
     * Tests accuracy of <code>getHandle()</code> method.
     * </p>
     */
    public void testGetHandleAccuracy() {
        InvalidHandleException exception = new InvalidHandleException(HANDLE, MESSAGE);
        assertEquals("Handle is not peoperly get.", HANDLE, exception.getHandle());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>InvalidHandleException</code>
     * subclasses <code>LiquidPortalServicingException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("InvalidHandleException does not subclass LiquidPortalServicingException.",
                new InvalidHandleException(HANDLE, MESSAGE) instanceof LiquidPortalServicingException);
    }
}
