/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests.mock;

import java.util.Date;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;

/**
 * A mock implementation of Game.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockGame implements Game {
    /**
     * Represents the id.
     */
    private Long id;

    /**
     * Creates the mock game.
     * 
     * @param id
     *            game id
     */
    MockGame(Long id) {
        this.id = id;
    }

    /**
     * Returns the id.
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * No implementation.
     * 
     * @return always null
     */
    public String getName() {
        return null;
    }

    /**
     * No implementation.
     * 
     * @return always null
     */
    public BallColor getBallColor() {
        return null;
    }

    /**
     * No implementation.
     * 
     * @return always 0
     */
    public int getKeyCount() {
        return 0;
    }

    /**
     * Returns the current date.
     * 
     * @return the current date
     */
    public Date getStartDate() {
        return new Date();
    }

    /**
     * No implementation.
     * 
     * @return always null
     */
    public Date getEndDate() {
        return id.longValue() % 2 == 0 ? new Date() : null;
    }

    /**
     * Gets a pre-defined Hosting Block array.
     * 
     * @return Hosting Block.
     */
    public HostingBlock[] getBlocks() {
        if (id.longValue() <= 1) {
            return new HostingBlock[0];
        }
        HostingBlock[] blocks = new HostingBlock[3];
        blocks[0] = new MockHostingBlock(1);
        blocks[1] = new MockHostingBlock(2);
        blocks[2] = new MockHostingBlock(3);
        return blocks;
    }
}