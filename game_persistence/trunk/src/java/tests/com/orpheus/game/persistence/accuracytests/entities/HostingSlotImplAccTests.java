/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests.entities;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.accuracytests.AccuracyHelper;
import com.orpheus.game.persistence.dao.SQLServerGameDataDAO;
import com.orpheus.game.persistence.entities.HostingSlotImpl;
import com.orpheus.game.persistence.entities.DomainTargetImpl;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>HostingSlotImpl</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class HostingSlotImplAccTests extends TestCase {
    /**
     * The namespace to initialize SQLServerHostingSlotDataDao.
     */
    public static final String DAO_NAMESPACE = "com.orpheus.game.persistence.SQLServerGameDataDAO";

    /**
     * The HostingSlotImpl instance to test
     */
    private HostingSlotImpl slot = null;

    /**
     * The SQLServeGameDataDAO instance used in test cases.
     */
    private SQLServerGameDataDAO dao = null;

    /**
     * The color instance used in test cases.
     */
    BallColor color = null;

    /**
     * The domain instance used in test cases.
     */
    Domain domain = null;

    /**
     * The DomainTargetImpl instance used in test cases.
     */
    private DomainTargetImpl target = null;

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

        domain = dao.getDomain(1);

        target = new DomainTargetImpl(new Long(1), 1, "uri", "idText", "idHash", 1);

        slot = new HostingSlotImpl(new Long(1), domain, 1, new long[]{1}, new Long(1), 1,
                new DomainTarget[]{target}, 1, AccuracyHelper.convertStringToDate("2006/1/1"),
                AccuracyHelper.convertStringToDate("2006/2/2"));
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
        assertEquals("Not the expected id.", 1, slot.getId().longValue());
        assertEquals("Not the expected sequence number.", 1, slot.getSequenceNumber());
        assertSame("Not the expected domain.", domain, slot.getDomain());
        assertEquals("Not the expected image id.", 1, slot.getImageId());
        assertEquals("Not the expected puzzle id.", 1, slot.getPuzzleId().longValue());
        assertEquals("Not the expected brain teaser id length.", 1, slot.getBrainTeaserIds().length);
    }

    /**
     * <p>
     * Accuracy test of the getId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetId() throws Exception {
        slot = new HostingSlotImpl(new Long(2), domain, 2, new long[]{2}, new Long(2), 2,
                new DomainTarget[]{target}, 2, AccuracyHelper.convertStringToDate("2006/1/1"),
                AccuracyHelper.convertStringToDate("2006/2/2"));
        assertEquals("Not the expected id.", 2, slot.getId().longValue());
    }

    /**
     * <p>
     * Accuracy test of the getSequenceNumber() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetSequenceNumber() throws Exception {
        slot = new HostingSlotImpl(new Long(2), domain, 2, new long[]{2}, new Long(2), 2,
                new DomainTarget[]{target}, 2, AccuracyHelper.convertStringToDate("2006/1/1"),
                AccuracyHelper.convertStringToDate("2006/2/2"));
        assertEquals("Not the expected sequence number.", 2, slot.getSequenceNumber());
    }

    /**
     * <p>
     * Accuracy test of the getImageId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetImageId() throws Exception {
        slot = new HostingSlotImpl(new Long(2), domain, 2, new long[]{2}, new Long(2), 2,
                new DomainTarget[]{target}, 2, AccuracyHelper.convertStringToDate("2006/1/1"),
                AccuracyHelper.convertStringToDate("2006/2/2"));
        assertEquals("Not the expected image id.", 2, slot.getImageId());
    }

    /**
     * <p>
     * Accuracy test of the getPuzzleId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPuzzleId() throws Exception {
        slot = new HostingSlotImpl(new Long(2), domain, 2, new long[]{2}, new Long(2), 2,
                new DomainTarget[]{target}, 2, AccuracyHelper.convertStringToDate("2006/1/1"),
                AccuracyHelper.convertStringToDate("2006/2/2"));
        assertEquals("Not the expected puzzle id.", 2, slot.getPuzzleId().longValue());
    }

    /**
     * <p>
     * Accuracy test of the getHostingStart() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetHostingStart() throws Exception {
        assertEquals("Not the expected hosting start date.", AccuracyHelper.convertStringToDate("2006/1/1"),
                slot.getHostingStart());
    }

    /**
     * <p>
     * Accuracy test of the getHostingEnd() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetHostingEnd() throws Exception {
        assertEquals("Not the expected hosting start date.", AccuracyHelper.convertStringToDate("2006/2/2"),
                slot.getHostingEnd());
    }
}
