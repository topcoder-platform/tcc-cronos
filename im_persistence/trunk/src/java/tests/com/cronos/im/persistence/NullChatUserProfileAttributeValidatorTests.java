/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.ChatUserProfile;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>NullChatUserProfileAttributeValidator</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class NullChatUserProfileAttributeValidatorTests extends TestCase {
    /**
     * Tests the constructor.
     */
    public void test_ctor() {
        // since the ctor is a no-op, there is not much to do here
        assertNotNull("constructor should create a valid instance", new NullChatUserProfileAttributeValidator());
    }

    /**
     * Tests that the <code>getMessage</code> method throws <code>IllegalArgumentException</code> when passed an
     * invalid type.
     */
    public void test_getMessage_invalid_profile() {
        try {
            new NullChatUserProfileAttributeValidator().getMessage("oh no");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>getMessage</code> method returns <code>null</code> when there are no errors.
     */
    public void test_getMessage() {
        ChatUserProfile profile = new ChatUserProfile("username", "type");
        profile.addProperty(UserDefinedAttributeNames.FIRST_NAME, "first");
        profile.addProperty(UserDefinedAttributeNames.LAST_NAME, "last");
        profile.addProperty(UserDefinedAttributeNames.EMAIL, "e-mail");

        assertNull("getMessage should return null", new NullChatUserProfileAttributeValidator().getMessage(profile));
    }

    /**
     * Tests that the <code>getMessage</code> method returns the correct errors when the properties do not exist.
     */
    public void test_getMessage_missing() {
        ChatUserProfile profile = new ChatUserProfile("username", "type");

        String message = new NullChatUserProfileAttributeValidator().getMessage(profile);
        assertNotNull("message should not be null", message);
        assertTrue("message should contain the string 'Missing first name.'",
                   message.indexOf("Missing first name.") > -1);
        assertTrue("message should contain the string 'Missing last name.'",
                   message.indexOf("Missing last name.") > -1);
        assertTrue("message should contain the string 'Missing e-mail address.'",
                   message.indexOf("Missing e-mail address.") > -1);
    }

    /**
     * Tests that the <code>getMessage</code> method returns the correct errors when the properties have empty
     * values.
     */
    public void test_getMessage_empty() {
        ChatUserProfile profile = new ChatUserProfile("username", "type");
        profile.addProperty(UserDefinedAttributeNames.FIRST_NAME, "   ");
        profile.addProperty(UserDefinedAttributeNames.LAST_NAME, "   ");
        profile.addProperty(UserDefinedAttributeNames.EMAIL, "   ");

        String message = new NullChatUserProfileAttributeValidator().getMessage(profile);
        assertNotNull("message should not be null", message);
        assertTrue("message should contain the string 'Empty first name.'",
                   message.indexOf("Empty first name.") > -1);
        assertTrue("message should contain the string 'Empty last name.'",
                   message.indexOf("Empty last name.") > -1);
        assertTrue("message should contain the string 'Empty e-mail address.'",
                   message.indexOf("Empty e-mail address.") > -1);
    }

    /**
     * Tests that the <code>valid</code> method throws <code>IllegalArgumentException</code> when passed an invalid
     * type.
     */
    public void test_valid_bad_type() {
        try {
            new NullChatUserProfileAttributeValidator().valid("oh no");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>valid</code> method.
     */
    public void test_valid() {
        ChatUserProfile profile = new ChatUserProfile("username", "type");
        NullChatUserProfileAttributeValidator chupav = new NullChatUserProfileAttributeValidator();

        assertFalse("profile should be invalid", chupav.valid(profile));

        profile.addProperty(UserDefinedAttributeNames.FIRST_NAME, "first");
        profile.addProperty(UserDefinedAttributeNames.LAST_NAME, "last");
        profile.addProperty(UserDefinedAttributeNames.EMAIL, "e-mail");

        assertTrue("profile should now be valid", chupav.valid(profile));
    }
}
