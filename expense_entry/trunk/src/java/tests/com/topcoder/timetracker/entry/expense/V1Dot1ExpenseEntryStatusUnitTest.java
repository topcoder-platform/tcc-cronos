/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryStatus</code> class. The functionality and error cases
 * which are already tested in <code>BasicInfo</code> are not repeated here.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1ExpenseEntryStatusUnitTest extends TestCase {
    /**
     * <p>
     * Tests constructor <code>ExpenseEntryStatus(int)</code> when the ID is -1. Expect IllegalArgumentException.
     * </p>
     */
    public void testExpenseEntryStatusIntInvalid() {
        try {
            new ExpenseEntryStatus(-1);
            fail("The ID is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryStatus(int)</code>. The ID should be correctly set.
     * </p>
     */
    public void testExpenseEntryStatusIntAccuracy() {
        for (int i = 0; i < 2; ++i) {
            BasicInfo status = new ExpenseEntryStatus(i);

            assertEquals("The ID should be correct.", i, status.getId());
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryStatus()</code>. The ID should be -1.
     * </p>
     */
    public void testExpenseEntryStatusAccuracy() {
        BasicInfo status = new ExpenseEntryStatus();

        assertEquals("The ID should be correct.", -1, status.getId());
    }
}
