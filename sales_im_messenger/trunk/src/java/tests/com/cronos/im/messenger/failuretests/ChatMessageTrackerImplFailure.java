/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import junit.framework.TestCase;

import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.MessageTrackerException;
import com.cronos.im.messenger.impl.ChatMessageTrackerImpl;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * Tests the {@link ChatMessageTrackerImpl} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class ChatMessageTrackerImplFailure extends TestCase {

    /**
     * Represents the ChatMessageTrackerImpl.
     */
    private ChatMessageTrackerImpl chatMessageTrackerImpl;

    /**
     * Represents the DbConnectionFactory.
     */
    private DBConnectionFactory connectionFactory;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadConfigFile("db_connection_factory_config.xml");
        connectionFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        chatMessageTrackerImpl = new ChatMessageTrackerImpl(connectionFactory, "connectionName", "user");
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        chatMessageTrackerImpl = null;
        FailureTestHelper.releaseConfigFiles();
    }

    /**
     * <p>
     * Tests the constructor of <code>ChatMessageTrackerImpl</code> for null <code>DbConnectionFactory</code>.
     * </p>
     *
     */
    public void testChatMessageTrackerImpl() {
        try {
            chatMessageTrackerImpl = new ChatMessageTrackerImpl(null, "connectionName", "user");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the constructor of <code>ChatMessageTrackerImpl</code> for null user.
     * </p>
     *
     */
    public void testChatMessageTrackerImpl2() {
        try {
            chatMessageTrackerImpl = new ChatMessageTrackerImpl(connectionFactory, "connectionName", null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the constructor of <code>ChatMessageTrackerImpl</code> for user as empty string.
     * </p>
     *
     */
    public void testChatMessageTrackerImpl3() {
        try {
            chatMessageTrackerImpl = new ChatMessageTrackerImpl(connectionFactory, "connectionName", "   ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the track method of <code>ChatMessageTrackerImpl</code>.
     * </p>
     *
     * <p>
     * Sets the message to track as null.
     * </p>
     *
     */
    public void testTrack1() {
        try {
            chatMessageTrackerImpl = new ChatMessageTrackerImpl(connectionFactory, "informix_connect", "ADMIN");
            chatMessageTrackerImpl.track(null, new long[] {1, 2 }, 1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (MessageTrackerException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the track method of <code>ChatMessageTrackerImpl</code>.
     * </p>
     *
     * <p>
     * Sets the user id array to track as null.
     * </p>
     *
     */
    public void testTrack2() {
        try {
            chatMessageTrackerImpl = new ChatMessageTrackerImpl(connectionFactory, "informix_connect", "ADMIN");
            chatMessageTrackerImpl.track(new ChatMessage(), null, 1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (MessageTrackerException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the track method of <code>ChatMessageTrackerImpl</code>.
     * </p>
     *
     * <p>
     * Sets the user id array with duplicate elements.
     * </p>
     *
     */
    public void testTrack3() {
        try {
            chatMessageTrackerImpl = new ChatMessageTrackerImpl(connectionFactory, "informix_connect", "ADMIN");
            chatMessageTrackerImpl.track(new ChatMessage(), new long[] {1, 1 }, 1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (MessageTrackerException e) {
            fail("Should throw IllegalArgumentException");
        }
    }   

    /**
     * <p>
     * Tests the track method of <code>ChatMessageTrackerImpl</code>.
     * </p>
     *
     * <p>
     * Tests the method whose session id is not there in database.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testTrack5() throws Exception {
        try {
            FailureTestHelper.releaseConfigFiles();
            FailureTestHelper.loadConfigFile("db_connection_factory_config.xml");
            chatMessageTrackerImpl = new ChatMessageTrackerImpl(connectionFactory, "informix_connect", "ADMIN");
            chatMessageTrackerImpl.track(new ChatMessage(), new long[] {1 }, 1000);
            fail("Should throw MessageTrackerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessageTrackerException");
        } catch (MessageTrackerException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the track method of <code>ChatMessageTrackerImpl</code>.
     * </p>
     *
     * <p>
     * Tests the method whose user id is not there in database.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testTrack6() throws Exception {
        try {
            FailureTestHelper.releaseConfigFiles();
            FailureTestHelper.loadConfigFile("db_connection_factory_config.xml");
            chatMessageTrackerImpl = new ChatMessageTrackerImpl(connectionFactory, "informix_connect", "ADMIN");
            chatMessageTrackerImpl.track(new ChatMessage(), new long[] {100 }, 0);
            fail("Should throw MessageTrackerException");
        } catch (IllegalArgumentException e) {
            fail("Should throw MessageTrackerException");
        } catch (MessageTrackerException e) {
            // expect
        }
    }

}
