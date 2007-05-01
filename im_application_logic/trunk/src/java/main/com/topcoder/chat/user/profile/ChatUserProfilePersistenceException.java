/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.user.profile;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown by <code>ChatUserProfilePersistence</code> to indicate some
 * persistence related failure unrelated to the logical cases of a profile
 * not existing in the persistence and of a profile already existing in the
 * persistence.
 * </p>
 *
 * <p>
 * <code>ChatUserProfileManager</code> propagates this exception.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as its super class is also thread safe.
 * </p>
 *
 * @author duner, TCSDEVELOPER
 * @version 2.0
 */
public class ChatUserProfilePersistenceException extends BaseException {
    /**
     * <p>
     * Constructs the exception taking a message as to why it was thrown.
     * </p>
     *
     * <p>
     * The message needs to be meaningful, so that the user will benefit
     * from meaningful messages.
     * </p>
     *
     * @param message A descriptive message of why it was thrown.
     */
    public ChatUserProfilePersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs the exception taking an original cause as to why it was thrown.
     * </p>
     *
     * <p>
     * The cause is the inner exception.
     * </p>
     *
     * @param cause The Throwable cause of the exception.
     */
    public ChatUserProfilePersistenceException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructs the exception taking a message as to why it was thrown,
     * as well as an original cause.
     * </p>
     *
     * <p>
     * The message needs to be meaningful, so that the user will benefit
     * from meaningful messages.
     * </p>
     *
     * <p>
     * The cause is the inner exception.
     * </p>
     *
     * @param message A descriptive message of why it was thrown.
     * @param cause The exception or error that originally caused this to be thrown.
     */
    public ChatUserProfilePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
