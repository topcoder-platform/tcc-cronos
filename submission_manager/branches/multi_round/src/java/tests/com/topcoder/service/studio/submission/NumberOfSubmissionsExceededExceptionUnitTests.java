/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link NumberOfsubmissionsExceededException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.3
 */
public class NumberOfSubmissionsExceededExceptionUnitTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Represents a throwable cause.
     */
    private static final Throwable CAUSE = new Exception("UnitTest");

    /**
     * <p>
     * Represents the exception data.
     * </p>
     */
    private static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * <p>
     * Represents the application code set in exception data.
     * </p>
     */
    private static final String APPLICATION_CODE = "Accuracy";

    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * unit test for
     * <code> {@link NumberOfSubmissionsExceededException#NumberOfSubmissionsExceededException(String)} </code>
     */
    public void testNumberOfSubmissionsExceededExceptionString() {
        NumberOfSubmissionsExceededException e = new NumberOfSubmissionsExceededException(DETAIL_MESSAGE);
        assertEquals(DETAIL_MESSAGE, e.getMessage());
    }

    /**
     * unit test for
     * <code> {@link NumberOfSubmissionsExceededException#NumberOfSubmissionsExceededException(String, Throwable)} </code>
     */
    public void testNumberOfSubmissionsExceededExceptionData() {
        NumberOfSubmissionsExceededException e = new NumberOfSubmissionsExceededException(DETAIL_MESSAGE,
                CAUSE);
        assertEquals(DETAIL_MESSAGE, e.getMessage());
        assertEquals(CAUSE, e.getCause());
    }

    /**
     * unit test for
     * <code> {@link NumberOfSubmissionsExceededException#NumberOfSubmissionsExceededException(String, ExceptionData)} </code>
     */
    public void testNumberOfSubmissionsExceededExceptionCause() {
        NumberOfSubmissionsExceededException e = new NumberOfSubmissionsExceededException(DETAIL_MESSAGE,
                EXCEPTION_DATA);
        assertEquals(DETAIL_MESSAGE, e.getMessage());
        assertEquals(APPLICATION_CODE, e.getApplicationCode());
    }

    /**
     * unit test for
     * <code> {@link NumberOfSubmissionsExceededException#NumberOfSubmissionsExceededException(String, Throwable, ExceptionData)} </code>
     */
    public void testNumberOfSubmissionsExceededExceptionDataCause() {
        NumberOfSubmissionsExceededException e = new NumberOfSubmissionsExceededException(DETAIL_MESSAGE,
                CAUSE, EXCEPTION_DATA);
        assertEquals(DETAIL_MESSAGE, e.getMessage());
        assertEquals(CAUSE, e.getCause());
        assertEquals(APPLICATION_CODE, e.getApplicationCode());
    }
    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(NumberOfSubmissionsExceededExceptionUnitTests.class);
    }
}
