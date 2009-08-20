/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit tests for <code>DocumentContentManagementException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DocumentContentManagementExceptionUnitTest extends TestCase {
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
        return new TestSuite(DocumentContentManagementExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DocumentContentManagementException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        DocumentContentManagementException exception = new DocumentContentManagementException(MESSAGE);
        assertNotNull("Unable to create DocumentContentManagementException instance.",
            exception);
        assertTrue("The DocumentContentManagementException should be instance of ContestManagementException.",
            exception instanceof ContestManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DocumentContentManagementException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        DocumentContentManagementException exception = new DocumentContentManagementException((String) null);
        assertNotNull("Unable to create DocumentContentManagementException instance.",
            exception);
        assertTrue("The DocumentContentManagementException should be instance of ContestManagementException.",
            exception instanceof ContestManagementException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DocumentContentManagementException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrExp() {
        DocumentContentManagementException exception = new DocumentContentManagementException(MESSAGE,
                DATA);
        assertNotNull("Unable to create DocumentContentManagementException instance.",
            exception);
        assertTrue("The DocumentContentManagementException should be instance of ContestManagementException.",
            exception instanceof ContestManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DocumentContentManagementException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and exception data.
     * </p>
     */
    public void testCtorStrExp_Null() {
        DocumentContentManagementException exception = new DocumentContentManagementException(null,
                (ExceptionData) null);
        assertNotNull("Unable to create DocumentContentManagementException instance.",
            exception);
        assertTrue("The DocumentContentManagementException should be instance of ContestManagementException.",
            exception instanceof ContestManagementException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DocumentContentManagementException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        DocumentContentManagementException exception = new DocumentContentManagementException(MESSAGE,
                CAUSE);
        assertNotNull("Unable to create DocumentContentManagementException instance.",
            exception);
        assertTrue("The DocumentContentManagementException should be instance of ContestManagementException.",
            exception instanceof ContestManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DocumentContentManagementException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        DocumentContentManagementException exception = new DocumentContentManagementException(null,
                (Throwable) null);
        assertNotNull("Unable to create DocumentContentManagementException instance.",
            exception);
        assertTrue("The DocumentContentManagementException should be instance of ContestManagementException.",
            exception instanceof ContestManagementException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DocumentContentManagementException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowableExp() {
        DocumentContentManagementException exception = new DocumentContentManagementException(MESSAGE,
                CAUSE, DATA);
        assertNotNull("Unable to create DocumentContentManagementException instance.",
            exception);
        assertTrue("The DocumentContentManagementException should be instance of ContestManagementException.",
            exception instanceof ContestManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DocumentContentManagementException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null arguments.
     * </p>
     */
    public void testCtorStrThrowableExp_Null() {
        DocumentContentManagementException exception = new DocumentContentManagementException(null,
                null, null);
        assertNotNull("Unable to create DocumentContentManagementException instance.",
            exception);
        assertTrue("The DocumentContentManagementException should be instance of ContestManagementException.",
            exception instanceof ContestManagementException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
