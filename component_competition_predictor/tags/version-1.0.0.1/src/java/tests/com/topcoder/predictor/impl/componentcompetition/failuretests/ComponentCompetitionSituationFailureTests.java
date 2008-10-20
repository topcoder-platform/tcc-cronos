/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.failuretests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Participant;
import com.topcoder.predictor.impl.componentcompetition.Technology;

import junit.framework.TestCase;
/**
 * The failure tests of the class <code>ComponentCompetitionSituation</code>.
 *
 * @author telly12
 * @version 1.0
 */
public class ComponentCompetitionSituationFailureTests extends TestCase {
    /**
     * The ComponentCompetitionSituation instance for test.
     */
    private ComponentCompetitionSituation instance = new ComponentCompetitionSituation();
    /**
     * The failure test of setPostingDate,
     * fail for the postingDate is later than endDate,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setPostingDate_failure() {
        Date date1 = new Date();
        Date date2 = new Date(date1.getTime() + 1);
        instance.setEndDate(date1);
        try {
            instance.setPostingDate(date2);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of setEndDate,
     * fail for the endDate is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setEndDate_failure() {
        instance = new ComponentCompetitionSituation();
        Date date1 = new Date();
        Date date2 = new Date(date1.getTime() + 1);
        instance.setPostingDate(date2);
        try {
            instance.setEndDate(date1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of setFinalFixCount,
     * fail for the finalFixCount is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setFinalFixCount_failure1() {
        try {
            instance.setFinalFixCount(new Integer(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of setPrize,
     * fail for the prize is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setPrize_failure1() {
        try {
            instance.setPrize(new Double(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of setDigitalRunPoints,
     * fail for the DigitalRunPoints is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setDigitalRunPoints_failure1() {
        try {
            instance.setDigitalRunPoints(new Integer(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of setKeywords,
     * fail for the Keywords contains null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setKeywords_failure1() {
        List<String> keywords = new ArrayList<String>();
        keywords.add(null);
        try {
            instance.setKeywords(keywords);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of setKeywords,
     * fail for the Keywords contains empty String,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setKeywords_failure2() {
        List<String> keywords = new ArrayList<String>();
        keywords.add("");
        try {
            instance.setKeywords(keywords);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of setKeywords,
     * fail for the Keywords contains trimmed empty String,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setKeywords_failure3() {
        List<String> keywords = new ArrayList<String>();
        keywords.add("  ");
        try {
            instance.setKeywords(keywords);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of setTechnologies,
     * fail for the technologies contains null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setTechnologies_failure1() {
        List<Technology> technologies = new ArrayList<Technology>();
        technologies.add(null);
        try {
            instance.setTechnologies(technologies);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of setParticipants,
     * fail for the participants contains null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_setParticipants_failure1() {
        List<Participant> participants = new ArrayList<Participant>();
        participants.add(null);
        try {
            instance.setParticipants(participants);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
}
