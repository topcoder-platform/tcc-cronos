/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.Game;


/**
 * <p>
 * This interface is an observer of new games that have been added to the server. We only consider new games to be ones
 * that have not been started yet.
 * Implementations do not have to be thread-safe since a single thread is guaranteed to trigger this notification.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public interface NewGameAvailableListener {
    /**
     * <p>This is a notification method that tells the listening observer that a new game has been descovered.
     * Implementing classes would simply decide what to do with this information.
     * </p>
     *
     * @param game the new game that was discovered on the server
     */
    public void newGameAvailable(Game game);
}
