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
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Unit tests for {@link Authorization} class. <br/>
 * </p>
 *
 * <p>
 * The {@code Authorization} constructor, getters and setters are tested:
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
public class AuthorizationTest {
    /**
     * Constant used for testing.
     */
    private static final BigDecimal VALUE = BigDecimal.valueOf(100.50);

    /**
     * Constant used for testing.
     */
    private static final BigDecimal VALUE2 = BigDecimal.valueOf(22.125);

    /**
     * The {@code Authorization} instance used for testing.
     */
    private Authorization instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AuthorizationTest.class);
    }

    /**
     * Sets up the test environment.
     */
    @Before
    public void setUp() {
        instance = new Authorization();
    }

    /**
     * Check that {@code Authorization} class implements {@code Serializable} interface.
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
        TestHelper.checkJavaBeanMethods(Authorization.class);
    }

    /**
     * Accuracy test for {@code Authorization} constructor. <br/>
     * Checks that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor() {
        assertEquals("The id should be zero", 0L, TestHelper.getField(instance, "id"));

        assertEquals("The multipleUseAuthorization should be false", false,
                TestHelper.getField(instance, "multipleUseAuthorization"));

        assertNull("The token id should be null", TestHelper.getField(instance, "tokenId"));

        assertNull("The authorized amount left should be null",
                TestHelper.getField(instance, "authorizedAmountLeft"));

        assertNull("The authorized fixed future amount should be null",
                TestHelper.getField(instance, "authorizedFixedFutureAmount"));

        assertEquals("The cancelled flag should be false", false,
                TestHelper.getField(instance, "cancelled"));
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

    //------------------- multipleUseAuthorization ---------------
    /**
     * Accuracy test for {@code isMultipleUseAuthorization} method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_isMultipleUseAuthorization() {
        TestHelper.setField(instance, "multipleUseAuthorization", true);
        assertTrue("The value should be true", instance.isMultipleUseAuthorization());
    }
    /**
     * Accuracy test for {@code setMultipleUseAuthorization} method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setMultipleUseAuthorization() {
        instance.setMultipleUseAuthorization(true);
        assertTrue("The value should be true", instance.isMultipleUseAuthorization());
    }

    //------------------- tokenId ---------------
    /**
     * Accuracy test for <code>getTokenId</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_getTokenId() {
        TestHelper.setField(instance, "tokenId", "some_string");
        assertEquals("The value should be correct", "some_string", instance.getTokenId());
    }
    /**
     * Accuracy test for <code>setTokenId</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setTokenId() {
        instance.setTokenId("new_string");
        assertEquals("The value should be correct", "new_string", instance.getTokenId());
    }

    //------------------- authorizedAmountLeft ---------------
    /**
     * Accuracy test for {@code getAuthorizedAmountLeft} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getAuthorizedAmountLeft() {
        TestHelper.setField(instance, "authorizedAmountLeft", VALUE);
        assertEquals("The value should be correct", VALUE, instance.getAuthorizedAmountLeft());
    }
    /**
     * Accuracy test for {@code setAuthorizedAmountLeft} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setAuthorizedAmountLeft() {
        instance.setAuthorizedAmountLeft(VALUE2);
        assertEquals("The value should be correct", VALUE2, instance.getAuthorizedAmountLeft());
    }

    //------------------- authorizedFixedFutureAmount ---------------
    /**
     * Accuracy test for {@code getAuthorizedFixedFutureAmount} method. <br/>
     * Set the field value using reflection and then check that the getter returns the same value.
     */
    @Test
    public void test_getAuthorizedFixedFutureAmount() {
        TestHelper.setField(instance, "authorizedFixedFutureAmount", VALUE);
        assertEquals("The value should be correct", VALUE, instance.getAuthorizedFixedFutureAmount());
    }
    /**
     * Accuracy test for {@code setAuthorizedFixedFutureAmount} method. <br/>
     * Set the field value and then check it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setAuthorizedFixedFutureAmount() {
        instance.setAuthorizedFixedFutureAmount(VALUE2);
        assertEquals("The value should be correct", VALUE2, instance.getAuthorizedFixedFutureAmount());
    }

    //------------------- cancelled ---------------
    /**
     * Accuracy test for {@code isCancelled} method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_isCancelled() {
        TestHelper.setField(instance, "cancelled", true);
        assertTrue("The value should be true", instance.isCancelled());
    }
    /**
     * Accuracy test for {@code setCancelled} method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setCancelled() {
        instance.setCancelled(true);
        assertTrue("The value should be true", instance.isCancelled());
    }
}
