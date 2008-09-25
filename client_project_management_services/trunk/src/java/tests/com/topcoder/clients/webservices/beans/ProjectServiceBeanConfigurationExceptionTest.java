/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import junit.framework.TestCase;

/**
 * Unit test for {@link ProjectServiceBeanConfigurationException}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ProjectServiceBeanConfigurationExceptionTest extends TestCase {

    /**
     * Represents the error message.
     */
    private static final String ERR_MESSAGE = "error message";

    /**
     * Represents the inner failure.
     */
    private static final Throwable INNER_ERROR = new RuntimeException();

    /**
     * Test for {@link ProjectServiceBeanConfigurationException#
     * ProjectServiceBeanConfigurationException(String)}.
     */
    public void testProjectServiceBeanConfigurationExceptionString() {
        ProjectServiceBeanConfigurationException err =
            new ProjectServiceBeanConfigurationException(ERR_MESSAGE);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail setup error message.", ERR_MESSAGE, err.getMessage());
        assertNull("Fail setup inner failure.", err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof ServiceBeanConfigurationException);
    }

    /**
     * Test for {@link ProjectServiceBeanConfigurationException#
     * ProjectServiceBeanConfigurationException(String, Throwable)}.
     */
    public void testProjectServiceBeanConfigurationExceptionStringThrowable() {
        ProjectServiceBeanConfigurationException err =
            new ProjectServiceBeanConfigurationException(ERR_MESSAGE, INNER_ERROR);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail setup error message.", ERR_MESSAGE, err.getMessage());
        assertEquals("Fail setup inner failure.", INNER_ERROR, err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof ServiceBeanConfigurationException);
    }
}
