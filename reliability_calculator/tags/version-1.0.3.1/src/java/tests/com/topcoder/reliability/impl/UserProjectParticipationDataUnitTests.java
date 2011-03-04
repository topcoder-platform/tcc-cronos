/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.reliability.TestsHelper;

/**
 * <p>
 * Unit tests for {@link UserProjectParticipationData} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class UserProjectParticipationDataUnitTests {
    /**
     * <p>
     * Represents the <code>UserProjectParticipationData</code> instance used in tests.
     * </p>
     */
    private UserProjectParticipationData instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserProjectParticipationDataUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     */
    @Before
    public void setUp() {
        instance = new UserProjectParticipationData();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>UserProjectParticipationData()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new UserProjectParticipationData();

        assertNull("'projectStartDate' should be correct.", TestsHelper.getField(instance, "projectStartDate"));
        assertNull("'userRegistrationDate' should be correct.",
            TestsHelper.getField(instance, "userRegistrationDate"));
        assertNull("'userScore' should be correct.", TestsHelper.getField(instance, "userScore"));
        assertFalse("'passedReview' should be correct.", (Boolean) TestsHelper.getField(instance, "passedReview"));
        assertNull("'submissionStatusId' should be correct.", TestsHelper.getField(instance, "submissionStatusId"));
        assertEquals("'submissionPhaseStatusId' should be correct.",
            0L, TestsHelper.getField(instance, "submissionPhaseStatusId"));
        assertNull("'submissionPhaseEnd' should be correct.", TestsHelper.getField(instance, "submissionPhaseEnd"));
        assertNull("'screeningPhaseEnd' should be correct.", TestsHelper.getField(instance, "screeningPhaseEnd"));
        assertNull("'appealsResponsePhaseEnd' should be correct.",
            TestsHelper.getField(instance, "appealsResponsePhaseEnd"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectStartDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectStartDate() {
        Date value = new Date();
        instance.setProjectStartDate(value);

        assertSame("'projectStartDate' value should be properly retrieved.",
            value, instance.getProjectStartDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectStartDate(Date projectStartDate)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectStartDate() {
        Date value = new Date();
        instance.setProjectStartDate(value);

        assertSame("'projectStartDate' value should be properly set.",
            value, TestsHelper.getField(instance, "projectStartDate"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserRegistrationDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUserRegistrationDate() {
        Date value = new Date();
        instance.setUserRegistrationDate(value);

        assertSame("'userRegistrationDate' value should be properly retrieved.",
            value, instance.getUserRegistrationDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserRegistrationDate(Date userRegistrationDate)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUserRegistrationDate() {
        Date value = new Date();
        instance.setUserRegistrationDate(value);

        assertSame("'userRegistrationDate' value should be properly set.",
            value, TestsHelper.getField(instance, "userRegistrationDate"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserScore()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUserScore() {
        Double value = 1.0;
        instance.setUserScore(value);

        assertEquals("'userScore' value should be properly retrieved.", value, instance.getUserScore(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserScore(Double userScore)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUserScore() {
        Double value = 1.0;
        instance.setUserScore(value);

        assertEquals("'userScore' value should be properly set.",
            value, (Double) TestsHelper.getField(instance, "userScore"), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>isPassedReview()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_isPassedReview() {
        boolean value = true;
        instance.setPassedReview(value);

        assertTrue("'passedReview' value should be properly retrieved.", instance.isPassedReview());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPassedReview(boolean passedReview)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPassedReview() {
        boolean value = true;
        instance.setPassedReview(value);

        assertTrue("'passedReview' value should be properly set.",
            (Boolean) TestsHelper.getField(instance, "passedReview"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubmissionStatusId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getSubmissionStatusId() {
        Long value = 1L;
        instance.setSubmissionStatusId(value);

        assertEquals("'submissionStatusId' value should be properly retrieved.",
            value, instance.getSubmissionStatusId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionStatusId(Long submissionStatusId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setSubmissionStatusId() {
        Long value = 1L;
        instance.setSubmissionStatusId(value);

        assertEquals("'submissionStatusId' value should be properly set.",
            value, TestsHelper.getField(instance, "submissionStatusId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubmissionPhaseStatusId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getSubmissionPhaseStatusId() {
        long value = 1L;
        instance.setSubmissionPhaseStatusId(value);

        assertEquals("'submissionPhaseStatusId' value should be properly retrieved.",
            value, instance.getSubmissionPhaseStatusId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionPhaseStatusId(long submissionPhaseStatusId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setSubmissionPhaseStatusId() {
        long value = 1L;
        instance.setSubmissionPhaseStatusId(value);

        assertEquals("'submissionPhaseStatusId' value should be properly set.",
            value, TestsHelper.getField(instance, "submissionPhaseStatusId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubmissionPhaseEnd()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getSubmissionPhaseEnd() {
        Date value = new Date();
        instance.setSubmissionPhaseEnd(value);

        assertSame("'submissionPhaseEnd' value should be properly retrieved.",
            value, instance.getSubmissionPhaseEnd());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionPhaseEnd(Date submissionPhaseEnd)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setSubmissionPhaseEnd() {
        Date value = new Date();
        instance.setSubmissionPhaseEnd(value);

        assertSame("'submissionPhaseEnd' value should be properly set.",
            value, TestsHelper.getField(instance, "submissionPhaseEnd"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getScreeningPhaseEnd()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getScreeningPhaseEnd() {
        Date value = new Date();
        instance.setScreeningPhaseEnd(value);

        assertSame("'screeningPhaseEnd' value should be properly retrieved.",
            value, instance.getScreeningPhaseEnd());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setScreeningPhaseEnd(Date screeningPhaseEnd)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setScreeningPhaseEnd() {
        Date value = new Date();
        instance.setScreeningPhaseEnd(value);

        assertSame("'screeningPhaseEnd' value should be properly set.",
            value, TestsHelper.getField(instance, "screeningPhaseEnd"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAppealsResponsePhaseEnd()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAppealsResponsePhaseEnd() {
        Date value = new Date();
        instance.setAppealsResponsePhaseEnd(value);

        assertSame("'appealsResponsePhaseEnd' value should be properly retrieved.",
            value, instance.getAppealsResponsePhaseEnd());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAppealsResponsePhaseEnd(Date appealsResponsePhaseEnd)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAppealsResponsePhaseEnd() {
        Date value = new Date();
        instance.setAppealsResponsePhaseEnd(value);

        assertSame("'appealsResponsePhaseEnd' value should be properly set.",
            value, TestsHelper.getField(instance, "appealsResponsePhaseEnd"));
    }
}