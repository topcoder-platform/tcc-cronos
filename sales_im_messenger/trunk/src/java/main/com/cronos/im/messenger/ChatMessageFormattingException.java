/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

/**
 * <p>
 * This exception will be thrown by <c>ChatMessageFormatter</c> concrete implementations
 * if any error occurred during formatting the chat message.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class ChatMessageFormattingException extends MessengerException {

    /**
     * The chat text to be formatted.
     * It must be a non-null string, but can have empty/blank values.
     * It is immutable after being set in the constructor.
     */
    private final String chatText;

    /**
     * Creates the exception with the specified error message and chat text that was formatted.
     *
     * @param msg      The error message.
     * @param chatText The chat text to be formatted.
     */
    public ChatMessageFormattingException(String msg, String chatText) {
        super(msg);

        this.chatText = chatText;
    }

    /**
     * create the exception with error message and cause, case super(msg, case)
     * assign the variable.
     *
     * @param msg      The error message.
     * @param cause    The cause exception.
     * @param chatText The chat text to be formatted.
     */
    public ChatMessageFormattingException(String msg, Throwable cause, String chatText) {
        super(msg, cause);

        this.chatText = chatText;
    }

    /**
     * Get the chat text.
     *
     * @return the chat text
     */
    public String getChatText() {
        return chatText;
    }
}
