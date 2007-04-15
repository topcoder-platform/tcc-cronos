/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.ajax.stresstests;

import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.session.ChatSession;
import com.cronos.im.messenger.Messenger;
import com.cronos.im.messenger.MessengerException;

/**
 * <p>
 * Mock class for Messenger implementation.
 * </p>
 *
 * @author 80x86
 * @version 1.0
 */
public class MockMessenger implements Messenger {

    /**
     * Message pool of this messenger.
     */
    private MessagePool pool = new MockMessagePool();

    /**
     * Post the message to user message pool.
     *
     * @param msg
     *            The message to post.
     * @param userId
     *            The user id representing the receiver.
     * @throws IllegalArgumentException
     *             If <c>msg</c> argument is null.
     * @throws MessengerException
     *             If any other error occurred.
     */
    public void postMessage(Message msg, long userId) throws MessengerException {
    }

    /**
     * Post the message to the user session pool.
     *
     * @param msg
     *            The message to post.
     * @param userId
     *            The user id representing the receiver.
     * @param sessionId
     *            The session id to which the given user belongs to.
     * @throws IllegalArgumentException
     *             If <c>msg</c> argument is null.
     * @throws MessengerException
     *             If any other error occurred.
     */
    public void postMessage(Message msg, long userId, long sessionId) throws MessengerException {
    }

    /**
     * Post the message to the all the users in the session, except the user who sends the message.
     *
     * @param msg
     *            The message to post.
     * @param session
     *            The session that holds the users.
     * @throws IllegalArgumentException
     *             If any of the arguments is null.
     * @throws MessengerException
     *             If any other error occurred.
     */
    public void postMessageToOthers(Message msg, ChatSession session) throws MessengerException {
    }

    /**
     * Post the message to all the users in the session, including the created user.
     *
     * @param msg
     *            The message to post.
     * @param session
     *            The session holds the users.
     * @throws IllegalArgumentException
     *             If any of the arguments is null.
     * @throws MessengerException
     *             If any other error occurred.
     */
    public void postMessageToAll(Message msg, ChatSession session) throws MessengerException {
    }

    /**
     * Get the message pool.
     *
     * @return The non-null message pool.
     */
    public MessagePool getMessagePool() {
        return this.pool;
    }

    /**
     * Set the message pool.
     *
     * @param pool
     *            The message pool.
     * @throws IllegalArgumentException
     *             If argument is null.
     */
    public void setMessagePool(MessagePool pool) {
    }
}
