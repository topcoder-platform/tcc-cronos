/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.impl.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>ProjectPhase</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectPhaseTest {
    /**
     * <p>
     * Represents the <code>ProjectPhase</code> instance used to test against.
     * </p>
     */
    private ProjectPhase impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectPhaseTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ProjectPhase();
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
     * Inheritance test, verifies <code>ProjectPhase</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>PhaseType()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
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
     * Accuracy test for the method <code>getPhaseTypeId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPhaseTypeId() {
        assertEquals("The initial value should be 0", 0, impl.getPhaseTypeId());

        long expect = 56;

        impl.setPhaseTypeId(expect);

        assertEquals("The return value should be same as ", expect, impl.getPhaseTypeId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPhaseTypeId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPhaseTypeId() {
        assertEquals("The initial value should be 0", 0, impl.getPhaseTypeId());

        long expect = 56;

        impl.setPhaseTypeId(expect);

        assertEquals("The return value should be same as ", expect, impl.getPhaseTypeId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPhaseStatusId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPhaseStatusId() {
        assertEquals("The initial value should be 0", 0, impl.getPhaseStatusId());

        long expect = 56;

        impl.setPhaseStatusId(expect);

        assertEquals("The return value should be same as ", expect, impl.getPhaseStatusId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPhaseStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPhaseStatusId() {
        assertEquals("The initial value should be 0", 0, impl.getPhaseStatusId());

        long expect = 56;

        impl.setPhaseStatusId(expect);

        assertEquals("The return value should be same as ", expect, impl.getPhaseStatusId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getScheduledStartTime()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetScheduledStartTime() {
        assertNull("The initial value should be null ", impl.getScheduledStartTime());

        Date expect = new Date();

        impl.setScheduledStartTime(expect);

        assertEquals("The return value should be same as ", expect, impl.getScheduledStartTime());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setScheduledStartTime(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetScheduledStartTime() {
        assertNull("The initial value should be null ", impl.getScheduledStartTime());

        Date expect = new Date();

        impl.setScheduledStartTime(expect);

        assertEquals("The return value should be same as ", expect, impl.getScheduledStartTime());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getScheduledEndTime()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetScheduledEndTime() {
        assertNull("The initial value should be null ", impl.getScheduledEndTime());

        Date expect = new Date();

        impl.setScheduledEndTime(expect);

        assertEquals("The return value should be same as ", expect, impl.getScheduledEndTime());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setScheduledEndTime(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetScheduledEndTime() {
        assertNull("The initial value should be null ", impl.getScheduledEndTime());

        Date expect = new Date();

        impl.setScheduledEndTime(expect);

        assertEquals("The return value should be same as ", expect, impl.getScheduledEndTime());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getActualEndTime()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetActualEndTime() {
        assertNull("The initial value should be null ", impl.getActualEndTime());

        Date expect = new Date();

        impl.setActualEndTime(expect);

        assertEquals("The return value should be same as ", expect, impl.getActualEndTime());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setActualEndTime(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetActualEndTime() {
        assertNull("The initial value should be null ", impl.getActualEndTime());

        Date expect = new Date();

        impl.setActualEndTime(expect);

        assertEquals("The return value should be same as ", expect, impl.getActualEndTime());
    }
}
