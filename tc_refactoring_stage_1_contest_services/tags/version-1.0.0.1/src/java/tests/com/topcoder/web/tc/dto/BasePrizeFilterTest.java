/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>BasePrizeFilter</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BasePrizeFilterTest {
    /**
     * <p>
     * Represents the <code>BasePrizeFilter</code> instance used to test against.
     * </p>
     */
    private BasePrizeFilter impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BasePrizeFilterTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ActiveContestFilter();
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
     * Inheritance test, verifies <code>BasePrizeFilter</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseFilter);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BasePrizeFilter()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPrizeLowerBound()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPrizeLowerBound() {
        assertNull("The initial value should be null", impl.getPrizeLowerBound());

        Integer expect = new Integer(56);

        impl.setPrizeLowerBound(expect);

        assertEquals("The return value should be same as ", expect, impl.getPrizeLowerBound());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrizeLowerBound(Integer)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPrizeLowerBound() {
        assertNull("The initial value should be null", impl.getPrizeLowerBound());

        Integer expect = new Integer(56);

        impl.setPrizeLowerBound(expect);

        assertEquals("The return value should be same as ", expect, impl.getPrizeLowerBound());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPrizeUpperBound()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPrizeUpperBound() {
        assertNull("The initial value should be null", impl.getPrizeUpperBound());

        Integer expect = new Integer(56);

        impl.setPrizeUpperBound(expect);

        assertEquals("The return value should be same as ", expect, impl.getPrizeUpperBound());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrizeUpperBound(Integer)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPrizeUpperBound() {
        assertNull("The initial value should be null", impl.getPrizeUpperBound());

        Integer expect = new Integer(56);

        impl.setPrizeUpperBound(expect);

        assertEquals("The return value should be same as ", expect, impl.getPrizeUpperBound());
    }
}
