/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.failure;

import junit.framework.TestCase;

import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.SessionStatusEventListener;
import com.cronos.im.logic.UserSessionEventListener;
import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.Status;

/**
 * <p>
 * Failure tests for SessionStatusEventListener class.
 * </p>
 *
 * @author singlewood
 * @version 1.0
 */
public class SessionStatusEventListenerFailureTests extends TestCase {

    private static final int OPEN = 203;

    private static final int CLOSE = 204;

    /**
     * <p>
     * Represents the default namespace to load the acknowledgeTime configuration if
     * namespace is not provided in the constructor.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.im.logic.UserSessionEventListener";

    /**
     * An instance of SessionStatusEventListener for tests.
     */
    private SessionStatusEventListener listener;

    /**
     * An instance of ChatUserStatusTracker for tests.
     */
    private ChatSessionStatusTracker chatSessionStatusTracker;

    /**
     * An instance of IMLogger for tests.
     */
    private IMLogger logger;

    /**
     * An instance of ChatSessionManager for tests.
     */
    private ChatSessionManager chatSessionManager;

    /**
     * An instance of Messenger for tests.
     */
    private Messenger messenger;

    /**
     * An instance of Entity for tests.
     */
    private Entity entity;

    /**
     * Initialize the environment.
     *
     * @throws Exception throw to JUnit
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadConfig();
        chatSessionStatusTracker = new ChatSessionStatusTracker();
        logger = new IMLogger(null);
        chatSessionManager = new ChatSessionManagerFMock();
        messenger = new MessengerFMock();
        listener = new SessionStatusEventListener(chatSessionStatusTracker, chatSessionManager, messenger, logger);
        entity = new Entity(1, "user", new String[] {"asdasdf"}, new Status[] {new Status(123)});
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception throw to JUnit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.clearConfig();
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if
     * chatSessionManager is null
     *
     * @throws Exception throw to JUnit
     */
    public void testConstructor1Failure1() throws Exception {
        try {
            new SessionStatusEventListener(null, chatSessionManager, messenger, logger);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if
     * messenger is null
     *
     * @throws Exception throw to JUnit
     */
    public void testConstructor1Failure2() throws Exception {
        try {
            new SessionStatusEventListener(chatSessionStatusTracker, null, messenger, logger);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if
     * chatSessionStatusTracker is null
     *
     * @throws Exception throw to JUnit
     */
    public void testConstructor1Failure3() throws Exception {
        try {
            new SessionStatusEventListener(chatSessionStatusTracker, chatSessionManager, null, logger);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if
     * chatSessionManager is null
     *
     * @throws Exception throw to JUnit
     */
    public void testConstructor2Failure1() throws Exception {
        try {
            new UserSessionEventListener(null, messenger, chatSessionStatusTracker, logger);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for userRequested method. IllegalArgumentException should be thrown if
     * entity is null.
     */
    public void testUserRequestedFailure1() throws Exception {
        try {
            listener.statusChanged(1, null, new Status(CLOSE), new Status(OPEN));
            fail("IllegalArgumentException should be thrown if session is null.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Failure test for userRequested method. IllegalArgumentException should be thrown if
     * newStatus is null.
     */
    public void testUserRequestedFailure2() throws Exception {
        try {
            listener.statusChanged(1, entity, new Status(CLOSE), null);
            fail("IllegalArgumentException should be thrown if session is null.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Failure test for userRequested method. IllegalArgumentException should be thrown if
     * user id is not equal to entity id.
     */
    public void testUserRequestedFailure3() throws Exception {
        try {
            listener.statusChanged(2, entity, new Status(CLOSE), null);
            fail("IllegalArgumentException should be thrown if session is null.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

}
