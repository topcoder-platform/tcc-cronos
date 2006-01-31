/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryStatus</code> class. The functionality and error cases
 * which are already tested in <code>CommonInfo</code> are not repeated here.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class ExpenseEntryStatusTestCase extends TestCase {
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
        for (int i = 0; i < 5; ++i) {
            CommonInfo status = new ExpenseEntryStatus(i);

            assertEquals("The ID should be correct.", i, status.getId());
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryStatus()</code>. The ID should be -1.
     * </p>
     */
    public void testExpenseEntryStatusAccuracy() {
        CommonInfo status = new ExpenseEntryStatus();

        assertEquals("The ID should be correct.", -1, status.getId());
    }

    /**
     * <p>
     * Aggragates all tests in this class.
     * </p>
     *
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryStatusTestCase.class);
    }
}






