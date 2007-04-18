/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;


/**
 * <p>
 * Tests functionality and error cases of <code>FieldMatchCriteria</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class FieldMatchCriteriaFailureTests extends TestCase {
    /**
     * Represents the value which will be used to create the new <code>FieldMatchCriteria</code> instance for testing.
     */
    private static final String VALUE = "value";

    /**
     * <p>
     * Tests constructor <code>FieldMatchCriteria(String, Object)</code> when field is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldMatchCriteria_NullField() {
        try {
            new FieldMatchCriteria(null, VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldMatchCriteria(String, Object)</code> when field is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldMatchCriteria_EmptyField() {
        try {
            new FieldMatchCriteria(" ", VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getCreationUserMatchCriteria(String)</code> when user is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetCreationUserMatchCriteria_NullUser() {
        try {
            FieldMatchCriteria.getCreationUserMatchCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getCreationUserMatchCriteria(String)</code> when user is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetCreationUserMatchCriteria_EmptyUser() {
        try {
            FieldMatchCriteria.getCreationUserMatchCriteria(" ");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getModificationUserMatchCriteria(String)</code> when user is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetModificationUserMatchCriteria_NullUser() {
        try {
            FieldMatchCriteria.getModificationUserMatchCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getModificationUserMatchCriteria(String)</code> when user is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetModificationUserMatchCriteria_EmptyUser() {
        try {
            FieldMatchCriteria.getModificationUserMatchCriteria(" ");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusCreationUserMatchCriteria(String)</code> when user is
     * <code>null</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseStatusCreationUserMatchCriteria_NullUser() {
        try {
            FieldMatchCriteria.getExpenseStatusCreationUserMatchCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusCreationUserMatchCriteria(String)</code> when user is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseStatusCreationUserMatchCriteria_EmptyUser() {
        try {
            FieldMatchCriteria.getExpenseStatusCreationUserMatchCriteria(" ");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusModificationUserMatchCriteria(String)</code> when user is
     * <code>null</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseStatusModificationUserMatchCriteria_NullUser() {
        try {
            FieldMatchCriteria.getExpenseStatusModificationUserMatchCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusModificationUserMatchCriteria(String)</code> when user is empty.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseStatusModificationUserMatchCriteria_EmptyUser() {
        try {
            FieldMatchCriteria.getExpenseStatusModificationUserMatchCriteria(" ");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseTypeCreationUserMatchCriteria(String)</code> when user is <code>null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseTypeCreationUserMatchCriteria_NullUser() {
        try {
            FieldMatchCriteria.getExpenseTypeCreationUserMatchCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseTypeCreationUserMatchCriteria(String)</code> when user is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseTypeCreationUserMatchCriteria_EmptyUser() {
        try {
            FieldMatchCriteria.getExpenseTypeCreationUserMatchCriteria(" ");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseTypeModificationUserMatchCriteria(String)</code> when user is
     * <code>null</code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseTypeModificationUserMatchCriteria_NullUser() {
        try {
            FieldMatchCriteria.getExpenseTypeModificationUserMatchCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseTypeModificationUserMatchCriteria(String)</code> when user is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseTypeModificationUserMatchCriteria_EmptyUser() {
        try {
            FieldMatchCriteria.getExpenseTypeModificationUserMatchCriteria(" ");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
