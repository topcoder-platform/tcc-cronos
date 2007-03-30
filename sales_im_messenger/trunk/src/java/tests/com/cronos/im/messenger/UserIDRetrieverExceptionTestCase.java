/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import junit.framework.TestCase;

/**
 * Test cases for the <code>UserIDRetrieverException</code> class.
 *
 * @author marius_neo
 * @version 1.0
 */
public class UserIDRetrieverExceptionTestCase extends TestCase {
    /**
     * Tests if this class inherits from <c>MessengerException</c> class as requested
     * in component specification.
     */
    public void testInheritance() {
        UserIDRetrieverException e = new UserIDRetrieverException("Hello there");
        assertTrue("This class should inherit MessengerException", e instanceof MessengerException);
    }

    /**
     * Tests the <code>UserIDRetrieverException(String)</code> constructor.
     * Simply verifies that the correct parameters are set in the constructor.
     */
    public void testUserIDRetrieverExceptionString() {
        UserIDRetrieverException e = new UserIDRetrieverException("Hello there");
        assertEquals("The message must be set in the constructor!", "Hello there", e.getMessage());
    }

    /**
     * Tests the <code>UserIDRetrieverException(String, Throwable)</code> constructor.
     * Simply verifies that the correct parameters are set in the constructor.
     */
    public void testUserIDRetrieverExceptionStringThrowable() {
        Exception me = new Exception("testException");
        UserIDRetrieverException e = new UserIDRetrieverException("I was caused by me!", me);
        assertEquals("The cause must be set in the constructor!", me, e.getCause());
    }
}