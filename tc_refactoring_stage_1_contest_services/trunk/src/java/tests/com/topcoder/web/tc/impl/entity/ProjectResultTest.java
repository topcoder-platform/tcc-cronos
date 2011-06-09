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
 * Unit tests for class <code>ProjectResult</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectResultTest {
    /**
     * <p>
     * Represents the <code>ProjectResult</code> instance used to test against.
     * </p>
     */
    private ProjectResult impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectResultTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ProjectResult();
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
     * Inheritance test, verifies <code>ProjectResult</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectResult()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
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
     * Accuracy test for the method <code>getPlaced()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPlaced() {
        assertEquals("The initial value should be 0", 0, impl.getPlaced());

        long expect = 56;

        impl.setPlaced(expect);

        assertEquals("The return value should be same as ", expect, impl.getPlaced());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPlaced(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPlaced() {
        assertEquals("The initial value should be 0", 0, impl.getPlaced());

        long expect = 56;

        impl.setPlaced(expect);

        assertEquals("The return value should be same as ", expect, impl.getPlaced());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getFinalScore()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetFinalScore() {
        assertEquals("The initial value should be 0", 0, impl.getFinalScore(), 0.001);

        double expect = 56.7;

        impl.setFinalScore(expect);

        assertEquals("The return value should be same as ", expect, impl.getFinalScore(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFinalScore(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetFinalScore() {
        assertEquals("The initial value should be 0", 0, impl.getFinalScore(), 0.001);

        double expect = 56.7;

        impl.setFinalScore(expect);

        assertEquals("The return value should be same as ", expect, impl.getFinalScore(), 0.001);
    }
}
