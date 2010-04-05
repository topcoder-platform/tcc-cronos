/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>LiquidPortalIllegalArgumentException</code>
 * class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LiquidPortalIllegalArgumentExceptionTests extends TestCase {
    /**
     * <p>
     * Represents the <code>Exception</code> instance used for testing.
     * </p>
     */
    private static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Represents the error code for testing.
     * </p>
     */
    private static final int ERROR_CODE = 2001;

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
     * Tests accuracy of <code>LiquidPortalIllegalArgumentException()</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the default error code is properly set.
     * </p>
     */
    public void testCtor1Accuracy() {
        LiquidPortalIllegalArgumentException exception = new LiquidPortalIllegalArgumentException();
        assertNotNull("Unable to instantiate LiquidPortalIllegalArgumentException.", exception);
        assertEquals("Default error code is not properly set.", 0, exception.getErrorCode());
    }

    /**
     * <p>
     * Tests accuracy of <code>LiquidPortalIllegalArgumentException(int)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error code is properly set.
     * </p>
     */
    public void testCtor2Accuracy() {
        LiquidPortalIllegalArgumentException exception = new LiquidPortalIllegalArgumentException(ERROR_CODE);
        assertNotNull("Unable to instantiate LiquidPortalIllegalArgumentException.", exception);
        assertEquals("Default error code is not properly set.", ERROR_CODE, exception.getErrorCode());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>LiquidPortalIllegalArgumentException(Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the cause is properly propagated, and default error code is
     * properly set.
     * </p>
     */
    public void testCtor3Accuracy() {
        LiquidPortalIllegalArgumentException exception = new LiquidPortalIllegalArgumentException(CAUSE);
        assertNotNull("Unable to instantiate LiquidPortalIllegalArgumentException.", exception);
        assertEquals("Default error code is not properly set.", 0, exception.getErrorCode());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>LiquidPortalIllegalArgumentException(int, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the cause is properly propagated, and error code is properly
     * set.
     * </p>
     */
    public void testCtor4Accuracy() {
        LiquidPortalIllegalArgumentException exception = new LiquidPortalIllegalArgumentException(ERROR_CODE, CAUSE);
        assertNotNull("Unable to instantiate LiquidPortalIllegalArgumentException.", exception);
        assertEquals("Default error code is not properly set.", ERROR_CODE, exception.getErrorCode());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>getErrorCode()</code> method.
     * </p>
     */
    public void testGetErrorCodeAccuracy() {
        LiquidPortalIllegalArgumentException exception = new LiquidPortalIllegalArgumentException(ERROR_CODE);
        assertNotNull("Unable to instantiate LiquidPortalIllegalArgumentException.", exception);
        assertEquals("Default error code is not properly set.", ERROR_CODE, exception.getErrorCode());
    }

    /**
     * <p>
     * Inheritance test, verifies
     * <code>LiquidPortalIllegalArgumentException</code> subclasses
     * <code>Exception</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("LiquidPortalIllegalArgumentException does not subclass Exception.",
                new LiquidPortalIllegalArgumentException() instanceof Exception);
    }
}
