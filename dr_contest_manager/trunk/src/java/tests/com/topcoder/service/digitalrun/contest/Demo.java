/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import java.util.List;

import javax.naming.InitialContext;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;
import com.topcoder.service.digitalrun.entity.TrackContestType;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;


/**
 * <p>
 * Demo the usage of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends BaseTestCase {

    /**
     * <p>
     * <code>Track</code> used in the tests.
     * </p>
     */
    private Track track;

    /**
     * <p>
     * <code>PointsCalculator</code> used in the tests.
     * </p>
     */
    private PointsCalculator pointsCalculator;

    /**
     * <p>
     * <code>TrackStatus</code> used in the tests.
     * </p>
     */
    private TrackStatus trackStatus;

    /**
     * <p>
     * <code>TrackType</code> used in the tests.
     * </p>
     */
    private TrackType trackType;

    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {

        pointsCalculator = createPointsCalculator();
        trackStatus = createTrackStatus();
        trackType = createTrackType();
        track = createTrack(pointsCalculator, trackStatus, trackType);

        persist(pointsCalculator);
        persist(trackStatus);
        persist(trackType);
        persist(track);
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        delete(track);
        delete(pointsCalculator);
        delete(trackStatus);
        delete(trackType);
    }

    /**
     * <p>
     * Demo the usage of this component.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDemo() throws Exception {
        // lookup the bean from JNDI
        DigitalRunContestManagerRemote manager = (DigitalRunContestManagerRemote)
            new InitialContext().lookup(EAR_NAME + "/DigitalRunContestManagerBean/remote");

        // create a new contest type
        TrackContestType entity = new TrackContestType();
        entity.setDescription("description");
        TrackContestType contestType = manager.createTrackContestType(entity);
        // get contest type
        contestType = manager.getTrackContestType(contestType.getId());
        // update contest type
        manager.updateTrackContestType(contestType);

        // get all contest types
        List < TrackContestType > contestTypes = manager.getAllTrackContestTypes();

        // create a new calculator
        TrackContestResultCalculator rc = new TrackContestResultCalculator();
        rc.setClassName("className");
        rc.setDescription("description");
        TrackContestResultCalculator calculator = manager.createTrackContestResultCalculator(rc);
        // get calculator
        calculator = manager.getTrackContestResultCalculator(calculator.getId());
        // update calculator
        manager.updateTrackContestResultCalculator(calculator);

        // get all calculators
        List < TrackContestResultCalculator > calculators = manager.getAllTrackContestResultCalculators();

        // create a new contest
        TrackContest contest = new TrackContest();
        contest.setDescription("description");
        contest.setTrack(track);
        contest.setTrackContestResultCalculator(calculator);
        contest.setTrackContestType(contestType);
        contest = manager.createTrackContest(contest);
        // get contest
        contest = manager.getTrackContest(contest.getId());
        // update contest
        manager.updateTrackContest(contest);

        // delete contest
        manager.removeTrackContest(contest.getId());

        // delete contest type
        manager.removeTrackContestType(contestType.getId());

        // delete calculator
        manager.removeTrackContestResultCalculator(calculator.getId());
    }
}
