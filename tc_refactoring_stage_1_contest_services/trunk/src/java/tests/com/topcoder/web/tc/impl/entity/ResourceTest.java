/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.impl.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>Resource</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceTest {
    /**
     * <p>
     * Represents the <code>Resource</code> instance used to test against.
     * </p>
     */
    private Resource impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ResourceTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new Resource();
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
     * Inheritance test, verifies <code>Resource</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>Resource()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getResourceId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetResourceId() {
        assertEquals("The initial value should be 0", 0, impl.getResourceId());

        long expect = 56;

        impl.setResourceId(expect);

        assertEquals("The return value should be same as ", expect, impl.getResourceId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setResourceId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetResourceId() {
        assertEquals("The initial value should be 0", 0, impl.getResourceId());

        long expect = 56;

        impl.setResourceId(expect);

        assertEquals("The return value should be same as ", expect, impl.getResourceId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetProjectId() {
        assertEquals("The initial value should be 0", 0, impl.getProjectId());

        long expect = 56;

        impl.setProjectId(expect);

        assertEquals("The return value should be same as ", expect, impl.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetProjectId() {
        assertEquals("The initial value should be 0", 0, impl.getProjectId());

        long expect = 56;

        impl.setProjectId(expect);

        assertEquals("The return value should be same as ", expect, impl.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectPhaseId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetProjectPhaseId() {
        assertEquals("The initial value should be 0", 0, impl.getProjectPhaseId());

        long expect = 56;

        impl.setProjectPhaseId(expect);

        assertEquals("The return value should be same as ", expect, impl.getProjectPhaseId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectPhaseId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetProjectPhaseId() {
        assertEquals("The initial value should be 0", 0, impl.getProjectPhaseId());

        long expect = 56;

        impl.setProjectPhaseId(expect);

        assertEquals("The return value should be same as ", expect, impl.getProjectPhaseId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getResourceRoleId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetResourceRoleId() {
        assertEquals("The initial value should be 0", 0, impl.getResourceRoleId());

        long expect = 56;

        impl.setResourceRoleId(expect);

        assertEquals("The return value should be same as ", expect, impl.getResourceRoleId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setResourceRoleId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetResourceRoleId() {
        assertEquals("The initial value should be 0", 0, impl.getResourceRoleId());

        long expect = 56;

        impl.setResourceRoleId(expect);

        assertEquals("The return value should be same as ", expect, impl.getResourceRoleId());
    }
}
