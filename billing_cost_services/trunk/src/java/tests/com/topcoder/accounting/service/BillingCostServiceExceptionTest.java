/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for class <code>BillingCostServiceException</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BillingCostServiceExceptionTest {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BillingCostServiceExceptionTest.class);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BillingCostServiceException</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.",
            new BillingCostServiceException("test") instanceof BaseCriticalException);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BillingCostServiceException(String)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        BillingCostServiceException exception = new BillingCostServiceException("test");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BillingCostServiceException(String, Throwable)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor2() {
        Throwable throwable = new NullPointerException();
        BillingCostServiceException exception = new BillingCostServiceException("test", throwable);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BillingCostServiceException(String, ExceptionData)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor3() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("appcode");
        BillingCostServiceException exception = new BillingCostServiceException("test", data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", "appcode", exception.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BillingCostServiceException(String, Throwable, ExceptionData)</code>
     * .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor4() {
        Throwable throwable = new NullPointerException();
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("appcode");
        BillingCostServiceException exception = new BillingCostServiceException("test", throwable, data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
        assertEquals("Cause should be set correctly", "appcode", exception.getApplicationCode());
    }

}
