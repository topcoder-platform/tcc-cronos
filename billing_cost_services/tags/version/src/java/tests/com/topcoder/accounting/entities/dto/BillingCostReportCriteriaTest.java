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
 * Unit tests for class <code>BillingCostReportCriteria</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BillingCostReportCriteriaTest {
    /**
     * <p>
     * Represents the <code>BillingCostReportCriteria</code> instance used to test against.
     * </p>
     */
    private BillingCostReportCriteria impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BillingCostReportCriteriaTest.class);
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
        impl = new BillingCostReportCriteria();
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
     * Inheritance test, verifies <code>BillingCostReportCriteria</code> subclasses should be correct.
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
     * Accuracy test for the constructor <code>BillingCostReportCriteria()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetProjectId() {
        assertNull("The initial value should be null", impl.getProjectId());
        Long expect = new Long(4);
        impl.setProjectId(expect);
        assertEquals("The id should be same as", expect, impl.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectId(Long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetProjectId() {
        assertNull("The initial value should be null", impl.getProjectId());
        Long expect = new Long(4);
        impl.setProjectId(expect);
        assertEquals("The id should be same as", expect, impl.getProjectId());
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
     * Accuracy test for the method <code>getStatusIds()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetStatusIds() {
        assertNull("The initial value should be null", impl.getStatusIds());
        long[] expect = new long[] {1, 2, 3};
        impl.setStatusIds(expect);
        assertEquals("The id should be same as", expect, impl.getStatusIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setStatusIds(long[])</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetStatusIds() {
        assertNull("The initial value should be null", impl.getStatusIds());
        long[] expect = new long[] {1, 2, 3};
        impl.setStatusIds(expect);
        assertEquals("The id should be same as", expect, impl.getStatusIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestTypeIds()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestTypeIds() {
        assertNull("The initial value should be null", impl.getContestTypeIds());
        long[] expect = new long[] {1, 2, 3};
        impl.setContestTypeIds(expect);
        assertEquals("The id should be same as", expect, impl.getContestTypeIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestTypeIds(long[])</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetContestTypeIds() {
        assertNull("The initial value should be null", impl.getContestTypeIds());
        long[] expect = new long[] {1, 2, 3};
        impl.setContestTypeIds(expect);
        assertEquals("The id should be same as", expect, impl.getContestTypeIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCustomerId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCustomerId() {
        assertNull("The initial value should be null", impl.getCustomerId());
        Long expect = new Long(4);
        impl.setCustomerId(expect);
        assertEquals("The id should be same as", expect, impl.getCustomerId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCustomerId(Long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCustomerId() {
        assertNull("The initial value should be null", impl.getCustomerId());
        Long expect = new Long(4);
        impl.setCustomerId(expect);
        assertEquals("The id should be same as", expect, impl.getCustomerId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBillingAccountId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetBillingAccountId() {
        assertNull("The initial value should be null", impl.getBillingAccountId());
        Long expect = new Long(4);
        impl.setBillingAccountId(expect);
        assertEquals("The id should be same as", expect, impl.getBillingAccountId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setBillingAccountId(Long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetBillingAccountId() {
        assertNull("The initial value should be null", impl.getBillingAccountId());
        Long expect = new Long(4);
        impl.setBillingAccountId(expect);
        assertEquals("The id should be same as", expect, impl.getBillingAccountId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>toJSONString()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testToJSONString() {
        String expect = "{\"billingAccountId\":null,\"startDate\":null,\"projectId\":null,\"endDate\":null,"
            + "\"contestTypeIds\":null,\"customerId\":null,\"statusIds\":null}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());

        impl.setProjectId(5L);
        impl.setStatusIds(new long[] {1, 2, 3});
        impl.setContestTypeIds(new long[] {4, 5});
        impl.setCustomerId(6L);
        expect = "{\"billingAccountId\":null,\"startDate\":null,\"projectId\":5,\"endDate\":null,"
            + "\"contestTypeIds\":[4,5],\"customerId\":6,\"statusIds\":[1,2,3]}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());
    }

}
