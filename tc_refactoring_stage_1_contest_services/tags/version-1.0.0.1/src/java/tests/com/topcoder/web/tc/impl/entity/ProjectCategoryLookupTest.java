/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.impl.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>ProjectCategoryLookup</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectCategoryLookupTest {
    /**
     * <p>
     * Represents the <code>ProjectCategoryLookup</code> instance used to test against.
     * </p>
     */
    private ProjectCategoryLookup impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectCategoryLookupTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ProjectCategoryLookup();
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
     * Inheritance test, verifies <code>ProjectCategoryLookup</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof LookupEntity);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectCategoryLookup()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectCategoryId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetProjectCategoryId() {
        assertEquals("The initial value should be 0", 0, impl.getProjectCategoryId());

        long expect = 56;

        impl.setProjectCategoryId(expect);

        assertEquals("The return value should be same as ", expect, impl.getProjectCategoryId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectCategoryId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetProjectCategoryId() {
        assertEquals("The initial value should be 0", 0, impl.getProjectCategoryId());

        long expect = 56;

        impl.setProjectCategoryId(expect);

        assertEquals("The return value should be same as ", expect, impl.getProjectCategoryId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectGroupCategoryId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetProjectGroupCategoryId() {
        assertEquals("The initial value should be 0", 0, impl.getProjectGroupCategoryId());

        long expect = 56;

        impl.setProjectGroupCategoryId(expect);

        assertEquals("The return value should be same as ", expect, impl.getProjectGroupCategoryId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectGroupCategoryId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetProjectGroupCategoryId() {
        assertEquals("The initial value should be 0", 0, impl.getProjectGroupCategoryId());

        long expect = 56;

        impl.setProjectGroupCategoryId(expect);

        assertEquals("The return value should be same as ", expect, impl.getProjectGroupCategoryId());
    }
}
