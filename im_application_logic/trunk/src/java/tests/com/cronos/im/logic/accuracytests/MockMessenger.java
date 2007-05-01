/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import com.cronos.im.messenger.Messenger;
import com.cronos.im.messenger.MessengerException;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.session.ChatSession;

/**
 * Mock Messenger for tesing purpose.
 *
 * @author mittu
 * @version 1.0
 */
public class MockMessenger implements Messenger {

    public MessagePool getMessagePool() {
        return new MockMessagePool();
    }

    public void postMessage(Message msg, long userId) throws MessengerException {
    }

    public void postMessage(Message msg, long userId, long sessionId) throws MessengerException {
    }

    public void postMessageToAll(Message msg, ChatSession session) throws MessengerException {
    }

    public void postMessageToOthers(Message msg, ChatSession session) throws MessengerException {
    }

    public void setMessagePool(MessagePool pool) {
    }
}