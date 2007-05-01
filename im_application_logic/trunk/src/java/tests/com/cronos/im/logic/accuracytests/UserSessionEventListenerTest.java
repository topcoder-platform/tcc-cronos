/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import java.text.DateFormat;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.UserSessionEventListener;
import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.session.OneOneSession;
import com.topcoder.chat.status.ChatSessionStatusTracker;

/**
 * Accuracy test for <code>{@link com.cronos.im.logic.UserSessionEventListener}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class UserSessionEventListenerTest extends TestCase {
    /**
     * Represents UserSessionEventListener for testing.
     */
    private UserSessionEventListener eventListener;

    /**
     * Represents IMLogger for testing.
     */
    private IMLogger logger;

    /**
     * Represents ChatSessionStatusTracker for testing.
     */
    private ChatSessionStatusTracker chatSessionStatusTracker;

    /**
     * Represents ChatSessionManager for testing.
     */
    private ChatSessionManager chatSessionManager;

    /**
     * Represents Messenger for testing.
     */
    private Messenger messenger;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadConfigs();

        chatSessionStatusTracker = new ChatSessionStatusTracker();
        chatSessionManager = new MockChatSessionManager();
        messenger = new MockMessenger();

        logger = new IMLogger(DateFormat.getDateInstance());
        eventListener = new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker,
            AccuracyTestHelper.ONE_SEC, logger);
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.releaseConfigs();
    }

    /**
     * Accuracy test for
     * <code>{@link UserSessionEventListener#UserSessionEventListener(ChatSessionManager,Messenger,
     * ChatSessionStatusTracker,long,IMLogger)}</code>.
     */
    public void testConstructor_1() {
        assertNotNull("Failed to create UserSessionEventListener", eventListener);
    }

    /**
     * Accuracy test for
     * <code>{@link UserSessionEventListener#UserSessionEventListener(ChatSessionManager,Messenger,
     * ChatSessionStatusTracker,IMLogger,String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_2() throws Exception {
        eventListener = new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker,
            logger, UserSessionEventListener.DEFAULT_NAMESPACE);
        assertNotNull("Failed to create UserSessionEventListener", eventListener);
    }

    /**
     * Accuracy test for
     * <code>{@link UserSessionEventListener#UserSessionEventListener(ChatSessionManager,Messenger,
     * ChatSessionStatusTracker,IMLogger)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_3() throws Exception {
        eventListener = new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker,
            logger);
        assertNotNull("Failed to create UserSessionEventListener", eventListener);
    }

    /**
     * Accuracy test for <code>{@link UserSessionEventListener#getChatSessionStatusTracker()}</code>.
     */
    public void testMethodGetChatSessionStatusTracker() {
        assertNotNull("Failed to get chat session status tracker", eventListener
            .getChatSessionStatusTracker());
    }

    /**
     * Accuracy test for <code>{@link UserSessionEventListener#getMessenger()}</code>.
     */
    public void testMethodGetMessenger() {
        assertNotNull("Failed to get messenger", eventListener.getMessenger());
    }

    /**
     * Accuracy test for <code>{@link UserSessionEventListener#getAcknowledgeTime()}</code>.
     */
    public void testMethodGetAcknowledgeTime() {
        assertEquals("Failed to get acknowldgeTime", AccuracyTestHelper.ONE_SEC, eventListener
            .getAcknowledgeTime());
    }

    /**
     * Accuracy test for <code>{@link UserSessionEventListener#getChatSessionManager()}</code>.
     */
    public void testMethodGetChatSessionManager() {
        assertNotNull("Failed to get chat session manager", eventListener.getChatSessionManager());
    }

    /**
     * Accuracy test for <code>{@link UserSessionEventListener#userRequested(ChatSession,long)}</code>.
     */
    public void testMethodUserRequested_ChatSession_long() {
        eventListener.userRequested(new OneOneSession(AccuracyTestHelper.REQ_USER_ID),
            AccuracyTestHelper.REQ_USER_ID);
    }

    /**
     * Accuracy test for <code>{@link UserSessionEventListener#userRemoved(ChatSession,long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUserRemoved_ChatSession_long() throws Exception {
        // chatSessionStatusTracker.setStatus(id, status);
        ChatSession session = new OneOneSession(AccuracyTestHelper.SESSION_ID,
            AccuracyTestHelper.REQ_USER_ID, new Date(), new long[] {}, new long[] {}, new long[] {});
        eventListener.userRemoved(session, AccuracyTestHelper.REQ_USER_ID);
    }

    /**
     * Accuracy test for <code>{@link UserSessionEventListener#userAdded(ChatSession,long)}</code>.
     */
    public void testMethodUserAdded_ChatSession_long() {
        eventListener.userAdded(new OneOneSession(AccuracyTestHelper.REQ_USER_ID),
            AccuracyTestHelper.REQ_USER_ID);
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserSessionEventListenerTest.class);
    }
}
