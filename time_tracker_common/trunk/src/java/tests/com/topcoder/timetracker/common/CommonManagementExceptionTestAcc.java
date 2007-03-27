/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * Accuracy tests for <code>CommonManagementException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class CommonManagementExceptionTestAcc extends BaseBaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>CommonManagementException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        CommonManagementException e = new CommonManagementException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate CommonManagementException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>CommonManagementException(Throwable)</code>,
     * with correct error message and cause. The cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        CommonManagementException e = new CommonManagementException(CAUSE);

        assertNotNull("Successfully to instantiate CommonManagementException.", e);
        this.assertErrorCausePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>CommonManagementException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor3() {
        CommonManagementException e = new CommonManagementException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate CommonManagementException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }
}

