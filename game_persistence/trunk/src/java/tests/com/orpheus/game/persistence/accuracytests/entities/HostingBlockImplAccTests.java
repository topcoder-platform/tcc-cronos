/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests.entities;

import com.orpheus.game.persistence.entities.BallColorImpl;
import com.orpheus.game.persistence.entities.GameImpl;
import com.orpheus.game.persistence.entities.HostingBlockImpl;
import com.orpheus.game.persistence.accuracytests.AccuracyHelper;
import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.dao.SQLServerGameDataDAO;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>HostingBlockImpl</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class HostingBlockImplAccTests extends TestCase {
    /**
     * The namespace to initialize SQLServerHostingBlockDataDao.
     */
    public static final String DAO_NAMESPACE = "com.orpheus.game.persistence.SQLServerGameDataDAO";

    /**
     * The HostingBlockImpl instance to test
     */
    private HostingBlockImpl block = null;

    /**
     * The SQLServeGameDataDAO instance used in test cases.
     */
    private SQLServerGameDataDAO dao = null;

    /**
     * The color instance used in test cases.
     */
    BallColor color = null;

    /**
     * The hosting block array used in test cases.
     */
    HostingSlot[] slots = null;

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

        HostingSlot slot = dao.getSlot(1);

        slots = new HostingSlot[]{slot};

        block = new HostingBlockImpl(new Long(1), 1, slots, 1);
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
        assertEquals("Not the expected id.", 1, block.getId().longValue());
        assertEquals("Not the expected sequence number.", 1, block.getSequenceNumber());
        assertEquals("Not the expected slot length.", 1, block.getSlots().length);
        assertEquals("Not the expected max hosting time per slot.", 1, block.getMaxHostingTimePerSlot());
    }

    /**
     * <p>
     * Accuracy test of the getId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetId() throws Exception {
        block = new HostingBlockImpl(new Long(2), 2, slots, 2);
        assertEquals("Not the expected id.", 2, block.getId().longValue());
    }

    /**
     * <p>
     * Accuracy test of the getSequenceNumber() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetSequenceNumber() throws Exception {
        block = new HostingBlockImpl(new Long(2), 2, slots, 2);
        assertEquals("Not the expected sequence number.", 2, block.getSequenceNumber());
    }

    /**
     * <p>
     * Accuracy test of the getSlots() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetSlots() throws Exception {
        block = new HostingBlockImpl(new Long(2), 2, slots, 2);
        assertEquals("Not the expected slot id.", 1, block.getSlots()[0].getId().longValue());
    }

    /**
     * <p>
     * Accuracy test of the getMaxHostingTimePerSlot() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetMaxHostingTimePerSlot() throws Exception {
        block = new HostingBlockImpl(new Long(2), 2, slots, 2);
        assertEquals("Not the expected max hosting time per slot.", 2, block.getMaxHostingTimePerSlot());
    }
}
