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
 * Unit tests for class <code>Upload</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UploadTest {
    /**
     * <p>
     * Represents the <code>Upload</code> instance used to test against.
     * </p>
     */
    private Upload impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UploadTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new Upload();
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
     * Inheritance test, verifies <code>Upload</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>Upload()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUploadId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetUploadId() {
        assertEquals("The initial value should be 0", 0, impl.getUploadId());

        long expect = 56;

        impl.setUploadId(expect);

        assertEquals("The return value should be same as ", expect, impl.getUploadId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUploadId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetUploadId() {
        assertEquals("The initial value should be 0", 0, impl.getUploadId());

        long expect = 56;

        impl.setUploadId(expect);

        assertEquals("The return value should be same as ", expect, impl.getUploadId());
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
}
