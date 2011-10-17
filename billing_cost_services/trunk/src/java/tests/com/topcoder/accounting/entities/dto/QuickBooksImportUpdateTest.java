/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
 * Unit tests for class <code>QuickBooksImportUpdate</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class QuickBooksImportUpdateTest {
    /**
     * <p>
     * Represents the <code>QuickBooksImportUpdate</code> instance used to test against.
     * </p>
     */
    private QuickBooksImportUpdate impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(QuickBooksImportUpdateTest.class);
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
        impl = new QuickBooksImportUpdate();
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
     * Inheritance test, verifies <code>QuickBooksImportUpdate</code> subclasses should be correct.
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
     * Accuracy test for the constructor <code>QuickBooksImportUpdate()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBillingCostExportDetailIds()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetBillingCostExportDetailIds() {
        assertNull("The initial value should be null", impl.getBillingCostExportDetailIds());
        long[] expect = new long[] {1, 2, 3};
        impl.setBillingCostExportDetailIds(expect);
        assertEquals("The id should be same as", expect, impl.getBillingCostExportDetailIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setBillingCostExportDetailIds(long[])</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetBillingCostExportDetailIds() {
        assertNull("The initial value should be null", impl.getBillingCostExportDetailIds());
        long[] expect = new long[] {1, 2, 3};
        impl.setBillingCostExportDetailIds(expect);
        assertEquals("The id should be same as", expect, impl.getBillingCostExportDetailIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getInvoiceNumber()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetInvoiceNumber() {
        assertNull("The initial value should be null", impl.getInvoiceNumber());
        String expect = "test";
        impl.setInvoiceNumber(expect);
        assertEquals("The id should be same as", expect, impl.getInvoiceNumber());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setInvoiceNumber(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetInvoiceNumber() {
        assertNull("The initial value should be null", impl.getInvoiceNumber());
        String expect = "test";
        impl.setInvoiceNumber(expect);
        assertEquals("The id should be same as", expect, impl.getInvoiceNumber());
    }

    /**
     * <p>
     * Accuracy test for the method <code>toJSONString()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testToJSONString() {
        String expect = "{\"billingCostExportDetailIds\":null,\"invoiceNumber\":null}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());

        impl.setBillingCostExportDetailIds(new long[] {2, 3, 4});
        impl.setInvoiceNumber("1011-113");
        expect = "{\"billingCostExportDetailIds\":[2,3,4],\"invoiceNumber\":\"1011-113\"}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());
    }

}
