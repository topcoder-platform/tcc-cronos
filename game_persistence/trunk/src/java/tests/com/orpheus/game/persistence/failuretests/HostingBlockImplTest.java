/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.entities.HostingBlockImpl;

import junit.framework.TestCase;

/**
 * Test case for <code>HostingBlockImpl</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class HostingBlockImplTest extends TestCase {

    /**
     * Test method for HostingBlockImpl(java.lang.Long, int, HostingSlot[], int).
     * In this case, the id is zero.
     */
    public void testHostingBlockImpl_ZeroId() {
        try {
            new HostingBlockImpl(new Long(0), 1, new HostingSlot[0], 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingBlockImpl(java.lang.Long, int, HostingSlot[], int).
     * In this case, the id is zero.
     */
    public void testHostingBlockImpl_NegativeId() {
        try {
            new HostingBlockImpl(new Long(-1), 1, new HostingSlot[0], 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingBlockImpl(java.lang.Long, int, HostingSlot[], int).
     * In this case, the sequence number is zero.
     */
    public void testHostingBlockImpl_NegativeSeqNum() {
        try {
            new HostingBlockImpl(new Long(1), -1, new HostingSlot[0], 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingBlockImpl(java.lang.Long, int, HostingSlot[], int).
     * In this case, the slots contains null.
     */
    public void testHostingBlockImpl_NullInSlots() {
        try {
            new HostingBlockImpl(new Long(1), 1, new HostingSlot[1], 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingBlockImpl(java.lang.Long, int, HostingSlot[], int).
     * In this case, the MaxHostingTimePerSlot is negative.
     */
    public void testHostingBlockImpl_NegativeMaxHostingTimePerSlot() {
        try {
            new HostingBlockImpl(new Long(1), 1, new HostingSlot[0], -1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
