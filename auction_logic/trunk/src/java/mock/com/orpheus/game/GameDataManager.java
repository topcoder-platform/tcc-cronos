/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

/**
 * <p>
 * This interface is referenced from the Auction Logic component but the Game Logic component where
 * it is defined is still under design, so implementation taken from the Auction Logic requirements
 * specification is used here. After the Game Logic will be released this interface can be removed.
 * </p>
 * <p>
 * In order to test Auction Logic component mock implementation of this interface is used. But when
 * you will substitute this interface with the real one, some modifications of mock class will be
 * required. Actually the stubs for the all methods except recordWinningBids should be added.
 * </p>
 * <p>
 * Defines the behavior of objects that manage Orpheus game data.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface GameDataManager {
    /**
     * <p>
     * Records the IDs of the winning bids for the slots in the specified hosting block.
     * </p>
     * @param blockId block id.
     * @param bidIds winning bids.
     * @throws GameDataException if a checked exception prevents this method from completing
     *         successfully, or if bid IDs have already been assigned to the block.
     */
    public void recordWinningBids(long blockId, long[] bidIds) throws GameDataException;
}
