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
 * Unit tests for class <code>ResourceSubmission</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceSubmissionTest {
    /**
     * <p>
     * Represents the <code>ResourceSubmission</code> instance used to test against.
     * </p>
     */
    private ResourceSubmission impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ResourceSubmissionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ResourceSubmission();
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
     * Inheritance test, verifies <code>ResourceSubmission</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ResourceSubmission()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubmissionId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSubmissionId() {
        assertEquals("The initial value should be 0", 0, impl.getSubmissionId());

        long expect = 56;

        impl.setSubmissionId(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSubmissionId() {
        assertEquals("The initial value should be 0", 0, impl.getSubmissionId());

        long expect = 56;

        impl.setSubmissionId(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionId());
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
}
