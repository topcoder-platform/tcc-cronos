/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>LookupServiceBeanConfigurationException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LookupServiceBeanConfigurationExceptionTest extends TestCase {

    /**
     * <p>
     * Accuracy test for the constructor <code>ClientStatusServiceException(String)</code>.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtorWithMsg() {
        LookupServiceBeanConfigurationException exception = new LookupServiceBeanConfigurationException(
                "test_message");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test_message'", "test_message", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ClientStatusServiceException(String,Throwable)</code>.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtorWithMsgCause() {
        Throwable cause = new NullPointerException();
        LookupServiceBeanConfigurationException exception = new LookupServiceBeanConfigurationException(
                "test", cause);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", cause, exception.getCause());
    }

}
