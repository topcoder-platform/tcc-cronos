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
 * Unit tests for class <code>ContestStatusFilter</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestStatusFilterTest {
    /**
     * <p>
     * Represents the <code>ContestStatusFilter</code> instance used to test against.
     * </p>
     */
    private ContestStatusFilter impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ContestStatusFilterTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ContestStatusFilter();
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
     * Inheritance test, verifies <code>ContestStatusFilter</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BasePrizeFilter);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestStatusFilter()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getWinnerHandle()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetWinnerHandle() {
        assertNull("The initial value should be null ", impl.getWinnerHandle());

        String expect = "test";

        impl.setWinnerHandle(expect);

        assertEquals("The return value should be same as ", expect, impl.getWinnerHandle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setWinnerHandle(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetWinnerHandle() {
        assertNull("The initial value should be null ", impl.getWinnerHandle());

        String expect = "test";

        impl.setWinnerHandle(expect);

        assertEquals("The return value should be same as ", expect, impl.getWinnerHandle());
    }
}
