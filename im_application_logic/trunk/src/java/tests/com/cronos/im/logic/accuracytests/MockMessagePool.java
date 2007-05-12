/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import com.cronos.im.messenger.EnterChatMessage;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.message.pool.SearchResult;
import com.topcoder.chat.message.pool.PoolNotRegisteredException;

/**
 * Mock MessagePool for tesing purpose.
 *
 * @author mittu
 * @version 1.0
 */
public class MockMessagePool implements MessagePool {

    private Message[] mockMessages;

    private SearchResult[] mockResults;

    public MockMessagePool() {
        mockMessages = new Message[] {new EnterChatMessage()};
        SearchResult result = new SearchResult(1, 1, System.currentTimeMillis(), true, true);
        mockResults = new SearchResult[] {result};
    }


    public Message[] pull(long user) {
        return mockMessages;
    }

    public Message[] pull(long user, long session) {
        return mockMessages;
    }

    public void push(long user, Message message) {
    }

    public void push(long[] users, Message message) {
    }

    public void push(long user, long session, Message message) {
    }

    public void push(long[] users, long session, Message message) {
    }

    public void push(long user, long[] sessions, Message message) {
    }

    public void register(long user) {
    }

    public void register(long user, long session) {
    }

    public SearchResult[] searchLastSessionPoolActivity(boolean isPushed) {
        return mockResults;
    }

    public SearchResult[] searchLastSessionPoolActivity(boolean isPushed, long user) {
        return mockResults;
    }

    public SearchResult[] searchLastSessionPoolActivity(boolean isPushed, long time, boolean isBefore) {
        return mockResults;
    }

    public SearchResult[] searchLastSessionPoolActivity(boolean isPushed, long user, long time,
        boolean isBefore) {
        return mockResults;
    }

    public SearchResult[] searchLastUserPoolActivity(boolean isPushed) {
        return mockResults;
    }

    public SearchResult[] searchLastUserPoolActivity(boolean isPushed, long time, boolean isBefore) {
        return mockResults;
    }

    public SearchResult[] searchSessionPoolActivity(boolean isPushed, long time, boolean isBefore) {
        return mockResults;
    }

    public SearchResult[] searchSessionPoolActivity(boolean isPushed, long timeStart, long timeStop,
        boolean isBetween) {
        return mockResults;
    }

    public SearchResult[] searchSessionPoolActivity(boolean isPushed, long user, long timeStart,
        long timeStop, boolean isBetween) {
        return mockResults;
    }

    public SearchResult[] searchUserPoolActivity(boolean isPushed, long time, boolean isBefore) {
        return mockResults;
    }

    public SearchResult[] searchUserPoolActivity(boolean isPushed, long timeStart, long timeStop,
        boolean isBetween) {
        return mockResults;
    }

    public void unregister(long user) {
    }

    public void unregister(long user, long session) {
    }

    /**
     * <p>
     * Implements the all session pool searching algorithm on the message pool for the predefined
     * time interval. This method searches for the all session pools, which access time is on the
     * correct side of the requested time interval. The all user pools are processed to find session
     * pools with the access time (push or pull) on the correct side of the requested time interval.
     * If the isBetween flag is true, then the searched time should be inside (inclusive) the
     * requested time interval, if false 每 then the searched time should be outside (exclusive) the
     * requested time interval. So, for each user the all session pools (if any) are returned. The
     * number of results can be any value. No results can be returned only if the message pool has
     * no user＊s pool, or each user pool has no session pool, or the all existed session＊s pools are
     * on the opposite side of the requested time interval.
     * </p>
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching.
     *            true 每 search by the time of the last push operation to the pool, false 每 search
     *            by the time of the last pull operation from the pool.
     * @param timeStart
     *            the beginning of the requested time interval used in the searching algorithm. Can
     *            be any value, but should be below or equal to the timeStop argument.
     * @param timeStop
     *            the ending of the requested time interval used in the searching algorithm. Can be
     *            any value, but should be greater or equal to the timeStart argument.
     * @param isBetween
     *            a control flag to define, from which side of the requested time interval the
     *            searching algorithm should work. true 每 search inside (inclusive) the requested
     *            time, false 每 search outside (exclusive) the requested time.
     * @return the information about the pool found. Can contain information about one session pool
     *         element for the each user. Can be any value. The array can be empty. Can not be null.
     *         No elements of the array can be null.
     * @throws IllegalArgumentException
     *             If the timeStart is greater than timeStop.
     * @since 1.1
     */
    public SearchResult[] searchSessionPoolActivity(boolean isPushed, boolean isBetween,
            long timeStart, long timeStop) {
        return mockResults;
    }
    /**
     * <p>
     * Implements the all user pool searching algorithm on the message pool for the predefined time
     * interval. This method searches for the all user pool, which access time is in the correct
     * side of the requested time interval. The all user pools are processed to all find of them
     * with the access time (push or pull) on the correct side of the requested time interval. If
     * the isBetween flag is true, then the searched time should be inside (inclusive) the requested
     * time interval, if false 每 then the searched time should be outside (exclusive) the requested
     * time interval. Any number search result returned. No results can be returned only if the
     * message pool has no user＊s pool, or the all existed user＊s pools are on the opposite side of
     * the requested time interval.
     * </p>
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching.
     *            true 每 search by the time of the last push operation to the pool, false 每 search
     *            by the time of the last pull operation from the pool.
     * @param timeStart
     *            the beginning of the requested time interval used in the searching algorithm. Can
     *            be any value, but should be below or equal to the timeStop argument.
     * @param timeStop
     *            the ending of the requested time interval used in the searching algorithm. Can be
     *            any value, but should be greater or equal to the timeStart argument.
     * @param isBetween
     *            a control flag to define, from which side of the requested time interval the
     *            searching algorithm should work. true 每 search inside (inclusive) the requested
     *            time interval, false 每 search outside (exclusive) the requested time interval.
     * @return the information about the pool found. Can contain any elements (the pool found), or
     *         be empty (no pool found). Can not be null. No elements of the array can be null.
     * @throws IllegalArgumentException
     *             If the timeStart is greater than timeStop.
     * @since 1.1
     */
    public SearchResult[] searchUserPoolActivity(boolean isPushed, boolean isBetween,
            long timeStart, long timeStop) {
        return mockResults;
    }

    /**
     * <p>
     * Implements the all session pool searching algorithm on the message pool for the predefined
     * time. This method searches for the all session pools, which access time is on the correct
     * side of the requested time, and the searching is implemented for the concrete user. The
     * requested user pool is processed to find session pool with the access time (push or pull) on
     * the correct side of the requested time. If the isBefore flag is true, then the searched time
     * should be before the requested time, if false 每 then the searched time should be after the
     * requested time. Any number of results are returned. No results can be returned only if the
     * user pool has no session pool, or the all existed session＊s pools are on the opposite side of
     * the requested time.
     * </p>
     * @param isPushed
     *            a control flag used to define, what accessed time should be used for searching.
     *            true 每 search by the time of the last push operation to the pool, false 每 search
     *            by the time of the last pull operation from the pool.
     * @param user
     *            user id, for which user＊s pool the searching algorithm should execute
     * @param time
     *            the requested time value used in the searching algorithm. Can be any value.
     * @param isBefore
     *            a control flag to define, from which side of the requested time the searching
     *            algorithm should work. true 每 search before the requested time, false 每 search
     *            after the requested time.
     * @return the information about the pool found. Can several number of elements (the pool
     *         found), or be empty (no pool found). Can not be null. No elements of the array can be
     *         null.
     * @throws PoolNotRegisteredException
     *             If the requested user pool is not found.
     * @since 1.1
     */
    public SearchResult[] searchSessionPoolActivity(boolean isPushed, long user, boolean isBefore,
            long time) throws PoolNotRegisteredException {
        return mockResults;
    }
}
