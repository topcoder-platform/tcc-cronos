/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import com.orpheus.administration.Helper;

/**
 * This class encapsulates the result of a handler operation. It holds a result
 * code, an explanatory message and an exception, if any, which caused the
 * failure.<br/> It is used by some of the Handler implementations like
 * LoginHandler as a value to set in the request attribute. The result to which
 * the request is then forwarded to can extract this instance from the request
 * attribute and do appropriate handling, such as displaying a different error
 * message based on the result code.<br/> Thread-Safety: This class is
 * immutable and hence thread-safe.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class HandlerResult {

    /**
     * will hold the ResultCode denoting the reason for failure.<br/> It is
     * initialized in the constructor and never changes after that.<br/> It
     * will never be null.
     *
     */
    private final ResultCode resultCode;

    /**
     * will hold the message explaining the result.<br/> It is initialized in
     * the constructor and never changes after that.<br/> It will never be null
     * or empty.
     *
     */
    private final String message;

    /**
     * will hold the ResultCode denoting the reason for failure.<br/> It is
     * initialized in the constructor and never changes after that.<br/> It may
     * be null.
     *
     */
    private final Exception cause;

    /**
     * Creates a HandlerResult instance with given values.
     *
     *
     * @param resultCode
     *            the result code.
     * @param message
     *            an explanatory message.
     * @throws IllegalArgumentException
     *             if any argument is null, or if message is empty.
     */
    public HandlerResult(ResultCode resultCode, String message) {
        Helper.checkNull(resultCode, "resultCode");
        Helper.checkString(message, "message");

        this.resultCode = resultCode;
        this.message = message;
        this.cause = null;
    }

    /**
     * Creates a HandlerResult instance with given values.
     *
     *
     * @param resultCode
     *            the result code.
     * @param message
     *            an explanatory message.
     * @param cause
     *            the cause of failure
     * @throws IllegalArgumentException
     *             if any argument is null, or if message is empty.
     */
    public HandlerResult(ResultCode resultCode, String message, Exception cause) {
        Helper.checkNull(resultCode, "resultCode");
        Helper.checkString(message, "message");
        Helper.checkNull(cause, "cause");

        this.resultCode = resultCode;
        this.message = message;
        this.cause = cause;
    }

    /**
     * Returns the result code.
     *
     *
     * @return the result code.
     */
    public ResultCode getResultCode() {
        return resultCode;
    }

    /**
     * Returns the explanatory message.
     *
     *
     * @return the explanatory message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the exception cause of failure if any.
     *
     *
     * @return the exception cause of failure if any.
     */
    public Exception getCause() {
        return cause;
    }
}
