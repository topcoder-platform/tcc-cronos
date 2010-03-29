/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit tests for <code>ContestManagementException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestManagementExceptionUnitTest extends TestCase {
    /**
    * <p>The error message used for testing.</p>
    */
    private static final String MESSAGE = "the error message";

    /**
     * <p>The ExceptionData used for testing.</p>
     */
    private static final ExceptionData DATA = new ExceptionData();

    /**
     * <p>The inner exception for testing.</p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestManagementExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ContestManagementException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        ContestManagementException exception = new ContestManagementException(MESSAGE);
        assertNotNull("Unable to create ContestManagementException instance.",
            exception);
        assertTrue("The ContestManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ContestManagementException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        ContestManagementException exception = new ContestManagementException((String) null);
        assertNotNull("Unable to create ContestManagementException instance.",
            exception);
        assertTrue("The ContestManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ContestManagementException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrExp() {
        ContestManagementException exception = new ContestManagementException(MESSAGE,
                DATA);
        assertNotNull("Unable to create ContestManagementException instance.",
            exception);
        assertTrue("The ContestManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ContestManagementException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and exception data.
     * </p>
     */
    public void testCtorStrExp_Null() {
        ContestManagementException exception = new ContestManagementException(null,
                (ExceptionData) null);
        assertNotNull("Unable to create ContestManagementException instance.",
            exception);
        assertTrue("The ContestManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ContestManagementException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        ContestManagementException exception = new ContestManagementException(MESSAGE,
                CAUSE);
        assertNotNull("Unable to create ContestManagementException instance.",
            exception);
        assertTrue("The ContestManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ContestManagementException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        ContestManagementException exception = new ContestManagementException(null,
                (Throwable) null);
        assertNotNull("Unable to create ContestManagementException instance.",
            exception);
        assertTrue("The ContestManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ContestManagementException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowableExp() {
        ContestManagementException exception = new ContestManagementException(MESSAGE,
                CAUSE, DATA);
        assertNotNull("Unable to create ContestManagementException instance.",
            exception);
        assertTrue("The ContestManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ContestManagementException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null arguments.
     * </p>
     */
    public void testCtorStrThrowableExp_Null() {
        ContestManagementException exception = new ContestManagementException(null,
                null, null);
        assertNotNull("Unable to create ContestManagementException instance.",
            exception);
        assertTrue("The ContestManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
