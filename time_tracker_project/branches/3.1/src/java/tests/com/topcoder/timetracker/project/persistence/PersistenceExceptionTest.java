/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit tests for PersistenceException implementation.
 *
 * @author colau
 * @version 1.1
 *
 * @since 1.0
 */
public class PersistenceExceptionTest extends TestCase {
    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(PersistenceExceptionTest.class);

        return suite;
    }

    /**
     * Test of constructor with one argument. Verifies it by getting the message being set.
     */
    public void testConstructor_OneArg() {
        String message = "message";
        PersistenceException exception = new PersistenceException(message);

        assertEquals("Fails to set message", message, exception.getMessage());
    }

    /**
     * Test of constructor with two arguments. Verifies it by getting the cause being set.
     */
    public void testConstructor_TwoArg() {
        Exception cause = new Exception();
        PersistenceException exception = new PersistenceException("message", cause);

        assertEquals("Fails to set cause", cause, exception.getCause());
    }
}
