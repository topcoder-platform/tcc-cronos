/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.impl.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>ContestEligibility</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestEligibilityTest {
    /**
     * <p>
     * Represents the <code>ContestEligibility</code> instance used to test against.
     * </p>
     */
    private ContestEligibility impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ContestEligibilityTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ContestEligibility();
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
     * Inheritance test, verifies <code>ContestEligibility</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestEligibility()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestEligibilityId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestEligibilityId() {
        assertEquals("The initial value should be 0", 0, impl.getContestEligibilityId());

        long expect = 56;

        impl.setContestEligibilityId(expect);

        assertEquals("The return value should be same as ", expect, impl.getContestEligibilityId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestEligibilityId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetContestEligibilityId() {
        assertEquals("The initial value should be 0", 0, impl.getContestEligibilityId());

        long expect = 56;

        impl.setContestEligibilityId(expect);

        assertEquals("The return value should be same as ", expect, impl.getContestEligibilityId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestId() {
        assertEquals("The initial value should be 0", 0, impl.getContestId());

        long expect = 56;

        impl.setContestId(expect);

        assertEquals("The return value should be same as ", expect, impl.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetContestId() {
        assertEquals("The initial value should be 0", 0, impl.getContestId());

        long expect = 56;

        impl.setContestId(expect);

        assertEquals("The return value should be same as ", expect, impl.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>isStudio()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetName() {
        assertFalse("The initial value should be false ", impl.isStudio());

        impl.setStudio(true);

        assertTrue("The return value should be true ", impl.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setStudio(boolean)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetName() {
        assertFalse("The initial value should be false ", impl.isStudio());

        impl.setStudio(true);

        assertTrue("The return value should be true ", impl.isStudio());
    }

}
