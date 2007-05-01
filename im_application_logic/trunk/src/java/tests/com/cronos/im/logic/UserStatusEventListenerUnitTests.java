/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.status.ChatUserStatusTracker;

import junit.framework.TestCase;
import com.topcoder.database.statustracker.Status;
import com.topcoder.database.statustracker.Entity;

/**
 * <p>
 * Unit test cases for UserStatusEventListener class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserStatusEventListenerUnitTests extends TestCase {

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
     * Initialize the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();

        chatUserStatusTracker = new ChatUserStatusTracker();
        messagePool = new MockMessagePool();
        logger = new IMLogger(null);
        entity = new Entity(1, "name", new String[] { "key1" }, new Status[] { new Status(123) });
        listener = new UserStatusEventListener(chatUserStatusTracker, messagePool, logger);
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
     * Tests the constructor. No exception should be thrown.
     */
    public void test_constructor1_1() {
        new UserStatusEventListener(chatUserStatusTracker, messagePool, logger);
    }

    /**
     * Tests the constructor. No exception should be thrown.
     */
    public void test_constructor1_2() {
        new UserStatusEventListener(chatUserStatusTracker, messagePool, null);
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if any argument except the
     * logger is null.
     */
    public void test_constructor1_failure1() {
        try {
            new UserStatusEventListener(null, messagePool, logger);
            fail("IllegalArgumentException should be thrown if any argument except the logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor. IllegalArgumentException should be thrown if any argument except the
     * logger is null.
     */
    public void test_constructor1_failure2() {
        try {
            new UserStatusEventListener(chatUserStatusTracker, null, logger);
            fail("IllegalArgumentException should be thrown if any argument except the logger is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the statusChanged. No exception should be thrown.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_statusChanged_1() throws Exception {
        Status oldStatus = new Status(IMHelper.USER_STATUS_OFFLINE);
        Status newStatus = new Status(IMHelper.USER_STATUS_ONLINE);
        listener.statusChanged(123, entity, oldStatus, newStatus);

        assertTrue(MockMessagePool.getMessage().endsWith("register(123)"));
    }

    /**
     * Tests the statusChanged method. No exception should be thrown.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_statusChanged_2() throws Exception {
        Status oldStatus = new Status(IMHelper.USER_STATUS_ONLINE);
        Status newStatus = new Status(IMHelper.USER_STATUS_OFFLINE);
        listener.statusChanged(123, entity, oldStatus, newStatus);

        assertTrue(MockMessagePool.getMessage().endsWith("unregister(123)"));
    }

    /**
     * Failure test for the statusChanged. IllegalArgumentException should be thrown if entity is null.
     */
    public void test_statusChanged_failure1() {
        try {
            listener.statusChanged(123, null, new Status(1), new Status(2));
            fail("IllegalArgumentException should be thrown if entity is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the statusChanged. IllegalArgumentException should be thrown if new status is null.
     */
    public void test_statusChanged_failure2() {
        try {
            listener.statusChanged(123, entity, new Status(1), null);
            fail("IllegalArgumentException should be thrown if new status is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the statusChanged. IllegalArgumentException should be thrown if entity is invalid.
     */
    public void test_statusChanged_failure3() {
        entity = new Entity(2, "name", new String[] { "key1" }, new Status[] { new Status(123) });
        try {
            listener.statusChanged(123, entity, new Status(1), new Status(2));
            fail("IllegalArgumentException should be thrown if entity is invalid.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the getMessagePool method. The returned result should be correct.
     */
    public void test_getMessagePool() {
        assertEquals("The getMessagePool method is incorrect.", messagePool, listener.getMessagePool());
    }

    /**
     * Tests the getChatUserStatusTracker method. The returned result should be correct.
     */
    public void test_getChatUserStatusTracker() {
        assertEquals("The getChatUserStatusTracker method is incorrect.", chatUserStatusTracker, listener
                .getChatUserStatusTracker());
    }

}
