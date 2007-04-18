/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.criteria.FieldLikeCriteria;

/**
 * <p>
 * Failure test cases of <code>FieldLikeCriteria</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class FieldLikeCriteriaFailureTests extends TestCase {
    /**
     * Represents the field which will be used to create the new FieldLikeCriteria instance for testing.
     */
    private static final String FIELD = "field";

    /**
     * Represents the pattern which will be used to create the new <code>FieldLikeCriteria</code> instance for testing.
     */
    private static final String PATTERN = "pattern";

    /**
     * <p>
     * Tests constructor <code>FieldLikeCriteria(String, String)</code> when field is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldLikeCriteria_NullField() {
        try {
            new FieldLikeCriteria(null, PATTERN);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldLikeCriteria(String, String)</code> when pattern is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldLikeCriteria_NullPattern() {
        try {
            new FieldLikeCriteria(FIELD, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldLikeCriteria(String, String)</code> when field is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldLikeCriteria_EmptyField() {
        try {
            new FieldLikeCriteria(" ", PATTERN);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldLikeCriteria(String, String)</code> when pattern is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldLikeCriteria_EmptyPattern() {
        try {
            new FieldLikeCriteria(FIELD, " ");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>getDescriptionContainsCriteria(String)</code> when value is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetDescriptionContainsCriteria_NullValue() {
        try {
            FieldLikeCriteria.getDescriptionContainsCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>getDescriptionContainsCriteria(String)</code> when value is length of zero. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetDescriptionContainsCriteria_ZeroLengthValue() {
        try {
            FieldLikeCriteria.getDescriptionContainsCriteria("");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>getExpenseStatusDescriptionContainsCriteria(String)</code> when value is <code>null
     * </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseStatusDescriptionContainsCriteria_NullValue() {
        try {
            FieldLikeCriteria.getExpenseStatusDescriptionContainsCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>getExpenseStatusDescriptionContainsCriteria(String)</code> when value is length of zero.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseStatusDescriptionContainsCriteria_ZeroLengthValue() {
        try {
            FieldLikeCriteria.getExpenseStatusDescriptionContainsCriteria("");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>getExpenseTypeDescriptionContainsCriteria(String)</code> when value is <code>null
     * </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseTypeDescriptionContainsCriteria_NullValue() {
        try {
            FieldLikeCriteria.getExpenseTypeDescriptionContainsCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>getExpenseTypeDescriptionContainsCriteria(String)</code> when value is length of zero.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseTypeDescriptionContainsCriteria_ZeroLengthValue() {
        try {
            FieldLikeCriteria.getExpenseTypeDescriptionContainsCriteria("");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
