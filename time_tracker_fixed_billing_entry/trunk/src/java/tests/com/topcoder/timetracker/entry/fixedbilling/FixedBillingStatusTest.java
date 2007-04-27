/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test cases for <code>{@link FixedBillingStatus}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class FixedBillingStatusTest extends TestCase {
    /** The String instance for testing. */
    private String description;

    /** The FixedBillingStatus instance for testing. */
    private FixedBillingStatus status;

    /**
     * Set up the initial values.
     */
    public void setUp() {
        description = "description";
        status = new FixedBillingStatus();
    }

    /**
     * Test the <code>{@link FixedBillingStatus#FixedBillingStatus()}</code> with success process.
     */
    public void testConstructor_Success() {
        assertNotNull("Unable to create the instance.", status);
    }

    /**
     * Test the <code>{@link FixedBillingStatus#getDescription()}</code> with success process.
     */
    public void testGetDescription_Success() {
        testSetDescription_String_Success();
    }

    /**
     * Test the <code>{@link FixedBillingStatus#setDescription(String)}</code> with null String. Should throw an
     * IllegalArgumentException here.
     */
    public void testSetDescription_String_NullString() {
        try {
            description = null;
            status.setDescription(description);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatus#setDescription(String)}</code> with empty String. Should throw an
     * IllegalArgumentException here.
     */
    public void testSetDescription_String_EmptyString() {
        try {
            description = " ";
            status.setDescription(description);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatus#setDescription(String)}</code> with success process.
     */
    public void testSetDescription_String_Success() {
        status.setDescription(description);
        assertEquals("The return result should be same.", description, status.getDescription());
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(FixedBillingStatusTest.class);
    }
}
