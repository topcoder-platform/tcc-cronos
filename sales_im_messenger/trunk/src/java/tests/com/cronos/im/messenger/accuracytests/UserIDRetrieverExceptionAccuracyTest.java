/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.MessengerException;
import com.cronos.im.messenger.UserIDRetrieverException;


/**
 * Tests the functionality for class <code>UserIDRetrieverExceptionAccuracyTest.java</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class UserIDRetrieverExceptionAccuracyTest extends MessengerExceptionAccuracyTest {
    /**
     * Test method for 'UserIDRetrieverException(String)'.
     */
    public void testUserIDRetrieverExceptionString() {
        UserIDRetrieverException exception = new UserIDRetrieverException("msg");
        assertTrue("Test method for 'UserIDRetrieverException(String)' failed.", exception instanceof MessengerException);
    }

    /**
     * Test method for 'UserIDRetrieverException(String)'.
     */
    public void testUserIDRetrieverExceptionString2() {
        UserIDRetrieverException exception = new UserIDRetrieverException("msg");
        assertEquals("Test method for 'UserIDRetrieverException(String)' failed.", "msg", exception.getMessage());
    }

    /**
     * Test method for 'UserIDRetrieverException.UserIDRetrieverException(String, Throwable)'
     */
    public void testUserIDRetrieverExceptionStringThrowable() {
        Exception cause = new Exception("Root");
        UserIDRetrieverException exception = new UserIDRetrieverException("msg", cause);
        assertEquals("Test method for 'UserIDRetrieverException(String, Throwable)' failed.", cause,
            exception.getCause());
    }
}
