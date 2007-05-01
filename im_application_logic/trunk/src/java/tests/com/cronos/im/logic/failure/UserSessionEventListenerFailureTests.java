/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.failure;

import junit.framework.TestCase;

import com.cronos.im.logic.IMConfigurationException;
import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.UserSessionEventListener;
import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatSessionStatusTracker;

/**
 * <p>
 * Failure tests for UserSessionEventListener class.
 * </p>
 *
 * @author singlewood
 * @version 1.0
 */
public class UserSessionEventListenerFailureTests extends TestCase {

    /**
     * <p>
     * Represents the default namespace to load the acknowledgeTime configuration if
     * namespace is not provided in the constructor.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.im.logic.UserSessionEventListener";

    /**
     * An instance of UserSessionEventListener for tests.
     */
    private UserSessionEventListener listener;

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
        listener = new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, 1000, logger);
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
            new UserSessionEventListener(null, messenger, chatSessionStatusTracker, 1000, logger);
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
            new UserSessionEventListener(chatSessionManager, null, chatSessionStatusTracker, 1000, logger);
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
            new UserSessionEventListener(chatSessionManager, messenger, null, 1000, logger);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if
     * acknowledgeTime is negtive.
     *
     * @throws Exception throw to JUnit
     */
    public void testConstructor1Failure4() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, -1, logger);
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
     * Failure test for the constructor. IllegalArgumentException should be thrown if
     * messenger is null
     *
     * @throws Exception throw to JUnit
     */
    public void testConstructor2Failure2() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, null, chatSessionStatusTracker, logger);
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
    public void testConstructor2Failure3() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, null, logger);
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
    public void testConstructor3Failure1() throws Exception {
        try {
            new UserSessionEventListener(null, messenger, chatSessionStatusTracker, logger, DEFAULT_NAMESPACE);
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
    public void testConstructor3Failure2() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, null, chatSessionStatusTracker, logger, DEFAULT_NAMESPACE);
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
    public void testConstructor3Failure3() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, null, logger, DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor. IMConfigurationException should be thrown if
     * acknowledge_time is missed in configuration.
     *
     * @throws Exception throw to JUnit
     */
    public void testInvalidConfig1() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, logger,
                    "com.cronos.im.logic.UserSessionEventListener.Invalid1");
            fail("IMConfigurationException should be thrown.");
        } catch (IMConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor. IMConfigurationException should be thrown if
     * acknowledge_time is illegal in configuration.
     *
     * @throws Exception throw to JUnit
     */
    public void testInvalidConfig2() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, logger,
                    "com.cronos.im.logic.UserSessionEventListener.Invalid2");
            fail("IMConfigurationException should be thrown.");
        } catch (IMConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor. IMConfigurationException should be thrown if
     * acknowledge_time is illegal in configuration.
     *
     * @throws Exception throw to JUnit
     */
    public void testInvalidConfig3() throws Exception {
        try {
            new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, logger,
                    "com.cronos.im.logic.UserSessionEventListener.Invalid3");
            fail("IMConfigurationException should be thrown.");
        } catch (IMConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for userRequested method. IllegalArgumentException should be thrown if session is
     * null.
     */
    public void testUserRequestedFailure() throws Exception {
        try {
            listener.userRequested(null, 1);
            fail("IllegalArgumentException should be thrown if session is null.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Failure test for userRequested method. IllegalArgumentException should be thrown if session is
     * null.
     */
    public void testUserAddedFailure() throws Exception {
        try {
            listener.userAdded(null, 11);
            fail("IllegalArgumentException should be thrown if session is null.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Failure test for userRequested method. IllegalArgumentException should be thrown if session is
     * null.
     */
    public void testUserRemovedFailure() throws Exception {
        try {
            listener.userRemoved(null, 11);
            fail("IllegalArgumentException should be thrown if session is null.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }


}
