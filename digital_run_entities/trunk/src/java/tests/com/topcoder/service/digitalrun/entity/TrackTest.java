/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link Track} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TrackTest extends TestCase {

    /**
     * Represents the <code>Track</code> instance to test.
     */
    private Track track = null;

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
        track = new Track();
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
        track = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(TrackTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link Track#Track()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_Track() {
        // check for null
        assertNotNull("Track creation failed", track);
    }

    /**
     * <p>
     * Accuracy test for {@link Track#setTrackType(TrackType)} and {@link Track#getTrackType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setTrackType() {
        // set the value to test
        TrackType type = new TrackType();
        type.setId(10);
        track.setTrackType(type);
        assertEquals("getTrackType and setTrackType failure occured", type, track.getTrackType());
    }

    /**
     * <p>
     * Failure test for {@link Track#setTrackType(TrackType)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      TrackType trackType : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setTrackType() throws Exception {
        try {
            track.setTrackType(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'trackType' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Track#setTrackStatus(TrackStatus)} and {@link Track#getTrackStatus()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setTrackStatus() {
        // set the value to test
        TrackStatus status = new TrackStatus();
        status.setId(12);
        track.setTrackStatus(status);
        assertEquals("getTrackStatus and setTrackStatus failure occured", status, track.getTrackStatus());
    }

    /**
     * <p>
     * Failure test for {@link Track#setTrackStatus(TrackStatus)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      TrackStatus trackStatus : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setTrackStatus() throws Exception {
        try {
            track.setTrackStatus(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'trackStatus' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Track#setStartDate(Date)} and {@link Track#getStartDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setStartDate() {
        // set the value to test
        Date date = new Date();
        track.setStartDate(date);
        assertEquals("getStartDate and setStartDate failure occured", date, track.getStartDate());
    }

    /**
     * <p>
     * Failure test for {@link Track#setStartDate(Date)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Date startDate : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setStartDate() throws Exception {
        try {
            track.setStartDate(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'startDate' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Track#setEndDate(Date)} and {@link Track#getEndDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setEndDate() {
        // set the value to test
        Date date = new Date();
        track.setEndDate(date);
        assertEquals("getEndDate and setEndDate failure occured", date, track.getEndDate());
    }

    /**
     * <p>
     * Failure test for {@link Track#setEndDate(Date)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Date endDate : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setEndDate() throws Exception {
        try {
            track.setEndDate(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'endDate' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Track#setProjectTypes(Collection<ProjectType>)} and {@link Track#getProjectTypes()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setProjectTypes() {
        // set the value to test
        Collection<ProjectType> types = new ArrayList<ProjectType>();
        ProjectType type = new ProjectType();
        type.setId(1);

        types.add(type);
        track.setProjectTypes(types);
        List<ProjectType> list = (List<ProjectType>) track.getProjectTypes();
        assertEquals("getProjectTypes and setProjectTypes failure occured", types.size(), list.size());
        // test the element
        ProjectType ret = list.get(0);
        assertTrue("getProjectTypes and setProjectTypes failure occured", type.equals(ret));

        // test the shallow copy
        list.add(new ProjectType());
        assertTrue("getProjectTypes and setProjectTypes failure occured, shallow copy expected",
                list.size() != types.size());
    }

    /**
     * <p>
     * Failure test for {@link Track#setProjectTypes(Collection<ProjectType>)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Collection&lt;ProjectType&gt; projectTypes : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setProjectTypes() throws Exception {
        try {
            track.setProjectTypes(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'projectTypes' cannot be null.");
        }
    }

    /**
     * <p>
     * Failure test for {@link Track#setProjectTypes(Collection<ProjectType>)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Collection&lt;ProjectType&gt; projectTypes : contains null
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setProjectTypes2() throws Exception {
        try {
            Collection<ProjectType> types = new ArrayList<ProjectType>();
            types.add(null);
            track.setProjectTypes(types);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The collection 'projectTypes' contains null elements.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Track#setPointsCalculator(PointsCalculator)} and
     * {@link Track#getPointsCalculator()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setPointsCalculator() {
        // set the value to test
        PointsCalculator calculator = new PointsCalculator();
        calculator.setId(10);
        track.setPointsCalculator(calculator);
        assertEquals("getPointsCalculator and setPointsCalculator failure occured", calculator, track
                .getPointsCalculator());
    }

    /**
     * <p>
     * Failure test for {@link Track#setPointsCalculator(PointsCalculator)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      PointsCalculator pointsCalculator : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setPointsCalculator() throws Exception {
        try {
            track.setPointsCalculator(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'pointsCalculator' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Track#setDigitalRunPoints(Collection<DigitalRunPoints>)} and
     * {@link Track#getDigitalRunPoints()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setDigitalRunPoints() {
        // set the value to test
        Collection<DigitalRunPoints> points = new ArrayList<DigitalRunPoints>();
        DigitalRunPoints point = new DigitalRunPoints();
        point.setId(1);
        points.add(point);
        track.setDigitalRunPoints(points);
        List<DigitalRunPoints> list = (List<DigitalRunPoints>) track.getDigitalRunPoints();
        assertEquals("getDigitalRunPoints and setDigitalRunPoints failure occured", points.size(), list.size());
        // test the element
        DigitalRunPoints ret = list.get(0);
        assertTrue("getDigitalRunPoints and setDigitalRunPoints failure occured", point.equals(ret));

        // test the shallow copy
        list.add(new DigitalRunPoints());
        assertTrue("getDigitalRunPoints and setDigitalRunPoints failure occured, shallow copy expected", list
                .size() != points.size());
    }

    /**
     * <p>
     * Failure test for {@link Track#setDigitalRunPoints(Collection<DigitalRunPoints>)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Collection&lt;DigitalRunPoints&gt; digitalRunPoints : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setDigitalRunPoints() throws Exception {
        try {
            track.setDigitalRunPoints(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'digitalRunPoints' cannot be null.");
        }
    }

    /**
     * <p>
     * Failure test for {@link Track#setDigitalRunPoints(Collection<DigitalRunPoints>)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Collection&lt;DigitalRunPoints&gt; digitalRunPoints : contains null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setDigitalRunPoints2() throws Exception {
        try {
            Collection<DigitalRunPoints> points = new ArrayList<DigitalRunPoints>();
            points.add(null);
            track.setDigitalRunPoints(points);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The collection 'digitalRunPoints' contains null elements.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Track#setTrackContests(Collection<TrackContest>)} and
     * {@link Track#getTrackContests()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setTrackContests() {
        // set the value to test
        Collection<TrackContest> contests = new ArrayList<TrackContest>();
        TrackContest contest = new TrackContest();
        contest.setId(1);
        contests.add(contest);
        track.setTrackContests(contests);
        assertEquals("getTrackContests and setTrackContests failure occured", contests.size(), track
                .getTrackContests().size());

        List<TrackContest> list = (List<TrackContest>) track.getTrackContests();
        assertEquals("getTrackContests and setTrackContests failure occured", contests.size(), list.size());
        // test the element
        TrackContest ret = list.get(0);
        assertTrue("getTrackContests and setTrackContests failure occured", contest.equals(ret));

        // test the shallow copy
        list.add(new TrackContest());
        assertTrue("getTrackContests and setTrackContests failure occured, shallow copy expected",
                list.size() != contests.size());
    }

    /**
     * <p>
     * Failure test for {@link Track#setTrackContests(Collection<TrackContest>)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Collection&lt;TrackContest&gt; trackContests : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setTrackContests() throws Exception {
        try {
            track.setTrackContests(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'trackContests' cannot be null.");
        }
    }

    /**
     * <p>
     * Failure test for {@link Track#setTrackContests(Collection<TrackContest>)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Collection&lt;TrackContest&gt; trackContests : contains null
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setTrackContests2() throws Exception {
        try {
            Collection<TrackContest> contests = new ArrayList<TrackContest>();
            contests.add(null);
            track.setTrackContests(contests);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The collection 'trackContests' contains null elements.");
        }
    }
}
