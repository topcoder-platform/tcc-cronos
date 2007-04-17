/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import junit.framework.TestCase;


/**
 * UnitTest for <code>ConfigurationException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ConfigurationExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>ConfigurationException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigurationExceptionAccuracy1()
        throws Exception {
        ConfigurationException e = new ConfigurationException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * Accuracy test of <code>ConfigurationException(String msg, Throwable cause)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigurationExceptionAccuracy2()
        throws Exception {
        Exception cause = new Exception("cause");
        ConfigurationException e = new ConfigurationException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
