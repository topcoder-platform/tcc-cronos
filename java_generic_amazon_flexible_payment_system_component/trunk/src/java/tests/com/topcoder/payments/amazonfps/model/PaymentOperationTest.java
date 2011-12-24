/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.io.Serializable;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.payments.amazonfps.TestHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Unit tests for {@link PaymentOperation} class. <br/>
 * </p>
 *
 * <p>
 * The {@code PaymentOperation} constructor, getters and setters are tested:
 * <ul>
 * <li>The constructor test validates that fields are initialized to correct default values.</li>
 * <li>Getters correctness are checked by setting initial values using reflection and then checking them.</li>
 * <li>Setters correctness are checked using corresponding getters (which itself were tested via reflection).</li>
 * </ul>
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class PaymentOperationTest {
    /**
     * The {@code PaymentOperation} instance used for testing.
     */
    private PaymentOperation instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PaymentOperationTest.class);
    }

    /**
     * Sets up the test environment.
     */
    @Before
    public void setUp() {
        instance = new PaymentOperation();
    }

    /**
     * Check that {@code PaymentOperation} class implements {@code Serializable} interface.
     */
    @Test
    public void test_implementedInterface() {
        assertTrue("Authorization should implement Serializable", instance instanceof Serializable);
    }

    /**
     * Check that class's methods obey JavaBean naming conventions.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_javaBeanMethods() throws Exception {
        TestHelper.checkJavaBeanMethods(PaymentOperation.class);
    }

    /**
     * Accuracy test for {@code PaymentOperation} constructor. <br/>
     * Checks that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor() {
        assertEquals("The id should be zero", 0L, TestHelper.getField(instance, "id"));
        assertEquals("The payment id should be zero", 0L, TestHelper.getField(instance, "paymentId"));
        assertNull("The request id should be null", TestHelper.getField(instance, "requestId"));
        assertNull("The operation type should be null", TestHelper.getField(instance, "type"));

        assertEquals("The successful flag should be false", false,
                TestHelper.getField(instance, "successful"));
    }

    //------------------- id ---------------
    /**
     * Accuracy test for {@code getId} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getId() {
        TestHelper.setField(instance, "id", 5L);
        assertEquals("The value should be correct", 5L, instance.getId());
    }
    /**
     * Accuracy test for {@code setId} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setId() {
        instance.setId(10L);
        assertEquals("The value should be correct", 10L, instance.getId());
    }

    //------------------- paymentId ---------------
    /**
     * Accuracy test for {@code getPaymentId} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getPaymentId() {
        TestHelper.setField(instance, "paymentId", 15L);
        assertEquals("The value should be correct", 15L, instance.getPaymentId());
    }
    /**
     * Accuracy test for {@code setPaymentId} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setPaymentId() {
        instance.setPaymentId(120L);
        assertEquals("The value should be correct", 120L, instance.getPaymentId());
    }

    //------------------- requestId ---------------
    /**
     * Accuracy test for <code>getRequestId</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_getRequestId() {
        TestHelper.setField(instance, "requestId", "request1");
        assertEquals("The value should be correct", "request1", instance.getRequestId());
    }
    /**
     * Accuracy test for <code>setRequestId</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setRequestId() {
        instance.setRequestId("request2");
        assertEquals("The value should be correct", "request2", instance.getRequestId());
    }

    //------------------- type ---------------
    /**
     * Accuracy test for {@code getType} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getType() {
        TestHelper.setField(instance, "type", PaymentOperationType.PAY);
        assertSame("The value should be correct", PaymentOperationType.PAY, instance.getType());
    }
    /**
     * Accuracy test for {@code setType} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setType() {
        instance.setType(PaymentOperationType.RESERVE);
        assertSame("The value should be correct", PaymentOperationType.RESERVE, instance.getType());
    }

    //------------------- successful ---------------
    /**
     * Accuracy test for <code>isSuccessful</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_isSuccessful() {
        TestHelper.setField(instance, "successful", true);
        assertTrue("The value should be true", instance.isSuccessful());
    }
    /**
     * Accuracy test for <code>setSuccessful</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setSuccessful() {
        instance.setSuccessful(true);
        assertTrue("The value should be true", instance.isSuccessful());
    }
}
