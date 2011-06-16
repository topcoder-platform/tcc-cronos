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
 * Unit tests for class <code>Submission</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SubmissionTest {
    /**
     * <p>
     * Represents the <code>Submission</code> instance used to test against.
     * </p>
     */
    private Submission impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SubmissionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new Submission();
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
     * Inheritance test, verifies <code>Submission</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>Submission()</code>.<br>
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
     * Accuracy test for the method <code>getScreeningScore()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetScreeningScore() {
        assertEquals("The initial value should be 0", 0, impl.getScreeningScore(), 0.001);

        double expect = 56.7;

        impl.setScreeningScore(expect);

        assertEquals("The return value should be same as ", expect, impl.getScreeningScore(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setScreeningScore(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetScreeningScore() {
        assertEquals("The initial value should be 0", 0, impl.getScreeningScore(), 0.001);

        double expect = 56.7;

        impl.setScreeningScore(expect);

        assertEquals("The return value should be same as ", expect, impl.getScreeningScore(), 0.001);
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
     * Accuracy test for the method <code>getSubmissionTypeId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSubmissionTypeId() {
        assertEquals("The initial value should be 0", 0, impl.getSubmissionTypeId());

        long expect = 56;

        impl.setSubmissionTypeId(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionTypeId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionTypeId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSubmissionTypeId() {
        assertEquals("The initial value should be 0", 0, impl.getSubmissionTypeId());

        long expect = 56;

        impl.setSubmissionTypeId(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionTypeId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubmissionStatusId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSubmissionStatusId() {
        assertEquals("The initial value should be 0", 0, impl.getSubmissionStatusId());

        long expect = 56;

        impl.setSubmissionStatusId(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionStatusId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSubmissionStatusId() {
        assertEquals("The initial value should be 0", 0, impl.getSubmissionStatusId());

        long expect = 56;

        impl.setSubmissionStatusId(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionStatusId());
    }
}
