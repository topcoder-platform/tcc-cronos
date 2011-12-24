/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
 * Unit tests for {@link Payment} class. <br/>
 * </p>
 *
 * <p>
 * The {@code Payment} constructor, getters and setters are tested:
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
public class PaymentTest {
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
    private Payment instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PaymentTest.class);
    }

    /**
     * Sets up the test environment.
     */
    @Before
    public void setUp() {
        instance = new Payment();
    }

    /**
     * Check that {@code Payment} class implements {@code Serializable} interface.
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
        TestHelper.checkJavaBeanMethods(Payment.class);
    }

    /**
     * Accuracy test for {@code Payment} constructor. <br/>
     * Checks that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor() {
        assertEquals("The id should be zero", 0L, TestHelper.getField(instance, "id"));
        assertEquals("The authorization id should be zero", 0L, TestHelper.getField(instance, "authorizationId"));
        assertNull("The amount should be null", TestHelper.getField(instance, "amount"));
        assertNull("The parameters should be null", TestHelper.getField(instance, "parameters"));
        assertNull("The transaction id should be null", TestHelper.getField(instance, "transactionId"));
        assertNull("The status should be null", TestHelper.getField(instance, "status"));
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

    //------------------- amount ---------------
    /**
     * Accuracy test for {@code getAmount} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getAmount() {
        TestHelper.setField(instance, "amount", VALUE);
        assertEquals("The value should be correct", VALUE, instance.getAmount());
    }
    /**
     * Accuracy test for {@code setAmount} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setAmount() {
        instance.setAmount(VALUE2);
        assertEquals("The value should be correct", VALUE2, instance.getAmount());
    }

    //------------------- parameters ---------------
    /**
     * Accuracy test for {@code getParameters} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getParameters() {
        Map<String, String> parameters = new HashMap<String, String>();
        TestHelper.setField(instance, "parameters", parameters);
        assertSame("The value should be correct", parameters, instance.getParameters());
    }
    /**
     * Accuracy test for {@code setParameters} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setParameters() {
        Map<String, String> parameters = new HashMap<String, String>();
        instance.setParameters(parameters);
        assertSame("The value should be correct", parameters, instance.getParameters());
    }

    //------------------- transactionId ---------------
    /**
     * Accuracy test for <code>getTransactionId</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_getTransactionId() {
        TestHelper.setField(instance, "transactionId", "id");
        assertEquals("The value should be correct", "id", instance.getTransactionId());
    }
    /**
     * Accuracy test for <code>setTransactionId</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setTransactionId() {
        instance.setTransactionId("myid123");
        assertEquals("The value should be correct", "myid123", instance.getTransactionId());
    }

    //------------------- status ---------------
    /**
     * Accuracy test for <code>getStatus</code> method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getStatus() {
        TestHelper.setField(instance, "status", PaymentStatus.COMPLETED);
        assertSame("The value should be correct", PaymentStatus.COMPLETED, instance.getStatus());
    }
    /**
     * Accuracy test for <code>setStatus</code> method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setStatus() {
        instance.setStatus(PaymentStatus.REFUNDED);
        assertSame("The value should be correct", PaymentStatus.REFUNDED, instance.getStatus());
    }
}
