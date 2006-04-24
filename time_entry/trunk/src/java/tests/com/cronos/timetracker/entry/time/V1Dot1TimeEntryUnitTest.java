/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

import junit.framework.TestCase;

import java.util.Map;


/**
 * <p>
 * Tests functionality and error cases of <code>TimeEntry</code> class. The functionality and error cases which are
 * already tested in V1.0 are not repeated here.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1TimeEntryUnitTest extends TestCase {
    /** Represents the <code>TimeEntry</code> instance used in tests. */
    private TimeEntry entry;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        entry = new TimeEntry();
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
     * Tests the method <code>addRejectReason(RejectReason)</code> when the given rejectReason is <code> null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testAddRejectReason_NullRejectReason() {
        try {
            entry.addRejectReason(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>addRejectReason(RejectReason)</code> when the given rejectReason does not
     * exist.
     * </p>
     */
    public void testAddRejectReason_NotExistRejectReasonAccuracy() {
        RejectReason reason = new RejectReason();
        reason.setPrimaryId(1);

        // add first
        entry.addRejectReason(reason);

        // check
        Map rejectReasons = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reason should be added properly.", 1, rejectReasons.size());
        assertEquals("The reject reason should be added properly.", reason, rejectReasons.get(new Integer(1)));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>addRejectReason(RejectReason)</code> when the given rejectReason does exist.
     * </p>
     */
    public void testAddRejectReason_ExistRejectReasonAccuracy() {
        RejectReason reason = new RejectReason();
        reason.setPrimaryId(1);

        // add first
        entry.addRejectReason(reason);

        // add again
        RejectReason newReason = new RejectReason();
        newReason.setPrimaryId(1);
        entry.addRejectReason(newReason);

        // check
        Map rejectReasons = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reason should be added properly.", 1, rejectReasons.size());
        assertEquals("The reject reason should be added properly.", newReason, rejectReasons.get(new Integer(1)));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getRejectReason(int)</code> when the given rejectReason does not exist.
     * </p>
     */
    public void testGetRejectReason_NotExistRejectReasonAccuracy() {
        assertNull("The reject reason should be got properly.", entry.getRejectReason(1));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getRejectReason(int)</code> when the given rejectReason does exist.
     * </p>
     */
    public void testGetRejectReason_ExistRejectReasonAccuracy() {
        RejectReason reason = new RejectReason();
        reason.setPrimaryId(1);

        // add first
        entry.addRejectReason(reason);

        // check
        assertEquals("The reject reason should be got properly.", reason, entry.getRejectReason(1));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>removeRejectReason(int)</code>.
     * </p>
     */
    public void testRemoveRejectReason_Accuracy() {
        RejectReason reason = new RejectReason();
        reason.setPrimaryId(1);

        // add first
        entry.addRejectReason(reason);

        // remove
        entry.removeRejectReason(1);

        // check
        Map rejectReasons = (Map) V1Dot1TestHelper.getPrivateField(entry.getClass(), entry, "rejectReasons");
        assertEquals("The reject reason should be added properly.", 0, rejectReasons.size());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getAllRejectReasons()</code>.
     * </p>
     */
    public void testGetAllRejectReasons_Accuracy() {
        // add first
        RejectReason[] reasons = new RejectReason[10];

        for (int i = 0; i < reasons.length; i++) {
            reasons[i] = new RejectReason();
            reasons[i].setPrimaryId(i + 1);
            entry.addRejectReason(reasons[i]);
        }

        // check
        RejectReason[] ret = entry.getAllRejectReasons();
        V1Dot1TestHelper.assertEquals("The return value of getAllRejectReasons should be correct.", reasons, ret);
    }
}
