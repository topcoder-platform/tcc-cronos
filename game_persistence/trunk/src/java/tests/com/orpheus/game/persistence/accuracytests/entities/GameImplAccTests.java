/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests.entities;

import com.orpheus.game.persistence.entities.BallColorImpl;
import com.orpheus.game.persistence.entities.GameImpl;
import com.orpheus.game.persistence.accuracytests.AccuracyHelper;
import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.dao.SQLServerGameDataDAO;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>GameImpl</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class GameImplAccTests extends TestCase {
    /**
     * The namespace to initialize SQLServerGameDataDao.
     */
    public static final String DAO_NAMESPACE = "com.orpheus.game.persistence.SQLServerGameDataDAO";

    /**
     * The GameImpl instance to test
     */
    private GameImpl game = null;

    /**
     * The SQLServerGameDataDAO instance used in test cases.
     */
    private SQLServerGameDataDAO dao = null;

    /**
     * The color instance used in test cases.
     */
    BallColor color = null;

    /**
     * The hosting block array used in test cases.
     */
    HostingBlock[] blocks = null;

    /**
     * Setup for each test cases.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        AccuracyHelper.clearAllConfigurationNS();

        //add config files
        AccuracyHelper.loadBaseConfig();

        // create test data in the database
        AccuracyHelper.setupDatabase();

        // initialize test instance
        dao = new SQLServerGameDataDAO(DAO_NAMESPACE);

        color = new BallColorImpl(new Long(1), "color_name", 1);

        HostingBlock block = dao.getBlock(1);

        blocks = new HostingBlock[]{block};

        game = new GameImpl(new Long(1), color, 1, AccuracyHelper.convertStringToDate("2006/1/1"),
                AccuracyHelper.convertStringToDate("2006/2/2"), blocks);
    }

    /**
     * Clean up for each test case.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        AccuracyHelper.cleanupDatabase();
        AccuracyHelper.clearAllConfigurationNS();
    }

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testConstructor() throws Exception {
        assertEquals("Not the expected id.", 1, game.getId().longValue());
        assertEquals("Not the expected color id.", 1, game.getBallColor().getId().longValue());
        assertEquals("Not the expected block length.", 1, game.getBlocks().length);
    }

    /**
     * <p>
     * Accuracy test of the getId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetId() throws Exception {
        game = new GameImpl(new Long(2), color, 1, AccuracyHelper.convertStringToDate("2006/1/1"),
                AccuracyHelper.convertStringToDate("2006/2/2"), blocks);
        assertEquals("Not the expected id.", 2, game.getId().longValue());
    }

    /**
     * <p>
     * Accuracy test of the getBallColor() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetBallColor() throws Exception {
        color = new BallColorImpl(new Long(2), "color_name", 1);
        game = new GameImpl(new Long(2), color, 1, AccuracyHelper.convertStringToDate("2006/1/1"),
                AccuracyHelper.convertStringToDate("2006/2/2"), blocks);
        assertEquals("Not the expected color id.", 2, game.getBallColor().getId().longValue());
    }

    /**
     * <p>
     * Accuracy test of the getStartDate() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetStartDate() throws Exception {
        assertEquals("Not the expected start date.", AccuracyHelper.convertStringToDate("2006/1/1"),
                game.getStartDate());
    }

    /**
     * <p>
     * Accuracy test of the getEndDate() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetEndDate() throws Exception {
        assertEquals("Not the expected start date.", AccuracyHelper.convertStringToDate("2006/2/2"),
                game.getEndDate());
    }
}
