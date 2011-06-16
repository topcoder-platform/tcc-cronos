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
 * Unit tests for class <code>PastContestDTO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PastContestDTOTest {
    /**
     * <p>
     * Represents the <code>PastContestDTO</code> instance used to test against.
     * </p>
     */
    private PastContestDTO impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PastContestDTOTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new PastContestDTO();
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
     * Inheritance test, verifies <code>PastContestDTO</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseContestDTO);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>PastContestDTO()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCompletionDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCompletionDate() {
        assertNull("The initial value should be null ", impl.getCompletionDate());

        Date expect = new Date();

        impl.setCompletionDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getCompletionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCompletionDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCompletionDate() {
        assertNull("The initial value should be null ", impl.getCompletionDate());

        Date expect = new Date();

        impl.setCompletionDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getCompletionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPassedScreeningCount()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPassedScreeningCount() {
        assertEquals("The initial value should be 0", 0, impl.getPassedScreeningCount());

        int expect = 56;

        impl.setPassedScreeningCount(expect);

        assertEquals("The return value should be same as ", expect, impl.getPassedScreeningCount());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPassedScreeningCount(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPassedScreeningCount() {
        assertEquals("The initial value should be 0", 0, impl.getPassedScreeningCount());

        int expect = 56;

        impl.setPassedScreeningCount(expect);

        assertEquals("The return value should be same as ", expect, impl.getPassedScreeningCount());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getWinnerProfileLink()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetWinnerProfileLink() {
        assertNull("The initial value should be null ", impl.getWinnerProfileLink());

        String expect = "test";

        impl.setWinnerProfileLink(expect);

        assertEquals("The return value should be same as ", expect, impl.getWinnerProfileLink());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setWinnerProfileLink(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetWinnerProfileLink() {
        assertNull("The initial value should be null ", impl.getWinnerProfileLink());

        String expect = "test";

        impl.setWinnerProfileLink(expect);

        assertEquals("The return value should be same as ", expect, impl.getWinnerProfileLink());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getWinnerScore()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetWinnerScore() {
        assertEquals("The initial value should be 0", 0, impl.getWinnerScore(), 0.001);

        double expect = 56.7;

        impl.setWinnerScore(expect);

        assertEquals("The return value should be same as ", expect, impl.getWinnerScore(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setWinnerScore(double)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetWinnerScore() {
        assertEquals("The initial value should be 0", 0, impl.getWinnerScore(), 0.001);

        double expect = 56.7;

        impl.setWinnerScore(expect);

        assertEquals("The return value should be same as ", expect, impl.getWinnerScore(), 0.001);
    }
}
