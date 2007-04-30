/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.session.ChatSession;

/**
 * <p>
 * This interface is the main entry of this component. It defines the contract of routing the messages
 * to the message pools of the desired recipients.
 * </p>
 * <p>
 * <b>Thread safety</b>: The implementation of this interface is required to be thread safe.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public interface Messenger {

    /**
     * Post the message to user message pool.
     *
     * @param msg    The message to post.
     * @param userId The user id representing the receiver.
     * @throws IllegalArgumentException If <c>msg</c> argument is null.
     * @throws MessengerException       If any other error occurred.
     */
    public void postMessage(Message msg, long userId)
        throws MessengerException;

    /**
     * Post the message to the user session pool.
     *
     * @param msg       The message to post.
     * @param userId    The user id  representing the receiver.
     * @param sessionId The session id to which the given user belongs to.
     * @throws IllegalArgumentException If <c>msg</c> argument is null.
     * @throws MessengerException       If any other error occurred.
     */
    public void postMessage(Message msg, long userId, long sessionId)
        throws MessengerException;

    /**
     * Post the message to the all the users in the session, except the
     * user who sends the message.
     *
     * @param msg     The message to post.
     * @param session The session that holds the users.
     * @throws IllegalArgumentException If any of the arguments is null.
     * @throws MessengerException       If any other error occurred.
     */
    public void postMessageToOthers(Message msg, ChatSession session)
        throws MessengerException;

    /**
     * Post the message to all the users in the session, including the created user.
     *
     * @param msg     The message to post.
     * @param session The session holds the users.
     * @throws IllegalArgumentException If any of the arguments is null.
     * @throws MessengerException       If any other error occurred.
     */
    public void postMessageToAll(Message msg, ChatSession session)
        throws MessengerException;

    /**
     * Get the message pool.
     *
     * @return The non-null message pool.
     */
    public MessagePool getMessagePool();
}


