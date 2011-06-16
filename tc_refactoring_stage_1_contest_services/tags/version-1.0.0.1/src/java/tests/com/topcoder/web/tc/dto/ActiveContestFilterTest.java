/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>ActiveContestFilter</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActiveContestFilterTest {
    /**
     * <p>
     * Represents the <code>ActiveContestFilter</code> instance used to test against.
     * </p>
     */
    private ActiveContestFilter impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ActiveContestFilterTest.class);
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
     * Inheritance test, verifies <code>ActiveContestFilter</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BasePrizeFilter);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ActiveContestFilter()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

}
