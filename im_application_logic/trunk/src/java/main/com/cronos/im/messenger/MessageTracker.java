/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

import com.topcoder.chat.message.pool.Message;

/**
 * <p>
 * This interface defines the contract of tracking the message. The tracking information could be stored
 * in the database, xml file, etc.
 * </p>
 * <p>
 * <b>Thread safety</b>: The implementation of this interface is required to be thread safe.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public interface MessageTracker {

    /**
     * Track the message with given user id and session id.
     *
     * @param msg       The message to track.
     * @param userIds   The user id.
     * @param sessionId The session id.
     * @throws IllegalArgumentException If <c>msg</c> argument is null or if <c>userIds</c>
     *                                  array is empty or it contains duplicate ids.
     * @throws MessageTrackerException  If any other error occurred.
     */
    public void track(Message msg, long[] userIds, long sessionId) throws MessageTrackerException;
}


