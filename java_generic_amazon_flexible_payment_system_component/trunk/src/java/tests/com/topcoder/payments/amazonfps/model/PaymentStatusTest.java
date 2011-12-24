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
 * Unit tests for {@link PaymentStatus} enumeration.
 *
 * @author KennyAlive
 * @version 1.0
 */
public class PaymentStatusTest {
    /**
     * The number of elements that design defines for {@code PaymentStatus} enumeration.
     */
    private static final int ELEMENTS_COUNT = 6;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PaymentStatusTest.class);
    }

    /**
     * Checks that enumeration defines elements with the names as specified by the design.
     */
    @Test
    public void test_enumerationPaymentEventType() {
        assertTrue("PaymentEventType should be an enum", PaymentStatus.class.isEnum());
        assertEquals("The PaymentOperationType should defined " + ELEMENTS_COUNT + " elements",
                ELEMENTS_COUNT, PaymentStatus.values().length);

        // Check that elements names defined by the design were correctly presented in code.
        try {
            PaymentStatus.valueOf("NOT_INITIATED");
            PaymentStatus.valueOf("RESERVED");
            PaymentStatus.valueOf("COMPLETED");
            PaymentStatus.valueOf("CANCELLED");
            PaymentStatus.valueOf("REFUNDED");
            PaymentStatus.valueOf("FAILED");
        } catch (IllegalArgumentException e) {
            fail("The enumeration should should match design specification: " + e.getMessage());
        }
    }
}
