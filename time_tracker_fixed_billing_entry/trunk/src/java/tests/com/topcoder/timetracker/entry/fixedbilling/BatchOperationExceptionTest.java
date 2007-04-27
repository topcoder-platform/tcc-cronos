/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test cases for <code>{@link BatchOperationException}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class BatchOperationExceptionTest extends TestCase {
    /** The String instance for testing. */
    private String message;

    /** The Throwable instance for testing. */
    private Throwable[] causes;

    /** The BatchOperationException instance for testing. */
    private BatchOperationException exception;

    /**
     * Set up the initial values.
     */
    public void setUp() {
        message = "msg";
        causes = new Exception[3];
    }

    /**
     * Test the <code>{@link BatchOperationException#BatchOperationException(String, Throwable[])}</code> with success
     * process.
     */
    public void testConstructor_StringThrowableArray_Success() {
        exception = new BatchOperationException(message, causes);
        assertNotNull("Unable to create the instance.", exception);
        assertEquals("The return message should be same.", message, exception.getMessage());
        assertEquals("The return array should be same.", causes, exception.getCauses());
    }

    /**
     * Test the <code>{@link BatchOperationException#getCauses()}</code> with success process.
     */
    public void testGetCauses_Success() {
        testConstructor_StringThrowableArray_Success();
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(BatchOperationExceptionTest.class);
    }
}
