/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.math.BigDecimal;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;

/**
 * <p>
 * Failure test cases of <code>ExpenseEntry</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class ExpenseEntryFailureTests extends TestCase {
    /** 
     * Represents the <code>ExpenseEntry</code> instance used for testing.
     */
    private ExpenseEntry expenseEntry = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        expenseEntry = new ExpenseEntry();
    }

    /**
     * <p>
     * Tests the constructor <code>ExpenseEntry(long id)</code> when the given id is not positive,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testExpenseEntry_LongInvalidId() {
        try {
            new ExpenseEntry(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setMileage(int mileage)</code> when the given mileage is negative,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetMileage_InvalidMileage() {
        try {
            expenseEntry.setMileage(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setAmount(BigDecimal amount)</code> when the given amount is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAmount_NullAmount() {
        try {
            expenseEntry.setAmount(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setAmount(BigDecimal amount)</code> when the given amount is negative,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAmount_NegativeAmount() {
        try {
            expenseEntry.setAmount(new BigDecimal("-1"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setExpenseType(ExpenseType expenseType)</code> when the given expenseType is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetExpenseType_NullExpenseType() {
        try {
            expenseEntry.setExpenseType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setStatus(ExpenseStatus status)</code> when the given status is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetStatus_NullStatus() {
        try {
            expenseEntry.setStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setInvoice(Invoice invoice)</code> when the given invoice is null, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetInvoice_NullInvoice() {
        expenseEntry.setInvoice(null);
        assertEquals("Failed to set the invoice", null, expenseEntry.getInvoice());
        assertFalse("The changed flag should be correct.", expenseEntry.isChanged());
    }
}
