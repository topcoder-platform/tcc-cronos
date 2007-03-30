/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import junit.framework.TestCase;

/**
 * Test cases for the <code>MessengerException</code> class.
 *
 * @author marius_neo
 * @version 1.0
 */
public class MessengerExceptionTestCase extends TestCase {
    /**
     * Tests the <code>MessengerException(String)</code> constructor.
     * Simply verifies that the correct parameters are set in the constructor.
     */
    public void testMessengerExceptionString() {
        MessengerException e = new MessengerException("Hello there");
        assertEquals("The message must be set in the constructor!", "Hello there", e.getMessage());
    }


    /**
     * Tests the <code>MessengerException(String, Throwable)</code> constructor.
     * Simply verifies that the correct parameters are set in the constructor.
     */
    public void testMessengerExceptionStringThrowable() {
        Exception me = new Exception("testException");
        MessengerException e = new MessengerException("I was caused by me!", me);
        assertEquals("The cause must be set in the constructor!", me, e.getCause());
    }
}