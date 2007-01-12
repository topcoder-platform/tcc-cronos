/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import com.orpheus.game.GameDataException;
import com.orpheus.game.GameDataManager;
import com.orpheus.game.persistence.HostingSlot;

/**
 * <p>
 * A mock implementation of the GameDataManager interface. Allows to store the arguments that are
 * passed to the recordWinningBids method.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockGameDataManager implements GameDataManager {

    /**
     * <p>
     * Block id.
     * </p>
     */
    private long blockId = 0;

    /**
     * <p>
     * Bid ids.
     * </p>
     */
    private long[] bidIds = null;

    /**
     * <p>
     * Method stub. Saves block id and bidIds.
     * </p>
     */
    public void recordWinningBids(long blockId, long[] bidIds) throws GameDataException {
        this.blockId = blockId;
        this.bidIds = bidIds;
    }

    /**
     * <p>
     * Returns the block id, stored in the last call of recordWinningBids method.
     * </p>
     * @return the block id, stored in the last call of recordWinningBids method.
     */
    public long getLastBlockId() {
        return blockId;
    }

    /**
     * <p>
     * Returns the bid ids array, stored in the last call of recordWinningBids method.
     * </p>
     * @return the bid ids array, stored in the last call of recordWinningBids method.
     */
    public long[] getLastBidIds() {
        return bidIds;
    }

    public void advanceHostingSlot(long slotId) {}

    public boolean testUpcomingDomain(HostingSlot slot) { return true; }
}
