/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * Unit tests for {@link PaymentAuthorizationRequest} class. <br/>
 * </p>
 *
 * <p>
 * The {@code PaymentAuthorizationRequest} constructor, getters and setters are tested:
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
public class PaymentAuthorizationRequestTest {
    /**
     * Constant used for testing.
     */
    private static final BigDecimal VALUE = BigDecimal.valueOf(1000.25);

    /**
     * Constant used for testing.
     */
    private static final BigDecimal VALUE2 = BigDecimal.valueOf(12.2);

    /**
     * The {@code Payment} instance used for testing.
     */
    private PaymentAuthorizationRequest instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PaymentAuthorizationRequestTest.class);
    }

    /**
     * Sets up the test environment.
     */
    @Before
    public void setUp() {
        instance = new PaymentAuthorizationRequest();
    }

    /**
     * Check that {@code PaymentAuthorizationRequest} class implements {@code Serializable} interface.
     */
    @Test
    public void test_implementedInterface() {
        assertTrue("MapEntry should implement Serializable", instance instanceof Serializable);
    }

    /**
     * Check that class's methods obey JavaBean naming conventions.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_javaBeanMethods() throws Exception {
        TestHelper.checkJavaBeanMethods(PaymentAuthorizationRequest.class);
    }

    /**
     * Accuracy test for {@code PaymentAuthorizationRequest} constructor. <br/>
     * Checks that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor() {
        assertNull("The redirect url should be null", TestHelper.getField(instance, "redirectUrl"));
        assertNull("The payment details should be null", TestHelper.getField(instance, "paymentDetails"));

        assertEquals("The futureChargesAuthorizationRequired should be false",
                false, TestHelper.getField(instance, "futureChargesAuthorizationRequired"));

        assertNull("The total charges threshold should be null",
                TestHelper.getField(instance, "totalChargesThreshold"));

        assertNull("The futureChargesFixedAmount should be null",
                TestHelper.getField(instance, "futureChargesFixedAmount"));
    }

    //------------------- redirectUrl ---------------
    /**
     * Accuracy test for <code>getRedirectUrl</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_getRedirectUrl() {
        TestHelper.setField(instance, "redirectUrl", "some/url");
        assertEquals("The value should be correct", "some/url", instance.getRedirectUrl());
    }
    /**
     * Accuracy test for <code>setRedirectUrl</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setRedirectUrl() {
        instance.setRedirectUrl("new/url/home");
        assertEquals("The value should be correct", "new/url/home", instance.getRedirectUrl());
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

    //------------------- futureChargesAuthorizationRequired ---------------
    /**
     * Accuracy test for <code>isFutureChargesAuthorizationRequired</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_isFutureChargesAuthorizationRequired() {
        TestHelper.setField(instance, "futureChargesAuthorizationRequired", true);
        assertTrue("The value should be true", instance.isFutureChargesAuthorizationRequired());
    }
    /**
     * Accuracy test for <code>setFutureChargesAuthorizationRequired</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setFutureChargesAuthorizationRequired() {
        instance.setFutureChargesAuthorizationRequired(true);
        assertTrue("The value should be true", instance.isFutureChargesAuthorizationRequired());
    }

    //------------------- totalChargesThreshold ---------------
    /**
     * Accuracy test for {@code getTotalChargesThreshold} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getTotalChargesThreshold() {
        TestHelper.setField(instance, "totalChargesThreshold", VALUE);
        assertEquals("The value should be correct", VALUE, instance.getTotalChargesThreshold());
    }
    /**
     * Accuracy test for {@code setTotalChargesThreshold} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setTotalChargesThreshold() {
        instance.setTotalChargesThreshold(VALUE2);
        assertEquals("The value should be correct", VALUE2, instance.getTotalChargesThreshold());
    }

    //------------------- futureChargesFixedAmount ---------------
    /**
     * Accuracy test for {@code getFutureChargesFixedAmount} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getFutureChargesFixedAmount() {
        TestHelper.setField(instance, "futureChargesFixedAmount", VALUE);
        assertEquals("The value should be correct", VALUE, instance.getFutureChargesFixedAmount());
    }
    /**
     * Accuracy test for {@code setFutureChargesFixedAmount} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setFutureChargesFixedAmount() {
        instance.setFutureChargesFixedAmount(VALUE2);
        assertEquals("The value should be correct", VALUE2, instance.getFutureChargesFixedAmount());
    }
}
