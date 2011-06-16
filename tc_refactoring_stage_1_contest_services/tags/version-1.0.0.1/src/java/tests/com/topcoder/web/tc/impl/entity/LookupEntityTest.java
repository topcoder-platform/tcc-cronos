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
 * Unit tests for class <code>LookupEntity</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LookupEntityTest {
    /**
     * <p>
     * Represents the <code>LookupEntity</code> instance used to test against.
     * </p>
     */
    private LookupEntity impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(LookupEntityTest.class);
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
     * Inheritance test, verifies <code>LookupEntity</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>LookupEntity()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
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
