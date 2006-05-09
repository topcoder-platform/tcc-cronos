/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.entry.time.TimeEntryDAO;
import com.cronos.timetracker.entry.time.search.ExpressionEvaluator;
import com.cronos.timetracker.entry.time.search.SQLBasedTimeEntryCriteriaExpressionEvaluator;

/**
 * <p>
 * Failure tests for SQLBasedTimeEntryCriteriaExpressionEvaluator.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
public class SQLBasedTimeEntryCriteriaExpressionEvaluatorFailureTests extends
        TestCase {
    /**
     * ExpressionEvaluator instance for testing.
     */
    private ExpressionEvaluator evaluator;

    /**
     * <p>
     * Set up tests.
     * </p>
     * @throws Exception to JUnit
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        evaluator = new SQLBasedTimeEntryCriteriaExpressionEvaluator(
                new TimeEntryDAO("mysql", "com.cronos.timetracker.entry.time.failuretests"));
    }

    /**
     * Failure test for SQLBasedTimeEntryCriteriaExpressionEvaluator(TimeEntryDAO),
     * with null TimeEntryDAO, IAE is expected.
     */
    public void testSQLBasedTimeEntryCriteriaExpressionEvaluatorNullTimeEntryDAO() {
        try {
            new SQLBasedTimeEntryCriteriaExpressionEvaluator(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for evaluate(SearchExpression), with null SearchExpression,
     * IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testEvaluateNullSearchExpression() throws Exception {
        try {
            evaluator.evaluate(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
