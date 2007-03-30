/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests.impl;

import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.MessageTracker;
import com.cronos.im.messenger.accuracytests.AccuracyHelper;
import com.cronos.im.messenger.impl.ChatMessageTrackerImpl;
import junit.framework.TestCase;

import java.sql.Connection;


/**
 * Tests the functionality for class <code>ChatMessageTrackerImpl</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class ChatMessageTrackerImplAccuracyTest extends TestCase {
    /**
     * An instance of <code>ChatMessageTrackerImpl</code> for testing.
     */
    private ChatMessageTrackerImpl chatMessageTracker;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception Propagated to JUnit.
     */
    protected void setUp() throws Exception {
        AccuracyHelper.clearConfig();
        AccuracyHelper.loadConfig("db_connection_factory.xml");

        chatMessageTracker = AccuracyHelper.createTracker();

        Connection conn = AccuracyHelper.getConnection();

        try {
            AccuracyHelper.deleteTables(conn);
            AccuracyHelper.prepareTables(conn);
        } finally {
            AccuracyHelper.closeConnection(conn);
        }
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        Connection conn = AccuracyHelper.getConnection();

        try {
            AccuracyHelper.deleteTables(conn);
            AccuracyHelper.prepareTables(conn);
        } finally {
            AccuracyHelper.closeConnection(conn);
        }

        AccuracyHelper.clearConfig();
        super.tearDown();
    }

    /**
     * Test method for 'ChatMessageTrackerImpl(DBConnectionFactory, String, String)'
     */
    public void testChatMessageTrackerImpl() {
        assertTrue("Test method for 'ChatMessageTrackerImpl(DBConnectionFactory, String, String)' failed.",
            chatMessageTracker instanceof MessageTracker);
    }

    /**
     * Test method for 'ChatMessageTrackerImpl.track(Message, long[], long)'
     *
     * @throws Exception to JUnit
     */
    public void testTrack() throws Exception {
        ChatMessage message = new ChatMessage();
        String chatText = "Hi everyone, enjoy our jobs~";
        message.setChatText(chatText);

        long[] userIds = new long[]{0, 1};
        long sessionId = 1;

        chatMessageTracker.track(message, userIds, sessionId);
    }
}
