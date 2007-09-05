/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.game.completion;

import com.orpheus.administration.persistence.PendingWinner;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.server.framework.game.completion.GameCompletion;
import com.orpheus.game.server.framework.game.completion.GameCompletionException;
import com.orpheus.game.server.util.AdminDataEJBAdapter;

/**
 * <p>A game completion strategy which causes the game to be completed if there are no pending winners left for the
 * game.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LastWinnerApprovedGameCompletion implements GameCompletion {

    /**
     * <p>An <code>AdminDataEJBAdapter</code> providing interface to underlying service layer.</p>
     */
    private final AdminDataEJBAdapter adminData;

    /**
     * <p>Constructs new <code>LastWinnerApprovedGameCompletion</code> instance which will be using the specified
     * interface to underlying service layer.</p>
     *
     * @param adminData an <code>AdminDataEJBAdapter</code> providing interface to underlying service layer.
     */
    public LastWinnerApprovedGameCompletion(AdminDataEJBAdapter adminData) {
        this.adminData = adminData;
    }

    /**
     * <p>Tests whether the specified game must be completed at the current moment based on the current state of the
     * game. This implementation returns <code>true</code> if there are no pending winners left for the game.</p>
     *
     * @param game a <code>Game</code> providing the details for the game.
     * @return <code>true</code> if specified games must be completed immediately; <code>false</code> otherwise.
     * @throws GameCompletionException if an error occurs while testing the game for completion.
     */
    public boolean mustCompleteGame(Game game) throws GameCompletionException {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        if (game.getEndDate() != null) {
            return false;
        } else {
            // Check if there are pending winners for the game, if so then the game must beleft intact, otherwise it
            // must be completed
            try {
                PendingWinner[] pendingWinners = this.adminData.getPendingWinners();
                boolean allWinnersApproved = true;
                for (int i = 0; allWinnersApproved && (i < pendingWinners.length); i++) {
                    if (pendingWinners[i].getGameId() == game.getId().longValue()) {
                        allWinnersApproved = false;
                    }
                }
                return allWinnersApproved;
            } catch (Exception e) {
                throw new GameCompletionException("Could not test if all winners are approved for game [" + game.getId()
                                                  + "] due to unexpected error", e);
            }
        }
    }
}
