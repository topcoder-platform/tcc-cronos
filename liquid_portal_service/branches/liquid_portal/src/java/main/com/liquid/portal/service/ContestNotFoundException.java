/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import javax.ejb.ApplicationException;

/**
 * <p>
 * This exception is thrown by the service if it cannot find the given contest.
 * It extends LiquidPortalServiceException.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class ContestNotFoundException extends LiquidPortalServiceException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -8981275298065576222L;

    /**
     * <p>
     * Represents the contest ID that was not found.
     * </p>
     * <p>
     * It is set in the constructor it is retrieved in the getter.
     * </p>
     */
    private final long contestId;

    /**
     * <p>
     * Creates a new exception instance with the contest ID and error message.
     * </p>
     *
     * @param contestId
     *            the contest ID message
     * @param message
     *            error message
     */
    public ContestNotFoundException(long contestId, String message) {
        super(message);
        this.contestId = contestId;
    }

    /**
     * <p>
     * Creates a new exception instance with contest ID and error message and
     * the given cause of error.
     * </p>
     *
     * @param contestId
     *            the contest ID
     * @param cause
     *            cause of error
     * @param message
     *            error message
     */
    public ContestNotFoundException(long contestId, String message, Throwable cause) {
        super(message, cause);
        this.contestId = contestId;
    }

    /**
     * <p>
     * Gets the contest ID.
     * </p>
     *
     * @return the value of contest ID
     */
    public long getContestId() {
        return contestId;
    }
}
