/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import com.topcoder.util.errorhandling.BaseNonCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This exception is the base for all other custom exceptions defined in this component.
 * </p>
 *
 * <p>
 * It can also be thrown by implementations of <code>DependencyReportGenerator</code> when I/O
 * or persistence error occurs while generating the dependency report (in such cases this exception
 * is used as a wrapper for other exceptions).
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
public class DependencyReportGenerationException extends BaseNonCriticalException {

    /**
     * <p>
     * Creates a new instance of this exception with error message.
     * </p>
     *
     * @param message The error message.
     */
    public DependencyReportGenerationException(String message) {
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
    public DependencyReportGenerationException(String message, Throwable cause) {
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
    public DependencyReportGenerationException(String message, ExceptionData data) {
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
    public DependencyReportGenerationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
