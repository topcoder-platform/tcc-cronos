/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of DirectServiceFacade when studio/software contest with the specified
 * ID cannot be found in persistence.
 * </p>
 *
 * <p>
 * Annotation: @ApplicationException(rollback = true).
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class ContestNotFoundException extends DirectServiceFacadeException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -6405681035367964154L;

    /**
     * <p>
     * The ID of the contest that cannot be found. Is initialized in the constructor and never changed after that. Can
     * be any value. Has a getter.
     * </p>
     */
    private final long contestId;

    /**
     * <p>
     * Creates a new instance of this exception with the given message and contest ID.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param contestId
     *            the ID of the contest that cannot be found
     */
    public ContestNotFoundException(String message, long contestId) {
        super(message);
        this.contestId = contestId;
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message, cause and contest ID.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param cause
     *            the inner cause of this exception
     * @param contestId
     *            the ID of the contest that cannot be found
     */
    public ContestNotFoundException(String message, Throwable cause, long contestId) {
        super(message, cause);
        this.contestId = contestId;
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message, exception data and contest ID.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param data
     *            the exception data
     * @param contestId
     *            the ID of the contest that cannot be found
     */
    public ContestNotFoundException(String message, ExceptionData data, long contestId) {
        super(message, data);
        this.contestId = contestId;
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message, cause, exception data and contest ID.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param cause
     *            the inner cause of this exception
     * @param data
     *            the exception data
     * @param contestId
     *            the ID of the contest that cannot be found
     */
    public ContestNotFoundException(String message, Throwable cause, ExceptionData data, long contestId) {
        super(message, cause, data);
        this.contestId = contestId;
    }

    /**
     * <p>
     * Retrieves the ID of the contest that cannot be found.
     * </p>
     *
     * @return the ID of the contest that cannot be found
     */
    public long getContestId() {
        return contestId;
    }
}
