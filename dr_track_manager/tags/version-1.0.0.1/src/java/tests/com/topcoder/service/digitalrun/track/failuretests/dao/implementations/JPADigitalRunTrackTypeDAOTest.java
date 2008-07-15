/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.failuretests.dao.implementations;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackTypeDAO;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackType;
import org.easymock.EasyMock;

/**
 * Some tests for JPADigitalRunTrackTypeDAO class.
 *
 * @author Orange_Cloud
 * @version 1.0
 */
public class JPADigitalRunTrackTypeDAOTest extends BaseTest {
    /**
     * Instance to test.
     */
    private JPADigitalRunTrackTypeDAO target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunTrackTypeDAOTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        target = new JPADigitalRunTrackTypeDAO();
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
     * Tests createTrackType method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackTypeForNull() throws Exception {
        try {
            target.createTrackType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateTrackType method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateTrackTypeForNull() throws Exception {
        try {
            target.updateTrackType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateTrackType method when entity does not exist. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateTrackTypeForNoEntity() throws Exception {
        TrackType entity = new TrackType();
        entity.setId(3);
        EasyMock.expect(em.find(entity.getClass(), 3l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.updateTrackType(entity);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests removeTrackType method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackTypeForNegative() throws Exception {
        try {
            target.removeTrackType(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests removeTrackType method when entity does not exist. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackTypeForNoEntity() throws Exception {
        EasyMock.expect(em.find(TrackType.class, 3l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.removeTrackType(3);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests getTrackType method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetTrackTypeForNegative() throws Exception {
        try {
            target.getTrackType(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getTrackType method when entity does not exist. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetTrackTypeForNoEntity() throws Exception {
        EasyMock.expect(em.find(TrackType.class, 30l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.getTrackType(30);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }
}