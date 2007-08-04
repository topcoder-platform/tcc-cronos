/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.prize;

import com.orpheus.game.server.framework.prize.PrizeCalculator;
import com.orpheus.game.server.framework.prize.PrizeException;
import com.orpheus.game.persistence.Game;

/**
 * <p>A prize amount calculator to be used if the fixed prizes are awarded based on the place taken by the winner.</p>
 *
 * <p><b>Thread safety:</b> This class is thread-safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public class FixedPrizeCalculator implements PrizeCalculator {

    /**
     * <p>A <code>double</code> array providing the fixed prizes to be awarded to winners based on placement. The first
     * element corresponds to 1st place; the second element corresponds to 2nd place and so on.</p>
     */
    private final double[] prizes;

    /**
     * <p>Constructs new <code>FixedPrizeCalculator</code> instance with specified fixed prizes to be awarded to players
     * based on the placement.</p>
     *
     * @param prizes a <code>double</code> array providing the fixed prizes to be awarded to winners based on placement.
     *        The first element corresponds to 1st place; the second element corresponds to 2nd place and so on.
     */
    FixedPrizeCalculator(double[] prizes) {
        this.prizes = prizes;
    }

    /**
     * <p>Calculates the prize amount which could be awarded to specified player who is currently taking the specified
     * placement in context of specified game.</p>
     *
     * @param game a <code>Game</code> providing the details for the game.
     * @param playerId a <code>long</code> providing the ID of a player to calculate the prize amount for.
     * @param placement a <code>int</code> providing the place taken by the specified player in context of the specified
     * game.
     * @return a <code>double</code> providing the prize amount to be awarded to specified player.
     * @throws PrizeException if an unexpected error occurs.
     * @throws IllegalArgumentException if specified <code>game</code> is <code>null</code>.
     */
    public double calculatePrizeAmount(Game game, long playerId, int placement) throws PrizeException {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        if ((placement < 1) || (placement > this.prizes.length)) {
            return 0.00;
        } else {
            return this.prizes[placement - 1];
        }
    }

    /**
     * <p>Gets the minimum prize amount which could be earned by the players winning the specified game. Such an amount
     * is calculated as the prize for the lowest place which could be taken by the players in context of specified game.
     * </p>
     *
     * @param game a <code>Game</code> providing the details for the game.
     * @return a <code>double</code> providing the minimum payment amount which could be earned by the players in
     *         context of specified game.
     * @throws PrizeException if an unexpected error occurs.
     * @throws IllegalArgumentException if specified <code>game</code> is <code>null</code>.
     */
    public double getMinimumPayout(Game game) throws PrizeException {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        return this.prizes[this.prizes.length - 1];
    }
}
