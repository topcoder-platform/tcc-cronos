/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception signals that there is error in performing method(s) of
 * ContestDataRetriever interface.
 * </p>
 * <p>
 * This is thrown by implementation of <code>ContestDataRetriever</code> interface during
 * retrieving contest data process (<code>retrieveContestData</code> methods).
 * </p>
 * <p>
 * <strong> Thread Safety:</strong> exceptions are not thread safe and they are
 * not expected to be used concurrently.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ContestDataRetrieverException extends BaseException {
    /**
     * Create exception with the given message.
     *
     * @param message The exception message
     *
     */
    public ContestDataRetrieverException(String message) {
        super(message);
    }

    /**
     * Create exception with the given message and cause.
     *
     * @param message The exception message
     * @param cause The exception cause
     *
     */
    public ContestDataRetrieverException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create exception with the given message and exception data.
     *
     * @param message The exception message
     * @param data The exception data
     *
     */
    public ContestDataRetrieverException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Create exception with the given message, cause and exception data.
     *
     * @param message The exception message
     * @param cause The exception cause
     * @param data The exception data
     *
     */
    public ContestDataRetrieverException(String message, Throwable cause,
            ExceptionData data) {
        super(message, cause, data);
    }
}