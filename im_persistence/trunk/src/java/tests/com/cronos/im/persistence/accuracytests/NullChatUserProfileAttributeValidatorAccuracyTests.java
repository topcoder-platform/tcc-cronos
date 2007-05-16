/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import junit.framework.TestCase;

import com.cronos.im.persistence.NullChatUserProfileAttributeValidator;
import com.cronos.im.persistence.UserDefinedAttributeNames;
import com.topcoder.chat.user.profile.ChatUserProfile;

/**
 * <p>
 * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class NullChatUserProfileAttributeValidatorAccuracyTests extends TestCase {

    /**
     * <p>
     * Represents the <code>{@link NullChatUserProfileAttributeValidator}</code> instance used in tests.
     * </p>
     */
    private NullChatUserProfileAttributeValidator nullChatUserProfileAttributeValidator;

    /**
     * <p>
     * Represents a ChatUserProfile instance.
     * </p>
     */
    private ChatUserProfile chatUserProfile;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        nullChatUserProfileAttributeValidator = new NullChatUserProfileAttributeValidator();
        chatUserProfile = new ChatUserProfile("FireIce", "DEVELOPER");
        chatUserProfile.addProperty(UserDefinedAttributeNames.FIRST_NAME, "FireIce");
        chatUserProfile.addProperty(UserDefinedAttributeNames.LAST_NAME, "Zhang");
        chatUserProfile.addProperty(UserDefinedAttributeNames.EMAIL, "FireIce@topcoder.com");
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator}</code> class.
     * </p>
     */
    public void testNullChatUserProfileAttributeValidatorAccuracy() {
        nullChatUserProfileAttributeValidator = new NullChatUserProfileAttributeValidator();
        assertNotNull(nullChatUserProfileAttributeValidator);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#valid(Object)}</code> method.
     * </p>
     */
    public void testValidAccuracy1() {
        assertTrue(nullChatUserProfileAttributeValidator.valid(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#valid(Object)}</code> method.
     * </p>
     */
    public void testValidAccuracy2() {
        chatUserProfile.removeProperty(UserDefinedAttributeNames.FIRST_NAME);
        assertFalse(nullChatUserProfileAttributeValidator.valid(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#valid(Object)}</code> method.
     * </p>
     */
    public void testValidAccuracy4() {
        chatUserProfile.setProperty(UserDefinedAttributeNames.FIRST_NAME, " ");
        assertFalse(nullChatUserProfileAttributeValidator.valid(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#valid(Object)}</code> method.
     * </p>
     */
    public void testValidAccuracy5() {
        chatUserProfile.removeProperty(UserDefinedAttributeNames.LAST_NAME);
        assertFalse(nullChatUserProfileAttributeValidator.valid(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#valid(Object)}</code> method.
     * </p>
     */
    public void testValidAccuracy7() {
        chatUserProfile.setProperty(UserDefinedAttributeNames.LAST_NAME, " ");
        assertFalse(nullChatUserProfileAttributeValidator.valid(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#valid(Object)}</code> method.
     * </p>
     */
    public void testValidAccuracy8() {
        chatUserProfile.removeProperty(UserDefinedAttributeNames.EMAIL);
        assertFalse(nullChatUserProfileAttributeValidator.valid(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#valid(Object)}</code> method.
     * </p>
     */
    public void testValidAccuracy10() {
        chatUserProfile.setProperty(UserDefinedAttributeNames.EMAIL, " ");
        assertFalse(nullChatUserProfileAttributeValidator.valid(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#getMessage(Object)}</code> method.
     * </p>
     */
    public void testGetMessageAccuracy1() {
        assertNull(nullChatUserProfileAttributeValidator.getMessage(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#getMessage(Object)}</code> method.
     * </p>
     */
    public void testGetMessageAccuracy2() {
        chatUserProfile.removeProperty(UserDefinedAttributeNames.FIRST_NAME);
        assertNotNull(nullChatUserProfileAttributeValidator.getMessage(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#getMessage(Object)}</code> method.
     * </p>
     */
    public void testGetMessageAccuracy4() {
        chatUserProfile.setProperty(UserDefinedAttributeNames.FIRST_NAME, " ");
        assertNotNull(nullChatUserProfileAttributeValidator.getMessage(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#getMessage(Object)}</code> method.
     * </p>
     */
    public void testGetMessageAccuracy5() {
        chatUserProfile.removeProperty(UserDefinedAttributeNames.LAST_NAME);
        assertNotNull(nullChatUserProfileAttributeValidator.getMessage(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#getMessage(Object)}</code> method.
     * </p>
     */
    public void testGetMessageAccuracy7() {
        chatUserProfile.setProperty(UserDefinedAttributeNames.LAST_NAME, " ");
        assertNotNull(nullChatUserProfileAttributeValidator.getMessage(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#getMessage(Object)}</code> method.
     * </p>
     */
    public void testGetMessageAccuracy8() {
        chatUserProfile.removeProperty(UserDefinedAttributeNames.EMAIL);
        assertNotNull(nullChatUserProfileAttributeValidator.getMessage(chatUserProfile));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link NullChatUserProfileAttributeValidator#getMessage(Object)}</code> method.
     * </p>
     */
    public void testGetMessageAccuracy10() {
        chatUserProfile.setProperty(UserDefinedAttributeNames.EMAIL, " ");
        assertNotNull(nullChatUserProfileAttributeValidator.getMessage(chatUserProfile));
    }
}
