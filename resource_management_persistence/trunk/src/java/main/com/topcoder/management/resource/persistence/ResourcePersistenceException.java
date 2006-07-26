/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * The ResourcePersistenceException indicates that there was an error accessing or updating a persisted resource store.
 * This exception is used to wrap the internal error that occurs when accessing the persistence store. For example, in
 * the SqlResourcePersistence implementation it is used to wrap SqlExceptions.
 * </p>
 *
 * <p>
 * This exception is initially thrown in ResourcePersistence implementations and from there passes through
 * ResourceManager implementations and back to the caller. It is also thrown directly by some ResourceManager
 * implementations.
 * </p>
 *
 * <p>
 * This class is thread safe.
 * </p>
 *
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class ResourcePersistenceException extends BaseException {

    /**
     * Creates a new ResourcePersistenceException with error message.
     *
     * @param message
     *            Explanation or error, can be null
     */
    public ResourcePersistenceException(String message) {
        super(message);
    }

    /**
     * Creates a new ResourcePersistenceException with error message and inner cause.
     *
     * @param message
     *            Explanation or error, can be null
     * @param cause
     *            Underlying cause of error, can be null
     */
    public ResourcePersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
