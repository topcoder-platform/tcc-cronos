/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.prize;

import com.orpheus.game.server.framework.prize.PrizeException;
import com.orpheus.game.server.framework.prize.PrizeCalculator;

/**
 * <p>A prize amount calculator type to be used if the fixed prizes are awarded to players.</p>
 *
 * <p><b>Thread safety:</b> This class is thread-safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public class FixedPrizeCalculatorType extends AbstractPrizeCalculatorType {

    /**
     * <p>A <code>double</code> array providing the fixed prizes to be awarded to winners based on placement. The first
     * element corresponds to 1st place; the second element corresponds to 2nd place and so on.</p>
     */
    private final double[] prizes;

    /**
     * <p>Constructs new <code>FixedPrizeCalculatorType</code> instance with specified ID and name. The ID must
     * uniquelly identify this type among other types while name is expected to provide an opaque label for this prize
     * amount calculator type.</p>
     *
     * @param id an <code>int</code> providing the ID of this prize amount calculator type.
     * @param name a <code>String</code> providing the name of this prize amount calculator type.
     * @param prizes <code>double</code> array providing the fixed prizes to be awarded to winners based on placement.
     *        The first element corresponds to 1st place; the second element corresponds to 2nd place and so on.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code> or specified array contains
     *         non-positive values or all those values do not sum up to 100 or array is empty.
     */
    public FixedPrizeCalculatorType(int id, String name, double[] prizes) {
        super(id, name);
        if (prizes == null) {
            throw new IllegalArgumentException("The parameter [prizes] is NULL");
        }
        if (prizes.length == 0) {
            throw new IllegalArgumentException("The [prizes] list is empty");
        }
        for (int i = 0; i < prizes.length; i++) {
            if (prizes[i] <= 0) {
                throw new IllegalArgumentException("The prize must be positive [" + prizes[i] + "]");
            }
        }
        this.prizes = (double[]) prizes.clone();
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
        return new FixedPrizeCalculator(this.prizes);
    }
}
