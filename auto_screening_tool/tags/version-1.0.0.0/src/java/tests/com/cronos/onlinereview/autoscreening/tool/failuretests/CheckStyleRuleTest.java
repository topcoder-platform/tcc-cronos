/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import java.util.HashMap;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.rules.CheckStyleRule;

/**
 * Failure tests for <code>CheckStyleRule</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class CheckStyleRuleTest extends TestCase {

    /**
     * Represents the rule to test.
     */
    private CheckStyleRule rule;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
        rule = new CheckStyleRule("jar", "test");
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
     * Test method for CheckStyleRule(java.lang.String, java.lang.String).
     */
    public void testCheckStyleRule_NullCommand() {
        try {
            new CheckStyleRule(null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for CheckStyleRule(java.lang.String, java.lang.String).
     */
    public void testCheckStyleRule_EmptyCommand() {
        try {
            new CheckStyleRule(" ", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for CheckStyleRule(java.lang.String, java.lang.String).
     */
    public void testCheckStyleRule_NullDir() {
        try {
            new CheckStyleRule("java", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for CheckStyleRule(java.lang.String, java.lang.String).
     */
    public void testCheckStyleRule_EmptyDir() {
        try {
            new CheckStyleRule("java", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for screen(ScreeningTask, java.util.Map).
     * In this case, the task is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testScreen_NullTask() {
        try {
            rule.screen(null, new HashMap());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for screen(ScreeningTask, java.util.Map).
     * In this case, the context is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testScreen_NullContext() {
        try {
            rule.screen(new ScreeningTask(), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }


}
