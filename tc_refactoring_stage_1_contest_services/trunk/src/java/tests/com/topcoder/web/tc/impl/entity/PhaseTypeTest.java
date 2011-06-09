/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.impl.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>PhaseType</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PhaseTypeTest {
    /**
     * <p>
     * Represents the <code>PhaseType</code> instance used to test against.
     * </p>
     */
    private PhaseType impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PhaseTypeTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new PhaseType();
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
     * Inheritance test, verifies <code>PhaseType</code> subclasses should be correct.
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
     * Accuracy test for the method <code>getName()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetName() {
        assertNull("The initial value should be null ", impl.getName());

        String expect = "test";

        impl.setName(expect);

        assertEquals("The return value should be same as ", expect, impl.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setName(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetName() {
        assertNull("The initial value should be null ", impl.getName());

        String expect = "test";

        impl.setName(expect);

        assertEquals("The return value should be same as ", expect, impl.getName());
    }

}
