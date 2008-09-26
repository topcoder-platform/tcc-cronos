/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit tests for <code>JiveForumManagementException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JiveForumManagementExceptionUnitTest extends TestCase {
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
        return new TestSuite(JiveForumManagementExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>JiveForumManagementException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        JiveForumManagementException exception = new JiveForumManagementException(MESSAGE);
        assertNotNull("Unable to create JiveForumManagementException instance.",
            exception);
        assertTrue("The JiveForumManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>JiveForumManagementException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        JiveForumManagementException exception = new JiveForumManagementException((String) null);
        assertNotNull("Unable to create JiveForumManagementException instance.",
            exception);
        assertTrue("The JiveForumManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>JiveForumManagementException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrExp() {
        JiveForumManagementException exception = new JiveForumManagementException(MESSAGE,
                DATA);
        assertNotNull("Unable to create JiveForumManagementException instance.",
            exception);
        assertTrue("The JiveForumManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>JiveForumManagementException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and exception data.
     * </p>
     */
    public void testCtorStrExp_Null() {
        JiveForumManagementException exception = new JiveForumManagementException(null,
                (ExceptionData) null);
        assertNotNull("Unable to create JiveForumManagementException instance.",
            exception);
        assertTrue("The JiveForumManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>JiveForumManagementException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        JiveForumManagementException exception = new JiveForumManagementException(MESSAGE,
                CAUSE);
        assertNotNull("Unable to create JiveForumManagementException instance.",
            exception);
        assertTrue("The JiveForumManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>JiveForumManagementException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        JiveForumManagementException exception = new JiveForumManagementException(null,
                (Throwable) null);
        assertNotNull("Unable to create JiveForumManagementException instance.",
            exception);
        assertTrue("The JiveForumManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>JiveForumManagementException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowableExp() {
        JiveForumManagementException exception = new JiveForumManagementException(MESSAGE,
                CAUSE, DATA);
        assertNotNull("Unable to create JiveForumManagementException instance.",
            exception);
        assertTrue("The JiveForumManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>JiveForumManagementException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null arguments.
     * </p>
     */
    public void testCtorStrThrowableExp_Null() {
        JiveForumManagementException exception = new JiveForumManagementException(null,
                null, null);
        assertNotNull("Unable to create JiveForumManagementException instance.",
            exception);
        assertTrue("The JiveForumManagementException should be instance of BaseCriticalException.",
            exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
