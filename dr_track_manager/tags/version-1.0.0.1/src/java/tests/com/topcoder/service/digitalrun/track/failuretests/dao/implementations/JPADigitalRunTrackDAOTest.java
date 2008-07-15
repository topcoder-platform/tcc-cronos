/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.failuretests.dao.implementations;

import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackDAO;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.easymock.EasyMock;

/**
 * Some tests for JPADigitalRunTrackDAO class.
 *
 * @author Orange_Cloud
 * @version 1.0
 */
public class JPADigitalRunTrackDAOTest extends BaseTest {
    /**
     * Instance to test.
     */
    private JPADigitalRunTrackDAO target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunTrackDAOTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        target = new JPADigitalRunTrackDAO();
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
     * Tests createTrack method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackForNull() throws Exception {
        try {
            target.createTrack(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateTrack method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateTrackForNull() throws Exception {
        try {
            target.updateTrack(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateTrack method when entity not presented. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateTrackForNoEntity() throws Exception {
        Track entity = new Track();
        entity.setId(3);
        EasyMock.expect(em.find(entity.getClass(), 3l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.updateTrack(entity);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests removeTrack method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackForNegative() throws Exception {
        try {
            target.removeTrack(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests removeTrack method when entity not presented. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackForNoEntity() throws Exception {
        Track entity = new Track();
        entity.setId(3);
        EasyMock.expect(em.find(entity.getClass(), 3l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.removeTrack(3);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests getTrack method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetTrackForNegative() throws Exception {
        try {
            target.getTrack(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getTrack method when entity not presented. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetTrackForNoEntity() throws Exception {
        Track entity = new Track();
        entity.setId(3);
        EasyMock.expect(em.find(entity.getClass(), 3l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.getTrack(3);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests searchTracks method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSearchTracksForNull() throws Exception {
        try {
            target.searchTracks(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests addTrackProjectType method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testAddTrackProjectTypeForNull() throws Exception {
        try {
            target.addTrackProjectType(null, new ProjectType());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests addTrackProjectType method when track does not exist. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testAddTrackProjectTypeForNoTrack() throws Exception {
        Track entity = new Track();
        entity.setId(3);
        EasyMock.expect(em.find(entity.getClass(), 3l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.addTrackProjectType(entity, new ProjectType());
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests removeTrackProjectType method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackProjectTypeForNull() throws Exception {
        try {
            target.removeTrackProjectType(new Track(), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests removeTrackProjectType method when type does not exist. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackProjectTypeForNoTrack() throws Exception {
        Track entity = new Track();
        entity.setId(3);
        EasyMock.expect(em.find(entity.getClass(), 3l)).andReturn(entity);

        ProjectType type = new ProjectType();
        type.setId(4);
        EasyMock.expect(em.find(type.getClass(), 4l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.addTrackProjectType(entity, type);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }
}