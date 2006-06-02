/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

import com.cronos.timetracker.entry.time.V1Dot1TestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>BooleanExpression</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class V1Dot1BooleanExpressionUnitTest extends TestCase {
    /** Represents the <code>ComparisonExpression</code> instances which will be used for testing. */
    private SearchExpression[] expressions = null;

    /** Represents the <code>BooleanExpression</code> instances which will be used for testing. */
    private BooleanExpression expression = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        expressions = new ComparisonExpression[2];
        expressions[0] = ComparisonExpression.equals(TimeEntryCriteria.CREATION_USER, "ivern");
        expressions[1] = ComparisonExpression.equals(TimeEntryCriteria.MODIFICATION_USER, "pops");
    }

    /**
     * <p>
     * Tests the method <code>and(SearchExpression, SearchExpression)</code> when left is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testAnd_NullLeft() {
        try {
            BooleanExpression.and(null, expressions[1]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>and(SearchExpression, SearchExpression)</code> when right is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testAnd_NullRight() {
        try {
            BooleanExpression.and(expressions[0], null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>and(SearchExpression, SearchExpression)</code>.
     * </p>
     */
    public void testAnd_Accuracy() {
        expression = BooleanExpression.and(expressions[0], expressions[1]);

        // check
        SearchExpression[] ret = (SearchExpression[]) V1Dot1TestHelper.getPrivateField(expression.getClass(),
                expression, "innerExpressions");
        assertEquals("The innerExpressions should be correct.", expressions.length, ret.length);

        for (int i = 0; i < expressions.length; i++) {
            assertEquals("The innerExpressions should be correct.", expressions[i], ret[i]);
        }

        assertEquals("The operator should be correct.", "And",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.",
            "( " + expressions[0].getSearchExpressionString() + " And " + expressions[1].getSearchExpressionString()
            + " )", V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getInnerExpressions()</code>.
     * </p>
     */
    public void testAnd_GetInnerExpressionsAccuracy() {
        expression = BooleanExpression.and(expressions[0], expressions[1]);

        // check
        SearchExpression[] ret = expression.getInnerExpressions();
        assertEquals("The innerExpressions should be correct.", expressions.length, ret.length);

        for (int i = 0; i < expressions.length; i++) {
            assertEquals("The innerExpressions should be correct.", expressions[i], ret[i]);
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testAnd_GetSearchExpressionString_Accuracy() {
        expression = BooleanExpression.and(expressions[0], expressions[1]);

        // check
        assertEquals("The expression should be correct.",
            "( " + expressions[0].getSearchExpressionString() + " And " + expressions[1].getSearchExpressionString()
            + " )", expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator</code>.
     * </p>
     */
    public void testAnd_GetOperatorAccuracy() {
        expression = BooleanExpression.and(expressions[0], expressions[1]);

        // check
        assertEquals("The operator should be correct.", "And", expression.getOperator());
    }

    /**
     * <p>
     * Tests the method <code>or(SearchExpression, SearchExpression)</code> when left is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testOr_NullLeft() {
        try {
            BooleanExpression.or(null, expressions[1]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>or(SearchExpression, SearchExpression)</code> when right is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testOr_NullRight() {
        try {
            BooleanExpression.or(expressions[0], null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>or(SearchExpression, SearchExpression)</code>.
     * </p>
     */
    public void testOr_Accuracy() {
        expression = BooleanExpression.or(expressions[0], expressions[1]);

        // check
        SearchExpression[] ret = (SearchExpression[]) V1Dot1TestHelper.getPrivateField(expression.getClass(),
                expression, "innerExpressions");
        assertEquals("The innerExpressions should be correct.", expressions.length, ret.length);

        for (int i = 0; i < expressions.length; i++) {
            assertEquals("The innerExpressions should be correct.", expressions[i], ret[i]);
        }

        assertEquals("The operator should be correct.", "Or",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.",
            "( " + expressions[0].getSearchExpressionString() + " Or " + expressions[1].getSearchExpressionString()
            + " )", V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getInnerExpressions()</code>.
     * </p>
     */
    public void testOr_GetInnerExpressionsAccuracy() {
        expression = BooleanExpression.or(expressions[0], expressions[1]);

        // check
        SearchExpression[] ret = expression.getInnerExpressions();
        assertEquals("The innerExpressions should be correct.", expressions.length, ret.length);

        for (int i = 0; i < expressions.length; i++) {
            assertEquals("The innerExpressions should be correct.", expressions[i], ret[i]);
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testOr_GetSearchExpressionString_Accuracy() {
        expression = BooleanExpression.or(expressions[0], expressions[1]);

        // check
        assertEquals("The expression should be correct.",
            "( " + expressions[0].getSearchExpressionString() + " Or " + expressions[1].getSearchExpressionString()
            + " )", expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator</code>.
     * </p>
     */
    public void testOr_GetOperatorAccuracy() {
        expression = BooleanExpression.or(expressions[0], expressions[1]);

        // check
        assertEquals("The operator should be correct.", "Or", expression.getOperator());
    }

    /**
     * <p>
     * Tests the method <code>not(SearchExpression))</code> when expressionToNegate is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testNot_NullLeft() {
        try {
            BooleanExpression.not(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>not(SearchExpression))</code>.
     * </p>
     */
    public void testNot_Accuracy() {
        expression = BooleanExpression.not(expressions[0]);

        // check
        SearchExpression[] ret = (SearchExpression[]) V1Dot1TestHelper.getPrivateField(expression.getClass(),
                expression, "innerExpressions");
        assertEquals("The innerExpressions should be correct.", 1, ret.length);

        assertEquals("The innerExpressions should be correct.", expressions[0], ret[0]);

        assertEquals("The operator should be correct.", "Not",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", "Not " + expressions[0].getSearchExpressionString(),
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getInnerExpressions()</code>.
     * </p>
     */
    public void testNot_GetInnerExpressionsAccuracy() {
        expression = BooleanExpression.not(expressions[0]);

        // check
        SearchExpression[] ret = expression.getInnerExpressions();
        assertEquals("The innerExpressions should be correct.", 1, ret.length);

        assertEquals("The innerExpressions should be correct.", expressions[0], ret[0]);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testNot_GetSearchExpressionString_Accuracy() {
        expression = BooleanExpression.not(expressions[0]);

        // check
        assertEquals("The expression should be correct.", "Not " + expressions[0].getSearchExpressionString(),
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator</code>.
     * </p>
     */
    public void testNot_GetOperatorAccuracy() {
        expression = BooleanExpression.not(expressions[0]);

        // check
        assertEquals("The operator should be correct.", "Not",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
    }
}
