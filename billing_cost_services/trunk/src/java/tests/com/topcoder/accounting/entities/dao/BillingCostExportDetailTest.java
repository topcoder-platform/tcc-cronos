/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
 * Unit tests for class <code>BillingCostExportDetail</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BillingCostExportDetailTest {
    /**
     * <p>
     * Represents the <code>BillingCostExportDetail</code> instance used to test against.
     * </p>
     */
    private BillingCostExportDetail impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BillingCostExportDetailTest.class);
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
        impl = new BillingCostExportDetail();
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
     * Inheritance test, verifies <code>BillingCostExportDetail</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof IdentifiableEntity);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BillingCostExportDetail()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBillingCostExportId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetBillingCostExportId() {
        assertEquals("The initial id should be same as", 0, impl.getBillingCostExportId());
        long expect = 7;
        impl.setBillingCostExportId(expect);
        assertEquals("The id should be same as", expect, impl.getBillingCostExportId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setBillingCostExportId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetBillingCostExportId() {
        assertEquals("The initial id should be same as", 0, impl.getBillingCostExportId());
        long expect = 7;
        impl.setBillingCostExportId(expect);
        assertEquals("The id should be same as", expect, impl.getBillingCostExportId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestId() {
        assertEquals("The initial id should be same as", 0, impl.getContestId());
        long expect = 7;
        impl.setContestId(expect);
        assertEquals("The id should be same as", expect, impl.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetContestId() {
        assertEquals("The initial id should be same as", 0, impl.getContestId());
        long expect = 7;
        impl.setContestId(expect);
        assertEquals("The id should be same as", expect, impl.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCustomer()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCustomer() {
        assertNull("The initial value should be null", impl.getCustomer());
        String expect = "test";
        impl.setCustomer(expect);
        assertEquals("The id should be same as", expect, impl.getCustomer());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCustomer(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCustomer() {
        assertNull("The initial value should be null", impl.getCustomer());
        String expect = "test";
        impl.setCustomer(expect);
        assertEquals("The id should be same as", expect, impl.getCustomer());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPaymentDetailId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPaymentDetailId() {
        assertNull("The initial value should be null", impl.getPaymentDetailId());
        Long expect = new Long(4);
        impl.setPaymentDetailId(expect);
        assertEquals("The id should be same as", expect, impl.getPaymentDetailId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPaymentDetailId(Long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPaymentDetailId() {
        assertNull("The initial value should be null", impl.getPaymentDetailId());
        Long expect = new Long(4);
        impl.setPaymentDetailId(expect);
        assertEquals("The id should be same as", expect, impl.getPaymentDetailId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectInfoTypeId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetProjectInfoTypeId() {
        assertNull("The initial value should be null", impl.getProjectInfoTypeId());
        Long expect = new Long(4);
        impl.setProjectInfoTypeId(expect);
        assertEquals("The id should be same as", expect, impl.getProjectInfoTypeId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectInfoTypeId(Long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetProjectInfoTypeId() {
        assertNull("The initial value should be null", impl.getProjectInfoTypeId());
        Long expect = new Long(4);
        impl.setProjectInfoTypeId(expect);
        assertEquals("The id should be same as", expect, impl.getProjectInfoTypeId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBillingAmount()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetBillingAmount() {
        assertEquals("The initial id should be same as", 0, impl.getBillingAmount(), 0.01);
        float expect = 6.6f;
        impl.setBillingAmount(expect);
        assertEquals("The id should be same as", expect, impl.getBillingAmount(), 0.01);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setBillingAmount(float)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetBillingAmount() {
        assertEquals("The initial id should be same as", 0, impl.getBillingAmount(), 0.01);
        float expect = 6.6f;
        impl.setBillingAmount(expect);
        assertEquals("The id should be same as", expect, impl.getBillingAmount(), 0.01);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPaymentType()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPaymentType() {
        assertNull("The initial value should be null", impl.getPaymentType());
        String expect = "test";
        impl.setPaymentType(expect);
        assertEquals("The id should be same as", expect, impl.getPaymentType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPaymentType(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPaymentType() {
        assertNull("The initial value should be null", impl.getPaymentType());
        String expect = "test";
        impl.setPaymentType(expect);
        assertEquals("The id should be same as", expect, impl.getPaymentType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestName()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestName() {
        assertNull("The initial value should be null", impl.getContestName());
        String expect = "test";
        impl.setContestName(expect);
        assertEquals("The id should be same as", expect, impl.getContestName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestName(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetContestName() {
        assertNull("The initial value should be null", impl.getContestName());
        String expect = "test";
        impl.setContestName(expect);
        assertEquals("The id should be same as", expect, impl.getContestName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>isInQuickBooks()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testIsInQuickBooks() {
        assertFalse("The initial value should be false", impl.isInQuickBooks());
        impl.setInQuickBooks(true);
        assertTrue("The expect value should be true", impl.isInQuickBooks());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setInQuickBooks(boolean)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetInQuickBooks() {
        assertFalse("The initial value should be false", impl.isInQuickBooks());
        impl.setInQuickBooks(true);
        assertTrue("The expect value should be true", impl.isInQuickBooks());
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
     * Accuracy test for the method <code>getQuickBooksImportTimestamp()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetQuickBooksImportTimestamp() {
        assertNull("The initial value should be null", impl.getQuickBooksImportTimestamp());
        Date expect = new Date();
        impl.setQuickBooksImportTimestamp(expect);
        assertEquals("The id should be same as", expect, impl.getQuickBooksImportTimestamp());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setQuickBooksImportTimestamp(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetQuickBooksImportTimestamp() {
        assertNull("The initial value should be null", impl.getQuickBooksImportTimestamp());
        Date expect = new Date();
        impl.setQuickBooksImportTimestamp(expect);
        assertEquals("The id should be same as", expect, impl.getQuickBooksImportTimestamp());
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
        String expect = "{\"quickBooksImportTimestamp\":null,\"contestName\":null,\"billingCostExportId\":0,"
            + "\"paymentType\":null,\"inQuickBooks\":false,\"billingAmount\":0,"
            + "\"paymentDetailId\":null,\"customer\":null,\"invoiceNumber\":null,"
            + "\"projectInfoTypeId\":null,\"contestId\":0,\"id\":4}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());

        impl.setBillingCostExportId(1);
        impl.setContestId(2);
        impl.setCustomer("bob");
        impl.setPaymentDetailId(3L);
        impl.setProjectInfoTypeId(4L);
        impl.setBillingAmount(5.5f);
        impl.setPaymentType("cash");
        impl.setContestName("abc");
        impl.setInQuickBooks(true);
        impl.setInvoiceNumber("512-101");
        expect = "{\"quickBooksImportTimestamp\":null,\"contestName\":\"abc\",\"billingCostExportId\":1,"
            + "\"paymentType\":\"cash\",\"inQuickBooks\":true,\"billingAmount\":5.5,"
            + "\"paymentDetailId\":3,\"customer\":\"bob\",\"invoiceNumber\":\"512-101\","
            + "\"projectInfoTypeId\":4,\"contestId\":2,\"id\":4}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());
    }

}
