/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.logic;

import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.session.ChatSession;
import com.cronos.im.messenger.Messenger;
import com.cronos.im.messenger.MessengerException;

/**
 * <p>
 * Mock messenger.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockMessenger implements Messenger {

    /**
     * Used to trace the operations.
     */
    private static String msg = "";

    /**
     * Get trace message.
     * 
     * @return trace message.
     */
    public static String getMessage() {
        return msg;
    }

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
        MockMessenger.msg += "postMessage(..., " + userId + ")";
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
        MockMessenger.msg += "postMessage(..., " + userId + ", " + sessionId + ")";
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
        MockMessenger.msg += "postMessageToOthers(..., ...)";
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
        MockMessenger.msg += "postMessageToAll(..., ...)";
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
