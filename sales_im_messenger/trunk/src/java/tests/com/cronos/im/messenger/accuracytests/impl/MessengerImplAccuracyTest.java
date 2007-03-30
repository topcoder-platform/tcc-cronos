/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests.impl;

import com.cronos.im.messenger.Messenger;
import com.cronos.im.messenger.UserIDRetriever;
import com.cronos.im.messenger.accuracytests.AccuracyHelper;
import com.cronos.im.messenger.impl.ChatMessageTrackerImpl;
import com.cronos.im.messenger.impl.MessengerImpl;
import com.cronos.im.messenger.impl.UserIDRetrieverImpl;
import com.topcoder.chat.contact.ChatContactManager;
import com.topcoder.chat.contact.SimpleChatContactManager;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.message.pool.impl.DefaultMessagePool;
import junit.framework.TestCase;


/**
 * Tests the functionality for class <code>MessengerImpl</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class MessengerImplAccuracyTest extends TestCase {
    /**
     * An instance of <code>MessagePool</code> for testing.
     */
    private MessagePool messagePool;

    /**
     * An instance of <code>ChatMessageTrackerImpl</code> for testing.
     */
    private ChatMessageTrackerImpl chatMessageTracker;

    /**
     * An instance of <code>UserIDRetriever</code> for testing.
     */
    private UserIDRetriever idRetriever;

    /**
     * An instance of <code>ChatContactManager</code> for testing.
     */
    private ChatContactManager contactManager;

    /**
     * An instance of <code>MesengerImpl</code> for testing.
     */
    private MessengerImpl messenger;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        AccuracyHelper.clearConfig();
        AccuracyHelper.loadConfig("config.xml");

        messagePool = new DefaultMessagePool();
        messagePool.register(999);
        messagePool.register(999, 999);

        chatMessageTracker = AccuracyHelper.createTracker();

        idRetriever = new UserIDRetrieverImpl();

        contactManager = new SimpleChatContactManager();
        messenger = new MessengerImpl(messagePool, chatMessageTracker, idRetriever, contactManager);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        AccuracyHelper.clearConfig();
    }

    /**
     * Test method for 'MessengerImpl(MessagePool, MessageTracker, UserIDRetriever, ChatContactManager)'
     */
    public void testMessengerImpl() {
        assertTrue("Test method for 'MessengerImpl(MessagePool, MessageTracker, UserIDRetriever, ChatContactManager)' failed.",
            messenger instanceof Messenger);
    }

    /**
     * Test method for 'MessengerImpl.getMessagePool()'.
     */
    public void testGetMessagePool() {
        MessagePool pool = messenger.getMessagePool();
        assertSame("Test method for 'MessengerImpl.getMessagePool()' failed.", messagePool, pool);
    }

    /**
     * Test method for 'MessengerImpl.setMessagePool(MessagePool)'.
     */
    public void testSetMessagePool() {
        messenger.setMessagePool(messagePool);

        MessagePool pool = (MessagePool) AccuracyHelper.getPrivateField(MessengerImpl.class, messenger, "pool");
        assertSame("Test method for 'MessengerImpl.setMessagePool(MessagePool)' failed.", messagePool, pool);
    }
}
