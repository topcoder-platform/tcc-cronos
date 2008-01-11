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
 * Unit tests for <code>UserNotFoundException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserNotFoundExceptionUnitTest extends TestCase {
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
        return new TestSuite(UserNotFoundExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>UserNotFoundException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        UserNotFoundException exception = new UserNotFoundException(MESSAGE);
        assertNotNull("Unable to create UserNotFoundException instance.",
            exception);
        assertTrue("The UserNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>UserNotFoundException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        UserNotFoundException exception = new UserNotFoundException((String) null);
        assertNotNull("Unable to create UserNotFoundException instance.",
            exception);
        assertTrue("The UserNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>UserNotFoundException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrExp() {
        UserNotFoundException exception = new UserNotFoundException(MESSAGE,
                DATA);
        assertNotNull("Unable to create UserNotFoundException instance.",
            exception);
        assertTrue("The UserNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>UserNotFoundException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and exception data.
     * </p>
     */
    public void testCtorStrExp_Null() {
        UserNotFoundException exception = new UserNotFoundException(null,
                (ExceptionData) null);
        assertNotNull("Unable to create UserNotFoundException instance.",
            exception);
        assertTrue("The UserNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>UserNotFoundException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        UserNotFoundException exception = new UserNotFoundException(MESSAGE,
                CAUSE);
        assertNotNull("Unable to create UserNotFoundException instance.",
            exception);
        assertTrue("The UserNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>UserNotFoundException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        UserNotFoundException exception = new UserNotFoundException(null,
                (Throwable) null);
        assertNotNull("Unable to create UserNotFoundException instance.",
            exception);
        assertTrue("The UserNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>UserNotFoundException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowableExp() {
        UserNotFoundException exception = new UserNotFoundException(MESSAGE,
                CAUSE, DATA);
        assertNotNull("Unable to create UserNotFoundException instance.",
            exception);
        assertTrue("The UserNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>UserNotFoundException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null arguments.
     * </p>
     */
    public void testCtorStrThrowableExp_Null() {
        UserNotFoundException exception = new UserNotFoundException(null, null,
                null);
        assertNotNull("Unable to create UserNotFoundException instance.",
            exception);
        assertTrue("The UserNotFoundException should be instance of JiveForumManagementException.",
            exception instanceof JiveForumManagementException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
