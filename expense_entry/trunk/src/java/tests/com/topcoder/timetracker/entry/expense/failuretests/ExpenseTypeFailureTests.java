/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ExpenseType;

/**
 * <p>
 * Failure test cases of <code>ExpenseType</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class ExpenseTypeFailureTests extends TestCase {
    /**
     * Represents the <code>ExpenseType</code> instance used for testing.
     */
    private ExpenseType expenseType = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        expenseType = new ExpenseType();
    }

    /**
     * <p>
     * Tests the constructor <code>ExpenseType(long id)</code> when the given id is not positive,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testExpenseType_LongInvalidId() {
        try {
            new ExpenseType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setCompanyId(long companyId)</code> when the given companyId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyId_InvalidCompanyId() {
        try {
            expenseType.setCompanyId(-1);
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
        expenseType.setDescription(null);
        assertEquals("Failed to set the description", null, expenseType.getDescription());
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is empty string, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_EmptyDescription1() {
        expenseType.setDescription("");
        assertEquals("Failed to set the description", "", expenseType.getDescription());
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is empty string, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_EmptyDescription2() {
        expenseType.setDescription(" ");
        assertEquals("Failed to set the description", " ", expenseType.getDescription());
    }
}
