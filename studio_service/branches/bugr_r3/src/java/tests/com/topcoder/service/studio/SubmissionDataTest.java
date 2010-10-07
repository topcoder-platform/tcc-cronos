/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Some tests for SubmissionData class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SubmissionDataTest extends TestCase {
    /**
     * Bean to test.
     */
    private SubmissionData target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(SubmissionDataTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new SubmissionData();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests setter/getter for submissionId field.
     */
    public void testSubmissionId() {
        assertEquals("default value", -1, target.getSubmissionId());
        target.setSubmissionId(35);
        assertEquals("new value", 35, target.getSubmissionId());
    }

    /**
     * Tests setter/getter for submitterId field.
     */
    public void testSubmitterId() {
        assertEquals("default value", -1, target.getSubmitterId());
        target.setSubmitterId(35);
        assertEquals("new value", 35, target.getSubmitterId());
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
     * Tests setter/getter for placement field.
     */
    public void testPlacement() {
        assertEquals("default value", -1, target.getPlacement());
        target.setPlacement(35);
        assertEquals("new value", 35, target.getPlacement());
    }

    /**
     * Tests setter/getter for price field.
     */
    public void testPrice() {
        assertEquals("default value", -1.0, target.getPrice());
        target.setPrice(35);
        assertEquals("new value", 35.0, target.getPrice());
    }

    /**
     * Tests setter/getter for submissionContent field.
     */
    public void testSubmissionContent() {
        assertNull("default value", target.getSubmissionContent());
        target.setSubmissionContent("abc");
        assertEquals("new value", "abc", target.getSubmissionContent());
    }

    /**
     * Tests setter/getter for passedScreening field.
     */
    public void testPassedScreening() {
        assertFalse("default value", target.isPassedScreening());
        target.setPassedScreening(true);
        assertTrue("new value", target.isPassedScreening());
    }

    /**
     * Tests setter/getter for paidFor field.
     */
    public void testPaidFor() {
        assertFalse("default value", target.isPaidFor());
        target.setPaidFor(true);
        assertTrue("new value", target.isPaidFor());
    }

    /**
     * Tests setter/getter for markedForPurchase field.
     */
    public void testMarkedForPurchase() {
        assertFalse("default value", target.isMarkedForPurchase());
        target.setMarkedForPurchase(true);
        assertTrue("new value", target.isMarkedForPurchase());
    }

    /**
     * Tests setter/getter for submittedDate field.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSubmittedDate() throws Exception {
        assertNull("default value", target.getSubmittedDate());
        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        target.setSubmittedDate(cal);
        assertSame("new value", cal, target.getSubmittedDate());
    }

    /**
     * Tests setter/getter for feedbackThumb field.
     *
     * @throws Exception when it occurs deeper
     */
    public void testFeedbackThumb() throws Exception {
        assertEquals("default value", 0, target.getFeedbackThumb());
        target.setFeedbackThumb(123);
        assertSame("new value", 123, target.getFeedbackThumb());
    }

    /**
     * Tests setter/getter for feedbackText field.
     *
     * @throws Exception when it occurs deeper
     */
    public void testFeedbackText() throws Exception {
        assertNull("default value", target.getFeedbackText());
        target.setFeedbackText("aa");
        assertSame("new value", "aa", target.getFeedbackText());
    }

    /**
     * Tests setter/getter for submissionUrl field.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSubmissionUrl() throws Exception {
        assertNull("default value", target.getSubmissionUrl());
        target.setSubmissionUrl("aa");
        assertSame("new value", "aa", target.getSubmissionUrl());
    }

    /**
     * Tests setter/getter for ArtifactCount field.
     *
     * @throws Exception when it occurs deeper
     */
    public void testArtifactCount() throws Exception {
        assertEquals("default value", 0, target.getArtifactCount());
        target.setArtifactCount(123);
        assertSame("new value", 123, target.getArtifactCount());
    }

    /**
     * Tests setter/getter for userRank field.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUserRank() throws Exception {
        assertEquals("default value", 0, target.getUserRank());
        target.setUserRank(123);
        assertSame("new value", 123, target.getUserRank());
    }

    /**
     * Tests setter/getter for submissionType field.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSubmissionType() throws Exception {
        assertNull("default value", target.getSubmissionType());
        target.setSubmissionType("aa");
        assertSame("new value", "aa", target.getSubmissionType());
    }
}
