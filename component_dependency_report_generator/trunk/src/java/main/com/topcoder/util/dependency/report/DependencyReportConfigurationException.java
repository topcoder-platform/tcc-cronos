/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This exception is thrown by <code>BaseDependencyReportGenerator</code> and its subclasses
 * when error occurred while reading the configuration (e.g. when configuration property is
 * missing or has invalid format).
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DependencyReportConfigurationException extends DependencyReportGenerationException {

    /**
     * <p>
     * Creates a new instance of this exception with error message.
     * </p>
     *
     * @param message The error message.
     */
    public DependencyReportConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of this exception with error message and inner cause.
     * </p>
     *
     * @param message The error message.
     * @param cause The cause of this exception.
     */
    public DependencyReportConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new instance of this exception with error message and exception data.
     * </p>
     *
     * @param data The exception data.
     * @param message The error message.
     */
    public DependencyReportConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates a new instance of this exception with error message, inner cause and exception data.
     * </p>
     *
     * @param message The error message.
     * @param cause The cause of this exception.
     * @param data The exception data.
     */
    public DependencyReportConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
