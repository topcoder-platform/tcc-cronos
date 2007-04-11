/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.ChatUserProfilePersistenceException;

/**
 * Exception thrown when a <code>ChatUserProfile</code> fails validation in the {@link
 * UnregisteredChatUserProfileInformixPersistence#createProfile createProfile} method.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class ChatUserProfileValidationException extends ChatUserProfilePersistenceException {
    /**
     * Creates a new <code>ChatUserProfileValidationException</code> with the specified message.
     *
     * @param message a description of the reason for this exception
     */
    public ChatUserProfileValidationException(String message) {
        super(message);
    }
}
