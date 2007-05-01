package com.cronos.im.logic.stresstests;

import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.MockMessagePool;
import com.cronos.im.logic.UserStatusEventListener;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.status.ChatUserStatusTracker;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.Status;

/**
 * Stress tests for UserStatusEventListener. Tests statusChanged method under stress.
 * 
 * @author kaqi072821
 * @version 1.0
 */
public class UserStatusEventListenerStressTest extends BaseStressTest {

    /**
     * An instance of UserStatusEventListener for the following tests.
     */
    private UserStatusEventListener listener = null;

    /**
     * An instance of ChatUserStatusTracker for the following tests.
     */
    private ChatUserStatusTracker chatUserStatusTracker = null;

    /**
     * An instance of MessagePool for the following tests.
     */
    private MessagePool messagePool = null;

    /**
     * An instance of IMLogger for the following tests.
     */
    private IMLogger logger = null;

    /**
     * An instance of Entity for the following tests.
     */
    private Entity entity = null;

    /**
     * Initializes the environment.
     * 
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();

        chatUserStatusTracker = new ChatUserStatusTracker();
        messagePool = new MockMessagePool();
        logger = new IMLogger(null);
        entity = new Entity(TestHelper.USER_ID_ONE, "name", new String[] { "key1" }, new Status[] { new Status(
                TestHelper.USER_STATUS_OFFLINE) });
        listener = new UserStatusEventListener(chatUserStatusTracker, messagePool, logger);
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
     * Stress test for statusChanged method when the status changes from offline to online.
     */
    public void testStatusChanged_OffToOn() {
        Status oldStatus = new Status(TestHelper.USER_STATUS_OFFLINE);
        Status newStatus = new Status(TestHelper.USER_STATUS_ONLINE);
        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            listener.statusChanged(TestHelper.USER_ID_ONE, entity, oldStatus, newStatus);
        }
        endTest("testStatusChanged_OffToOn()", TestHelper.MAX_TIME);
    }

    /**
     * Stress test for statusChanged method when the status changes from online to offline.
     */
    public void testStatusChanged_OnToOff() {
        Status oldStatus = new Status(TestHelper.USER_STATUS_ONLINE);
        Status newStatus = new Status(TestHelper.USER_STATUS_OFFLINE);

        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            listener.statusChanged(TestHelper.USER_ID_ONE, entity, oldStatus, newStatus);
        }
        endTest("testStatusChanged_OnToOff()", TestHelper.MAX_TIME);
    }
}
