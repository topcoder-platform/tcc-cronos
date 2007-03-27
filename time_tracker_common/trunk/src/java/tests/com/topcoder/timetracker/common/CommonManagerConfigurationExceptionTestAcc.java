/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * Accuracy tests for <code>CommonManagerConfigurationException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class CommonManagerConfigurationExceptionTestAcc extends BaseBaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>CommonManagerConfigurationException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        CommonManagerConfigurationException e = new CommonManagerConfigurationException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate CommonManagerConfigurationException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>CommonManagerConfigurationException(Throwable)</code>,
     * with correct error message and cause. The cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        CommonManagerConfigurationException e = new CommonManagerConfigurationException(CAUSE);

        assertNotNull("Successfully to instantiate CommonManagerConfigurationException.", e);
        this.assertErrorCausePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>CommonManagerConfigurationException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor3() {
        CommonManagerConfigurationException e = new CommonManagerConfigurationException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate CommonManagerConfigurationException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }

}
