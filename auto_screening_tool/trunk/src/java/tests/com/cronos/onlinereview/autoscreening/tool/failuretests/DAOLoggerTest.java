/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import com.cronos.onlinereview.autoscreening.tool.ResponseLevel;
import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningLogic;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.logger.DAOLogger;
import com.cronos.onlinereview.autoscreening.tool.rules.ComponentSpecificationRule;

import junit.framework.TestCase;

/**
 * Failure tests for <code>DAOLogger</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DAOLoggerTest extends TestCase {

    /**
     * Represents the logger to test.
     */
    private DAOLogger logger;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
        logger = new DAOLogger(new MockScreeningResponseDAO());
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
     * Test method for DAOLogger(com.cronos.onlinereview.autoscreening.tool.ScreeningResponseDAO).
     * In this case, the dao is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDAOLogger() {
        try {
            new DAOLogger(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for logResponse(ScreeningTask, ScreeningLogic, RuleResult[]).
     * In this case, the task is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLogResponse_NullTask() {
        try {
            logger.logResponse(null,
                    new ScreeningLogic(new ComponentSpecificationRule(), ResponseLevel.PASS,
                            ResponseLevel.WARN, ResponseLevel.FAIL),
                    new RuleResult[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for logResponse(ScreeningTask, ScreeningLogic, RuleResult[]).
     * In this case, the task is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLogResponse_NullLogic() {
        try {
            logger.logResponse(new ScreeningTask(),
                    null,
//                    new ScreeningLogic(new ComponentSpecificationRule(), ResponseLevel.PASS,
//                            ResponseLevel.WARN, ResponseLevel.FAIL),
                    new RuleResult[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for logResponse(ScreeningTask, ScreeningLogic, RuleResult[]).
     * In this case, the rule results is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLogResponse_NullRuleResults() {
        try {
            logger.logResponse(new ScreeningTask(),
                    new ScreeningLogic(new ComponentSpecificationRule(), ResponseLevel.PASS,
                            ResponseLevel.WARN, ResponseLevel.FAIL),
                    null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setOperator(java.lang.String).
     * In this case, the op is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetOperator_Null() {
        try {
            logger.setOperator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setOperator(java.lang.String).
     * In this case, the op is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetOperator_Empty() {
        try {
            logger.setOperator(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
