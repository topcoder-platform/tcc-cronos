/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
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
            CommonInfo status = new ExpenseEntryType(i);

            assertEquals("The ID should be correct.", i, status.getId());
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryType()</code>. The ID should be -1.
     * </p>
     */
    public void testExpenseEntryTypeAccuracy() {
        CommonInfo status = new ExpenseEntryType();

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
        return new TestSuite(ExpenseEntryTypeTestCase.class);
    }
}






