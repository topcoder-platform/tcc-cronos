/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import java.text.DateFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.SessionStatusEventListener;
import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.Status;

/**
 * Accuracy test for <code>{@link com.cronos.im.logic.SessionStatusEventListener}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class SessionStatusEventListenerTest extends TestCase {
    /**
     * Represents SessionStatusEventListener for testing.
     */
    private SessionStatusEventListener eventListener;

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

        eventListener = new SessionStatusEventListener(chatSessionStatusTracker, chatSessionManager,
            messenger, logger);
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
     * <code>{@link SessionStatusEventListener#SessionStatusEventListener(ChatSessionStatusTracker,
     * ChatSessionManager,Messenger,IMLogger)}</code>.
     */
    public void testConstructor() {
        assertNotNull("Failed to create SessionStatusEventListener", eventListener);
    }

    /**
     * Accuracy test for <code>{@link SessionStatusEventListener#getChatSessionStatusTracker()}</code>.
     */
    public void testMethodGetChatSessionStatusTracker() {
        assertNotNull("Failed to get chat session status tracker", eventListener
            .getChatSessionStatusTracker());
    }

    /**
     * Accuracy test for <code>{@link SessionStatusEventListener#getMessenger()}</code>.
     */
    public void testMethodGetMessenger() {
        assertNotNull("Failed to get messenger", eventListener.getMessenger());
    }

    /**
     * Accuracy test for <code>{@link SessionStatusEventListener#getChatSessionManager()}</code>.
     */
    public void testMethodGetChatSessionManager() {
        assertNotNull("Failed to get chat session manager", eventListener.getChatSessionManager());
    }

    /**
     * Accuracy test for
     * <code>{@link SessionStatusEventListener#statusChanged(long,Entity,Status,Status)}</code>.
     */
    public void testMethodStatusChanged_long_Entity_Status_Status() {
        Status oldStatus = new Status(204);
        Status newStatus = new Status(203);
        Entity entity = new Entity(2, "accuracy_test", new String[] { "key" }, new Status[] { new Status(
            AccuracyTestHelper.SESSION_ID) });
        eventListener.statusChanged(AccuracyTestHelper.SESSION_ID, entity, oldStatus, newStatus);
        // Check the log
        // Expected actions
        // 1) Send EnterChatMessage to all users
        // 2) Register MessagePool for user
        // 3) Send PresenceMessage to others
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(SessionStatusEventListenerTest.class);
    }
}
