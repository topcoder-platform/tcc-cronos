/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>ActiveContestDTO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActiveContestDTOTest {
    /**
     * <p>
     * Represents the <code>ActiveContestDTO</code> instance used to test against.
     * </p>
     */
    private ActiveContestDTO impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ActiveContestDTOTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ActiveContestDTO();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        impl = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ActiveContestDTO</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseContestDTO);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ActiveContestDTO()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getRegistrationEndDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetRegistrationEndDate() {
        assertNull("The initial value should be null ", impl.getRegistrationEndDate());

        Date expect = new Date();

        impl.setRegistrationEndDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getRegistrationEndDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRegistrationEndDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetRegistrationEndDate() {
        assertNull("The initial value should be null ", impl.getRegistrationEndDate());

        Date expect = new Date();

        impl.setRegistrationEndDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getRegistrationEndDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubmissionEndDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSubmissionEndDate() {
        assertNull("The initial value should be null ", impl.getSubmissionEndDate());

        Date expect = new Date();

        impl.setSubmissionEndDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionEndDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionEndDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSubmissionEndDate() {
        assertNull("The initial value should be null ", impl.getSubmissionEndDate());

        Date expect = new Date();

        impl.setSubmissionEndDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionEndDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getFirstPrize()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetFirstPrize() {
        assertEquals("The initial value should be 0", 0, impl.getFirstPrize(), 0.001);

        double expect = 56.7;

        impl.setFirstPrize(expect);

        assertEquals("The return value should be same as ", expect, impl.getFirstPrize(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFirstPrize(double)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetFirstPrize() {
        assertEquals("The initial value should be 0", 0, impl.getFirstPrize(), 0.001);

        double expect = 56.7;

        impl.setFirstPrize(expect);

        assertEquals("The return value should be same as ", expect, impl.getFirstPrize(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getReliabilityBonus()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetReliabilityBonus() {
        assertEquals("The initial value should be 0", 0, impl.getReliabilityBonus(), 0.001);

        double expect = 56.7;

        impl.setReliabilityBonus(expect);

        assertEquals("The return value should be same as ", expect, impl.getReliabilityBonus(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setReliabilityBonus(double)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetReliabilityBonus() {
        assertEquals("The initial value should be 0", 0, impl.getReliabilityBonus(), 0.001);

        double expect = 56.7;

        impl.setReliabilityBonus(expect);

        assertEquals("The return value should be same as ", expect, impl.getReliabilityBonus(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDigitalRunPoints()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetDigitalRunPoints() {
        assertNull("The initial value should be null", impl.getDigitalRunPoints());

        Double expect = new Double(56.7);

        impl.setDigitalRunPoints(expect);

        assertEquals("The return value should be same as ", expect, impl.getDigitalRunPoints());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDigitalRunPoints(Double)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetDigitalRunPoints() {
        assertNull("The initial value should be null", impl.getDigitalRunPoints());

        Double expect = new Double(56.7);

        impl.setDigitalRunPoints(expect);

        assertEquals("The return value should be same as ", expect, impl.getDigitalRunPoints());
    }
}
