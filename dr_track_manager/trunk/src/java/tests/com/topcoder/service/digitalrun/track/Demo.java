/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.track.dao.implementations.BaseTestCase;

import junit.extensions.TestSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;


/**
 * Demo shows how to use the component.
 * @author waits
 * @version 1.0
 */
public class Demo extends BaseTestCase {
    /** DigitalRunTrackManagerRemote to test against. */
    private static DigitalRunTrackManagerRemote remote = null;

    /**
     * <p>
     * Return All the EJB test suite.
     * </p>
     *
     * @return all the EJB test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(Demo.class);

        /**
         * <p>
         * Setup the unit test.
         * </p>
         */
        TestSetup wrapper = new TestSetup(suite) {
                /**
                 * Create instance.
                 *
                 * @throws Exception into JUnit
                 */
                @SuppressWarnings("unchecked")
                protected void setUp() throws Exception {
                    remote = (DigitalRunTrackManagerRemote) new InitialContext().lookup(EAR_NAME +
                            "/DigitalRunTrackManagerBean/remote");
                }

                /**
                 * <p>
                 * Tear down the test.
                 * </p>
                 */
                @Override
                protected void tearDown() throws Exception {
                    //to remove all
                }
            };

        return wrapper;
    }

    /**
     * <p>
     * Show how to use the api of the DigitalRunTrackManager to manage the track.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testHowToManageTrack() throws Exception {
        //create project type
        ProjectType projectType = this.createProjectType(10L);
        persist(projectType);

        //create pointsCalculator
        PointsCalculator pointsCalculator = createPointsCalculator(null);
        persist(pointsCalculator);

        //create trackStatus
        TrackStatus trackStatus = createTrackStatus();
        persist(trackStatus);

        //create trackType
        TrackType trackType = createTrackType();
        persist(trackType);

        //create track
        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        //create Track entity into persistence
        track = remote.createTrack(track);

        //the track entity has now an id; the start date is current date
        //modify start date to tomorrow date
        track.setStartDate(new Date(new Date().getTime() + (24 * 60 * 60 * 1000)));
        remote.updateTrack(track);

        //get a Track entity by specifying an id
        long id = track.getId();
        //the Track created above was returned
        remote.getTrack(id);

        //search for Track entities using a filter that returns entities //with start date > current date
        DigitalRunTrackFilterFactory.createTrackStartDateGreaterOrEqualFilter(new Date());

        //get active tracks
        List < Track > tracks2 = remote.getActiveTracks();

        //remove the created Track entity
        remote.removeTrack(id);
        delete(pointsCalculator);
        delete(trackStatus);
        delete(trackType);
        delete(projectType);
    }

    /**
     * <p>
     * Show how to use the api of the DigitalRunTrackManager to manage the TrackType.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testHowToManageTrackType() throws Exception {
        //create type
        TrackType type = createTrackType();

        //create track type
        type = remote.createTrackType(type);

        type.setCreationDate(new Date());
        type.setModificationDate(new Date());
        //update track type
        remote.updateTrackType(type);

        //get track type
        TrackType type1 = remote.getTrackType(type.getId());

        //get all track types
        List < TrackType > types = remote.getAllTrackTypes();

        //delete track type
        remote.removeTrackType(type.getId());
    }

    /**
     * <p>
     * Show how to use the api of the DigitalRunTrackManager to manage the TrackStatus.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testHowToManageTrackStatus() throws Exception {
        TrackStatus status = createTrackStatus();
        //create track status
        status = remote.createTrackStatus(status);
        status.setCreationDate(new Date());
        status.setModificationDate(new Date());
        //update status
        remote.updateTrackStatus(status);

        //get track status
        TrackStatus status1 = remote.getTrackStatus(status.getId());

        //get all track statuses
        List < TrackStatus > statuses = remote.getAllTrackStatuses();

        //delete track status
        remote.removeTrackStatus(status.getId());
    }

    /**
     * <p>
     * Show how to use the api of the DigitalRunTrackManager to manage the PointsCalculator.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testHowToManagePointsCalculator() throws Exception {
        PointsCalculator pc = createPointsCalculator(null);
        //create points calculator
        pc = remote.createPointsCalculator(pc);

        pc.setCreationDate(new Date());
        pc.setModificationDate(new Date());
        //update points calculator
        remote.updatePointsCalculator(pc);

        //get points calculator
        PointsCalculator pc1 = remote.getPointsCalculator(pc.getId());

        //get all points calculators
        List < PointsCalculator > pc2 = remote.getAllPointsCalculators();

        //delete points calculator
        remote.removePointsCalculator(pc.getId());
    }

    /**
     * <p>
     * Show how to use the api of the DigitalRunTrackManager to manage the ProjectTyp.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testHowToManageProjectType() throws Exception {
        ProjectType pt = createProjectType(10L);
        persist(pt);
        //get project type
        pt = remote.getProjectType(10L);

        //get all project types
        List < ProjectType > pt1 = remote.getAllProjectTypes();

        delete(getEntityManager().find(ProjectType.class, pt.getId()));
    }
}
