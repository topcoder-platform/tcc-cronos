/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.framework.game.completion;

import com.orpheus.game.persistence.Game;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public interface GameCompletion {

    /**
     * <p>Tests whether the specified game must be completed at the current moment based on the current state of the
     * game.</p>
     *
     * @param game a <code>Game</code> providing the details for the game.
     * @return <code>true</code> if specified games must be completed immediately; <code>false</code> otherwise.
     * @throws GameCompletionException if an error occurs while testing the game for completion.
     */
    public boolean mustCompleteGame(Game game) throws GameCompletionException;
}
