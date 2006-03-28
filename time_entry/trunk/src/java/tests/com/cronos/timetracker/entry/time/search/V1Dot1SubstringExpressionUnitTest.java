/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

import com.cronos.timetracker.entry.time.V1Dot1TestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>SubstringExpression</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1SubstringExpressionUnitTest extends TestCase {
    /**
     * Represents the criteria which will be used to create the new <code>SubstringExpression</code> instance for
     * testing.
     */
    private static final TimeEntryCriteria CRITERIA = TimeEntryCriteria.CREATION_USER;

    /**
     * Represents the value which will be used to create the new <code>SubstringExpression</code> instance for testing.
     */
    private static final String VALUE = "value";

    /** Represents the <code>SubstringExpression</code> instance which will be used for testing. */
    private SubstringExpression expression = null;

    /**
     * <p>
     * Tests the method <code>contains(TimeEntryCriteria, String)</code> when criteria is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testContains_NullCriteria() {
        try {
            SubstringExpression.contains(null, VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>contains(TimeEntryCriteria, String)</code> when value is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testContains_NullValue() {
        try {
            SubstringExpression.contains(CRITERIA, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>contains(TimeEntryCriteria, String)</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testContains_Accuracy() {
        expression = SubstringExpression.contains(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "criteria"));
        assertEquals("The value should be correct.", VALUE,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "value"));
        assertEquals("The operator should be correct.", "LIKE",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", CRITERIA.getName() + " LIKE '%" + VALUE + "%'",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getValue()</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testContains_GetValueAccuracy() {
        expression = SubstringExpression.contains(CRITERIA, VALUE);

        // check
        assertEquals("The value should be correct.", VALUE, expression.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testContains_GetSearchExpressionStringAccuracy() {
        expression = SubstringExpression.contains(CRITERIA, VALUE);

        // check
        assertEquals("The expression should be correct.", CRITERIA.getName() + " LIKE '%" + VALUE + "%'",
            expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testContains_GetCriteriaAccuracy() {
        expression = SubstringExpression.contains(CRITERIA, VALUE);

        // check
        assertEquals("The criteria should be correct.", CRITERIA, expression.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator()</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testContains_GetOperatorAccuracy() {
        expression = SubstringExpression.contains(CRITERIA, VALUE);

        // check
        assertEquals("The operator should be correct.", "LIKE", expression.getOperator());
    }
}
