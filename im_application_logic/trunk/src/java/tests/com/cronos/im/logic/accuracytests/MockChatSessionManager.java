/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.session.ChatSessionDataStoreException;
import com.topcoder.chat.session.ChatSessionEventListener;
import com.topcoder.chat.session.ChatSessionIncorrectEntityIdException;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.session.ChatSessionModeIncorrectOperationException;
import com.topcoder.chat.session.OneOneSession;

/**
 * Mock ChatSessionManager for testing purpose.
 *
 * @author mittu
 * @version 1.0
 */
public class MockChatSessionManager implements ChatSessionManager {

    public void addChatSessionEventListener(ChatSessionEventListener listener) {
    }

    public void addUserToSession(ChatSession session, long user)
        throws ChatSessionIncorrectEntityIdException, ChatSessionModeIncorrectOperationException,
        ChatSessionDataStoreException {
    }

    public void createSession(ChatSession session) throws ChatSessionDataStoreException {
    }

    public ChatSessionEventListener[] getChatSessionEventListeners() {
        return null;
    }

    public long[] getRecentlyRequestedUsers(long user) throws ChatSessionDataStoreException,
        ChatSessionIncorrectEntityIdException {
        return null;
    }

    public long[] getRecentlyRequestedUsers(long user, int recent) throws ChatSessionDataStoreException,
        ChatSessionIncorrectEntityIdException {
        return null;
    }

    public ChatSession getSession(long id) throws ChatSessionDataStoreException,
        ChatSessionIncorrectEntityIdException {
        OneOneSession session = new OneOneSession(id);
        session.addUser(20);
        session.addRequestedUser(33);
        return session;
    }

    public ChatSession[] getSessions(long[] ids) throws ChatSessionDataStoreException {
        return null;
    }

    public boolean hasSessions(long user) throws ChatSessionDataStoreException {
        return false;
    }

    public void removeChatSessionEventListener(ChatSessionEventListener listener) {
    }

    public void removeChatSessionEventListeners() {
    }

    public void removeUserFromSession(ChatSession session, long user) throws ChatSessionDataStoreException,
        ChatSessionIncorrectEntityIdException {
    }

    public void removeUserFromSessions(long user) throws ChatSessionDataStoreException {
    }

    public void requestUserToSession(ChatSession session, long user) throws ChatSessionDataStoreException {
    }
}