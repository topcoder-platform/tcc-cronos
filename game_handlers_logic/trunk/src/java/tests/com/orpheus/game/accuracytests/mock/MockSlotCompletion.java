/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests.mock;

import java.util.Date;

import com.orpheus.game.persistence.SlotCompletion;

/**
 * A mock implementation of SlotCompletion.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockSlotCompletion implements SlotCompletion {
    /**
     * Represents the player id.
     */
    private long playerId;

    /**
     * Create the mock slot completion.
     * 
     * @param playerId
     *            the player id
     */
    MockSlotCompletion(long playerId) {
        this.playerId = playerId;
    }

    /**
     * No implementation.
     * 
     * @return always 0.
     */
    public long getSlotId() {
        return 0;
    }

    /**
     * Returns the player id.
     * 
     * @return player id.
     */
    public long getPlayerId() {
        return playerId;
    }

    /**
     * Returns the current timestamp.
     * 
     * @return current timestamp.
     */
    public Date getTimestamp() {
        return new Date();
    }

    /**
     * No implementation.
     * 
     * @return always null
     */
    public String getKeyText() {
        return null;
    }

    /**
     * No implementation.
     * 
     * @return always 0
     */

    public long getKeyImageId() {
        return 0;
    }

}
