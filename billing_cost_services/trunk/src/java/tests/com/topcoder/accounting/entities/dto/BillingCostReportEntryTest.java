/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
 * Unit tests for class <code>BillingCostReportEntry</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BillingCostReportEntryTest {
    /**
     * <p>
     * Represents the <code>BillingCostReportEntry</code> instance used to test against.
     * </p>
     */
    private BillingCostReportEntry impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BillingCostReportEntryTest.class);
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
        impl = new BillingCostReportEntry();
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
     * Inheritance test, verifies <code>BillingCostReportEntry</code> subclasses should be correct.
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
     * Accuracy test for the constructor <code>BillingCostReportEntry()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPaymentDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPaymentDate() {
        assertNull("The initial value should be null", impl.getPaymentDate());
        Date expect = new Date();
        impl.setPaymentDate(expect);
        assertEquals("The id should be same as", expect, impl.getPaymentDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPaymentDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPaymentDate() {
        assertNull("The initial value should be null", impl.getPaymentDate());
        Date expect = new Date();
        impl.setPaymentDate(expect);
        assertEquals("The id should be same as", expect, impl.getPaymentDate());
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
     * Accuracy test for the method <code>getBillingAccount()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetBillingAccount() {
        assertNull("The initial value should be null", impl.getBillingAccount());
        String expect = "test";
        impl.setBillingAccount(expect);
        assertEquals("The id should be same as", expect, impl.getBillingAccount());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setBillingAccount(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetBillingAccount() {
        assertNull("The initial value should be null", impl.getBillingAccount());
        String expect = "test";
        impl.setBillingAccount(expect);
        assertEquals("The id should be same as", expect, impl.getBillingAccount());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectName()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetProjectName() {
        assertNull("The initial value should be null", impl.getProjectName());
        String expect = "test";
        impl.setProjectName(expect);
        assertEquals("The id should be same as", expect, impl.getProjectName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectName(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetProjectName() {
        assertNull("The initial value should be null", impl.getProjectName());
        String expect = "test";
        impl.setProjectName(expect);
        assertEquals("The id should be same as", expect, impl.getProjectName());
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
     * Accuracy test for the method <code>getReferenceId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetReferenceId() {
        assertNull("The initial value should be null", impl.getReferenceId());
        String expect = "test";
        impl.setReferenceId(expect);
        assertEquals("The id should be same as", expect, impl.getReferenceId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setReferenceId(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetReferenceId() {
        assertNull("The initial value should be null", impl.getReferenceId());
        String expect = "test";
        impl.setReferenceId(expect);
        assertEquals("The id should be same as", expect, impl.getReferenceId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCategory()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCategory() {
        assertNull("The initial value should be null", impl.getCategory());
        String expect = "test";
        impl.setCategory(expect);
        assertEquals("The id should be same as", expect, impl.getCategory());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCategory(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCategory() {
        assertNull("The initial value should be null", impl.getCategory());
        String expect = "test";
        impl.setCategory(expect);
        assertEquals("The id should be same as", expect, impl.getCategory());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getStatus()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetStatus() {
        assertNull("The initial value should be null", impl.getStatus());
        String expect = "test";
        impl.setStatus(expect);
        assertEquals("The id should be same as", expect, impl.getStatus());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setStatus(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetStatus() {
        assertNull("The initial value should be null", impl.getStatus());
        String expect = "test";
        impl.setStatus(expect);
        assertEquals("The id should be same as", expect, impl.getStatus());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLaunchDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetLaunchDate() {
        assertNull("The initial value should be null", impl.getLaunchDate());
        Date expect = new Date();
        impl.setLaunchDate(expect);
        assertEquals("The id should be same as", expect, impl.getLaunchDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLaunchDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetLaunchDate() {
        assertNull("The initial value should be null", impl.getLaunchDate());
        Date expect = new Date();
        impl.setLaunchDate(expect);
        assertEquals("The id should be same as", expect, impl.getLaunchDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCompletionDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCompletionDate() {
        assertNull("The initial value should be null", impl.getCompletionDate());
        Date expect = new Date();
        impl.setCompletionDate(expect);
        assertEquals("The id should be same as", expect, impl.getCompletionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCompletionDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCompletionDate() {
        assertNull("The initial value should be null", impl.getCompletionDate());
        Date expect = new Date();
        impl.setCompletionDate(expect);
        assertEquals("The id should be same as", expect, impl.getCompletionDate());
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
     * Accuracy test for the method <code>getAmount()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetAmount() {
        assertEquals("The initial id should be same as", 0, impl.getAmount(), 0.001);
        float expect = 3.3f;
        impl.setAmount(expect);
        assertEquals("The id should be same as", expect, impl.getAmount(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAmount(float)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetAmount() {
        assertEquals("The initial id should be same as", 0, impl.getAmount(), 0.001);
        float expect = 3.3f;
        impl.setAmount(expect);
        assertEquals("The id should be same as", expect, impl.getAmount(), 0.001);
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
     * Accuracy test for the method <code>isExported()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testIsExported() {
        assertFalse("The initial value should be false", impl.isExported());
        impl.setExported(true);
        assertTrue("The expect value should be true", impl.isExported());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setExported(boolean)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetExported() {
        assertFalse("The initial value should be false", impl.isExported());
        impl.setExported(true);
        assertTrue("The expect value should be true", impl.isExported());
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
        String expect = "{\"paymentDate\":null,\"paymentDetailId\":null,\"category\":null,\"customer\":null,"
            + "\"status\":null,\"projectInfoTypeId\":null,\"referenceId\":null,"
            + "\"completionDate\":null,\"contestName\":null,\"amount\":0,\"exported\":false,"
            + "\"paymentType\":null,\"launchDate\":null,\"invoiceNumber\":null,"
            + "\"billingAccount\":null,\"projectName\":null,\"contestId\":0}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());

        impl.setCustomer("customer");
        impl.setBillingAccount("billingAccount");
        impl.setProjectName("projectName");
        impl.setContestName("contestName");
        impl.setContestId(4);
        impl.setReferenceId("6");
        impl.setCategory("category");
        impl.setStatus("status");
        impl.setPaymentType("paymentType");
        impl.setAmount(4.4f);
        impl.setPaymentDetailId(6L);
        impl.setPaymentDetailId(8L);
        impl.setExported(true);
        impl.setInvoiceNumber("911-101");

        expect = "{\"paymentDate\":null,\"paymentDetailId\":8,\"category\":\"category\",\"customer\":"
            + "\"customer\",\"status\":\"status\",\"projectInfoTypeId\":null,\"referenceId\":\"6\","
            + "\"completionDate\":null,\"contestName\":\"contestName\",\"amount\":4.400000095367431640625,"
            + "\"exported\":true,\"paymentType\":\"paymentType\",\"launchDate\":null,"
            + "\"invoiceNumber\":\"911-101\",\"billingAccount\":\"billingAccount\","
            + "\"projectName\":\"projectName\",\"contestId\":4}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());
    }

}
