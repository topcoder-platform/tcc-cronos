/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.JsonPrintable;

/**
 * <p>
 * Unit tests for class <code>IdentifiableEntity</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class IdentifiableEntityTest {
    /**
     * <p>
     * Represents the <code>IdentifiableEntity</code> instance used to test against.
     * </p>
     */
    private IdentifiableEntity impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(IdentifiableEntityTest.class);
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
     * Inheritance test, verifies <code>IdentifiableEntity</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Serializable);
        assertTrue("The instance's subclass is not correct.", impl instanceof JsonPrintable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>IdentifiableEntity()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetId() {
        assertEquals("The initial id should be same as", 0, impl.getId());
        long expect = 7;
        impl.setId(expect);
        assertEquals("The id should be same as", expect, impl.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetId() {
        assertEquals("The initial id should be same as", 0, impl.getId());
        long expect = 7;
        impl.setId(expect);
        assertEquals("The id should be same as", expect, impl.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>equals(Object)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testEquals() {
        impl.setId(9);
        PaymentArea obj = new PaymentArea();
        obj.setId(9);
        obj.setName("name");
        assertTrue("The two object should be same", impl.equals(obj));

        obj.setId(8);
        assertFalse("The two object should be different", impl.equals(obj));

        assertFalse("The two object should be different", impl.equals(new PaymentArea()));

        BillingCostExport obj2 = new BillingCostExport();
        obj2.setId(9);
        assertFalse("The two object should be different", impl.equals(obj2));

        assertFalse("The two object should be different", impl.equals(null));

        assertFalse("The two object should be different", impl.equals(new Object()));
    }

    /**
     * <p>
     * Accuracy test for the method <code>hashCode()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testHashCode() {
        assertEquals("The hash code should be same as", 0, impl.hashCode());
        impl.setId(6);
        assertEquals("The hash code should be same as", 6, impl.hashCode());
    }
}
