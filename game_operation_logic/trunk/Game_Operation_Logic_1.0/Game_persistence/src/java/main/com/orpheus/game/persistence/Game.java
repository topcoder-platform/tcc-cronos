/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import java.io.Serializable;

import java.util.Date;


/**
 * <p>
 * The {@link Game} interface represents an individual game managed by the application. It carries a unique
 * identifier, a start date, and an end date, and can provide a {@link BallColor } representing the color associated
 * with this game and a game name string computed based on the game id and color.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>Implementations should strive to be thread-safe, but they can expect to be used in
 * a thread-safe manner.
 * </p>
 * 
 * @author argolite, waits
 * @version 1.0
 */
public interface Game extends Serializable {
    /**
     * <p>
     * Gets the identifier for this game, as a Long. The identifier may be null if this Game has not yet been
     * assigned one.
     * </p>
     *
     * @return the id
     */
    Long getId();

    /**
     * <p>
     * Gets the name of this game, which is the concatenation of the name of the associated BallColor with the ID of
     * this game. If this game does not have an ID or BallColor yet assigned then that part of the name is
     * represented by a single question mark.
     * </p>
     *
     * @return the name
     */
    String getName();

    /**
     * <p>
     * Returns the BallColor object assigned to this game, or null if there is none.
     * </p>
     *
     * @return the color of the ball
     */
    BallColor getBallColor();

    /**
     * <p>
     * Returns the number of keys required to attempt to win this game.
     * </p>
     *
     * @return the key count
     */
    int getKeyCount();

    /**
     * <p>
     * Retrieves the planned or past start date for this game; will not be null.
     * </p>
     *
     * @return the start date
     */
    Date getStartDate();

    /**
     * <p>
     * Retrieves the end date of this game, if it has already ended, or null if it hasn't.
     * </p>
     *
     * @return the end date
     */
    Date getEndDate();

    /**
     * <p>
     * Retrieves an array of HostingBlock objects representing the hosting blocks within this game.
     * </p>
     *
     * @return the array of HostingBlock objects
     */
    HostingBlock[] getBlocks();
}
