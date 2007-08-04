/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;

import java.util.Date;

/**
 * The implementation of Game interface which represents a BarookaBall game. It
 * carries a unique identifier, a start date, and an end date, and can provide a
 * BallColor representing the color associated with this game and a game name
 * string computed based on the game id and color.<br/> No validation is done
 * for any of the fields as this is a plain POJO class meant to be used as a
 * data transfer object between remote calls.<br/> Thread-Safety: this class is
 * not thread safe as it is mutable.
 *
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
public class GameImpl implements Game {

    /**
     * will hold the identifier for this game.<br/> It can be retrieved/set
     * using corresponding getter/setter.<br/> It may be null.
     *
     */
    private Long id;

    /**
     * will hold the name of this game.<br/> It can be retrieved/set using
     * corresponding getter/setter.<br/> It may be null.
     *
     */
    private String name;

    /**
     * will hold the BallColor object assigned to this game.<br/> It can be
     * retrieved/set using corresponding getter/setter.<br/> It may be null.
     *
     */
    private BallColor ballColor;

    /**
     * will hold the number of keys required to attempt to win this game.<br/>
     * It can be retrieved/set using corresponding getter/setter.<br/> It may
     * be any value.
     *
     */
    private int keyCount;

    /**
     * will hold the planned or past start date for this game.<br/> It can be
     * retrieved/set using corresponding getter/setter.<br/> It may be null.
     *
     */
    private Date startDate;

    /**
     * will hold the end date of this game, if it has already ended.<br/> It
     * can be retrieved/set using corresponding getter/setter.<br/> It may be
     * null.
     *
     */
    private Date endDate;

    /**
     * will hold an array of HostingBlock objects representing the hosting
     * blocks within this game.<br/> It can be retrieved/set using
     * corresponding getter/setter.<br/> It may be null.
     *
     */
    private HostingBlock[] blocks;

    /**
     * <p>
     * The ID of bounce point calculation schema. Such schema provides the rules to be used for calculating the bounce
     * points to be awarded to players playing this game.
     * </p>
     */
    private int bouncePointCalculationType;

    /**
     * <p>
     * The ID of prize amount calculation schema. Such schema provides the rules to be used for calculating the prize
     * amounts to be awarded to players winning this game.
     * </p>
     */
    private int prizeCalculationType;

    /**
     * <p>
     * The ID of game complettion schema. Such schema provides the rules to be used for determining when this game must
     * be completed.
     * </p>
     */
    private int completionType;

    /**
     * Empty constructor.
     *
     */
    public GameImpl() {

    }

    /**
     * Returns the BallColor object assigned to this game.
     *
     *
     * @return Returns the ballColor.
     */
    public BallColor getBallColor() {
        return ballColor;
    }

    /**
     * Sets the BallColor of this game.
     *
     *
     * @param ballColor
     *            The ballColor to set. It can be null.
     */
    public void setBallColor(BallColor ballColor) {
        this.ballColor = ballColor;
    }

    /**
     * Retrieves an array of HostingBlock objects representing the hosting
     * blocks within this game.
     *
     *
     * @return Returns array of HostingBlock objects representing the hosting
     *         blocks within this game.
     */
    public HostingBlock[] getBlocks() {
        return blocks;
    }

    /**
     * Sets array of HostingBlock objects representing the hosting blocks within
     * this game.
     *
     * @param blocks
     *            of HostingBlock objects representing the hosting blocks within
     *            this game.
     */
    public void setBlocks(HostingBlock[] blocks) {
        this.blocks = blocks;
    }

    /**
     * Retrieves the end date of this game.
     *
     *
     * @return The end date of this game.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of this game.
     *
     *
     * @param endDate
     *            The endDate to set.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the identifier for this game.
     *
     *
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the identifier for this game.
     *
     *
     * @param id
     *            The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the number of keys required to attempt to win this game.
     *
     *
     * @return the number of keys required to attempt to win this game.
     */
    public int getKeyCount() {
        return keyCount;
    }

    /**
     * Sets the number of keys required to attempt to win this game.
     *
     *
     * @param keyCount
     *            The keyCount to set.
     */
    public void setKeyCount(int keyCount) {
        this.keyCount = keyCount;
    }

    /**
     * Gets the name of this game.
     *
     *
     * @return the name of this game.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this game.
     *
     *
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the planned or past start date for this game.
     *
     *
     * @return The planned or past start date for this game; will not be null.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the planned or past start date for this game.
     *
     * @param startDate
     *            planned or past start date for this game.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Retrieves the ID of bounce point calculation schema. Such schema provides the rules to be used for calculating
     * the bounce points to be awarded to players playing this game.
     * </p>
     *
     * @return the reference to bounce point calculation schema.
     */
    public int getBouncePointCalculationType() {
        return this.bouncePointCalculationType;
    }

    /**
     * <p>
     * Sets the ID of bounce point calculation schema. Such schema provides the rules to be used for calculating
     * the bounce points to be awarded to players playing this game.
     * </p>
     *
     * @param bouncePointCalculationType the reference to bounce point calculation schema.
     */
    public void setBouncePointCalculationType(int bouncePointCalculationType) {
        this.bouncePointCalculationType = bouncePointCalculationType;
    }

    /**
     * <p>
     * Retrieves the ID of prize amount calculation schema. Such schema provides the rules to be used for calculating
     * the prize amounts to be awarded to players winning this game.
     * </p>
     *
     * @return the reference to prize amount calculation schema.
     */
    public int getPrizeCalculationType() {
        return this.prizeCalculationType;
    }

    /**
     * <p>
     * Sets the ID of prize amount calculation schema. Such schema provides the rules to be used for calculating
     * the prize amounts to be awarded to players winning this game.
     * </p>
     *
     * @param prizeCalculationType the reference to prize amount calculation schema.
     */
    public void setPrizeCalculationType(int prizeCalculationType) {
        this.prizeCalculationType = prizeCalculationType;
    }

    /**
     * <p>
     * Retrieves the ID of game completion schema. Such schema provides the rules to be used for determining when
     * the game has to be completed.
     * </p>
     *
     * @return the reference to game completion schema.
     */
    public int getCompletionType() {
        return this.completionType;
    }

    /**
     * <p>
     * Sets the ID of game completion schema. Such schema provides the rules to be used for determining when
     * the game has to be completed.
     * </p>
     *
     * @param completionType the reference to game completion schema.
     */
    public void setCompletionType(int completionType) {
        this.completionType = completionType;
    }
}
