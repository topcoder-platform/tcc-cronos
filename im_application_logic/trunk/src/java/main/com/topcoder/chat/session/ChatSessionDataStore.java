/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.session;

/**
 * <p>
 * This interface is used to provide pluggable access to different type of data stores.
 * </p>
 *
 * <p>
 * For now it exists only its realization for database, but may be in future will be added
 * the other realization or even the other kinds of data stores.
 * </p>
 *
 * <p>
 * This interface is used by <code>ChatSessionManagerImpl</code>.
 * </p>
 *
 * <p>
 * Thread Safety : Implements of this interface should be thread safe
 * </p>
 *
 * @author tushak, TCSDEVELOPER
 * @version 1.0
 */
public interface ChatSessionDataStore {
    /**
     * <p>
     * This method is used to create new session to the data store.
     * </p>
     *
     * @param session <code>ChatSession</code> instance, null impossible
     *
     * @throws ChatSessionDataStoreException when impossible to create session
     * @throws IllegalArgumentException if session is null
     */
    public void createSession(ChatSession session) throws ChatSessionDataStoreException;

    /**
     * <p>
     * This method is used to get concrete session by its id from data store.
     * </p>
     *
     * <p>
     * If the session id cannot be found in the data store,
     * <code>ChatSessionIncorrectEntityIdException</code> should be thrown.
     * </p>
     *
     * @param id id of session, any long value
     * @return <code>ChatSession</code> instance, it will never be null.
     *
     * @throws ChatSessionDataStoreException when impossible to retrieve session for some reasons
     * @throws ChatSessionIncorrectEntityIdException if session instance not found
     */
    public ChatSession getSession(long id) throws ChatSessionDataStoreException, ChatSessionIncorrectEntityIdException;

    /**
     * <p>
     * This method is used to get sessions by their ids from data store.
     * </p>
     *
     * <p>
     * If one of the session id cannot be found in the data store, then the corresponding <code>ChatSession</code>
     * for it should be null.
     * </p>
     *
     * @param ids ids of sessions, array with any long values
     * @return array of <code>ChatSession</code> instances, null impossible but may be empty array,
     * elements of array may be null if session can not be found
     *
     * @throws IllegalArgumentException if ids is null
     * @throws ChatSessionDataStoreException when impossible to retrieve session for some reasons
     */
    public ChatSession[] getSessions(long[] ids) throws ChatSessionDataStoreException;

    /**
     * <p>
     * This method is used to request user to session in data store.
     * </p>
     *
     * @param session <code>ChatSession</code> instance, null impossible
     * @param user user id, any possible long value
     *
     * @throws ChatSessionDataStoreException when impossible to requested user to session for some reasons
     * @throws IllegalArgumentException if session is null
     */
    public void requestUserToSession(ChatSession session, long user) throws ChatSessionDataStoreException;

    /**
     * <p>
     * This method is used to add user to session in data store.
     * </p>
     *
     * @param session <code>ChatSession</code> instance, null impossible
     * @param user user id, any possible long value
     *
     * @throws ChatSessionDataStoreException when impossible to add user to session for some reasons
     * @throws IllegalArgumentException if session is null
     * @throws ChatSessionIncorrectEntityIdException if user was already added to session or the session
     * id does not exist
     * @throws ChatSessionModeIncorrectOperationException if session is 1-1 or sales and it already
     * contains two participants
     */
    public void addUserToSession(ChatSession session, long user) throws ChatSessionIncorrectEntityIdException,
        ChatSessionModeIncorrectOperationException, ChatSessionDataStoreException;

    /**
     * <p>
     * This method is used to remove user from session from data store.
     * </p>
     *
     * @param session <code>ChatSession</code> instance, null impossible
     * @param user user id, any possible long value
     *
     * @throws ChatSessionDataStoreException when impossible to remove user from session for some reasons
     * @throws IllegalArgumentException if session is null
     * @throws ChatSessionIncorrectEntityIdException if user or session is not present in data store
     */
    public void removeUserFromSession(ChatSession session, long user) throws ChatSessionDataStoreException,
        ChatSessionIncorrectEntityIdException;

    /**
     * <p>
     * This method is used to remove user from all sessions in data store.
     * </p>
     *
     * @param user user id, any possible long value
     * @return array of <code>ChatSession</code> instances, null impossible but may be empty array,
     * elements of array may be null if concrete session can not be found
     *
     * @throws ChatSessionDataStoreException when impossible to remove user from all sessions for some reasons
     */
    public ChatSession[] removeUserFromSessions(long user) throws ChatSessionDataStoreException;

    /**
     * <p>
     * This method is used to check if some user is added to some session.
     * If such session was found, it will true, otherwise false.
     * </p>
     *
     * @param user user id, any possible long value
     * @return true if session has given user, otherwise false
     *
     * @throws ChatSessionDataStoreException when impossible to check if session contains user for some reasons
     */
    public boolean hasSessions(long user) throws ChatSessionDataStoreException;

    /**
     * <p>
     * This method is used to retrieve the users that create the sessions that were requested by
     * the given user under some time conditions.
     * </p>
     *
     * <p>
     * If the given recent is negative, then the users that create all the sessions requested by the
     * given user will be retrieved.
     * </p>
     *
     * <p>
     * If the given recent is zero, then an empty array will be returned.
     * </p>
     *
     * <p>
     * If the given recent is negative, then the users that create the sessions that were requested by
     * the given user before special moment of time which is calculated like current date minus numbers
     * of days equals recent value will be retrieved.
     * </p>
     *
     * @param user user id, any possible long value
     * @param recent number days before today, any integer value
     * @return array of user id instances, null impossible but may be empty array
     *
     * @throws ChatSessionDataStoreException when impossible to retrieve data from data store for some reasons
     * @throws ChatSessionIncorrectEntityIdException if user is not requested
     */
    public long[] getRecentlyRequestedUsers(long user, int recent) throws ChatSessionDataStoreException,
        ChatSessionIncorrectEntityIdException;
}
