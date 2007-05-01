/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.session.SalesSession;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.database.statustracker.Status;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for UserSessionEventListener class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserSessionEventListenerUnitTests extends TestCase {

    /**
     * An instance of UserSessionEventListener for the following tests.
     */
    private UserSessionEventListener listener = null;

    /**
     * An instance of ChatSessionStatusTracker for the following tests.
     */
    private ChatSessionStatusTracker chatSessionStatusTracker = null;

    /**
     * An instance of ChatSessionManager for the following tests.
     */
    private ChatSessionManager chatSessionManager = null;

    /**
     * An instance of Messenger for the following tests.
     */
    private Messenger messenger = null;

    /**
     * An instance of IMLogger for the following tests.
     */
    private IMLogger logger = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();

        chatSessionStatusTracker = new ChatSessionStatusTracker();
        chatSessionStatusTracker.setStatus(1, new Status(IMHelper.SESSION_STATUS_OPEN));
        chatSessionManager = new MockChatSessionManager();
        messenger = new MockMessenger();
        logger = new IMLogger(null);
        listener = new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker,
                1000, logger);
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Tests the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger messenger,
     * ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger). No exception
     * should be thrown.
     */
    public void test_constructor1_1() {
        new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, 1000, logger);
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger).
     * IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void test_constructor1_failure1() {
        try {
            new UserSessionEventListener(null, messenger, chatSessionStatusTracker, 1000, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger).
     * IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void test_constructor1_failure2() {
        try {
            new UserSessionEventListener(chatSessionManager, null, chatSessionStatusTracker, 1000, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger).
     * IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void test_constructor1_failure3() {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, null, 1000, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger).
     * IllegalArgumentException should be thrown if the time is negative.
     */
    public void test_constructor1_failure4() {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, -1000,
                    logger);
            fail("IllegalArgumentException should be thrown if the time is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger messenger,
     * ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger). No exception
     * should be thrown.
     */
    public void test_constructor1_2() {
        new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, 1000, null);
    }

    /**
     * Tests the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger messenger,
     * ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger). No exception
     * should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor2_1() throws Exception {
        new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, logger);
    }

    /**
     * Tests the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger messenger,
     * ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger). No exception
     * should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor2_2() throws Exception {
        new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, null);
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IllegalArgumentException
     * should be thrown if any argument except logger is null.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor2_failure1() throws Exception {
        try {
            new UserSessionEventListener(null, messenger, chatSessionStatusTracker, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IllegalArgumentException
     * should be thrown if any argument except logger is null.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor2_failure2() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, null, chatSessionStatusTracker, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IllegalArgumentException
     * should be thrown if any argument except logger is null.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor2_failure3() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, null, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger messenger,
     * ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger). No exception
     * should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor3_1() throws Exception {
        new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, logger,
                UserSessionEventListener.DEFAULT_NAMESPACE);
    }

    /**
     * Tests the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger messenger,
     * ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger). No exception
     * should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor3_2() throws Exception {
        new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, null,
                UserSessionEventListener.DEFAULT_NAMESPACE);
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IllegalArgumentException
     * should be thrown if any argument except logger is null.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor3_failure1() throws Exception {
        try {
            new UserSessionEventListener(null, messenger, chatSessionStatusTracker, logger,
                    UserSessionEventListener.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IllegalArgumentException
     * should be thrown if any argument except logger is null.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor3_failure2() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, null, chatSessionStatusTracker, logger,
                    UserSessionEventListener.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IllegalArgumentException
     * should be thrown if any argument except logger is null.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor3_failure3() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, null, logger,
                    UserSessionEventListener.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IllegalArgumentException
     * should be thrown if any argument except logger is null.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor3_failure4() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, logger,
                    null);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IllegalArgumentException
     * should be thrown if namespace is empty.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor3_failure5() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, logger,
                    "   ");
            fail("IllegalArgumentException should be thrown if namespace is empty.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IMConfigurationException
     * should be thrown if configuration is invalid.
     */
    public void test_constructor3_failure6() {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, logger,
                    UserSessionEventListener.DEFAULT_NAMESPACE + ".Invalid1");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IMConfigurationException
     * should be thrown if configuration is invalid.
     */
    public void test_constructor3_failure7() {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, logger,
                    UserSessionEventListener.DEFAULT_NAMESPACE + ".Invalid2");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor UserSessionEventListener(ChatSessionManager sessionManager, Messenger
     * messenger, ChatSessionStatusTracker sessionStatusTracker, IMLogger logger). IMConfigurationException
     * should be thrown if configuration is invalid.
     */
    public void test_constructor3_failure8() {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, logger,
                    UserSessionEventListener.DEFAULT_NAMESPACE + ".Invalid3");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the userRequested method. No exception should be thrown.
     */
    public void test_userRequested_1() {
        // no exception
        ChatSession session = new SalesSession(123, 456);
        session.setId(1);
        listener.userRequested(session, 123);
        
        assertTrue(MockMessenger.getMessage().endsWith("postMessage(..., 123)"));
    }

    /**
     * Failure test for the userRequested method. IllegalArgumentException should be thrown if session is
     * null.
     */
    public void test_userRequested_failure1() {
        try {
            listener.userRequested(null, 123);
            fail("IllegalArgumentException should be thrown if session is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the userAdded method. No exception should be thrown.
     */
    public void test_userAdded_1() {
        // no exception
        ChatSession session = new SalesSession(123, 456);
        session.addActiveUser(1);
        session.addActiveUser(2);
        session.addActiveUser(3);
        session.setId(1);
        listener.userAdded(session, 123);

        String s = MockMessagePool.getMessage();
        assertTrue(s.endsWith("register(123, 1)"));
        s = MockMessenger.getMessage();
        assertTrue(s.endsWith("postMessage(..., 123)postMessageToOthers(..., ...)postMessage(..., 123, 1)postMessage(..., 123, 1)postMessage(..., 123, 1)"));
    }

    /**
     * Failure test for the userRequested method. IllegalArgumentException should be thrown if session is
     * null.
     */
    public void test_userAdded_failure1() {
        try {
            listener.userAdded(null, 123);
            fail("IllegalArgumentException should be thrown if session is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the userRemoved method. No exception should be thrown.
     */
    public void test_userRemoved_1() {
        // no exception
        ChatSession session = new SalesSession(123, 456);
        session.addActiveUser(1);
        session.addActiveUser(2);
        session.addActiveUser(3);
        session.setId(1);
        listener.userRemoved(session, 123);

        String s = MockMessagePool.getMessage();
        assertTrue(s.endsWith(";register(123, 1);unregister(123, 1)"));
        s = MockMessenger.getMessage();
        assertTrue(s.endsWith("postMessage(..., 123)postMessageToOthers(..., ...)postMessage(..., 123, 1)postMessage(..., 123, 1)postMessage(..., 123, 1)postMessage(..., 2, 1)postMessage(..., 1, 1)postMessage(..., 3, 1)"));
    }

    /**
     * Failure test for the userRequested method. IllegalArgumentException should be thrown if session is
     * null.
     */
    public void test_userRemoved_failure1() {
        try {
            listener.userRemoved(null, 123);
            fail("IllegalArgumentException should be thrown if session is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the userRemoved method. No exception should be thrown.
     */
    public void test_userRemoved_2() {
        // no exception
        ChatSession session = new SalesSession(123, 456);
        listener.userRemoved(session, 123);
    }

    /**
     * Tests the getMessenger method. The returned result should be correct.
     */
    public void test_getMessenger() {
        assertEquals("The getMessenger method is incorrect.", messenger, listener.getMessenger());
    }

    /**
     * Tests the getChatSessionManager method. The returned result should be correct.
     */
    public void test_getChatSessionManager() {
        assertEquals("The getChatSessionManager method is incorrect.", chatSessionManager, listener
                .getChatSessionManager());
    }

    /**
     * Tests the getChatSessionStatusTracker method. The returned result should be correct.
     */
    public void test_getChatSessionStatusTracker() {
        assertEquals("The getChatSessionStatusTracker method is incorrect.", chatSessionStatusTracker,
                listener.getChatSessionStatusTracker());
    }

    /**
     * Tests the getAcknowledgeTime method. The returned result should be correct.
     */
    public void test_getAcknowledgeTime() {
        assertEquals("The getMessenger method is incorrect.", 1000, listener.getAcknowledgeTime());
    }

}
