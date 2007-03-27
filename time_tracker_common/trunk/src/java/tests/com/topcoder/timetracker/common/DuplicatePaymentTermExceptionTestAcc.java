/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;


/**
 * <p>
 * Accuracy tests for <code>DuplicatePaymentTermException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class DuplicatePaymentTermExceptionTestAcc extends BaseBaseTestCase {

    /**
     * <p>
     * Test ctor and <code>getDuplicatePaymentTerm()</code>. Given duplicate <code>PaymentTerm</code> is null.
     * </p>
     */
    public void testDuplicatePaymentTermException1() {
        DuplicatePaymentTermException e = new DuplicatePaymentTermException(ERROR_MESSAGE,
                null);
        assertNotNull("Successfully to instantiate DuplicatePaymentTermException.", e);
        this.assertErrorMessagePropagated(e);
        assertNull("The duplicate PaymentTerm should be null", e.getDuplicatePaymentTerm());
    }

    /**
     * <p>
     * Test ctor and <code>getDuplicatePaymentTerm()</code>. Given duplicate <code>PaymentTerm</code> is not null.
     * </p>
     */
    public void testDuplicatePaymentTermException2() {
        DuplicatePaymentTermException e = new DuplicatePaymentTermException(ERROR_MESSAGE,
                    this.getPaymentTermWithId(1));
        assertNotNull("Successfully to instantiate DuplicatePaymentTermException.", e);
        this.assertErrorMessagePropagated(e);
        assertNotNull("The duplicate PaymentTerm should not be null", e.getDuplicatePaymentTerm());
        assertEquals("The duplicate id should be 1", 1, e.getDuplicatePaymentTerm().getId());
    }
}
