/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.util.errorhandling.ExceptionData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for StudioServiceException class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StudioServiceExceptionTest extends TestCase {
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
     * Aggregates all tests.
     *
     * @return test suite will be returned
     */
    public static Test suite() {
        return new TestSuite(StudioServiceExceptionTest.class);
    }

    /**
     * Test first constructor.
     */
    public void testConstructor1() {
        try {
            throw new StudioServiceException(SOME_MESSAGE);
        } catch (StudioServiceException ex) {
            assertEquals("Message of exception", SOME_MESSAGE, ex.getMessage());
        }
    }

    /**
     * Test second constructor.
     */
    public void testConstructor2() {
        try {
            throw new StudioServiceException(SOME_MESSAGE, SOME_CAUSE);
        } catch (StudioServiceException ex) {
            assertEquals("Message of exception", SOME_MESSAGE, ex.getMessage());
            assertSame("Cause of exception", SOME_CAUSE, ex.getCause());
        }
    }

    /**
     * Test third constructor.
     */
    public void testConstructor3() {
        try {
            throw new StudioServiceException(SOME_MESSAGE, SOME_DATA);
        } catch (StudioServiceException ex) {
            assertEquals("Message of exception", SOME_MESSAGE, ex.getMessage());
        }
    }

    /**
     * Test forth constructor.
     */
    public void testConstructor4() {
        try {
            throw new StudioServiceException(SOME_MESSAGE, SOME_CAUSE, SOME_DATA);
        } catch (StudioServiceException ex) {
            assertEquals("Message of exception", SOME_MESSAGE, ex.getMessage());
            assertSame("Cause of exception", SOME_CAUSE, ex.getCause());
        }
    }
}
