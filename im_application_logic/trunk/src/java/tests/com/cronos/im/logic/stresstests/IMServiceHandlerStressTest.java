package com.cronos.im.logic.stresstests;

import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.IMServiceHandler;
import com.cronos.im.logic.MockMessenger;
import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.chat.status.ChatUserStatusTracker;
import com.topcoder.database.statustracker.Status;
import com.topcoder.service.Category;
import com.topcoder.service.ServiceElement;
import com.topcoder.service.ServiceEngine;
import com.topcoder.service.ServiceEvent;

/**
 * Stress Tests for IMServiceHandler.
 * @author kaqi072821
 * @version 1.0
 */
public class IMServiceHandlerStressTest extends BaseStressTest {

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
     * Check response interval used in the stress tests.
     */
    private static final int CHECK_RESPONSE_INTERVAL = 3;

    /**
     * Responder wait time used in the stress tests.
     */
    private static final int RESPONDER_WAIT_TIME = 10;

    /**
     * Initializes the environment.
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
            chatUserStatusTracker, RESPONDER_WAIT_TIME, CHECK_RESPONSE_INTERVAL, logger);

        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY, new Long(TestHelper.USER_ID_ONE));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(TestHelper.SESSION_ID_ONE));
        ServiceElement responder = new ServiceElement();
        responder.setProperty(IMServiceHandler.USER_ID_KEY, new Long(TestHelper.USER_ID_TWO));
        Category category = new Category(TestHelper.CATEGORY_ID, "category");
        serviceEvent = new ServiceEvent(serviceEngine, requester, responder, category, null);
    }

    /**
     * Clears the test environment.
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Stress test for onToBeServiced(ServiceEvent serviceEvent).
     * @throws Exception
     *             to junit
     */
    public void testonToBeServiced() throws Exception {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY, new Long(TestHelper.USER_ID_ONE));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(TestHelper.SESSION_ID_ONE));
        ServiceElement responder = new ServiceElement();
        responder.setProperty(IMServiceHandler.USER_ID_KEY, new Long(TestHelper.USER_ID_TWO));
        Category category = new Category(TestHelper.CATEGORY_ID, "category");
        handler.setResponder(requester, category, responder);

        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            handler.onToBeServiced(serviceEvent);
        }
        endTest("testonToBeServiced()", TestHelper.MAX_TIME);
        Thread.sleep(RESPONDER_WAIT_TIME * RUN_TIMES);
    }

    /**
     * Stress test for onServiced(ServiceEvent serviceEvent) when there is a responder.
     * @throws Exception
     *             to junit
     */
    public void testonServiced_WithResponder() throws Exception {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY, new Long(TestHelper.USER_ID_ONE));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(TestHelper.SESSION_ID_ONE));
        ServiceElement responder = new ServiceElement();
        responder.setProperty(IMServiceHandler.USER_ID_KEY, new Long(TestHelper.USER_ID_TWO));
        Category category = new Category(TestHelper.CATEGORY_ID, "category");
        handler.setResponder(requester, category, responder);

        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            handler.onServiced(serviceEvent);
            chatSessionStatusTracker.setStatus(TestHelper.SESSION_ID_ONE, new Status(
                TestHelper.SESSION_STATUS_CLOSE));
        }
        endTest("testonServiced_WithResponder()", TestHelper.MAX_TIME);
    }

    /**
     * Stress test for onServiced(ServiceEvent serviceEvent) when there are no responders.
     * @throws Exception
     *             to junit
     */
    public void testonServiced_NoResponder() throws Exception {
        ServiceElement requester = new ServiceElement();
        requester.setProperty(IMServiceHandler.USER_ID_KEY, new Long(TestHelper.USER_ID_ONE));
        requester.setProperty(IMServiceHandler.SESSION_ID_KEY, new Long(TestHelper.SESSION_ID_ONE));
        Category category = new Category(TestHelper.CATEGORY_ID, "cacegory");
        serviceEvent = new ServiceEvent(serviceEngine, requester, null, category, null);

        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            handler.onServiced(serviceEvent);
            Status onlineStatus = new Status(TestHelper.USER_STATUS_ONLINE);
            chatUserStatusTracker.setStatus(TestHelper.USER_ID_ONE, onlineStatus);
        }
        endTest("testonServiced_NoResponder()", TestHelper.MAX_TIME);
    }

}
