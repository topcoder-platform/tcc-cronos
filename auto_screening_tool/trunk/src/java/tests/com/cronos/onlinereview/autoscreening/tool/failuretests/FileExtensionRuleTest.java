/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import java.util.HashMap;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.rules.CheckStyleRule;
import com.cronos.onlinereview.autoscreening.tool.rules.FileExtensionRule;

/**
 * Failure tests for <code>FileExtensionRule</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class FileExtensionRuleTest extends TestCase {

    /**
     * Represents the rule to test.
     */
    private FileExtensionRule rule;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
        rule = new FileExtensionRule("jar", "test");
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
     * Test method for FileExtensionRule(java.lang.String, java.lang.String).
     */
    public void testCheckStyleRule_NullCommand() {
        try {
            new FileExtensionRule(null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for FileExtensionRule(java.lang.String, java.lang.String).
     */
    public void testCheckStyleRule_EmptyCommand() {
        try {
            new FileExtensionRule(" ", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for FileExtensionRule(java.lang.String, java.lang.String).
     */
    public void testCheckStyleRule_NullDir() {
        try {
            new FileExtensionRule("java", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for FileExtensionRule(java.lang.String, java.lang.String).
     */
    public void testCheckStyleRule_EmptyDir() {
        try {
            new FileExtensionRule("java", " ");
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
