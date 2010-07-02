/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>ContestNotFoundException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestNotFoundExceptionTests extends TestCase {
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
     * Represents the contest id.
     * </p>
     */
    private static final long CONTEST_ID = 123;

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
     * Tests accuracy of <code>ContestNotFoundException(long, String)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated and contest id is
     * properly set.
     * </p>
     */
    public void testCtor1Accuracy() {
        ContestNotFoundException exception = new ContestNotFoundException(CONTEST_ID, MESSAGE);
        assertNotNull("Unable to instantiate ContestNotFoundException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Contest ID is not properly set.", CONTEST_ID, exception.getContestId());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>ContestNotFoundException(long, String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated, and
     * contest id is properly set.
     * </p>
     */
    public void testCtor2Accuracy() {
        ContestNotFoundException exception = new ContestNotFoundException(CONTEST_ID, MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ContestNotFoundException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
        assertEquals("Contest ID is not properly set.", CONTEST_ID, exception.getContestId());
    }

    /**
     * <p>
     * Tests accuracy of <code>getContestId()</code> method.
     * </p>
     */
    public void testGetContestIdAccuracy() {
        ContestNotFoundException exception = new ContestNotFoundException(CONTEST_ID, MESSAGE);
        assertEquals("Contest ID is not peoperly get.", CONTEST_ID, exception.getContestId());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ContestNotFoundException</code>
     * subclasses <code>LiquidPortalServicingException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ContestNotFoundException does not subclass LiquidPortalServicingException.",
                new ContestNotFoundException(CONTEST_ID, MESSAGE) instanceof LiquidPortalServicingException);
    }
}
