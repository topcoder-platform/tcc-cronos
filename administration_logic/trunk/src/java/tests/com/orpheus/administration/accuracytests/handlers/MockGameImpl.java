/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import java.util.Date;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;

/**
 * Dummy class.
 *
 * @author KKD
 * @version 1.0
 */
public class MockGameImpl implements Game {
    /**
     * The id.
     */
    private Long id;

    /**
     * The hosingBlock array.
     */
    private HostingBlock[] blocks;

    /**
     * Dummy
     * @param id the id
     */
    public MockGameImpl(long id) {
        this.id = new Long(id);    
    }

    /**
     * Dummy
     */
    public Long getId() {
        return id;
    }

    /**
     * Dummy
     */
    public String getName() {
        return null;
    }

    /**
     * Dummy
     */
    public BallColor getBallColor() {
        return null;
    }

    /**
     * Dummy
     */
    public int getKeyCount() {
        return 0;
    }

    /**
     * Dummy
     */
    public Date getStartDate() {
        return null;
    }

    /**
     * Dummy
     */
    public Date getEndDate() {
        return null;
    }

    /**
     * Dummy
     */
    public HostingBlock[] getBlocks() {
        return blocks;
    }

    /**
     * Dummy
     * @param blocks the blocks.
     */
    public void setBlocks(HostingBlock[] blocks) {
        this.blocks = blocks;
    }
}
