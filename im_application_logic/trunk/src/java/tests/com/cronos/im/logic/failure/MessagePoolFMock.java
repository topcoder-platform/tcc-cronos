/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.failure;

import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.SearchResult;
import com.cronos.im.messenger.EnterChatMessage;

/**
 * Mock MessagePool implementation.
 *
 * @author singlewood
 * @version 1.0
 */
public class MessagePoolFMock implements MessagePool {

    /**
     * Messages used in this mock class.
     */
    private Message[] msgs;

    /**
     * Search results used in this mock class.
     */
    private SearchResult[] results;

    /**
     * Empty constructor.
     */
    public MessagePoolFMock() {
        msgs = new Message[] {new EnterChatMessage()};
        SearchResult r = new SearchResult(1, 1, System.currentTimeMillis(), true, true);
        results = new SearchResult[] {r};
    }

    /**
     * <p>
     * This method pushes given message to user's pool. IllegalArgumentException is thrown if message is null.
     * PoolNotRegisteredException is thrown if this pool is not registered.
     * </p>
     *
     *
     * @param user
     *            user id in whose pool message will be pushed.
     * @param message
     *            message to be pushed. can not be null.
     */
    public void push(long user, Message message) {
    }

    /**
     * <p>
     * This batch method pushes given message to several users' pools. IllegalArgumentException is thrown if
     * message is null. PoolNotRegisteredException is thrown if this pool is not registered. The method will
     * try to perform actions on the all elements of the batch even if this process fails at some intermediate
     * point. The exception will throw only at the end of the batch.
     * </p>
     *
     *
     * @param users
     *            the array of user ids, in whose pools a message will be pushed.
     * @param message
     *            message to be pushed. can not be null.
     */
    public void push(long[] users, Message message) {
    }

    /**
     * <p>
     * This method pushes given message to user's session's pool. IllegalArgumentException is thrown if
     * message is null. PoolNotRegisteredException is thrown if this pool is not registered.
     * </p>
     *
     *
     * @param user
     *            user id in whose session's pool message will be pushed
     * @param session
     *            session id in whose pool message will be pushed
     * @param message
     *            message to be pushed. can not be null.
     */
    public void push(long user, long session, Message message) {
    }

    /**
     * <p>
     * This batch method pushes given message to several users' session's pools. So, the same session's pools
     * of the several users will be pushed by a message. IllegalArgumentException is thrown if message is
     * null. PoolNotRegisteredException is thrown if this pool is not registered. The method will try to
     * perform actions on the all elements of the batch even if this process fails at some intermediate point.
     * The exception will throw only at the end of the batch.
     * </p>
     *
     *
     * @param users
     *            an array of user ids, in whose session's pool a message will be pushed
     * @param session
     *            session id, in whose pool a message will be pushed
     * @param message
     *            message to be pushed. can not be null.
     */
    public void push(long[] users, long session, Message message) {
    }

    /**
     * <p>
     * This batch method pushes given message to several user's sessions' pools. So, the several sessions'
     * pools for the same user will be pushed by a message. IllegalArgumentException is thrown if message is
     * null. PoolNotRegisteredException is thrown if this pool is not registered. The method will try to
     * perform actions on the all elements of the batch even if this process fails at some intermediate point.
     * The exception will throw only at the end of the batch.
     * </p>
     *
     *
     * @param user
     *            user id, in whose sessions' pools a message will be pushed
     * @param sessions
     *            an array of session ids, in whose pool a message will be pushed
     * @param message
     *            message to be pushed. can not be null.
     */
    public void push(long user, long[] sessions, Message message) {
    }

    /**
     * <p>
     * Pulls messages from user's pool. PoolNotRegisteredException is thrown if this pool is not registered.
     * </p>
     *
     *
     * @param user
     *            user id from whose pool messages will be pulled
     * @return Messages pulled from the pool. can not be null.
     */
    public Message[] pull(long user) {
        return msgs;
    }

    /**
     * <p>
     * Pulls messages from user's session's pool. PoolNotRegisteredException is thrown if this pool is not
     * registered.
     * </p>
     *
     *
     * @param user
     *            user id from whose session's pool messages will be pulled
     * @param session
     *            session id from whose pool messages will be pulled
     * @return Messages pulled from the pool. can not be null.
     */
    public Message[] pull(long user, long session) {
        return msgs;
    }

    /**
     * <p>
     * Registers specified user's pool. Has no effect if the pool is already registered.
     * </p>
     *
     *
     * @param user
     *            user id whose pool is to be registered
     */
    public void register(long user) {
    }

    /**
     * <p>
     * Unregisters specified user's pool. UnregistrationException is thrown if specified user's pool is not
     * registered or it has registered sessions' pool by this time.
     * </p>
     * <p>
     * Version 1.1 changes. There was a bug in v.1.0 of this component. The unregistration was denied when
     * there were unregistered session pools. But the action should be different: deny unregistration if the
     * registered session pool exists.
     * </p>
     *
     *
     * @param user
     *            user id whose pool is to be unregistered
     */
    public void unregister(long user) {
    }

    /**
     * <p>
     * Registers user's session's pool. UserNotRegistered is thrown if this user's pool is not registered yet.
     * </p>
     *
     *
     * @param user
     *            user id whose session's pool is to be registered
     * @param session
     *            session id whose pool is to be registered
     */
    public void register(long user, long session) {
    }

    /**
     * <p>
     * Unregisters user's session's pool. UnregistrationException is thrown if specified user's pool is not
     * registered, or it the requested session poll is not registered to the specified user pool.
     * </p>
     *
     *
     *
     * @param user
     *            user id whose session's pool is to be unregistered
     * @param session
     *            session id whose pool is to be unregistered
     */
    public void unregister(long user, long session) {
    }

    /**
     * Implements the latest user pool searching algorithm on the message pool. This method searches for the
     * latest accessed user pool. The all user pools are processed to find one with the latest access time
     * (push or pull). Just 1 (or zero) search result returned. No results can be returned only if the message
     * pool has no user＊s pool.
     * <p>
     * Exception handling. None.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @return the information about the pool found. Can contain 1 element (the pool found), or be empty (no
     *         pool found). Can not be null. No elements of the array can be null.
     */
    public SearchResult[] searchLastUserPoolActivity(boolean isPushed) {
        return results;
    }

    /**
     * Implements the latest session pool searching algorithm on the message pool. This method searches for
     * the latest accessed session pools. The all user pools are processed to find latest accessed (push or
     * pull) session pools. So, for each user the latest accessed session pool (if any) is returned. The
     * number of results can be the same as number of users or below (if some users has no session pool). No
     * results can be returned only if the message pool has no user＊s pool, or each user pool has no session
     * pool.
     * <p>
     * Exception handling. None.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @return the information about the pool found. Can contain information about one session pool element
     *         for the each user. If some user pools has no session pool, then the number of returned elements
     *         reduced. The array can be empty. Can not be null. No elements of the array can be null.
     */
    public SearchResult[] searchLastSessionPoolActivity(boolean isPushed) {
        return results;
    }

    /**
     * Implements the latest session pool searching algorithm on the message pool. This method searches for
     * the latest accessed session pool for the concrete user. The requested user pool is processed to find
     * latest accessed (push or pull) session pool. Just 1 (or zero) search result returned. No results can be
     * returned only if the user pool has no session pool.
     * <p>
     * Exception handling. PoolNotRegisteredException is thrown if the requested user pool is not found.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @param user
     *            user id, for which user＊s pool the searching algorithm should execute
     * @return the information about the pool found. Can contain 1 element (the pool found), or be empty (no
     *         pool found). Can not be null. No elements of the array can be null.
     */
    public SearchResult[] searchLastSessionPoolActivity(boolean isPushed, long user) {
        return results;
    }

    /**
     * Implements the latest user pool searching algorithm on the message pool for the predefined time. This
     * method searches for the user pool, which access time is most close to the requested time. The all user
     * pools are processed to find one with the access time (push or pull) most close to the requested time.
     * If the isBefore flag is true, then the searched time should be before the requested time, if false 每
     * then the searched time should be after the requested time. Just 1 (or zero) search result returned. No
     * results can be returned only if the message pool has no user＊s pool, or the all existed user＊s pools
     * are on the opposite side of the requested time.
     * <p>
     * Exception handling. None.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @param time
     *            the requested time value used in the searching algorithm. Can be any value.
     * @param isBefore
     *            a control flag to define, from which side of the requested time the searching algorithm
     *            should work. true 每 search before the requested time, false 每 search after the requested
     *            time.
     * @return the information about the pool found. Can contain 1 element (the pool found), or be empty (no
     *         pool found). Can not be null. No elements of the array can be null.
     */
    public SearchResult[] searchLastUserPoolActivity(boolean isPushed, long time, boolean isBefore) {
        return results;
    }

    /**
     * Implements the latest session pool searching algorithm on the message pool for the predefined time.
     * This method searches for the session pools, which access time is most close to the requested time. The
     * all user pools are processed to find session pools with the access time (push or pull) most close to
     * the requested time. If the isBefore flag is true, then the searched time should be before the requested
     * time, if false 每 then the searched time should be after the requested time. So, for each user the
     * latest session pool (if any) is returned. The number of results can be the same as number of users or
     * below (if some users has no session pool, or has time on the opposite side of the requested time). No
     * results can be returned only if the message pool has no user＊s pool, or each user pool has no session
     * pool, or the all existed session＊s pools are on the opposite side of the requested time.
     * <p>
     * Exception handling. None.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @param time
     *            the requested time value used in the searching algorithm. Can be any value.
     * @param isBefore
     *            a control flag to define, from which side of the requested time the searching algorithm
     *            should work. true 每 search before the requested time, false 每 search after the requested
     *            time.
     * @return the information about the pool found. Can contain information about one session pool element
     *         for the each user. If some user pools has no session pool (or their times are on the opposite
     *         side of the requsted time), then the number of returned elements reduced. The array can be
     *         empty. Can not be null. No elements of the array can be null.
     */
    public SearchResult[] searchLastSessionPoolActivity(boolean isPushed, long time, boolean isBefore) {
        return results;
    }

    /**
     * Implements the latest session pool searching algorithm on the message pool for the predefined time.
     * This method searches for the session pool, which acccess time is most close to the requested time, and
     * the searching is impelemented for the concrete user. The requested user pool is processed to find
     * session pool with the access time (push or pull) most close to the requested time. If the isBefore flag
     * is true, then the searched time should be before the requested time, if false 每 then the searched time
     * should be after the requested time. Just 1 (or zero) search result returned. No results can be returned
     * only if the user pool has no session pool, or the all existed session＊s pools are on the opposite side
     * of the requested time.
     * <p>
     * Exception handling. PoolNotRegisteredException is thrown if the requested user pool is not found.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @param user
     *            user id, for which user＊s pool the searching algorithm should execute
     * @param time
     *            the requested time value used in the searching algorithm. Can be any value.
     * @param isBefore
     *            a control flag to define, from which side of the requested time the searching algorithm
     *            should work. true 每 search before the requested time, false 每 search after the requested
     *            time.
     * @return the information about the pool found. Can contain 1 element (the pool found), or be empty (no
     *         pool found). Can not be null. No elements of the array can be null.
     */
    public SearchResult[] searchLastSessionPoolActivity(boolean isPushed, long user, long time,
            boolean isBefore) {
        return results;
    }

    /**
     * Implements the all user pool searching algorithm on the message pool for the predefined time. This
     * method searches for the all user pool, which access time is on the correct side of the requested time.
     * The all user pools are processed to find all of them with the access time (push or pull) on the correct
     * side of the requested time. If the isBefore flag is true, then the searched time should be before the
     * requested time, if false 每 then the searched time should be after the requested time. Any number search
     * result returned. No results can be returned only if the message pool has no user＊s pool, or the all
     * existed user＊s pools are on the opposite side of the requested time.
     * <p>
     * Exception handling. None.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @param time
     *            the requested time value used in the searching algorithm. Can be any value.
     * @param isBefore
     *            a control flag to define, from which side of the requested time the searching algorithm
     *            should work. true 每 search before the requested time, false 每 search after the requested
     *            time.
     * @return the information about the pool found. Can contain any elements (the pool found), or be empty
     *         (no pool found). Can not be null. No elements of the array can be null.
     */
    public SearchResult[] searchUserPoolActivity(boolean isPushed, long time, boolean isBefore) {
        return results;
    }

    /**
     * Implements the all session pool searching algorithm on the message pool for the predefined time. This
     * method searches for the all session pools, which access time is on the correct side of the requested
     * time. The all user pools are processed to find session pools with the access time (push or pull) on the
     * correct side of the requested time. If the isBefore flag is true, then the searched time should be
     * before the requested time, if false 每 then the searched time should be after the requested time. So,
     * for each user the all session pools (if any) are returned. The number of results can be any value. No
     * results can be returned only if the message pool has no user＊s pool, or each user pool has no session
     * pool, or the all existed session＊s pools are on the opposite side of the requested time.
     * <p>
     * Exception handling. None.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @param time
     *            the requested time value used in the searching algorithm. Can be any value.
     * @param isBefore
     *            a control flag to define, from which side of the requested time the searching algorithm
     *            should work. true 每 search before the requested time, false 每 search after the requested
     *            time.
     * @return the information about the pool found. Can contain information about one session pool element
     *         for the each user. Can be any value. The array can be empty. Can not be null. No elements of
     *         the array can be null.
     */
    public SearchResult[] searchSessionPoolActivity(boolean isPushed, long time, boolean isBefore) {
        return results;
    }

    /**
     * Implements the all user pool searching algorithm on the message pool for the predefined time interval.
     * This method searches for the all user pool, which access time is in the correct side of the requested
     * time interval. The all user pools are processed to all find of them with the access time (push or pull)
     * on the correct side of the requested time interval. If the isBetween flag is true, then the searched
     * time should be inside (inclusive) the requested time interval, if false 每 then the searched time should
     * be outside (exclusive) the requested time interval. Any number search result returned. No results can
     * be returned only if the message pool has no user＊s pool, or the all existed user＊s pools are on the
     * opposite side of the requested time interval.
     * <p>
     * Exception handling. IllegalArgumentException is thrown if the timeStart > timeStop.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @param timeStart
     *            the beginning of the requested time interval used in the searching algorithm. Can be any
     *            value, but should be below or equal to the timeStop argument.
     * @param timeStop
     *            the ending of the requested time interval used in the searching algorithm. Can be any value,
     *            but should be greater or equal to the timeStart argument.
     * @param isBetween
     *            a control flag to define, from which side of the requested time interval the searching
     *            algorithm should work. true 每 search inside (inclusive) the requested time interval, false 每
     *            search outside (exclusive) the requested time interval.
     * @return the information about the pool found. Can contain any elements (the pool found), or be empty
     *         (no pool found). Can not be null. No elements of the array can be null.
     */
    public SearchResult[] searchUserPoolActivity(boolean isPushed, long timeStart, long timeStop,
            boolean isBetween) {
        return results;
    }

    /**
     * Implements the all session pool searching algorithm on the message pool for the predefined time
     * interval. This method searches for the all session pools, which access time is on the correct side of
     * the requested time interval. The all user pools are processed to find session pools with the access
     * time (push or pull) on the correct side of the requested time interval. If the isBetween flag is true,
     * then the searched time should be inside (inclusive) the requested time interval, if false 每 then the
     * searched time should be outside (exclusive) the requested time interval. So, for each user the all
     * session pools (if any) are returned. The number of results can be any value. No results can be returned
     * only if the message pool has no user＊s pool, or each user pool has no session pool, or the all existed
     * session＊s pools are on the opposite side of the requested time interval.
     * <p>
     * Exception handling. IllegalArgumentException is thrown if the timeStart > timeStop.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @param timeStart
     *            the beginning of the requested time interval used in the searching algorithm. Can be any
     *            value, but should be below or equal to the timeStop argument.
     * @param timeStop
     *            the ending of the requested time interval used in the searching algorithm. Can be any value,
     *            but should be greater or equal to the timeStart argument.
     * @param isBetween
     *            a control flag to define, from which side of the requested time interval the searching
     *            algorithm should work. true 每 search inside (inclusive) the requested time, false 每 search
     *            outside (exclusive) the requested time.
     * @return the information about the pool found. Can contain information about one session pool element
     *         for the each user. Can be any value. The array can be empty. Can not be null. No elements of
     *         the array can be null.
     */
    public SearchResult[] searchSessionPoolActivity(boolean isPushed, long timeStart, long timeStop,
            boolean isBetween) {
        return results;
    }

    /**
     * Implements the all session pool searching algorithm on the message pool for the predefined time
     * interval. This method searches for the all session pools, which acccess time is on the correct side of
     * the requested time interval, and the searching is impelemented for the concrete user. The requested
     * user pool is processed to find session pool with the access time (push or pull) on the correct side of
     * the requested time interval. If the isBetween flag is true, then the searched time should be inside
     * (inclusive) the requested time interval, if false 每 then the searched time should be outside
     * (exclusive) the requested time interval. Any number of results are returned. No results can be returned
     * only if the user pool has no session pool, or the all existed session＊s pools are on the opposite side
     * of the requested time interval.
     * <p>
     * Exception handling. IllegalArgumentException is thrown if the timeStart > timeStop.
     * PoolNotRegisteredException is thrown if the requested user pool is not found.
     * </p>
     *
     *
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching. true 每
     *            search by the time of the last push operation to the pool, false 每 search by the time of the
     *            last pull operation from the pool.
     * @param user
     *            user id, for which user＊s pool the searching algorithm should execute
     * @param timeStart
     *            the beginning of the requested time interval used in the searching algorithm. Can be any
     *            value, but should be below or equal to the timeStop argument.
     * @param timeStop
     *            the ending of the requested time interval used in the searching algorithm. Can be any value,
     *            but should be greater or equal to the timeStart argument.
     * @param isBetween
     *            a control flag to define, from which side of the requested time interval the searching
     *            algorithm should work. true 每 search inside (inclusive) the requested time, false 每 search
     *            outside (exclusive) the requested time.
     * @return the information about the pool found. Can several number of elements (the pool found), or be
     *         empty (no pool found). Can not be null. No elements of the array can be null.
     */
    public SearchResult[] searchSessionPoolActivity(boolean isPushed, long user, long timeStart,
            long timeStop, boolean isBetween) {
        return results;
    }
}
