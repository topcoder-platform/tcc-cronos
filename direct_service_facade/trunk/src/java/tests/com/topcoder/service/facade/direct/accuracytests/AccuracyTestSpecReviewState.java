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

import com.topcoder.service.facade.direct.SpecReviewState;
import com.topcoder.service.facade.direct.SpecReviewStatus;
/**
 * This class contains unit tests for <code>SpecReviewState</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestSpecReviewState extends TestCase {
    /**
     * <p>
     * Represents SpecReviewState instance to test against.
     * </p>
     */
    private SpecReviewState instance = null;

    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new SpecReviewState();
    }

    /**
     * Clean up the test environment after testing.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Test method for {@link SpecReviewState#SpecReviewState()}. It verifies the new instance is created.
     * </p>
     */
    public void testSpecReviewState() {
        assertNotNull("Unable to instantiate SpecReviewState", instance);
    }
    /**
     * <p>
     * Test method for {@link SpecReviewState#getDeadline()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetDeadline() {
        assertNull("Incorrect default value for deadline", instance.getDeadline());

        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setDeadline(cal);
        assertEquals("Incorrect deadline", cal, instance.getDeadline());
    }

    /**
     * <p>
     * Test method for {@link SpecReviewState#setDeadline(XMLGregorianCalendar)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetDeadline() {
        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setDeadline(cal);
        assertEquals("Incorrect deadline", cal, instance.getDeadline());

        // set to null
        instance.setDeadline(null);
        assertNull("Incorrect deadline after set to null", instance.getDeadline());
    }
    /**
     * <p>
     * Test method for {@link SpecReviewState#isDelayed()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetDelayed() {
        assertFalse("Incorrect default value for delayed", instance.isDelayed());

        // set a value
        instance.setDelayed(true);
        assertEquals("Incorrect delayed", true, instance.isDelayed());
    }

    /**
     * <p>
     * Test method for {@link SpecReviewState#setDelayed(boolean)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetDelayed() {
        // set a value
        instance.setDelayed(true);
        assertEquals("Incorrect delayed", true, instance.isDelayed());

    }
    /**
     * <p>
     * Test method for {@link SpecReviewState#getDelayedHour()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetDelayedHour() {
        assertEquals("Incorrect default value for delayedHour", 0, instance.getDelayedHour());

        // set a value
        instance.setDelayedHour(888888);
        assertEquals("Incorrect delayedHour", 888888, instance.getDelayedHour());
    }

    /**
     * <p>
     * Test method for {@link SpecReviewState#setDelayedHour(int)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetDelayedHour() {
        // set a value
        instance.setDelayedHour(888888);
        assertEquals("Incorrect delayedHour", 888888, instance.getDelayedHour());

    }
    /**
     * <p>
     * Test method for {@link SpecReviewState#getStatus()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetStatus() {
        assertNull("Incorrect default value for status", instance.getStatus());

        // set a value
        instance.setStatus(SpecReviewStatus.FINAL_FIX_REJECTED);
        assertEquals("Incorrect status", SpecReviewStatus.FINAL_FIX_REJECTED, instance.getStatus());
    }

    /**
     * <p>
     * Test method for {@link SpecReviewState#setStatus(SpecReviewStatus)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetStatus() {
        // set a value
        instance.setStatus(SpecReviewStatus.FINAL_FIX_REJECTED);
        assertEquals("Incorrect status", SpecReviewStatus.FINAL_FIX_REJECTED, instance.getStatus());

        // set to null
        instance.setStatus(null);
        assertNull("Incorrect status after set to null", instance.getStatus());
    }

    /**
     * Converts standard java Date object into XMLGregorianCalendar instance.
     * Returns null if parameter is null.
     *
     * @param date
     *            Date object to convert
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