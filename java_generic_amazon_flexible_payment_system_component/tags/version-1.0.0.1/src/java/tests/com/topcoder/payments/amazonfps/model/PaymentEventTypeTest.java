/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit tests for {@link PaymentEventType} enumeration.
 *
 * @author KennyAlive
 * @version 1.0
 */
public class PaymentEventTypeTest {
    /**
     * The number of elements that design defines for {@code PaymentEventType} enumeration.
     */
    private static final int ELEMENTS_COUNT = 14;
    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PaymentEventTypeTest.class);
    }

    /**
     * Checks that enumeration defines elements with the names as specified by the design.
     */
    @Test
    public void test_enumerationPaymentEventType() {
        assertTrue("PaymentEventType should be an enum", PaymentEventType.class.isEnum());
        assertEquals("The PaymentEventType should defined " + ELEMENTS_COUNT + " elements",
                ELEMENTS_COUNT, PaymentEventType.values().length);

        // Check that elements names defined by the design were correctly presented in code.
        try {
            PaymentEventType.valueOf("PAYMENT_FAILURE");
            PaymentEventType.valueOf("RESERVATION_SUCCESS");
            PaymentEventType.valueOf("RESERVATION_FAILURE");
            PaymentEventType.valueOf("SETTLEMENT_SUCCESS");
            PaymentEventType.valueOf("SETTLEMENT_FAILURE");
            PaymentEventType.valueOf("REFUND_SUCCESS");
            PaymentEventType.valueOf("REFUND_FAILURE");
            PaymentEventType.valueOf("PAYMENT_CANCELLATION_SUCCESS");
            PaymentEventType.valueOf("PAYMENT_CANCELLATION_FAILURE");
            PaymentEventType.valueOf("AUTHORIZATION_SUCCESS");
            PaymentEventType.valueOf("AUTHORIZATION_FAILURE");
            PaymentEventType.valueOf("AUTHORIZATION_CANCELLATION_SUCCESS");
            PaymentEventType.valueOf("AUTHORIZATION_CANCELLATION_FAILURE");
            PaymentEventType.valueOf("PAYMENT_SUCCESS");
        } catch (IllegalArgumentException e) {
            fail("The enumeration should should match design specification: " + e.getMessage());
        }
    }
}
