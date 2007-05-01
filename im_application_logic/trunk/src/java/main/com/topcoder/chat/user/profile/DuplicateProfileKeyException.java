/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.user.profile;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown by <code>ProfileKeyManager</code> to indicate that a <code>ProfileKey</code>
 * already exists when one was not expected.
 * </p>
 *
 * <p>
 * This exception is only thrown from a call to {@link ProfileKeyManager#createProfileKey(ProfileKey)}.
 * </p>
 *
 * <p>
 * <code>ChatUserProfileManager</code> propagates this exception from <code>ProfileKeyManager</code>.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as its super class is also thread safe.
 * </p>
 *
 * @author duner, TCSDEVELOPER
 * @version 2.0
 */
public class DuplicateProfileKeyException extends BaseException {
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
    public DuplicateProfileKeyException(String message) {
        super(message);
    }
}
