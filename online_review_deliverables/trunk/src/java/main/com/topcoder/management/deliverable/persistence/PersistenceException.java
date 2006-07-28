/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.deliverable.persistence;

import com.topcoder.util.errorhandling.BaseException;

/**
 * @author kr00tki
 * @version 1.0
 */
public class PersistenceException extends BaseException {
    /**
     * DeliverableCheckingException constructor: Creates a new DeliverableCheckingException .
     *
     *
     * @param message Explanation of error, can be null
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * DeliverableCheckingException constructor: Creates a new DeliverableCheckingException .
     *
     *
     * @param message Explanation of error, can be null
     * @param cause Underlying cause of error, can be null
     */
    public PersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
