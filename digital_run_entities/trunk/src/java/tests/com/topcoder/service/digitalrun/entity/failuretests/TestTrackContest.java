/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity.failuretests;


import com.topcoder.service.digitalrun.entity.TrackContest;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the failure test cases for TrackContest class.
 * </p>
 *
 * @author akhil_bansal
 * @version 1.0
 */
public class TestTrackContest extends TestCase {
    /**
     * <p>
     * Represents the instance of TrackContest to be used.
     * </p>
     */
    private TrackContest trackContest = null;

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
        this.trackContest = new TrackContest();
    }

    /**
     * <p>
     * This method tests the setTrackContestType() method to throw IllegalArgumentException
     * for null trackContestType.
     * </p>
     */
    public void testSetTrackContestTypeNull() {
        try {
            this.trackContest.setTrackContestType(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setTrackContestResultCalculator() method to throw IllegalArgumentException
     * for null trackContestResultCalculator.
     * </p>
     */
    public void testSetTrackContestResultCalculatorNull() {
        try {
            this.trackContest.setTrackContestResultCalculator(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setTrack() method to throw IllegalArgumentException
     * for null track.
     * </p>
     */
    public void testSetTrackNull() {
        try {
            this.trackContest.setTrack(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

}
