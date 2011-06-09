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
 * Unit tests for class <code>DateIntervalSpecification</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DateIntervalSpecificationTest {
    /**
     * <p>
     * Represents the <code>DateIntervalSpecification</code> instance used to test against.
     * </p>
     */
    private DateIntervalSpecification impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DateIntervalSpecificationTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new DateIntervalSpecification();
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
     * Inheritance test, verifies <code>DateIntervalSpecification</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Object);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DateIntervalSpecification()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getIntervalType()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetIntervalType() {
        assertNull("The initial value should be null ", impl.getIntervalType());

        DateIntervalType expect = DateIntervalType.BEFORE;

        impl.setIntervalType(expect);

        assertEquals("The return value should be same as ", expect, impl.getIntervalType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setIntervalType(DateIntervalType)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetIntervalType() {
        assertNull("The initial value should be null ", impl.getIntervalType());

        DateIntervalType expect = DateIntervalType.BEFORE;

        impl.setIntervalType(expect);

        assertEquals("The return value should be same as ", expect, impl.getIntervalType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getFirstDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetFirstDate() {
        assertNull("The initial value should be null ", impl.getFirstDate());

        Date expect = new Date();

        impl.setFirstDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getFirstDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFirstDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetFirstDate() {
        assertNull("The initial value should be null ", impl.getFirstDate());

        Date expect = new Date();

        impl.setFirstDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getFirstDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSecondDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSecondDate() {
        assertNull("The initial value should be null ", impl.getSecondDate());

        Date expect = new Date();

        impl.setSecondDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getSecondDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSecondDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSecondDate() {
        assertNull("The initial value should be null ", impl.getSecondDate());

        Date expect = new Date();

        impl.setSecondDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getSecondDate());
    }
}
