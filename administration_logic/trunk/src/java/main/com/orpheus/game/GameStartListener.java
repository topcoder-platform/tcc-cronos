/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.Game;


/**
 * <p>
 * This interface is an observer of a status change for games that go from not started to started.
 * Implementations do not have to be thread-safe since a single thread is guaranteed to trigger this notification.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public interface GameStartListener {
    /**
     * <p>
     * This is a notification method that tells the listening observer that a game has changed its status to started.
     * Implementing classes would simply decide what to do with this information.
     * Note that this notification is sinply a way to
     * notify that the game has its time in the range of being started and is not a ohisical manifestation that a game
     * has actually started.
     * </p>
     *
     * @param game the game that shoudl be started
     */
    public void gameStatusChangedToStarted(Game game);
}
