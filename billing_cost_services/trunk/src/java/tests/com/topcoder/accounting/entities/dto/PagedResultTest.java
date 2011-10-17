/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.JsonPrintable;
import com.topcoder.accounting.entities.dao.PaymentArea;

/**
 * <p>
 * Unit tests for class <code>PagedResult</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class PagedResultTest {
    /**
     * <p>
     * Represents the <code>PagedResult</code> instance used to test against.
     * </p>
     */
    private PagedResult<PaymentArea> impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PagedResultTest.class);
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
        impl = new PagedResult<PaymentArea>();
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
     * Inheritance test, verifies <code>PagedResult</code> subclasses should be correct.
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
     * Accuracy test for the constructor <code>PagedResult()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getRecords()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetRecords() {
        assertNull("The initial value should be null", impl.getRecords());
        List<PaymentArea> expect = new ArrayList<PaymentArea>();
        impl.setRecords(expect);
        assertEquals("The id should be same as", expect, impl.getRecords());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRecords(List)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetRecords() {
        assertNull("The initial value should be null", impl.getRecords());
        List<PaymentArea> expect = new ArrayList<PaymentArea>();
        impl.setRecords(expect);
        assertEquals("The id should be same as", expect, impl.getRecords());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTotalRecords()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetTotalRecords() {
        assertEquals("The initial id should be same as", 0, impl.getTotalRecords());
        int expect = 7;
        impl.setTotalRecords(expect);
        assertEquals("The id should be same as", expect, impl.getTotalRecords());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTotalRecords(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetTotalRecords() {
        assertEquals("The initial id should be same as", 0, impl.getTotalRecords());
        int expect = 7;
        impl.setTotalRecords(expect);
        assertEquals("The id should be same as", expect, impl.getTotalRecords());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPageNo()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPageNo() {
        assertEquals("The initial id should be same as", 0, impl.getPageNo());
        int expect = 7;
        impl.setPageNo(expect);
        assertEquals("The id should be same as", expect, impl.getPageNo());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPageNo(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPageNo() {
        assertEquals("The initial id should be same as", 0, impl.getPageNo());
        int expect = 7;
        impl.setPageNo(expect);
        assertEquals("The id should be same as", expect, impl.getPageNo());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPageSize()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPageSize() {
        assertEquals("The initial id should be same as", 0, impl.getPageSize());
        int expect = 7;
        impl.setPageSize(expect);
        assertEquals("The id should be same as", expect, impl.getPageSize());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPageSize(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPageSize() {
        assertEquals("The initial id should be same as", 0, impl.getPageSize());
        int expect = 7;
        impl.setPageSize(expect);
        assertEquals("The id should be same as", expect, impl.getPageSize());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTotalPages()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetTotalPages() {
        assertEquals("The initial id should be same as", 0, impl.getTotalPages());
        int expect = 7;
        impl.setTotalPages(expect);
        assertEquals("The id should be same as", expect, impl.getTotalPages());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTotalPages(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetTotalPages() {
        assertEquals("The initial id should be same as", 0, impl.getTotalPages());
        int expect = 7;
        impl.setTotalPages(expect);
        assertEquals("The id should be same as", expect, impl.getTotalPages());
    }

    /**
     * <p>
     * Accuracy test for the method <code>toJSONString()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testToJSONString() {
        String expect = "{\"records\":null,\"pageSize\":0,\"totalRecords\":0,\"totalPages\":0,\"pageNo\":0}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());

        List<PaymentArea> records = new ArrayList<PaymentArea>();
        PaymentArea pa = new PaymentArea();
        pa.setId(4);
        pa.setName("pa1");
        records.add(pa);
        pa = new PaymentArea();
        pa.setId(5);
        pa.setName("pa5");
        records.add(pa);
        impl.setRecords(records);
        impl.setPageNo(5);
        impl.setPageSize(6);
        impl.setTotalPages(10);
        impl.setTotalRecords(4);

        expect = "{\"records\":[\"{\\\"name\\\":\\\"pa1\\\",\\\"id\\\":4}\",\"{\\\"name\\\":\\\"pa5\\\","
            + "\\\"id\\\":5}\"],\"pageSize\":6,\"totalRecords\":4,\"totalPages\":10,\"pageNo\":5}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());
    }

}
