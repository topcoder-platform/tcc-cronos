/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>
 * Accuracy tests for <code>ConfigurationException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ConfigurationExceptionTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>ConfigurationException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        ConfigurationException e = new ConfigurationException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate ConfigurationException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>ConfigurationException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        ConfigurationException e = new ConfigurationException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate ConfigurationException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }

}
