/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.search;

import com.topcoder.timetracker.entry.time.V1Dot1TestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>ComparisonExpression</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class V1Dot1ComparisonExpressionUnitTest extends TestCase {
    /**
     * Represents the criteria which will be used to create the new <code>ComparisonExpression</code> instance for
     * testing.
     */
    private static final TimeEntryCriteria CRITERIA = TimeEntryCriteria.CREATION_USER;

    /**
     * Represents the value which will be used to create the new <code>ComparisonExpression</code> instance for
     * testing.
     */
    private static final String VALUE = "ivern";

    /** Represents the <code>ComparisonExpression</code> instance which will be used for testing. */
    private ComparisonExpression expression = null;

    /**
     * <p>
     * Tests the method <code>notEquals(TimeEntryCriteria, String)</code> when criteria is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testEquals_NullCriteria() {
        try {
            ComparisonExpression.equals(null, VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>notEquals(TimeEntryCriteria, String)</code> when value is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testEquals_NullValue() {
        try {
            ComparisonExpression.equals(CRITERIA, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>notEquals(TimeEntryCriteria, String)</code>.
     * </p>
     */
    public void testEquals_Accuracy() {
        expression = ComparisonExpression.equals(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "criteria"));
        assertEquals("The value should be correct.", VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "value"));
        assertEquals("The operator should be correct.", "=",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", CRITERIA.getName() + " = " + VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getValue()</code>.
     * </p>
     */
    public void testEquals_GetValueAccuracy() {
        expression = ComparisonExpression.equals(CRITERIA, VALUE);

        // check
        assertEquals("The value should be correct.", VALUE, expression.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testEquals_GetCriteriaAccuracy() {
        expression = ComparisonExpression.equals(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA, expression.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testEquals_GetSearchExpressionStringAccuracy() {
        expression = ComparisonExpression.equals(CRITERIA, VALUE);

        // check
        assertEquals("The expression should be correct.", CRITERIA.getName() + " = " + VALUE,
            expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator()</code>.
     * </p>
     */
    public void testEquals_GetOperatorAccuracy() {
        expression = ComparisonExpression.equals(CRITERIA, VALUE);

        // check
        assertEquals("The operator should be correct.", "=", expression.getOperator());
    }

    /**
     * <p>
     * Tests the method <code>notEquals(TimeEntryCriteria, String)</code> when criteria is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testNotEquals_NullCriteria() {
        try {
            ComparisonExpression.notEquals(null, VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>notEquals(TimeEntryCriteria, String)</code> when value is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testNotEquals_NullValue() {
        try {
            ComparisonExpression.notEquals(CRITERIA, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>notEquals(TimeEntryCriteria, String)</code>.
     * </p>
     */
    public void testNotEquals_Accuracy() {
        expression = ComparisonExpression.notEquals(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA,
            (TimeEntryCriteria) V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "criteria"));
        assertEquals("The value should be correct.", VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "value"));
        assertEquals("The operator should be correct.", "<>",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", CRITERIA.getName() + " <> " + VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getValue()</code>.
     * </p>
     */
    public void testNotEquals_GetValueAccuracy() {
        expression = ComparisonExpression.notEquals(CRITERIA, VALUE);

        // check
        assertEquals("The value should be correct.", VALUE, expression.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testNotEquals_GetCriteriaAccuracy() {
        expression = ComparisonExpression.notEquals(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA, expression.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testNotEquals_GetSearchExpressionStringAccuracy() {
        expression = ComparisonExpression.notEquals(CRITERIA, VALUE);

        // check
        assertEquals("The expression should be correct.", CRITERIA.getName() + " <> " + VALUE,
            expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator()</code>.
     * </p>
     */
    public void testNotEquals_GetOperatorAccuracy() {
        expression = ComparisonExpression.notEquals(CRITERIA, VALUE);

        // check
        assertEquals("The operator should be correct.", "<>", expression.getOperator());
    }

    /**
     * <p>
     * Tests the method <code>greaterThan(TimeEntryCriteria, String)</code> when criteria is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGreaterThan_NullCriteria() {
        try {
            ComparisonExpression.greaterThan(null, VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>greaterThan(TimeEntryCriteria, String)</code> when value is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGreaterThan_NullValue() {
        try {
            ComparisonExpression.greaterThan(CRITERIA, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>greaterThan(TimeEntryCriteria, String)</code>.
     * </p>
     */
    public void testGreaterThan_Accuracy() {
        expression = ComparisonExpression.greaterThan(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA,
            (TimeEntryCriteria) V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "criteria"));
        assertEquals("The value should be correct.", VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "value"));
        assertEquals("The operator should be correct.", ">",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", CRITERIA.getName() + " > " + VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getValue()</code>.
     * </p>
     */
    public void testGreaterThan_GetValueAccuracy() {
        expression = ComparisonExpression.greaterThan(CRITERIA, VALUE);

        // check
        assertEquals("The value should be correct.", VALUE, expression.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testGreaterThan_GetCriteriaAccuracy() {
        expression = ComparisonExpression.greaterThan(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA, expression.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testGreaterThan_GetSearchExpressionStringAccuracy() {
        expression = ComparisonExpression.greaterThan(CRITERIA, VALUE);

        // check
        assertEquals("The expression should be correct.", CRITERIA.getName() + " > " + VALUE,
            expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator()</code>.
     * </p>
     */
    public void testGreaterThan_GetOperatorAccuracy() {
        expression = ComparisonExpression.greaterThan(CRITERIA, VALUE);

        // check
        assertEquals("The operator should be correct.", ">", expression.getOperator());
    }

    /**
     * <p>
     * Tests the method <code>greaterThanOrEquals(TimeEntryCriteria, String)</code> when criteria is <code>null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGreaterThanOrEquals_NullCriteria() {
        try {
            ComparisonExpression.greaterThanOrEquals(null, VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>greaterThanOrEquals(TimeEntryCriteria, String)</code> when value is <code>null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGreaterThanOrEquals_NullValue() {
        try {
            ComparisonExpression.greaterThanOrEquals(CRITERIA, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>greaterThanOrEquals(TimeEntryCriteria, String)</code>.
     * </p>
     */
    public void testGreaterThanOrEquals_Accuracy() {
        expression = ComparisonExpression.greaterThanOrEquals(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA,
            (TimeEntryCriteria) V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "criteria"));
        assertEquals("The value should be correct.", VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "value"));
        assertEquals("The operator should be correct.", ">=",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", CRITERIA.getName() + " >= " + VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getValue()</code>.
     * </p>
     */
    public void testGreaterThanOrEquals_GetValueAccuracy() {
        expression = ComparisonExpression.greaterThanOrEquals(CRITERIA, VALUE);

        // check
        assertEquals("The value should be correct.", VALUE, expression.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testGreaterThanOrEquals_GetCriteriaAccuracy() {
        expression = ComparisonExpression.greaterThanOrEquals(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA, expression.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testGreaterThanOrEquals_GetSearchExpressionStringAccuracy() {
        expression = ComparisonExpression.greaterThanOrEquals(CRITERIA, VALUE);

        // check
        assertEquals("The expression should be correct.", CRITERIA.getName() + " >= " + VALUE,
            expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator()</code>.
     * </p>
     */
    public void testGreaterThanOrEquals_GetOperatorAccuracy() {
        expression = ComparisonExpression.greaterThanOrEquals(CRITERIA, VALUE);

        // check
        assertEquals("The operator should be correct.", ">=", expression.getOperator());
    }

    /**
     * <p>
     * Tests the method <code>lessThan(TimeEntryCriteria, String)</code> when criteria is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testLessThan_NullCriteria() {
        try {
            ComparisonExpression.lessThan(null, VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>lessThan(TimeEntryCriteria, String)</code> when value is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testLessThan_NullValue() {
        try {
            ComparisonExpression.lessThan(CRITERIA, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>lessThan(TimeEntryCriteria, String)</code>.
     * </p>
     */
    public void testLessThan_Accuracy() {
        expression = ComparisonExpression.lessThan(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA,
            (TimeEntryCriteria) V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "criteria"));
        assertEquals("The value should be correct.", VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "value"));
        assertEquals("The operator should be correct.", "<",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", CRITERIA.getName() + " < " + VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getValue()</code>.
     * </p>
     */
    public void testLessThan_GetValueAccuracy() {
        expression = ComparisonExpression.lessThan(CRITERIA, VALUE);

        // check
        assertEquals("The value should be correct.", VALUE, expression.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testLessThan_GetCriteriaAccuracy() {
        expression = ComparisonExpression.lessThan(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA, expression.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testLessThan_GetSearchExpressionStringAccuracy() {
        expression = ComparisonExpression.lessThan(CRITERIA, VALUE);

        // check
        assertEquals("The expression should be correct.", CRITERIA.getName() + " < " + VALUE,
            expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator()</code>.
     * </p>
     */
    public void testLessThan_GetOperatorAccuracy() {
        expression = ComparisonExpression.lessThan(CRITERIA, VALUE);

        // check
        assertEquals("The operator should be correct.", "<", expression.getOperator());
    }

    /**
     * <p>
     * Tests the method <code>lessThanOrEquals(TimeEntryCriteria, String)</code> when criteria is <code>null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testLessThanOrEquals_NullCriteria() {
        try {
            ComparisonExpression.lessThanOrEquals(null, VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>lessThanOrEquals(TimeEntryCriteria, String)</code> when value is <code>null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testLessThanOrEquals_NullValue() {
        try {
            ComparisonExpression.lessThanOrEquals(CRITERIA, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>lessThanOrEquals(TimeEntryCriteria, String)</code>.
     * </p>
     */
    public void testLessThanOrEquals_Accuracy() {
        expression = ComparisonExpression.lessThanOrEquals(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA,
            (TimeEntryCriteria) V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "criteria"));
        assertEquals("The value should be correct.", VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "value"));
        assertEquals("The operator should be correct.", "<=",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", CRITERIA.getName() + " <= " + VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getValue()</code>.
     * </p>
     */
    public void testLessThanOrEquals_GetValueAccuracy() {
        expression = ComparisonExpression.lessThanOrEquals(CRITERIA, VALUE);

        // check
        assertEquals("The value should be correct.", VALUE, expression.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testLessThanOrEquals_GetCriteriaAccuracy() {
        expression = ComparisonExpression.lessThanOrEquals(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA, expression.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testLessThanOrEquals_GetSearchExpressionStringAccuracy() {
        expression = ComparisonExpression.lessThanOrEquals(CRITERIA, VALUE);

        // check
        assertEquals("The expression should be correct.", CRITERIA.getName() + " <= " + VALUE,
            expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator()</code>.
     * </p>
     */
    public void testLessThanOrEquals_GetOperatorAccuracy() {
        expression = ComparisonExpression.lessThanOrEquals(CRITERIA, VALUE);

        // check
        assertEquals("The operator should be correct.", "<=", expression.getOperator());
    }
}
