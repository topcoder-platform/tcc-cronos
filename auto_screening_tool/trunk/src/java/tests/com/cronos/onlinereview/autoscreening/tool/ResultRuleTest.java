/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The unit test cases for class ResultRule.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResultRuleTest extends TestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ResultRuleTest.class);
    }

    /**
     * <p>
     * Accuracy Test for the constructor
     * <code>RuleResult(Throwable error)</code>.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorA1() throws Exception {
        Throwable cause = new Exception();
        RuleResult result = new RuleResult(cause);

        assertEquals("check status", false, result.isSuccessful());
        assertEquals("check message", null, result.getMessage());
        assertEquals("check error", cause, result.getError());
    }

    /**
     * <p>
     * Failure Test for the constructor <code>RuleResult(Throwable error)</code>.
     * </p>
     * <p>
     * error is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA1() throws Exception {

        try {
            new RuleResult(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "error should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the constructor
     * <code>RuleResult(boolean success, String message)</code>.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorB1() throws Exception {
        RuleResult result = new RuleResult(true, "abc");

        assertEquals("check status", true, result.isSuccessful());
        assertEquals("check message", "abc", result.getMessage());
        assertEquals("check error", null, result.getError());
    }

    /**
     * <p>
     * Failure Test for the constructor
     * <code>RuleResult(boolean success, String message)</code>.
     * </p>
     * <p>
     * message is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB1() throws Exception {

        try {
            new RuleResult(true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "message should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the constructor
     * <code>RuleResult(boolean success, String message)</code>.
     * </p>
     * <p>
     * message is empty. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB2() throws Exception {

        try {
            new RuleResult(true, "   ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "message should not be empty (trimmed).", e.getMessage());
        }
    }
}