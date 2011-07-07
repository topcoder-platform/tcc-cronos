/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl;
import junit.framework.TestCase;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Unit tests for <code>ProfileCompletenessCheckerException</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class ProfileCompletenessCheckerExceptionTest extends TestCase {

    /**
     * Accuracy test for the constructor <code>ProfileCompletenessCheckerException(String)</code>.
     *
     * Instance should be created successfully.
     */
    public void testCtorWithMsg() {
        ProfileCompletenessCheckerException exception = new ProfileCompletenessCheckerException(
            "test_message");
        assertNotNull(exception);
        assertEquals("test_message", exception.getMessage());
        assertNull(exception.getCause());
        assertNull(exception.getErrorCode());
    }

    /**
     * Accuracy test for the constructor
     * <code>ProfileCompletenessCheckerException(String message, Throwable cause)</code>.
     *
     * Instance should be created successfully.
     */
    public void testCtorWithMsgAndCause() {
        Throwable cause = new NullPointerException();
        ProfileCompletenessCheckerException exception = new ProfileCompletenessCheckerException(
            "test_message", cause);
        assertNotNull(exception);
        assertEquals("test_message", exception.getMessage());
        assertSame(cause, exception.getCause());
        assertNull(exception.getErrorCode());
    }

    /**
     * Accuracy test for the constructor
     * <code>ProfileCompletenessCheckerException(String message, ExceptionData data)</code> .
     *
     * Instance should be created successfully.
     */
    public void testCtorWithMsgAndData() {
        ExceptionData data = new ExceptionData();
        data.setErrorCode("CODE123");
        ProfileCompletenessCheckerException exception = new ProfileCompletenessCheckerException(
            "test_message", data);
        assertNotNull(exception);
        assertEquals("test_message", exception.getMessage());
        assertEquals("CODE123", exception.getErrorCode());
    }

    /**
     * Accuracy test for the constructor
     * <code>ProfileCompletenessCheckerException(String message, Throwable cause, ExceptionData data)</code>
     * .
     *
     * Instance should be created successfully.
     *
     */
    public void testCtorWithMsgAndDataAndCause() {
        ExceptionData data = new ExceptionData();
        data.setErrorCode("CODE123");
        Throwable cause = new NullPointerException();
        ProfileCompletenessCheckerException exception = new ProfileCompletenessCheckerException(
            "test_message", cause, data);
        assertNotNull(exception);
        assertSame(cause, exception.getCause());
        assertEquals("test_message", exception.getMessage());
        assertEquals("CODE123", exception.getErrorCode());
    }

}
