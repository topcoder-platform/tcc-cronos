/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.search.BooleanExpression;
import com.topcoder.timetracker.entry.time.search.ComparisonExpression;
import com.topcoder.timetracker.entry.time.search.RangeExpression;
import com.topcoder.timetracker.entry.time.search.SubstringExpression;
import com.topcoder.timetracker.entry.time.search.TimeEntryCriteria;

/**
 * <p>
 * Failure tests for <code>SearchExpression</code>s.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
public class ExpressionsFailureTests extends TestCase {
    /**
     * <p>
     * Failure test for BooleanExpression.and(SearchExpression left, SearchExpression right),
     * with null left SearchExpression, IAE is expected.
     * </p>
     */
    public void testBooleanExpressionAndNullLeftSearchExpression() {
        try {
            BooleanExpression.and(null, new MockSearchExpression());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for BooleanExpression.and(SearchExpression left, SearchExpression right),
     * with null right SearchExpression, IAE is expected.
     * </p>
     */
    public void testBooleanExpressionAndNullRightSearchExpression() {
        try {
            BooleanExpression.and(new MockSearchExpression(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for BooleanExpression.or(SearchExpression left, SearchExpression right),
     * with null left SearchExpression, IAE is expected.
     * </p>
     */
    public void testBooleanExpressionOrNullLeftSearchExpression() {
        try {
            BooleanExpression.or(null, new MockSearchExpression());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for BooleanExpression.or(SearchExpression left, SearchExpression right),
     * with null right SearchExpression, IAE is expected.
     * </p>
     */
    public void testBooleanExpressionOrNullRightSearchExpression() {
        try {
            BooleanExpression.or(new MockSearchExpression(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    
    /**
     * <p>
     * Failure test for BooleanExpression.not(SearchExpression expression),
     * with null SearchExpression, IAE is expected.
     * </p>
     */
    public void testBooleanExpressionOrNotRightSearchExpression() {
        try {
            BooleanExpression.not(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for ComparisonExpression.equals(TimeEntryCriteria criteria, String value),
     * with null TimeEntryCriteria, IAE is expected.
     * </p>
     */
    public void testComparisonExpressionEqualsNullTimeEntryCriteria() {
        try {
            ComparisonExpression.equals(null, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
       
    /**
     * <p>
     * Failure test for ComparisonExpression.equals(TimeEntryCriteria criteria, String value),
     * with null value, IAE is expected.
     * </p>
     */
    public void testComparisonExpressionEqualsNullValue() {
        try {
            ComparisonExpression.equals(TimeEntryCriteria.BILLABLE_FLAG, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for RangeExpression.fromTo(TimeEntryCriteria criteria, String from, String to),
     * with null TimeEntryCriteria, IAE is expected.
     * </p>
     */
    public void testRangeExpressionFromToNullTimeEntryCriteria() {
        try {
            RangeExpression.fromTo(null, "from", "to");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
       
    /**
     * <p>
     * Failure test for RangeExpression.fromTo(TimeEntryCriteria criteria, String from, String to),
     * with null from, IAE is expected.
     * </p>
     */
    public void testRangeExpressionFromToNullFrom() {
        try {
            RangeExpression.fromTo(TimeEntryCriteria.BILLABLE_FLAG, null, "to");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for RangeExpression.fromTo(TimeEntryCriteria criteria, String from, String to),
     * with null to, IAE is expected.
     * </p>
     */
    public void testRangeExpressionFromToNullTo() {
        try {
            RangeExpression.fromTo(TimeEntryCriteria.BILLABLE_FLAG, "from", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for RangeExpression.fromTo(TimeEntryCriteria criteria, String from),
     * with null TimeEntryCriteria, IAE is expected.
     * </p>
     */
    public void testRangeExpressionFromTo2NullTimeEntryCriteria() {
        try {
            RangeExpression.from(null, "from");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for RangeExpression.fromTo(TimeEntryCriteria criteria, String from),
     * with null from, IAE is expected.
     * </p>
     */
    public void testRangeExpressionFromTo2NullFrom() {
        try {
            RangeExpression.from(TimeEntryCriteria.BILLABLE_FLAG, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for SubstringExpression.contains(TimeEntryCriteria criteria, String value),
     * with null TimeEntryCriteria, IAE is expected.
     * </p>
     */
    public void testSubstringExpressionContainsNullTimeEntryCriteria() {
        try {
            SubstringExpression.contains(null, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Failure test for SubstringExpression.contains(TimeEntryCriteria criteria, String value),
     * with null value, IAE is expected.
     * </p>
     */
    public void testSubstringExpressionContainsNullValue() {
        try {
            SubstringExpression.contains(TimeEntryCriteria.BILLABLE_FLAG, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
