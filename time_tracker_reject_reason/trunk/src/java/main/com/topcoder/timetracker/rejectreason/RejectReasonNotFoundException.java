/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

/**
 * <p>
 * This exception is thrown if the involved RejectReason was not found in the data store. This is thrown during
 * update/delete methods.
 * </p>
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class RejectReasonNotFoundException extends RejectReasonDAOException {
    /**
     * <p>
     * Creates a new instance of <code>RejectReasonNotFoundException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public RejectReasonNotFoundException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>RejectReasonNotFoundException</code> class with a detail error message and the
     * original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public RejectReasonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new instance of <code>RejectReasonNotFoundException</code> class with a detail error message, the
     * original exception causing the error and the RejectReason that the DAO was working on when the exception
     * occurred.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     * @param problemRejectReason the RejectReason that the DAO was working on when the exception occurred.
     */
    public RejectReasonNotFoundException(String message, Throwable cause, RejectReason problemRejectReason) {
        super(message, cause, problemRejectReason);
    }
}
