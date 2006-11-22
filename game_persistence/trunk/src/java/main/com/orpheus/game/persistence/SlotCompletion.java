/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import java.io.Serializable;

import java.util.Date;


/**
 * <p>
 * Represents the recorded data about a player's completion of a hosting slot.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>:  Implementations should strive to be thread-safe, but they can expect to be used in a
 * thread-safe manner.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface SlotCompletion extends Serializable {
    /**
     * Returns the ID of the hosting slot that was completed.
     *
     * @return the slot id
     */
    long getSlotId();

    /**
     * Returns the ID of the player who completed the slot.
     *
     * @return the player id
     */
    long getPlayerId();

    /**
     * Returns a Date representing the date and time at which the slot was completed.
     *
     * @return the completion date of this slot
     */
    Date getTimestamp();

    /**
     * Returns the text of the key associated with this completion.
     *
     * @return the key text
     */
    String getKeyText();

    /**
     * Returns the download object ID of an image containing the key text, to be presented to users instead of plain
     * text.
     *
     * @return the download object ID of an image containing the key text
     */
    long getKeyImageId();
}
