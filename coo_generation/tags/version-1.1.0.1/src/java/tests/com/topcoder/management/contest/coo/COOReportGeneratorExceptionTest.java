/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test case of {@link COOReportGeneratorException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class COOReportGeneratorExceptionTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String MESSAGE = "the error message";
    /**
     * <p>
     * The exception data used for testing.
     * </p>
     */
    private static final ExceptionData EXCEPTIONDATA = new ExceptionData();
    /**
     * <p>
     * The inner exception for testing.
     * </p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>
     * Test method for
     * {@link COOReportGeneratorException#COOReportGeneratorException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testCOOReportGeneratorExceptionString() {
        COOReportGeneratorException exception = new COOReportGeneratorException(MESSAGE);
        assertNotNull("Unable to instantiate COOReportGeneratorException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link COOReportGeneratorException#COOReportGeneratorException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testCOOReportGeneratorExceptionStringThrowable() {
        COOReportGeneratorException exception = new COOReportGeneratorException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate COOReportGeneratorException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Test method for
     * {@link COOReportGeneratorException#COOReportGeneratorException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testCOOReportGeneratorExceptionStringExceptionData() {
        COOReportGeneratorException exception = new COOReportGeneratorException(MESSAGE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate COOReportGeneratorException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link COOReportGeneratorException#COOReportGeneratorException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testCOOReportGeneratorExceptionStringCauseExceptionData() {
        COOReportGeneratorException exception = new COOReportGeneratorException(MESSAGE, CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate COOReportGeneratorException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }
}
