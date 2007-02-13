/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryRejectReason</code> class. The functionality and error
 * cases which are already tested in <code>CommonInfo</code> are not repeated here.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1ExpenseEntryRejectReasonUnitTest extends TestCase {
    /** Represents the <code>ExpenseEntryRejectReason</code> instance used in tests. */
    private ExpenseEntryRejectReason reason;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        reason = new ExpenseEntryRejectReason(1);
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryRejectReason()</code>. The ID should be 1.
     * </p>
     */
    public void testExpenseEntryRejectReason_Accuracy() {
        assertEquals("The rejectReasonId value should be correct.", "" + 1,
            V1Dot1TestHelper.getPrivateField(reason.getClass(), reason, "rejectReasonId").toString());
    }

    /**
     * <p>
     * Tests accuracy of method <code>getRejectReasonId()</code>. The ID should be 1.
     * </p>
     */
    public void testGetRejectReasonId_Accuracy() {
        assertEquals("The rejectReasonId value should be correct.", 1, reason.getRejectReasonId());
    }

    /**
     * <p>
     * Tests accuracy of method <code>setRejectReasonId(int)</code>. The ID should be 2.
     * </p>
     */
    public void testSetRejectReasonId_Accuracy() {
        reason.setRejectReasonId(2);
        assertEquals("The rejectReasonId value should be correct.", "" + 2,
            V1Dot1TestHelper.getPrivateField(reason.getClass(), reason, "rejectReasonId").toString());
    }
}
