/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.entities;

import java.util.Date;

import junit.framework.TestCase;

import com.orpheus.administration.entities.GameImpl;
import com.orpheus.administration.entities.HostingBlockImpl;
import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.HostingBlock;

/**
 * <p>
 * Test the <code>GameImpl</code> class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class GameImplAccuracyTests extends TestCase {
    /**
     * will hold the identifier for this game.
     *
     */
    private final Long id = new Long(12321);

    /**
     * will hold the name of this game.
     *
     */
    private final String name = "name";

    /**
     * will hold the BallColor object assigned to this game.
     *
     */
    private final BallColor ballColor = new BallColor() {

        public Long getId() {
            return null;
        }

        public long getImageId() {
            return 0;
        }

        public String getName() {
            return null;
        }
    };

    /**
     * will hold the number of keys required to attempt to win this game.
     *
     */
    private final int keyCount = 23452;

    /**
     * will hold the planned or past start date for this game.
     *
     */
    private final Date startDate = new Date(
            System.currentTimeMillis() - 1000000);

    /**
     * will hold the end date of this game, if it has already ended.
     *
     */
    private final Date endDate = new Date(System.currentTimeMillis() - 2000000);;

    /**
     * will hold an array of HostingBlock objects representing the hosting blocks within this game.
     *
     */
    private final HostingBlock[] blocks = new HostingBlock[] {
        new HostingBlockImpl(), new HostingBlockImpl()};

    /**
     * GameImpl instance to test.
     */
    private GameImpl target = null;

    /**
     * <p>
     * setUp() routine. Creates test GameImpl instance.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new GameImpl();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>GameImpl()</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to get GameImpl instance.", target);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getBallColor()</code> for
     * proper behavior. Verify that BallColor got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetBallColorAccuracy() throws Exception {
        target.setBallColor(ballColor);
        assertEquals("BallColor got incorrectly.", ballColor, target
                .getBallColor());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setBallColor(BallColor)</code> for
     * proper behavior. Verify that BallColor set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetBallColorAccuracy() throws Exception {
        target.setBallColor(ballColor);
        assertEquals("BallColor set incorrectly.", ballColor, target
                .getBallColor());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getBlocks()</code> for
     * proper behavior. Verify that Blocks got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetBlocksAccuracy() throws Exception {
        target.setBlocks(blocks);
        assertEquals("Blocks got incorrectly.", blocks, target.getBlocks());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setBlocks(HostingBlock[])</code> for
     * proper behavior. Verify that Blocks set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetBlocksAccuracy() throws Exception {
        target.setBlocks(blocks);
        assertEquals("Blocks set incorrectly.", blocks, target.getBlocks());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getEndDate()</code> for
     * proper behavior. Verify that EndDate got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetEndDateAccuracy() throws Exception {
        target.setEndDate(endDate);
        assertEquals("EndDate got incorrectly.", endDate, target.getEndDate());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setEndDate(Date)</code> for
     * proper behavior. Verify that EndDate set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetEndDateAccuracy() throws Exception {
        target.setEndDate(endDate);
        assertEquals("EndDate set incorrectly.", endDate, target.getEndDate());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getId()</code> for
     * proper behavior. Verify that id got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetIdAccuracy() throws Exception {
        target.setId(id);
        assertEquals("id got incorrectly.", id, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setId(Long)</code> for
     * proper behavior. Verify that id set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetIdAccuracy() throws Exception {
        target.setId(id);
        assertEquals("id set incorrectly.", id, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getKeyCount()</code> for
     * proper behavior. Verify that KeyCount got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetKeyCountAccuracy() throws Exception {
        target.setKeyCount(keyCount);
        assertEquals("KeyCount got incorrectly.", keyCount, target
                .getKeyCount());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setKeyCount(int)</code> for
     * proper behavior. Verify that KeyCount set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetKeyCountAccuracy() throws Exception {
        target.setKeyCount(keyCount);
        assertEquals("KeyCount set incorrectly.", keyCount, target
                .getKeyCount());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getName()</code> for
     * proper behavior. Verify that Name got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetNameAccuracy() throws Exception {
        target.setName(name);
        assertEquals("Name set incorrectly.", name, target.getName());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setName(Name)</code> for
     * proper behavior. Verify that Name set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetNameAccuracy() throws Exception {
        target.setName(name);
        assertEquals("Name set incorrectly.", name, target.getName());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getStartDate()</code> for
     * proper behavior. Verify that StartDate got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetStartDateAccuracy() throws Exception {
        target.setStartDate(startDate);
        assertEquals("StartDate got incorrectly.", startDate, target
                .getStartDate());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setStartDate(Date)</code> for
     * proper behavior. Verify that StartDate set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetStartDateAccuracy() throws Exception {
        target.setStartDate(startDate);
        assertEquals("StartDate set incorrectly.", startDate, target
                .getStartDate());
    }
}
