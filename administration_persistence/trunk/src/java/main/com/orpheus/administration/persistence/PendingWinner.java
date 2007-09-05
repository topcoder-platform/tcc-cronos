/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import java.io.Serializable;

/**
 * <p>Interface representing the details associated with a provisional winner of a game. It is used by the {@link
 * AdminData AdminData} as a DTO but is not actually manipulated by the EJB layer. Instead, it is used by the DAO.
 *
 * <p><strong>Thread Safety</strong>: Implementations of this interface are expected to be thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface PendingWinner extends Serializable {
    /**
     * Returns the player ID.
     *
     * @return the player ID
     */
    public long getPlayerId();

    /**
     * Returns the game ID.
     *
     * @return the game ID
     */
    public long getGameId();

    /**
     * Returns the payout due to the player if he or she is declared the winner.
     *
     * @return the payout due to the player if he or she is declared the winner
     */
    public int getPayout();

    /**
     * Returns the placement for the player if he or she is declared the winner.
     *
     * @return the placement for the player if he or she is declared the winner
     */
    public int getPlacement();
}


