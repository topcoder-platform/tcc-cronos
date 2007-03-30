/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import junit.framework.TestCase;

/**
 * Test cases for the <code>ChatMessageFormattingException</code> class.This
 * class tests the constructors and <c>getChatText</c> method of this class for
 * accuracy and failure.
 *
 * @author marius_neo
 * @version 1.0
 */
public class ChatMessageFormattingExceptionTestCase extends TestCase {
    /**
     * Represents the <c>chatText</c> parameter passed to the exception
     * constructors when testing them.
     */
    private final String chatText = "some chat text";

    /**
     * Represents the <c>cause</c> exception used in testing
     * <c>ChatMessageFormattingException(String, Throwable, String)</c> constructor.
     */
    private final Throwable cause = new Exception("I am the cause exception");

    /**
     * Tests if this class inherits from <c>MessengerException</c> class as requested
     * in component specification.
     */
    public void testInheritance() {
        ChatMessageFormattingException e = new ChatMessageFormattingException("Hello there", chatText);
        assertTrue("This class should inherit MessengerException", e instanceof MessengerException);
    }

    /**
     * Tests the <code>ChatMessageFormattingException(String,String)</code> constructor and the
     * getter method <c>getChatText</c> for accuracy.
     * Simply verifies that the correct parameters are set in the constructor.
     */
    public void testChatMessageFormattingExceptionStringString() {
        ChatMessageFormattingException e = new ChatMessageFormattingException("Hello there", chatText);
        assertEquals("The message must be set in the constructor!", "Hello there", e.getMessage());
        assertEquals("The chat texts should be equals", chatText, e.getChatText());
    }

    /**
     * Tests the <code>ChatMessageFormattingException(String, Throwable, String)</code> constructor
     * and the getter method <c>getChatText</c> for accuracy.
     * Simply verifies that the correct parameters are set in the constructor.
     */
    public void testChatMessageFormattingExceptionStringThrowable() {
        ChatMessageFormattingException e = new ChatMessageFormattingException("I was caused by me!"
            , cause, chatText);
        assertEquals("The cause must be set in the constructor!", cause, e.getCause());
        assertEquals("The chat texts should be equals", chatText, e.getChatText());
    }
}