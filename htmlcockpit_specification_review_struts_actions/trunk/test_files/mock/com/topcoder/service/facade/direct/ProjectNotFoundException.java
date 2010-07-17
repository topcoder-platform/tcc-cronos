/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of DirectServiceFacade when project with the specified ID cannot be
 * found in persistence.
 * </p>
 *
 * <p>
 * Annotation: @ApplicationException(rollback=true).
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
public class ProjectNotFoundException extends DirectServiceFacadeException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -1602131280331390827L;

    /**
     * <p>
     * The ID of the project that cannot be found. Is initialized in the constructor and never changed after that. Can
     * be any value. Has a getter.
     * </p>
     */
    private final long projectId;

    /**
     * <p>
     * Creates a new instance of this exception with the given message and project ID.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param projectId
     *            the ID of the project that cannot be found
     */
    public ProjectNotFoundException(String message, long projectId) {
        super(message);
        this.projectId = projectId;
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message, cause and project ID.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param cause
     *            the inner cause of this exception
     * @param projectId
     *            the ID of the project that cannot be found
     */
    public ProjectNotFoundException(String message, Throwable cause, long projectId) {
        super(message, cause);
        this.projectId = projectId;
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message, exception data and project ID.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param data
     *            the exception data
     * @param projectId
     *            the ID of the project that cannot be found
     */
    public ProjectNotFoundException(String message, ExceptionData data, long projectId) {
        super(message, data);
        this.projectId = projectId;
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message, cause, exception data and project ID.
     * </p>
     *
     * @param message
     *            the detailed error message of this exception
     * @param cause
     *            the inner cause of this exception
     * @param data
     *            the exception data
     * @param projectId
     *            the ID of the project that cannot be found
     */
    public ProjectNotFoundException(String message, Throwable cause, ExceptionData data, long projectId) {
        super(message, cause, data);
        this.projectId = projectId;
    }

    /**
     * <p>
     * Retrieves the ID of the project that cannot be found.
     * </p>
     *
     * @return the ID of the project that cannot be found
     */
    public long getProjectId() {
        return projectId;
    }
}
