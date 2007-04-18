/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ExpenseStatus;

/**
 * <p>
 * Failure test cases of <code>ExpenseStatus</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class ExpenseStatusFailureTests extends TestCase {
    /**
     * Represents the <code>ExpenseStatus</code> instance used for testing.
     */
    private ExpenseStatus expenseStatus = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        expenseStatus = new ExpenseStatus();
    }

    /**
     * <p>
     * Tests the constructor <code>ExpenseStatus(long id)</code> when the given id is not positive,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testExpenseStatus_LongInvalidId() {
        try {
            new ExpenseStatus(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is null, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_NullDescription() {
        expenseStatus.setDescription(null);
        assertEquals("Failed to set the description", null, expenseStatus.getDescription());
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is empty string, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_EmptyDescription1() {
        expenseStatus.setDescription("");
        assertEquals("Failed to set the description", "", expenseStatus.getDescription());
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is empty string, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_EmptyDescription2() {
        expenseStatus.setDescription(" ");
        assertEquals("Failed to set the description", " ", expenseStatus.getDescription());
    }
}
