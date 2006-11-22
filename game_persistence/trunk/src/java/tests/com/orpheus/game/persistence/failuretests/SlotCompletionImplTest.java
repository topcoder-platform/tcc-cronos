/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import java.util.Date;

import com.orpheus.game.persistence.entities.SlotCompletionImpl;

import junit.framework.TestCase;

/**
 * Test case for <code>SlotCompletionImpl</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class SlotCompletionImplTest extends TestCase {

    /**
     * Test method for SlotCompletionImpl(long, long, java.util.Date, java.lang.String, long).
     * In this case, the id is zero.
     */
    public void testSlotCompletionImpl_ZeroId() {
        try {
            new SlotCompletionImpl(0, 1, new Date(), "test", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotCompletionImpl(long, long, java.util.Date, java.lang.String, long).
     * In this case, the id is negative.
     */
    public void testSlotCompletionImpl_NegativeId() {
        try {
            new SlotCompletionImpl(-1, 1, new Date(), "test", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotCompletionImpl(long, long, java.util.Date, java.lang.String, long).
     * In this case, the player id is zero.
     */
    public void testSlotCompletionImpl_ZeroPlayerId() {
        try {
            new SlotCompletionImpl(1, 0, new Date(), "test", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotCompletionImpl(long, long, java.util.Date, java.lang.String, long).
     * In this case, the player id is negative.
     */
    public void testSlotCompletionImpl_NegativePlayerId() {
        try {
            new SlotCompletionImpl(1, -1, new Date(), "test", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotCompletionImpl(long, long, java.util.Date, java.lang.String, long).
     * In this case, the time stamp is null.
     */
    public void testSlotCompletionImpl_NullTimeStamp() {
        try {
            new SlotCompletionImpl(1, 1, null, "test", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotCompletionImpl(long, long, java.util.Date, java.lang.String, long).
     * In this case, the text is null.
     */
    public void testSlotCompletionImpl_NullText() {
        try {
            new SlotCompletionImpl(1, 1, new Date(), null, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotCompletionImpl(long, long, java.util.Date, java.lang.String, long).
     * In this case, the text is empty.
     */
    public void testSlotCompletionImpl_EmptyText() {
        try {
            new SlotCompletionImpl(1, 1, new Date(), " ", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotCompletionImpl(long, long, java.util.Date, java.lang.String, long).
     * In this case, the image id is zero.
     */
    public void testSlotCompletionImpl_ZeroImageId() {
        try {
            new SlotCompletionImpl(1, 1, new Date(), "test", 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotCompletionImpl(long, long, java.util.Date, java.lang.String, long).
     * In this case, the image id is negative.
     */
    public void testSlotCompletionImpl_NegativeImageId() {
        try {
            new SlotCompletionImpl(1, 1, new Date(), "test", -1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }
}
