/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>LiquidPortalServiceConfigurationException</code>
 * class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LiquidPortalServiceConfigurationExceptionTests extends TestCase {
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
     * Tests accuracy of
     * <code>LiquidPortalServiceConfigurationException(String)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor1Accuracy() {
        LiquidPortalServiceConfigurationException exception = new LiquidPortalServiceConfigurationException(MESSAGE);
        assertNotNull("Unable to instantiate LiquidPortalServiceConfigurationException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>LiquidPortalServiceConfigurationException(String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        LiquidPortalServiceConfigurationException exception = new LiquidPortalServiceConfigurationException(MESSAGE,
                CAUSE);
        assertNotNull("Unable to instantiate LiquidPortalServiceConfigurationException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>LiquidPortalServiceConfigurationException()</code> constructor.
     * </p>
     */
    public void testCtor3Accuracy() {
        LiquidPortalServiceConfigurationException exception = new LiquidPortalServiceConfigurationException();
        assertNotNull("Unable to instantiate LiquidPortalServiceConfigurationException.", exception);
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>LiquidPortalServiceConfigurationException(Throwable)</code> constructor.
     * </p>
     */
    public void testCtor4Accuracy() {
        LiquidPortalServiceConfigurationException exception = new LiquidPortalServiceConfigurationException(CAUSE);
        assertNotNull("Unable to instantiate LiquidPortalServiceConfigurationException.", exception);
        assertEquals(CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies
     * <code>LiquidPortalServiceConfigurationException</code> subclasses
     * <code>RuntimeException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("LiquidPortalServiceConfigurationException does not subclass LiquidPortalServicingException.",
                new LiquidPortalServiceConfigurationException(MESSAGE) instanceof RuntimeException);
    }
}
