/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;

import java.util.Date;


/**
 * <p>
 * The mock implement game interface.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestGame implements Game {
    /**
     * The id.
     */
    private Long id;

    /**
     * The start date.
     */
    private Date startDate;

    /**
     * The hostingBlock array
     */
    private HostingBlock[] blocks;

    /**
     * Empty constructor.
     */
    public AccuracyTestGame() {
    }

    /**
     * Get the ID.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * The id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name.
     *
     * @return the name
     */
    public String getName() {
        return "game1";
    }

    /**
     * Return ball color.
     *
     * @return null
     */
    public BallColor getBallColor() {
        return null;
    }

    /**
     * Get the key count.
     *
     * @return 0
     */
    public int getKeyCount() {
        return 0;
    }

    /**
     * Set the start date.
     *
     * @param startDate the date to be set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the start date.
     *
     * @return the current time
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Get the end date.
     *
     * @return null
     */
    public Date getEndDate() {
        return null;
    }

    /**
     * Get an array of HostingBlock.
     *
     * @return  an array of HostingBlock
     */
    public HostingBlock[] getBlocks() {
        return this.blocks;
    }

    /**
     * Set the array of HostingBlock.
     * @param blocks the hostingBlock array
     */
    public void setBlocks(HostingBlock[] blocks) {
    	this.blocks = blocks;
    }
}
