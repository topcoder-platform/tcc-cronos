/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import java.text.DateFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
import com.topcoder.service.ServiceStatus;

/**
 * Accuracy test for <code>{@link com.cronos.im.logic.IMServiceHandler}</code> class.
 * @author mittu
 * @version 1.0
 */
public class IMServiceHandlerTest extends TestCase {
    /**
     * Represents IMServiceHandler for testing.
     */
    private IMServiceHandler handler;

    /**
     * Represents ChatUserStatusTracker for testing.
     */
    private ChatUserStatusTracker chatUserStatusTracker;

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
     * Represents ServiceEngine for testing.
     */
    private ServiceEngine serviceEngine;

    /**
     * Represents ServiceEvent for testing.
     */
    private ServiceEvent serviceEvent;

    /**
     * Sets up test environment.
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadConfigs();

        chatSessionStatusTracker = new ChatSessionStatusTracker();
        chatUserStatusTracker = new ChatUserStatusTracker();
        serviceEngine = new ServiceEngine();
        chatSessionManager = new MockChatSessionManager();
        messenger = new MockMessenger();

        logger = new IMLogger(DateFormat.getDateInstance());
        handler = new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
            chatUserStatusTracker, logger);
    }

    /**
     * Tears down test environment.
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.releaseConfigs();
    }

    /**
     * Accuracy test for
     * <code>{@link IMServiceHandler#IMServiceHandler(ChatSessionManager,Messenger,ChatSessionStatusTracker,
     * ChatUserStatusTracker,ServiceEngine,IMLogger)}</code>.
     */
    public void testConstructor_1() {
        assertNotNull("Failed to create IMServiceHandler", handler);
    }

    /**
     * Accuracy test for
     * <code>{@link IMServiceHandler#IMServiceHandler(ChatSessionManager,Messenger,ChatSessionStatusTracker,
     * ChatUserStatusTracker,ServiceEngine,long,long,IMLogger)}</code>.
     * @throws Exception
     *             if any error occurs.
     */
    public void testConstructor_2() throws Exception {
        handler = new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
            chatUserStatusTracker, AccuracyTestHelper.ONE_SEC, AccuracyTestHelper.ONE_SEC, logger);
        assertNotNull("Failed to create IMServiceHandler", handler);
    }

    /**
     * Accuracy test for
     * <code>{@link IMServiceHandler#IMServiceHandler(ChatSessionManager,Messenger,ChatSessionStatusTracker,
     * ChatUserStatusTracker,ServiceEngine,IMLogger,String)}</code>.
     * @throws Exception
     *             if any error occurs.
     */
    public void testConstructor_3() throws Exception {
        handler = new IMServiceHandler(chatSessionManager, messenger, chatSessionStatusTracker,
            chatUserStatusTracker, logger, IMServiceHandler.DEFAULT_NAMESPACE);
        assertNotNull("Failed to create IMServiceHandler", handler);
    }

    /**
     * Accuracy test for <code>{@link IMServiceHandler#getCheckResponseInterval()}</code>.
     */
    public void testMethodGetCheckResponseInterval() {
        assertEquals("Failed to get the response interval", AccuracyTestHelper.ONE_SEC, handler
            .getCheckResponseInterval());
    }

    /**
     * Accuracy test for <code>{@link IMServiceHandler#getResponderWaitTime()}</code>.
     */
    public void testMethodGetResponderWaitTime() {
        assertEquals("Failed to get the responder wait time", AccuracyTestHelper.ONE_SEC, handler
            .getResponderWaitTime());
    }

    /**
     * Accuracy test for <code>{@link IMServiceHandler#getResponder(ServiceElement,Category)}</code>
     * <code>{@link IMServiceHandler#clearAllResponders()}</code>
     * and
     * <code>{@link IMServiceHandler#setResponder(ServiceElement,Category,ServiceElement)}</code>.
     */
    public void testMethodGetResponder_ServiceElement_Category() {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY,
            new Long(AccuracyTestHelper.REQ_USER_ID));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(
            AccuracyTestHelper.SESSION_ID));
        ServiceElement responder = new ServiceElement();
        responder.setProperty(IMServiceHandler.USER_ID_KEY,
            new Long(AccuracyTestHelper.RES_USER_ID));
        Category category = new Category(AccuracyTestHelper.CATEGORY_ID, "category");

        handler.setResponder(requester, category, responder);
        responder = handler.getResponder(requester, category);

        assertNotNull("Failed to get the responders", responder);

        handler.clearAllResponders();
        responder = handler.getResponder(requester, category);
        assertNull("Failed to clear all responders", responder);
    }

    /**
     * Accuracy test for <code>{@link IMServiceHandler#getMessenger()}</code>.
     */
    public void testMethodGetMessenger() {
        assertNotNull("Failed to get the messenger", handler.getMessenger());
    }

    /**
     * Accuracy test for <code>{@link IMServiceHandler#getChatSessionManager()}</code>.
     */
    public void testMethodGetChatSessionManager() {
        assertNotNull("Failed to get the chat session manager", handler.getChatSessionManager());
    }

    /**
     * Accuracy test for <code>{@link IMServiceHandler#getChatSessionStatusTracker()}</code>.
     */
    public void testMethodGetChatSessionStatusTracker() {
        assertNotNull("Failed to get the chat session status tracker", handler
            .getChatSessionStatusTracker());
    }

    /**
     * Accuracy test for <code>{@link IMServiceHandler#getChatUserStatusTracker()}</code>.
     */
    public void testMethodGetChatUserStatusTracker() {
        assertNotNull("Failed to get the chat user status tracker", handler
            .getChatUserStatusTracker());
    }

    /**
     * Accuracy test for <code>{@link IMServiceHandler#onServiced(ServiceEvent)}</code>.
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodOnServiced_ServiceEvent_1() throws Exception {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY,
            new Long(AccuracyTestHelper.REQ_USER_ID));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(
            AccuracyTestHelper.SESSION_ID));
        ServiceElement responder = new ServiceElement();
        responder.setProperty(IMServiceHandler.USER_ID_KEY,
            new Long(AccuracyTestHelper.RES_USER_ID));
        Category category = new Category(AccuracyTestHelper.CATEGORY_ID, "category");
        serviceEvent = new ServiceEvent(serviceEngine, requester, responder, category, ServiceStatus.STARTED);
        handler.setResponder(requester, category, responder);
        handler.onServiced(serviceEvent);
        // Check the logs manually
        // Expected actions are
        // 1) Add user to session
        // 2) Change Session status to OPEN
        // 3) Post Session unavailable for all requested users.
    }

    /**
     * Accuracy test for <code>{@link IMServiceHandler#onServiced(ServiceEvent)}</code>.
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodOnServiced_ServiceEvent_2() throws Exception {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY,
            new Long(AccuracyTestHelper.REQ_USER_ID));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(
            AccuracyTestHelper.REQ_USER_ID));
        Category category = new Category(AccuracyTestHelper.CATEGORY_ID, "category");
        serviceEvent = new ServiceEvent(serviceEngine, requester, null, category, ServiceStatus.STARTED);
        handler.onServiced(serviceEvent);
        // Check the logs manually
        // Expected actions are
        // 1) Post Session unavailable to user
        // 2) Remove user from session
        // 3) Change Session status to OFFLINE
    }

    /**
     * Accuracy test for <code>{@link IMServiceHandler#onToBeServiced(ServiceEvent)}</code>.
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodOnToBeServiced_ServiceEvent() throws Exception {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY,
            new Long(AccuracyTestHelper.REQ_USER_ID));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(
            AccuracyTestHelper.SESSION_ID));
        ServiceElement responder = new ServiceElement();
        responder.setProperty(IMServiceHandler.USER_ID_KEY,
            new Long(AccuracyTestHelper.RES_USER_ID));
        Category category = new Category(AccuracyTestHelper.CATEGORY_ID, "category");
        serviceEvent = new ServiceEvent(serviceEngine, requester, responder, category, ServiceStatus.STARTED);
        handler.setResponder(requester, category, responder);
        handler.onToBeServiced(serviceEvent);
        Thread.sleep(AccuracyTestHelper.ONE_SEC);
    }

    /**
     * Returns all tests.
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(IMServiceHandlerTest.class);
    }
}
