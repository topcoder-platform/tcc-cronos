/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import com.cronos.im.messenger.EnterChatMessage;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.message.pool.SearchResult;

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

}
