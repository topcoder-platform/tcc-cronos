/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryType</code> class. The functionality and error cases which
 * are already tested in <code>BasicInfo</code> are not repeated here.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1ExpenseEntryTypeUnitTest extends TestCase {
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
        for (int i = 0; i < 2; ++i) {
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
}
