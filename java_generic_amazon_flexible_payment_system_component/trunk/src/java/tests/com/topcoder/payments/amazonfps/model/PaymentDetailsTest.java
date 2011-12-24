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
 * Unit tests for {@link PaymentDetails} class. <br/>
 * </p>
 *
 * <p>
 * The {@code PaymentDetails} constructor, getters and setters are tested:
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
public class PaymentDetailsTest {
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
    private PaymentDetails instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PaymentDetailsTest.class);
    }

    /**
     * Sets up the test environment.
     */
    @Before
    public void setUp() {
        instance = new PaymentDetails();
    }

    /**
     * Check that {@code PaymentDetails} class implements {@code Serializable} interface.
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
        TestHelper.checkJavaBeanMethods(PaymentDetails.class);
    }

    /**
     * Accuracy test for {@code PaymentDetails} constructor. <br/>
     * Checks that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor() {
        assertNull("The parameters should be null", TestHelper.getField(instance, "parameters"));
        assertNull("The amount should be null", TestHelper.getField(instance, "amount"));

        assertEquals("The reservation should be false",
                false, TestHelper.getField(instance, "reservation"));
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

    //------------------- reservation ---------------
    /**
     * Accuracy test for <code>isReservation</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_isReservation() {
        TestHelper.setField(instance, "reservation", true);
        assertTrue("The value should be true", instance.isReservation());
    }
    /**
     * Accuracy test for <code>setReservation</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setReservation() {
        instance.setReservation(true);
        assertTrue("The value should be true", instance.isReservation());
    }
}
