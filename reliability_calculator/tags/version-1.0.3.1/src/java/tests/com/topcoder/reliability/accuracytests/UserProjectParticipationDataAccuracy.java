/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import com.topcoder.reliability.impl.BaseUserProjectData;
import com.topcoder.reliability.impl.UserProjectParticipationData;

import junit.framework.TestCase;

import java.util.Date;


/**
 * Accuracy test for <code>UserProjectParticipationData</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserProjectParticipationDataAccuracy extends TestCase {
    /**
     * The <code>UserProjectParticipationData</code> instance for testing.
     */
    private UserProjectParticipationData instance = new UserProjectParticipationData();

    /**
     * Accuracy test for constructor.
     */
    public void testCtor() {
        assertNotNull("The result should match.", instance);
        assertTrue("The result should match.",
            instance instanceof BaseUserProjectData);
    }

    /**
     * Accuracy test for <code>getProjectStartDate()</code>.
     */
    public void testGetProjectStartDate() {
        assertNull("The default value is null", instance.getProjectStartDate());

        Date value = new Date();
        instance.setProjectStartDate(value);
        assertEquals("The result should match.", value,
            instance.getProjectStartDate());

        instance.setProjectStartDate(null);
        assertNull("The result should match.", instance.getProjectStartDate());
    }

    /**
     * Accuracy test for <code>setProjectStartDate(Date)</code>.
     */
    public void testSetProjectStartDate() {
        Date value = new Date();
        instance.setProjectStartDate(value);
        assertEquals("The result should match.", value,
            instance.getProjectStartDate());

        instance.setProjectStartDate(null);
        assertNull("The result should match.", instance.getProjectStartDate());
    }

    /**
     * Accuracy test for <code>getUserRegistrationDate()</code>.
     */
    public void testGetUserRegistrationDate() {
        assertNull("The default value is null",
            instance.getUserRegistrationDate());

        Date value = new Date();
        instance.setUserRegistrationDate(value);
        assertEquals("The result should match.", value,
            instance.getUserRegistrationDate());

        instance.setUserRegistrationDate(null);
        assertNull("The result should match.",
            instance.getUserRegistrationDate());
    }

    /**
     * Accuracy test for <code>setUserRegistrationDate(Date)</code>.
     */
    public void testSetUserRegistrationDate() {
        Date value = new Date();
        instance.setUserRegistrationDate(value);
        assertEquals("The result should match.", value,
            instance.getUserRegistrationDate());

        instance.setUserRegistrationDate(null);
        assertNull("The result should match.",
            instance.getUserRegistrationDate());
    }

    /**
     * Accuracy test for <code>getUserScore()</code>.
     */
    public void testGetUserScore() {
        assertNull("The default value is 0", instance.getUserScore());

        instance.setUserScore(1.0);
        assertEquals("The result should match.", 1.0, instance.getUserScore());

        instance.setUserScore(100.0);
        assertEquals("The result should match.", 100.0, instance.getUserScore());

        instance.setUserScore(-1.0);
        assertEquals("The result should match.", -1.0, instance.getUserScore());
    }

    /**
     * Accuracy test for <code>setUserScore(long)</code>.
     */
    public void testSetUserScore() {
        instance.setUserScore(1.0);
        assertEquals("The result should match.", 1.0, instance.getUserScore());

        instance.setUserScore(100.0);
        assertEquals("The result should match.", 100.0, instance.getUserScore());

        instance.setUserScore(-1.0);
        assertEquals("The result should match.", -1.0, instance.getUserScore());
    }

    /**
     * Accuracy test for <code>isPassedReview()</code>.
     */
    public void testIsPassedReview() {
        assertEquals("The default is fale.", false, instance.isPassedReview());

        instance.setPassedReview(true);
        assertEquals("Set to true.", true, instance.isPassedReview());

        instance.setPassedReview(false);
        assertEquals("Set to false.", false, instance.isPassedReview());
    }

    /**
     * Accuracy test for <code>setPassedReview()</code>.
     */
    public void testSetPassedReview() {
        instance.setPassedReview(true);
        assertEquals("Set to true.", true, instance.isPassedReview());

        instance.setPassedReview(false);
        assertEquals("Set to false.", false, instance.isPassedReview());
    }

    /**
     * Accuracy test for <code>getSubmissionStatusId()</code>.
     */
    public void testGetSubmissionStatusId() {
        assertNull("The default value is 0", instance.getSubmissionStatusId());

        instance.setSubmissionStatusId(1L);
        assertTrue("The result should match.",
            instance.getSubmissionStatusId().equals(1L));

        instance.setSubmissionStatusId(100L);
        assertTrue("The result should match.",
            instance.getSubmissionStatusId().equals(100L));

        instance.setSubmissionStatusId(-1L);
        assertTrue("The result should match.",
            instance.getSubmissionStatusId().equals(-1L));
    }

    /**
     * Accuracy test for <code>setSubmissionStatusId(long)</code>.
     */
    public void testSetSubmissionStatusId() {
        instance.setSubmissionStatusId(1L);
        assertTrue("The result should match.",
            instance.getSubmissionStatusId().equals(1L));

        instance.setSubmissionStatusId(100L);
        assertTrue("The result should match.",
            instance.getSubmissionStatusId().equals(100L));

        instance.setSubmissionStatusId(-1L);
        assertTrue("The result should match.",
            instance.getSubmissionStatusId().equals(-1L));

        instance.setSubmissionStatusId(null);
        assertNull("The value is set to null.", instance.getSubmissionStatusId());
    }

    /**
     * Accuracy test for <code>getSubmissionPhaseStatusId()</code>.
     */
    public void testGetSubmissionPhaseStatusId() {
        assertEquals("The default value is 0", 0L,
            instance.getSubmissionPhaseStatusId());

        instance.setSubmissionPhaseStatusId(1L);
        assertEquals("The result should match.", 1L,
            instance.getSubmissionPhaseStatusId());

        instance.setSubmissionPhaseStatusId(100L);
        assertEquals("The result should match.", 100L,
            instance.getSubmissionPhaseStatusId());

        instance.setSubmissionPhaseStatusId(-1L);
        assertEquals("The result should match.", -1L,
            instance.getSubmissionPhaseStatusId());
    }

    /**
     * Accuracy test for <code>setSubmissionPhaseStatusId(long)</code>.
     */
    public void testSetSubmissionPhaseStatusId() {
        instance.setSubmissionPhaseStatusId(1L);
        assertEquals("The result should match.", 1L,
            instance.getSubmissionPhaseStatusId());

        instance.setSubmissionPhaseStatusId(100L);
        assertEquals("The result should match.", 100L,
            instance.getSubmissionPhaseStatusId());

        instance.setSubmissionPhaseStatusId(-1L);
        assertEquals("The result should match.", -1L,
            instance.getSubmissionPhaseStatusId());
    }

    /**
     * Accuracy test for <code>getSubmissionPhaseEnd()</code>.
     */
    public void testGetSubmissionPhaseEnd() {
        assertNull("The default value is null", instance.getSubmissionPhaseEnd());

        Date value = new Date();
        instance.setSubmissionPhaseEnd(value);
        assertEquals("The result should match.", value,
            instance.getSubmissionPhaseEnd());

        instance.setSubmissionPhaseEnd(null);
        assertNull("The result should match.", instance.getSubmissionPhaseEnd());
    }

    /**
     * Accuracy test for <code>setSubmissionPhaseEnd(Date)</code>.
     */
    public void testSetSubmissionPhaseEnd() {
        Date value = new Date();
        instance.setSubmissionPhaseEnd(value);
        assertEquals("The result should match.", value,
            instance.getSubmissionPhaseEnd());

        instance.setSubmissionPhaseEnd(null);
        assertNull("The result should match.", instance.getSubmissionPhaseEnd());
    }

    /**
     * Accuracy test for <code>getScreeningPhaseEnd()</code>.
     */
    public void testGetScreeningPhaseEnd() {
        assertNull("The default value is null", instance.getScreeningPhaseEnd());

        Date value = new Date();
        instance.setScreeningPhaseEnd(value);
        assertEquals("The result should match.", value,
            instance.getScreeningPhaseEnd());

        instance.setScreeningPhaseEnd(null);
        assertNull("The result should match.", instance.getScreeningPhaseEnd());
    }

    /**
     * Accuracy test for <code>setScreeningPhaseEnd(Date)</code>.
     */
    public void testSetScreeningPhaseEnd() {
        Date value = new Date();
        instance.setScreeningPhaseEnd(value);
        assertEquals("The result should match.", value,
            instance.getScreeningPhaseEnd());

        instance.setScreeningPhaseEnd(null);
        assertNull("The result should match.", instance.getScreeningPhaseEnd());
    }

    /**
     * Accuracy test for <code>getAppealsResponsePhaseEnd()</code>.
     */
    public void testGetAppealsResponsePhaseEnd() {
        assertNull("The default value is null",
            instance.getAppealsResponsePhaseEnd());

        Date value = new Date();
        instance.setAppealsResponsePhaseEnd(value);
        assertEquals("The result should match.", value,
            instance.getAppealsResponsePhaseEnd());

        instance.setAppealsResponsePhaseEnd(null);
        assertNull("The result should match.",
            instance.getAppealsResponsePhaseEnd());
    }

    /**
     * Accuracy test for <code>setAppealsResponsePhaseEnd(Date)</code>.
     */
    public void testSetAppealsResponsePhaseEnd() {
        Date value = new Date();
        instance.setAppealsResponsePhaseEnd(value);
        assertEquals("The result should match.", value,
            instance.getAppealsResponsePhaseEnd());

        instance.setAppealsResponsePhaseEnd(null);
        assertNull("The result should match.",
            instance.getAppealsResponsePhaseEnd());
    }
}
