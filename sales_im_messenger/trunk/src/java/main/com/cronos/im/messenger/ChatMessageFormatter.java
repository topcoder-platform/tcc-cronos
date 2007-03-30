/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

/**
 * <p>
 * This interface defines the contract of formatting the message from user name, timestamp, and chat text.
 * </p>
 * <p>
 * <b>Thread safety</b>:The implementation of this interface is required to be thread safe.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public interface ChatMessageFormatter {

    /**
     * Format the chat message from user name, timestamp and chat text.
     *
     * @param userName  The user name to format.
     * @param timestamp The timestamp to format.
     * @param chatText  The chat text to format.
     * @return The formatted message.
     * @throws IllegalArgumentException       If any of the arguments is null or empty string.
     * @throws ChatMessageFormattingException If any other error ocurred during formatting.
     */
    public String format(String userName, String timestamp
        , String chatText) throws ChatMessageFormattingException;
}


