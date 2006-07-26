/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * The DeliverablePersistenceException indicates that there was an error accessing or updating a
 * persisted resource store. This exception is used to wrap the internal error that occurs when
 * accessing the persistence store. For example, in the SqlDeliverablePersistence implementation it
 * is used to wrap SqlExceptions.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0
 */
public class DeliverablePersistenceException extends BaseException {

    /**
     * Creates a new DeliverablePersistenceException.
     *
     * @param message Explanation of error, can be null
     */
    public DeliverablePersistenceException(String message) {
        super(message);
    }

    /**
     * Creates a new DeliverablePersistenceException.
     *
     * @param message Explanation of error, can be null
     * @param cause Underlying cause of error, can be null
     */
    public DeliverablePersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
