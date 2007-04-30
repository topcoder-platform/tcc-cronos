/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import com.topcoder.chat.message.pool.Message;
import junit.framework.TestCase;

/**
 * Test cases for the <code>MessageTrackerException</code> class.
 * The getter methods of this exception will be tested in the methods
 * which test the constructors, for avoiding to duplicate unecessarily the code.
 *
 * @author marius_neo
 * @version 1.0
 */
public class MessageTrackerExceptionTestCase extends TestCase {

    /**
     * Represents a <c>Message</c> instance used for testing purposes.
     * Is being used as parameter for the exception's constructor.
     */
    private final Message message = new ChatMessage();

    /**
     * Represents the <c>userId</c> used as parameter for the exception's constructor.
     */
    private final long userId = 123;

    /**
     * Represents the <c>sessionId</c> used as parameter for the exception's constructor.
     */
    private final long sessionId = 100;

    /**
     * Represents the <c>cause</c> exception used in testing
     * <c>MessageTrackerException(String, Throwable, Message, long, long)</c> constructor.
     */
    private final Throwable cause = new Exception("I am the cause exception");

    /**
     * Tests if this class inherits from <c>MessengerException</c> class as requested
     * in component specification.
     */
    public void testInheritance() {
        MessageTrackerException e = new MessageTrackerException("Hello there", message, userId, sessionId);
        assertTrue("This class should inherit MessengerException", e instanceof MessengerException);
    }

    /**
     * Tests the <code>MessageTrackerException(String, Message, long, long)</code> constructor
     * and the getter methods of this exception.Simply verifies that the correct
     * parameters are set in the constructor.
     */
    public void testMessageTrackerExceptionStringMessageLongLong() {
        MessageTrackerException e = new MessageTrackerException("Hello there", message, userId, sessionId);
        assertEquals("The message must be set in the constructor!", "Hello there", e.getMessage());
        assertEquals("The message instances should be the same", message, e.getTrackedMessage());
        assertEquals("The user ids should be equal", userId, e.getUserId());
        assertEquals("The session ids should be equal", sessionId, e.getSessionId());
    }

    /**
     * Tests the <code>MessageTrackerException(String, Throwable, Message, long, long)</code> constructor
     * and the getter methods of this exception.Simply verifies that the correct
     * parameters are set in the constructor.
     */
    public void testMessageTrackerExceptionStringThrowableMessageLongLong() {
        MessageTrackerException e = new MessageTrackerException("I was caused by me!"
            , cause, message, userId, sessionId);
        assertEquals("The cause must be set in the constructor!", cause, e.getCause());
        assertEquals("The message instances should be the same", message, e.getTrackedMessage());
        assertEquals("The user ids should be equal", userId, e.getUserId());
        assertEquals("The session ids should be equal", sessionId, e.getSessionId());
    }
}
