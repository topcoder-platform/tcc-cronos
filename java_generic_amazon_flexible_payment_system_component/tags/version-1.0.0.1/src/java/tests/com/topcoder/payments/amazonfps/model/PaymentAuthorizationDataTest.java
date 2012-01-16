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
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Unit tests for {@link PaymentAuthorizationData} class. <br/>
 * </p>
 *
 * <p>
 * The {@code PaymentAuthorizationData} constructor, getters and setters are tested:
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
public class PaymentAuthorizationDataTest {
    /**
     * The {@code PaymentAuthorizationData} instance used for testing.
     */
    private PaymentAuthorizationData instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PaymentAuthorizationDataTest.class);
    }

    /**
     * Sets up the test environment.
     */
    @Before
    public void setUp() {
        instance = new PaymentAuthorizationData();
    }

    /**
     * Check that {@code PaymentAuthorizationData} class implements {@code Serializable} interface.
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
        TestHelper.checkJavaBeanMethods(PaymentAuthorizationData.class);
    }

    /**
     * Accuracy test for {@code PaymentAuthorizationData} constructor. <br/>
     * Checks that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor() {
        assertNull("The authorization url should be null", TestHelper.getField(instance, "authorizationUrl"));
        assertEquals("The authorization id should be zero", 0L, TestHelper.getField(instance, "authorizationId"));
        assertEquals("The payment id should be zero", 0L, TestHelper.getField(instance, "paymentId"));
    }

    //------------------- authorizationUrl ---------------
    /**
     * Accuracy test for <code>getAuthorizationUrl</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_getAuthorizationUrl() {
        TestHelper.setField(instance, "authorizationUrl", "auth.com");
        assertEquals("The value should be correct", "auth.com", instance.getAuthorizationUrl());
    }
    /**
     * Accuracy test for <code>setAuthorizationUrl</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setAuthorizationUrl() {
        instance.setAuthorizationUrl("path/auth");
        assertEquals("The value should be correct", "path/auth", instance.getAuthorizationUrl());
    }

    //------------------- authorizationId ---------------
    /**
     * Accuracy test for {@code getAuthorizationId} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getAuthorizationId() {
        TestHelper.setField(instance, "authorizationId", 255L);
        assertEquals("The value should be correct", 255L, instance.getAuthorizationId());
    }
    /**
     * Accuracy test for {@code setAuthorizationId} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setAuthorizationId() {
        instance.setAuthorizationId(20L);
        assertEquals("The value should be correct", 20L, instance.getAuthorizationId());
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
}
