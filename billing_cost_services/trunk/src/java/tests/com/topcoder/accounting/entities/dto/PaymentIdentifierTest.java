/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.JsonPrintable;

/**
 * <p>
 * Unit tests for class <code>PaymentIdentifier</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class PaymentIdentifierTest {
    /**
     * <p>
     * Represents the <code>PaymentIdentifier</code> instance used to test against.
     * </p>
     */
    private PaymentIdentifier impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PaymentIdentifierTest.class);
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
        impl = new PaymentIdentifier();
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
     * Inheritance test, verifies <code>PaymentIdentifier</code> subclasses should be correct.
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
     * Accuracy test for the constructor <code>PaymentIdentifier()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestId() {
        assertNull("The initial value should be null", impl.getContestId());
        Long expect = new Long(4);
        impl.setContestId(expect);
        assertEquals("The id should be same as", expect, impl.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestId(Long)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetContestId() {
        assertNull("The initial value should be null", impl.getContestId());
        Long expect = new Long(4);
        impl.setContestId(expect);
        assertEquals("The id should be same as", expect, impl.getContestId());
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
     * Accuracy test for the method <code>toJSONString()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testToJSONString() {
        String expect = "{\"paymentDetailId\":null,\"projectInfoTypeId\":null,\"contestId\":null}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());

        impl.setContestId(1L);
        impl.setPaymentDetailId(2L);
        impl.setProjectInfoTypeId(3L);

        expect = "{\"paymentDetailId\":2,\"projectInfoTypeId\":3,\"contestId\":1}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());
    }

}
