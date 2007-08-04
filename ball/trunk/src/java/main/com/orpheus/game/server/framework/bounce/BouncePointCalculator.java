/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.framework.bounce;

/**
 * <p>An interface for calculators for bounce points which could be earned by the players participating in the specified
 * games.
 * </p>
 *
 * <p><b>Thread safety:</b> The implementations are required to operate in thread-safe fashion.</p>
 *
 * @author isv
 * @version 1.0
 */
public interface BouncePointCalculator {

    /**
     * <p>Calculates the amount of bounce points which could be awarded to player who have triggered the specified
     * event.</p>
     *
     * @param event a <code>BouncePointEvent</code> providing the details for the event triggered by the player in the
     *        course of the game play. 
     * @return a <code>int</code> providing the amount of bounce points which could be awarded to the player who have
     *         triggered the specified event.
     * @throws BouncePointException if an unexpected error occurs.
     */
    public int calculateBouncePoints(BouncePointEvent event) throws BouncePointException;
}
