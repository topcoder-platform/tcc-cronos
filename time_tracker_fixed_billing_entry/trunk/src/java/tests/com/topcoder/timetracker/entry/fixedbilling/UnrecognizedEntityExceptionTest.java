/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test cases for <code>{@link UnrecognizedEntityException}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class UnrecognizedEntityExceptionTest extends TestCase {
    /** The long instance for testing. */
    private long id;

    /** The String instance for testing. */
    private String msg;

    /** The UnrecognizedEntityException instance for testing. */
    private UnrecognizedEntityException exception;

    /**
     * Set up the initial values.
     */
    public void setUp() {
        id = 1;
        msg = "message";
    }

    /**
     * Test the <code>{@link UnrecognizedEntityException#UnrecognizedEntityException(long)}</code> with success
     * process.
     */
    public void testConstructor_long_Success() {
        exception = new UnrecognizedEntityException(id);
        assertNotNull("Unable to create the instance.", exception);
        assertNotNull("The return message should not be null.", exception.getMessage());
        assertEquals("The return id should be same.", id, exception.getId());
    }

    /**
     * Test the <code>{@link UnrecognizedEntityException#UnrecognizedEntityException(long, String)}</code> with success
     * process.
     */
    public void testConstructor_longString_Success() {
        exception = new UnrecognizedEntityException(id, msg);
        assertNotNull("Unable to create the instance.", exception);
        assertEquals("The return message should be same.", msg, exception.getMessage());
        assertEquals("The return id should be same.", id, exception.getId());
    }

    /**
     * Test the <code>{@link UnrecognizedEntityException#getId()}</code> with success process.
     */
    public void testGetId_Success() {
        testConstructor_longString_Success();
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(UnrecognizedEntityExceptionTest.class);
    }
}
