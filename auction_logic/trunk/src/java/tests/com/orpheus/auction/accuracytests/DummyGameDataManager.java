/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import com.orpheus.game.GameDataException;
import com.orpheus.game.GameDataManager;
import com.orpheus.game.persistence.HostingSlot;

/**
 * Dummy GameDataManager implementation used in tests.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class DummyGameDataManager implements GameDataManager {

    /**
     * The block id.
     */
    private long blockId;
    
    /**
     * The bids id.
     */
    private long[] bidIds;
    
    /**
     * Sets the given parameters to internal fields.
     * 
     * @param blockId the block's id.
     * @param bidIds an array of bids ids.
     * 
     * @throws GameDataException never.
     */
    public void recordWinningBids(long blockId, long[] bidIds) throws GameDataException {
        this.blockId = blockId;
        this.bidIds = bidIds;
    }

    /**
     * Returns the bid ids arrays.
     * 
     * @return array of bid ids.
     */
    public long[] getBidIds() {
        return bidIds;
    }

    /**
     * The block id.
     * 
     * @return block id.
     */
    public long getBlockId() {
        return blockId;
    }

    public void advanceHostingSlot(long slotId) {}

    public boolean testUpcomingDomain(HostingSlot slot) { return true; }

}
