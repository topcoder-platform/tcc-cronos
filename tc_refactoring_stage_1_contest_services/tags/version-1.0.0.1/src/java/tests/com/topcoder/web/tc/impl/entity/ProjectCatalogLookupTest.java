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
 * Unit tests for class <code>ProjectCatalogLookup</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectCatalogLookupTest {
    /**
     * <p>
     * Represents the <code>ProjectCatalogLookup</code> instance used to test against.
     * </p>
     */
    private ProjectCatalogLookup impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectCatalogLookupTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ProjectCatalogLookup();
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
     * Inheritance test, verifies <code>ProjectCatalogLookup</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof LookupEntity);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectCatalogLookup()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectCatalogId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetProjectCatalogId() {
        assertEquals("The initial value should be 0", 0, impl.getProjectCatalogId());

        long expect = 56;

        impl.setProjectCatalogId(expect);

        assertEquals("The return value should be same as ", expect, impl.getProjectCatalogId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectCatalogId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetProjectCatalogId() {
        assertEquals("The initial value should be 0", 0, impl.getProjectCatalogId());

        long expect = 56;

        impl.setProjectCatalogId(expect);

        assertEquals("The return value should be same as ", expect, impl.getProjectCatalogId());
    }

}
