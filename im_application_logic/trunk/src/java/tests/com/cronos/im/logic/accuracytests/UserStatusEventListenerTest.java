/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import java.text.DateFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.UserStatusEventListener;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.status.ChatUserStatusTracker;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.Status;

/**
 * Accuracy test for <code>{@link com.cronos.im.logic.UserStatusEventListener}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class UserStatusEventListenerTest extends TestCase {
    /**
     * Represents UserSessionEventListener for testing.
     */
    private UserStatusEventListener eventListener;

    /**
     * Represents IMLogger for testing.
     */
    private IMLogger logger;

    /**
     * Represents ChatUserStatusTracker for testing.
     */
    private ChatUserStatusTracker chatUserStatusTracker;

    /**
     * Represents MessagePool for testing.
     */
    private MessagePool messagePool;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadConfigs();

        chatUserStatusTracker = new ChatUserStatusTracker();
        messagePool = new MockMessagePool();
        logger = new IMLogger(DateFormat.getDateInstance());
        eventListener = new UserStatusEventListener(chatUserStatusTracker, messagePool, logger);
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
     * Accuracy test for <code>{@link UserStatusEventListener#UserStatusEventListener(ChatUserStatusTracker,
     * MessagePool,IMLogger)}</code>.
     */
    public void testConstructor() {
        assertNotNull("Failed to create UserStatusEventListener", eventListener);
    }

    /**
     * Accuracy test for <code>{@link UserStatusEventListener#getChatUserStatusTracker()}</code>.
     */
    public void testMethodGetChatUserStatusTracker() {
        assertNotNull("Failed to get chat user status tracker", eventListener.getChatUserStatusTracker());
    }

    /**
     * Accuracy test for <code>{@link UserStatusEventListener#getMessagePool()}</code>.
     */
    public void testMethodGetMessagePool() {
        assertNotNull("Failed to get message pool", eventListener.getMessagePool());
    }

    /**
     * Accuracy test for <code>{@link UserStatusEventListener#statusChanged(long,Entity,Status,Status)}</code>.
     */
    public void testMethodStatusChanged_long_Entity_Status_Status() {
        Status status1 = new Status(103);
        Status status2 = new Status(101);
        Entity entity = new Entity(1, "accuracy_tester", new String[] { "key" }, new Status[] { new Status(
            123) });
        eventListener.statusChanged(AccuracyTestHelper.REQ_USER_ID, entity, status1, status2);
        eventListener.statusChanged(AccuracyTestHelper.REQ_USER_ID, entity, status2, status1);
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserStatusEventListenerTest.class);
    }
}
