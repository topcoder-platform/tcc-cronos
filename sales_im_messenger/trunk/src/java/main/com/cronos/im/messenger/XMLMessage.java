/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

import com.topcoder.chat.message.pool.Message;


/**
 * <p>
 * XMLMessage is the base class of all the other six messages defined in this component.
 * It provides a method to set the date time format which will be used to format the timestamp.
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is not thread safe since it’s mutable.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public abstract class XMLMessage extends Message {

    /**
     * The chat session id. It is set and accessed with set/get ChatSessionId method.
     */
    private long chatSessionId;

    /**
     * Create the message instance.
     */
    protected XMLMessage() {
    }

    /**
     * Retrieves the chat session id for this message.
     *
     * @return The chat session id.
     */
    public long getChatSessionId() {
        return chatSessionId;
    }

    /**
     * Sets the chat session id for this message.
     *
     * @param id The chat session id.
     */
    public void setChatSessionId(long id) {
        chatSessionId = id;
    }

    /**
     * Returns the xml representation of the message.
     *
     * @param context The date format context.
     * @return XML string representation of the message.
     * @throws IllegalStateException          If any required field is missing.
     * @throws IllegalArgumentException       If the argument of the method is null.
     * @throws ChatMessageFormattingException Wraps any other exceptions that might appear during
     *                                        the operation.
     */
    public abstract String toXMLString(DateFormatContext context)
        throws ChatMessageFormattingException;
}
