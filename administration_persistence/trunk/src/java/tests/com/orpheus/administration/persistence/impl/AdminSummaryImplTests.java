/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>AdminSummaryImpl</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class AdminSummaryImplTests extends TestCase {
    // the constructor is tested by the other tests, so no need for a special test case

    /**
     * Tests the <code>getPendingSponsorCount</code> method.
     */
    public void test_getPendingSponsorCount() {
        assertEquals("getPendingSponsorCount() should return the sponsor count passed to the constructor", 1,
                     new AdminSummaryImpl(1, 2, 3).getPendingSponsorCount());
    }

    /**
     * Tests the <code>getPendingWinnerCount</code> method.
     */
    public void test_getPendingWinnerCount() {
        assertEquals("getPendingWinnerCount() should return the winner count passed to the constructor", 2,
                     new AdminSummaryImpl(1, 2, 3).getPendingWinnerCount());
    }

    /**
     * Tests the <code>getActiveGameCount</code> method.
     */
    public void test_getActiveGameCount() {
        assertEquals("getActiveGameCount() should return the game count passed to the constructor", 3,
                     new AdminSummaryImpl(1, 2, 3).getActiveGameCount());
    }
}
