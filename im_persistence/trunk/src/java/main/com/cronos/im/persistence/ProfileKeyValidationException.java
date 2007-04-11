/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.ProfileKeyManagerPersistenceException;

/**
 * An exception thrown by the {@link InformixProfileKeyManager#createProfileKey createProfileKey} method when a
 * profile key is rejected by the object validator.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class ProfileKeyValidationException extends ProfileKeyManagerPersistenceException {
    /**
     * Creates a new <code>ProfileKeyValidationException</code> with the specified message.
     *
     * @param message a description of the reason for this exception
     */
    public ProfileKeyValidationException(String message) {
        super(message);
    }
}
