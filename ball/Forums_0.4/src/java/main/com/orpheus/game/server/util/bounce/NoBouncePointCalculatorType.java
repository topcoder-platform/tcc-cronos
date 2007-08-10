/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.bounce;

import com.orpheus.game.server.framework.bounce.BouncePointException;
import com.orpheus.game.server.framework.bounce.BouncePointCalculator;

/**
 * <p>A bounce point calculator type to be used if the game rules do not assume any bounce points awarding to players.
 * </p>
 *
 * <p><b>Thread safety:</b> This class is thread-safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public class NoBouncePointCalculatorType extends AbstractBouncePointCalculatorType {

    /**
     * <p>A cached <code>NoBouncePointsCalculator</code> to be returned on each invocation of {@link #getCalculator()}
     * method.</p>
     */
    private final NoBouncePointsCalculator calculator;

    /**
     * <p>Constructs new <code>NoBouncePointCalculatorType</code> instance with specified ID and name. The ID must
     * uniquelly identify this type among other types while name is expected to provide an opaque label for this bounce
     * point calculator type.</p>
     * 
     * @param id an <code>int</code> providing the ID of this bounce point calculator type.
     * @param name a <code>String</code> providing the name of this bounce point calculator type.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public NoBouncePointCalculatorType(int id, String name) {
        super(id, name);
        this.calculator = new NoBouncePointsCalculator();
    }

    /**
     * <p>Gets the bounce point calculator of this type.
     *
     * @return a <code>BouncePointCalculator</code> to be used for calculating the bounce points in accordance with
     *         rules set for this type.
     * @throws BouncePointException if an unexpected error occurs while creating thew new bounce point calculator
     *         instance of this type.
     */
    public BouncePointCalculator getCalculator() throws BouncePointException {
        return this.calculator;
    }
}
