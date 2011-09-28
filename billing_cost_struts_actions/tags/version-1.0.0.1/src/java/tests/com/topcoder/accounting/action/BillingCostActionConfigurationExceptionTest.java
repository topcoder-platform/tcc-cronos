/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.topcoder.accounting.service.BillingCostConfigurationException;

/**
 * <p>
 * Unit tests for the {@link BillingCostActionConfigurationException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BillingCostActionConfigurationExceptionTest {
    /**
     * <p>
     * Represents the error message for testing.
     * </p>
     */
    private static final String MESSAGE = "error message";

    /**
     * <p>
     * Represents the <code>Exception</code> instance used for testing.
     * </p>
     */
    private static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Tests accuracy of
     * <code>BillingCostActionConfigurationException(String)</code> constructor.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    @Test
    public void testCtor1Accuracy() {
        BillingCostActionConfigurationException exception = new BillingCostActionConfigurationException(
                MESSAGE);
        assertNotNull(
                "Unable to instantiate BillingCostActionConfigurationException.",
                exception);
        assertEquals(
                "Error message is not properly propagated to super class.",
                MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>BillingCostActionConfigurationException(String, Throwable)</code>
     * constructor.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    @Test
    public void testCtor2Accuracy() {
        BillingCostActionConfigurationException exception = new BillingCostActionConfigurationException(
                MESSAGE, CAUSE);
        assertNotNull(
                "Unable to instantiate BillingCostActionConfigurationException.",
                exception);
        assertEquals(
                "Error message is not properly propagated to super class.",
                MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE,
                exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies
     * <code>BillingCostActionConfigurationException</code> subclasses
     * <code>BillingCostConfigurationException</code>.
     * </p>
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testInheritance() {
        Class superClass = BillingCostActionConfigurationException.class
                .getSuperclass();
        assertTrue(
                "BillingCostActionConfigurationException does not subclass BillingCostConfigurationException.",
                superClass == BillingCostConfigurationException.class);
    }
}
