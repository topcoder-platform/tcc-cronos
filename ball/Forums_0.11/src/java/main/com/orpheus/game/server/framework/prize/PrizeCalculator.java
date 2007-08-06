/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.framework.prize;

import com.orpheus.game.persistence.Game;

/**
 * <p>An interface for calculators for prize amounts which could be earned by the players winning the specified games.
 * </p>
 *
 * <p><b>Thread safety:</b> The implementations are required to operate in thread-safe fashion.</p>
 *
 * @author isv
 * @version 1.0
 */
public interface PrizeCalculator {

    /**
     * <p>Calculates the prize amount which could be awarded to specified player who is currently taking the specified
     * placement in context of specified game.</p>
     *
     * @param game a <code>Game</code> providing the details for the game. 
     * @param playerId a <code>long</code> providing the ID of a player to calculate the prize amount for.
     * @param placement a <code>int</code> providing the place taken by the specified player in context of the specified
     *        game.
     * @return a <code>double</code> providing the prize amount to be awarded to specified player.
     * @throws PrizeException if an unexpected error occurs.
     */
    public double calculatePrizeAmount(Game game, long playerId, int placement) throws PrizeException;

    /**
     * <p>Gets the minimum prize amount which could be earned by the players winning the specified game.</p>
     *
     * @param game a <code>Game</code> providing the details for the game.
     * @return a <code>double</code> providing the minimum payment amount which could be earned by the players in
     *         context of specified game.
     * @throws PrizeException if an unexpected error occurs.
     */
    public double getMinimumPayout(Game game) throws PrizeException;
}
