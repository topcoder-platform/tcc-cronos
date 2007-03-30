/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.MessageTracker;
import com.cronos.im.messenger.MessengerException;
import com.cronos.im.messenger.SessionUnavailableMessage;
import com.cronos.im.messenger.UserIDRetriever;
import com.cronos.im.messenger.impl.ChatMessageTrackerImpl;
import com.cronos.im.messenger.impl.MessengerImpl;
import com.cronos.im.messenger.impl.UserIDRetrieverImpl;
import com.topcoder.chat.contact.ChatContactManager;
import com.topcoder.chat.contact.SimpleChatContactManager;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.message.pool.impl.DefaultMessagePool;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.session.GroupSession;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * Tests the {@link MessengerImpl} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class MessengerImplFailure extends TestCase {

    /**
     * Represents the MessengerImpl.
     */
    private MessengerImpl messengerImpl;

    /**
     * Represents the MessagePool.
     */
    private MessagePool messagePool;

    /**
     * Represents the ChatContactManager.
     */
    private ChatContactManager chatContactManager;

    /**
     * Represents the MessageTracker.
     */
    private MessageTracker messageTracker;

    /**
     * Represents the UserIdRetriever.
     */
    private UserIDRetriever userIDRetriever;

    /**
     * Represents the ChatSession.
     */
    private ChatSession chatSession;

    /**
     * Represents the Message.
     */
    private Message message;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadConfigFile("db_connection_factory_config.xml");
        FailureTestHelper.loadConfigFile("chat_session_manager_config.xml");
        FailureTestHelper.loadConfigFile("chat_contact_manager_config.xml");
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        messageTracker = new ChatMessageTrackerImpl(connectionFactory, "connectionName", "user");
        userIDRetriever = new UserIDRetrieverImpl();
        messagePool = new DefaultMessagePool();
        chatContactManager = new SimpleChatContactManager();
        messengerImpl = new MessengerImpl(messagePool, messageTracker, userIDRetriever, chatContactManager);
        chatSession = new GroupSession(1, 1, new Date(), new long[] {}, new long[] {}, new long[] {1, 3 },
                "TopCoder");
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        messengerImpl = null;
        FailureTestHelper.releaseConfigFiles();
    }

    /**
     * <p>
     * Tests the constructor of <code>MessengerImpl</code> for null <code>MessagePool</code>.
     * </p>
     *
     */
    public void testChatMessageTrackerImpl() {
        try {
            messengerImpl = new MessengerImpl(null, messageTracker, userIDRetriever, chatContactManager);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the constructor of <code>MessengerImpl</code> for null <code>MessageTracker</code>.
     * </p>
     *
     */
    public void testChatMessageTrackerImpl1() {
        try {
            messengerImpl = new MessengerImpl(messagePool, null, userIDRetriever, chatContactManager);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the constructor of <code>MessengerImpl</code> for null <code>UserIDRetriever</code>.
     * </p>
     *
     */
    public void testChatMessageTrackerImpl2() {
        try {
            messengerImpl = new MessengerImpl(messagePool, messageTracker, null, chatContactManager);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the constructor of <code>MessengerImpl</code> for null <code>ChatContactManager</code>.
     * </p>
     *
     */
    public void testChatMessageTrackerImpl3() {
        try {
            messengerImpl = new MessengerImpl(messagePool, messageTracker, userIDRetriever, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Sets the message as null.
     * </p>
     *
     */
    public void testPostMessage() {
        try {
            messengerImpl.postMessage(null, 1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (MessengerException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Sets the message as null.
     * </p>
     *
     */
    public void testPostMessage1() {
        try {
            messengerImpl.postMessage(null, 1, 1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (MessengerException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the postMessageToOthers method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Sets the message as null.
     * </p>
     *
     */
    public void testPostMessageToOthers1() {
        try {
            messengerImpl.postMessageToOthers(null, chatSession);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (MessengerException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the postMessageToOthers method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Sets the chat session as null.
     * </p>
     *
     */
    public void testPostMessageToOthers2() {
        try {
            messengerImpl.postMessageToOthers(message, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (MessengerException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the postMessageToAll method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Sets the message as null.
     * </p>
     *
     */
    public void testPostMessageToAll1() {
        try {
            messengerImpl.postMessageToAll(null, chatSession);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (MessengerException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the postMessageToAll method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Sets the chat session as null.
     * </p>
     *
     */
    public void testPostMessageToAll2() {
        try {
            messengerImpl.postMessageToAll(message, null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (MessengerException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the setMessagePool method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Sets the message pool as null.
     * </p>
     *
     */
    public void testSetMessagePool() {
        try {
            messengerImpl.setMessagePool(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose sender is null.
     * </p>
     *
     */
    public void testPostMessage2() {
        try {
            message = new SessionUnavailableMessage();
            message.setSender(null);
            messengerImpl.postMessage(message, 1);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose sender and receiver have same id.
     * </p>
     *
     */
    public void testPostMessage3() {
        try {
            message = new SessionUnavailableMessage();
            message.setSender(new Long(1));
            messengerImpl.postMessage(message, 1);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose user id is unknown.
     * </p>
     *
     */
    public void testPostMessage4() {
        try {
            message = new SessionUnavailableMessage();
            message.setSender(new Long(2));
            messengerImpl.postMessage(message, 100);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose sender id is unknown.
     * </p>
     *
     */
    public void testPostMessage5() {
        try {
            message = new SessionUnavailableMessage();
            message.setSender(new Long(100));
            messengerImpl.postMessage(message, 1);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose user pool doesn't exists.
     * </p>
     *
     */
    public void testPostMessage6() {
        try {
            message = new SessionUnavailableMessage();
            message.setSender(new Long(2));
            messengerImpl.postMessage(message, 1);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose sender is null.
     * </p>
     *
     */
    public void testPostMessage7() {
        try {
            message = new ChatMessage();
            message.setSender(null);
            messengerImpl.postMessage(message, 1, 1);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose sender and receiver have same id.
     * </p>
     *
     */
    public void testPostMessage8() {
        try {
            message = new ChatMessage();
            message.setSender(new Long(1));
            messengerImpl.postMessage(message, 1, 1);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose user id is unknown.
     * </p>
     *
     */
    public void testPostMessage9() {
        try {
            message = new ChatMessage();
            message.setSender(new Long(2));
            messengerImpl.postMessage(message, 100, 1);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose sender id is unknown.
     * </p>
     *
     */
    public void testPostMessage10() {
        try {
            message = new ChatMessage();
            message.setSender(new Long(100));
            messengerImpl.postMessage(message, 1, 1);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose session id is unknown.
     * </p>
     *
     */
    public void testPostMessage11() {
        try {
            message = new ChatMessage();
            message.setSender(new Long(1));
            messengerImpl.postMessage(message, 1, 100);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessage method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose user pool doesn't exists.
     * </p>
     *
     */
    public void testPostMessage12() {
        try {
            message = new ChatMessage();
            message.setSender(new Long(2));
            messengerImpl.postMessage(message, 1, 1);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessageToOthers method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose sender is null.
     * </p>
     *
     */
    public void testPostMessageToOthers3() {
        try {
            message = new ChatMessage();
            message.setSender(null);
            messengerImpl.postMessageToOthers(message, chatSession);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessageToOthers method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose sender is null.
     * </p>
     *
     */
    public void testPostMessageToOthers4() {
        try {
            message = new ChatMessage();
            message.setSender(new Long(1));
            messengerImpl.postMessageToOthers(message, chatSession);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessageToAll method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose sender is null.
     * </p>
     *
     */
    public void testPostMessageToAll3() {
        try {
            message = new ChatMessage();
            message.setSender(null);
            messengerImpl.postMessageToAll(message, chatSession);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the postMessageToAll method of <code>MessengerImpl</code>.
     * </p>
     *
     * <p>
     * Tries to post a message whose sender is null.
     * </p>
     *
     */
    public void testPostMessageToAll4() {
        try {
            message = new ChatMessage();
            message.setSender(new Long(1));
            messengerImpl.postMessageToAll(message, chatSession);
            fail("Should throw MessengerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessengerException");
        } catch (MessengerException e) {
            // expect
        }
    }
}
