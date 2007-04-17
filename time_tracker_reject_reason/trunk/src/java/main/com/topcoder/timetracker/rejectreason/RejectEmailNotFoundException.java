/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

/**
 * <p>
 * This exception is thrown if the involved RejectEmail was not found in the data store. This is thrown during
 * update/delete methods.
 * </p>
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailNotFoundException extends RejectEmailDAOException {
    /**
     * <p>
     * Creates a new instance of <code>RejectEmailNotFoundException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public RejectEmailNotFoundException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>RejectEmailNotFoundException</code> class with a detail error message and the
     * original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public RejectEmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new instance of <code>RejectEmailNotFoundException</code> class with a detail error message, the
     * original exception causing the error and the RejectEmail that the DAO was working on when the exception
     * occurred.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     * @param problemRejectEmail the RejectEmail that the DAO was working on when the exception occurred.
     */
    public RejectEmailNotFoundException(String message, Throwable cause, RejectEmail problemRejectEmail) {
        super(message, cause, problemRejectEmail);
    }
}
