/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import junit.framework.TestCase;

import com.cronos.im.messenger.UserIDRetrieverException;
import com.cronos.im.messenger.impl.UserIDRetrieverImpl;

/**
 * Tests the {@link UserIDRetrieverImpl} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class UserIDRetrieverImplFailure extends TestCase {

    /**
     * Represents the MessengerImpl.
     */
    private UserIDRetrieverImpl userIDRetrieverImpl;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        userIDRetrieverImpl = new UserIDRetrieverImpl();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        userIDRetrieverImpl = null;
    }

    /**
     * <p>
     * Tests the retrieve method of <code>UserIDRetrieverImpl</code>.
     * </p>
     *
     * <p>
     * Sets the user id as null.
     * </p>
     *
     */
    public void testRetrieve() {
        try {
            userIDRetrieverImpl.retrieve(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        } catch (UserIDRetrieverException e) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * <p>
     * Tests the retrieve method of <code>UserIDRetrieverImpl</code>.
     * </p>
     *
     * <p>
     * Sets the user id as different object type other than Long.
     * </p>
     *
     */
    public void testRetrieve1() {
        try {
            userIDRetrieverImpl.retrieve("Not Long");
            fail("Should throw UserIDRetrieverException");
        } catch (IllegalArgumentException e) {
            fail("Should throw UserIDRetrieverException");
        } catch (UserIDRetrieverException e) {
            // expect
        }
    }
}