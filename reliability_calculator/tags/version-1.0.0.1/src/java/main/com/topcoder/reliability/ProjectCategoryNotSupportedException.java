/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of ReliabilityCalculator when project category with the given ID is not
 * supported by this reliability calculator.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ProjectCategoryNotSupportedException extends ReliabilityCalculationException {
    /**
     * <p>
     * The ID of the project category that is not supported by this reliability calculator.
     * </p>
     *
     * <p>
     * Is initialized in the constructor and never changed after that. Can be any value. Has a getter.
     * </p>
     */
    private final long projectCategoryId;

    /**
     * <p>
     * Constructor with error message and ID of the project category.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param projectCategoryId
     *            the ID of the project category that is not supported by this reliability calculator.
     */
    public ProjectCategoryNotSupportedException(String message, long projectCategoryId) {
        super(message);

        this.projectCategoryId = projectCategoryId;
    }

    /**
     * <p>
     * Constructor with error message, inner cause and ID of the project category.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param cause
     *            the underlying cause of the error. Can be <code>null</code>, which means that initial exception is
     *            nonexistent or unknown.
     * @param projectCategoryId
     *            the ID of the project category that is not supported by this reliability calculator.
     */
    public ProjectCategoryNotSupportedException(String message, Throwable cause, long projectCategoryId) {
        super(message, cause);

        this.projectCategoryId = projectCategoryId;
    }

    /**
     * <p>
     * Constructor with error message, exception data and ID of the project category.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param data
     *            the additional data to attach to the exception. If this is <code>null</code>, a new ExceptionData()
     *            will be automatically used instead.
     * @param projectCategoryId
     *            the ID of the project category that is not supported by this reliability calculator.
     */
    public ProjectCategoryNotSupportedException(String message, ExceptionData data, long projectCategoryId) {
        super(message, data);

        this.projectCategoryId = projectCategoryId;
    }

    /**
     * <p>
     * Constructor with error message, inner cause, exception data and ID of the project category.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param cause
     *            the underlying cause of the error. Can be <code>null</code>, which means that initial exception is
     *            nonexistent or unknown.
     * @param data
     *            the additional data to attach to the exception. If this is <code>null</code>, a new ExceptionData()
     *            will be automatically used instead.
     * @param projectCategoryId
     *            the ID of the project category that is not supported by this reliability calculator.
     */
    public ProjectCategoryNotSupportedException(String message, Throwable cause, ExceptionData data,
        long projectCategoryId) {
        super(message, cause, data);

        this.projectCategoryId = projectCategoryId;
    }

    /**
     * <p>
     * Gets the ID of the project category that is not supported by this reliability calculator.
     * </p>
     *
     * @return the ID of the project category that is not supported by this reliability calculator.
     */
    public long getProjectCategoryId() {
        return projectCategoryId;
    }
}
