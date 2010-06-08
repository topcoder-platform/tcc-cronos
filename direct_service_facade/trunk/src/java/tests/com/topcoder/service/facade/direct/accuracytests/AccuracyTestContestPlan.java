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

import com.topcoder.service.facade.direct.ContestPlan;
/**
 * This class contains unit tests for <code>ContestPlan</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestContestPlan extends TestCase {
    /**
     * <p>
     * Represents ContestPlan instance to test against.
     * </p>
     */
    private ContestPlan instance = null;

    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ContestPlan();
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
     * Test method for {@link ContestPlan#ContestPlan()}. It verifies the new instance is created.
     * </p>
     */
    public void testContestPlan() {
        assertNotNull("Unable to instantiate ContestPlan", instance);
    }
    /**
     * <p>
     * Test method for {@link ContestPlan#getContestId()}. It verifies the returned value is correct.
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
     * Test method for {@link ContestPlan#setContestId(long)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestId() {
        // set a value
        instance.setContestId(8L);
        assertEquals("Incorrect contestId", 8L, instance.getContestId());

    }
    /**
     * <p>
     * Test method for {@link ContestPlan#isStudio()}. It verifies the returned value is correct.
     * </p>
     */
    public void testIsStudio() {
        assertFalse("Incorrect default value for studio", instance.isStudio());

        // set a value
        instance.setStudio(true);
        assertEquals("Incorrect studio", true, instance.isStudio());
    }

    /**
     * <p>
     * Test method for {@link ContestPlan#setStudio(boolean)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetStudio() {
        // set a value
        instance.setStudio(true);
        assertEquals("Incorrect studio", true, instance.isStudio());

    }
    /**
     * <p>
     * Test method for {@link ContestPlan#getName()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetName() {
        assertNull("Incorrect default value for name", instance.getName());

        // set a value
        instance.setName("myString");
        assertEquals("Incorrect name", "myString", instance.getName());
    }

    /**
     * <p>
     * Test method for {@link ContestPlan#setName(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetName() {
        // set a value
        instance.setName("myString");
        assertEquals("Incorrect name", "myString", instance.getName());

        // set to null
        instance.setName(null);
        assertNull("Incorrect name after set to null", instance.getName());
    }
    /**
     * <p>
     * Test method for {@link ContestPlan#getStartDate()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetStartDate() {
        assertNull("Incorrect default value for startDate", instance.getStartDate());

        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setStartDate(cal);
        assertEquals("Incorrect startDate", cal, instance.getStartDate());
    }

    /**
     * <p>
     * Test method for {@link ContestPlan#setStartDate(XMLGregorianCalendar)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetStartDate() {
        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setStartDate(cal);
        assertEquals("Incorrect startDate", cal, instance.getStartDate());

        // set to null
        instance.setStartDate(null);
        assertNull("Incorrect startDate after set to null", instance.getStartDate());
    }
    /**
     * <p>
     * Test method for {@link ContestPlan#getEndDate()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetEndDate() {
        assertNull("Incorrect default value for endDate", instance.getEndDate());

        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setEndDate(cal);
        assertEquals("Incorrect endDate", cal, instance.getEndDate());
    }

    /**
     * <p>
     * Test method for {@link ContestPlan#setEndDate(XMLGregorianCalendar)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetEndDate() {
        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setEndDate(cal);
        assertEquals("Incorrect endDate", cal, instance.getEndDate());

        // set to null
        instance.setEndDate(null);
        assertNull("Incorrect endDate after set to null", instance.getEndDate());
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