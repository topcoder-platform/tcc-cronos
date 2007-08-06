/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception is the base exception for this component. All custom exceptions should extend from this exception.
 * The constructors in this exception should wrap those of the base class.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */
public class FirefoxClientException extends BaseException {
    /**
     * <p>
     * Creates a new instance of <code>FirefoxClientException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public FirefoxClientException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>FirefoxClientException</code> class with a detail error message and the original
     * exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public FirefoxClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
