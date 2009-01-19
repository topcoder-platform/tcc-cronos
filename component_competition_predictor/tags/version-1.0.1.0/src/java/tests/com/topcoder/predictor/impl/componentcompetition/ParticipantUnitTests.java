/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for Participant class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ParticipantUnitTests extends TestCase {

    /**
     * An instance of Participant for the following tests.
     */
    private Participant tester = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        tester = new Participant();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        tester = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor. No exception is thrown.
     * </p>
     */
    public void test_ctor() {
        // no exception
        new Participant();
    }

    /**
     * <p>
     * Accuracy test for the user id property. It should be got/set properly.
     * </p>
     */
    public void test_user_id() {
        assertNull("Initial value should be null.", tester.getUserId());
        tester.setUserId(123);
        assertEquals("The user id is not got/set properly.", new Integer(123), tester.getUserId());
    }

    /**
     * <p>
     * Accuracy test for the rating property. It should be got/set properly.
     * </p>
     */
    public void test_rating() {
        assertNull("Initial value should be null.", tester.getRating());
        tester.setRating(333);
        assertEquals("The rating is not got/set properly.", new Integer(333), tester.getRating());
    }

    /**
     * <p>
     * Accuracy test for the reliability property. It should be got/set properly.
     * </p>
     */
    public void test_reliability() {
        assertNull("Initial value should be null.", tester.getReliability());
        tester.setReliability(0.456);
        assertEquals("The reliability is not got/set properly.", new Double(0.456), tester.getReliability());
    }

    /**
     * <p>
     * Accuracy test for the autoScreeningResult property. It should be got/set properly.
     * </p>
     */
    public void test_autoScreeningResult() {
        assertNull("Initial value should be null.", tester.getAutoScreeningResult());
        tester.setAutoScreeningResult("ok");
        assertEquals("The autoScreeningResult is not got/set properly.", "ok", tester.getAutoScreeningResult());
    }

    /**
     * <p>
     * Accuracy test for the screeningScore property. It should be got/set properly.
     * </p>
     */
    public void test_screeningScore() {
        assertNull("Initial value should be null.", tester.getScreeningScore());
        tester.setScreeningScore(23.456);
        assertEquals("The screeningScore is not got/set properly.", new Double(23.456), tester.getScreeningScore());
    }

    /**
     * <p>
     * Accuracy test for the passedScreening property. It should be got/set properly.
     * </p>
     */
    public void test_passedScreening() {
        assertNull("Initial value should be null.", tester.getPassedScreening());
        tester.setPassedScreening(true);
        assertEquals("The passedScreening is not got/set properly.", new Boolean(true), tester.getPassedScreening());
    }

    /**
     * <p>
     * Accuracy test for the scoreBeforeAppeals property. It should be got/set properly.
     * </p>
     */
    public void test_scoreBeforeAppeals() {
        assertNull("Initial value should be null.", tester.getScoreBeforeAppeals());
        tester.setScoreBeforeAppeals(1.1);
        assertEquals("The scoreBeforeAppeals is not got/set properly.", new Double(1.1),
            tester.getScoreBeforeAppeals());
    }

    /**
     * <p>
     * Accuracy test for the scoreAfterAppeals property. It should be got/set properly.
     * </p>
     */
    public void test_scoreAfterAppeals() {
        assertNull("Initial value should be null.", tester.getScoreAfterAppeals());
        tester.setScoreAfterAppeals(2.2);
        assertEquals("The scoreAfterAppeals is not got/set properly.", new Double(2.2), tester.getScoreAfterAppeals());
    }

    /**
     * <p>
     * Accuracy test for the passedReview property. It should be got/set properly.
     * </p>
     */
    public void test_passedReview() {
        assertNull("Initial value should be null.", tester.getPassedReview());
        tester.setPassedReview(false);
        assertEquals("The passedReview is not got/set properly.", new Boolean(false), tester.getPassedReview());
    }

    /**
     * <p>
     * Accuracy test for the appealCount property. It should be got/set properly.
     * </p>
     */
    public void test_appealCount() {
        assertNull("Initial value should be null.", tester.getAppealCount());
        tester.setAppealCount(3);
        assertEquals("The appealCount is not got/set properly.", new Integer(3), tester.getAppealCount());
    }

    /**
     * <p>
     * Accuracy test for the successfulAppealCount property. It should be got/set properly.
     * </p>
     */
    public void test_successfulAppealCount() {
        assertNull("Initial value should be null.", tester.getSuccessfulAppealCount());
        tester.setSuccessfulAppealCount(5);
        assertEquals("The successfulAppealCount is not got/set properly.", new Integer(5), tester
                        .getSuccessfulAppealCount());
    }

    /**
     * <p>
     * Accuracy test for the clone method, the cloned object should be same as the original object.
     * </p>
     */
    public void test_clone() {
        tester.setAppealCount(6);
        tester.setAutoScreeningResult("ok");
        tester.setPassedReview(true);
        tester.setPassedScreening(true);
        tester.setRating(100);
        tester.setReliability(0.5);
        tester.setScoreAfterAppeals(50.0);
        tester.setScoreBeforeAppeals(40.0);
        tester.setScreeningScore(100.0);
        tester.setSuccessfulAppealCount(3);
        tester.setUserId(111);
        Object obj = tester.clone();
        assertTrue("Cloned object should be type of Participant.", obj instanceof Participant);
        Participant t = (Participant) obj;
        assertEquals("The appeal count is incorrect.", 6, t.getAppealCount().intValue());
        assertEquals("The auto screening result is incorrect.", "ok", t.getAutoScreeningResult());
        assertEquals("The passed review is incorrect.", true, t.getPassedReview().booleanValue());
        assertEquals("The passed screening is incorrect.", true, t.getPassedScreening().booleanValue());
        assertEquals("The rating is incorrect.", 100, t.getRating().intValue());
        assertEquals("The reliability is incorrect.", 0.5, t.getReliability().doubleValue());
        assertEquals("The score after appeals is incorrect.", 50.0, t.getScoreAfterAppeals().doubleValue());
        assertEquals("The score before appeals is incorrect.", 40.0, t.getScoreBeforeAppeals().doubleValue());
        assertEquals("The screening score is incorrect.", 100.0, t.getScreeningScore().doubleValue());
        assertEquals("The successful appeal count is incorrect.", 3, t.getSuccessfulAppealCount().intValue());
        assertEquals("The user id is incorrect.", 111, t.getUserId().intValue());
    }

    /**
     * <p>
     * Failure test for the rating property. IllegalArgumentException is thrown if rating is negative.
     * </p>
     */
    public void test_rating_failure1() {
        try {
            tester.setRating(-123);
            fail("IllegalArgumentException is thrown if rating is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the reliability property. IllegalArgumentException is thrown if reliability is negative.
     * </p>
     */
    public void test_reliability_failure1() {
        try {
            tester.setReliability(-0.2);
            fail("IllegalArgumentException is thrown if reliability is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the reliability property. IllegalArgumentException is thrown if reliability > 1.
     * </p>
     */
    public void test_reliability_failure2() {
        try {
            tester.setReliability(2.0);
            fail("IllegalArgumentException is thrown if reliability > 1.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the screeningScore property. IllegalArgumentException is thrown if screeningScore is negative.
     * </p>
     */
    public void test_screeningScore_failure1() {
        try {
            tester.setScreeningScore(-1.0);
            fail("IllegalArgumentException is thrown if screeningScore is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the screeningScore property. IllegalArgumentException is thrown if screeningScore > 100.
     * </p>
     */
    public void test_screeningScore_failure2() {
        try {
            tester.setScreeningScore(101.0);
            fail("IllegalArgumentException is thrown if screeningScore > 100.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the scoreBeforeAppeals property. IllegalArgumentException is thrown if it is negative.
     * </p>
     */
    public void test_scoreBeforeAppeals_failure1() {
        try {
            tester.setScoreBeforeAppeals(-1.1);
            fail("IllegalArgumentException is thrown if scoreBeforeAppeals is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the scoreBeforeAppeals property. IllegalArgumentException is thrown if it is larger than 100.
     * </p>
     */
    public void test_scoreBeforeAppeals_failure2() {
        try {
            tester.setScoreBeforeAppeals(100.1);
            fail("IllegalArgumentException is thrown if scoreBeforeAppeals is larger than 100.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the scoreAfterAppeals property. IllegalArgumentException is thrown if it is negative.
     * </p>
     */
    public void test_scoreAfterAppeals_failure1() {
        try {
            tester.setScoreAfterAppeals(-1.1);
            fail("IllegalArgumentException is thrown if scoreAfterAppeals is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the scoreAfterAppeals property. IllegalArgumentException is thrown if it is larger than 100.
     * </p>
     */
    public void test_scoreAfterAppeals_failure2() {
        try {
            tester.setScoreAfterAppeals(100.1);
            fail("IllegalArgumentException is thrown if scoreAfterAppeals is larger than 100.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the appealCount property. IllegalArgumentException is thrown if appealCount is negative.
     * </p>
     */
    public void test_appealCount_failure1() {
        try {
            tester.setAppealCount(-1);
            fail("IllegalArgumentException is thrown if appealCount is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the appealCount property. IllegalArgumentException is thrown if appeal count is
     * less than successful appeal count.
     * </p>
     */
    public void test_appealCount_failure2() {
        tester.setSuccessfulAppealCount(100);
        try {
            tester.setAppealCount(3);
            fail("IllegalArgumentException is thrown if appeal count is less than successful appeal count.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the successfulAppealCount property. IllegalArgumentException is thrown if
     * successfulAppealCount is negative.
     * </p>
     */
    public void test_successfulAppealCount_failure1() {
        try {
            tester.setSuccessfulAppealCount(-1);
            fail("IllegalArgumentException is thrown if successfulAppealCount is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the successfulAppealCount property. IllegalArgumentException is thrown if appeal
     * count is less than successful appeal count.
     * </p>
     */
    public void test_successfulAppealCount_failure2() {
        tester.setAppealCount(1);
        try {
            tester.setSuccessfulAppealCount(300);
            fail("IllegalArgumentException is thrown if appeal count is less than successful appeal count.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

}
