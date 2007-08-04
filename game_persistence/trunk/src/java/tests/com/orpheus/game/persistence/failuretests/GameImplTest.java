/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.entities.GameImpl;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Test case for <code>GameImplTest</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class GameImplTest extends TestCase {

    /**
     * Test method for GameImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.BallColor,
     * int,
     * java.util.Date,
     * java.util.Date,
     * com.orpheus.game.persistence.HostingBlock[]).
     *
     * In this case, the id is zero.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testGameImpl_ZeroId() {
        try {
            new GameImpl(new Long(0), null, 1, new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis()), new HostingBlock[0], 1, 2, 3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.BallColor,
     * int,
     * java.util.Date,
     * java.util.Date,
     * com.orpheus.game.persistence.HostingBlock[]).
     *
     * In this case, the id is negative.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testGameImpl_NegativeId() {
        try {
            new GameImpl(new Long(-1), null, 1, new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis()), new HostingBlock[0], 1, 2, 3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.BallColor,
     * int,
     * java.util.Date,
     * java.util.Date,
     * com.orpheus.game.persistence.HostingBlock[]).
     *
     * In this case, the key count is negative.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testGameImpl_NegativeKeyCount() {
        try {
            new GameImpl(new Long(1), null, -1, new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis()), new HostingBlock[0], 1, 2, 3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.BallColor,
     * int,
     * java.util.Date,
     * java.util.Date,
     * com.orpheus.game.persistence.HostingBlock[]).
     *
     * In this case, the start date is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testGameImpl_NullStartDate() {
        try {
            new GameImpl(new Long(1), null, 1, null, //new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis()), new HostingBlock[0], 1, 2, 3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.BallColor,
     * int,
     * java.util.Date,
     * java.util.Date,
     * com.orpheus.game.persistence.HostingBlock[]).
     *
     * In this case, the end date is earlier than the start date
     * Expected : {@link IllegalArgumentException}.
     */
    public void testGameImpl_InvalidStartDate() {
        try {
            new GameImpl(new Long(1), null, 1, new Date(100),
                    new Date(10), new HostingBlock[0], 1, 2, 3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.BallColor,
     * int,
     * java.util.Date,
     * java.util.Date,
     * com.orpheus.game.persistence.HostingBlock[]).
     *
     * In this case, the blocks is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testGameImpl_NullBlocks() {
        try {
            new GameImpl(new Long(1), null, 1, new Date(10),
                    new Date(100), null, 1, 2, 3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.BallColor,
     * int,
     * java.util.Date,
     * java.util.Date,
     * com.orpheus.game.persistence.HostingBlock[]).
     *
     * In this case, the blocks contains null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testGameImpl_NullInBlocks() {
        try {
            new GameImpl(new Long(1), null, 1, new Date(10),
                    new Date(100), new HostingBlock[1], 1, 2, 3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
