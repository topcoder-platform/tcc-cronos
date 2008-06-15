/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of DependenciesEntryExtractor when fatal error occurred while extracting
 * dependencies from a file (e.g. when the specified file doesn't exist, has invalid or corrupted format, or when target
 * component name cannot be detected).
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class DependenciesEntryExtractionException extends ComponentDependencyException {
    /**
     * <p>
     * Serial version UID.
     * </p>
     */
    private static final long serialVersionUID = -3575471014121151367L;

    /**
     * <p>
     * Creates a new instance of this exception with the given message.
     * </p>
     *
     * @param message the detailed error message of this exception
     */
    public DependenciesEntryExtractionException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message and cause.
     * </p>
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public DependenciesEntryExtractionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message and exception data.
     * </p>
     *
     * @param data the data for this exception
     * @param message the detailed error message of this exception
     */
    public DependenciesEntryExtractionException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates a new instance of this exception with the given message, cause and exception data.
     * </p>
     *
     * @param data the data for this exception
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public DependenciesEntryExtractionException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
