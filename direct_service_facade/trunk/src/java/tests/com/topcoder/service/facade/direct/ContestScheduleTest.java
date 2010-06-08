/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>ContestSchedule</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestScheduleTest extends TestCase {

    /**
     * <p>
     * Represents the <code>ContestSchedule</code> instance for test.
     * </p>
     */
    private ContestSchedule schedule;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        schedule = new ContestSchedule();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestSchedule</code>, expects the instance is created properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("Failed to create the ContestSchedule instance.", schedule);
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestId</code> method, expects the contestId is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestIdAccuracy() throws Exception {
        assertEquals("Expects the contestId is 0 by default.", 0, schedule.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the <code>setContestId</code> method, expects the contestId is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetContestIdAccuracy() throws Exception {
        schedule.setContestId(1);
        assertEquals("Expects the contestId is set properly.", 1, schedule.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the <code>isStudio</code> method, expects the studio is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testIsStudioAccuracy() throws Exception {
        assertFalse("Expects the studio is false by default.", schedule.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>setStudio</code> method, expects the studio is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetStudioAccuracy() throws Exception {
        schedule.setStudio(true);
        assertTrue("Expects the studio is set properly.", schedule.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>getRegistrationDeadline</code> method, expects the registrationDeadline is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegistrationDeadlineAccuracy() throws Exception {
        assertNull("Expects the registrationDeadline is null by default.", schedule.getRegistrationDeadline());
    }

    /**
     * <p>
     * Accuracy test for the <code>setRegistrationDeadline</code> method, expects the registrationDeadline is set
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetRegistrationDeadlineAccuracy() throws Exception {
        XMLGregorianCalendar registrationDeadline = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                new GregorianCalendar());
        schedule.setRegistrationDeadline(registrationDeadline);
        assertEquals("Expects the registrationDeadline is set properly.", registrationDeadline, schedule
                .getRegistrationDeadline());
    }

    /**
     * <p>
     * Accuracy test for the <code>getRegistrationDuration</code> method, expects the registrationDuration is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegistrationDurationAccuracy() throws Exception {
        assertEquals("Expects the registrationDuration is 0 by default.", 0, schedule.getRegistrationDuration());
    }

    /**
     * <p>
     * Accuracy test for the <code>setRegistrationDuration</code> method, expects the registrationDuration is set
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetRegistrationDurationAccuracy() throws Exception {
        schedule.setRegistrationDuration(1);
        assertEquals("Expects the registrationDuration is set properly.", 1, schedule.getRegistrationDuration());
    }

    /**
     * <p>
     * Accuracy test for the <code>getMilestoneDeadline</code> method, expects the milestoneDeadline is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetMilestoneDeadlineAccuracy() throws Exception {
        assertNull("Expects the milestoneDeadline is null by default.", schedule.getMilestoneDeadline());
    }

    /**
     * <p>
     * Accuracy test for the <code>setMilestoneDeadline</code> method, expects the milestoneDeadline is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetMilestoneDeadlineAccuracy() throws Exception {
        XMLGregorianCalendar milestoneDeadline = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                new GregorianCalendar());
        schedule.setMilestoneDeadline(milestoneDeadline);
        assertEquals("Expects the milestoneDeadline is set properly.", milestoneDeadline, schedule
                .getMilestoneDeadline());
    }

    /**
     * <p>
     * Accuracy test for the <code>getMilestoneDuration</code> method, expects the milestoneDuration is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetMilestoneDurationAccuracy() throws Exception {
        assertNull("Expects the milestoneDuration is null by default.", schedule.getMilestoneDuration());
    }

    /**
     * <p>
     * Accuracy test for the <code>setMilestoneDuration</code> method, expects the milestoneDuration is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetMilestoneDurationAccuracy() throws Exception {
        schedule.setMilestoneDuration(1);
        assertEquals("Expects the milestoneDuration is set properly.", 1, schedule.getMilestoneDuration().intValue());
    }

    /**
     * <p>
     * Accuracy test for the <code>getSubmissionDeadline</code> method, expects the submissionDeadline is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSubmissionDeadlineAccuracy() throws Exception {
        assertNull("Expects the submissionDeadline is null by default.", schedule.getSubmissionDeadline());
    }

    /**
     * <p>
     * Accuracy test for the <code>setSubmissionDeadline</code> method, expects the submissionDeadline is set
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetSubmissionDeadlineAccuracy() throws Exception {
        XMLGregorianCalendar submissionDeadline = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                new GregorianCalendar());
        schedule.setSubmissionDeadline(submissionDeadline);
        assertEquals("Expects the submissionDeadline is set properly.", submissionDeadline, schedule
                .getSubmissionDeadline());
    }

    /**
     * <p>
     * Accuracy test for the <code>getSubmissionDuration</code> method, expects the submissionDuration is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSubmissionDurationAccuracy() throws Exception {
        assertEquals("Expects the submissionDuration is 0 by default.", 0, schedule.getSubmissionDuration());
    }

    /**
     * <p>
     * Accuracy test for the <code>setSubmissionDuration</code> method, expects the submissionDuration is set
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetSubmissionDurationAccuracy() throws Exception {
        schedule.setSubmissionDuration(1);
        assertEquals("Expects the submissionDuration is set properly.", 1, schedule.getSubmissionDuration());
    }
}
