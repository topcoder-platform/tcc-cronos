/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.session;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception extends the <code>BaseException</code>.
 * </p>
 *
 * <p>
 * It is thrown when it is impossible to provide changes in data store.
 * It may be different reasons for throwing this exception like incorrect user id,
 * failed to create connection to database etc.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as its super class is also thread safe.
 * </p>
 *
 * @author tushak, TCSDEVELOPER
 * @version 1.0
 */
public class ChatSessionDataStoreException extends BaseException {
    /**
     * <p>
     * Constructs the exception taking a message as to why it was thrown.
     * </p>
     *
     * <p>
     * The message needs to be meaningful, so that the user will benefit from
     * meaningful messages.
     * </p>
     *
     * @param message A descriptive message of why it was thrown.
     */
    public ChatSessionDataStoreException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs the exception taking a message as to why it was thrown,
     * as well as an original cause.
     * </p>
     *
     * <p>
     * The message needs to be meaningful, so that the user will benefit from
     * meaningful messages.
     * </p>
     *
     * <p>
     * The cause is the inner exception.
     * </p>
     *
     * @param message A descriptive message of why it was thrown.
     * @param cause The exception or error that originally caused this to be thrown.
     */
    public ChatSessionDataStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
