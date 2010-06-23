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
 * Unit tests for the <code>SpecReviewState</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SpecReviewStateTest extends TestCase {

    /**
     * <p>
     * Represents the <code>SpecReviewState</code> instance for test.
     * </p>
     */
    private SpecReviewState specReviewState;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        specReviewState = new SpecReviewState();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>SpecReviewState</code>, expects the instance is created properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("Failed to create the SpecReviewState instance.", specReviewState);
    }

    /**
     * <p>
     * Accuracy test for the <code>getDeadline</code> method, expects the deadline is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDeadlineAccuracy() throws Exception {
        assertNull("Expects the deadline is null by default.", specReviewState.getDeadline());
    }

    /**
     * <p>
     * Accuracy test for the <code>setDeadline</code> method, expects the deadline is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetDeadlineAccuracy() throws Exception {
        XMLGregorianCalendar deadline = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(new GregorianCalendar());
        specReviewState.setDeadline(deadline);
        assertEquals("Expects the deadline is set properly.", deadline, specReviewState.getDeadline());
    }

    /**
     * <p>
     * Accuracy test for the <code>isDelayed</code> method, expects the delayed is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testIsDelayedAccuracy() throws Exception {
        assertFalse("Expects the delayed is false by default.", specReviewState.isDelayed());
    }

    /**
     * <p>
     * Accuracy test for the <code>setDelayed</code> method, expects the delayed is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetDelayedAccuracy() throws Exception {
        specReviewState.setDelayed(true);
        assertTrue("Expects the delayed is set properly.", specReviewState.isDelayed());
    }

    /**
     * <p>
     * Accuracy test for the <code>getDelayedHour</code> method, expects the delayedHour is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDelayedHourAccuracy() throws Exception {
        assertEquals("Expects the delayedHour is 0 by default.", 0, specReviewState.getDelayedHour());
    }

    /**
     * <p>
     * Accuracy test for the <code>setDelayedHour</code> method, expects the delayedHour is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetDelayedHourAccuracy() throws Exception {
        specReviewState.setDelayedHour(1);
        assertEquals("Expects the delayedHour is set properly.", 1, specReviewState.getDelayedHour());
    }

    /**
     * <p>
     * Accuracy test for the <code>getStatus</code> method, expects the status is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetStatusAccuracy() throws Exception {
        assertNull("Expects the status is null by default.", specReviewState.getStatus());
    }

    /**
     * <p>
     * Accuracy test for the <code>setStatus</code> method, expects the status is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetStatusAccuracy() throws Exception {
        specReviewState.setStatus(SpecReviewStatus.FINAL_FIX_ACCEPTED);
        assertEquals("Expects the status is set properly.", SpecReviewStatus.FINAL_FIX_ACCEPTED, specReviewState
                .getStatus());
    }
}
