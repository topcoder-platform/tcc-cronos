/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.failuretests;
import com.cronos.timetracker.entry.expense.ExpenseEntry;
import com.cronos.timetracker.entry.expense.ExpenseEntryRejectReason;

import junit.framework.TestCase;
/**
 * Tests for ExpenseEntry class.
 * @author qiucx0161
 * @version 1.0
 */
public class TestExpenseEntry extends TestCase {
    
    /**
     * <c>ExpenseEntry</c> instance to test on.
     */
    private ExpenseEntry entry = null;
    
    /**
     * ExpenseEntryRejectReason instance used for test.
     */
    private ExpenseEntryRejectReason reason = null;
    
    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        reason = new ExpenseEntryRejectReason(12345);
        entry = new ExpenseEntry();
    }

    /**
     * Tests addRejectReason(ExpenseEntryRejectReason rejectReason) method with null RejectReason
     * IllegalArgumentException should be thrown.
     */
    public void testAddRejectReasonNullRejectReason1() {
        try {
            entry.addRejectReason(null);
            fail("testAddRejectReasonNullRejectReason is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddRejectReasonNullRejectReason is failure.");
        }
    }
    
    /**
     * Tests addRejectReason(ExpenseEntryRejectReason[] rejectReason) method with null RejectReason
     * IllegalArgumentException should be thrown.
     */
    public void testAddRejectReasonNullRejectReason2() {
        try {
            entry.addRejectReasons(null);
            fail("testAddRejectReasonNullRejectReason is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddRejectReasonNullRejectReason is failure.");
        }
    }
    
    /**
     * Tests addRejectReasons(ExpenseEntryRejectReason[] rejectReasons) method with empty RejectReasons
     * IllegalArgumentException should be thrown.
     */
    public void testAddRejectReasonsEmptyRejectReasons() {
        try {
            entry.addRejectReasons(new ExpenseEntryRejectReason[]{});
            fail("testAddRejectReasonsEmptyRejectReasons is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddRejectReasonsEmptyRejectReasons is failure.");
        }
    }
    
    /**
     * Tests addRejectReasons(ExpenseEntryRejectReason[] rejectReasons) method with empty RejectReasons
     * IllegalArgumentException should be thrown.
     */
    public void testAddRejectReasonsNullElementInRejectReasons() {
        try {
            entry.addRejectReasons(new ExpenseEntryRejectReason[]{reason, null});
            fail("testAddRejectReasonsNullElementInRejectReasons is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddRejectReasonsNullElementInRejectReasons is failure.");
        }
    }

    /**
     * Tests deleteRejectReasons(int[] rejectReasonIds) method with null RejectReasonIds
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteRejectReasonsNullRejectReasonIds() {
        try {
            entry.deleteRejectReasons(null);
            fail("testDeleteRejectReasonsNullRejectReasonIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testDeleteRejectReasonsNullRejectReasonIds is failure.");
        }
    }
    
    /**
     * Tests deleteRejectReasons(int[] rejectReasonIds) method with empty RejectReasonIds
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteRejectReasonsEmptyRejectReasonIds() {
        try {
            entry.deleteRejectReasons(new int[]{});
            fail("testDeleteRejectReasonsEmptyRejectReasonIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testDeleteRejectReasonsEmptyRejectReasonIds is failure.");
        }
    }

    /**
     * Tests updateRejectReason(ExpenseEntryRejectReason rejectReason) method with null RejectReason
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateRejectReasonNullRejectReason() {
        try {
            entry.updateRejectReason(null);
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateRejectReasonNullRejectReason is failure.");
        }
    }

    /**
     * Tests updateRejectReasons(ExpenseEntryRejectReason[] rejectReasons) method with null RejectReasons
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateRejectReasonsNullRejectReasons() {
        try {
            entry.updateRejectReasons(null);
            fail("testUpdateRejectReasonsNullRejectReasons is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateRejectReasonsNullRejectReasons is failure.");
        }
    }
    
    /**
     * Tests updateRejectReasons(ExpenseEntryRejectReason[] rejectReasons) method with empty RejectReasons
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateRejectReasonsEmptyRejectReasons() {
        try {
            entry.updateRejectReasons(new ExpenseEntryRejectReason[]{});
            fail("testUpdateRejectReasonsNullRejectReasons is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateRejectReasonsNullRejectReasons is failure.");
        }
    }
    
    /**
     * Tests updateRejectReasons(ExpenseEntryRejectReason[] rejectReasons) method with null element
     * in RejectReasons, IllegalArgumentException should be thrown.
     */
    public void testUpdateRejectReasonsNullElementInRejectReasons() {
        try {
            entry.updateRejectReasons(new ExpenseEntryRejectReason[]{null});
            fail("testUpdateRejectReasonsNullElementInRejectReasons is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateRejectReasonsNullElementInRejectReasons is failure.");
        }
    }
}
