/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.bounce;

import com.orpheus.game.server.framework.bounce.BouncePointCalculator;
import com.orpheus.game.server.framework.bounce.BouncePointEvent;
import com.orpheus.game.server.framework.bounce.BouncePointException;

/**
 * <p>A bounce point calculator which always returns <code>0</code> thus not awarding any bounce points regardless of
 * the original game play event which has triggered the bounce point calculation. Such a calculator could be used if the
 * game rules do not assume any bounce points awarding to players.</p>
 *
 * <p><b>Thread safety:</b> This class is thread-safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public class NoBouncePointsCalculator implements BouncePointCalculator {

    /**
     * <p>Constructs new <code>NoBouncePointsCalculator</code> instance. This implementation does nothing.</p>
     */
    public NoBouncePointsCalculator() {
    }

    /**
     * <p>Calculates the amount of bounce points which could be awarded to player who have triggered the specified
     * event.</p>
     *
     * @param event a <code>BouncePointEvent</code> providing the details for the event triggered by the player in the
     *        course of the game play.
     * @return <code>0</code> always.
     * @throws BouncePointException if an unexpected error occurs.
     */
    public int calculateBouncePoints(BouncePointEvent event) throws BouncePointException {
        return 0;
    }
}
