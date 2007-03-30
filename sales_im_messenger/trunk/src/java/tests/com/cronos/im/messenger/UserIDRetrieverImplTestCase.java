/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import com.cronos.im.messenger.impl.UserIDRetrieverImpl;
import junit.framework.TestCase;

/**
 * Unit test cases for <c>UserIDRetrieverImpl</c> class.
 *
 * @author marius_neo
 * @version 1.0
 */
public class UserIDRetrieverImplTestCase extends TestCase {
    /**
     * <c>UserIDRetrieverImpl</c> class instance used in tests.
     * It is initialized in <c>setUp()</c> method.
     */
    private UserIDRetriever retriever;

    /**
     * Initializes test environment. Sets <c>retriever</c> field.
     */
    protected void setUp() {
        retriever = new UserIDRetrieverImpl();
    }

    /**
     * Tests <c>UserIDRetrieverImpl()</c>. Simply tests if the instance
     * was created.
     */
    public void testCtor() {
        assertNotNull("retriever was not created", retriever);
    }

    /**
     * Tests <c>retrieve(Object)</c> method for accuracy.No exception should be thrown.
     *
     * @throws UserIDRetrieverException Should not be thrown.
     */
    public void testRetriveAccuracy() throws UserIDRetrieverException {
        long userId = 123;

        long resultedUserId = retriever.retrieve(new Long(userId));

        assertEquals("The user ids should be equal", userId, resultedUserId);
    }

    /**
     * Tests <c>retrieve(Object)</c> method for failure when the argument passed to the
     * method is null.
     *
     * @throws UserIDRetrieverException Should not be thrown.
     * @throws IllegalArgumentException Is expected to be thrown.
     */
    public void testRetrieveNull() throws UserIDRetrieverException {
        try {
            retriever.retrieve(null);

            fail("Should have thrown IllegalArgumentException because of null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>retrieve(Object)</c> method for failure when the argument passed to the
     * method is not long.
     *
     * @throws UserIDRetrieverException Is expected to be thrown.
     */
    public void testRetrieveNotLong() {
        try {
            retriever.retrieve(new Integer(123));

            fail("Should have thrown UserIDRetrieverException because of not-Long argument");
        } catch (UserIDRetrieverException e) {
            // Success.
        }
    }
}
