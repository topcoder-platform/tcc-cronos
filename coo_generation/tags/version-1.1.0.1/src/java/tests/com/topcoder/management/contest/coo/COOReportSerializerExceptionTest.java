/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test case of {@link COOReportSerializerException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class COOReportSerializerExceptionTest extends TestCase {
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
     * {@link COOReportSerializerException#COOReportSerializerException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testCOOReportSerializerExceptionString() {
        COOReportSerializerException exception = new COOReportSerializerException(MESSAGE);
        assertNotNull("Unable to instantiate COOReportSerializerException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link COOReportSerializerException#COOReportSerializerException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testCOOReportSerializerExceptionStringThrowable() {
        COOReportSerializerException exception = new COOReportSerializerException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate COOReportSerializerException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Test method for
     * {@link COOReportSerializerException#COOReportSerializerException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testCOOReportSerializerExceptionStringExceptionData() {
        COOReportSerializerException exception = new COOReportSerializerException(MESSAGE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate COOReportSerializerException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for
     * {@link COOReportSerializerException#COOReportSerializerException()}. It
     * verifies the exception is created successfully.
     * </p>
     */
    public void testCOOReportSerializerExceptionStringCauseExceptionData() {
        COOReportSerializerException exception = new COOReportSerializerException(MESSAGE, CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate COOReportSerializerException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }
}
