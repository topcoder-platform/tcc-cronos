package com.cronos.im.logic.stresstests;

import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.MockChatSessionManager;
import com.cronos.im.logic.MockMessenger;
import com.cronos.im.logic.UserSessionEventListener;
import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.session.SalesSession;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.database.statustracker.Status;

/**
 * Unit tests for UserSessionEventListener. Tests userRequested, userRemoved,userAdded methods under stress.
 * 
 * @author kaqi072821
 * @version 1.0
 */
public class UserSessionEventListenerStressTest extends BaseStressTest {

    /**
     * A user id used in this test case.
     */
    private static final int USER_ADDED = 3;

    /**
     * An instance of UserSessionEventListener for the following tests.
     */
    private UserSessionEventListener listener = null;

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
     * Initializes the environment.
     * 
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();

        chatSessionStatusTracker = new ChatSessionStatusTracker();
        chatSessionStatusTracker.setStatus(1, new Status(TestHelper.SESSION_STATUS_OPEN));
        chatSessionManager = new MockChatSessionManager();
        messenger = new MockMessenger();
        logger = new IMLogger(null);
        listener = new UserSessionEventListener(chatSessionManager, messenger, chatSessionStatusTracker, 1000, logger);
    }

    /**
     * Clears the test environment.
     * 
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Stress test for the userAdded method.
     */
    public void testUserAdded() {
        ChatSession session = new SalesSession(TestHelper.USER_ID_ONE, TestHelper.CATEGORY_ID);
        session.addUser(TestHelper.USER_ID_ONE);
        session.addUser(TestHelper.USER_ID_TWO);
        session.setId(TestHelper.SESSION_ID_ONE);
        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            listener.userAdded(session, USER_ADDED);
        }
        endTest("testUserAdded()", TestHelper.MAX_TIME);
    }

    /**
     * Stress test for the userRemoved method.
     */
    public void testUserRemoved() {
        ChatSession session = new SalesSession(TestHelper.USER_ID_ONE, TestHelper.CATEGORY_ID);
        session.addUser(TestHelper.USER_ID_ONE);
        session.addUser(TestHelper.USER_ID_TWO);
        session.setId(TestHelper.SESSION_ID_ONE);
        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            listener.userRemoved(session, TestHelper.USER_ID_ONE);
        }
        endTest("testUserRemoved()", TestHelper.MAX_TIME);
    }

    /**
     * Stress test for the userRequested method.
     */
    public void testUserRequested() {
        ChatSession session = new SalesSession(TestHelper.USER_ID_ONE, TestHelper.CATEGORY_ID);
        session.addUser(TestHelper.USER_ID_ONE);
        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            listener.userRequested(session, USER_ADDED);
        }
        endTest("testUserRequested()", TestHelper.MAX_TIME);
    }
}
