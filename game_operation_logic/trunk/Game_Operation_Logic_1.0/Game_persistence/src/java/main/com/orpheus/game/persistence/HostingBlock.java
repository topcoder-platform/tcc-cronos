/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import java.io.Serializable;


/**
 * <p>
 * Represents a 'block'; of hosting slots. Blocks serve as an organizational unit for hosting auctions, and
 * furthermore help to obscure the specific sequence of upcoming domains, even from sponsors privy to the auction
 * details.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>:Implementations should strive to be thread-safe, but they can expect to be used in a
 * thread-safe manner.
 * </p>
 *
 * @author argolite, waits
 * @version 1.0
 */
public interface HostingBlock extends Serializable {
    /**
     * Returns the ID of this block as a Long, or null if no ID has yet been assigned.
     *
     * @return the id
     */
    Long getId();

    /**
     * Returns the sequence number of this block within its game.
     *
     * @return the sequence number
     */
    int getSequenceNumber();

    /**
     * Returns an array of HostingSlot objects representing all the slots associated with this block.
     *
     * @return the array of HostingSlot objects
     */
    HostingSlot[] getSlots();

    /**
     * Returns the maximum hosting time for this block, in minutes.
     *
     * @return the maximum hosting time for this block, in minutes
     */
    int getMaxHostingTimePerSlot();
}
