/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.criteria.CompositeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;

/**
 * <p>
 * Failure test cases of <code>CompositeCriteria</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class CompositeCriteriaFailureTests extends TestCase {
	/**
     * Represents the criteria instances which will be used to create the new <code>CompositeCriteria</code>
     * instance for testing.
     */
    private Criteria[] criterias = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        criterias = new Criteria[5];
        criterias[0] = FieldMatchCriteria.getBillableMatchCriteria(true);
        criterias[1] = FieldMatchCriteria.getCreationUserMatchCriteria("creationUser");
        criterias[2] = FieldMatchCriteria.getModificationUserMatchCriteria("modificationUser");
        criterias[3] = FieldMatchCriteria.getExpenseStatusMatchCriteria(1);
        criterias[4] = FieldMatchCriteria.getExpenseTypeMatchCriteria(2);
    }

    /**
     * <p>
     * Tests constructor <code>CompositeCriteria(String, Criteria[])</code> when compositionKeyword is <code>null
     * </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testCompositeCriteria_NullCompositionKeyword() {
        try {
            new CompositeCriteria(null, criterias);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>CompositeCriteria(String, Criteria[])</code> when criteria is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testCompositeCriteria_NullCriteria() {
        try {
            new CompositeCriteria(CompositeCriteria.AND_COMPOSITION, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>CompositeCriteria(String, Criteria[])</code> when criteria collection's size is less
     * than 2. Expect IllegalArgumentException.
     * </p>
     */
    public void testCompositeCriteria_CriteriaSizeLessThanTwo() {
        try {
            new CompositeCriteria(CompositeCriteria.AND_COMPOSITION, new Criteria[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>CompositeCriteria(String, Criteria[])</code> when criteria collection contains <code>
     * null</code> element. Expect IllegalArgumentException.
     * </p>
     */
    public void testCompositeCriteria_CriteriaContainsNullElement() {
        try {
            this.criterias[0] = null;
            new CompositeCriteria(CompositeCriteria.AND_COMPOSITION, criterias);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getAndCompositeCriteria(Criteria, Criteria)</code> when left is <code> null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetAndCompositeCriteria_NullLeft() {
        try {
            CompositeCriteria.getAndCompositeCriteria(null, criterias[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getAndCompositeCriteria(Criteria, Criteria)</code> when right is <code> null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetAndCompositeCriteria_NullRight() {
        try {
            CompositeCriteria.getAndCompositeCriteria(criterias[0], null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getAndCompositeCriteria(Criteria[])</code> when criteria is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetAndCompositeCriteria_NullCriteria() {
        try {
            CompositeCriteria.getAndCompositeCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of  <code>getAndCompositeCriteria(Criteria[])</code> when criteria collection's size is less
     * than 2. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetAndCompositeCriteria_CriteriaSizeLessThanTwo() {
        try {
            CompositeCriteria.getAndCompositeCriteria(new Criteria[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of  <code>getAndCompositeCriteria(Criteria[])</code> when criteria collection contains <code>
     * null</code> element. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetAndCompositeCriteria_CriteriaContainsNullElement() {
        try {
            this.criterias[0] = null;
            CompositeCriteria.getAndCompositeCriteria(criterias);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getOrCompositeCriteria(Criteria, Criteria)</code> when left is <code> null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetOrCompositeCriteria_NullLeft() {
        try {
            CompositeCriteria.getOrCompositeCriteria(null, criterias[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getOrCompositeCriteria(Criteria, Criteria)</code> when right is <code> null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetOrCompositeCriteria_NullRight() {
        try {
            CompositeCriteria.getOrCompositeCriteria(criterias[0], null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getOrCompositeCriteria(Criteria[])</code> when criteria is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetOrCompositeCriteria_NullCriteria() {
        try {
            CompositeCriteria.getOrCompositeCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of  <code>getOrCompositeCriteria(Criteria[])</code> when criteria collection's size is less
     * than 2. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetOrCompositeCriteria_CriteriaSizeLessThanTwo() {
        try {
            CompositeCriteria.getOrCompositeCriteria(new Criteria[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of  <code>getOrCompositeCriteria(Criteria[])</code> when criteria collection contains <code>
     * null</code> element. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetOrCompositeCriteria_CriteriaContainsNullElement() {
        try {
            this.criterias[0] = null;
            CompositeCriteria.getOrCompositeCriteria(criterias);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
