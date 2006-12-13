/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.accuracytests.handlers;

import com.orpheus.administration.persistence.PendingWinner;

/**
 * <p>A simple data structure implementation of the <code>PendingWinner</code> interface. Instances are created by
 * {@link SQLServerAdminDataDAO SQLServerAdminDataDAO}.
 *
 * <p><strong>Thread Safety</strong>: This object is immutable and thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class PendingWinnerImpl implements PendingWinner {
    /**
     * The player ID. This member is initialized in the constructor and is immutable. It is expected to contain
     * non-negative values but this constraint is not enforced.
     */
    private final long playerId;

    /**
     * The game ID. This member is initialized in the constructor and is immutable. It is expected to contain
     * non-negative values but this constraint is not enforced.
     */
    private final long gameId;

    /**
     * The the payout due to the player if he or she is declared the winner. This member is initialized in the
     * constructor and is immutable. It is expected to contain non-negative values but this constraint is not
     * enforced.
     */
    private final int payout;

    /**
     * Constructs a new <code>PendingWinnerImpl</code> with the specified fields.
     *
     * @param playerId the player ID
     * @param gameId game ID
     * @param payout the payout due to the player if he or she is declared the winner
     */
    public PendingWinnerImpl(long playerId, long gameId, int payout) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.payout = payout;
    }

    /**
     * Returns the player ID.
     *
     * @return the player ID
     */
    public long getPlayerId() {
        return playerId;
    }

    /**
     * Returns the game ID.
     *
     * @return the game ID
     */
    public long getGameId() {
        return gameId;
    }

    /**
     * Returns the payout due to the player if he or she is declared the winner.
     *
     * @return the payout due to the player if he or she is declared the winner
     */
    public int getPayout() {
        return payout;
    }
}
