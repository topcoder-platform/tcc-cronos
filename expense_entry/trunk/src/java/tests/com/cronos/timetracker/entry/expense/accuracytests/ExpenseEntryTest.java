/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.accuracytests;

import com.cronos.timetracker.entry.expense.BasicInfo;
import com.cronos.timetracker.entry.expense.ExpenseEntry;
import com.cronos.timetracker.entry.expense.ExpenseEntryRejectReason;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy tests for class ExpenseEntry. Here only the new methods not in version 1.0 are tested.
 * </p>
 *
 * @author -oo-
 * @author kr00tki
 * @version 2.0
 * @since 1.1
 */
public class ExpenseEntryTest extends TestCase {
    /** Represents the reject reason id array used in tests. */
    private final int[] reasonIds = new int[3];

    /** Represents the <code>ExpenseEntryRejectReason</code> instance array used in tests. */
    private final ExpenseEntryRejectReason[] reasons = new ExpenseEntryRejectReason[reasonIds.length];

    /** Represents the <code>ExpenseEntry</code> instance used in tests. */
    private ExpenseEntry entry;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        for (int i = 0; i < reasonIds.length; i++) {
            reasonIds[i] = i;
            reasons[i] = new ExpenseEntryRejectReason(reasonIds[i]);
        }

        BasicInfo info = new ExpenseEntry();
        entry = (ExpenseEntry) info;
    }

    /**
     * Accuracy test for addRejectReason().
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReason() throws Exception {
        // test while the reason not existed
        assertTrue("the return value is not correct", entry.addRejectReason(reasons[0]));

        ExpenseEntryRejectReason[] ret = entry.getRejectReasons();
        assertTrue("the result is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[] { reasons[0] }, ret));

        // test while the reason already existed
        assertFalse("the return value is not correct", entry.addRejectReason(reasons[0]));
        ret = entry.getRejectReasons();
        assertTrue("the result is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[] { reasons[0] }, ret));
    }

    /**
     * Accuracy test for addRejectReasons().
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasons() throws Exception {
        // test while all things OK
        assertNull("the return value is not correct", entry.addRejectReasons(reasons));

        ExpenseEntryRejectReason[] ret = entry.getRejectReasons();
        assertTrue("the result is not correct", TestHelper.compareRejectReasonsArray(reasons, ret));

        // test while some reasons duplicated
        ret = entry.addRejectReasons(new ExpenseEntryRejectReason[] { new ExpenseEntryRejectReason(1000), reasons[1] });
        assertTrue("the result is not correct", TestHelper.compareRejectReasonsArray(reasons, entry.getRejectReasons()));
    }

    /**
     * Accuracy test for deleteRejectReason().
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRejectReason() throws Exception {
        // test while the reason not existed
        assertFalse("the return value is not correct", entry.deleteRejectReason(reasons[0].getRejectReasonId()));

        ExpenseEntryRejectReason[] ret = entry.getRejectReasons();
        assertTrue("the result is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[0], ret));

        // test while the reason already existed
        entry.addRejectReason(reasons[0]);
        assertTrue("the return value is not correct", entry.deleteRejectReason(reasons[0].getRejectReasonId()));
        ret = entry.getRejectReasons();
        assertTrue("the result is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[0], ret));
    }

    /**
     * Accuracy test for deleteRejectReasons().
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRejectReasons() throws Exception {
        // test while all things OK
        entry.addRejectReasons(reasons);
        assertNull("the return value is not correct", entry.deleteRejectReasons(reasonIds));

        ExpenseEntryRejectReason[] ret = entry.getRejectReasons();
        assertTrue("the result is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[0], ret));

        // test while some reasons not existed
        entry.addRejectReasons(reasons);
        entry.deleteRejectReason(reasonIds[1]);

        int[] ret2 = entry.deleteRejectReasons(reasonIds);
        ret = entry.getRejectReasons();
        assertTrue("the result is not correct", TestHelper.compareIntArray(new int[] { reasonIds[1] }, ret2));
        assertTrue("the result is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[] { reasons[0], reasons[2] },
                entry.getRejectReasons()));
    }

    /**
     * Accuracy test for updateRejectReason().
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRejectReason() throws Exception {
        // test while the reason not existed
        assertFalse("the return value is not correct", entry.updateRejectReason(reasons[0]));

        ExpenseEntryRejectReason[] ret = entry.getRejectReasons();
        assertTrue("the result is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[0], ret));

        // test while the reason already existed
        entry.addRejectReason(reasons[0]);
        reasons[1] = new ExpenseEntryRejectReason(reasonIds[0]);
        assertTrue("the return value is not correct", entry.updateRejectReason(reasons[1]));
        ret = entry.getRejectReasons();
        assertTrue("the result is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[] { reasons[1] }, ret));
    }

    /**
     * Accuracy test for updateRejectReasons().
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRejectReasons() throws Exception {
        // test while the reasons not existed
        entry.addRejectReason(reasons[1]);
        assertTrue("the return value is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[] { reasons[0], reasons[2] },
                entry.updateRejectReasons(reasons)));

        ExpenseEntryRejectReason[] ret = entry.getRejectReasons();
        assertTrue("the result is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[] { reasons[1] }, ret));

        // test while the reasons already existed
        entry.deleteRejectReason(reasonIds[1]);
        entry.addRejectReasons(reasons);

        for (int i = 0; i < reasons.length; ++i) {
            reasons[i] = new ExpenseEntryRejectReason(reasonIds[i]);
        }

        assertNull("the return value is not correct", entry.updateRejectReasons(reasons));
        ret = entry.getRejectReasons();
        assertTrue("the result is not correct", TestHelper.compareRejectReasonsArray(reasons, ret));
    }

    /**
     * Accuracy test for getRejectReasons() && getRejectReasonIds().
     *
     * @throws Exception to JUnit
     */
    public void testGetRejectReasons() throws Exception {
        // test while no reasons existed
        assertTrue("the result is not correct",
            TestHelper.compareRejectReasonsArray(new ExpenseEntryRejectReason[0], entry.getRejectReasons()));
        assertTrue("the result is not correct", TestHelper.compareIntArray(new int[0], entry.getRejectReasonIds()));

        // test while some reasons existed
        entry.addRejectReasons(reasons);
        assertTrue("the result is not correct", TestHelper.compareRejectReasonsArray(reasons, entry.getRejectReasons()));
        assertTrue("the result is not correct", TestHelper.compareIntArray(reasonIds, entry.getRejectReasonIds()));
    }
}
