/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.Message;
import com.orpheus.administration.persistence.ParameterHelpers;

import java.util.Date;

/**
 * <p>A simple implementation of the <code>Message</code> interface used to communicate between the client and
 * {@link SQLServerMessageDAO SQLServerMessageDAO}.
 *
 * <p><strong>Thread Safety</strong>: This object is immutable and thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class MessageImpl implements Message {
    /**
     * The unique global ID of this message. This member is set in the constructor, is immutable, and will never be
     * <code>null</code> or an empty string.
     */
    private final String guid;

    /**
     * The category of this message. This member is set in the constructor, is immutable, and will never be
     * <code>null</code> or an empty string.
     */
    private final String category;

    /**
     * The content type of this message. This member is set in the constructor, is immutable, and will never be
     * <code>null</code> or an empty string.
     */
    private final String contentType;

    /**
     * The content of this message. This member is set in the constructor, is immutable, and will never be
     * <code>null</code> or an empty string.
     */
    private final String content;

    /**
     * The timestamp of this message. This member is set in the constructor, is immutable, and will never be
     * <code>null</code>.
     */
    private final Date timestamp;

    /**
     * Constructs a new <code>MessageImpl</code> with the specified field values.
     *
     * @param guid the global ID of the message
     * @param category the category of the message
     * @param contentType the content type of the message
     * @param message the content of the message
     * @param timestamp the timestamp of the message
     * @throws IllegalArgumentException if any parameter is <code>null</code> or an empty string
     */
    public MessageImpl(String guid, String category, String contentType, String message, Date timestamp) {
        ParameterHelpers.checkString(guid, "message GUID");
        ParameterHelpers.checkString(category, "message category");
        ParameterHelpers.checkString(contentType, "message content type");
        ParameterHelpers.checkString(message, "message content");

        if (timestamp == null) {
            throw new IllegalArgumentException("timestamp must not be null");
        }

        this.guid = guid;
        this.category = category;
        this.contentType = contentType;
        this.content = message;
        this.timestamp = timestamp;
    }

    /**
     * Returns the unique global ID of this message.
     *
     * @return the unique global ID of this message
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Returns the unique global ID of this message.
     *
     * @return the unique global ID of this message
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the content type of this message.
     *
     * @return the content type of this message
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Returns the content of this message.
     *
     * @return the content of this message
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the timestamp of this message.
     *
     * @return the timestamp of this message
     */
    public Date getTimestamp() {
        return timestamp;
    }
}
