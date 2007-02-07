/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.BaseGameDataManager;
import com.orpheus.game.GameDataException;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.randomstringimg.RandomStringImage;
import com.topcoder.util.algorithm.hash.HashAlgorithmManager;


/**
 * <p>
 * A simple mock of the class abstract class <code>BaseGameDataManager</code>,
 * it is used to this abstract class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestBaseGameDataManager extends BaseGameDataManager {
    /**
     * The member check whether it is stopped.
     */
    private boolean isStopped = false;

    /**
     * The constructor.
     *
     */
    public AccuracyTestBaseGameDataManager() {
        super();
    }

    /**
     * Stop the manager.
     */
    public void stopManager() {
        isStopped = true;
    }

    /**
     * The mock method implements the GameDataManager instance.
     *
     * @param slot just ignore
     * @return false
     * @throws GameDataException if occurs
     */
    public boolean testUpcomingDomain(HostingSlot slot)
        throws GameDataException {
        return false;
    }

    /**
     * The mock method implements the GameDataManager instance.
     *
     * @param blockId just ignore
     * @param bidIds just ignore
     * @throws GameDataException if occurs
     */
    public void recordWinningBids(long blockId, long[] bidIds)
        throws GameDataException {
    }

    /**
     * The mock method implements the GameDataManager instance.
     *
     * @param gameId just ignore
     * @throws GameDataException if occurs
     */
    public void advanceHostingSlot(long gameId) throws GameDataException {
    }

    /**
     * <p>
     * Check whether the manager is stopped.
     * </p>
     *
     * @return true if the manager is stopped, otherwise false.
     */
    public boolean isStopped() {
        return isStopped;
    }

    /**
     * Noting.
     * @see com.orpheus.game.BaseGameDataManager#persistSlot(com.orpheus.game.persistence.HostingSlot)
     */
    protected void persistSlot(HostingSlot slot) {
    }

    /**
     * Nothing.
     * @see com.orpheus.game.GameDataManager#regenerateBrainTeaser(long)
     */
    public void regenerateBrainTeaser(long slotId) throws GameDataException {
    }

    /**
     * Nothing.
     * @see com.orpheus.game.GameDataManager#regeneratePuzzle(long)
     */
    public void regeneratePuzzle(long slotId) throws GameDataException {
    }

    /**
     * Noting.
     * @see com.orpheus.game.BaseGameDataManager#getGameDataPersistenceLocal()
     */
    protected GameDataLocal getGameDataPersistenceLocal() {
        return null;
    }

    /**
     * Noting.
     * @see com.orpheus.game.BaseGameDataManager#getGameDataPersistenceRemote()
     */
    protected GameData getGameDataPersistenceRemote() {
        return null;
    }

    /**
     * Noting.
     * @see com.orpheus.game.BaseGameDataManager#getHashAlgorithmManager()
     */
    protected HashAlgorithmManager getHashAlgorithmManager() {
        return null;
    }

    /**
     * Noting.
     * @see com.orpheus.game.BaseGameDataManager#getRandomStringImage()
     */
    protected RandomStringImage getRandomStringImage() {
        return null;
    }
}
