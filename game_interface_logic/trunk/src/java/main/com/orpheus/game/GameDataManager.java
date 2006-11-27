/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.HostingSlot;


/**
 * <p>
 * Defines the behavior of objects that manage Orpheus game data.
 * This interface essentially sets with two aspects of the game data interaction.
 * On the one hand we need to ensure that the public API is properly implemented (which is not
 * more than testing upcoming domain for validity, persisting winning bids, and advancing hosting slots)
 * and on the other hand we also have this contract specifying that we should be able
 * to track the assigned start dates of all games that have not yet started.
 * It is expected that the implementation swill be thread-safe.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public interface GameDataManager {
    /**
     * <p>
     * Tests whether an upcoming domain is ready to begin hosting a specific slot.  Bascially we need to ensure that the
     * domain¡¯s document root and the documents hosting all the slot¡¯s domain targets are all reachable
     * </p>
     *
     * @param slot Hosting slot to test for validity
     * @return true if valid; false otherwise.
     * @throws IllegalArgumentException if the slot is null
     * @throws GameDataException if a checked exception prevents this method from completing successfully.
     */
    public boolean testUpcomingDomain(HostingSlot slot)
        throws GameDataException;

    /**
     * <p>
     * Records the IDs of the winning bids for the slots in the specified hosting block.
     * It is expected that the component will shuffle the provided IDs into a random order
     * before handing them off to the associated persistence component to do the
     * real work.
     * </p>
     *
     * @param blockId block id for the bids
     * @param bidIds bids to be recorded
     * @throws IllegalArgumentException if the bid ids is null
     * @throws GameDataException if a checked exception prevents this method from completing successfully,
     * or if bid IDs have already been assigned to the block.
     */
    public void recordWinningBids(long blockId, long[] bidIds)
        throws GameDataException;

    /**
     * <p>This call will advance to the next hosting slot by setting
     * the hosting end timestamp on the current slot and the hosting
     * start timestamp on the next one in the same game.
     * When it does this, it should also test the subsequent domain to make sure it is still valid;
     * if not then it will flag the slot as inaccessible by setting its sequence number to ?1.
     * </p>
     *
     * @param gameId Id of the game to advance the hosting slot of
     * @throws GameDataException if a checked exception prevents this method from completing successfully.
     */
    public void advanceHostingSlot(long gameId) throws GameDataException;
}
