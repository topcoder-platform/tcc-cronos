/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.MessageTrackerException;
import com.cronos.im.messenger.MessengerException;

import com.topcoder.chat.message.pool.Message;


/**
 * Tests the functionality for class <code>MessageTrackerException</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class MessageTrackerExceptionAccuracyTest extends MessengerExceptionAccuracyTest {
    /**
     * Test method for 'MessageTrackerException(String)'.
     */
    public void testUserIDRetrieverExceptionString() {
        MessageTrackerException exception = new MessageTrackerException("msg", new ChatMessage(), 2133, 235);
        assertTrue("Test method for 'MessageTrackerException(String)' failed.", exception instanceof MessengerException);
    }

    /**
     * Test method for 'MessageTrackerException(String)'.
     */
    public void testUserIDRetrieverExceptionString2() {
        MessageTrackerException exception = new MessageTrackerException("msg", new ChatMessage(), 2133, 235);
        assertEquals("Test method for 'MessageTrackerException(String)' failed.", "msg", exception.getMessage());
    }

    /**
     * Test method for 'MessageTrackerException.MessageTrackerException(String, Throwable)'
     */
    public void testUserIDRetrieverExceptionStringThrowable() {
        Exception cause = new Exception("Root");
        MessageTrackerException exception = new MessageTrackerException("msg", cause, new ChatMessage(), 2133, 235);
        assertEquals("Test method for 'MessageTrackerException(String, Throwable)' failed.", cause, exception.getCause());
    }

    /**
     * Test method for 'MessageTrackerException.getTrackedMessage()'.
     */
    public void testGetTrackedMessage() {
        Message msg = new ChatMessage();
        MessageTrackerException exception = new MessageTrackerException("msg", msg, 2133, 235);
        assertEquals("Test method for 'MessageTrackerException.getTrackedMessage()' failed.", msg,
            exception.getTrackedMessage());
    }

    /**
     * Test method for 'MessageTrackerException.getUserId()'.
     */
    public void testGetUserId() {
        MessageTrackerException exception = new MessageTrackerException("msg", new ChatMessage(), 2133, 235);
        assertEquals("Test method for 'MessageTrackerException.getUserId()' failed.", 2133, exception.getUserId());
    }

    /**
     * Test method for 'MessageTrackerException.getSessionId()'.
     */
    public void testGetSessionId() {
        MessageTrackerException exception = new MessageTrackerException("msg", new ChatMessage(), 2133, 235);
        assertEquals("Test method for 'MessageTrackerException.getSessionId()' failed.", 235, exception.getSessionId());
    }
}
