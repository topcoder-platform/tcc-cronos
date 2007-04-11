/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.ProfileKeyManagerPersistenceException;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>ProfileKeyValidationException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class ProfileKeyValidationExceptionTests extends TestCase {
    /**
     * Tests that <code>ProfileKeyValidationException</code> inherits from
     * <code>ProfileKeyManagerPersistenceException</code>.
     */
    public void test_inheritance() {
        assertTrue("ProfileKeyValidationException should inherit from ProfileKeyManagerPersistenceException",
                   ProfileKeyManagerPersistenceException.class.isAssignableFrom(ProfileKeyValidationException.class));
    }

    /**
     * Tests the first constructor and the <code>getMessage</code> method.
     */
    public void test_ctor1() {
        assertEquals("getMessage() should return the message passed to the constructor", "message",
                     new ProfileKeyValidationException("message").getMessage());
    }
}
