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
 * Unit tests for {@link PaymentEvent} class. <br/>
 * </p>
 *
 * <p>
 * The {@code PaymentEvent} constructor, getters and setters are tested:
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
public class PaymentEventTest {
    /**
     * The {@code PaymentEvent} instance used for testing.
     */
    private PaymentEvent instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PaymentEventTest.class);
    }

    /**
     * Sets up the test environment.
     */
    @Before
    public void setUp() {
        instance = new PaymentEvent();
    }

    /**
     * Check that {@code PaymentEvent} class implements {@code Serializable} interface.
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
        TestHelper.checkJavaBeanMethods(PaymentEvent.class);
    }

    /**
     * Accuracy test for {@code PaymentEvent} constructor. <br/>
     * Checks that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor() {
        assertNull("The event type should be null", TestHelper.getField(instance, "eventType"));
        assertNull("The payment details should be null", TestHelper.getField(instance, "paymentDetails"));

        assertEquals("The authorization id should be 0", 0L,
                TestHelper.getField(instance, "authorizationId"));
        assertEquals("The payment id should be 0", 0L,
                TestHelper.getField(instance, "paymentId"));

        assertNull("The error code should be null", TestHelper.getField(instance, "errorCode"));
        assertNull("The error type should be null", TestHelper.getField(instance, "errorType"));
        assertNull("The error message should be null", TestHelper.getField(instance, "errorMessage"));
    }

    //------------------- eventType ---------------
    /**
     * Accuracy test for {@code getEventType} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getEventType() {
        TestHelper.setField(instance, "eventType", PaymentEventType.AUTHORIZATION_SUCCESS);
        assertSame("The value should be correct", PaymentEventType.AUTHORIZATION_SUCCESS, instance.getEventType());
    }
    /**
     * Accuracy test for {@code setEventType} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setEventType() {
        instance.setEventType(PaymentEventType.PAYMENT_FAILURE);
        assertEquals("The value should be correct", PaymentEventType.PAYMENT_FAILURE, instance.getEventType());
    }

    //------------------- paymentDetails ---------------
    /**
     * Accuracy test for <code>getPaymentDetails</code> method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getPaymentDetails() {
        PaymentDetails paymentDetails = new PaymentDetails();
        TestHelper.setField(instance, "paymentDetails", paymentDetails);
        assertSame("The value should be correct", paymentDetails, instance.getPaymentDetails());
    }
    /**
     * Accuracy test for <code>setPaymentDetails</code> method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setPaymentDetails() {
        PaymentDetails paymentDetails = new PaymentDetails();
        instance.setPaymentDetails(paymentDetails);
        assertSame("The value should be correct", paymentDetails, instance.getPaymentDetails());
    }

    //------------------- authorizationId ---------------
    /**
     * Accuracy test for {@code getAuthorizationId} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getAuthorizationId() {
        TestHelper.setField(instance, "authorizationId", 5L);
        assertEquals("The value should be correct", 5L, instance.getAuthorizationId());
    }
    /**
     * Accuracy test for {@code setAuthorizationId} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setAuthorizationId() {
        instance.setAuthorizationId(10L);
        assertEquals("The value should be correct", 10L, instance.getAuthorizationId());
    }

    //------------------- paymentId ---------------
    /**
     * Accuracy test for {@code getPaymentId} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getPaymentId() {
        TestHelper.setField(instance, "paymentId", 2L);
        assertEquals("The value should be correct", 2L, instance.getPaymentId());
    }
    /**
     * Accuracy test for {@code setPaymentId} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setPaymentId() {
        instance.setPaymentId(12L);
        assertEquals("The value should be correct", 12L, instance.getPaymentId());
    }

    //------------------- errorCode ---------------
    /**
     * Accuracy test for <code>getErrorCode</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_getErrorCode() {
        TestHelper.setField(instance, "errorCode", "err1");
        assertEquals("The value should be correct", "err1", instance.getErrorCode());
    }
    /**
     * Accuracy test for <code>setErrorCode</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setErrorCode() {
        instance.setErrorCode("fatal_error");
        assertEquals("The value should be correct", "fatal_error", instance.getErrorCode());
    }

    //------------------- errorType ---------------
    /**
     * Accuracy test for <code>getErrorType</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_getErrorType() {
        TestHelper.setField(instance, "errorType", "minor_error");
        assertEquals("The value should be correct", "minor_error", instance.getErrorType());
    }
    /**
     * Accuracy test for <code>setErrorType</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setErrorType() {
        instance.setErrorType("err_type5");
        assertEquals("The value should be correct", "err_type5", instance.getErrorType());
    }

    //------------------- errorMessage ---------------
    /**
     * Accuracy test for <code>getErrorMessage</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_getErrorMessage() {
        TestHelper.setField(instance, "errorMessage", "error");
        assertEquals("The value should be correct", "error", instance.getErrorMessage());
    }
    /**
     * Accuracy test for <code>setErrorMessage</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setErrorMessage() {
        instance.setErrorMessage("warning!");
        assertEquals("The value should be correct", "warning!", instance.getErrorMessage());
    }
}
