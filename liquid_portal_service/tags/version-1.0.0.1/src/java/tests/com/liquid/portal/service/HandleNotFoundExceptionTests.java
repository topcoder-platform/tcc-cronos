/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>HandleNotFoundException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HandleNotFoundExceptionTests extends TestCase {
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
     * Tests accuracy of <code>HandleNotFoundException(String, String)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated and handle is properly
     * set.
     * </p>
     */
    public void testCtor1Accuracy() {
        HandleNotFoundException exception = new HandleNotFoundException(HANDLE, MESSAGE);
        assertNotNull("Unable to instantiate HandleNotFoundException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Handle is not properly set.", HANDLE, exception.getHandle());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>HandleNotFoundException(String, String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated, and
     * handle is properly set.
     * </p>
     */
    public void testCtor2Accuracy() {
        HandleNotFoundException exception = new HandleNotFoundException(HANDLE, MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate HandleNotFoundException.", exception);
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
        HandleNotFoundException exception = new HandleNotFoundException(HANDLE, MESSAGE);
        assertEquals("Handle is not peoperly get.", HANDLE, exception.getHandle());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>HandleNotFoundException</code>
     * subclasses <code>LiquidPortalServicingException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("HandleNotFoundException does not subclass LiquidPortalServicingException.",
                new HandleNotFoundException(HANDLE, MESSAGE) instanceof LiquidPortalServicingException);
    }
}
