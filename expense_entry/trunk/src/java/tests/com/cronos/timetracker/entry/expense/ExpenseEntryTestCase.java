/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntry</code> class. The functionality and error cases which
 * are already tested in <code>CommonInfo</code> are not repeated here.
 * </p>
 *
 * @author visualage
 * @version 2.0
 */
public class ExpenseEntryTestCase extends TestCase {
    /** Represents the <code>ExpenseEntry</code> instance used in tests. */
    private ExpenseEntry entry;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        entry = new ExpenseEntry();
    }

    /**
     * <p>
     * Cleans up the test environment. The test instance is disposed.
     * </p>
     */
    protected void tearDown() {
        entry = null;
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntry(int)</code> when the ID is -1. Expect IllegalArgumentException.
     * </p>
     */
    public void testExpenseEntryIntInvalid() {
        try {
            new ExpenseEntry(-1);
            fail("The ID is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntry(int)</code>. The ID should be correctly set.
     * </p>
     */
    public void testExpenseEntryIntAccuracy() {
        for (int i = 0; i < 5; ++i) {
            BasicInfo status = new ExpenseEntry(i);

            assertEquals("The ID should be correct.", i, status.getId());
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntry()</code>. The ID should be -1.
     * </p>
     */
    public void testExpenseEntryAccuracy() {
        BasicInfo status = new ExpenseEntry();

        assertEquals("The ID should be correct.", -1, status.getId());
    }

    /**
     * <p>
     * Tests <code>setDate</code> when the given date is <code>null</code>. Expect NullPointerException.
     * </p>
     */
    public void testSetDateNull() {
        try {
            entry.setDate(null);
            fail("The date is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setExpenseType</code> when the given expense entry type is <code>null</code>. Expect
     * NullPointerException.
     * </p>
     */
    public void testSetExpenseTypeNull() {
        try {
            entry.setExpenseType(null);
            fail("The expense type is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setStatus</code> when the given expense entry status is <code>null</code>. Expect
     * NullPointerException.
     * </p>
     */
    public void testSetStatusNull() {
        try {
            entry.setStatus(null);
            fail("The expense status is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setAmount</code> when the given amount of money is <code>null</code>. Expect NullPointerException.
     * </p>
     */
    public void testSetAmountNull() {
        try {
            entry.setAmount(null);
            fail("The amount of money is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setAmount</code> when the given amount of money is negative. Expect IllegalArgumentException.
     * </p>
     */
    public void testSetAmountNegative() {
        try {
            entry.setAmount(new BigDecimal(-1));
            fail("The amount of money is negative, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>getDate</code> for default value. By default, the date is <code>null</code>.
     * </p>
     */
    public void testGetDateDefaultAccuracy() {
        assertNull("By default, the date is null.", entry.getDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>setDate</code>. The date should be correctly set.
     * </p>
     */
    public void testSetDateAccuarcy() {
        Date date = new Date();

        entry.setDate(date);

        assertEquals("The date should be correct.", date, entry.getDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>getAmount</code> for default value. By default, the amount of money is
     * <code>null</code>.
     * </p>
     */
    public void testGetAmountDefaultAccuracy() {
        assertNull("By default, the amount of money is null.", entry.getAmount());
    }

    /**
     * <p>
     * Tests accuracy of <code>setAmount</code>. The amount of money should be correctly set.
     * </p>
     */
    public void testSetAmountAccuarcy() {
        for (int i = 0; i < 5; ++i) {
            entry.setAmount(new BigDecimal(i * 100));

            assertEquals("The amount of money should be correct.", new BigDecimal(i * 100), entry.getAmount());
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>getExpenseType</code> for default value. By default, the expense type should be
     * <code>null</code>.
     * </p>
     */
    public void testGetExpenseTypeDefaultAccuracy() {
        assertNull("By default, the expense type should be null.", entry.getExpenseType());
    }

    /**
     * <p>
     * Tests accuracy of <code>setExpenseType</code>. The expense type should be correctly set.
     * </p>
     */
    public void testSetExpenseTypeAccuracy() {
        ExpenseEntryType type = new ExpenseEntryType();

        entry.setExpenseType(type);

        assertEquals("The expense type should be correct.", type, entry.getExpenseType());
    }

    /**
     * <p>
     * Tests accuracy of <code>getStatus</code> for default value. By default, the expense status should be
     * <code>null</code>.
     * </p>
     */
    public void testGetStatusDefaultAccuracy() {
        assertNull("By default, the expense status should be null.", entry.getStatus());
    }

    /**
     * <p>
     * Tests accuracy of <code>setStatus</code>. The expense status should be correctly set.
     * </p>
     */
    public void testSetExpenseStatusAccuracy() {
        ExpenseEntryStatus status = new ExpenseEntryStatus();

        entry.setStatus(status);

        assertEquals("The expense status should be correct.", status, entry.getStatus());
    }

    /**
     * <p>
     * Tests accuracy of <code>isBillable</code> for default value. By default, the billable flag is
     * <code>false</code>.
     * </p>
     */
    public void testIsBillableDefaultAccuracy() {
        assertFalse("By default, the billable flag should be false.", entry.isBillable());
    }

    /**
     * <p>
     * Tests accuracy of <code>getBillable</code> for default value. By default, the billable flag is
     * <code>false</code>.
     * </p>
     */
    public void testGetBillableDefaultAccuracy() {
        assertFalse("By default, the billable flag should be false.", entry.getBillable());
    }

    /**
     * <p>
     * Tests accuracy of <code>setBillable</code>. The billable flag should be correctly set.
     * </p>
     */
    public void testSetBillableAccuracy() {
        entry.setBillable(true);
        assertTrue("The billable flag should be correct.", entry.isBillable());

        entry.setBillable(false);
        assertFalse("The billable flag should be correct.", entry.isBillable());
    }

    /**
     * <p>
     * Test accuracy of <code>getCompanyId</code>.
     * </p>
     */
    public void testGetCompanyId() {
        assertEquals("Failed to get the company id", -1, entry.getCompanyId());
    }

    /**
     * <p>
     * Test accuracy of <code>setCompanyId</code>. The company id should set to new value.
     * </p>
     */
    public void testSetCompanyId() {
        assertEquals("Failed to get the company id", -1, entry.getCompanyId());

        entry.setCompanyId(1);
        assertEquals("Failed to get the company id", 1, entry.getCompanyId());
    }

    /**
     * <p>
     * Test <code>setCompanyId</code>. When set to -1, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyIdNegative() {
        try {
            entry.setCompanyId(-1);
            fail("company id can't be set to -1, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Aggragates all tests in this class.
     * </p>
     *
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryTestCase.class);
    }
}
