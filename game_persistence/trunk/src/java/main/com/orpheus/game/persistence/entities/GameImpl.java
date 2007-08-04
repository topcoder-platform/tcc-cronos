/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.Helper;
import com.orpheus.game.persistence.HostingBlock;

import java.util.Date;


/**
 * <p>
 * Simple implementation of the Game. Represents an individual game managed by the application. It carries a unique
 * identifier, a start date, and an end date, and can provide a BallColor representing the color associated with
 * this game and a game name string computed based on the game id and color.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>:This class is immutable and thread-safe.
 * </p>
 * @author argolite, waits
 * @version 1.0
 */
public class GameImpl implements Game {
    /**
     * <p>
     * Represents the identifier for this game, as a Long. Set in the constructor, can be null, and will not change.
     * If not null, must be positive.
     * </p>
     */
    private final Long id;

    /**
     * <p>
     * Represents the name of this game, which is the concatenation of the name of the associated BallColor with the
     * ID of this game.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Represents the BallColor object assigned to this game, or null if there is none. Set in the constructor and
     * will not change.
     * </p>
     */
    private final BallColor ballColor;

    /**
     * <p>
     * Represents the number of keys required to attempt to win this game. Set in the constructor, can be any
     * non-negative value, and will not change.
     * </p>
     */
    private final int keyCount;

    /**
     * <p>
     * Represents the planned or past start date for this game; will not be null. Set in the constructor and will not
     * change.
     * </p>
     */
    private final Date startDate;

    /**
     * <p>
     * Represents the end date of this game, if it has already ended, or null if it hasn't. Set in the constructor
     * and will not change.
     * </p>
     */
    private final Date endDate;

    /**
     * <p>
     * Represents an array of HostingBlock objects representing the hosting blocks within this game. Set in the
     * constructor and will not change, and will not be null or contain null elements
     * </p>
     */
    private final HostingBlock[] blocks;

    /**
     * <p>
     * The ID of bounce point calculation schema. Such schema provides the rules to be used for calculating the bounce
     * points to be awarded to players playing this game.
     * </p>
     */
    private final int bouncePointCalculationType;

    /**
     * <p>
     * The ID of prize amount calculation schema. Such schema provides the rules to be used for calculating the prize
     * amounts to be awarded to players winning this game.
     * </p>
     */
    private final int prizeCalculationType;

    /**
     * <p>
     * The ID of game completion schema. Such schema provides the rules to be used for determining when the game has to
     * be completed.
     * </p>
     */
    private final int gameCompletionType;

    /**
     * <p>
     * Constructor.  Set name to the concatenation of the name of the associated BallColor with the ID of this game.
     * If this game does not have an ID or BallColor yet assigned then that part of the name is represented by a
     * single question. Make a shallow copy of the blocks array.
     * </p>
     *
     * @param id the id
     * @param ballColor the color of the ball
     * @param keyCount the key count
     * @param startDate the start date
     * @param endDate the end date
     * @param blocks the array of HostingBlock objects
     * @param bouncePointCalculationType a reference to bounce point calculation schema.
     * @param prizeCalculationType a reference to prize calculation schema. 
     * @param gameCompletionType a reference to game completion schema.
     *
     * @throws IllegalArgumentException If id, if given, is not positive, or if keyCount is negative, or if startDate
     *         is null, or if blocks is null or contans null elements, or stareDate early endDate if both provided.
     */
    public GameImpl(Long id, BallColor ballColor, int keyCount, Date startDate, Date endDate, HostingBlock[] blocks,
                    int bouncePointCalculationType, int prizeCalculationType, int gameCompletionType) {
        if (id != null) {
            Helper.checkNotPositive(id.longValue(), "Id");
        }

        Helper.checkNotNull(ballColor, "BallColor");
        Helper.checkNegative(keyCount, "keyCount");
        Helper.checkNotNull(startDate, "StartDate");

        //the HostingBlock should not be null and should not contain null element
        Helper.checkNotNullOrContainNullElement(blocks, "HostingBlocks");

        //the startDate should be before the endDate
        if ((endDate != null) && startDate.after(endDate)) {
            throw new IllegalArgumentException("The startDate should be before the endDate");
        }

        this.id = id;
        this.ballColor = ballColor;
        this.keyCount = keyCount;
        this.startDate = startDate;
        this.endDate = endDate;

        //set the name
        name = (this.id != null) ? (ballColor.getName() + id.longValue()) : ballColor.getName();

        //shallow copy the block
        this.blocks = (HostingBlock[]) blocks.clone();

        this.bouncePointCalculationType = bouncePointCalculationType;
        this.prizeCalculationType = prizeCalculationType;
        this.gameCompletionType = gameCompletionType;
    }

    /**
     * <p>
     * Gets the identifier for this game, as a Long. The identifier may be null if this Game has not yet been
     * assigned one.
     * </p>
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Gets the name of this game, which is the concatenation of the name of the associated BallColor with the ID of
     * this game.
     * </p>
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Returns the BallColor object assigned to this game, or null if there is none.
     * </p>
     *
     * @return the color of the ball
     */
    public BallColor getBallColor() {
        return this.ballColor;
    }

    /**
     * <p>
     * Returns the number of keys required to attempt to win this game.
     * </p>
     *
     * @return the key count
     */
    public int getKeyCount() {
        return this.keyCount;
    }

    /**
     * <p>
     * Retrieves the planned or past start date for this game; will not be null.
     * </p>
     *
     * @return the start date
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * <p>
     * Retrieves the end date of this game, if it has already ended, or null if it hasn't.
     * </p>
     *
     * @return the end date
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * <p>
     * Retrieves an array of HostingBlock objects representing the hosting blocks within this game. Returns a shallow
     * copy.
     * </p>
     *
     * @return the array of HostingBlock objects
     */
    public HostingBlock[] getBlocks() {
        return (HostingBlock[]) this.blocks.clone();
    }

    /**
     * <p>
     * Retrieves the ID of bounce point calculation schema. Such schema provides the rules to be used for
     * calculating the bounce points to be awarded to players playing this game.
     * </p>
     *
     * @return the reference to bounce point calculation schema.
     */
    public int getBouncePointCalculationType() {
        return this.bouncePointCalculationType;
    }

    /**
     * <p>
     * Retrieves the ID of prize amount calculation schema. Such schema provides the rules to be used for
     * calculating the prize amounts to be awarded to players winning this game.
     * </p>
     *
     * @return the reference to prize amount calculation schema.
     */
    public int getPrizeCalculationType() {
        return this.prizeCalculationType;
    }

    /**
     * <p>
     * Retrieves the ID of game completion schema. Such schema provides the rules to be used for determining when the
     * game has to be completed.
     * </p>
     *
     * @return the reference to game completion schema.
     */
    public int getCompletionType() {
        return this.gameCompletionType;
    }
}
