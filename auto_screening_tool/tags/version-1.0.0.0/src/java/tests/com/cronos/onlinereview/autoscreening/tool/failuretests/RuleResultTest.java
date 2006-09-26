/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;

/**
 * Failure tests for <code>RuleResult</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class RuleResultTest extends TestCase {

    /**
     * Represents the result to test.
     */
    private RuleResult result;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
        result = new RuleResult(new Exception());
    }

    /**
     * Clean up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }

    /**
     * Test method for RuleResult(java.lang.Throwable).
     * In this case, the error is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testRuleResultThrowable() {
        try {
            new RuleResult(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for RuleResult(boolean, java.lang.String).
     * In this case, the message is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testRuleResult_NullMessage() {
        try {
            new RuleResult(false, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for RuleResult(boolean, java.lang.String).
     * In this case, the message is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testRuleResult_EmptyMessage() {
        try {
            new RuleResult(false, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
