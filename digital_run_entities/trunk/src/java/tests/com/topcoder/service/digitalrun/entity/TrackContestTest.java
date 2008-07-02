/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link TrackContest} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TrackContestTest extends TestCase {

    /**
     * Represents the <code>TrackContest</code> instance to test.
     */
    private TrackContest trackContest = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        trackContest = new TrackContest();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void tearDown() throws Exception {
        trackContest = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(TrackContestTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link TrackContest#TrackContest()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_TrackContest() {
        // check for null
        assertNotNull("TrackContest creation failed", trackContest);
    }

    /**
     * <p>
     * Accuracy test for {@link TrackContest#setTrackContestType(TrackContestType)} and
     * {@link TrackContest#getTrackContestType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setTrackContestType() {
        // set the value to test
        TrackContestType type = new TrackContestType();
        type.setId(10);
        trackContest.setTrackContestType(type);
        assertEquals("getTrackContestType and setTrackContestType failure occured", type, trackContest
                .getTrackContestType());
    }

    /**
     * <p>
     * Failure test for {@link TrackContest#setTrackContestType(TrackContestType)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      TrackContestType trackContestType : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setTrackContestType() throws Exception {
        try {
            trackContest.setTrackContestType(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'trackContestType' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link TrackContest#setTrackContestResultCalculator(TrackContestResultCalculator)} and
     * {@link TrackContest#getTrackContestResultCalculator()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setTrackContestResultCalculator() {
        // set the value to test
        TrackContestResultCalculator calculator = new TrackContestResultCalculator();
        calculator.setId(100);
        trackContest.setTrackContestResultCalculator(calculator);
        assertEquals("getTrackContestResultCalculator and setTrackContestResultCalculator failure occured",
                calculator, trackContest.getTrackContestResultCalculator());
    }

    /**
     * <p>
     * Failure test for {@link TrackContest#setTrackContestResultCalculator(TrackContestResultCalculator)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      TrackContestResultCalculator trackContestResultCalculator : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setTrackContestResultCalculator() throws Exception {
        try {
            trackContest.setTrackContestResultCalculator(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'trackContestResultCalculator' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link TrackContest#setTrack(Track)} and {@link TrackContest#getTrack()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setTrack() {
        // set the value to test
        Track track = new Track();
        track.setId(10);
        trackContest.setTrack(track);
        assertEquals("getTrack and setTrack failure occured", track, trackContest.getTrack());
    }

    /**
     * <p>
     * Failure test for {@link TrackContest#setTrack(Track)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Track track : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setTrack() throws Exception {
        try {
            trackContest.setTrack(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'track' cannot be null.");
        }
    }
}
