/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.stresstests;

import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.session.ChatSessionDataStoreException;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.session.SalesSession;
import com.topcoder.chat.session.ChatSessionIncorrectEntityIdException;
import com.topcoder.chat.session.ChatSessionModeIncorrectOperationException;
import com.topcoder.chat.session.ChatSessionEventListener;

/**
 * <p>
 * Mock ChatSessionManager implementation.
 * </p>
 *
 * @author kaqi072821
 * @version 1.0
 */
public class MockChatSessionManager implements ChatSessionManager {
    /**
     * <p>
     * This method is used to create new session to some configurable data store.
     * </p>
     *
     * @param session
     *            <code>ChatSession</code> instance, null impossible
     *
     * @throws IllegalArgumentException
     *             if session is null
     * @throws ChatSessionDataStoreException
     *             when impossible to create session
     */
    public void createSession(ChatSession session) throws ChatSessionDataStoreException {
    }

    /**
     * <p>
     * This method is used to get concrete session by its id from some configurable data store.
     * </p>
     *
     * @param id
     *            id of session, any long value
     * @return <code>ChatSession</code> instance, it will never be null
     *
     * @throws ChatSessionDataStoreException
     *             when impossible to retrieve session for some reasons
     * @throws ChatSessionIncorrectEntityIdException
     *             if session instance not found
     */
    public ChatSession getSession(long id) throws ChatSessionDataStoreException,
            ChatSessionIncorrectEntityIdException {
        SalesSession s = new SalesSession(TestHelper.USER_ID_ONE, TestHelper.CATEGORY_ID);
        s.addRequestedUser(TestHelper.USER_ID_ONE);
        s.addRequestedUser(TestHelper.USER_ID_TWO);
        s.addUser(TestHelper.USER_ID_ONE);
        s.addUser(TestHelper.USER_ID_TWO);
        return s;
    }

    /**
     * <p>
     * This method is used to get sessions by their ids from some configurable data store by their ids.
     * </p>
     *
     * @param ids
     *            ids of sessions, array with any long values
     *
     * @return array of <code>ChatSession</code> instances, null impossible but may be empty array, elements
     *         of array may be null if the corresponding session can not be found
     *
     * @throws ChatSessionDataStoreException
     *             when impossible to retrieve sessions for some reasons
     */
    public ChatSession[] getSessions(long[] ids) throws ChatSessionDataStoreException {
        return null;
    }

    /**
     * <p>
     * This method is used to request user to session in some configurable data store.
     * </p>
     *
     * @param session
     *            <code>ChatSession</code> instance, null impossible
     * @param user
     *            user id, any possible long value
     *
     * @throws IllegalArgumentException
     *             if session is null
     * @throws ChatSessionDataStoreException
     *             when impossible to requested user to session for some reasons
     */
    public void requestUserToSession(ChatSession session, long user) throws ChatSessionDataStoreException {
    }

    /**
     * <p>
     * This method is used to add user to session in some configurable data store.
     * </p>
     *
     * @param session
     *            <code>ChatSession</code> instance, null impossible
     * @param user
     *            user id, any possible long value
     *
     * @throws IllegalArgumentException
     *             if session is null
     * @throws ChatSessionDataStoreException
     *             when impossible to add user to session for some reasons
     * @throws ChatSessionIncorrectEntityIdException
     *             if user was already added to session or the session id does not exist
     * @throws ChatSessionModeIncorrectOperationException
     *             if session is 1-1 or sales and it already contains two participants
     */
    public void addUserToSession(ChatSession session, long user)
            throws ChatSessionIncorrectEntityIdException, ChatSessionModeIncorrectOperationException,
            ChatSessionDataStoreException {
    }

    /**
     * <p>
     * This method is used to remove user from session from some configurable data store.
     * </p>
     *
     * @param session
     *            <code>ChatSession</code> instance, null impossible
     * @param user
     *            user id, any possible long value
     *
     * @throws IllegalArgumentException
     *             if session is null
     * @throws ChatSessionDataStoreException
     *             when impossible to remove user from session for some reasons
     * @throws ChatSessionIncorrectEntityIdException
     *             if user or session is not present in data base
     */
    public void removeUserFromSession(ChatSession session, long user) throws ChatSessionDataStoreException,
            ChatSessionIncorrectEntityIdException {
    }

    /**
     * <p>
     * This method is used to remove user from all sessions from all sessions in some configurable data store.
     * </p>
     *
     * @param user
     *            user id, any possible long value
     *
     * @throws ChatSessionDataStoreException
     *             when impossible to remove user from all sessions for some reasons
     */
    public void removeUserFromSessions(long user) throws ChatSessionDataStoreException {
    }

    /**
     * <p>
     * This method is used to check if some user is added to some session. If such session was found, it
     * should return true, otherwise false.
     * </p>
     *
     * @param user
     *            user id, any possible long value
     * @return true if some session has the given user, otherwise false
     *
     * @throws ChatSessionDataStoreException
     *             when impossible to check if session contains the given user for some reasons
     */
    public boolean hasSessions(long user) throws ChatSessionDataStoreException {
        return false;
    }

    /**
     * <p>
     * This method is used to retrieve the users that create the sessions that were requested by the given
     * user under some time conditions.
     * </p>
     *
     * <p>
     * If the recent for this manager is negative, then the users that create all the sessions requested by
     * the given user will be retrieved.
     * </p>
     *
     * <p>
     * If the recent for this manager is zero, then an empty array will be returned.
     * </p>
     *
     * <p>
     * If the recent for this manager is positive, then the users that create the sessions that were requested
     * by the given user before special moment of time which is calculated like current date minus numbers of
     * days equals recent value will be retrieved.
     * </p>
     *
     * @param user
     *            user id, any possible long value
     * @return array of user id instances, null impossible but may be empty array
     *
     * @throws ChatSessionDataStoreException
     *             when impossible to retrieve data from data store for some reasons
     * @throws ChatSessionIncorrectEntityIdException
     *             if user is not requested
     */
    public long[] getRecentlyRequestedUsers(long user) throws ChatSessionDataStoreException,
            ChatSessionIncorrectEntityIdException {
        return null;
    }

    /**
     * <p>
     * This method is used to retrieve the users that create the sessions that were requested by the given
     * user under some time conditions.
     * </p>
     *
     * <p>
     * If the given recent is negative, then the users that create all the sessions requested by the given
     * user will be retrieved.
     * </p>
     *
     * <p>
     * If the given recent is zero, then an empty array will be returned.
     * </p>
     *
     * <p>
     * If the given recent is positive, then the users that create the sessions that were requested by the
     * given user before special moment of time which is calculated like current date minus numbers of days
     * equals recent value will be retrieved.
     * </p>
     *
     * @param user
     *            user id, any possible long value
     * @param recent
     *            number days before today, any integer value
     * @return array of user id instances, null impossible but may be empty array
     *
     * @throws ChatSessionDataStoreException
     *             when impossible to retrieve data from data store for some reasons
     * @throws ChatSessionIncorrectEntityIdException
     *             if user is not requested
     */
    public long[] getRecentlyRequestedUsers(long user, int recent) throws ChatSessionDataStoreException,
            ChatSessionIncorrectEntityIdException {
        return null;
    }

    /**
     * <p>
     * This method is used to add new listener to receive notification about changes of this manager.
     * </p>
     *
     * @param listener
     *            <code>ChatSessionEventListener</code> instance, null impossible
     *
     * @throws IllegalArgumentException
     *             if listener is null
     */
    public void addChatSessionEventListener(ChatSessionEventListener listener) {
    }

    /**
     * <p>
     * This method is used to remove the given listener from this manager.
     * </p>
     *
     * @param listener
     *            <code>ChatSessionEventListener</code> instance, null impossible
     *
     * @throws IllegalArgumentException
     *             if listener is null
     */
    public void removeChatSessionEventListener(ChatSessionEventListener listener) {
    }

    /**
     * <p>
     * This method is used to retrieve all added listeners of this manager.
     * </p>
     *
     * @return array of <code>ChatSessionEventListener</code> instances in this manager, null impossible but
     *         may be empty array
     */
    public ChatSessionEventListener[] getChatSessionEventListeners() {
        return null;
    }

    /**
     * <p>
     * This method is used to remove all the listeners added to this manager.
     * </p>
     */
    public void removeChatSessionEventListeners() {
    }
}
