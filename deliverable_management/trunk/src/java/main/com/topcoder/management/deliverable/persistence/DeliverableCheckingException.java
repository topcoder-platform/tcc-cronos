/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * The DeliverableCheckingException indicates that there was an error when determining whether a
 * Deliverable has been completed or not.
 * </p>
 * <p>
 * This exception is thrown by DeliverableChecker implementations and then passes back through the
 * DeliverableManager interface and implementations.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0
 */
public class DeliverableCheckingException extends BaseException {

    /**
     * Creates a new DeliverableCheckingException.
     *
     * @param message Explanation of error, can be null
     */
    public DeliverableCheckingException(String message) {
        super(message);
    }

    /**
     * Creates a new DeliverableCheckingException.
     *
     * @param message Explanation of error, can be null
     * @param cause Underlying cause of error, can be null
     */
    public DeliverableCheckingException(String message, Exception cause) {
        super(message, cause);
    }
}
