/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.SlotCompletion;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Unit test cases for class <code>SlotCompletionImpl</code>.
 * </p>
 * @author waits
 * @version 1.0
 */
public class SlotCompletionImplUnitTests extends TestCase {
    /** represents the slotID constants for testing. */
    public static final long SLOT_ID = 1l;

    /** represents the playerId constants for testing. */
    public static final long PLAYER_ID = 2l;

    /** represents the keyImageId constants for testing. */
    public static final long KEY_IMAGE_ID = 3l;

    /** keyText. */
    public static final String KEY_TEXT = "html";

    /** timestamp. */
    private final Date timestamp = new Date();

    /** SlotCompletionImpl instance to test against. */
    private SlotCompletion slot = null;

    /**
     * create SlotCompletionImpl instance.
     */
    protected void setUp() {
        slot = new SlotCompletionImpl(SLOT_ID, PLAYER_ID, timestamp, KEY_TEXT, KEY_IMAGE_ID);
    }

    /**
     * test the ctor, simply verify the instance.
     */
    public void testCtor() {
        assertNotNull("The SlotCompletionImpl can not be instantiate.", slot);
    }

    /**
     * test the ctor, the slotId is not positive, iae expected.
     */
    public void testCtor_notPositiveDownLoadId() {
        try {
            new SlotCompletionImpl(0, PLAYER_ID, timestamp, KEY_TEXT, KEY_IMAGE_ID);
            fail("The slotId should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the PlayerId is not positive, iae expected.
     */
    public void testCtor_notPlayerId() {
        try {
            new SlotCompletionImpl(SLOT_ID, 0, timestamp, KEY_TEXT, KEY_IMAGE_ID);
            fail("The PlayerId should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the KeyImageId is not positive, iae expected.
     */
    public void testCtor_notKeyImageId() {
        try {
            new SlotCompletionImpl(SLOT_ID, PLAYER_ID, timestamp, KEY_TEXT, 0);
            fail("The KeyImageId should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the timeStamp is null, iae expected.
     */
    public void testCtor_nullTimeStamp() {
        try {
            new SlotCompletionImpl(SLOT_ID, PLAYER_ID, null, KEY_TEXT, KEY_IMAGE_ID);
            fail("The timeStamp is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the keyText is null, iae expected.
     */
    public void testCtor_nullKeyText() {
        try {
            new SlotCompletionImpl(SLOT_ID, PLAYER_ID, timestamp, null, KEY_IMAGE_ID);
            fail("The keyText is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the keyText is empty, iae expected.
     */
    public void testCtor_emptyKeyText() {
        try {
            new SlotCompletionImpl(SLOT_ID, PLAYER_ID, timestamp, " ", KEY_IMAGE_ID);
            fail("The keyText is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * simply verify the getSlotId method.
     */
    public void testGetSlotId() {
        assertEquals("The slot to set is the not one get.", SLOT_ID, this.slot.getSlotId());
    }

    /**
     * simply verify the getId method.
     */
    public void testGetPlayerId() {
        assertEquals("The id to set is the not one get.", PLAYER_ID, this.slot.getPlayerId());
    }

    /**
     * simply verify the getTimestamp method.
     */
    public void testGetTimeStamp() {
        assertEquals("The id to set is the not one get.", timestamp, this.slot.getTimestamp());
    }

    /**
     * simply verify the getKeyImageId method.
     */
    public void testGetKeyImageId() {
        assertEquals("The keyImageId to set is the not one get.", KEY_IMAGE_ID, this.slot.getKeyImageId());
    }

    /**
     * simply verify the getKeyText method.
     */
    public void testGetId() {
        assertEquals("The keyText to set is the not one get.", KEY_TEXT, this.slot.getKeyText());
    }
}
