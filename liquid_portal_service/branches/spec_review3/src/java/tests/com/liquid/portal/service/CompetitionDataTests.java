/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>CompetitionData</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompetitionDataTests extends TestCase {
    /**
     * <p>
     * Represents the instance of <code>CompetitionData</code>.
     * </p>
     */
    private CompetitionData data;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        data = new CompetitionData();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

    }

    /**
     * <p>
     * Accuracy test case for <code>getContestTypeName()</code> and
     * <code>setContestTypeName(String)</code>.
     * </p>
     */
    public void testSetGetContestTypeName() {
        data.setContestTypeName("studio");
        assertEquals("studio", data.getContestTypeName());
    }

    /**
     * <p>
     * Accuracy test case for <code>getSubContestTypeName()</code> and
     * <code>setSubContestTypeName(String)</code>.
     * </p>
     */
    public void testSetGetSubContestTypeName() {
        data.setSubContestTypeName("studio");
        assertEquals("studio", data.getSubContestTypeName());
    }

    /**
     * <p>
     * Accuracy test case for <code>getContestName()</code> and
     * <code>setContestName(String)</code>.
     * </p>
     */
    public void testSetGetContestName() {
        data.setContestName("contest");
        assertEquals("contest", data.getContestName());
    }

    /**
     * <p>
     * Accuracy test case for <code>getCockpitProjectName()</code> and
     * <code>setCockpitProjectName(String)</code>.
     * </p>
     */
    public void testSetGetCockpitProjectName() {
        data.setCockpitProjectName("cockpit");
        assertEquals("cockpit", data.getCockpitProjectName());
    }

    /**
     * <p>
     * Accuracy test case for <code>getRequestedStartDate()</code> and
     * <code>setRequestedStartDate(Date)</code>.
     * </p>
     */
    public void testSetGetRequestedStartDate() {
        Date date = new Date();
        data.setRequestedStartDate(date);
        assertEquals(date, data.getRequestedStartDate());
    }

    /**
     * <p>
     * Accuracy test case for <code>getAutoReschedule()</code> and
     * <code>setAutoReschedule(boolean)</code>.
     * </p>
     */
    public void testSetGetAutoReschedule() {
        data.setAutoReschedule(true);
        assertTrue(data.getAutoReschedule());
        data.setAutoReschedule(false);
        assertFalse(data.getAutoReschedule());
    }

    /**
     * <p>
     * Accuracy test case for <code>getBillingProjectId()</code> and
     * <code>setBillingProjectId(long)</code>.
     * </p>
     */
    public void testSetGetBillingProjectId() {
        data.setBillingProjectId(1L);
        assertEquals(1L, data.getBillingProjectId());
    }

    /**
     * <p>
     * Accuracy test case for <code>getCca()</code> and
     * <code>setCca(boolean)</code>.
     * </p>
     */
    public void testSetGetCca() {
        data.setCca(true);
        assertTrue(data.getCca());
        data.setCca(false);
        assertFalse(data.getCca());
    }
}
