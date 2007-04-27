/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test cases for <code>{@link InvalidCompanyException}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class InvalidCompanyExceptionTest extends TestCase {
    /** The long instance for testing. */
    private long entryId;

    /** The long instance for testing. */
    private long otherId;

    /** The String instance for testing. */
    private String message;

    /** The InvalidCompanyException instance for testing. */
    private InvalidCompanyException exception;

    /**
     * Set up the initial values.
     */
    public void setUp() {
        entryId = 1;
        otherId = 2;
        message = "msg";
    }

    /**
     * Test the <code>{@link InvalidCompanyException#InvalidCompanyException(long, long)}</code> with success process.
     */
    public void testConstructor_longlong_Success() {
        exception = new InvalidCompanyException(entryId, otherId);
        assertNotNull("Unable to create the instance.", exception);
        assertNotNull("The return message should not be null.", exception.getMessage());
        assertEquals("The return id should be same.", entryId, exception.getFixedBillingEntryCompanyId());
        assertEquals("The return id should be same.", otherId, exception.getOtherEntityCompanyId());
    }

    /**
     * Test the <code>{@link InvalidCompanyException#InvalidCompanyException(long, long, String)}</code> with success
     * process.
     */
    public void testConstructor_longlongString_Success() {
        exception = new InvalidCompanyException(entryId, otherId, message);
        assertNotNull("Unable to create the instance.", exception);
        assertEquals("The return message should be same.", message, exception.getMessage());
        assertEquals("The return id should be same.", entryId, exception.getFixedBillingEntryCompanyId());
        assertEquals("The return id should be same.", otherId, exception.getOtherEntityCompanyId());
    }

    /**
     * Test the <code>{@link InvalidCompanyException#getFixedBillingEntryCompanyId()}</code> with success process.
     */
    public void testGetFixedBillingEntryCompanyId_Success() {
        testConstructor_longlongString_Success();
    }

    /**
     * Test the <code>{@link InvalidCompanyException#getOtherEntityCompanyId()}</code> with success process.
     */
    public void testGetOtherEntityCompanyId_Success() {
        testConstructor_longlongString_Success();
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(InvalidCompanyExceptionTest.class);
    }
}
