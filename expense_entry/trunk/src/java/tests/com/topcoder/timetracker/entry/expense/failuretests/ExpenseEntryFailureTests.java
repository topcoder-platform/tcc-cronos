/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.math.BigDecimal;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;

/**
 * Failure tests for <c>ExpenseEntry</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class ExpenseEntryFailureTests extends TestCase {

    /**
     * <c>ExpenseEntry</c> instance to test on.
     */
    private ExpenseEntry expenseEntry = new ExpenseEntry();

    /**
     * Tests <c>setDate</c> method failure.
     */
    public void testSetDateNull() {
        try {
            expenseEntry.setDate(null);
            fail("NPE expected, null value");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * Tests <c>setAmount</c> method failure.
     */
    public void testSetAmountNull() {
        try {
            expenseEntry.setAmount(null);
            fail("NPE expected, null value");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * Tests <c>setAmount</c> method failure.
     */
    public void testSetAmountNegative() {
        try {
            expenseEntry.setAmount(new BigDecimal(-1));
            fail("IPE expected, value is negative");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests <c>setExpenseType</c> method failure.
     */
    public void testSetExpenseTypeNull() {
        try {
            expenseEntry.setExpenseType(null);
            fail("NPE expected, null value");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * Tests <c>setStatus</c> method failure.
     */
    public void testSetStatusNull() {
        try {
            expenseEntry.setStatus(null);
            fail("NPE expected, null value");
        } catch (NullPointerException e) {
            // ok
        }
    } 

}
