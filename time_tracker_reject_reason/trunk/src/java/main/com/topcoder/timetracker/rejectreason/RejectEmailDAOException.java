/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown when a problem occurs while working with the RejectEmail DAOs.
 * </p>
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailDAOException extends BaseException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -7364260081909768645L;

	/**
     * <p>
     * The Reject Email that the DAO was working with when a problem occurred.
     * </p>
     */
    private final RejectEmail problemRejectEmail;

    /**
     * <p>
     * Creates a new instance of <code>RejectEmailDAOException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public RejectEmailDAOException(String message) {
        this(message, null, null);
    }

    /**
     * <p>
     * Creates a new instance of <code>RejectEmailDAOException</code> class with a detail error message and the
     * original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public RejectEmailDAOException(String message, Throwable cause) {
        this(message, cause, null);
    }

    /**
     * <p>
     * Creates a new instance of <code>RejectEmailDAOException</code> class with a detail error message, the original
     * exception causing the error and the RejectEmail that the DAO was working on when the exception occurred.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     * @param problemRejectEmail the RejectEmail that the DAO was working on when the exception occurred.
     */
    public RejectEmailDAOException(String message, Throwable cause, RejectEmail problemRejectEmail) {
        super(message, cause);
        this.problemRejectEmail = problemRejectEmail;
    }

    /**
     * <p>
     * Retrieves the Reject Email that the DAO was working with when a problem occurred.
     * </p>
     *
     * @return the Reject Email that the DAO was working with when a problem occurred.
     */
    public RejectEmail getProblemRejectEmail() {
        return problemRejectEmail;
    }
}
