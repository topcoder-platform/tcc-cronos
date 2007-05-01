/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.chat.status.ChatUserStatusTracker;
import com.topcoder.service.ServiceEngine;
import com.topcoder.service.ServiceEvent;
import com.topcoder.service.ServiceElement;
import com.topcoder.service.Category;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for IMServiceHandler class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IMServiceHandlerUnitTests extends TestCase {

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
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();

        chatUserStatusTracker = new ChatUserStatusTracker();
        logger = new IMLogger(null);
        chatSessionStatusTracker = new ChatSessionStatusTracker();
        chatSessionManager = new MockChatSessionManager();
        messenger = new MockMessenger();
        serviceEngine = new ServiceEngine();
        handler = new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                chatUserStatusTracker, serviceEngine, 1000, 1000, logger);

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
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Tests the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, long responderWaitTime, long checkResponseInterval,
     * IMLogger logger). No exception should be thrown.
     */
    public void test_constructor1_1() {
        new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                serviceEngine, 1000, 1000, logger);
    }

    /**
     * Tests the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, long responderWaitTime, long checkResponseInterval,
     * IMLogger logger). No exception should be thrown.
     */
    public void test_constructor1_2() {
        new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                serviceEngine, 1000, 1000, null);
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, long responderWaitTime, long checkResponseInterval,
     * IMLogger logger). IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void test_constructor1_failure1() {
        try {
            new IMServiceHandler(null, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, 1000, 1000, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, long responderWaitTime, long checkResponseInterval,
     * IMLogger logger). IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void test_constructor1_failure2() {
        try {
            new IMServiceHandler(chatSessionManager, null, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, 1000, 1000, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, long responderWaitTime, long checkResponseInterval,
     * IMLogger logger). IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void test_constructor1_failure3() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, null, chatUserStatusTracker, serviceEngine,
                    1000, 1000, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, long responderWaitTime, long checkResponseInterval,
     * IMLogger logger). IllegalArgumentException should be thrown if any argument except logger is null.
     */
    public void test_constructor1_failure4() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, null, 1000, 1000, logger);
            fail("IllegalArgumentException should be thrown if any argument except logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, long responderWaitTime, long checkResponseInterval,
     * IMLogger logger). IllegalArgumentException should be thrown if the time is negative.
     */
    public void test_constructor1_failure5() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, serviceEngine, -1000, 1000, logger);
            fail("IllegalArgumentException should be thrown if the time is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, long responderWaitTime, long checkResponseInterval,
     * IMLogger logger). IllegalArgumentException should be thrown if the time is negative.
     */
    public void test_constructor1_failure6() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, serviceEngine, 1000, -1000, logger);
            fail("IllegalArgumentException should be thrown if the time is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger). No exception should be thrown.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor2_1() throws Exception {
        new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                serviceEngine, logger);
    }

    /**
     * Tests the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger). No exception should be thrown.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor2_2() throws Exception {
        new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                serviceEngine, null);
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger). IllegalArgumentException should be
     * thrown if any argument exception logger is null.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor2_failure1() throws Exception {
        try {
            new IMServiceHandler(null, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger). IllegalArgumentException should be
     * thrown if any argument exception logger is null.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor2_failure2() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, null, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger). IllegalArgumentException should be
     * thrown if any argument exception logger is null.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor2_failure3() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, null, chatUserStatusTracker, serviceEngine,
                    logger);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger). IllegalArgumentException should be
     * thrown if any argument exception logger is null.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor2_failure4() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, null,
                    serviceEngine, logger);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger). IllegalArgumentException should be
     * thrown if any argument exception logger is null.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor2_failure5() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, null, logger);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace). No exception should
     * be thrown.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor3_1() throws Exception {
        new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE);
    }

    /**
     * Tests the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace). No exception should
     * be thrown.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor3_2() throws Exception {
        new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                serviceEngine, null, IMServiceHandler.DEFAULT_NAMESPACE);
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IllegalArgumentException should be thrown if any argument exception logger is null.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor3_failure1() throws Exception {
        try {
            new IMServiceHandler(null, messenger, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IllegalArgumentException should be thrown if any argument exception logger is null.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor3_failure2() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, null, chatSessionStatusTracker, chatUserStatusTracker,
                    serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IllegalArgumentException should be thrown if any argument exception logger is null.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor3_failure3() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, null, chatUserStatusTracker, serviceEngine,
                    logger, IMServiceHandler.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IllegalArgumentException should be thrown if any argument exception logger is null.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_constructor3_failure4() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker, null,
                    serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IllegalArgumentException should be thrown if any argument exception logger is null.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor3_failure5() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, null, logger, IMServiceHandler.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IllegalArgumentException should be thrown if any argument exception logger is null.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor3_failure6() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, serviceEngine, logger, null);
            fail("IllegalArgumentException should be thrown if any argument exception logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IllegalArgumentException should be thrown if namespace is empty.
     * 
     * @throws to
     *             JUnit
     */
    public void test_constructor3_failure7() throws Exception {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, serviceEngine, logger, "  ");
            fail("IllegalArgumentException should be thrown if namespace is empty.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IMConfigurationException should be thrown if configuration is invalid.
     */
    public void test_constructor3_failure_invalid_config_1() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE
                            + ".Invalid1");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IMConfigurationException should be thrown if configuration is invalid.
     */
    public void test_constructor3_failure_invalid_config_2() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE
                            + ".Invalid2");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IMConfigurationException should be thrown if configuration is invalid.
     */
    public void test_constructor3_failure_invalid_config_3() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE
                            + ".Invalid3");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor IMServiceHandler(ChatSessionManager sessionManager, Messenger
     * messenger,ChatSessionStatusTracker sessionStatusTracker, ChatUserStatusTracker
     * userStatusTracker,ServiceEngine serviceEngine, IMLogger logger, String namespace).
     * IMConfigurationException should be thrown if configuration is invalid.
     */
    public void test_constructor3_failure_invalid_config_4() {
        try {
            new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
                    chatUserStatusTracker, serviceEngine, logger, IMServiceHandler.DEFAULT_NAMESPACE
                            + ".Invalid4");
            fail("IMConfigurationException should be thrown if configuration is invalid.");
        } catch (IMConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the onToBeServiced and setReuqester methods. The returned result should be correct.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_onToBeServiced_getReuqester_1() throws Exception {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY, new Long(1));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(1));
        ServiceElement responder = new ServiceElement();
        responder.setProperty(IMServiceHandler.USER_ID_KEY, new Long(2));
        Category category = new Category(123, "cacegory");
        handler.setResponder(requester, category, responder);

        // no exception
        handler.onToBeServiced(serviceEvent);
        // wait for the thread initiated above to stop
        Thread.sleep(2000);

        String s = MockChatSessionManager.getMessage();
        assertTrue(s.endsWith("requestUserToSession(..., 2)"));
    }

    /**
     * Tests the setResponder, clearAllResponders and getReuqester methods. The returned result should be
     * correct.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_setResponder_clearAllResponders_getReuqester1() throws Exception {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY, new Long(1));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(1));
        ServiceElement responder = new ServiceElement();
        responder.setProperty(IMServiceHandler.USER_ID_KEY, new Long(2));
        Category category = new Category(123, "cacegory");
        handler.setResponder(requester, category, responder);

        // clear responders
        handler.clearAllResponders();

        // get the responder
        responder = handler.getResponder(serviceEvent.getRequester(), serviceEvent.getCategory());
        assertNull("The responders are not cleared correctly.", responder);
    }

    /**
     * Failure test for the onToBeServiced method. IllegalArgumentException should be thrown for null
     * argument.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_onToBeServiced_failure_1() throws Exception {
        try {
            handler.onToBeServiced(null);
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the onServiced method. IllegalArgumentException should be thrown for null argument.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_onServiced_failure_1() throws Exception {
        try {
            handler.onServiced(null);
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the getResponder method. IllegalArgumentException should be thrown for null argument.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getResponder_failure_1() throws Exception {
        try {
            handler.getResponder(null, serviceEvent.getCategory());
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the getResponder method. IllegalArgumentException should be thrown for null argument.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getResponder_failure_2() throws Exception {
        try {
            handler.getResponder(serviceEvent.getRequester(), null);
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the onToBeServiced and onServiced methods. No exception should be thrown.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_setResponder_onServiced_1() throws Exception {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY, new Long(1));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(1));
        ServiceElement responder = new ServiceElement();
        responder.setProperty(IMServiceHandler.USER_ID_KEY, new Long(2));
        Category category = new Category(123, "cacegory");
        handler.setResponder(requester, category, responder);

        // no exception
        handler.onServiced(serviceEvent);

        String s = MockMessenger.getMessage();
        assertTrue(s.endsWith("postMessage(..., 2)postMessage(..., 1)postMessage(..., 123)postMessage(..., 3)"));
        s = MockChatSessionManager.getMessage();
        assertTrue(s.endsWith("requestUserToSession(..., 2)addUserToSession(..., 2)"));
    }

    /**
     * Tests the onServiced method. The responder is null. No exception should be thrown.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_onServiced_1() throws Exception {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY, new Long(1));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(1));
        Category category = new Category(123, "cacegory");
        serviceEvent = new ServiceEvent(serviceEngine, requester, null, category, null);

        handler.onServiced(serviceEvent);

        String s = MockMessenger.getMessage();
        assertTrue(s.endsWith("postMessage(..., 2)postMessage(..., 1)postMessage(..., 123)postMessage(..., 3)postMessage(..., 1)"));
        s = MockChatSessionManager.getMessage();
        assertTrue(s.endsWith("requestUserToSession(..., 2)addUserToSession(..., 2)removeUserFromSession(..., 1)"));
    }

    /**
     * Tests the getChatUserStatusTracker method. The returned result should be correct.
     */
    public void test_getChatUserStatusTracker() {
        assertEquals("The getChatUserStatusTracker method is incorrect.", chatUserStatusTracker, handler
                .getChatUserStatusTracker());
    }

    /**
     * Tests the getServiceEngine method. The returned result should be correct.
     */
    public void test_getServiceEngine() {
        assertEquals("The getServiceEngine method is incorrect.", serviceEngine, handler.getServiceEngine());
    }

    /**
     * Tests the getMessenger method. The returned result should be correct.
     */
    public void test_getMessenger() {
        assertEquals("The getMessenger method is incorrect.", messenger, handler.getMessenger());
    }

    /**
     * Tests the getChatSessionManager method. The returned result should be correct.
     */
    public void test_getChatSessionManager() {
        assertEquals("The getChatSessionManager method is incorrect.", chatSessionManager, handler
                .getChatSessionManager());
    }

    /**
     * Tests the getChatSessionStatusTracker method. The returned result should be correct.
     */
    public void test_getChatSessionStatusTracker() {
        assertEquals("The getChatSessionStatusTracker method is incorrect.", chatSessionStatusTracker,
                handler.getChatSessionStatusTracker());
    }

    /**
     * Tests the getResponderWaitTime method. The returned result should be correct.
     */
    public void test_getResponderWaitTime() {
        assertEquals("The getResponderWaitTime method is incorrect.", 1000, handler.getResponderWaitTime());
    }

    /**
     * Tests the getCheckResponseInterval method. The returned result should be correct.
     */
    public void test_getCheckResponseInterval() {
        assertEquals("The getCheckResponseInterval method is incorrect.", 1000, handler
                .getCheckResponseInterval());
    }

}
