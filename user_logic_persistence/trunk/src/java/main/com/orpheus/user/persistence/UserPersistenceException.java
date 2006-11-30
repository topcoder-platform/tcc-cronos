/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Thrown when an error occurs in the User Logic Persistence component. This
 * exception is the base class for all exceptions defined by the User Logic
 * Persistence component. Users are encouraged to catch this exception in
 * try-catch blocks to simplify error handling, instead of catching each
 * specific component exception.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 */
public class UserPersistenceException extends BaseException {

    /**
     * <p>
     * Creates a new <code>UserPersistenceException</code> with the specified
     * detail message.
     * </p>
     *
     * @param message the detail message describing the reason for the exception
     */
    public UserPersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new <code>UserPersistenceException</code> with the specified
     * detail message and cause.
     * </p>
     *
     * @param message the detail message describing the reason for the exception
     * @param cause the cause of this exception
     */
    public UserPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
