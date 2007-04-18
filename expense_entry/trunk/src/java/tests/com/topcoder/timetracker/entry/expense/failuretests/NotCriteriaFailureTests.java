/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.criteria.NotCriteria;

/**
 * <p>
 * Failure test cases of <code>NotCriteria</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class NotCriteriaFailureTests extends TestCase {
    /**
     * <p>
     * Tests constructor <code>NotCriteria(Criteria)</code> when criteria is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testNotCriteria_NullCriteria() {
        try {
            new NotCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
