/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity.failuretests;


import java.util.ArrayList;
import java.util.List;

import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackContest;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the failure test cases for Track class.
 * </p>
 *
 * @author akhil_bansal
 * @version 1.0
 */
public class TestTrack extends TestCase {

    /**
     * <p>
     * Represents the instance of Track to be used.
     * </p>
     */
    private Track track = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *         throws exception if any, raise it to JUnit.
     */
    @Override
    public void setUp() throws Exception {
        this.track = new Track();
    }

    /**
     * <p>
     * This method tests the setTrackType() method to throw IllegalArgumentException
     * for null trackType.
     * </p>
     */
    public void testSetTrackTypeNull() {
        try {
            this.track.setTrackType(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setTrackStatus() method to throw IllegalArgumentException
     * for null trackStatus.
     * </p>
     */
    public void testSetTrackStatusNull() {
        try {
            this.track.setTrackStatus(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setStartDate() method to throw IllegalArgumentException
     * for null start date.
     * </p>
     */
    public void testSetStartDateNull() {
        try {
            this.track.setStartDate(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setEndDate() method to throw IllegalArgumentException
     * for null end date.
     * </p>
     */
    public void testSetEndDateNull() {
        try {
            this.track.setEndDate(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setProjectTypes() method to throw IllegalArgumentException
     * for null project types.
     * </p>
     */
    public void testSetProjectTypesNull() {
        try {
            this.track.setProjectTypes(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setProjectTypes() method to throw IllegalArgumentException
     * for collection with null elements.
     * </p>
     */
    public void testSetProjectTypesNullElements() {
        try {
            List<ProjectType> projectTypes = new ArrayList<ProjectType>();
            projectTypes.add(null);
            this.track.setProjectTypes(projectTypes);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setPointsCalculator() method to throw IllegalArgumentException
     * for null points calculator.
     * </p>
     */
    public void testSetPointsCalculatorNull() {
        try {
            this.track.setPointsCalculator(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setDigitalRunPoints() method to throw IllegalArgumentException
     * for null digital run points.
     * </p>
     */
    public void testSetDigitalRunPointsNull() {
        try {
            this.track.setDigitalRunPoints(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setDigitalRunPoints() method to throw IllegalArgumentException
     * for collection with null elements.
     * </p>
     */
    public void testSetDigitalRunPointsNullElements() {
        try {
            List<DigitalRunPoints> digitalRunPOints = new ArrayList<DigitalRunPoints>();
            digitalRunPOints.add(null);
            this.track.setDigitalRunPoints(digitalRunPOints);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setTrackContests() method to throw IllegalArgumentException
     * for null track contests.
     * </p>
     */
    public void testSetTrackContestsNull() {
        try {
            this.track.setTrackContests(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setTrackContests() method to throw IllegalArgumentException
     * for collection with null elements.
     * </p>
     */
    public void testSetTrackContestsNullElements() {
        try {
            List<TrackContest> trackContests = new ArrayList<TrackContest>();
            trackContests.add(null);
            this.track.setTrackContests(trackContests);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

}
