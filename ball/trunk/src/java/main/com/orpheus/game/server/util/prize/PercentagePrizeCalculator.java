/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.prize;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.server.framework.prize.PrizeCalculator;
import com.orpheus.game.server.framework.prize.PrizeException;

/**
 * <p>A prize amount calculator to be used if the prizes are awarded based on the percentage of the overall game jackpot
 * and the place taken by the winner.</p>
 *
 * <p><b>Thread safety:</b> This class is thread-safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PercentagePrizeCalculator implements PrizeCalculator {

    /**
     * <p>A <code>double</code> array providing the percentage of the overall game jackpot to be awarded to winners
     * based on placement. The first element corresponds to 1st place; the second element corresponds to 2nd place and
     * so on.</p>
     */
    private final double[] percents;

    /**
     * <p>A <code>double</code> providing the administration fee to be subtracted from overall game jackpot prior to
     * calculating the prize amounts. Must be in range from <code>0.00</code> to <code>1.0</code>.</p>
     */
    private final double adminFee;

    /**
     * <p>Constructs new <code>PercentagePrizeCalculator</code> instance with specified percentages to be used for
     * calculating the prize amounts based on the placement of the player.</p>
     * 
     * @param percents a <code>double</code> providing the percentage of the overall game jackpot to be awarded to
     *        winners based on placement. The first element corresponds to 1st place; the second element corresponds to
     *        2nd place and so on.
     * @param adminFee a <code>double</code> providing the administration fee.
     */
    PercentagePrizeCalculator(double[] percents, double adminFee) {
        this.percents = percents;
        this.adminFee = adminFee;
    }

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
     * @throws IllegalArgumentException if specified <code>game</code> is <code>null</code>.
     */
    public double calculatePrizeAmount(Game game, long playerId, int placement) throws PrizeException {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        if ((placement < 1) || (placement > this.percents.length)) {
            return 0.00;
        } else {
            double jackpot = calculateGameJackpot(game);
            return jackpot * this.percents[placement - 1] / 100.00;
        }
    }

    /**
     * <p>Gets the minimum prize amount which could be earned by the players winning the specified game. Such an amount
     * is calculated as the percentage of overall game jackpot corresponding to lowest place which could be taken by the
     * players in context of specified game.</p>
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
        double jackpot = calculateGameJackpot(game);
        return jackpot * this.percents[this.percents.length - 1] / 100.00;
    }

    /**
     * <p>Gets the amount of jackpot for specified game without the administration fee.</p>
     *
     * @param game a <code>Game</code> providing the details for the game to calculate the jackpot for.
     * @return a <code>double</code> providing the jackpot for specified game with subtracted administration fee. 
     */
    private double calculateGameJackpot(Game game) {
        double payment = 0.00;
        HostingBlock[] blocks = game.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            HostingBlock block = blocks[i];
            HostingSlot[] slots = block.getSlots();
            for (int j = 0; j < slots.length; j++) {
                HostingSlot slot = slots[j];
                if (slot.getHostingStart() != null) {
                    payment += slot.getWinningBid();
                }
            }
        }
        return payment * (1.0 - this.adminFee);
    }
}
