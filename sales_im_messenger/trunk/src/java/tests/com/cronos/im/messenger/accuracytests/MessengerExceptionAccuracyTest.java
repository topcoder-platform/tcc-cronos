/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.MessengerException;

import junit.framework.TestCase;


/**
 * Tests the functionality for class <code>MessengerExceptionAccuracyTest.java</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class MessengerExceptionAccuracyTest extends TestCase {
    /**
     * Test method for 'MessengerException(String)'.
     */
    public void testMessengerExceptionString() {
        MessengerException exception = new MessengerException("msg");
        assertTrue("Test method for 'MessengerException(String)' failed.", exception instanceof MessengerException);
    }

    /**
     * Test method for 'MessengerException(String)'.
     */
    public void testMessengerExceptionString2() {
        MessengerException exception = new MessengerException("msg");
        assertEquals("Test method for 'MessengerException(String)' failed.", "msg", exception.getMessage());
    }

    /**
     * Test method for 'MessengerException.MessengerException(String, Throwable)'
     */
    public void testMessengerExceptionStringThrowable() {
        Exception cause = new Exception("Root");
        MessengerException exception = new MessengerException("msg", cause);
        assertEquals("Test method for 'MessengerException(String, Throwable)' failed.", cause, exception.getCause());
    }
}
