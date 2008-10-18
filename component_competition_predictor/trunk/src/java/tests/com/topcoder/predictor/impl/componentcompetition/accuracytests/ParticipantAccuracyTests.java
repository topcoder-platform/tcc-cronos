/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.accuracytests;

import com.topcoder.predictor.impl.componentcompetition.Participant;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link Participant}</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class ParticipantAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the <code>Participant</code> used in tests.
     * </p>
     */
    private Participant instance = null;

    /**
     * <p>
     * Sets up the tests.
     * </p>
     */
    public void setUp() {
        instance = new Participant();
    }

    /**
     * <p>
     * Tests accuracy of <code>Participant()</code> constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor() {
        assertNull("UserId should be correct.", instance.getUserId());
        assertNull("Rating should be correct.", instance.getRating());
        assertNull("Reliability should be correct.", instance.getReliability());
        assertNull("AutoScreeningResult should be correct.", instance.getAutoScreeningResult());
        assertNull("ScreeningScore should be correct.", instance.getScreeningScore());
        assertNull("PassedScreening should be correct.", instance.getPassedScreening());
        assertNull("ScoreBeforeAppeals should be correct.", instance.getScoreBeforeAppeals());
        assertNull("ScoreAfterAppeals should be correct.", instance.getScoreAfterAppeals());
        assertNull("PassedReview should be correct.", instance.getPassedReview());
        assertNull("AppealCount should be correct.", instance.getAppealCount());
        assertNull("SuccessfulAppealCount should be correct.", instance.getSuccessfulAppealCount());
    }

    /**
     * <p>
     * Tests accuracy of <code>getUserId()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetUserId() {
        instance.setUserId(1);

        assertEquals("'getUserId' should be correct.", 1, instance.getUserId().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setUserId(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetUserId1() {
        instance.setUserId(1);

        assertEquals("'setUserId' should be correct.", 1, (int)instance.getUserId());
    }

    /**
     * <p>
     * Tests accuracy of <code>setUserId(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetUserId2() {
        instance.setUserId(null);

        assertNull("'setUserId' should be correct.", instance.getUserId());
    }

    /**
     * <p>
     * Tests accuracy of <code>getRating()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetRating() {
        instance.setRating(1);

        assertEquals("'getRating' should be correct.", 1, instance.getRating().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setRating(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetRating1() {
        instance.setRating(1);

        assertEquals("'setRating' should be correct.", 1, instance.getRating().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setRating(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetRating2() {
        instance.setRating(null);

        assertNull("'setRating' should be correct.", instance.getRating());
    }

    /**
     * <p>
     * Tests accuracy of <code>getReliability()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetReliability() {
        instance.setReliability(0.0);

        assertEquals("'getReliability' should be correct.", 0.0, instance.getReliability().doubleValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setReliability(Double)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetReliability1() {
        instance.setReliability(1.0);

        assertEquals("'setReliability' should be correct.", 1.0, instance.getReliability().doubleValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setReliability(Double)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetReliability2() {
        instance.setReliability(null);

        assertNull("'setReliability' should be correct.", instance.getReliability());
    }

    /**
     * <p>
     * Tests accuracy of <code>getAutoScreeningResult()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetAutoScreeningResult() {
        instance.setAutoScreeningResult("new_value");

        assertEquals("'getAutoScreeningResult' should be correct.", "new_value", instance.getAutoScreeningResult());
    }

    /**
     * <p>
     * Tests accuracy of <code>setAutoScreeningResult(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetAutoScreeningResult1() {
        instance.setAutoScreeningResult("new_value");

        assertEquals("'setAutoScreeningResult' should be correct.", "new_value", instance.getAutoScreeningResult());
    }

    /**
     * <p>
     * Tests accuracy of <code>setAutoScreeningResult(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetAutoScreeningResult2() {
        instance.setAutoScreeningResult(null);

        assertNull("'setAutoScreeningResult' should be correct.", instance.getAutoScreeningResult());
    }

    /**
     * <p>
     * Tests accuracy of <code>getScreeningScore()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetScreeningScore() {
        instance.setScreeningScore(0.0);

        assertEquals("'getScreeningScore' should be correct.", 0.0, instance.getScreeningScore().doubleValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setScreeningScore(Double)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetScreeningScore1() {
        instance.setScreeningScore(0.0);

        assertEquals("'setScreeningScore' should be correct.", 0.0, instance.getScreeningScore().doubleValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setScreeningScore(Double)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetScreeningScore2() {
        instance.setScreeningScore(null);

        assertNull("'setScreeningScore' should be correct.", instance.getScreeningScore());
    }

    /**
     * <p>
     * Tests accuracy of <code>getPassedScreening()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetPassedScreening() {
        instance.setPassedScreening(true);

        assertTrue("'getPassedScreening' should be correct.", instance.getPassedScreening());
    }

    /**
     * <p>
     * Tests accuracy of <code>setPassedScreening(Boolean)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetPassedScreening1() {
        instance.setPassedScreening(false);

        assertFalse("'setPassedScreening' should be correct.", instance.getPassedScreening());
    }

    /**
     * <p>
     * Tests accuracy of <code>setPassedScreening(Boolean)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetPassedScreening2() {
        instance.setPassedScreening(null);

        assertNull("'setPassedScreening' should be correct.", instance.getPassedScreening());
    }

    /**
     * <p>
     * Tests accuracy of <code>getScoreBeforeAppeals()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetScoreBeforeAppeals() {
        instance.setScoreBeforeAppeals(0.0);

        assertEquals("'getScoreBeforeAppeals' should be correct.", 0.0, instance.getScoreBeforeAppeals()
            .doubleValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setScoreBeforeAppeals(Double)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetScoreBeforeAppeals1() {
        instance.setScoreBeforeAppeals(100.00);

        assertEquals("'setScoreBeforeAppeals' should be correct.", 100.00, instance.getScoreBeforeAppeals()
            .doubleValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setScoreBeforeAppeals(Double)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetScoreBeforeAppeals2() {
        instance.setScoreBeforeAppeals(null);

        assertNull("'setScoreBeforeAppeals' should be correct.", instance.getScoreBeforeAppeals());
    }

    /**
     * <p>
     * Tests accuracy of <code>getScoreAfterAppeals()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetScoreAfterAppeals() {
        instance.setScoreAfterAppeals(0.0);

        assertEquals("'getScoreAfterAppeals' should be correct.", 0.0, instance.getScoreAfterAppeals().doubleValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setScoreAfterAppeals(Double)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetScoreAfterAppeals1() {
        instance.setScoreAfterAppeals(100.0);

        assertEquals("'setScoreAfterAppeals' should be correct.", 100.0, instance.getScoreAfterAppeals().doubleValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setScoreAfterAppeals(Double)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetScoreAfterAppeals2() {
        instance.setScoreAfterAppeals(null);

        assertNull("'setScoreAfterAppeals' should be correct.", instance.getScoreAfterAppeals());
    }

    /**
     * <p>
     * Tests accuracy of <code>getPassedReview()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetPassedReview() {
        instance.setPassedReview(true);

        assertTrue("'getPassedReview' should be correct.", instance.getPassedReview());
    }

    /**
     * <p>
     * Tests accuracy of <code>setPassedReview(Boolean)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetPassedReview1() {
        instance.setPassedReview(false);

        assertFalse("'setPassedReview' should be correct.", instance.getPassedReview());
    }

    /**
     * <p>
     * Tests accuracy of <code>setPassedReview(Boolean)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetPassedReview2() {
        instance.setPassedReview(null);

        assertNull("'setPassedReview' should be correct.", instance.getPassedReview());
    }

    /**
     * <p>
     * Tests accuracy of <code>getAppealCount()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetAppealCount() {
        instance.setAppealCount(0);

        assertEquals("'getAppealCount' should be correct.", 0, instance.getAppealCount().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setAppealCount(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetAppealCount1() {
        instance.setAppealCount(1);

        assertEquals("'setAppealCount' should be correct.", 1, instance.getAppealCount().intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setAppealCount(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetAppealCount2() {
        instance.setAppealCount(null);

        assertNull("'setAppealCount' should be correct.", instance.getAppealCount());
    }

    /**
     * <p>
     * Tests accuracy of <code>getSuccessfulAppealCount()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetSuccessfulAppealCount() {
        assertNull("'getSuccessfulAppealCount' should be correct.", instance.getSuccessfulAppealCount());
    }

    /**
     * <p>
     * Tests accuracy of <code>setSuccessfulAppealCount(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetSuccessfulAppealCount1() {
        instance.setAppealCount(1);
        instance.setSuccessfulAppealCount(1);

        assertEquals("'setSuccessfulAppealCount' should be correct.", 1, instance.getSuccessfulAppealCount()
            .intValue());
    }

    /**
     * <p>
     * Tests accuracy of <code>setSuccessfulAppealCount(Integer)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetSuccessfulAppealCount2() {
        instance.setSuccessfulAppealCount(null);

        assertNull("'setSuccessfulAppealCount' should be correct.", instance.getSuccessfulAppealCount());
    }

    /**
     * <p>
     * Tests accuracy of <code>clone()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testClone1() {
        Participant cloneObj = (Participant) instance.clone();

        assertNull("'clone' should be correct.", cloneObj.getUserId());
        assertNull("'clone' should be correct.", cloneObj.getRating());
        assertNull("'clone' should be correct.", cloneObj.getReliability());
        assertNull("'clone' should be correct.", cloneObj.getAutoScreeningResult());
        assertNull("'clone' should be correct.", cloneObj.getScreeningScore());
        assertNull("'clone' should be correct.", cloneObj.getPassedScreening());
        assertNull("'clone' should be correct.", cloneObj.getScoreBeforeAppeals());
        assertNull("'clone' should be correct.", cloneObj.getScoreAfterAppeals());
        assertNull("'clone' should be correct.", cloneObj.getPassedReview());
        assertNull("'clone' should be correct.", cloneObj.getAppealCount());
        assertNull("'clone' should be correct.", cloneObj.getSuccessfulAppealCount());
    }

    /**
     * <p>
     * Tests accuracy of <code>clone()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testClone2() {
        instance.setUserId(1);
        instance.setRating(2);
        instance.setReliability(0.1);
        instance.setAutoScreeningResult("autoScreeningResult");
        instance.setScreeningScore(0.2);
        instance.setPassedScreening(true);
        instance.setScoreBeforeAppeals(0.3);
        instance.setScoreAfterAppeals(0.4);
        instance.setPassedReview(true);
        instance.setAppealCount(4);
        instance.setSuccessfulAppealCount(3);

        Participant cloneObj = (Participant) instance.clone();

        assertEquals("'clone' should be correct.", 1, cloneObj.getUserId().intValue());
        assertEquals("'clone' should be correct.", 2, cloneObj.getRating().intValue());
        assertEquals("'clone' should be correct.", 0.1, cloneObj.getReliability().doubleValue());
        assertEquals("'clone' should be correct.", "autoScreeningResult", cloneObj.getAutoScreeningResult());
        assertEquals("'clone' should be correct.", 0.2, cloneObj.getScreeningScore());
        assertTrue("'clone' should be correct.", cloneObj.getPassedScreening());
        assertEquals("'clone' should be correct.", 0.3, cloneObj.getScoreBeforeAppeals().doubleValue());
        assertEquals("'clone' should be correct.", 0.4, cloneObj.getScoreAfterAppeals().doubleValue());
        assertTrue("'clone' should be correct.", cloneObj.getPassedReview());
        assertEquals("'clone' should be correct.", 4, cloneObj.getAppealCount().intValue());
        assertEquals("'clone' should be correct.", 3, cloneObj.getSuccessfulAppealCount().intValue());
    }
}
