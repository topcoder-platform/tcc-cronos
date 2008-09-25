/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import junit.framework.TestCase;

/**
 * Unit test for {@link ServiceBeanConfigurationException}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ServiceBeanConfigurationExceptionTest extends TestCase {

    /**
     * Represents the error message.
     */
    private static final String ERR_MESSAGE = "error message";

    /**
     * Represents the inner failure.
     */
    private static final Throwable INNER_ERROR = new RuntimeException();

    /**
     * Test for {@link ServiceBeanConfigurationException#
     * ServiceBeanConfigurationException(String)}.
     */
    public void testServiceBeanConfigurationExceptionString() {
        ServiceBeanConfigurationException err =
            new ServiceBeanConfigurationException(ERR_MESSAGE);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail setup error message.", ERR_MESSAGE, err.getMessage());
        assertNull("Fail setup inner failure.", err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof RuntimeException);
    }

    /**
     * Test for {@link ServiceBeanConfigurationException#
     * ServiceBeanConfigurationException(String, Throwable)}.
     */
    public void testServiceBeanConfigurationExceptionStringThrowable() {
        ServiceBeanConfigurationException err =
            new ServiceBeanConfigurationException(ERR_MESSAGE, INNER_ERROR);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail setup error message.", ERR_MESSAGE, err.getMessage());
        assertEquals("Fail setup inner failure.", INNER_ERROR, err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof RuntimeException);
    }
}
