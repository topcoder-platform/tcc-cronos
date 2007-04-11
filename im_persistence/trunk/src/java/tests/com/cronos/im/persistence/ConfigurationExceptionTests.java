/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>ConfigurationException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class ConfigurationExceptionTests extends TestCase {
    /**
     * Tests that <code>ConfigurationException</code> inherits from <code>BaseException</code>.
     */
    public void test_inheritance() {
        assertTrue("ConfigurationException should inherit from BaseException",
                   BaseException.class.isAssignableFrom(ConfigurationException.class));
    }

    /**
     * Tests the first constructor and the <code>getMessage</code> and <code>getCause</code> methods.
     */
    public void test_ctor1() {
        Throwable ex = new RuntimeException();
        ConfigurationException exception = new ConfigurationException("message", ex);
        assertTrue("getMessage() should contain the message passed to the constructor",
                   exception.getMessage().indexOf("message") > -1);
        assertEquals("getCause() should return the cause passed to the constructor", ex, exception.getCause());
    }

    /**
     * Tests the second constructor and the <code>getMessage</code> method.
     */
    public void test_ctor2() {
        assertEquals("getMessage() should return the message passed to the constructor", "message",
                     new ConfigurationException("message").getMessage());
    }
}
