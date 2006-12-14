/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.Helper;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;


/**
 * <p>
 * Simple implementation of the HostingBlock. Represents a 'block'; of hosting slots. Blocks serve as an
 * organizational unit for hosting auctions, and furthermore help to obscure the specific sequence of upcoming
 * domains, even from sponsors privy to the auction details.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>:This class is immutable and thread-safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class HostingBlockImpl implements HostingBlock {
    /**
     * <p>
     * Represents the ID of this block as a Long. Set in the constructor, can be null, and will not change. If not
     * null, must be positive.
     * </p>
     */
    private final Long id;

    /**
     * <p>
     * Represents the sequence number of this block within its game. Set in the constructor, can be any non-negative
     * value, and will not change.
     * </p>
     */
    private final int sequenceNumber;

    /**
     * <p>
     * Represents the maximum hosting time for this block, in minutes. Set in the constructor, can be any
     * non-negative value, and will not change.
     * </p>
     */
    private final int maxHostingTimePerSlot;

    /**
     * <p>
     * Represents an array of HostingSlot objects representing all the slots associated with this block. Set in the
     * constructor with a shallow copy. Can be empty, and will not change.
     * </p>
     */
    private final HostingSlot[] slots;

    /**
     * <p>
     * Constructor. It will make a shalow copy of the slots array.
     * </p>
     *
     * @param id the id
     * @param sequenceNumber the sequence number
     * @param slots the array of HostingSlot objects
     * @param maxHostingTimePerSlot the maximum hosting time for this block, in minutes
     *
     * @throws IllegalArgumentException If id, if given, is not positive, or if sequenceNumber is negative, or if
     *         maxHostingTimePerSlot is negative, or if slots is null or contans null elements
     */
    public HostingBlockImpl(Long id, int sequenceNumber, HostingSlot[] slots, int maxHostingTimePerSlot) {
        if (id != null) {
            Helper.checkNotPositive(id.longValue(), "Id");
        }

        Helper.checkNegative(sequenceNumber, "sequenceNumber");
        Helper.checkNegative(maxHostingTimePerSlot, "maxHostingTimePerSlot");

        //the slots can be null but should not contain null element
        if (slots != null) {
            for (int i = 0; i < slots.length; i++) {
                Helper.checkNotNull(slots[i], "HostingSlot");
            }
        }
        
        this.id = id;
        this.sequenceNumber = sequenceNumber;
        this.maxHostingTimePerSlot = maxHostingTimePerSlot;

        //shallow copy the slot array
        this.slots = slots != null?(HostingSlot[]) slots.clone():null;
    }

    /**
     * <p>
     * Returns the ID of this block as a Long, or null if no ID has yet been assigned.
     * </p>
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Returns the sequence number of this block within its game.
     * </p>
     *
     * @return the sequence number
     */
    public int getSequenceNumber() {
        return this.sequenceNumber;
    }

    /**
     * <p>
     * Returns an array of HostingSlot objects representing all the slots associated with this block. Returns a
     * shallow copy.
     * </p>
     *
     * @return the array of HostingSlot objects
     */
    public HostingSlot[] getSlots() {
        return slots!=null?(HostingSlot[]) this.slots.clone():null;
    }

    /**
     * <p>
     * Returns the maximum hosting time for this block, in minutes.
     * </p>
     *
     * @return the maximum hosting time for this block, in minutes
     */
    public int getMaxHostingTimePerSlot() {
        return this.maxHostingTimePerSlot;
    }
}
