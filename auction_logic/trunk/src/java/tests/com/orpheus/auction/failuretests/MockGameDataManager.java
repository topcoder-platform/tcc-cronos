/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.failuretests;

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
     * Whether an exception should be thrown.
     */
    private boolean failed = false;

    /**
     * Sets whether an exception should be thrown.
     * @param failed
     *            whether an exception should be thrown
     */
    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    /**
     * <p>
     * Method stub. Saves block id and bidIds.
     * </p>
     * @param blockId
     *            ignore
     * @param bidIds
     *            ignore
     * @throws GameDataException
     *             ignore
     */
    public void recordWinningBids(long blockId, long[] bidIds) throws GameDataException {
        if (failed) {
            throw new GameDataException();
        }

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
