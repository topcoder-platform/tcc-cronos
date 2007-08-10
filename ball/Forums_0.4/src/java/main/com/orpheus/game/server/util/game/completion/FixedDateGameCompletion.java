/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.game.completion;

import com.orpheus.game.server.framework.game.completion.GameCompletion;
import com.orpheus.game.server.framework.game.completion.GameCompletionException;
import com.orpheus.game.persistence.Game;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public class FixedDateGameCompletion implements GameCompletion {

    /**
     * <p>Constructs new <code>FixedDateGameCompletion</code> instance. This implementation does nothing.</p>
     */
    public FixedDateGameCompletion() {
    }

    /**
     * <p>Tests whether the specified game must be completed at the current moment based on the current state of the
     * game.</p>
     *
     * @param game a <code>Game</code> providing the details for the game.
     * @return <code>false</code> always.
     * @throws GameCompletionException if specified game does not have the date of completion already set.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public boolean mustCompleteGame(Game game) throws GameCompletionException {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        if (game.getEndDate() == null) {
            throw new GameCompletionException("The game [" + game.getId() + "] does not have the end date set");
        } else {
            return false;
        }
    }
}
