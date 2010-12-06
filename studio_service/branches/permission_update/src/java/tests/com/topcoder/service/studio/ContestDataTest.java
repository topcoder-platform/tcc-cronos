/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Arrays;

/**
 * Some tests for ContestData class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestDataTest extends TestCase {
    /**
     * Bean to test.
     */
    private ContestData target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestDataTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new ContestData();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests setter/getter for contestId field.
     */
    public void testContestId() {
        assertEquals("default value", -1, target.getContestId());
        target.setContestId(35);
        assertEquals("new value", 35, target.getContestId());
    }

    /**
     * Tests setter/getter for projectId field.
     */
    public void testProjectId() {
        assertEquals("default value", -1, target.getProjectId());
        target.setProjectId(35);
        assertEquals("new value", 35, target.getProjectId());
    }

    /**
     * Tests setter/getter for tcDirectProjectId field.
     */
    public void testTcDirectProjectId() {
        assertEquals("default value", -1, target.getTcDirectProjectId());
        target.setTcDirectProjectId(35);
        assertEquals("new value", 35, target.getTcDirectProjectId());
    }

    /**
     * Tests setter/getter for durationInHours field.
     */
    public void testDurationInHours() {
        assertEquals("default value", -1.0, target.getDurationInHours());
        target.setDurationInHours(35);
        assertEquals("new value", 35.0, target.getDurationInHours());
    }

    /**
     * Tests setter/getter for creatorUserId field.
     */
    public void testCreatorUserId() {
        assertEquals("default value", -1, target.getCreatorUserId());
        target.setCreatorUserId(35);
        assertEquals("new value", 35, target.getCreatorUserId());
    }

    /**
     * Tests setter/getter for name field.
     */
    public void testName() {
        assertNull("default value", target.getName());
        target.setName("abc");
        assertEquals("new value", "abc", target.getName());
    }

    /**
     * Tests setter/getter for shortSummary field.
     */
    public void testShortSummary() {
        assertNull("default value", target.getShortSummary());
        target.setShortSummary("abc");
        assertEquals("new value", "abc", target.getShortSummary());
    }

    /**
     * Tests setter/getter for contestDescriptionAndRequirements field.
     */
    public void testContestDescriptionAndRequirements() {
        assertNull("default value", target.getContestDescriptionAndRequirements());
        target.setContestDescriptionAndRequirements("abc");
        assertEquals("new value", "abc", target.getContestDescriptionAndRequirements());
    }

    /**
     * Tests setter/getter for requiredOrRestrictedColors field.
     */
    public void testRequiredOrRestrictedColors() {
        assertNull("default value", target.getRequiredOrRestrictedColors());
        target.setRequiredOrRestrictedColors("abc");
        assertEquals("new value", "abc", target.getRequiredOrRestrictedColors());
    }

    /**
     * Tests setter/getter for requiredOrRestrictedFonts field.
     */
    public void testRequiredOrRestrictedFonts() {
        assertNull("default value", target.getRequiredOrRestrictedFonts());
        target.setRequiredOrRestrictedFonts("abc");
        assertEquals("new value", "abc", target.getRequiredOrRestrictedFonts());
    }

    /**
     * Tests setter/getter for sizeRequirements field.
     */
    public void testSizeRequirements() {
        assertNull("default value", target.getSizeRequirements());
        target.setSizeRequirements("abc");
        assertEquals("new value", "abc", target.getSizeRequirements());
    }

    /**
     * Tests setter/getter for otherRequirementsOrRestrictions field.
     */
    public void testOtherRequirementsOrRestrictions() {
        assertNull("default value", target.getOtherRequirementsOrRestrictions());
        target.setOtherRequirementsOrRestrictions("abc");
        assertEquals("new value", "abc", target.getOtherRequirementsOrRestrictions());
    }

    /**
     * Tests setter/getter for finalFileFormat field.
     */
    public void testFinalFileFormat() {
        assertNull("default value", target.getFinalFileFormat());
        target.setFinalFileFormat("abc");
        assertEquals("new value", "abc", target.getFinalFileFormat());
    }

    /**
     * Tests setter/getter for otherFileFormats field.
     */
    public void testOtherFileFormats() {
        assertNull("default value", target.getOtherFileFormats());
        target.setOtherFileFormats("abc");
        assertEquals("new value", "abc", target.getOtherFileFormats());
    }

    /**
     * Tests setter/getter for launchDateAndTime field.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLaunchDateAndTime() throws Exception {
        assertNull("default value", target.getLaunchDateAndTime());
        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        target.setLaunchDateAndTime(cal);
        assertSame("new value", cal, target.getLaunchDateAndTime());
    }

    /**
     * Tests setter/getter for winnerAnnoucementDeadline field.
     *
     * @throws Exception when it occurs deeper
     */
    public void testWinnerAnnoucementDeadline() throws Exception {
        assertNull("default value", target.getWinnerAnnoucementDeadline());
        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        target.setWinnerAnnoucementDeadline(cal);
        assertSame("new value", cal, target.getWinnerAnnoucementDeadline());
    }

    /**
     * Tests setter/getter for documentationUploads field.
     */
    public void testDocumentationUploads() {
        assertEquals("default value", 0, target.getDocumentationUploads().size());
        UploadedDocument[] val = new UploadedDocument[] {new UploadedDocument(), new UploadedDocument()};
        target.setDocumentationUploads(Arrays.asList(val));
        assertSame("new value", val[0], target.getDocumentationUploads().get(0));
        assertSame("new value", val[1], target.getDocumentationUploads().get(1));
    }

    /**
     * Tests setDocumentationUploads method for null value. IllegalArgumentException expected.
     */
    public void testSetDocumentationUploadsForNull() {
        try {
            target.setDocumentationUploads(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests setter/getter for contestPayloads field.
     */
    public void testContestPayloads() {
        assertEquals("default value", 0, target.getContestPayloads().size());
        ContestPayload[] val = new ContestPayload[] {new ContestPayload(), new ContestPayload()};
        target.setContestPayloads(Arrays.asList(val));
        assertSame("new value", val[0], target.getContestPayloads().get(0));
        assertSame("new value", val[1], target.getContestPayloads().get(1));
    }

    /**
     * Tests setContestPayloads method for null value. IllegalArgumentException expected.
     */
    public void testSetContestPayloadsForNull() {
        try {
            target.setContestPayloads(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests setter/getter for prizes field.
     */
    public void testPrizes() {
        assertEquals("default value", 0, target.getPrizes().size());
        PrizeData[] val = new PrizeData[] {new PrizeData(), new PrizeData()};
        target.setPrizes(Arrays.asList(val));
        assertSame("new value", val[0], target.getPrizes().get(0));
        assertSame("new value", val[1], target.getPrizes().get(1));
    }

    /**
     * Tests setPrizes method for null value. IllegalArgumentException expected.
     */
    public void testSetPrizesForNull() {
        try {
            target.setPrizes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }


    /**
     * Tests setter/getter for submissionCount field.
     */
    public void testSubmissionCount() {
        assertEquals("default value", 0, target.getSubmissionCount());
        target.setSubmissionCount(33);
        assertEquals("new value", 33, target.getSubmissionCount());
    }
}
