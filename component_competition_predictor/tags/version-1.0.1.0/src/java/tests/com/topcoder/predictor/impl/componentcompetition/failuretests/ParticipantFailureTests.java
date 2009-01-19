/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.failuretests;

import com.topcoder.predictor.impl.componentcompetition.Participant;

import junit.framework.TestCase;
/**
 * The failure tests of the class <code>Participant</code>.
 *
 * @author telly12
 * @version 1.0
 */
public class ParticipantFailureTests extends TestCase {
    /**
     * The Participant instance for test.
     */
    private Participant instance = new Participant();
    /**
     * The failure test of the method setRating,
     * fail for the rating is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setRating_failure() {
        try {
            instance.setRating(new Integer(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method setReliability,
     * fail for the Reliability is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setReliability_failure() {
        try {
            instance.setReliability(new Double(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method setScreeningScore,
     * fail for the ScreeningScore is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setScreeningScore_failure() {
        try {
            instance.setScreeningScore(new Double(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method setScoreBeforeAppeals,
     * fail for the ScoreBeforeAppeals is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setScoreBeforeAppeals_failure() {
        try {
            instance.setScoreBeforeAppeals(new Double(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method setScoreAfterAppeals,
     * fail for the ScoreAfterAppeals is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setScoreAfterAppeals_failure() {
        try {
            instance.setScoreAfterAppeals(new Double(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method setAppealCount,
     * fail for the AppealCount is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setAppealCount_failure1() {
        try {
            instance.setAppealCount(new Integer(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method setAppealCount,
     * fail for the SuccessfulAppealCount is larger than AppealCount,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setAppealCount_failure2() {
        instance = new Participant();
        instance.setSuccessfulAppealCount(new Integer(5));
        try {
            instance.setAppealCount(new Integer(3));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method setSuccessfulAppealCount,
     * fail for the SuccessfulAppealCount is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setSuccessfulAppealCount_failure1() {
        try {
            instance.setSuccessfulAppealCount(new Integer(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method setSuccessfulAppealCount,
     * fail for the SuccessfulAppealCount is larger than AppealCount,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setSuccessfulAppealCount_failure2() {
        instance = new Participant();
        instance.setAppealCount(new Integer(3));
        try {
            instance.setSuccessfulAppealCount(new Integer(5));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
}
