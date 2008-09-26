/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit tests for <code>ForumEntityNotFoundException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ForumEntityNotFoundExceptionUnitTest extends TestCase {
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
        return new TestSuite(ForumEntityNotFoundExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ForumEntityNotFoundException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        ForumEntityNotFoundException exception = new ForumEntityNotFoundException(MESSAGE);
        assertNotNull("Unable to create ForumEntityNotFoundException instance.",
            exception);
        assertTrue("The ForumEntityNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ForumEntityNotFoundException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        ForumEntityNotFoundException exception = new ForumEntityNotFoundException((String) null);
        assertNotNull("Unable to create ForumEntityNotFoundException instance.",
            exception);
        assertTrue("The ForumEntityNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ForumEntityNotFoundException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrExp() {
        ForumEntityNotFoundException exception = new ForumEntityNotFoundException(MESSAGE,
                DATA);
        assertNotNull("Unable to create ForumEntityNotFoundException instance.",
            exception);
        assertTrue("The ForumEntityNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ForumEntityNotFoundException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and exception data.
     * </p>
     */
    public void testCtorStrExp_Null() {
        ForumEntityNotFoundException exception = new ForumEntityNotFoundException(null,
                (ExceptionData) null);
        assertNotNull("Unable to create ForumEntityNotFoundException instance.",
            exception);
        assertTrue("The ForumEntityNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ForumEntityNotFoundException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        ForumEntityNotFoundException exception = new ForumEntityNotFoundException(MESSAGE,
                CAUSE);
        assertNotNull("Unable to create ForumEntityNotFoundException instance.",
            exception);
        assertTrue("The ForumEntityNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ForumEntityNotFoundException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        ForumEntityNotFoundException exception = new ForumEntityNotFoundException(null,
                (Throwable) null);
        assertNotNull("Unable to create ForumEntityNotFoundException instance.",
            exception);
        assertTrue("The ForumEntityNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ForumEntityNotFoundException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowableExp() {
        ForumEntityNotFoundException exception = new ForumEntityNotFoundException(MESSAGE,
                CAUSE, DATA);
        assertNotNull("Unable to create ForumEntityNotFoundException instance.",
            exception);
        assertTrue("The ForumEntityNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ForumEntityNotFoundException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null arguments.
     * </p>
     */
    public void testCtorStrThrowableExp_Null() {
        ForumEntityNotFoundException exception = new ForumEntityNotFoundException(null,
                null, null);
        assertNotNull("Unable to create ForumEntityNotFoundException instance.",
            exception);
        assertTrue("The ForumEntityNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
