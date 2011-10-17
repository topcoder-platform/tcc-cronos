/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dto;

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

import com.topcoder.accounting.entities.JsonPrintable;

/**
 * <p>
 * Unit tests for class <code>BillingCostExportHistoryCriteria</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BillingCostExportHistoryCriteriaTest {
    /**
     * <p>
     * Represents the <code>BillingCostExportHistoryCriteria</code> instance used to test against.
     * </p>
     */
    private BillingCostExportHistoryCriteria impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BillingCostExportHistoryCriteriaTest.class);
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
        impl = new BillingCostExportHistoryCriteria();
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
     * Inheritance test, verifies <code>BillingCostExportHistoryCriteria</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof JsonPrintable);
        assertTrue("The instance's subclass is not correct.", impl instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BillingCostExportHistoryCriteria()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getStartDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetStartDate() {
        assertNull("The initial value should be null", impl.getStartDate());
        Date expect = new Date();
        impl.setStartDate(expect);
        assertEquals("The id should be same as", expect, impl.getStartDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setStartDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetStartDate() {
        assertNull("The initial value should be null", impl.getStartDate());
        Date expect = new Date();
        impl.setStartDate(expect);
        assertEquals("The id should be same as", expect, impl.getStartDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEndDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetEndDate() {
        assertNull("The initial value should be null", impl.getEndDate());
        Date expect = new Date();
        impl.setEndDate(expect);
        assertEquals("The id should be same as", expect, impl.getEndDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEndDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetEndDate() {
        assertNull("The initial value should be null", impl.getEndDate());
        Date expect = new Date();
        impl.setEndDate(expect);
        assertEquals("The id should be same as", expect, impl.getEndDate());
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
     * Accuracy test for the method <code>getPaymentAreaId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPaymentAreaId() {
        assertNull("The initial value should be null", impl.getPaymentAreaId());
        Long expect = new Long(4);
        impl.setPaymentAreaId(expect);
        assertEquals("The id should be same as", expect, impl.getPaymentAreaId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPaymentAreaId(Long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPaymentAreaId() {
        assertNull("The initial value should be null", impl.getPaymentAreaId());
        Long expect = new Long(4);
        impl.setPaymentAreaId(expect);
        assertEquals("The id should be same as", expect, impl.getPaymentAreaId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>toJSONString()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testToJSONString() {
        String expect = "{\"paymentAreaId\":null,\"startDate\":null,\"endDate\":null,\"accountantName\":null}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());

        impl.setAccountantName("jack");
        impl.setPaymentAreaId(new Long(5));
        expect = "{\"paymentAreaId\":5,\"startDate\":null,\"endDate\":null,\"accountantName\":\"jack\"}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());
    }

}
