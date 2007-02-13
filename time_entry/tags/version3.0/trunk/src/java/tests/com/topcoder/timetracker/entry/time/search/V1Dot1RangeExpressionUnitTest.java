/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.search;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.V1Dot1TestHelper;


/**
 * <p>
 * Tests functionality and error cases of <code>RangeExpression</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class V1Dot1RangeExpressionUnitTest extends TestCase {
    /**
     * Represents the criteria which will be used to create the new <code>RangeExpression</code> instance for testing.
     */
    private static final TimeEntryCriteria CRITERIA = TimeEntryCriteria.CREATION_USER;

    /**
     * Represents the from value which will be used to create the new <code>RangeExpression</code> instance for
     * testing.
     */
    private static final String FROM = "from";

    /**
     * Represents the to value which will be used to create the new <code>RangeExpression</code> instance for testing.
     */
    private static final String TO = "to";

    /** Represents the <code>RangeExpression</code> instance which will be used for testing. */
    private RangeExpression expression = null;

    /**
     * <p>
     * Tests the method <code>fromTo(TimeEntryCriteria, String, String)</code> when criteria is <code>null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testFromTo_NullCriteria() {
        try {
            RangeExpression.fromTo(null, FROM, TO);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>fromTo(TimeEntryCriteria, String, String)</code> when from is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFromTo_NullFROM() {
        try {
            RangeExpression.fromTo(CRITERIA, null, TO);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>fromTo(TimeEntryCriteria, String, String)</code> when to is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFromTo_NullTo() {
        try {
            RangeExpression.fromTo(CRITERIA, FROM, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>fromTo(TimeEntryCriteria, String, String)</code>.
     * </p>
     */
    public void testFromTo_Accuracy() {
        expression = RangeExpression.fromTo(CRITERIA, FROM, TO);

        // check
        assertEquals("The criteria should be correct.", CRITERIA,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "criteria"));
        assertEquals("The from should be correct.", FROM,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "from"));
        assertEquals("The to should be correct.", TO,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "to"));
        assertEquals("The operator should be correct.", "[...]",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", CRITERIA.getName() + " " + FROM + " [...] " + TO,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getFromValue()</code>.
     * </p>
     */
    public void testFromTo_GetFromValue_Accuracy() {
        expression = RangeExpression.fromTo(CRITERIA, FROM, TO);

        // check
        assertEquals("The from should be correct.", FROM, expression.getFromValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getToValue()</code>.
     * </p>
     */
    public void testFromTo_GetToValue_Accuracy() {
        expression = RangeExpression.fromTo(CRITERIA, FROM, TO);

        // check
        assertEquals("The to should be correct.", TO, expression.getToValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testFromTo_GetSearchExpressionStringAccuracy() {
        expression = RangeExpression.fromTo(CRITERIA, FROM, TO);

        // check
        assertEquals("The expression should be correct.", CRITERIA.getName() + " " + FROM + " [...] " + TO,
            expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testFromTo_GetCriteriaAccuracy() {
        expression = RangeExpression.fromTo(CRITERIA, FROM, TO);

        // check
        assertEquals("The criteria should be correct.", CRITERIA, expression.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator()</code>.
     * </p>
     */
    public void testFromTo_GetOperatorAccuracy() {
        expression = RangeExpression.fromTo(CRITERIA, FROM, TO);

        // check
        assertEquals("The operator should be correct.", "[...]", expression.getOperator());
    }

    /**
     * <p>
     * Tests the method <code>from(TimeEntryCriteria, String)</code> when criteria is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFrom_NullCriteria() {
        try {
            RangeExpression.from(null, FROM);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>from(TimeEntryCriteria, String)</code> when from is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFrom_NullFROM() {
        try {
            RangeExpression.from(CRITERIA, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>from(TimeEntryCriteria, String)</code>.
     * </p>
     */
    public void testFrom_Accuracy() {
        expression = RangeExpression.from(CRITERIA, FROM);

        // check
        assertEquals("The criteria should be correct.", CRITERIA,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "criteria"));
        assertEquals("The from should be correct.", FROM,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "from"));
        assertNull("The to should be correct.",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "to"));
        assertEquals("The operator should be correct.", "[...",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", CRITERIA.getName() + " " + FROM + " [...",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getFromValue()</code>.
     * </p>
     */
    public void testFrom_GetFromValue_Accuracy() {
        expression = RangeExpression.from(CRITERIA, FROM);

        // check
        assertEquals("The from should be correct.", FROM, expression.getFromValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getToValue()</code>.
     * </p>
     */
    public void testFrom_GetToValue_Accuracy() {
        expression = RangeExpression.from(CRITERIA, FROM);

        // check
        assertNull("The to should be correct.", expression.getToValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testFrom_GetSearchExpressionStringAccuracy() {
        expression = RangeExpression.from(CRITERIA, FROM);

        // check
        assertEquals("The expression should be correct.", CRITERIA.getName() + " " + FROM + " [...",
            expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testFrom_GetCriteriaAccuracy() {
        expression = RangeExpression.from(CRITERIA, FROM);

        // check
        assertEquals("The criteria should be correct.", CRITERIA, expression.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator()</code>.
     * </p>
     */
    public void testFrom_GetOperatorAccuracy() {
        expression = RangeExpression.from(CRITERIA, FROM);

        // check
        assertEquals("The operator should be correct.", "[...", expression.getOperator());
    }

    /**
     * <p>
     * Tests the method <code>to(TimeEntryCriteria, String)</code> when criteria is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testTo_NullCriteria() {
        try {
            RangeExpression.to(null, TO);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>to(TimeEntryCriteria, String)</code> when to is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testTo_NullFROM() {
        try {
            RangeExpression.to(CRITERIA, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>to(TimeEntryCriteria, String)</code>.
     * </p>
     */
    public void testTo_Accuracy() {
        expression = RangeExpression.to(CRITERIA, TO);

        // check
        assertEquals("The criteria should be correct.", CRITERIA,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "criteria"));
        assertNull("The from should be correct.",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "from"));
        assertEquals("The to should be correct.", TO,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "to"));
        assertEquals("The operator should be correct.", "...]",
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "operator"));
        assertEquals("The expression should be correct.", CRITERIA.getName() + " ...] " + TO,
            V1Dot1TestHelper.getPrivateField(expression.getClass(), expression, "expression"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getToValue()</code>.
     * </p>
     */
    public void testTo_GetFromValue_Accuracy() {
        expression = RangeExpression.to(CRITERIA, TO);

        // check
        assertNull("The from should be correct.", expression.getFromValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getToValue()</code>.
     * </p>
     */
    public void testTo_GetToValue_Accuracy() {
        expression = RangeExpression.to(CRITERIA, TO);

        // check
        assertEquals("The to should be correct.", TO, expression.getToValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSearchExpressionString()</code>.
     * </p>
     */
    public void testTo_GetSearchExpressionStringAccuracy() {
        expression = RangeExpression.to(CRITERIA, TO);

        // check
        assertEquals("The expression should be correct.", CRITERIA.getName() + " ...] " + TO,
            expression.getSearchExpressionString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testTo_GetCriteriaAccuracy() {
        expression = RangeExpression.to(CRITERIA, TO);

        // check
        assertEquals("The criteria should be correct.", CRITERIA, expression.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOperator()</code>.
     * </p>
     */
    public void testTo_GetOperatorAccuracy() {
        expression = RangeExpression.to(CRITERIA, TO);

        // check
        assertEquals("The operator should be correct.", "...]", expression.getOperator());
    }
}
