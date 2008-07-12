/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import javax.ejb.EJBException;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;
import com.topcoder.service.digitalrun.entity.TrackContestType;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;

/**
 * <p>
 * Failure tests for <code>DigitalRunContestManagerBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunContestManagerBeanTestExp extends BaseTestCase {

    /**
     * <p>
     * <code>TrackContest</code> to be created already exists, <code>EntityExistsException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContest_EntityExistsException() throws Exception {

        PointsCalculator pointsCalculator = createPointsCalculator();
        TrackStatus trackStatus = createTrackStatus();
        TrackType trackType = createTrackType();
        Track track = createTrack(pointsCalculator, trackStatus, trackType);

        persist(pointsCalculator);
        persist(trackStatus);
        persist(trackType);
        persist(track);

        TrackContestResultCalculator trackContestResultCalculator = createTrackContestResultCalculator();
        TrackContestType trackContestType = createTrackContestType();

        persist(trackContestResultCalculator);
        persist(trackContestType);

        TrackContest entity = createTrackContest(track, trackContestResultCalculator, trackContestType);

        // Create TrackContest
        TrackContest created = getDigitalRunContestManagerRemoteEJB().createTrackContest(entity);

        // Create again
        try {
            getDigitalRunContestManagerRemoteEJB().createTrackContest(created);
            fail("EntityExistsException expected");
        } catch (EntityExistsException e) {
            // pass
        }

        // Remove TrackContest
        getDigitalRunContestManagerRemoteEJB().removeTrackContest(created.getId());

        delete(trackContestResultCalculator);
        delete(trackContestType);
        delete(track);
        delete(pointsCalculator);
        delete(trackStatus);
        delete(trackType);
    }

    /**
     * <p>
     * <code>TrackContest</code> to be created contains invalid properties,
     * <code>PersistenceException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContest_PersistenceException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().createTrackContest(new TrackContest());
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * <code>TrackContest</code> to be created is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContest_IllegalArgumentException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().createTrackContest(null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * <code>TrackContest</code> to be updated is not found in persistence,
     * <code>EntityNotFoundException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateTrackContest_EntityNotFoundException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().updateTrackContest(new TrackContest());
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * <code>TrackContest</code> to be updated contains invalid properties,
     * <code>PersistenceException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateTrackContest_PersistenceException() throws Exception {

        PointsCalculator pointsCalculator = createPointsCalculator();
        TrackStatus trackStatus = createTrackStatus();
        TrackType trackType = createTrackType();
        Track track = createTrack(pointsCalculator, trackStatus, trackType);

        persist(pointsCalculator);
        persist(trackStatus);
        persist(trackType);
        persist(track);

        TrackContestResultCalculator trackContestResultCalculator = createTrackContestResultCalculator();
        TrackContestType trackContestType = createTrackContestType();

        persist(trackContestResultCalculator);
        persist(trackContestType);

        TrackContest entity = createTrackContest(track, trackContestResultCalculator, trackContestType);

        // Create TrackContest
        TrackContest created = getDigitalRunContestManagerRemoteEJB().createTrackContest(entity);

        // Make a 'fake' entity
        TrackContest fake = new TrackContest();
        fake.setId(created.getId());
        try {
            getDigitalRunContestManagerRemoteEJB().updateTrackContest(fake);
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }

        // Remove TrackContest
        getDigitalRunContestManagerRemoteEJB().removeTrackContest(created.getId());

        delete(trackContestResultCalculator);
        delete(trackContestType);
        delete(track);
        delete(pointsCalculator);
        delete(trackStatus);
        delete(trackType);
    }

    /**
     * <p>
     * <code>TrackContest</code> to be updated is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateTrackContest_IllegalArgumentException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().updateTrackContest(null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Filter is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchTrackContests_IllegalArgumentException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().searchTrackContests(null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * <code>TrackContest</code> to be removed is not found in persistence,
     * <code>EntityNotFoundException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveTrackContest_EntityNotFoundException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().removeTrackContest(Long.MAX_VALUE);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * <code>TrackContestType</code> to be created is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContestType_IllegalArgumentException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().createTrackContestType(null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * <code>TrackContestType</code> to be created already exists, <code>EntityExistsException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContestType_EntityExistsException() throws Exception {
        TrackContestType trackContestType = createTrackContestType();

        trackContestType = getDigitalRunContestManagerRemoteEJB().createTrackContestType(trackContestType);

        try {
            getDigitalRunContestManagerRemoteEJB().createTrackContestType(trackContestType);
            fail("EntityExistsException expected");
        } catch (EntityExistsException e) {
            // pass
        }

        getDigitalRunContestManagerRemoteEJB().removeTrackContestType(trackContestType.getId());
    }

    /**
     * <p>
     * <code>TrackContestType</code> to be created contains invalid properties,
     * <code>PersistenceException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContestType_PersistenceException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().createTrackContestType(new TrackContestType());
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * <code>TrackContestType</code> to be updated is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateTrackContestType_IllegalArgumentException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().updateTrackContestType(null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * <code>TrackContestType</code> to be updated is not found in persistence,
     * <code>EntityNotFoundException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateTrackContestType_EntityNotFoundException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().updateTrackContestType(new TrackContestType());
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * <code>TrackContestType</code> to be updated contains invalid properties,
     * <code>PersistenceException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateTrackContestType_PersistenceException() throws Exception {
        TrackContestType trackContestType = createTrackContestType();

        trackContestType = getDigitalRunContestManagerRemoteEJB().createTrackContestType(trackContestType);

        // Make a 'fake' entity
        TrackContestType fake = new TrackContestType();
        fake.setId(trackContestType.getId());
        try {
            getDigitalRunContestManagerRemoteEJB().updateTrackContestType(fake);
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }

        getDigitalRunContestManagerRemoteEJB().removeTrackContestType(trackContestType.getId());
    }

    /**
     * <p>
     * <code>TrackContestType</code> to be removed is not found in persistence,
     * <code>EntityNotFoundException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveTrackContestType_EntityNotFoundException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().removeTrackContestType(Long.MAX_VALUE);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * <code>TrackContestResultCalculator</code> to be created is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContestResultCalculator_IllegalArgumentException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().createTrackContestResultCalculator(null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * <code>TrackContestResultCalculator</code> to be created already exists,
     * <code>EntityExistsException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContestResultCalculator_EntityExistsException() throws Exception {
        TrackContestResultCalculator trackContestType = createTrackContestResultCalculator();

        trackContestType = getDigitalRunContestManagerRemoteEJB().createTrackContestResultCalculator(trackContestType);

        try {
            getDigitalRunContestManagerRemoteEJB().createTrackContestResultCalculator(trackContestType);
            fail("EntityExistsException expected");
        } catch (EntityExistsException e) {
            // pass
        }

        getDigitalRunContestManagerRemoteEJB().removeTrackContestResultCalculator(trackContestType.getId());
    }

    /**
     * <p>
     * <code>TrackContestResultCalculator</code> to be created contains invalid properties,
     * <code>PersistenceException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContestResultCalculator_PersistenceException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().createTrackContestResultCalculator(
                new TrackContestResultCalculator());
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * <code>TrackContestResultCalculator</code> to be updated is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateTrackContestResultCalculator_IllegalArgumentException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().updateTrackContestResultCalculator(null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * <code>TrackContestResultCalculator</code> to be updated is not found in persistence,
     * <code>EntityNotFoundException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateTrackContestResultCalculator_EntityNotFoundException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().updateTrackContestResultCalculator(
                new TrackContestResultCalculator());
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * <code>TrackContestResultCalculator</code> to be updated contains invalid properties,
     * <code>PersistenceException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateTrackContestResultCalculator_PersistenceException() throws Exception {
        TrackContestResultCalculator trackContestType = createTrackContestResultCalculator();

        trackContestType = getDigitalRunContestManagerRemoteEJB().createTrackContestResultCalculator(trackContestType);

        // Make a 'fake' entity
        TrackContestResultCalculator fake = new TrackContestResultCalculator();
        fake.setId(trackContestType.getId());
        try {
            getDigitalRunContestManagerRemoteEJB().updateTrackContestResultCalculator(fake);
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }

        getDigitalRunContestManagerRemoteEJB().removeTrackContestResultCalculator(trackContestType.getId());
    }

    /**
     * <p>
     * <code>TrackContestResultCalculator</code> to be removed is not found in persistence,
     * <code>EntityNotFoundException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveTrackContestResultCalculator_EntityNotFoundException() throws Exception {
        try {
            getDigitalRunContestManagerRemoteEJB().removeTrackContestResultCalculator(Long.MAX_VALUE);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }
}
