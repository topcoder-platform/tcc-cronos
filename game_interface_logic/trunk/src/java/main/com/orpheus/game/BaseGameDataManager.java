/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


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
     * Represents the list of currently known not-yet-started games.
     * </p>
     *
     * <p>
     * This is initialized in all ctors, and is then changed through:
     * <li>The gameStatusChangedToStarted method when we removes the game from this list.</li>
     * <li>The newGameAvailable method when we add a new entry into the list.</li>
     * It can NOT be null, but can be empty. No elememnts of the list can be null.
     * </p>
     *
     */
    private final List notYetStartedGames;

    /**
     * <p>
     * A simple constructor, init the <code>notYetStartedGames</code> member.
     * </p>
     *
     */
    protected BaseGameDataManager() {
        notYetStartedGames = new ArrayList();
    }

    /**
     * <p>
     * This is a listener implementation method that simply takes the game to start, starts the game physically,
     * and removes it from that list.
     * All of these steps must be synchronized to achieve an atomic trasnaction and thread-safety.
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
                Date current = new Date();

                for (int i = 0; i < blocks.length; i++) {
                    HostingSlot[] slots = blocks[i].getSlots();

                    //if slot exists for the block
                    if ((slots != null) && (slots.length != 0)) {
                        //set the first slot's start date to current date to start the game
                        slots[0] = Helper.copyToSetStartDate(slots[0], current);
                    }
                }
            }

            //remove from the not started games
            notYetStartedGames.remove(game);
        }
    }

    /**
     * <p>
     * This is a listener implementation method that simply takes the new game,
     * and places this game on into the list of notYetStartedGames list.
     * We have to ensure that the list is properly sorted after insertion, closest start times to current
     * time should be closer to the beginning of the list.
     * All of these steps must be synchronized to achieve an atomic trasnaction and thread-safety.
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
            notYetStartedGames.add(game);

            //resort the whole collections
            Collections.sort(notYetStartedGames, new GameComparator());
        }
    }

    /**
     * <p>
     * Returns a copy if the notYetStartedGames list as an array.
     * When copying we ensure synchronization.
     * Simply synchronize on the <code>notYetStartedGames</code> instance.
     * </p>
     *
     * @return an array of games that have not yet started
     * @throws IllegalStateException if the manager has been stopped.
     */
    public Game[] getAllCurrentNotStartedGames() {
        checkStopped();

        synchronized (notYetStartedGames) {
            return (Game[]) notYetStartedGames.toArray(new Game[0]);
        }
    }

    /**
     * <p>
     * Copies a whole array of games into the this.notYetStartedGames list
     * When copying we ensure synchronization.
     * Simply synchronize on the <code>notYetStartedGames</code> instance.
     * </p>
     *
     * @param notYetStartedGames an array of games that have not yet started
     * @throws IllegalArgumentException if the parameter is null or the array contains null element
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void setCurrentNotStartedGames(Game[] notYetStartedGames) {
        Helper.checkObjectNotNull(notYetStartedGames, "game");

        //the elements in the array can not be null
        for (int i = 0; i < notYetStartedGames.length; i++) {
            Helper.checkObjectNotNull(notYetStartedGames[i],
                "notYetStartedGames[" + i + "]");
        }

        checkStopped();

        synchronized (this.notYetStartedGames) {
            //firstly clear the init ones
            this.notYetStartedGames.clear();
            this.notYetStartedGames.addAll(Arrays.asList(notYetStartedGames));
        }
    }

    /**
     * <p>
     * Add a new game to the list of notYetStartedGames.
     * We have to ensure that the list is properly sorted after insertion, closest start times to current
     * time should be closer to the beginning of the list.
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
            notYetStartedGames.add(game);
            Collections.sort(notYetStartedGames, new GameComparator());
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
            for (Iterator it = notYetStartedGames.iterator(); it.hasNext();) {
                Game game = (Game) it.next();

                //remove the game with the same id in the list
                if ((game.getId() != null) && (game.getId().longValue() == gameId)) {
                    it.remove();

                    return;
                }
            }
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

    /**
     * The comparator used to compare two games.
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class GameComparator implements Comparator {
        /**
         * Compares two games objects.
         * @param obj1
         *            the first object.
         * @param obj2
         *            the second object.
         * @return a negative integer, zero, or a positive integer as the date of the first
         *         argument is less than, equal to, or greater than the second.
         */
        public int compare(Object obj1, Object obj2) {
            Game game1 = (Game) obj1;
            Game game2 = (Game) obj2;
            Date date1 = game1.getStartDate();
            Date date2 = game2.getStartDate();

            if (date1.after(date2)) {
                return -1;
            }

            if (date1.before(date2)) {
                return 1;
            }

            return 0;
        }
    }
}
