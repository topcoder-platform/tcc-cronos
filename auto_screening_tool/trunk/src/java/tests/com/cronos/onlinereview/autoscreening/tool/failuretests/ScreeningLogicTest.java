/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import java.util.HashMap;

import com.cronos.onlinereview.autoscreening.tool.ResponseLevel;
import com.cronos.onlinereview.autoscreening.tool.ScreeningLogic;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.rules.ComponentSpecificationRule;

import junit.framework.TestCase;

/**
 * Failure tests for <code>ScreeningLogic</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class ScreeningLogicTest extends TestCase {

    /**
     * Represents the logic to test.
     */
    private ScreeningLogic logic;

    /**
     * Represents the rule used in test.
     */
    private ScreeningRule rule;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
        rule = new ComponentSpecificationRule();
        logic = new ScreeningLogic(rule, ResponseLevel.PASS,
            ResponseLevel.WARN, ResponseLevel.FAIL);
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
     * Test method for ScreeningLogic(ScreeningRule, ResponseLevel, ResponseLevel, ResponseLevel).
     * In this case, the rule is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testConstructor1_NullScreeningRule() {
        try {
            new ScreeningLogic(null, ResponseLevel.PASS, ResponseLevel.WARN, ResponseLevel.FAIL);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ScreeningLogic(ScreeningRule, ResponseLevel, ResponseLevel, ResponseLevel).
     * In this case, the SuccessSeverity is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testConstructor1_NullSuccessSeverity() {
        try {
            new ScreeningLogic(rule, null, ResponseLevel.WARN, ResponseLevel.FAIL);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ScreeningLogic(ScreeningRule, ResponseLevel, ResponseLevel, ResponseLevel).
     * In this case, the FailureSeverity is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testConstructor1_NullFailureSeverity() {
        try {
            new ScreeningLogic(rule, ResponseLevel.PASS, null, ResponseLevel.FAIL);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ScreeningLogic(ScreeningRule, ResponseLevel, ResponseLevel, ResponseLevel).
     * In this case, the ErrorSeverity is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testConstructor1_NullErrorSeverity() {
        try {
            new ScreeningLogic(rule, ResponseLevel.WARN, ResponseLevel.FAIL, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ScreeningLogic(ScreeningRule, ResponseLevel, ResponseLevel, ResponseLevel).
     * In this case, the rule is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testConstructor2_NullScreeningRule() {
        try {
            new ScreeningLogic(null, ResponseLevel.PASS, ResponseLevel.WARN, ResponseLevel.FAIL,
                    1, 1, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ScreeningLogic(ScreeningRule, ResponseLevel, ResponseLevel, ResponseLevel).
     * In this case, the SuccessSeverity is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testConstructor2_NullSuccessSeverity() {
        try {
            new ScreeningLogic(rule, null, ResponseLevel.WARN, ResponseLevel.FAIL, 1, 1, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ScreeningLogic(ScreeningRule, ResponseLevel, ResponseLevel, ResponseLevel).
     * In this case, the FailureSeverity is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testConstructor2_NullFailureSeverity() {
        try {
            new ScreeningLogic(rule, ResponseLevel.PASS, null, ResponseLevel.FAIL, 1, 1, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ScreeningLogic(ScreeningRule, ResponseLevel, ResponseLevel, ResponseLevel).
     * In this case, the ErrorSeverity is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testConstructor2_NullErrorSeverity() {
        try {
            new ScreeningLogic(rule, ResponseLevel.WARN, ResponseLevel.FAIL, null, 1, 1, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ScreeningLogic#screen(ScreeningTask, Map)}.
     * In this case, the task is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testScreen_NullTask() {
        try {
            this.logic.screen(null, new HashMap());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ScreeningLogic#screen(ScreeningTask, Map)}.
     * In this case, the context is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testScreen_NullContext() {
        try {
            this.logic.screen(new ScreeningTask(), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
