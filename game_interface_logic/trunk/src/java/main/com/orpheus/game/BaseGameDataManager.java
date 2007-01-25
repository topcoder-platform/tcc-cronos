/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * This is a base abstraction of the manager.
 * It adds implementations of listener methods (the manager becomes listener)
 * as well as direct game data manipulation for starting game functionality.
 * </p>
 *
 * <p>
 * The added methods are thread-safe. Sub classes must be thread-safe.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseGameDataManager implements GameDataManager,
    GameStartListener, NewGameAvailableListener {

    /**
     * <p>
     * Represents the collection of currently known not-yet-started games.
     * </p>
     *
     * <p>
     * This is initialized in all ctors, and is then changed through:
     * <li>The gameStatusChangedToStarted method when we remove an entry</li>
     * <li>The newGameAvailable method when we add a new entry</li>
     * It can NOT be null, but can be empty. None of the values may be null.
     * </p>
     */
    private final Map notYetStartedGames;

    /**
     * <p>
     * A simple constructor, init the <code>notYetStartedGames</code> member.
     * </p>
     *
     */
    protected BaseGameDataManager() {
        notYetStartedGames = new HashMap();
    }

    /**
     * <p>Update the hosting slot in database. The impl class need to call game data ejb to update the slot to db.
     * Basically, the start date of slot will be updated.</p>
     * 
     * @param slot HostingSlot with new start date
     */
    protected abstract void persistSlot(HostingSlot slot);
    
    /**
     * <p>
     * This is a listener implementation method that simply takes the game to start, starts the game physically,
     * and removes it from that list.
     * All of these steps must be synchronized to achieve an atomic transaction and thread-safety.
     * Simply synchronize on the <code>notYetStartedGames</code> instance.
     * </p>
     *
     * @param game game that should be started
     * @throws IllegalArgumentException if the game is null
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void gameStatusChangedToStarted(Game game) {
        Helper.checkObjectNotNull(game, "game");
        checkStopped();

        synchronized (notYetStartedGames) {
            //get the blocks of the game
            HostingBlock[] blocks = game.getBlocks();

            if (blocks != null) {
                hosting_blocks:
                for (int blockNumber = 0; blockNumber < blocks.length; blockNumber++) {
                    HostingSlot[] slots = blocks[blockNumber].getSlots();

                    //if slot exists for the block
                    if (slots != null) {
                        for (int slotNumber = 0; slotNumber < slots.length; slotNumber++) {
                            HostingSlot slot = slots[slotNumber];

                            // look out for deleted slots
                            if (slot.getSequenceNumber() >= 0) {

                                // set the slot's start date in the DB to the current date to
                                // start the game
                                persistSlot(Helper.copyToSetStartDate(slots[0], new Date()));

                                break hosting_blocks;
                            }
                        }
                    }
                }
            }

            // remove from the not started games
            notYetStartedGames.remove(game.getId());
        }
    }

    /**
     * <p>
     * This is a listener implementation method that simply takes the new game,
     * and places this game into the notYetStartedGames map.
     * This must be synchronized to achieve an atomic transaction and thread-safety.
     * Simply synchronize on the <code>notYetStartedGames</code> instance.
     * </p>
     *
     * @param game the new game
     * @throws IllegalArgumentException if the parameter is null
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void newGameAvailable(Game game) {
        Helper.checkObjectNotNull(game, "game");
        checkStopped();

        synchronized (notYetStartedGames) {
            notYetStartedGames.put(game.getId(), game);
        }
    }

    /**
     * <p>
     * Returns a copy if the notYetStartedGames list as an array.
     * When copying we ensure synchronization.
     * Simply synchronize on the <code>notYetStartedGames</code> instance.
     * </p>
     *
     * @return an array of games that have not yet started, in no particular order
     * @throws IllegalStateException if the manager has been stopped.
     */
    public Game[] getAllCurrentNotStartedGames() {
        checkStopped();

        synchronized (notYetStartedGames) {
            return (Game[]) notYetStartedGames.values().toArray(new Game[0]);
        }
    }

    /**
     * <p>
     * Copies a whole array of games into the this.notYetStartedGames list
     * When copying we ensure synchronization.
     * Simply synchronize on the <code>notYetStartedGames</code> instance.
     * </p>
     *
     * @param games an array of games that have not yet started
     * @throws IllegalArgumentException if the parameter is null or the array contains null element
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void setCurrentNotStartedGames(Game[] games) {
        Helper.checkObjectNotNull(games, "game");

        //the elements in the array can not be null
        for (int i = 0; i < games.length; i++) {
            Helper.checkObjectNotNull(games[i],
                    "games[" + i + "]");
        }

        checkStopped();

        synchronized (notYetStartedGames) {
            // first clear the current games
            notYetStartedGames.clear();

            // then insert the specified ones
            for (int i = 0; i < games.length; i++) {
                notYetStartedGames.put(games[i].getId(), games[i]);
            }
        }
    }

    /**
     * <p>
     * Add a new game to the list of notYetStartedGames.
     * All of these steps must be synchronized to achieve an atomic trasnaction and thread-safety.
     * Simply synchronize on the notYetStartedGames instance.
     * </p>
     *
     * @param game a new game which has not started yet
     * @throws IllegalArgumentException if the parameter is null
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void addNewNotStartedGame(Game game) {
        Helper.checkObjectNotNull(game, "game");
        checkStopped();

        synchronized (notYetStartedGames) {
            notYetStartedGames.put(game.getId(), game);
        }
    }

    /**
     * <p>
     * Remove an existing game from the list of notYetStartedGames.
     * All of these steps must be synchronized to achieve an atomic trasnaction and thread-safety.
     * Simply synchronize on the notYetStartedGames instance.
     * </p>
     *
     * @param gameId id of the game to remove
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void removeStartedGameFromNotStartedList(long gameId) {
        checkStopped();

        synchronized (notYetStartedGames) {
            notYetStartedGames.remove(new Long(gameId));
        }
    }

    /**
     * <p>
     * Check whether the manager is stopped or not.
     * </p>
     *
     * @throws IllegalStateException if it is already stopped.
     */
    private void checkStopped() {
        if (isStopped()) {
            throw new IllegalStateException("The manager is stopped.");
        }
    }

    /**
     * <p>
     * This is a clean up routine or the manager.
     * If the manager uses any kind of remote resources such as db connections, or threads this method
     * would be implemented to call on all the resources to clean up.
     * Any exceptions should be wrapped into GameDataException.
     * </p>
     *
     * @throws GameDataException if there were any issues with thos method call.
     */
    public abstract void stopManager() throws GameDataException;

    /**
     * <p>
     * An abstract method used to check whether the manager is stopped or not.
     * </p>
     *
     * @return true if the manager is stopped, otherwise false.
     */
    public abstract boolean isStopped();

}

