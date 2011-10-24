/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>PaymentArea</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class PaymentAreaTest {
    /**
     * <p>
     * Represents the <code>PaymentArea</code> instance used to test against.
     * </p>
     */
    private PaymentArea impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PaymentAreaTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        impl = new PaymentArea();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        impl = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>PaymentArea</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof IdentifiableEntity);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>PaymentArea()</code>.<br>
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
        assertNull("The initial value should be null", impl.getName());
        String expect = "test";
        impl.setName(expect);
        assertEquals("The id should be same as", expect, impl.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setName(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetName() {
        assertNull("The initial value should be null", impl.getName());
        String expect = "test";
        impl.setName(expect);
        assertEquals("The id should be same as", expect, impl.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>toJSONString()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testToJSONString() {
        impl.setId(4);
        String expect = "{\"name\":null,\"id\":4}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());

        impl.setName("aaa");
        expect = "{\"name\":\"aaa\",\"id\":4}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());
    }

}
