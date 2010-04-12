/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>HandleCreationException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HandleCreationExceptionTests extends TestCase {
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
     * Represents the handle that was not created.
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
     * Tests accuracy of <code>HandleCreationException(String, String)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated and handle is properly
     * set.
     * </p>
     */
    public void testCtor1Accuracy() {
        HandleCreationException exception = new HandleCreationException(HANDLE, MESSAGE);
        assertNotNull("Unable to instantiate HandleCreationException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Handle is not properly set.", HANDLE, exception.getHandle());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>HandleCreationException(String, String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated, and
     * handle is properly set.
     * </p>
     */
    public void testCtor2Accuracy() {
        HandleCreationException exception = new HandleCreationException(HANDLE, MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate HandleCreationException.", exception);
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
        HandleCreationException exception = new HandleCreationException(HANDLE, MESSAGE);
        assertNotNull("Unable to instantiate HandleCreationException.", exception);
        assertEquals("Handle is not peoperly get.", HANDLE, exception.getHandle());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>HandleCreationException</code>
     * subclasses <code>LiquidPortalServicingException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("HandleCreationException does not subclass LiquidPortalServicingException.",
                new HandleCreationException(HANDLE, MESSAGE) instanceof LiquidPortalServicingException);
    }
}
