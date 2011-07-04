/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Unit tests for <code>CheckProfileCompletenessConfigurationException</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class CheckProfileCompletenessConfigurationExceptionTest extends TestCase {

    /**
     * Accuracy test for the constructor
     * <code>CheckProfileCompletenessConfigurationException(String)</code>.
     *
     * Instance should be created successfully.
     */
    public void testCtorWithMsg() {
        CheckProfileCompletenessConfigurationException exception = new CheckProfileCompletenessConfigurationException(
            "test_message");
        assertNotNull(exception);
        assertEquals("test_message", exception.getMessage());
        assertNull(exception.getCause());
        assertNull(exception.getErrorCode());
    }

    /**
     * Accuracy test for the constructor
     * <code>CheckProfileCompletenessConfigurationException(String message, Throwable cause)</code>.
     *
     * Instance should be created successfully.
     */
    public void testCtorWithMsgAndCause() {
        Throwable cause = new NullPointerException();
        CheckProfileCompletenessConfigurationException exception = new CheckProfileCompletenessConfigurationException(
            "test_message", cause);
        assertNotNull(exception);
        assertEquals("test_message", exception.getMessage());
        assertSame(cause, exception.getCause());
        assertNull(exception.getErrorCode());
    }

    /**
     * Accuracy test for the constructor
     * <code>CheckProfileCompletenessConfigurationException(String message, ExceptionData data)</code>
     * .
     *
     * Instance should be created successfully.
     */
    public void testCtorWithMsgAndData() {
        ExceptionData data = new ExceptionData();
        data.setErrorCode("CODE123");
        CheckProfileCompletenessConfigurationException exception = new CheckProfileCompletenessConfigurationException(
            "test_message", data);
        assertNotNull(exception);
        assertEquals("test_message", exception.getMessage());
        assertEquals("CODE123", exception.getErrorCode());
    }

    /**
     * Accuracy test for the constructor
     * <code>CheckProfileCompletenessConfigurationException(String message, Throwable cause, ExceptionData data)</code>
     * .
     *
     * Instance should be created successfully.
     *
     */
    public void testCtorWithMsgAndDataAndCause() {
        ExceptionData data = new ExceptionData();
        data.setErrorCode("CODE123");
        Throwable cause = new NullPointerException();
        CheckProfileCompletenessConfigurationException exception = new CheckProfileCompletenessConfigurationException(
            "test_message", cause, data);
        assertNotNull(exception);
        assertSame(cause, exception.getCause());
        assertEquals("test_message", exception.getMessage());
        assertEquals("CODE123", exception.getErrorCode());
    }

}
