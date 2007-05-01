/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.Status;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for SessionStatusEventListener class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SessionStatusEventListenerUnitTests extends TestCase {

    /**
     * An instance of SessionStatusEventListener for the following tests.
     */
    private SessionStatusEventListener listener = null;

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
     * An instance of Entity for the following tests.
     */
    private Entity entity = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();

        chatSessionStatusTracker = new ChatSessionStatusTracker();
        chatSessionManager = new MockChatSessionManager();
        messenger = new MockMessenger();
        logger = new IMLogger(null);
        entity = new Entity(2, "name", new String[] {"key1"}, new Status[] {new Status(123)});
        listener = new SessionStatusEventListener(chatSessionStatusTracker, chatSessionManager, messenger,
                logger);
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
     * Tests the constructor. No exception should be thrown.
     */
    public void test_constructor1_1() {
        // no exception
        new SessionStatusEventListener(chatSessionStatusTracker, chatSessionManager, messenger, logger);
    }

    /**
     * Tests the constructor. No exception should be thrown.
     */
    public void test_constructor1_2() {
        // no exception
        new SessionStatusEventListener(chatSessionStatusTracker, chatSessionManager, messenger, null);
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if any argument except
     * logger is null.
     */
    public void test_constructor1_failure1() {
        try {
            new SessionStatusEventListener(null, chatSessionManager, messenger, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if any argument except
     * logger is null.
     */
    public void test_constructor1_failure2() {
        try {
            new SessionStatusEventListener(chatSessionStatusTracker, null, messenger, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if any argument except
     * logger is null.
     */
    public void test_constructor1_failure3() {
        try {
            new SessionStatusEventListener(chatSessionStatusTracker, chatSessionManager, null, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the statusChanged. No exception should be thrown.
     */
    public void test_statusChanged_1() {
        Status oldStatus = new Status(IMHelper.SESSION_STATUS_CLOSE);
        Status newStatus = new Status(IMHelper.SESSION_STATUS_OPEN);
        listener.statusChanged(123, entity, oldStatus, newStatus);

        String s = MockMessagePool.getMessage();
        assertTrue(s.endsWith(";register(2, 0);register(1, 0);register(123, 0);register(3, 0)"));
        s = MockMessenger.getMessage();
        System.out.println(s);
        assertTrue(s.endsWith("postMessage(..., 2)postMessage(..., 1)postMessage(..., 123)postMessage(..., 3)postMessageToOthers(..., ...)postMessageToOthers(..., ...)postMessageToOthers(..., ...)postMessageToOthers(..., ...)"));
    }

    /**
     * Failure test for the statusChanged. IllegalArgumentException should be thrown if entity is null.
     */
    public void test_statusChanged_failure1() {
        try {
            listener.statusChanged(123, null, new Status(1), new Status(2));
            fail("IllegalArgumentException should be thrown if entity is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the statusChanged. IllegalArgumentException should be thrown if new status is null.
     */
    public void test_statusChanged_failure2() {
        try {
            listener.statusChanged(123, entity, new Status(1), null);
            fail("IllegalArgumentException should be thrown if new status is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the statusChanged. IllegalArgumentException should be thrown if entity is invalid.
     */
    public void test_statusChanged_failure3() {
        entity = new Entity(1, "name", new String[] {"key1"}, new Status[] {new Status(123)});
        try {
            listener.statusChanged(123, entity, new Status(1), new Status(2));
            fail("IllegalArgumentException should be thrown if entity is invalid.");
        } catch (IllegalArgumentException e) {
            // ok
        }
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

}
