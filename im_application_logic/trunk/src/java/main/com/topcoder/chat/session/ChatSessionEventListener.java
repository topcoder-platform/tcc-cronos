/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.session;

import java.util.EventListener;

/**
 * <p>
 * This interface extends <code>EventListener</code> and represents three methods which may be
 * realized for getting notification about changes in data store which was made by this component.
 * </p>
 *
 * <p>
 * It notifies about the next changes: adding, removing and requesting user to session.
 * </p>
 *
 * <p>
 * This interface is used by <code>ChatSessionManagerImpl</code>.
 * </p>
 *
 * <p>
 * Thread Safety : Implementations of this interface should be thread safe.
 * </p>
 *
 * @author tushak, TCSDEVELOPER
 * @version 1.0
 */
public interface ChatSessionEventListener extends EventListener {
    /**
     * <p>
     * This method is used to provide some functionality after user was requested to session.
     * </p>
     *
     * @param session <code>ChatSession</code> instance, null impossible
     * @param user user id, any possible long value
     *
     * @throws IllegalArgumentException if parameter session is null
     */
    public void userRequested(ChatSession session, long user);

    /**
     * <p>
     * This method is used to provide some functionality after user was added to session.
     * </p>
     *
     * @param session <code>ChatSession</code> instance, null impossible
     *
     * @param user user id, any possible long value
     * @throws IllegalArgumentException if parameter session is null
     */
    public void userAdded(ChatSession session, long user);

    /**
     * <p>
     * This method is used to provide some functionality after user was removed from session.
     * </p>
     *
     *
     * @param session <code>ChatSession</code> instance, null impossible
     * @param user user id, any possible long value
     *
     * @throws IllegalArgumentException if parameter session is null
     */
    public void userRemoved(ChatSession session, long user);
}