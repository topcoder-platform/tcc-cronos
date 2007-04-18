/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.invoice.Invoice;

import junit.framework.TestCase;

import java.math.BigDecimal;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntry</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseEntryUnitTest extends TestCase {
    /** Represents the <code>ExpenseEntry</code> instance used for testing. */
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
     * Tests the accuracy of constructor <code>ExpenseEntry()</code>.
     * </p>
     */
    public void testExpenseEntry_Accuracy() {
        assertNotNull("The ExpenseEntry instance should be created.", expenseEntry);
        assertTrue("The ExpenseEntry instance should be instance of TimeTrackerBean.",
            expenseEntry instanceof TimeTrackerBean);
        assertEquals("The ID should be correct.", -1, expenseEntry.getId());
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
     * Tests the accuracy of constructor <code>ExpenseEntry(long id)</code>.
     * </p>
     */
    public void testExpenseEntry_LongAccuracy() {
        expenseEntry = new ExpenseEntry(1);
        assertNotNull("The ExpenseEntry instance should be created.", expenseEntry);
        assertTrue("The ExpenseEntry instance should be instance of TimeTrackerBean.",
            expenseEntry instanceof TimeTrackerBean);
        assertEquals("The ID should be correct.", 1, expenseEntry.getId());
    }

    /**
     * <p>
     * Tests the accuracy of <code>isBillable()</code>.
     * </p>
     */
    public void testIsBillable_Accuracy() {
        assertFalse("Failed to get the billable", expenseEntry.isBillable());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setBillable(boolean active)</code>.
     * </p>
     */
    public void testSetBillable_Accuracy1() {
        expenseEntry.setBillable(true);
        assertTrue("Failed to set the billable.", expenseEntry.isBillable());
        assertTrue("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setBillable(boolean active)</code>.
     * </p>
     */
    public void testSetBillable_Accuracy2() {
        expenseEntry.setBillable(true);
        assertTrue("Failed to set the billable.", expenseEntry.isBillable());
        expenseEntry.setChanged(false);
        expenseEntry.setBillable(true);
        assertFalse("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>getMileage()</code>.
     * </p>
     */
    public void testGetMileage_Accuracy() {
        assertEquals("Failed to get the mileage", 0, expenseEntry.getMileage());
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
     * Tests the accuracy of <code>setMileage(int mileage)</code>.
     * </p>
     */
    public void testSetMileage_Accuracy1() {
        expenseEntry.setMileage(1);
        assertEquals("Failed to set the mileage", 1, expenseEntry.getMileage());
        assertTrue("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setMileage(int mileage)</code>.
     * </p>
     */
    public void testSetMileage_Accuracy2() {
        expenseEntry.setMileage(1);
        assertEquals("Failed to set the mileage", 1, expenseEntry.getMileage());
        expenseEntry.setChanged(false);
        expenseEntry.setMileage(1);
        assertFalse("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>getAmount()</code>.
     * </p>
     */
    public void testGetAmount_Accuracy() {
        assertEquals("Failed to get the amount", null, expenseEntry.getAmount());
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
     * Tests the accuracy of <code>setAmount(BigDecimal amount)</code>.
     * </p>
     */
    public void testSetAmount_Accuracy1() {
        expenseEntry.setAmount(new BigDecimal("1"));
        assertEquals("Failed to set the amount", new BigDecimal("1"), expenseEntry.getAmount());
        assertTrue("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setAmount(BigDecimal amount)</code>.
     * </p>
     */
    public void testSetAmount_Accuracy2() {
        expenseEntry.setAmount(new BigDecimal("1"));
        assertEquals("Failed to set the amount", new BigDecimal("1"), expenseEntry.getAmount());
        expenseEntry.setChanged(false);
        expenseEntry.setAmount(new BigDecimal("1"));
        assertFalse("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>getExpenseType()</code>.
     * </p>
     */
    public void testGetExpenseType_Accuracy() {
        assertEquals("Failed to get the expenseType", null, expenseEntry.getExpenseType());
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
     * Tests the accuracy of <code>setExpenseType(ExpenseType expenseType)</code>.
     * </p>
     */
    public void testSetExpenseType_Accuracy1() {
        ExpenseType type = new ExpenseType();
        expenseEntry.setExpenseType(type);
        assertEquals("Failed to set the expenseType", type, expenseEntry.getExpenseType());
        assertTrue("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setExpenseType(ExpenseType expenseType)</code>.
     * </p>
     */
    public void testSetExpenseType_Accuracy2() {
        ExpenseType type = new ExpenseType();
        expenseEntry.setExpenseType(type);
        assertEquals("Failed to set the expenseType", type, expenseEntry.getExpenseType());
        expenseEntry.setChanged(false);
        expenseEntry.setExpenseType(type);
        assertFalse("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>getStatus()</code>.
     * </p>
     */
    public void testGetStatus_Accuracy() {
        assertEquals("Failed to get the status", null, expenseEntry.getStatus());
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
     * Tests the accuracy of <code>setStatus(ExpenseStatus status)</code>.
     * </p>
     */
    public void testSetStatus_Accuracy1() {
        ExpenseStatus status = new ExpenseStatus();
        expenseEntry.setStatus(status);
        assertEquals("Failed to set the status", status, expenseEntry.getStatus());
        assertTrue("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setStatus(ExpenseStatus status)</code>.
     * </p>
     */
    public void testSetStatus_Accuracy2() {
        ExpenseStatus status = new ExpenseStatus();
        expenseEntry.setStatus(status);
        assertEquals("Failed to set the status", status, expenseEntry.getStatus());
        expenseEntry.setChanged(false);
        expenseEntry.setStatus(status);
        assertFalse("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>getInvoice()</code>.
     * </p>
     */
    public void testGetInvoice_Accuracy() {
        assertEquals("Failed to get the invoice", null, expenseEntry.getInvoice());
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

    /**
     * <p>
     * Tests the accuracy of <code>setInvoice(Invoice invoice)</code>.
     * </p>
     */
    public void testSetInvoice_Accuracy1() {
        Invoice invoice = new Invoice();
        expenseEntry.setInvoice(invoice);
        assertEquals("Failed to set the invoice", invoice, expenseEntry.getInvoice());
        assertTrue("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setInvoice(Invoice invoice)</code>.
     * </p>
     */
    public void testSetInvoice_Accuracy2() {
        Invoice invoice = new Invoice();
        expenseEntry.setInvoice(invoice);
        assertEquals("Failed to set the invoice", invoice, expenseEntry.getInvoice());
        expenseEntry.setChanged(false);
        expenseEntry.setInvoice(invoice);
        assertFalse("The changed flag should be correct.", expenseEntry.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setInvoice(Invoice invoice)</code>.
     * </p>
     */
    public void testSetInvoice_Accuracy3() {
        Invoice invoice = new Invoice();
        expenseEntry.setInvoice(invoice);
        assertEquals("Failed to set the invoice", invoice, expenseEntry.getInvoice());
        expenseEntry.setChanged(false);
        expenseEntry.setInvoice(null);
        assertTrue("The changed flag should be correct.", expenseEntry.isChanged());
    }
}
