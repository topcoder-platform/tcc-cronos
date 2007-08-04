/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.prize;

import com.orpheus.game.server.framework.prize.PrizeException;
import com.orpheus.game.server.framework.prize.PrizeCalculator;

/**
 * <p>A prize amount calculator type to be used if the prizes are awarded based on the percentage of the overall game
 * jackpot and the place taken by the winner.</p>
 *
 * <p><b>Thread safety:</b> This class is thread-safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PercentagePrizeCalculatorType extends AbstractPrizeCalculatorType {

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
     * <p>Constructs new <code>PercentagePrizeCalculatorType</code> instance with specified ID and name. The ID must
     * uniquelly identify this type among other types while name is expected to provide an opaque label for this prize
     * amount calculator type.</p>
     *
     * @param id an <code>int</code> providing the ID of this prize amount calculator type.
     * @param name a <code>String</code> providing the name of this prize amount calculator type.
     * @param percents a <code>double</code> providing the percentage of the overall game jackpot to be awarded to
     *        winners based on placement. The first element corresponds to 1st place; the second element corresponds to
     *        2nd place and so on.
     * @param adminFee a <code>double</code> providing the administration fee. 
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code> or specified array contains
     *         non-positive values or all those values do not sum up to 100 or array is empty.
     */
    public PercentagePrizeCalculatorType(int id, String name, double[] percents, double adminFee) {
        super(id, name);
        if ((adminFee < 0) || (adminFee > 1.0)) {
            throw new IllegalArgumentException("The parameter [adminFee] is not in valid range [" + adminFee + "]");
        }
        if (percents == null) {
            throw new IllegalArgumentException("The parameter [percents] is NULL");
        }
        if (percents.length == 0) {
            throw new IllegalArgumentException("");
        }
        double sum = 0;
        for (int i = 0; i < percents.length; i++) {
            if (percents[i] <= 0) {
                throw new IllegalArgumentException("The percent must be positive [" + percents[i] + "]");
            }
            sum += percents[i];
        }
        if (Math.abs(sum - 100.00) > 0.01) {
            throw new IllegalArgumentException("The persents do not sum up to 100 [" + sum + "]");
        }
        this.percents = (double[]) percents.clone();
        this.adminFee = adminFee;
    }

    /**
     * <p>Gets the prize amount calculator of this type.</p>
     *
     * @return a <code>PrizeCalculator</code> to be used for calculating the prize amounts in accordance with rules set
     *         for this type.
     * @throws PrizeException if an unexpected error occurs while creating thew new prize amount calculator instance of
     *         this type.
     */
    public PrizeCalculator getCalculator() throws PrizeException {
        return new PercentagePrizeCalculator(this.percents, this.adminFee);
    }
}
