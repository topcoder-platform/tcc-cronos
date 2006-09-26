/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import java.util.HashMap;

import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.rules.ArchiveFileRule;
import com.cronos.onlinereview.autoscreening.tool.rules.DirectoryStructureRule;

import junit.framework.TestCase;

/**
 * Failure tests for <code>DirectoryStructureRule</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DirectoryStructureRuleTest extends TestCase {

    /**
     * Represents the rule to test.
     */
    private DirectoryStructureRule rule;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
        rule = new DirectoryStructureRule("jar");
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
     * Test method for DirectoryStructureRule(java.lang.String).
     * In this case, the file is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDirectoryStructureRule_Null() {
        try {
            new DirectoryStructureRule(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DirectoryStructureRule(java.lang.String).
     * In this case, the file is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDirectoryStructureRule_Empty() {
        try {
            new DirectoryStructureRule(" ");
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
