/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.user.profile;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown only by <code>ChatUserProfileManager</code> and its implementations
 * to indicate a type source that is not registered with the manager.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as its super class is also thread safe.
 * </p>
 *
 * @author duner, TCSDEVELOPER
 * @version 2.0
 */
public class UnrecognizedDataSourceTypeException extends BaseException {
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
    public UnrecognizedDataSourceTypeException(String message) {
        super(message);
    }
}
