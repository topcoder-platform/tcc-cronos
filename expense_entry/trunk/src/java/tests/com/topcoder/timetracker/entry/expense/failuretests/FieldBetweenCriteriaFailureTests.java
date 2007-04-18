/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.criteria.FieldBetweenCriteria;

/**
 * <p>
 * Failure test cases of <code>FieldBetweenCriteria</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class FieldBetweenCriteriaFailureTests extends TestCase {
    /**
     * Represents the field which will be used to create the new <code>FieldBetweenCriteria</code> instance for
     * testing.
     */
    private static final String FIELD = "field";

    /**
     * Represents the from value which will be used to create the new <code>FieldBetweenCriteria</code> instance for
     * testing.
     */
    private static final String FROM_VALUE = "fromValue";

    /**
     * Represents the to value which will be used to create the new <code>FieldBetweenCriteria</code> instance for
     * testing.
     */
    private static final String TO_VALUE = "toValue";

    /**
     * <p>
     * Tests constructor <code>FieldBetweenCriteria(String, Object, Object)</code> when field is <code>null </code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testFieldBetweenCriteria_NullField() {
        try {
            new FieldBetweenCriteria(null, FROM_VALUE, TO_VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldBetweenCriteria(String, Object, Object)</code> when field is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldBetweenCriteria_EmptyField() {
        try {
            new FieldBetweenCriteria(" ", FROM_VALUE, TO_VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldBetweenCriteria(String, Object, Object)</code> when both the fromValue and toValue
     * are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testFieldBetweenCriteria_NullFromValueNullToValue() {
        try {
            new FieldBetweenCriteria(FIELD, null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getAmountBetweenCriteria(BigDecimal, BigDecimal)</code> when both the fromValue and
     * toValue are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetAmountBetweenCriteria_NullFromValueNullToValue() {
        try {
            FieldBetweenCriteria.getAmountBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getCreationDateBetweenCriteria(Date, Date)</code> when both the fromDate and toDate
     * are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetCreationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getCreationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusCreationDateBetweenCriteria(Date, Date)</code> when both
     * the fromDate and toDate are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseStatusCreationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getExpenseStatusCreationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusModificationDateBetweenCriteria(Date, Date)</code>
     * when both the fromDate and toDate
     * are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseStatusModificationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getExpenseStatusModificationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusCreationDateBetweenCriteria(Date, Date)</code>
     * when both the fromDate and toDate are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseTypeCreationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getExpenseTypeCreationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseTypeModificationDateBetweenCriteria(Date, Date)</code>
     * when both the fromDate and toDate
     * are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseTypeModificationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getExpenseTypeModificationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getModificationDateBetweenCriteria(Date, Date)</code> when both the fromDate and
     * toDate are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetModificationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getModificationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
