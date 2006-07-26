/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

/**
 * <p>
 * The IdAlreadySetException is used to signal that the id of one of the resource modeling classes has already been set.
 * This is used to prevent the id being changed once it has been set.
 * </p>
 *
 * <p>
 * This exception is initially thrown in the 3 setId methods of the resource modeling classes.
 * </p>
 *
 * <p>
 * This class is thread safe.
 * </p>
 *
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class IdAlreadySetException extends IllegalStateException {

    /**
     * Creates a new IdAlreadySetException.
     *
     * @param message
     *            Explanation of error
     */
    public IdAlreadySetException(String message) {
        super(message);
    }
}
