/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.failuretests.dao.implementations;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackStatusDAO;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import org.easymock.EasyMock;

/**
 * Some tests for JPADigitalRunTrackStatusDAO class.
 *
 * @author Orange_Cloud
 * @version 1.0
 */
public class JPADigitalRunTrackStatusDAOTest extends BaseTest {
    /**
     * Instance to test.
     */
    private JPADigitalRunTrackStatusDAO target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunTrackStatusDAOTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        target = new JPADigitalRunTrackStatusDAO();
        target.setUnitName("persistence_unit");
        target.setSessionContext(context);
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests createTrackStatus method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackStatusForNull() throws Exception {
        try {
            target.createTrackStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateTrackStatus method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateTrackStatusForNull() throws Exception {
        try {
            target.updateTrackStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateTrackStatus method when entity not found. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateTrackStatusForNoEntity() throws Exception {
        TrackStatus entity = new TrackStatus();
        entity.setId(3);
        EasyMock.expect(em.find(entity.getClass(), 3l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.updateTrackStatus(entity);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests removeTrackStatus method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackStatusForNegative() throws Exception {
        try {
            target.removeTrackStatus(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests removeTrackStatus method when entity not found. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackStatusForNoEntity() throws Exception {
        EasyMock.expect(em.find(TrackStatus.class, 3l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.removeTrackStatus(3);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests getTrackStatus method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetTrackStatusForNegative() throws Exception {
        try {
            target.getTrackStatus(-10);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getTrackStatus method when entity not found. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetTrackStatusForNoEntity() throws Exception {
        EasyMock.expect(em.find(TrackStatus.class, 3l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.getTrackStatus(3);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }
}