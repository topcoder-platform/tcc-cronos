/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of StatisticsCalculator when project with the given ID doesn't exist in
 * persistence.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ProjectNotFoundException extends StatisticsCalculatorException {
    /**
     * <p>
     * The ID of the project that cannot be found in persistence.
     * </p>
     *
     * <p>
     * Is initialized in the constructor and never changed after that. Can be any value. Has a getter.
     * </p>
     */
    private final long projectId;

    /**
     * Creates a new instance of this exception with the given message and project ID.
     *
     * @param projectId the ID of the project that cannot be found in persistence
     * @param message the detailed error message of this exception
     */
    public ProjectNotFoundException(String message, long projectId) {
        super(message);

        this.projectId = projectId;
    }

    /**
     * Creates a new instance of this exception with the given message, cause and project ID.
     *
     * @param projectId the ID of the project that cannot be found in persistence
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public ProjectNotFoundException(String message, Throwable cause, long projectId) {
        super(message, cause);

        this.projectId = projectId;
    }

    /**
     * Creates a new instance of this exception with the given message, exception data and project ID.
     *
     * @param projectId the ID of the project that cannot be found in persistence
     * @param data the exception data
     * @param message the detailed error message of this exception
     */
    public ProjectNotFoundException(String message, ExceptionData data, long projectId) {
        super(message, data);

        this.projectId = projectId;
    }

    /**
     * Creates a new instance of this exception with the given message, cause, exception data and project ID.
     *
     * @param projectId the ID of the project that cannot be found in persistence
     * @param data the exception data
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public ProjectNotFoundException(String message, Throwable cause, ExceptionData data, long projectId) {
        super(message, cause, data);

        this.projectId = projectId;
    }

    /**
     * Retrieves the ID of the project that cannot be found in persistence.
     *
     * @return the ID of the project that cannot be found in persistence
     */
    public long getProjectId() {
        return projectId;
    }
}
