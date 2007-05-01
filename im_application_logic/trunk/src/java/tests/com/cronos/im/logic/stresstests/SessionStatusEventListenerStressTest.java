package com.cronos.im.logic.stresstests;

import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.MockChatSessionManager;
import com.cronos.im.logic.MockMessenger;
import com.cronos.im.logic.SessionStatusEventListener;
import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.Status;

/**
 * Stress tests for SessionStatusEventListener. Tests statusChanged(long id, Entity entity, Status oldStatus, Status
 * newStatus) under stress.
 * 
 * @author kaqi072821
 * @version 1.0
 */
public class SessionStatusEventListenerStressTest extends BaseStressTest {

    /**
     * An instance of SessionStatusEventListener for the stress tests.
     */
    private SessionStatusEventListener listener = null;

    /**
     * An instance of ChatSessionStatusTracker for the stress tests.
     */
    private ChatSessionStatusTracker chatSessionStatusTracker = null;

    /**
     * An instance of ChatSessionManager for the stress tests.
     */
    private ChatSessionManager chatSessionManager = null;

    /**
     * An instance of Messenger for the stress tests.
     */
    private Messenger messenger = null;

    /**
     * An instance of IMLogger for the stress tests.
     */
    private IMLogger logger = null;

    /**
     * An instance of Entity for the stress tests.
     */
    private Entity entity = null;

    /**
     * Initializes the environment.
     * 
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();

        chatSessionStatusTracker = new ChatSessionStatusTracker();
        chatSessionManager = new MockChatSessionManager();
        messenger = new MockMessenger();
        logger = new IMLogger(null);
        entity = new Entity(TestHelper.ENTITY_SESSION, "name", new String[] { "key1" }, new Status[] { new Status(TestHelper.SESSION_STATUS_CLOSE) });
        listener = new SessionStatusEventListener(chatSessionStatusTracker, chatSessionManager, messenger, logger);
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
     * Stress test for the statusChanged method. Changes session status from open to close.
     */
    public void testStatusChanged_OpenToClose() {
        Status oldStatus = new Status(TestHelper.SESSION_STATUS_OPEN);
        Status newStatus = new Status(TestHelper.SESSION_STATUS_CLOSE);
        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            listener.statusChanged(TestHelper.USER_ID_ONE, entity, oldStatus, newStatus);
        }
        endTest("testStatusChanged_OpenToClose()", TestHelper.MAX_TIME);
    }

    /**
     * Stress test for the statusChanged method. Changes session status from close to open.
     */
    public void testStatusChanged_CloseToOpen() {
        Status oldStatus = new Status(TestHelper.SESSION_STATUS_CLOSE);
        Status newStatus = new Status(TestHelper.SESSION_STATUS_OPEN);
        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            listener.statusChanged(TestHelper.USER_ID_ONE, entity, oldStatus, newStatus);
        }
        endTest("testStatusChanged_CloseToOpen()", TestHelper.MAX_TIME);
    }
}
