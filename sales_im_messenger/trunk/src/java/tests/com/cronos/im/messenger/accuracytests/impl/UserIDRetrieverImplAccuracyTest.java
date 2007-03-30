/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests.impl;

import com.cronos.im.messenger.UserIDRetriever;
import com.cronos.im.messenger.UserIDRetrieverException;
import com.cronos.im.messenger.impl.UserIDRetrieverImpl;
import junit.framework.TestCase;


/**
 * Accuracy test for class <code>UserIDRetrieverImpl</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class UserIDRetrieverImplAccuracyTest extends TestCase {
    /**
     * An instance of <code>AskForChatMessage</code> class for testing.
     */
    private UserIDRetrieverImpl retriever;

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        retriever = new UserIDRetrieverImpl();
    }

    /**
     * Test method for 'UserIDRetrieverImpl.UserIDRetrieverImpl()'.
     */
    public void testUserIDRetrieverImpl() {
        assertTrue("Test method for 'UserIDRetrieverImpl.UserIDRetrieverImpl()' failed.",
            retriever instanceof UserIDRetriever);
    }

    /**
     * Test method for 'UserIDRetrieverImpl.retrieve(Object)'
     *
     * @throws UserIDRetrieverException to JUnit
     */
    public void testRetrieve() throws UserIDRetrieverException {
        Long id = new Long(24234);
        assertEquals("Test method for 'UserIDRetrieverImpl.retrieve(Object)' failed.", 24234, retriever.retrieve(id));
    }
}
