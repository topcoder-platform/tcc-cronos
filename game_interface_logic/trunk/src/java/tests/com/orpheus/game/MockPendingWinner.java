/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.administration.persistence.PendingWinner;


/**
 * This is a dummy class for PendingWinner.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPendingWinner implements PendingWinner {
    /**
     * playerId.
     */
    private long playerId;

    /**
     * gameId.
     */
    private long gameId;

    /**
     * payout.
     */
    private int payout;

    /**
     * get player id.
     * @return player id
     */
    public long getPlayerId() {
        return playerId;
    }

    /**
     * get game id.
     * @return game id
     */
    public long getGameId() {
        return gameId;
    }

    /**
     * get payout.
     * @return payout
     */
    public int getPayout() {
        return payout;
    }

    /**
     * set game id.
     * @param gameId game id
     */
    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    /**
     * set payout.
     * @param payout payout
     */
    public void setPayout(int payout) {
        this.payout = payout;
    }

    /**
     * set playerId.
     * @param playerId playerId
     */
    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
