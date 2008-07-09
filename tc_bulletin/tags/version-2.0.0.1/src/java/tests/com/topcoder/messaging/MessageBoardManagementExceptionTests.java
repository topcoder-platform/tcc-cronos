/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;


/**
 * The unit test for the class {@link MessageBoardManagementException}.
 * @author yqw
 * @version 2.0
 */
public class MessageBoardManagementExceptionTests extends TestCase {
    /**
     * The accuracy test for the method
     * {@link MessageBoardManagementException#MessageBoardManagementException(String)}.
     */
    public void testCtor_1() {
        MessageBoardManagementException e = new MessageBoardManagementException(
                "test");
        assertNotNull("The instanse should not be null.", e);
        assertEquals("The message is incorrect.", "test", e.getMessage());
    }

    /**
     * The accuracy test for the method
     * {@link MessageBoardManagementException#MessageBoardManagementException(String message, Throwable cause)}.
     */
    public void testCtor_2() {
        Throwable t = new Exception();
        MessageBoardManagementException e = new MessageBoardManagementException("test",
                t);
        assertNotNull("The instanse should not be null.", e);
        assertEquals("The message is incorrect.", "test", e.getMessage());
        assertEquals("The inner exception is incorrect.", t, e.getCause());
    }

    /**
     * The accuracy test for the method
     * {@link MessageBoardManagementException#MessageBoardManagementException(String message, ExceptionData data)}.
     */
    public void testCtor_3() {
        ExceptionData t = new ExceptionData();
        t.setApplicationCode("test");

        MessageBoardManagementException e = new MessageBoardManagementException("test",
                t);
        assertNotNull("The instanse should not be null.", e);
        assertEquals("The message is incorrect.", "test", e.getMessage());
        assertEquals("test", e.getApplicationCode());
    }

    /**
     * The accuracy test for the method
     * {@link MessageBoardManagementException#MessageBoardManagementException(String message, Throwable cause,
                          ExceptionData data)}.
     */
    public void testCtor_4() {
        ExceptionData t = new ExceptionData();
        t.setApplicationCode("test");

        Throwable inner = new Exception();
        MessageBoardManagementException e = new MessageBoardManagementException("test",
                inner, t);
        assertNotNull("The instanse should not be null.", e);
        assertEquals("The message is incorrect.", "test", e.getMessage());
        assertEquals("The inner exception is incorrect.", inner, e.getCause());
        assertEquals("test", e.getApplicationCode());
    }
}
