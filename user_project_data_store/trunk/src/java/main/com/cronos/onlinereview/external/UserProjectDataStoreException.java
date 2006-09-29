/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>This is the base exception for all exceptions in the User Project Data Store component.</p>
 * <p>It can be used with or without a message, and with or without an underlying cause.</p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 */
public class UserProjectDataStoreException extends BaseException {

    /**
     * <p>Creates this exception with no message.</p>
     */
    public UserProjectDataStoreException() {
        super();
    }

    /**
     * <p>Creates this exception with the given message.</p>
     *
     * @param msg The message of this exception. May be null or empty after trim.
     */
    public UserProjectDataStoreException(String msg) {
        super(msg);
    }

    /**
     * <p>Create this exception with the given message and underlying cause.</p>
     *
     * @param msg The message of this exception. May be null or empty after trim.
     * @param cause the underlying cause of the exception.
     */
    public  UserProjectDataStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
