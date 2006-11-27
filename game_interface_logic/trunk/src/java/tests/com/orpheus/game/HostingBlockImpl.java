/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;


/**
 * <p>
 * The mock implement HostingBlock interface.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HostingBlockImpl implements HostingBlock {
    /**
     * Constructor.
     *
     */
    public HostingBlockImpl() {
    }

    /**
     * Get the id.
     *
     * @return id
     */
    public Long getId() {
        return new Long(1);
    }

    /**
     * Get the sequence number.
     *
     * @return the sequence number.
     */
    public int getSequenceNumber() {
        return 0;
    }

    /**
     * Get an array of slot.
     *
     * @return an array of slot.
     */
    public HostingSlot[] getSlots() {
        return null;
    }

    /**
     * Get the max hosting time.
     *
     * @return 0
     */
    public int getMaxHostingTimePerSlot() {
        return 0;
    }
}
