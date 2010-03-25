/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.service.studio.ejb.IllegalArgumentWSFault;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for IllegalArgumentWSException class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IllegalArgumentWSExceptionTest extends TestCase {
    /**
     * Sample of message for exception.
     */
    private static final String SOME_MESSAGE = "yes, this is sample message";

    /**
     * Exception to test.
     */
    private IllegalArgumentWSException target;

    /**
     * Aggregates all tests.
     *
     * @return test suite will be returned
     */
    public static Test suite() {
        return new TestSuite(IllegalArgumentWSExceptionTest.class);
    }

    /**
     * Tests constructor 1.
     */
    public void testConstructor1() {
        IllegalArgumentWSFault fault = new IllegalArgumentWSFault();
        fault.setIllegalArgumentMessage("10");
        target = new IllegalArgumentWSException(SOME_MESSAGE, fault);

        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertEquals("message", "10", target.getIllegalArgumentMessage());
        assertSame("fault info", fault, target.getFaultInfo());
    }

    /**
     * Tests constructor 1 for null fault.
     */
    public void testConstructor1ForNullFault() {
        target = new IllegalArgumentWSException(SOME_MESSAGE, (IllegalArgumentWSFault) null);

        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertNull("message", target.getIllegalArgumentMessage());
        assertNull("fault info", target.getFaultInfo());
    }

    /**
     * Test constructor 2.
     */
    public void testConstructor2() {
        target = new IllegalArgumentWSException(SOME_MESSAGE, "3");
        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertEquals("message", "3", target.getIllegalArgumentMessage());
        assertNull("fault info", target.getFaultInfo());
    }
}
