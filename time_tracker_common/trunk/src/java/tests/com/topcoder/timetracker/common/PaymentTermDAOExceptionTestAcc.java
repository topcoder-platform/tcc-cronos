/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * Accuracy tests for <code>PaymentTermDAOException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class PaymentTermDAOExceptionTestAcc extends BaseBaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>PaymentTermDAOException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        PaymentTermDAOException e = new PaymentTermDAOException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate PaymentTermDAOException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>PaymentTermDAOException(Throwable)</code>,
     * with correct error message and cause. The cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        PaymentTermDAOException e = new PaymentTermDAOException(CAUSE);

        assertNotNull("Successfully to instantiate PaymentTermDAOException.", e);
        this.assertErrorCausePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>PaymentTermDAOException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor3() {
        PaymentTermDAOException e = new PaymentTermDAOException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate PaymentTermDAOException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }

}
