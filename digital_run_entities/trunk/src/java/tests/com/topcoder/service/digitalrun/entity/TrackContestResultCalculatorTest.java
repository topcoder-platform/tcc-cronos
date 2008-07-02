/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link TrackContestResultCalculator} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TrackContestResultCalculatorTest extends TestCase {

    /**
     * Represents the <code>TrackContestResultCalculator</code> instance to test.
     */
    private TrackContestResultCalculator trackContestResultCalculator = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        trackContestResultCalculator = new TrackContestResultCalculator();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void tearDown() throws Exception {
        trackContestResultCalculator = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(TrackContestResultCalculatorTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link TrackContestResultCalculator#TrackContestResultCalculator()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_TrackContestResultCalculator() {
        // check for null
        assertNotNull("TrackContestResultCalculator creation failed", trackContestResultCalculator);
    }

    /**
     * <p>
     * Accuracy test for {@link TrackContestResultCalculator#setClassName(String)} and
     * {@link TrackContestResultCalculator#getClassName()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     *
     */
    public void test_accuracy_setClassName() {
        // set the value to test
        trackContestResultCalculator.setClassName("test");
        assertEquals("getClassName and setClassName failure occured", "test", trackContestResultCalculator
                .getClassName());
    }

    /**
     * <p>
     * Failure test for {@link TrackContestResultCalculator#setClassName(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String className : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setClassName() throws Exception {
        try {
            trackContestResultCalculator.setClassName(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'className' cannot be null.");
        }
    }

    /**
     * <p>
     * Failure test for {@link TrackContestResultCalculator#setClassName(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String className : Empty string
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setClassName1() throws Exception {
        try {
            trackContestResultCalculator.setClassName("");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'className' cannot be empty.");
        }
    }

    /**
     * <p>
     * Failure test for {@link TrackContestResultCalculator#setClassName(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String className : Empty string with only spaces
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setClassName2() throws Exception {
        try {
            trackContestResultCalculator.setClassName("   ");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'className' cannot be empty.");
        }
    }
}
