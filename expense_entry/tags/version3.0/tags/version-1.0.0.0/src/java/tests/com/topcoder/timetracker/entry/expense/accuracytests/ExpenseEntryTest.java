/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.math.BigDecimal;

import java.util.Date;


/**
 * <p>
 * This class contains the accuracy unit tests for ExpenseEntry.java.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ExpenseEntryTest extends TestCase {
    /**
     * <p>
     * Represents the ExpenseEntry instance for testing.
     * </p>
     */
    private ExpenseEntry expenseEntry = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    protected void setUp() {
        expenseEntry = new ExpenseEntry();
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryTest.class);
    }

    /**
     * <p>
     * Tests accuracy of getDate() method.
     * </p>
     */
    public void testGetDateAccuracy() {
        assertNull("the date should be correctly got.", expenseEntry.getDate());
    }

    /**
     * <p>
     * Tests accuracy of SetDate() method.
     * </p>
     */
    public void testSetDateAccuracy() {
        Date date = new Date();
        expenseEntry.setDate(date);
        assertEquals("the date should be correctly set.", date, expenseEntry.getDate());
    }

    /**
     * <p>
     * Tests accuracy of getAmount() method. Default is null.
     * </p>
     */
    public void testGetAmountAccuracy() {
        assertNull("The amount should be correctly got.", expenseEntry.getAmount());
    }

    /**
     * <p>
     * Tests accuracy of setAmount() method.
     * </p>
     */
    public void testSetAmountAccuracy() {
        BigDecimal decimal = new BigDecimal("10000000");
        expenseEntry.setAmount(decimal);

        assertEquals("The amount should be correctly set.", decimal, expenseEntry.getAmount());
    }

    /**
     * <p>
     * Tests accuracy of getExpenseType() method. Default is null.
     * </p>
     */
    public void testGetExpenseTypeAccuracy() {
        assertNull("The expense type should be correctly got.", expenseEntry.getExpenseType());
    }

    /**
     * <p>
     * Tests accuracy of setExpenseType() method.
     * </p>
     */
    public void testSetExpenseTypeAccuracy() {
        ExpenseEntryType expenseType = new ExpenseEntryType();

        expenseEntry.setExpenseType(expenseType);

        assertEquals("The expense type should be correctly set.", expenseType, expenseEntry.getExpenseType());
    }

    /**
     * <p>
     * Tests accuracy of getStatus() method. Default is null.
     * </p>
     */
    public void testGetStatusAccuracy() {
        assertNull("The expense status should be correctly got.", expenseEntry.getStatus());
    }

    /**
     * <p>
     * Tests accuracy of setStatus() method.
     * </p>
     */
    public void testSetStatusAccuracy() {
        ExpenseEntryStatus expenseStatus = new ExpenseEntryStatus();

        expenseEntry.setStatus(expenseStatus);

        assertEquals("The expense status should be correctly set.", expenseStatus, expenseEntry.getStatus());
    }

    /**
     * <p>
     * Tests accuracy of isBillable() method. Default is null.
     * </p>
     */
    public void testIsBillableAccuracy() {
        assertFalse("The billable should be correctly got.", expenseEntry.isBillable());
    }

    /**
     * <p>
     * Tests accuracy of setBillable() method.
     * </p>
     */
    public void testSetBillableAccuracy() {
        expenseEntry.setBillable(true);

        assertTrue("The billable should be correctly set.", expenseEntry.isBillable());
    }
}
