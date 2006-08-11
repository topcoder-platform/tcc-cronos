/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external;

/**
 * <p>This exception represents a problem with retrieving data from persistent storage in the User
 * Project Data Store component.</p>
 * <p>It is thrown by the UserRetrieval and ProjectRetrieval interfaces (and their implementations and
 * base classes.) It can be used with a message, and with or without an underlying cause.</p>
 *
 * @author dplass, oodinary
 * @version 1.0
 */
public class RetrievalException extends UserProjectDataStoreException {

    /**
     * <p>Create this exception with the given message.</p>
     *
     * @param msg The message of this exception. May be null or empty after trim.
     */
    public RetrievalException(String msg) {
        super(msg);
    }

    /**
     * <p>Create this exception with the given message and underlying cause.</p>
     *
     * @param msg The message of this exception. May be null or empty after trim.
     * @param cause the underlying cause of the exception.
     */
    public RetrievalException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
