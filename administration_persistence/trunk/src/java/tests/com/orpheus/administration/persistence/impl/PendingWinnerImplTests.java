/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>PendingWinnerImpl</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class PendingWinnerImplTests extends TestCase {
    // the constructor is tested by the other tests, so no need for a special test

    /**
     * Tests the <code>getPlayerId</code> method.
     */
    public void test_getPlayerId() {
        assertEquals("the player ID should match the one passed to the constructor",
                     1, new PendingWinnerImpl(1, 2, 3).getPlayerId());
    }

    /**
     * Tests the <code>getGameId</code> method.
     */
    public void test_getGameId() {
        assertEquals("the game ID should match the one passed to the constructor",
                     2, new PendingWinnerImpl(1, 2, 3).getGameId());
    }

    /**
     * Tests the <code>getPayout</code> method.
     */
    public void test_getPayout() {
        assertEquals("the payout should match the one passed to the constructor",
                     3, new PendingWinnerImpl(1, 2, 3).getPayout());
    }
}
