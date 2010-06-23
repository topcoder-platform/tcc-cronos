package com.topcoder.service.facade.direct.accuracytests;

/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

import com.topcoder.service.facade.direct.ContestSchedule;

/**
 * This class contains unit tests for <code>ContestSchedule</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestContestSchedule extends TestCase {
    /**
     * <p>
     * Represents ContestSchedule instance to test against.
     * </p>
     */
    private ContestSchedule instance = null;

    /**
     * Set Up the test environment before testing.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ContestSchedule();
    }

    /**
     * Clean up the test environment after testing.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#ContestSchedule()}. It verifies the new instance is created.
     * </p>
     */
    public void testContestSchedule() {
        assertNotNull("Unable to instantiate ContestSchedule", instance);
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#getContestId()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetContestId() {
        assertEquals("Incorrect default value for contestId", 0, instance.getContestId());

        // set a value
        instance.setContestId(8L);
        assertEquals("Incorrect contestId", 8L, instance.getContestId());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#setContestId(long)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestId() {
        // set a value
        instance.setContestId(8L);
        assertEquals("Incorrect contestId", 8L, instance.getContestId());

    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#isStudio()}. It verifies the returned value is correct.
     * </p>
     */
    public void testisStudio() {
        assertFalse("Incorrect default value for studio", instance.isStudio());

        // set a value
        instance.setStudio(true);
        assertEquals("Incorrect studio", true, instance.isStudio());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#setStudio(boolean)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetStudio() {
        // set a value
        instance.setStudio(true);
        assertEquals("Incorrect studio", true, instance.isStudio());

    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#getRegistrationDeadline()}. It verifies the returned value is
     * correct.
     * </p>
     */
    public void testGetRegistrationDeadline() {
        assertNull("Incorrect default value for registrationDeadline", instance.getRegistrationDeadline());

        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setRegistrationDeadline(cal);
        assertEquals("Incorrect registrationDeadline", cal, instance.getRegistrationDeadline());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#setRegistrationDeadline(XMLGregorianCalendar)}. It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testSetRegistrationDeadline() {
        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setRegistrationDeadline(cal);
        assertEquals("Incorrect registrationDeadline", cal, instance.getRegistrationDeadline());

        // set to null
        instance.setRegistrationDeadline(null);
        assertNull("Incorrect registrationDeadline after set to null", instance.getRegistrationDeadline());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#getRegistrationDuration()}. It verifies the returned value is
     * correct.
     * </p>
     */
    public void testGetRegistrationDuration() {
        assertEquals("Incorrect default value for registrationDuration", 0, instance.getRegistrationDuration());

        // set a value
        instance.setRegistrationDuration(888888);
        assertEquals("Incorrect registrationDuration", 888888, instance.getRegistrationDuration());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#setRegistrationDuration(int)}. It verifies the assigned value
     * is correct.
     * </p>
     */
    public void testSetRegistrationDuration() {
        // set a value
        instance.setRegistrationDuration(888888);
        assertEquals("Incorrect registrationDuration", 888888, instance.getRegistrationDuration());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#getMilestoneDeadline()}. It verifies the returned value is
     * correct.
     * </p>
     */
    public void testGetMilestoneDeadline() {
        assertNull("Incorrect default value for milestoneDeadline", instance.getMilestoneDeadline());

        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setMilestoneDeadline(cal);
        assertEquals("Incorrect milestoneDeadline", cal, instance.getMilestoneDeadline());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#setMilestoneDeadline(XMLGregorianCalendar)}. It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testSetMilestoneDeadline() {
        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setMilestoneDeadline(cal);
        assertEquals("Incorrect milestoneDeadline", cal, instance.getMilestoneDeadline());

        // set to null
        instance.setMilestoneDeadline(null);
        assertNull("Incorrect milestoneDeadline after set to null", instance.getMilestoneDeadline());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#getMilestoneDuration()}. It verifies the returned value is
     * correct.
     * </p>
     */
    public void testGetMilestoneDuration() {
        assertNull("Incorrect default value for milestoneDuration", instance.getMilestoneDuration());

        // set a value
        instance.setMilestoneDuration(new Integer(666666));
        assertEquals("Incorrect milestoneDuration", new Integer(666666), instance.getMilestoneDuration());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#setMilestoneDuration(Integer)}. It verifies the assigned value
     * is correct.
     * </p>
     */
    public void testSetMilestoneDuration() {
        // set a value
        instance.setMilestoneDuration(new Integer(666666));
        assertEquals("Incorrect milestoneDuration", new Integer(666666), instance.getMilestoneDuration());

        // set to null
        instance.setMilestoneDuration(null);
        assertNull("Incorrect milestoneDuration after set to null", instance.getMilestoneDuration());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#getSubmissionDeadline()}. It verifies the returned value is
     * correct.
     * </p>
     */
    public void testGetSubmissionDeadline() {
        assertNull("Incorrect default value for submissionDeadline", instance.getSubmissionDeadline());

        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setSubmissionDeadline(cal);
        assertEquals("Incorrect submissionDeadline", cal, instance.getSubmissionDeadline());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#setSubmissionDeadline(XMLGregorianCalendar)}. It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testSetSubmissionDeadline() {
        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setSubmissionDeadline(cal);
        assertEquals("Incorrect submissionDeadline", cal, instance.getSubmissionDeadline());

        // set to null
        instance.setSubmissionDeadline(null);
        assertNull("Incorrect submissionDeadline after set to null", instance.getSubmissionDeadline());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#getSubmissionDuration()}. It verifies the returned value is
     * correct.
     * </p>
     */
    public void testGetSubmissionDuration() {
        assertEquals("Incorrect default value for submissionDuration", 0, instance.getSubmissionDuration());

        // set a value
        instance.setSubmissionDuration(888888);
        assertEquals("Incorrect submissionDuration", 888888, instance.getSubmissionDuration());
    }

    /**
     * <p>
     * Test method for {@link ContestSchedule#setSubmissionDuration(int)}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetSubmissionDuration() {
        // set a value
        instance.setSubmissionDuration(888888);
        assertEquals("Incorrect submissionDuration", 888888, instance.getSubmissionDuration());

    }

    /**
     * Converts standard java Date object into XMLGregorianCalendar instance. Returns null if parameter is
     * null.
     *
     * @param date Date object to convert
     * @return converted calendar instance
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}