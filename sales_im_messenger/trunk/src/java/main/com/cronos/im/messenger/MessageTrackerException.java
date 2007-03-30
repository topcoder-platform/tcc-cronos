/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import com.topcoder.chat.message.pool.Message;

/**
 * This exception will be thrown by <c>MessageTracker</c> concrete implementations
 * if any error occurred during tracking of a chat message.
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class MessageTrackerException extends MessengerException {

    /**
     * The tracked message causing this exception.
     * It is set in the constructor and never changed later.
     * Can't be null.
     */
    private final Message message;

    /**
     * The tracked user id causing this exception.
     * It is set in the constructor and never changed later.
     */
    private final long userId;

    /**
     * The tracked session id causing this exception.
     * It is set in the constructor and never changed later.
     */
    private final long sessionId;

    /**
     * Create the exception with specified error message, tracked message, user id and session id.
     *
     * @param msg        The error message.
     * @param trackedMsg The tracked message.
     * @param userId     The tracked user id.
     * @param sessionId  The tracked session id.     
     */
    public MessageTrackerException(String msg, Message trackedMsg, long userId, long sessionId) {
        super(msg);
        this.message = trackedMsg;
        this.userId = userId;
        this.sessionId = sessionId;
    }

    /**
     * Create the exception with error message and cause, tracked message, user id and session id.
     *
     * @param msg        The error message
     * @param cause      The cause exception
     * @param trackedMsg The tracked message
     * @param userId     The tracked user id
     * @param sessionId  The tracked session id
     */
    public MessageTrackerException(String msg, Throwable cause, Message trackedMsg
        , long userId, long sessionId) {
        super(msg, cause);
        this.message = trackedMsg;
        this.userId = userId;
        this.sessionId = sessionId;
    }

    /**
     * Retrieves the tracked message for this exception.
     *
     * @return The tracked message.
     */
    public Message getTrackedMessage() {
        return message;
    }

    /**
     * Get the tracked user id for this exception.
     *
     * @return The tracked user id.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Get the tracked session id for this exception.
     *
     * @return The tracked session id.
     */
    public long getSessionId() {
        return sessionId;
    }
}
