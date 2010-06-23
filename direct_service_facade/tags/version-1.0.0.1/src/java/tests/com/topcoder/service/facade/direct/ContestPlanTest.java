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
 * Unit tests for the <code>ContestPlan</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestPlanTest extends TestCase {

    /**
     * <p>
     * Represents the <code>ContestPlan</code> instance for test.
     * </p>
     */
    private ContestPlan contestPlan;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        contestPlan = new ContestPlan();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestPlan</code>, expects the instance is created properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("Failed to create the ContestPlan instance.", contestPlan);
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestId</code> method, expects the contest id is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestIdAccuracy() throws Exception {
        assertEquals("Expects the contestId is 0 by default.", 0, contestPlan.getContestId());
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
        contestPlan.setContestId(1);
        assertEquals("Expects the contestId is set properly.", 1, contestPlan.getContestId());
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
        assertFalse("Expects the studio is false by default.", contestPlan.isStudio());
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
        contestPlan.setStudio(true);
        assertTrue("Expects the studio is set properly.", contestPlan.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>getName</code> method, expects the name is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetNameAccuracy() throws Exception {
        assertNull("Expects the name is null by default.", contestPlan.getName());
    }

    /**
     * <p>
     * Accuracy test for the <code>setName</code> method, expects the name is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetNameAccuracy() throws Exception {
        contestPlan.setName("name");
        assertEquals("Expects the name is set properly.", "name", contestPlan.getName());
    }

    /**
     * <p>
     * Accuracy test for the <code>getStartDate</code> method, expects the startDate is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetStartDateAccuracy() throws Exception {
        assertNull("Expects the startDate is null by default.", contestPlan.getStartDate());
    }

    /**
     * <p>
     * Accuracy test for the <code>setStartDate</code> method, expects the startDate is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetStartDateAccuracy() throws Exception {
        XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                new GregorianCalendar());
        contestPlan.setStartDate(startDate);
        assertEquals("Expects the startDate is set properly.", startDate, contestPlan.getStartDate());
    }

    /**
     * <p>
     * Accuracy test for the <code>getEndDate</code> method, expects the endDate is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetEndDateAccuracy() throws Exception {
        assertNull("Expects the endDate is null by default.", contestPlan.getEndDate());
    }

    /**
     * <p>
     * Accuracy test for the <code>setEndDate</code> method, expects the endDate is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetEndDateAccuracy() throws Exception {
        XMLGregorianCalendar endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        contestPlan.setEndDate(endDate);
        assertEquals("Expects the endDate is set properly.", endDate, contestPlan.getEndDate());
    }
}
