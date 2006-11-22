/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests.mock;

import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;

/**
 * A mock implementation of HostingBlock.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockHostingBlock implements HostingBlock {
    /**
     * Represents the sequence number.
     */
    private int seq;

    /**
     * Creates the mock hosting block.
     * 
     * @param seq
     *            sequence number
     */
    MockHostingBlock(int seq) {
        this.seq = seq;
    }

    /**
     * No implementations.
     * 
     * @return always null
     */
    public Long getId() {
        return null;
    }

    /**
     * Returns the sequence number.
     * 
     * @return sequence number
     */
    public int getSequenceNumber() {
        return seq;
    }

    /**
     * Returns a pre-defined HostingSlot array.
     * 
     * @return HostingSlot array
     */
    public HostingSlot[] getSlots() {
        HostingSlot[] slots = new HostingSlot[3];
        int[] seqNo = new int[] {};

        if (seq == 1) {
            seqNo = new int[] { 10, 11, 12 };
        } else if (seq == 1) {
            seqNo = new int[] { 4, 5, 6 };
        } else {
            seqNo = new int[] { 7, 8, 9 };
        }
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new MockHostingSlot(seqNo[i], seqNo[i], seqNo[i]);
        }
        return slots;
    }

    /**
     * No implementation.
     * 
     * @return always 0
     */
    public int getMaxHostingTimePerSlot() {
        return 0;
    }

}
