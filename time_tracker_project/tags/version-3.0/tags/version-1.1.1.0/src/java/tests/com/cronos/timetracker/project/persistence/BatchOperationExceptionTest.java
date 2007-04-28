/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;


/**
 * Unit tests for BatchOperationException implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class BatchOperationExceptionTest extends TestCase {
    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BatchOperationExceptionTest.class);

        return suite;
    }

    /**
     * Test of constructor with message and null causes. Verifies it by getting the message and causes being set.
     */
    public void testConstructor_NullCauses() {
        String message = "message";
        BatchOperationException exception = new BatchOperationException(message, null);

        assertEquals("Fails to set message", message, exception.getMessage());
        assertNull("Fails to set causes", exception.getCauses());
    }

    /**
     * Test of constructor with message and non-null causes. Verifies it by getting the message and causes being set.
     */
    public void testConstructor_NonNullCauses() {
        String message = "message";
        Throwable[] causes = new Throwable[] {new Exception()};
        BatchOperationException exception = new BatchOperationException(message, causes);

        assertEquals("Fails to set message", message, exception.getMessage());
        assertTrue("Fails to set causes", Arrays.equals(causes, exception.getCauses()));
    }
}
