/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.session;

/**
 * <p>
 * This abstract class represents an empty implementation of <code>ChatSessionEventListener</code>.
 * </p>
 *
 * <p>
 * This adapter is defined to allow user to implement only needed methods and does no care about the rest.
 * </p>
 *
 * <p>
 * Thread Safety : This class is thread safe, because it is immutable.
 * </p>
 *
 * @author tushak, TCSDEVELOPER
 * @version 1.0
 */
public abstract class ChatSessionEventAdapter implements ChatSessionEventListener {
    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    protected ChatSessionEventAdapter() {
        // empty
    }

    /**
     * <p>
     * This adapter does nothing after user was requested to session.
     * </p>
     *
     * <p>
     * It is mainly used for overriding.
     * </p>
     *
     * @param session <code>ChatSession</code> instance
     * @param user user id, any possible long value
     */
    public void userRequested(ChatSession session, long user) {
        // empty
    }

    /**
     * <p>
     * This adapter does nothing after user was added to session.
     * </p>
     *
     * <p>
     * It is mainly used for overriding.
     * </p>
     *
     * @param session <code>ChatSession</code> instance
     * @param user user id, any possible long value
     */
    public void userAdded(ChatSession session, long user) {
        // empty
    }

    /**
     * <p>
     * This adapter does nothing after user was removed to session.
     * </p>
     *
     * <p>
     * It is mainly used for overriding.
     * </p>
     *
     * @param session <code>ChatSession</code> instance
     * @param user user id, any possible long value
     */
    public void userRemoved(ChatSession session, long user) {
        // empty
    }
}