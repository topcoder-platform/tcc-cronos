/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.report.informix.ExpenseEntryReport;

import junit.framework.TestCase;

/**
 * The test of ExpenseEntryReport.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ExpenseEntryReportAccuracyTests extends TestCase {
    /** The tset ExpenseEntryReport for testing. */
    private ExpenseEntryReport instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        instance = new ExpenseEntryReport();
    }

    /**
     * Test method for 'ExpenseEntryReport()'
     */
    public void testExpenseEntryReport() {
        assertNotNull("the instance is created", instance);
    }

    /**
     * Test method for 'getExpenseEntry()'
     */
    public void testGetExpenseEntry() {
        ExpenseEntry expenseEntry = new ExpenseEntry();
        instance.setExpenseEntry(expenseEntry);
        assertEquals("incorrect expenseEntry", expenseEntry, instance.getExpenseEntry());
    }

    /**
     * Test method for 'setExpenseEntry(ExpenseEntry)'
     */
    public void testSetExpenseEntry() {
        ExpenseEntry expenseEntry = new ExpenseEntry();
        instance.setExpenseEntry(expenseEntry);
        assertEquals("incorrect expenseEntry", expenseEntry, instance.getExpenseEntry());

        ExpenseEntry expenseEntry1 = new ExpenseEntry();
        instance.setExpenseEntry(expenseEntry1);
        assertEquals("incorrect expenseEntry", expenseEntry1, instance.getExpenseEntry());
    }

    /**
     * Test method for 'getExpenseStatus()'
     */
    public void testGetExpenseStatus() {
        ExpenseStatus expenseStatus = new ExpenseStatus();
        instance.setExpenseStatus(expenseStatus);
        assertEquals("incorrect expenseStatus", expenseStatus, instance.getExpenseStatus());
    }

    /**
     * Test method for 'setExpenseStatus(ExpenseStatus)'
     */
    public void testSetExpenseStatus() {
        ExpenseStatus expenseStatus = new ExpenseStatus();
        instance.setExpenseStatus(expenseStatus);
        assertEquals("incorrect expenseStatus", expenseStatus, instance.getExpenseStatus());

        ExpenseStatus expenseStatus1 = new ExpenseStatus();
        instance.setExpenseStatus(expenseStatus1);
        assertEquals("incorrect expenseStatus", expenseStatus1, instance.getExpenseStatus());
    }

    /**
     * Test method for 'getExpenseType()'
     */
    public void testGetExpenseType() {
        ExpenseType expenseType = new ExpenseType();
        instance.setExpenseType(expenseType);
        assertEquals("incorrect expenseType", expenseType, instance.getExpenseType());
    }

    /**
     * Test method for 'setExpenseType(ExpenseType)'
     */
    public void testSetExpenseType() {
        ExpenseType expenseType = new ExpenseType();
        instance.setExpenseType(expenseType);
        assertEquals("incorrect expenseType", expenseType, instance.getExpenseType());

        ExpenseType expenseType1 = new ExpenseType();
        instance.setExpenseType(expenseType1);
        assertEquals("incorrect expenseType", expenseType1, instance.getExpenseType());
    }

}
