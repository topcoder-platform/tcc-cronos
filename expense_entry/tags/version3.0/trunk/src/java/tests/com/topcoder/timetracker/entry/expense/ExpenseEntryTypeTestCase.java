/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryType</code> class. The functionality and error cases
 * which are already tested in <code>CommonInfo</code> are not repeated here.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class ExpenseEntryTypeTestCase extends TestCase {
    /**
     * <p>
     * Tests constructor <code>ExpenseEntryType(int)</code> when the ID is -1. Expect IllegalArgumentException.
     * </p>
     */
    public void testExpenseEntryTypeIntInvalid() {
        try {
            new ExpenseEntryType(-1);
            fail("The ID is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryType(int)</code>. The ID should be correctly set.
     * </p>
     */
    public void testExpenseEntryTypeIntAccuracy() {
        for (int i = 0; i < 5; ++i) {
            BasicInfo status = new ExpenseEntryType(i);

            assertEquals("The ID should be correct.", i, status.getId());
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryType()</code>. The ID should be -1.
     * </p>
     */
    public void testExpenseEntryTypeAccuracy() {
        BasicInfo status = new ExpenseEntryType();

        assertEquals("The ID should be correct.", -1, status.getId());
    }

    /**
     * <p>
     * Test accuracy of <code>isActive</code>.
     * </p>
     */
    public void testGetActive() {
        assertFalse("Failed to get the active", new ExpenseEntryType().isActive());
    }

    /**
     * <p>
     * Test accuracy of <code>setActive</code>.
     * </p>
     */
    public void testSetActive() {
        ExpenseEntryType type = new ExpenseEntryType();
        assertFalse("Failed to get the active", type.isActive());

        type.setActive(true);
        assertTrue("Failed to get the active", type.isActive());
    }

    /**
     * <p>
     * Test accuracy of <code>getCompanyId</code>.
     * </p>
     */
    public void testGetCompanyIde() {
        assertEquals("Failed to get the company id", -1, new ExpenseEntryType().getCompanyId());
    }

    /**
     * <p>
     * Test accuracy of <code>setCompanyId</code>. The company id should set to new value.
     * </p>
     */
    public void testSetCompanyId() {
        ExpenseEntryType type = new ExpenseEntryType();
        assertEquals("Failed to get the active", 0, type.getCompanyId());

        type.setCompanyId(1);
        assertEquals("Failed to get the active", 1, type.getCompanyId());
    }

    /**
     * <p>
     * Aggragates all tests in this class.
     * </p>
     *
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryTypeTestCase.class);
    }
}






