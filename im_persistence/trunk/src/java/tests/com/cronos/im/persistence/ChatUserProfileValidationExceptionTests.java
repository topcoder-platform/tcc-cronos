/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.ChatUserProfilePersistenceException;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>ChatUserProfileValidationException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class ChatUserProfileValidationExceptionTests extends TestCase {
    /**
     * Tests that <code>ChatUserProfileValidationException</code> inherits from
     * <code>ChatUserProfilePersistenceException</code>.
     */
    public void test_inheritance() {
        assertTrue("ChatUserProfileValidationException should inherit from ChatUserProfilePersistenceException",
                   ChatUserProfilePersistenceException.class.
                   isAssignableFrom(ChatUserProfileValidationException.class));
    }

    /**
     * Tests the first constructor and the <code>getMessage</code> method.
     */
    public void test_ctor1() {
        assertEquals("getMessage() should return the message passed to the constructor", "message",
                     new ChatUserProfileValidationException("message").getMessage());
    }
}
