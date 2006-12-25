/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.Helper;
import com.orpheus.game.persistence.SlotCompletion;

import java.util.Date;


/**
 * <p>
 * Simple implementation of the SlotCompletion. Represents the recorded data about a player's completion of a hosting
 * slot.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b>This class is immutable and thread-safe.
 * </p>
 * 
 * @author argolite, waits
 * @version 1.0
 */
public class SlotCompletionImpl implements SlotCompletion {
    /**
     * <p>
     * Represents the ID of the hosting slot that was completed. Set in the constructor, can be any positive number,
     * and will not change.
     * </p>
     */
    private final long slotId;

    /**
     * <p>
     * Represents the ID of the player who completed the slot. Set in the constructor, can be any positive number,
     * and will not change.
     * </p>
     */
    private final long playerId;

    /**
     * <p>
     * Represents a Date representing the date and time at which the slot was completed. Set in the constructor,
     * cannot be null, and will not change.
     * </p>
     */
    private final Date timestamp;

    /**
     * <p>
     * Represents the text of the key associated with this completion. Set in the constructor, cannot be null/empty,
     * and will not change.
     * </p>
     */
    private final String keyText;

    /**
     * <p>
     * Represents the download object ID of an image containing the key text, to be presented to users instead of
     * plain text. Set in the constructor, can be any positive value, and will not change.
     * </p>
     */
    private final long keyImageId;

    /**
     * <p>
     * Constructor.
     * </p>
     *
     * @param slotId the slot id
     * @param playerId the player id
     * @param timestamp the completion date of this slot
     * @param keyText the key text
     * @param keyImageId the download object ID of an image containing the key text
     *
     * @throws IllegalArgumentException If slotId, keyImageId, or playerId is not positive, or if timestamp is null,
     *         or if keyText is null/empty
     */
    public SlotCompletionImpl(long slotId, long playerId, Date timestamp, String keyText, long keyImageId) {
        Helper.checkNotPositive(slotId, "slotId");
        Helper.checkNotPositive(playerId, "playerId");
        Helper.checkNotPositive(keyImageId, "keyImageId");
        Helper.checkNotNullOrEmpty(keyText, "keyText");
        Helper.checkNotNull(timestamp, "timestamp");

        this.slotId = slotId;
        this.playerId = playerId;
        this.timestamp = timestamp;
        this.keyText = keyText;
        this.keyImageId = keyImageId;
    }

    /**
     * <p>
     * Returns the ID of the hosting slot that was completed.
     * </p>
     *
     * @return the slot id
     */
    public long getSlotId() {
        return this.slotId;
    }

    /**
     * <p>
     * Returns the ID of the player who completed the slot.
     * </p>
     *
     * @return the player id
     */
    public long getPlayerId() {
        return this.playerId;
    }

    /**
     * <p>
     * Returns a Date representing the date and time at which the slot was completed.
     * </p>
     *
     * @return the completion date of this slot
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    /**
     * <p>
     * Returns the text of the key associated with this completion.
     * </p>
     *
     * @return the key text
     */
    public String getKeyText() {
        return this.keyText;
    }

    /**
     * <p>
     * Returns the download object ID of an image containing the key text, to be presented to users instead of plain.
     * </p>
     * text.
     *
     * @return the download object ID of an image containing the key text
     */
    public long getKeyImageId() {
        return this.keyImageId;
    }
}
