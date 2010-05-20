/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.service.studio.ejb.PersistenceFault;
import com.topcoder.util.errorhandling.ExceptionData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for PersistenceException class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceExceptionTest extends TestCase {
    /**
     * Sample of message for exception.
     */
    private static final String SOME_MESSAGE = "yes, this is sample message";

    /**
     * Sample of cause for exception.
     */
    private static final Exception SOME_CAUSE = new Exception("sample cause, as noticed");

    /**
     * Sample of data for exception.
     */
    private static final ExceptionData SOME_DATA = new ExceptionData();

    /**
     * Exception to test.
     */
    private PersistenceException target;

    /**
     * Aggregates all tests.
     *
     * @return test suite will be returned
     */
    public static Test suite() {
        return new TestSuite(PersistenceExceptionTest.class);
    }

    /**
     * Tests constructor 1.
     */
    public void testConstructor1() {
        PersistenceFault fault = new PersistenceFault();
        fault.setPersistenceMessage("abc");
        target = new PersistenceException(SOME_MESSAGE, fault);

        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertEquals("message", "abc", target.getPersistenceMessage());
        assertSame("fault info", fault, target.getFaultInfo());
    }

    /**
     * Tests constructor 1 for null fault.
     */
    public void testConstructor1ForNullFault() {
        target = new PersistenceException(SOME_MESSAGE, (PersistenceFault) null);

        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertNull("message", target.getPersistenceMessage());
        assertNull("fault info", target.getFaultInfo());
    }

    /**
     * Tests constructor 2.
     */
    public void testConstructor2() {
        PersistenceFault fault = new PersistenceFault();
        fault.setPersistenceMessage("12");
        target = new PersistenceException(SOME_MESSAGE, fault, SOME_CAUSE);

        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertSame("Cause of exception", SOME_CAUSE, target.getCause());
        assertEquals("message", "12", target.getPersistenceMessage());
        assertSame("fault info", fault, target.getFaultInfo());
    }

    /**
     * Tests constructor 2 for null fault.
     */
    public void testConstructor2ForNullFault() {
        target = new PersistenceException(SOME_MESSAGE, null, SOME_CAUSE);

        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertSame("Cause of exception", SOME_CAUSE, target.getCause());
        assertNull("message", target.getPersistenceMessage());
        assertNull("fault info", target.getFaultInfo());
    }

    /**
     * Test constructor 3.
     */
    public void testConstructor3() {
        target = new PersistenceException(SOME_MESSAGE, "3");
        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertEquals("message", "3", target.getPersistenceMessage());
        assertNull("fault info", target.getFaultInfo());
    }

    /**
     * Test constructor 4.
     */
    public void testConstructor4() {
        target = new PersistenceException(SOME_MESSAGE, SOME_CAUSE, "4");
        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertSame("Cause of exception", SOME_CAUSE, target.getCause());
        assertEquals("message", "4", target.getPersistenceMessage());
        assertNull("fault info", target.getFaultInfo());
    }

    /**
     * Test constructor 5.
     */
    public void testConstructor5() {
        target = new PersistenceException(SOME_MESSAGE, SOME_DATA, "5");
        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertEquals("message", "5", target.getPersistenceMessage());
        assertNull("fault info", target.getFaultInfo());
    }

    /**
     * Test constructor 6.
     */
    public void testConstructor6() {
        target = new PersistenceException(SOME_MESSAGE, SOME_CAUSE, SOME_DATA, "6");
        assertEquals("Message of exception", SOME_MESSAGE, target.getMessage());
        assertSame("Cause of exception", SOME_CAUSE, target.getCause());
        assertEquals("message", "6", target.getPersistenceMessage());
        assertNull("fault info", target.getFaultInfo());
    }
}
