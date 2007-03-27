/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;


/**
 * <p>
 * Accuracy tests for <code>PaymentTermNotFoundException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class PaymentTermNotFoundExceptionTestAcc extends BaseBaseTestCase {

    /**
     * <p>
     * Test ctor and <code>getProblemPaymentTermId()</code>. Given problem id is negative.
     * This is allowed but really does not make sense.
     * </p>
     */
    public void testPaymentTermNotFoundException1() {
        PaymentTermNotFoundException e = new PaymentTermNotFoundException(ERROR_MESSAGE, -1);
        assertNotNull("Successfully to instantiate DuplicatePaymentTermException.", e);
        this.assertErrorMessagePropagated(e);
        assertEquals("The not found id should be -1", -1, e.getProblemPaymentTermId());
    }
    /**
     * <p>
     * Test ctor and <code>getProblemPaymentTermId()</code>. Given problem id is positive.
     * </p>
     */
    public void testPaymentTermNotFoundException2() {
        PaymentTermNotFoundException e = new PaymentTermNotFoundException(ERROR_MESSAGE, 1);
        assertNotNull("Successfully to instantiate DuplicatePaymentTermException.", e);
        this.assertErrorMessagePropagated(e);
        assertEquals("The not found id should be 1", 1, e.getProblemPaymentTermId());
    }

}
