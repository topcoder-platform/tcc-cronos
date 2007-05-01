/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.failure;

import junit.framework.TestCase;

import com.cronos.im.logic.IMConfigurationException;
import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.IMServiceHandler;
import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.chat.status.ChatUserStatusTracker;
import com.topcoder.service.Category;
import com.topcoder.service.ServiceElement;
import com.topcoder.service.ServiceEngine;
import com.topcoder.service.ServiceEvent;

/**
 * <p>
 * Failure test cases for IMServiceHandler class.
 * </p>
 *
 * @author singlewood
 * @version 1.0
 */
public class IMServiceHandlerFailureTests extends TestCase {

    /**
     * An instance of IMServiceHandler for the following tests.
     */
    private IMServiceHandler handler = null;

    /**
     * An instance of ChatUserStatusTracker for the following tests.
     */
    private ChatUserStatusTracker chatUserStatusTracker = null;

    /**
     * An instance of IMLogger for the following tests.
     */
    private IMLogger logger = null;

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
     * An instance of ServiceEngine for the following tests.
     */
    private ServiceEngine serviceEngine = null;

    /**
     * An instance of ServiceEvent for the following tests.
     */
    private ServiceEvent serviceEvent = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception throws to JUnit
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadConfig();

        chatUserStatusTracker = new ChatUserStatusTracker();
        logger = new IMLogger(null);
        chatSessionStatusTracker = new ChatSessionStatusTracker();
        chatSessionManager = new ChatSessionManagerFMock();
        messenger = new MessengerFMock();
        serviceEngine = new ServiceEngine();
        handler = new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                serviceEngine, 1000, 1000, logger);

        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY, new Long(1));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(1));
        ServiceElement responder = new ServiceElement();
        responder.setProperty(IMServiceHandler.USER_ID_KEY, new Long(2));
        Category category = new Category(123, "cacegory");
        serviceEvent = new ServiceEvent(serviceEngine, requester, responder, category, null);
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception throws to JUnit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.clearConfig();
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, long
     * responderWaitTime, long checkResponseInterval, IMLogger logger).
     * IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void testConstructor1Failure1() {
        try {
            new IMServiceHandler(null, messenger, chatSessionStatusTracker, chatUserStatusTracker, serviceEngine, 1000,
                    1000, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, long
     * responderWaitTime, long checkResponseInterval, IMLogger logger).
     * IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void testConstructor1Failure2() {
        try {
            new IMServiceHandler(chatSessionManager, null, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, 1000, 1000, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, long
     * responderWaitTime, long checkResponseInterval, IMLogger logger).
     * IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void testConstructor1Failure3() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, null, chatUserStatusTracker, serviceEngine, 1000, 1000,
                    logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, long
     * responderWaitTime, long checkResponseInterval, IMLogger logger).
     * IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void testConstructor1Failure4() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker, null,
                    1000, 1000, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, long
     * responderWaitTime, long checkResponseInterval, IMLogger logger).
     * IllegalArgumentException should be thrown if the time is negative.
     */
    public void testConstructor1Failure5() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, -1000, 1000, logger);
            fail("IllegalArgumentException should be thrown if the time is negative.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, long
     * responderWaitTime, long checkResponseInterval, IMLogger logger).
     * IllegalArgumentException should be thrown if the time is negative.
     */
    public void testConstructor1Failure6() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, 1000, -1000, logger);
            fail("IllegalArgumentException should be thrown if the time is negative.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger). IllegalArgumentException should be thrown if any argument exception logger
     * is null.
     *
     * @throws to JUnit
     */
    public void testConstructor2Failure1() throws Exception {
        try {
            new IMServiceHandler(null, messenger, chatSessionStatusTracker, chatUserStatusTracker, serviceEngine,
                    logger);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger). IllegalArgumentException should be thrown if any argument exception logger
     * is null.
     *
     * @throws to JUnit
     */
    public void testConstructor2Failure2() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, null, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger). IllegalArgumentException should be thrown if any argument exception logger
     * is null.
     *
     * @throws to JUnit
     */
    public void testConstructor2Failure3() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, null, chatUserStatusTracker, serviceEngine, logger);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger). IllegalArgumentException should be thrown if any argument exception logger
     * is null.
     *
     * @throws to JUnit
     */
    public void testConstructor2Failure4() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, null, serviceEngine, logger);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger). IllegalArgumentException should be thrown if any argument exception logger
     * is null.
     *
     * @throws to JUnit
     */
    public void testConstructor2Failure5() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker, null,
                    logger);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IllegalArgumentException should be thrown if any
     * argument exception logger is null.
     *
     * @throws to JUnit
     */
    public void testConstructor3Failure1() throws Exception {
        try {
            new IMServiceHandler(null, messenger, chatSessionStatusTracker, chatUserStatusTracker, serviceEngine,
                    logger, IMServiceHandler.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IllegalArgumentException should be thrown if any
     * argument exception logger is null.
     *
     * @throws to JUnit
     */
    public void testConstructor3Failure2() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, null, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IllegalArgumentException should be thrown if any
     * argument exception logger is null.
     *
     * @throws to JUnit
     */
    public void testConstructor3Failure3() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, null, chatUserStatusTracker, serviceEngine, logger,
                    IMServiceHandler.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IllegalArgumentException should be thrown if any
     * argument exception logger is null.
     *
     * @throws to JUnit
     */
    public void testConstructor3Failure4() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, null, serviceEngine, logger,
                    IMServiceHandler.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IllegalArgumentException should be thrown if any
     * argument exception logger is null.
     *
     * @throws to JUnit
     */
    public void testConstructor3Failure5() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker, null,
                    logger, IMServiceHandler.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IllegalArgumentException should be thrown if any
     * argument exception logger is null.
     *
     * @throws to JUnit
     */
    public void testConstructor3Failure6() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger, null);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IllegalArgumentException should be thrown if namespace
     * is empty.
     *
     * @throws to JUnit
     */
    public void testConstructor3Failure7() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger, "  ");
            fail("IllegalArgumentException should be thrown if namespace is empty.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IMConfigurationException should be thrown if
     * configuration is invalid.
     */
    public void testConstructorInvalid1() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE + ".Invalid1");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IMConfigurationException should be thrown if
     * configuration is invalid.
     */
    public void testConstructorInvalid2() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE + ".Invalid2");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IMConfigurationException should be thrown if
     * configuration is invalid.
     */
    public void testConstructorInvalid3() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE + ".Invalid3");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager
     * sessionManager, Messenger messenger,ChatSessionStatusTracker sessionStatusTracker,
     * ChatUserStatusTracker userStatusTracker,ServiceEngine serviceEngine, IMLogger
     * logger, String namespace). IMConfigurationException should be thrown if
     * configuration is invalid.
     */
    public void testConstructorInvalid4() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE + ".Invalid4");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for the onServiced method. IllegalArgumentException should be thrown
     * for null argument.
     *
     * @throws Exception throws to JUnit
     */
    public void testOnServicedFailure() throws Exception {
        try {
            handler.onServiced(null);
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the getResponder method. IllegalArgumentException should be thrown
     * for null argument.
     *
     * @throws Exception throws to JUnit
     */
    public void testGetResponderFailure1() throws Exception {
        try {
            handler.getResponder(null, serviceEvent.getCategory());
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the getResponder method. IllegalArgumentException should be thrown
     * for null argument.
     *
     * @throws Exception throws to JUnit
     */
    public void testGetResponderFailure2() throws Exception {
        try {
            handler.getResponder(serviceEvent.getRequester(), null);
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
