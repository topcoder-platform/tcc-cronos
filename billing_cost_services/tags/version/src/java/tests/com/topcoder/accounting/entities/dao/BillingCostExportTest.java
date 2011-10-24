/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>BillingCostExport</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BillingCostExportTest {
    /**
     * <p>
     * Represents the <code>BillingCostExport</code> instance used to test against.
     * </p>
     */
    private BillingCostExport impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BillingCostExportTest.class);
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
        impl = new BillingCostExport();
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
     * Inheritance test, verifies <code>BillingCostExport</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof IdentifiableEntity);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BillingCostExport()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAccountantName()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetAccountantName() {
        assertNull("The initial value should be null", impl.getAccountantName());
        String expect = "test";
        impl.setAccountantName(expect);
        assertEquals("The id should be same as", expect, impl.getAccountantName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAccountantName(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetAccountantName() {
        assertNull("The initial value should be null", impl.getAccountantName());
        String expect = "test";
        impl.setAccountantName(expect);
        assertEquals("The id should be same as", expect, impl.getAccountantName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPaymentArea()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPaymentArea() {
        assertNull("The initial value should be null", impl.getPaymentArea());
        PaymentArea expect = new PaymentArea();
        impl.setPaymentArea(expect);
        assertEquals("The id should be same as", expect, impl.getPaymentArea());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPaymentArea(PaymentArea)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPaymentArea() {
        assertNull("The initial value should be null", impl.getPaymentArea());
        PaymentArea expect = new PaymentArea();
        impl.setPaymentArea(expect);
        assertEquals("The id should be same as", expect, impl.getPaymentArea());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getRecordsCount()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetRecordsCount() {
        assertEquals("The initial id should be same as", 0, impl.getRecordsCount());
        int expect = 7;
        impl.setRecordsCount(expect);
        assertEquals("The id should be same as", expect, impl.getRecordsCount());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRecordsCount(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetRecordsCount() {
        assertEquals("The initial id should be same as", 0, impl.getRecordsCount());
        int expect = 7;
        impl.setRecordsCount(expect);
        assertEquals("The id should be same as", expect, impl.getRecordsCount());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTimestamp()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetTimestamp() {
        assertNull("The initial value should be null", impl.getTimestamp());
        Date expect = new Date();
        impl.setTimestamp(expect);
        assertEquals("The id should be same as", expect, impl.getTimestamp());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTimestamp(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetTimestamp() {
        assertNull("The initial value should be null", impl.getTimestamp());
        Date expect = new Date();
        impl.setTimestamp(expect);
        assertEquals("The id should be same as", expect, impl.getTimestamp());
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
        String expect = "{\"recordsCount\":0,\"timestamp\":null,\"paymentArea\":null,\"id\":4,"
            + "\"accountantName\":null}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());

        impl.setAccountantName("accName");
        PaymentArea paymentArea = new PaymentArea();
        paymentArea.setId(6);
        paymentArea.setName("park");
        impl.setPaymentArea(paymentArea);
        impl.setRecordsCount(7);
        expect = "{\"recordsCount\":7,\"timestamp\":null,\"paymentArea\":"
            + "{\"name\":\"park\",\"id\":6},\"id\":4,\"accountantName\":\"accName\"}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());
    }
}
