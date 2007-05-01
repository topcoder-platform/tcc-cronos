/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.failure;

import junit.framework.TestCase;

import com.cronos.im.logic.IMLogger;
import com.cronos.im.logic.UserStatusEventListener;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.status.ChatUserStatusTracker;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.Status;

/**
 * <p>
 * Failure tests for UserStatusEventListener class.
 * </p>
 *
 * @author singlewood
 * @version 1.0
 */
public class UserStatusEventListenerFailureTests extends TestCase {

    /**
     * An instance of UserStatusEventListener for tests.
     */
    private UserStatusEventListener listener;

    /**
     * An instance of ChatUserStatusTracker for tests.
     */
    private ChatUserStatusTracker chatUserStatusTracker;

    /**
     * An instance of MessagePool for tests.
     */
    private MessagePool messagePool;

    /**
     * An instance of IMLogger for tests.
     */
    private IMLogger logger;

    /**
     * An instance of Entity for tests.
     */
    private Entity entity;

    /**
     * Initialize the environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadConfig();
        chatUserStatusTracker = new ChatUserStatusTracker();
        logger = new IMLogger(null);
        entity = new Entity(1, "name", new String[] {"sad"}, new Status[] {new Status(222)});
        messagePool = new MessagePoolFMock();
        listener = new UserStatusEventListener(chatUserStatusTracker, messagePool, logger);
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.clearConfig();
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if
     * chatUserStatusTracker is null
     *
     * @throws Exception throw to JUnit
     */
    public void testConstructor1Failure1() {
        try {
            new UserStatusEventListener(null, messagePool, logger);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if
     * messagePool is null
     *
     * @throws Exception throw to JUnit
     */
    public void testConstructor1Failure2() {
        try {
            new UserStatusEventListener(chatUserStatusTracker, null, logger);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the statusChanged(long id, Entity entity, Status oldStatus, Status
     * newStatus). IllegalArgumentException should be thrown if entity is null.
     */
    public void testStatusChangedFailure1() {
        try {
            listener.statusChanged(12312, null, new Status(1), new Status(2));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the statusChanged(long id, Entity entity, Status oldStatus, Status
     * newStatus). IllegalArgumentException should be thrown if newstatus is null.
     */
    public void testStatusChangedFailure2() {
        try {
            listener.statusChanged(123, entity, new Status(1), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for the statusChanged(long id, Entity entity, Status oldStatus, Status
     * newStatus). IllegalArgumentException should be thrown if entity is invalid.
     */
    public void testStatusChangedFailure3() {
        entity = new Entity(2, "lala", new String[] {"pisd"}, new Status[] {new Status(2)});
        try {
            listener.statusChanged(123, entity, new Status(1), new Status(2));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
