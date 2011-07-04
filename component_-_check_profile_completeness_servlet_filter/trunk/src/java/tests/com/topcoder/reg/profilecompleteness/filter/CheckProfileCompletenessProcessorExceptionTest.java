/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Unit tests for <code>CheckProfileCompletenessProcessorException</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class CheckProfileCompletenessProcessorExceptionTest extends TestCase {

    /**
     * Accuracy test for the constructor
     * <code>CheckProfileCompletenessProcessorException(String)</code>.
     *
     * Instance should be created successfully.
     */
    public void testCtorWithMsg() {
        CheckProfileCompletenessProcessorException exception = new CheckProfileCompletenessProcessorException(
            "test_message");
        assertNotNull(exception);
        assertEquals("test_message", exception.getMessage());
        assertNull(exception.getCause());
        assertNull(exception.getErrorCode());
    }

    /**
     * Accuracy test for the constructor
     * <code>CheckProfileCompletenessProcessorException(String message, Throwable cause)</code>.
     *
     * Instance should be created successfully.
     */
    public void testCtorWithMsgAndCause() {
        Throwable cause = new NullPointerException();
        CheckProfileCompletenessProcessorException exception = new CheckProfileCompletenessProcessorException(
            "test_message", cause);
        assertNotNull(exception);
        assertEquals("test_message", exception.getMessage());
        assertSame(cause, exception.getCause());
        assertNull(exception.getErrorCode());
    }

    /**
     * Accuracy test for the constructor
     * <code>CheckProfileCompletenessProcessorException(String message, ExceptionData data)</code> .
     *
     * Instance should be created successfully.
     */
    public void testCtorWithMsgAndData() {
        ExceptionData data = new ExceptionData();
        data.setErrorCode("CODE123");
        CheckProfileCompletenessProcessorException exception = new CheckProfileCompletenessProcessorException(
            "test_message", data);
        assertNotNull(exception);
        assertEquals("test_message", exception.getMessage());
        assertEquals("CODE123", exception.getErrorCode());
    }

    /**
     * Accuracy test for the constructor
     * <code>CheckProfileCompletenessProcessorException(String message, Throwable cause, ExceptionData data)</code>
     * .
     *
     * Instance should be created successfully.
     *
     */
    public void testCtorWithMsgAndDataAndCause() {
        ExceptionData data = new ExceptionData();
        data.setErrorCode("CODE123");
        Throwable cause = new NullPointerException();
        CheckProfileCompletenessProcessorException exception = new CheckProfileCompletenessProcessorException(
            "test_message", cause, data);
        assertNotNull(exception);
        assertSame(cause, exception.getCause());
        assertEquals("test_message", exception.getMessage());
        assertEquals("CODE123", exception.getErrorCode());
    }

}
