/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.ChatMessageFormattingException;
import com.cronos.im.messenger.MessengerException;


/**
 * Tests the functionality for class <code>ChatMessageFormattingExceptionAccuracyTest.java</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class ChatMessageFormattingExceptionAccuracyTest extends MessengerExceptionAccuracyTest {
    /**
     * Test method for 'ChatMessageFormattingException.ChatMessageFormattingException(String, String)'
     */
    public void testChatMessageFormattingExceptionStringString() {
        ChatMessageFormattingException exception = new ChatMessageFormattingException("msg", "text");
        assertTrue("Test method for 'ChatMessageFormattingException(String)' failed.",
            exception instanceof MessengerException);
    }

    /**
     * Test method for 'ChatMessageFormattingException.ChatMessageFormattingException(String, Throwable, String)'
     */
    public void testChatMessageFormattingExceptionStringThrowableString() {
        ChatMessageFormattingException exception = new ChatMessageFormattingException("msg", "text");
        assertEquals("Test method for 'ChatMessageFormattingException(String)' failed.", "msg", exception.getMessage());
    }

    /**
     * Test method for 'ChatMessageFormattingException.ChatMessageFormattingException(String, Throwable, String)'
     */
    public void testChatMessageFormattingExceptionStringThrowableString2() {
        Exception cause = new Exception("Root");
        ChatMessageFormattingException exception = new ChatMessageFormattingException("msg", cause, "text");
        assertEquals("Test method for 'ChatMessageFormattingException(String, Throwable)' failed.", cause,
            exception.getCause());
    }

    /**
     * Test method for 'ChatMessageFormattingException.getChatText()'
     */
    public void testGetChatText() {
        ChatMessageFormattingException exception = new ChatMessageFormattingException("msg", "text");
        assertEquals("Test method for 'ChatMessageFormattingException(String)' failed.", "text", exception.getChatText());
    }
}
