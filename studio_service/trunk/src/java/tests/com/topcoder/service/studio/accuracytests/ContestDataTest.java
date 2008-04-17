/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

import com.topcoder.service.studio.ContestData;

/**
 * <p>
 * Accuracy tests for ContestData class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestDataTest extends TestCase {
    /**
     * <p>
     * Represents the data to test.
     * </p>
     */
    private ContestData data;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        data = new ContestData();
    }

    /**
     * <p>
     * Tests setter/getter for contestId field.
     * </p>
     */
    public void testContestId() {
        data.setContestId(35);
        assertEquals("The contest id is wrong.", 35, data.getContestId());
    }

    /**
     * <p>
     * Tests setter/getter for projectId field.
     * </p>
     */
    public void testProjectId() {
        data.setProjectId(35);
        assertEquals("The project id is wrong.", 35, data.getProjectId());
    }

    /**
     * <p>
     * Tests setter/getter for tcDirectProjectId field.
     * </p>
     */
    public void testTcDirectProjectId() {
        data.setTcDirectProjectId(35);
        assertEquals("The project id is wrong.", 35, data.getTcDirectProjectId());
    }

    /**
     * <p>
     * Tests setter/getter for durationInHours field.
     * </p>
     */
    public void testDurationInHours() {
        data.setDurationInHours(35);
        assertEquals("The duration in hours is wrong.", 35, data.getDurationInHours());
    }

    /**
     * <p>
     * Tests setter/getter for contestCategoryId field.
     * </p>
     */
    public void testContestCategoryId() {
        data.setContestCategoryId(35);
        assertEquals("The category id is wrong.", 35, data.getContestCategoryId());
    }

    /**
     * <p>
     * Tests setter/getter for creatorUserId field.
     * </p>
     */
    public void testCreatorUserId() {
        data.setCreatorUserId(35);
        assertEquals("The creator user id is wrong.", 35, data.getCreatorUserId());
    }

    /**
     * <p>
     * Tests setter/getter for name field.
     * </p>
     */
    public void testName() {
        data.setName("test");
        assertEquals("The name is wrong.", "test", data.getName());
    }

    /**
     * <p>
     * Tests setter/getter for shortSummary field.
     * </p>
     */
    public void testShortSummary() {
        data.setShortSummary("test");
        assertEquals("The summary is wrong.", "test", data.getShortSummary());
    }

    /**
     * <p>
     * Tests setter/getter for contestDescriptionAndRequirements field.
     * </p>
     */
    public void testContestDescriptionAndRequirements() {
        data.setContestDescriptionAndRequirements("test");
        assertEquals("The description is wrong.", "test", data.getContestDescriptionAndRequirements());
    }

    /**
     * <p>
     * Tests setter/getter for requiredOrRestrictedColors field.
     * </p>
     */
    public void testRequiredOrRestrictedColors() {
        data.setRequiredOrRestrictedColors("test");
        assertEquals("The color is wrong.", "test", data.getRequiredOrRestrictedColors());
    }

    /**
     * <p>
     * Tests setter/getter for requiredOrRestrictedFonts field.
     * </p>
     */
    public void testRequiredOrRestrictedFonts() {
        data.setRequiredOrRestrictedFonts("test");
        assertEquals("The font is wrong.", "test", data.getRequiredOrRestrictedFonts());
    }

    /**
     * <p>
     * Tests setter/getter for sizeRequirements field.
     * </p>
     */
    public void testSizeRequirements() {
        data.setSizeRequirements("test");
        assertEquals("The requirements is wrong", "test", data.getSizeRequirements());
    }

    /**
     * <p>
     * Tests setter/getter for otherFileFormats field.
     * </p>
     */
    public void testOtherFileFormats() {
        data.setOtherFileFormats("txt");
        assertEquals("The file format is wrong.", "txt", data.getOtherFileFormats());
    }

    /**
     * <p>
     * Tests setter/getter for launchDateAndTime field.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testLaunchDateAndTime() throws Exception {
        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        data.setLaunchDateAndTime(cal);
        assertSame("The time is wrong.", cal, data.getLaunchDateAndTime());
    }

    /**
     * <p>
     * Tests setter/getter for winnerAnnoucementDeadline field.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testWinnerAnnoucementDeadline() throws Exception {
        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        data.setWinnerAnnoucementDeadline(cal);
        assertSame("The date is wrong.", cal, data.getWinnerAnnoucementDeadline());
    }

}
