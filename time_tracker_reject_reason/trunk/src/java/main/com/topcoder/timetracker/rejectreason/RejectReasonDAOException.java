/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown when a problem occurs while working with the RejectReason DAOs.
 * </p>
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class RejectReasonDAOException extends BaseException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 4523220526378572136L;

	/**
     * <p>
     * The Reject Reason that the DAO was working with when a problem occurred.
     * </p>
     */
    private final RejectReason problemRejectReason;

    /**
     * <p>
     * Creates a new instance of <code>RejectReasonDAOException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public RejectReasonDAOException(String message) {
        this(message, null, null);
    }

    /**
     * <p>
     * Creates a new instance of <code>RejectReasonDAOException</code> class with a detail error message and the
     * original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public RejectReasonDAOException(String message, Throwable cause) {
        this(message, cause, null);
    }

    /**
     * <p>
     * Creates a new instance of <code>RejectReasonDAOException</code> class with a detail error message, the original
     * exception causing the error and the RejectReason that the DAO was working on when the exception occurred.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     * @param problemRejectReason the RejectReason that the DAO was working on when the exception occurred.
     */
    public RejectReasonDAOException(String message, Throwable cause, RejectReason problemRejectReason) {
        super(message, cause);
        this.problemRejectReason = problemRejectReason;
    }

    /**
     * <p>
     * Retrieves the Reject Reason that the DAO was working with when a problem occurred.
     * </p>
     *
     * @return the Reject Reason that the DAO was working with when a problem occurred.
     */
    public RejectReason getProblemRejectReason() {
        return problemRejectReason;
    }
}
