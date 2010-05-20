/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>LiquidPortalServicingException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LiquidPortalServicingExceptionTests extends TestCase {
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
     * Tests accuracy of <code>LiquidPortalServicingException(String)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor1Accuracy() {
        LiquidPortalServicingException exception = new LiquidPortalServicingException(MESSAGE);
        assertNotNull("Unable to instantiate LiquidPortalServicingException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>LiquidPortalServicingException(String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        LiquidPortalServicingException exception = new LiquidPortalServicingException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate LiquidPortalServicingException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>LiquidPortalServicingException()</code>
     * constructor.
     * </p>
     */
    public void testCtor3Accuracy() {
        LiquidPortalServicingException exception = new LiquidPortalServicingException();
        assertNotNull("Unable to instantiate LiquidPortalServicingException.", exception);
    }

    /**
     * <p>
     * Tests accuracy of <code>LiquidPortalServicingException(Throwable)</code>
     * constructor.
     * </p>
     */
    public void testCtor4Accuracy() {
        LiquidPortalServicingException exception = new LiquidPortalServicingException(CAUSE);
        assertNotNull("Unable to instantiate LiquidPortalServicingException.", exception);
        assertEquals(CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>LiquidPortalServicingException</code>
     * subclasses <code>Exception</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("LiquidPortalServicingException does not subclass LiquidPortalServicingException.",
                new LiquidPortalServicingException(MESSAGE) instanceof Exception);
    }
}
